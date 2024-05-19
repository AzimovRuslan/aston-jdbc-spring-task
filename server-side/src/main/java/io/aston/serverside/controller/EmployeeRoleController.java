package io.aston.serverside.controller;

import io.aston.serverside.dao.DAOInterface;
import io.aston.serverside.entity.EmployeeRole;
import io.aston.serverside.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/employee-roles")
@AllArgsConstructor
@Slf4j
public class EmployeeRoleController {

    private final DAOInterface<EmployeeRole> employeeRoleDAO;

    @GetMapping("/")
    public List<EmployeeRole> getAll() {
        try {
            return employeeRoleDAO.getAll();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @GetMapping("/{id}")
    public EmployeeRole getById(@PathVariable("id") long id) {
        try {
            return employeeRoleDAO.getById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @PostMapping
    public void save(@RequestBody EmployeeRole employeeRole) {
        try {
            employeeRoleDAO.save(employeeRole);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody EmployeeRole employeeRole) {
        try {
            employeeRoleDAO.update(id, employeeRole);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        try {
            employeeRoleDAO.deleteById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }
}
