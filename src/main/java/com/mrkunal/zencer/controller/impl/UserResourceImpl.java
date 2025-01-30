package com.mrkunal.zencer.controller.impl;

import com.google.inject.Inject;
import com.mrkunal.zencer.annotation.HandleApiException;
import com.mrkunal.zencer.annotation.HandleValidationError;
import com.mrkunal.zencer.annotation.JwtAuth;
import com.mrkunal.zencer.controller.contract.UserResource;
import com.mrkunal.zencer.dto.request.user.SignInRequest;
import com.mrkunal.zencer.dto.request.user.SignUpRequest;
import com.mrkunal.zencer.dto.response.SignInResponse;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;



@RestController
@HandleApiException
public class UserResourceImpl implements UserResource {
    private final UserService userService;

    @Inject
    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @HandleValidationError
    public ResponseEntity<StandardResponse<String>> createUser(SignUpRequest signUpRequest, BindingResult bindingResult) {
        userService.createUser(signUpRequest);
        return ResponseEntity
                .ok(StandardResponse.success("200", "User Created", null));
    }

    @Override
    @HandleValidationError
    public ResponseEntity<StandardResponse<SignInResponse>> login(SignInRequest signInRequest, BindingResult bindingResult) {
        String token = userService.authenticateUser(signInRequest.getMobileNumber(), signInRequest.getPassword());
        return ResponseEntity.ok(StandardResponse.success("200", "Success", new SignInResponse(token)));
    }

    @Override
    @JwtAuth
    public ResponseEntity<StandardResponse<String>> logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean success = userService.logoutUser(token);

        if (success) {
            return ResponseEntity
                    .ok(StandardResponse.success("200", "Successfully logged out", null));
        } else {
            return ResponseEntity
                    .ok(StandardResponse.success("400", "Invalid token or already logged out", null));
        }
    }


}
