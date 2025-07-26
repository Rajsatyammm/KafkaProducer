package com.hcl.producer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto implements Serializable {
    @Email(message = "Email not valid")
    private String email;
    @Size(min = 3, max = 100, message = "Name should be min 3 and max 100 character")
    private String name;
    @Positive(message = "salary can not be negative")
    private Double salary;
}
