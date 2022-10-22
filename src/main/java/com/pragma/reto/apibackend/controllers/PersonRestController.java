package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.models.document.Picture;
import com.pragma.reto.apibackend.models.entity.Person;
import com.pragma.reto.apibackend.models.services.IPersonService;
import com.pragma.reto.apibackend.models.services.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PersonRestController {

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPictureService pictureService;

    @Value("${project.image}")
    private String path;

    private Map<String, Object> responseApi(Integer statusCode, String message,Object body){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("message",message);
        response.put("body", body);
        return response;
    }
    //getAll()
    @GetMapping("/people")
    public ResponseEntity<?> getPeople(){
        List<Person> people;
        Map<String, Object> response;
        try{
            people= personService.findAll();
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(people.size() == 0){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"No hay personas registradas",people);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",people);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/pictures")
    public ResponseEntity<?> getPictures(){
        List<Picture> pictures;
        Map<String,Object> response;
        try{
            pictures = pictureService.findAll();
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(pictures.size() == 0){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"No hay imagenes registradas",pictures);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",pictures);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //getById()
    @GetMapping("/people/{id}")
    public ResponseEntity<?> showPerson(@PathVariable Long id){

        Person person;
        Map<String,Object> response;
        try{
            person = personService.findById(id);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(person == null){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"La persona con el ID: ".concat(id.toString().concat(" no existe en la base de datos")) , null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",person);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/pictures/{id}")
    public ResponseEntity<?> showImage(@PathVariable String id){
        Picture picture;
        Map<String,Object> response;
        try{
            picture = pictureService.findById(id);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(picture == null){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"La image con el ID: ".concat(id.toString().concat(" no existe en la base de datos")) , null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",picture);
        return new ResponseEntity<>(response,HttpStatus.OK);
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
    public ResponseEntity<?> showPersonOrderByAge(@PathVariable Integer age){

        List<Person> people;
        Map<String, Object> response;
        try{
            people= personService.findPeopleAgeGreaterThanOrEqualsTo(age);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(people.size() == 0){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"No hay personas con edades mayor o igual a "+age,people);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",people);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/people/{identificationType}/{identificationNumber}")
    public ResponseEntity<?> showPersonByIdTypeAndIdNumber(@PathVariable String identificationType, @PathVariable String identificationNumber){

        List<Person> people;
        Map<String, Object> response;
        try{
            people= personService.findByIdentificationTypeAndIdentificationNumber(identificationType,identificationNumber);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error al realizar la consulta a la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(people.size() == 0){
            response = responseApi(HttpStatus.NOT_FOUND.value(),"No existe una persona con ese número de documento asociado a ese tipo de documento",people);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response = responseApi(HttpStatus.OK.value(),"successful",people);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //add()
    @PostMapping("/people")
    public ResponseEntity<?> createPerson(Person person){
        Person personNew;
        Map<String,Object> response;
        try{
            personNew = personService.save(person);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al realizar el insert en la base de datos",
                    e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response = responseApi(HttpStatus.CREATED.value(), "successful",personNew);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/pictures")
    public ResponseEntity<?> createImage(Picture picture, @RequestParam("imageFile") MultipartFile imageFile){
        Map<String,Object> response;

        //save image and get file name
        String fileName = pictureService.uploadImage(path,imageFile);
        picture.setPictureName(fileName);

        Picture pictureNew;
        try{
            pictureNew = pictureService.save(picture);
        }catch (DataAccessException e){
            response = responseApi(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al realizar el insert en la base de datos",
                    e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response = responseApi(HttpStatus.CREATED.value(), "successful",pictureNew);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //update()
    @PutMapping("/people/{id}")
    public ResponseEntity<?> updatePerson(Person person, @PathVariable Long id){
        Person currentPerson = personService.findById(id);
        Person personUpdated;
        Map<String,Object> response;

        if(currentPerson == null){
            response  = responseApi(HttpStatus.NOT_FOUND.value(), "La persona con el ID: ".concat(id.toString().concat(" no existe en la base de datos")),null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentPerson.setLastName(person.getLastName());
            currentPerson.setName(person.getName());
            currentPerson.setIdentificationType(person.getIdentificationType());
            currentPerson.setIdentificationNumber(person.getIdentificationNumber());
            currentPerson.setDateBirth(person.getDateBirth());
            currentPerson.setCityBirth(person.getCityBirth());
            personUpdated = personService.save(currentPerson);
        }catch (DataAccessException e){
            response = responseApi(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la persona en la base de datos",
                    e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response = responseApi(HttpStatus.CREATED.value(), "successful",personUpdated);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @PutMapping("/pictures/{id}")
    public ResponseEntity<?> updateImage( @PathVariable String id, @RequestParam("imageFile") MultipartFile imageFile){

        Picture currentImage = pictureService.findById(id);
        Picture imageUpdated;
        Map<String,Object> response;

        if(currentImage == null){
            response  = responseApi(HttpStatus.NOT_FOUND.value(), "La imagen con el ID: ".concat(id.toString().concat(" no existe en la base de datos")),null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        //delete image in disk
        pictureService.deleteImageInDisk(path,currentImage.getPictureName());

        //save image and get file name
        String fileName = pictureService.uploadImage(path,imageFile);

        currentImage.setPictureName(fileName);
        imageUpdated =  pictureService.save(currentImage);

        response =responseApi(HttpStatus.CREATED.value(), "successful",imageUpdated);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //delete()
    @DeleteMapping("/people/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id){
        Map<String,Object> response;
        try{
            personService.delete(id);
        }catch (DataAccessException e){
            response = responseApi(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error al eliminar a la persona en la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response = responseApi(HttpStatus.OK.value(), "successful","La persona fue eliminada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/pictures/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id){
        Picture pictureCurrent = pictureService.findById(id);
        Map<String,Object> response;

        if(pictureCurrent == null){
            response  = responseApi(HttpStatus.NOT_FOUND.value(), "La imagen con el ID: ".concat(id.concat(" no existe en la base de datos")),null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        pictureService.deleteImageInDisk(path,pictureCurrent.getPictureName());
        try{
            pictureService.delete(id);

        }catch (DataAccessException e){
            response = responseApi(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error al eliminar a la imagen en la base de datos",
                    e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response = responseApi(HttpStatus.OK.value(), "successful","La imagen fue eliminada con éxito");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
