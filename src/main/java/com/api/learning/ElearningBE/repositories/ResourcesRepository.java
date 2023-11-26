package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long>, JpaSpecificationExecutor<Resources> {
    List<Resources> findAllByModulesIdIn(List<Long> modulesIds);
}
