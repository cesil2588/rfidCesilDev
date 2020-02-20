package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import com.systemk.spyder.Dto.Request.ErpWorkBean;
import com.systemk.spyder.Dto.Request.StoreMoveSendBean;


public interface ErpStoreMoveService {

	Map<String, Object> findAll(String startDate, String endDate, String completeYn, String customerCode, String moveType, String moveKind) throws Exception;

	Map<String, Object> findDetail(Long moveOrderSeq) throws Exception;

	Map<String, Object> getBoxDetailInfo(Long boxNum) throws Exception;

	Map<String, Object> getMaxBoxNo() throws Exception;

	Map<String, Object> saveWork(List<ErpWorkBean> erpWorkBean) throws Exception;

	Map<String, Object> DeleteWork(List<ErpWorkBean> erpWorkBean) throws Exception;

	Map<String, Object> completeWork(ErpWorkBean erpWorkBean) throws Exception;

	Map<String, Object> saveOrder(StoreMoveSendBean moveBean) throws Exception;

	Map<String, Object> deleteOrder(List<ErpWorkBean> erpWorkBean) throws Exception;

	Map<String, Object> completeOrder(ErpWorkBean erpWorkBean) throws Exception;

}
