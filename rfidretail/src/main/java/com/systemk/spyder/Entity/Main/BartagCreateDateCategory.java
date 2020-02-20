package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BartagCreateDateCategory implements Serializable{

	private static final long serialVersionUID = -4035336700334197827L;

	//  바택 발행일자 일련번호
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bartagCreateDateCategorySeq;

    //  발행일자
	@Column(nullable = false, length = 8)
    private String createDate;

    //  등록일자
    private Date regDate;

    public Integer getBartagCreateDateCategorySeq() {
        return bartagCreateDateCategorySeq;
    }

    public void setBartagCreateDateCategorySeq(Integer bartagCreateDateCategorySeq) {
        this.bartagCreateDateCategorySeq = bartagCreateDateCategorySeq;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "BartagCreateDateCategory [bartagCreateDateCategorySeq=" + bartagCreateDateCategorySeq + ", createDate="
				+ createDate + ", regDate=" + regDate + "]";
	}
}
