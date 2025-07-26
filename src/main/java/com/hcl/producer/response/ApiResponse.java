package com.hcl.producer.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse {
//    @Builder.Default
//    private LocalDateTime timeStamp = LocalDateTime.now();
    private Boolean success;
    private HttpStatus status;
    private String message;
    private Object data;
}
