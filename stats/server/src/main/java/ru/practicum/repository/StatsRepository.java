package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    List<EndpointHit> findByUriInAndTimestampBetween(List<String> uri, LocalDateTime start, LocalDateTime end);

    List<EndpointHit> findDistinctByUriInAndTimestampBetween(List<String> uri, LocalDateTime start, LocalDateTime end);
}
