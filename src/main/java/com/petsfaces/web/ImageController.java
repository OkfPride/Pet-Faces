/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.web;

import com.petsfaces.repositories.ImageModelRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.petsfaces.payload.response.MessageResponse;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.http.HttpStatus;
import com.petsfaces.servises.I_ImageService;
import com.petsfaces.Entity.ImageModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Филипповы *
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/image")
public class ImageController {

    I_ImageService imageService;

    @Autowired
    public ImageController(I_ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam(name = "fileName") MultipartFile file, Principal principal) {
        try {
            imageService.uploadImage(file, principal);
        } catch (IOException ex) {
            Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(new MessageResponse("all is fine save image to user"), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable Long postId, Principal principal, @RequestParam(value = "file") MultipartFile file) {
        try {
            ImageModel imageModel = imageService.uploadImageToPost(postId, principal, file);
            System.out.println("5");
        } catch (IOException ex) {
            System.out.println("in catch");
        }
        return new ResponseEntity<>(new MessageResponse("all is fine save image to post"), HttpStatus.OK);
    }

    @GetMapping(value = "/profileImage")
    public ResponseEntity<ImageModel> getImageToUser(Principal principal) {
        ImageModel imageModel = imageService.getImageFromProfile(principal);
        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}/upload")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable Long postId) {
        ImageModel imageModel = imageService.getImageFromPost(postId);
        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

}
