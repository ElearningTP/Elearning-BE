package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long>, JpaSpecificationExecutor<Assignment> {
    List<Assignment> findAllByModulesIdIn(List<Long> modulesIds);

    @Query("SELECT assign, c " +
            "FROM Assignment assign " +
            "INNER JOIN Modules m ON assign.modules.id = m.id " +
            "INNER JOIN LessonPlan lp ON m.lessonPlan.id = lp.id " +
            "INNER JOIN Course c ON c.lessonPlan.id = lp.id " +
            "INNER JOIN CourseRegistration cr ON cr.course.id = c.id " +
            "INNER JOIN Account s ON cr.student.id = s.id " +
            "WHERE s.id = :studentId")
    List<Object[]> findAllAssignmentByStudentId(@Param("studentId") Long studentId);
    void deleteAllByModulesId(Long modulesId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_assignment a " +
            "WHERE a.id IN ( " +
            "   SELECT Id FROM (SELECT a.id " +
            "   FROM db_assignment a " +
            "   INNER JOIN db_modules m ON m.id = a.modules_id " +
            "   WHERE m.lesson_plan_id = :lessonPlanId)as subquery)", nativeQuery = true)
    void deleteAllByLessonPlanId(@Param("lessonPlanId") Long lessonPlanId);

//    @Query("SELECT a " +
//            "FROM Assignment a " +
//            "WHERE (a.endDate >= NOW() AND DATE_ADD(NOW(), INTERVAL :numberDate DAY))")
//    List<Assignment> findAllByEndDateAfterDay(@Param("numberDate") Integer numberDate);
}
