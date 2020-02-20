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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.BartagOrderRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.BartagOrderSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BartagOrderService;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.CustomBean.BartagSubCompany;
import com.systemk.spyder.Service.CustomBean.Group.BartagOrderGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.SubCompanyMapper;
import com.systemk.spyder.Service.Mapper.Group.BartagOrderGroupModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagSeasonModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class BartagOrderServiceImpl implements BartagOrderService{

	@Autowired
	private BartagOrderRepository bartagOrderRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Autowired
	private ErpService erpService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private JdbcTemplate template;

	@Transactional(readOnly = true)
	@Override
	public List<BartagOrderGroupModel> findBartagOrderGroupList(String startDate, String endDate, Long companySeq, String searchDate, String search, String option, Pageable pageable, String style) throws Exception{

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortStandard = searchDate.equals("regDate") ? "bo.reg_date" : "bo.upd_date";
		String sortQuery = PagingUtil.sortSetting(pageable, "date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER ( ");
		query.append("ORDER BY CURRENT_TIMESTAMP) AS ROW ");
		query.append("FROM ( ");
		query.append("SELECT TOP(:groupCount) CONVERT(VARCHAR(10), " + sortStandard + ", 112) AS date, ");
		query.append("bo.product_yy, ");
		query.append("bo.product_season, ");
		query.append("ci.name, ");
		query.append("ci.company_seq, ");
		query.append("SUM(bo.order_amount) AS order_amount, ");
		query.append("SUM(bo.complete_amount) AS complete_amount, ");
		query.append("SUM(bo.non_check_complete_amount) AS non_check_complete_amount, ");
		query.append("SUM(bo.addition_amount) AS addition_amount, ");
		query.append("SUM(bo.non_check_addition_amount) AS non_check_addition_amount ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE CONVERT(VARCHAR(10), " + sortStandard + ", 112) BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!style.equals("")){
			query.append("AND bo.style = :style ");
			params.put("style", style);
		}

		query.append("GROUP BY CONVERT(VARCHAR(10), " + sortStandard + ", 112), bo.product_yy, bo.product_season, bo.production_company_seq, ci.company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + ") inner_query) ");
		query.append("SELECT date, product_yy, product_season, name, company_seq, order_amount, complete_amount, non_check_complete_amount, addition_amount, non_check_addition_amount ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new BartagOrderGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountBartagOrderGroupList(String startDate, String endDate, Long companySeq, String searchDate, String search, String option, String style) throws Exception{

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		String sortStandard = searchDate.equals("regDate") ? "bo.reg_date" : "bo.upd_date";

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT CONVERT(VARCHAR(10), " + sortStandard + ", 112) AS reg_date, bo.product_yy, bo.product_season, bo.production_company_seq ");
		query.append("FROM bartag_order bo ");
		query.append("WHERE CONVERT(VARCHAR(10), " + sortStandard + ", 112) BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!style.equals("")){
			query.append("AND bo.style = :style ");
			params.put("style", style);
		}

		query.append("GROUP BY CONVERT(VARCHAR(10), " + sortStandard + ", 112), bo.product_yy, bo.product_season, bo.production_company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BartagOrder> findAll(String date, Long companySeq, String subCompanyName, String stat, String productYy, String productSeason, String style, String color, String size, String searchDate, String search, String option, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(date);
		Date end = CalendarUtil.convertEndDate(date);

		Specifications<BartagOrder> specifications = null;

		if(searchDate.equals("regDate")) {
			specifications = Specifications.where(BartagOrderSpecification.regDateBetween(start, end));
		} else if(searchDate.equals("updDate")) {
			specifications = Specifications.where(BartagOrderSpecification.updDateBetween(start, end));
		}

		if(companySeq != 0){
			specifications = specifications.and(BartagOrderSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(BartagOrderSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(!stat.equals("all")){
			specifications = specifications.and(BartagOrderSpecification.statEqual(stat));
		}

		if(!productYy.equals("")){
			specifications = specifications.and(BartagOrderSpecification.productYyEqual(productYy));
		}

		if(!productSeason.equals("")){
			specifications = specifications.and(BartagOrderSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("")){
			specifications = specifications.and(BartagOrderSpecification.styleEqual(style));
		}

		if(!color.equals("")){
			specifications = specifications.and(BartagOrderSpecification.colorEqual(color));
		}

		if(!size.equals("")){
			specifications = specifications.and(BartagOrderSpecification.sizeEqual(size));
		}

		if(!search.equals("") && option.equals("style")){
			specifications = specifications.and(BartagOrderSpecification.styleContain(search));
		} else if(!search.equals("") && option.equals("color")){
			specifications = specifications.and(BartagOrderSpecification.colorContain(search));
		} else if(!search.equals("") && option.equals("size")){
			specifications = specifications.and(BartagOrderSpecification.sizeContain(search));
		}

		return bartagOrderRepository.findAll(specifications, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderYy(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.product_yy AS data ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.product_yy ");
		query.append("ORDER BY bo.product_yy ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderSeason(Long companySeq, String productYy) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.product_season AS data, ");
		query.append("CASE bo.product_season ");
		query.append("WHEN 'A' THEN 'SEASONLESS' ");
		query.append("WHEN 'P' THEN 'SPRING' ");
		query.append("WHEN 'M' THEN 'SUMMER' ");
		query.append("WHEN 'F' THEN 'FALL' ");
		query.append("WHEN 'W' THEN 'WINTER' ");
		query.append("END AS explanatory ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE bo.product_yy = :productYy ");
		params.put("productYy", productYy);

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.product_season ");
		query.append("ORDER BY bo.product_season ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagSeasonModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderStyle(Long companySeq, String productYy, String productSeason) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.style AS data ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE bo.product_yy = :productYy ");
		query.append("AND bo.product_season = :productSeason ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.style ");
		query.append("ORDER BY bo.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderColor(Long companySeq, String productYy, String productSeason, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.color AS data ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE bo.product_yy = :productYy ");
		query.append("AND bo.product_season = :productSeason ");
		query.append("AND bo.style = :style ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.color ");
		query.append("ORDER BY bo.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.size AS data ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE bo.product_yy = :productYy ");
		query.append("AND bo.product_season = :productSeason ");
		query.append("AND bo.style = :style ");
		query.append("AND bo.color = :color ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.size ");
		query.append("ORDER BY bo.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional
	@Override
	public Map<String, Object> bartagOrderAmount(List<BartagOrder> bartagOrderList) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String createDate = CalendarUtil.convertFormat("yyyyMMdd");
		List<BartagMaster> bartagInsertList = new ArrayList<BartagMaster>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		Long count = Long.valueOf(0);
		Long seq = bartagService.bartagMaxSeq();

		seq ++;

		if(userInfo == null){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return map;
		}

		// 검증 단계
		for(BartagOrder bartagOrder : bartagOrderList){

			BartagOrder tempBartagOrder = bartagOrderRepository.findOne(bartagOrder.getBartagOrderSeq());

			if(tempBartagOrder.getStat().equals("2") || tempBartagOrder.getStat().equals("4")){

	    		map.put("resultCode", ApiResultConstans.PROCESS_BARTAG);
				map.put("resultMessage", ApiResultConstans.PROCESS_BARTAG_MESSAGE);
				return map;
	    	}

			if(tempBartagOrder.getStat().equals("6")){

	    		map.put("resultCode", ApiResultConstans.END_BARTAG_ORDER);
				map.put("resultMessage", ApiResultConstans.END_BARTAG_ORDER_MESSAGE);
				return map;
	    	}

			String checkOrderDegree = checkOrderDegree(bartagOrder.getErpKey(), bartagOrder.getOrderDegree());

			if(checkOrderDegree != null){
				map.put("resultCode", ApiResultConstans.NOT_COMPLETE_BEFORE_BARTAG_ORDER);
				map.put("resultMessage", ApiResultConstans.NOT_COMPLETE_BEFORE_BARTAG_ORDER_MESSAGE);
				return map;
			}
		}

		for(BartagOrder bartagOrder : bartagOrderList){

	    	BartagMaster bartagMaster = new BartagMaster();

	    	count ++;

	    	bartagMaster.setCreateDate(createDate);
	    	bartagMaster.setSeq(seq);
	    	bartagMaster.setLineSeq(count);

	    	bartagMaster.setStyle(bartagOrder.getStyle());
	    	bartagMaster.setColor(bartagOrder.getColor());
	    	bartagMaster.setSize(bartagOrder.getSize());

	    	ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSizeAndStatNot(bartagOrder.getStyle(),
	    																							   bartagOrder.getColor(),
	    																							   bartagOrder.getSize(), "D");

	    	bartagMaster.setProductRfidYySeason(productMaster.getProductRfidYySeason());

	    	bartagMaster.setProductYy(bartagOrder.getProductYy());
	    	bartagMaster.setProductSeason(bartagOrder.getProductSeason());
	    	bartagMaster.setOrderDegree(bartagOrder.getOrderDegree());
	    	bartagMaster.setAnnotherStyle(bartagOrder.getAnotherStyle());

	    	bartagMaster.setProductionCompanyInfo(bartagOrder.getProductionCompanyInfo());

	    	bartagMaster.setErpKey(bartagOrder.getErpKey());
	    	bartagMaster.setStat("0");
	    	bartagMaster.setResult("N");
	    	bartagMaster.setGenerateSeqYn("N");
	    	bartagMaster.setRegDate(new Date());
	    	bartagMaster.setReissueRequestYn("N");
	    	bartagMaster.setAdditionOrderDegree("0");
	    	bartagMaster.setStartFlag("R");

	    	Long amount = Long.valueOf(0);

			if(bartagOrder.getStat().equals("1")){

	    		amount = bartagOrder.getNonCheckCompleteAmount();
				bartagOrder.setStat("2");

	    	} else if(bartagOrder.getStat().equals("3") || bartagOrder.getStat().equals("4") || bartagOrder.getStat().equals("5")){

	    		amount = bartagOrder.getTempAdditionAmount();

	    		bartagOrder.setNonCheckAdditionAmount(bartagOrder.getNonCheckAdditionAmount() + bartagOrder.getTempAdditionAmount());

	    		int calc = Integer.parseInt(bartagOrder.getAdditionOrderDegree()) + 1;

	    		bartagOrder.setAdditionOrderDegree(Integer.toString(calc));
	    		bartagMaster.setAdditionOrderDegree(Integer.toString(calc));

	    		bartagOrder.setStat("4");
	    	}

	    	bartagMaster.setAmount(amount);
	    	bartagMaster.setTotalAmount(amount);

	    	String date = bartagOrder.getStyle() + bartagOrder.getColor() + bartagOrder.getSize() + " " +
	    				  bartagOrder.getErpKey() + " " +
	    				  bartagOrder.getStyle() + " " +
	    				  bartagOrder.getOrderDegree() + " " +
	    				  bartagOrder.getColor() + " " +
	    				  bartagOrder.getSize() + " " +
	    				  amount;

	    	bartagMaster.setData(date);

	    	// 태그발급업체 우선은 한곳만 가져올수 있도록 처리
	    	CompanyInfo publishCompanyInfo = companyInfoRepository.findTopByRoleInfoRole("publish");

	    	bartagMaster.setPublishCompanyInfo(publishCompanyInfo);

	    	bartagMaster.setDetailProductionCompanyName(bartagOrder.getDetailProductionCompanyName());

	    	// 시리얼 생성은 확정단계에서 진행
//	    	bartagMaster = bartagService.bartagSerialGenerate(bartagMaster);

			bartagInsertList.add(bartagMaster);

			// RFID 생산 정보 업데이트
			bartagOrder.setUpdDate(new Date());
			bartagOrder.setUpdUserInfo(userInfo);

			/*
			// 해당 RFID 생산 요청 오더보다 높은 오더가 있을 경우 해당 RFID 생산 요청은 종결 처리
			if(!maxOrderDegree(bartagOrder.getErpKey()).equals(bartagOrder.getOrderDegree())){
				bartagOrder.setStat("5");
				bartagOrder.setCompleteYn("Y");
			}
			*/
		}

		bartagOrderRepository.save(bartagOrderList);

		bartagInsertList = bartagMasterRepository.save(bartagInsertList);

		// ERP 바택 요청 정보 저장
//		erpService.saveBartag(bartagInsertList);

		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> bartagOrderComplete(List<BartagOrder> bartagOrderList) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<BartagMaster> bartagInsertList = new ArrayList<BartagMaster>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return map;
		}

		// 검증 단계
		for(BartagOrder bartagOrder : bartagOrderList){

			BartagOrder tempBartagOrder = bartagOrderRepository.findOne(bartagOrder.getBartagOrderSeq());

			if(tempBartagOrder.getStat().equals("3") || tempBartagOrder.getStat().equals("5")){

	    		map.put("resultCode", ApiResultConstans.COMPLETE_BARTAG_ORDER);
				map.put("resultMessage", ApiResultConstans.COMPLETE_BARTAG_ORDER_MESSAGE);
				return map;
	    	}

			if(tempBartagOrder.getStat().equals("6")){

	    		map.put("resultCode", ApiResultConstans.END_BARTAG_ORDER);
				map.put("resultMessage", ApiResultConstans.END_BARTAG_ORDER_MESSAGE);
				return map;
	    	}

		}

		for(BartagOrder bartagOrder : bartagOrderList){

			if(bartagOrder.getStat().equals("2")){

				bartagOrder.setCompleteYn("Y");
				bartagOrder.setCompleteDate(new Date());
				bartagOrder.setStat("3");

	    	} else if(bartagOrder.getStat().equals("4")){

	    		bartagOrder.setStat("5");
	    	}

			// RFID 생산 정보 업데이트
			bartagOrder.setUpdDate(new Date());
			bartagOrder.setUpdUserInfo(userInfo);

			Long completeAmount = Long.valueOf(0);
			Long additionAmount = Long.valueOf(0);

			List<BartagMaster> tempBartagList = bartagMasterRepository.findByErpKeyAndOrderDegreeAndStat(bartagOrder.getErpKey(), bartagOrder.getOrderDegree(), "0");

			/*
			// 같은 추가발주 차주가 있을 경우 추가 발주 차수 +1
			for(BartagMaster bartag : tempBartagList){

				if(bartag.getAdditionOrderDegree().equals(bartagOrder.getAdditionOrderDegree())) {
					int tempCalc = Integer.parseInt(bartagOrder.getAdditionOrderDegree()) + 1;
					bartagOrder.setAdditionOrderDegree(Integer.toString(tempCalc));
				}
			}
			*/

			for(BartagMaster bartag : tempBartagList){

				bartag.setStat("1");

				bartag = bartagService.bartagSerialGenerate(bartag, bartagOrder.getAdditionOrderDegree());

				if(bartag.getAdditionOrderDegree().equals("0")){
					completeAmount += bartag.getAmount();
				} else {
					additionAmount += bartag.getAmount();
				}

				bartagInsertList.add(bartag);

			}

			bartagOrder.setCompleteAmount(bartagOrder.getCompleteAmount() + completeAmount);
			bartagOrder.setNonCheckCompleteAmount(bartagOrder.getNonCheckCompleteAmount() - completeAmount);

			bartagOrder.setAdditionAmount(bartagOrder.getAdditionAmount() + additionAmount);
			bartagOrder.setNonCheckAdditionAmount(bartagOrder.getNonCheckAdditionAmount() - additionAmount);

			// 해당 RFID 생산 요청 오더보다 높은 오더가 있을 경우 해당 RFID 생산 요청은 종결 처리
			if (!maxOrderDegree(bartagOrder.getErpKey()).equals(bartagOrder.getOrderDegree())) {
				bartagOrder.setStat("6");
			}

		}

		// ERP 바택 요청 정보 저장
		erpService.saveBartag(bartagInsertList);

		bartagOrderRepository.save(bartagOrderList);
		bartagMasterRepository.save(bartagInsertList);

		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public void bartagOrderEndUpdate(String erpKey, String orderDegree) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE bartag_order " +
						  "SET stat = '6', " +
						      "complete_date = getDate(), " +
				              "complete_yn = 'Y', " +
						  	  "upd_date = getDate(), " +
						  	  "upd_user_seq = 1 " +
				   		"WHERE erp_key = ? " +
						  "AND order_degree <> ? " +
						  "AND stat > '1' ";

    	template.update(query, erpKey, orderDegree);
	}

	@Transactional(readOnly = true)
	@Override
	public String maxOrderDegree(String erpKey) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT MAX(order_degree) AS order_degree " +
				   		 "FROM bartag_order " +
				   		"WHERE erp_key = :erpKey";

		params.put("erpKey", erpKey);

		return nameTemplate.queryForObject(query, params, String.class);
	}

	@Transactional(readOnly = true)
	@Override
	public String checkOrderDegree(String erpKey, String orderDegree) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT MAX(order_degree) AS order_degree " +
				   		 "FROM bartag_order " +
				   		"WHERE erp_key = :erpKey " +
				   		  "AND complete_yn = 'N'" +
						  "AND order_degree < :orderDegree" ;

		params.put("erpKey", erpKey);
		params.put("orderDegree", orderDegree);

		return nameTemplate.queryForObject(query, params, String.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findBartagOrderDetail(Long seq, Pageable pageable) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		BartagOrder bartagOrder = bartagOrderRepository.findOne(seq);
		Page<BartagMaster> bartagList = null;

		if(bartagOrder != null){
			bartagList = bartagMasterRepository.findByErpKeyAndOrderDegree(bartagOrder.getErpKey(), bartagOrder.getOrderDegree(), pageable);

			obj.put("bartagOrder", bartagOrder);
			obj.put("bartagList", bartagList);
		}

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagSubCompany> findSubCompanyList(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT detail_production_company_name ");
		query.append("FROM bartag_order ");
		query.append("WHERE detail_production_company_name != '' ");
		query.append("AND production_company_seq = :companySeq ");
		query.append("AND detail_production_company_name IS NOT NULL ");
		query.append("GROUP BY detail_production_company_name ");

		params.put("companySeq", companySeq);

		return nameTemplate.query(query.toString(), params, new SubCompanyMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagOrderStylePerCom(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bo.style AS data ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");

		if(companySeq != 0){
			query.append("AND bo.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bo.style ");
		query.append("ORDER BY bo.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}
}
