package io.bayrktlihn.jobportal.service;

import io.bayrktlihn.jobportal.entity.UserType;
import io.bayrktlihn.jobportal.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;


    public List<UserType> fetchAll() {
        return userTypeRepository.findAll();
    }

}
