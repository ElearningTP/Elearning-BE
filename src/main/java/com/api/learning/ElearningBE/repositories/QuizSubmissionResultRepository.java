package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.QuizSubmissionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionResultRepository extends JpaRepository<QuizSubmissionResult,Long>, JpaSpecificationExecutor<QuizSubmissionResult> {
    List<QuizSubmissionResult> findAllBySubmissionId(Long submissionId);
}
