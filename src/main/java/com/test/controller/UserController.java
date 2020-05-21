package com.test.controller;

import com.test.api.request.UserRequest;
import com.test.api.request.UserSearchRequest;
import com.test.api.response.UserResponse;
import com.test.domain.User;
import com.test.service.UserService;
import com.test.service.UserServiceImpl;
import com.test.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @GetMapping
    public List<UserResponse> findAll(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "country", required = false) String country,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          @RequestParam(value = "creationDateFrom", required = false) LocalDateTime creationDateFrom,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          @RequestParam(value = "creationDateTo", required = false) LocalDateTime creationDateTo,
                                      @RequestParam(value = "role", required = false) String role) {
        UserSearchRequest userSearchRequest = UserSearchRequest.builder()
                .name(name)
                .email(email)
                .country(country)
                .creationDateFrom(creationDateFrom)
                .creationDateTo(creationDateTo)
                .role(role)
                .build();

        return objectMapperUtils.mapAll(userService.findAllUsers(userSearchRequest), UserResponse.class);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        User createdUser = userService.createUser(userRequest);
        return objectMapperUtils.map(createdUser, UserResponse.class);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable("userId") Long userId,
                                   @Valid @RequestBody UserRequest userRequest) {
        User updatedUser = userService.updateUser(userId, userRequest);
        return objectMapperUtils.map(updatedUser, UserResponse.class);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
