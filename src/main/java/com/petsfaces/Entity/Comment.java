/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author JavaDev
 */
@Data
@Table(name = "comment_table")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "id_for_comment", initialValue = 1, allocationSize = 1)
    private long id;
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(updatable = false)
    private LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @PrePersist
    private void onCreate() {
        this.createDateTime = LocalDateTime.now();
    }
}
