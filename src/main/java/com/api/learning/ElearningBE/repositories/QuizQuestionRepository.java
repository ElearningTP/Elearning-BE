package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long>, JpaSpecificationExecutor<QuizQuestion> {
    void deleteAllByQuizId(Long quizId);
    List<QuizQuestion> findAllByQuizId(Long quizId);
    List<QuizQuestion> findAllByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_quiz_question qq " +
            "WHERE qq.id IN " +
            "   (SELECT Id FROM(SELECT qq.id " +
            "       FROM db_quiz_question qq " +
            "       INNER JOIN db_quiz q ON q.id = qq.quiz_id " +
            "       INNER JOIN db_modules m ON m.id = q.modules_id " +
            "       INNER JOIN db_lesson_plan lp ON lp.id = m.lesson_plan_id " +
            "       WHERE lp.id = :lessonPlanId " +
            "       ) AS subquery)", nativeQuery = true)
    void deleteAllByLessonPlanId(@Param("lessonPlanId") Long lessonPlanId);
}
