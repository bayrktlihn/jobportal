package io.bayrktlihn.jobportal.service;

import io.bayrktlihn.jobportal.entity.JobSeekerProfile;
import io.bayrktlihn.jobportal.entity.RecruiterProfile;
import io.bayrktlihn.jobportal.entity.User;
import io.bayrktlihn.jobportal.entity.UserType;
import io.bayrktlihn.jobportal.exception.MessageSupportedException;
import io.bayrktlihn.jobportal.repository.JobSeekerProfileRepository;
import io.bayrktlihn.jobportal.repository.RecruiterProfileRepository;
import io.bayrktlihn.jobportal.repository.UserRepository;
import io.bayrktlihn.jobportal.repository.UserTypeRepository;
import io.bayrktlihn.jobportal.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public User addNew(User user) {

        boolean existsEmail = userRepository.existsByEmail(user.getEmail());

        if (existsEmail) {
            throw new MessageSupportedException("Email already registered try to login or register with other email.");
        }


        UserType userType = userTypeRepository.findById(user.getUserTypeId()).orElse(null);

        String encodedPassword = passwordEncoder.encode(user.getRawPassword());

        Date now = DateUtil.now();
        user.setActive(true);
        user.setRegistrationDate(now);
        user.setUserType(userType);
        user.setPassword(encodedPassword);
        user = userRepository.save(user);

        if (!Objects.isNull(userType)) {
            if (userType.getId() == 1) {
                RecruiterProfile recruiterProfile = new RecruiterProfile();
                recruiterProfile.setUser(user);
                recruiterProfile = recruiterProfileRepository.save(recruiterProfile);
            } else {
                JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
                jobSeekerProfile.setUser(user);
                jobSeekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
            }
        }


        return user;
    }

    public User fetchUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
        return user;
    }

    public String fetchCurrentUserName(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();


        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return authentication.getName();
        }

        return null;
    }

    public Object fetchCurrentUserProfile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String userName = authentication.getName();
            User user = userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
            boolean hasJobSeekerAuthority = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("Job Seeker"));
            boolean hasRecruiterAuthority = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("Recruiter"));
            if(hasRecruiterAuthority){
                return recruiterProfileRepository.findById(user.getId()).orElse(null);
            } else if(hasJobSeekerAuthority){
                return jobSeekerProfileRepository.findById(user.getId()).orElse(null);
            }
        }

        return null;
    }
}
