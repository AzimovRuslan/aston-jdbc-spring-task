package io.aston.serverside.controller;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.dao.interfaces.EmployeeManipulation;
import io.aston.serverside.entity.Employee;
import io.aston.serverside.entity.Project;
import io.aston.serverside.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v0/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final DAOInterface<Employee> daoInterface;
    private final EmployeeManipulation employeeManipulation;

    @GetMapping("/")
    public List<Employee> getAll() {
        try {
            return daoInterface.getAll();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") long id) {
        try {
            return daoInterface.getById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @PostMapping
    public void save(@RequestBody Employee employee) {
        try {
            daoInterface.save(employee);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Employee employee) {
        try {
            daoInterface.update(id, employee);
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

    @GetMapping("/get-employee-with-project/{id}")
    public Map<Employee, List<Project>> getEmployeeByIdWithProjects(@PathVariable("id") int id) {
        try {
            return employeeManipulation.getEmployeeByIdWithProjects(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyMap();
        }
    }
}
