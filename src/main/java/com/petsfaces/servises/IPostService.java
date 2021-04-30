/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.Post;
import com.petsfaces.data_transfer_object.PostDTO;
import java.security.Principal;
import java.util.List;

/**
 *
 * @author JavaDev
 */
public interface IPostService {

    public Post createPost(PostDTO postDTO, Principal principal);

    public List<Post> getAllPosts();

    public List<Post> getAllPostsForUser(Principal principal);

    public Post likePost(Long postId, Principal principal);

    public void deletePost(Long postId, Principal principal);

    
    
}
