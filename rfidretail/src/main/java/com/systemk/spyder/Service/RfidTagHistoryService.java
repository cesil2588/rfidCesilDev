package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.RfidTagHistory;

public interface RfidTagHistoryService {

	public Page<RfidTagHistory> findAll(String barcode, Pageable pageable) throws Exception;

	public List<RfidTagHistory> findByRfidTag(String rfidTag) throws Exception;

	public Map<String, Object> findByHistory(String value, String flag) throws Exception;
}
