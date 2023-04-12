package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@Validated
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("{eventId}")
    public ResponseEntity<EventFullDto> updateEventByAdmin(@PathVariable @Min(1) Long eventId,
                                                           @Valid @RequestBody UpdateEventAdminRequest adminRequest) {
        log.info("PATCH AdminEventController id=" + eventId + adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEventByAdmin(eventId, adminRequest));
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEventsByParams(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Valid @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET AdminEventController by params");
        return ResponseEntity.status(HttpStatus.OK).body(
                eventService.getEventByParamsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size));
    }
}
