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
import com.systemk.spyder.Service.TempProductionService;
import com.systemk.spyder.Service.CustomBean.ValidBarcode;
import com.systemk.spyder.Service.CustomBean.ValidTag;
import com.systemk.spyder.Service.Mapper.ValidTagRowMapper;
import com.systemk.spyder.Service.Mapper.ValidTempBarcodeRowMapper;
import com.systemk.spyder.Service.Mapper.ValidTempReleaseTagRowMapper;
import com.systemk.spyder.Service.Mapper.ValidTempStorageTagRowMapper;

@Service
public class TempProductionServiceImpl implements TempProductionService{
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional
	@Override
	public void deleteStorageHeader(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_storage_header WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteStorageAllStyle(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_storage_style WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteStorageAllTag(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_storage_tag WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseHeader(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_release_header WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseAllBox(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_release_box WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}
	
	@Transactional
	@Override
	public void deleteReleaseAllStyle(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_release_style WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
	}

	@Transactional
	@Override
	public void deleteReleaseAllTag(Long seq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "DELETE FROM temp_production_release_tag WHERE temp_header_seq = ?";
		
    	template.update(query, seq);
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> exitsStorageAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT psrt.rfid_tag " +
				   		 "FROM production_storage_rfid_tag psrt " +
				   		"WHERE EXISTS (SELECT tpst.rfid_tag " + 
				                   		"FROM temp_production_storage_tag tpst " +
				                   	   "WHERE tpst.rfid_tag  = psrt.rfid_tag " +
				                   		 "AND tpst.temp_header_seq = :seq ) " +
				          "AND psrt.stat = '1' ";
		
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

	@Transactional(readOnly = true)
	@Override
	public List<String> exitsReleaseAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT psrt.rfid_tag " +
				   		 "FROM production_storage_rfid_tag psrt " +
				   		"WHERE EXISTS (SELECT tpst.rfid_tag " + 
				                   		"FROM temp_production_release_tag tpst " +
				                   	   "WHERE tpst.rfid_tag  = psrt.rfid_tag " +
				                   		 "AND tpst.temp_header_seq = :seq ) " +
				          "AND psrt.stat = '2' ";
		
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
	
	@Transactional(readOnly = true)
	@Override
	public List<ValidTag> validStorageAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT tpst.rfid_tag AS rfid_tag, psrt.rfid_tag AS target_rfid_tag, psrt.stat AS target_stat, psrt.box_barcode AS target_box_barcode " + 
					     "FROM temp_production_storage_tag tpst " + 
			  "LEFT OUTER JOIN production_storage_rfid_tag psrt " + 
						   "ON tpst.rfid_tag = psrt.rfid_tag " + 
						"WHERE tpst.temp_header_seq = :seq ";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params, new ValidTagRowMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Long> validStorageAllSeq(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT psrt.production_storage_seq " + 
						 "FROM temp_production_storage_tag tpst " + 
			  "LEFT OUTER JOIN production_storage_rfid_tag psrt " + 
						  "ON tpst.rfid_tag = psrt.rfid_tag " + 
					   "WHERE temp_header_seq = :seq " + 
					"GROUP BY psrt.production_storage_seq";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params,
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int row) throws SQLException {
				return new Long(rs.getLong("production_storage_seq"));
			}
		});
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Long> validReleaseAllSeq(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT psrt.production_storage_seq " + 
						 "FROM temp_production_release_tag tprt " + 
			  "LEFT OUTER JOIN production_storage_rfid_tag psrt " + 
						  "ON tprt.rfid_tag = psrt.rfid_tag " + 
					   "WHERE temp_header_seq = :seq " + 
					"GROUP BY psrt.production_storage_seq";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params,
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int row) throws SQLException {
				return new Long(rs.getLong("production_storage_seq"));
			}
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<ValidTag> validReleaseAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT tprt.rfid_tag AS rfid_tag, psrt.rfid_tag AS target_rfid_tag, psrt.stat AS target_stat, psrt.box_barcode AS target_box_barcode " + 
					     "FROM temp_production_release_tag tprt " + 
			  "LEFT OUTER JOIN production_storage_rfid_tag psrt " + 
						   "ON tprt.rfid_tag = psrt.rfid_tag " + 
						"WHERE tprt.temp_header_seq = :seq ";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params, new ValidTagRowMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ValidBarcode> validTempReleaseAllBox(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT tprb.barcode AS barcode, temp.barcode as target_barcode " +
					     "FROM temp_production_release_box tprb " +
			  "LEFT OUTER JOIN (SELECT ttprb.barcode " +
			                   	 "FROM temp_production_release_header ttprh " +
			               "INNER JOIN temp_production_release_box ttprb " + 
			                       "ON ttprh.temp_header_seq = ttprb.temp_header_seq " +
			                    "WHERE ttprh.request_yn = 'Y' " +
			                      "AND ttprh.complete_yn = 'Y' " +
			                      "AND ttprh.batch_yn = 'N' " +
			                      "AND ttprb.temp_header_seq != :seq) temp ON tprb.barcode = temp.barcode " +
			            "WHERE tprb.temp_header_seq = :seq ";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params, new ValidTempBarcodeRowMapper());
	}
	
	@Transactional
	@Override
	public List<ValidTag> validTempStorageAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT tpst.rfid_tag AS rfid_tag, temp.rfid_tag AS target_rfid_tag " +
					     "FROM temp_production_storage_tag tpst " +
			  "LEFT OUTER JOIN (SELECT ttpst.rfid_tag " +
			                   	 "FROM temp_production_storage_header ttpsh " +
			               "INNER JOIN temp_production_storage_tag ttpst " + 
			                       "ON ttpsh.temp_header_seq = ttpst.temp_header_seq " +
			                    "WHERE ttpsh.request_yn = 'Y' " +
			                      "AND ttpsh.complete_yn = 'Y' " +
			                      "AND ttpsh.batch_yn = 'N' " +
			                      "AND ttpsh.temp_header_seq != :seq) temp ON tpst.rfid_tag = temp.rfid_tag " +
			            "WHERE tpst.temp_header_seq = :seq ";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params, new ValidTempStorageTagRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<ValidTag> validTempReleaseAllTag(Long seq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String query = "SELECT tprt.rfid_tag AS rfid_tag, temp.rfid_tag AS target_rfid_tag, temp.barcode AS target_box_barcode " +
					     "FROM temp_production_release_tag tprt " +
			  "LEFT OUTER JOIN (SELECT ttprt.rfid_tag, ttprb.barcode " +
			                   	 "FROM temp_production_release_header ttprh " +
			               "INNER JOIN temp_production_release_box ttprb " +
			                   	   "ON ttprh.temp_header_seq = ttprb.temp_header_seq " +
			               "INNER JOIN temp_production_release_style ttprs " + 
			                   	   "ON ttprb.temp_box_seq = ttprs.temp_box_seq " +
			               "INNER JOIN temp_production_release_tag ttprt " +
			                   	   "ON ttprs.temp_style_seq = ttprt.temp_style_seq " +
			                    "WHERE ttprh.request_yn = 'Y' " +
			                      "AND ttprh.complete_yn = 'Y' " +
			                      "AND ttprh.batch_yn = 'N' " +
			                      "AND ttprh.temp_header_seq != :seq) temp ON tprt.rfid_tag = temp.rfid_tag " +
			            "WHERE tprt.temp_header_seq = :seq ";
		
		params.put("seq", seq);
		
		return nameTemplate.query(query, params, new ValidTempReleaseTagRowMapper());
	}
	
	@Transactional
	@Override
	public void updateStorageHeaderSeq(Long headerSeq, Long styleSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE temp_production_storage_tag " + 
						  "SET temp_header_seq = ? " +
				   		"WHERE temp_style_seq = ? "; 
		
    	template.update(query, headerSeq, styleSeq);
	}

	@Transactional
	@Override
	public void updateStorageAllTag(Long seq, Long userSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE psrt " + 
						  "SET psrt.stat = '2', " +
						  	  "psrt.upd_date = getdate(), " +
						  	  "psrt.upd_user_seq = ? " +
						 "FROM production_storage_rfid_tag psrt " +
				   "INNER JOIN temp_production_storage_tag tpst " +
				   		   "ON (psrt.rfid_tag = tpst.rfid_tag " +
				   		   		"AND tpst.temp_header_seq = ?) " +
				   		"WHERE psrt.stat = '1'"; 
		
    	template.update(query, userSeq, seq);
	}
	
	@Transactional
	@Override
	public void updateReleaseStyleHeaderSeq(Long headerSeq, Long boxSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE temp_production_release_style " + 
						  "SET temp_header_seq = ? " +
				   		"WHERE temp_box_seq = ? "; 
		
    	template.update(query, headerSeq, boxSeq);
	}

	@Transactional
	@Override
	public void updateReleaseTagHeaderSeq(Long headerSeq, Long styleSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE temp_production_release_tag " + 
						  "SET temp_header_seq = ? " +
				   		"WHERE temp_style_seq = ? "; 
		
    	template.update(query, headerSeq, styleSeq);
	}

	@Transactional
	@Override
	public void updateReleaseBoxTag(Long seq, Long userSeq, BoxInfo boxInfo) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE psrt " + 
						  "SET psrt.stat = '3', " +
						  	  "psrt.upd_date = getdate(), " +
						  	  "psrt.upd_user_seq = ?, " +
						  	  "psrt.box_barcode = ?, " +
						  	  "psrt.box_seq = ? " +
						 "FROM production_storage_rfid_tag psrt " +
				   "INNER JOIN temp_production_release_tag tpst " +
				   		   "ON (psrt.rfid_tag = tpst.rfid_tag " +
				   		  "AND tpst.temp_style_seq = ?) " +
				   		"WHERE psrt.stat = '2'"; 
		
    	template.update(query, userSeq, boxInfo.getBarcode(), boxInfo.getBoxSeq(), seq);
	}
}
