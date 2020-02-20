package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.ProductionStorageLogRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Service.ProductionStorageLogService;
import com.systemk.spyder.Service.CustomBean.CountModel;

@Service
public class ProductionStorageLogServiceImpl implements ProductionStorageLogService{

	@Autowired
	private ProductionStorageLogRepository productionStorageLogRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Transactional
	@Override
	public void init(ProductionStorage productionStorage, Date startDate, Long userSeq) {

		ProductionStorageLog productionStorageLog = new ProductionStorageLog();
		productionStorageLog.setBeforeTotalAmount(Long.valueOf(0));
		productionStorageLog.setBeforeNonCheckAmount(Long.valueOf(0));
		productionStorageLog.setBeforeStockAmount(Long.valueOf(0));
		productionStorageLog.setBeforeReleaseAmount(Long.valueOf(0));
		productionStorageLog.setBeforeReissueAmount(Long.valueOf(0));
		productionStorageLog.setBeforeDisuseAmount(Long.valueOf(0));
		productionStorageLog.setBeforeReturnNonCheckAmount(Long.valueOf(0));
		productionStorageLog.setBeforeReturnAmount(Long.valueOf(0));
		productionStorageLog.CopyCurrentData(productionStorage);

		productionStorageLog.setProductionStorage(productionStorage);
		productionStorageLog.setType("1");
		productionStorageLog.setStat("1");
		productionStorageLog.setStartDate(startDate);
		productionStorageLog.setEndDate(new Date());
		productionStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		productionStorageLog.setRegUserInfo(regUserInfo);

		productionStorageLogRepository.save(productionStorageLog);
	}

	@Override
	public ProductionStorage beforeAmountSetting(ProductionStorage productionStorage) {
		return new ProductionStorage(productionStorage);
	}

	@Transactional(readOnly = true)
	@Override
	public ProductionStorage currentAmountSetting(ProductionStorage production) throws Exception {

//		CountModel count = productionStorageRfidTagService.count(production.getProductionStorageSeq());

		CountModel count = productionStorageLogRepository.findCountAll(production.getProductionStorageSeq());

		production.setNonCheckAmount(count.getStat1_amount());
		production.setStockAmount(count.getStat2_amount());
		production.setReleaseAmount(count.getStat3_amount());
		production.setReissueAmount(count.getStat4_amount());
		production.setDisuseAmount(count.getStat5_amount());
		production.setReturnNonCheckAmount(count.getStat6_amount());
		production.setReturnAmount(count.getStat7_amount());

		production.setTotalAmount(production.getNonCheckAmount() +
								  production.getStockAmount() +
								  production.getReleaseAmount() +
								  production.getReissueAmount() +
								  production.getDisuseAmount() +
								  production.getReturnNonCheckAmount() +
								  production.getReturnAmount());

		return production;
	}

	@Transactional
	@Override
	public void save(ProductionStorage beforeProductionStorage, ProductionStorage currentProductionStorage, Date startDate, String stat, String type) {

		ProductionStorageLog productionStorageLog = new ProductionStorageLog();

		productionStorageLog.setProductionStorage(currentProductionStorage);
		productionStorageLog.CopyBeforeData(beforeProductionStorage);
		productionStorageLog.CopyCurrentData(currentProductionStorage);
		productionStorageLog.setStat(stat);
		productionStorageLog.setType(type);
		productionStorageLog.setStartDate(startDate);
		productionStorageLog.setEndDate(new Date());
		productionStorageLog.setRegDate(new Date());

		productionStorageLogRepository.save(productionStorageLog);

	}

	@Transactional
	@Override
	public void save(ProductionStorage beforeProductionStorage, ProductionStorage currentProductionStorage, Long userSeq, Date startDate, String stat, String type) {

		ProductionStorageLog productionStorageLog = new ProductionStorageLog();

		productionStorageLog.setProductionStorage(currentProductionStorage);
		productionStorageLog.CopyBeforeData(beforeProductionStorage);
		productionStorageLog.CopyCurrentData(currentProductionStorage);
		productionStorageLog.setStat(stat);
		productionStorageLog.setType(type);
		productionStorageLog.setStartDate(startDate);
		productionStorageLog.setEndDate(new Date());
		productionStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		productionStorageLog.setRegUserInfo(regUserInfo);

		productionStorageLogRepository.save(productionStorageLog);
	}

	@Transactional
	@Override
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception {

		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);

		if(seqList.size() > 0){

			for(Long productionStorageSeq : seqList){

				ProductionStorage production = productionStorageRepository.findOne(productionStorageSeq);

				ProductionStorage tempProductionStorage = beforeAmountSetting(production);
				production = currentAmountSetting(production);
				production.setUpdDate(new Date());
				production.setUpdUserInfo(userInfo);

				productionStorageRepository.save(production);

				// 생산 수량 로그 업데이트
				save(tempProductionStorage, production, userInfo.getUserSeq(), startDate, stat, type);
			}
		}
	}

	@Transactional
	@Override
	public void initTest(List<Long> seqList, UserInfo userInfo) throws Exception {

		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);

		if(seqList.size() > 0){

			for(Long productionStorageSeq : seqList){

				ProductionStorage production = productionStorageRepository.findOne(productionStorageSeq);

				production = currentAmountSetting(production);
				production.setUpdDate(new Date());
				production.setUpdUserInfo(userInfo);

				productionStorageRepository.save(production);

			}
		}
	}

}
