package ru.practicum.ewm.participationRequest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationRequestMapper {

    List<ParticipationRequestDto> toParticipationRequestDtoList(List<ParticipationRequest> list);

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
//    @Mapping(source = "requestId", target = "id")
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest toParticipationRequest);
}
