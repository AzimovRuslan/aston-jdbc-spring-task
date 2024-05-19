package io.aston.serverside.utility;

public class Constants {
    public static final String URL = "jdbc:postgresql://localhost:5432/aston_jdbc_spring_task_db";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "admin";

    public static final  String GET_ALL_EMPLOYEE_ROLES = "select id, role from employee_roles";
    public static final String INSERT_INTO_EMPLOYEE_ROLES = "insert into employee_roles (role) values (?)";
    public static final String UPDATE_EMPLOYEE_ROLE = "update employee_roles set role=? where id=?";
    public static final String GET_BY_ID_FROM_EMPLOYEE_ROLES = "select * from employee_roles where id=?";
    public static final String DELETE_FROM_EMPLOYEE_ROLES = "delete from employee_roles where id=?";

    public static final String REQUEST_FAILED = " -> request failed";
    public static final String EMPLOYEE_ROLE = " employee role ";
    public static final String SUCCESSFUL_SAVE = " successfully saved ";
    public static final String SUCCESSFUL_UPDATE = " successfully updated ";
    public static final String SUCCESSFUL_DELETE = " successfully deleted ";
    public static final String UNSUCCESSFUL_SAVE = " unsuccessfully saved ";
    public static final String UNSUCCESSFUL_UPDATE = " unsuccessfully updated ";
    public static final String UNSUCCESSFUL_DELETE = " unsuccessfully deleted ";
    public static final String WITH_ID = " with id = ";
    public static final String FOUND = " found ";
    public static final String NOT_FOUND = " not found ";



}
