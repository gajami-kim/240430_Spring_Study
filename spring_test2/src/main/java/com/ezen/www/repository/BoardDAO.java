package com.ezen.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	int update(BoardVO bvo);

	int remove(int bno);

	int getTotal(PagingVO pgvo);

	int selectCnoBno();

	void cmtCountupdate();

	void fileCountupdate();

	int updateComment(@Param("bno")int bno, @Param("cnt")int cnt);

	void fileCount(int bno);

	void updateReadCount(int bno);

}
