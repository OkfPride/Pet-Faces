/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.facade;

import com.petsfaces.Entity.Comment;
import com.petsfaces.data_transfer_object.PostDTO;
import com.petsfaces.Entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author JavaDev
 */
@Component
@Data
public class PostFacade {
    
    public PostDTO postToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        
        postDTO.setLocation(post.getLocation());
        postDTO.setCaprion(post.getCaption());
        postDTO.setTitle(post.getTitle());
        postDTO.setLikes(post.getLikes());
        postDTO.setLikedUsers(post.getLikedUsers());
        postDTO.setId(post.getId());
        List<String> collect = post.getComments().stream().map((Comment t) -> {
            return t.getMessage();
        }).collect(Collectors.toList());
        postDTO.setComments(collect);
        
        postDTO.setUsername(post.getUser().getUsername());
        return postDTO;
    }
}
