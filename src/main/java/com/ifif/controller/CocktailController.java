package com.ifif.controller;

import com.ifif.constant.ItemValue;
import com.ifif.dto.ItemFormDto;
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
public class CocktailController {
    private final ItemService itemService;

    @GetMapping(value = "/cocktail")
    public String itemRec(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        // 사용자 등록한 데이터만 가져오기
        List<Item> userItems = itemService.getUserItems();

        model.addAttribute("items", userItems);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/cocktail";
    }
    @GetMapping(value = "/{ITEM1}")
    public String itemRec2( ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

     return "item/cocktail";
    }

}

