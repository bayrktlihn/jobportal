package io.bayrktlihn.jobportal.controller;

import io.bayrktlihn.jobportal.dto.RecruiterJobDto;
import io.bayrktlihn.jobportal.entity.JobPostActivity;
import io.bayrktlihn.jobportal.entity.RecruiterProfile;
import io.bayrktlihn.jobportal.entity.User;
import io.bayrktlihn.jobportal.service.JobPostActivityService;
import io.bayrktlihn.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;


    @GetMapping("dashboard/")
    public String dashboard(Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        Object currentUserProfile = userService.fetchCurrentUserProfile();

        String username = userService.fetchCurrentUserName();

        if (Objects.nonNull(username)) {
            model.addAttribute("username", username);
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Recruiter"))) {
                RecruiterProfile recruiterProfile = (RecruiterProfile) currentUserProfile;
                List<RecruiterJobDto> recruiterJobs = jobPostActivityService.fetchRecruiterJobs(recruiterProfile.getId());
                model.addAttribute("jobPost", recruiterJobs);
            }
        }

        model.addAttribute("userProfile", currentUserProfile);


        return "dashboard";

    }

    @GetMapping("dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("userProfile", userService.fetchCurrentUserProfile());
        return "add_jobs";
    }

    @PostMapping("dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        User user = userService.fetchCurrentUser();

        if (Objects.nonNull(user)) {
            jobPostActivity.setUser(user);
        }
        jobPostActivity.setPostedDate(new Date());

        jobPostActivity = jobPostActivityService.addNew(jobPostActivity);

        model.addAttribute("jobPostActivity", jobPostActivity);

        return "redirect:/dashboard/";
    }

}
