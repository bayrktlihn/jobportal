package io.bayrktlihn.jobportal.repository;

import io.bayrktlihn.jobportal.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
