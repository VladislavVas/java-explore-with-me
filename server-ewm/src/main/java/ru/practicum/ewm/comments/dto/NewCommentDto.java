package ru.practicum.ewm.comments.dto;

import lombok.*;
import ru.practicum.ewm.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {

    @NotNull(groups = {Create.class}, message = "Field: userId. Error: must not be null")
    private Long userId;

    @NotNull(groups = {Create.class}, message = "Field: eventId. Error: must not be null")
    private Long eventId;

    @NotNull(groups = {Create.class}, message = "Field: text. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: text. Error: must not be blank.")
    @Size(min = 2, max = 2000, message = "Field: text. maxLength: 2000, minLength: 2.")
    private String text;

}
