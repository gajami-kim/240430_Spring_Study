package com.ezen.www.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class CommentController {

	private final CommentService csv;
	
	@PostMapping(value="/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo) {
		log.info(">>cvo>>{}",cvo);
		int isOk = csv.post(cvo);
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
	
}
