package ru.practicum.ewm.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@Builder
public class Exception {

    private String message;
    private String reason;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private String timestamp;
}
