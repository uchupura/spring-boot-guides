package com.guide.redis.repository;

import com.guide.redis.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenReposiroty extends CrudRepository<Token, String> {
}
