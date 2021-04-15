/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.payload.response;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 */
@Getter
public class InvalidLoginResponse {
    
    private String username;
    private String password;
    
    public InvalidLoginResponse(){
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
