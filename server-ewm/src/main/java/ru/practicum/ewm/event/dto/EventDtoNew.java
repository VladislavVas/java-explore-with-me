package ru.practicum.ewm.event.dto;

import lombok.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.event.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoNew {
    @NotNull(groups = {Create.class}, message = "Field: annotation. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: annotation. Error: must not be blank.")
    @Size(min = 20, max = 2000, message = "Field: annotation. maxLength: 2000, minLength: 20.")
    private String annotation;
    private Integer category;
    @NotNull(groups = {Create.class}, message = "Field: description. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: description. Error: must not be blank.")
    @Size(min = 20, max = 7000, message = "Field: description. maxLength: 7000, minLength: 20.")
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotNull(groups = {Create.class}, message = "Field: title. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: title. Error: must not be blank.")
    @Size(min = 3, max = 120, message = "Field: title. maxLength: 120, minLength: 3.")
    private String title;
}
