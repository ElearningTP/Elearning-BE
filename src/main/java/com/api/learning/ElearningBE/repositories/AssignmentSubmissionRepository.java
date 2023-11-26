package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long>, JpaSpecificationExecutor<AssignmentSubmission> {
    Boolean existsByStudentIdAndAssignmentIdAndCourseId(Long studentId, Long assignmentId, Long courseId);
}
