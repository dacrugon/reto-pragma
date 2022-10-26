package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.repository.IPersonRepository;
import com.pragma.reto.apibackend.models.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService{

    @Autowired
    IPersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person findByIdentificationNumber(String in) {
        return personRepository.findByIdentificationNumber(in);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(String id) {
        personRepository.deleteByIdentificationNumber(id);
    }

    @Override
    public List<Person> findByIdentificationTypeAndIdentificationNumber(String identificationType, String identificationNumber) {
        return personRepository.findByIdentificationTypeAndIdentificationNumber(identificationType,identificationNumber);
    }

    @Override
    public List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age) {
        return personRepository.findPeopleAgeGreaterThanOrEqualsTo(age);
    }


}
