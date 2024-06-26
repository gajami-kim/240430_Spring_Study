package com.ezen.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.test.domain.BoardDTO;
import com.ezen.test.domain.BoardVO;
import com.ezen.test.domain.FileVO;
import com.ezen.test.domain.PagingVO;
import com.ezen.test.repository.BoardDAO;
import com.ezen.test.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;
	private final FileDAO fdao;
	
	@Override
	public int insert(BoardDTO bdto) {
		log.info(">>> board regiseter service check");
		int isOk = bdao.insert(bdto.getBvo());
		
		//file 처리 => bno는 아직 없음
		if(bdto.getFlist()==null) {
			return isOk;
		} else {
			//파일 저장
			if(isOk>0 && bdto.getFlist().size()>0) {
				//bno는 아직 없음. 
				//insert를 통해서 자동생성이 되기때문에 DB에 가서 검색해와야함
				int bno = bdao.selectBno();
				//지금 들어오는 flist는 모두 같은 bno를 가짐
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					//파일 저장
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		return isOk; 
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info(">>> board getList service check");
		return bdao.getList(pgvo);
	}

	@Override
	public BoardDTO getDetail(int bno) {
		log.info(">>> board getDetail service check");
		//read_count 증가
		bdao.update_readCount(bno);
		BoardDTO bdto = new BoardDTO();
		BoardVO bvo = bdao.getDetail(bno); //기존에 처리된 bvo객체(file삽입 전 시점)
		bdto.setBvo(bvo);
		bdto.setFlist(fdao.getFileList(bno)); //bno에 해당하는 모든 파일 리스트 검색
		return bdto;
	}

	@Override
	public void update(BoardDTO bdto) {
		log.info(">>> board update service check");
		//파일 추가 작업
		//bvo=>boardMapper / flist=>fileMapper
		
		int isOk = bdao.update(bdto.getBvo());
		//파일값이 없다면
		if(bdto.getFlist()==null) {
			return;
		}
		
		//bvo 업데이트 후 파일이 있다면 파일 추가
		if(isOk>0 && bdto.getFlist().size()>0) {
			//bno setting(모든 파일에 bno를 부여해야하기 때문에 for문을 사용)
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bdto.getBvo().getBno());
				isOk *= fdao.insertFile(fvo);
			}
		}
	}

	@Override
	public void isDel(int bno) {
		log.info(">>> board delete service check");
		bdao.isDel(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info(">>> getTotal service check");
		return bdao.getTotal(pgvo);
	}

	@Override
	public int removeFile(String uuid, int bno) {
		log.info(">>> removeFile service check");
		int isOk = fdao.removeFile(uuid);
		if(isOk>0) {
			bdao.fileCount(bno);
		}
		return isOk; 
	}

	//댓글추가
	@Override
	public int updateComment(int bno) {
		log.info(">>> updateComment service check");
		int cnt = -1;
		return bdao.updateComment(bno,cnt);
	}
	
	//댓글삭제
//	@Override
//	public int deleteComment(int bno) {
//		log.info(">>> deleteComment service check");
//		return bdao.deleteComment(bno);
//	}

	//기존 DB 값 반영하기
	@Override
	public void cmtCountupdate() {
		bdao.cmtCountupdate();
		
	}
	@Override
	public void fileCountupdate() {
		bdao.fileCountupdate();
		
	}

	
}
