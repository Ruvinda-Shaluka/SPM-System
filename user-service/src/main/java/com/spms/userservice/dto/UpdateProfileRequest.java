package com.spms.userservice.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String phoneNumber;

    private String profileImageUrl;

    private String password;
}
