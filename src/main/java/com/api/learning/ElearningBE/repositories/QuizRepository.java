package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long>, JpaSpecificationExecutor<Quiz> {
    List<Quiz> findAllByModulesIdIn(List<Long> modulesIds);
}
