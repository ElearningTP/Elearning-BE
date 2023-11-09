package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation,Long>, JpaSpecificationExecutor<Nation> {
    Boolean existsByName(String name);
}
