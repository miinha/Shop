package kh.study.shop.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.config.MemberRole;
import kh.study.shop.config.MemberStatus;
import kh.study.shop.member.service.MemberService;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder encoder; 

	//회원가입
	@PostMapping("/join")
	public String join(MemberVO memberVO) {
		System.out.println(memberVO);
		
		memberVO.setMemberStatus(MemberStatus.ACTIVE.toString()); //'ACTIVE'가 문자로 데이터에 들어간다.
		memberVO.setMemberRole(MemberRole.MEMBER.toString());
		String pw = encoder.encode(memberVO.getMemberPw());
		memberVO.setMemberPw(pw);
		
		memberService.join(memberVO);
		
		return "redirect:/item/list";
	}
	
//	//로그인
//	@PostMapping("/login")
//	public String login(HttpSession session, MemberVO memberVO) {
//		MemberVO loginInfo = memberService.login(memberVO);
//		
//		if(loginInfo != null) { //로그인 성공시 세션에 데이터 들어간다.
//			session.setAttribute("loginInfo", loginInfo);
//		}
//		
//		return "content/member/login_result";
//	}
	
	@GetMapping("/loginResult")
	public String loginResult() {
		return "content/member/login_result";
	}
	
	//Ajax로 로그인
	@ResponseBody
	@PostMapping("/ajaxLogin")
	public boolean ajaxLogin(MemberVO memberVO, HttpSession session) {
		MemberVO loginInfo = memberService.login(memberVO);
		
		if(loginInfo != null) { 
			session.setAttribute("loginInfo", loginInfo);
		}
		
		return loginInfo == null ? false : true; //ajax의 return은 페이지 이동이 아니라 데이터를 구현할수 있다.
		//참 또는 거짓이므로 자료형에 boolean으로 작성해준다.
	}
	
	//구매목록조회
	@GetMapping("/buyList")
	public String buyList(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		//회원정보 조회
		model.addAttribute("buyList", memberService.getBuyList(user.getUsername()));
		
		return "content/member/buy_list";
	}
	
}
