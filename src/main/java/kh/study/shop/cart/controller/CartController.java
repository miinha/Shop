package kh.study.shop.cart.controller;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.cart.service.CartService;
import kh.study.shop.cart.vo.CartVO;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Resource(name = "cartService")
	private CartService cartService; 
	
	//장바구니 등록
	@ResponseBody
	@PostMapping("/regCart")
	public void regCart(CartVO cartVO, Authentication authentication) {
		//로그인한 유저의 정보를 가져 옴
		User user = (User)authentication.getPrincipal();
		cartVO.setMemberId(user.getUsername()); //로그인한 사람의 아이디
		
		cartService.insertCart(cartVO);
	}
	
	//장바구니 목록 페이지로 이동
	@GetMapping("/cartList")
	public String cartList(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		
		List<CartVO> list = cartService.selectCartList(user.getUsername());
		for(CartVO e : list) {
			System.out.println(e);
		}
		
		model.addAttribute("cartList", list);
		
		
		//전체 총 가격 데이터
		int finalPrice = 0;
		for(CartVO cart : list) {
			finalPrice = finalPrice + cart.getTotalPrice();
		}
		model.addAttribute("finalPrice", finalPrice);
		
		return "content/cart/cart_list";
	}
	
	//ajax 수량변경
	@ResponseBody
	@PostMapping("/updateAmount")
	public void updateAmount(CartVO cartVO) {
		cartService.updateAmount(cartVO);
	}
	
	//장바구니 삭제
	@PostMapping("/cartDelete")
	public String cartDelete(String cartCodes) {//"cart_001,cart_002,"
		String[] cartCodeArr = cartCodes.split(",");
		List<String> cartCodeList = Arrays.asList(cartCodeArr);
		
		CartVO cartVO = new CartVO();
		cartVO.setCartCodeList(cartCodeList);		
		
		cartService.cartDelete(cartVO);
		return "redirect:/cart/cartList";
	}
	
	
	
	//dataset 연습 메소드
	@GetMapping("/test")
	public String datasetTest() {
		return "content/cart/test";
	}
	
}
