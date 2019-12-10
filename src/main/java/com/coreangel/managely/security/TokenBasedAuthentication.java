package com.coreangel.managely.security;

import com.coreangel.managely.security.user.datails.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    private final CustomUserDetails principle;
    private final String token;

    public TokenBasedAuthentication(CustomUserDetails principle, String token) {
        super( principle.getAuthorities() );
        this.principle = principle;
        this.token = token;
        this.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return this.token;
    }

    @Override
    public CustomUserDetails getPrincipal() {
        return this.principle;
    }




}
