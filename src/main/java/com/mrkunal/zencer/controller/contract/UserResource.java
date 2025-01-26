package com.mrkunal.zencer.controller.contract;

import com.mrkunal.zencer.dto.request.SignInRequest;
import com.mrkunal.zencer.dto.request.SignUpRequest;
import com.mrkunal.zencer.dto.response.SignInResponse;
import com.mrkunal.zencer.dto.response.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/user")
@Tag(name = "user management API", description = "Api for user")
public interface UserResource {

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> createUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult);

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<SignInResponse>> login(@Valid @RequestBody SignInRequest signInRequest, BindingResult bindingResult);

    @GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse<String>> logout(@RequestHeader("Authorization") String token);
}
