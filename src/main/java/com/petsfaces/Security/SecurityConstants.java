/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Security;

/**
 *
 * @author JavaDev
 */
public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/auth/*";
    public static final String SECRET = "SecretkeyGenJWT";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING= "Authorization";
    public static final String CONTENT_TYPE= "application/json";
    //время истечения сессии в милисикундах
    public static final long EXPIRATION_TIME= 600_000_000;
     
}
