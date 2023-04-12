package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.EndPointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<ViewStats> viewStats = new ArrayList<>();
        List<EndpointHit> hitsList;
        for (String uri : uris) {
            if (unique) {
                hitsList = statsRepository.findDistinctByUriInAndTimestampBetween(List.of(uri), start, end);
                viewStats.add(new ViewStats("EWM-service", uri, hitsList.size()));
            } else if (!unique || unique == null) {
                hitsList = statsRepository.findByUriInAndTimestampBetween(List.of(uri), start, end);
                viewStats.add(new ViewStats("EWM-service", uri, hitsList.size()));
            }

            viewStats.sort(Comparator.comparing(ViewStats::getHits).reversed());
        }
        return viewStats;
    }
}
