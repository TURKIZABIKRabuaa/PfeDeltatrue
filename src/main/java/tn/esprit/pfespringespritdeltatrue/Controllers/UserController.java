package tn.esprit.pfespringespritdeltatrue.Controllers;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pfespringespritdeltatrue.Config.JwtUtils;
import tn.esprit.pfespringespritdeltatrue.Entities.SignInResponse;
import tn.esprit.pfespringespritdeltatrue.Entities.User;
import tn.esprit.pfespringespritdeltatrue.Repositories.UserRepository;
import tn.esprit.pfespringespritdeltatrue.Services.UserService;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("User")
public class UserController  {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    // SignUp method to register a new user
    @PostMapping("SignUp")
    public User addUser(@RequestBody User user, @RequestParam Long roleId) {
        return userService.addUser(user, roleId);
    }

    // SignIn method to authenticate and generate JWT token
    @PostMapping("SignIn")
    public SignInResponse signIn(@RequestParam String email, @RequestParam String password) {
        // Authenticate user and get the JWT token
        String jwtToken = userService.authenticateUser(email, password);

        if (jwtToken != null) {
            // Get the user details after successful authentication
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            // Return both JWT token and user details in the response
            return new SignInResponse(jwtToken, user);
        } else {
            throw new RuntimeException("Invalid credentials"); // Handle invalid login
        }
    }

    // Logout method to invalidate the session and clear context
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        userService.logout(request, response);

        // Return a success message after logout
        return ResponseEntity.ok("User logged out successfully");
    }

    // Method to retrieve the current authenticated user
    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null); // Return unauthorized if principal is not available
        }

        String email = principal.getName(); // Retrieve the email from the principal

        // Retrieve the user from the repository based on email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Return not found if the user doesn't exist
        }

        User user = userOptional.get(); // Get the user from the Optional

        return ResponseEntity.ok(user); // Return the user with a 200 OK response
    }

    // Endpoint to get all connected users
    @GetMapping("ConnectedUser")
    public List<User> getConnectedUsers() {
        return userService.getConnectedUsers();
    }
    @GetMapping("/userSearch")
    List<User> searchByKeyword(@Param("keyword") String keyword){
        return userService.searchByKeyword(keyword);
    }

    // Endpoint to get all disconnected users
    @GetMapping("DisconnectedUser")
    public List<User> getDisconnectedUsers() {
        return userService.getDisconnectedUsers();
    }

    // Endpoint to get all active users
    @GetMapping("ActiveUserAccounts")
    public List<User> getActiveUsers() {
        return userService.getActiveUsers();
    }

    // Endpoint to get all blocked users
    @GetMapping("BlockedUserAccounts")
    public List<User> getBlockedUsers() {
        return userService.getBlockedUsers();
    }
}
