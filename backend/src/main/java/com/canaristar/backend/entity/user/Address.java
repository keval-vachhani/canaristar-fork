package com.canaristar.backend.entity.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;
}
