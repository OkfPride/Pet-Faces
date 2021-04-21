/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.User;
import com.petsfaces.Entity.enums.UserRole;
import com.petsfaces.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author JavaDev
 */
@Service
public class CustomUserDetailServise implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not founded" + username));
        return build(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    public static User build(User user) {
        List<GrantedAuthority> authorities;
        authorities = user.getUserRole().stream().map((UserRole role) -> {
            return new SimpleGrantedAuthority(role.name());
        }).collect(Collectors.toList());
        return new User(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), authorities);
    }

}
