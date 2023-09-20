package com.winter.app.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.winter.app.board.FileVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileDownView extends AbstractView {
	@Value("${app.upload}")
	private String filePath;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String board = (String)model.get("board");
		FileVO fileVO = (FileVO)model.get("file");
		
		File file = new File(filePath + board, fileVO.getFileName());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentLengthLong(file.length());
		String downName = URLEncoder.encode(fileVO.getOriginalName(), "UTF-8");
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" + downName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		FileInputStream fi = new FileInputStream(file);
		OutputStream os = response.getOutputStream();
		FileCopyUtils.copy(fi, os);
		
		os.close();
		fi.close();
		
		log.info("==================== File Down View ====================");
		log.info("board : {}", board);
	}
}