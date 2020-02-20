package com.systemk.spyder.Entity.Main;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ApiModel(description = "배치 Job Entity")
@Entity
@org.hibernate.annotations.DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BatchJob implements Serializable{
	
	private static final long serialVersionUID = -3031457966925175842L;

	//  배치 작업 일련번호
	//@ApiModelProperty(notes = "일련번호")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long batchJobSeq;

    //  명령어
	//@ApiModelProperty(notes = "배치 명령어")
	@Column(nullable = false, length = 50)
    private String command;

    //  크론, 딜레이 여부
	//@ApiModelProperty(notes = "Cron, Delay 구분(cron, delay)")
	@Column(nullable = false, length = 10)
    private String jobType;

    //  크론 표현식
	//@ApiModelProperty(notes = "Cron 표현식")
	@Column(nullable = true, length = 20)
    private String cron;

    //  딜레이 표현식
	//@ApiModelProperty(notes = "Delay 표현식")
	@Column(nullable = true, length = 10)
    private String delay;
	
	// 사용 여부
	//@ApiModelProperty(notes = "사용여부(Y, N)")
	@Column(nullable = false, length = 1)
    private String useYn;

    //  등록일
	//@ApiModelProperty(notes = "등록일")
    private Date regDate;

    //  수정일
	//@ApiModelProperty(notes = "수정일")
    private Date updDate;
    
    
	//@ApiModelProperty(notes = "등록자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="regUserSeq")
	private UserInfo regUserInfo;

    //  수정자
	//@ApiModelProperty(notes = "수정자")
    @JsonIgnoreProperties({"companyInfo", "userEmailInfo"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updUserSeq")
	private UserInfo updUserInfo;
    
    public Long getBatchJobSeq() {
        return batchJobSeq;
    }

    public void setBatchJobSeq(Long batchJobSeq) {
        this.batchJobSeq = batchJobSeq;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

	public UserInfo getRegUserInfo() {
		return regUserInfo;
	}

	public void setRegUserInfo(UserInfo regUserInfo) {
		this.regUserInfo = regUserInfo;
	}

	public UserInfo getUpdUserInfo() {
		return updUserInfo;
	}

	public void setUpdUserInfo(UserInfo updUserInfo) {
		this.updUserInfo = updUserInfo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
