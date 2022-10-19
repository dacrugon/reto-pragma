package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.models.document.Picture;
import com.pragma.reto.apibackend.models.entity.Person;
import com.pragma.reto.apibackend.models.services.IPersonService;
import com.pragma.reto.apibackend.models.services.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonRestController {

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPictureService pictureService;

    @Value("${project.image}")
    private String path;

    //getAll()
    @GetMapping("/people")
    public List<Person> getPeople(){
        return personService.findAll();
    }

    @GetMapping("/pictures")
    public List<Picture> getPictures(){
        return pictureService.findAll();
    }

    //getById()
    @GetMapping("/people/{id}")
    public Person showPerson(@PathVariable Long id){
        return personService.findById(id);
    }

    @GetMapping("/pictures/{id}")
    public Picture showImage(@PathVariable String id){
        return pictureService.findById(id);
    }

        //getImageByNameFile()
    @GetMapping(value = "/pictures/download/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response){
        InputStream resource = pictureService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //additionalSearchPeople()
    @GetMapping("/people/greaterThanOrEqualTo/{age}")
    public List<Person> showPersonOrderByAge(@PathVariable Integer age){
        return personService.findPeopleAgeGreaterThanOrEqualsTo(age);
    }
    @GetMapping("/people/{identificationType}/{identificationNumber}")
    public List<Person> showPersonByIdTypeAndIdNumber(@PathVariable String identificationType, @PathVariable String identificationNumber){
        return personService.findByIdentificationTypeAndNumber(identificationType,identificationNumber);
    }

    //add()
    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(Person person){
        return personService.save(person);
    }

    @PostMapping("/pictures")
    public Picture createImage(Picture image, @RequestParam("imageFile") MultipartFile imageFile){

        //save image and get file name
        String fileName = pictureService.uploadImage(path,imageFile);

        image.setPictureUrl(fileName);

        return pictureService.save(image);
    }

    //update()
    @PutMapping("/people/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Person updatePerson(Person person, @PathVariable Long id){
        Person currentPerson = personService.findById(id);
        currentPerson.setLastName(person.getLastName());
        currentPerson.setName(person.getName());
        currentPerson.setIdentificationType(person.getIdentificationType());
        currentPerson.setIdentificationNumber(person.getIdentificationNumber());
        currentPerson.setDateBirth(person.getDateBirth());
        currentPerson.setCityBirth(person.getCityBirth());
        return personService.save(currentPerson);
    }

    @PutMapping("/pictures/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Picture updateImage( @PathVariable String id, @RequestParam("imageFile") MultipartFile imageFile){

        Picture currentImage = pictureService.findById(id);

        pictureService.deleteImageInDisk(path,currentImage.getPictureUrl());

        //save image and get file name
        String fileName = pictureService.uploadImage(path,imageFile);

        currentImage.setPictureUrl(fileName);
        return pictureService.save(currentImage);
    }

    //delete()
    @DeleteMapping("/people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }

    @DeleteMapping("/pictures/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable String id){
        Picture image = pictureService.findById(id);

        pictureService.deleteImageInDisk(path,image.getPictureUrl());
        pictureService.delete(id);
    }

}
