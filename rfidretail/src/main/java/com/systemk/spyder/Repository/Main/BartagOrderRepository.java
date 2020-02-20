package com.systemk.spyder.Repository.Main;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

public interface BartagOrderRepository extends JpaRepository<BartagOrder, Long>, JpaSpecificationExecutor<BartagOrder>{

	public boolean existsByCreateDateAndCreateSeqAndCreateNo(String createDate, Long createSeq, Long createNo);
	
	public BartagOrder findByCreateDateAndCreateSeqAndCreateNo(String createDate, Long createSeq, Long createNo);
	
	public BartagOrder findByErpKeyAndOrderDegree(String erpKey, String orderDegree);
	
	public List<BartagOrder> findByErpKeyAndOrderDegreeLessThan(String erpKey, String orderDegree);
	
	public List<BartagOrder> findByErpKeyAndOrderDegreeNot(String erpKey, String orderDegree);
	
	public List<BartagOrder> findByErpKeyAndOrderDegreeGreaterThan(String erpKey, String orderDegree);
	
	public List<BartagOrder> findByErpKeyAndCompleteYn(String erpKey, String completeYn);
	
	public List<BartagOrder> findByErpKey(String erpKey);
	
	public List<BartagOrder> findByProductionCompanyInfoCompanySeqIsNull();
		
}
