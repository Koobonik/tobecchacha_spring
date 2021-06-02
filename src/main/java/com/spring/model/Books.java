package com.spring.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Books extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int price; // 책 가격

    @Column(nullable = false)
    private String title; // 책 제목

    @Column(nullable = false)
    private String content; // 책 내용

    @Column(nullable = false)
    private String createdWho; // 지은이

    @Column(nullable = false)
    private String createdDate; // 책이 만들어진 날

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 이미지들

    @Column(nullable = true)
    private float width; // 가로 길이
    @Column(nullable = true)
    private float height; // 세로 길이
    @Column(nullable = true)
    private float depth; // 두께
    @Column(nullable = true)
    private String publishingHouse; // 출판사
    @Column(unique = true)
    private String ISBN; // 책 일련번호
    @Column(nullable = false)
    private String bookBinding; // 책 제본 ex 상철
    @Column(nullable = true)
    private int pages;
    @Column(nullable = true)
    private String tableOfContent;
    @Column(nullable = true)
    private String nPayLink;

    @Column(nullable = true)
    private String edition;

    @Column(nullable = true)
    private int editionNumber;

    @Column(nullable = true)
    private Boolean isShow = true;


}