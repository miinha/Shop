package kh.study.shop.cart.service;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.cart.vo.CartVO;


@Service("cartService")
public class CartServiceImpl implements CartService{

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void insertCart(CartVO cartVO) {
		sqlSession.insert("cartMapper.insertCart", cartVO);
	}

	@Override
	public List<CartVO> selectCartList(String memberId) {
		return sqlSession.selectList("cartMapper.selectCartList", memberId);
	}

	@Override
	public void cartDelete(CartVO cartVO) {
		sqlSession.delete("cartMapper.cartDelete", cartVO);
	}

	@Override
	public void updateAmount(CartVO cartVO) {
		sqlSession.update("cartMapper.updateAmount", cartVO);
	}

	

	
	
	
}
