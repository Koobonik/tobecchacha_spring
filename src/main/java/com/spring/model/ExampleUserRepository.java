package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleUserRepository extends JpaRepository<ExampleUser, Long> {
    ExampleUser findByUserLoginId(String loginId);
}
