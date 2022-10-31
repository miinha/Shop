package kh.study.shop.config;

import java.util.Calendar;

public class ShopDateUtil {

	//오늘 날짜 구하기
	//오늘 날짜를 문자열로 리턴
	public static String getNowDateToString() {
		Calendar cal = Calendar.getInstance();
		
		//년도
		int year = cal.get(Calendar.YEAR); 
		//월
		int month = cal.get(Calendar.MONTH) + 1; //배열로 되어 있어서 +1 해주어야 한다.
		
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		//일
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + "" + monthStr + "" + dateStr;
		
		return nowDate; //20221005
	}
	
//	getNowDateToString(); 
//	getNowDateToString("-"); -> 문자열이 들어가므로 format에 -들어가서 실행된다.
	
	//오늘 날짜를 문자열로 리턴 + 포맷지정 20221005, 2022-10-05, 2022/10/05
	public static String getNowDateToString(String format) {
		Calendar cal = Calendar.getInstance();
		
		//년도
		int year = cal.get(Calendar.YEAR); 
		//월
		int month = cal.get(Calendar.MONTH) + 1; //배열로 되어 있어서 +1 해주어야 한다.
		
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		//일
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + format + monthStr + format + dateStr;
		
		return nowDate; //2022-10-05
	}
		
	//한달 전 날짜를 문자열로 리턴
	public static String getBeforeMonthDateToString() {
		Calendar cal = Calendar.getInstance();
		
		//년도
		int year = cal.get(Calendar.YEAR); 
		//월
		int month = cal.get(Calendar.MONTH);
		
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		//일
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		String nowDate = year + "-" + monthStr + "-" + dateStr;
		
		return nowDate; //2022-10-05
	}
}
