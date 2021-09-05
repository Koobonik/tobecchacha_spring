package com.spring.dto.requestDto;

import com.spring.model.Etc;
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
public class EtcCreateRequestDto {
    // Model 에 보이는 설명들.

    @ApiModelProperty(example = "구로삶터, 푸른스물, 다시청년", value = "etc 제목", required = true)
    private String title;

    @ApiModelProperty(example = "구로삶터지역자활센터 20주년 기념전시", value = "etc 소제목", required = true)
    private String subTitle;

    @ApiModelProperty(example = "전시문의 : 문화온도 씨도시", value = "etc 정보에 대한 설명", required = true)
    private String informationContent;

    @ApiModelProperty(example = "노동의 땀방울을 춤과 흥으로 표현한 정인하 그림책\n밥춤과 어우러진 전시", value = "etc 대한 설명", required = true)
    private String content;

    @ApiModelProperty(example = "김성애", value = "이름 적기", required = true)
    private String withWho;

    @ApiModelProperty(example = "https://youtube.com", value = "비디오 링크들", required = true)
    private List<String> videoLinks;



    public Etc toEntity(List<String> imagesParam){
        log.info("toEntity 실행");
        return Etc.builder()
                .title(title)
                .subTitle(subTitle)
                .informationContent(informationContent)
                .content(content)
                .withWho(withWho)
                .videoLinks(videoLinks)
                .images(imagesParam)
                .isShow(true)
                .build();
    }

}
