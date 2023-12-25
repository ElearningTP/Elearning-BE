package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.TopicComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TopicCommentRepository extends JpaRepository<TopicComment,Long>, JpaSpecificationExecutor<TopicComment> {
    List<TopicComment> findAllByTopicIdIn(List<Long> topicIds);
    void deleteAllByTopicId(Long topicId);
    void deleteAllByAccountId(Long accountId);

    @Transactional
    @Modifying
    @Query(value = "DELETE tc FROM db_topic_comment tc " +
            "INNER JOIN db_topic t ON tc.topic_id = t.id " +
            "INNER JOIN db_forum f ON t.forum_id = f.course_id " +
            "INNER JOIN db_course c ON f.course_id = c.id " +
            "WHERE c.id = :courseId", nativeQuery = true)
    void deleteAllByCourseId(@Param("courseId") Long courseId);
}
