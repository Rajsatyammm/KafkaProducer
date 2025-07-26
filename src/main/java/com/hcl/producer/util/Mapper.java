package com.hcl.producer.util;

import com.hcl.producer.dto.EmployeeDto;
import com.hcl.producer.models.Employee;
import org.springframework.stereotype.Component;

public class Mapper {
    public static Employee toModel(EmployeeDto employeeDto) {
        return Employee.builder()
                .email(employeeDto.getEmail())
                .name(employeeDto.getName())
                .salary(employeeDto.getSalary())
                .build();
    }

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .email(employee.getEmail())
                .name(employee.getName())
                .salary(employee.getSalary())
                .build();
    }
}
