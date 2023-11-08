package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
    Boolean existsByName(String name);
    Role findByName(String name);
}
