package kh.study.shop.buy.vo;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyVO {
	private String buyCode;
	private String memberId;
	private String buyDate;
	private int totalPrice;
	
	private List<BuyDetailVO> buyDetailList;
	private int buyCnt;
}
