package com.pragma.reto.apibackend.controllers;

import com.pragma.reto.apibackend.models.entity.Person;
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

    @GetMapping("/people")
    public List<Person> index(){
        return personService.findAll();
    }

    @GetMapping("/people/{id}")
    public Person show(@PathVariable Long id){
        return personService.findById(id);
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person){
        return personService.save(person);
    }

    @PutMapping("/people/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Person update(@RequestBody Person person, @PathVariable Long id){
        Person currentPerson = personService.findById(id);
        currentPerson.setLastName(person.getLastName());
        currentPerson.setName(person.getName());
        currentPerson.setIdentificationType(person.getIdentificationType());
        currentPerson.setIdentificationNumber(person.getIdentificationNumber());
        currentPerson.setDateBirth(person.getDateBirth());
        currentPerson.setCityBirth(person.getCityBirth());
        return personService.save(currentPerson);
    }

    @DeleteMapping("/people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }

}
