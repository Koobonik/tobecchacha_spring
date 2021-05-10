package com.spring.model;

import lombok.*;
import javax.persistence.*;
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

    private String ISBN; // 책 일련번호
    @Column(nullable = true)
    private int pages;
    @Column(nullable = true)
    private String tableOfContent;
    @Column(nullable = true)
    private String nPayLink;

}