package kh.study.shop.item.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVO {
	private String itemCode;
	private String itemName;
	private int itemPrice;
	private String itemComment;
	private String regDate;
	private int itemStock;
	private String cateCode;
	private String itemStatus;
	
	private String cateName;
	
	private List<ImgVO> imgList;
	private CategoryVO cateInfo;
	
}
