/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.data_transfer_object;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author JavaDev
 */
@Data
public class CommentDTO {

    @NotEmpty
    private String message;
    private String userName;
    private long id;

}
