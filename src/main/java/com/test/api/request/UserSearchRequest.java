package com.test.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSearchRequest {
    private String name;
    private String email;
    private String country;
    private String role;
    private LocalDateTime creationDateFrom;
    private LocalDateTime creationDateTo;
}
