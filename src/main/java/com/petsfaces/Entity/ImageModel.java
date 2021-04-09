/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Table(name = "image_model")
@Entity
@Data
public class ImageModel {

    @Id
    private final long ID;

    private String name;
    private String cation;
    private String location;
    private int likes;
    private long userId;
    private long postId;
    private byte [] imageBytes;
    
    List<Post>posts = new ArrayList<>();
    
    List<Comment>comments = new ArrayList<>();
    
    Set<User>users = new HashSet<>();
    
    private LocalDateTime createdDateTime;

    @PrePersist
    private void onCreate() {
        this.createdDateTime = LocalDateTime.now();
    }
}
