package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.StoreReissueBean;
import com.systemk.spyder.Dto.Request.StoreReissueListBean;
import com.systemk.spyder.Dto.Response.ReissueResult;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.ReissueTagGroupModel;

public interface RfidTagReissueRequestService {

	public Map<String, Object> save(RfidTagReissueRequest rfidTagReissueRequest, String publishLocation) throws Exception;

	public Map<String, Object> reissueSave(RfidTagReissueRequest rfidTagReissueRequest) throws Exception;

	public Map<String, Object> reissueConfirm(List<ReissueTagGroupModel> groupList) throws Exception;

	public Map<String, Object> reissuePublish(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception;

	public Map<String, Object> reissueDistribution(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception;

	public Map<String, Object> reissueComplete(List<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailList) throws Exception;

	public Map<String, Object> findAll(String startDate, String endDate, String customerCode, String confirmYn) throws Exception;

	public Page<RfidTagReissueRequest> findAll(String createDate, Long companySeq, String confirmYn, String completeYn, String search, String option, Pageable pageable) throws Exception;

	public Map<String, Object> storeDetail(Long seq) throws Exception;

	public Map<String, Object> storeSave(StoreReissueBean reissueBean) throws Exception;

	public Map<String, Object> storeDelete(StoreReissueListBean reissueListBean) throws Exception;

	public Map<String, Object> storeComplete(StoreReissueListBean reissueListBean) throws Exception;

	public Long maxWorkLine(String createDate, String reissueType, Long companySeq) throws Exception;

	public List<ReissueTagGroupModel> findAll(String startDate, String endDate, Long companySeq, String type, String search, String option, Pageable pageable) throws Exception;

	public Long CountReissueTagGroupList(String startDate, String endDate, Long companySeq, String type, String search, String option) throws Exception;

	public CountModel reissueTagCount(String createDate, Long companySeq, String confirmYn, String completeYn) throws Exception;

	public Map<String, Object> reissueGroupUpdate(List<ReissueTagGroupModel> groupList) throws Exception;

	public Map<String, Object> reissueGroupDelete(List<ReissueTagGroupModel> groupList) throws Exception;

	public List<RfidTagReissueRequestDetail> findDetailList(List<ReissueTagGroupModel> groupList) throws Exception;

	public Map<String, Object> reissueUpdate(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception;

	public Map<String, Object> reissueDelete(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception;

	public void deleteReissueRequestDetail(Long rfidTagReissueRequestSeq) throws Exception;
}
