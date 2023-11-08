package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long>, JpaSpecificationExecutor<Subject> {
    Boolean existsByCode(String code);
}
