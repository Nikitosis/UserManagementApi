package com.test.service;

import com.test.api.request.UserRequest;
import com.test.dao.CountryRepository;
import com.test.dao.RoleRepository;
import com.test.dao.UserRepository;
import com.test.domain.Country;
import com.test.domain.Role;
import com.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private RoleService roleService;

    public User createUser(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        Role defaultRole = roleService.findByName("USER");
        Set<Role> defaultRoles = new HashSet<>(Arrays.asList(defaultRole));

        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .country(countryService.findByName(userRequest.getCountry().getName()))
                .creationDate(LocalDateTime.now())
                .roles(defaultRoles)
                .build();

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if(!userRepository.findById(userId).isPresent()) {
            throw new IllegalArgumentException("Can't find user by id");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long userId, UserRequest userRequest) {
        //check if no user with id
        if(!userRepository.findById(userId).isPresent()) {
            throw new IllegalArgumentException("Can't find user by id");
        }

        //check if another user has the same email
        Optional<User> userByEmail = userRepository.findByEmail(userRequest.getEmail());
        if(userByEmail.isPresent() && userByEmail.get().getId() != userId) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = User.builder()
                .id(userId)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .country(countryService.findByName(userRequest.getCountry().getName()))
                .creationDate(LocalDateTime.now())
                .roles(userRepository.findById(userId).get().getRoles())
                .build();

        return userRepository.save(user);
    }
}
