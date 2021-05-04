/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petsfaces.repositories;

import com.petsfaces.Entity.ImageModel;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Филипповы
 */
@Repository
public class ImageRepositoryDAO {
Logger logger = LoggerFactory.getLogger(ImageRepositoryDAO.class);
    @Autowired
    EntityManager entityManager;

   
    public <S extends ImageModel> S save(S s) {
        logger.error("i try to save object");
        Query createQuery
                =  entityManager.createQuery("insert into image_model (image_bytes, name, post_id, user_id, id) values (?,?,?,?,?)");
        System.out.println("i try to save object");
        createQuery.setParameter(0, 123);
        createQuery.setParameter(1, "name");
        createQuery.setParameter(2, 1);
        createQuery.setParameter(3, 1);
        createQuery.setParameter(4, 1);
        int executeUpdate = createQuery.executeUpdate();
        return s;
    }

}
