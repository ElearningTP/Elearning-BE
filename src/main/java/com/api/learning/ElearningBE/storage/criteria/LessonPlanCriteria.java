package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class LessonPlanCriteria {
    private Long id;
    private String name;
    private Long teacherId;

    public Specification<LessonPlan> getSpecification(){
        return new Specification<LessonPlan>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<LessonPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+getName()+"%"));
                }
                if (getTeacherId() != null){
                    Join<LessonPlan, Account> join = root.join("teacher");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getTeacherId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
