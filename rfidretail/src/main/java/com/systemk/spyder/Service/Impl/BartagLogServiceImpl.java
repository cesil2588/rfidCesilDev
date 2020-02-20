package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BartagMasterLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterLogRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Service.BartagLogService;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.CustomBean.CountModel;

@Service
public class BartagLogServiceImpl implements BartagLogService{
	
	@Autowired
	private BartagMasterLogRepository bartagMasterLogRepository;
	
	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;
	
	@Autowired
	private BartagMasterRepository bartagMasterRepository;
	
	@Autowired
	private RfidTagService rfidTagService;

	@Transactional
	@Override
	public void init(BartagMaster bartagMaster, Date startDate) {
		
		BartagMasterLog bartagMasterLog = new BartagMasterLog();
		
		bartagMasterLog.setBartagMaster(bartagMaster);
		bartagMasterLog.setBeforeTotalAmount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat1Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat2Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat3Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat4Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat5Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat6Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat7Amount(Long.valueOf(0));
		bartagMasterLog.CopyCurrentData(bartagMaster);
		bartagMasterLog.setRegDate(new Date());
		bartagMasterLog.setStat("1");
		bartagMasterLog.setStartDate(startDate);
		bartagMasterLog.setEndDate(new Date());
		
		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(Long.valueOf(1));
		
		bartagMasterLog.setRegUserInfo(regUserInfo);
		
		bartagMasterLogRepository.save(bartagMasterLog);
	}
	
	@Transactional
	@Override
	public void init(BartagMaster bartagMaster, Long userSeq) {
		
		BartagMasterLog bartagMasterLog = new BartagMasterLog();
		
		bartagMasterLog.setBartagMaster(bartagMaster);
		bartagMasterLog.setBeforeTotalAmount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat1Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat2Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat3Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat4Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat5Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat6Amount(Long.valueOf(0));
		bartagMasterLog.setBeforeStat7Amount(Long.valueOf(0));
		bartagMasterLog.CopyCurrentData(bartagMaster);
		bartagMasterLog.setRegDate(new Date());
		bartagMasterLog.setStat("1");
		
		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);
		
		bartagMasterLog.setRegUserInfo(regUserInfo);
		
		bartagMasterLogRepository.save(bartagMasterLog);
		
	}

	@Transactional
	@Override
	public void save(BartagMaster beforeBartagMaster, BartagMaster currentBartagMaster, Date startDate, String stat) {
		
		BartagMasterLog bartagMasterLog = new BartagMasterLog();
		
		bartagMasterLog.setBartagMaster(currentBartagMaster);
		bartagMasterLog.CopyBeforeData(beforeBartagMaster);
		bartagMasterLog.CopyCurrentData(currentBartagMaster);
		bartagMasterLog.setStartDate(startDate);
		bartagMasterLog.setEndDate(new Date());
		bartagMasterLog.setRegDate(new Date());
		bartagMasterLog.setStat(stat);
		
		bartagMasterLogRepository.save(bartagMasterLog);
	}

	@Transactional
	@Override
	public void save(BartagMaster beforeBartagMaster, BartagMaster currentBartagMaster, Long userSeq, Date startDate, String stat) {
		
		BartagMasterLog bartagMasterLog = new BartagMasterLog();
		
		bartagMasterLog.setBartagMaster(currentBartagMaster);
		bartagMasterLog.CopyBeforeData(beforeBartagMaster);
		bartagMasterLog.CopyCurrentData(currentBartagMaster);
		bartagMasterLog.setStartDate(startDate);
		bartagMasterLog.setEndDate(new Date());
		bartagMasterLog.setRegDate(new Date());
		bartagMasterLog.setStat(stat);
		
		UserInfo regUserInfo = new UserInfo();
		regUserInfo.setUserSeq(userSeq);
		
		bartagMasterLog.setRegUserInfo(regUserInfo);
		
		bartagMasterLogRepository.save(bartagMasterLog);
	}

	@Override
	public BartagMaster beforeAmountSetting(BartagMaster bartag) {
		
		BartagMaster tempBartag = new BartagMaster();
		tempBartag.setStat1Amount(bartag.getStat1Amount());
		tempBartag.setStat2Amount(bartag.getStat2Amount());
		tempBartag.setStat3Amount(bartag.getStat3Amount());
		tempBartag.setStat4Amount(bartag.getStat4Amount());
		tempBartag.setStat5Amount(bartag.getStat5Amount());
		tempBartag.setStat6Amount(bartag.getStat6Amount());
		tempBartag.setStat7Amount(bartag.getStat7Amount());
		
		tempBartag.setTotalAmount(bartag.getStat1Amount() + 
							  bartag.getStat2Amount() + 
							  bartag.getStat3Amount() + 
							  bartag.getStat4Amount() + 
							  bartag.getStat5Amount() +
							  bartag.getStat6Amount() +
							  bartag.getStat7Amount());
		
		return tempBartag;
	}

	@Transactional(readOnly = true)
	@Override
	public BartagMaster currentAmountSetting(BartagMaster bartag) throws Exception{
		
//		CountModel count = rfidTagService.count(bartag.getBartagSeq());
		
		CountModel count = bartagMasterLogRepository.findCountAll(bartag.getBartagSeq());
		
		bartag.setStat1Amount(count.getStat1_amount());
		bartag.setStat2Amount(count.getStat2_amount());
		bartag.setStat3Amount(count.getStat3_amount());
		bartag.setStat4Amount(count.getStat4_amount());
		bartag.setStat5Amount(count.getStat5_amount());
		bartag.setStat6Amount(count.getStat6_amount());
		bartag.setStat7Amount(count.getStat7_amount());
		
		bartag.setTotalAmount(bartag.getStat1Amount() + 
							  bartag.getStat2Amount() + 
							  bartag.getStat3Amount() + 
							  bartag.getStat4Amount() + 
							  bartag.getStat5Amount() +
							  bartag.getStat6Amount() +
							  bartag.getStat7Amount());
		return bartag;
	}
	
	@Transactional
	@Override
	public void save(List<Long> seqList, UserInfo userInfo, Date startDate, String stat) throws Exception {
		
		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);
		
		if(seqList.size() > 0){
			
			for(Long seq : seqList){
				
				BartagMaster bartag = bartagMasterRepository.findOne(seq);
				
				BartagMaster tempBartag = beforeAmountSetting(bartag);
				bartag = currentAmountSetting(bartag);
				bartag.setUpdDate(new Date());
				bartag.setUpdUserInfo(userInfo);

				bartagMasterRepository.save(bartag);

				// 바택 수량 로그 업데이트
				save(tempBartag, bartag, userInfo.getUserSeq(), startDate, stat);
			}
		}
	}

	@Transactional
	@Override
	public void initTest(List<Long> seqList, UserInfo userInfo) throws Exception {
		
		HashSet<Long> distinctData = new HashSet<Long>(seqList);
		seqList = new ArrayList<Long>(distinctData);
		
		if(seqList.size() > 0){
			
			for(Long seq : seqList){
				
				BartagMaster bartag = bartagMasterRepository.findOne(seq);
				
				bartag = currentAmountSetting(bartag);
				bartag.setUpdDate(new Date());
				bartag.setUpdUserInfo(userInfo);

				bartagMasterRepository.save(bartag);
			}
		}
	}
}
