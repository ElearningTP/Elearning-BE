package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {
    Boolean existsAccountByEmail(String email);
    Account findByEmail(String email);
    @Query("SELECT DISTINCT a " +
            "FROM Account a " +
            "INNER JOIN CourseRegistration cr ON cr.student.id = a.id " +
            "INNER JOIN  Course c ON c.id = cr.course.id " +
            "WHERE a.id != :studentId AND c.id IN " +
            "   (SELECT c " +
            "   FROM Account a " +
            "   INNER JOIN CourseRegistration cr ON cr.student.id = a.id " +
            "   INNER JOIN Course c ON c.id = cr.course.id " +
            "   WHERE a.id = :studentId)")
    Page<Account> findAllMemberTheSameCourse(@Param("studentId") Long studentId, Pageable pageable);
}
