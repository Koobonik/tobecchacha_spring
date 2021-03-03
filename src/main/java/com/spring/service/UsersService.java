package com.spring.service;

import com.spring.dto.requestDto.SignUpRequestDto;
import com.spring.dto.requestDto.ValidateAuthNumberRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.EmailAuthCode;
import com.spring.model.EmailAuthCodeRepository;
import com.spring.model.Users;
import com.spring.model.UsersRepository;
import com.spring.util.AES256Cipher;
import com.spring.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final AES256Cipher aes256Cipher;
    private final EmailAuthService emailAuthService;
    @Transactional
    public Long save(Users dto){
        return usersRepository.save(dto).getUserId();
    }

    // 회원가입 todo:// 나중에 SignUpService를 따로 만들어줘서 관리하는 것이 편할 것으로 예상.
    public ResponseEntity<?> signUp(SignUpRequestDto signUpRequestDto) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, ParseException {

        // 유저의 이메일과 이메일 코드로 시크릿을 만들어서 검증한다.
        if(emailAuthService.validateAuthNumber(new ValidateAuthNumberRequestDto(signUpRequestDto.getEmailCode(), signUpRequestDto.getUserEmail())).getStatusCodeValue() != 200){
            return emailAuthService.validateAuthNumber(new ValidateAuthNumberRequestDto(signUpRequestDto.getEmailCode(), signUpRequestDto.getUserEmail()));
        }

        // todo 이메일은 양방향 암호화, 비밀번호는 단방향 암호화하여 저장한다.

        // 유저를 만들어준다.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        Users users = new Users(signUpRequestDto.getUserEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getNickName(), roles);

        usersRepository.save(users);
        return new ResponseEntity<>(new DefaultResponseDto(200, "The membership has been registered normally."), HttpStatus.OK);

    }

    // 로그인
    public ResponseEntity<?> login(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그아웃
    @javax.transaction.Transactional
    public ResponseEntity<?> logout(String jwt){
        ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
        logoutValueOperations.set(jwt, jwt); // redis set 명령어
        Users users = (Users) jwtTokenProvider.getAuthentication(jwt).getPrincipal();
        log.info("로그아웃 유저 아이디 : '{}' , 유저 이름 : '{}'", users.getUserId(), users.getUserEmail());
        return new ResponseEntity<>(new DefaultResponseDto(200,"로그아웃 되었습니다."), HttpStatus.OK);
    }
}
