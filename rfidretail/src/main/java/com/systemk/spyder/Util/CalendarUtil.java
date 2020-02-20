package com.systemk.spyder.Util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static String convertFormat(String simpleFormat) {
		Date date = new Date();
		Format format = new SimpleDateFormat(simpleFormat);
		return format.format(date);
	}
	
	public static String convertFormat(String simpleFormat, Date date) {
		Format format = new SimpleDateFormat(simpleFormat);
		return format.format(date);
	}
	
	public static Date convertStartDate(String startDate) throws Exception{
		String startDt = startDate + " 00:00:00";
		
		SimpleDateFormat dt = null;
		
		if(startDate.length() == 8 || startDate.length() == 0){
			dt = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
		} else if(startDate.length() == 10){
			dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} 
		
		Date start = new Date();
		if(!startDate.equals("")) {
			start = dt.parse(startDt);
		}
		if("".equals(startDate)) {
			String defaltDate = getCustomDate() + " 00:00:00";
			start = dt.parse(defaltDate);
		}
		
		return start;
	}
	
	public static Date convertEndDate(String endDate) throws Exception{
		
		String endDt = endDate + " 23:59:59";
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
		Date end = new Date();
		if(!endDate.equals("")) {
			end = dt.parse(endDt);
		}
		if("".equals(endDate)) {
			end = new Date();
		}
		
		return end;
	}
	
	public static String initStartDate(String startDate) throws Exception{
		
		if("".equals(startDate)){
			startDate = getCustomDate();
		}
		
		return startDate;
	}
	
	public static String initEndDate(String endDate) throws Exception{
		
		if("".equals(endDate)){
			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
			Date end = new Date();
			endDate = dt.format(end);
		}
		
		return endDate;
	}
	
	public static String getCustomDate() {
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String yyyyMMdd = format.format(dt);
	    int year  = Integer.parseInt(yyyyMMdd.substring(0, 4));
	    int month = Integer.parseInt(yyyyMMdd.substring(4, 6));
	    int date  = Integer.parseInt(yyyyMMdd.substring(6, 8));

	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, date);

	    //cal.add(Calendar.YEAR, -1);
	    cal.add(Calendar.MONTH, -1);
	    //cal.add(Calendar.DATE, -1);

	    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	    String r = dateFormatter.format(cal.getTime());

	    return r;
	}
}
