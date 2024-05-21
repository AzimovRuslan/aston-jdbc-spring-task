package io.aston.serverside.dao.interfaces;

import io.aston.serverside.entity.Employee;
import io.aston.serverside.entity.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeManipulation {
    Map<Employee, List<Project>> getEmployeeByIdWithProjects(int id) throws SQLException;
}
