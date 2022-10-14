package com.pragma.reto.apibackend.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="people")
@Getter
@Setter
public class Person implements Serializable {
    @Id
    @Column(name="idperson")
    private long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "identification_type")
    private String identificationType;
    @Column(name = "identification_number")
    private String identificationNumber;
    @Column(name = "date_birth")
    private Date dateBirth;
    @Column(name = "city_birth")
    private String cityBirth;
    @Column(name = "create_at")
    private Date createAt;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }
}
