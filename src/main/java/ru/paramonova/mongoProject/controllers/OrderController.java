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
import ru.paramonova.mongoProject.dao.OrderDao;
import ru.paramonova.mongoProject.models.ModelDB;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderDao dao;

    @SneakyThrows
    @GetMapping
    public ModelAndView getPageOrders(HttpSession session, HttpServletResponse httpResponse, Model model) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        } else {
            dao.setModelDB((ModelDB) session.getAttribute("template"));
            model.addAttribute("orders", dao.findAll());
        }
        return new ModelAndView("OrdersView");
    }

    @SneakyThrows
    @PostMapping("/searchIdClient")
    public ModelAndView searchIdClient(HttpSession session, HttpServletResponse response, Model model, @RequestParam("idClient") Integer idClient) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("orders", dao.findByIdClient(idClient));
        }
        return new ModelAndView("OrdersView");
    }

    @SneakyThrows
    @PostMapping("/searchIdBook")
    public ModelAndView searchIdBook(HttpSession session, HttpServletResponse response, Model model, @RequestParam("idBook") Integer idBook) {
        if (session.getAttribute("template") == null) {
            response.sendRedirect("/login");
        } else {
            model.addAttribute("orders", dao.findByIdBook(idBook));
        }
        return new ModelAndView("OrdersView");
    }

    @SneakyThrows
    @PostMapping("/groupIdClient")
    public RedirectView groupIdClient(HttpSession session, RedirectAttributes ra, @RequestParam("idClient") Integer idClient) {
        if (session.getAttribute("template") == null) {
            return new RedirectView("/login");
        } else {
            ra.addFlashAttribute("countClientsOrders", dao.groupByIdClient(idClient));
        }
        return new RedirectView("/orders");
    }

    @SneakyThrows
    @PostMapping("/groupIdBook")
    public RedirectView groupIdBook(HttpSession session, RedirectAttributes ra, @RequestParam("idBook") Integer idBook) {
        if (session.getAttribute("template") == null) {
            return new RedirectView("/login");
        } else {
            ra.addFlashAttribute("countBooksOrders", dao.groupByIdBook(idBook));
        }
        return new RedirectView("/orders");
    }
}
