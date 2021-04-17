package com.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUserEmail(String userEmail);
    Users findByUserEmailAndUserNickname(String userEmail, String nickName);
    Users findByUserId(long id);
}
