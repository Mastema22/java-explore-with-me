package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.EndpointHitDto;
import ru.practicum.ewm.response.ViewStatsDto;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam(value = "start") LocalDateTime start,
                                       @RequestParam(value = "end") LocalDateTime end,
                                       @RequestParam(value = "uris", required = false) List<String> uris,
                                       @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        List<ViewStatsDto> viewStatsList = statsService.getStats(start, end, uris, unique);
        log.info("Statistics collection is completed successfully!");
        return viewStatsList;
    }

    @PostMapping(value = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto saveHit(@Valid
                                  @RequestBody EndpointHitDto endpointHitDto) {
        EndpointHitDto endpointHitDtoResult = statsService.saveHit(endpointHitDto);
        log.info("The data has been saved successfully!");
        return endpointHitDtoResult;
    }
}