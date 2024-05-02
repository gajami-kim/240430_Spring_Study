package com.ezen.www.service;

import java.util.List;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO cvo);

	PagingHandler getList(int bno, PagingVO pgvo);

}
