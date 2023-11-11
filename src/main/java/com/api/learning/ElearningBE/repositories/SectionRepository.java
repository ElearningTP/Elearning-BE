package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section,Long>, JpaSpecificationExecutor<Section> {
}
