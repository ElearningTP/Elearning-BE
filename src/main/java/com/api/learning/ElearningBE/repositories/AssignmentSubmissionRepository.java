package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long>, JpaSpecificationExecutor<AssignmentSubmission> {
    Boolean existsByStudentIdAndAssignmentIdAndCourseId(Long studentId, Long assignmentId, Long courseId);
    List<AssignmentSubmission> findAllByAssignmentIdInAndStudentIdAndCourseId(List<Long> assignmentIds, Long studentId, Long courseId);
}
