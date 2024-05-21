package io.aston.serverside.dao;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.dao.interfaces.ProjectManipulation;
import io.aston.serverside.entity.Employee;
import io.aston.serverside.entity.Project;
import io.aston.serverside.entity.ProjectToEmployee;
import io.aston.serverside.exception.sql.FailedDeleteException;
import io.aston.serverside.exception.sql.FailedSaveException;
import io.aston.serverside.exception.sql.FailedUpdateException;
import io.aston.serverside.exception.sql.NoSuchRecordException;
import io.aston.serverside.utility.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Data
@Slf4j
public class ProjectDAO implements DAOInterface<Project>, ProjectManipulation {

    private final DataSource dataSource;
    private final DAOInterface<Employee> employeeDAO;

    @Override
    public List<Project> getAll() throws SQLException {
        String query = Constants.GET_ALL_PROJECTS;
        List<Project> projects = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                projects.add(project);
            }
        }

        return projects;
    }

    @Override
    public Project getById(long id) throws SQLException {
        String query = Constants.GET_BY_ID_FROM_PROJECTS;
        Project project = new Project();
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                log.info(Constants.PROJECT + Constants.FOUND + project);
            } else {
                throw new NoSuchRecordException(Constants.PROJECT + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return project;
    }

    @Override
    public void save(Project project) throws SQLException {
        String query = Constants.INSERT_INTO_PROJECTS;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.getName());
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(project.getName() + Constants.PROJECT + Constants.SUCCESSFUL_SAVE);
            } else {
                throw new FailedSaveException(project.getName() + Constants.PROJECT + Constants.UNSUCCESSFUL_SAVE);
            }
        }
    }

    @Override
    public void update(long id, Project project) throws SQLException {
        String query = Constants.UPDATE_PROJECTS;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, id);

            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.PROJECT + Constants.SUCCESSFUL_UPDATE + Constants.WITH_ID + id);
            } else {
                throw new FailedUpdateException(Constants.PROJECT + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String query = Constants.DELETE_FROM_PROJECTS;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.PROJECT + Constants.SUCCESSFUL_DELETE + Constants.WITH_ID + id);
            } else {
                throw new FailedDeleteException(Constants.PROJECT + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }

    @Override
    public void addEmployee(ProjectToEmployee projectToEmployee) throws SQLException {
        String query = Constants.ADD_EMPLOYEE_TO_PROJECT;


        List<Employee> employees = employeeDAO.getAll();
        List<Project> projects = getAll();

        int employeeId = employees.stream()
                .filter(e -> e.equals(projectToEmployee.getEmployee()))
                .findFirst()
                .orElseThrow()
                .getId();

        int projectId = projects.stream()
                .filter(p -> p.equals(projectToEmployee.getProject()))
                .findFirst()
                .orElseThrow()
                .getId();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(projectToEmployee.getEmployee() + Constants.SUCCESSFUL_ADDED + Constants.PROJECT);
            } else {
                throw new FailedSaveException(projectToEmployee.getEmployee() + Constants.UNSUCCESSFUL_ADDED + Constants.PROJECT);
            }
        }
    }

    @Override
    public List<ProjectToEmployee> getAllProjectWithWorkers() throws SQLException {
        String query = Constants.GET_ALL_PROJECTS_WITH_WORKERS;
        List<ProjectToEmployee> projectToEmployees = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProjectToEmployee projectToEmployee = new ProjectToEmployee();
                projectToEmployee.setProject(getById(resultSet.getInt("project_id")));
                projectToEmployee.setEmployee(employeeDAO.getById(resultSet.getInt("employee_id")));

                projectToEmployees.add(projectToEmployee);
            }
        }

        return projectToEmployees;
    }

    @Override
    public List<ProjectToEmployee> getProjectByIdWithWorkers(int id) throws SQLException {
        String query = Constants.GET_PROJECT_BY_ID_WITH_WORKERS;
        List<ProjectToEmployee> projectToEmployees = new ArrayList<>();
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProjectToEmployee projectToEmployee = new ProjectToEmployee();
                projectToEmployee.setProject(getById(resultSet.getInt("project_id")));
                projectToEmployee.setEmployee(employeeDAO.getById(resultSet.getInt("employee_id")));

                projectToEmployees.add(projectToEmployee);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return projectToEmployees;
    }
}
