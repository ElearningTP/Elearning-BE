package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long>, JpaSpecificationExecutor<Resources> {
    List<Resources> findAllByModulesIdIn(List<Long> modulesIds);
    void deleteAllByModulesId(Long modulesId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM db_resources r " +
            "WHERE r.id IN ( " +
            "   SELECT Id FROM (SELECT r.id " +
            "   FROM db_resources r " +
            "   INNER JOIN db_modules m ON m.id = r.modules_id " +
            "   WHERE m.lesson_plan_id = :lessonPLanId " +
            "   )as subquery)", nativeQuery = true)
    void deleteAllByLessonPLanId(@Param("lessonPLanId") Long lessonPLanId);
}
