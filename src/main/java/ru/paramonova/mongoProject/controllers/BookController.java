package ru.paramonova.mongoProject.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.paramonova.mongoProject.dao.BookDao;
import ru.paramonova.mongoProject.models.Book;
import ru.paramonova.mongoProject.models.ModelDB;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookDao dao;

    @SneakyThrows
    @GetMapping
    public ModelAndView getPageBooks(HttpSession session, HttpServletResponse httpResponse, Model model) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.setModelDB((ModelDB) session.getAttribute("template"));
            model.addAttribute("books", dao.findAll());
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @PostMapping("/create")
    public ModelAndView createBook(HttpSession session, HttpServletResponse httpResponse, Model model, @ModelAttribute Book book) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.save(book);
            model.addAttribute("books", dao.findAll());
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @GetMapping("/update/{id}")
    public ModelAndView getBook(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            model.addAttribute("book", dao.findById(id));
        }
        return new ModelAndView("SelectedBookView");
    }

    @SneakyThrows
    @PostMapping("/update/{id}")
    public ModelAndView updateBook(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id, @ModelAttribute Book newBook) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.save(newBook);
            model.addAttribute("book", newBook);
        }
        return new ModelAndView("SelectedBookView");
    }

    @SneakyThrows
    @PostMapping("/delete/{id}")
    public ModelAndView deleteBook(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.deleteById(id);
            model.addAttribute("books", dao.findAll());
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @PostMapping("/searchTitle")
    public ModelAndView searchTitle(HttpSession session, HttpServletResponse response, Model model, @RequestParam("title") String title) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("books", dao.findByTitle(title));
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @PostMapping("/searchGenre")
    public ModelAndView searchGenre(HttpSession session, HttpServletResponse response, Model model, @RequestParam("genre") String genre) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("books", dao.findByGenre(genre));
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @PostMapping("/searchAmount")
    public ModelAndView searchAmount(HttpSession session, HttpServletResponse response, Model model) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("books", dao.findByAmount());
        }
        return new ModelAndView("BooksView");
    }

    @SneakyThrows
    @PostMapping("/groupGenre")
    public RedirectView groupGenre(HttpSession session, RedirectAttributes ra, @RequestParam("genre") String genre) {
        if (session.getAttribute("template") == null) {
            return new RedirectView("/login");
        } else {
            ra.addFlashAttribute("countBooks", dao.groupByGenre(genre));
        }
        return new RedirectView("/books");
    }
}
