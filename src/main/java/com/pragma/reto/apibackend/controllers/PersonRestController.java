package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.exceptions.RequestException;
import com.pragma.reto.apibackend.models.entity.Person;
import com.pragma.reto.apibackend.models.services.IPersonService;
import com.pragma.reto.apibackend.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonRestController {

    @Autowired
    private IPersonService personService;
    @Operation(summary = "Get all people")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found people"),
            @ApiResponse(responseCode = "404", description = "people not found",content = @Content)})
    @GetMapping("/people")
    public ResponseEntity<Object> getPeople(){
        List<Person> people = personService.findAll();;

        if(people.isEmpty()){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Empty list");
        }
        return ResponseHandler.generateResponse("P-201","successful",people,HttpStatus.OK);
    }
    @GetMapping("/people/{in}")
    public ResponseEntity<Object> getPersonByIdentificationNumber(@PathVariable String in){
        Person person = personService.findByIdentificationNumber(in);
        if(person==null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"No person found");
        }
        return ResponseHandler.generateResponse("P-201","successful",person,HttpStatus.OK);
    }
    @GetMapping("/people/greaterThanOrEqualTo/{age}")
    public ResponseEntity<Object> showPersonOrderByAge(@PathVariable Integer age){
        List<Person> people = personService.findPeopleAgeGreaterThanOrEqualsTo(age);
        if(people.isEmpty()){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Empty list");
        }
        return ResponseHandler.generateResponse("P-201","successful",people,HttpStatus.OK);
    }
    @GetMapping("/people/{identificationType}/{identificationNumber}")
    public ResponseEntity<Object> showPersonByIdTypeAndIdNumber(@PathVariable String identificationType, @PathVariable String identificationNumber){
        List<Person> people= personService.findByIdentificationTypeAndIdentificationNumber(identificationType,identificationNumber);

        if(people.isEmpty()){
            throw new RequestException("P-404", HttpStatus.NOT_FOUND, "Empty list");
        }
        return ResponseHandler.generateResponse("P-201","successful",people,HttpStatus.OK);

    }

    @PostMapping("/people")
    public ResponseEntity<?> createPerson(Person person){
        Person personNew= personService.save(person);

        return ResponseHandler.generateResponse("P-201","successful",personNew,HttpStatus.OK);
    }

    @PutMapping("/people/{in}")
    public ResponseEntity<?> updatePersonByIdentificationNumber(Person person, @PathVariable String in){
        Person currentPerson = personService.findByIdentificationNumber(in);
        Person personUpdated;

        if(currentPerson == null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Not found");
        }
        currentPerson.setLastName(person.getLastName());
        currentPerson.setName(person.getName());
        currentPerson.setIdentificationType(person.getIdentificationType());
        currentPerson.setIdentificationNumber(person.getIdentificationNumber());
        currentPerson.setDateBirth(person.getDateBirth());
        currentPerson.setCityBirth(person.getCityBirth());
        personUpdated = personService.save(currentPerson);

        return ResponseHandler.generateResponse("P-201","updated",personUpdated,HttpStatus.CREATED);

    }

    @DeleteMapping("/people/{in}")
    public ResponseEntity<?> deletePersonByIdentificationNumber(@PathVariable String in){
        Person currentPerson = personService.findByIdentificationNumber(in);
        if(currentPerson == null){
            throw new RequestException("P-404",HttpStatus.NOT_FOUND,"Not found");
        }
        personService.delete(in);

        return ResponseHandler.generateResponse("P-201","deleted",null,HttpStatus.OK);
    }



}
