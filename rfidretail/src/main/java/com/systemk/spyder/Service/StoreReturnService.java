package com.systemk.spyder.Service;

import java.util.*;

import com.systemk.spyder.Dto.Request.*;

public interface StoreReturnService {

	public Map<String, Object> findErpReturnInfoAll(String startDate, String endDate, String returnType, String userId) throws Exception;

	public Map<String, Object> findErpReturnDetailInfo(Long returnInfoSeq) throws Exception;

	public Map<String, Object> erpReturnWorkSave(List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception;

	public Map<String, Object> DeleteErpReturnWork(List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception;

	public Map<String, Object> completeReturnWork(ErpReturnWorkBean erpReturnWorkBean) throws Exception;
	
	public Map<String, Object> getMaxBoxNo() throws Exception;

	public Map<String, Object> getBoxDetailInfo(Long boxNum) throws Exception;

}
