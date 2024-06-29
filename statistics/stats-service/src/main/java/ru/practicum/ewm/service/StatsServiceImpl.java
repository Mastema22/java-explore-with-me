package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.mapper.ViewStatsMapper;
import ru.practicum.ewm.request.EndpointHitDto;
import ru.practicum.ewm.response.ViewStatsDto;
import ru.practicum.ewm.mapper.EndpointHitMapper;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        PageRequest pageable = PageRequest.of(0, 10);
        if (unique) {
            return ViewStatsMapper.toDtoList(repository.findUniqueViewStats(start, end, uris, pageable));
        } else {
            return ViewStatsMapper.toDtoList(repository.findViewStats(start, end, uris, pageable));
        }
    }

    @Transactional
    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        return EndpointHitMapper.toEndpointHitDto(repository.save(EndpointHitMapper.toEndpointHit(endpointHitDto)));
    }
}
