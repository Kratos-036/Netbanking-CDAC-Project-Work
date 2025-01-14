package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.dto.BankEmployeeDTO;
import com.app.dto.customer.CustomerDetailsDTO;
import com.app.entities.BankEmployeeDetails;

public interface EmployeeService {
	Optional<BankEmployeeDetails> getEmpDetails(Long empId);
	
	BankEmployeeDetails addEmpDetails(BankEmployeeDTO empDetails);

	// Method to delete an employee by their ID
	String deleteEmp(Long empId);

	List<BankEmployeeDetails> getAllEmployeeDetails();

	Optional<BankEmployeeDetails> getEmployeeDetailsByUsername(String username);

	Optional<BankEmployeeDetails> getEmployeeDetailsByUsernameIfPresent(String username);

	void updateLastLogin(Long employeeId);

	String getTotalNumberOfEmployees();

}
