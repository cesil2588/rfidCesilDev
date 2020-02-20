package com.systemk.spyder.Service;

import java.util.List;

import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Service.CustomBean.ValidBarcode;
import com.systemk.spyder.Service.CustomBean.ValidTag;

public interface TempProductionService {

	public void deleteStorageHeader(Long seq) throws Exception;
	
	public void deleteStorageAllStyle(Long seq) throws Exception;
	
	public void deleteStorageAllTag(Long seq) throws Exception;
	
	public void deleteReleaseHeader(Long seq) throws Exception;
	
	public void deleteReleaseAllBox(Long seq) throws Exception;
	
	public void deleteReleaseAllStyle(Long seq) throws Exception;
	
	public void deleteReleaseAllTag(Long seq) throws Exception;
	
	public List<String> exitsStorageAllTag(Long seq) throws Exception;
	
	public List<String> exitsReleaseAllTag(Long seq) throws Exception;
	
	public List<ValidTag> validStorageAllTag(Long seq) throws Exception;
	
	public List<Long> validStorageAllSeq(Long seq) throws Exception;
	
	public List<Long> validReleaseAllSeq(Long seq) throws Exception;
	
	public List<ValidTag> validReleaseAllTag(Long seq) throws Exception;
	
	public List<ValidBarcode> validTempReleaseAllBox(Long seq) throws Exception;
	
	public List<ValidTag> validTempStorageAllTag(Long seq) throws Exception;
	
	public List<ValidTag> validTempReleaseAllTag(Long seq) throws Exception;
	
	public void updateStorageHeaderSeq(Long headerSeq, Long styleSeq) throws Exception;
	
	public void updateStorageAllTag(Long seq, Long userSeq) throws Exception;
	
	public void updateReleaseStyleHeaderSeq(Long headerSeq, Long boxSeq) throws Exception;
	
	public void updateReleaseTagHeaderSeq(Long headerSeq, Long styleSeq) throws Exception;
	
	public void updateReleaseBoxTag(Long seq, Long userSeq, BoxInfo boxInfo) throws Exception;
}
