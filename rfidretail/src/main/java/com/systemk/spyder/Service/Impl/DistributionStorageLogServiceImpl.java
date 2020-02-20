package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.DistributionStorageLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidIb01IfRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageLogRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.ReturnBoxModel;
import com.systemk.spyder.Service.Mapper.ReturnBoxModelRowMapper;

@Service
public class DistributionStorageLogServiceImpl implements DistributionStorageLogService{

	@Autowired
	private DistributionStorageLogRepository distributionStorageLogRepository;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private RfidIb01IfRepository rfidIb01IfRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional
	@Override
	public void init(DistributionStorage distributionStorage, Date startDate, Long userSeq) {

		DistributionStorageLog distributionStorageLog = new DistributionStorageLog();
		distributionStorageLog.setBeforeTotalAmount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat1Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat2Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat3Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat4Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat5Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat6Amount(Long.valueOf(0));
		distributionStorageLog.setBeforeStat7Amount(Long.valueOf(0));
		distributionStorageLog.CopyCurrentData(distributionStorage);

		distributionStorageLog.setDistributionStorage(distributionStorage);
		distributionStorageLog.setType("1");
		distributionStorageLog.setStat("1");
		distributionStorageLog.setStartDate(startDate);
		distributionStorageLog.setEndDate(new Date());
		distributionStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		distributionStorageLog.setRegUserInfo(regUserInfo);

		distributionStorageLogRepository.save(distributionStorageLog);

	}

	@Override
	public DistributionStorage beforeAmountSetting(DistributionStorage distributionStorage) {
		return new DistributionStorage(distributionStorage);
	}

	@Transactional(readOnly = true)
	@Override
	public DistributionStorage currentAmountSetting(DistributionStorage distributionStorage) throws Exception{

//		CountModel count = distributionStorageRfidTagService.count(distributionStorage.getDistributionStorageSeq());

		CountModel count = distributionStorageLogRepository.findCountAll(distributionStorage.getDistributionStorageSeq());

		distributionStorage.setStat1Amount(count.getStat1_amount());
		distributionStorage.setStat2Amount(count.getStat2_amount());
		distributionStorage.setStat3Amount(count.getStat3_amount());
		distributionStorage.setStat4Amount(count.getStat4_amount());
		distributionStorage.setStat5Amount(count.getStat5_amount());
		distributionStorage.setStat6Amount(count.getStat6_amount());
		distributionStorage.setStat7Amount(count.getStat7_amount());

		distributionStorage.setTotalAmount(distributionStorage.getStat1Amount() +
											distributionStorage.getStat2Amount() +
											distributionStorage.getStat3Amount() +
											distributionStorage.getStat4Amount() +
											distributionStorage.getStat5Amount() +
											distributionStorage.getStat6Amount() +
											distributionStorage.getStat7Amount());

		return distributionStorage;
	}

	@Transactional
	@Override
	public void save(DistributionStorage beforeDistributionStorage, DistributionStorage currentDistributionStorage, Date startDate, String stat, String type) {

		DistributionStorageLog distributionStorageLog = new DistributionStorageLog();

		distributionStorageLog.setDistributionStorage(currentDistributionStorage);
		distributionStorageLog.CopyBeforeData(beforeDistributionStorage);
		distributionStorageLog.CopyCurrentData(currentDistributionStorage);
		distributionStorageLog.setStat(stat);
		distributionStorageLog.setType(type);
		distributionStorageLog.setStartDate(startDate);
		distributionStorageLog.setEndDate(new Date());
		distributionStorageLog.setRegDate(new Date());

		distributionStorageLogRepository.save(distributionStorageLog);

	}

	@Transactional
	@Override
	public void save(DistributionStorage beforeDistributionStorage, DistributionStorage currentDistributionStorage, Long userSeq, Date startDate, String stat, String type) {

		DistributionStorageLog distributionStorageLog = new DistributionStorageLog();

		distributionStorageLog.setDistributionStorage(currentDistributionStorage);
		distributionStorageLog.CopyBeforeData(beforeDistributionStorage);
		distributionStorageLog.CopyCurrentData(currentDistributionStorage);
		distributionStorageLog.setStat(stat);
		distributionStorageLog.setType(type);
		distributionStorageLog.setStartDate(startDate);
		distributionStorageLog.setEndDate(new Date());
		distributionStorageLog.setRegDate(new Date());

		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);

		distributionStorageLog.setRegUserInfo(regUserInfo);

		distributionStorageLogRepository.save(distributionStorageLog);
	}

	@Transactional
	@Override
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat, String type) throws Exception {

		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);

		for (Long distributionStorageSeq : seqList) {
			DistributionStorage distributionStorage = distributionStorageRepository.findOne(distributionStorageSeq);

			DistributionStorage tempDistributionStorage = beforeAmountSetting(distributionStorage);
			distributionStorage = currentAmountSetting(distributionStorage);
			distributionStorage.setUpdDate(new Date());
			distributionStorage.setUpdUserInfo(userInfo);

			distributionStorageRepository.save(distributionStorage);

			// 물류 수량 로그 업데이트
			save(tempDistributionStorage, distributionStorage, userInfo.getUserSeq(), startDate, stat, type);
		}
	}

	@Override
	public List<RfidIb01If> findCompleteBoxInfo(String startDate, String endDate) {

		List<RfidIb01If> totalBoxInfoList = new ArrayList<RfidIb01If>();
		totalBoxInfoList = rfidIb01IfRepository.findStorageCompleteBoxInfo(startDate, endDate);
		return totalBoxInfoList;

	}

	@Override
	public List<ReturnBoxModel> findReturnCompleteBoxInfo(String startDate, String endDate) {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		StringBuffer query = new StringBuffer();

		query.append("SELECT a.barcode, a.amount, a.style, a.color, a.size, CONVERT(char(8),a.complete_date, 112) AS completeDate, b.name AS companyName FROM ");
		query.append("(SELECT sdl.barcode, sdl.amount, sdl.style, sdl.color, sdl.size, sl.complete_date ");
		query.append("FROM STORAGE_SCHEDULE_DETAIL_LOG sdl, STORAGE_SCHEDULE_LOG sl ");
		query.append("WHERE sdl.storage_schedule_log_seq = sl.storage_schedule_log_seq ");
		query.append("AND sl.complete_yn = 'Y' and CONVERT(char(8), sl.complete_date, 112) BETWEEN :startDate AND :endDate AND sl.order_type = '10-R') a, ");
		query.append("(SELECT ci.name, ers.return_order_no FROM ERP_STORE_RETURN_SCHEDULE ers, COMPANY_INFO ci WHERE ers.start_company_seq = ci.company_seq GROUP BY ci.name, ers.return_order_no) b ");
		query.append("WHERE a.barcode = b.return_order_no  ");

		return nameTemplate.query(query.toString(), params, new ReturnBoxModelRowMapper());

	}



}
