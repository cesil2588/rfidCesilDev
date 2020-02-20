package com.systemk.spyder.Service;

import java.util.Map;

public interface RfidTagStatusService {

	public Map<String, Object> findByRfidTag(String rfidTag) throws Exception;
	
	public Map<String, Object> findByTagBarcode(String barcode) throws Exception;
}
