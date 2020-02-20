package com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Dto.Request.ErpStoreReturnNoBean;
import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSubInfo;
import com.systemk.spyder.Repository.Main.ErpStoreReturnInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnSubInfoRepository;

public class ReturnOrderListItemWriter implements ItemWriter<ErpStoreReturnInfo> {

	private static final Logger log = LoggerFactory.getLogger(ReturnOrderListItemWriter.class);
		
	private ErpStoreReturnSubInfoRepository erpStoreReturnSubInfoRepository;
	
	private ErpStoreReturnInfoRepository erpStoreReturnInfoRepository;
	
	public ReturnOrderListItemWriter(ErpStoreReturnInfoRepository erpStoreReturnInfoRepository, ErpStoreReturnSubInfoRepository erpStoreReturnSubInfoRepository){
		this.erpStoreReturnInfoRepository = erpStoreReturnInfoRepository;
		this.erpStoreReturnSubInfoRepository = erpStoreReturnSubInfoRepository;
	}
	
	@Override
	public void write(List<? extends ErpStoreReturnInfo> returnOrderList) throws Exception {
		
		ArrayList<ErpStoreReturnInfo> tempOrderList = new ArrayList<ErpStoreReturnInfo>();
		ArrayList<ErpStoreReturnSubInfo> tempOrderSubList = new ArrayList<ErpStoreReturnSubInfo>();
		List<ErpStoreReturnNoBean> erpNoList = new ArrayList<ErpStoreReturnNoBean>();

		for(ErpStoreReturnInfo ei : returnOrderList) {
			
			
			ErpStoreReturnSubInfo returnSubInfo = new ErpStoreReturnSubInfo();
			ErpStoreReturnNoBean erpNoBean = new ErpStoreReturnNoBean();
			
			returnSubInfo.setReturnInfoSeq(ei.getErpReturnNo());
			returnSubInfo.setReturnStyle(ei.getSku().substring(0,12));
			returnSubInfo.setReturnColor(ei.getSku().substring(12,15));
			returnSubInfo.setReturnSize(ei.getSku().substring(15));
			returnSubInfo.setOrderAmount(ei.getOrderAmount());
			returnSubInfo.setExecuteAmount(0L);
			returnSubInfo.setConfirmAmount(0L);
			returnSubInfo.setRfidYn(ei.getRfidYn());
			returnSubInfo.setAnotherYn("N");
			returnSubInfo.setErpSd14Key(ei.getErpOrderKey());
			tempOrderSubList.add(returnSubInfo);	
			
			erpNoBean.setErpRegDate(ei.getErpRegDate());
			erpNoBean.setErpReturnNo(ei.getErpReturnNo());
			erpNoBean.setOrderAmount(ei.getOrderAmount());
			erpNoBean.setReturnConfirmDate(ei.getReturnConfirmDate());
			erpNoBean.setReturnTitle(ei.getReturnTitle());
			erpNoBean.setReturnType(ei.getReturnType());
			erpNoBean.setFromCustomerCode(ei.getFromCustomerCode());
			erpNoBean.setFromCornerCode(ei.getFromCornerCode());
			erpNoBean.setToCustomerCode(ei.getToCustomerCode());
			erpNoBean.setToCornerCode(ei.getToCornerCode());
			erpNoBean.setRfidYn(ei.getRfidYn());
			erpNoList.add(erpNoBean);			
			}
			erpNoList = mergeErpNo(erpNoList);
			
			for(ErpStoreReturnNoBean bean : erpNoList) {
				ErpStoreReturnInfo returnInfoPre = erpStoreReturnInfoRepository.findByErpReturnNo(bean.getErpReturnNo());
				
				if(returnInfoPre!=null) {
					
					erpStoreReturnInfoRepository.updateOrderAmount(bean.getOrderAmount(), returnInfoPre.getErpReturnNo());
		
				}else {
					ErpStoreReturnInfo returnInfo = new ErpStoreReturnInfo();
					returnInfo.setReturnInfoSeq(bean.getErpReturnNo());
					returnInfo.setErpReturnNo(bean.getErpReturnNo());
					returnInfo.setErpRegDate(bean.getErpRegDate());
					returnInfo.setReturnConfirmDate(bean.getReturnConfirmDate());
					returnInfo.setReturnTitle(bean.getReturnTitle());
					returnInfo.setReturnType(bean.getReturnType());	
					returnInfo.setOrderAmount(bean.getOrderAmount());
					returnInfo.setExecuteAmount(0L);
					returnInfo.setConfirmAmount(0L);
					returnInfo.setConfirmYn("N");
					returnInfo.setRegType("E");
					returnInfo.setRfidYn(bean.getRfidYn());
					returnInfo.setTransYn("Y");
					returnInfo.setTransDate(new Date());
					returnInfo.setFromCustomerCode(bean.getFromCustomerCode());
					returnInfo.setFromCornerCode(bean.getFromCornerCode());
					returnInfo.setToCustomerCode(bean.getToCustomerCode());
					returnInfo.setToCornerCode(bean.getToCornerCode());
					tempOrderList.add(returnInfo);
					
				}
			}


		erpStoreReturnInfoRepository.save(tempOrderList);
		tempOrderSubList = getReturnInfoSeq(tempOrderSubList);
		erpStoreReturnSubInfoRepository.save(tempOrderSubList);
		
	
		log.info("ERP RFID 반품지시 리스트 배치 종료");
	}

	private List<ErpStoreReturnNoBean> mergeErpNo(List<ErpStoreReturnNoBean> erpNoBeanList) {
		
		List<ErpStoreReturnNoBean> tempErpNoList = new ArrayList<ErpStoreReturnNoBean>();
				
				for(ErpStoreReturnNoBean erpNo : erpNoBeanList) {
					
					boolean pushFlag = true;
					
					loop :
					for(ErpStoreReturnNoBean tempErpNo : tempErpNoList) {
						if(erpNo.getErpReturnNo().equals(tempErpNo.getErpReturnNo())) {
							
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
	
	private ArrayList<ErpStoreReturnSubInfo> getReturnInfoSeq(ArrayList<ErpStoreReturnSubInfo> subInfoList) {

		for(ErpStoreReturnSubInfo subInfo : subInfoList){
			ErpStoreReturnInfo returnInfo = erpStoreReturnInfoRepository.findByErpReturnNo(subInfo.getReturnInfoSeq());
			Long returnInfoSeq = returnInfo.getReturnInfoSeq();
			subInfo.setReturnInfoSeq(returnInfoSeq);
		}
		return subInfoList;
	}
}
