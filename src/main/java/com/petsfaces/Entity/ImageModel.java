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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

/**
 *
 * @author JavaDev
 */
@Table(name = "image_model")
@Entity
@Data
public class ImageModel {

    @Id
    @GeneratedValue(generator = "seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq", allocationSize = 1, initialValue = 1, sequenceName = "id_for_imagege")
    private  long id;
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private long postId; 
    @Lob
    @Column(columnDefinition = "Bytea")
    private byte[] imageBytes;
}
