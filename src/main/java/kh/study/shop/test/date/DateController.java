package kh.study.shop.test.date;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //페이지는 이동하지 않고 데이터만 나온다.
@RequestMapping("/date")
public class DateController {
	
	//현재 날짜
	@GetMapping("/test1")
	public String test1() {
		
		//Calender 사용
		Calendar cal = Calendar.getInstance();
		
		//년도
		int year = cal.get(Calendar.YEAR); 
		//월
		int month = cal.get(Calendar.MONTH) + 1; //배열로 되어 있어서 +1 해주어야 한다.
		
		String monthStr = month / 10 == 0 ? "0" + month : month + "";
		
		//일
		int date = cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : date + "";
		
		
		String nowDate = year + "-" + monthStr + "-" + dateStr;
		System.out.println(nowDate);
		
		return nowDate;
	
	}
	
}
