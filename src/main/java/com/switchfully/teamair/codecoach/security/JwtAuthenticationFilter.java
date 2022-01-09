package com.switchfully.teamair.codecoach.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.teamair.codecoach.security.external.SecuredUser;
import com.switchfully.teamair.codecoach.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final String JwtSecret;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
        this.authenticationManager = authenticationManager;
        JwtSecret = jwtSecret;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecuredUser securedUser = getSecuredUser(request);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(securedUser.getEmail(), securedUser.getPassword()));
    }

    private SecuredUser getSecuredUser(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), SecuredUser.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read body from request", e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        var token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(JwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(authentication.getName())
                .setExpiration(new Date(new Date().getTime() + 3600000)) // 1h
                .claim("rol", authentication.getAuthorities())
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);

    }


}
