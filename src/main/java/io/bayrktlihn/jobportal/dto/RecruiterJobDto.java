package io.bayrktlihn.jobportal.dto;

import io.bayrktlihn.jobportal.entity.JobCompany;
import io.bayrktlihn.jobportal.entity.JobLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecruiterJobDto {
    private Long totalCandidates;
    private Long jobPostActivityUserId;
    private String jobTitle;
    private JobLocation jobLocation;
    private JobCompany jobCompany;
}
