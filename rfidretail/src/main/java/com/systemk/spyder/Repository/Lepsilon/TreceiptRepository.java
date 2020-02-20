package com.systemk.spyder.Repository.Lepsilon;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Lepsilon.Treceipt;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptKey;

public interface TreceiptRepository extends JpaRepository<Treceipt, TreceiptKey>{
	
	public Treceipt findByKeyReceiptno(String receiptNo);
	
	public Treceipt findByCustomerPoNo(String barcode);
}
