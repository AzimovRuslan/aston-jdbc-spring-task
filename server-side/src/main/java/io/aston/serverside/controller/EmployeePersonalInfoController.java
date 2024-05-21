package io.aston.serverside.controller;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.entity.EmployeePersonalInfo;
import io.aston.serverside.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v0/employees-personal-info")
@AllArgsConstructor
@Slf4j
public class EmployeePersonalInfoController {

    private final DAOInterface<EmployeePersonalInfo> daoInterface;

    @GetMapping("/")
    public List<EmployeePersonalInfo> getAll() {
        try {
            return daoInterface.getAll();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }

    @GetMapping("/{id}")
    public EmployeePersonalInfo getById(@PathVariable("id") long id) {
        try {
            return daoInterface.getById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @PostMapping
    public void save(@RequestBody EmployeePersonalInfo employeePersonalInfo) {
        try {
            daoInterface.save(employeePersonalInfo);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody EmployeePersonalInfo employeePersonalInfo) {
        try {
            daoInterface.update(id, employeePersonalInfo);
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
