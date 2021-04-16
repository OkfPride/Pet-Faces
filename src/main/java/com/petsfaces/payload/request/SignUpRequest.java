/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Data
public class SignUpRequest {
    @Email(message = "must be an email in format ****@***.*** ")
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "firstname cannot be Empty")
    private String firstname;
    @NotEmpty(message = "lastname cannot be Empty")
    private String lastname;
    @NotEmpty(message = "username cannot be Empty")
    private String username;
    @NotEmpty(message = "password cannot be Empty")
    @Size(min = 6)
    private String password;
    @NotEmpty(message = "confirm password cannot be Empty")
    private String confirmPassword;
}
