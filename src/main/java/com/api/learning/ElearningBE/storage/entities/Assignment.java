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
@Table(name = "db_assignment")
public class Assignment extends Auditable<String> {
    private String title;
    @Column(columnDefinition = "longtext")
    private String content;
    @Column(name = "assignment_type")
    private Integer assignmentType; // 1 file. 2 text
    private Integer state; // 1 created, 2 started, 3 expired
    private Date startDate;
    private Date endDate;
    private String urlDocument;
    @ManyToOne
    @JoinColumn(name = "modules_id")
    private Modules modules;
}
