package ru.practicum.ewm.compilation.dto;


import lombok.*;
import ru.practicum.ewm.Create;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationNewDto {

    private List<Long> events;

    private Boolean pinned;

    @NotNull(groups = {Create.class}, message = "Field: title. Error: must not be null")
    private String title;

}
