package com.ezen.www.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;
import com.ezen.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class CommentController {

	private final CommentService csv;
	private final BoardService bsv;
	
	//메서드, 헤더, 바디가 모두 들어간(Entity를 모두 구성한) 방법
	@PostMapping(value="/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo) {
		log.info(">>cvo>>{}",cvo);
		int isOk = csv.post(cvo);
		int isOk_com = bsv.updateComment(cvo.getBno());
		return isOk>0? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	 @GetMapping(value="/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE) 
	 public ResponseEntity<PagingHandler> list(@PathVariable("bno")int bno,@PathVariable("page")int page) {
		 //PagingVO / PagingHandler(쓸 값만 가져와서) 사용
		 PagingVO pgvo = new PagingVO(page, 5);
		 PagingHandler ph = csv.getList(bno, pgvo);
		 
		 //List<CommentVO> list = csv.getList(bno);
		 return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
	 }
	 
	 //body만 받아와서 처리하는 방법
	 //responseBody 방식 - return은 body에 실을 값(isOk)
	 @ResponseBody
	 @PutMapping("/modify")
	 public String edit(@RequestBody CommentVO cvo) {
		 log.info("cvo>>{}",cvo);
		 int isOk = csv.modify(cvo);
		 return isOk>0? "1":"0";
	 }
	 
	 @ResponseBody
	 @DeleteMapping("/{cno}")
	 public String delete(@PathVariable("cno")int cno) {
		int isOk = csv.remove(cno);
		return isOk>0? "1":"0";
	 }
	 
	 
//	 @PutMapping(value="/modify", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
//	 public ResponseEntity<String> modify(@RequestBody CommentVO cvo) {
//		 int isOk = csv.modify(cvo);
//		 return isOk>0? new ResponseEntity<String>("1",HttpStatus.OK) :
//			 new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	 
//	 @DeleteMapping(value="/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
//	 public ResponseEntity<String> delete(@PathVariable("cno")int cno) {
//		 int isOk = csv.remove(cno);
//		 return isOk>0? new ResponseEntity<String>("1", HttpStatus.OK):
//			 new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//	 }
	
}
