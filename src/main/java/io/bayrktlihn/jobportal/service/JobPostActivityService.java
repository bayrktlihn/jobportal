package io.bayrktlihn.jobportal.service;

import io.bayrktlihn.jobportal.dto.RecruiterJobDto;
import io.bayrktlihn.jobportal.dto.projection.RecruiterJobProjection;
import io.bayrktlihn.jobportal.entity.JobCompany;
import io.bayrktlihn.jobportal.entity.JobLocation;
import io.bayrktlihn.jobportal.entity.JobPostActivity;
import io.bayrktlihn.jobportal.repository.JobCompanyRepository;
import io.bayrktlihn.jobportal.repository.JobLocationRepository;
import io.bayrktlihn.jobportal.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;
    private final JobCompanyRepository jobCompanyRepository;
    private final JobLocationRepository jobLocationRepository;

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobDto> fetchRecruiterJobs(long recruiter) {
        List<RecruiterJobProjection> recruiterJobProjectionList = jobPostActivityRepository.findRecruiterJobs(recruiter);
        return recruiterJobProjectionList.stream().map(recruiterJobProjection -> {

            JobCompany jobCompany = jobCompanyRepository.findById(recruiterJobProjection.getJobCompanyId()).orElse(null);
            JobLocation jobLocation = jobLocationRepository.findById(recruiterJobProjection.getJobLocationId()).orElse(null);

            RecruiterJobDto recruiterJobDto = new RecruiterJobDto();
            recruiterJobDto.setJobCompany(jobCompany);
            recruiterJobDto.setJobLocation(jobLocation);
            recruiterJobDto.setJobTitle(recruiterJobProjection.getJobTitle());
            recruiterJobDto.setJobPostActivityUserId(recruiterJobProjection.getJobPostActivityUserId());

            return recruiterJobDto;

        }).collect(Collectors.toList());
    }

}
