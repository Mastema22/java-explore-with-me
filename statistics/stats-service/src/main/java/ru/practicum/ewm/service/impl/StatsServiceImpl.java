package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exceptions.DateNotValidationException;
import ru.practicum.ewm.mapper.ViewStatsMapper;
import ru.practicum.ewm.request.EndpointHitDto;
import ru.practicum.ewm.response.ViewStatsDto;
import ru.practicum.ewm.mapper.EndpointHitMapper;
import ru.practicum.ewm.repository.StatsRepository;
import ru.practicum.ewm.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        PageRequest pageable = PageRequest.of(0, 10);
        List<ViewStatsDto> viewStatsDtoList;
        validateParam(start,end);
        if (unique) {
            viewStatsDtoList = ViewStatsMapper.toDtoList(repository.findUniqueViewStats(start, end, uris, pageable));
        } else {
            viewStatsDtoList = ViewStatsMapper.toDtoList(repository.findViewStats(start, end, uris, pageable));
        }
        log.info("Statistics collection completed!");
        return viewStatsDtoList;
    }

    @Transactional
    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        log.info("Save a new statistic entry!");
        return EndpointHitMapper.toEndpointHitDto(repository.save(EndpointHitMapper.toEndpointHit(endpointHitDto)));
    }

    private void validateParam(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isEqual(null) || endDate.isEqual(null)) {
            throw new DateNotValidationException("Start date or end date cannot be null!");
        }

        if (startDate.isAfter(endDate)) {
            throw new DateNotValidationException("Start date is after end date - checked!");
        }
    }
}
