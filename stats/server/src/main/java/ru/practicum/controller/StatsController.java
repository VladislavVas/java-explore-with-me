package ru.practicum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@Validated
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> postHit(@RequestBody EndpointHitDto endpointHitDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statsService.postHit(endpointHitDto));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getViewStats(@RequestParam String start,
                                                        @RequestParam String end,
                                                        @RequestParam(required = false) String[] uris,
                                                        @RequestParam(defaultValue = "false") Boolean unique) {
        LocalDateTime startFromDto = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endFromDto = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return ResponseEntity.status(HttpStatus.OK).body(statsService.getViewStats(startFromDto, endFromDto, uris, unique));

    }
}
