package ru.paramonova.mongoProject.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.paramonova.mongoProject.dao.AuthorDao;
import ru.paramonova.mongoProject.models.Author;
import ru.paramonova.mongoProject.models.ModelDB;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorsController {
    private final AuthorDao dao;

    @SneakyThrows
    @GetMapping
    public ModelAndView getPageAuthors(HttpSession session, HttpServletResponse httpResponse, Model model) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.setModelDB((ModelDB) session.getAttribute("template"));
            model.addAttribute("authors", dao.findAll());
        }
        return new ModelAndView("AuthorsView");
    }

    @SneakyThrows
    @PostMapping("/create")
    public ModelAndView createAuthor(HttpSession session, HttpServletResponse httpResponse, Model model, @ModelAttribute Author author) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.save(author);
            model.addAttribute("authors", dao.findAll());
        }
        return new ModelAndView("AuthorsView");
    }

    @SneakyThrows
    @GetMapping("/update/{id}")
    public ModelAndView getAuthor(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            model.addAttribute("author", dao.findById(id));
        }
        return new ModelAndView("SelectedAuthorView");
    }

    @SneakyThrows
    @PostMapping("/update/{id}")
    public ModelAndView updateAuthor(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id, @ModelAttribute Author newAuthor) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.save(newAuthor);
            model.addAttribute("author", newAuthor);
        }
        return new ModelAndView("SelectedAuthorView");
    }

    @SneakyThrows
    @PostMapping("/delete/{id}")
    public ModelAndView deleteAuthor(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.deleteById(id);
            model.addAttribute("authors", dao.findAll());
        }
        return new ModelAndView("AuthorsView");
    }

    @SneakyThrows
    @PostMapping("/searchSurname")
    public ModelAndView searchSurname(HttpSession session, HttpServletResponse response, Model model, @RequestParam("surname") String surname) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("authors", dao.findBySurname(surname));
        }
        return new ModelAndView("AuthorsView");
    }
}
