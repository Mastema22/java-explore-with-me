package ru.practicum.ewm.service;

import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;

import java.util.List;

public interface StatsService {
    List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean unique);

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);
}