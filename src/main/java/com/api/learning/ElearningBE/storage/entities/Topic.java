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
@Table(name = "db_topic")
public class Topic extends Auditable<String> {
    @Column(columnDefinition = "longtext")
    private String content;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
