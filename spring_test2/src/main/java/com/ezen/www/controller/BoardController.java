package com.ezen.www.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService bsv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/insert")
	public String insert(BoardVO bvo) {
		bsv.insert(bvo);
		return "index";
	}
	
	@GetMapping("/list")
	public void list(Model m) {
		List<BoardVO> list = bsv.getList();
		//가져온 리스트 => /board/list.jsp로 전달
		m.addAttribute("list", list);
		log.info(">>>list>>> {}", list);
	}
	
	@GetMapping({"/detail","/modify"})
	public void detail(@RequestParam("bno")int bno, Model m) {
		BoardVO bvo = bsv.getDetail(bno);
		m.addAttribute("bvo", bvo);
	}
	
	//RedirectAttributes : redirect로 보내는 객체
	@PostMapping("/modify")
	public String modify(BoardVO bvo, RedirectAttributes re) {
		int isOk = bsv.update(bvo);
		if(isOk>0) {
			re.addFlashAttribute("msg_bd_modify","1");
		}
		
		//이런방법으로 bno를 detail로 넘길 수 있다
//		re.addAttribute("bno", bvo.getBno());
//		return "redirect:/board/detail";
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

}