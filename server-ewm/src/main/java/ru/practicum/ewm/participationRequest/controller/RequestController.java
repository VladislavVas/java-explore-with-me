package ru.practicum.ewm.participationRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.participationRequest.service.ParticipationService;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RequestController {
    private final ParticipationService participationService;

    @PostMapping("{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable @Min(1) long userId,
                                                                 @RequestParam @Min(1) long eventId) {
        log.info("POST RequestController user id=" + userId+ " event id=" + eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(participationService.createRequest(userId, eventId));
    }

    @GetMapping("{userId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequests(@PathVariable @Min(1) long userId) {
        log.info("GET RequestController user id=" + userId);
        return ResponseEntity.status(HttpStatus.OK).body(participationService.getUserRequests(userId));
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable @Min(1) long userId,
                                                                 @PathVariable @Min(1) long requestId) {
        log.info("PATCH RequestController user id=" + userId+ " request id=" + requestId);
        return ResponseEntity.status(HttpStatus.OK).body(participationService.canceledRequest(userId, requestId));
    }
}
