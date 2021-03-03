package com.spring.controller;

import com.spring.dto.requestDto.EmailRequestDto;
import com.spring.dto.requestDto.LoginRequestDto;
import com.spring.dto.requestDto.SignUpRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.dto.responseDto.PublicKeyResponseDto;
import com.spring.model.Users;
import com.spring.service.EmailAuthService;
import com.spring.service.UsersService;
import com.spring.util.jwt.JwtTokenProvider;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.util.Collections;

@Log4j2
@RestController
@RequiredArgsConstructor
@Api(value = "API", tags = "유저 정보")
@RequestMapping("api/v1")
public class Api_V1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final EmailAuthService emailAuthService;
    @ApiOperation(value = "HTTP GET EXAMPLE", notes = "GET 요청에 대한 예제 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String main(@ApiParam(value = "테스트 파라미터_1", required = true, example = "test_parameter_1") @RequestParam String test1,
                                     @ApiParam(value = "테스트 파라미터_2", required = true, example = "test_parameter_2") @RequestParam String test2) {
        return test1 + " : " + test2;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 이메일 전송", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "입력한 이메일로 인증 번호 요청 api", notes = "")
    @PostMapping("/sendEmailForAuthEmail")
    public ResponseEntity<?> sendEmailForAuthEmail(@RequestBody EmailRequestDto emailRequestDto){
        return emailAuthService.sendEmailForAuthEmail(emailRequestDto);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 회원가입", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "메일 인증코드와 함께 입력한 정보로 회원 가입", notes = "")
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, ParseException {
        return usersService.signUp(signUpRequestDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인에 대한 요청을 보냅니다.")
    @PostMapping(value = "login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpSession httpSession){
        // 일단 이렇게 계정이 있고 알맞게 로그인했다고 가정합시다!
        Users users = new Users();
        users.setUserId(1);
        users.setUserEmail(loginRequestDto.getUserLoginId());
        users.setUserPassword(loginRequestDto.getUserPassword());
        users.setRoles(Collections.singletonList("ROLE_USER"));
        if(loginRequestDto.getUserLoginId().equals("test_login_id") && loginRequestDto.getUserPassword().equals("test_login_password")){
            log.info((PrivateKey) httpSession.getAttribute("privateKey"));
            usersService.save(users);
            return jwtTokenProvider.createToken(users.getUserEmail(), users.getRoles());
        }
        return loginRequestDto.getUserLoginId() + " : " + loginRequestDto.getUserPassword();
    }

    @PostMapping("jwtValidation")
    public String jwtValidation(@RequestHeader @RequestParam String jwt){
        // 헤더에서 토큰값 추출
        log.info(jwt);
        // 토큰값이 유효한 경우
        if(jwtTokenProvider.validateToken(jwt)) {
            log.info("토큰 유효함");
            // 유저 정보 추출 (아이디)
            log.info(jwtTokenProvider.getUserPk(jwt));
            return "true";
            // 인증 정보 조회
//            log.info(jwtTokenProvider.getAuthentication(jwt).getAuthorities()); // ex ROLE_USER
//            log.info(jwtTokenProvider.getAuthentication(jwt).getCredentials());
//            log.info(jwtTokenProvider.getAuthentication(jwt).getDetails());
//            log.info(((ExampleUser) jwtTokenProvider.getAuthentication(jwt).getPrincipal()).getPassword()); // 유저 클래스를 가져와준다!
//            log.info(jwtTokenProvider.getAuthentication(jwt).getName());
        }
        return "hi";
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃", response = String.class)
    })
    @ApiOperation(value = "로그아웃 api", notes = "헤더에 jwt, refreshJwt를 넣어서 보내주세요.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader @RequestParam String jwt){
        return usersService.logout(jwt);
    }

}
