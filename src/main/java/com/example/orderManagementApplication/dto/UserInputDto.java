package com.example.orderManagementApplication.dto;

import com.example.orderManagementApplication.entity.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInputDto {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private UserStatus userStatus;
}