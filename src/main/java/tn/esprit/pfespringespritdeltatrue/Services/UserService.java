package tn.esprit.pfespringespritdeltatrue.Services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.pfespringespritdeltatrue.Config.JwtUtils;
import tn.esprit.pfespringespritdeltatrue.Entities.*;
import tn.esprit.pfespringespritdeltatrue.Repositories.RoleRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtils jwtUtils;

    // Add new user, encode password and assign a role
    public User addUser(User user, Long roleId) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set role for the user
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        String Email = user.getEmail();
        String Name = user.getName();
        String Surname = user.getSurname();
        String Password = user.getPassword();
        emailService.sendSimpleEmail(Email,"Vos identifiants de connexion","Bonjour" +Name + Surname+" ! \n **Adresse e-mail** :"+Email+" \n **Mot de passe** : "+Password+" \n  Nous vous recommandons de modifier votre mot de passe dès votre première connexion pour des raisons de sécurité. \n Si vous avez des questions ou besoin d'aide, n'hésitez pas à nous contacter. \n Nous sommes ravis de vous compter parmi nous et espérons que vous apprécierez votre expérience sur notre plateforme.\n Cordialement," );
        // Save the user to the repository
        return userRepository.save(user);
    }

    // Authenticate user and generate JWT token
    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            user.setConnected(Connection.Connected);  // Set user as connected after authentication
            userRepository.save(user);  // Save the updated user with the "Connected" status
            return jwtUtils.generateToken(user.getEmail()); // Return JWT token if authentication is successful
        }
        return null; // Return null if authentication fails
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString(); // for non-UserDetails implementations
            }
        }
        return null;
    }

    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName(); // Assumes the user's email is the username
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Set the user's account status to Disconnected (log out)
                user.setConnected(Connection.Disconnected);
                userRepository.save(user); // Save the updated user to the database
            }
        }

        // Invalidate the current session
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        // Clear the SecurityContext
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("User logged out successfully, account set to Disconnected");
    }

    public List<User> getConnectedUsers() {
        return userRepository.ConnectedUser();
    }

    public List<User> getDisconnectedUsers() {
        return userRepository.DisconnectedUser();
    }

    public List<User> getActiveUsers() {
        return userRepository.CompteActiveUser();
    }

    public List<User> getBlockedUsers() {
        return userRepository.BloqueeUser();
    }
    public List<User> searchByKeyword( String keyword){
        return userRepository.searchByKeyword(keyword);
    }

}
