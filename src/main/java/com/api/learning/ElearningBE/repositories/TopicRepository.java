package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    List<Topic> findAllByForumId(Long forumId);
    void deleteAllByAccountId(Long accountId);

    @Modifying
    @Transactional
    @Query(value = "DELETE t FROM db_topic t " +
            "INNER JOIN db_forum f ON t.forum_id = f.course_id " +
            "INNER JOIN db_course c ON f.course_id = c.id " +
            "WHERE c.id = :courseId ", nativeQuery = true)
    void deleteAllByCourseId(@Param("courseId") Long courseId);
}
