package com.ezen.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardDAO;
import com.ezen.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDAO bdao;
	private final FileDAO fdao;
	
	@Override
	public int insert(BoardDTO bdto) {
		//bvo 저장 후 bno set 한 후 fileVO 저장
		int isOk = bdao.insert(bdto.getBvo());
		
		if(bdto.getFlist()==null) {
			return isOk;
		} else {
			if(isOk>0&&bdto.getFlist().size()>0) {
				//bno setting
				int bno = bdao.selectCnoBno(); //가장 마지막에 등록된 bno
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					isOk*=fdao.insertFile(fvo);
				}
			}
			
		}
		return isOk;
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
