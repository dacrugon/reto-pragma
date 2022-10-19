package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.document.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IPictureService {

    List<Picture> findAll();
    Picture findById(String id);

    InputStream getResource(String path, String fileName);

    Picture save(Picture picture);
    String uploadImage(String path, MultipartFile file);


    void deleteImageInDisk(String path, String nameImage);
    void delete(String id);

}
