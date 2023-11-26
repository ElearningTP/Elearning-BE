package com.api.learning.ElearningBE.repositories;

import com.api.learning.ElearningBE.storage.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long>, JpaSpecificationExecutor<Lecture> {
}
