/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.validators;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 *
 * @author JavaDev
 */
@Service
public class ResponseErrorValidation {
    public ResponseEntity<Object>mapValidationService(BindingResult result){
        if (result.hasErrors()) {
            HashMap<String, String> hashMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError allError : result.getAllErrors()) {
                    hashMap.put(allError.getCode(), allError.getDefaultMessage());
                }
            }
            for (FieldError fieldError : result.getFieldErrors()) {
                hashMap.put(fieldError.getCode(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
