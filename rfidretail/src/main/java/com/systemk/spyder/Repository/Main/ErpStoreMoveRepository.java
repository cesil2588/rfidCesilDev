package com.systemk.spyder.Repository.Main;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ErpStoreMove;

public interface ErpStoreMoveRepository extends JpaRepository<ErpStoreMove, Long>, JpaSpecificationExecutor<ErpStoreMove> {

	ErpStoreMove findByMoveSeq(Long moveSeq);
	
	ErpStoreMove findByOrderSeq(Long orderSeq);
	
		
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move SET execute_amount = execute_amount + :addAmount WHERE move_seq = :moveSeq", nativeQuery = true)
	public void updateExecuteAmount(@Param("addAmount") Long addAmount, @Param("moveSeq") Long moveSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move SET execute_amount = execute_amount - :delAmount WHERE move_seq = :moveSeq", nativeQuery = true)
	public void updateDelExecuteAmount(@Param("delAmount") int delAmount, @Param("moveSeq") Long moveSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move SET from_complete_date = GETDATE(), from_complete_yn = 'Y' WHERE move_seq = :moveSeq", nativeQuery = true)
	public void updateConfirmYn(@Param("moveSeq") Long moveSeq);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move SET confirm_amount = confirm_amount + :addAmount WHERE move_seq = :moveSeq", nativeQuery = true)
	public void updateConfirmAmount(@Param("addAmount") int addAmount, @Param("moveSeq") Long moveSeq);

	@Modifying
	@Transactional
	@Query(value="UPDATE erp_store_move SET order_amount = :orderAmount WHERE move_seq = :moveSeq", nativeQuery = true)
	public void updateOrderAmount(@Param("moveSeq") Long moveSeq, @Param("orderAmount") Long orderAmount);
	

}
