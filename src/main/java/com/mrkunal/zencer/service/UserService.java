package com.mrkunal.zencer.service;

import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.repository.UserRepo;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

import static com.mrkunal.zencer.util.HashUtil.encryptPassword;

@Service
public class UserService {

    private final UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    @Inject
    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public void createUser(SignUpRequest request){
        userRepo.createUser(request);
    }

    public User authenticateUser(final String mobileNumber, final String password) {
        User user = userRepo.findByMobileNumber(mobileNumber);
        System.out.println(encryptPassword(password));
        System.out.println(user.getPassword());
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid mobile number or password");
    }
}

