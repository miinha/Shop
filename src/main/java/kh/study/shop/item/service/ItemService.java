package kh.study.shop.item.service;

import java.util.List;

import kh.study.shop.item.vo.ItemVO;

public interface ItemService {
	
	//상품 목록 조회
	List<ItemVO> selectItemList();
	
	//상품 상세 목록 조회
	ItemVO selectItemDetailList(String itemCode);
	
	
	
}
