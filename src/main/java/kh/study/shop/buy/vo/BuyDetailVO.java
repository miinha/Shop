package kh.study.shop.buy.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyDetailVO {
   private String buyDetailCode;
   private String itemCode;
   private String buyCode;
   private int buyAmount;
   
   private String cateName;
   private int itemPrice;
   private int buyPrice;
   private String attachedName;
   private String itemName;
	
	
}
