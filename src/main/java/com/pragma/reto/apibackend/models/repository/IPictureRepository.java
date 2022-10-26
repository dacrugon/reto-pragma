package com.pragma.reto.apibackend.models.repository;

import com.pragma.reto.apibackend.models.document.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPictureRepository extends MongoRepository<Picture,String> {
    Picture findByIdentificationNumber(String in);
    void deleteByIdentificationNumber(String in);

}
