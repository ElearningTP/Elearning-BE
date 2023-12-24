package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long>, JpaSpecificationExecutor<CourseRegistration> {
    Boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    void deleteAllByStudentId(Long studentId);
}