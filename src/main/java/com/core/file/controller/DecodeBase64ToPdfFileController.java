package com.core.file.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.core.file.service.DecodeBase64ToPdfFileService;
import com.core.file.service.EncodePdfFileToBase64Service;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 15, 2020
 */
@Controller
public class DecodeBase64ToPdfFileController {
	
	@Autowired
	DecodeBase64ToPdfFileService decodeBase64ToPdfFileService;
	
	@Autowired
	EncodePdfFileToBase64Service encodePdfFileToBase64Service;
	
	@PostMapping("/report")
	public ResponseEntity<?> decodeBase64 (@RequestParam MultipartFile multipartFile) throws IOException{
		return decodeBase64ToPdfFileService.decodeBase64(encodePdfFileToBase64Service.encodePdfFile(multipartFile));
	}
}
