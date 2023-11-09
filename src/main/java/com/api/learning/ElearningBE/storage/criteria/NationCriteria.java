package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Nation;
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
public class NationCriteria {
    private Long id;
    private String name;
    private Integer status;

    public Specification<Nation> getSpecification(){
        return new Specification<Nation>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Nation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (getStatus() != null){
                    predicates.add(criteriaBuilder.equal(root.get("status"), getStatus()));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+getName()+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
