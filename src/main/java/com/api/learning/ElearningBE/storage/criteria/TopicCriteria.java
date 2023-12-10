package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Forum;
import com.api.learning.ElearningBE.storage.entities.Topic;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TopicCriteria {
    private Long forumId;
    private Long accountId;

    public Specification<Topic> getSpecification(){
        return new Specification<Topic>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getForumId() != null){
                    Join<Topic, Forum> join = root.join("forum");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getForumId()));
                }
                if (getAccountId() != null){
                    Join<Topic, Account> join = root.join("account");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getAccountId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
