package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHitDto postHit(EndpointHitDto endpointHitDto);

    List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
