package kh.study.shop.buy.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.shop.buy.service.BuyService;
import kh.study.shop.buy.vo.BuyDetailVO;
import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.service.ItemService;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.service.MemberService;

@Controller
@RequestMapping("/buy")
public class BuyController {
	
	@Resource(name = "buyService")
	private BuyService buyService;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "itemService")
	private ItemService itemService;
	
	//상품상세 -> 바로구매
	@PostMapping("/buyInfoDirect")
	public String buyInfoDirect(String itemCode, int buyAmount, Model model, Authentication authentication) {
		ItemVO itemVO = itemService.selectItemDetailList(itemCode);
		model.addAttribute("item", itemVO);
		model.addAttribute("buyAmount", buyAmount);
		
		//로그인한 회원의 정보 추출
		User user = (User)authentication.getPrincipal();
		
		//회원정보 조회
		model.addAttribute("memberInfo", memberService.selectMemberInfo(user.getUsername()));
		
		return "content/buy/buy_info";
	}
	
	//장바구니 -> 선택구매
	@PostMapping("/buyInfo")
	public String buyInfo(String cartCodes, CartVO cartVO, Model model, Authentication authentication) {
		String [] cartCodeArr = cartCodes.split(",");
		List<String> cartCodeList = Arrays.asList(cartCodeArr);
		cartVO.setCartCodeList(cartCodeList);
		
		//구매 예정 목록 조회
		model.addAttribute("buyingList", buyService.selectBuyingList(cartVO));
		
		//로그인한 회원의 정보 추출
		User user = (User)authentication.getPrincipal();
		
		//회원정보 조회
		model.addAttribute("memberInfo", memberService.selectMemberInfo(user.getUsername()));//로그인한 사람의 아이디를 받아온다
		
		return "content/buy/buy_info";
	}
	
	//바로구매
	@PostMapping("/insertDirect")
	public String insertDirect(String itemCode, int buyAmount, int itemPrice, Authentication authentication) {
		String buyCode = buyService.selectNextBuyCode();
		User user = (User)authentication.getPrincipal();
		
		BuyVO buyVO = new BuyVO();
		buyVO.setBuyCode(buyCode);
		buyVO.setMemberId(user.getUsername());
		buyVO.setTotalPrice(itemPrice * buyAmount);
		
		List<BuyDetailVO> buyDetailList = new ArrayList<>();
		BuyDetailVO buyDetailVO = new BuyDetailVO();
		buyDetailVO.setBuyCode(buyCode);
		buyDetailVO.setItemCode(itemCode);
		buyDetailVO.setBuyAmount(buyAmount);
		buyDetailList.add(buyDetailVO);
		
		buyVO.setBuyDetailList(buyDetailList);
		
		buyService.insertBuy(buyVO, null);
		
		return "redirect:/item/list";
	}
	
	//장바구니에서 구매
	@PostMapping("/insert")
	public String insertBuy(String[] cartCodes, CartVO cartVO, Authentication authentication) {
		//insert 되어야하는 buy_code 조회
	 	String buyCode = buyService.selectNextBuyCode();
		
	 	//totalPrice, itemCode들, cartAmount들 - cartCodes로 가져옴
	 	List<String> cartCodeList = Arrays.asList(cartCodes);
	 	cartVO.setCartCodeList(cartCodeList);
	 	List<CartVO> cartList = buyService.getCartInfoForBuy(cartVO);
	 	List<BuyDetailVO> buyDetaiList = new ArrayList<>();
	 	
	 	int totalPrice = 0;
	 	for(CartVO e : cartList) {
	 		totalPrice = totalPrice + e.getTotalPrice();
	 	
	 		BuyDetailVO vo = new BuyDetailVO();
	 		vo.setItemCode(e.getItemCode());
	 		vo.setBuyAmount(e.getCartAmount());
	 		buyDetaiList.add(vo);
	 	}
	 	
	 	//memberId
	 	User user = (User)authentication.getPrincipal();
	 	
	 	BuyVO buyVO = new BuyVO();
	 	buyVO.setBuyCode(buyCode);
	 	buyVO.setMemberId(user.getUsername());
	 	buyVO.setTotalPrice(totalPrice);
	 	buyVO.setBuyDetailList(buyDetaiList);  //buyDetailVO -> itemCode, buyAmount
	 	
	 	buyService.insertBuy(buyVO, cartVO);
	 	
		return "redirect:/item/list";
	}
	
	
}

