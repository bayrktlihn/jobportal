package io.bayrktlihn.jobportal.controller;

import io.bayrktlihn.jobportal.entity.RecruiterProfile;
import io.bayrktlihn.jobportal.entity.User;
import io.bayrktlihn.jobportal.service.RecruiterProfileService;
import io.bayrktlihn.jobportal.service.UserService;
import io.bayrktlihn.jobportal.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("recruiter-profile")
@RequiredArgsConstructor
public class RecruiterProfileController {

    private final UserService userService;
    private final RecruiterProfileService recruiterProfileService;


    @GetMapping("/")
    public String recruiterProfile(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = userService.fetchCurrentUserName();
            User user = userService.fetchUserByEmail(username);

            RecruiterProfile recruiterProfile = recruiterProfileService.fetchById(user.getId());
            if (Objects.nonNull(recruiterProfile)) {
                model.addAttribute("recruiterProfile", recruiterProfile);
            }
        }

        return "recruiter_profile";
    }


    @PostMapping("addNew")
    public String addNew(
            RecruiterProfile recruiterProfile,
            @RequestParam("image") MultipartFile multipartFile,
            Model model
    ) throws IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = userService.fetchCurrentUserName();
            User user = userService.fetchUserByEmail(username);
            recruiterProfile.setUser(user);
            recruiterProfile.setId(user.getId());
        }
        model.addAttribute("recruiterProfile", recruiterProfile);
        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename());
            fileName = StringUtils.cleanPath(originalFilename);
            recruiterProfile.setProfilePhoto(fileName);
        }

        RecruiterProfile savedRecruiterProfile = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedRecruiterProfile.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/dashboard/";
    }


}
