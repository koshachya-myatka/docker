package ru.paramonova.mongoProject.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    private Integer id;
    private LocalDate dateOrder;
    private Integer idClient;
    private Integer idBook;
}
