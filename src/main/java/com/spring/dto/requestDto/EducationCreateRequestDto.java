package com.spring.dto.requestDto;

import com.spring.model.Books;
import com.spring.model.Education;
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
public class EducationCreateRequestDto {
    // Model 에 보이는 설명들.
    @ApiModelProperty(example = "35000", value = "숫자만 입력", required = true)
    private int price;

    @ApiModelProperty(example = "심리적 독립을 위한 스케치", value = "교육 타이틀", required = true)
    private String title;

    @ApiModelProperty(example = "다함께 재밌게 놀아보아요", value = "교육 서브 타이틀", required = false)
    private String subTitle;

    @ApiModelProperty(example = "여행 드오링 키트를 제공하여...", value = "교육에 대한 설명", required = true)
    private String content;

    @ApiModelProperty(example = "teenager", value = "누구와 함께인지", required = true)
    private String withWho; // 누구랑 함께 하는지... (일단 컬럼으로 넣어주자..)

    @ApiModelProperty(example = "구로아이쿱생협에서 진행한 1인 독립가구를 위한 드로잉 프로그램", value = "프로그램 개요", required = true)
    private String introduction; // 책 일련번호


    public Education toEntity(List<String> imagesParam){
        return Education.builder()
                .price(price)
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .withWho(withWho)
                .introduction(introduction)
                .isShow(true)
                .images(imagesParam)
                .build();
    }

}
