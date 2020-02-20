package com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Dto.ErpStoreMoveNoBean;
import com.systemk.spyder.Entity.Main.ErpStoreMove;
import com.systemk.spyder.Entity.Main.ErpStoreMoveDetail;
import com.systemk.spyder.Repository.Main.ErpStoreMoveDetailRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveRepository;

public class MoveOrderListItemWriter implements ItemWriter<ErpStoreMove> {

	private static final Logger log = LoggerFactory.getLogger(MoveOrderListItemWriter.class);
		
	private ErpStoreMoveRepository erpStoreMoveRepository;
	
	private ErpStoreMoveDetailRepository erpStoreMoveDetailRepository;
	
	public MoveOrderListItemWriter(ErpStoreMoveRepository erpStoreMoveRepository, ErpStoreMoveDetailRepository erpStoreMoveDetailRepository){
		this.erpStoreMoveRepository = erpStoreMoveRepository;
		this.erpStoreMoveDetailRepository = erpStoreMoveDetailRepository;
	}
	
	@Override
	public void write(List<? extends ErpStoreMove> moveOrderList) throws Exception {
		
		ArrayList<ErpStoreMove> tempOrderList = new ArrayList<ErpStoreMove>();
		ArrayList<ErpStoreMoveDetail> tempOrderSubList = new ArrayList<ErpStoreMoveDetail>();
		List<ErpStoreMoveNoBean> erpNoList = new ArrayList<ErpStoreMoveNoBean>();

		for(ErpStoreMove em : moveOrderList) {
			
			
			ErpStoreMoveDetail moveDetail = new ErpStoreMoveDetail();
			ErpStoreMoveNoBean erpNoBean = new ErpStoreMoveNoBean();
			
			moveDetail.setMoveSeq(em.getOrderSeq());
			moveDetail.setMoveStyle(em.getSku().substring(0,12));
			moveDetail.setMoveColor(em.getSku().substring(12,15));
			moveDetail.setMoveSize(em.getSku().substring(15));
			moveDetail.setOrderAmount(em.getOrderAmount());
			moveDetail.setExecuteAmount(0L);
			moveDetail.setConfirmAmount(0L);
			//moveDetail.setRfidYn(em.getRfidYn());  ===>확인후
			moveDetail.setAnotherYn("N");
			tempOrderSubList.add(moveDetail);	
			
			erpNoBean.setOrderRegDate(em.getOrderRegDate());
			erpNoBean.setOrderSeq(em.getOrderSeq());
			erpNoBean.setOrderSerial(em.getOrderSerial());
			erpNoBean.setOrderAmount(em.getOrderAmount());
			erpNoBean.setFromCustomerCode(em.getFromCustomerCode());
			erpNoBean.setFromCornerCode(em.getFromCornerCode());
			erpNoBean.setToCustomerCode(em.getToCustomerCode());
			erpNoBean.setToCornerCode(em.getToCornerCode());
			erpNoList.add(erpNoBean);	
			
			}
		
			erpNoList = mergeErpNo(erpNoList);
			
			for(ErpStoreMoveNoBean bean : erpNoList) {
				ErpStoreMove moveInfoPre = erpStoreMoveRepository.findByOrderSeq(bean.getOrderSeq());
				
				if(moveInfoPre!=null) {
					
					erpStoreMoveRepository.updateOrderAmount(bean.getOrderAmount(), moveInfoPre.getOrderSeq());
		
				}else {
					ErpStoreMove moveInfo = new ErpStoreMove();
					moveInfo.setOrderRegDate(bean.getOrderRegDate());
					moveInfo.setOrderSeq(bean.getOrderSeq());
					moveInfo.setOrderSerial(bean.getOrderSerial());
					moveInfo.setFromCustomerCode(bean.getFromCustomerCode());
					moveInfo.setFromCornerCode(bean.getFromCornerCode());
					moveInfo.setToCustomerCode(bean.getToCustomerCode());
					moveInfo.setToCornerCode(bean.getToCornerCode());
					moveInfo.setOrderAmount(bean.getOrderAmount());
					moveInfo.setExecuteAmount(0L);
					moveInfo.setConfirmAmount(0L);
					moveInfo.setFromCompleteYn("N");
					moveInfo.setToCompleteYn("N");
					moveInfo.setMoveType("1");
					
					tempOrderList.add(moveInfo);
					
				}
			}


		erpStoreMoveRepository.save(tempOrderList);
		tempOrderSubList = getMoveDetailSeq(tempOrderSubList);
		erpStoreMoveDetailRepository.save(tempOrderSubList);
		
	
		log.info("ERP RFID 이동지시 리스트 배치 종료");
	}

	private List<ErpStoreMoveNoBean> mergeErpNo(List<ErpStoreMoveNoBean> erpNoBeanList) {
		
		List<ErpStoreMoveNoBean> tempErpNoList = new ArrayList<ErpStoreMoveNoBean>();
				
				for(ErpStoreMoveNoBean erpNo : erpNoBeanList) {
					
					boolean pushFlag = true;
					
					loop :
					for(ErpStoreMoveNoBean tempErpNo : tempErpNoList) {
						if(erpNo.getOrderSeq().equals(tempErpNo.getOrderSeq())) {
							
							pushFlag = false;
							
							tempErpNo.setOrderAmount(tempErpNo.getOrderAmount()+erpNo.getOrderAmount());
							
							break loop;
						}
					}
					
					if(pushFlag) {
						tempErpNoList.add(erpNo);
					}
				}
				
		return tempErpNoList;
	}
	
	private ArrayList<ErpStoreMoveDetail> getMoveDetailSeq(ArrayList<ErpStoreMoveDetail> moveDetailList) {

		for(ErpStoreMoveDetail moveDetail : moveDetailList){	
			ErpStoreMove storeMove = erpStoreMoveRepository.findByOrderSeq(moveDetail.getMoveSeq());
			Long moveSeq = storeMove.getMoveSeq();
			moveDetail.setMoveSeq(moveSeq);
			moveDetail.setTransYn("Y");
			moveDetail.setTransDate(new Date());
		}
		return moveDetailList;
	}
}
