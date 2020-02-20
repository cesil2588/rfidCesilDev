package com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;
import com.systemk.spyder.Entity.Main.InventoryScheduleTag;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

public class DistributionInventoryItemWriter implements ItemWriter<InventoryScheduleHeader> {

	private static final Logger log = LoggerFactory.getLogger(DistributionInventoryItemWriter.class);

	private DistributionStorageRepository distributionStorageRepository;

	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	private DistributionStorageLogService distributionStorageLogService;

	private DistributionStorageRfidTagService distributionStorageRfidTagService;


	private RfidTagHistoryRepository rfidTagHistoryRepository;

	public DistributionInventoryItemWriter(DistributionStorageRepository distributionStorageRepository,
							   DistributionStorageRfidTagRepository distributionStorageRfidTagRepository,
							   DistributionStorageLogService distributionStorageLogService,
							   DistributionStorageRfidTagService distributionStorageRfidTagService,
							   RfidTagHistoryRepository rfidTagHistoryRepository){
		this.distributionStorageRepository = distributionStorageRepository;
		this.distributionStorageRfidTagRepository = distributionStorageRfidTagRepository;
		this.distributionStorageLogService = distributionStorageLogService;
		this.distributionStorageRfidTagService = distributionStorageRfidTagService;
		this.rfidTagHistoryRepository = rfidTagHistoryRepository;
	}

	@Override
	public void write(List<? extends InventoryScheduleHeader> tempScheduleList) throws Exception {

		Date startDate = new Date();

		for(InventoryScheduleHeader schedule : tempScheduleList){

			List<Long> distributionStorageSeqList = new ArrayList<Long>();
			List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
			List<RfidTagHistory> rfidTaghistoryList = new ArrayList<RfidTagHistory>();

			/*
			for(InventoryScheduleStyle detail : schedule.getStyleList()){

				DistributionStorage storage = distributionStorageRepository.findOne(detail.getStyleSeq());

				if(storage == null){
					continue;
				}

				Long completeAmount = Long.valueOf(0);

				for(InventoryScheduleTag subDetail : detail.getRfidTagList()){

					DistributionStorageRfidTag distributionRfidTag = distributionStorageRfidTagRepository.findByRfidTag(subDetail.getRfidTag());

					if(distributionRfidTag == null){

						subDetail.setStat("3");
						continue;
					}

					if(!distributionRfidTag.getCustomerCd().equals(storage.getDistributionCompanyInfo().getCode())){

						distributionStorageSeqList.add(distributionRfidTag.getDistributionStorageSeq());
						distributionStorageSeqList.add(storage.getDistributionStorageSeq());

						DistributionStorageRfidTag tempRfidTag = distributionStorageRfidTagService.moveRfidTag(distributionRfidTag, storage, schedule.getUpdUserInfo());

						rfidTaghistoryList.add(setHistory(tempRfidTag, schedule));
						distributionStorageRfidTagList.add(tempRfidTag);

						completeAmount ++;
						subDetail.setStat("4");
						continue;
					}

					if(!distributionRfidTag.getStat().equals("2")){

						distributionStorageSeqList.add(distributionRfidTag.getDistributionStorageSeq());

						DistributionStorageRfidTag tempRfidTag = distributionStorageRfidTagService.updateRfidTag(distributionRfidTag, schedule.getUpdUserInfo());

						rfidTaghistoryList.add(setHistory(tempRfidTag, schedule));
						distributionStorageRfidTagList.add(tempRfidTag);

						completeAmount ++;
						subDetail.setStat("5");
						continue;
					}

					completeAmount ++;
					subDetail.setStat("2");
				}

				detail.setCompleteAmount(completeAmount);
			}
			*/

			schedule.setCompleteYn("Y");
			schedule.setCompleteDate(new Date());
			schedule.setUpdDate(new Date());

			rfidTagHistoryRepository.save(rfidTaghistoryList);
			distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);

			distributionStorageLogService.save(distributionStorageSeqList, schedule.getUpdUserInfo(), startDate, "8", "4");
		}

	}

	private RfidTagHistory setHistory(DistributionStorageRfidTag tag, InventoryScheduleHeader schedule) throws Exception{

		RfidTagHistory rfidTagHistory = new RfidTagHistory();

		rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
		rfidTagHistory.setErpKey(tag.getErpKey());
		rfidTagHistory.setRfidTag(tag.getRfidTag());
		rfidTagHistory.setRfidSeq(tag.getRfidSeq());
		rfidTagHistory.setWork("28");
		rfidTagHistory.setRegUserInfo(schedule.getUpdUserInfo());
		rfidTagHistory.setCompanyInfo(schedule.getCompanyInfo());
		rfidTagHistory.setRegDate(new Date());

		return rfidTagHistory;
	}

}
