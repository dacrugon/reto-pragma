package com.pragma.reto.apibackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="images")
@Getter
@Setter
public class Image implements Serializable {

    @Id
    @Column(name = "idimage")
    private Long id;

    //@Column(name = "id_person")
    @JoinColumn(name = "id_person")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Person id_person;

    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "create_at")
    private Date createAt;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

}
