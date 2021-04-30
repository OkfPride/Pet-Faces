/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.Comment;
import com.petsfaces.Entity.User;
import com.petsfaces.data_transfer_object.CommentDTO;
import com.petsfaces.repositories.CommentRepository;
import com.petsfaces.repositories.UserRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author JavaDev
 */
@Service
public class CommentService implements ICommentService {

    CommentRepository commentRepository;
    UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(CommentDTO commentDTO, Principal principal) {
        User user = getUserByName(principal);
        Comment comment = new Comment();
        comment.setMessage(commentDTO.getMessage());
        comment.setUserName(user.getUsername());
        comment.setUserId(user.getId());
        Comment save = commentRepository.save(comment);
        return save;
    }

    private User getUserByName(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = " + username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

}
