package com.hcl.producer.controllers;

import com.hcl.producer.constants.AppConstants;
import com.hcl.producer.dto.EmployeeDto;
import com.hcl.producer.models.Employee;
import com.hcl.producer.response.ApiResponse;
import com.hcl.producer.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/producer/employee")
public class FrontController {

    private final IEmployeeService employeeService;

    public FrontController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveData(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("saveData Api called with email :: {}", employeeDto.getEmail());
        Employee employee = employeeService.saveEmployee(employeeDto);
        log.info("returning success response for email :: {}", employee.getEmail());
        ApiResponse response = ApiResponse.builder()
                .data(employee)
                .message(AppConstants.SUCCESS)
                .status(HttpStatus.CREATED)
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/check/{email}")
    public ResponseEntity<ApiResponse> checkForAckReceived(@PathVariable String email) {
        log.info("checking for ack received for email :: {}", email);
//        System.out.println("email ::" + email);
        boolean status = employeeService.checkForAckRecieved(email);
        log.info("Ack received :: {}", status);
        ApiResponse response = ApiResponse.builder()
                .success(status)
                .status(HttpStatus.OK)
                .message(status ? "Ack received" : "something wrong occurred")
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllEmployees() {
        log.info("getAllEmployees called");
        var employees = employeeService.getAllEmployees();
        ApiResponse response = ApiResponse.builder()
                .data(employees)
                .message("success")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteEmployee(@RequestParam String email) {
        log.info("deleteEmployee called");
        Employee employee = employeeService.deleteEmployee(email);
        ApiResponse response = ApiResponse.builder()
                .data(employee.getEmail())
                .status(HttpStatus.OK)
                .success(true)
                .message("success")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeById(@PathVariable Integer id) {
        log.info("deleteEmployeeById called");
        Employee employee = employeeService.deleteEmployee(id);
        ApiResponse response = ApiResponse.builder()
                .data(employee.getEmail())
                .status(HttpStatus.OK)
                .success(true)
                .message("success")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@RequestBody EmployeeDto dto) {
        log.info("called updateEmployee");
        Employee employee = employeeService.updateEmployee(dto);
        ApiResponse response = ApiResponse.builder()
                .data(employee)
                .status(HttpStatus.OK)
                .success(true)
                .message("success")
                .build();
        return ResponseEntity.ok(response);
    }
}
