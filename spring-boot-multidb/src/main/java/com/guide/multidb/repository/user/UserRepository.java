package com.guide.multidb.repository.user;

import com.guide.multidb.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
