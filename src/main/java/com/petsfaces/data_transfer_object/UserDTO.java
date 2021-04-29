/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.UserDataTransferObject;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * for transfer not full User from DB -> only needed fields 
 * @author JavaDev
 */
@Data
public class UserDTO {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String LastName;
    @NotEmpty
    private String bio;
    private Long id;
    
}
