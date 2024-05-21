package io.aston.serverside.dao;

import io.aston.serverside.dao.interfaces.DAOInterface;
import io.aston.serverside.exception.sql.FailedDeleteException;
import io.aston.serverside.exception.sql.FailedSaveException;
import io.aston.serverside.exception.sql.FailedUpdateException;
import io.aston.serverside.exception.sql.NoSuchRecordException;
import io.aston.serverside.utility.Constants;
import io.aston.serverside.entity.EmployeeRole;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@Data
@Slf4j
public class EmployeeRoleDAO implements DAOInterface<EmployeeRole> {

    private final DataSource dataSource;

    @Override
    public List<EmployeeRole> getAll() throws SQLException {
        String query = Constants.GET_ALL_EMPLOYEE_ROLES;
        List<EmployeeRole> employeeRoles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setId(resultSet.getShort("id"));
                employeeRole.setRole(resultSet.getString("role"));
                employeeRoles.add(employeeRole);
            }
        }

        return employeeRoles;
    }

    @Override
    public EmployeeRole getById(long id) throws SQLException {
        String query = Constants.GET_BY_ID_FROM_EMPLOYEE_ROLES;
        EmployeeRole employeeRole = new EmployeeRole();
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeeRole.setId(resultSet.getShort("id"));
                employeeRole.setRole(resultSet.getString("role"));
                log.info(Constants.EMPLOYEE_ROLE + Constants.FOUND + employeeRole);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE_ROLE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return employeeRole;
    }

    @Override
    public void save(EmployeeRole employeeRole) throws SQLException {
        String query = Constants.INSERT_INTO_EMPLOYEE_ROLES;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employeeRole.getRole());
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(employeeRole.getRole() + Constants.EMPLOYEE_ROLE + Constants.SUCCESSFUL_SAVE);
            } else {
                throw new FailedSaveException(employeeRole.getRole() + Constants.EMPLOYEE_ROLE + Constants.UNSUCCESSFUL_SAVE);
            }
        }
    }

    @Override
    public void update(long id, EmployeeRole employeeRole) throws SQLException {
        String query = Constants.UPDATE_EMPLOYEE_ROLE;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employeeRole.getRole());
            preparedStatement.setLong(2, id);

            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.EMPLOYEE_ROLE + Constants.SUCCESSFUL_UPDATE + Constants.WITH_ID + id);
            } else {
               throw new FailedUpdateException(Constants.EMPLOYEE_ROLE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String query = Constants.DELETE_FROM_EMPLOYEE_ROLES;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.EMPLOYEE_ROLE + Constants.SUCCESSFUL_DELETE + Constants.WITH_ID + id);
            } else {
                throw new FailedDeleteException(Constants.EMPLOYEE_ROLE + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }
}
