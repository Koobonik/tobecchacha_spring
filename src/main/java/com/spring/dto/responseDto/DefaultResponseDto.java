package com.spring.dto.responseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultResponseDto {
    @ApiModelProperty(example = "응답 코드 번호", value = "에러 코드값")
    private int code;
    @ApiModelProperty(example = "안내 메시지", value = "메시지")
    private String message;

    public static ResponseEntity<?> canNotFindAccount(){
        return new ResponseEntity<>(new DefaultResponseDto(409, "Can't find account"), HttpStatus.CONFLICT);
    }
    public static ResponseEntity<?> canNotMatchedAccount(){
        return new ResponseEntity<>(new DefaultResponseDto(409, "Can't find matched account"), HttpStatus.CONFLICT);
    }
    public static ResponseEntity<?> canNotFindProfile(){
        return new ResponseEntity<>(new DefaultResponseDto(409, "Can't find profile"), HttpStatus.CONFLICT);
    }
}