package com.systemk.spyder.Util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlienUtils {

	public static String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String hexToString(String arg) {
		String result = "";
		arg = arg.replaceAll(" ", "");
		for (int i = 0; i < arg.length(); i += 2) {
			String s = arg.substring(i, (i + 2));
			int decimal = Integer.parseInt(s, 16);
			result = result + (char) decimal;
		}
		return result;
	}

	public static String stringToHex(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			result += String.format("%02X", (int) s.charAt(i));
		}
		return result;
	}
	
	public static String byteArrayToHex(byte[] a) {
	    StringBuilder sb = new StringBuilder();
	    for(final byte b: a)
	    	if(b != 0){
	    		sb.append(String.format("%02X", b&0xff));
	    	}
	    return sb.toString();
	}
	
	public static String generateTempCode(String s){
		String result = "";
		int maxSize = 12;
		int calc = maxSize - s.length();
		if(calc > 0){
			for(int i=0; i < calc; i++){
				result += "20";
			}
		}
		
		return result;
	}
}
