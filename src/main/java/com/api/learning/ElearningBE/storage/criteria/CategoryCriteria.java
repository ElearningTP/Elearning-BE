package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Category;
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
public class CategoryCriteria {
    private Long id;
    private String name;

    public Specification<Category> getSpecification(){
        return new Specification<Category>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getName())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+getName()+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
