package com.systemk.spyder.Dto.Request;

import java.util.List;

public class StoreReissueBean {

    private Long userSeq;

    private String type;

    private String explanatory;

    private List<ReissueBean> content;

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public String getExplanatory() {
        return explanatory;
    }

    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }

    public List<ReissueBean> getContent() {
        return content;
    }

    public void setContent(List<ReissueBean> content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
