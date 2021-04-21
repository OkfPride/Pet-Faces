/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author JavaDev
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistExseption extends RuntimeException{

    public UserExistExseption(String message) {
        super(message);
    }
}
