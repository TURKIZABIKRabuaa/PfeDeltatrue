package tn.esprit.pfespringespritdeltatrue.Entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();  // Delegate to User entity to return authorities
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can implement your logic here
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can implement your logic here
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can implement your logic here
    }

    @Override
    public boolean isEnabled() {
        return true; // You can implement your logic here
    }

    public User getUser() {
        return user;
    }
}
