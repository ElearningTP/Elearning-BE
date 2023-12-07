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
@Table(name = "db_quiz_question")
public class QuizQuestion extends Auditable<String> {
    @Column(columnDefinition = "longtext")
    private String content;
    private Double score;
    private Integer questionType; // 1 single choice, 2 multi choice
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
