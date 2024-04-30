package com.ezen.test.service;

import java.util.List;

import com.ezen.test.domain.BoardDTO;
import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.PagingVO;

public interface BoardService {

	int insert(BoardDTO bdto);

	List<BoardVO> getList(PagingVO pgvo);

	BoardDTO getDetail(int bno);

	void update(BoardDTO bdto);

	void isDel(int bno);

	int getTotal(PagingVO pgvo);

	int removeFile(String uuid, int bno);

	//댓글수
	int updateComment(int bno);
	//댓글수

//	//댓삭제
//	int deleteComment(int bno);
//	//댓삭제

	//기존 DB 값 반영하기
	void cmtCountupdate();
	void fileCountupdate();

}
