package com.systemk.spyder.Dto.Response;

import java.io.Serializable;

import com.systemk.spyder.Entity.Main.ProductMaster;

/**
 * 제품마스터 DTO
 * @author escho
 *
 */
public class ProductMasterResult implements Serializable{
	
	private static final long serialVersionUID = -3894611065333464244L;

	// 제품연도
	private String productYy;

	// 제품시즌
	private String productSeason;
	
	// 스타일
	private String style;
	
	// 컬러
	private String color;

	// 사이즈
	private String size;
	
	// erpKey
	private String erpKey;

	public String getProductYy() {
		return productYy;
	}

	public void setProductYy(String productYy) {
		this.productYy = productYy;
	}

	public String getProductSeason() {
		return productSeason;
	}

	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}
	
	public ProductMasterResult() {
		
	}

	public ProductMasterResult(ProductMaster param) {
		this.productYy = param.getProductYy();
		this.productSeason = param.getProductSeason();
		this.style = param.getStyle();
		this.color = param.getColor();
		this.size = param.getSize();
		this.erpKey = param.getErpKey();
	}
}
