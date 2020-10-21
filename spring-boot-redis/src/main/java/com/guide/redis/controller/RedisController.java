package com.guide.redis.controller;

import com.guide.redis.entity.Token;
import com.guide.redis.repository.TokenReposiroty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private final TokenReposiroty reposiroty;

    @PostMapping
    public void create(@RequestBody Token token) {

        reposiroty.save(token);
    }

    @GetMapping("{id}")
    public Token read(@PathVariable("id") String id) {
        return reposiroty.findById(id).orElse(null);
    }

        @PutMapping
    public Token update(@RequestBody Token token) {
        return reposiroty.save(token);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        reposiroty.deleteById(id);
    }
}
