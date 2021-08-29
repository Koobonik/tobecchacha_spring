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
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String title; // 책 제목

    @Column(columnDefinition = "TEXT")
    private String subTitle = ""; // 소제목

    @Column(nullable = false)
    private String informationContent; // 책 내용

    @Column(nullable = false)
    private String content; // 책 내용

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 이미지들

    @Column(nullable = true)
    private Boolean isShow = true;


}