package com.core.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.core.file.common.Common;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 15, 2020
 */
@Service
public class DecodeBase64ToPdfFileService {
	
	@Autowired
	ServletContext servletContext;
	
	public ResponseEntity<?> decodeBase64(String pdfFileContent) throws IOException{
		File dir = new File("/report-out/");
		dir.mkdir();
		String fileName = "test.pdf";
		File file = new File(dir,fileName);
		byte[] decoder = Base64.getDecoder().decode(pdfFileContent);
		FileUtils.writeByteArrayToFile(file, decoder);
		byte[] bFile = Files.readAllBytes(file.toPath());
		MediaType mediaType = Common.GetMediaTypeForFileName(this.servletContext, file.getName());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
				.contentType(mediaType).contentLength(file.length()).body(bFile);
	}
}	

