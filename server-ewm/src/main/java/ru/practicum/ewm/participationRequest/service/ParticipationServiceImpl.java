package ru.practicum.ewm.participationRequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.participationRequest.model.RequestStatus;
import ru.practicum.ewm.participationRequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestMapper requestMapper;

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requester = getUserFromRepository(userId);
        Event event = getEventFromRepository(eventId);
        validate(event, requester);
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setEvent(event);
        participationRequest.setRequester(requester);
        if (checkLimit(event) && !event.getRequestModeration()) {
            participationRequest.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        return requestMapper.toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        User user = getUserFromRepository(userId);
        List<ParticipationRequest> requests = participationRequestRepository.findAllByRequester(user);
        return requestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto canceledRequest(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id=%" + userId + "was not found");
        }
        ParticipationRequest request = getRequestFromRepository(requestId);
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.toParticipationRequestDto(participationRequestRepository.save(request));
    }

    private ParticipationRequest getRequestFromRepository(long id) {
        return participationRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Request with id=%" + id + "was not found"));
    }

    private User getUserFromRepository(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=%" + id + "was not found"));
    }

    private Event getEventFromRepository(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=%" + id + "was not found"));
    }

    private void validate(Event event, User requester) {
        if (event.getInitiator().getId() == requester.getId()) {
            throw new ConflictException("The initiator of the event cannot apply for participation");
        }
        if (!event.getState().equals(State.PUBLISHED) || event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("The event is not available for participation");
        }
    }

    private boolean checkLimit(Event event) {
        int limit = event.getParticipantLimit();
        int confirmedRequests = event.getConfirmedRequests();
        if (limit == 0 || limit > confirmedRequests) {
            return true;
        } else {
            return false;
        }
    }

}

