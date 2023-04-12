package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class StatClient extends BaseClient {

    @Autowired
    public StatClient(@Value("${STATS_SERVER_URL}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    @Transactional
    public void postStat(HttpServletRequest servlet, String app) {
        EndpointHitDto hit = EndpointHitDto
                .builder()
                .app(app)
                .ip(servlet.getRemoteAddr())
                .uri(servlet.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        post("/hit", hit);
    }

    @Transactional
    public Long getViews(Long eventId) {
        String url = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
        Map<String, Object> parameters = Map.of(
                "start", LocalDateTime.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                "end", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                "uris", "/events/" + eventId,
                "unique", "false"
        );
        ResponseEntity<List<ViewStats>> response = get(url, parameters);
        List<ViewStats> viewStatsList = response.hasBody() ? response.getBody() : null;
        return viewStatsList != null && !viewStatsList.isEmpty() ? viewStatsList.get(0).getHits() : 0L;
    }
}
