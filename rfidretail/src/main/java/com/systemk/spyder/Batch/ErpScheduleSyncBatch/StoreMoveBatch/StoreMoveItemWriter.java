package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.External.RfidSd02If;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.StoreMoveLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.StoreMoveLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Service.StoreMoveLogService;

public class StoreMoveItemWriter implements ItemWriter<RfidSd02If> {

	private static final Logger log = LoggerFactory.getLogger(StoreMoveItemWriter.class);

	private StoreMoveLogRepository storeMoveLogRepository;

	private CompanyInfoRepository companyInfoRepository;

	private UserInfoRepository userInfoRepository;

	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	private StoreMoveLogService storeMoveLogService;

	private BoxInfoRepository boxInfoRepository;

	public StoreMoveItemWriter(StoreMoveLogRepository storeMoveLogRepository,
							   CompanyInfoRepository companyInfoRepository,
							   UserInfoRepository userInfoRepository,
							   StoreStorageRfidTagRepository storeStorageRfidTagRepository,
							   StoreMoveLogService storeMoveLogService,
							   BoxInfoRepository boxInfoRepository){
		this.storeMoveLogRepository = storeMoveLogRepository;
		this.companyInfoRepository = companyInfoRepository;
		this.userInfoRepository = userInfoRepository;
		this.storeStorageRfidTagRepository = storeStorageRfidTagRepository;
		this.storeMoveLogService = storeMoveLogService;
		this.boxInfoRepository = boxInfoRepository;
	}

	@Override
	public void write(List<? extends RfidSd02If> erpStoreMoveList) throws Exception {
		/*
		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		for(RfidSd02IfBean erpStoreMove : erpStoreMoveList){

			boolean logFlag = false;
			boolean detailLogFlag = false;
			boolean subDetailLogFlag = false;

			for(StoreMoveLog storeMoveLog : storeMoveLogList){

				if(storeMoveLog.getBoxInfo().getBarcode().equals(erpStoreMove.getSd02Bxno()) &&
				   storeMoveLog.getBoxInfo().getStartCompanyInfo().getCustomerCode().equals(erpStoreMove.getSd02Frcd()) &&
				   storeMoveLog.getBoxInfo().getEndCompanyInfo().getCustomerCode().equals(erpStoreMove.getSd02Tocd())){

					logFlag = true;

					for(StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()){

						if(detailLog.getStyle().equals(erpStoreMove.getSd02Styl()) &&
						   detailLog.getColor().equals(erpStoreMove.getSd02Stcd().substring(0, 3)) &&
						   detailLog.getSize().equals(erpStoreMove.getSd02Stcd().substring(3, 6))){

							detailLogFlag = true;

							for(StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()){

								if(subDetailLog.getRfidTag().equals(erpStoreMove.getSd02RfidF())){
									subDetailLogFlag = true;
									break;
								}
							}

							if(!subDetailLogFlag){
								StoreMoveSubDetailLog subDetailLog = new StoreMoveSubDetailLog();
								subDetailLog.setRfidTag(erpStoreMove.getSd02RfidF());
								subDetailLog.setStat("1");
								subDetailLog.setRfidLineNum(erpStoreMove.getSd02Bxsr().longValue());
								subDetailLog.setRfidCreateDate(erpStoreMove.getSd02Mgdt());

								detailLog.getStoreMoveSubDetailLog().add(subDetailLog);
								detailLog.setAmount(detailLog.getAmount() + 1);
							}
						}
					}

					if(!detailLogFlag){

						StoreStorageRfidTag rfidTag = storeStorageRfidTagRepository.findByRfidTag(erpStoreMove.getSd02RfidF());

						if(rfidTag != null){

							StoreMoveDetailLog detailLog = new StoreMoveDetailLog();

							detailLog.setBarcode(erpStoreMove.getSd02Bxno());
							detailLog.setAmount(Long.valueOf(1));
							detailLog.setStyle(erpStoreMove.getSd02Styl());
							detailLog.setColor(erpStoreMove.getSd02Stcd().substring(0, 3));
							detailLog.setSize(erpStoreMove.getSd02Stcd().substring(3, 6));
							detailLog.setOrderDegree(StringUtil.convertCipher("3", rfidTag.getOrderDegree()));
							detailLog.setCompleteAmount(Long.valueOf(0));
							detailLog.setStyleSeq(rfidTag.getStoreStorageSeq());

							StoreMoveSubDetailLog subDetailLog = new StoreMoveSubDetailLog();
							subDetailLog.setRfidTag(erpStoreMove.getSd02RfidF());
							subDetailLog.setStat("1");
							subDetailLog.setRfidLineNum(erpStoreMove.getSd02Bxsr().longValue());
							subDetailLog.setRfidCreateDate(erpStoreMove.getSd02Mgdt());

							detailLog.getStoreMoveSubDetailLog().add(subDetailLog);

							storeMoveLog.getStoreMoveDetailLog().add(detailLog);
						}
					}
				}
			}

			if(!logFlag){

				if(!storeMoveLogRepository.existsByBoxInfoBarcode(erpStoreMove.getSd02Bxno())){

					CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCode(erpStoreMove.getSd02Frcd());
					CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCode(erpStoreMove.getSd02Tocd());
					StoreStorageRfidTag rfidTag = storeStorageRfidTagRepository.findByRfidTag(erpStoreMove.getSd02RfidF());

					if(startCompanyInfo != null && endCompanyInfo != null && rfidTag != null){

						BoxInfo boxInfo = new BoxInfo();
						boxInfo.setBarcode(erpStoreMove.getSd02Bxno());
						boxInfo.setType("03");
						boxInfo.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
						boxInfo.setStartCompanyInfo(startCompanyInfo);
						boxInfo.setEndCompanyInfo(endCompanyInfo);
						boxInfo.setReturnYn("Y");
						boxInfo.setStat("1");
						boxInfo.setRegDate(new Date());
						boxInfo.setRegUserInfo(userInfoRepository.findOne(Long.valueOf(1)));

						boxInfo = boxInfoRepository.save(boxInfo);

						Long workLine = storeMoveLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), boxInfo.getStartCompanyInfo().getCompanySeq());

						StoreMoveLog storeMoveLog = setStoreMoveLog(boxInfo, userInfoRepository.findOne(Long.valueOf(1)), ++workLine);
						storeMoveLog.setReturnType(erpStoreMove.getSd02Mggb());

						StoreMoveDetailLog detailLog = new StoreMoveDetailLog();

						detailLog.setBarcode(erpStoreMove.getSd02Bxno());
						detailLog.setAmount(Long.valueOf(1));
						detailLog.setStyle(erpStoreMove.getSd02Styl());
						detailLog.setColor(erpStoreMove.getSd02Stcd().substring(0, 3));
						detailLog.setSize(erpStoreMove.getSd02Stcd().substring(3, 6));
						detailLog.setOrderDegree(StringUtil.convertCipher("3", rfidTag.getOrderDegree()));
						detailLog.setCompleteAmount(Long.valueOf(0));
						detailLog.setStyleSeq(rfidTag.getStoreStorageSeq());

						StoreMoveSubDetailLog subDetailLog = new StoreMoveSubDetailLog();
						subDetailLog.setRfidTag(erpStoreMove.getSd02RfidF());
						subDetailLog.setStat("1");
						subDetailLog.setRfidLineNum(erpStoreMove.getSd02Bxsr().longValue());
						subDetailLog.setRfidCreateDate(erpStoreMove.getSd02Mgdt());

						detailLog.setStoreMoveSubDetailLog(new HashSet<StoreMoveSubDetailLog>());
						detailLog.getStoreMoveSubDetailLog().add(subDetailLog);

						storeMoveLog.setStoreMoveDetailLog(new HashSet<StoreMoveDetailLog>());
						storeMoveLog.getStoreMoveDetailLog().add(detailLog);

						storeMoveLogList.add(storeMoveLog);
					}
				}
			}
		}

		storeMoveLogRepository.save(storeMoveLogList);
		storeMoveLogRepository.flush();

		log.info("ERP 매장간 이동 배치 종료");	*/
	}

	private StoreMoveLog setStoreMoveLog(BoxInfo boxInfo, UserInfo userInfo, Long workLine) throws Exception {

		StoreMoveLog obj = new StoreMoveLog();

		obj.setRegDate(new Date());
		obj.setRegUserInfo(userInfo);
		obj.setStartWorkYn("N");
		obj.setStartConfirmYn("N");
		obj.setEndWorkYn("N");
		obj.setEndConfirmYn("N");
		obj.setBoxInfo(boxInfo);
		obj.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		obj.setWorkLine(workLine);
		obj.setDisuseYn("N");
		obj.setErpYn("Y");

		return obj;
	}
}
