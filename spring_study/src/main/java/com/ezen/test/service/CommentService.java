package com.ezen.test.service;

import java.util.List;

import com.ezen.test.domain.CommentVO;

public interface CommentService {

	int post(CommentVO cvo);

	List<CommentVO> getList(int bno);

	int update(CommentVO cvo);

	int remove(int cno);

}
