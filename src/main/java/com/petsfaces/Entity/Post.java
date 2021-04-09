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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Entity
@Data
@Table(name = "post_table")
public class Post implements Serializable {

    @Id
    @GeneratedValue(generator = "seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq", allocationSize = 1, sequenceName = "id_for_post")
    private  long ID;

    private String title;
    private String Caption;
    private String location;
    private int likes;

    @Column
    @ElementCollection(targetClass = String.class)
    Set<String> likedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    User postCreator;

    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy = "post",orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime creDateTime;

    @PrePersist
    private void onCreate() {
        this.creDateTime = LocalDateTime.now();
    }
}
