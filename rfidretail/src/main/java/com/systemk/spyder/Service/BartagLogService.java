package com.systemk.spyder.Service;

import java.util.Date;
import java.util.List;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.UserInfo;

public interface BartagLogService {

	public void init(BartagMaster bartagMaster, Date startDate);
	
	public void init(BartagMaster bartagMaster, Long userSeq);
	
	public BartagMaster beforeAmountSetting(BartagMaster bartagMaster);
	
	public BartagMaster currentAmountSetting(BartagMaster bartag) throws Exception;
	
	public void save(BartagMaster beforeBartagMaster, BartagMaster currentBartagMaster, Date startDate, String stat);
	
	public void save(BartagMaster beforeBartagMaster, BartagMaster currentBartagMaster, Long userSeq, Date startDate, String stat);
	
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat) throws Exception;
	
	public void initTest(List<Long> seqList, UserInfo userInfo) throws Exception;
}
