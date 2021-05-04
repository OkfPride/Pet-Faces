/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.repositories;

import com.petsfaces.Entity.ImageModel;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JavaDev
 */
@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long>{
    Optional<ImageModel>findById(Long id);
    Optional<ImageModel>findByUserId(Long id);
    Optional<ImageModel>findByPostId(Long id);
    Optional<List<ImageModel>>findAllByPostId(Long id);

}
