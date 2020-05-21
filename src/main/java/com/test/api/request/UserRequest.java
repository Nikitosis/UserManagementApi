package com.test.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private CountryRequest country;
}
