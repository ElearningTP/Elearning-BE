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
}
