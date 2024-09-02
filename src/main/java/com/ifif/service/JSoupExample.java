package com.ifif.service;

import com.ifif.entity.Item;
import com.ifif.entity.ItemImg;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class JSoupExample {

    private final ItemService itemService;
    private final ItemImgService itemImgService;

    @Autowired
    public JSoupExample(ItemService itemService, ItemImgService itemImgService) {
        this.itemService = itemService;
        this.itemImgService = itemImgService;
    }

    @Transactional
    public void crawlAndSaveItems() throws IOException {
        // 크롤링할 웹페이지 URL
        String baseUrl = "http://liquorstore21.co.kr/bbs/board.php?bo_table=product1&page=";
        String detailBaseUrl = "http://liquorstore21.co.kr/bbs/board.php?bo_table=product1&wr_id=";

        // 페이지 번호 설정 (예: 1~5페이지 크롤링)
        int totalPages = 5; // 크롤링할 총 페이지 수 설정

        // 각 페이지에 대해 반복
        for (int page = 1; page <= totalPages; page++) {
            // 현재 페이지의 URL
            String url = baseUrl + page;

            // Jsoup을 사용하여 웹페이지를 가져옴
            Document document = Jsoup.connect(url).get();

            // "gall_li" 클래스를 가진 모든 요소를 선택
            Elements products = document.getElementsByClass("gall_li");

            // 각 제품에 대해 반복
            for (Element product : products) {
                // 이미지 URL 추출
                Element imgElement = product.selectFirst(".gall_href img");
                String imageUrl = imgElement != null ? imgElement.attr("src") : "이미지 없음";

                // 제품 이름 추출
                Element titleElement = product.selectFirst(".gall_text_href a");
                String productName = titleElement != null ? titleElement.text() : "이름 없음";

                // 가격 추출 (주어진 CSS 선택자 사용)
                Element priceElement = product.selectFirst("ul > li.gall_text_href > span");
                String productPriceStr = priceElement != null ? priceElement.text().replace(",", "").replace(".", "").replace("/", "").replace("원", "").trim() : "0";

                // 가격이 "품절"인 경우 예외 처리
                if (productPriceStr.isEmpty() || productPriceStr.equals("품절")) {
                    productPriceStr = "0"; // 기본값 설정 (예: 0)
                }

                int productPrice;
                try {
                    productPrice = Integer.parseInt(productPriceStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                // 상세 페이지 URL을 구성하고 상세 정보 추출
                String detailUrl = detailBaseUrl + extractWrId(product); // wr_id 값을 추출하여 URL에 추가
                String productDetail = fetchProductDetail(detailUrl);

                // 중복 체크: 데이터베이스에 이미 존재하는지 확인
                if (itemService.existsByNameAndPrice(productName, productPrice)) {
                    // 이미 존재하는 아이템이므로 건너뜁니다.
                    continue;
                }

                // Item 객체 생성
                Item item = new Item();
                item.setItemNm(productName);
                item.setPrice(productPrice);
                item.setStockNumber(100); // 기본 재고 수량 설정
                item.setItemDetail(productDetail); // 크롤링한 상세 정보 설정
                item.setDataSource("CRAWLED"); // 데이터 소스 설정 (크롤링 데이터임을 표시)

                // 데이터베이스에 저장
                itemService.saveItem(item);

                // 이미지 저장 (ItemImg)
                if (!"이미지 없음".equals(imageUrl)) {
                    ItemImg itemImg = new ItemImg();
                    itemImg.setItem(item);
                    itemImg.setRepImgYn("Y"); // 첫 번째 이미지를 대표 이미지로 설정

                    // 크롤링된 이미지 URL 저장 메서드 호출
                    itemImgService.saveCrawledItemImg(itemImg, imageUrl);
                }
            }
        }
    }

    // wr_id 추출 함수 (제품의 상세 링크에서 추출)
    private String extractWrId(Element product) {
        Element linkElement = product.selectFirst(".gall_text_href a");
        if (linkElement != null) {
            String href = linkElement.attr("href");
            return href.substring(href.indexOf("wr_id=") + 6); // wr_id= 다음 부분을 추출
        }
        return "";
    }

    // 상세 페이지에서 상세 정보를 가져오는 함수
    private String fetchProductDetail(String detailUrl) throws IOException {
        Document detailDocument = Jsoup.connect(detailUrl).get();
        Elements detailElements = detailDocument.select("div#bo_v_con p");
        StringBuilder productDetail = new StringBuilder();

        for (Element detailElement : detailElements) {
            productDetail.append(detailElement.text()).append("\n");
        }

        return productDetail.toString().trim();
    }
}
