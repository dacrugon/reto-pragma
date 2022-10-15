package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.models.entity.Image;
import com.pragma.reto.apibackend.models.entity.Person;
import com.pragma.reto.apibackend.models.services.IImageService;
import com.pragma.reto.apibackend.models.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonRestController {

    @Autowired
    private IPersonService personService;

    @Autowired
    private IImageService imageService;

    @GetMapping("/people")
    public List<Person> getPeople(){
        return personService.findAll();
    }

    @GetMapping("/images")
    public List<Image> getImages(){
        return imageService.findAll();
    }
    @GetMapping("/people/{id}")
    public Person showPerson(@PathVariable Long id){
        return personService.findById(id);
    }

    @GetMapping("/people/greaterThanOrEqualTo/{age}")
    public List<Person> showPersonOrderByAge(@PathVariable Integer age){
        return personService.findPeopleAgeGreaterThanOrEqualsTo(age);
    }

    @GetMapping("/people/{identificationType}/{identificationNumber}")
    public List<Person> showPersonByIdTypeAndIdNumber(@PathVariable String identificationType, @PathVariable String identificationNumber){
        return personService.findByIdentificationTypeAndNumber(identificationType,identificationNumber);
    }

    @GetMapping("/images/{id}")
    public Image showImage(@PathVariable Long id){
        return imageService.findById(id);
    }
    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person){
        return personService.save(person);
    }

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    public Image createImage(@RequestBody Image image){
        return imageService.save(image);
    }

    @PutMapping("/people/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Person updatePerson(@RequestBody Person person, @PathVariable Long id){
        Person currentPerson = personService.findById(id);
        currentPerson.setLastName(person.getLastName());
        currentPerson.setName(person.getName());
        currentPerson.setIdentificationType(person.getIdentificationType());
        currentPerson.setIdentificationNumber(person.getIdentificationNumber());
        currentPerson.setDateBirth(person.getDateBirth());
        currentPerson.setCityBirth(person.getCityBirth());
        return personService.save(currentPerson);
    }

    @PutMapping("/images/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Image updateImage(@RequestBody Image image, @PathVariable Long id){
        Image currentImage = imageService.findById(id);
        currentImage.setImagePath(image.getImagePath());
        currentImage.setImageUrl(image.getImageUrl());
        return imageService.save(currentImage);
    }

    @DeleteMapping("/people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }

    @DeleteMapping("/images/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id){
        imageService.delete(id);
    }


}
