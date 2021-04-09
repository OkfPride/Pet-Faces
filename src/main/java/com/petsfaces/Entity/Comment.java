/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Data
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    private final long ID;

    private String userName;
    private String caption;
    private String location;
    private int likes;
    private LocalDateTime createDateTime;
    
    private Post post;
    

    @PrePersist
    private void onCreate() {
        this.createDateTime = LocalDateTime.now();
    }
}
