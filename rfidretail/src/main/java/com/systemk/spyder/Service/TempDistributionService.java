package com.systemk.spyder.Service;

import java.util.List;

import com.systemk.spyder.Entity.Main.BoxInfo;

public interface TempDistributionService {

	public void deleteReleaseHeader(Long seq) throws Exception;
	
	public void deleteReleaseAllBox(Long seq) throws Exception;
	
	public void deleteReleaseAllStyle(Long seq) throws Exception;
	
	public void deleteReleaseAllTag(Long seq) throws Exception;
	
	public List<String> exitsReleaseAllTag(Long seq) throws Exception;
	
	public List<Long> existsReleaseStorageSeq(Long tempBoxSeq) throws Exception;
	
	public void updateReleaseBoxTag(Long seq, Long userSeq, BoxInfo boxInfo) throws Exception;
	
	public void updateReleaseStyleHeaderSeq(Long headerSeq, String barcode) throws Exception;
	
	public void updateReleaseTagHeaderSeq(Long headerSeq, Long styleSeq) throws Exception;
	
}
