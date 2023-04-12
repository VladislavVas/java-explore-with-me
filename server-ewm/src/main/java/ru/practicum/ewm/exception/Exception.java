package ru.practicum.ewm.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class Exception {

    private String message;

    private String reason;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private String timestamp;

}
