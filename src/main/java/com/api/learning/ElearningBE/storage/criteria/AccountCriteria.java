package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Role;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountCriteria {
    private String email;
    private String roleName;

    public Specification<Account> getSpecification(){
        return new Specification<Account>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getEmail())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%"+getEmail()+"%"));
                }
                if (!StringUtils.isEmpty(getRoleName())){
                    Join<Account, Role> join = root.join("role");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get("name")),"%"+getRoleName()+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}