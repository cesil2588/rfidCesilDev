package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreMoveTag;

public interface ErpStoreMoveTagRepository extends JpaRepository<ErpStoreMoveTag, Long>, JpaSpecificationExecutor<ErpStoreMoveTag> {

	public int countByWorkBoxNum(Long workBoxNum);
	
	public List<ErpStoreMoveTag> findByWorkBoxNum(Long workBoxNum);
	
	public ErpStoreMoveTag findByRfidTag(String rfidTag);
	
	@Query(value="SELECT count(*) FROM erp_store_move_tag WHERE rfid_tag = :rfidTag", nativeQuery = true)
	public int chkExistRfidTag(@Param("rfidTag") String rfidTag);
	
	@Query(value="SELECT COUNT(*) FROM erp_store_move_tag WHERE work_box_num  = :workBoxNum AND complete_yn = 'Y'", nativeQuery = true)
	public int chkConfirmYnByBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move_tag SET complete_yn = 'Y', complete_date = GETDATE() WHERE move_tag_seq = :moveTagSeq", nativeQuery = true)
	public void updateCompleteYn(@Param("moveTagSeq") Long moveTagSeq);
	
	@Query(value="SELECT COUNT(a.move_detail_seq) FROM erp_store_move_detail a "
				+ "LEFT OUTER JOIN (SELECT move_detail_seq FROM erp_store_move_tag WHERE complete_yn = 'Y') b "
				+ "ON a.move_detail_seq = b.move_detail_seq "
				+ "WHERE b.move_detail_seq IS NULL AND a.move_detail_seq IN "
				+ "(SELECT move_detail_seq FROM erp_store_move_detail WHERE move_seq = :moveSeq)", nativeQuery = true)
	public int chkCompleteYn(@Param("moveSeq") Long moveSeq);
	
}
