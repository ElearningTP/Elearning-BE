package com.api.learning.ElearningBE.schedules;

import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.AssignmentRepository;
import com.api.learning.ElearningBE.repositories.NotificationRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AssignmentNotificationSchedule {

    private final NotificationRepository notificationRepository;
    private final AssignmentRepository assignmentRepository;
    private final AccountRepository accountRepository;

    public AssignmentNotificationSchedule(NotificationRepository notificationRepository, AssignmentRepository assignmentRepository, AccountRepository accountRepository) {
        this.notificationRepository = notificationRepository;
        this.assignmentRepository = assignmentRepository;
        this.accountRepository = accountRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void notificationAssignmentExpireAfterADaySchedule(){
        List<Account> accounts = accountRepository.findAllStudentHaveAssignmentExpireAfterDay(1);

    }
}
