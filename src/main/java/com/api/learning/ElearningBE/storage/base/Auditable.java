package com.api.learning.ElearningBE.storage.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Auditable<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;
    @LastModifiedDate
    @Column(name = "modified_date", nullable = false, updatable = false)
    private Date modifiedDate;
    @CreatedBy
    @Column(name = "create_by", nullable = false)
    private T createBy;
    @LastModifiedBy
    @Column(name = "modified", nullable = false)
    private T modifiedBy;
    private Integer status = 1;
}
