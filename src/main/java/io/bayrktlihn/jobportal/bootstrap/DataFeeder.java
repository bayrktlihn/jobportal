package io.bayrktlihn.jobportal.bootstrap;


import io.bayrktlihn.jobportal.entity.UserType;
import io.bayrktlihn.jobportal.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataFeeder implements CommandLineRunner {

    @Value("${datafeeder.run}")
    private boolean datafeederRun;

    private final UserTypeRepository userTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        if(datafeederRun){
            UserType recruiter = new UserType();
            recruiter.setName("Recruiter");
            userTypeRepository.save(recruiter);

            UserType jobSeeker = new UserType();
            jobSeeker.setName("Job Seeker");
            userTypeRepository.save(jobSeeker);
        }

    }
}
