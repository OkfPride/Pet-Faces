/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.ImageModel;
import java.io.IOException;
import java.security.Principal;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Филипповы
 */
public interface I_ImageService {

    public ImageModel uploadImage (MultipartFile file, Principal principal) throws IOException ;

    public ImageModel uploadImageToPost(Long postId, Principal principal, MultipartFile file);

    public ImageModel getImageFromProfile(Principal principal);

    public ImageModel getImageFromPost(Long postId);
    
}
