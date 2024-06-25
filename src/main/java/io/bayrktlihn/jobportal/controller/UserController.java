package io.bayrktlihn.jobportal.controller;

import io.bayrktlihn.jobportal.entity.User;
import io.bayrktlihn.jobportal.entity.UserType;
import io.bayrktlihn.jobportal.exception.MessageSupportedException;
import io.bayrktlihn.jobportal.service.UserService;
import io.bayrktlihn.jobportal.service.UserTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserTypeService userTypeService;
    private final UserService userService;

    @GetMapping("register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.fetchAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register/new")
    public String userRegistration(@Valid User user, Model model) {
        try {
            userService.addNew(user);
            return "dashboard";
        } catch (MessageSupportedException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return register(model);
        }
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (Objects.nonNull(authentication)) {
            SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
            securityContextLogoutHandler.logout(request, response, authentication);
        }
        return "redirect:/";
    }

}
