package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.stats.ViewStats;
import ru.practicum.ewm.response.ViewStatsDto;

import java.util.List;
import java.util.stream.Collectors;

public class ViewStatsMapper {

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setApp(viewStats.getApp());
        viewStatsDto.setUri(viewStats.getUri());
        viewStatsDto.setHits(viewStats.getHits());
        return viewStatsDto;
    }

    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        ViewStats viewStats = new ViewStats();
        viewStats.setApp(viewStatsDto.getApp());
        viewStats.setUri(viewStatsDto.getUri());
        viewStats.setHits(viewStatsDto.getHits());
        return viewStats;
    }

    public static List<ViewStatsDto> toDtoList(List<ViewStats> viewStats) {
        return viewStats.stream().map(ViewStatsMapper::toViewStatsDto).collect(Collectors.toList());
    }

}
