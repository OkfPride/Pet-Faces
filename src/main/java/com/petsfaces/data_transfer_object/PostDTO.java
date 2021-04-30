/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.data_transfer_object;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Data
public class PostDTO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String caprion;
    private String username;
    private String location;
    private Set<String> likedUsers;
    private Integer likes;
}
