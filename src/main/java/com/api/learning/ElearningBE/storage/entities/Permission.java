package com.api.learning.ElearningBE.storage.entities;

import com.api.learning.ElearningBE.storage.base.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_permission")
public class Permission extends Auditable<String> {
    private String name;
    private String action;
    private Boolean showMenu = false;
    @Column(columnDefinition = "text")
    private String description;
    @Column(unique = true)
    private String nameGroup;
    @Column(unique = true)
    private String pCode;
}
