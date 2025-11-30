package com.canaristar.backend.entity.user;

import com.canaristar.backend.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank
    private String mobile;
    private Address address;
    private Role role = Role.USER;
}
