package com.ifif.service;

import com.ifif.entity.ItemImg;
import com.ifif.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {
    @Value("${itemImgLocation}") //application.properties에 itemImgLocation
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    // 기존 사용자 등록 이미지 저장 메서드
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";  // 실제 저장될 이미지 파일명
        String imgUrl = "";  // 이미지 조회 경로
        System.out.println("123123123123");
        if (!StringUtils.isEmpty(oriImgName)) {//oriImgName 문자열로 비어 있지 않으면 실행
            System.out.println("******");
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "images/item/" + imgName;
        }
        System.out.println("1111");
        // 이미지 파일 저장 로직 (예: 로컬 파일 시스템, AWS S3 등)
        // ...
        // 이미지 정보 업데이트
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    // 이미지 업데이트 메서드
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            if (savedItemImg.getOriImgName() != null) {
                // 기존 이미지 삭제 로직 (예: 로컬 파일 시스템, AWS S3에서 삭제)
                // ...
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = "";  // 새로 저장할 이미지 파일명
            String imgUrl = "";  // 새 이미지의 조회 경로

            // 파일 업로드 코드 추가
            // ...

            // 새 이미지 정보 업데이트
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

    // 크롤링된 이미지 저장 메서드
    public void saveCrawledItemImg(ItemImg itemImg, String imgUrl) {
        itemImg.setCrawledImg(imgUrl);
        itemImgRepository.save(itemImg);
    }

}
