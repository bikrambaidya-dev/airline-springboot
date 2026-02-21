package com.airline.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User registration request")
public class RegisterRequest {

    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "bikram.dotlinkertech@gmail.com")
    private String email;

    @Schema(example = "12345678")
    private String password;

    @Schema(example = "9876543210")
    private String phone;
}
