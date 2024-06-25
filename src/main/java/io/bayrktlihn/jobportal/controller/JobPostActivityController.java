package io.bayrktlihn.jobportal.controller;

import io.bayrktlihn.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

    private final UserService userService;


    @GetMapping("dashboard/")
    public String dashboard(Model model){
        Object currentUserProfile = userService.fetchCurrentUserProfile();

        String username = userService.fetchCurrentUserName();

        if(Objects.nonNull(username)){
            model.addAttribute("username", username);
        }

        model.addAttribute("userProfile", currentUserProfile);


        return "dashboard";

    }

}
