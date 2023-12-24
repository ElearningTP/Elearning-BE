package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.QuizSubmissionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizSubmissionResultRepository extends JpaRepository<QuizSubmissionResult,Long>, JpaSpecificationExecutor<QuizSubmissionResult> {
    List<QuizSubmissionResult> findAllBySubmissionId(Long submissionId);

    @Modifying
    @Transactional
    @Query("DELETE " +
            "FROM QuizSubmissionResult qsr " +
            "WHERE qsr.submission.id IN ( " +
            "   SELECT qs.id " +
            "   FROM QuizSubmission qs " +
            "   INNER JOIN Quiz q ON q.id = qs.quiz.id " +
            "   WHERE q.id = :quizId)")
    void deleteAllByQuizId(@Param("quizId") Long quizId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_quiz_submission_result AS qsr " +
            "WHERE qsr.id IN " +
            "   (SELECT Id " +
            "   FROM (SELECT qsr.id " +
            "           FROM db_quiz_submission_result AS qsr " +
            "           INNER JOIN db_quiz_submission AS qs ON qs.id = qsr.submission_id " +
            "           WHERE qs.student_id = :studentId) AS subquery)", nativeQuery = true)
    void deleteAllByStudentId(@Param("studentId") Long studentId);
}
