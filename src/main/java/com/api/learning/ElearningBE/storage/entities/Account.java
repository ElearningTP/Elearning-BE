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
@Table(name = "db_account")
public class Account extends Auditable<String> {
    @Column(unique = true)
    private String email;
    private String password;
    private String fullName;
    private String avatarPath;
    private Boolean isAdmin = false;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
