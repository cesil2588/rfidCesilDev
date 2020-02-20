package com.systemk.spyder.Service.Impl;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.*;
import com.systemk.spyder.Dto.Response.InventoryScheduleDownloadResult;
import com.systemk.spyder.Dto.Response.InventoryScheduleResult;
import com.systemk.spyder.Dto.Response.InventoryScheduleStyleResult;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.Main.*;
import com.systemk.spyder.Repository.Main.Specification.DistributionStorageSpecification;
import com.systemk.spyder.Repository.Main.Specification.InventoryScheduleSpecification;
import com.systemk.spyder.Repository.Main.Specification.StoreStorageSpecification;
import com.systemk.spyder.Service.CustomBean.Group.InventoryScheduleGroupModel;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.InventoryScheduleService;
import com.systemk.spyder.Service.Mapper.Group.InventoryScheduleGroupModelMapper;
import com.systemk.spyder.Service.UserNotiService;
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryScheduleServiceImpl implements InventoryScheduleService{

	@Autowired
	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;

	@Autowired
	private ErpService erpService;

	@Transactional(readOnly = true)
	@Override
	public List<InventoryScheduleGroupModel> findGroupList(String startDate, String endDate, Long companySeq, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "ish.create_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ish.create_date, ci.company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'N' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat1_header_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'Y' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat2_header_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'Y' ");
		query.append("AND tish.complete_yn = 'Y' ");
		query.append(") AS stat3_header_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'N' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'Y' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'Y' ");
		query.append("AND tish.complete_yn = 'Y' ");
		query.append(") AS stat3_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("INNER JOIN inventory_schedule_tag tist ");
		query.append("ON tiss.inventory_schedule_style_seq = tist.inventory_schedule_style_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'N' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("INNER JOIN inventory_schedule_tag tist ");
		query.append("ON tiss.inventory_schedule_style_seq = tist.inventory_schedule_style_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tish.confirm_yn = 'Y' ");
		query.append("AND tish.complete_yn = 'N' ");
		query.append(") AS stat2_tag_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("INNER JOIN inventory_schedule_tag tist ");
		query.append("ON tiss.inventory_schedule_style_seq = tist.inventory_schedule_style_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tist.stat != '2' ");
		query.append(") AS stat3_tag_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM inventory_schedule_header tish ");
		query.append("INNER JOIN inventory_schedule_style tiss ");
		query.append("ON tish.inventory_schedule_header_seq = tiss.inventory_schedule_header_seq ");
		query.append("INNER JOIN inventory_schedule_tag tist ");
		query.append("ON tiss.inventory_schedule_style_seq = tist.inventory_schedule_style_seq ");
		query.append("WHERE tish.create_date = ish.create_date ");
		query.append("AND tish.company_seq = ci.company_seq ");
		query.append("AND tist.stat = '2' ");
		query.append(") AS stat4_tag_count ");

		query.append("FROM inventory_schedule_header ish ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ish.company_seq = ci.company_seq ");
		query.append("WHERE ish.create_date BETWEEN :startDate AND :endDate ");

		query.append("GROUP BY ish.create_date, ci.name, ci.company_seq ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, name, company_seq, stat1_header_count, stat2_header_count, stat3_header_count, stat1_style_count, stat2_style_count, stat3_style_count, stat1_tag_count, stat2_tag_count, stat3_tag_count, stat4_tag_count, ");
		query.append("CAST(CAST(stat4_tag_count AS FLOAT)/CAST((stat3_tag_count + stat4_tag_count) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new InventoryScheduleGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountGroupList(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();

		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ish.create_date, ish.company_seq ");
		query.append("FROM inventory_schedule_header ish ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ish.company_seq = ci.company_seq ");

		query.append("WHERE ish.create_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ish.create_date, ish.company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	/**
	 *
	 * @param startDate
	 * @param endDate
	 * @param customerCode
	 * @param confirmYn
	 * @param completeYn
	 * @param disuseYn
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findAll(String startDate, String endDate, String customerCode, String confirmYn, String completeYn, String disuseYn, String type) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Specifications<InventoryScheduleHeader> specifications = Specifications.where(InventoryScheduleSpecification.createDateBetween(startDate, endDate));

		if (!customerCode.equals("")) {
			specifications = specifications.and(InventoryScheduleSpecification.customerCodeEqual(customerCode));
		}

		if (!confirmYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.confirmYnEqual(confirmYn));
		}

		if (!completeYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.completeYnEqual(completeYn));
		}

		if (!disuseYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.disuseYnEqual(disuseYn));
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, inventoryScheduleHeaderRepository.findAll(specifications)
																															  .stream()
				 																											  .map(obj -> new InventoryScheduleResult(obj, false))
																															  .collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<InventoryScheduleHeader> findAll(String startDate, String endDate, Long companySeq, String confirmYn, String completeYn, String disuseYn, String type, Pageable pageable) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Specifications<InventoryScheduleHeader> specifications = Specifications.where(InventoryScheduleSpecification.createDateBetween(startDate, endDate));

		if (companySeq != 0) {
			specifications = specifications.and(InventoryScheduleSpecification.companySeqEqual(companySeq));
		}

		if (!confirmYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.confirmYnEqual(confirmYn));
		}

		if (!completeYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.completeYnEqual(completeYn));
		}

		if (!disuseYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.disuseYnEqual(disuseYn));
		}

		return inventoryScheduleHeaderRepository.findAll(specifications, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<InventoryScheduleHeader> findAll(String creatDate, Long companySeq, String confirmYn, String completeYn, String disuseYn, Pageable pageable) throws Exception {

		Page<InventoryScheduleHeader> page = null;

		Specifications<InventoryScheduleHeader> specifications = Specifications.where(InventoryScheduleSpecification.createDateEqual(creatDate));

		if (companySeq != 0) {
			specifications = specifications.and(InventoryScheduleSpecification.companySeqEqual(companySeq));
		}

		if (!confirmYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.confirmYnEqual(confirmYn));
		}

		if (!completeYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.completeYnEqual(completeYn));
		}

		if (!disuseYn.equals("all")) {
			specifications = specifications.and(InventoryScheduleSpecification.disuseYnEqual(disuseYn));
		}

		page = inventoryScheduleHeaderRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> detail(Long seq) throws Exception {

		InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(seq);

		if (inventorySchedule == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
		}

		/*
		 * 작업 완료된 내역이 있으나 현 PDA에서 작업 안한 경우
		 * TODO 현재 적용 안함
		 *
		if (inventorySchedule.getFlag().equals("Y") &&
			!inventorySchedule.getPdaKey().equals(result.getPdaKey()) &&
			!inventorySchedule.getUpdDate().equals(result.getUpdDate())) {
			return ResultUtil.setCommonResult("M", ApiResultConstans.ANOTHER_WORK_INVENTORY_SCHEDULE_MESSAGE);
		}
		*/

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, new InventoryScheduleResult(inventorySchedule, true));
	}

	@Transactional
	@Override
	public Map<String, Object> save(InventoryScheduleBean inventoryScheduleBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(inventoryScheduleBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(inventoryScheduleBean.getCompanySeq());

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		Specifications<InventoryScheduleHeader> specifications = Specifications.where(InventoryScheduleSpecification.companySeqEqual(companyInfo.getCompanySeq()))
																			   .and(InventoryScheduleSpecification.confirmYnEqual("N"));

		Long checkCount = inventoryScheduleHeaderRepository.count(specifications);

		if(checkCount > 0){
			obj.put("resultCode", ApiResultConstans.FIND_NOT_CONFIRM_INVENTORY_SCHEDULE);
			obj.put("resultMessage", ApiResultConstans.FIND_NOT_CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
			return obj;
		}

		Long workLine = maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), userInfo.getCompanyInfo().getCompanySeq());

		// 재고조사 스케줄 저장
		save(inventoryScheduleBean, companyInfo, userInfo, ++workLine);

		// 알림 추가
		userNotiService.save("RFID 재고조사 작업이 저장되었습니다.", userInfo, inventoryScheduleBean.getCompanyType(), Long.valueOf(0));

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> update(InventoryScheduleBean inventoryScheduleBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(inventoryScheduleBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(inventoryScheduleBean.getCompanySeq());

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		if (inventoryScheduleBean.getInventoryScheduleHeaderSeq() == null){
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(inventoryScheduleBean.getInventoryScheduleHeaderSeq());

		if (inventorySchedule == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
			return obj;
		}

		if (inventorySchedule.getConfirmYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE);
			obj.put("resultMessage", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
			return obj;
		}

		if (inventorySchedule.getCompleteYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
			return obj;
		}

		if (inventorySchedule.getDisuseYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE);
			obj.put("resultMessage", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
			return obj;
		}

		update(inventoryScheduleBean, inventorySchedule);

		// 알림 수정
		userNotiService.save("RFID 재고조사 작업이 수정되었습니다.", userInfo, "store", Long.valueOf(0));

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> delete(InventoryScheduleListBean inventoryScheduleListBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();

		UserInfo userInfo = userInfoRepository.findOne(inventoryScheduleListBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(inventoryScheduleListBean.getCompanySeq());

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		for(InventoryScheduleBean inventoryScheduleBean : inventoryScheduleListBean.getInventoryScheduleHeaderList()){

			if (inventoryScheduleBean.getInventoryScheduleHeaderSeq() == null){
				obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
				obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
				return obj;
			}

			InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(inventoryScheduleBean.getInventoryScheduleHeaderSeq());

			if (inventorySchedule == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getDisuseYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			scheduleList.add(inventorySchedule);
		}

		inventoryScheduleHeaderRepository.delete(scheduleList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> complete(InventoryScheduleListBean inventoryScheduleListBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();

		UserInfo userInfo = userInfoRepository.findOne(inventoryScheduleListBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(inventoryScheduleListBean.getCompanySeq());

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		for(InventoryScheduleBean inventoryScheduleBean : inventoryScheduleListBean.getInventoryScheduleHeaderList()){

			if (inventoryScheduleBean.getInventoryScheduleHeaderSeq() == null){
				obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
				obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
				return obj;
			}

			InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(inventoryScheduleBean.getInventoryScheduleHeaderSeq());

			if (inventorySchedule == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			if (inventorySchedule.getDisuseYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE);
				obj.put("resultMessage", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
				return obj;
			}

			inventorySchedule.setConfirmYn("Y");
			inventorySchedule.setConfirmDate(new Date());
			inventorySchedule.setUpdUserInfo(userInfo);
			inventorySchedule.setUpdDate(new Date());

			scheduleList.add(inventorySchedule);
		}

		inventoryScheduleHeaderRepository.save(scheduleList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> storeStorageDetail(Long userSeq, Long companySeq) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(companySeq);

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		Specifications<StoreStorage> specifications = Specifications.where(StoreStorageSpecification.companySeqEqual(companySeq))
																	.and(StoreStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)))
																	.and(StoreStorageSpecification.totalAmountGreaterThan(Long.valueOf(0)));

		List<StoreStorage> storeStorageList = storeStorageRepository.findAll(specifications);

		if(storeStorageList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_BARTAG_MESSAGE);
			return obj;
		}

		List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

		for(StoreStorage storeStorage : storeStorageList){

			Map<String, Object> styleObj = new LinkedHashMap<String, Object>();

			styleObj.put("storeStorageSeq", storeStorage.getStoreStorageSeq());
			styleObj.put("productYy", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getProductYy());
			styleObj.put("productSeason", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getProductSeason());
			styleObj.put("style", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
			styleObj.put("color", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
			styleObj.put("size", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
			styleObj.put("erpKey", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
			styleObj.put("orderDegree", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getOrderDegree());
			styleObj.put("additionOrderDegree", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getAdditionOrderDegree());
			styleObj.put("startRfidSeq", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getStartRfidSeq());
			styleObj.put("endRfidSeq", storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getEndRfidSeq());
			styleObj.put("totalAmount", storeStorage.getTotalAmount());
			styleObj.put("stat1Amount", storeStorage.getStat1Amount());
			styleObj.put("stat2Amount", storeStorage.getStat2Amount());
			styleObj.put("stat3Amount", storeStorage.getStat3Amount());
			styleObj.put("stat4Amount", storeStorage.getStat4Amount());
			styleObj.put("stat5Amount", storeStorage.getStat5Amount());
			styleObj.put("stat6Amount", storeStorage.getStat6Amount());
			styleObj.put("stat7Amount", storeStorage.getStat7Amount());

			styleList.add(styleObj);
		}

		obj.put("styleList", styleList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> distributionStorageDetail(Long userSeq, Long companySeq) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo companyInfo = companyInfoRepository.findOne(companySeq);

		// 업체 정보 확인
		if (companyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_COMPANY_MESSAGE);
			return obj;
		}

		Specifications<DistributionStorage> specifications = Specifications.where(DistributionStorageSpecification.companySeqEqual(companySeq))
																	.and(DistributionStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)))
																	.and(DistributionStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)));

		List<DistributionStorage> distributionStorageList = distributionStorageRepository.findAll(specifications);

		if(distributionStorageList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_BARTAG_MESSAGE);
			return obj;
		}

		List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

		for(DistributionStorage distributionStorage : distributionStorageList){

			Map<String, Object> styleObj = new LinkedHashMap<String, Object>();

			styleObj.put("distributionStorageSeq", distributionStorage.getDistributionStorageSeq());
			styleObj.put("productYy", distributionStorage.getProductionStorage().getBartagMaster().getProductYy());
			styleObj.put("productSeason", distributionStorage.getProductionStorage().getBartagMaster().getProductSeason());
			styleObj.put("style", distributionStorage.getProductionStorage().getBartagMaster().getStyle());
			styleObj.put("color", distributionStorage.getProductionStorage().getBartagMaster().getColor());
			styleObj.put("size", distributionStorage.getProductionStorage().getBartagMaster().getSize());
			styleObj.put("erpKey", distributionStorage.getProductionStorage().getBartagMaster().getErpKey());
			styleObj.put("orderDegree", distributionStorage.getProductionStorage().getBartagMaster().getOrderDegree());
			styleObj.put("additionOrderDegree", distributionStorage.getProductionStorage().getBartagMaster().getAdditionOrderDegree());
			styleObj.put("startRfidSeq", distributionStorage.getProductionStorage().getBartagMaster().getStartRfidSeq());
			styleObj.put("endRfidSeq", distributionStorage.getProductionStorage().getBartagMaster().getEndRfidSeq());
			styleObj.put("totalAmount", distributionStorage.getTotalAmount());
			styleObj.put("stat1Amount", distributionStorage.getStat1Amount());
			styleObj.put("stat2Amount", distributionStorage.getStat2Amount());
			styleObj.put("stat3Amount", distributionStorage.getStat3Amount());
			styleObj.put("stat4Amount", distributionStorage.getStat4Amount());
			styleObj.put("stat5Amount", distributionStorage.getStat5Amount());
			styleObj.put("stat6Amount", distributionStorage.getStat6Amount());
			styleObj.put("stat7Amount", distributionStorage.getStat7Amount());

			styleList.add(styleObj);
		}

		obj.put("styleList", styleList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public List<InventoryScheduleHeader> save(InventoryScheduleBean inventoryScheduleBean, CompanyInfo companyInfo, UserInfo userInfo, Long workLine) throws Exception {

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();

		// 재고조사스케줄 셋팅
		InventoryScheduleHeader schedule = setInventoryScheduleHeader(inventoryScheduleBean, companyInfo, userInfo, workLine);

		HashSet<InventoryScheduleStyle> styleSet = new HashSet<InventoryScheduleStyle>();

		for (StyleBean style : inventoryScheduleBean.getStyleList()) {

			// 재고조사스케줄 스타일 셋팅
			InventoryScheduleStyle scheduleStyle = new InventoryScheduleStyle();

			if(inventoryScheduleBean.getCompanyType().equals("store")) {

				StoreStorage storeStorage = storeStorageRepository.findOne(style.getStorageSeq());

				scheduleStyle.setStyle(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
				scheduleStyle.setColor(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
				scheduleStyle.setSize(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
				scheduleStyle.setErpKey(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
				scheduleStyle.setAmount(storeStorage.getStat2Amount());

			} else if (inventoryScheduleBean.getCompanyType().equals("distribution")){

				DistributionStorage distributionStorage = distributionStorageRepository.findOne(style.getStorageSeq());

				scheduleStyle.setStyle(distributionStorage.getProductionStorage().getBartagMaster().getStyle());
				scheduleStyle.setColor(distributionStorage.getProductionStorage().getBartagMaster().getColor());
				scheduleStyle.setSize(distributionStorage.getProductionStorage().getBartagMaster().getSize());
				scheduleStyle.setErpKey(distributionStorage.getProductionStorage().getBartagMaster().getErpKey());
				scheduleStyle.setAmount(distributionStorage.getStat2Amount());

			}

			scheduleStyle.setCompleteAmount(style.getAmount());
			scheduleStyle.setDisuseAmount(Long.valueOf(0));

			styleSet.add(scheduleStyle);

			HashSet<InventoryScheduleTag> tagSet = new HashSet<InventoryScheduleTag>();

			for (RfidTagBean rfid : style.getRfidTagList()) {

				InventoryScheduleTag scheduleTag = new InventoryScheduleTag();

				if(rfid.getRfidTag() != null){

					RfidTagMaster tempRfidTag = StringUtil.setRfidTagMaster(rfid.getRfidTag());

					scheduleTag.setRfidTag(rfid.getRfidTag());
					scheduleTag.setBarcode(tempRfidTag.getErpKey() + tempRfidTag.getRfidSeq());

				} else {

					RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByBarcodeAndStat(rfid.getBarcode(), "1");

					if(rfidTagStatus == null){
						continue;
					}

					scheduleTag.setRfidTag(rfidTagStatus.getRfidTag());
					scheduleTag.setBarcode(rfid.getBarcode());
				}

				tagSet.add(scheduleTag);
			}

			scheduleStyle.setRfidTagList(tagSet);
		}

		schedule.setStyleList(styleSet);

		scheduleList.add(schedule);

		return inventoryScheduleHeaderRepository.save(scheduleList);
	}

	@Transactional
	public List<InventoryScheduleHeader> update(InventoryScheduleBean inventoryScheduleBean, InventoryScheduleHeader schedule) throws Exception {

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();

		Set<InventoryScheduleStyle> inventoryScheduleStyleSet = new HashSet<InventoryScheduleStyle>();

		for (StyleBean style : inventoryScheduleBean.getStyleList()) {

			// 재고조사스케줄 스타일 셋팅
			InventoryScheduleStyle scheduleStyle = new InventoryScheduleStyle();

			if (inventoryScheduleBean.getCompanyType().equals("store")) {

				StoreStorage storeStorage = storeStorageRepository.findOne(style.getStorageSeq());

				scheduleStyle.setStyle(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
				scheduleStyle.setColor(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
				scheduleStyle.setSize(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
				scheduleStyle.setErpKey(storeStorage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
				scheduleStyle.setAmount(storeStorage.getStat2Amount());

			} else if (inventoryScheduleBean.getCompanyType().equals("distribution")) {

				DistributionStorage distributionStorage = distributionStorageRepository.findOne(style.getStorageSeq());

				scheduleStyle.setStyle(distributionStorage.getProductionStorage().getBartagMaster().getStyle());
				scheduleStyle.setColor(distributionStorage.getProductionStorage().getBartagMaster().getColor());
				scheduleStyle.setSize(distributionStorage.getProductionStorage().getBartagMaster().getSize());
				scheduleStyle.setErpKey(distributionStorage.getProductionStorage().getBartagMaster().getErpKey());
				scheduleStyle.setAmount(distributionStorage.getStat2Amount());

			}

			scheduleStyle.setCompleteAmount(style.getAmount());
			scheduleStyle.setDisuseAmount(Long.valueOf(0));

			HashSet<InventoryScheduleTag> tagSet = new HashSet<InventoryScheduleTag>();

			for (RfidTagBean rfid : style.getRfidTagList()) {

				InventoryScheduleTag scheduleTag = new InventoryScheduleTag();

				if(rfid.getRfidTag() != null){

					RfidTagMaster tempRfidTag = StringUtil.setRfidTagMaster(rfid.getRfidTag());

					scheduleTag.setRfidTag(rfid.getRfidTag());
					scheduleTag.setBarcode(tempRfidTag.getErpKey() + tempRfidTag.getRfidSeq());

				} else {

					RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByBarcodeAndStat(rfid.getBarcode(), "1");

					if(rfidTagStatus == null){
						continue;
					}

					scheduleTag.setRfidTag(rfidTagStatus.getRfidTag());
					scheduleTag.setBarcode(rfid.getBarcode());
				}

				tagSet.add(scheduleTag);
			}

			scheduleStyle.setRfidTagList(tagSet);

			inventoryScheduleStyleSet.add(scheduleStyle);
		}

		schedule.getStyleList().clear();
		schedule.getStyleList().addAll(inventoryScheduleStyleSet);

		scheduleList.add(schedule);

		return inventoryScheduleHeaderRepository.save(scheduleList);

	}

	public InventoryScheduleHeader setInventoryScheduleHeader(InventoryScheduleBean inventoryScheduleBean, CompanyInfo companyInfo, UserInfo userInfo, Long workLine) throws Exception {

		InventoryScheduleHeader obj = new InventoryScheduleHeader();

		obj.setCompanyInfo(companyInfo);
		obj.setCompanyType(inventoryScheduleBean.getCompanyType());
		obj.setRegDate(new Date());
		obj.setRegUserInfo(userInfo);
		obj.setConfirmYn("N");
		obj.setCompleteYn("N");
		obj.setBatchYn("N");
		obj.setExplanatory(inventoryScheduleBean.getExplanatory());
		obj.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		obj.setWorkLine(workLine);
		obj.setDisuseYn("N");

		return obj;
	};

	@Transactional(readOnly = true)
	@Override
	public Long maxWorkLine(String createDate, Long companySeq) throws Exception {

		InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findTop1ByCreateDateAndCompanyInfoCompanySeqOrderByWorkLineDesc(createDate, companySeq);

		Long maxWorkLine;

		if (inventorySchedule == null) {
			maxWorkLine = Long.valueOf(0);
		} else {
			maxWorkLine = inventorySchedule.getWorkLine();
		}
		return maxWorkLine;
	}

	@Transactional
	@Override
	public Map<String, Object> confirm(InventoryScheduleHeaderBean req) throws Exception {

		List<InventoryScheduleHeader> scheduleList = new ArrayList<>();

		UserInfo userInfo = userInfoRepository.findOne(req.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		if(req.getContent() == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		for(InventoryScheduleResult result : req.getContent()){

			// Seq 확인
			if (result.getInventoryScheduleSeq() == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			}

			InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(result.getInventoryScheduleSeq());

			// 재고조사 목록 없을시 에러
			if (inventorySchedule == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 작업 안된 재고조사
			if (inventorySchedule.getFlag().equals("N")) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_WORK_INVENTORY_SCHEDULE_MESSAGE);
			}

			/*
			// 확정된 재고조사
			if (inventorySchedule.getConfirmYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 완료된 재고조사
			if (inventorySchedule.getCompleteYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
			}
			 */

			// 폐기된 재고조사
			if (inventorySchedule.getDisuseYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
			}

			scheduleList.add(inventorySchedule);
		}

		// 데이터 셋팅
		for(InventoryScheduleHeader header : scheduleList){
			header.setConfirmYn("Y");
			header.setConfirmDate(new Date());
			header.setCompleteYn("Y");
			header.setCompleteDate(new Date());
			header.setUpdUserInfo(userInfo);
			header.setUpdDate(new Date());
		}

		inventoryScheduleHeaderRepository.save(scheduleList);

		// 동일한 작업 내역 ERP 실적에 있을시 삭제하고 insert
		erpService.saveStoreInventory(scheduleList, userInfo);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> init(InventoryScheduleHeaderBean req) throws Exception {

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();

		UserInfo userInfo = userInfoRepository.findOne(req.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		if(req.getContent() == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		for(InventoryScheduleResult result : req.getContent()){

			// Seq 확인
			if (result.getInventoryScheduleSeq() == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			}

			InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(result.getInventoryScheduleSeq());

			// 재고조사 목록 없을시 에러
			if (inventorySchedule == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 작업 완료 안됐을시 에러
			if (inventorySchedule.getFlag().equals("N")) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_WORK_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 확정된 재고조사
			if (inventorySchedule.getConfirmYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
			}

			// ERP 전송된 재고조사
			if (inventorySchedule.getCompleteYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 폐기된 재고조사
			if (inventorySchedule.getDisuseYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
			}

			scheduleList.add(inventorySchedule);
		}

		// 초기화 작업
		for(InventoryScheduleHeader header : scheduleList){
			header.init(userInfo);
		}

		inventoryScheduleHeaderRepository.save(scheduleList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> download(InventoryScheduleHeaderBean req) throws Exception {

		List<InventoryScheduleHeader> scheduleList = new ArrayList<InventoryScheduleHeader>();
		List<InventoryScheduleDownloadResult> downloadList = new ArrayList<InventoryScheduleDownloadResult>();

		UserInfo userInfo = userInfoRepository.findOne(req.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		if(req.getContent() == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		for(InventoryScheduleResult result : req.getContent()){

			// Seq 확인
			if (result.getInventoryScheduleSeq() == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			}

			InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(result.getInventoryScheduleSeq());

			// 재고조사 목록 없을시 에러
			if (inventorySchedule == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 작업 완료 안됐을시 에러
			if (inventorySchedule.getFlag().equals("N")) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_WORK_INVENTORY_SCHEDULE_MESSAGE);
			}

			// 폐기된 재고조사
			if (inventorySchedule.getDisuseYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
			}

			scheduleList.add(inventorySchedule);
		}

		// DTO에 삽입
		for (InventoryScheduleHeader header : scheduleList) {
			downloadList.addAll(header.getStyleList()
									  .stream()
									  .map(style -> new InventoryScheduleDownloadResult(header, style))
									  .collect(Collectors.toList()));
		}

		// List Sort 처리 로직
		Comparator<InventoryScheduleDownloadResult> comparator = Comparator.comparing(InventoryScheduleDownloadResult::getCreateDate)
																			.reversed()
																			.thenComparing(InventoryScheduleDownloadResult::getStyle)
																			.thenComparing(InventoryScheduleDownloadResult::getColor)
																			.thenComparing(InventoryScheduleDownloadResult::getSize);

		Collections.sort(downloadList, comparator);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, downloadList);
	}

	@Transactional
	@Override
	public Map<String, Object> save(InventoryScheduleDetailBean req) throws Exception {

		UserInfo userInfo = userInfoRepository.findOne(req.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		// Seq 확인
		InventoryScheduleResult result = req.getContent();

		if (result == null || result.getInventoryScheduleSeq() == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		InventoryScheduleHeader inventorySchedule = inventoryScheduleHeaderRepository.findOne(result.getInventoryScheduleSeq());

		// 재고조사 목록 없을시 에러
		if (inventorySchedule == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_INVENTORY_SCHEDULE_MESSAGE);
		}

		// 작업 여부가 없는 재고조사에 초기화된 작업 내역 전송시 에러
		if(inventorySchedule.getFlag().equals("N") && result.getFlag().equals("N")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_WORK_SAME_INVENTORY_SCHEDULE_MESSAGE);
		}

		/*
		 * 작업 완료된 내역이 있으나 현 PDA에서 작업 안한 경우
		 * TODO 현재 적용 안함
		 *
		if (inventorySchedule.getFlag().equals("Y") &&
			!inventorySchedule.getPdaKey().equals(result.getPdaKey()) &&
			!inventorySchedule.getUpdDate().equals(result.getUpdDate())) {
			return ResultUtil.setCommonResult("M", ApiResultConstans.ANOTHER_WORK_INVENTORY_SCHEDULE_MESSAGE);
		}
		*/

		// 확정된 재고조사
		/*
		if (inventorySchedule.getConfirmYn().equals("Y")) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.CONFIRM_INVENTORY_SCHEDULE_MESSAGE);
		}

		// 완료된 재고조사
		if (inventorySchedule.getCompleteYn().equals("Y")) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_INVENTORY_SCHEDULE_MESSAGE);
		}
		 */

		// 폐기된 재고조사
		if (inventorySchedule.getDisuseYn().equals("Y")) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.DISUSE_INVENTORY_SCHEDULE_MESSAGE);
		}

		// DTO to Entity 삽입
		inventorySchedule.copyData(result);

		inventoryScheduleHeaderRepository.save(inventorySchedule);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}
}
