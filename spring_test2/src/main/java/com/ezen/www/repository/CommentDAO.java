package com.ezen.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;

public interface CommentDAO {

	int post(CommentVO cvo);

	List<CommentVO> getList(@Param("bno")int bno, @Param("pgvo")PagingVO pgvo);

	int getSelectOneBnoTotalCount(int bno);

	int modify(CommentVO cvo);

	int remove(int cno);
	
}
