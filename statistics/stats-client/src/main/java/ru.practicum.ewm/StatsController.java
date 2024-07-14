package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.EndpointHitDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private static StatsClient statsClient;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam(value = "start")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String start,
                                           @RequestParam(value = "end")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String end,
                                           @RequestParam(value = "uris", required = false) List<String> uris,
                                           @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        ResponseEntity<Object> viewStatsList = statsClient.getStatistics(start, end, uris, unique);
        log.info("Request received GET /stats");
        return viewStatsList;
    }

    @PostMapping(path = "/hit")
    public ResponseEntity<Object> saveHit(@Valid
                                          @RequestBody EndpointHitDto endpointHitDto) {
        ResponseEntity<Object> endpointHitDtoResult = statsClient.postHit(new EndpointHitDto());
        log.info("Request received POST /hit");
        return endpointHitDtoResult;
    }
}
