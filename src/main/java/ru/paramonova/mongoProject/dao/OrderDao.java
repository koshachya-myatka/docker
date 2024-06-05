package ru.paramonova.mongoProject.dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.paramonova.mongoProject.models.Book;
import ru.paramonova.mongoProject.models.ModelDB;
import ru.paramonova.mongoProject.models.Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDao {
    @Setter
    private ModelDB modelDB;

    public List<Order> findAll() {
        return modelDB.getTemplate().findAll(Order.class, "orders");
    }

    public List<Order> findByIdClient(Integer idClient) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idClient").is(idClient));
        return modelDB.getTemplate().find(query, Order.class, "orders");
    }

    public List<Order> findByIdBook(Integer idBook) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idBook").is(idBook));
        return modelDB.getTemplate().find(query, Order.class, "orders");
    }

    public long groupByIdClient(Integer idClient) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idClient").is(idClient));
        return modelDB.getTemplate().count(query, Book.class, "orders");
    }

    public long groupByIdBook(Integer idBook) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idBook").is(idBook));
        return modelDB.getTemplate().count(query, Order.class, "orders");
    }
}
