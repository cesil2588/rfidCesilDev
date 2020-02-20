package com.systemk.spyder.Service;

import java.util.Date;
import java.util.List;

import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.UserInfo;

public interface ProductionStorageLogService {

	public void init(ProductionStorage productionStorage, Date startDate, Long userSeq);
	
	public ProductionStorage beforeAmountSetting(ProductionStorage productionStorage);
	
	public ProductionStorage currentAmountSetting(ProductionStorage productionStorage) throws Exception;
	
	public void save(ProductionStorage beforeProductionStorage, ProductionStorage currentProductionStorage, Date startDate, String stat, String type);
	
	public void save(ProductionStorage beforeProductionStorage, ProductionStorage currentProductionStorage, Long userSeq, Date startDate, String stat, String type);
	
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception;
	
	public void initTest(List<Long> seqList, UserInfo userInfo) throws Exception;
}
