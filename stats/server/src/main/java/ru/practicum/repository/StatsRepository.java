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


    @Query("select new ru.practicum.dto.ViewStats(hit.app, hit.uri, count (hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between ?1 and ?2 " +
            "and hit.uri in (?3) " +
            "group by hit.app, hit.uri " +
            "order by (hit.ip) desc ")
    List<ViewStats> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query("select new ru.practicum.dto.ViewStats(hit.app, hit.uri, count (hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between ?1 AND ?2 " +
            "group by hit.app, hit.uri " +
            "order by count (hit.ip) desc ")
    List<ViewStats> getAllStats(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.ViewStats(hit.app, hit.uri, count (distinct hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between ?1 and ?2 " +
            "and hit.uri IN (?3) " +
            "group by hit.app, hit.uri " +
            "order by count (distinct hit.ip) desc ")
    List<ViewStats> getStatsByUrisDistinctIp(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query("select new ru.practicum.dto.ViewStats(hit.app, hit.uri, count (distinct hit.ip)) " +
            "from EndpointHit as hit " +
            "where hit.timestamp between ?1 and ?2 " +
            "group by hit.app, hit.uri " +
            "order by count (distinct hit.ip) desc")
    List<ViewStats> getAllStatsDistinctIp(LocalDateTime start, LocalDateTime end);


}
