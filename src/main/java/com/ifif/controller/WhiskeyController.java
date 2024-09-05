package com.ifif.controller;

import com.ifif.dto.ItemSearchDto;
import com.ifif.dto.MainItemDto;
import com.ifif.entity.Item;
import com.ifif.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WhiskeyController {
    private final ItemService itemService;

    @GetMapping(value = "/whiskey")
    public String itemRec(ItemSearchDto itemSearchDto, Optional<Integer> page,
                          @RequestParam(value = "searchQuery", required = false) String searchQuery,
                          Model model) {
        // 크롤링한 데이터만 가져오기
        List<Item> crawledItems = itemService.getCrawledItems();

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            //검색어가 있을 경우 검색 로직 수행
            crawledItems = itemService.searchCrawledItems(searchQuery);
        }else {
            //검색어가 없을경우 모든 크롤링된 데이터 가져오기
            crawledItems = itemService.getCrawledItems();
        }
        model.addAttribute("items", crawledItems);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("searchQuery",searchQuery);
        model.addAttribute("maxPage", 5);

        //검색 결과가 없는 경우 메시지 추가
        if (crawledItems.isEmpty()){
            model.addAttribute("searchMessage","검색한 물품이 없습니다.");
        }
        return "item/whiskey";
    }
}
