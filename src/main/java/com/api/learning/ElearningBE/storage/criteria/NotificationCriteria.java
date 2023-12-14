package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Notification;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotificationCriteria {
    private Long userId;
    private Boolean isRead;
    public Specification<Notification> getSpecification(){
        return new Specification<Notification>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getUserId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("idUser"), getUserId()));
                }
                if (getIsRead() != null){
                    predicates.add(criteriaBuilder.equal(root.get("isRead"), getIsRead()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
