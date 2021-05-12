/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.AOP.pointcuts;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 * 
 * execution( modifiers-pattern? return-type-pattern{1} declaring-type-pattern? 
method-name-pattern(parameters-pattern){1} throws-pattern? )
 */
@Aspect
@Component

public class PerfomancePointcuts {
    @Pointcut(value = "execution(public * com.petsfaces.web.*.*(..))")
    public void log(){
        
    }
}
