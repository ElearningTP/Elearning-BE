package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum,Long>, JpaSpecificationExecutor<Forum> {
    boolean existsByCourseId(Long courseId);
}
