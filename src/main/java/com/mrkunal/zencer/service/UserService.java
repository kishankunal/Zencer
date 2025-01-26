package com.mrkunal.zencer.service;

import com.mrkunal.zencer.model.Entity.UserSession;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.model.enums.TokenStatus;
import com.mrkunal.zencer.repository.SessionRepo;
import com.mrkunal.zencer.repository.UserRepo;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import com.mrkunal.zencer.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

import static com.mrkunal.zencer.constant.ExceptionMessageConstant.INVALID_MOBILE_NUMBER_OR_PASSWORD;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final SessionRepo sessionRepo;
    @Inject
    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, SessionRepo sessionRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.sessionRepo = sessionRepo;
    }


    public void createUser(SignUpRequest request){
        userRepo.createUser(request);
    }

    public String authenticateUser(final String mobileNumber, final String password) {
        User user = userRepo.findByMobileNumber(mobileNumber);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return createOrUpdateToken(user);
        }
        throw new RuntimeException(INVALID_MOBILE_NUMBER_OR_PASSWORD);

    }

    private String createOrUpdateToken(User user){
        UserSession existingSession = sessionRepo.findByUserIdAndTokenStatus(user, TokenStatus.VALID);

        String jwtToken;
        if (existingSession!=null) {
            jwtToken = existingSession.getToken();
        } else {
            jwtToken = JwtUtil.generateJwtToken(user.getUserId(), user.getUserType().toString());
            UserSession userSession = new UserSession(user, jwtToken, TokenStatus.VALID);
            sessionRepo.save(userSession);
        }
        return jwtToken;
    }

    public boolean logoutUser(String token) {
        try {
            UserSession existingSession = sessionRepo.findByTokenAndTokenStatus(token, TokenStatus.VALID);
            if(existingSession == null) return false;
            sessionRepo.invalidateSession(existingSession);
            return true;
        } catch (Exception e){
            return false;
        }

    }
}

