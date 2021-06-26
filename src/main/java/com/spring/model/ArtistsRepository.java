package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistsRepository extends JpaRepository<Artists, Integer> {
    @Query(value = "select p from Artists p where p.id = :id and p.isShow = true")
    Artists findById(int id);
}