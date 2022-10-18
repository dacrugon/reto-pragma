package com.pragma.reto.apibackend.models.dao;

import com.pragma.reto.apibackend.models.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface IImageDao extends CrudRepository<Image,Long> {

}
