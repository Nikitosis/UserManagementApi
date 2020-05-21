package com.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api.response.UserResponse;
import com.test.domain.User;
import com.test.service.UserService;
import com.test.utils.ObjectMapperUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
        @Bean
        public ObjectMapperUtils objectMapperUtils() {
            return new ObjectMapperUtils(modelMapper());
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @WithMockUser(value = "admin")
    @Test
    public void createUser_successPath() throws Exception {
        User user1 = User.builder()
                .name("user1")
                .build();
        User user2 = User.builder()
                .name("user2")
                .build();

        when(userService.findAllUsers(any())).thenReturn(Arrays.asList(user1, user2));

        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        List<UserResponse> userResponses = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserResponse>>(){});

        assertEquals(userResponses.size(), 2);
        assertEquals(userResponses.get(0).getName(), user1.getName());
        assertEquals(userResponses.get(1).getName(), user2.getName());
    }
}
