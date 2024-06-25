package io.bayrktlihn.jobportal.controller;

import io.bayrktlihn.jobportal.entity.User;
import io.bayrktlihn.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {

    private final UserService userService;


    @GetMapping("")
    public String recruiterProfile(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = userService.fetchCurrentUserName();
            User user = userService.fetchUserByEmail(username);
        }

        return null;
    }


}
