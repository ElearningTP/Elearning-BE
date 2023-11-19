package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long>, JpaSpecificationExecutor<Course> {
    List<Course> findAllByIdIn(List<Long> ids);
}
