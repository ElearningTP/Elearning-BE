package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.TopicComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicCommentRepository extends JpaRepository<TopicComment,Long>, JpaSpecificationExecutor<TopicComment> {
}
