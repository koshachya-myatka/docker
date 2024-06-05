package ru.paramonova.mongoProject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.paramonova.mongoProject.dto.UserDto;
import ru.paramonova.mongoProject.models.ModelDB;

@RestController
public class LoginController {

    @GetMapping("/login")
    public ModelAndView getPageLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("LoginView");
    }

    @SneakyThrows
    @PostMapping("/login")
    public RedirectView postPageLogin(HttpServletRequest request, @ModelAttribute("userDto") UserDto userDto, RedirectAttributes ra) {
        ModelDB modelDB = new ModelDB(userDto.getUsername(), userDto.getPassword());
        if (modelDB.getTemplate() == null) {
            ra.addFlashAttribute("message", "[x] Неверное имя пользователя или пароль.");
            return new RedirectView("/login");
        }
        request.getSession().setAttribute("template", modelDB);
        return new RedirectView("/collections");
    }

    @SneakyThrows
    @GetMapping("/collections")
    public ModelAndView getPageSelectCollection(HttpSession session, HttpServletResponse httpResponse) {
        if (session.getAttribute("template") == null) {
            httpResponse.sendRedirect("/login");
        }
        return new ModelAndView("SelectCollectionView");
    }

    @GetMapping("/notaccess")
    public ModelAndView getPageNotAccess() {
        return new ModelAndView("NotAccessPageView");
    }
}
