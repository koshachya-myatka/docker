package ru.paramonova.mongoProject.dao;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.paramonova.mongoProject.models.Author;
import ru.paramonova.mongoProject.models.Book;
import ru.paramonova.mongoProject.models.ModelDB;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorDao {
    @Setter
    private ModelDB modelDB;
    private final ModelMapper modelMapper;

    public List<Author> findAll() {
        return modelDB.getTemplate().findAll(Author.class, "authors");
    }

    public Author findById(Integer id) {
        return modelDB.getTemplate().findById(id, Author.class, "authors");
    }

    public List<Author> findBySurname(String surname) {
        Query query = new Query();
        query.addCriteria(Criteria.where("surname").regex(surname.toLowerCase(), "i"));
        return modelDB.getTemplate().find(query, Author.class, "authors");
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            int number = (int) modelDB.getTemplate().getCollection("authors").countDocuments();
            author.setId(number + 1);
            modelDB.getTemplate().save(author, "authors");
        } else {
            if (modelDB.getTemplate().findById(author.getId(), Author.class, "authors") == null) {
                throw new IllegalArgumentException();
            }
            Author oldAuthor = modelMapper.map(author, Author.class);
            modelDB.getTemplate().save(oldAuthor, "authors");
        }
        return author;
    }

    public void deleteById(Integer id) {
        Author author = modelDB.getTemplate().findById(id, Author.class, "authors");
        if (author != null) {
            modelDB.getTemplate().remove(author, "authors");
        }
    }
}
