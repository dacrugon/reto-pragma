package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.exceptions.RequestException;
import com.pragma.reto.apibackend.models.document.Picture;
import com.pragma.reto.apibackend.models.services.IPictureService;
import com.pragma.reto.apibackend.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PictureRestController {
    @Autowired
    private IPictureService pictureService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/pictures")
    public ResponseEntity<Object> getPictures(){
        List<Picture> pictures = pictureService.findAll();

        if(pictures.isEmpty()){

            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Empty list");
        }
        return ResponseHandler.generateResponse("P-201","successful",pictures,HttpStatus.OK);
    }

    @GetMapping("/pictures/{in}")
    public ResponseEntity<Object> showPictureByIdentificationNumber(@PathVariable String in){
        Picture picture = pictureService.findByIdentificationNumber(in);

        if(picture == null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Not found");
        }
        return ResponseHandler.generateResponse("P-201","successful",picture,HttpStatus.OK);
    }

    @GetMapping(value = "/pictures/download/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadPictureByFileName(@PathVariable("imageName") String imageName, HttpServletResponse response){
        InputStream resource = pictureService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/pictures")
    public ResponseEntity<?> createPicture(Picture picture, @RequestParam("imageFile") MultipartFile imageFile){
        String fileName = pictureService.uploadImage(path,imageFile);
        picture.setPictureName(fileName);
        Picture pictureNew = pictureService.save(picture);
        return ResponseHandler.generateResponse("P-201","successful",pictureNew,HttpStatus.CREATED);
    }

    @PutMapping("/pictures/{in}")
    public ResponseEntity<?> updatePictureByIdentificationNumber( @PathVariable String in, @RequestParam("imageFile") MultipartFile imageFile){

        Picture currentImage = pictureService.findByIdentificationNumber(in);
        Picture imageUpdated;
        if(currentImage == null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Not found");
        }
        pictureService.deleteImageInDisk(path,currentImage.getPictureName());
        String fileName = pictureService.uploadImage(path,imageFile);
        currentImage.setPictureName(fileName);

        imageUpdated =  pictureService.save(currentImage);

        return ResponseHandler.generateResponse("P-201","successful",imageUpdated,HttpStatus.OK);
    }

    @DeleteMapping("/pictures/{in}")
    public ResponseEntity<?> deletePictureByIdentificationNumber(@PathVariable String in){
        Picture pictureCurrent = pictureService.findByIdentificationNumber(in);

        if(pictureCurrent == null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Not found");
        }
        pictureService.deleteImageInDisk(path,pictureCurrent.getPictureName());
        pictureService.deleteByIdentificationNumber(in);

        return ResponseHandler.generateResponse("P-201","successful",null, HttpStatus.OK);
    }
}
