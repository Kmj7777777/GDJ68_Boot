package com.winter.app.board;

import java.util.List;

import com.winter.app.commons.Pager;

public interface BoardDAO {
	public Long getCount(Pager pager);
	public List<BoardVO> getList(Pager pager);
	public int add(BoardVO boardVO);
	public int fileAdd(FileVO fileVO);
	public BoardVO getDetail(BoardVO boardVO);
	public FileVO getFileDetail(FileVO fileVO);
	public int setUpdate (BoardVO boardVO);
	public int setHitUpdate (BoardVO boardVO);
	public int setDelete(BoardVO boardVO);
}