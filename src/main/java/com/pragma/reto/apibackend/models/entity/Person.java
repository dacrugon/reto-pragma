package com.pragma.reto.apibackend.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="people")
@Getter
@Setter
public class Person implements Serializable {
    @Id
    @Column(name="idperson")
    @JsonIgnore
    private long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "identification_type")
    private String identificationType;
    @Column(name = "identification_number",nullable = false,unique = true)
    private String identificationNumber;
    @Column(name = "date_birth")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateBirth;
    @Column(name = "city_birth")
    private String cityBirth;
    @Column(name = "create_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate = LocalDateTime.now();
}
