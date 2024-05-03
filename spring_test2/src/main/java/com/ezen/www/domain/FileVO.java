package com.ezen.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class FileVO {
	
	private String uuid;
	private String saveDir;
	private String fileName;
	private int fileType;
	private int bno;
	private long fileSize;
	private String regDate;

}
