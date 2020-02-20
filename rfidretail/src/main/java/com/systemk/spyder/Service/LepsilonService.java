package com.systemk.spyder.Service;

import com.systemk.spyder.Entity.Lepsilon.Treceipt;

public interface LepsilonService {
	
	public Treceipt storageBarcode(String barcode) throws Exception;

	public boolean storageUpdate(String barcode) throws Exception;
}
