package com.systemk.spyder.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.BatchTriggerDetail;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Service.CustomBean.CountModel;

public interface RfidTagService {
	
	public Resource loadFile(String filename);
	
	public ArrayList<RfidTagMaster> textLoad(File file, long seq, long userSeq);
	
	public ArrayList<RfidTagMaster> textLoadReissue(File file, long seq, long userSeq);
	
	public void update(String rfidTag, String publishDegree, String publishRegDate, long seq, String rfidSeq);
	
	public int update(RfidTagMaster tempRfidTag);
	
	public void update(long seq, String stat, long userSeq);
	
	public void reissueUpdate(String erpKey, String season, String orderDegree, String customerCd, String rfidSeq, String publishDegree, String publishRegDate);
	
	public Page<RfidTagMaster> findAll(Long bartagSeq, String stat, String search, String option, Pageable pageable);
	
	public Page<RfidTagMaster> getTagStat(Long bartagSeq, String stat, Pageable pageable);
	
	public Set<BatchTriggerDetail> textLoadBatch(BatchTrigger trigger) throws Exception;
	
	public Set<BatchTriggerDetail> textLoadReissueBatch(BatchTrigger trigger) throws Exception;
	
	public Long countByBartagSeqAndStat(Long bartagSeq, String stat) throws Exception;
	
	public Map<String, Object> getRfidTagDetail(String rfidTag) throws Exception;
	
	public CountModel count(long bartagSeq) throws Exception;
	
	public void textUploadValue() throws Exception;
	
	public void textLoadTestList(String publishDegree, String publishRegDate, long userSeq, List<BartagMaster> bartagList) throws Exception;
	
	public void textLoadTestAll(String publishDegree, String publishRegDate, long userSeq) throws Exception;
	
	public Long maxSeq() throws Exception;
	
	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception;
}
