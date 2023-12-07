package com.api.learning.ElearningBE.storage.entities;

import com.api.learning.ElearningBE.storage.base.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_quiz_submission_result")
public class QuizSubmissionResult extends Auditable<String> {
    @ManyToOne
    @JoinColumn(name = "submission_id")
    private QuizSubmission submission;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerQuestion answer;
}
