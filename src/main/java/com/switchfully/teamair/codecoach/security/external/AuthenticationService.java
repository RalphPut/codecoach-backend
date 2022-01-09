package com.switchfully.teamair.codecoach.security.external;

import com.switchfully.teamair.codecoach.domain.entities.User;
import com.switchfully.teamair.codecoach.services.UserService;
import com.switchfully.teamair.codecoach.services.exceptions.InvalidUserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;

        this.passwordEncoder = passwordEncoder;
    }
    public SecuredUser getUser(String username, String password) {

        User user = userService.findUserByEmail(username);
        if(user == null){
            throw new InvalidUserException();
        }
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidUserException();
        }

        return SecuredUser.externalAuthentication()
                .withId(user.getUserId().toString())
                .withUsername(username)
                .withPassword(password)
                .withRoles(newArrayList(user.getRoles()
                        .stream()
                        .map(role -> role.getAuthority().toString()).toList()));
    }
}
