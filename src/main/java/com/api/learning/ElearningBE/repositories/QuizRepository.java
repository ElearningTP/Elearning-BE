package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long>, JpaSpecificationExecutor<Quiz> {
    List<Quiz> findAllByModulesIdIn(List<Long> modulesIds);
    @Query("SELECT q, c " +
            "FROM Quiz q " +
            "INNER JOIN Modules m ON q.modules.id = m.id " +
            "INNER JOIN LessonPlan lp ON m.lessonPlan.id = lp.id " +
            "INNER JOIN Course c ON c.lessonPlan.id = lp.id " +
            "INNER JOIN CourseRegistration cr ON cr.course.id = c.id " +
            "INNER JOIN Account s ON cr.student.id = s.id " +
            "WHERE s.id = :studentId")
    List<Object[]> findAllQuizByStudentId(@Param("studentId") Long studentId);
}
