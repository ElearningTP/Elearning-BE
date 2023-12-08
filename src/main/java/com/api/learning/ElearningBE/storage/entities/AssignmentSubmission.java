package com.api.learning.ElearningBE.storage.entities;

import com.api.learning.ElearningBE.storage.base.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_assignment_submission")
public class AssignmentSubmission extends Auditable<String> {
    private Double score;
    @Column(name = "text_submission", columnDefinition = "longtext")
    private String textSubmission;
    @Column(name = "file_submission_url")
    private String fileSubmissionUrl;
    @OneToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
