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
public class Etc extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title; // 책 제목

    @Column(columnDefinition = "TEXT")
    private String subTitle = ""; // 소제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String informationContent; // 책 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 책 내용

    @Column(nullable = true)
    private String withWho = ""; // 누가 했는지

    @Column(nullable = true, columnDefinition = "TEXT")
    private String mp4File = ""; // 동영상 링크

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 이미지들

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<String> videoLinks = new ArrayList<>(); // 이미지들 텍스트

    @Column(nullable = true)
    private Boolean isShow = true;


}