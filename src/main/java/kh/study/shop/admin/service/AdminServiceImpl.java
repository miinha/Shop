package kh.study.shop.admin.service;

import java.util.List;
import java.util.Map;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.shop.item.vo.CategoryVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.vo.MemberVO;

@Service("adminService")

public class AdminServiceImpl implements AdminService {
	
	@Autowired
	SqlSessionTemplate sqlSession;

	@Override
	public List<CategoryVO> selectCateListAll() {
		return sqlSession.selectList("adminMapper.selectCateListAll");
	}
	@Override
	public List<CategoryVO> selectCateListInUse() {
		return sqlSession.selectList("adminMapper.selectCateListInUse");
	}

	@Override
	public void insertCate(CategoryVO categoryVO) {
		sqlSession.insert("adminMapper.insertCate", categoryVO);
	}

	@Override
	public void updateStatus(CategoryVO categoryVO) {
		sqlSession.update("adminMapper.updateStatus", categoryVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertItem(ItemVO itemVO) {
		sqlSession.insert("adminMapper.insertItem", itemVO);
		//상품이미지 등록
		sqlSession.insert("adminMapper.insertImages", itemVO);
	}
	@Override
	public List<MemberVO> selectMemberList() {
		return sqlSession.selectList("adminMapper.selectMemberList");
	}
	@Override
	public MemberVO selectMemberDetail(String memberId) {
		return sqlSession.selectOne("adminMapper.selectMemberDetail", memberId);
	}
	@Override
	public List<ItemVO> selectItemListforAdmin(Map<String, String> map) {
		return sqlSession.selectList("adminMapper.selectItemListforAdmin", map);
	}

	@Override
	public void updateStock(ItemVO itemVO) {
		sqlSession.update("adminMapper.updateStock", itemVO);
	}
	@Override
	public String getNextItemCode() {
		return sqlSession.selectOne("adminMapper.getNextItemCode");
	}
	

	
	
}
