package kh.study.shop.admin.service;

import java.util.List;
import java.util.Map;

import kh.study.shop.item.vo.CategoryVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.vo.MemberVO;

public interface AdminService {

	//카테고리 목록조회
	List<CategoryVO> selectCateListAll();
	
	//사용중인 카테고리 목록 조회
	List<CategoryVO> selectCateListInUse();
	
	//카테고리 등록
	public void insertCate(CategoryVO categoryVO);
	
	//ajax 상태 변경
	void updateStatus(CategoryVO categoryVO);
	
	//상품 등록 
	void insertItem(ItemVO itemVO);
	
	//회원목록 조회
	List<MemberVO> selectMemberList();
	
	//회원상세조회
	public MemberVO selectMemberDetail(String memberId);
	
	List<ItemVO> selectItemListforAdmin(Map<String, String> map);
	
	void updateStock(ItemVO itemVO);
	
	String getNextItemCode();
	
	
	
}
