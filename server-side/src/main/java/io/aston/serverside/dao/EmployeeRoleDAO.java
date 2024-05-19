package io.aston.serverside.dao;

import io.aston.serverside.aspect.db.DbConnectorSingleton;
import io.aston.serverside.entity.EmployeeRole;
import lombok.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeRoleDAO implements DAOInterface<EmployeeRole> {
    @Override
    public List<EmployeeRole> getAll() {
        String query = "select id, role from employee_roles";
        List<EmployeeRole> employeeRoles = new ArrayList<>();

        try (Connection connection = DbConnectorSingleton.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setId(resultSet.getShort("id"));
                employeeRole.setRole(resultSet.getString("role"));
                employeeRoles.add(employeeRole);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeRoles;
    }

    @Override
    public EmployeeRole getById() {
        return null;
    }

    @Override
    public void save(EmployeeRole employeeRole) {
        String query = "insert into employee_roles (role) values (?)";

        try(Connection connection = DbConnectorSingleton.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employeeRole.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(long id, EmployeeRole employeeRole) {

    }

    @Override
    public void delete(long id) {

    }
}
