package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import com.api.learning.ElearningBE.storage.entities.Modules;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ModulesCriteria {
    private Long id;
    private String name;
    private Long lessonPlanId;

    public Specification<Modules> getSpecification(){
        return new Specification<Modules>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Modules> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+getName()+"%"));
                }
                if (getLessonPlanId() != null){
                    Join<Modules, LessonPlan> join = root.join("lessonPlan");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getLessonPlanId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
