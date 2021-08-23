package com.spring.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    @Query(value = "select p from Gallery p where p.id = :id and p.isShow = true")
    Gallery findById(int id);

    List<Gallery> findAllByIsShow(boolean isShow, Pageable pageable);
}