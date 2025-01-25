package com.mrkunal.zencer.controller.impl;

import com.mrkunal.zencer.controller.contract.UserResource;
import com.mrkunal.zencer.model.Entity.User;
import com.mrkunal.zencer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResourceImpl implements UserResource {
    private final UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
//        return StandardResponse.<User>builder()
//                .success(true)
//                .data(userService.createUser(user))
//                .code("200")
//                .message("fds")
//                .build();x
        User user1 = userService.createUser(user);
//        return StandardResponse.<User>buildResponse()
//                .success(true)
//                .code("200")
//                .message("User created successfully")
//                .data(user1)
//                .build();
//        return new StandardResponse<>(true, "User created successfully", user1);

        return ResponseEntity.ok(userService.createUser(user1));
    }

}
