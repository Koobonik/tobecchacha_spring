package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MainNoticeRepository extends JpaRepository<MainNotice, Integer> {
    @Query(value = "select p from MainNotice p where p.id = :id and p.isShow = true")
    MainNotice findById(int id);
}