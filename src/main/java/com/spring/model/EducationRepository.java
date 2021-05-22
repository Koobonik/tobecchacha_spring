package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Query(value = "select p from Education p where p.id = :id and p.isShow = true")
    Education findById(int id);
}