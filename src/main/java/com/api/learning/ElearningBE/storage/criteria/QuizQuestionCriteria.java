package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Quiz;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuizQuestionCriteria {
    private Long quizId;

    public Specification<QuizQuestion> getSpecification(){
        return new Specification<QuizQuestion>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<QuizQuestion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getQuizId() != null){
                    Join<QuizQuestion, Quiz> join = root.join("quiz");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getQuizId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
