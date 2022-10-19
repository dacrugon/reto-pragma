package com.pragma.reto.apibackend.models.repository;

import com.pragma.reto.apibackend.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.identificationType = ?1 AND p.identificationNumber = ?2")
    List<Person> findByIdentificationTypeAndNumber(String identificationType, String identificationNumber);

    @Query("SELECT p FROM Person p WHERE timestampdiff(year, p.dateBirth, curdate()) >= ?1")
    List<Person> findPeopleAgeGreaterThanOrEqualsTo(Integer age);

}
