package com.systemk.spyder.Util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import com.google.common.base.CaseFormat;

// JDBC Templete 환경하에 Pageable을 이용한 Paging Util
public class PagingUtil {
	
	// sort 셋팅
	public static String sortSetting(Pageable pageable, String defaultSort){
		
		String sortQuery = "";
		
		if(pageable.getSort() != null){
			String[] sortList = StringUtils.split(pageable.getSort().toString(), ":");
			// camel to snake로 변경
			sortQuery = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortList[0]) + sortList[1]; 
		} else {
			sortQuery = defaultSort;
		}
		
		return sortQuery;
	}

	// 페이징 셋팅
	public static Map<String, Object> pagingSetting(Pageable pageable, Map<String, Object> params) {
		
		int number = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int startRow = (size * number) + 1;
		int endRow = (size * (number + 1)) +1;
		int groupCount = endRow - 1;
		
		params.put("groupCount", groupCount);
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		
		return params;
	}
	
	// 페이징 모델 셋팅
	public static Map<String, Object> pagingModel(Pageable pageable, Long totalCount, Map<String, Object> obj){
		
		Long totalElements = totalCount;
		Long totalPages = totalElements % 10 > 0 ? (totalElements / 10) + 1 : totalElements / 10;
		
		obj.put("number", pageable.getPageNumber());
		obj.put("totalElements", totalElements);
		obj.put("totalPages", totalPages);
		
		return obj;
	}
}
