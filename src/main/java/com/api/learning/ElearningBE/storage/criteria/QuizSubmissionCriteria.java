package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import com.api.learning.ElearningBE.storage.entities.QuizSubmission;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuizSubmissionCriteria {
    private Long studentId;
    private Long courseId;
    private Long quizId;

    public Specification<QuizSubmission> getSpecification(){
        return new Specification<QuizSubmission>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<QuizSubmission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getStudentId() != null){
                    Join<QuizSubmission, Account> join = root.join("student");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getStudentId()));
                }
                if (getCourseId() != null){
                    Join<QuizSubmission, Course> join = root.join("course");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getCourseId()));
                }
                if (getQuizId() != null){
                    Join<QuizSubmission, Quiz> join = root.join("quiz");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getQuizId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
