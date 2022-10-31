package kh.study.shop.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.item.vo.ImgVO;

public class UploadFileUtil {
	//파일이 첨부될 경로
	private static final String UPLOAD_PATH = "D:\\workspaceSTS\\Shop\\src\\main\\resources\\static\\images\\";
	
	//파일첨부
	public static ImgVO uploadFile(MultipartFile mainImg) {
		String fileName = null;
		String originFileName = null;
		
		//실제 첨부파일이 있을때만 첨부 기능 실행
		if(!mainImg.isEmpty()) {
			
			//첨부하려는 원본 파일명
			originFileName = mainImg.getOriginalFilename();
			//파일명 중복을 막기 위해 랜덤한 파일명을 문자열로 생성
			String uuid = UUID.randomUUID().toString();
			//확장자 추출
			String extension = originFileName.substring(originFileName.lastIndexOf(".")); // "apple.jpg".substring(3) -> 3번째 글자부터 보여준다(0,1,2,3~~~) : "le.jpg" 
			
			//첨부될 파일명 생성
			fileName = uuid + extension; //"ss" + ".jpg"
			
			try {
				//파일 객체 생성
				File file = new File(UPLOAD_PATH + fileName);
				
				mainImg.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//리턴해야하는 데이터를 저장하기 위한 객체
		ImgVO imgVO = new ImgVO();
		imgVO.setAttachedName(fileName);
		imgVO.setOriginName(originFileName);
		imgVO.setIsMain("Y");
		
		return imgVO;
	}
	
	//다중 파일첨부
	public static List<ImgVO> multiUploadFile(List<MultipartFile> subImgs) {
		List<ImgVO> list = new ArrayList<>();
		
		//첨부된 파일의 개수만큼 첨부를 시작
		for( MultipartFile subImg : subImgs) {
			ImgVO vo = uploadFile(subImg);
			vo.setIsMain("N"); //list에 담기 전에 Y로 들어가 있는 데이터를 N으로 바꿔준다.(메인이미지 아니므로)
			list.add(vo);
		}
		
		return list;
	}
}
