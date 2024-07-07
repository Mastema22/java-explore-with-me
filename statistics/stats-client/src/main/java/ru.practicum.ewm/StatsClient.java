package ru.practicum.ewm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import ru.practicum.ewm.request.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-service.url}") String serviceUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> postHit(Long userId, EndpointHitDto endpointHitDto) {
        return post("/hit", userId, endpointHitDto);
    }

    public ResponseEntity<Object> getStatistics(Long userId, LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        Map<String, Object> params = Map.of(
                "start", start,
                "end", end,
                "unique", unique,
                "uris", uris
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", userId, params);
    }
}
