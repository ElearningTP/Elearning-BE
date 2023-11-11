package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Category;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseCriteria {
    private Long id;
    private String name;
    private Integer state;
    private Long teacherId;
    private Long lessonPlanId;
    private Long categoryId;

    public Specification<Course> getSpecification(){
        return new Specification<Course>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+getName()+"%"));
                }
                if (getState() != null){
                    predicates.add(criteriaBuilder.equal(root.get("state"), getState()));
                }
                if (getTeacherId() != null){
                    Join<Course,Account> join = root.join("teacher");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTeacherId()));
                }
                if (getLessonPlanId() != null){
                    Join<Course, LessonPlan> join = root.join("lessonPlan");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getLessonPlanId()));
                }
                if (getCategoryId() != null){
                    Join<Course,Category> join = root.join("category");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getCategoryId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
