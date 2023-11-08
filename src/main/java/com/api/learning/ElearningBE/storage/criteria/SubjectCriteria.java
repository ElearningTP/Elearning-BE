package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Subject;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectCriteria {
    private Long id;
    private String code;
    private String name;
    private Integer status;

    public Specification<Subject> getSpecification(){
        return new Specification<Subject>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getCode())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%"+getCode()+"%"));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+getName()+"%"));
                }
                if (getStatus() != null){
                    predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
