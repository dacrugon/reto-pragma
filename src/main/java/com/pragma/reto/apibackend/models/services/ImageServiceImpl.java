package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.dao.IImageDao;
import com.pragma.reto.apibackend.models.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl implements IImageService{

    @Autowired
    private IImageDao imageDao;
    @Override
    @Transactional(readOnly = true)
    public List<Image> findAll() {
        return (List<Image>) imageDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Image findById(Long id) {
        return imageDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Image save(Image image) {
        return imageDao.save(image);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        imageDao.deleteById(id);

    }

    @Override
    public String saveImage(MultipartFile imageFile) {
        String fileName = "";



        return fileName;
    }
}
