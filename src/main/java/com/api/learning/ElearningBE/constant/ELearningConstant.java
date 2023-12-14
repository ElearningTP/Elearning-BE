package com.api.learning.ElearningBE.constant;

import com.api.learning.ElearningBE.utils.ConfigurationService;

public class ELearningConstant {
    public static final String PATH_DIRECTORY = ConfigurationService.getInstance().getString("file.upload-dir", "/tmp/upload");

    // Global status
    public static final Integer STATUS_LOCKED = 0;
    public static final Integer STATUS_ACTIVE = 1;

    //Group
    public static final Integer ROLE_KIND_ADMIN = 1;
    public static final Integer ROLE_KIND_TEACHER = 2;
    public static final Integer ROLE_KIND_STUDENT = 3;

    //Account
    public static final Integer ACCOUNT_KIND_ADMIN = 1;
    public static final Integer ACCOUNT_KIND_TEACHER = 2;
    public static final Integer ACCOUNT_KIND_STUDENT = 3;

    // Nation

    // Course
    public static final Integer COURSE_STATE_CREATED = 1;
    public static final Integer COURSE_STATE_STARTED = 2;

    //Assignment
    public static final Integer ASSIGNMENT_STATE_CREATED = 1;
    public static final Integer ASSIGNMENT_STATE_STARTED = 2;
    public static final Integer ASSIGNMENT_STATE_EXPIRED = 3;

    // Quiz question
    public static final Integer QUIZ_QUESTION_TYPE_SINGLE_CHOICE = 1;
    public static final Integer QUIZ_QUESTION_TYPE_MULTI_CHOICE = 2;
    public static final Double QUIZ_QUESTION_SCORE_DEFAULT = 1D;
    public static final Integer QUIZ_QUESTION_ATTEMPT_NUMBER_UNLIMITED = 0;

    // Notification
    public static final String NOTIFICATION_KIND_TOPIC = "TOPIC";
    public static final String NOTIFICATION_KIND_COMMENT = "COMMENT";
}
