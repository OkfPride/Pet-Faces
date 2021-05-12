/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import com.petsfaces.Entity.User;
import com.petsfaces.data_transfer_object.UserDTO;
import com.petsfaces.facade.UserFacade;
import com.petsfaces.servises.IUserServise;
import com.petsfaces.servises.UserService;
import com.petsfaces.validators.ResponseErrorValidation;
import java.security.Principal;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JavaDev
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private IUserServise userService;
    private ResponseErrorValidation responseErrorValidation;
    private UserFacade userFacade;
    
    @Autowired
    public UserController(IUserServise userService, ResponseErrorValidation responseErrorValidation, UserFacade userFacade) {
        this.userService = userService;
        this.responseErrorValidation = responseErrorValidation;
        this.userFacade = userFacade;
    }
    
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDTO userDTO, Principal principal, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        User updatedUser = userService.updateUser(userDTO, principal);
        UserDTO updatedUserDTO = userFacade.userToUserDTO(updatedUser);
        logger.info("User "+ updatedUserDTO +" is updated");
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") String id) {
        User userById = null;
        UserDTO userDTO = null;
        try {
            userById = userService.getUserById(Long.parseLong(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(userDTO, HttpStatus.NOT_FOUND);
        }
        userDTO = userFacade.userToUserDTO(userById);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getUserbyName(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
}
