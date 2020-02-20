package com.systemk.spyder.Service.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Service.TempDistributionService;

@Service
public class TempDistributionServiceImpl implements TempDistributionService{

	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional
	@Override
	public void deleteReleaseHeader(Long seq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_distribution_release_header WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseAllBox(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_distribution_release_box WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseAllStyle(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_distribution_release_style WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseAllTag(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_distribution_release_tag WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> exitsReleaseAllTag(Long seq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT dsrt.rfid_tag " +
				   		 "FROM distribution_storage_rfid_tag dsrt " +
				   		"WHERE EXISTS (SELECT tdst.rfid_tag " + 
				                   		"FROM temp_distribution_release_tag tdst " +
				                   	   "WHERE tdst.rfid_tag  = dsrt.rfid_tag " +
				                   		 "AND tdst.temp_header_seq = :seq ) " +
				          "AND dsrt.stat = '2' ";
		
		params.put("seq", seq);
		
		List<String> list = nameTemplate.query(query, params,
			new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int row) throws SQLException {
					return new String(rs.getString("rfid_tag"));
				}
			});
		
		return list;
	}
	
	@Override
	public List<Long> existsReleaseStorageSeq(Long tempBoxSeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT dsrt.distribution_storage_seq " + 
				 		 "FROM distribution_storage_rfid_tag dsrt " + 
				 		"WHERE EXISTS ( " + 
				 	   "SELECT tdrt.rfid_tag" + 
				 	   	 "FROM temp_distribution_release_box tdrb " + 
				   "INNER JOIN temp_distribution_release_style tdrs " + 
				      "ON tdrb.temp_box_seq = tdrs.temp_box_seq " + 
				   "INNER JOIN temp_distribution_release_tag tdrt " + 
				   	  "ON tdrs.temp_style_seq = tdrt.temp_style_seq " + 
				   "WHERE dsrt.rfid_tag = tdrt.rfid_tag " + 
				     "AND tdrb.temp_box_seq = :tempBoxSeq) " + 
				"GROUP BY dsrt.distribution_storage_seq";
		
		params.put("tempBoxSeq", tempBoxSeq);
		
		return nameTemplate.query(query, params,
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int row) throws SQLException {
				return new Long(rs.getLong("distribution_storage_seq"));
			}
		});
	}

	@Transactional
	@Override
	public void updateReleaseBoxTag(Long seq, Long userSeq, BoxInfo boxInfo) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE dsrt " + 
						  "SET dsrt.stat = '3', " +
						  	  "dsrt.upd_date = getdate(), " +
						  	  "dsrt.upd_user_seq = ?, " +
						  	  "dsrt.box_seq = ? " +
						 "FROM distribution_storage_rfid_tag dsrt " +
				   "INNER JOIN temp_distribution_release_tag tdst " +
				   		   "ON (dsrt.rfid_tag = tdst.rfid_tag " +
				   		   		"AND tdst.temp_style_seq = ?) " +
				   		"WHERE dsrt.stat = '2'"; 
		
    	template.update(query, userSeq, boxInfo.getBoxSeq(), seq);
	}

	@Transactional
	@Override
	public void updateReleaseStyleHeaderSeq(Long headerSeq, String barcode) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE temp_distribution_release_style " + 
						  "SET temp_header_seq = ? " +
				   		"WHERE temp_box_seq = ? "; 
		
    	template.update(query, headerSeq, barcode);
	}

	@Transactional
	@Override
	public void updateReleaseTagHeaderSeq(Long headerSeq, Long styleSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE temp_distribution_release_tag " + 
						  "SET temp_header_seq = ? " +
				   		"WHERE temp_style_seq = ? "; 
		
    	template.update(query, headerSeq, styleSeq);
	}
}
