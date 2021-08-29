package com.spring.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    @Query(value = "select p from Etc p where p.id = :id and p.isShow = true")
    News findById(int id);

//    @Query(value = "select p from Books p where p.isShow = true )
    List<News> findAllByIsShow(boolean isShow, Pageable pageable);
}