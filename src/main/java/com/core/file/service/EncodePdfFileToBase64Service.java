package com.core.file.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 15, 2020
 */
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
