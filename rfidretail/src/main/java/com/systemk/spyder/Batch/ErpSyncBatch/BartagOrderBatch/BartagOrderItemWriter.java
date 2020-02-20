package com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Repository.Main.BartagOrderRepository;

public class BartagOrderItemWriter implements ItemWriter<BartagOrder> {

	private static final Logger log = LoggerFactory.getLogger(BartagOrderItemWriter.class);

	private BartagOrderRepository bartagOrderRepository;

	public BartagOrderItemWriter(BartagOrderRepository bartagOrderRepository){
		this.bartagOrderRepository = bartagOrderRepository;
	}

	@Override
	public void write(List<? extends BartagOrder> bartagOrderList) throws Exception {

		List<BartagOrder> bartagOrderInsertList = new ArrayList<BartagOrder>();

		for(BartagOrder bartagOrder : bartagOrderList){

			// 등록
			if (bartagOrder.getErpStat().equals("C")) {

				if(!bartagOrderRepository.existsByCreateDateAndCreateSeqAndCreateNo(bartagOrder.getCreateDate(), bartagOrder.getCreateSeq(), bartagOrder.getCreateNo())) {

					if(bartagOrder.getProductionCompanyInfo() == null) {
						continue;
					}

					bartagOrderInsertList.add(bartagOrder);

					// 이전 RFID 생산 요청 완료된 오더차수가 있으면 종결 처리, 생산 요청 미완료 오더차수면 그대로 보존
//					bartagOrderService.bartagOrderEndUpdate(bartagOrder.getErpKey(), bartagOrder.getOrderDegree());
				}

				// 수정, 삭제
			} else if (bartagOrder.getErpStat().equals("U") || bartagOrder.getErpStat().equals("D")) {

				BartagOrder tempBartagOrder = bartagOrderRepository.findByCreateDateAndCreateSeqAndCreateNo(bartagOrder.getCreateDate(), bartagOrder.getCreateSeq(), bartagOrder.getCreateNo());

				tempBartagOrder.CopyData(bartagOrder);
				tempBartagOrder.setUpdDate(new Date());
				bartagOrderInsertList.add(tempBartagOrder);
			}
		}

		bartagOrderRepository.save(bartagOrderInsertList);
		bartagOrderRepository.flush();

		log.info("바택 오더 배치 종료");
	}
}
