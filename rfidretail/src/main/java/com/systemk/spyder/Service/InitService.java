package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import com.systemk.spyder.Entity.Lepsilon.TboxPick;
import com.systemk.spyder.Entity.Lepsilon.Tshipment;

public interface InitService {
	
	public Map<String, Object> initStorage(String type) throws Exception;
	
	public Map<String, Object> resetStorage() throws Exception;
	
	public Map<String, Object> initRelease(String type) throws Exception;
	
	public Map<String, Object> resetRelease() throws Exception;
	
	public Map<String, Object> tboxList(List<String> referenceList) throws Exception;
	
	public Map<String, Object> tboxBarcodeCheck(String referenceNo, String barcode) throws Exception;
	
	public Map<String, Object> tboxUpdate(List<TboxPick> tboxPickList) throws Exception;
	
	public Map<String, Object> tboxDelete(List<TboxPick> tboxPickList) throws Exception;
	
	public Map<String, Object> tboxSave(TboxPick tboxPick) throws Exception;
	
	public Map<String, Object> tboxPickList() throws Exception;
	
	public Map<String, Object> tshipment(String referenceNo) throws Exception;
	
	public Map<String, Object> tshipmentUpdate(Tshipment tshipment) throws Exception;
	
	public Map<String, Object> tshipmentDelete(List<TboxPick> tboxPickList) throws Exception;
	
	public Map<String, Object> tshipmentSave(List<TboxPick> tboxPickList) throws Exception;
	
	public Map<String, Object> tshipmentInit(Tshipment tshipment) throws Exception;
	
	public Map<String, Object> tshipmentTotalUpdate(Tshipment tshipment) throws Exception;
}
