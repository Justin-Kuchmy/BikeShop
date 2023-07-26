package com.justinkuchmy.security.dto;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class SignUpDto {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public SignUpDto() {
        super();
    }

    public SignUpDto(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email, @NotEmpty String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
