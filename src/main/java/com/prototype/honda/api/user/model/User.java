package com.prototype.honda.api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    @JsonIgnore
    private String id;

    @Indexed(unique = true)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email inválido"
    )
    private String imageProfile;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank
    private String phone;
    @NotBlank
    private Address address;
    private Collection<String> roles;
}
