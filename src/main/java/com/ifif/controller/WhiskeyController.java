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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WhiskeyController {
    private final ItemService itemService;

    @GetMapping(value = "/whiskey")
    public String itemRec(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        // 크롤링한 데이터만 가져오기
        List<Item> crawledItems = itemService.getCrawledItems();

        model.addAttribute("items", crawledItems);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/whiskey";
    }
}
