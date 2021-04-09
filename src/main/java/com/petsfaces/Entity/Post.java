/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Entity;

import java.io.Serializable;
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
@Entity
@Data
@Table(name = "post")
public class Post implements Serializable {

    @Id
    private final long ID;
    private String title;
    private String Caption;
    private String location;
    private int likes;

    Set<String> likedPost = new HashSet<>();

    User postCreator;

    List<ImageModel> images = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();

    private LocalDateTime creDateTime;

    @PrePersist
    private void onCreate() {
        this.creDateTime = LocalDateTime.now();
    }
}
