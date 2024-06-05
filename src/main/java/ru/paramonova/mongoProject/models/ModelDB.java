package ru.paramonova.mongoProject.models;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;

@Getter
@ToString
public class ModelDB {
    private MongoTemplate template;

    public ModelDB(String username, String password) {
        try {
           ConnectionString connectionString = new ConnectionString(String.format("mongodb://%s:%s@mongodb/?authSource=project_db&authMechanism=SCRAM-SHA-1", username, password));           
            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient = MongoClients.create(mongoClientSettings);
            template = new MongoTemplate(mongoClient, "project_db");
            template.findAll(Book.class, "books");
        } catch (UncategorizedMongoDbException ex) {
            System.out.println("ошибка!");
            template = null;
        }
    }
}
