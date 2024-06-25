package io.bayrktlihn.jobportal.service;

import io.bayrktlihn.jobportal.entity.RecruiterProfile;
import io.bayrktlihn.jobportal.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;


    public RecruiterProfile fetchById(Long id) {
        return recruiterProfileRepository.findById(id).orElse(null);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }
}
