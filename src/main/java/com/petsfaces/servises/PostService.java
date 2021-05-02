/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.facade.PostFacade;
import com.petsfaces.Entity.Post;
import com.petsfaces.Entity.User;
import com.petsfaces.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.petsfaces.data_transfer_object.PostDTO;
import com.petsfaces.exceptions.PostNotFoundException;
import com.petsfaces.repositories.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author JavaDev
 */
@Service
public class PostService implements IPostService {

    Logger logger = LoggerFactory.getLogger(PostService.class);

    PostFacade postFacade;
    PostRepository postRepository;
    UserRepository userRepository;

    @Autowired
    public PostService(PostFacade postFacade, PostRepository postRepository, UserRepository userRepository) {
        this.postFacade = postFacade;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Set<PostDTO> getAllPosts(Principal principal) {
        String userName = principal.getName();
        return null;
    }

    @Override
    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByName(principal);
        Post post = new Post();
        post.setCaption(postDTO.getCaprion());
        post.setTitle(postDTO.getTitle());
        post.setLocation(postDTO.getLocation());
        post.setLikes(0);
        post.setUser(user);
//        List<Post> userPosts = user.getUserPosts();
//        userPosts.add(post);
//        user.setUserPosts(userPosts);
//        userRepository.
        logger.info("saving post for user " + user.getEmail());
        return postRepository.save(post);

    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> findAll = postRepository.findAllByOrderByCreatedDateDesc();
        return findAll;

    }

    @Override
    public List<Post> getAllPostsForUser(Principal principal) {
        User user = getUserByName(principal);
        List<Post> findAllByUserOrderByCreatedDateDesc = postRepository.findAllByUserOrderByCreatedDateDesc(user);
        return findAllByUserOrderByCreatedDateDesc;
    }

    private User getUserByName(Principal principal) {
        String username = principal.getName();
        System.out.println(username);
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = " + username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

    @Override
    public Post likePost(Long postId, Principal principal) {
        User user = getUserByName(principal);
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            return new PostNotFoundException("post with id = " + postId + " not found"); //To change body of generated lambdas, choose Tools | Templates.
        });
        Set<String> likedUsers = post.getLikedUsers();
        if (likedUsers.contains(user.getUsername())) {
            post.setLikes(post.getLikes() - 1);
            boolean remove = likedUsers.remove(user.getUsername());
            if (remove == false) {
                throw new IllegalStateException();
            }
        } else {
            post.setLikes(post.getLikes() + 1);
            likedUsers.add(user.getUsername());
        }
        post.setLikedUsers(likedUsers);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, Principal principal) {
        User user = getUserByName(principal);
        List<Post> posts = user.getUserPosts();
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            return new PostNotFoundException("no post with id = "+ postId); //To change body of generated lambdas, choose Tools | Templates.
        });
//        posts.remove(post);
//        userRepository.save(user);
        postRepository.delete(post);
    }
    

}
