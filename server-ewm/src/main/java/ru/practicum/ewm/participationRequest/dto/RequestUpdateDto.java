package ru.practicum.ewm.participationRequest.dto;

import lombok.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.participationRequest.model.RequestStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {
    @NotNull(groups = {Create.class}, message = "Field: requestIds. Error: must not be null")
    List<Long> requestIds;
    @NotNull(groups = {Create.class}, message = "Field: status. Error: must not be null")
    RequestStatus status;
}
