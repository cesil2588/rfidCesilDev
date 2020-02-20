package com.systemk.spyder.Service;

import com.systemk.spyder.Dto.Request.InventoryScheduleBean;
import com.systemk.spyder.Dto.Request.InventoryScheduleDetailBean;
import com.systemk.spyder.Dto.Request.InventoryScheduleHeaderBean;
import com.systemk.spyder.Dto.Request.InventoryScheduleListBean;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Service.CustomBean.Group.InventoryScheduleGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface InventoryScheduleService {

	public List<InventoryScheduleGroupModel> findGroupList(String startDate, String endDate, Long companySeq, Pageable pageable) throws Exception;

	public Long CountGroupList(String startDate, String endDate, Long companySeq) throws Exception;

	public Map<String, Object> findAll(String startDate, String endDate, String customerCode, String confirmYn, String completeYn, String disuseYn, String type) throws Exception;

	public Page<InventoryScheduleHeader> findAll(String startDate, String endDate, Long companySeq, String confirmYn, String completeYn, String disuseYn, String type, Pageable pageable) throws Exception;

	public Page<InventoryScheduleHeader> findAll(String creatDate, Long companySeq, String confirmYn, String completeYn, String disuseYn, Pageable pageable) throws Exception;

	public Map<String, Object> detail(Long seq) throws Exception;

	public Map<String, Object> save(InventoryScheduleBean inventoryScheduleBean) throws Exception;

	public Map<String, Object> update(InventoryScheduleBean inventoryScheduleBean) throws Exception;

	public Map<String, Object> delete(InventoryScheduleListBean inventoryScheduleListBean) throws Exception;

	public Map<String, Object> complete(InventoryScheduleListBean inventoryScheduleListBean) throws Exception;

	public Map<String, Object> storeStorageDetail(Long userSeq, Long companySeq) throws Exception;

	public Map<String, Object> distributionStorageDetail(Long userSeq, Long companySeq) throws Exception;

	public Long maxWorkLine(String createDate, Long companySeq) throws Exception;

	public Map<String, Object> confirm(InventoryScheduleHeaderBean req) throws Exception;

	public Map<String, Object> init(InventoryScheduleHeaderBean req) throws Exception;

	public Map<String, Object> download(InventoryScheduleHeaderBean req) throws Exception;

	public Map<String, Object> save(InventoryScheduleDetailBean req) throws Exception;
}
