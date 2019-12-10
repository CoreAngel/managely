package com.coreangel.managely.config;

import com.coreangel.managely.model.AccountRole;
import com.coreangel.managely.security.JwtTokenAuthenticationFilter;
import com.coreangel.managely.security.user.datails.CustomUserDetailsService;
import com.coreangel.managely.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private SecretsConfigurationValueService secrets;
    private JwtTokenUtil jwtTokenUtil;
    private CustomUserDetailsService detailsService;

    @Autowired
    public SecurityConfiguration(SecretsConfigurationValueService secrets, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService detailsService) {
        this.secrets = secrets;
        this.jwtTokenUtil = jwtTokenUtil;
        this.detailsService = detailsService;
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(detailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable().and()
                .formLogin().disable();

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2_console/**").permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/**").hasAnyAuthority(AccountRole.USER.toString())
                .anyRequest().authenticated().and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(secrets, jwtTokenUtil, detailsService), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/**",
                "/",
                "/h2-console/**"
        );

    }


}
