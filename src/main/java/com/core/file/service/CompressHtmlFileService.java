package com.core.file.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.core.file.common.Common;
import com.core.file.common.DateTimeUtil;
import com.core.file.common.RegexCommon;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 16, 2020
 */
@Service
public class CompressHtmlFileService {
	
	@Autowired
	ServletContext servletContext;
	
	private static final String path="/report-out/"+File.separator;
	
	public ResponseEntity<?> compressHtmlFile(MultipartFile multipartFile) {
		File file = null;
		Object bFile = null;
		MediaType mediaType = null;
		try {
			File fileDir = MoveAndStoreFile(multipartFile,multipartFile.getOriginalFilename());
			String perfixImageRaw = multipartFile.getOriginalFilename()+DateTimeUtil.convertToShortStringFileDate(new Date());
			String perfixImageCompress = multipartFile.getOriginalFilename()+"compressed-05"+DateTimeUtil.convertToShortStringFileDate(new Date());
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			
			String strCompress = "";
			String strCompressFinal = "";
			String str = in.lines().collect(Collectors.joining(System.lineSeparator()));
			Matcher matcher = RegexCommon.regexFindBase64(str);
			Map<String, String> mapString = new HashMap<String, String>();
			int count = 0;
			while (matcher.find())
			{
				String countSet = "!!!"+ count++ + "!!!";
				mapString.put(countSet, matcher.group());
				strCompress = matcher.replaceAll(countSet);
			}
			
			for(Entry<String, String> e : mapString.entrySet()){
				String [] base64Image = mapString.get(e.getKey()).split(",");
				Matcher matcherSuffixImage = RegexCommon.regexFindSuffixImage(base64Image[0]);
				if(matcherSuffixImage.find()) {
					BufferedImage bufferedImage = DecodeToImage(base64Image[1],perfixImageRaw+e.getKey(),matcherSuffixImage.group(1));
					String base64ImageCompress = EncodeImageToBase64(CompressImages(bufferedImage,perfixImageCompress+e.getKey(),matcherSuffixImage.group(1)));
					StringBuilder stringBuilderbase64ImageCompress = new StringBuilder();
					stringBuilderbase64ImageCompress.insert(0, "data:image/"+matcherSuffixImage.group(1)+";base64,");
					stringBuilderbase64ImageCompress.append(base64ImageCompress);
					strCompressFinal = strCompress.replace(e.getKey(), stringBuilderbase64ImageCompress.toString());
				}
			};
			
			File dir = new File("/report-out-compress/");
			dir.mkdir();
			String fileName = multipartFile.getOriginalFilename();
			file = new File(dir,fileName);
			byte[] decoder = strCompressFinal.getBytes("UTF-8");
			FileUtils.writeByteArrayToFile(file, decoder);
			bFile = Files.readAllBytes(file.toPath());
			mediaType = Common.GetMediaTypeForFileName(this.servletContext, file.getName());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
				.contentType(mediaType).contentLength(file.length()).body(bFile);
	}
	
	public static File MoveAndStoreFile(MultipartFile file, String name) throws IOException {
	    String url = path+name;
	    File fileToSave = new File(url);
	    fileToSave.createNewFile();
	    FileOutputStream fos = new FileOutputStream(fileToSave); 
	    fos.write(file.getBytes());
	    fos.close();
	    return fileToSave;
	}
	
	public static BufferedImage DecodeToImage(String imageString, String perfixImage, String suffixImage) {
		 
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            File outputfile = new File("/report-out/"+perfixImage+"."+suffixImage);
            ImageIO.write(image, suffixImage, outputfile);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
	
	public static String EncodeImageToBase64(File file) {
		String base64Image="";
		try {
			byte[] bFile = Files.readAllBytes(file.toPath());
			base64Image = Base64.getEncoder().encodeToString(bFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64Image;
	}
	
    public static File CompressImages(BufferedImage image, String perfixImage, String suffixImage){

//        File input = new File("/tmp/duke.jpg");
//        BufferedImage image = ImageIO.read(input);
    	File output = null;
    	try {
    		output = new File("/report-out/"+perfixImage +"."+suffixImage);
            OutputStream out = new FileOutputStream(output);
            
            ImageWriter writer =  ImageIO.getImageWritersByFormatName(suffixImage).next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()){
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.05f);
            }

            writer.write(null, new IIOImage(image, null, null), param);
            
            out.close();
            ios.close();
            writer.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return output;
    }
}
