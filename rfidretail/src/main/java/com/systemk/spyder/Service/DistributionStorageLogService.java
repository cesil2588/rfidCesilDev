package com.systemk.spyder.Service;

import java.util.Date;
import java.util.List;

import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.ReturnBoxModel;

public interface DistributionStorageLogService {

	public void init(DistributionStorage distributionStorage, Date startDate, Long userSeq);
	
	public DistributionStorage beforeAmountSetting(DistributionStorage distributionStorage);
	
	public DistributionStorage currentAmountSetting(DistributionStorage distributionStorage) throws Exception;
	
	public void save(DistributionStorage beforeDistributionStorage, DistributionStorage currentDistributionStorage, Date startDate, String stat, String type);
	
	public void save(DistributionStorage beforeDistributionStorage, DistributionStorage currentDistributionStorage, Long userSeq, Date startDate, String stat, String type);
	
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception;

	public List<RfidIb01If> findCompleteBoxInfo(String startDate, String endDate);

	public List<ReturnBoxModel> findReturnCompleteBoxInfo(String startDate, String endDate);
}
