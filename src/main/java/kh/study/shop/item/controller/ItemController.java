package kh.study.shop.item.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.shop.item.service.ItemService;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource(name = "itemService")
	private ItemService itemService; 
	
	//상품 목록 페이지
	//boolean loginFail : 로그인 실패시에만 데이터를 true로 받아온다.
	@GetMapping("/list")
	public String itemList(boolean isLoginFail, Model model
							, @CookieValue(required = false) String imgName) { 
		
		//상품목록 조회
		List<ItemVO> itemList = itemService.selectItemList();
		model.addAttribute("itemList", itemList);
		
		//System.out.println("~~~~~~" + loginFail); //loginFail의 데이터가 넘어올때만 비밀번호 잘못 입력 -> 이 경우에만 모달창 실행시킨다.
		//로그인 성공, 실패 여부 데이터를 html에 전달 -> 전달한 데이터를  js에서 쓰기 위해서?
		model.addAttribute("isLoginFail", isLoginFail);
		
		String[] cookieArr = imgName.split(",");
		List<String> cookieList = Arrays.asList(cookieArr);
		
		model.addAttribute("cookie_imgName", cookieList); 
		
//		for(ItemVO e : itemList) {
//			System.out.println(e);
//		}
		
		return "content/item/item_list";
	}
	
	//상품 상세 페이지
	@GetMapping("/itemDetail")
	public String itemDetail(Model model, String itemCode 
							, HttpServletResponse response
							, @CookieValue(required = false, name = "imgName") String cookieImgName) {
		ItemVO item = itemService.selectItemDetailList(itemCode);
		model.addAttribute("itemInfo", item);
		
		//상세보기한 상품의 이름을 쿠키에 저장한다.
		//javax.servlet.http
		//쿠키 데이터 생성
		Cookie cookie = new Cookie("name", "java");
		
		//매개변수 : 쿠키 데이터 유지 초(시간단위)
		//cookie.setMaxAge(0); //브라우저가 종료되면 쿠키데이터 소멸
		//cookie.setMaxAge(60 * 60 * 24); 하루 
		cookie.setMaxAge(60); //1분
		
		//생성된 쿠키 데이터를 클라이언트에 전달
		response.addCookie(cookie); //쿠키를 저장.
		
		Cookie cookie2 = new Cookie("age", "20");
		response.addCookie(cookie2);
		
		//상세보기 한 상품의 대표 이미지를 쿠키로 저장
		String imgName = "";
		for(ImgVO img : item.getImgList()) {
			if(img.getIsMain().equals("Y")) {
				imgName = img.getAttachedName();
			}
		}
		
		//java.net
		//인코딩만 진행하면 공백이 + 문자로 바뀐다. ex> "안 녕" -> "안+녕" : +를 공백으로 바꾸어주어야 한다.
		String encodeImgName = null;
		
		if(cookieImgName != null) {
			encodeImgName = getEncodeStr(cookieImgName + "," + imgName);
		}
		else {
			encodeImgName = getEncodeStr(imgName);
		}
		
		Cookie cookie_imgName = new Cookie("imgName", encodeImgName);
		response.addCookie(cookie_imgName);
		
		return "content/item/item_detail";
	}
	
	
	public String getEncodeStr(String str) {
		//java.net
		//인코딩만 진행하면 공백이 + 문자로 바뀐다. ex> "안 녕" -> "안+녕" : +를 공백으로 바꾸어주어야 한다.
		String result = "";
		try {
			result = URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
