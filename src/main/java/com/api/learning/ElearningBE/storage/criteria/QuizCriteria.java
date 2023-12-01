package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Modules;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuizCriteria {
    private String title;
    private Long modulesId;

    public Specification<Quiz> getSpecification(){
        return new Specification<Quiz>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Quiz> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getTitle())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"+getTitle()+"%"));
                }
                if (getModulesId() != null){
                    Join<Quiz, Modules> join = root.join("modules");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getModulesId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
