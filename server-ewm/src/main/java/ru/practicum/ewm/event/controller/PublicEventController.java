package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.ShortEventDto;
import ru.practicum.ewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@Validated
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;

    @GetMapping("{id}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable @Min(1) Long id,  HttpServletRequest servlet) {
        log.info("GET PublicEventController id=" + id);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id, servlet));
    }


    @GetMapping
    public ResponseEntity<List<ShortEventDto>> getEventsByParams(@RequestParam(required = false) String text,
                                                                 @RequestParam(required = false) List<Long> categories,
                                                                 @RequestParam(required = false) Boolean paid,
                                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                                 @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                                 @RequestParam(required = false) String sort,
                                                                 @Valid @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                 @Valid @Positive @RequestParam(defaultValue = "10") Integer size,
                                                                 HttpServletRequest servlet) {
        log.info("GET PublicEventController by params");
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventsByParamsForPublic(text, categories, paid,
                rangeStart, rangeEnd,
                onlyAvailable, sort,
                from, size, servlet));
    }
}
