package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.StoreStorageLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.StoreStorageLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Service.StoreStorageLogService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;
import com.systemk.spyder.Service.CustomBean.CountModel;

@Service
public class StoreStorageLogServiceImpl implements StoreStorageLogService{

	@Autowired
	private StoreStorageLogRepository storeStorageLogRepository;

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Transactional
	@Override
	public void init(StoreStorage storeStorage, Long userSeq, Date startDate, String type) {

		StoreStorageLog storeStorageLog = new StoreStorageLog();
		storeStorageLog.setBeforeTotalAmount(Long.valueOf(0));
		storeStorageLog.setBeforeStat1Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat2Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat3Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat4Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat5Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat6Amount(Long.valueOf(0));
		storeStorageLog.setBeforeStat7Amount(Long.valueOf(0));
		storeStorageLog.CopyCurrentData(storeStorage);

		storeStorageLog.setStoreStorage(storeStorage);
		storeStorageLog.setType(type);
		storeStorageLog.setStat("1");
		storeStorageLog.setStartDate(startDate);
		storeStorageLog.setEndDate(new Date());
		storeStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		storeStorageLog.setRegUserInfo(regUserInfo);

		storeStorageLogRepository.save(storeStorageLog);
	}

	@Override
	public StoreStorage beforeAmountSetting(StoreStorage storeStorage) {
		return new StoreStorage(storeStorage);
	}

	@Transactional(readOnly = true)
	@Override
	public StoreStorage currentAmountSetting(StoreStorage storeStorage) throws Exception{

//		CountModel count = storeStorageRfidTagService.count(storeStorage.getStoreStorageSeq());

		CountModel count = storeStorageLogRepository.findCountAll(storeStorage.getStoreStorageSeq());

		storeStorage.setStat1Amount(count.getStat1_amount());
		storeStorage.setStat2Amount(count.getStat2_amount());
		storeStorage.setStat3Amount(count.getStat3_amount());
		storeStorage.setStat4Amount(count.getStat4_amount());
		storeStorage.setStat5Amount(count.getStat5_amount());
		storeStorage.setStat6Amount(count.getStat6_amount());
		storeStorage.setStat7Amount(count.getStat7_amount());

		storeStorage.setTotalAmount(storeStorage.getStat1Amount() +
									storeStorage.getStat2Amount() +
									storeStorage.getStat3Amount() +
									storeStorage.getStat4Amount() +
									storeStorage.getStat5Amount() +
									storeStorage.getStat6Amount() +
									storeStorage.getStat7Amount());

		return storeStorage;
	}

	@Transactional
	@Override
	public void save(StoreStorage beforeStoreStorage, StoreStorage currentStoreStorage, Date startDate, String stat, String type) {

		StoreStorageLog storeStorageLog = new StoreStorageLog();

		storeStorageLog.setStoreStorage(currentStoreStorage);
		storeStorageLog.CopyBeforeData(beforeStoreStorage);
		storeStorageLog.CopyCurrentData(currentStoreStorage);
		storeStorageLog.setStat(stat);
		storeStorageLog.setType(type);
		storeStorageLog.setStartDate(startDate);
		storeStorageLog.setEndDate(new Date());
		storeStorageLog.setRegDate(new Date());

		storeStorageLogRepository.save(storeStorageLog);
	}

	@Transactional
	@Override
	public void save(StoreStorage beforeStoreStorage, StoreStorage currentStoreStorage, Long userSeq, Date startDate, String stat, String type) {

		StoreStorageLog storeStorageLog = new StoreStorageLog();

		storeStorageLog.setStoreStorage(currentStoreStorage);
		storeStorageLog.CopyBeforeData(beforeStoreStorage);
		storeStorageLog.CopyCurrentData(currentStoreStorage);
		storeStorageLog.setStat(stat);
		storeStorageLog.setType(type);
		storeStorageLog.setStartDate(startDate);
		storeStorageLog.setEndDate(new Date());
		storeStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		storeStorageLog.setRegUserInfo(regUserInfo);

		storeStorageLogRepository.save(storeStorageLog);
	}

	@Transactional
	@Override
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception {

		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);

		for (Long storeStorageSeq : seqList) {

			StoreStorage storeStorage = storeStorageRepository.findOne(storeStorageSeq);

			StoreStorage tempStoreStorage = beforeAmountSetting(storeStorage);
			storeStorage = currentAmountSetting(storeStorage);
			storeStorage.setUpdDate(new Date());
			storeStorage.setUpdUserInfo(userInfo);

			storeStorageRepository.save(storeStorage);

			// 판매 수량 로그 업데이트
			save(tempStoreStorage, storeStorage, userInfo.getUserSeq(), startDate, stat, type);
		}
	}

}
