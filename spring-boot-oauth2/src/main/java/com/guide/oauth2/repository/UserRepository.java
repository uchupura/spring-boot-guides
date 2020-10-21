package com.guide.oauth2.repository;

import com.guide.oauth2.entity.user.User;
import com.guide.oauth2.entity.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);

    Page<User> findByRole(UserRole role, Pageable pageable);
}
