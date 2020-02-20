package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch;


import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class StoreStorageItemWriter implements ItemWriter<ReleaseScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StoreStorageItemWriter.class);

	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	private BoxInfoRepository boxInfoRepository;

	public StoreStorageItemWriter(ReleaseScheduleLogRepository releaseScheduleLogRepository,
								  BoxInfoRepository boxInfoRepository){
		this.releaseScheduleLogRepository = releaseScheduleLogRepository;
		this.boxInfoRepository = boxInfoRepository;
	}

	@Override
	public void write(List<? extends ReleaseScheduleLog> scheduleList) throws Exception {

		List<ReleaseScheduleLog> tempScheduleList = new ArrayList<ReleaseScheduleLog>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();

		for(ReleaseScheduleLog scheduleLog : scheduleList) {

			// 이미 컨베이어 통과된 제품은 아래 로직 수행 안함
			if(scheduleLog.getMappingYn().equals("Y")) {
				continue;
			}

			boolean headerFlag = true;

			// Row별로 온 스타일정보를 ReleaseLog, ReleaseDetailLog 트리 구조로 변경
			for(ReleaseScheduleLog tempScheduleLog : tempScheduleList) {

				// 같은 바코드인지 확인
				if(scheduleLog.equals(tempScheduleLog)) {

					headerFlag = false;

					for(ReleaseScheduleDetailLog detailLog : scheduleLog.getReleaseScheduleDetailLog()) {

						boolean styleFlag = true;

						if(tempScheduleLog.getReleaseScheduleDetailLog().contains(detailLog)){
							styleFlag = false;
						}

						if(styleFlag) {
							tempScheduleLog.getReleaseScheduleDetailLog().add(detailLog);
						}
					}

				}
			}

			if(headerFlag) {
				tempScheduleList.add(scheduleLog);
			}
		}

		for(ReleaseScheduleLog scheduleLog : tempScheduleList) {
			boxList.add(scheduleLog.getBoxInfo());
		}

		boxInfoRepository.save(boxList);

		releaseScheduleLogRepository.save(tempScheduleList);

		log.info("ERP RFID 매장 출고정보 배치 종료");
	}
}
