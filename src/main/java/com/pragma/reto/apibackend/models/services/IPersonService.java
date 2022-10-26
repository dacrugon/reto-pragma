package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Person;

import java.util.List;

public interface IPersonService {

    List<Person> findAll();
    Person findByIdentificationNumber(String in);

    Person save(Person person);

    void delete(String in);

    List<Person> findByIdentificationTypeAndIdentificationNumber(String identificationType, String identificationNumber);

    List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age);


}
