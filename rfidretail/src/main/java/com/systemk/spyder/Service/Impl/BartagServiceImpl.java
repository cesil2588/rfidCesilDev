package com.systemk.spyder.Service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Response.BartagMinMaxResult;
import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.RfidTagIf;
import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;
import com.systemk.spyder.Entity.External.Key.RfidTagIfKey;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;
import com.systemk.spyder.Entity.Main.RfidTagStatus;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.External.RfidTagIfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.BartagOrderRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagReissueRequestDetailRepository;
import com.systemk.spyder.Repository.Main.RfidTagReissueRequestRepository;
import com.systemk.spyder.Repository.Main.RfidTagStatusRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.BartagMasterSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BartagLogService;
import com.systemk.spyder.Service.BartagOrderService;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.ProductionStorageLogService;
import com.systemk.spyder.Service.ProductionStorageService;
import com.systemk.spyder.Service.RedisService;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.CustomBean.BartagSubCompany;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.BartagGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;
import com.systemk.spyder.Service.Mapper.BartagCountRowMapper;
import com.systemk.spyder.Service.Mapper.BartagMinMaxResultMapper;
import com.systemk.spyder.Service.Mapper.SubCompanyMapper;
import com.systemk.spyder.Service.Mapper.Group.BartagGroupModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagSeasonModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectGroupByMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class BartagServiceImpl implements BartagService{

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;

	@Autowired
	private RfidTagIfRepository rfidTagIfRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RedisService redisService;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	@Autowired
	private RfidTagService rfidTagService;

	@Autowired
	private RfidTagReissueRequestDetailRepository rfidTagReissueRequestDetailRepository;

	@Autowired
	private BartagLogService bartagLogService;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private ProductionStorageService productionStorageService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private BartagOrderRepository bartagOrderRepository;

	@Autowired
	private ErpService erpService;

	@Autowired
	private BartagOrderService bartagOrderService;

	@Autowired
	private RfidTagReissueRequestRepository rfidTagReissueRequestRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@PersistenceContext
    private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public Page<BartagMaster> findAll(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option, Pageable pageable) throws Exception{

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<BartagMaster> page = null;
		Specifications<BartagMaster> specifications = null;

		if(defaultDateType.equals("1")){
			specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end));
		} else {
			specifications = Specifications.where(BartagMasterSpecification.createDateBetween(startDate, endDate));
		}

		specifications = specifications.and(BartagMasterSpecification.statGreaterThan("1"));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(BartagMasterSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(completeYn.equals("Y")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateNotNull());
		} else if(completeYn.equals("N")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateIsNull());
		}

		if(!search.equals("") && option.equals("productYy")){
			specifications = specifications.and(BartagMasterSpecification.productYyContain(search));
		} else if(!search.equals("") && option.equals("productSeason")){
			specifications = specifications.and(BartagMasterSpecification.productSeasonContain(search));
		} else if(!search.equals("") && option.equals("erpKey")){
			specifications = specifications.and(BartagMasterSpecification.erpKeyContain(search));
		} else if(!search.equals("") && option.equals("style")){
			specifications = specifications.and(BartagMasterSpecification.styleContain(search));
		} else if(!search.equals("") && option.equals("color")){
			specifications = specifications.and(BartagMasterSpecification.colorContain(search));
		} else if(!search.equals("") && option.equals("size")){
			specifications = specifications.and(BartagMasterSpecification.sizeContain(search));
		}

		page = bartagMasterRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BartagMaster> findAll(String regDate, Long companySeq, String subCompanyName, String completeYn, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception{

		Date start = CalendarUtil.convertStartDate(regDate);
		Date end = CalendarUtil.convertEndDate(regDate);

		Page<BartagMaster> page = null;
		Specifications<BartagMaster> specifications = null;

		specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end))
										.and(BartagMasterSpecification.statGreaterThan("1"));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(BartagMasterSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(completeYn.equals("Y")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateNotNull());
		} else if(completeYn.equals("N")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateIsNull());
		}

		if(!productYy.equals("")){
			specifications = specifications.and(BartagMasterSpecification.productYyEqual(productYy));
		}

		if(!productSeason.equals("")){
			specifications = specifications.and(BartagMasterSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("")){
			specifications = specifications.and(BartagMasterSpecification.styleEqual(style));
		}

		if(!color.equals("")){
			specifications = specifications.and(BartagMasterSpecification.colorEqual(color));

		}

		if(!size.equals("")){
			specifications = specifications.and(BartagMasterSpecification.sizeEqual(size));
		}

		page = bartagMasterRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagMaster> findAll(String regDate, Long companySeq, String defaultDateType, String subCompanyName, String completeYn) throws Exception{

		Date start = CalendarUtil.convertStartDate(regDate);
		Date end = CalendarUtil.convertEndDate(regDate);

		List<BartagMaster> list = null;
		Specifications<BartagMaster> specifications = null;

		specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end))
									   .and(BartagMasterSpecification.generateSeqYnEqual("Y"))
									   .and(BartagMasterSpecification.statGreaterThan("1"));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(BartagMasterSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(completeYn.equals("Y")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateNotNull());
		} else if(completeYn.equals("N")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateIsNull());
		}

		list = bartagMasterRepository.findAll(specifications);

		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagMaster> findAll(String startDate, String endDate, Long companySeq) throws Exception{

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		List<BartagMaster> list = null;
		Specifications<BartagMaster> specifications = null;

		specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end))
									   .and(BartagMasterSpecification.generateSeqYnEqual("Y"))
									   .and(BartagMasterSpecification.statGreaterThan("1"));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		list = bartagMasterRepository.findAll(specifications, new Sort(Direction.DESC, "bartagSeq"));

		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagMaster> findAll(String startDate, String endDate, Long companySeq, String productYy, String productSeason) throws Exception{

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		List<BartagMaster> list = null;
		Specifications<BartagMaster> specifications = null;

		specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end))
									   .and(BartagMasterSpecification.generateSeqYnEqual("Y"))
									   .and(BartagMasterSpecification.statGreaterThan("1"))
									   .and(BartagMasterSpecification.productYyEqual(productYy))
									   .and(BartagMasterSpecification.productSeasonEqual(productSeason));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		list = bartagMasterRepository.findAll(specifications, new Sort(Direction.DESC, "bartagSeq"));

		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagGroupModel> findBartagGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "reg_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER ( ");
		query.append("ORDER BY CURRENT_TIMESTAMP) AS ROW ");
		query.append("FROM ( ");
		query.append("SELECT TOP(:groupCount) CONVERT(VARCHAR(10), bm.reg_date, 112) AS reg_date, ");
		query.append("bm.product_yy, ");
		query.append("bm.product_season, ");
		query.append("ci.name, ");
		query.append("ci.company_seq, ");
		query.append("SUM(bm.amount) AS amount, ");
		query.append("SUM(bm.stat1_amount) AS stat1_amount, ");
		query.append("SUM(bm.stat2_amount) AS stat2_amount, ");
		query.append("SUM(bm.stat3_amount) AS stat3_amount, ");
		query.append("SUM(bm.stat4_amount) AS stat4_amount, ");
		query.append("SUM(bm.stat5_amount) AS stat5_amount, ");
		query.append("SUM(bm.stat6_amount) AS stat6_amount, ");
		query.append("SUM(bm.stat7_amount) AS stat7_amount ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		query.append("WHERE bm.reg_date BETWEEN :start AND :end ");
		query.append("AND bm.stat > 1 ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY CONVERT(VARCHAR(10), bm.reg_date, 112), bm.product_yy, bm.product_season, ci.company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + ") inner_query) ");
		query.append("SELECT reg_date, product_yy, product_season, name, company_seq, amount, stat1_amount, stat2_amount, stat3_amount, stat4_amount, stat5_amount, stat6_amount, stat7_amount ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("start", start);
		params.put("end", end);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new BartagGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountBartagGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT CONVERT(VARCHAR(10), bm.reg_date, 112) AS reg_date, bm.product_yy, bm.product_season, bm.production_company_seq ");
		query.append("FROM bartag_master bm ");
		query.append("WHERE bm.reg_date BETWEEN :start AND :end ");
		query.append("AND bm.stat > 1 ");

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY CONVERT(VARCHAR(10), bm.reg_date, 112), bm.product_yy, bm.product_season, bm.production_company_seq ");
		query.append(") AS temp_table ");

		params.put("start", start);
		params.put("end", end);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	/**
	 * 바택 발행 완료 프로세스
	 */
	@Transactional
	@Override
	public String bartagCompleteProcess(Long bartagSeq, Long userSeq) throws Exception {

		Date startDate = new Date();

		BartagMaster bartag = bartagMasterRepository.findOne(bartagSeq);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserSeq(userSeq);

		ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();

		// ERP 바택발행 조회
		RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
		rfidAc18IfKey.setAc18Crdt(bartag.getCreateDate());
		rfidAc18IfKey.setAc18Crsq(new BigDecimal(bartag.getSeq()));
		rfidAc18IfKey.setAc18Crno(new BigDecimal(bartag.getLineSeq()));
		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

		if (rfidAc18 != null && (bartag.getBartagEndDate() == null || ("").equals(bartag.getBartagEndDate()))) {

			// ERP 바택발행 업데이트
			rfidAc18.setAc18Prdt(new Date());
			rfidAc18.setAc18Pryn("Y");
			rfidAc18IfRepository.save(rfidAc18);

		}

		// 업체 재고 수량 저장
		ProductionStorage tempProductionStorage = productionStorageService.init(bartag, userSeq);

		// 업체 재고 이력 로그 저장
		productionStorageLogService.init(tempProductionStorage, startDate, userSeq);

		List<RfidTagMaster> rfidTagList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "2");

//		int count = 0;

		for(RfidTagMaster tag : rfidTagList){

			// 생산 입고예정 태그 저장
			ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();
			productionTag.setProductionStorageSeq(tempProductionStorage.getProductionStorageSeq());
			productionTag.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			productionTag.setErpKey(tag.getErpKey());
			productionTag.setRfidTag(tag.getRfidTag());
			productionTag.setRfidSeq(tag.getRfidSeq());
	    	productionTag.setRegUserInfo(userInfo);
			productionTag.setStat("1");
			productionTag.setCustomerCd(tag.getCustomerCd());
			productionTag.setRegDate(new Date());

			productionStorageRfidTagList.add(productionTag);

			// 생산 입고예정 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("1");
	    	rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 생산 입고예정 태그 상태 저장
			RfidTagStatus rfidTagStatus = new RfidTagStatus();
			rfidTagStatus.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagStatus.setErpKey(tag.getErpKey());
			rfidTagStatus.setRfidTag(tag.getRfidTag());
			rfidTagStatus.setRfidSeq(tag.getRfidSeq());
			rfidTagStatus.setStat("1");
			rfidTagStatus.setRegUserInfo(userInfo);
			rfidTagStatus.setRegDate(new Date());

			rfidTagStatusList.add(rfidTagStatus);

			// 생산 입고예정 태그 ERP 태그 테이블 저장
			RfidTagIf erpTag = new RfidTagIf();

			RfidTagIfKey rfidTagIfKey = new RfidTagIfKey();
			rfidTagIfKey.setTagRfid(tag.getRfidTag());
			rfidTagIfKey.setTagCrdt(bartag.getCreateDate());
			rfidTagIfKey.setTagCrsq(new BigDecimal(bartag.getSeq()));
			rfidTagIfKey.setTagCrno(new BigDecimal(bartag.getLineSeq()));

			erpTag.setKey(rfidTagIfKey);
			erpTag.setTagRfbc(tag.getErpKey() + tag.getRfidSeq());
			erpTag.setTagErpk(tag.getErpKey());
			erpTag.setTagYyss(tag.getSeason());
			erpTag.setTagOrdq(tag.getOrderDegree());
			erpTag.setTagPrcd(tag.getCustomerCd());
			erpTag.setTagPrtj(tag.getPublishLocation());
			erpTag.setTagPrdt(tag.getPublishRegDate());
			erpTag.setTagPrch(tag.getPublishDegree());
			erpTag.setTagSeqn(tag.getRfidSeq());
			erpTag.setTagUsyn("Y");
			erpTag.setTagStat("발행완료");
			erpTag.setTagStyl(bartag.getStyle());
			erpTag.setTagStcd(bartag.getAnnotherStyle());
			erpTag.setTagJjch(bartag.getOrderDegree());
			erpTag.setTagEndt(new Date());

			rfidTagIfList.add(erpTag);

		}

		// 태그 상태 변경
		rfidTagService.update(bartagSeq,"3", userSeq);

		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagIfRepository.save(rfidTagIfList);

		// 생산 입고예정으로 바택 상태 변경
		bartag.setStat("5");
		bartag.setUpdUserInfo(userInfo);
		bartag.setProductionStorage(tempProductionStorage);

		if(bartag.getBartagStartDate() == null || ("").equals(bartag.getBartagEndDate())){
			bartag.setBartagStartDate(new Date());
		}

		bartag.setBartagEndDate(new Date());

		// 바택 발행에 바택 발행 완료 수량 업데이트
		BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
		bartag = bartagLogService.currentAmountSetting(bartag);

		bartagMasterRepository.save(bartag);

		// 바택 수량 로그 업데이트
		bartagLogService.save(tempBartag, bartag, userSeq, startDate, "3");

		return bartag.getProductionCompanyInfo().getCustomerCode();

	}

	@Transactional
	@Override
	public String bartagCompleteProcessMod(BartagMaster bartag, UserInfo userInfo) throws Exception{

		Date startDate = new Date();

		ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();

		// ERP 바택발행 조회
		RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
		rfidAc18IfKey.setAc18Crdt(bartag.getCreateDate());
		rfidAc18IfKey.setAc18Crsq(new BigDecimal(bartag.getSeq()));
		rfidAc18IfKey.setAc18Crno(new BigDecimal(bartag.getLineSeq()));
		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

		if (rfidAc18 != null && (bartag.getBartagEndDate() == null || ("").equals(bartag.getBartagEndDate()))) {

			// ERP 바택발행 업데이트
			rfidAc18.setAc18Prdt(new Date());
			rfidAc18.setAc18Pryn("Y");
			rfidAc18IfRepository.save(rfidAc18);
		}

		// 업체 재고 수량 저장
		ProductionStorage tempProductionStorage = productionStorageService.init(bartag, userInfo.getUserSeq());

		// 업체 재고 이력 로그 저장
		productionStorageLogService.init(tempProductionStorage, startDate, userInfo.getUserSeq());

		List<RfidTagMaster> rfidList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "2");

		for(RfidTagMaster tag : rfidList){

			// 생산 입고예정 태그 저장
			ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();
			productionTag.setProductionStorageSeq(tempProductionStorage.getProductionStorageSeq());
			productionTag.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			productionTag.setErpKey(tag.getErpKey());
			productionTag.setRfidTag(tag.getRfidTag());
			productionTag.setRfidSeq(tag.getRfidSeq());
	    	productionTag.setRegUserInfo(userInfo);
			productionTag.setStat("1");
			productionTag.setCustomerCd(tag.getCustomerCd());
			productionTag.setRegDate(new Date());

			productionStorageRfidTagList.add(productionTag);

			// 생산 입고예정 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("1");
	    	rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 생산 입고예정 태그 상태 저장
			RfidTagStatus rfidTagStatus = new RfidTagStatus();
			rfidTagStatus.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagStatus.setErpKey(tag.getErpKey());
			rfidTagStatus.setRfidTag(tag.getRfidTag());
			rfidTagStatus.setRfidSeq(tag.getRfidSeq());
			rfidTagStatus.setStat("1");
			rfidTagStatus.setRegUserInfo(userInfo);
			rfidTagStatus.setRegDate(new Date());

			rfidTagStatusList.add(rfidTagStatus);

			// 생산 입고예정 태그 ERP 태그 테이블 저장
			RfidTagIf erpTag = new RfidTagIf();

			RfidTagIfKey rfidTagIfKey = new RfidTagIfKey();
			rfidTagIfKey.setTagRfid(tag.getRfidTag());
			rfidTagIfKey.setTagCrdt(bartag.getCreateDate());
			rfidTagIfKey.setTagCrsq(new BigDecimal(bartag.getSeq()));
			rfidTagIfKey.setTagCrno(new BigDecimal(bartag.getLineSeq()));

			erpTag.setKey(rfidTagIfKey);
			erpTag.setTagRfbc(tag.getErpKey() + tag.getRfidSeq());
			erpTag.setTagErpk(tag.getErpKey());
			erpTag.setTagYyss(tag.getSeason());
			erpTag.setTagOrdq(tag.getOrderDegree());
			erpTag.setTagPrcd(tag.getCustomerCd());
			erpTag.setTagPrtj(tag.getPublishLocation());
			erpTag.setTagPrdt(tag.getPublishRegDate());
			erpTag.setTagPrch(tag.getPublishDegree());
			erpTag.setTagSeqn(tag.getRfidSeq());
			erpTag.setTagUsyn("Y");
			erpTag.setTagStat("발행완료");
			erpTag.setTagStyl(bartag.getStyle());
			erpTag.setTagStcd(bartag.getAnnotherStyle());
			erpTag.setTagJjch(bartag.getOrderDegree());
			erpTag.setTagEndt(new Date());

			rfidTagIfList.add(erpTag);

		}

		rfidTagService.update(bartag.getBartagSeq(),"3", userInfo.getUserSeq());

		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagIfRepository.save(rfidTagIfList);

		// 생산 입고예정으로 바택 상태 변경
		bartag.setStat("5");
		bartag.setUpdUserInfo(userInfo);
		bartag.setProductionStorage(tempProductionStorage);

		if(bartag.getBartagStartDate() == null || ("").equals(bartag.getBartagEndDate())){
			bartag.setBartagStartDate(new Date());
		}

		bartag.setBartagEndDate(new Date());

		// 바택 발행에 바택 발행 완료 수량 업데이트
		BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
		bartag = bartagLogService.currentAmountSetting(bartag);

		bartagMasterRepository.save(bartag);

		// 바택 수량 로그 업데이트
		bartagLogService.save(tempBartag, bartag, userInfo.getUserSeq(), startDate, "3");

		return bartag.getProductionCompanyInfo().getCustomerCode();
	}

	/**
	 * 바택 재발행 완료 프로세스
	 */
	@Transactional
	@Override
	public String bartagReissueProcess(BartagMaster bartag, UserInfo userInfo) throws Exception {

		Date startDate = new Date();

		ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailList = new ArrayList<RfidTagReissueRequestDetail>();
		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<Long> reissueRequestSeqList = new ArrayList<Long>();
		ArrayList<RfidTagReissueRequest> reissueRequestList = new ArrayList<RfidTagReissueRequest>();

		List<RfidTagMaster> disuseRfidList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "6");
		List<RfidTagMaster> reissueRfidList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "4");

		ProductionStorage productionStorage = productionStorageRepository.findByBartagMasterBartagSeq(bartag.getBartagSeq());

		for(RfidTagMaster tag : disuseRfidList){

			// 바택 발행 태그 상태 > 폐기 업데이트
			tag.setStat("7");
			tag.setUpdDate(new Date());
			tag.setUpdUserInfo(userInfo);
			rfidTagList.add(tag);

			// 생산 재발행 요청 태그 > 폐기 업데이트
			ProductionStorageRfidTag productionTag = productionStorageRfidTagRepository.findByRfidTag(tag.getRfidTag());

	    	productionTag.setUpdUserInfo(userInfo);
			productionTag.setStat("5");
			productionTag.setUpdDate(new Date());

			productionStorageRfidTagList.add(productionTag);

			// 생산 재발행 요청 태그 히스토리 등록
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("4");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 생산 재발행 요청 상세 업데이트
			RfidTagReissueRequestDetail rfidTagReissueDetail = rfidTagReissueRequestDetailRepository.findByRfidTagAndStat(tag.getRfidTag(), "1");
			rfidTagReissueDetail.setStat("2");

			rfidTagReissueRequestDetailList.add(rfidTagReissueDetail);

			if(!reissueRequestSeqList.contains(rfidTagReissueDetail.getRfidTagReissueRequest().getRfidTagReissueRequestSeq())){
				reissueRequestSeqList.add(rfidTagReissueDetail.getRfidTagReissueRequest().getRfidTagReissueRequestSeq());
			}

			// 생산 재발행 요청 태그 ERP 태그 테이블 > 페기 업데이트
			RfidTagIf erpTag = rfidTagIfRepository.findByKeyTagRfid(tag.getRfidTag());
			erpTag.setTagUsyn("N");
			erpTag.setTagStat("폐기");
			erpTag.setTagUpdt(new Date());

			rfidTagIfList.add(erpTag);
		}

		for(RfidTagMaster tag : reissueRfidList){

			// 바택 발행 태그 상태 > 재발행완료 업데이트
			tag.setStat("5");
			tag.setUpdDate(new Date());
			tag.setUpdUserInfo(userInfo);

			rfidTagList.add(tag);

			// 생산 재발행 태그 > 저장
			ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();

			productionTag.setProductionStorageSeq(productionStorage.getProductionStorageSeq());
			productionTag.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			productionTag.setErpKey(tag.getErpKey());
			productionTag.setRfidTag(tag.getRfidTag());
			productionTag.setRfidSeq(tag.getRfidSeq());
	    	productionTag.setRegUserInfo(userInfo);
			productionTag.setStat("1");
			productionTag.setRegDate(new Date());

			productionStorageRfidTagList.add(productionTag);

			// 생산 재발행 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("5");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 생산 재발행 태그 상태값 저장
			RfidTagStatus rfidTagStatus = new RfidTagStatus();
			rfidTagStatus.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagStatus.setErpKey(tag.getErpKey());
			rfidTagStatus.setRfidTag(tag.getRfidTag());
			rfidTagStatus.setRfidSeq(tag.getRfidSeq());
			rfidTagStatus.setStat("1");
			rfidTagStatus.setRegUserInfo(userInfo);
			rfidTagStatus.setRegDate(new Date());

			rfidTagStatusList.add(rfidTagStatus);

			// 생산 재발행 태그 ERP 태그 테이블 저장
			RfidTagIf erpTag = new RfidTagIf();

			RfidTagIfKey rfidTagIfKey = new RfidTagIfKey();
			rfidTagIfKey.setTagRfid(tag.getRfidTag());
			rfidTagIfKey.setTagCrdt(bartag.getCreateDate());
			rfidTagIfKey.setTagCrsq(new BigDecimal(bartag.getSeq()));
			rfidTagIfKey.setTagCrno(new BigDecimal(bartag.getLineSeq()));

			erpTag.setKey(rfidTagIfKey);
			erpTag.setTagRfbc(tag.getErpKey() + tag.getRfidSeq());
			erpTag.setTagErpk(tag.getErpKey());
			erpTag.setTagYyss(tag.getSeason());
			erpTag.setTagOrdq(tag.getOrderDegree());
			erpTag.setTagPrcd(tag.getCustomerCd());
			erpTag.setTagPrtj(tag.getPublishLocation());
			erpTag.setTagPrdt(tag.getPublishRegDate());
			erpTag.setTagPrch(tag.getPublishDegree());
			erpTag.setTagSeqn(tag.getRfidSeq());
			erpTag.setTagUsyn("Y");
			erpTag.setTagStat("발행완료");
			erpTag.setTagStyl(bartag.getStyle());
			erpTag.setTagStcd(bartag.getAnnotherStyle());
			erpTag.setTagJjch(bartag.getOrderDegree());
			erpTag.setTagEndt(new Date());

			rfidTagIfList.add(erpTag);
		}

		rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagReissueRequestDetailRepository.save(rfidTagReissueRequestDetailList);
		rfidTagIfRepository.save(rfidTagIfList);

//		productionStorageRfidTagRepository.flush();
//		rfidTagMasterRepository.flush();

		// 이전 생산 수량 정보 넣음
		ProductionStorage tempProductionStorage = productionStorageLogService.beforeAmountSetting(productionStorage);
		productionStorage = productionStorageLogService.currentAmountSetting(productionStorage);
		productionStorage.setUpdDate(new Date());
		productionStorage.setUpdUserInfo(userInfo);

		productionStorageRepository.save(productionStorage);

		// 생산 수량 로그 업데이트
		productionStorageLogService.save(tempProductionStorage, productionStorage, userInfo.getUserSeq(), startDate, "5", "1");

		// 이전 바택 수량 정보 넣음
		BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
		bartag = bartagLogService.currentAmountSetting(bartag);

		bartag.setUpdDate(new Date());
		bartag.setUpdUserInfo(userInfo);
		bartag.setReissueRequestYn("N");

		bartagMasterRepository.save(bartag);

		// 바택 수량 로그 업데이트
		bartagLogService.save(tempBartag, bartag, userInfo.getUserSeq(), startDate, "5");

		// 태그 재발행 요청 완료 여부 체크
		for(Long reissueRequestSeq : reissueRequestSeqList){
			RfidTagReissueRequest tempReissueRequest = rfidTagReissueRequestRepository.findOne(reissueRequestSeq);

			boolean check = true;

			for(RfidTagReissueRequestDetail tempReissueRequestDetail : tempReissueRequest.getRfidTagReissueRequestDetail()){
				if(tempReissueRequestDetail.getStat().equals("2")){
					check = false;
				}
			}

			if(check){
				tempReissueRequest.setCompleteYn("Y");
				tempReissueRequest.setCompleteDate(new Date());
				tempReissueRequest.setUpdUserInfo(userInfo);
				tempReissueRequest.setUpdDate(new Date());

				reissueRequestList.add(tempReissueRequest);
			}
		}

		rfidTagReissueRequestRepository.save(reissueRequestList);

		return bartag.getProductionCompanyInfo().getCustomerCode();
	}

	@Override
	public String bartagDownloadSeq(String bartagRegDate, LoginUser user) throws Exception {

		String downloadSeq = redisService.find("bartagDownloadSeq", bartagRegDate);

		if(downloadSeq == null || downloadSeq.isEmpty()){
			downloadSeq = StringUtil.convertCipher("3", 1);
			redisService.save("bartagDownloadSeq", bartagRegDate, downloadSeq);

		} else if(user.getCompanyInfo().getRoleInfo().getRole().equals("publish")){
			int tempDownloadSeq = Integer.parseInt(downloadSeq);
			tempDownloadSeq ++;
			downloadSeq = StringUtil.convertCipher("3", tempDownloadSeq);
			redisService.save("bartagDownloadSeq", bartagRegDate, downloadSeq);
		}

		return downloadSeq;
	}

	@Transactional(readOnly = true)
	@Override
	public JSONObject countAll(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option) throws Exception{

		JSONObject obj = new JSONObject();

		CountModel count = count(startDate, endDate, companySeq, subCompanyName, defaultDateType, completeYn, search, option);

		obj.put("allStat1Amount", count.getStat1_amount());
		obj.put("allStat2Amount", count.getStat2_amount());
		obj.put("allStat3Amount", count.getStat3_amount());
		obj.put("allStat4Amount", count.getStat4_amount());
		obj.put("allStat5Amount", count.getStat5_amount());
		obj.put("allStat6Amount", count.getStat6_amount());
		obj.put("allStat7Amount", count.getStat7_amount());
		obj.put("allAmount", count.getAmount());
		obj.put("allTotalAmount", count.getTotal_amount());

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BartagSubCompany> findSubCompanyList(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT detail_production_company_name ");
		query.append("FROM bartag_master ");
		query.append("WHERE detail_production_company_name != '' ");
		query.append("AND production_company_seq = :companySeq ");
		query.append("AND detail_production_company_name IS NOT NULL ");
		query.append("GROUP BY detail_production_company_name ");

		params.put("companySeq", companySeq);

		return nameTemplate.query(query.toString(), params, new SubCompanyMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(String startDate, String endDate, Long companySeq, String subCompanyName, String defaultDateType, String completeYn, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT SUM(amount) amount, ");
		query.append("SUM(total_amount) total_amount, ");
		query.append("SUM(stat1_amount) stat1_amount, ");
		query.append("SUM(stat2_amount) stat2_amount, ");
		query.append("SUM(stat3_amount) stat3_amount, ");
		query.append("SUM(stat4_amount) stat4_amount, ");
		query.append("SUM(stat5_amount) stat5_amount, ");
		query.append("SUM(stat6_amount) stat6_amount, ");
		query.append("SUM(stat7_amount) stat7_amount ");
		query.append("FROM bartag_master ");

		if(defaultDateType.equals("1")){
			query.append("WHERE reg_date BETWEEN :startDate AND :endDate ");
			params.put("startDate", start);
			params.put("endDate", end);
		} else {
			query.append("WHERE create_date BETWEEN :startDate AND :endDate ");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}

		query.append("AND stat > '1' ");

		if(companySeq != 0){
			query.append("AND production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!subCompanyName.equals("")){
			query.append("AND detail_production_company_name LIKE :subCompanyName ");
			params.put("subCompanyName", "%" + subCompanyName + "%");
		}

		if(completeYn.equals("Y")){
			query.append("AND bartag_end_date IS NOT NULL ");
		} else if(completeYn.equals("N")){
			query.append("AND bartag_end_date IS NULL ");
		}

		if(!search.equals("") && option.equals("productYy")){
			query.append("AND product_yy LIKE :productYy ");
			params.put("productYy", "%" + search + "%");
		} else if(!search.equals("") && option.equals("productSeason")){
			query.append("AND product_season LIKE :productSeason ");
			params.put("productSeason", "%" + search + "%");
		} else if(!search.equals("") && option.equals("erpKey")){
			query.append("AND erpKey LIKE :erpKey ");
			params.put("erpKey", "%" + search + "%");
		} else if(!search.equals("") && option.equals("style")){
			query.append("AND style LIKE :style ");
			params.put("style", "%" + search + "%");
		} else if(!search.equals("") && option.equals("color")){
			query.append("AND color LIKE :color ");
			params.put("color", "%" + search + "%");
		} else if(!search.equals("") && option.equals("size")){
			query.append("AND size LIKE :size ");
			params.put("size", "%" + search + "%");
		}

		return nameTemplate.queryForObject(query.toString(), params, new BartagCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long bartagMaxSeq() throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "SELECT MAX(seq) FROM bartag_master";

    	return template.queryForObject(query, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public BartagMaster bartagSerialGenerate(BartagMaster bartagMaster) throws Exception {

		Long endRfidSeq = bartagMasterRepository.findMaxEndRfidSeq(bartagMaster.getErpKey(), bartagMaster.getBartagSeq());

		int startAmount = 0;
    	int endAmount = 0;

    	if(endRfidSeq.intValue() == 0) {

			startAmount = 1;
			endAmount = bartagMaster.getAmount().intValue();

			bartagMaster.setStartRfidSeq(StringUtil.convertCipher("5", startAmount));
			bartagMaster.setEndRfidSeq(StringUtil.convertCipher("5", endAmount));
			bartagMaster.setAdditionOrderDegree("0");
			bartagMaster.setStat1Amount(Long.valueOf(0));
			bartagMaster.setStat2Amount(Long.valueOf(0));
			bartagMaster.setStat3Amount(Long.valueOf(0));
			bartagMaster.setStat4Amount(Long.valueOf(0));
			bartagMaster.setStat5Amount(Long.valueOf(0));
			bartagMaster.setStat6Amount(Long.valueOf(0));
			bartagMaster.setStat7Amount(Long.valueOf(0));

		} else {

			BartagMaster tempBartag = bartagMasterRepository.findTopByErpKeyAndOrderDegreeOrderByAdditionOrderDegreeDesc(bartagMaster.getErpKey(), bartagMaster.getOrderDegree());
			if(tempBartag != null){
				int addOrderDegree = Integer.parseInt(tempBartag.getAdditionOrderDegree());
				addOrderDegree ++;
				bartagMaster.setAdditionOrderDegree(String.valueOf(addOrderDegree));
			} else {
				bartagMaster.setAdditionOrderDegree("0");
			}

			endAmount = endRfidSeq.intValue() + bartagMaster.getAmount().intValue();
			startAmount = (endAmount - bartagMaster.getAmount().intValue()) + 1;

			bartagMaster.setStartRfidSeq(StringUtil.convertCipher("5", startAmount));
			bartagMaster.setEndRfidSeq(StringUtil.convertCipher("5", endAmount));

			bartagMaster.setStat1Amount(Long.valueOf(0));
			bartagMaster.setStat2Amount(Long.valueOf(0));
			bartagMaster.setStat3Amount(Long.valueOf(0));
			bartagMaster.setStat4Amount(Long.valueOf(0));
			bartagMaster.setStat5Amount(Long.valueOf(0));
			bartagMaster.setStat6Amount(Long.valueOf(0));
			bartagMaster.setStat7Amount(Long.valueOf(0));
		}

		return bartagMaster;
	}

	@Transactional(readOnly = true)
	@Override
	public BartagMaster bartagSerialGenerate(BartagMaster bartagMaster, String additionOrderDegree) throws Exception {

		Long endRfidSeq = bartagMasterRepository.findMaxEndRfidSeq(bartagMaster.getErpKey(), bartagMaster.getBartagSeq());

		int startAmount = 0;
    	int endAmount = 0;

    	if(endRfidSeq.intValue() == 0) {

    		startAmount = 1;
			endAmount = bartagMaster.getAmount().intValue();

			bartagMaster.setStartRfidSeq(StringUtil.convertCipher("5", startAmount));
			bartagMaster.setEndRfidSeq(StringUtil.convertCipher("5", endAmount));
			bartagMaster.setAdditionOrderDegree("0");
			bartagMaster.setStat1Amount(Long.valueOf(0));
			bartagMaster.setStat2Amount(Long.valueOf(0));
			bartagMaster.setStat3Amount(Long.valueOf(0));
			bartagMaster.setStat4Amount(Long.valueOf(0));
			bartagMaster.setStat5Amount(Long.valueOf(0));
			bartagMaster.setStat6Amount(Long.valueOf(0));
			bartagMaster.setStat7Amount(Long.valueOf(0));

    	} else {

    		bartagMaster.setAdditionOrderDegree(additionOrderDegree);

			endAmount = endRfidSeq.intValue() + bartagMaster.getAmount().intValue();
			startAmount = (endAmount - bartagMaster.getAmount().intValue()) + 1;

			bartagMaster.setStartRfidSeq(StringUtil.convertCipher("5", startAmount));
			bartagMaster.setEndRfidSeq(StringUtil.convertCipher("5", endAmount));

			bartagMaster.setStat1Amount(Long.valueOf(0));
			bartagMaster.setStat2Amount(Long.valueOf(0));
			bartagMaster.setStat3Amount(Long.valueOf(0));
			bartagMaster.setStat4Amount(Long.valueOf(0));
			bartagMaster.setStat5Amount(Long.valueOf(0));
			bartagMaster.setStat6Amount(Long.valueOf(0));
			bartagMaster.setStat7Amount(Long.valueOf(0));
    	}

		return bartagMaster;
	}

	@Transactional
	@Override
	public Map<String, Object> delete(List<BartagMaster> bartagList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		// 사용자 정보 없을시 에러 리턴
		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_USE_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		// 전달된 바택 리스트가 없을시 에러 리턴
		if(bartagList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
			return obj;
		}

		// erp키와 오더차수 셋팅
		String erpKey = bartagList.get(0).getErpKey();
		String orderDegree = bartagList.get(0).getOrderDegree();

		for(BartagMaster bartag : bartagList){

			// 확정 이후에는 에러 발생
			if(!bartag.getStat().equals("0")){
				obj.put("resultCode", ApiResultConstans.PROCESS_BARTAG);
				obj.put("resultMessage", ApiResultConstans.PROCESS_BARTAG_MESSAGE);
				return obj;
			}
		}

		bartagMasterRepository.delete(bartagList);
		bartagMasterRepository.flush();

		// 바택 오더 수량 수정
		BartagOrder bartagOrder = bartagOrderRepository.findByErpKeyAndOrderDegree(erpKey, orderDegree);

		List<BartagMaster> tempBartagList = bartagMasterRepository.findByErpKeyAndOrderDegreeAndStat(erpKey, orderDegree, "0");

		Long nonCheckCompleteAmount = Long.valueOf(0);
		Long nonCheckAdditionAmount = Long.valueOf(0);

		for(BartagMaster bartag : tempBartagList){
			if(bartag.getAdditionOrderDegree().equals("0")){
				nonCheckCompleteAmount += bartag.getAmount();
			} else {
				nonCheckAdditionAmount += bartag.getAmount();
			}
		}

		bartagOrder.setNonCheckCompleteAmount(nonCheckCompleteAmount);
		bartagOrder.setNonCheckAdditionAmount(nonCheckAdditionAmount);

		// 바택 정보에서 추가오더 맥스값 확인
		BartagMaster tempBartag = bartagMasterRepository.findTopByErpKeyAndOrderDegreeOrderByAdditionOrderDegreeDesc(erpKey, orderDegree);

		// 바택 추가차수가 없을 경우 0차수 입력
		if(tempBartag == null){
			bartagOrder.setAdditionOrderDegree("0");
			bartagOrder.setStat("1");

		// 바택 추가차수가 있을 경우 해당 차수 바택오더에 입력
		} else {

			bartagOrder.setAdditionOrderDegree(tempBartag.getAdditionOrderDegree());
			bartagOrder.setStat("3");
		}

		bartagOrder.setUpdUserInfo(userInfo);
		bartagOrder.setUpdDate(new Date());

		bartagOrderRepository.save(bartagOrder);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> update(List<BartagMaster> bartagList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_USE_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		if(bartagList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
			return obj;
		}

		// 검증 단계
		for(BartagMaster bartag : bartagList){

			// 확정 이후에는 에러 발생
			if(!bartag.getStat().equals("0")){
				obj.put("resultCode", ApiResultConstans.PROCESS_BARTAG);
				obj.put("resultMessage", ApiResultConstans.PROCESS_BARTAG_MESSAGE);
				return obj;
			}

		}

		// erp키와 오더차수 셋팅
		String erpKey = bartagList.get(0).getErpKey();
		String orderDegree = bartagList.get(0).getOrderDegree();

		for(BartagMaster bartag : bartagList){
			String date = bartag.getStyle() + bartag.getColor() + bartag.getSize() + " " +
					bartag.getErpKey() + " " +
					bartag.getStyle() + " " +
					bartag.getOrderDegree() + " " +
	  				bartag.getColor() + " " +
	  				bartag.getSize() + " " +
	  				bartag.getAmount();

			bartag.setData(date);
			bartag.setTotalAmount(bartag.getAmount());
		}

		bartagList = bartagMasterRepository.save(bartagList);
//		bartagMasterRepository.flush();

		// 바택 오더 수량 수정
		BartagOrder bartagOrder = bartagOrderRepository.findByErpKeyAndOrderDegree(erpKey, orderDegree);

		List<BartagMaster> tempBartagList = bartagMasterRepository.findByErpKeyAndOrderDegreeAndStat(erpKey, orderDegree, "0");

		Long nonCheckCompleteAmount = Long.valueOf(0);
		Long nonCheckAdditionAmount = Long.valueOf(0);

		for(BartagMaster bartag : tempBartagList){
			if(bartag.getAdditionOrderDegree().equals("0")){
				nonCheckCompleteAmount += bartag.getAmount();
			} else {
				nonCheckAdditionAmount += bartag.getAmount();
			}
		}

		bartagOrder.setNonCheckCompleteAmount(nonCheckCompleteAmount);
		bartagOrder.setNonCheckAdditionAmount(nonCheckAdditionAmount);

		bartagOrder.setUpdUserInfo(userInfo);
		bartagOrder.setUpdDate(new Date());

		bartagOrderRepository.save(bartagOrder);

		bartagMasterRepository.save(bartagList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> complete(List<BartagMaster> bartagList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_USE_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		if(bartagList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
			return obj;
		}

		// 검증 단계
		for(BartagMaster bartag : bartagList){

			// 확정 이후에는 에러 발생
			if(!bartag.getStat().equals("0")){
				obj.put("resultCode", ApiResultConstans.PROCESS_BARTAG);
				obj.put("resultMessage", ApiResultConstans.PROCESS_BARTAG_MESSAGE);
				return obj;
			}
		}
		// erp키와 오더차수 셋팅
		String erpKey = bartagList.get(0).getErpKey();
		String orderDegree = bartagList.get(0).getOrderDegree();

		BartagOrder bartagOrder = bartagOrderRepository.findByErpKeyAndOrderDegree(erpKey, orderDegree);

		Long completeAmount = Long.valueOf(0);
		Long additionAmount = Long.valueOf(0);

		for(BartagMaster bartag : bartagList){

			// 확정 이후에는 에러 발생
			if(!bartag.getStat().equals("0")){
				obj.put("resultCode", ApiResultConstans.PROCESS_BARTAG);
				obj.put("resultMessage", ApiResultConstans.PROCESS_BARTAG_MESSAGE);
				return obj;
			}

			if(bartag.getAdditionOrderDegree().equals("0")){
				completeAmount += bartag.getAmount();
			} else {
				additionAmount += bartag.getAmount();
			}

			bartag.setStat("1");
			bartag = bartagSerialGenerate(bartag, bartagOrder.getAdditionOrderDegree());
		}

		if(bartagOrder.getStat().equals("2")){
			bartagOrder.setCompleteYn("Y");
			bartagOrder.setCompleteDate(new Date());
			bartagOrder.setStat("3");
		} else if(bartagOrder.getStat().equals("4")){
			bartagOrder.setStat("5");
		}

		// 확정수량과 미확정 수량 변경
		bartagOrder.setCompleteAmount(bartagOrder.getCompleteAmount() + completeAmount);
		bartagOrder.setNonCheckCompleteAmount(bartagOrder.getNonCheckCompleteAmount() - completeAmount);

		bartagOrder.setAdditionAmount(bartagOrder.getAdditionAmount() + additionAmount);
		bartagOrder.setNonCheckAdditionAmount(bartagOrder.getNonCheckAdditionAmount() - additionAmount);

		// 해당 RFID 생산 요청 오더보다 높은 오더가 있을 경우 해당 RFID 생산 요청은 종결 처리
		if (!bartagOrderService.maxOrderDegree(bartagOrder.getErpKey()).equals(bartagOrder.getOrderDegree())) {
			bartagOrder.setStat("6");
		}

		// ERP 바택 요청 정보 저장
		erpService.saveBartag(bartagList);

		bartagOrder.setUpdUserInfo(userInfo);
		bartagOrder.setUpdDate(new Date());

		bartagOrderRepository.save(bartagOrder);

		bartagMasterRepository.save(bartagList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagYy(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_yy AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.product_yy ");
		query.append("ORDER BY bm.product_yy ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSeason(Long companySeq, String productYy) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.product_season AS data, ");
		query.append("CASE bm.product_season ");
		query.append("WHEN 'A' THEN 'SEASONLESS' ");
		query.append("WHEN 'P' THEN 'SPRING' ");
		query.append("WHEN 'M' THEN 'SUMMER' ");
		query.append("WHEN 'F' THEN 'FALL' ");
		query.append("WHEN 'W' THEN 'WINTER' ");
		query.append("END AS explanatory ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		query.append("WHERE bm.product_yy = :productYy ");
		params.put("productYy", productYy);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.product_season ");
		query.append("ORDER BY bm.product_season ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagSeasonModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(Long companySeq, String productYy, String productSeason) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.style ");
		query.append("ORDER BY bm.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());

	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String productYy, String productSeason, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.color AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style = :style ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.color ");
		query.append("ORDER BY bm.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String productYy, String productSeason, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.size AS data ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		query.append("WHERE bm.product_yy = :productYy ");
		query.append("AND bm.product_season = :productSeason ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");

		params.put("productYy", productYy);
		params.put("productSeason", productSeason);
		params.put("style", style);
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.size ");
		query.append("ORDER BY bm.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> apiFindOne(Long bartagSeq) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		BartagMaster bartagMaster = bartagMasterRepository.findOne(bartagSeq);

		if(bartagMaster == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_DATA);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_DATA_MESSAGE);

			return obj;
		}

		obj.put("data", bartagMaster);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}


	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> apiFindAll(String startDate, String endDate, Long companySeq, String subCompanyName, String completeYn, String productYy, String productSeason, String style, String color, String size, String search, String option, Pageable pageable) throws Exception{

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<BartagMaster> page = null;
		Specifications<BartagMaster> specifications = null;

		specifications = Specifications.where(BartagMasterSpecification.regDateBetween(start, end))
										.and(BartagMasterSpecification.statGreaterThan("1"));

		if(companySeq != 0){
			specifications = specifications.and(BartagMasterSpecification.companySeqEqual(companySeq));
		}

		if(!subCompanyName.equals("")){
			specifications = specifications.and(BartagMasterSpecification.detailProductionCompanyNameContain(subCompanyName));
		}

		if(completeYn.equals("Y")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateNotNull());
		} else if(completeYn.equals("N")){
			specifications = specifications.and(BartagMasterSpecification.bartagEndDateIsNull());
		}

		if(!productYy.equals("")){
			specifications = specifications.and(BartagMasterSpecification.productYyEqual(productYy));
		}

		if(!productSeason.equals("")){
			specifications = specifications.and(BartagMasterSpecification.productSeasonEqual(productSeason));
		}

		if(!style.equals("")){
			specifications = specifications.and(BartagMasterSpecification.styleEqual(style));
		}

		if(!color.equals("")){
			specifications = specifications.and(BartagMasterSpecification.colorEqual(color));

		}

		if(!size.equals("")){
			specifications = specifications.and(BartagMasterSpecification.sizeEqual(size));
		}

		page = bartagMasterRepository.findAll(specifications, pageable);

		if(page.getContent().size() == 0) {

			obj.put("resultCode", ApiResultConstans.NOT_FIND_DATA);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_DATA_MESSAGE);

			return obj;
		}

		obj.put("data", page);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	//태그 이력 조회
	@Override
	public Map<String, Object> getBartagHistory(String rfidTag) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

			List<RfidTagHistory> rfidHistoryList = rfidTagHistoryRepository.findByRfidTag(rfidTag);
			ProductMaster pm = new ProductMaster();

				List<Map<String,Object>> rfidInfoList = new ArrayList<Map<String,Object>>();

				for(RfidTagHistory rfid : rfidHistoryList){

					Map<String,Object> rfidMap = new HashMap<String, Object>();

					rfidMap.put("erpKey", rfid.getErpKey());
					rfidMap.put("regDate", rfid.getRegDate().toString());

					String[] workName = {"생산 발행 완료", "생산 입고", "생산 출고", "생산 재발행 요청", "생산 재발행 완료", "생산 반품 검수", "생산 반품", "생산 폐기", "9", "10"
										, "물류 입고 예정", "물류 입고", "물류 출고", "물류 재발행 대기", "물류 재발행 요청", "물류 반품", "물류 폐기", "물류 입고 반품", "19" , "20"
										, "매장 입고 예정", "매장 입고", "매장 출고(판매)", "반품", "매장 재발행", "매장간 이동", "폐기"};

					//workName 배열의 인덱스 값을 work의 코드 값으로 이용하여 내용을 추출... 해당 값 없는 경우는 그냥 숫자로 표기
					rfidMap.put("work", workName[Integer.parseInt(rfid.getWork())-1]);
					if(rfid.getRegUserInfo()==null)
						rfidMap.put("regUser", "");
					else
						rfidMap.put("regUser", rfid.getRegUserInfo().getCompanyInfo().getName());
					//해당 erp_key에 해당하는 상품의 정보를 가져오기 위해 product_master 호출
					pm = productMasterRepository.findByErpKey(rfid.getErpKey());

					rfidMap.put("style", pm.getStyle());
					rfidMap.put("color", pm.getColor());
					rfidMap.put("size", pm.getSize());

					rfidInfoList.add(rfidMap);

				}

				obj.put("content", rfidInfoList);

		return obj;
	}

	//업체 , 스타일, 컬러, 사이즈별 태그 리스트를 추출
	@Override
	public Map<String, Object> getBartagList(Long companySeq, String style, String color, String size) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<String> tagMasterList = rfidTagMasterRepository.findRfidTagList(companySeq, style, color, size);

		List<Map<String, Object>> rfidTagList = new ArrayList<Map<String, Object>>();

		for(String rfid : tagMasterList) {

			Map<String, Object> rfidMap = new HashMap<String, Object>();
			rfidMap.put("rfidTag", rfid);
			rfidTagList.add(rfidMap);

		}

		obj.put("list", rfidTagList);
		return obj;

	}

	//업체별 스타일, 컬러, 사이즈 정보 가져오기
	@Transactional(readOnly=true)
	@Override
	public List<SelectGroupBy> findSkuPerComany(Long companySeq) throws Exception{

		/*Map<String, Object> obj = new HashMap<String, Object>();

		List<Map<String, Object>> skuList = new ArrayList<Map<String, Object>>();

		List<SelectGroupBy> findList = bartagOrderRepository.findSkuPerCompany(companySeq);
		/*
		for(SelectGroupBy sb : findList) {

			Map<String, Object> boMap = new HashMap<String,Object>();

			boMap.put("data", sb.getData());
			boMap.put("flag", sb.getFlag());
			skuList.add(boMap);

		}

		obj.put("content", skuList);
		return findList;*/
		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "WITH tb_sku AS (SELECT style, color, size FROM bartag_order WHERE production_company_seq = " + companySeq + ") " +
				"SELECT style as data, 'style' as flag, style as rank FROM tb_sku GROUP BY style " +
				"UNION SELECT color as data, 'color' as flag, style as rank FROM tb_sku GROUP BY style, color " +
				"UNION SELECT size as data, 'size' as flag, style+color as rank FROM tb_sku GROUP BY style, color, size";

		return template.query(query, new SelectGroupByMapper());

	}

	@Transactional(readOnly = true)
	@Override
	public BartagMinMaxResult findByMinMax(String style, String color, String size) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		StringBuilder query = new StringBuilder();
		query.append("SELECT bm.erp_key, ");
		query.append("       MIN(bm.start_rfid_seq) AS start_rfid_seq, ");
		query.append("       MAX(bm.end_rfid_seq) AS end_rfid_seq ");
		query.append("  FROM bartag_master bm ");
		query.append(" WHERE bm.style = :style ");
		query.append("   AND bm.color = :color ");
		query.append("   AND bm.size = :size ");
		query.append("GROUP BY bm.erp_key ");

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("style", style);
		params.put("color", color);
		params.put("size", size);

		return nameTemplate.queryForObject(query.toString(), params, new BartagMinMaxResultMapper());
	}

}
