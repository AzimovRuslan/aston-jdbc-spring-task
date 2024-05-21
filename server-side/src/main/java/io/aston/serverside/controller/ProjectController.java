package io.aston.serverside.controller;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.dao.interfaces.ProjectManipulation;
import io.aston.serverside.entity.Project;
import io.aston.serverside.entity.ProjectToEmployee;
import io.aston.serverside.utility.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("api/v0/projects")
@AllArgsConstructor
@Slf4j
public class ProjectController {

    private final DAOInterface<Project> daoInterface;
    private final ProjectManipulation projectManipulation;

    @GetMapping("/")
    public List<Project> getAll() {
        try {
            return daoInterface.getAll();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable("id") long id) {
        try {
            return daoInterface.getById(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return null;
        }
    }

    @PostMapping
    public void save(@RequestBody Project project) {
        try {
            daoInterface.save(project);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Project project) {
        try {
            daoInterface.update(id, project);
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

    @PostMapping("/add-employee")
    public void addEmployee(@RequestBody ProjectToEmployee projectToEmployee) {
        try {
            projectManipulation.addEmployee(projectToEmployee);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @DeleteMapping("/delete-employee/{project_id}-{employee_id}")
    public void deleteEmployee(@PathVariable("project_id") int projectId,
                               @PathVariable("employee_id") int employeeId) {
        try {
            projectManipulation.deleteEmployee(projectId, employeeId);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
        }
    }

    @GetMapping("/get-project-with-workers/")
    public List<ProjectToEmployee> getAllProjectsWithWorkers() {
        try {
            return projectManipulation.getAllProjectWithWorkers();
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }

    @GetMapping("/get-project-with-workers/{id}")
    public List<ProjectToEmployee> getProjectByIdWithWorkers(@PathVariable("id") int id) {
        try {
            return projectManipulation.getProjectByIdWithWorkers(id);
        } catch (SQLException e) {
            log.error(e.getMessage() + Constants.REQUEST_FAILED);
            return Collections.emptyList();
        }
    }
}
