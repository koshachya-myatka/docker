package ru.paramonova.mongoProject.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.paramonova.mongoProject.dao.ClientDao;
import ru.paramonova.mongoProject.models.Client;
import ru.paramonova.mongoProject.models.ModelDB;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {
    private final ClientDao dao;

    @SneakyThrows
    @GetMapping
    public ModelAndView getPageClients(HttpSession session, HttpServletResponse httpResponse, Model model) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.setModelDB((ModelDB) session.getAttribute("template"));
            model.addAttribute("clients", dao.findAll());
        }
        return new ModelAndView("ClientsView");
    }

    @SneakyThrows
    @PostMapping("/create")
    public ModelAndView createClient(HttpSession session, HttpServletResponse httpResponse, Model model, @ModelAttribute Client client) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.save(client);
            model.addAttribute("clients", dao.findAll());
        }
        return new ModelAndView("ClientsView");
    }

    @SneakyThrows
    @PostMapping("/delete/{id}")
    public ModelAndView deleteClient(HttpSession session, HttpServletResponse httpResponse, Model model, @PathVariable Integer id) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.deleteById(id);
            model.addAttribute("clients", dao.findAll());
        }
        return new ModelAndView("ClientsView");
    }

    @SneakyThrows
    @PostMapping("/searchId")
    public ModelAndView searchId(HttpSession session, HttpServletResponse response, Model model, @RequestParam("id") Integer id) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("clients", dao.findById(id));
        }
        return new ModelAndView("ClientsView");
    }

    @SneakyThrows
    @PostMapping("/searchLogin")
    public ModelAndView searchLogin(HttpSession session, HttpServletResponse response, Model model, @RequestParam("login") String login) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("clients", dao.findByLogin(login));
        }
        return new ModelAndView("ClientsView");
    }
}
