/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.exceptions;

/**
 *
 * @author Филипповы
 */
public class ImageNotFoundException extends RuntimeException{
    
    private static final String  def= "not image";

    public ImageNotFoundException() {
        super(def);
    }

    public ImageNotFoundException(String message) {
        super(message);
    }
    
}
