package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.dao.IPersonDao;
import com.pragma.reto.apibackend.models.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService{

    @Autowired
    private IPersonDao personDao;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return (List<Person>) personDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return personDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        return personDao.save(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personDao.deleteById(id);
    }

    @Override
    public List<Person> findByIdentificationTypeAndNumber(String identificationType, String identificationNumber) {
        return personDao.findByIdentificationTypeAndNumber(identificationType,identificationNumber);
    }

    @Override
    public List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age) {
        return personDao.findPeopleAgeGreaterThanOrEqualsTo(age);
    }


}
