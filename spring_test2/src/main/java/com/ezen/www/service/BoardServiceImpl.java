package com.ezen.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDAO bdao;

	@Override
	public int insert(BoardVO bvo) {
//		log.info("insert service in");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.getList(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.getDetail(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		return bdao.update(bvo);
	}

	@Override
	public int remove(int bno) {
		return bdao.remove(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		return bdao.getTotal(pgvo);
	}

	
}
