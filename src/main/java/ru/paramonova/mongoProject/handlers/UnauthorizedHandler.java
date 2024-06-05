package ru.paramonova.mongoProject.handlers;

import com.mongodb.MongoCommandException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.RedirectView;

@RestControllerAdvice
public class UnauthorizedHandler {
    @ExceptionHandler(UncategorizedMongoDbException.class)
    public RedirectView uncategorizedHandle(UncategorizedMongoDbException ex) {
        return new RedirectView("/notaccess");
    }

    @ExceptionHandler(MongoCommandException.class)
    public RedirectView unauthorizedHandle(MongoCommandException ex) {
        return new RedirectView("/notaccess");
    }
}
