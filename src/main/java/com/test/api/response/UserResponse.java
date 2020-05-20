package com.test.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private CountryResponse country;
    private Set<RoleResponse> roles;
    private LocalDateTime creationDate;
}
