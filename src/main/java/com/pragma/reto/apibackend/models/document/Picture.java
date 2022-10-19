package com.pragma.reto.apibackend.models.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "pictures")
@Getter
@Setter
public class Picture {
    @Id
    private String id;
    private long idPerson;
    private String pictureUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate = LocalDateTime.now();
}
