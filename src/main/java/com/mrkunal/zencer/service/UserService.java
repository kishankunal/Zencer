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

import static com.mrkunal.zencer.util.JwtUtil.getUseridFromJwtToken;

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
        throw new RuntimeException("Invalid mobile number or password");

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


//    public void logout(String token) {
//        // Extract userId from the token
//        Claims claims = JwtUtil.validateToken(token);
//        String userId = claims.get("userId", String.class);
//
//        // Mark the session as invalid in the database
//        Optional<Session> session = sessionRepository.findByUserIdAndTokenStatus(userId, TokenStatus.VALID);
//        if (session.isPresent()) {
//            Session existingSession = session.get();
//            existingSession.setTokenStatus(TokenStatus.INVALID); // Mark token as invalid
//            sessionRepository.save(existingSession);  // Persist the change
//        }
//
//        // Client deletes the token from storage (localStorage/sessionStorage)
//        // Example: localStorage.removeItem("jwtToken");
//
//        // Optionally, redirect to the login page
//    }
}

