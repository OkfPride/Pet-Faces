/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.User;
import com.petsfaces.UserDataTransferObject.UserDTO;
import com.petsfaces.payload.request.SignUpRequest;
import java.security.Principal;

/**
 *
 * @author JavaDev
 */
public interface IUserServise {
    public User createUser(SignUpRequest userIn);
    public User updateUser(UserDTO user, Principal principal);
    public User getUserbyName(Principal principal);
}
