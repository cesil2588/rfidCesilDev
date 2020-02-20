package com.systemk.spyder.Dto.Request;

import java.util.List;

public class ProductionTagListBean{
	
	private Long productionStorageSeq;
	
	private List<String> tagList;

	public Long getProductionStorageSeq() {
		return productionStorageSeq;
	}

	public void setProductionStorageSeq(Long productionStorageSeq) {
		this.productionStorageSeq = productionStorageSeq;
	}

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

	@Override
	public String toString() {
		return "ProductionTagListBean [productionStorageSeq=" + productionStorageSeq + ", tagList=" + tagList + "]";
	}
}
