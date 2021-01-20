package com.guide.jpa.domain.circle;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CircleService {
    private final CircleRepository circleRepository;

    public boolean exists(Circle circle) {
        Circle duplicated = circleRepository.findByName(circle.getName());
        return duplicated != null;
    }
}
