package com.switchfully.teamair.codecoach.services;

import com.switchfully.teamair.codecoach.api.dtos.*;
import com.switchfully.teamair.codecoach.domain.entities.*;
import com.switchfully.teamair.codecoach.domain.repositories.CoachDetailsRepository;
import com.switchfully.teamair.codecoach.domain.repositories.RoleRepository;
import com.switchfully.teamair.codecoach.domain.repositories.TopicRepository;
import com.switchfully.teamair.codecoach.domain.repositories.UserRepository;
import com.switchfully.teamair.codecoach.security.SecurityConstants;
import com.switchfully.teamair.codecoach.services.mappers.TopicAssociationMapper;
import com.switchfully.teamair.codecoach.services.mappers.UserMapper;
import com.switchfully.teamair.codecoach.services.utils.ValidationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private static final int COACHEE_ID = 1;
    private static final int COACH_ID = 2;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ValidationService validationService;
    private final CoachDetailsRepository coachDetailsRepository;
    private final TopicAssociationMapper topicAssociationMapper;
    private final TopicRepository topicRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final String JwtSecret;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       RoleRepository roleRepository, ValidationService validationService, CoachDetailsRepository coachDetailsRepository,
                       TopicAssociationMapper topicAssociationMapper, @Value("${jwt.secret}") String jwtSecret, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.validationService = validationService;
        this.coachDetailsRepository = coachDetailsRepository;
        this.topicAssociationMapper = topicAssociationMapper;
        JwtSecret = jwtSecret;
        this.topicRepository = topicRepository;
        roleRepository.save(new Role(RoleStatus.COACHEE));
        roleRepository.save(new Role(RoleStatus.COACH));
    }

    public void registerUser(UserDtoRequest userDtoRequest) {
        validationService.assertCreateUserNotNull(userDtoRequest);
        validationService.assertEmailIsNew(userDtoRequest.getEmail());
        validationService.assertPasswordIsValid(userDtoRequest.getPassword());
        User user = userMapper.toEntity(userDtoRequest);
        user.addRole(roleRepository.findRoleById(COACHEE_ID));
        userRepository.save(user);
        logger.info("Service: User with email {} has been registered", user.getEmail());
        logger.info("Service: This user has id {}", user.getUserId());
    }

    @Cacheable("allCoachesCache")
    public List<UserDtoResponse> getAllCoaches() {
        logger.info("Service: Getting all coaches");
        Role role = roleRepository.findRoleById(COACH_ID);
        return userRepository.findAllByRolesContaining(role).stream().map(userMapper::toResponse).toList();
    }

    public UserDtoResponse getUserById(String userId, String authorizationToken) {
        validationService.assertUserExists(UUID.fromString(userId));
        User user = userRepository.findUserByUserId(UUID.fromString(userId));
        Role role = roleRepository.findRoleById(COACH_ID);
        if (!user.getRoles().contains(role)) {
            validationService.assertValidToken(userId, authorizationToken);
        }
        logger.info("Service: Getting user with id {}", userId);
        return userMapper.toResponse(user);
    }

    public String toggleCoachStatus(String userId, String authorizationToken) {
        logger.info("Service: Toggling coach status for user with id {}", userId);
        validationService.assertUserExists(UUID.fromString(userId));
        validationService.assertValidToken(userId, authorizationToken);
        Role coachRole = roleRepository.findRoleById(COACH_ID);
        User user = userRepository.findUserByUserId(UUID.fromString(userId));

        if (!user.getRoles().contains(coachRole)) {
            addCoachData(user, coachRole);
            logger.info("Service: User with id {} is now a coach", userId);
        } else {
            removeCoachData(user, coachRole);
            logger.info("Service: User with id {} is no longer a coach", userId);
        }
        userRepository.save(user);
        return createRefreshedToken(UUID.fromString(userId));
    }

    public void addCoachData(User user, Role coachRole) {
        user.addRole(coachRole);
        CoachDetails coachDetails = new CoachDetails();
        user.setCoachDetails(coachDetails);
    }

    public void removeCoachData(User user, Role coachRole) {
        Integer id = user.getCoachDetails().getId();
        user.removeRole(coachRole);
        user.getCoachDetails().removeTopicAssociations();
        user.removeCoachDetails();
        coachDetailsRepository.deleteCoachDetailsById(id);

        //TODO: delete according coachdetail
    }

    @CacheEvict(value = "allCoachesCache", allEntries = true)
    public void updateUser(String userId, UpdateUserDtoRequest updateUserDtoRequest, String authorizationToken) {
        validationService.assertUserExists(UUID.fromString(userId));
        validationService.assertValidToken(userId, authorizationToken);
        validationService.assertEmailIsNew(updateUserDtoRequest.getEmail());
        User user = userRepository.findUserByUserId(UUID.fromString(userId));

        user.setFirstName(updateUserDtoRequest.getFirstName());
        user.setLastName(updateUserDtoRequest.getLastName());
        user.setEmail(updateUserDtoRequest.getEmail());
        user.setCompany(updateUserDtoRequest.getCompany());
        user.setImageUrl(updateUserDtoRequest.getImageUrl());

        if (user.getRoles().contains(roleRepository.findRoleById(COACH_ID))) {
            logger.info("Service: User is a coach, updating getting coach details");
            if (updateUserDtoRequest.getCoachDetails() != null) {
                CoachDetailsDtoRequest coachDetailsDtoRequest = updateUserDtoRequest.getCoachDetails();
                user.getCoachDetails().setAvailableTime(coachDetailsDtoRequest.getAvailableTime());
                user.getCoachDetails().setIntroduction(coachDetailsDtoRequest.getIntroduction());
                user.getCoachDetails().setCoachXp(coachDetailsDtoRequest.getCoachXp());
                TopicAssociationDtoRequest newTopicAssociationDtoRequests = updateUserDtoRequest.getCoachDetails().getTopics();
                updateTopicAssociation(userId, newTopicAssociationDtoRequests);

            }
        }
    }

    public void updateTopicAssociation(String userId,
                                       TopicAssociationDtoRequest newTopicAssociationDtoRequests) {

        CoachDetails coachDetails = userRepository.findUserByUserId(UUID.fromString(userId)).getCoachDetails();
        TopicAssociation associations = topicAssociationMapper.toEntity(newTopicAssociationDtoRequests);

        if(associations.getTopic() != null && associations.getCoachKnowledgeLevel() != null){
            Topic topic = topicRepository.findTopicByName(associations.getTopic().getName());
            if (topic == null) {
                Topic newTopic = new Topic(associations.getTopic().getName());
                topicRepository.save(newTopic);
                coachDetails.addTopic(topicRepository.findTopicByName(newTopic.getName()), associations.getCoachKnowledgeLevel());
            } else {
                for (TopicAssociation topicAssociation : coachDetails.getTopics()) {
                    Topic existingTopic = topicAssociation.getTopic();
                    if (existingTopic.getName().equals(newTopicAssociationDtoRequests.getTopic().getName())) {
                        topicAssociation.setCoachKnowledgeLevel(newTopicAssociationDtoRequests.getCoachKnowledgeLevel());
                        return;
                    }
                }
                coachDetails.addTopic(topic, associations.getCoachKnowledgeLevel());
            }
        }

    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String createRefreshedToken(UUID userId) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(JwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(userId.toString())
                .setExpiration(new Date(new Date().getTime() + 3600000)) // 1h
                .claim("rol", userRepository.findUserByUserId(userId).getRoles())
                .compact();
    }
}
