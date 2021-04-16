/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.payload.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Data
public class LoginRequest {

    @NotEmpty(message = "cannot be empty")
    private String username;
    @NotEmpty(message = "cannot be empty")
    private String password;
    
    
}
