/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petsfaces.Entity.enums.UserRole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author JavaDev
 */
@Entity
@Table(name = "user_table")
@Data
public class User {
    @Column(name = "id")
    @GeneratedValue(generator = "seq",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq",sequenceName = "id_for_user",allocationSize = 1)
    @Id
    private  long ID; 
    @Column(name = "username",nullable = false,unique = true, updatable = false)
    private String username;
    @Column(name = "password",length = 3000)
    private String password;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "lastname",nullable = false)
    private String lastname;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "biograthy",columnDefinition = "text")
    private String biograthy;
    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
    
    
    
    @OneToMany(mappedBy = "postCreator",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true )
    private List<Post>userPosts= new ArrayList<>();
    
  
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRole>userRole= new HashSet<>();
    
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    
    @PrePersist
    private void onCreate(){
        createdDateTime= LocalDateTime.now();
    }
}
