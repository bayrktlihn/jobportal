package io.bayrktlihn.jobportal.repository;

import io.bayrktlihn.jobportal.entity.JobCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCompanyRepository extends JpaRepository<JobCompany, Long> {
}
