package com.ezen.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.test.domain.CommentVO;
import com.ezen.test.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("post service in");
		return cdao.post(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("getList service in");
		return cdao.getList(bno);
	}

	@Override
	public int update(CommentVO cvo) {
		log.info("update service in");
		return cdao.update(cvo);
	}

	@Override
	public int remove(int cno) {
		log.info("remove service in");
		return cdao.remove(cno);
	}
	
}
