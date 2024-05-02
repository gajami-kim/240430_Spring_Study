package com.ezen.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		return cdao.post(cvo);
	}
	
	@Override
	public PagingHandler getList(int bno, PagingVO pgvo) {
		//cmtList 값을 ph 객체 안에 삽입(limit를 위해 pgvo추가)
		List<CommentVO> list = cdao.getList(bno, pgvo);
		//totalCount 구해오기
		int totalCount = cdao.getSelectOneBnoTotalCount(bno);
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		return ph;
	}
	
}
