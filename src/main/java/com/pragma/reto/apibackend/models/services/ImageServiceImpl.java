package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.dao.IImageDao;
import com.pragma.reto.apibackend.models.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public String uploadImage(String path, MultipartFile file) {
        //file name
        String name = file.getOriginalFilename();

        //random name generate file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //fullpath
        String filePath = path+ File.separator+fileName;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        try {
            Files.copy(file.getInputStream(),Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) {
        String fullPath = path+File.separator+fileName;
        InputStream is;
        try {
             is = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return is;
    }

    @Override
    public void deleteImageInDisk(String path, String nameImage) {
        String fullPath = path+File.separator+nameImage;
        File file = new File(fullPath);
        file.delete();
    }


}
