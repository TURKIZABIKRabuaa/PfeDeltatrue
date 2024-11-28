package tn.esprit.pfespringespritdeltatrue.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor injection for JwtUtils, CustomUserDetailsService, and JwtAuthenticationFilter
    public SecurityConfig(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure CORS and disable CSRF
        http
                .cors()  // Enable CORS support
                .and()
                .csrf().disable()  // Disable CSRF protection for stateless JWT authentication
                .authorizeRequests()
                .requestMatchers("/esprit/User/current-user").authenticated()
                .requestMatchers("/**").permitAll()  // Allow all requests to any endpoint
                .anyRequest().authenticated()  // Require authentication for other requests
                .and()
                .formLogin().permitAll()  // Enable form login (if needed for certain endpoints)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before the default authentication filter

        return http.build();
    }

    // Define the AuthenticationManager to handle user authentication using CustomUserDetailsService
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)  // Use custom service to load user details by email
                .passwordEncoder(passwordEncoder())  // Use BCryptPasswordEncoder for password matching
                .and()
                .build();
    }

    // Password Encoder Bean: Used to encode passwords securely (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCryptPasswordEncoder for password encoding
    }
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutUrl("/logout")  // URL to log out
                .logoutSuccessUrl("/login")  // Redirect after successful logout
                .invalidateHttpSession(true)  // Invalidate the session
                .clearAuthentication(true)  // Clear the authentication
                .permitAll();
    }


}
