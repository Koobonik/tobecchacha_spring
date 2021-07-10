package com.spring.dto.requestDto;

import com.spring.model.MainNotice;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class MainNoticeCreateRequestDto {

    @ApiModelProperty(example = "지금 우리가 하는 일", value = "공지 내용", required = true)
    private String description;

    @ApiModelProperty(example = "https://instagram.com", value = "링크", required = false)
    private String link;

    @ApiModelProperty(example = "true", value = "공지를 노출할지 말지에 대한 플래그 값", required = true)
    private boolean isShow;


    public MainNotice toEntity(String image){
        log.info("toEntity 실행");
        return MainNotice.builder()
                .image(image)
                .description(description)
                .isShow(isShow)
                .build();
    }

}
