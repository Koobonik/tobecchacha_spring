package com.spring.service;

import com.spring.configuration.PasswordEncoding;
import com.spring.dto.requestDto.LoginRequestDto;
import com.spring.dto.requestDto.SignUpRequestDto;
import com.spring.dto.requestDto.ValidateAuthNumberRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.dto.responseDto.JwtResponseDto;
import com.spring.dto.responseDto.ProfileResponseDto;
import com.spring.model.EmailAuthCodeRepository;
import com.spring.model.Users;
import com.spring.model.UsersRepository;
import com.spring.util.AES256Cipher;
import com.spring.util.DateCreator;
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
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
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
        String encryptedEmail = aes256Cipher.AES_Encode(signUpRequestDto.getUserEmail());
        String password = new PasswordEncoding().encode(signUpRequestDto.getPassword());

        // 유저를 만들어준다.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        Users users = new Users(encryptedEmail, password, signUpRequestDto.getNickName(), roles);

        usersRepository.save(users);
        return new ResponseEntity<>(new DefaultResponseDto(200, "The membership has been registered successfully!"), HttpStatus.OK);
    }

    // 로그인
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, ParseException {
        // 일단 유저를 찾는다.
        log.info("유저 이메일 {}", loginRequestDto.getUserEmail());
        Users users = usersRepository.findByUserEmail(aes256Cipher.AES_Encode(loginRequestDto.getUserEmail()));
        if(users == null) return DefaultResponseDto.canNotFindAccount();
        users.setLastLogin(new DateCreator().getTimestamp());
        usersRepository.save(users);
        if(new PasswordEncoding().matches(loginRequestDto.getUserPassword(), users.getPassword())){
            return new ResponseEntity<>(jwtTokenProvider.createTokens(users.getUserEmail(), users.getRoles()),HttpStatus.OK);
        }
        return DefaultResponseDto.canNotMatchedAccount();
    }

    public ResponseEntity<?> getUserProfile(HttpServletRequest httpServletRequest){
        String jwt = jwtTokenProvider.resolveToken(httpServletRequest);
        log.info("유저 토큰 {}", jwt);
        if(jwtTokenProvider.validateToken(jwt)){
            log.info("잘 들어옴");
            Users user = jwtTokenProvider.getUsersFromToken(httpServletRequest);
            ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.getUserId(), user.getUserNickname(), user.getImageUrl(),user.getRoles());
            return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
        }
        return DefaultResponseDto.canNotFindProfile();
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

    public ResponseEntity<?> renewalToken(String token) throws ParseException {
        log.info("리뉴얼을 위해 들어온 토큰값! '{}'\n이 토큰이 유효한가?! -> '{}'", token.substring(token.length()-3), jwtTokenProvider.validateToken(token));
        if(jwtTokenProvider.validateToken(token)) {
            log.info("권한 통과");
            Users user = ((Users) jwtTokenProvider.getAuthentication(token).getPrincipal());

            log.info("새로운 리뉴얼 토큰 발행");
            JwtResponseDto jwtResponseDto = jwtTokenProvider.createTokens(user.getUserEmail(), user.getRoles());
            user.setLastLogin(new DateCreator().getTimestamp());
            usersRepository.save(user);
            log.info("유저이름 : '{}'\njwt -> '{}'\nrefresh -> '{}'", user.getUserNickname(), jwtResponseDto.getJwt().substring(jwtResponseDto.getJwt().length()-3), jwtResponseDto.getRefreshJwt().substring(jwtResponseDto.getRefreshJwt().length()-3));
            new Thread(
                    () -> {
                        try {
                            Thread.sleep(10*1000);
                            invalidationToken(token);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();

            return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
        }
        log.info("refreshToken 토큰 유효하지 않아서 401 반환");
        return new ResponseEntity<>( new DefaultResponseDto(401, "토큰이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
    }

    @javax.transaction.Transactional
    public void invalidationToken(String token) throws InterruptedException {
        ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
        Users user = (Users) jwtTokenProvider.getAuthentication(token).getPrincipal();
        logoutValueOperations.set(token, String.valueOf(user.getUserId())); // redis set 명령어

        log.info("토큰 무효화! 유저 아이디 : '{}' , 유저 이름 : '{}'", user.getUserId(), user.getUserNickname());
    }
}
