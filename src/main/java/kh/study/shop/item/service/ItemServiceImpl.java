package kh.study.shop.item.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.item.vo.ItemVO;

@Service("itemService")

public class ItemServiceImpl implements ItemService{

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<ItemVO> selectItemList() {
		return sqlSession.selectList("itemMapper.selectItemList");
	}

	@Override
	public ItemVO selectItemDetailList(String itemCode) {
		return sqlSession.selectOne("itemMapper.selectItemDetailList", itemCode);
	}
	
	
	
}
