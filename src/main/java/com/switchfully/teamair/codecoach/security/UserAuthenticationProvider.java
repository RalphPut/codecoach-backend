package com.switchfully.teamair.codecoach.security;

import com.switchfully.teamair.codecoach.security.external.AuthenticationService;
import com.switchfully.teamair.codecoach.security.external.SecuredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    @Autowired
    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecuredUser user = authenticationService.
            getUser(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(
                   user.getId(),
                    user.getPassword(),
                    user.getRoles());
        }
        throw new BadCredentialsException("The provided credentials were invalid.");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
