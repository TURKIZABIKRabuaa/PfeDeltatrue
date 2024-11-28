package tn.esprit.pfespringespritdeltatrue.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.pfespringespritdeltatrue.Config.CustomUserDetailsService;
import tn.esprit.pfespringespritdeltatrue.Config.JwtUtils;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = jwtUtils.getJwtFromRequest(request);

        if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
            // Extract email from the JWT token
            String email = jwtUtils.getUserEmailFromJwtToken(jwtToken);

            // Load the user details by email
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // Create an Authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Set request details for web authentication
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication object into the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
