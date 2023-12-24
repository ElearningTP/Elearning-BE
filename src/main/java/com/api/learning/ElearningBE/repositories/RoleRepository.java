package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
    Boolean existsByName(String name);
    Role findByName(String name);
    @Query("SELECT r " +
            "FROM Role r " +
            "WHERE r.name != 'SuperAdmin' ")
    List<Role> findAllRole();
}
