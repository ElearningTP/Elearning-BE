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
@Table(name = "db_course_registration")
public class CourseRegistration extends Auditable<String>{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(name = "total_gpa")
    private Double totalGPA = 0.0;
}
