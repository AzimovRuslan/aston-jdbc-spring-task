package io.aston.serverside.controller;

import io.aston.serverside.dao.EmployeeRoleDAO;
import io.aston.serverside.entity.EmployeeRole;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employee-roles")
@AllArgsConstructor
public class EmployeeRoleController {

    private final EmployeeRoleDAO employeeRoleDAO;

    @GetMapping("/")
    public List<EmployeeRole> getAll() {
        return employeeRoleDAO.getAll();
    }
}
