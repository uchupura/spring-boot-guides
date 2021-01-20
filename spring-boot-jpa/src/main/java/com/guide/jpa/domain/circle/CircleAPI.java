package com.guide.jpa.domain.circle;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CircleAPI.URL)
public class CircleAPI {
    public static final String URL = "/circles";

    private final CircleApiService circleApiService;

    @GetMapping
    public List<CircleDTO.Response.Circle> getCircles() {
        return circleApiService.getCircles();
    }
    @PostMapping
    public CircleDTO.Response.Create create(@RequestBody CircleDTO.Request.Create body) {
        return circleApiService.create(body);
    }

    @PostMapping("{id}")
    public void join(@RequestBody CircleDTO.Request.Join body) {
        circleApiService.join(body);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        circleApiService.delete(id);
    }
}