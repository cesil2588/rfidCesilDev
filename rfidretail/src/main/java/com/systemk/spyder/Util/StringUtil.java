package com.systemk.spyder.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemk.spyder.Entity.Main.RfidTagMaster;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {

	public static String convertCipher(String size, int serial) {
		return String.format("%0" + size + "d", serial);
	}
	
	public static String convertCipher(String size, String serial) {
		return String.format("%0" + size + "d", Integer.parseInt(serial));
	}
	
	public static String convertJsonString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	public static Map<String, Object> setRfidTag(String rfidTag) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		String erpKey = rfidTag.substring(0, 10);
		String productYy = rfidTag.substring(10, 12);
		String productSeason = rfidTag.substring(12, 13);
		String orderDegree = rfidTag.substring(13, 15);
		String productionCompanyCode = rfidTag.substring(15, 18);
		String publishLocation = rfidTag.substring(18, 19);
		String publishDate = rfidTag.substring(19, 25);
		String publishDegree = rfidTag.substring(25, 27);
		String rfidSeq = rfidTag.substring(27, 32);
		
		obj.put("erpKey", erpKey);
		obj.put("productYy", productYy);
		obj.put("productSeason", productSeason);
		obj.put("orderDegree", orderDegree);
		obj.put("productionCompanyCode", productionCompanyCode);
		obj.put("publishLocation", publishLocation);
		obj.put("publishDate", publishDate);
		obj.put("publishDegree", publishDegree);
		obj.put("rfidSeq", rfidSeq);
		
		return obj;
	}
	
	public static RfidTagMaster setRfidTagMaster(String rfidTag){
		
		RfidTagMaster obj = new RfidTagMaster();
		
		String erpKey = rfidTag.substring(0, 10);
		String productYy = rfidTag.substring(10, 12);
		String productSeason = rfidTag.substring(12, 13);
		String orderDegree = rfidTag.substring(13, 15);
		String productionCompanyCode = rfidTag.substring(15, 18);
		String publishLocation = rfidTag.substring(18, 19);
		String publishDate = rfidTag.substring(19, 25);
		String publishDegree = rfidTag.substring(25, 27);
		String rfidSeq = rfidTag.substring(27, 32);
		
		obj.setErpKey(erpKey);
		obj.setSeason(productYy + productSeason);
		obj.setOrderDegree(orderDegree);
		obj.setCustomerCd(productionCompanyCode);
		obj.setPublishLocation(publishLocation);
		obj.setPublishRegDate(publishDate);
		obj.setPublishDegree(publishDegree);
		obj.setRfidSeq(rfidSeq);
		obj.setRfidTag(rfidTag);
		
		return obj;
	}
	
	public static String getPrintStackTrace(Exception e) {
        
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
         
        return errors.toString();
         
    }
	
	public static String getPrintStackTrace(Throwable throwData) {
        
        StringWriter errors = new StringWriter();
        throwData.printStackTrace(new PrintWriter(errors));
         
        return errors.toString();
         
    }
	
	public static void replace(Class<?> clazz, Object object, String field) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            if( f.get(object) != null ) {
                if( f.get(object) instanceof String[] ) {
                    String[] stringArr = (String[]) f.get(object);
                    for (int i = 0; i < stringArr.length; i++) {
                        stringArr[i] = stringArr[i].replaceAll("'", "''");
                    }
                } else {
                    String value =  f.get(object).toString();
                    if( value.contains("'") ) {
                        f.set(object, value.replaceAll("'", "''"));
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            //e.printStackTrace();
        }
    }
	
	public static String getBody(HttpServletRequest request) throws IOException {
	      
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;
	    InputStream inputStream = null;
	     
	    try {
	        inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	    	ex.getMessage();
	    } finally {
	        try {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            if (bufferedReader != null) {
	                bufferedReader.close();
	            }
	        } catch (IOException ex) {
	        	ex.getMessage();
	        }
	    }
	 
	    body = stringBuilder.toString();
	    return body;
	}
	
	public static boolean isStringDouble(String s) {
	    try {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public static Map<String, String> getQueryMap(String query)
	{
	    String[] params = query.split("&");
	    Map<String, String> map = new HashMap<String, String>();
	    for (String param : params)
	    {
	        String name = param.split("=")[0];
	        String value = param.split("=")[1];
	        map.put(name, value);
	    }
	    return map;
	}

}
