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
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.Specification.ProductionStorageSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.ProductionStorageService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.ProductionCountRowMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagSeasonModelMapper;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class ProductionStorageServiceImpl implements ProductionStorageService{

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorage> findAll(LoginUser user, String search, String option, Pageable pageable) throws Exception {

		Page<ProductionStorage> page = null;

		if(user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && search.equals("")){
			page = productionStorageRepository.findAll(pageable);

		} else if(!user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && search.equals("")){
			page = productionStorageRepository.findByBartagMasterProductionCompanyInfoCustomerCode(user.getCompanyInfo().getCustomerCode(), pageable);

		} else if(user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("style")){
			page = productionStorageRepository.findByBartagMasterStyleContaining(search, pageable);

		} else if(!user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("style")){
			page = productionStorageRepository.findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterStyleContaining(user.getCompanyInfo().getCustomerCode(), search, pageable);

		} else if(user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("productYy")){
			page = productionStorageRepository.findByBartagMasterProductYyContaining(search, pageable);

		} else if(!user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("productYy")){
			page = productionStorageRepository.findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterProductYyContaining(user.getCompanyInfo().getCustomerCode(), search, pageable);

		} else if(user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("productSeason")){
			page = productionStorageRepository.findByBartagMasterProductSeasonContaining(search, pageable);

		}  else if(!user.getCompanyInfo().getRoleInfo().getRole().equals("admin") && !search.equals("") && option.equals("productSeason")){
			page = productionStorageRepository.findByBartagMasterProductionCompanyInfoCustomerCodeAndBartagMasterProductSeasonContaining(user.getCompanyInfo().getCustomerCode(), search, pageable);
		}

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorage> findAll(String startDate, String endDate, Long companySeq, String subCompanyName, String productSeason, String search, String option, String flag, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<ProductionStorage> page = null;

		Specifications<ProductionStorage> specifications = Specifications.where(ProductionStorageSpecification.regDateBetween(start, end))
																		 .and(ProductionStorageSpecification.statNotEqual("7"));

		if(companySeq != 0){
			specifications = specifications.and(ProductionStorageSpecification.companySeqEqual(companySeq));
		}

		if(!productSeason.equals("all")){
			specifications = specifications.and(ProductionStorageSpecification.productSeasonEqual(productSeason));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(!search.equals("") && option.equals("erpKey")){
			specifications = specifications.and(ProductionStorageSpecification.erpKeyContain(search));
		} else if(!search.equals("") && option.equals("style")){
			specifications = specifications.and(ProductionStorageSpecification.styleContain(search));
		} else if(!search.equals("") && option.equals("color")){
			specifications = specifications.and(ProductionStorageSpecification.colorContain(search));
		} else if(!search.equals("") && option.equals("size")){
			specifications = specifications.and(ProductionStorageSpecification.sizeContain(search));
		}

		if(flag.equals("schedule")){
			specifications = specifications.and(ProductionStorageSpecification.nonCheckAmountGreaterThan(Long.valueOf(0)));
		}

		page = productionStorageRepository.findAll(specifications, pageable);

		return page;
	}

	@Override
	public Page<ProductionStorage> findAll(String startDate, String endDate, Long companySeq, String subCompanyName, String productYy, String productSeason, String style, String color, String styleSize, String flag, String search, String option, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<ProductionStorage> page = null;

		Specifications<ProductionStorage> specifications = Specifications.where(ProductionStorageSpecification.regDateBetween(start, end))
																		 .and(ProductionStorageSpecification.statNotEqual("7"));

		if(companySeq != 0){
			specifications = specifications.and(ProductionStorageSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(!productYy.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.productYyEqual(productYy));
		}

		if(!productSeason.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.styleEqual(style));
		}

		if(!color.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.colorEqual(color));

		}

		if(!styleSize.equals("")){
			specifications = specifications.and(ProductionStorageSpecification.sizeEqual(styleSize));
		}

		if(flag.equals("schedule")){
			specifications = specifications.and(ProductionStorageSpecification.nonCheckAmountGreaterThan(Long.valueOf(0)));
		}

		page = productionStorageRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public JSONObject countAll(String startDate, String endDate, Long companySeq, String subCompanyName, String productSeason, String search, String option, String flag) throws Exception{

		JSONObject obj = new JSONObject();

		CountModel count = count(startDate, endDate, companySeq, subCompanyName, productSeason, search, option, flag);

		obj.put("allTotalAmount", count.getTotal_amount());
		obj.put("allNonCheckAmount", count.getStat1_amount());
		obj.put("allStockAmount", count.getStat2_amount());
		obj.put("allReleaseAmount", count.getStat3_amount());
		obj.put("allReissueAmount", count.getStat4_amount());
		obj.put("allDisuseAmount", count.getStat5_amount());
		obj.put("allReturnNonCheckAmount", count.getStat6_amount());
		obj.put("allReturnAmount", count.getStat7_amount());

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(String startDate, String endDate, Long companySeq, String subCompanyName, String productSeason, String search, String option, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", start);
		params.put("endDate", end);

		StringBuffer query = new StringBuffer();
		query.append("SELECT SUM(production_storage.total_amount) total_amount, ");
		query.append("SUM(production_storage.non_check_amount) stat1_amount, ");
		query.append("SUM(production_storage.stock_amount) stat2_amount, ");
		query.append("SUM(production_storage.release_amount) stat3_amount, ");
		query.append("SUM(production_storage.reissue_amount) stat4_amount, ");
		query.append("SUM(production_storage.disuse_amount) stat5_amount, ");
		query.append("SUM(production_storage.return_non_check_amount) stat6_amount, ");
		query.append("SUM(production_storage.return_amount) stat7_amount ");
		query.append("FROM production_storage ");
		query.append("INNER JOIN bartag_master ");
		query.append("ON production_storage.bartag_seq = bartag_master.bartag_seq ");
		query.append("WHERE production_storage.reg_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND bartag_master.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!subCompanyName.equals("")){
			query.append("AND bartag_master.detail_production_company_name LIKE :subCompanyName ");
			params.put("subCompanyName", "%" + subCompanyName + "%");
		}

		if(!productSeason.equals("all")){
			query.append("AND bartag_master.product_season = :productSeason ");
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

		if(flag.equals("schedule")){
			query.append("AND non_check_amount > :checkAmount ");
			params.put("checkAmount", 0);
		}

		return nameTemplate.queryForObject(query.toString(), params, new ProductionCountRowMapper());
	}

	@Transactional
	@Override
	public ProductionStorage init(BartagMaster bartag, Long userSeq) {
		ProductionStorage productionStorage = new ProductionStorage(bartag, userSeq);
		return productionStorageRepository.save(productionStorage);
	}

	@Transactional
	@Override
	public void updateAmount(ProductionStorage production, Long userSeq) throws Exception{

		CountModel count = productionStorageRfidTagService.count(production.getProductionStorageSeq());

		production.setNonCheckAmount(count.getStat1_amount());
		production.setStockAmount(count.getStat2_amount());
		production.setReleaseAmount(count.getStat3_amount());
		production.setReissueAmount(count.getStat4_amount());
		production.setDisuseAmount(count.getStat5_amount());
		production.setReturnNonCheckAmount(count.getStat6_amount());
		production.setReturnAmount(count.getStat7_amount());

		productionStorageRepository.save(production);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagYy(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.product_yy AS data ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ps.product_yy ");
		query.append("ORDER BY ps.product_yy ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.product_season AS data, ");
		query.append("CASE ps.product_season ");
		query.append("WHEN 'A' THEN 'SEASONLESS' ");
		query.append("WHEN 'P' THEN 'SPRING' ");
		query.append("WHEN 'M' THEN 'SUMMER' ");
		query.append("WHEN 'F' THEN 'FALL' ");
		query.append("WHEN 'W' THEN 'WINTER' ");
		query.append("END AS explanatory ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		query.append("WHERE ps.product_yy = :productYy ");
		params.put("productYy", productYy);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ps.product_season ");
		query.append("ORDER BY ps.product_season ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagSeasonModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.style AS data ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		query.append("WHERE ps.product_yy = :productYy ");
		query.append("AND ps.product_season = :productSeason ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ps.style ");
		query.append("ORDER BY ps.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());

	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.color AS data ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		query.append("WHERE ps.product_yy = :productYy ");
		query.append("AND ps.product_season = :productSeason ");
		query.append("AND ps.style = :style ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ps.color ");
		query.append("ORDER BY ps.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ps.size AS data ");
		query.append("FROM production_storage ps ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ps.company_seq = ci.company_seq ");
		query.append("WHERE ps.product_yy = :productYy ");
		query.append("AND ps.product_season = :productSeason ");
		query.append("AND ps.style = :style ");
		query.append("AND ps.color = :color ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ps.size ");
		query.append("ORDER BY ps.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

}
