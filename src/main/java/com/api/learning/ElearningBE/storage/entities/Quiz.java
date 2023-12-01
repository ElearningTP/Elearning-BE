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
@Table(name = "db_quiz")
public class Quiz extends Auditable<String> {
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "longtext")
    private String description;
    private Long timeLimit; //minutes
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "modules_id")
    private Modules modules;
}
