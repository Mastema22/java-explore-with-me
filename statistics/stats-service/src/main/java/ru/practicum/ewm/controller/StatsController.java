package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@NotEmpty
                                       @RequestParam(value = "start")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String start,
                                       @NotEmpty
                                       @RequestParam(value = "end")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String end,
                                       @RequestParam(value = "uris", required = false) List<String> uris,
                                       @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        List<ViewStatsDto> viewStatsList = statsService.getStats(start, end, uris, unique);
        log.info("Statistics collection is completed successfully!");
        return viewStatsList;
    }

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto saveHit(@Valid @RequestParam EndpointHitDto endpointHitDto) {
        EndpointHitDto endpointHitDtoResult = statsService.saveHit(endpointHitDto);
        log.info("The data has been saved successfully!");
        return endpointHitDtoResult;
    }
}
