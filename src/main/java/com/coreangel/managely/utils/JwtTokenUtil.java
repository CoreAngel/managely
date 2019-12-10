package com.coreangel.managely.utils;

import com.coreangel.managely.config.SecretsConfigurationValueService;
import com.coreangel.managely.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private SecretsConfigurationValueService secretsService;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Autowired
    public JwtTokenUtil(SecretsConfigurationValueService secretsService) {
        this.secretsService = secretsService;
    }

    public String getLoginFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public String generateToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        return this.doGenerateToken(claims, account.getLogin());
    }

    public boolean validateToken(String token, Account account) {
        final String username = this.getLoginFromToken(token);
        return username.equals(account.getLogin()) && !this.isTokenExpired(token);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secretsService.getJwtSecret()).parseClaimsJws(token).getBody();
    }


    private boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, this.secretsService.getJwtSecret())
                .compact();
    }

}
