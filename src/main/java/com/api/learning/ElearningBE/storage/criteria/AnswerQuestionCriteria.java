package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerQuestionCriteria {
    private Long questionId;

    public Specification<AnswerQuestion> getSpecification(){
        return new Specification<AnswerQuestion>() {
            private final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<AnswerQuestion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getQuestionId() != null){
                    Join<AnswerQuestion, QuizQuestion> join = root.join("question");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getQuestionId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
