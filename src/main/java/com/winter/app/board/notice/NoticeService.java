package com.winter.app.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardService;
import com.winter.app.board.BoardVO;
import com.winter.app.board.FileVO;
import com.winter.app.commons.FileManager;
import com.winter.app.commons.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService implements BoardService {	
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private FileManager fileManager;
	@Value("${app.upload}") // SpEL(Spring EL) : properties 값을 Java에서 사용하기
	private String uploadPath;
	@Value("${app.board.notice}")
	private String boardName;
	
	@Override
	public List<BoardVO> getList(Pager pager) {
		return noticeDAO.getList(pager);
	}
	
	@Override
	public int add(BoardVO boardVO, MultipartFile[] files) throws Exception {
		int result = noticeDAO.add(boardVO);
		
		for(MultipartFile multipartFile : files) {
			if(multipartFile.isEmpty()) {
				continue;
			}
			
			NoticeFileVO noticeFileVO = new NoticeFileVO();
			String fileName = fileManager.save(uploadPath + boardName, multipartFile);
			noticeFileVO.setBoardNo(boardVO.getBoardNo());
			noticeFileVO.setFileName(fileName);
			noticeFileVO.setOriginalName(multipartFile.getOriginalFilename());
			result = noticeDAO.fileAdd(noticeFileVO);
		}
		
		return result;
	}
	
	@Override
	public BoardVO getDetail(BoardVO boardVO) {
		return noticeDAO.getDetail(boardVO);
	}
	
	@Override
	public FileVO getFileDetail(FileVO fileVO) {
		return noticeDAO.getFileDetail(fileVO);
	}
	
	@Override
	public int setUpdate(BoardVO boardVO) {
		return noticeDAO.setUpdate(boardVO);
	}
	
	@Override
	public int setDelete(BoardVO boardVO) {
		return noticeDAO.setDelete(boardVO);
	}
}