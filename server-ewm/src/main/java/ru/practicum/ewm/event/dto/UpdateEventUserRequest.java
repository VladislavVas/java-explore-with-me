package ru.practicum.ewm.event.dto;

import lombok.*;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.StateAction;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000, message = "Field: annotation. maxLength: 2000, minLength: 20.")
    private String annotation;

    @Min(1)
    private Long category;

    @Size(min = 20, max = 7000, message = "Field: description. maxLength: 7000, minLength: 20.")
    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    @Min(0)
    private Integer participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    @Size(min = 3, max = 120, message = "Field: title. maxLength: 120, minLength: 3.")
    private String title;

}

