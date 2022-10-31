package kh.study.shop.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.admin.service.AdminService;
import kh.study.shop.config.ShopDateUtil;
import kh.study.shop.config.UploadFileUtil;
import kh.study.shop.item.vo.CategoryVO;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
	@ModelAttribute
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		model.addAttribute("menu", menu);
		//사용중인 카테고리 목록조회
		//model.addAttribute("cateListInUse", adminService.selectCateListInUse());
	}
	
	
	//상품 등록 페이지
	@GetMapping("/regItem")
	public String regItem(ItemVO itemVO, Model model) {
		//전체 카테고리 목록 조회
		model.addAttribute("cateListAll", adminService.selectCateListAll());
		
		
		return "content/admin/reg_item";
	}
	
	//카테고리 등록
	@PostMapping("/regCate")
	public String regCate(CategoryVO categoryVO) {
		adminService.insertCate(categoryVO);
		return "redirect:/admin/regItem"; 
	}
	
	//Ajax 함수 실행
	@ResponseBody
	@PostMapping("/changeStatusAjax")
	public void changeStatus(CategoryVO categoryVO) {
		adminService.updateStatus(categoryVO);
		
	}
	
	//상품 등록
	//일반적인 데이터는 커멘드 객체로 전달 받는다.
	//첨부파일 데이터는 MultipartFile 객체를 통해 전달받아야 한다.
	@PostMapping("/regItem")
	public String regItemProcess(ItemVO itemVO
								, MultipartFile mainImg
								, List<MultipartFile> subImgs) {
		
		//단일 이미지 파일 첨부 - 메인이미지
		ImgVO uploadInfo =  UploadFileUtil.uploadFile(mainImg);
		//다중 이미지 파일 첨부 - 서브이미지
		List<ImgVO> uploadList =  UploadFileUtil.multiUploadFile(subImgs);
		
		uploadList.add(uploadInfo);
		
		//상품 정보 insert
		//다음에 들어간 ITEM_CODE를 조회하고,
		String nextItemCode = adminService.getNextItemCode();
		itemVO.setItemCode(nextItemCode);
		
		//이미지 정보를 insert하기 위한 데이터를 가진 uploadList에 
		//조회한 ITEM_CODE도 넣어준다.
		for(ImgVO vo : uploadList) { //uploadList는 IMG_CODE빼고 모든 데이터 들어있음
			vo.setItemCode(nextItemCode); 
		}
		
		itemVO.setImgList(uploadList); //첨부파일에 대한 정보도 setter로 가져간다.
		
		//조회한 ITEM_CODE로 INSERT 진행
		//상품 정보 insert + 이미지 정보 insert
		adminService.insertItem(itemVO);// 데이터베이스에 상품등록하는 기능
		
		//상품의 이미지 정보 insert
		//IMG_CODE, 원본파일명, 첨부한파일명, IS_MAIN, ITEM_CODE
		
		
		return "redirect:/admin/regItem"; 
	}
	
	//Ajax 함수 실행
	@ResponseBody // 데이터 전달 하기 위해서
	@PostMapping("/selectCategoryListInUseAjax")
	public List<CategoryVO> selectCategoryListInUseAjax() {
		List<CategoryVO> cateList = adminService.selectCateListInUse();
		
		return cateList;
	}
	
	//회원권한설정 페이지
	@GetMapping("/memberRoleSet")
	public String memberRoleSet(Model model) {
		model.addAttribute("memberList", adminService.selectMemberList());
		
		return "content/admin/set_manage";
	}
	
	//Ajax 함수 실행
	@ResponseBody
	@PostMapping("/selectMemberDetail")
	public MemberVO selectMemberDetail(String memberId) {
		return adminService.selectMemberDetail(memberId);
		
	}
	
	//상품관리 페이지
	@RequestMapping("/itemManage")
	public String itemManage(@RequestParam Map<String, String> paramMap, Model model) {
		//paramMap.put("itemName", "book"); 내부적으로 실행
		System.out.println("상품명 : " + paramMap.get("itemName"));
		System.out.println("카테고리 코드 : " + paramMap.get("cateCode"));
		System.out.println("재고 : " + paramMap.get("itemStock"));
		System.out.println("fromDate : " + paramMap.get("fromDate"));
		System.out.println("toDate : " + paramMap.get("toDate"));
		System.out.println("상품상태 : " + paramMap.get("itemStatus"));
		
		
		model.addAttribute("itemList", adminService.selectItemListforAdmin(paramMap));
		model.addAttribute("cateList", adminService.selectCateListAll());
		
		//현재 날짜
		String nowDate = ShopDateUtil.getNowDateToString("-"); //2022-10-10 
		//한달 전 날짜
		String beforeDate = ShopDateUtil.getBeforeMonthDateToString();
		
		//넘어오는 fromDate가 없다면 한달 전 날짜로 세팅
		if(paramMap.get("fromDate") == null) {
			paramMap.put("fromDate", beforeDate);
		}
		if(paramMap.get("toDate") == null) {
			paramMap.put("toDate", nowDate);
		}
		
//		model.addAttribute("nowDate", nowDate);
//		model.addAttribute("beforeDate", beforeDate);
		
		model.addAttribute("paramMap", paramMap);
		
		return "content/admin/item_manage";
	}
	
	//Ajax 함수 실행 - 상품 재고변경 버튼 클릭시 실행
	@ResponseBody
	@PostMapping("/updateStockAjax")
	public void updateStock(ItemVO itemVO) {
		adminService.updateStock(itemVO);
	}

	

}

