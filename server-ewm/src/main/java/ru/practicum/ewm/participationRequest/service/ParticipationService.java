package ru.practicum.ewm.participationRequest.service;

import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationService {
    ParticipationRequestDto createRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto canceledRequest(Long userId, Long requestId);
}
