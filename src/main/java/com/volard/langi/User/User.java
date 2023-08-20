package com.volard.langi.User;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "Users")
@NoArgsConstructor // Empty constructor is required by the data layer and JSON (maybe)
@AllArgsConstructor
@Data
public class User implements UserDetails {
    @Id private String id;
    private String username;
    private String email;
    private String accountPassword;
    private boolean isActive = true;
    private boolean isNonLocked = false;
    private boolean isNonExpired = true;
    private boolean isAccountNonExpired = true;
    private List<String> roles = List.of(Role.USER.toString());

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    @Override
    public String getPassword() {
        return accountPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
