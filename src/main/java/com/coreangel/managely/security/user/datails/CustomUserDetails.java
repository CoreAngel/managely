package com.coreangel.managely.security.user.datails;

import com.coreangel.managely.model.Account;
import com.coreangel.managely.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class CustomUserDetails implements UserDetails {
    private Account account;

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        for (Role role: this.account.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getRole().toString()));
        }

        return list;
    }

    public Account getAccount() {
        return this.account;
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    public String getEmail() {
        return this.account.getEmail();
    }

    public long getID() {
        return this.account.getId();
    }

    @Override
    public String getUsername() {
        return this.account.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
