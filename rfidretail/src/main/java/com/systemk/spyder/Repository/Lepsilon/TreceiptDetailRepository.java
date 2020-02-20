package com.systemk.spyder.Repository.Lepsilon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Lepsilon.TreceiptDetail;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptDetailKey;

public interface TreceiptDetailRepository extends JpaRepository<TreceiptDetail, TreceiptDetailKey>{

	public List<TreceiptDetail> findByKeyReceiptNoAndStatusAndDelFlag(String receiptNo, String status, String delFlag);
	
	public List<TreceiptDetail> findByKeyReceiptNoAndDelFlag(String receiptNo, String delFlag);
	
	public List<TreceiptDetail> findByKeyReceiptNo(String receiptNo);
}
