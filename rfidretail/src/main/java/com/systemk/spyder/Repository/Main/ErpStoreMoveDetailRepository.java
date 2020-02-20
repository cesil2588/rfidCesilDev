package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreMoveDetail;

public interface ErpStoreMoveDetailRepository extends JpaRepository<ErpStoreMoveDetail, Long>, JpaSpecificationExecutor<ErpStoreMoveDetail>{

	List<ErpStoreMoveDetail> findByMoveSeq(Long moveSeq);
	
	ErpStoreMoveDetail findByMoveDetailSeq(Long moveDetailSeq);
	
	List<ErpStoreMoveDetail> findByMoveStyleAndMoveColorAndMoveSize(String style, String color, String size);

	@Query(value="SELECT emd.move_detail_seq, move_seq, move_style, move_color, move_size, order_amount, et.execute_amount, confirm_amount, rfid_yn, another_yn FROM erp_store_move_detail emd " + 
			"INNER JOIN (SELECT COUNT(move_detail_seq) AS execute_amount, move_detail_seq FROM erp_store_move_tag WHERE work_box_num = :workBoxNum GROUP BY move_detail_seq) et " + 
			"ON et.move_detail_seq = emd.move_detail_seq", nativeQuery = true)
	public List<ErpStoreMoveDetail> findByWorkBoxNum(@Param("workBoxNum") Long workBoxNum);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_move_detail SET execute_amount = execute_amount + :addAmount WHERE move_detail_seq = :moveDetailSeq", nativeQuery = true)
	public void updateExecuteAmount(@Param("addAmount") Long addAmount, @Param("moveDetailSeq") Long moveDetailSeq);

	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_move_detail SET execute_amount = execute_amount - :delAmount WHERE move_detail_seq = :moveDetailSeq", nativeQuery = true)
	public void updateDelExecuteAmount(@Param("delAmount") int delAmount, @Param("moveDetailSeq") Long moveDetailSeq);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM erp_store_move_detail WHERE another_yn = 'Y' AND execute_amount=0", nativeQuery = true)
	public void deleteAnotherYN();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE erp_store_move_detail SET confirm_amount = confirm_amount + :addAmount WHERE move_detail_seq = :moveDetailSeq", nativeQuery = true)
	public void updateConfirmAmount(@Param("addAmount") int addAmount, @Param("moveDetailSeq") Long moveDetailSeq);
	
	@Query(value = "SELECT move_seq FROM erp_store_move_detail WHERE move_detail_seq = :moveDetailSeq", nativeQuery = true)
	public Long findMoveSeqByDetailSeq(@Param("moveDetailSeq") Long moveDetailSeq);

	
}
