package com.api.learning.ElearningBE.storage.entities;

import com.api.learning.ElearningBE.storage.base.Auditable;
import com.api.learning.ElearningBE.storage.converter.StringListConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_course")
public class Course extends Auditable<String> {
    private String name;
    private String thumbnail;
    private Integer state;
    private Date startDate;
    @Column(columnDefinition = "text")
    @Convert(converter = StringListConverter.class)
    private List<String> requirements =  new ArrayList<>();
    @Column(columnDefinition = "text")
    @Convert(converter = StringListConverter.class)
    private List<String> objectives = new ArrayList<>();
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToOne
    private Account teacher;
    @ManyToOne
    @JoinColumn(name = "lesson_plan_id")
    private LessonPlan lessonPlan;
    @ManyToOne
    private Category category;
}
