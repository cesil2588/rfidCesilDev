package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.RfidTagStatus;

public interface RfidTagStatusRepository extends JpaRepository<RfidTagStatus, Long>{

	public RfidTagStatus findByRfidTag(String rfidTag);
	
	public RfidTagStatus findByBarcodeAndStat(String barcode, String stat);
	
	public void deleteByRfidTag(String rfidTag);
	
	public List<RfidTagStatus> findByBarcode(String barcode);
	
}
