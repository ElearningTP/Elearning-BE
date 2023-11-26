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
@Table(name = "db_lecture")
public class Lecture extends Auditable<String> {
    private String name;
    @Column(columnDefinition = "longtext")
    private String content;
    @ManyToOne
    @JoinColumn(name = "modules_id")
    private Modules modules;
}
