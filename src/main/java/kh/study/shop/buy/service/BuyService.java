package kh.study.shop.buy.service;

import java.util.List;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

public interface BuyService {
	
//구매할 목록 조회	
List<CartVO> selectBuyingList(CartVO cartVO);

String selectNextBuyCode();
	
//장바구니 선택구매
void insertBuy(BuyVO buyVO, CartVO cartVO);

List<CartVO> getCartInfoForBuy(CartVO cartVO);

}
