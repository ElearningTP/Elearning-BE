package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission,Long>, JpaSpecificationExecutor<QuizSubmission> {
    Integer countByStudentIdAndQuizIdAndCourseId(Long studentId, Long quizId, Long courseId);
    List<QuizSubmission> findAllByStudentIdAndCourseIdAndQuizIdIn(Long studentId, Long courseId, List<Long> quizIds);
}
