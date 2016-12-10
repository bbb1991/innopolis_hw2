package me.bbb1991.service;

import me.bbb1991.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Created by bbb1991 on 12/10/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class CustomUserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    private Set<GrantedAuthority> authorities = null;

    public Long getId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
        return !user.isBlocked();
    }

    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    public boolean isCredentialsNonExpired() {
        return !user.isBlocked();
    }

    public boolean isEnabled() {
        return !user.isBlocked();
    }

}