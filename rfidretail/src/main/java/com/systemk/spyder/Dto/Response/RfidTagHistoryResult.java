package com.systemk.spyder.Dto.Response;

import com.systemk.spyder.Entity.Main.RfidTagHistory;

import java.io.Serializable;
import java.util.Date;

public class RfidTagHistoryResult implements Serializable {

    // 작업 내역
    private String explanatory;

    // 작업자
    private String regUserId;

    // 작업일
    private Date regDate;

    public String getExplanatory() {
        return explanatory;
    }

    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public RfidTagHistoryResult() {
    }

    public RfidTagHistoryResult(RfidTagHistory rfidTagHistory) {
        this.explanatory = rfidTagHistory.getExplanatory();
        this.regUserId = rfidTagHistory.getRegUserInfo().getUserId();
        this.regDate = rfidTagHistory.getRegDate();
    }

    // TODO 작업내역 가져오는 부분 고민하기
    public String workDetail(String work){
        return null;
    }
}
