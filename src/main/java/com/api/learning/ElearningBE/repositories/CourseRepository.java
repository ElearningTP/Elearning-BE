package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long>, JpaSpecificationExecutor<Course> {
    List<Course> findAllByIdIn(List<Long> ids);
    boolean existsById(Long id);

    @Query("SELECT c.teacher.id " +
            "FROM Course c " +
            "INNER JOIN Forum f ON f.course.id = c.id " +
            "WHERE f.id = :forumId")
    Long findTeacherIdByForumId(@Param("forumId") Long forumId);
}
