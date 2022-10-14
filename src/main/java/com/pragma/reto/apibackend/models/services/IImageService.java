package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Image;

import java.util.List;

public interface IImageService {

    List<Image> findAll();

    Image findById(Long id);

    Image save(Image image);

    void delete(Long id);
}
