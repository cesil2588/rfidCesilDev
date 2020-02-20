package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch;


import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.Main.InventoryScheduleHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class StoreInventoryItemWriter implements ItemWriter<InventoryScheduleHeader> {

	private static final Logger log = LoggerFactory.getLogger(StoreInventoryItemWriter.class);

	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	public StoreInventoryItemWriter(InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository){
		this.inventoryScheduleHeaderRepository = inventoryScheduleHeaderRepository;
	}

	@Override
	public void write(List<? extends InventoryScheduleHeader> headerList) throws Exception {

		List<InventoryScheduleHeader> tempHeaderList = new ArrayList<InventoryScheduleHeader>();

		for(InventoryScheduleHeader header : headerList) {
			boolean headerFlag = true;

			// Row별로 온 재고실사 스타일정보를 Header, Style 트리 구조로 변경
			for(InventoryScheduleHeader tempHeader : tempHeaderList) {

				// 같은 업체 코드, 코너 코드, 등록구분, 생성일자가 같을 경우 검증, 재고실사 헤더로 인식
				if(header.equals(tempHeader)) {

					headerFlag = false;

					for(InventoryScheduleStyle style : header.getStyleList()) {

						boolean styleFlag = true;

						// 동일한 스타일이 있는지 확인
						if(tempHeader.getStyleList().contains(style)){
							styleFlag = false;
						}

						if(styleFlag) {
							tempHeader.getStyleList().add(style);

							// 추가된 스타일 수량 header total에 추가
							tempHeader.setTotalAmount(tempHeader.getTotalAmount() + style.getAmount());
							tempHeader.setTotalDisuseAmount(tempHeader.getTotalAmount());
						}
					}
				}
			}

			if(headerFlag) {
				tempHeaderList.add(header);
			}
		}

		inventoryScheduleHeaderRepository.save(tempHeaderList);

		log.info("ERP RFID 매장 재고실사 배치 종료");
	}
}
