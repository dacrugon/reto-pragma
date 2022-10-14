package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Person;

import java.util.List;

public interface IPersonService {

    public List<Person> findAll();

    public Person findById(Long id);

    public Person save(Person person);

    public void delete(Long id);


}
