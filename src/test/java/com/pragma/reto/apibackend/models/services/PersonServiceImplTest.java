package com.pragma.reto.apibackend.models.services;

import com.pragma.reto.apibackend.models.entity.Person;
import com.pragma.reto.apibackend.models.repository.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class PersonServiceImplTest {

    @Mock
    private IPersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        person = new Person();
        person.setName("José");
        person.setLastName("González");
        person.setIdentificationType("DNI");
        person.setIdentificationNumber("12345678");
        person.setDateBirth(new Date());

    }

    @Test
    void findAll() {
        when(personRepository.findAll()).thenReturn(Arrays.asList(person));
        assertNotNull(personService.findAll());
    }

    @Test
    void findByIdentificationNumber() {
        when(personRepository.findByIdentificationNumber(any())).thenReturn(person);
        assertNotNull(personRepository.findByIdentificationNumber(person.getIdentificationNumber()));
    }

    @Test
    void save() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        assertNotNull(personRepository.save(new Person()));
    }

    @Test
    void delete() {

    }

    @Test
    void findByIdentificationTypeAndIdentificationNumber() {
    }

    @Test
    void findPeopleAgeGreaterThanOrEqualsTo() {
    }
}