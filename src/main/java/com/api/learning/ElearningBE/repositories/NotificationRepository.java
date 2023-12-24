package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>, JpaSpecificationExecutor<Notification> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE db_notification " +
            "SET is_read = TRUE " +
            "WHERE id IN ( " +
            "   SELECT Id FROM " +
            "       (SELECT n.id " +
            "        FROM db_notification AS n " +
            "        WHERE n.id_user = :accountId AND n.is_read IS FALSE) AS subq)",nativeQuery = true)
    void updateAllNotificationsToRead(@Param("accountId") Long accountId);
    void deleteAllByIdUser(Long id);
}
