package com.core.file.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author <a href="mailto:hiepnv14@fpt.com.vn">hiepnv14</a>
 * @version 1.0.0
 * @date Jan 16, 2020
 */
public class DateTimeUtil {
	public static final String DEFAULT_SHORTDATEFORMAT = "dd/MM/yyyy";
	public static final String DEFAULT_SHORTDATEFORMAT2 = "MM/dd/yyyy";
	public static final String DEFAULT_FILEDATE = "yyyyMMddHHmmss";
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SHORTDATEFORMAT);
	public static final SimpleDateFormat SHORT_DATE_FORMAT2 = new SimpleDateFormat(DEFAULT_SHORTDATEFORMAT2);
	public static final SimpleDateFormat SHORT_DATE__FILEDATE = new SimpleDateFormat(DEFAULT_FILEDATE);
	
	/**
	 * Lấy tên của quý trong năm
	 * 
	 * @param quarter
	 * @return
	 */
	public static String getNameQuarter(Integer quarter) {
		String quarterString = "";
		if(quarter==1) {
			quarterString = "Quý 1";
		}else if(quarter==2) {
			quarterString = "Quý 2";
		}else if(quarter==3) {
			quarterString = "Quý 3";
		}else if(quarter==4) {
			quarterString = "Quý 4";
		}
		return quarterString;
	}
	
	/**
	 * Lay ngay bat dau cua thang
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static Date firstDayOfMonth(Integer month, Integer year) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDateTime firstDayOfMonth = null;
		LocalDateTime date = LocalDateTime.of(year, month, 12,00,00);
		firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
		return Date.from(firstDayOfMonth.atZone(defaultZoneId).toInstant());
	}
	
	/**
	 * Lay ngay cuoi cung cua thang
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static Date lastDayOfMonth(Integer month, Integer year) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDateTime lastDayOfMonth = null;
		LocalDateTime date = LocalDateTime.of(year, month, 12,00,00);
		lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		return Date.from(lastDayOfMonth.atZone(defaultZoneId).toInstant());
	}
	
	/**
	 * Lấy ngày bắt đầu của quý
	 * 
	 * @param quarter
	 * @param year
	 * @return
	 */
	public static Date firstDayOfQuarter(int quarter, Integer year) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDateTime firstDayOfQuarter = null;
		if(quarter==1) {
			LocalDateTime thang1 = LocalDateTime.of(year, 1, 12,00,00);
			firstDayOfQuarter = thang1.with(TemporalAdjusters.firstDayOfMonth());
		}else if(quarter==2) {
			LocalDateTime thang4 = LocalDateTime.of(year, 4, 12,00,00);
			firstDayOfQuarter = thang4.with(TemporalAdjusters.firstDayOfMonth());
		}else if(quarter==3) {
			LocalDateTime thang7 = LocalDateTime.of(year, 7, 12,00,00);
			firstDayOfQuarter = thang7.with(TemporalAdjusters.firstDayOfMonth());
		}else if(quarter==4) {
			LocalDateTime thang10 = LocalDateTime.of(year, 10, 12,00,00);
			firstDayOfQuarter = thang10.with(TemporalAdjusters.firstDayOfMonth());
		}
		return Date.from(firstDayOfQuarter.atZone(defaultZoneId).toInstant());
	}
	
	/**
	 * Lấy ngày cuối cùng của quý
	 * 
	 * @param quarter
	 * @param year
	 * @return
	 */
	public static Date lastDayOfQuarter(int quarter, Integer year) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDateTime lastDayOfQuarter = null;
		if(quarter==1) {
			LocalDateTime thang3 = LocalDateTime.of(year, 3, 12,00,00);
			lastDayOfQuarter = thang3.with(TemporalAdjusters.lastDayOfMonth());
		}else if(quarter==2) {
			LocalDateTime thang6 = LocalDateTime.of(year, 6, 12,00,00);
			lastDayOfQuarter = thang6.with(TemporalAdjusters.lastDayOfMonth());
		}else if(quarter==3) {
			LocalDateTime thang9 = LocalDateTime.of(year, 9, 12,00,00);
			lastDayOfQuarter = thang9.with(TemporalAdjusters.lastDayOfMonth());
		}else if(quarter==4) {
			LocalDateTime thang12 = LocalDateTime.of(year, 12, 12,00,00);
			lastDayOfQuarter = thang12.with(TemporalAdjusters.lastDayOfMonth());
		}
		return Date.from(lastDayOfQuarter.atZone(defaultZoneId).toInstant());
	}
	
	/**
	 * Convert string to date "dd/MM/yyyy"
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date convertFromShortStringDate(final String date) throws ParseException {
        return SHORT_DATE_FORMAT.parse(date);
    }
	
	/**
	 * Convert date to string "dd/MM/yyyy"
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String convertFromShortDateString(final Date date) throws ParseException {
        return SHORT_DATE_FORMAT.format(date);
    }
	
	/**
	 * Convert date to string "MM/dd/yyyy"
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToShortString(final Date date) {
        return SHORT_DATE_FORMAT2.format(date);
    }
	
	/**
	 * Convert date to string "yyyyMMddHHmmss"
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToShortStringFileDate(final Date date) {
        return SHORT_DATE__FILEDATE.format(date);
    }
	
	/**
	 * Convert date to date "dd/MM/yyyy"
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date convertToShortDate(final Date date) throws ParseException {
        return SHORT_DATE_FORMAT.parse(SHORT_DATE_FORMAT.format(date));
    }
	
	/**
	 * Convert String date "dd/MM/yyyy" -> "MM/dd/yyyy"
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String convertStringToShortStringDate(final String date) throws ParseException {
        return SHORT_DATE_FORMAT2.format(SHORT_DATE_FORMAT.parse(date));
    }
}
