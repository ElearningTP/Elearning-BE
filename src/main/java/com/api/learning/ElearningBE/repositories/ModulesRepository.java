package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulesRepository extends JpaRepository<Modules,Long>, JpaSpecificationExecutor<Modules> {
}
