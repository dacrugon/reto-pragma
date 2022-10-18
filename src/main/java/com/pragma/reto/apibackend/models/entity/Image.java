package com.pragma.reto.apibackend.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image implements Serializable {
    @Id
    @Column(name = "idimages")
    private long id;
    @Column(name = "id_person")
    private long idPerson;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "create_at")
    private Date createAt;

    @PrePersist
    public void prePersist(){
        createAt=new Date();
    }
}
