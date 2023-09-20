package com.winter.app.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.winter.app.commons.Pager;

public interface BoardService {
	public List<BoardVO> getList(Pager pager);
	public int add(BoardVO boardVO, MultipartFile[] files) throws Exception;	
	public BoardVO getDetail(BoardVO boardVO);
	public FileVO getFileDetail(FileVO fileVO);
	public int setUpdate (BoardVO boardVO);
	public int setDelete(BoardVO boardVO);
}