package io.aston.serverside.dao;

import io.aston.serverside.entity.EmployeePersonalInfo;
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
public class EmployeePersonalInfoDAO implements DAOInterface<EmployeePersonalInfo> {

    private static DataSource dataSource;

    @Override
    public List<EmployeePersonalInfo> getAll() throws SQLException {
        String query = Constants.GET_ALL_EMPLOYEES_PERSONAL_INFO;
        List<EmployeePersonalInfo> employeePersonalInfos = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                EmployeePersonalInfo employeePersonalInfo = new EmployeePersonalInfo();
                employeePersonalInfo.setId(resultSet.getInt("id"));
                employeePersonalInfo.setEmail(resultSet.getString("email"));
                employeePersonalInfo.setEmail(resultSet.getString("phone"));
                employeePersonalInfos.add(employeePersonalInfo);
            }
        }

        return employeePersonalInfos;
    }

    @Override
    public EmployeePersonalInfo getById(long id) throws SQLException {
        String query = Constants.GET_BY_ID_FROM_EMPLOYEES_PERSONAL_INFO;
        EmployeePersonalInfo employeePersonalInfo = new EmployeePersonalInfo();
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeePersonalInfo.setId(resultSet.getInt("id"));
                employeePersonalInfo.setEmail(resultSet.getString("email"));
                employeePersonalInfo.setPhone(resultSet.getString("phone"));
                log.info(Constants.EMPLOYEE_PERSONAL_INFO + Constants.FOUND + employeePersonalInfo);
            } else {
                throw new NoSuchRecordException(Constants.EMPLOYEE_PERSONAL_INFO + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return employeePersonalInfo;
    }

    @Override
    public void save(EmployeePersonalInfo employeePersonalInfo) throws SQLException {
        String query = Constants.INSERT_INTO_EMPLOYEES_PERSONAL_INFO;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employeePersonalInfo.getEmail());
            preparedStatement.setString(2, employeePersonalInfo.getPhone());
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(employeePersonalInfo + Constants.EMPLOYEE_PERSONAL_INFO + Constants.SUCCESSFUL_SAVE);
            } else {
                throw new FailedSaveException(employeePersonalInfo + Constants.EMPLOYEE_PERSONAL_INFO + Constants.UNSUCCESSFUL_SAVE);
            }
        }
    }

    @Override
    public void update(long id, EmployeePersonalInfo employeePersonalInfo) throws SQLException {
        String query = Constants.UPDATE_EMPLOYEES_PERSONAL_INFO;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employeePersonalInfo.getEmail());
            preparedStatement.setString(2, employeePersonalInfo.getPhone());
            preparedStatement.setLong(3, id);

            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.EMPLOYEE_PERSONAL_INFO + Constants.SUCCESSFUL_UPDATE + Constants.WITH_ID + id);
            } else {
                throw new FailedUpdateException(Constants.EMPLOYEE_PERSONAL_INFO + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String query = Constants.DELETE_FROM_EMPLOYEES_PERSONAL_INFO;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            int out = preparedStatement.executeUpdate();

            if (out != 0) {
                log.info(Constants.EMPLOYEE_PERSONAL_INFO + Constants.SUCCESSFUL_DELETE + Constants.WITH_ID + id);
            } else {
                throw new FailedDeleteException(Constants.EMPLOYEE_PERSONAL_INFO + Constants.NOT_FOUND + Constants.WITH_ID + id);
            }
        }
    }
}
