package com.switchfully.teamair.codecoach.services.mappers;

import com.switchfully.teamair.codecoach.api.dtos.UserDtoRequest;
import com.switchfully.teamair.codecoach.api.dtos.UserDtoResponse;
import com.switchfully.teamair.codecoach.domain.entities.User;
import com.switchfully.teamair.codecoach.domain.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final CoachDetailsMapper coachDetailsMapper;
    private final RoleRepository roleRepository;

    public UserMapper(RoleMapper roleMapper, CoachDetailsMapper coachDetailsMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
        this.coachDetailsMapper = coachDetailsMapper;
        this.roleRepository = roleRepository;
    }

    public UserDtoResponse toResponse(User user) {

        if (user.getCoachDetails() != null) {

            return UserDtoResponse.builder()
                    .email(user.getEmail())
                    .company(user.getCompany())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userId(user.getUserId())
                    .imageUrl(user.getImageUrl())
                    .roleStatus(user.getRoles()
                            .stream()
                            .map(roleMapper::toRoleDtoResponse).toList())
                    .coachDetails(coachDetailsMapper.toResponse(user.getCoachDetails()))
                    .build();
        } else {
            return UserDtoResponse.builder()
                    .email(user.getEmail())
                    .company(user.getCompany())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userId(user.getUserId())
                    .imageUrl(user.getImageUrl())
                    .roleStatus(user.getRoles()
                            .stream()
                            .map(roleMapper::toRoleDtoResponse).toList())
                    .build();

        }


    }

//    public CoacheeDtoResponse toCoacheeResponse(User user){
//        return CoacheeDtoResponse.builder()
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .imageUrl(user.getImageUrl())
//                .build();
//    }

    public User toEntity(UserDtoRequest userDtoRequest) {
        if (userDtoRequest.getCoachDetailsDtoRequest() != null) {
            return User.builder()
                    .email(userDtoRequest.getEmail())
                    .company(userDtoRequest.getCompany())
                    .firstName(userDtoRequest.getFirstName())
                    .lastName(userDtoRequest.getLastName())
                    .password(passwordEncoder.encode(userDtoRequest.getPassword()))
                    .roles(new HashSet<>())
                    .coachDetails(coachDetailsMapper.toEntity(userDtoRequest.getCoachDetailsDtoRequest()))
                    .build();
        } else {
            return User.builder()
                    .email(userDtoRequest.getEmail())
                    .company(userDtoRequest.getCompany())
                    .firstName(userDtoRequest.getFirstName())
                    .lastName(userDtoRequest.getLastName())
                    .password(passwordEncoder.encode(userDtoRequest.getPassword()))
                    .roles(new HashSet<>())
                    .build();
        }
    }

}
