package com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Util.StringUtil;

public class BartagSerialItemWriter implements ItemWriter<BartagMaster> {

	private static final Logger log = LoggerFactory.getLogger(BartagSerialItemWriter.class);

	private RfidTagMasterRepository rfidTagMasterRepository;

	private CompanyInfoRepository companyInfoRepository;

	private BartagMasterRepository bartagMasterRepository;

	private RfidAc18IfRepository rfidAc18IfRepository;

	public BartagSerialItemWriter(RfidTagMasterRepository rfidTagMasterRepository,
								  CompanyInfoRepository companyInfoRepository,
								  BartagMasterRepository bartagMasterRepository,
								  RfidAc18IfRepository rfidAc18IfRepository){
		this.rfidTagMasterRepository = rfidTagMasterRepository;
		this.companyInfoRepository = companyInfoRepository;
		this.bartagMasterRepository = bartagMasterRepository;
		this.rfidAc18IfRepository = rfidAc18IfRepository;
	}

	@Override
	public void write(List<? extends BartagMaster> bartagList) throws Exception {

		List<BartagMaster> updateBartagList = new ArrayList<BartagMaster>();
		List<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();

		for(BartagMaster bartagMaster : bartagList){

			if(bartagMaster.getProductionCompanyInfo() == null){

				RfidAc18IfKey key = new RfidAc18IfKey();

				key.setAc18Crdt(bartagMaster.getCreateDate());
				key.setAc18Crsq(new BigDecimal(bartagMaster.getSeq()));
				key.setAc18Crno(new BigDecimal(bartagMaster.getLineSeq()));

				RfidAc18If rfidAc18If = rfidAc18IfRepository.findOne(key);

				CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(rfidAc18If.getAc18Prod());
				bartagMaster.setProductionCompanyInfo(productionCompanyInfo);

				updateBartagList.add(bartagMaster);
			}

			if(bartagMaster.getProductionCompanyInfo() != null){

				CompanyInfo companyInfo = companyInfoRepository.findByCustomerCode(bartagMaster.getProductionCompanyInfo().getCustomerCode());

				int start = Integer.parseInt(bartagMaster.getStartRfidSeq());
		    	int endAmount = Integer.parseInt(bartagMaster.getEndRfidSeq());

				for(int i=start; i<=endAmount; i++){

					RfidTagMaster rfidTag = new RfidTagMaster();

					rfidTag.setBartagSeq(bartagMaster.getBartagSeq());
					rfidTag.setErpKey(bartagMaster.getErpKey());
					rfidTag.setSeason(bartagMaster.getProductRfidYySeason());
					rfidTag.setOrderDegree(StringUtil.convertCipher("2", bartagMaster.getOrderDegree()));
					rfidTag.setCustomerCd(companyInfo.getCode());
					rfidTag.setPublishLocation("1");
					rfidTag.setRfidSeq(StringUtil.convertCipher("5", i));
					rfidTag.setStat("1");
					rfidTag.setRegDate(new Date());
					rfidTag.setCreateDate(bartagMaster.getCreateDate());
					rfidTag.setSeq(bartagMaster.getSeq());
					rfidTag.setLineSeq(bartagMaster.getLineSeq());

					rfidTagList.add(rfidTag);
				}
			}
		}

		bartagMasterRepository.save(updateBartagList);
		rfidTagMasterRepository.save(rfidTagList);

		bartagMasterRepository.flush();
		rfidTagMasterRepository.flush();

		log.info("바택 태그 시리얼 배치 종료");
	}
}
