package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    List<Permission> findAllByIdIn(List<Long> ids);
    Boolean existsByPermissionCode(String pCode);
}
