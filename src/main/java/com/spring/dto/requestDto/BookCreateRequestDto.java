package com.spring.dto.requestDto;

import com.spring.model.Books;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class BookCreateRequestDto {
    // Model 에 보이는 설명들.
    @ApiModelProperty(example = "35000", value = "숫자만 입력", required = true)
    private int price;

    @ApiModelProperty(example = "성애쌤의 해외 봉사 라이프", value = "책 제목", required = true)
    private String title;

    @ApiModelProperty(example = "철수가 무겁게 입을 열었다\n여름이었다.", value = "책에 대한 설명", required = true)
    private String content;

    @ApiModelProperty(example = "김성애", value = "유저의 닉네임", required = true)
    private String createdWho;

    @ApiModelProperty(example = "3.5", value = "책의 가로 길이", required = true)
    private float width; // 가로 길이
    @ApiModelProperty(example = "3.5", value = "책의 세로 길이", required = true)
    private float height; // 세로 길이
    @ApiModelProperty(example = "3.5", value = "책의 두께", required = true)
    private float depth; // 두께
    @ApiModelProperty(example = "3.5", value = "차차 출판", required = true)
    private String publishingHouse; // 출판사사
    @ApiModelProperty(example = "abcd-efg-123-4567", value = "책의 일련번호", required = true)
    private String ISBN; // 책 일련번호
    @ApiModelProperty(example = "상철", value = "책의 제본", required = true)
    private String bookBinding; // 책 제본
    @ApiModelProperty(example = "167", value = "책의 페이지 수", required = true)
    private int pages;
    @ApiModelProperty(example = "알아서 입력하시오", value = "책의 목차", required = true)
    private String tableOfContent;
    @ApiModelProperty(example = "https", value = "네이버 스토어 링크", required = true)
    private String nPayLink;
    @ApiModelProperty(example = "초판", value = "초판", required = true)
    private String edition;
    @ApiModelProperty(example = "1", value = "1쇄", required = true)
    private int editionNumber;
    @ApiModelProperty(example = "2021-05-15", value = "책이 만들어진 날", required = true)
    private String createdDate;


    public Books toEntity(List<String> imagesParam){
        log.info("toEntity 실행");
        return Books.builder()
                .price(price)
                .title(title)
                .content(content)
                .createdWho(createdWho)
                .width(width)
                .height(height)
                .depth(depth)
                .publishingHouse(publishingHouse)
                .ISBN(ISBN)
                .nPayLink(nPayLink)
                .tableOfContent(tableOfContent)
                .isShow(true)
                .images(imagesParam)
                .bookBinding(bookBinding)
                .edition(edition)
                .editionNumber(editionNumber)
                .createdDate(createdDate)
                .build();
    }

}
