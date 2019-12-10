package com.coreangel.managely.security;

import com.coreangel.managely.config.SecretsConfigurationValueService;
import com.coreangel.managely.security.user.datails.CustomUserDetails;
import com.coreangel.managely.security.user.datails.CustomUserDetailsService;
import com.coreangel.managely.utils.JwtTokenUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecretsConfigurationValueService secrets;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService detailsService;

    public JwtTokenAuthenticationFilter(SecretsConfigurationValueService secrets, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService detailsService) {
        this.secrets = secrets;
        this.jwtTokenUtil = jwtTokenUtil;
        this.detailsService = detailsService;
    }

    @Transactional
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        String header = request.getHeader(secrets.getJwtHeader());

        if(header == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String login = this.jwtTokenUtil.getLoginFromToken(header);

            CustomUserDetails userDetails = this.detailsService.loadUserByUsername(login);
            if (this.jwtTokenUtil.validateToken(header, userDetails.getAccount())) {
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails, header);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
