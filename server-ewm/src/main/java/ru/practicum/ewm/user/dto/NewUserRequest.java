package ru.practicum.ewm.user.dto;

import lombok.*;
import ru.practicum.ewm.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    @NotNull(groups = {Create.class}, message = "Field: name. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: name. Error: must not be blank.")
    private String name;
    @NotNull(groups = {Create.class}, message = "Field: email. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: email. Error: must not be blank.")
    @Email(groups = {Create.class}, message = "Field: email. Error: invalid format")
    private String email;
}
