package io.aston.serverside.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class EmployeeRole {

    private short id;

    @NotEmpty(message = "Role must not be empty")
    private String role;

    public EmployeeRole(String role) {
        this.role = role;
    }
}
