package com.test.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private CountryRequest country;
}
