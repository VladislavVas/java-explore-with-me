package ru.practicum.ewm.category.dto;

import lombok.*;
import ru.practicum.ewm.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotNull(groups = {Create.class}, message = "Field: name. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: name. Error: must not be blank.")
    private String name;

}
