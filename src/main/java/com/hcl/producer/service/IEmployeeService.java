package com.hcl.producer.service;

import com.hcl.producer.dto.EmployeeDto;
import com.hcl.producer.models.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee saveEmployee(EmployeeDto employeeDto);
    boolean checkForAckRecieved(String email);
    List<Employee> getAllEmployees();
    Employee deleteEmployee(EmployeeDto employeeDto);
    Employee deleteEmployee(String email);
    Employee deleteEmployee(Integer id);
    Employee updateEmployee(EmployeeDto dto);
}
