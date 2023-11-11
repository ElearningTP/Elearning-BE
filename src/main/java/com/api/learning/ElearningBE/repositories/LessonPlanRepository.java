package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonPlanRepository extends JpaRepository<LessonPlan,Long>, JpaSpecificationExecutor<LessonPlan> {
}
