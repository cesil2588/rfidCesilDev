package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//제품 마스터 테이블
//@ApiModel(description = "제품 마스터 Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductMaster implements Serializable{

	private static final long serialVersionUID = -34774678169018493L;

	//  제품 마스터 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long productSeq;
	
	// erp키
	//@ApiModelProperty(notes = "ERP 키")
	@Column(nullable = false, length = 10)
	private String erpKey;

    //  스타일
	//@ApiModelProperty(notes = "스타일")
	@Column(nullable = false, length = 20)
    private String style;

    //  나머지제품코드
	//@ApiModelProperty(notes = "컬러 + 사이즈")
	@Column(nullable = false, length = 20)
    private String annotherStyle;

    //  제품연도
	//@ApiModelProperty(notes = "제품연도")
	@Column(nullable = false, length = 4)
    private String productYy;

    //  제품시즌
	//@ApiModelProperty(notes = "제품시즌")
	@Column(nullable = false, length = 5)
    private String productSeason;
	
	//@ApiModelProperty(notes = "RFID 태그 연도, 시즌")
	@Column(nullable = true, length = 3)
	private String productRfidYySeason;

    //  컬러
	//@ApiModelProperty(notes = "컬러")
	@Column(nullable = false, length = 10)
    private String color;

    //  사이즈
	//@ApiModelProperty(notes = "사이즈")
	@Column(nullable = false, length = 10)
    private String size;

    //  변경상태
	//@ApiModelProperty(notes = "변경 상태(C: 생성, D: 삭제, U: 변경)")
	@Column(nullable = false, length = 1)
    private String stat;

    //  등록일시
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;
    
    // 수정일시
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;

    //  ERP 생성일자
	//@ApiModelProperty(notes = "ERP 생성일")
    @Column(nullable = false, length = 8)
    private String createDate;

    //  ERP 순번
	//@ApiModelProperty(notes = "ERP 생성 순번")
    private Long createNo;
    
    //  ERP 일련번호
	//@ApiModelProperty(notes = "ERP 생성 일련번호")
    private Long createSeq;
    
    public Long getProductSeq() {
        return productSeq;
    }

    public void setProductSeq(Long productSeq) {
        this.productSeq = productSeq;
    }
    
    public String getErpKey() {
		return erpKey;
	}

	public void setErpKey(String erpKey) {
		this.erpKey = erpKey;
	}

	public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAnnotherStyle() {
        return annotherStyle;
    }

    public void setAnnotherStyle(String annotherStyle) {
        this.annotherStyle = annotherStyle;
    }

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

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getCreateNo() {
        return createNo;
    }

    public void setCreateNo(Long createNo) {
        this.createNo = createNo;
    }
    
	public String getProductRfidYySeason() {
		return productRfidYySeason;
	}

	public void setProductRfidYySeason(String productRfidYySeason) {
		this.productRfidYySeason = productRfidYySeason;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Override
	public String toString() {
		return "ProductMaster [productSeq=" + productSeq + ", erpKey=" + erpKey + ", style=" + style
				+ ", annotherStyle=" + annotherStyle + ", productYy=" + productYy + ", productSeason=" + productSeason
				+ ", productRfidYySeason=" + productRfidYySeason + ", color=" + color + ", size=" + size + ", stat="
				+ stat + ", regDate=" + regDate + ", updDate=" + updDate + ", createDate=" + createDate + ", createNo="
				+ createNo + "]";
	}

	public Long getCreateSeq() {
		return createSeq;
	}

	public void setCreateSeq(Long createSeq) {
		this.createSeq = createSeq;
	}

}
