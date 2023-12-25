package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long>, JpaSpecificationExecutor<Lecture> {
    List<Lecture> findAllByModulesIdIn(List<Long> lectureIds);
    void deleteAllByModulesId(Long modulesId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_lecture l " +
            "WHERE l.id IN ( " +
            "   SELECT Id FROM (SELECT l.id " +
            "   FROM db_lecture l " +
            "   INNER JOIN db_modules m ON m.id = l.modules_id " +
            "   WHERE m.lesson_plan_id = :lessonPlanId)as subquery)", nativeQuery = true)
    void deleteAllByLessonPlanId(@Param("lessonPlanId") Long lessonPlanId);
}
