package com.core.file.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 16, 2020
 */
public class RegexCommon {
	public static String BASE64 = "data:image/jpeg;base64(.*?)==";
	public static String SUFFIXIMAGE = "^data:[^/]+/([^;]+);base64";
	
	public static Matcher regexFindBase64(String str) {
		Pattern pattern = Pattern.compile(BASE64);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	
	public static Matcher regexFindSuffixImage(String str) {
		Pattern pattern = Pattern.compile(SUFFIXIMAGE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
}
