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
		MediaType mediaType = getMediaTypeForFileName(this.servletContext, file.getName());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
				.contentType(mediaType).contentLength(file.length()).body(bFile);
	}
	
	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
