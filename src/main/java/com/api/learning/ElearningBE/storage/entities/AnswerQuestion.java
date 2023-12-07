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
@Table(name = "db_answer_question")
public class AnswerQuestion extends Auditable<String> {
    @Column(columnDefinition = "longtext")
    private String content;
    private Boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;
}
