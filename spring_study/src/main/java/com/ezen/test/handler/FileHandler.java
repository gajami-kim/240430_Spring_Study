package com.ezen.test.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.test.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
//사용자가 만드는 class를 spring에게 인지시킬 수 있도록 하는 어노테이션
//new로 객체를 만들어줄 필요없이 spring에서 관리하는 파일과 같이 사용가능하다
@Component
public class FileHandler {

	//실제 파일이 저장되는 경로
	private final String UP_DIR="D:\\_myProject\\_java\\_fileUpload";

	public List<FileVO> uploadFiles(MultipartFile[] files) {
		//리턴 객체 생성
		List<FileVO> flist = new ArrayList<FileVO>();
		
		//MultipartFile[] 받아서 FileVO 형태의 list로 리턴
		//오늘날짜로 경로 생성(가변형태로 저장) 년 월 일 폴더생성
		
		//오늘날짜로 경로 생성
		LocalDate date = LocalDate.now();
		String today = date.toString();
		log.info(today);
		
		//오늘날짜를 폴더형식으로 구성
		today = today.replace("-", File.separator);
		
		//D:\\_myProject\\_java\\_fileUpload\\2024\\04\\29
		File folders = new File(UP_DIR, today);
		//폴더생성 -> mkdir(폴더 1개 생성), mkdirs(하위 폴더까지 구조로 생성)
		//exists : 있는지 없는지 확인(없으면 생성, 있으면 생성x)
		if(!folders.exists()) {
			folders.mkdirs(); //폴더생성(\\를 파일 구분자로 자동인식)
		}
		
		//리스트 설정
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSave_dir(today);
			fvo.setFile_size(file.getSize()); //return long
			
			//getOriginalFilename() : 경로+파일이름. 파일경로를 포함하는 케이스도 있음
			String originalFileName = file.getOriginalFilename();
			String onlyFileName = originalFileName.substring(
					originalFileName.lastIndexOf(File.separator)+1); //실제 파일명만 추출
			fvo.setFile_name(onlyFileName);
			
			//UUID 생성
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			//<-----------여기까지 fvo Setting--------------->
			//bno, file_type
			
			//Disk에 저장
			//Disk에 저장할 파일 객체 생성 -> 저장
			//uuid_fileName / uuid_th_fileName
			String fullFileName = uuidStr+"_"+onlyFileName;
			File storeFile = new File(folders, fullFileName); //저장할 객체
			
			//저장 => 저장 경로 또는 파일이 없다면 IOException 발생
			try {
				file.transferTo(storeFile); //저장

				//파일 타입 결정(이미지만 썸네일 설정)
				if(isImageFile(storeFile)) {
					fvo.setFile_type(1);
					
					//썸네일 생성
					File thumbNail = new File(folders, uuidStr+"_th_"+onlyFileName);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
				
			} catch (Exception e) {
				log.info("file 저장 error");
				e.printStackTrace();
			}
			
			//list에 fvo 추가
			flist.add(fvo);
			
		}
		
		return flist;
	}

	//tika를 활용한 파일 형식 체크 => 이미지 파일이 맞는지 확인
	public boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile); //image/png image/jpg 추출
		return mimeType.startsWith("image")?true:false; //파일명이 이미지로 시작하는지 확인
	}

}

