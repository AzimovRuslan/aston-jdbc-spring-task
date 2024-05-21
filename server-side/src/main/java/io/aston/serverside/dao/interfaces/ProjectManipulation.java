package io.aston.serverside.dao.interfaces;

import io.aston.serverside.entity.ProjectToEmployee;

import java.sql.SQLException;
import java.util.List;

public interface ProjectManipulation {
    void addEmployee(ProjectToEmployee projectToEmployee) throws SQLException;

    List<ProjectToEmployee> getAllProjectWithWorkers() throws SQLException;

    List<ProjectToEmployee> getProjectByIdWithWorkers(int id) throws SQLException;
}
