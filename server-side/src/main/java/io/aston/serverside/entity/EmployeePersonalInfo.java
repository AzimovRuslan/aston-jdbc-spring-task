package io.aston.serverside.entity;

import io.aston.serverside.dao.EmployeePersonalInfoDAO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class EmployeePersonalInfo {

    private int id;

    @NotEmpty(message = "Email must not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Phone must not be empty")
    private String phone;

    public EmployeePersonalInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}
