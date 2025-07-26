package com.hcl.producer.producer;

import com.hcl.producer.dto.EmployeeDto;

public interface IKafkaProducer {
    void produceDataToKafka(String topic, EmployeeDto employeeDto);
}
