package com.justinkuchmy.security.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "_user")
@Builder
@AllArgsConstructor
public class UserDto {
    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    private String first_name;
    private String last_name;
    private String email; 
    private String hashword;
    private String token;
    @Enumerated(EnumType.STRING)
    private Role role;

    
    private String roles;

    public UserDto() {
        super();
    }

    public UserDto(Long id, String firstName, String lastName, String email, String hashword, Role role) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.hashword = hashword;
        this.role = role;
    }

    public String getRoles()
    {
        this.roles = this.role.toString();
        return this.roles;
    }
}
