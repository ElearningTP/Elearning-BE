package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Topic;
import com.api.learning.ElearningBE.storage.entities.TopicComment;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TopicCommentCriteria {
    private Long topicId;
    private Long accountId;

    public Specification<TopicComment> getSpecification(){
        return new Specification<TopicComment>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TopicComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getTopicId() != null){
                    Join<TopicComment, Topic> join = root.join("topic");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getTopicId()));
                }
                if (getAccountId() != null){
                    Join<TopicComment, Account> join = root.join("account");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getAccountId()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
