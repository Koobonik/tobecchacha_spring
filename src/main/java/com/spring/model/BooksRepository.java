package com.spring.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query(value = "select p from Books p where p.id = :id and p.isShow = true")
    Books findById(int id);

//    @Query(value = "select p from Books p where p.isShow = true )
    List<Books> findAllByIsShow(boolean isShow, Pageable pageable);
}