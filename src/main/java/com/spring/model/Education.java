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
public class Education extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true)
    private int price = 0; // 프로그램 가격

    @Column(nullable = false)
    private String title; // 프로그램 제목

    @Column(nullable = true)
    private String subTitle; // 부제목

    @Column(nullable = false)
    private String content; // 프로그램 내용

    @Column(nullable = false)
    private String withWho; // 누구랑 함께 하는지... (일단 컬럼으로 넣어주자..)

    @Column(nullable = false)
    private String introduction; // 프로그램 개요

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 이미지들

    @Column(nullable = true)
    private Boolean isShow = true;

}