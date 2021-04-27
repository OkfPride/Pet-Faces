/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.User;
import com.petsfaces.Entity.enums.UserRole;
import com.petsfaces.UserDataTransferObject.UserDTO;
import com.petsfaces.exceptions.UserExistExseption;
import com.petsfaces.payload.request.SignUpRequest;
import com.petsfaces.repositories.UserRepository;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author JavaDev
 */
@Service
public class UserService implements IUserServise {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getUserRole().add(UserRole.ROLE_USER);
        try {
            logger.info("Saving user ", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("error during registration", e.getMessage());
            throw new UserExistExseption("user " + user.getUsername() + " already exist");
        }
    }

    @Override
    public User updateUser(UserDTO user, Principal principal) {
        User userByName = getUserByName(principal);
        userByName.setBiograthy(user.getBio());
        userByName.setLastname(user.getLastName());
        userByName.setName(user.getFirstName());
        return userRepository.save(userByName);
    }
    @Override
    public User getUserbyName(Principal principal){
        return getUserByName(principal);
    }
    
    private User getUserByName(Principal principal){
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = "+ username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

}
