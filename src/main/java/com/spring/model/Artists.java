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
public class Artists extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String createdDate; // 아티스트 데뷔일?

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name; // 아티스트 데뷔일?

    @Column(nullable = false)
    private String email; // 아티스트 데뷔일?

    @Column(nullable = false)
    private String job; // 아티스트 데뷔일?

    @Column(nullable = false)
    private String image; // 아티스트 사진

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> images = new ArrayList<>(); // 대표작 이미지들

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduce; // 책 제본 ex 상철

    @Column(nullable = true)
    private Boolean isShow = true;
}