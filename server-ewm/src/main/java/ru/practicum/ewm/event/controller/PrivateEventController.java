package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.event.dto.EventDtoNew;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.ShortEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateDto;
import ru.practicum.ewm.participationRequest.dto.RequestUpdateResultDto;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping("{userId}/events")
    public ResponseEntity<EventFullDto> postEventByUser(@PathVariable @Min(1) Long userId,
                                                        @Validated(Create.class) @RequestBody EventDtoNew eventDtoNew) {
        log.info("POST PrivateEventController " + eventDtoNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(userId, eventDtoNew));
    }

    @PatchMapping("{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable @Min(1) Long userId,
                                                    @PathVariable @Min(1) Long eventId,
                                                    @RequestBody @Validated(Create.class) UpdateEventUserRequest userRequest) {
        log.info("PATCH PrivateEventController user id=" + userId + " eventId= " + eventId + userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEventByUser(userId, eventId, userRequest));
    }

    @GetMapping("{userId}/events")
    public ResponseEntity<List<ShortEventDto>> getEventsByInitiator(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                                    @RequestParam(defaultValue = "10") @Min(1) Integer size,
                                                                    @PathVariable @Min(1) Long userId) {
        log.info("GET PrivateEventController by user id =" + userId + " by size = " + size + " from " + from);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventsByInitiator(userId, from, size));
    }

    @GetMapping("{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventByIdAndInitiatorId(@PathVariable @Min(1) Long userId,
                                                                   @PathVariable @Min(1) Long eventId) {
        log.info("GET PrivateEventController user id= " + userId + " event id " + eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventByIdAndInitiatorId(userId, eventId));
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getEventRequestsForInitiator(@PathVariable @Min(1) Long userId,
                                                                                      @PathVariable @Min(1) Long eventId) {
        log.info("GET PrivateEventController requests" + userId + " event id " + eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventRequestsForInitiator(userId, eventId));
    }

    @PatchMapping("{userId}/events/{eventId}/requests")
    public ResponseEntity<RequestUpdateResultDto> approveRequests(@PathVariable @Min(1) Long userId,
                                                                  @PathVariable @Min(1) Long eventId,
                                                                  @Validated(Create.class) @RequestBody RequestUpdateDto requests) {
        log.info("GET PrivateEventController approve requsts" + userId + " event id " + eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.approveRequests(userId, eventId, requests));
    }
}
