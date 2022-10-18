package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IImageService {

    List<Image> findAll();

    Image findById(Long id);

    Image save(Image image);

    void delete(Long id);

    String uploadImage(String path, MultipartFile file);

    InputStream getResource(String path, String fileName);

    void deleteImageInDisk(String path, String nameImage);
}
