package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query(value = "select p from Books p where p.id = :id and p.isShow = true")
    Books findById(int id);
}