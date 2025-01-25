package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.SignUpRequest;
import com.mrkunal.zencer.dto.response.StandardResponse;
import com.mrkunal.zencer.model.Entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/v1/user")
@Tag(name = "user management API", description = "Api for user")
public interface UserResource {

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> createUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult);
}
