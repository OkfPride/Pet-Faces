/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.ImageModel;
import com.petsfaces.repositories.ImageModelRepository;
import com.petsfaces.Entity.User;
import com.petsfaces.exceptions.UserExistExseption;
import com.petsfaces.repositories.PostRepository;
import com.petsfaces.repositories.UserRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Филипповы
 *
 *
 *
 */
@Service
public class ImageService implements I_ImageService {

    ImageModelRepository imageModelRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    public ImageService(ImageModelRepository imageModelRepository, UserRepository userRepository, PostRepository postRepository) {
        this.imageModelRepository = imageModelRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ImageModel uploadImage(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByName(principal);
        ImageModel imageModel = imageModelRepository.findByUserId(user.getId()).orElseThrow(UserExistExseption::new);
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModelRepository.delete(imageModel);
        }
        ImageModel createdImageModel = new ImageModel();
        createdImageModel.setUserId(user.getId());
        createdImageModel.setName(file.getOriginalFilename());
        byte[] coded = compressBytes(file.getBytes());
        createdImageModel.setImageBytes(coded);
        return imageModelRepository.save(createdImageModel);
    }

    @Override
    public ImageModel uploadImageToPost(Long postId, Principal principal, MultipartFile file) {
    }

    @Override
    public ImageModel getImageFromProfile(Principal principal) {
        User user = getUserByName(principal);
        ImageModel imageModel = imageModelRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            byte[] decompressBytes = decompressBytes(imageModel.getImageBytes());
            imageModel.setImageBytes(decompressBytes);
        }
        return imageModel;
    }

    @Override
    public ImageModel getImageFromPost(Long postId) {
    }

    private User getUserByName(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = " + username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

    private byte[] compressBytes(byte[] bytes) {
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bytes.length);
        while (!deflater.finished()) {
            int deflate = deflater.deflate(buffer);
            byteArrayOutputStream.write(buffer, 0, deflate);
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException ex) {
            logger.warn(Arrays.toString(ex.getStackTrace()));
        }
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] decompressBytes(byte[] bytes) {
        Inflater inflater = new Inflater();
        inflater.setInput(bytes);
        byte[] buffer = new byte[1024];

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bytes.length);) {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                byteArrayOutputStream.write(buffer, 0, count);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | DataFormatException ex) {
            logger.warn(Arrays.toString(ex.getStackTrace()));
            return buffer;
        }
    }

}
