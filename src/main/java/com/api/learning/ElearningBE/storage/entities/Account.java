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
@Table(name = "db_account")
public class Account extends Auditable<String> {
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private Integer kind; // 1 admin, 2 teacher, 3 student
    private String avatarPath;
    private Boolean isSuperAdmin = false;
    private Date lastLogin;
    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
