package com.mrkunal.zencer.service;

import com.mrkunal.zencer.repository.UserRepo;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

@Service
public class UserService {

    private final UserRepo userRepo;
    @Inject
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public void createUser(SignUpRequest request){
        userRepo.createUser(request);
    }
}

// remove this autowired and use google juice for injection
// resolve created at and updated at issue
// standard response class

