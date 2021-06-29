package com.spring.dto.requestDto;

import com.spring.model.Artists;
import com.spring.model.Gallery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class ArtistsCreateRequestDto {

    @ApiModelProperty(example = "2021-06-29", value = "아티스트 데뷔일?", required = true)
    private String createdDate;

    @ApiModelProperty(example = "김성애", value = "성명", required = true)
    private String name;

    @ApiModelProperty(example = "abcd@naver.com", value = "연락처", required = true)
    private String email;

    @ApiModelProperty(example = "포토그래퍼", value = "직업", required = true)
    private String job;
    @ApiModelProperty(example = "사진 한장에 많은 의미를 담고자 노력하는 사진 작가 입니다.", value = "작가에 대한 소개", required = true)
    private String introduce;


    public Artists toEntity(List<String> imagesParam, String image){
        log.info("toEntity 실행");
        return Artists.builder()
                .createdDate(createdDate)
                .name(name)
                .email(email)
                .job(job)
                .introduce(introduce)
                .images(imagesParam)
                .image(image)
                .build();
    }

}
