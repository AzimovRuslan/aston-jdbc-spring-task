package io.aston.serverside.controller;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.entity.EmployeeRole;
import io.aston.serverside.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v0/employee-roles")
@AllArgsConstructor
@Slf4j
public class EmployeeRoleController {

    private final DAOInterface<EmployeeRole> daoInterface;

    @GetMapping("/")
    public List<EmployeeRole> getAll() {
        try {
            return daoInterface.getAll();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }

    @GetMapping("/{id}")
    public EmployeeRole getById(@PathVariable("id") long id) {
        try {
            return daoInterface.getById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @PostMapping
    public void save(@RequestBody EmployeeRole employeeRole) {
        try {
            daoInterface.save(employeeRole);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody EmployeeRole employeeRole) {
        try {
            daoInterface.update(id, employeeRole);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        try {
            daoInterface.deleteById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }
}
