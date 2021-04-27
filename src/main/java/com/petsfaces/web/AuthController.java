/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import com.petsfaces.Security.JWTTokenProvider;
import com.petsfaces.Security.SecurityConstants;
import com.petsfaces.exceptions.UserExistExseption;
import com.petsfaces.payload.request.LoginRequest;
import com.petsfaces.payload.request.SignUpRequest;
import com.petsfaces.payload.response.JWTTokenSuccessResponse;
import com.petsfaces.payload.response.MessageResponse;
import com.petsfaces.servises.UserService;
import com.petsfaces.validators.ResponseErrorValidation;
import javax.validation.Valid;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JavaDev
 */
//CORS (Cross-Origin Resource Sharing) https://sysout.ru/nastrojka-cors-v-spring-security/
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    
    private ResponseErrorValidation responseErrorValidation;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jWTTokenProvider;
    private UserService userService;
    
    @Autowired
    public AuthController(ResponseErrorValidation responseErrorValidation, AuthenticationManager authenticationManager, JWTTokenProvider jWTTokenProvider, UserService userService) {
        this.responseErrorValidation = responseErrorValidation;
        this.authenticationManager = authenticationManager;
        this.jWTTokenProvider = jWTTokenProvider;
        this.userService = userService;
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = SecurityConstants.TOKEN_PREFIX + jWTTokenProvider.generateToken(authenticate);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
        
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        try {
            userService.createUser(signUpRequest);
        } catch (UserExistExseption e) {
            return new ResponseEntity<>("User alredy Exists ",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("User succesfully registered"));
    }
    
}
