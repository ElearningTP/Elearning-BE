package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

//    @Query("SELECT a " +
//            "FROM Assignment a " +
//            "WHERE (a.endDate >= NOW() AND DATE_ADD(NOW(), INTERVAL :numberDate DAY))")
//    List<Assignment> findAllByEndDateAfterDay(@Param("numberDate") Integer numberDate);
}
