package com.guide.jpa.domain.circle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CircleRepository extends JpaRepository<Circle, CircleId> {
    Circle findByName(CircleName name);
}
