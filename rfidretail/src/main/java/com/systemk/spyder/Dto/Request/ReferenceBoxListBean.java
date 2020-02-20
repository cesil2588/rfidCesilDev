package com.systemk.spyder.Dto.Request;

import java.util.ArrayList;
import java.util.List;

public class ReferenceBoxListBean {
	
	private String barcode;
	
	private String checkYn;
	
	public List<ReferenceStyleListBean> styleList;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCheckYn() {
		return checkYn;
	}

	public void setCheckYn(String checkYn) {
		this.checkYn = checkYn;
	}

	public List<ReferenceStyleListBean> getStyleList() {
		return styleList;
	}

	public void setStyleList(List<ReferenceStyleListBean> styleList) {
		this.styleList = styleList;
	}
	
	// 중복된 스타일, 컬러, 사이즈, ReferenceNo Merge
	public void mergeStyle() {
		
		List<ReferenceStyleListBean> tempStyleList = new ArrayList<ReferenceStyleListBean>();
		
		for(ReferenceStyleListBean style : this.styleList) {
			
			boolean pushFlag = true;
			
			loop :
			for(ReferenceStyleListBean tempStyle : tempStyleList) {
				if(style.getStyle().equals(tempStyle.getStyle()) &&
				   style.getColor().equals(tempStyle.getColor()) &&
				   style.getSize().equals(tempStyle.getSize()) &&
				   style.getReferenceNo().equals(tempStyle.getReferenceNo())) {
					
					pushFlag = false;
					
					tempStyle.setAmount(tempStyle.getAmount() + style.getAmount());
					
					break loop;
				}
			}
			
			if(pushFlag) {
				tempStyleList.add(style);
			}
		}
		
		this.styleList = tempStyleList;
	}
}
