package com.core.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.file.service.EncodePdfFileToBase64Service;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 15, 2020
 */
@Controller
public class EncodePdfFileToBase64Controller {
	
	@Autowired
	EncodePdfFileToBase64Service encodePdfFileToBase64Service;
	
	@PostMapping("/uploadPdfFile")
	public @ResponseBody String encodePdfFile(@RequestParam MultipartFile multipartFile) {
		return encodePdfFileToBase64Service.encodePdfFile(multipartFile);
	}
}
