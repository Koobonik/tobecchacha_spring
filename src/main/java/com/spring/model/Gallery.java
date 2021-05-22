package com.spring.model;

import lombok.*;

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
public class Gallery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int price; // 책 가격

    @Column(nullable = false)
    private String title; // 책 제목

    @Column(nullable = false)
    private String content; // 책 내용

    @Column(nullable = false)
    private String createdWho; // 작가

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 이미지들

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<String> imagesDescription = new ArrayList<>(); // 이미지들 텍스트

    @Column(nullable = true)
    private float width; // 가로 길이
    @Column(nullable = true)
    private float height; // 세로 길이

    @Column(nullable = true)
    private String nPayLink;

    @Column(nullable = true)
    private Boolean isShow = true;
}