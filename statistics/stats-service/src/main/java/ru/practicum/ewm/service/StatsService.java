package ru.practicum.ewm.service;

import ru.practicum.ewm.request.EndpointHitDto;
import ru.practicum.ewm.response.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);
}