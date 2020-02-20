package com.systemk.spyder.Entity.External;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@org.hibernate.annotations.DynamicUpdate
public class RfidTemp implements Serializable {

    private static final long serialVersionUID = 463751255812940844L;

    @Id
    private Long seq;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 1)
    private String functionYn;

    private Date updDate;

    private Date regDate;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getFunctionYn() {
        return functionYn;
    }

    public void setFunctionYn(String functionYn) {
        this.functionYn = functionYn;
    }
}
