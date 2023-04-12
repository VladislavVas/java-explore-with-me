package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateResultDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {


    EventFullDto createEvent(Long userId, EventDtoNew eventDtoNew);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest adminRequest);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest userRequest);

    List<ShortEventDto> getEventsByInitiator(Long userId, Integer from, Integer size);

    EventFullDto getEventByIdAndInitiatorId(Long userId, Long eventId);

    List<EventFullDto> getEventByParamsForAdmin(List<Long> users,
                                                List<State> states,
                                                List<Long> categories,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Integer from, Integer size);

    List<ShortEventDto> getEventsByParamsForPublic(String text,
                                                   List<Long> categories,
                                                   Boolean paid,
                                                   LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable,
                                                   String sort,
                                                   Integer from, Integer size,
                                                   HttpServletRequest servletRequest);

    EventFullDto getEventById(Long id, HttpServletRequest servlet);

    List<ParticipationRequestDto> getEventRequestsForInitiator(Long userId, Long eventId);

    RequestUpdateResultDto approveRequests(Long userId, Long eventId, RequestUpdateDto requests);
}
