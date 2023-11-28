package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    List<Topic> findAllByForumId(Long forumId);
}
