/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.AOP.advices;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.SourceLocation;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 */
@Component
@Aspect
public class PerfomanceAspects {

   private static final Logger LOGGER = LoggerFactory.getLogger(PerfomanceAspects.class);

    @Before(value = "com.petsfaces.AOP.pointcuts.PerfomancePointcuts.log()")
    private void loger() {
        System.out.println("Before");
    }

    @After(value = "com.petsfaces.AOP.pointcuts.PerfomancePointcuts.log()")
    private void logerAfter() {
        System.out.println("After");
    }

    @AfterReturning(value = "com.petsfaces.AOP.pointcuts.PerfomancePointcuts.log()")
    private void logerAfterReturning() {
        System.out.println("AfterReturning");
    }

    @AfterThrowing(value = "com.petsfaces.AOP.pointcuts.PerfomancePointcuts.log()")
    private void logerAfterThrowing() {
        System.out.println("AfterThrowing");
    }

    @Around(value = "com.petsfaces.AOP.pointcuts.PerfomancePointcuts.log()")
    private void loggerAround(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("before Around");
       long start = System.nanoTime();
        try {
            proceedingJoinPoint.proceed();
//            Class<? extends ProceedingJoinPoint> aClass = proceedingJoinPoint.getClass();
//            SourceLocation sourceLocation = proceedingJoinPoint.getSourceLocation();
//            Object target = proceedingJoinPoint.getTarget();
//            Object thiss = proceedingJoinPoint.getThis();
//            Signature signature = proceedingJoinPoint.getSignature();
//            String name = signature.getName();
//            Class declaringType = signature.getDeclaringType();
//            String declaringTypeName = signature.getDeclaringTypeName();
//            
//            
//            System.out.println(name+ " | "+ declaringType+ " | "+ declaringTypeName);
//            System.out.println(aClass+ " | "+ sourceLocation+ " |\n "+ target+ " | "+thiss+ " | "+signature);
        } catch (Throwable ex) {
            LOGGER.warn("Smth went wronng in "+ proceedingJoinPoint.getSignature());
        }
       long end = System.nanoTime();
//       Class.forName("").
//       LOGGER.info("Perfomance of method "+GetDeclaredMethod()+"  = " +(end-start)/1000000);
        System.out.println("Perfomance of "+proceedingJoinPoint.getSignature().getDeclaringTypeName()+"."+proceedingJoinPoint.getSignature().getName()+" is :"+(end-start)/1000000 + "[ miliseconds]");
        System.out.println("after Around");
    }

}
