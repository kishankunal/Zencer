package com.mrkunal.zencer.controller.impl;

import com.google.inject.Inject;
import com.mrkunal.zencer.controller.contract.UserResource;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserResourceImpl implements UserResource {
    private final UserService userService;

    @Inject
    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<StandardResponse<User>> createUser(User user) {
        User createdUser = userService.createUser(user);
        StandardResponse<User> standardResponse = new StandardResponse.Builder<User>()
                .success(true)
                .code("200")
                .data(createdUser)
                .message("user Created")
                .build();

        return ResponseEntity.ok(standardResponse);
    }
}
