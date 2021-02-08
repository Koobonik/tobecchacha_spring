package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.ExampleUser;
import com.spring.model.ExampleUserRepository;
import com.spring.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Log4j2
@RequiredArgsConstructor
@Service
public class ExampleUsersService {

    private final ExampleUserRepository exampleUserRepository;
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public Long save(ExampleUser dto){
        return exampleUserRepository.save(dto).getExampleUserId();
    }

    // 로그아웃
    @javax.transaction.Transactional
    public ResponseEntity<?> logout(String jwt){
        ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
        logoutValueOperations.set(jwt, jwt); // redis set 명령어
        ExampleUser exampleUser = (ExampleUser) jwtTokenProvider.getAuthentication(jwt).getPrincipal();
        log.info("로그아웃 유저 아이디 : '{}' , 유저 이름 : '{}'", exampleUser.getExampleUserId(), exampleUser.getUserLoginId());
        return new ResponseEntity<>(new DefaultResponseDto(200,"로그아웃 되었습니다."), HttpStatus.OK);
    }
}
