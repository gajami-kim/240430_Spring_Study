package com.ezen.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component //사용자 클래스를 빈으로 등록
public class FileHandler {

	private final String UP_DIR = "D:\\_myProject\\_java\\_fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		//객체이름의 두번째글자만! 대문자면 jsp가 인지X
		List<FileVO> flist = new ArrayList<>();
		//FileVO 생성, 파일을 저장, 썸네일 저장
		//날짜로 폴더를 생성하여 업로드 파일을 관리
		LocalDate date = LocalDate.now(); //2024-05-03 형태
		String today = date.toString(); //String으로 변환
		today = today.replace("-", File.separator); //-를 파일 경로 표시 \로 변환
		
		//폴더 생성(객체)
		//D:\\_myProject\\_java\\_fileUpload\\2024\\05\\03
		File folders = new File(UP_DIR, today); //기존 경로에 날짜로 폴더 생성
		
		//폴더 생성
		if(!folders.exists()) {
			//mkdir은 하위 폴더 1개 생성
			folders.mkdirs(); //하위의 모든 폴더 생성
		}
		
		//files(배열)를 가지고 객체설정
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename();
			String fileName = originalFileName.substring(
					originalFileName.lastIndexOf(File.separator)+1);
			fvo.setFileName(fileName);
			
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			//기본 fvo 셋팅 완료
			
			//디스크에 저장작업 => 저장 객체를 생성
			String fullFileName = uuidStr+"_"+fileName;
			//D:\\_myProject\\_java\\_fileUpload\\2024\\05\\03\\uuid_name;
			//실제 파일이 저장되려면 첫 경로부터 다 설정되어야 저장됨
			File storeFile = new File(folders, fullFileName);
			
			try {
				file.transferTo(storeFile);
				//썸네일 저장 => 이미지만 썸네일 생성 가능
				//이미지인지 확인 필요
				if(isImageFile(storeFile)) {
					fvo.setFileType(1); //이미지는 타입이 1(내가 결정)
					//썸네일 생성
					File thumbnail = new File(folders+"_th_"+fullFileName);
					Thumbnails.of(storeFile).size(100, 100);
				}
			} catch (Exception e) {
				log.info("파일 저장 오류");
				e.printStackTrace();
			}
			flist.add(fvo);	
		}
		
		return flist;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		//type image/jpg
		String mimeType = new Tika().detect(storeFile); //타입만 가져옴
		return mimeType.startsWith("image")?true:false;
	}
	
}
