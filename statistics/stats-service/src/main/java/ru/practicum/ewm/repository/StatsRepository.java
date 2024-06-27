package ru.practicum.ewm.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.ewm.ViewStatsDto(s.app, s.uri, COUNT (s.ip))" +
            "FROM endpoint_hits AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT (s.ip) DESC")
    List<ViewStatsDto> findUniqueViewStatsDto(LocalDateTime start, LocalDateTime end, List<String> uris, PageRequest pageable);

    @Query("SELECT new ru.practicum.ewm.ViewStatsDto(s.app, s.uri, COUNT (s.ip))" +
            "FROM endpoint_hits AS s " +
            "WHERE s.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT (s.ip) DESC")
    List<ViewStatsDto> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, PageRequest pageable);
}