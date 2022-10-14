package com.pragma.reto.apibackend.models.dao;

import com.pragma.reto.apibackend.models.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonDao extends CrudRepository<Person, Long> {
}
