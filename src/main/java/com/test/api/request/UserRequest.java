package com.test.api.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    @NotNull
    private String name;
    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Valid
    private CountryRequest country;
}
