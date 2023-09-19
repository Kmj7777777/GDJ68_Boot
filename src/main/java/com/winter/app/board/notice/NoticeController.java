package com.winter.app.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winter.app.board.BoardVO;
import com.winter.app.commons.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice/")
@Slf4j // Simple Logging Facade for Java
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("list")
	public String getList(Pager pager, Model model) {
		List<BoardVO> list = noticeService.getList(pager);
		model.addAttribute("list", list);
		// ERROR > WARN > INFO > DEBUG > TRACE
		log.error("getList 실행");
		return "board/list";
	}
	
	@GetMapping("add")
	public String add() {
		return "board/add";
	}
	
	@PostMapping("add")
	public String add(NoticeVO noticeVO) {
		int result = noticeService.add(noticeVO);
		// log.info("NoticeVO : {}", noticeVO);
		return "redirect:./list";
	}
	
	@GetMapping("detail")
	public String detail(NoticeVO noticeVO, Model model) {
		BoardVO boardVO = noticeService.getDetail(noticeVO);
		model.addAttribute("notice", boardVO);
		return "board/detail";
	}
	
	@GetMapping("update")
	public String update(NoticeVO noticeVO, Model model) {
		BoardVO boardVO = noticeService.getDetail(noticeVO);
		model.addAttribute("notice", boardVO);
		
		return "board/update";
	}
	
	@PostMapping("update")
	public String update(NoticeVO noticeVO) {
		noticeService.setUpdate(noticeVO);
		return "redirect:./detail?boardNo=" + noticeVO.getBoardNo();
	}
}