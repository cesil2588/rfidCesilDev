package com.systemk.spyder.Repository.Lepsilon;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Lepsilon.TboxPick;
import com.systemk.spyder.Entity.Lepsilon.Key.TboxPickKey;

public interface TboxPickRepository extends JpaRepository<TboxPick, TboxPickKey>{

	public List<TboxPick> findByKeyShipmentNo(String shipmentNo);
	
	public List<TboxPick> findByKeyShipmentNoAndOrderQty(String shipmentNo, BigDecimal orderQty);
	
	public List<TboxPick> findByOrderByRfidFlagAsc();
}
