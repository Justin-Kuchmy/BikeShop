package com.justinkuchmy.security.dto;
import lombok.Data;

@Data
public class CredentialsDto {
    private String login;
    private String password;

     public CredentialsDto() {
        super();
    }

    public CredentialsDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
