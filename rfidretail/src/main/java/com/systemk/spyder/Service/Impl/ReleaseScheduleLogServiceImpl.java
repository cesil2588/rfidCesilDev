package com.systemk.spyder.Service.Impl;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.StoreScheduleCompleteBean;
import com.systemk.spyder.Dto.Response.StoreScheduleResult;
import com.systemk.spyder.Dto.Response.StoreScheduleStyleResult;
import com.systemk.spyder.Dto.Response.StoreScheduleTagResult;
import com.systemk.spyder.Entity.External.Key.RfidLe10IfKey;
import com.systemk.spyder.Entity.External.RfidLe10If;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.External.RfidLe10IfRepository;
import com.systemk.spyder.Repository.Main.*;
import com.systemk.spyder.Repository.Main.Specification.ReleaseScheduleLogSpecification;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreReleaseGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.*;
import com.systemk.spyder.Service.Mapper.BoxCountRowMapper;
import com.systemk.spyder.Service.Mapper.Group.StoreReleaseGroupModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.ResultUtil;
import com.systemk.spyder.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ReleaseScheduleLogServiceImpl implements ReleaseScheduleLogService{

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;

	@Autowired
	private RfidLe10IfRepository rfidLe10IfRepository;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Transactional
	@Override
	public void save(TempDistributionReleaseBox tempReleaseBox, BoxInfo boxInfo, UserInfo userInfo, Long workLine, String orderType) throws Exception{

		// 물류 출고 로그 셋팅
		ReleaseScheduleLog releaseScheduleLog = setReleaseScheduleLog(boxInfo, userInfo, workLine, orderType);

		HashSet<ReleaseScheduleDetailLog> releaseScheduleDetailLogSet = new HashSet<ReleaseScheduleDetailLog>();

		for (TempDistributionReleaseStyle style : tempReleaseBox.getStyleList()) {

			// 물류 출고 detail 로그 설정
			ReleaseScheduleDetailLog releaseScheduleDetailLog = new ReleaseScheduleDetailLog();
			releaseScheduleDetailLog.setBarcode(boxInfo.getBarcode());
			releaseScheduleDetailLog.setReferenceNo(style.getReferenceNo());
			releaseScheduleDetailLog.setStyle(style.getStyle());
			releaseScheduleDetailLog.setColor(style.getColor());
			releaseScheduleDetailLog.setSize(style.getSize());
			releaseScheduleDetailLog.setAmount(style.getReleaseAmount());
			releaseScheduleDetailLog.setRfidYn(style.getRfidYn());
			releaseScheduleDetailLog.setErpKey(style.getErpKey());
			releaseScheduleDetailLog.setErpStoreScheduleSeq(style.getErpStoreScheduleSeq());

			releaseScheduleDetailLogSet.add(releaseScheduleDetailLog);

			HashSet<ReleaseScheduleSubDetailLog> releaseScheduleSubDetailLogSet = new HashSet<ReleaseScheduleSubDetailLog>();

			for (TempDistributionReleaseTag rfid : style.getTagList()) {
				ReleaseScheduleSubDetailLog releaseScheduleSubDetailLog = new ReleaseScheduleSubDetailLog();
				releaseScheduleSubDetailLog.setRfidTag(rfid.getRfidTag());
				releaseScheduleSubDetailLog.setBarcode(rfid.getRfidTag().substring(0, 10).concat(rfid.getRfidTag().substring(27, 32)));

				releaseScheduleSubDetailLogSet.add(releaseScheduleSubDetailLog);
			}

			releaseScheduleDetailLog.setReleaseScheduleSubDetailLog(releaseScheduleSubDetailLogSet);
		}

		releaseScheduleLog.setReleaseScheduleDetailLog(releaseScheduleDetailLogSet);

		releaseScheduleLogRepository.save(releaseScheduleLog);
	}

	private ReleaseScheduleLog setReleaseScheduleLog(BoxInfo boxInfo, UserInfo userInfo, Long workLine, String orderType) throws Exception {

		ReleaseScheduleLog releaseScheduleLog = new ReleaseScheduleLog();
		releaseScheduleLog.setOrderType(orderType);
		releaseScheduleLog.setRegDate(new Date());
		releaseScheduleLog.setRegUserInfo(userInfo);
		releaseScheduleLog.setReleaseYn("Y");
		releaseScheduleLog.setReleaseDate(new Date());
		releaseScheduleLog.setCompleteYn("N");
		releaseScheduleLog.setCompleteBatchYn("N");
		releaseScheduleLog.setBoxInfo(boxInfo);
		releaseScheduleLog.setCreateDate(CalendarUtil.convertFormat("yyyyMMdd"));
		releaseScheduleLog.setWorkLine(workLine);
		releaseScheduleLog.setDisuseYn("N");
		releaseScheduleLog.setErpCompleteYn("N");

		return releaseScheduleLog;
	}

	@Transactional(readOnly = true)
	@Override
	public Long maxWorkLine(String createDate, String orderType) throws Exception {

		ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findTop1ByCreateDateAndOrderTypeOrderByWorkLineDesc(createDate, orderType);

		Long maxWorkLine;

		if(releaseScheduleLog == null){
			maxWorkLine = Long.valueOf(0);
		} else {
			maxWorkLine = releaseScheduleLog.getWorkLine();
		}
		return maxWorkLine;
	}

	/*
	@Transactional(readOnly = true)
	@Override
	public List<StoreReleaseGroupModel> findStoreReleaseGroupList(String startDate, String endDate, Long endCompanySeq, String completeYn, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "rsl.create_date DESC, rsl.work_line");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) rsl.create_date, rsl.work_line, rsl.complete_yn, bi.end_company_seq, ci.name, reg_ui.user_id AS reg_user_id, upd_ui.user_id AS upd_user_id, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.work_line = rsl.work_line ");
		query.append("AND tbi.end_company_seq = bi.end_company_seq ");
		query.append("AND trsl.complete_yn = rsl.complete_yn ");
		query.append(") AS box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.work_line = rsl.work_line ");
		query.append("AND tbi.end_company_seq = bi.end_company_seq ");
		query.append("AND trsl.complete_yn = rsl.complete_yn ");
		query.append(") AS style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN release_schedule_sub_detail_log trssdl ");
		query.append("ON trsdl.release_schedule_detail_log_seq = trssdl.release_schedule_detail_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.work_line = rsl.work_line ");
		query.append("AND tbi.end_company_seq = bi.end_company_seq ");
		query.append("AND trsl.complete_yn = rsl.complete_yn ");
		query.append(") AS tag_count ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("INNER JOIN user_info reg_ui ");
		query.append("ON rsl.reg_user_seq = reg_ui.user_seq ");
		query.append("LEFT JOIN user_info upd_ui ");
		query.append("ON rsl.upd_user_seq = upd_ui.user_seq ");
		query.append("WHERE rsl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND bi.type = '02' ");

		if(endCompanySeq != 0){
			query.append("AND ci.company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}
		if(!completeYn.equals("all")){
			query.append("AND rsl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}
		query.append("GROUP BY rsl.create_date, rsl.work_line, rsl.complete_yn, bi.end_company_seq, ci.name, reg_ui.user_id, upd_ui.user_id ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, work_line, complete_yn, end_company_seq, name, box_count, style_count, tag_count, reg_user_id, upd_user_id ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StoreReleaseGroupModelMapper());
	}
	*/

	@Transactional(readOnly = true)
	@Override
	public Long CountReleaseGroupList(String startDate, String endDate, Long endCompanySeq, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT rsl.create_date, rsl.work_line, bi.end_company_seq ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE rsl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND bi.type = '02' ");

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		query.append("GROUP BY rsl.create_date, rsl.work_line, bi.end_company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogBoxCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rsl.complete_yn = 'N' THEN rsl.complete_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN rsl.complete_yn = 'Y' THEN rsl.complete_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date BETWEEN :startDate AND :endDate ");

			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogStyleCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rsl.complete_yn = 'N' THEN rsl.complete_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN rsl.complete_yn = 'Y'THEN rsl.complete_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date BETWEEN :startDate AND :endDate ");

			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogTagCount(String startDate, String endDate, Long endCompanySeq, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN rsl.complete_yn = 'N' THEN rsdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN rsl.complete_yn = 'Y'THEN rsdl.amount END), 0) AS stat3_amount, ");
		query.append("ISNULL(SUM(rsdl.amount), 0) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date BETWEEN :startDate AND :endDate ");

			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogBoxCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		createDate = CalendarUtil.initStartDate(createDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rsl.complete_yn = 'N' THEN rsl.complete_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN rsl.complete_yn = 'Y' THEN rsl.complete_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date = :createDate ");
			params.put("createDate", createDate);
		}

		if(!completeYn.equals("all")){
			query.append("AND rsl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogStyleCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		createDate = CalendarUtil.initStartDate(createDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rsl.complete_yn = 'N' THEN rsl.complete_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN rsl.complete_yn = 'Y'THEN rsl.complete_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date = :createDate ");
			params.put("createDate", createDate);
		}

		if(!completeYn.equals("all")){
			query.append("AND rsl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());

	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storeReleaseScheduleLogTagCount(String createDate, Long endCompanySeq, String completeYn, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		createDate = CalendarUtil.initStartDate(createDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN rsl.complete_yn = 'N' THEN rsdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN rsl.complete_yn = 'Y'THEN rsdl.amount END), 0) AS stat3_amount, ");
		query.append("ISNULL(SUM(rsdl.amount), 0) AS total_amount ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type = '02' ");

		if(flag.equals("")){
			query.append("AND rsl.create_date = :createDate ");
			params.put("createDate", createDate);
		}

		if(!completeYn.equals("all")){
			query.append("AND rsl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		if(endCompanySeq != 0){
			query.append("AND bi.end_company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ReleaseScheduleLog> findAll(String createDate, Long endCompanySeq, String search, String option, Pageable pageable) throws Exception {

		Page<ReleaseScheduleLog> page = null;

		Specifications<ReleaseScheduleLog> specifications = Specifications.where(ReleaseScheduleLogSpecification.createDateEqual(createDate));

		if(endCompanySeq != 0) {
			specifications = specifications.and(ReleaseScheduleLogSpecification.endCompanySeqEqual(endCompanySeq));
		}

		page = releaseScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ReleaseScheduleLog> findAll(String createDate, Long endCompanySeq, String completeYn, String style, String color, String size, String search, String option, Pageable pageable) throws Exception {

		Page<ReleaseScheduleLog> page = null;

		Specifications<ReleaseScheduleLog> specifications = Specifications.where(ReleaseScheduleLogSpecification.createDateEqual(createDate));

		if(!completeYn.equals("all")){
			specifications = specifications.and(ReleaseScheduleLogSpecification.completeYnEqual(completeYn));
		}

		if(endCompanySeq != 0) {
			specifications = specifications.and(ReleaseScheduleLogSpecification.endCompanySeqEqual(endCompanySeq));
		}

		specifications = specifications.and(ReleaseScheduleLogSpecification.styleSearchEqual(style, color, size));

		page = releaseScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReleaseScheduleLog> findAll(String createDate, String confirmYn, String completeYn) throws Exception {

		createDate = CalendarUtil.initStartDate(createDate);

		Specifications<ReleaseScheduleLog> specifications = Specifications.where(ReleaseScheduleLogSpecification.createDateEqual(createDate));

		if(!completeYn.equals("all")){
			specifications = specifications.and(ReleaseScheduleLogSpecification.completeYnEqual(completeYn));
		}

		return releaseScheduleLogRepository.findAll(specifications);
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreReleaseGroupModel> findStoreReleaseGroupList(String startDate, String endDate, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "rsl.create_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) rsl.create_date, ci.company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT ISNULL(SUM(trsdl.amount), 0) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(trsdl.amount), 0) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_tag_count ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("WHERE rsl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND rsl.release_yn = 'Y' ");

		query.append("GROUP BY rsl.create_date, ci.name, ci.company_seq ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, name, company_seq, stat1_box_count, stat2_box_count, (stat1_box_count + stat2_box_count) AS total_box_count, ");
		query.append("stat1_style_count, stat2_style_count, (stat1_style_count + stat2_style_count) AS total_style_count, ");
		query.append("stat1_tag_count, stat2_tag_count, (stat1_tag_count + stat2_tag_count) AS total_tag_count, ");
		query.append("CAST(CAST(stat2_tag_count AS FLOAT)/CAST((stat1_tag_count + stat2_tag_count) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StoreReleaseGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreReleaseGroupModel> findStoreReleaseGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "rsl.create_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) rsl.create_date, ci.company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT ISNULL(SUM(trsdl.amount), 0) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'N' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(trsdl.amount), 0) ");
		query.append("FROM release_schedule_log trsl ");
		query.append("INNER JOIN release_schedule_detail_log trsdl ");
		query.append("ON trsl.release_schedule_log_seq = trsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON trsl.box_seq = tbi.box_seq ");
		query.append("WHERE trsl.create_date = rsl.create_date ");
		query.append("AND tbi.end_company_seq = ci.company_seq ");
		query.append("AND trsl.complete_yn = 'Y' ");
		query.append("AND trsl.release_yn = 'Y' ");
		query.append(") AS stat2_tag_count ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("WHERE rsl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND rsl.release_yn = 'Y' ");

		if(companySeq != 0){
			query.append("AND bi.end_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY rsl.create_date, ci.name, ci.company_seq ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, name, company_seq, stat1_box_count, stat2_box_count, (stat1_box_count + stat2_box_count) AS total_box_count, ");
		query.append("stat1_style_count, stat2_style_count, (stat1_style_count + stat2_style_count) AS total_style_count, ");
		query.append("stat1_tag_count, stat2_tag_count, (stat1_tag_count + stat2_tag_count) AS total_tag_count, ");
		query.append("CAST(CAST(stat2_tag_count AS FLOAT)/CAST((stat1_tag_count + stat2_tag_count) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StoreReleaseGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findStoreSchedule(String barcode) throws Exception {

		// 바코드(운송장번호 검증)
		if(barcode.equals("")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
		}

		ReleaseScheduleLog releaseSchedule = releaseScheduleLogRepository.findByBoxInfoBarcode(barcode);

		if(releaseSchedule == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
		}

		// 이미 완료된 입고 정보
		if(releaseSchedule.getCompleteYn().equals("Y")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_BOX_MESSAGE);
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, new StoreScheduleResult(releaseSchedule));
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleComplete(List<Map<String, Object>> barcodeList, Long userSeq, String type) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<ReleaseScheduleLog> releaseScheduleLogList = new ArrayList<ReleaseScheduleLog>();

		if(userSeq == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		for(Map<String, Object> barcodeObj : barcodeList){

			String barcode = barcodeObj.get("barcode").toString();

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

			if(boxInfo == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

			ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBarcode(barcode);

			if(releaseScheduleLog == null){
				obj.put("resultCode", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
				return obj;
			}

			if(releaseScheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return obj;
			}

			releaseScheduleLogList.add(releaseScheduleLog);
		}

		for(ReleaseScheduleLog releaseScheduleLog : releaseScheduleLogList) {
			releaseScheduleLog.setCompleteYn("Y");
			releaseScheduleLog.setCompleteDate(new Date());
			releaseScheduleLog.setUpdDate(new Date());
			releaseScheduleLog.setUpdUserInfo(userInfo);

			// ERP I/F를 위한 출고수량, 플래그 저장
			for (ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {
				detailLog.setReleaseAmount(detailLog.getAmount());
				detailLog.setFlag("N");
			}
		}

		// 매장 예정 정보 업데이트
		releaseScheduleLogRepository.save(releaseScheduleLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleComplete(StoreScheduleCompleteBean req) throws Exception {

		// 파라미터 검증
		if(req.getUserSeq() == null || req.getUserSeq() == 0 ||
		   req.getType() == null || req.getType().equals("") ||
		   req.getSchedule() == null || req.getSchedule().getFlag() == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		// 플래그 검증
		for(StoreScheduleStyleResult style : req.getSchedule().getStyleList()) {
			if(style.getFlag() == null) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			}
		}

		UserInfo userInfo = userInfoRepository.findOne(req.getUserSeq());

		// 사용자 정보 검증
		if(userInfo == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBarcode(req.getSchedule().getBarcode());

		// 매장입고예정정보 검증
		if(releaseScheduleLog == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
		}

		// 작업 완료된 박스 여부 검증
		if(releaseScheduleLog.getCompleteYn().equals("Y")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_BOX_MESSAGE);
		}

		// 예외 입고시 필수 파라미터 확인
		if(releaseScheduleLog.getMappingYn().equals("N")) {
			if(req.getSchedule().getStyleList() == null || req.getSchedule().getStyleList().size() == 0) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			}

			for(StoreScheduleStyleResult style : req.getSchedule().getStyleList()) {
				if(style.getRfidYn().equals("Y") && (style.getTagList() == null || style.getTagList().size() == 0)) {
					return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
				}
			}
		}

		// 정상 입고
		if(releaseScheduleLog.getMappingYn().equals("Y")) {

			// 운송장 박스에 추가된 스타일, 태그가 있을 경우 아래 로직 수행
			for(StoreScheduleStyleResult style : req.getSchedule().getStyleList()) {

				// 변경 사항 없이 리딩
				if(style.getFlag().equals("N")) {

					loop:
					for (ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {
						if (style.getStyle().equals(detailLog.getStyle()) &&
							style.getColor().equals(detailLog.getColor()) &&
							style.getSize().equals(detailLog.getSize())) {

							detailLog.setFlag(style.getFlag());
							detailLog.setReleaseAmount(style.getAmount());

							break loop;
						}
					}

				// 신규 스타일
				} else if(style.getFlag().equals("S")) {
					releaseScheduleLog.getReleaseScheduleDetailLog().add(new ReleaseScheduleDetailLog(req.getSchedule().getBarcode(), style));

				// 신규 태그 추가
				} else if(style.getFlag().equals("T")) {

					loop:
					for(ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {
						if (style.getStyle().equals(detailLog.getStyle()) &&
							style.getColor().equals(detailLog.getColor()) &&
							style.getSize().equals(detailLog.getSize())) {

							detailLog.setFlag(style.getFlag());
							detailLog.setReleaseAmount(style.getAmount());

							for(StoreScheduleTagResult tag : style.getTagList()) {
								if(tag.getFlag() != null && tag.getFlag().equals("T")) {
									detailLog.getReleaseScheduleSubDetailLog().add(new ReleaseScheduleSubDetailLog(tag));
								}
							}

							break loop;
						}
					}
				}
			}

		// 예외 입고
		} else {

			// 운송장 박스에 추가된 스타일, 태그가 있을 경우 아래 로직 수행
			for (StoreScheduleStyleResult style : req.getSchedule().getStyleList()) {

				// 변경 사항 없거나 태그가 추가됐을 경우
				if (style.getFlag().equals("N") || style.getFlag().equals("T")) {

					loop:
					for (ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {
						if (style.getStyle().equals(detailLog.getStyle()) &&
							style.getColor().equals(detailLog.getColor()) &&
							style.getSize().equals(detailLog.getSize())) {

							detailLog.setFlag(style.getFlag());
							detailLog.setReleaseAmount(style.getAmount());
							detailLog.getReleaseScheduleSubDetailLog().clear();

							if(detailLog.getRfidYn().equals("Y")) {
								for (StoreScheduleTagResult tag : style.getTagList()) {
									detailLog.getReleaseScheduleSubDetailLog().add(new ReleaseScheduleSubDetailLog(tag));
								}
							}

							break loop;
						}
					}

				// 신규 스타일
				} else if (style.getFlag().equals("S")) {
					releaseScheduleLog.getReleaseScheduleDetailLog().add(new ReleaseScheduleDetailLog(req.getSchedule().getBarcode(), style));
				}
			}
		}

		releaseScheduleLog.setCompleteYn("Y");
		releaseScheduleLog.setCompleteDate(new Date());
		releaseScheduleLog.setUpdDate(new Date());
		releaseScheduleLog.setUpdUserInfo(userInfo);

		// 판매 입고 업데이트
		BoxInfo boxInfo = releaseScheduleLog.getBoxInfo();
		boxInfo.setStat("3", userInfo);

		boxInfoRepository.save(boxInfo);

		// 매장 예정 정보 실적 업데이트
		releaseScheduleLogRepository.save(releaseScheduleLog);

		// 매장 입고 이력 저장
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<>();

		for(ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()){
			detailLog.getReleaseScheduleSubDetailLog()
					 .stream()
					 .map(obj -> rfidTagHistoryList.add(new RfidTagHistory(StringUtil.setRfidTagMaster(obj.getRfidTag()), userInfo, boxInfo, "22")));
		}

		// 속도 개선을 위해 아래 로직 삭제
		// 신규 스타일, 태그 추가
		/*
		List<String> rfidList = storeStorageRfidTagService.existsNotStoreRfidTag(releaseScheduleLog);

		distributionStorageRfidTagService.releaseProcess(rfidList, userInfo, boxInfo, releaseScheduleLog.getReleaseScheduleLogSeq(), "2", "E");

		// 입고 처리
		storeStorageRfidTagService.inspectionBox(boxInfo, userInfo,"2");
		 */

		// ERP Dev Function 소스 생성 후 주석 풀 예정
		List<RfidLe10If> rfidLe10IfList = new ArrayList<RfidLe10If>();

		for(ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {

			// 변경 없음 or 태그 수량 변경시
			if(detailLog.getFlag().equals("N") || detailLog.getFlag().equals("T")) {
				RfidLe10If rfidLe10If = rfidLe10IfRepository.findOne(new RfidLe10IfKey(releaseScheduleLog.getErpReleaseDate(),
						releaseScheduleLog.getBoxInfo().getBarcode(),
						releaseScheduleLog.getErpReleaseSeq(),
						releaseScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode(),
						releaseScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode(),
						detailLog.getStyle(),
						detailLog.getColor(),
						detailLog.getSize()));

				// null 처리
				if(rfidLe10If == null){
					continue;
				}

				rfidLe10If.update(detailLog);
				rfidLe10IfList.add(rfidLe10If);

				// 신규 스타일 추가시
			} else {
				rfidLe10IfList.add(new RfidLe10If(releaseScheduleLog, detailLog, releaseScheduleLog.getErpReleaseSeq()));
			}
		}

		// ERP I/F 테이블 저장
		rfidLe10IfRepository.save(rfidLe10IfList);
		rfidLe10IfRepository.flush();

		// Exception 발생시 트랜잭션 처리 완료
		Map<String, Object> obj = erpService.storeStorageFunctionCall(releaseScheduleLog.getErpReleaseDate(),
				releaseScheduleLog.getErpReleaseSeq(),
				releaseScheduleLog.getBoxInfo().getEndCompanyInfo(),
				releaseScheduleLog.getBoxInfo().getBarcode());

		if(!obj.get("resultCode").toString().equals("0")) {
			// 강제 에러 처리 테스트
			throw new RuntimeException();
		}

		releaseScheduleLog.setCompleteBatchYn("Y");
		releaseScheduleLog.setCompleteBatchDate(new Date());

		// ERP I/F 저장 및 Function 호출 후 저장
		releaseScheduleLogRepository.save(releaseScheduleLog);
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleGroupComplete(List<Map<String, Object>> scheduleGroupList, Long userSeq, String type) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<ReleaseScheduleLog> releaseScheduleLogList = new ArrayList<ReleaseScheduleLog>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();

		if(userSeq == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return obj;
		}

		for(Map<String, Object> tempObj : scheduleGroupList){

			String createDate = tempObj.get("createDate").toString();

			List<ReleaseScheduleLog> tempReleaseScheduleList = findAll(createDate, "Y", "N");

			for(ReleaseScheduleLog scheduleLog : tempReleaseScheduleList){

				String barcode = scheduleLog.getBoxInfo().getBarcode();

				ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBarcode(barcode);

				if(releaseScheduleLog == null){
					obj.put("resultCode", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE);
					obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
					return obj;
				}

				if(releaseScheduleLog.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
					return obj;
				}

				releaseScheduleLogList.add(releaseScheduleLog);
			}
		}

		for(ReleaseScheduleLog releaseScheduleLog : releaseScheduleLogList) {

			// 판매 입고 업데이트
			BoxInfo boxInfo = releaseScheduleLog.getBoxInfo();

			storeStorageRfidTagService.inspectionBox(boxInfo, userInfo,"2");

			boxInfo.setStat("3");
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setCompleteDate(new Date());

			boxList.add(boxInfo);

			releaseScheduleLog.setCompleteYn("Y");
			releaseScheduleLog.setCompleteDate(new Date());
			releaseScheduleLog.setUpdDate(new Date());
			releaseScheduleLog.setUpdUserInfo(userInfo);

			// ERP I/F를 위한 출고수량, 플래그 저장
			for(ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()) {
				detailLog.setReleaseAmount(detailLog.getAmount());
				detailLog.setFlag("N");
			}
		}

		boxInfoRepository.save(boxList);

		// 판매 예정 정보 업데이트
		releaseScheduleLogRepository.save(releaseScheduleLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public void storeScheduleCompleteBatch(ReleaseScheduleLog scheduleLog) throws Exception {

		List<RfidLe10If> rfidLe10IfList = new ArrayList<RfidLe10If>();
		List<ErpStoreSchedule> erpStoreScheduleList = new ArrayList<ErpStoreSchedule>();

		BoxInfo boxInfo = scheduleLog.getBoxInfo();

		UserInfo userInfo = scheduleLog.getUpdUserInfo();

		// 신규 스타일, 신규 태그 물류 > 매장 이동
		List<String> rfidList = storeStorageRfidTagService.existsNotStoreRfidTag(scheduleLog);

		distributionStorageRfidTagService.releaseProcess(rfidList, userInfo, boxInfo, scheduleLog.getReleaseScheduleLogSeq(), "2", "E");

		// 매장 입고 업데이트
		storeStorageRfidTagService.inspectionBox(boxInfo, userInfo, "2");

		boxInfo.setStat("3");
		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setCompleteDate(new Date());

		scheduleLog.setUpdDate(new Date());
		scheduleLog.setUpdUserInfo(userInfo);

		for(ReleaseScheduleDetailLog detailLog : scheduleLog.getReleaseScheduleDetailLog()) {

			ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(detailLog.getErpStoreScheduleSeq());

			if(erpStoreSchedule == null) {
				continue;
			}

			// 완료 실적 적용
			erpStoreSchedule.setCompleteAmount(erpStoreSchedule.getCompleteAmount() + detailLog.getAmount());
			erpStoreSchedule.setUpdDate(new Date());

			erpStoreScheduleList.add(erpStoreSchedule);
		}

		for(ReleaseScheduleDetailLog detailLog : scheduleLog.getReleaseScheduleDetailLog()) {

			// 변경 없음 or 태그 수량 변경시
			if(detailLog.getFlag().equals("N") || detailLog.getFlag().equals("T")) {
				RfidLe10If rfidLe10If = rfidLe10IfRepository.findOne(new RfidLe10IfKey(scheduleLog.getErpReleaseDate(),
																					   	scheduleLog.getBoxInfo().getBarcode(),
																						scheduleLog.getErpReleaseSeq(),
																					   	boxInfo.getEndCompanyInfo().getCustomerCode(),
																					   	boxInfo.getEndCompanyInfo().getCornerCode(),
																					   	detailLog.getStyle(),
																					   	detailLog.getColor(),
																					   	detailLog.getSize()));
				rfidLe10If.update(detailLog);
				rfidLe10IfList.add(rfidLe10If);

			// 신규 스타일 추가시
			} else {
				rfidLe10IfList.add(new RfidLe10If(scheduleLog, detailLog, scheduleLog.getErpReleaseSeq()));
			}
		}

		boxInfoRepository.save(boxInfo);

		// 출고예정정보에도 저장
		erpStoreScheduleRepository.save(erpStoreScheduleList);

		// 매장 예정 정보 업데이트
		releaseScheduleLogRepository.save(scheduleLog);

		// ERP I/F 테이블 저장
		rfidLe10IfRepository.save(rfidLe10IfList);

		// ERP I/F Function 호출
		erpService.storeStorageFunctionCall(scheduleLog.getErpReleaseDate(),
											scheduleLog.getErpReleaseSeq(),
											boxInfo.getEndCompanyInfo(),
											boxInfo.getBarcode());

		releaseScheduleLogRepository.save(scheduleLog);

	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(String createDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT rsdl.style AS data ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("AND rsl.create_date = :createDate ");
		params.put("createDate", createDate);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY rsdl.style ");
		query.append("ORDER BY rsdl.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(String createDate, Long companySeq, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT rsdl.color AS data ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("AND rsl.create_date = :createDate ");
		params.put("createDate", createDate);

		query.append("AND rsdl.style = :style ");
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY rsdl.color ");
		query.append("ORDER BY rsdl.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(String createDate, Long companySeq, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT rsdl.size AS data ");
		query.append("FROM release_schedule_log rsl ");
		query.append("INNER JOIN release_schedule_detail_log rsdl ");
		query.append("ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.end_company_seq = ci.company_seq ");
		query.append("AND rsl.create_date = :createDate ");
		params.put("createDate", createDate);

		query.append("AND rsdl.style = :style ");
		params.put("style", style);
		query.append("AND rsdl.color = :color ");
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY rsdl.size ");
		query.append("ORDER BY rsdl.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

}
