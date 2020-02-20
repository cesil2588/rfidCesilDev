package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.RfidTagHistory;

public interface RfidTagHistoryRepository extends JpaRepository<RfidTagHistory, Long>{

	public List<RfidTagHistory> findByBarcode(String barcode);
	
	public List<RfidTagHistory> findByRfidTag(String rfidTag);
	
	public Page<RfidTagHistory> findByBarcode(String barcode, Pageable pageable);
	
	public List<RfidTagHistory> findByRfidTagLike(String rfidTag);
	
	public void deleteByRfidTag(String rfidTag);
		
}
