/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.facade;

import com.petsfaces.data_transfer_object.PostDTO;
import com.petsfaces.Entity.Post;
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
        postDTO.setUsername(post.getUser().getUsername());
        return postDTO;
    }
}
