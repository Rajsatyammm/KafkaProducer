package com.hcl.producer.consumers;

import com.hcl.producer.constants.KafkaTopic;
import com.hcl.producer.dto.EmployeeDto;
import com.hcl.producer.models.Employee;
import com.hcl.producer.repositories.IEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private final IEmployeeRepository employeeRepository;

    public KafkaConsumer(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @KafkaListener(topics = KafkaTopic.SEND_ACKNOWLEDGEMENT)
    public void consume(ConsumerRecord<String, EmployeeDto> record) {
        Employee employee = employeeRepository.findByEmail(record.value().getEmail()).orElse(null);
        if(employee != null) {
            employee.setAckReceived(true);
            employeeRepository.save(employee);
            log.info("Received Ack :: {}", record.value().toString());
            log.info("Transaction Completed :: ");
        }
    }

}
