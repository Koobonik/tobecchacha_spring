package com.spring.controller;

import com.spring.dto.requestDto.JwtRequestDto;
import com.spring.dto.requestDto.LoginRequestDto;
import com.spring.dto.responseDto.PublicKeyResponseDto;
import com.spring.model.ExampleUser;
import com.spring.service.ExampleUsersService;
import com.spring.util.jwt.JwtTokenProvider;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Collections;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@Api(value = "API", tags = "유저 정보")
@RequestMapping("api/v1")
public class Api_V1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExampleUsersService exampleUsersService;
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

    @ApiOperation(value = "로그인", notes = "로그인에 대한 요청을 보냅니다.")
    @PostMapping(value = "login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpSession httpSession){
        // 일단 이렇게 계정이 있고 알맞게 로그인했다고 가정합시다!
        ExampleUser exampleUser = new ExampleUser();
        exampleUser.setExampleUserId(1);
        exampleUser.setUserLoginId(loginRequestDto.getUserLoginId());
        exampleUser.setUserPassword(loginRequestDto.getUserPassword());
        exampleUser.setRoles(Collections.singletonList("ROLE_USER"));
        if(loginRequestDto.getUserLoginId().equals("test_login_id") && loginRequestDto.getUserPassword().equals("test_login_password")){
            log.info((PrivateKey) httpSession.getAttribute("privateKey"));
            exampleUsersService.save(exampleUser);
            return jwtTokenProvider.createToken(exampleUser.getUserLoginId(), exampleUser.getRoles());
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

    // 공개키 발급
    @ApiOperation(value = "공개키 api", notes = "로그인이나 회원가입시 쓰이는 공개키 가져오는 api")
    @GetMapping("/getPublicKey")
    public PublicKeyResponseDto getPublicKey(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        HttpSession httpSession = request.getSession();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // httpSession(세션) : 서버단에서 관리! -> 개인키가 안전하게 보관됨 -> 이후에 자동적으로 만료되며 소멸되기에 관리에 용이함
        // 회원가입에 성공하거나 로그인 했을경우에는 세션에서 개인키를 지워 주면 Best
        httpSession.setAttribute("privateKey", privateKey);
        log.info("개인키");
        log.info(httpSession.getAttribute("privateKey"));
        // 추출
        RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        String publicKeyModulus = publicSpec.getModulus().toString(16);
        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
        PublicKeyResponseDto publicKeyResponseDto = PublicKeyResponseDto.builder()
                .publicKey(publicKey.toString())
                .RSAExponent(publicKeyModulus)
                .RSAModulus(publicKeyExponent)
                .build();
        log.info(publicKeyResponseDto);
        return publicKeyResponseDto;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃", response = String.class)
    })
    @ApiOperation(value = "로그아웃 api", notes = "헤더에 jwt, refreshJwt를 넣어서 보내주세요.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader @RequestParam String jwt){
        return exampleUsersService.logout(jwt);
    }

}
