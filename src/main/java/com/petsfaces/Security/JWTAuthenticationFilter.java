/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Security;

import com.petsfaces.Entity.User;
import com.petsfaces.servises.CustomUserDetailServise;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author JavaDev
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTTokenProvider jWTTokenProvider;
    @Autowired
    CustomUserDetailServise customUserDetailServise;

    Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest hsreq, HttpServletResponse hsresp, FilterChain fc) throws ServletException, IOException {
        try {
            String jwtFromRequest = getJWTFromRequest(hsreq);
            if (StringUtils.hasText(jwtFromRequest)&&jWTTokenProvider.validateToken(jwtFromRequest)) {
                Long userIdFromToken = jWTTokenProvider.getuserIdFromToken(jwtFromRequest);
                User loadUserById = customUserDetailServise.loadUserById(userIdFromToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loadUserById, null, Collections.EMPTY_LIST);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsreq));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
        logger.error("could not set user authentication");
        }
        fc.doFilter(hsreq, hsresp);
        
    }
    
    private String getJWTFromRequest (HttpServletRequest request){
        String bearToken = request.getHeader(SecurityConstants.TOKEN_PREFIX);
        if (StringUtils.hasText(bearToken)&&bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearToken.split(" ")[1];
        }
        return null;
    }

}
