package ru.paramonova.mongoProject.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    private Integer id;
    private String title;
    private Integer countPage;
    private Integer amount;
    private String genre;
}
