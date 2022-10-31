package kh.study.shop.cart.service;

import java.util.List;

import kh.study.shop.cart.vo.CartVO;

public interface CartService {
	
//장바구니 등록
public void insertCart(CartVO cartVO);
	
//장바구니 목록조회
List<CartVO> selectCartList(String memberId);

//장바구니 삭제
void cartDelete(CartVO cartVO);

void updateAmount(CartVO cartVO);

}
