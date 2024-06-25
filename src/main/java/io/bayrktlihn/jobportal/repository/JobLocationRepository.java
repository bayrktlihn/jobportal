package io.bayrktlihn.jobportal.repository;

import io.bayrktlihn.jobportal.entity.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {
}
