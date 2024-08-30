package com.ifif.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName;  // 이미지 파일명 (사용자 업로드 이미지의 경우)

    private String oriImgName;  // 원본 이미지 파일명

    private String imgUrl;  // 이미지 조회 경로 (크롤링 데이터의 경우 URL 저장)

    private String repImgYn;  // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 이미지 업데이트 메서드
    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    // 크롤링된 이미지 설정 메서드
    public void setCrawledImg(String imgUrl) {
        this.imgUrl = imgUrl;
        this.repImgYn = "Y";  // 크롤링된 이미지를 대표 이미지로 설정
    }
}
