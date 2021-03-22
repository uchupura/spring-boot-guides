package com.guide.jpa.domain.user.repository;

import com.guide.jpa.domain.user.entity.User;
import com.guide.jpa.domain.user.entity.UserId;
import com.guide.jpa.domain.user.entity.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    User findByName(UserName name);
}
