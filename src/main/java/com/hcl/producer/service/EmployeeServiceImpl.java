package com.hcl.producer.service;

import com.hcl.producer.constants.AppConstants;
import com.hcl.producer.constants.KafkaTopic;
import com.hcl.producer.dto.EmployeeDto;
import com.hcl.producer.exceptions.UserAlreadyExists;
import com.hcl.producer.exceptions.UserNotFoundException;
import com.hcl.producer.models.Employee;
import com.hcl.producer.producer.IKafkaProducer;
import com.hcl.producer.repositories.IEmployeeRepository;
import com.hcl.producer.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    private final IKafkaProducer kafkaProducer;

    @Value("${producer.produce.without-save:false}")
    private boolean produceWithoutSaving;

    public EmployeeServiceImpl(IEmployeeRepository employeeRepository, IKafkaProducer kafkaProducer) {
        this.employeeRepository = employeeRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Employee saveEmployee(EmployeeDto employeeDto) {
        log.info("saveEmployee api called with email :: {}", employeeDto.getEmail());
        Employee savedEmployee = null;
        Employee employee = employeeRepository.findByEmail(employeeDto.getEmail()).orElse(null);
        if (employee != null) {
            log.error("User already exists with email :: {}", employeeDto.getEmail());
            throw new UserAlreadyExists("User already exists with email :: " + employeeDto.getEmail());
        }
        if (produceWithoutSaving) {
            log.info("Proceeding without saving the data :: {}", employeeDto.getEmail());
//            System.out.println("Proceeding without saving the data");
            log.info("Producing data to kafka to topic :: {}", KafkaTopic.PRODUCE_MESSAGE_TOPIC);
            kafkaProducer.produceDataToKafka(KafkaTopic.PRODUCE_MESSAGE_TOPIC, employeeDto);
        } else {
            employee = Mapper.toModel(employeeDto);
            employee.setProduced(true);
            savedEmployee = employeeRepository.save(employee);
            log.info("Proceeding after saving the data :: {}", savedEmployee.getEmail());
//            System.out.println("Proceeding after saving the data");
            log.info("Producing to kafka topic :: {}", KafkaTopic.PRODUCE_MESSAGE_TOPIC);
            kafkaProducer.produceDataToKafka(KafkaTopic.PRODUCE_MESSAGE_TOPIC, Mapper.toDto(savedEmployee));
        }
        return savedEmployee;
    }

    @Override
    public boolean checkForAckRecieved(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Not Found"));
        return employee.isProduced() && employee.isConsumed() && employee.isAckReceived();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee deleteEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findByEmail(employeeDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
        employeeRepository.delete(Mapper.toModel(employeeDto));
        return employee;
    }

    @Override
    public Employee deleteEmployee(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public Employee deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(EmployeeDto dto) {
        Employee employee = employeeRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());
        return employeeRepository.save(employee);
    }
}
