package com.guide.jpa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    User findByName(UserName name);
}
