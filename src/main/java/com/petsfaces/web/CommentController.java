/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import com.petsfaces.Entity.Comment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petsfaces.data_transfer_object.CommentDTO;
import com.petsfaces.facade.CommentFacade;
import com.petsfaces.servises.ICommentService;
import com.petsfaces.validators.ResponseErrorValidation;
import java.security.Principal;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author JavaDev
 */
@CrossOrigin
@RequestMapping(path = "/api/comment")
@RestController
public class CommentController {

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    ICommentService commentService;
    ResponseErrorValidation responseErrorValidation;
    CommentFacade commentFacade;

    @Autowired
    public CommentController(ICommentService commentService, ResponseErrorValidation responseErrorValidation, CommentFacade commentFacade) {
        this.commentService = commentService;
        this.responseErrorValidation = responseErrorValidation;
        this.commentFacade = commentFacade;
    }

    @PostMapping(path = "/{postid}/create")
    public ResponseEntity<Object> createComment(@PathVariable(value = "postid") Long postId, @RequestBody @Valid CommentDTO commentDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Comment createComment = commentService.createComment(commentDTO, principal);
        CommentDTO commenttoCommentDTO = commentFacade.commenttoCommentDTO(createComment);
        return new ResponseEntity<>(commenttoCommentDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/{postid}/delete")
    public void deleteComment() {
    }

    @GetMapping(path = "/{postid}/all")
    public void getAllCommentsToPost() {
    }
}
