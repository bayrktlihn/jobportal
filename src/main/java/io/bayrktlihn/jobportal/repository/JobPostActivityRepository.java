package io.bayrktlihn.jobportal.repository;

import io.bayrktlihn.jobportal.dto.projection.RecruiterJobProjection;
import io.bayrktlihn.jobportal.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Long> {

    @Query(
            value = """
                    select\s
                    jpa.user_id as "jobPostActivityUserId" ,
                    jpa.job_title as "jobTitle",
                    jl.id as "jobLocationId",
                    jl.city as "jobLocationCity",
                    jl.state as "jobLocationState",
                    jl.country as "jobLocationCountry",
                    jc.id as "jobCompanyId",
                    jc."name" as "jobCompanyName"\s
                    from job_post_activity jpa\s
                    inner join job_location jl on jl.id = jpa.job_location_id\s
                    inner join job_company jc on jc.id = jpa.job_company_id\s
                    where jpa.user_id = :recruiter
                                        """,
            nativeQuery = true
    )
    List<RecruiterJobProjection> findRecruiterJobs(@Param("recruiter") long recruiter);

}
