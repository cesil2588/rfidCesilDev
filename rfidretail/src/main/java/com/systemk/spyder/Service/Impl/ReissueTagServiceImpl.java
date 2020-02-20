package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Repository.Main.RfidTagReissueRequestRepository;
import com.systemk.spyder.Repository.Main.Specification.RfidTagReissueRequestSpecification;
import com.systemk.spyder.Service.ReissueTagService;
import com.systemk.spyder.Service.CustomBean.Select.SelectApiBartagModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.SelectApiBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagSeqModelMapper;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class ReissueTagServiceImpl implements ReissueTagService {
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private RfidTagReissueRequestRepository rfidTagReissueRequestRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<RfidTagReissueRequest> findAll(String startDate, String endDate, Long companySeq, String reissueYn, String search, String option, Pageable pageable) throws Exception {
		
		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);
		
		Page<RfidTagReissueRequest> page = null;
		
		Specifications<RfidTagReissueRequest> specifications = Specifications.where(RfidTagReissueRequestSpecification.regDateBetween(start, end));
		
		if(companySeq != 0) {
			specifications = specifications.and(RfidTagReissueRequestSpecification.companySeqEqual(companySeq));
		}
		
		if(!search.equals("") && option.equals("explanatory")){
			specifications = specifications.and(RfidTagReissueRequestSpecification.explanatoryContain(search));
		}
		
		page = rfidTagReissueRequestRepository.findAll(specifications, pageable);
		
		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagYy(Long companySeq, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_yy AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.product_yy ");
		query.append("ORDER BY bm.product_yy ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_season AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		params.put("productYy", productYy);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.product_season ");
		query.append("ORDER BY bm.product_season ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.style ");
		query.append("ORDER BY bm.style ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.color AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style = :style ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.color ");
		query.append("ORDER BY bm.color ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColorContain(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.color AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.color ");
		query.append("ORDER BY bm.color ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.size AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.size ");
		query.append("ORDER BY bm.size ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSizeContain(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.size AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		query.append("AND bm.color = :color ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		params.put("color", color);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.size ");
		query.append("ORDER BY bm.size ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderDegree(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT bm.order_degree AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);
		params.put("size", size);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.order_degree ");
		query.append("ORDER BY bm.order_degree ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagAdditionOrderDegree(Long companySeq, String productYy, String productSeason, String style, String color, String size, String orderDegree, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		
		if(type.equals("01")){
			
			query.append("SELECT bm.addition_order_degree AS data, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ps.production_storage_seq AS seq ");
			query.append("FROM bartag_master bm ");
			query.append("INNER JOIN production_storage ps ");
			query.append("ON bm.bartag_seq = ps.bartag_seq ");
			query.append("INNER JOIN company_info ci ");
			query.append("ON ps.company_seq = ci.company_seq ");
			
			query.append("WHERE bm.product_yy = :productYy ");
			query.append("AND bm.product_season = :productSeason ");
			query.append("AND bm.style = :style ");
			query.append("AND bm.color = :color ");
			query.append("AND bm.size = :size ");
			query.append("AND bm.order_degree = :orderDegree ");
			
			params.put("productYy", productYy);
			params.put("productSeason", productSeason);
			params.put("style", style);
			params.put("color", color);
			params.put("size", size);
			params.put("orderDegree", orderDegree);
			
			if(companySeq != 0){
				query.append("AND " + prefix + ".company_seq = :companySeq ");
				params.put("companySeq", companySeq);
			}
			
			query.append("GROUP BY bm.addition_order_degree, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ps.production_storage_seq ");
			query.append("ORDER BY bm.addition_order_degree ASC ");
		
		} else if(type.equals("02")){
			
			query.append("SELECT bm.addition_order_degree AS data, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ds.distribution_storage_seq AS seq ");
			query.append("FROM bartag_master bm ");
			query.append("INNER JOIN production_storage ps ");
			query.append("ON bm.bartag_seq = ps.bartag_seq ");
			query.append("INNER JOIN company_info ci ");
			query.append("ON ps.company_seq = ci.company_seq ");
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			
			query.append("WHERE bm.product_yy = :productYy ");
			query.append("AND bm.product_season = :productSeason ");
			query.append("AND bm.style = :style ");
			query.append("AND bm.color = :color ");
			query.append("AND bm.size = :size ");
			query.append("AND bm.order_degree = :orderDegree ");
			
			params.put("productYy", productYy);
			params.put("productSeason", productSeason);
			params.put("style", style);
			params.put("color", color);
			params.put("size", size);
			params.put("orderDegree", orderDegree);
			
			if(companySeq != 0){
				query.append("AND " + prefix + ".company_seq = :companySeq ");
				params.put("companySeq", companySeq);
			}
			
			query.append("GROUP BY bm.addition_order_degree, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ds.distribution_storage_seq ");
			query.append("ORDER BY bm.addition_order_degree ASC ");
			
		} else if(type.equals("03")){
			
			query.append("SELECT bm.addition_order_degree AS data, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ss.store_storage_seq AS seq ");
			query.append("FROM bartag_master bm ");
			query.append("INNER JOIN production_storage ps ");
			query.append("ON bm.bartag_seq = ps.bartag_seq ");
			query.append("INNER JOIN company_info ci ");
			query.append("ON ps.company_seq = ci.company_seq ");
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
			
			query.append("WHERE bm.product_yy = :productYy ");
			query.append("AND bm.product_season = :productSeason ");
			query.append("AND bm.style = :style ");
			query.append("AND bm.color = :color ");
			query.append("AND bm.size = :size ");
			query.append("AND bm.order_degree = :orderDegree ");
			
			params.put("productYy", productYy);
			params.put("productSeason", productSeason);
			params.put("style", style);
			params.put("color", color);
			params.put("size", size);
			params.put("orderDegree", orderDegree);
			
			if(companySeq != 0){
				query.append("AND " + prefix + ".company_seq = :companySeq ");
				params.put("companySeq", companySeq);
			}
			
			query.append("GROUP BY bm.addition_order_degree, bm.bartag_seq, bm.start_rfid_seq, bm.end_rfid_seq, ss.store_storage_seq ");
			query.append("ORDER BY bm.addition_order_degree ASC ");
		}
		
		return nameTemplate.query(query.toString(), params, new SelectBartagSeqModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectBartagRfidTagContain(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.erp_key AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		params.put("color", color);
		params.put("size", size);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.erp_key ");
		query.append("ORDER BY bm.erp_key ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagYy(Long companySeq, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_yy AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.product_yy, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.product_yy ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagSeason(Long companySeq, String productYy, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_season AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		params.put("productYy", productYy);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.product_season, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.product_season ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagStyle(Long companySeq, String productYy, String productSeason, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.style ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagColorContain(Long companySeq, String productYy, String productSeason, String style, String type) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.color AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.color, bm.style, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.color ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagSizeContain(Long companySeq, String productYy, String productSeason, String style, String color, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.size AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		query.append("AND bm.color = :color ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		params.put("color", color);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.size, bm.style, bm.color, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.size ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectBartagModel> selectApiBartagRfidTagContain(Long companySeq, String productYy, String productSeason, String style, String color, String size, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		String prefix = type.equals("01") ? "ps" : type.equals("02") ? "ds" : "ss"; 
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.erp_key AS data, bm.style, bm.color, bm.size, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		
		if(type.equals("02")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		} else if(type.equals("03")){
			query.append("INNER JOIN distribution_storage ds ");
			query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
			query.append("INNER JOIN store_storage ss ");
			query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		}
		
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style LIKE :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		
		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", "%" + style + "%");
		params.put("color", color);
		params.put("size", size);
		
		if(companySeq != 0){
			query.append("AND " + prefix + ".company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bm.erp_key, bm.style, bm.color, bm.size, bm.start_rfid_seq, bm.end_rfid_seq ");
		query.append("ORDER BY bm.erp_key ASC ");
		
		return nameTemplate.query(query.toString(), params, new SelectApiBartagModelMapper());
	}

	@Transactional(readOnly = true)	
	@Override
	public List<SelectApiBartagModel> generateApiBartag(List<SelectBartagModel> selectBartagList) throws Exception {
		
		List<SelectApiBartagModel> headerList = new ArrayList<SelectApiBartagModel>();
		
		for(SelectBartagModel model : selectBartagList){
			
			boolean headerPushFlag = true;
			
			for(SelectApiBartagModel header : headerList){
				
				if(header.getData().equals(model.getData())){

					boolean detailPushFlag = true;
					
					for(SelectBartagModel detail : header.getDetailList()){
						
						if(detail.getErpKey().equals(model.getErpKey()) &&
						   detail.getStartRfidSeq().equals(model.getStartRfidSeq()) &&
						   detail.getEndRfidSeq().equals(model.getEndRfidSeq())){
							
							detailPushFlag = false;
						}
					}
					
					if(detailPushFlag){
						List<SelectBartagModel> detailList = header.getDetailList();
						model.setData(null);
						detailList.add(model);
						
						header.setDetailList(detailList);
					}
					
					headerPushFlag = false;
				}
			}
			
			if(headerPushFlag){
				SelectApiBartagModel tempHeader = new SelectApiBartagModel();
				tempHeader.setData(model.getData());
				
				List<SelectBartagModel> tempDetailList = new ArrayList<SelectBartagModel>();
				model.setData(null);
				tempDetailList.add(model);
				
				tempHeader.setDetailList(tempDetailList);
				
				headerList.add(tempHeader);
			}
		}
		
		return headerList;
		
	}
}
