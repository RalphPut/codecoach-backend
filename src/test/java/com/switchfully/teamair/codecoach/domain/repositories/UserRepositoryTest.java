//package com.switchfully.teamair.codecoach.domain.repositories;
//
//import com.switchfully.teamair.codecoach.domain.entities.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//    private User userEntity;
//
//    @BeforeAll
//    void setUp() {
//        userEntity = new User();
//        userRepository.save(userEntity);
//    }
//
//    @Test
//    void getAllUsers() {
//        var toCheck = userRepository.findAll().size();
//        Assertions.assertEquals(1, toCheck);
//    }
//
//    @Test
//    void getUserById() {
//        var toCheck = userRepository.findById(userEntity.getUserId());
//        Assertions.assertNotNull(toCheck);
//    }
//
//    @Test
//    void createUser() {
//        userRepository.save(new User());
//        var toCheck = userRepository.findAll().size();
//        Assertions.assertEquals(2, toCheck);
//    }
//
////    @Test
////    void updateUser() {
////    }
////
////    @Test
////    void deleteUser() {
////    }
//}
