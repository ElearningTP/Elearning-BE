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
@Table(name = "db_notification")
public class Notification extends Auditable<String> {
    @Column(columnDefinition = "text")
    private String message;
    @Column(name = "ref_id")
    private String refId;
    private Boolean isRead = false;
    @Column(name = "idUser")
    private Long idUser;
}
