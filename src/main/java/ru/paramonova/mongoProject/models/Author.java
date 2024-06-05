package ru.paramonova.mongoProject.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {
    @Id
    private Integer id;
    private String surname;
    private String name;
    private LocalDate dateOfBorn;
    private Integer experience;
}
