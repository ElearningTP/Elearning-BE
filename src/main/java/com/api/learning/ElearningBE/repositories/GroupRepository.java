package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    Boolean existsByName(String name);
}
