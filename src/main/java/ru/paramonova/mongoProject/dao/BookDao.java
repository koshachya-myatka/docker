package ru.paramonova.mongoProject.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.paramonova.mongoProject.models.Book;
import ru.paramonova.mongoProject.models.ModelDB;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDao {
    @Setter
    private ModelDB modelDB;
    private final ModelMapper modelMapper;

    public List<Book> findAll() {
        return modelDB.getTemplate().findAll(Book.class, "books");
    }

    public Book findById(Integer id) {
        return modelDB.getTemplate().findById(id, Book.class, "books");
    }

    public List<Book> findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(title.toLowerCase(), "i"));
        return modelDB.getTemplate().find(query, Book.class, "books");
    }

    public List<Book> findByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre").regex("^" + genre.toLowerCase() + "$", "i"));
        return modelDB.getTemplate().find(query, Book.class, "books");
    }

    public List<Book> findByAmount() {
        Query query = new Query();
        query.addCriteria(Criteria.where("amount").gt(0));
        return modelDB.getTemplate().find(query, Book.class, "books");
    }

    public long groupByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre").regex("^" + genre.toLowerCase() + "$", "i"));
        return modelDB.getTemplate().count(query, Book.class, "books");
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            int number = (int) modelDB.getTemplate().getCollection("books").countDocuments();
            book.setId(number + 1);
            modelDB.getTemplate().save(book, "books");
        } else {
            if (modelDB.getTemplate().findById(book.getId(), Book.class, "books") == null) {
                throw new IllegalArgumentException();
            }
            Book oldBook = modelMapper.map(book, Book.class);
            modelDB.getTemplate().save(oldBook, "books");
        }
        return book;
    }

    public void deleteById(Integer id) {
        Book book = modelDB.getTemplate().findById(id, Book.class, "books");
        if (book != null) {
            modelDB.getTemplate().remove(book, "books");
        }
    }
}
