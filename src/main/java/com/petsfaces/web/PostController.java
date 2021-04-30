/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petsfaces.data_transfer_object.PostDTO;
import com.petsfaces.facade.PostFacade;
import com.petsfaces.servises.IPostService;
import com.petsfaces.Entity.Post;
import com.petsfaces.validators.ResponseErrorValidation;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.petsfaces.payload.response.MessageResponse;

/**
 *
 * @author JavaDev
 */
@RestController
@CrossOrigin
@RequestMapping(path = "api/post")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
    IPostService postService;
    ResponseErrorValidation responseErrorValidation;
    PostFacade postFacade;

    @Autowired
    public PostController(IPostService postService, ResponseErrorValidation responseErrorValidation, PostFacade postFacade) {
        this.postService = postService;
        this.responseErrorValidation = responseErrorValidation;
        this.postFacade = postFacade;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO, Principal principal, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Post post = postService.createPost(postDTO, principal);
        PostDTO createdPost = postFacade.postToPostDTO(post);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);

    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> ListOfPosts = postService.getAllPosts();
        List<PostDTO> allPostsDTO = ListOfPosts.stream().map(postFacade::postToPostDTO).collect(Collectors.toList());
        return new ResponseEntity<>(allPostsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<Post> userPosts = postService.getAllPostsForUser(principal);
        List<PostDTO> userPostsDTO = userPosts.stream().map(postFacade::postToPostDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userPostsDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/{postId}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable(value = "postId") Long postId, Principal principal) {
        Post post = postService.likePost(postId, principal);
        PostDTO postDTO = postFacade.postToPostDTO(post);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable(value = "postId") Long postId, Principal principal) {
         postService.deletePost(postId, principal);
//        List<PostDTO> listPostDTO = postList.stream().map(postFacade::postToPostDTO).collect(Collectors.toList());
        return new ResponseEntity<>(new MessageResponse("post was deleted"), HttpStatus.OK);
    }
    //deletePost
//
//    @GetMapping(path = "/")
//    public ResponseEntity<PostDTO> getPost() {
//
//        return null;
//    }
}
