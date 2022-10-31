package kh.study.shop.member.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void join(MemberVO memberVO) {
		sqlSession.insert("memberMapper.join", memberVO);
	}

	@Override
	public MemberVO login(MemberVO memberVO) {
		return sqlSession.selectOne("memberMapper.login", memberVO);
	}

	@Override
	public MemberVO selectMemberInfo(String memberId) {
		return sqlSession.selectOne("memberMapper.selectMemberInfo", memberId);
	}

	@Override
	public List<BuyVO> getBuyList(String memberId) {
		return sqlSession.selectList("memberMapper.getBuyList", memberId);
	}

	
	
}
