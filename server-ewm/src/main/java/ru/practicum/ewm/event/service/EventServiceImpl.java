package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.model.StateAction;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.LocationRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateResultDto;
import ru.practicum.ewm.participationRequest.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.participationRequest.model.RequestStatus;
import ru.practicum.ewm.participationRequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final EventMapper eventMapper;
    private final ParticipationRequestMapper requestMapper;
    private final StatClient statClient;

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, EventDtoNew eventDtoNew) {
        User initiator = getUserFromRepository(userId);
        Category category = getCategoryFromRepository(eventDtoNew.getCategory());
        Location location = locationRepository.save(eventDtoNew.getLocation());
        Event event = eventMapper.toEvent(eventDtoNew);
        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Wrong event date");
        }
        event.setInitiator(initiator);
        event.setLocation(location);
        event.setCategory(category);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }


    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest adminRequest) {
        Event updateEvent = getEventFromRepository(eventId);
        eventMapper.toEvent(adminRequest, updateEvent);
        if (adminRequest.getEventDate() != null) {
            LocalDateTime eventDate = validateDate(adminRequest.getEventDate());
            updateEvent.setEventDate(eventDate);
        }
        if (adminRequest.getCategory() != null) {
            Category category = getCategoryFromRepository(adminRequest.getCategory());
            updateEvent.setCategory(category);
        }
        if (adminRequest.getLocation() != null) {
            locationRepository.save(adminRequest.getLocation());
            updateEvent.setLocation(adminRequest.getLocation());
        }
        if (adminRequest.getStateAction() != null) {
            StateAction stateAction = adminRequest.getStateAction();
            setState(stateAction, updateEvent);
        }
        return eventMapper.toEventFullDto(eventRepository.save(updateEvent));
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest userRequest) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id=%" + userId + "was not found");
        }
        Event updateEvent = getEventFromRepository(eventId);
        if (updateEvent.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event is already published");
        }
        if (userRequest.getEventDate() != null) {
            LocalDateTime eventDate = validateDate(userRequest.getEventDate());
            updateEvent.setEventDate(eventDate);
        }
        eventMapper.toEvent(userRequest, updateEvent);
        if (userRequest.getCategory() != null) {
            Category category = getCategoryFromRepository(userRequest.getCategory());
            updateEvent.setCategory(category);
        }
        if (userRequest.getLocation() != null) {
            locationRepository.save(userRequest.getLocation());
            updateEvent.setLocation(userRequest.getLocation());
        }
        if (userRequest.getStateAction() != null) {
            StateAction stateAction = userRequest.getStateAction();
            setState(stateAction, updateEvent);
        }
        return eventMapper.toEventFullDto(eventRepository.save(updateEvent));
    }

    @Override
    public List<ShortEventDto> getEventsByInitiator(Long initiatorId, Integer from, Integer size) {
        if (userRepository.existsById(initiatorId)) {
            List<Event> events = eventRepository.findAllByInitiatorId(initiatorId, size, from);
            List<ShortEventDto> result = eventMapper.toShortEventDtoList(events);
            return result;
        } else throw new NotFoundException("User with id=%" + initiatorId + "was not found");
    }

    @Override
    public EventFullDto getEventByIdAndInitiatorId(Long initiatorId, Long eventId) {
        if (userRepository.existsById(initiatorId)) {
            return eventMapper.toEventFullDto(eventRepository.findByIdAndInitiatorId(eventId, initiatorId));
        } else throw new NotFoundException("User with id=%" + initiatorId + "was not found");
    }

    @Override
    public List<EventFullDto> getEventByParamsForAdmin(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        List<Event> events = eventRepository.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventMapper.toEventFullDto(events);
    }

    @Override
    public List<ShortEventDto> getEventsByParamsForPublic(String text, List<Long> categories, Boolean paid,
                                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                          Boolean onlyAvailable, String sort, Integer from, Integer size,
                                                          HttpServletRequest servlet) {
        List<Event> events = eventRepository.getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, from, size);
        Map<Long, Integer> eventsParticipantLimit = new HashMap<>();
        events.forEach(event -> eventsParticipantLimit.put(event.getId(), event.getParticipantLimit()));
        events.forEach(event -> statClient.postStat(servlet, "EWM-service"));
        events.forEach(event -> event.setViews(statClient.getViews(event.getId())));
        List<ShortEventDto> eventDtos = eventMapper.toShortEventDtoList(events);
        eventDtos.stream().map(shortEventDto -> statClient.getViews(shortEventDto.getId())).collect(Collectors.toList());
        if (onlyAvailable) {
            eventDtos = eventDtos.stream()
                    .filter(eventShort -> (eventsParticipantLimit.get(eventShort.getId()) == 0 ||
                            eventsParticipantLimit.get(eventShort.getId()) > eventShort.getConfirmedRequests()))
                    .collect(Collectors.toList());
        }
        if (sort != null && sort.equals("VIEWS")) {
            eventDtos.sort(Comparator.comparing(ShortEventDto::getViews));
        } else if (sort != null && sort.equals("EVENT_DATE")) {
            eventDtos.sort(Comparator.comparing(ShortEventDto::getEventDate));
        }
        return eventDtos;
    }

    @Override
    @Transactional
    public EventFullDto getEventById(Long id, HttpServletRequest servlet) {
        Event event = getEventFromRepository(id);
        statClient.postStat(servlet, "EWM-server");
        event.setViews(statClient.getViews(id) + 1);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestsForInitiator(Long userId, Long eventId) {
        if (userRepository.existsById(userId)) {
            Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
            List<ParticipationRequest> requests = requestRepository.findAllByEvent(event);
            return requestMapper.toParticipationRequestDtoList(requests);
        }
        throw new NotFoundException("User with id=%" + userId + "was not found");
    }

    @Override
    public RequestUpdateResultDto approveRequests(Long userId, Long eventId, RequestUpdateDto updateRequests) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        List<ParticipationRequest> requestList = requestRepository.findAllByEvent(event);
        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();
        for (ParticipationRequest participationRequest : requestList) {
            if (participationRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
                throw new ConflictException("the participation request has already been approved");
            }
            if (updateRequests.getRequestIds().contains(participationRequest.getId())
                    && updateRequests.getStatus().equals(RequestStatus.REJECTED)) {
                participationRequest.setStatus(RequestStatus.REJECTED);
                rejected.add(requestMapper.toParticipationRequestDto(participationRequest));
            } else if (updateRequests.getRequestIds().contains(participationRequest.getId())) {
                if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
                    throw new ConflictException("limit exceeded");
                }
                participationRequest.setStatus(RequestStatus.CONFIRMED);
                confirmed.add(requestMapper.toParticipationRequestDto(participationRequest));
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            }
        }
        eventRepository.save(event);
        return RequestUpdateResultDto.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }

    private void setState(StateAction stateAction, Event event) {
        State state = event.getState();
        switch (stateAction) {
            case REJECT_EVENT:
                if (state.equals(State.PUBLISHED)) {
                    throw new ConflictException("State conflict!");
                }
                event.setState(State.CANCELED);
                break;
            case PUBLISH_EVENT:
                if (state.equals(State.PUBLISHED) || state.equals(State.CANCELED)) {
                    throw new ConflictException("State conflict!");
                }
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                break;
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                break;
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
        }
    }


    private Category getCategoryFromRepository(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=%" + id + "was not found"));
    }

    private User getUserFromRepository(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=%" + id + "was not found"));
    }

    private Event getEventFromRepository(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=%" + id + "was not found"));
    }

    private LocalDateTime validateDate(String date) {
        LocalDateTime eventDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ConflictException("Wrong event date");
        }
        return eventDate;
    }
}
