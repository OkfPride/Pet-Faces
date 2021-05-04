/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.servises;

import com.petsfaces.Entity.ImageModel;
import com.petsfaces.Entity.Post;
import com.petsfaces.repositories.ImageModelRepository;
import com.petsfaces.Entity.User;
import com.petsfaces.exceptions.ImageNotFoundException;
import com.petsfaces.exceptions.PostNotFoundException;
import com.petsfaces.exceptions.UserExistExseption;
import com.petsfaces.repositories.ImageRepositoryDAO;
import com.petsfaces.repositories.PostRepository;
import com.petsfaces.repositories.UserRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    ImageRepositoryDAO imageRepositoryDAO;

    @Autowired
    public ImageService(ImageModelRepository imageModelRepository, UserRepository userRepository, PostRepository postRepository) {
        this.imageModelRepository = imageModelRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ImageModel uploadImage(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByName(principal);
            logger.info("user = "+user.getId());
        ImageModel imageModel = imageModelRepository.findByUserId(user.getId()).orElse(null);
            logger.info("imageModel  = "+imageModel);
        if (!ObjectUtils.isEmpty(imageModel)) {
            System.out.println("check0");
            imageModelRepository.delete(imageModel);
        } 
        logger.info("checkpoint0.5");
        ImageModel createdImageModel = new ImageModel();
        createdImageModel.setUserId(user.getId());
        createdImageModel.setName(file.getOriginalFilename());
        byte[] coded = compressBytes(file.getBytes());
        createdImageModel.setImageBytes(coded);
        logger.info("checkpoint1");
        return imageModelRepository.save(createdImageModel);
        
    }

    @Override
    public ImageModel uploadImageToPost(Long postId, Principal principal, MultipartFile file) throws IOException {
        User user = getUserByName(principal);
        Post post = user.getUserPosts().stream().filter((Post t) -> {
            if (Long.compare(t.getId(), postId) == 0) { return true;} 
            else {return false;   }
        }).collect(getCollector());
        
        ImageModel createdImageModel = new ImageModel();
        
        createdImageModel.setPostId(post.getId());
        createdImageModel.setName(file.getOriginalFilename());
        byte[] compressBytes = compressBytes(file.getBytes());
        createdImageModel.setImageBytes(compressBytes);
        return imageModelRepository.save(createdImageModel);
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
    public List<ImageModel> getImageFromPost(Long postId) {
        List<ImageModel> imageModel = imageModelRepository.findAllByPostId(postId).orElse(null);
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.stream().forEach((t) -> {
            t.setImageBytes(decompressBytes(t.getImageBytes()));
            });
        }
        return imageModel;
    }

    private User getUserByName(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("cant find user with username = " + username); //To change body of generated lambdas, choose Tools | Templates.
        });
        return user;
    }

    private static byte[] compressBytes(byte[] bytes) {
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
            System.out.println("in catch deflater");
//            logger.warn(Arrays.toString(ex.getStackTrace()));
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] bytes) {
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
//            logger.warn(Arrays.toString(ex.getStackTrace()));
            return buffer;
        }
    }
    private <T>Collector<T,?,T> getCollector(){
        return Collectors.collectingAndThen(Collectors.toList(), ( List<T> t) -> {
            if (t.size() != 1) {
                throw new IllegalStateException();
            }
            return t.get(0);
        });
    }

}
