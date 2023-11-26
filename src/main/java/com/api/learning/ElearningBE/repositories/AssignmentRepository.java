package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long>, JpaSpecificationExecutor<Assignment> {
    List<Assignment> findAllByModulesIdIn(List<Long> modulesIds);
}
