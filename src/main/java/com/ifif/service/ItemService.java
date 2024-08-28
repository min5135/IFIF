package com.ifif.service;

import com.ifif.dto.ItemFormDto;
import com.ifif.dto.ItemImgDto;
import com.ifif.dto.ItemSearchDto;
import com.ifif.dto.MainItemDto;
import com.ifif.entity.Item;
import com.ifif.entity.ItemImg;
import com.ifif.repository.ItemImgRepository;
import com.ifif.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    public Long saveItem(Item item){
        if (item.getItemDetail() == null || item.getItemDetail().isEmpty()) {
            item.setItemDetail("상세 정보 없음");
        }
        //상품등록
        return itemRepository.save(item).getId();
    }
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)
            throws Exception{
        //상품등록
        Item item = itemFormDto.createItem();

        itemRepository.save(item);
        //이미지 등록
        for(int i =0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0)
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");
            itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        //Entity
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        //DB에서 데이터를 가지고 옵니다.
        //DTO
        List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //왜 DTO 만들었나요??

        for(ItemImg itemimg : itemImgList){
            // Entity -> DTO
            ItemImgDto itemImgDto = ItemImgDto.of(itemimg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        // Item -> ItemFormDto modelMapper
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)
            throws Exception{
        //상품 변경
        Item item = itemRepository.findById(itemFormDto.getId()).
                orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        //상품 이미지 변경
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for(int i =0; i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }
    @Transactional(readOnly = true) // 쿼리문 실행 읽기만 한다.
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto,pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public List<Item> getItemAll(){
        return itemRepository.findAll();

    }

}
