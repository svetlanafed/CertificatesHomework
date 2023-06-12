package com.example.certificateshomework.dto;

import com.example.certificateshomework.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
