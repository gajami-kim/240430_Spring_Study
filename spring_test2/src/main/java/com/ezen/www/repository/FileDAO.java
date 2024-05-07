package com.ezen.www.repository;

import java.util.List;

import com.ezen.www.domain.FileVO;

public interface FileDAO {

	int insertFile(FileVO fvo);

	List<FileVO> getList(int bno);

	int removeFile(String uuid);

	List<FileVO> selectListAllFile();

}
