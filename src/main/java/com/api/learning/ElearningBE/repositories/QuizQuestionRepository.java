package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long>, JpaSpecificationExecutor<QuizQuestion> {
    void deleteAllByQuizId(Long quizId);
    List<QuizQuestion> findAllByQuizId(Long quizId);
    List<QuizQuestion> findAllByIdIn(List<Long> ids);
}
