package com.systemk.spyder.Repository.Main;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;

public interface ErpStoreReturnInfoRepository extends JpaRepository<ErpStoreReturnInfo, Long>, JpaSpecificationExecutor<ErpStoreReturnInfo> {
		
	public ErpStoreReturnInfo findByReturnInfoSeq(Long returnInfoSeq);
	
	public ErpStoreReturnInfo findByErpReturnNo(Long erpReturnNo);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_info SET execute_amount = execute_amount + :addAmount WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public void updateExecuteAmount(@Param("addAmount") Long addAmount, @Param("returnInfoSeq") Long returnInfoSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_info set confirm_amount = confirm_amount + :addAmount WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public void updateConfirmAmount(@Param("addAmount") int addAmount, @Param("returnInfoSeq") Long returnInfoSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_info set return_confirm_date = GETDATE(), confirm_yn = 'Y' WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public void updateConfirmYn(@Param("returnInfoSeq") Long returnInfoSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_info SET execute_amount = execute_amount - :delAmount WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public void updateDelExecuteAmount(@Param("delAmount") int delAmount, @Param("returnInfoSeq") Long returnInfoSeq);
	
	@Query(value = "SELECT order_amount FROM erp_store_return_info WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public int findOrderAmount(@Param("returnInfoSeq") Long returnInfoSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_info SET order_amount = order_amount + :addAmount WHERE erp_return_no = :erpReturnNo", nativeQuery = true)
	public void updateOrderAmount(@Param("addAmount") Long addAmount, @Param("erpReturnNo") Long erpReturnNo);
	
}
