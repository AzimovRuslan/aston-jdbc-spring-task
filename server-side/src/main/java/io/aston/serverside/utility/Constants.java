package io.aston.serverside.utility;

public class Constants {
    public static final String URL = "jdbc:postgresql://localhost:5432/aston_jdbc_spring_task_db";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "admin";

    public static final String GET_ALL_EMPLOYEE_ROLES = "select * from employee_roles";
    public static final String GET_ALL_EMPLOYEES_PERSONAL_INFO = "select * from employees_personal_info";
    public static final String GET_ALL_EMPLOYEES = "select * from employees";
    public static final String GET_ALL_PROJECTS = "select * from projects";
    public static final String GET_ALL_PROJECTS_WITH_WORKERS = "select projects.id as project_id, employees.id as employee_id " +
            "from projects join projects_employees " +
            "on projects.id = projects_employees.project_id " +
            "join employees " +
            "on projects_employees.employee_id = employees.id";

    public static final String GET_PROJECT_BY_ID_WITH_WORKERS = "select projects.id as project_id, employees.id as employee_id " +
            "from projects join projects_employees " +
            "on projects.id = projects_employees.project_id " +
            "join employees " +
            "on projects_employees.employee_id = employees.id " +
            "where projects.id=?";

    public static final String GET_EMPLOYEE_BY_ID_WITH_PROJECTS = "select projects.id as project_id, employees.id as employee_id " +
            "from projects join projects_employees " +
            "on projects.id = projects_employees.project_id " +
            "join employees " +
            "on projects_employees.employee_id = employees.id " +
            "where employees.id=?";

    public static final String INSERT_INTO_EMPLOYEE_ROLES = "insert into employee_roles (role) values (?)";
    public static final String INSERT_INTO_EMPLOYEES_PERSONAL_INFO = "insert into employees_personal_info (email, phone) values (?, ?)";
    public static final String INSERT_INTO_EMPLOYEES = "insert into employees (name, surname, role_id, personal_info_id) values (?, ?, ?, ?)";
    public static final String INSERT_INTO_PROJECTS = "insert into projects (name) values (?)";
    public static final String ADD_EMPLOYEE_TO_PROJECT = "insert into projects_employees (project_id, employee_id) values (?, ?)";


    public static final String UPDATE_EMPLOYEE_ROLE = "update employee_roles set role=? where id=?";
    public static final String UPDATE_EMPLOYEES_PERSONAL_INFO = "update employees_personal_info set email=?, phone=? where id=?";
    public static final String UPDATE_EMPLOYEES = "update employees set name=?, surname=?, role_id=? where id=?";
    public static final String UPDATE_PROJECTS = "update projects set name=? where id=?";

    public static final String GET_BY_ID_FROM_EMPLOYEE_ROLES = "select * from employee_roles where id=?";
    public static final String GET_BY_ID_FROM_EMPLOYEES_PERSONAL_INFO = "select * from employees_personal_info where id=?";
    public static final String GET_BY_ID_FROM_EMPLOYEES = "select * from employees where id=?";
    public static final String GET_BY_ID_FROM_PROJECTS = "select * from projects where id=?";

    public static final String DELETE_FROM_EMPLOYEE_ROLES = "delete from employee_roles where id=?";
    public static final String DELETE_FROM_EMPLOYEES_PERSONAL_INFO = "delete from employees_personal_info where id=?";
    public static final String DELETE_FROM_EMPLOYEES = "delete from employees where id=?";
    public static final String DELETE_FROM_PROJECTS = "delete from projects where id=?";



    public static final String GET_FROM_EMPLOYEE_ROLES_BY_ATTRIBUTES = "select * from employee_roles where role=?";
    public static final String GET_FROM_EMPLOYEES_PERSONAL_INFO_BY_ATTRIBUTES = "select * from employees_personal_info where email=? and phone=?";

    public static final String REQUEST_FAILED = " -> request failed";
    public static final String EMPLOYEE_ROLE = " employee role ";
    public static final String EMPLOYEE = " employee ";
    public static final String PROJECT = " project ";


    public static final String EMPLOYEE_PERSONAL_INFO = " employee personal info ";
    public static final String SUCCESSFUL_SAVE = " successfully saved ";
    public static final String SUCCESSFUL_ADDED = " successfully added to ";
    public static final String UNSUCCESSFUL_ADDED = " unsuccessfully added to ";
    public static final String SUCCESSFUL_UPDATE = " successfully updated ";
    public static final String SUCCESSFUL_DELETE = " successfully deleted ";
    public static final String UNSUCCESSFUL_SAVE = " unsuccessfully saved ";
    public static final String UNSUCCESSFUL_UPDATE = " unsuccessfully updated ";
    public static final String UNSUCCESSFUL_DELETE = " unsuccessfully deleted ";
    public static final String WITH_ID = " with id = ";
    public static final String FOUND = " found ";
    public static final String NOT_FOUND = " not found ";


}
