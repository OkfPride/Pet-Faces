/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.Comment;
import com.petsfaces.Entity.User;
import com.petsfaces.Entity.Post;
import com.petsfaces.data_transfer_object.CommentDTO;
import com.petsfaces.exceptions.CommentNotFoundException;
import com.petsfaces.exceptions.PostNotFoundException;
import com.petsfaces.repositories.CommentRepository;
import com.petsfaces.repositories.PostRepository;
import com.petsfaces.repositories.UserRepository;
import java.security.Principal;
import java.util.List;
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
    PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(CommentDTO commentDTO, Principal principal, Long postId) {
        Comment comment = new Comment();
        User user = getUserByName(principal);
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            return new PostNotFoundException(("post not fount")); //To change body of generated lambdas, choose Tools | Templates.
        });
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUserName(user.getUsername());
        comment.setMessage(commentDTO.getMessage());
        Comment save = commentRepository.save(comment);

        List<Comment> postComments = post.getComments();
        postComments.add(comment);
        post.setComments(postComments);
        postRepository.save(post);
        return save;
    }

    @Override
    public void deleteComment(Principal principal, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentsToPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<Comment> findAllByPost = commentRepository.findAllByPost(post);
        return findAllByPost;
    }

    //getallCommentsToPost
    private User getUserByName(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = " + username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

}
