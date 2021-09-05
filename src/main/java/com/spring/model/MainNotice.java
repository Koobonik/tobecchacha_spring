package com.spring.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MainNotice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String image; // 공지 이미지

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // 공지에 대한 설명

    @Column(nullable = true)
    private String link; // 혹시 필요할지도 모르는 링크

    @Column(nullable = false)
    private boolean isShow = true; // 공지를 보여줄지에 대한 플래그

}
