/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.facade;

import lombok.Data;
import com.petsfaces.Entity.Comment;
import com.petsfaces.data_transfer_object.CommentDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 */
@Data
@Component
public class CommentFacade {

    public CommentDTO commenttoCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserName(comment.getUserName());
        commentDTO.setMessage(comment.getMessage());
        return commentDTO;
    }
}
