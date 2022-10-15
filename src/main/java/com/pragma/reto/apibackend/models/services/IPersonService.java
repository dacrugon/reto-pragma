package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Person;

import java.util.List;

public interface IPersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person save(Person person);

    void delete(Long id);

    List<Person> findByIdentificationTypeAndNumber(String identificationType, String identificationNumber);

    List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age);


}
