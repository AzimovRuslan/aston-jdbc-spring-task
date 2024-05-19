package io.aston.serverside;

import io.aston.serverside.dao.DAOInterface;
import io.aston.serverside.dao.EmployeeRoleDAO;
import io.aston.serverside.entity.EmployeeRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerSideApplication.class, args);

		DAOInterface<EmployeeRole> employeeRoleDAOInterface = new EmployeeRoleDAO();
		EmployeeRole employeeRole = new EmployeeRole("Developer");
		employeeRoleDAOInterface.save(employeeRole);
	}

}
