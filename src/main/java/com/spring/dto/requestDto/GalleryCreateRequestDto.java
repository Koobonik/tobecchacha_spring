package com.spring.dto.requestDto;

import com.spring.model.Books;
import com.spring.model.Gallery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class GalleryCreateRequestDto {
    // Model 에 보이는 설명들.
    @ApiModelProperty(example = "35000", value = "숫자만 입력", required = true)
    private int price;

    @ApiModelProperty(example = "성애쌤의 해외 봉사 라이프", value = "책 제목", required = true)
    private String title;

    @ApiModelProperty(example = "철수가 무겁게 입을 열었다\n여름이었다.", value = "책에 대한 설명", required = true)
    private String content;

    @ApiModelProperty(example = "김성애", value = "작가 이름", required = true)
    private String createdWho;

    @ApiModelProperty(example = "김성애", value = "작가 이메일", required = true)
    private String createdEmail;

    @ApiModelProperty(example = "3.5", value = "책의 가로 길이", required = true)
    private float width; // 가로 길이
    @ApiModelProperty(example = "3.5", value = "책의 세로 길이", required = true)
    private float height; // 세로 길이
    @ApiModelProperty(example = "https", value = "네이버 스토어 링크", required = true)
    private String nPayLink;
    @ApiModelProperty(example = "사진집단 포토청 정기사진전 <서울의 경계에서> / 갤러리 류가헌", value = "전시 정보", required = true)
    private String information;
    @ApiModelProperty(example = "이미지 설명들.", value = "책이 만들어진 날", required = true)
    private List<String> imagesDescription;

    @ApiModelProperty(example = "2021-05-15", value = "촬영된 날", required = true)
    private LocalDateTime createdDate;


    public Gallery toEntity(List<String> imagesParam){
        log.info("toEntity 실행");
        return Gallery.builder()
                .price(price)
                .title(title)
                .content(content)
                .createdWho(createdWho)
                .width(width)
                .height(height)
                .nPayLink(nPayLink)
                .isShow(true)
                .images(imagesParam)
                .imagesDescription(imagesDescription)
                .createdEmail(createdEmail)
                .createdDate(createdDate)
                .information(information)
                .build();
    }

}
