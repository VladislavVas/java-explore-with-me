package ru.practicum.repository;

import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;


public interface HitsRepository {

    List<ViewStats> getHits(LocalDateTime start,
                            LocalDateTime end,
                            List<String> uris,
                            boolean unique);

}
