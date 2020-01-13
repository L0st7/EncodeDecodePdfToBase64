package com.core.file.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EncodePdfFileToBase64Service {
	
	public String encodePdfFile(MultipartFile multipartFile) {
		String encodedString = "";
		try {
//			File file = new File(multipartFile.getOriginalFilename());
//			byte[] fileContent = FileUtils.readFileToByteArray(file);
			encodedString = Base64.getEncoder().encodeToString(multipartFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedString;
	}
}
