package com.ezen.www.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.FileHandler;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService bsv;
	private final FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/insert")
	public String insert(BoardVO bvo, 
			@RequestParam(name="files", required = false)MultipartFile[] files) {
		List<FileVO> flist = null;
		
		if(files[0].getSize()>0 ) { 
			//파일이 있다면
			flist = fh.uploadFiles(files);
			
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		int isOk = bsv.insert(bdto);
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public void list(Model m, PagingVO pgvo) {
		log.info(">> pagingVO >> {}", pgvo);
		
		//cmt_qty, read_count 기존값 반영하기
		bsv.cmtCountupdate();
		bsv.fileCountupdate();
		
		List<BoardVO> list = bsv.getList(pgvo);
		
		//totalCount 구해오기
		int totalCount = bsv.getTotal(pgvo);
		
		//페이징핸들러 사용
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		
		//Model m : 가져온 리스트 => /board/list.jsp로 전달하는 역할
		m.addAttribute("list", list);
		m.addAttribute("ph", ph);
		log.info(">>>ph>>> {}", ph);
	}
	
	@GetMapping({"/detail","/modify"})
	public void detail(@RequestParam("bno")int bno, Model m) {
		BoardDTO bdto = bsv.getDetail(bno);
		m.addAttribute("bdto", bdto);
	}
	
	//RedirectAttributes : redirect로 보내는 객체
	@PostMapping("/modify")
	public String modify(BoardVO bvo, RedirectAttributes re,
			@RequestParam(name="files", required=false)MultipartFile[] files) {
		List<FileVO> flist = null;
		if(files[0].getSize()>0 ) { 
			//파일이 있다면
			flist = fh.uploadFiles(files);
			bvo.setHasFile(bvo.getHasFile()+flist.size());
			
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		bsv.update(bdto);
		
//		int isOk = bsv.update(bvo);
//		if(isOk>0) {
//			re.addFlashAttribute("msg_bd_modify","1");
//		}
		
		//이런방법으로도 bno를 detail로 넘길 수 있다
//		re.addAttribute("bno", bvo.getBno());
//		return "redirect:/board/detail";
		re.addAttribute("bno", bvo.getBno());
		return "redirect:/board/detail?bno="+bvo.getBno();
	}
	
	@GetMapping("/remove") 
	public String remove(@RequestParam("bno")int bno, RedirectAttributes re) {
		int isOk = bsv.remove(bno);
		if(isOk>0) {
			re.addFlashAttribute("msg_bd_remove", "1");
		}
		return "redirect:/board/list";
	}
	
	@ResponseBody
	@DeleteMapping("/{uuid}/{bno}")
	public String removeFile(@PathVariable("uuid")String uuid,@PathVariable("bno")int bno){
		int isOk = bsv.removeFile(uuid,bno);
		return isOk>0? "1":"0";
	}

}
