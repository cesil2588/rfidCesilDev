package com.systemk.spyder.Dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ReissueResult implements Serializable {

    private static final long serialVersionUID = 1317181908911799522L;

    // 태그 재발행 요청 일련번호
    private Long rfidTagReissueRequestSeq;

    // 생성일
    private String createDate;

    // 사유
    private String explanatory;

    // 확정 여부
    private String confirmYn;

    // 완료 여부
    private String completeYn;

    // 태그 재발행 수량
    private Long amount;

    @JsonInclude(Include.NON_DEFAULT)
    private Set<ReissueDetailResult> tagList = new HashSet<ReissueDetailResult>();

    public Long getRfidTagReissueRequestSeq() {
        return rfidTagReissueRequestSeq;
    }

    public void setRfidTagReissueRequestSeq(Long rfidTagReissueRequestSeq) {
        this.rfidTagReissueRequestSeq = rfidTagReissueRequestSeq;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExplanatory() {
        return explanatory;
    }

    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }

    public String getConfirmYn() {
        return confirmYn;
    }

    public void setConfirmYn(String confirmYn) {
        this.confirmYn = confirmYn;
    }

    public String getCompleteYn() {
        return completeYn;
    }

    public void setCompleteYn(String completeYn) {
        this.completeYn = completeYn;
    }

    public Set<ReissueDetailResult> getTagList() { return tagList; }

    public void setTagList(Set<ReissueDetailResult> tagList) { this.tagList = tagList; }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ReissueResult() {

    }

    public ReissueResult(RfidTagReissueRequest obj, boolean flag) {
        this.rfidTagReissueRequestSeq = obj.getRfidTagReissueRequestSeq();
        this.createDate = obj.getCreateDate();
        this.explanatory = obj.getExplanatory();
        this.confirmYn = obj.getConfirmYn();
        this.completeYn = obj.getCompleteYn();
        this.amount = obj.getRfidTagReissueRequestDetail() != null ? Long.valueOf(obj.getRfidTagReissueRequestDetail().size()): 0L;

        if (flag) {
            for (RfidTagReissueRequestDetail detail : obj.getRfidTagReissueRequestDetail()) {
                this.tagList.add(new ReissueDetailResult(detail));
            }
        }
    }
}
