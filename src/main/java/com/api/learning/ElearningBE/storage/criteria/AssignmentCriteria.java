package com.api.learning.ElearningBE.storage.criteria;

import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import com.api.learning.ElearningBE.storage.entities.Modules;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AssignmentCriteria {
    private Long id;
    private Integer type;
    private Integer state;
    private Long modulesId;
    private Long courseId;

    public Specification<Assignment> getSpecification(){
        return new Specification<Assignment>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Assignment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("id"),getId()));
                }
                if (getType() != null){
                    predicates.add(criteriaBuilder.equal(root.get("assignmentType"), getType()));
                }
                if (getState() != null){
                    predicates.add(criteriaBuilder.equal(root.get("state"), getState()));
                }
                if (getModulesId() != null){
                    Join<Assignment, Modules> join = root.join("modules");
                    predicates.add(criteriaBuilder.equal(join.get("id"), getModulesId()));
                }
                if (getCourseId() != null){
                    Join<Assignment, Modules> modulesJoin = root.join("modules");
                    Join<Modules, LessonPlan> lessonPlanJoin = modulesJoin.join("lessonPlan");

                    Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
                    Root<Course> courseRoot = subquery.from(Course.class);
                    Join<Course, LessonPlan> courseLessonPlanJoin = courseRoot.join("lessonPlan");
                    subquery.select(courseLessonPlanJoin.get("id"));
                    subquery.where(criteriaBuilder.equal(courseRoot.get("id"), getCourseId()));

                    predicates.add(criteriaBuilder.equal(lessonPlanJoin.get("id"), subquery));

                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
