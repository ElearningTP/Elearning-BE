package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import com.api.learning.ElearningBE.storage.entities.Course;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AssignmentSubmissionCriteria {
    private Long assignmentId;
    private Long studentId;
    private Long courseId;

    public Specification<AssignmentSubmission> getSpecification(){
        return new Specification<AssignmentSubmission>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<AssignmentSubmission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getAssignmentId() != null){
                    Join<AssignmentSubmission, Assignment> join = root.join("assignment");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getAssignmentId()));
                }
                if (getStudentId() != null){
                    Join<AssignmentSubmission, Account> join = root.join("student");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getStudentId()));
                }
                if (getCourseId() != null){
                    Join<AssignmentSubmission, Course> join = root.join("course");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getCourseId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
