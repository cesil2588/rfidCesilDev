package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

public interface ReleaseScheduleLogRepository extends JpaRepository<ReleaseScheduleLog, Long>, JpaSpecificationExecutor<ReleaseScheduleLog>{

	public ReleaseScheduleLog findTop1ByCreateDateAndOrderTypeOrderByWorkLineDesc(String createDate, String orderType);

	public ReleaseScheduleLog findByBoxInfoBarcode(String barcode);

	public List<ReleaseScheduleLog> findByBoxInfoBarcodeLike(String barcode);

	public ReleaseScheduleLog findByBoxInfoBoxSeq(Long boxSeq);

	public ReleaseScheduleLog findByBoxInfoBarcodeAndReleaseYn(String barcode, String releaseYn);

	public ReleaseScheduleLog findByBoxInfoBarcodeAndCompleteYn(String barcode, String completeYn);

	public List<ReleaseScheduleLog> findByCreateDate(String createDate);

	@Modifying
	@Transactional
	@Query(value = "UPDATE dsrt " +
		              "SET dsrt.stat = :stat, dsrt.upd_user_seq = :updUserSeq, dsrt.upd_date = getDate(), dsrt.box_seq = :boxSeq " +
			 		 "FROM distribution_storage_rfid_tag dsrt " +
			 		"WHERE EXISTS ( " +
			 	   "SELECT rssdl.rfid_tag " +
			 	   	 "FROM release_schedule_log rsl " +
			   "INNER JOIN release_schedule_detail_log rsdl " +
			           "ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq " +
			   "INNER JOIN release_schedule_sub_detail_log rssdl " +
			   		   "ON rsdl.release_schedule_detail_log_seq = rssdl.release_schedule_detail_log_seq " +
			   "WHERE dsrt.rfid_tag = rssdl.rfid_tag " +
			     "AND rsl.release_schedule_log_seq = :releaseScheduleLogSeq)", nativeQuery = true)
	public void updateExistsReleaseTag(@Param("stat") String stat, @Param("updUserSeq") Long updUserSeq, @Param("boxSeq") Long boxSeq, @Param("releaseScheduleLogSeq") Long releaseScheduleLogSeq);

}
