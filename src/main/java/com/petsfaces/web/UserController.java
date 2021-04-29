/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import com.petsfaces.Entity.User;
import com.petsfaces.UserDataTransferObject.UserDTO;
import com.petsfaces.servises.IUserServise;
import com.petsfaces.validators.ResponseErrorValidation;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JavaDev
 */
@RestController
@CrossOrigin
@RequestMapping(name = "/api/user")
public class CRUD_Controller {

    private IUserServise iuserService;
    private ResponseErrorValidation responseErrorValidation;

    @Autowired
    public CRUD_Controller(IUserServise iuserService, ResponseErrorValidation responseErrorValidation) {
        this.iuserService = iuserService;
        this.responseErrorValidation = responseErrorValidation;
    }

//    @PostMapping(name = "/update")
//    public User updateUser(UserDTO userDTO, Principal principal, BindingResult bindingResult) {
//        iuserService.getUserbyName();
//        return user;
//    }

}
