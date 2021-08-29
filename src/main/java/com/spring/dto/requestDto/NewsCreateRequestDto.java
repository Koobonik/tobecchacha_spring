package com.spring.dto.requestDto;

import com.spring.model.News;
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
public class NewsCreateRequestDto {
    // Model 에 보이는 설명들.

    @ApiModelProperty(example = "김성애 선생님, 해외 봉사 자서전 출시... 반응 폭발적?", value = "news 제목", required = true)
    private String title;

    @ApiModelProperty(example = "구로삶터지역자활센터 20주년 기념전시", value = "news 소제목", required = true)
    private String subTitle;

    @ApiModelProperty(example = "전시문의 : 문화온도 씨도시", value = "news 정보에 대한 설명", required = true)
    private String informationContent;

    @ApiModelProperty(example = "노동의 땀방울을 춤과 흥으로 표현한 정인하 그림책\n밥춤과 어우러진 전시", value = "news 대한 설명", required = true)
    private String content;



    public News toEntity(List<String> imagesParam){
        log.info("toEntity 실행");
        return News.builder()
                .title(title)
                .subTitle(subTitle)
                .informationContent(informationContent)
                .content(content)
                .images(imagesParam)
                .isShow(true)
                .build();
    }

}
