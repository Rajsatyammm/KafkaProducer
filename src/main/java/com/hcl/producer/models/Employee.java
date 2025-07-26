package com.hcl.producer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Email
    @Column(unique = true)
    private String email;
    private String name;
    private Double salary;
    private boolean isProduced;
    private boolean isConsumed;
    private boolean isAckReceived;
}
