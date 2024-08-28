package com.ifif.controller;

import com.ifif.dto.ItemSearchDto;
import com.ifif.dto.MainItemDto;
import com.ifif.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WhiskeyController {
    private final ItemService itemService;

    @GetMapping(value = "/whiskey")
    public String itemRec(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 100);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        System.out.println(items.getNumber()+"!!!!!!!!!!");
        System.out.println(items.getTotalPages()+"#########");
        //items.getContent().get(0).getImgUrl()
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "item/whiskey";
    }

}
