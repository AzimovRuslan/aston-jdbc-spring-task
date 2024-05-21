package io.aston.serverside.dao;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.dao.interfaces.EmployeeManipulation;
import io.aston.serverside.entity.*;
import io.aston.serverside.exception.FailedDeleteException;
import io.aston.serverside.exception.FailedSaveException;
import io.aston.serverside.exception.FailedUpdateException;
import io.aston.serverside.exception.NoSuchRecordException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Data
@Slf4j
public class EmployeeDAO implements DAOInterface<Employee>, EmployeeManipulation {

    private final DataSource dataSource;
    private final DAOInterface<EmployeePersonalInfo> employeePersonalInfoDAO;
    private final DAOInterface<EmployeeRole> employeeRoleDAO;
//    private final DAOInterface<Project> projectDAO;

    @Override
    public List<Employee> getAll() throws SQLException {
        String query = Constants.GET_ALL_EMPLOYEES;
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();

                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setRole(getRecordFromTable(resultSet, employeeRoleDAO, "role_id"));
                employee.setPersonalInfo(getRecordFromTable(resultSet, employeePersonalInfoDAO, "personal_info_id"));

                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public Employee getById(long id) throws SQLException {
        String query = Constants.GET_BY_ID_FROM_EMPLOYEES;

        ResultSet resultSet = null;

        Employee employee = new Employee();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setRole(getRecordFromTable(resultSet, employeeRoleDAO, "role_id"));
                employee.setPersonalInfo(getRecordFromTable(resultSet, employeePersonalInfoDAO, "personal_info_id"));

                log.info(Constants.EMPLOYEE + Constants.FOUND + employee);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return employee;
    }

    @Override
    public void save(Employee employee) throws SQLException {
        String insertEmployeeQuery = Constants.INSERT_INTO_EMPLOYEES;
        String findRoleQuery = Constants.GET_FROM_EMPLOYEE_ROLES_BY_ATTRIBUTES;
        String findPersonalInfoQuery = Constants.GET_FROM_EMPLOYEES_PERSONAL_INFO_BY_ATTRIBUTES;

        ResultSet roleRs = null;
        ResultSet personalInfoRs = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement findRolePreparedStatement = connection.prepareStatement(findRoleQuery);
             PreparedStatement findPersonalInfoPreparedStatement = connection.prepareStatement(findPersonalInfoQuery);
             PreparedStatement insertEmployeePreparedStatement = connection.prepareStatement(insertEmployeeQuery)) {

            short roleId;
            int personalInfoId;

            findRolePreparedStatement.setString(1, employee.getRole().getRole());
            roleRs = findRolePreparedStatement.executeQuery();

            findPersonalInfoPreparedStatement.setString(1, employee.getPersonalInfo().getEmail());
            findPersonalInfoPreparedStatement.setString(2, employee.getPersonalInfo().getPhone());
            personalInfoRs = findPersonalInfoPreparedStatement.executeQuery();

            if (roleRs.next()) {
                roleId = roleRs.getShort("id");
                log.info(Constants.EMPLOYEE_ROLE + Constants.FOUND);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE_ROLE + Constants.NOT_FOUND);
            }

            if (personalInfoRs.next()) {
                personalInfoId = personalInfoRs.getInt("id");
                log.info(Constants.EMPLOYEE_PERSONAL_INFO + Constants.FOUND);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE_PERSONAL_INFO + Constants.NOT_FOUND);
            }

            insertEmployeePreparedStatement.setString(1, employee.getName());
            insertEmployeePreparedStatement.setString(2, employee.getSurname());
            insertEmployeePreparedStatement.setShort(3, roleId);
            insertEmployeePreparedStatement.setInt(4, personalInfoId);
            int out = insertEmployeePreparedStatement.executeUpdate();

            if (out != 0) {
                log.info(employee + Constants.EMPLOYEE + Constants.SUCCESSFUL_SAVE);
            } else {
                throw new FailedSaveException(employee + Constants.EMPLOYEE + Constants.UNSUCCESSFUL_SAVE);
            }
        } finally {
            if (roleRs != null) {
                roleRs.close();
            }

            if (personalInfoRs != null) {
                personalInfoRs.close();
            }
        }
    }

    @Override
    public void update(long id, Employee employee) throws SQLException {
        String updateEmployeeQuery = Constants.UPDATE_EMPLOYEES;
        String findRoleQuery = Constants.GET_FROM_EMPLOYEE_ROLES_BY_ATTRIBUTES;

        ResultSet roleRs = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateEmployeePreparedStatement = connection.prepareStatement(updateEmployeeQuery);
             PreparedStatement findRolePreparedStatement = connection.prepareStatement(findRoleQuery)) {

            short roleId;
            findRolePreparedStatement.setString(1, employee.getRole().getRole());
            roleRs = findRolePreparedStatement.executeQuery();

            if (roleRs.next()) {
                roleId = roleRs.getShort("id");
                log.info(Constants.EMPLOYEE_ROLE + Constants.FOUND);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE_ROLE + Constants.NOT_FOUND);
            }

            updateEmployeePreparedStatement.setString(1, employee.getName());
            updateEmployeePreparedStatement.setString(2, employee.getSurname());
            updateEmployeePreparedStatement.setShort(3, roleId);
            updateEmployeePreparedStatement.setLong(4, id);

            int out = updateEmployeePreparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.EMPLOYEE + Constants.SUCCESSFUL_UPDATE + Constants.WITH_ID + id);
            } else {
                throw new FailedUpdateException(Constants.EMPLOYEE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        } finally {
            if (roleRs != null) {
                roleRs.close();
            }
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String findPersonalInfoQuery = Constants.GET_FROM_EMPLOYEES_PERSONAL_INFO_BY_ATTRIBUTES;
        ResultSet personalInfoRs = null;

        Employee employee = getById(id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement findPersonalInfoPreparedStatement = connection.prepareStatement(findPersonalInfoQuery)) {

            int personalInfoId;

            findPersonalInfoPreparedStatement.setString(1, employee.getPersonalInfo().getEmail());
            findPersonalInfoPreparedStatement.setString(2, employee.getPersonalInfo().getPhone());
            personalInfoRs = findPersonalInfoPreparedStatement.executeQuery();

            if (personalInfoRs.next()) {
                personalInfoId = personalInfoRs.getInt("id");
                log.info(Constants.EMPLOYEE_PERSONAL_INFO + Constants.FOUND);

                //When personal information is deleted, the employee will also be deleted cascade
                employeePersonalInfoDAO.deleteById(personalInfoId);
                log.info(Constants.EMPLOYEE + Constants.SUCCESSFUL_DELETE + Constants.WITH_ID + id);
            } else {
                throw new FailedDeleteException(Constants.EMPLOYEE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        } finally {
            if (personalInfoRs != null) {
                personalInfoRs.close();
            }
        }
    }

    private <T> T getRecordFromTable(ResultSet resultSet, DAOInterface<T> daoInterface, String attribute) throws SQLException {
        return daoInterface.getById(resultSet.getInt(attribute));
    }

    @Override
    public Map<Employee, List<Project>> getEmployeeByIdWithProjects(int id) throws SQLException {
        String employeeQuery = Constants.GET_EMPLOYEE_BY_ID_WITH_PROJECTS;
        String projectQuery = Constants.GET_BY_ID_FROM_PROJECTS;

        Map<Employee, List<Project>> employeeWithProjects = new HashMap<>();
        List<Project> projects = new ArrayList<>();
        Employee employee = getById(id);
        ResultSet employeeRs = null;
        ResultSet projectRs = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(employeeQuery);
             PreparedStatement preparedStatement2 = connection.prepareStatement(projectQuery)) {

            preparedStatement1.setInt(1, id);
            employeeRs = preparedStatement1.executeQuery();

            while (employeeRs.next()) {
                Project project = new Project();
                preparedStatement2.setInt(1, projectRs.getInt("project_id"));
                projectRs = preparedStatement2.executeQuery();

                while (projectRs.next()) {
                    project.setId(projectRs.getInt("id"));
                    project.setName(projectRs.getString("name"));
                }

                projects.add(project);
            }
        } finally {
            if (employeeRs != null) {
                employeeRs.close();
            }

            if (projectRs != null) {
                projectRs.close();
            }
        }

        employeeWithProjects.put(employee, projects);

        return employeeWithProjects;
    }
}
