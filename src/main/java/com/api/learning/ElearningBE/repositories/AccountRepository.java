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

    @Query(value = "SELECT DISTINCT a.* " +
            "FROM db_account AS a " +
            "INNER JOIN db_course_registration AS cr ON cr.student_id = a.id " +
            "INNER JOIN db_course AS c ON c.id = cr.course_id " +
            "INNER JOIN db_lesson_plan AS lp ON lp.id = c.lesson_plan_id " +
            "INNER JOIN db_modules AS m ON m.lesson_plan_id = lp.id " +
            "INNER JOIN db_assignment AS assign ON assign.modules_id = m.id " +
            "WHERE assign.id IN ( " +
            "SELECT a.id " +
            "FROM db_assignment AS a " +
            "WHERE (a.end_date >= NOW()) AND (a.end_date <= DATE_ADD(NOW(), INTERVAL 1 DAY)))", nativeQuery = true)
    List<Account> findAllStudentHaveAssignmentExpireAfterDay(@Param("numberDate") Integer numberDate);

    @Query("SELECT a.id, c.teacher.id " +
            "FROM Account a " +
            "INNER JOIN CourseRegistration cr ON cr.student.id = a.id " +
            "INNER JOIN Course c ON c.id = cr.course.id " +
            "INNER JOIN Forum f ON f.course.id = c.id " +
            "INNER JOIN Topic t ON t.forum.id = f.id " +
            "WHERE t.id = :topicId AND a.id != :accountId")
    List<Object[]> findAllMemberOfCourseByTopicId(@Param("topicId") Long topicId, @Param("accountId") Long accountId);

    @Query("SELECT a " +
            "FROM Account a " +
            "INNER JOIN CourseRegistration cr ON cr.student.id = a.id " +
            "INNER JOIN Course c ON c.id = cr.course.id " +
            "WHERE c.id = :courseId")
    Page<Account> findAllStudentByCourseId(@Param("courseId") Long courseId, Pageable pageable);
}
