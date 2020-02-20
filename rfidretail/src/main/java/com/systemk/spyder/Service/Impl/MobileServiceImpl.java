package com.systemk.spyder.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Service.MobileService;
import com.systemk.spyder.Service.CustomBean.MobileInspectionRfidTag;
import com.systemk.spyder.Service.CustomBean.MobileInspectionStyle;
import com.systemk.spyder.Service.CustomBean.MobileReleaseBox;
import com.systemk.spyder.Service.CustomBean.MobileReleaseRfidTag;
import com.systemk.spyder.Service.CustomBean.MobileReleaseStyle;
import com.systemk.spyder.Service.Mapper.MobileInspectionRfidTagMapper;
import com.systemk.spyder.Service.Mapper.MobileInspectionStyleMapper;
import com.systemk.spyder.Service.Mapper.MobileReleaseBoxMapper;
import com.systemk.spyder.Service.Mapper.MobileReleaseRfidTagMapper;
import com.systemk.spyder.Service.Mapper.MobileReleaseStyleMapper;

@Service
public class MobileServiceImpl implements MobileService {
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Override
	public List<MobileInspectionStyle> inspectionStyleList(Long companySeq) throws Exception {
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT a.style, "); 
		query.append("a.color, ");
		query.append("a.size,  ");
		query.append("a.order_degree, ");
		query.append("a.addition_order_degree, ");
		query.append("b.non_check_amount, ");
		query.append("b.stock_amount, ");
		query.append("b.bartag_seq, ");
		query.append("a.production_storage_seq ");
		query.append("FROM bartag_master a ");
		query.append("INNER JOIN production_storage b ");
		query.append("ON a.bartag_seq = b.bartag_seq ");
		query.append("WHERE non_check_amount > 0  ");
		query.append("AND production_company_seq = :companySeq ");
		
		params.put("companySeq", companySeq);
		
		return nameTemplate.query(query.toString(), params, new MobileInspectionStyleMapper());
	}
	
	@Override
	public List<MobileInspectionRfidTag> inspectionRfidTagList(String stat, Long companySeq) throws Exception{
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT psrt.rfid_tag, ");
		query.append("psrt.production_storage_seq, ");
		query.append("ps.bartag_seq ");
		query.append("FROM production_storage_rfid_tag psrt ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON psrt.production_storage_seq = ps.production_storage_seq ");
		query.append("WHERE psrt.stat = :stat ");
		query.append("AND ps.company_seq = :companySeq ");
		query.append("ORDER BY ps.bartag_seq, psrt.rfid_tag; ");
		
		params.put("stat", stat);
		params.put("companySeq", companySeq);
		
		return nameTemplate.query(query.toString(), params, new MobileInspectionRfidTagMapper());
	}

	@Override
	public List<MobileReleaseStyle> releaseStyleList(Long companySeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.stock_amount, ");
		query.append("ps.release_amount, ");
		query.append("ps.production_storage_seq, ");
		query.append("bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("bm.addition_order_degree ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq= ci.company_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq= bm.bartag_seq ");
		query.append("WHERE ci.company_seq = :companySeq ");
		query.append("AND ps.stock_amount > 0 ");
		
		params.put("companySeq", companySeq);
		
		return nameTemplate.query(query.toString(), params, new MobileReleaseStyleMapper());
	}

	@Override
	public List<MobileReleaseRfidTag> releaseRfidTagList(String stat, Long companySeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT p.rfid_tag, ");
		query.append("p.production_storage_seq, ");
		query.append("bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("bm.addition_order_degree ");
		query.append("FROM production_storage_rfid_tag p ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON p.production_storage_seq=ps.production_storage_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq= bm.bartag_seq ");
		query.append("WHERE p.stat = :stat ");
		query.append("AND ci.company_seq = :companySeq ");
		
		params.put("stat", stat);
		params.put("companySeq", companySeq);
		
		return nameTemplate.query(query.toString(), params, new MobileReleaseRfidTagMapper());
	}

	@Override
	public List<MobileReleaseBox> releaseBoxList(Long companySeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT b.box_seq, ");
		query.append("b.barcode, ");
		query.append("b.box_num, ");
		query.append("b.type, ");
		query.append("b.return_yn, ");
		query.append("b.start_company_seq, ");
		query.append("b.end_company_seq, ");
		query.append("b.stat ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN company_info c ");
		query.append("ON b.start_company_seq = c.company_seq ");
		query.append("WHERE c.company_seq = :companySeq ");
		
		params.put("companySeq", companySeq);
		
		return nameTemplate.query(query.toString(), params, new MobileReleaseBoxMapper());
	}

}
