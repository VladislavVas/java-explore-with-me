package ru.practicum.ewm.participationRequest.dto;

import lombok.*;
import ru.practicum.ewm.participationRequest.model.RequestStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private RequestStatus status;
    private String created;
}
