package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnswerQuestionRepository extends JpaRepository<AnswerQuestion,Long>, JpaSpecificationExecutor<AnswerQuestion> {
    void deleteAllByQuestionId(Long questionId);
    @Transactional
    @Modifying
    @Query("DELETE FROM AnswerQuestion aq " +
            "WHERE aq.question.id IN (" +
            "   SELECT qq.id " +
            "   FROM QuizQuestion qq " +
            "   INNER JOIN Quiz q ON qq.quiz.id = q.id " +
            "   WHERE q.id = :quizId)")
    void deleteAllByQuizId(@Param("quizId") Long quizId);
    List<AnswerQuestion> findAllByQuestionIdIn(List<Long> ids);
    List<AnswerQuestion> findAllByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_answer_question aq " +
            "WHERE aq.id IN " +
            "   (SELECT Id FROM(SELECT aq.id " +
            "       FROM db_answer_question aq " +
            "       INNER JOIN db_quiz_question qq ON qq.id = aq.question_id " +
            "       INNER JOIN db_quiz q ON q.id = qq.quiz_id " +
            "       INNER JOIN db_modules m ON m.id = q.modules_id " +
            "       INNER JOIN db_lesson_plan lp ON lp.id = m.lesson_plan_id " +
            "       WHERE lp.id = :lessonPlanId " +
            "       ) AS subquery)", nativeQuery = true)
    void deleteAllByLessonPlanId(@Param("lessonPlanId") Long lessonPlanId);
}
