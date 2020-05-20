package com.test.service;

import com.test.api.request.UserRequest;
import com.test.api.request.UserSearchRequest;
import com.test.domain.User;

import java.util.List;

public interface UserService {
    public User createUser(UserRequest userRequest);

    public User updateUser(Long userId, UserRequest userRequest);

    public void deleteUser(Long userId);

    public List<User> findAllUsers();

    public List<User> findAllUsers(UserSearchRequest userSearchRequest);
}
