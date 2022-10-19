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
    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> findByIdentificationTypeAndNumber(String identificationType, String identificationNumber) {
        return personRepository.findByIdentificationTypeAndNumber(identificationType,identificationNumber);
    }

    @Override
    public List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age) {
        return personRepository.findPeopleAgeGreaterThanOrEqualsTo(age);
    }


}
