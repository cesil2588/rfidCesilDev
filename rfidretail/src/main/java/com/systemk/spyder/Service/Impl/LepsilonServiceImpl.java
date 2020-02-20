package com.systemk.spyder.Service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Lepsilon.Treceipt;
import com.systemk.spyder.Entity.Lepsilon.TreceiptDetail;
import com.systemk.spyder.Repository.Lepsilon.TreceiptDetailRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptRepository;
import com.systemk.spyder.Service.LepsilonService;

@Service
public class LepsilonServiceImpl implements LepsilonService{
	
	private static final Logger log = LoggerFactory.getLogger(LepsilonServiceImpl.class);
	
	@Autowired
	private TreceiptRepository treceiptRepository;
	
	@Autowired
	private TreceiptDetailRepository treceiptDetailRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Treceipt storageBarcode(String barcode) throws Exception {
		return treceiptRepository.findByCustomerPoNo(barcode);
	}

	@Transactional
	@Override
	public boolean storageUpdate(String receiptNo) throws Exception {
		
		boolean success = true;
		
		List<TreceiptDetail> treceiptDetailList = treceiptDetailRepository.findByKeyReceiptNoAndStatusAndDelFlag(receiptNo, "999", "N");
		
		if(treceiptDetailList.size() == 0) {
			log.error("TreciptDetail 데이터 없음");
			return success = false;
		}
		
		for(TreceiptDetail detail : treceiptDetailList){
			detail.setStatus("0");
		}
		
		treceiptDetailRepository.save(treceiptDetailList);
		
		return success;
	}
}
