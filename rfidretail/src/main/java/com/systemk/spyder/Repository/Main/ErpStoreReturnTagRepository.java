package com.systemk.spyder.Repository.Main;


import java.util.*;

import javax.transaction.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import com.systemk.spyder.Entity.Main.*;

public interface ErpStoreReturnTagRepository extends JpaRepository<ErpStoreReturnTag, Long> {
		
	public List<ErpStoreReturnTag> findByWorkBoxNum(Long workBoxNum);
	
	public int countByWorkBoxNum(Long workBoxNum);
	
	public ErpStoreReturnTag findByRfidTag(String rfidTag);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_tag SET complete_yn = 'Y', complete_date = GETDATE() WHERE return_tag_seq = :returnTagSeq", nativeQuery = true)
	public void updateCompleteYn(@Param("returnTagSeq") Long returnTagSeq);
	
	public List<ErpStoreReturnTag> findByReturnInfoSubSeq(Long reutnInfoSubSeq);
	
	@Query(value="SELECT COUNT(*) FROM erp_store_return_tag WHERE return_info_sub_seq  = :returnInfoSubSeq AND complete_yn = 'Y'", nativeQuery = true)
	public int chkConfirmYn(@Param("returnInfoSubSeq") Long returnInfoSubSeq);
	
	@Query(value="SELECT return_info_sub_seq FROM erp_store_return_tag WHERE work_box_num = :workBoxNum", nativeQuery = true)
	public List<Long> findReturnInfoSubSeqByBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Query(value="SELECT SUM(confirm_amount) FROM erp_store_return_sub_info WHERE return_info_sub_seq IN (SELECT return_info_sub_seq FROM erp_store_return_tag WHERE work_box_num = :workBoxNum", nativeQuery = true)
	public int findSumConfirmAmountByBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Query(value="SELECT COUNT(*) FROM erp_store_return_tag WHERE work_box_num  = :workBoxNum AND complete_yn = 'Y'", nativeQuery = true)
	public int chkConfirmYnByBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_return_tag SET erp_return_invoice_num = :erpReturnInvoiceNum WHERE work_box_num = :workBoxNum", nativeQuery = true)
	public void updateErpReturnInvoiceNum(@Param("erpReturnInvoiceNum") String erpReturnInvoiceNum, @Param("workBoxNum") Long workBoxNum);
	
	@Query(value="SELECT count(*) FROM erp_store_return_tag WHERE rfid_tag = :rfidTag", nativeQuery = true)
	public int chkExistRfidTag(@Param("rfidTag") String rfidTag);
	
}