package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.model.Entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/v1/user")
@Tag(name = "user management API", description = "Api for user")
public interface UserResource {

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> createUser(@RequestBody User user);
}
