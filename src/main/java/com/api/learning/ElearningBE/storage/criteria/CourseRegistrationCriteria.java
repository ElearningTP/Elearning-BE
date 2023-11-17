package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.controller.CourseRegistrationController;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.CourseRegistration;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseRegistrationCriteria {
    private Long id;
    private Long studentId;
    private Long courseId;

    public Specification<CourseRegistration> getSpecification(){
        return new Specification<CourseRegistration>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<CourseRegistration> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (getStudentId() != null){
                    Join<CourseRegistrationController, Account> join = root.join("student");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getStudentId()));
                }
                if (getCourseId() != null){
                    Join<CourseRegistrationController, Course> join = root.join("course");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getCourseId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
