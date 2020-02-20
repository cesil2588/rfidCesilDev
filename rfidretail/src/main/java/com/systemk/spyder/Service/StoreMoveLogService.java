package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.StoreMoveBean;
import com.systemk.spyder.Dto.Request.StoreMoveListBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.StoreMoveLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Service.CustomBean.Group.StoreMoveGroupModel;

public interface StoreMoveLogService {

	public Page<StoreMoveLog> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq, String workYn, String confirmYn, String disuseYn, String type, Pageable pageable) throws Exception;

	public Map<String, Object> detail(Long seq) throws Exception;

	public Map<String, Object> storeMoveSave(StoreMoveBean storeMoveBean) throws Exception;

	public Map<String, Object> storeMoveErp(String barcode, Long userSeq, String type) throws Exception;

	public Map<String, Object> storeMoveUpdate(StoreMoveBean storeMoveBean) throws Exception;

	public Map<String, Object> storeMoveDelete(StoreMoveListBean storeMoveListBeanList) throws Exception;

	public Map<String, Object> storeMoveComplete(StoreMoveListBean storeMoveListBeanList) throws Exception;

	public Map<String, Object> storeMoveDisuse(StoreMoveListBean storeMoveListBeanList) throws Exception;

	public List<StoreMoveLog> save(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String returnType) throws Exception;

	public StoreMoveLog update(StoreMoveLog storeMoveLog, UserInfo userInfo) throws Exception;

	public Long maxWorkLine(String createDate, Long companySeq) throws Exception;

	public Long maxRfidWorkLine(String createDate, String boxBarcode, Long companySeq) throws Exception;

	public Map<String, Object> storeMoveBarcode(String barcode, Long companySeq, String companyType, String erpYn) throws Exception;

	public List<StoreMoveGroupModel> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq, String companyType, Pageable pageable) throws Exception;

	public Long CountGroupList(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq, String companyType) throws Exception;

	public Page<StoreMoveLog> findAll(String createDate, Long startCompanySeq, Long endCompanySeq, String workYn, String confirmYn, String disuseYn, String companyType, String search, String option, Pageable pageable) throws Exception;
}
