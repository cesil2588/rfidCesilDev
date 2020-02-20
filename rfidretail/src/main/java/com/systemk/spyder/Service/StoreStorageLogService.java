package com.systemk.spyder.Service;

import java.util.Date;
import java.util.List;

import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.UserInfo;

public interface StoreStorageLogService {

	public void init(StoreStorage storeStorage, Long userSeq, Date startDate, String type);
	
	public StoreStorage beforeAmountSetting(StoreStorage storeStorage);
	
	public StoreStorage currentAmountSetting(StoreStorage storeStorage) throws Exception;
	
	public void save(StoreStorage beforeStoreStorage, StoreStorage currentStoreStorage, Date startDate, String stat, String type);
	
	public void save(StoreStorage beforeStoreStorage, StoreStorage currentStoreStorage, Long userSeq, Date startDate, String stat, String type);
	
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception;
}
