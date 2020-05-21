package com.test.service;

import com.test.api.request.CountryRequest;
import com.test.api.request.UserRequest;
import com.test.dao.UserRepository;
import com.test.domain.Country;
import com.test.domain.Role;
import com.test.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CountryService countryService;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_emailAlreadyExists_throwErrorTest() {
        UserRequest userRequest = userRequest();
        User foundUser = new User();
        when(userRepository.findByEmail(eq(userRequest.getEmail()))).thenReturn(Optional.of(foundUser));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userRequest));
    }

    @Test
    public void createUser_successPathTest() {
        UserRequest userRequest = userRequest();
        Country country = new Country(1L, "Name", "NM");
        when(userRepository.findByEmail(eq(userRequest.getEmail()))).thenReturn(Optional.empty());
        when(countryService.findByName(eq(userRequest.getCountry().getName()))).thenReturn(country);
        when(roleService.findByName(eq("USER"))).thenReturn(new Role(1L, "USER"));

        userService.createUser(userRequest);

        ArgumentCaptor<User> savedUserCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(savedUserCaptor.capture());

        User createdUser = savedUserCaptor.getValue();
        assertEquals(createdUser.getCountry(), country);
        assertEquals(createdUser.getName(), userRequest.getName());
        assertEquals(createdUser.getEmail(), userRequest.getEmail());
        assertEquals(createdUser.getRoles().size(), 1);
        Role role = new ArrayList<Role>(createdUser.getRoles()).get(0);
        assertEquals(role.getName(), "USER");
    }

    private UserRequest userRequest() {
        return UserRequest.builder()
                .name("Name")
                .email("Email")
                .country(new CountryRequest("Country"))
                .build();
    }
}
