package com.systemk.spyder.Repository.Main;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;

public interface TempDistributionReleaseBoxRepository extends JpaRepository<TempDistributionReleaseBox, String>, JpaSpecificationExecutor<TempDistributionReleaseBox>{

	public TempDistributionReleaseBox findByBarcode(String barcode);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE dsrt " +
		              "SET dsrt.stat = :stat, dsrt.upd_user_seq = :updUserSeq, dsrt.upd_date = getDate(), dsrt.box_seq = :boxSeq " +
			 		 "FROM distribution_storage_rfid_tag dsrt " + 
			 		"WHERE EXISTS ( " + 
			 	   "SELECT tdrt.rfid_tag " + 
			 	   	 "FROM temp_distribution_release_box tdrb " + 
			   "INNER JOIN temp_distribution_release_style tdrs " + 
			      "ON tdrb.temp_box_seq = tdrs.temp_box_seq " + 
			   "INNER JOIN temp_distribution_release_tag tdrt " + 
			   	  "ON tdrs.temp_style_seq = tdrt.temp_style_seq " + 
			   "WHERE dsrt.rfid_tag = tdrt.rfid_tag " + 
			     "AND tdrb.temp_box_seq = :tempBoxSeq)", nativeQuery = true)
	public void updateExistsReleaseTag(@Param("stat") String stat, @Param("updUserSeq") Long updUserSeq, @Param("boxSeq") Long boxSeq, @Param("tempBoxSeq") Long tempBoxSeq);
	
	
	@Query("SELECT tdrb FROM TempDistributionReleaseBox tdrb JOIN FETCH tdrb.styleList sl " +
		    "WHERE sl.referenceNo = :referenceNo " +
			  "AND sl.style = :style " +
			  "AND sl.color = :color " + 
			  "AND sl.size = :size ")
	public List<TempDistributionReleaseBox> findByCompleteBox(@Param("referenceNo") String referenceNo,
															  @Param("style") String style,
															  @Param("color") String color,
															  @Param("size") String size);
}
 