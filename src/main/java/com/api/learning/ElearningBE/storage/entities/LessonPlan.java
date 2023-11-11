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
@Table(name = "db_lesson_plan")
public class LessonPlan extends Auditable<String> {
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @ManyToOne
    private Account teacher;
}
