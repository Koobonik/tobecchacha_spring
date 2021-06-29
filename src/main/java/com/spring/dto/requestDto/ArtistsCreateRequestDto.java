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

    @ApiModelProperty(example = "김성애", value = "구매 문의", required = true)
    private String email;

    @ApiModelProperty(example = "https", value = "네이버 스토어 링크", required = true)
    private String job;
    @ApiModelProperty(example = "사진집단 포토청 정기사진전 <서울의 경계에서> / 갤러리 류가헌", value = "작품 정보", required = true)
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
