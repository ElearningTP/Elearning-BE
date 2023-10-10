package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
}
