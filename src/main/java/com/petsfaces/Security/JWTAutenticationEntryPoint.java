/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Security;

import com.google.gson.Gson;
import com.petsfaces.payload.response.InvalidLoginResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 */
@Component
public class JWTAutenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest hsr, HttpServletResponse hsr1, AuthenticationException ae) throws IOException, ServletException {
        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        String toJson = new Gson().toJson(invalidLoginResponse);
        hsr1.setContentType(SecurityConstants.CONTENT_TYPE);
        hsr1.setStatus(HttpStatus.UNAUTHORIZED.value());
        hsr1.getWriter().println(toJson);

    }
    
}
