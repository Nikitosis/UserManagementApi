package com.test.controller;

import com.test.api.request.UserRequest;
import com.test.api.response.UserResponse;
import com.test.domain.User;
import com.test.service.UserService;
import com.test.service.UserServiceImpl;
import com.test.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @GetMapping
    public List<UserResponse> findAll() {
        return objectMapperUtils.mapAll(userService.findAllUsers(), UserResponse.class);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User createdUser = userService.createUser(userRequest);
        return objectMapperUtils.map(createdUser, UserResponse.class);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable("userId") Long userId,
                                   @RequestBody UserRequest userRequest) {
        User updatedUser = userService.updateUser(userId, userRequest);
        return objectMapperUtils.map(updatedUser, UserResponse.class);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
