/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.validators;

import com.petsfaces.annotations.PasswordMatches;
import com.petsfaces.payload.request.SignUpRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author JavaDev
 */
public class PasswordMatchesValidator implements  ConstraintValidator <PasswordMatches, Object>{

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

 

    @Override
    public boolean isValid(Object t, ConstraintValidatorContext cvc) {
        SignUpRequest signUpRequest = (SignUpRequest) t;
        return signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword());
        
    }
    
}
