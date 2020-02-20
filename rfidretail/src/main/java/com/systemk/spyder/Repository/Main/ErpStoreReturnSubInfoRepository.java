package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSubInfo;

public interface ErpStoreReturnSubInfoRepository extends JpaRepository<ErpStoreReturnSubInfo, Long> {

	public List<ErpStoreReturnSubInfo> findByReturnInfoSeq(Long returnInfoSeq);
	
	public ErpStoreReturnSubInfo findByReturnInfoSubSeq(Long returnInfoSubSeq);
	
	public List<ErpStoreReturnSubInfo> findByReturnStyleAndReturnColorAndReturnSize(String style, String color, String size);
		
	@Query(value = "SELECT return_info_seq FROM erp_store_return_sub_info WHERE return_info_sub_seq = :returnInfoSubSeq", nativeQuery = true)
	public Long findReturnInfoSeqBySubSeq(@Param("returnInfoSubSeq") Long returnInfoSubSeq);
		
	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_return_sub_info SET execute_amount = execute_amount + :addAmount WHERE return_info_sub_seq = :returnInfoSubSeq", nativeQuery = true)
	public void updateExecuteAmount(@Param("addAmount") Long addAmount, @Param("returnInfoSubSeq") Long returnInfoSubSeq);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_return_sub_info SET confirm_amount = confirm_amount + :addAmount WHERE return_info_sub_seq = :returnInfoSubSeq", nativeQuery = true)
	public void updateConfirmAmount(@Param("addAmount") int addAmount, @Param("returnInfoSubSeq") Long returnInfoSubSeq);
	 
	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_return_sub_info SET execute_amount = execute_amount - :delAmount WHERE return_info_sub_seq = :returnInfoSubSeq", nativeQuery = true)
	public void updateDelExecuteAmount(@Param("delAmount") int delAmount, @Param("returnInfoSubSeq") Long returnInfoSubSeq);
	
	@Query(value="SELECT SUM(confirm_amount) FROM erp_store_return_sub_info WHERE return_info_seq = :returnInfoSeq", nativeQuery = true)
	public int findSumConfirmAmount(@Param("returnInfoSeq") Long returnInfoSeq);
	
	@Query(value="SELECT esi.return_info_sub_seq, return_info_seq, return_style, return_color, return_size, order_amount, et.execute_amount, confirm_amount, rfid_yn, another_yn, erp_sd14Key FROM erp_store_return_sub_info esi " + 
			"INNER JOIN (SELECT COUNT(return_info_sub_seq) AS execute_amount, return_info_sub_seq FROM erp_store_return_tag WHERE work_box_num = :workBoxNum GROUP BY return_info_sub_seq) et " + 
			"ON et.return_Info_sub_seq = esi.return_info_sub_seq", nativeQuery = true)
	public List<ErpStoreReturnSubInfo> findByWorkBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM erp_store_return_sub_info WHERE another_yn = 'Y' AND execute_amount=0", nativeQuery = true)
	public void deleteAnotherYN();

}
