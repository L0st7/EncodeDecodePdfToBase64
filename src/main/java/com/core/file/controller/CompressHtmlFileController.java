package com.core.file.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.core.file.service.CompressHtmlFileService;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 16, 2020
 */
@Controller
public class CompressHtmlFileController {
	@Autowired
	CompressHtmlFileService compressHtmlFileService;
	
	@PostMapping("/compress")
	public ResponseEntity<?> decodeBase64 (@RequestParam MultipartFile multipartFile) throws IOException{
		return compressHtmlFileService.compressHtmlFile(multipartFile);
	}
}
