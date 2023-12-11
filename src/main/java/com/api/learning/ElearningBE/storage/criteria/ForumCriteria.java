package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.CourseRegistration;
import com.api.learning.ElearningBE.storage.entities.Forum;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ForumCriteria {
    private String title;
    private Long courseId;
    private Long accountId;

    public Specification<Forum> getSpecification(){
        return new Specification<Forum>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Forum> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(getTitle())){
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"+getTitle()+"%"));
                }
                if (getCourseId() != null){
                    Join<Forum, Course> join = root.join("course");
                    predicates.add(criteriaBuilder.equal(join.get("id"),getCourseId()));
                }
                if (getAccountId() != null){
                    Join<Forum,Course> forumJoinCourse = root.join("course", JoinType.INNER);
                    Predicate teacherPredicate = criteriaBuilder.equal(forumJoinCourse.get("teacher").get("id"), getAccountId());

                    Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
                    Root<CourseRegistration> registrationRoot = subquery.from(CourseRegistration.class);
                    subquery.select(registrationRoot.get("course").get("id"));
                    subquery.where(criteriaBuilder.equal(registrationRoot.get("student"), getAccountId()));
                    Predicate studentPredicate = criteriaBuilder.in(forumJoinCourse.get("id")).value(subquery);

                    predicates.add(criteriaBuilder.or(teacherPredicate,studentPredicate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
