package io.aston.serverside.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Employee {

    private int id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    private String surname;

    @NotEmpty(message = "Employee personal info must not be empty")
    private EmployeePersonalInfo personalInfo;

    @NotEmpty(message = "Role must not be empty")
    private EmployeeRole role;

    public Employee(String name, String surname, EmployeePersonalInfo personalInfo, EmployeeRole role) {
        this.name = name;
        this.surname = surname;
        this.personalInfo = personalInfo;
        this.role = role;
    }
}
