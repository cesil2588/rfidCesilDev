package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.RfidModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleChildModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleParentModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleSubChildModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Service.CustomBean.Group.BoxGroupModel;

public interface BoxService {

	public Page<BoxInfo> findAll(Long seq, String type, String stat, String search, String option, Pageable pageable) throws Exception;
	
	public Page<BoxInfo> findAll(String startDate, String endDate, Long startCompanySeq, String search, String option, Pageable pageable) throws Exception;
	
	public Page<BoxInfo> findAll(Long companySeq, Pageable pageable) throws Exception;
	
	public Page<BoxInfo> findAll(String createDate, Long companySeq, String type, String search, String option, Pageable pageable) throws Exception;
	
	public void saveAll(List<BoxInfo> boxList, UserInfo regUserInfo) throws Exception;
	
	public CountModel boxCount(String startDate, String endDate, Long companySeq) throws Exception;
	
	public CountModel boxCountAll(String startDate, String endDate, Long companySeq) throws Exception;
	
	public List<StyleModel> boxStyleCountProductionList(Long boxSeq) throws Exception;
	
	public List<RfidModel> boxStyleRfidProductionList(Long boxSeq, StyleModel style) throws Exception;
	
	public List<StyleModel> boxStyleCountDistributionList(Long boxSeq) throws Exception; 
	
	public List<RfidModel> boxStyleRfidDistributionList(Long boxSeq, StyleModel style) throws Exception;
	
	public List<StyleModel> boxStyleCountStoreList(Long boxSeq) throws Exception;
	
	public List<RfidModel> boxStyleRfidStoreList(Long boxSeq, StyleModel style) throws Exception;
	
	public List<StoreScheduleParentModel> boxStoreScheduleParentList() throws Exception;
	
	public List<StoreScheduleChildModel> boxStoreScheduleChildList() throws Exception;
	
	public List<StoreScheduleSubChildModel> boxStoreScheduleSubChildList() throws Exception;
	
	public List<BoxGroupModel> findBoxGroupList(String startDate, String endDate, Long startCompanySeq, String type, String search, String option, Pageable pageable) throws Exception;
	
	public Long CountBoxGroupList(String startDate, String endDate, Long startCompanySeq, String type, String search, String option) throws Exception;
	
	public Map<String, Object> findBoxMappingTagList(String type, Long boxSeq) throws Exception;
	
	public Map<String, Object> findBarcode(String barcode) throws Exception;
	
	public Map<String, Object> save(BoxInfo boxInfo) throws Exception;
	
	public Map<String, Object> update(BoxInfo boxInfo) throws Exception;

	List<StyleModel> returnStyleCountStoreList(Long boxSeq) throws Exception;
}
