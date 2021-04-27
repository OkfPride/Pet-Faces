/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.validators;

import com.petsfaces.annotations.ValidEmail;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author JavaDev
 */
public class EmailValidator  implements  ConstraintValidator<ValidEmail, String>{
    private static final String EMAIL_PATTERN = "^[\\w]+@{1}[A-Za-z]+\\.[\\w]+";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cvc) {
        return (validateEmail(email));
    }
    private boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN); // here is regex pattern wich will check input
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
