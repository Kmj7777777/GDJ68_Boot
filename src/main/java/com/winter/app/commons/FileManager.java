package com.winter.app.commons;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
	public String save(String path, MultipartFile multipartFile) throws Exception {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		file = new File(file, fileName);
		
		// FileCopyUtils.copy(multipartFile.getBytes(), file);
		multipartFile.transferTo(file);
		
		return fileName;
	}
}