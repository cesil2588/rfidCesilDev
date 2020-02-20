package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.Specification.DistributionStorageSpecification;
import com.systemk.spyder.Service.DistributionStorageService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.DistributionCountRowMapper;
import com.systemk.spyder.Service.Mapper.FindStyleModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagSeasonModelMapper;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class DistributionStorageServiceImpl implements DistributionStorageService {

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Page<DistributionStorage> findAll(String startDate, String endDate, Long companySeq, String productSeason, String search, String option, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<DistributionStorage> page = null;

		Specifications<DistributionStorage> specifications = Specifications.where(DistributionStorageSpecification.regDateBetween(start, end));

		if(companySeq != 0){
			specifications = specifications.and(DistributionStorageSpecification.productionCompanySeqEqual(companySeq));
		}

		if(!productSeason.equals("all")){
			specifications = specifications.and(DistributionStorageSpecification.productSeasonEqual(productSeason));
		}

		if(!search.equals("") && option.equals("erpKey")){
			specifications = specifications.and(DistributionStorageSpecification.erpKeyContain(search));
		} else if(!search.equals("") && option.equals("style")){
			specifications = specifications.and(DistributionStorageSpecification.styleContain(search));
		} else if(!search.equals("") && option.equals("color")){
			specifications = specifications.and(DistributionStorageSpecification.colorContain(search));
		} else if(!search.equals("") && option.equals("size")){
			specifications = specifications.and(DistributionStorageSpecification.sizeContain(search));
		}

		page = distributionStorageRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<DistributionStorage> findAll(String startDate, String endDate, Long companySeq, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<DistributionStorage> page = null;

		Specifications<DistributionStorage> specifications = Specifications.where(DistributionStorageSpecification.regDateBetween(start, end))
																		   .and(DistributionStorageSpecification.statNotEqual("7"));

		if(companySeq != 0){
			specifications = specifications.and(DistributionStorageSpecification.productionCompanySeqEqual(companySeq));
		}

		if(!productYy.equals("")){
			specifications = specifications.and(DistributionStorageSpecification.productYyEqual(productYy));
		}

		if(!productSeason.equals("")){
			specifications = specifications.and(DistributionStorageSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("")){
			specifications = specifications.and(DistributionStorageSpecification.styleEqual(style));
		}

		if(!color.equals("")){
			specifications = specifications.and(DistributionStorageSpecification.colorEqual(color));

		}

		if(!size.equals("")){
			specifications = specifications.and(DistributionStorageSpecification.sizeEqual(size));
		}


		page = distributionStorageRepository.findAll(specifications, pageable);

		return page;
	}


	@Transactional(readOnly = true)
	@Override
	public JSONObject countAll(String startDate, String endDate, Long companySeq, String productSeason, String search, String option) throws Exception{

		JSONObject obj = new JSONObject();

		CountModel count = count(startDate, endDate, companySeq, productSeason, search, option);

		obj.put("allTotalAmount", count.getTotal_amount());
		obj.put("allStat1Amount", count.getStat1_amount());
		obj.put("allStat2Amount", count.getStat2_amount());
		obj.put("allStat3Amount", count.getStat3_amount());
		obj.put("allStat4Amount", count.getStat4_amount());
		obj.put("allStat5Amount", count.getStat5_amount());
		obj.put("allStat6Amount", count.getStat6_amount());
		obj.put("allStat7Amount", count.getStat7_amount());

		return obj;
	}


	@Transactional(readOnly = true)
	@Override
	public CountModel count(String startDate, String endDate, Long companySeq, String productSeason, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", start);
		params.put("endDate", end);

		StringBuffer query = new StringBuffer();
		query.append("SELECT SUM(distribution_storage.total_amount) total_amount, ");
		query.append("SUM(distribution_storage.stat1_amount) stat1_amount, ");
		query.append("SUM(distribution_storage.stat2_amount) stat2_amount, ");
		query.append("SUM(distribution_storage.stat3_amount) stat3_amount, ");
		query.append("SUM(distribution_storage.stat4_amount) stat4_amount, ");
		query.append("SUM(distribution_storage.stat5_amount) stat5_amount, ");
		query.append("SUM(distribution_storage.stat6_amount) stat6_amount, ");
		query.append("SUM(distribution_storage.stat7_amount) stat7_amount ");
		query.append("FROM distribution_storage ");
		query.append("INNER JOIN production_storage ");
		query.append("ON distribution_storage.production_storage_seq = production_storage.production_storage_seq ");
		query.append("INNER JOIN bartag_master ");
		query.append("ON production_storage.bartag_seq = bartag_master.bartag_seq ");
		query.append("WHERE distribution_storage.reg_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND bartag_master.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!productSeason.equals("all")){
			query.append("AND bartag_master.product_season = :productSeason");
			params.put("productSeason", productSeason);
		}

		if(!search.equals("") && option.equals("erpKey")){
			query.append("AND bartag_master.erpKey LIKE :erpKey ");
			params.put("erpKey", "%" + search + "%");
		} else if(!search.equals("") && option.equals("style")){
			query.append("AND bartag_master.style LIKE :style ");
			params.put("style", "%" + search + "%");
		} else if(!search.equals("") && option.equals("color")){
			query.append("AND bartag_master.color LIKE :color ");
			params.put("color", "%" + search + "%");
		} else if(!search.equals("") && option.equals("size")){
			query.append("AND bartag_master.size LIKE :size ");
			params.put("size", "%" + search + "%");
		}
		return nameTemplate.queryForObject(query.toString(), params, new DistributionCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagYy(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ds.product_yy AS data ");
		query.append("FROM distribution_storage ds ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ds.company_seq = ci.company_seq ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ds.product_yy ");
		query.append("ORDER BY ds.product_yy ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ds.product_season AS data, ");
		query.append("CASE ds.product_season ");
		query.append("WHEN 'A' THEN 'SEASONLESS' ");
		query.append("WHEN 'P' THEN 'SPRING' ");
		query.append("WHEN 'M' THEN 'SUMMER' ");
		query.append("WHEN 'F' THEN 'FALL' ");
		query.append("WHEN 'W' THEN 'WINTER' ");
		query.append("END AS explanatory ");
		query.append("FROM distribution_storage ds ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ds.company_seq = ci.company_seq ");
		query.append("WHERE ds.product_yy = :productYy ");
		params.put("productYy", productYy);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ds.product_season ");
		query.append("ORDER BY ds.product_season ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagSeasonModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ds.style AS data ");
		query.append("FROM distribution_storage ds ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ds.company_seq = ci.company_seq ");
		query.append("WHERE ds.product_yy = :productYy ");
		query.append("AND ds.product_season = :productSeason ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ds.style ");
		query.append("ORDER BY ds.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());

	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ds.color AS data ");
		query.append("FROM distribution_storage ds ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ds.company_seq = ci.company_seq ");
		query.append("WHERE ds.product_yy = :productYy ");
		query.append("AND ds.product_season = :productSeason ");
		query.append("AND ds.style = :style ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ds.color ");
		query.append("ORDER BY ds.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ds.size AS data ");
		query.append("FROM distribution_storage ds ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ds.company_seq = ci.company_seq ");
		query.append("WHERE ds.product_yy = :productYy ");
		query.append("AND ds.product_season = :productSeason ");
		query.append("AND ds.style = :style ");
		query.append("AND ds.color = :color ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ds.size ");
		query.append("ORDER BY ds.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> findStyleList() throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, bm.color, bm.size, bm.erp_key ");
		query.append("FROM bartag_master bm ");
		/*
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ps.production_storage_seq = bm.production_storage_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		*/
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.erp_key ");

		return nameTemplate.query(query.toString(), params, new FindStyleModelMapper());
	}

}
