package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.EndPointHitMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    private final EndPointHitMapper endPointHitMapper;

    public StatsServiceImpl(StatsRepository statsRepository, EndPointHitMapper endPointHitMapper) {
        this.statsRepository = statsRepository;
        this.endPointHitMapper = endPointHitMapper;
    }

    @Override
    public EndpointHitDto postHit(EndpointHitDto endpointHitDto) {
        return endPointHitMapper.toEndpointHitDto(statsRepository.save(endPointHitMapper.toEndpointHit(endpointHitDto)));
    }

    @Override
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return statsRepository.getHits(start, end, uris, unique);
    }

}
