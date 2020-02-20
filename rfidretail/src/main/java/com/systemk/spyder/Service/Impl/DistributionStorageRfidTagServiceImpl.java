package com.systemk.spyder.Service.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.BoxBean;
import com.systemk.spyder.Dto.Request.BoxTagListBean;
import com.systemk.spyder.Dto.Request.DistributionReleaseCompleteBean;
import com.systemk.spyder.Dto.Request.ReferenceBean;
import com.systemk.spyder.Dto.Request.ReferenceBoxListBean;
import com.systemk.spyder.Dto.Request.ReferenceStyleListBean;
import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Dto.Request.StyleBean;
import com.systemk.spyder.Dto.Response.BartagMinMaxResult;
import com.systemk.spyder.Dto.Response.ReleaseScheduleResult;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseStyle;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.TempDistributionReleaseBoxRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.DistributionStorageRfidTagSpecification;
import com.systemk.spyder.Repository.Main.Specification.DistributionStorageSpecification;
import com.systemk.spyder.Repository.Main.Specification.ErpStoreScheduleSpecification;
import com.systemk.spyder.Repository.Main.Specification.ProductionStorageSpecification;
import com.systemk.spyder.Repository.Main.Specification.StoreStorageSpecification;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.ProductionStorageLogService;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.StoreStorageLogService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.Mapper.BoxCountAllRowMapper;
import com.systemk.spyder.Service.Mapper.RfidCountRowMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class DistributionStorageRfidTagServiceImpl implements DistributionStorageRfidTagService{

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private StoreStorageLogService storeStorageLogService;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private ReleaseScheduleLogService releaseScheduleLogService;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<DistributionStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception {

		Page<DistributionStorageRfidTag> page = null;

		Specifications<DistributionStorageRfidTag> specifications = Specifications.where(DistributionStorageRfidTagSpecification.distributionStorageSeqEqual(seq));

		if(!stat.equals("all")){
			specifications = specifications.and(DistributionStorageRfidTagSpecification.statEqual(stat));
		}

		if(!search.equals("") && option.equals("rfidTag")){
			specifications = specifications.and(DistributionStorageRfidTagSpecification.rfidTagContaining(search));
		}

		page = distributionStorageRfidTagRepository.findAll(specifications, pageable);


		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<DistributionStorageRfidTag> findStat(Long seq, String stat, Pageable pageable) throws Exception {
		return distributionStorageRfidTagRepository.findByDistributionStorageSeqAndStat(seq, stat, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<DistributionStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception {

		Page<DistributionStorageRfidTag> page = null;

		Specifications<DistributionStorageRfidTag> specifications = Specifications.where(DistributionStorageRfidTagSpecification.distributionStorageSeqEqual(seq));

		specifications = specifications.and(DistributionStorageRfidTagSpecification.startEndRfidSeqBetween(startRfidSeq, endRfidSeq));

		page = distributionStorageRfidTagRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional
	@Override
	public void inspectionAll(Long distributionStorageSeq) {
		// TODO Auto-generated method stub

	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(Long distributionStorageSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("distributionStorageSeq", distributionStorageSeq);

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '1' THEN stat END) stat1_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '2' THEN stat END) stat2_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '3' THEN stat END) stat3_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '4' THEN stat END) stat4_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '5' THEN stat END) stat5_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '6' THEN stat END) stat6_amount, ");
		query.append("COUNT(CASE WHEN distribution_storage_rfid_tag.stat = '7' THEN stat END) stat7_amount ");
		query.append("FROM distribution_storage_rfid_tag ");
		query.append("WHERE distribution_storage_seq = :distributionStorageSeq ");

		return nameTemplate.queryForObject(query.toString(), params, new RfidCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN b.stat = '1' THEN b.stat END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN b.stat = '2' THEN b.stat END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN b.stat = '3' THEN b.stat END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM distribution_storage_rfid_tag d ");
		query.append("INNER JOIN box_info b ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("WHERE b.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", start);
		params.put("endDate", end);

		if(companySeq != 0){
			query.append("AND start_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountAllRowMapper());
	}

	@Transactional
	@Override
	public Map<String, Object> inspectionBox(BoxInfo boxInfo, UserInfo userInfo, String type) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> distributionStorageSeqList = new ArrayList<Long>();

		List<DistributionStorageRfidTag> distributionStorageRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

		ArrayList<DistributionStorageRfidTag> tempRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<RfidTagHistory> tempRfidTagHistoryList = new ArrayList<RfidTagHistory>();

		for(DistributionStorageRfidTag tag : distributionStorageRfidTagList){

			if(tag.getStat().equals("7")){
				obj.put("resultCode", ApiResultConstans.INCLUDE_DISUSE_RFID_TAG);
				obj.put("resultMessage", ApiResultConstans.INCLUDE_DISUSE_RFID_TAG_MESSAGE);
				return obj;
			}

			if(!tag.getStat().equals("1")){
				obj.put("resultCode", ApiResultConstans.NOT_VALID_RFID_TAG);
				obj.put("resultMessage", ApiResultConstans.NOT_VALID_RFID_TAG_MESSAGE);
				return obj;
			}
		}

		for(DistributionStorageRfidTag tag : distributionStorageRfidTagList){

			tag.setStat("2");
			tag.setUpdUserInfo(userInfo);
			tag.setUpdDate(new Date());

			tempRfidTagList.add(tag);

			// 물류 입고완료 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("12");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(boxInfo.getEndCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			tempRfidTagHistoryList.add(rfidTagHistory);

			distributionStorageSeqList.add(tag.getDistributionStorageSeq());
		}

		distributionStorageRfidTagRepository.save(tempRfidTagList);
		rfidTagHistoryRepository.save(tempRfidTagHistoryList);

//		distributionStorageRfidTagRepository.flush();

		distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "2", type);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public List<Long> inspectionBoxBatch(BoxInfo boxInfo, UserInfo userInfo) throws Exception {

		List<Long> distributionStorageSeqList = new ArrayList<Long>();

		List<DistributionStorageRfidTag> distributionStorageRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

		ArrayList<RfidTagHistory> tempRfidTagHistoryList = new ArrayList<RfidTagHistory>();

		distributionStorageRfidTagRepository.updateBoxMappingTag(boxInfo.getBoxSeq(), "2", userInfo.getUserSeq());

		for(DistributionStorageRfidTag tag : distributionStorageRfidTagList){

			// 물류 입고완료 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("12");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(boxInfo.getEndCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			tempRfidTagHistoryList.add(rfidTagHistory);

			distributionStorageSeqList.add(tag.getDistributionStorageSeq());
		}

		rfidTagHistoryRepository.save(tempRfidTagHistoryList);

		return distributionStorageSeqList;
	}

	@Transactional
	@Override
	public List<Long> inspectionBoxBatchTest(BoxInfo boxInfo, UserInfo userInfo) throws Exception {

		List<Long> distributionStorageSeqList = new ArrayList<Long>();

		Long checkCount = distributionStorageRfidTagRepository.countByBoxMappingTag(boxInfo.getBoxSeq(), "1");

		if(checkCount > 0) {

			distributionStorageRfidTagRepository.updateBoxMappingTag(boxInfo.getBoxSeq(), "2", userInfo.getUserSeq());

			List<DistributionStorageRfidTag> distributionStorageRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			ArrayList<RfidTagHistory> tempRfidTagHistoryList = new ArrayList<RfidTagHistory>();

			for(DistributionStorageRfidTag tag : distributionStorageRfidTagList){

				// 물류 입고완료 태그 히스토리 저장
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(tag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("12");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(boxInfo.getEndCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				tempRfidTagHistoryList.add(rfidTagHistory);

				distributionStorageSeqList.add(tag.getDistributionStorageSeq());
			}

			rfidTagHistoryRepository.save(tempRfidTagHistoryList);
		}

		return distributionStorageSeqList;
	}

	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> releaseComplete(DistributionReleaseCompleteBean releaseCompleteBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(releaseCompleteBean.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		BoxBean requestBox = releaseCompleteBean.getBoxInfo();

		if(requestBox == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		// 이미 송장번호 조회시 검증하니 빼는것은?
		/*
		if(boxInfoRepository.existsByBarcode(requestBox.getBarcode())){
			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);
			return obj;
		}
		*/

		// WMS 작업 라인 체크
		TempDistributionReleaseBox checkTempReleaseBox = tempDistributionReleaseBoxRepository.findByBarcode(requestBox.getBarcode());

		if(checkTempReleaseBox != null) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WMS_BARCODE);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WMS_BARCODE_MESSAGE);
			return obj;
		}

		Set<TempDistributionReleaseStyle> styleList = new HashSet<TempDistributionReleaseStyle>();
		List<String> vaildTagList = new ArrayList<String>();

		// 스타일, 태그 업데이트
		for (StyleBean requestStyle : requestBox.getStyleList()) {

			TempDistributionReleaseStyle style = new TempDistributionReleaseStyle();
			style.setStyle(requestStyle.getStyle());
			style.setColor(requestStyle.getColor());
			style.setSize(requestStyle.getSize());
			style.setReferenceNo(requestStyle.getReferenceNo());
			style.setErpKey(requestStyle.getRfidYn().equals("Y") ? requestStyle.getErpKey() : "-");
			style.setReleaseAmount(requestStyle.getRfidYn().equals("Y") ? Long.valueOf(requestStyle.getRfidTagList().size()) : requestStyle.getCount());
			style.setRfidYn(requestStyle.getRfidYn().equals("Y") ? requestStyle.getRfidYn() : "N");

			if(requestStyle.getRfidYn().equals("Y")) {

				Set<TempDistributionReleaseTag> tagList = new HashSet<TempDistributionReleaseTag>();

				for (RfidTagBean rfidTag : requestStyle.getRfidTagList()) {
					TempDistributionReleaseTag tag = new TempDistributionReleaseTag();
					tag.setRfidTag(rfidTag.getRfidTag());

					tagList.add(tag);

					vaildTagList.add(rfidTag.getRfidTag());
				}

				style.setTagList(tagList);
			}

			styleList.add(style);
		}

		// 태그 검증
		List<DistributionStorageRfidTag> tempTagList = distributionStorageRfidTagRepository.findByRfidTagIn(vaildTagList);

		List<Map<String, Object>> returnTagList = new ArrayList<Map<String, Object>>();
		boolean validCheck = true;

		for(DistributionStorageRfidTag tempTag : tempTagList) {

			if(tempTag.getStat().equals("1") || tempTag.getStat().equals("2")) {
				continue;
			}

			validCheck = false;

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", tempTag.getRfidTag());
			returnTagObj.put("stat", tempTag.getStat());
			returnTagObj.put("boxBarcode", tempTag.getBoxInfo() == null ? "" : tempTag.getBoxInfo().getBarcode());

			returnTagList.add(returnTagObj);

		}

		// 태그 검증 실패시 리턴
		if(!validCheck) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		TempDistributionReleaseBox tempReleaseBox = new TempDistributionReleaseBox();

		tempReleaseBox.setBarcode(requestBox.getBarcode());
		// TODO 온라인 적용시 변경
		tempReleaseBox.setOrderType("C");
		tempReleaseBox.setRegDate(new Date());
		tempReleaseBox.setCompleteYn("N");
		tempReleaseBox.setBatchYn("N");

		tempReleaseBox.setWmsCompleteDate(new Date());
		tempReleaseBox.setWmsCompleteYn("Y");
		tempReleaseBox.setWmsValidDate(new Date());
		tempReleaseBox.setWmsValidYn("Y");
		tempReleaseBox.setType("3");
		tempReleaseBox.setUpdDate(new Date());
		tempReleaseBox.setUserSeq(userInfo.getUserSeq());

		tempReleaseBox.setStyleList(styleList);

		tempDistributionReleaseBoxRepository.save(tempReleaseBox);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	/**
	 * WMS 실적 업데이트가 없기에 사용 안함 Deprecated
	 */
	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> releaseCompleteAfter(String barcode) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		/*
		if(barcode.equals("")) {
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		// WMS 작업 라인 체크
		TempDistributionReleaseBox tempReleaseBox = tempDistributionReleaseBoxRepository.findByBarcode(barcode);

		if (tempReleaseBox == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_WMS_BARCODE);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_WMS_BARCODE_MESSAGE);
			return obj;
		}

		if(tempReleaseBox.getWmsValidYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_VALID_WMS_BARCODE);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_WMS_BARCODE_MESSAGE);
			return obj;
		}

		if(tempReleaseBox.getWmsCompleteYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WMS_BARCODE);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WMS_BARCODE_MESSAGE);
			return obj;
		}

		// WMS 작업 완료 바코드 업데이트
		tempReleaseBox.setWmsCompleteDate(new Date());
		tempReleaseBox.setWmsCompleteYn("Y");
		tempReleaseBox.setUpdDate(new Date());

		tempDistributionReleaseBoxRepository.save(tempReleaseBox);
		*/

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public void releaseCompleteAfterBatch(TempDistributionReleaseBox tempReleaseBox) throws Exception {

		List<ErpStoreSchedule> erpStoreScheduleList = new ArrayList<ErpStoreSchedule>();

		UserInfo userInfo = userInfoRepository.findOne(tempReleaseBox.getUserSeq());

		// ERP 출고예정정보 실적 수량 업데이트
		// TODO 굳이 필요없으면 삭제하도록 할 예정
		for (TempDistributionReleaseStyle style : tempReleaseBox.getStyleList()) {

			ErpStoreSchedule erpStoreSchedule = erpStoreScheduleService.findByReleaseSchedule(style.getReferenceNo(),
																							  style.getStyle(),
																							  style.getColor(),
																							  style.getSize());

			if (erpStoreSchedule == null) {
				continue;
			}

			// Temp Style에 물류출고예정 일련번호 삽입
			style.setErpStoreScheduleSeq(erpStoreSchedule.getErpStoreScheduleSeq());

			// 물류출고예정정보 업데이트
			erpStoreSchedule.setReleaseAmount(erpStoreSchedule.getReleaseAmount() + style.getReleaseAmount());
			erpStoreSchedule.setReleaseDate(CalendarUtil.convertFormat("yyyyMMdd"));
			erpStoreSchedule.setUpdDate(new Date());
			erpStoreSchedule.setReleaseUserId(userInfo.getUserId());

			erpStoreScheduleList.add(erpStoreSchedule);
		}

		CompanyInfo endCompanyInfo = erpStoreScheduleList.get(0).getEndCompanyInfo();

		BoxInfo boxInfo = new BoxInfo(tempReleaseBox.getBarcode(),
									  erpStoreScheduleList.get(0).getStartCompanyInfo(),
									  endCompanyInfo,
									  userInfo);

		boxInfoRepository.save(boxInfo);
		boxInfoRepository.flush();

		/**
		 * 생산 재고 상태인 태그를 물류 출고할때 처리 로직
		 * 2019-05-20 최의선
		 */
		productionRfidTagMove(tempReleaseBox);

		// 물류 > 매장 입고예정 정보 생성
		releaseProcess(null, userInfo, boxInfo, tempReleaseBox.getTempBoxSeq(), tempReleaseBox.getType(), "S");

		Long workLine = releaseScheduleLogService.maxWorkLine(CalendarUtil.convertFormat("yyyyMMdd"), "storeRelease");

		workLine++;

		// 매장 입고예정정보 저장
		releaseScheduleLogService.save(tempReleaseBox, boxInfo, userInfo, workLine, "storeRelease");

		// 출고예정정보에도 저장
		erpStoreScheduleRepository.save(erpStoreScheduleList);

		tempReleaseBox.setCompleteYn("Y");
		tempReleaseBox.setCompleteDate(new Date());
		tempReleaseBox.setUpdDate(new Date());

		tempDistributionReleaseBoxRepository.save(tempReleaseBox);
	}

	@Transactional
	@Override
	public Map<String, Object> exceptionReleaseComplete(ReleaseBean releaseBean, String flag) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		UserInfo userInfo = userInfoRepository.findOne(releaseBean.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		List<Long> distributionStorageSeqList = new ArrayList<Long>();
		List<Long> productionStorageSeqList = new ArrayList<Long>();

		List<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		Long companySeq = Long.valueOf(0);

		for(BoxTagListBean box : releaseBean.getBoxList()){

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(box.getBarcode());

			if(boxInfo == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

			companySeq = boxInfo.getStartCompanyInfo().getCompanySeq();

			if(boxInfo.getStat().equals("2")){
				obj.put("resultCode", ApiResultConstans.MAPPING_BOX);
				obj.put("resultMessage", ApiResultConstans.MAPPING_BOX_MESSAGE);
				return obj;
			}

			if(boxInfo.getStat().equals("3")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return obj;
			}

			if(boxInfo.getStat().equals("4")){
				obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
				obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
				return obj;
			}

			for(RfidTagBean rfidTag : box.getRfidTagList()){

				ProductionStorageRfidTag productionRfidTag = productionStorageRfidTagRepository.findByRfidTagAndStat(rfidTag.getRfidTag(), "2");

				if(productionRfidTag == null){
					obj.put("resultCode", ApiResultConstans.ONLY_STOCK_RFID_TAG_POSSIBLE);
					obj.put("resultMessage", ApiResultConstans.ONLY_STOCK_RFID_TAG_POSSIBLE_MESSAGE);
					return obj;
				}

				productionRfidTag.setTempBoxInfo(boxInfo);
				productionStorageRfidTagList.add(productionRfidTag);
			}

			boxInfo.setTempInvoiceNum(box.getInvoiceNum());
			boxInfoList.add(boxInfo);
		}


		for(ProductionStorageRfidTag productionRfidTag : productionStorageRfidTagList) {

			BoxInfo boxInfo = productionRfidTag.getTempBoxInfo();

			// 생산 재고 > 출고로 업데이트
			productionRfidTag.setStat("3");
			productionRfidTag.setUpdUserInfo(userInfo);
			productionRfidTag.setUpdDate(new Date());
			productionRfidTag.setBoxBarcode(boxInfo.getBarcode());
			productionRfidTag.setBoxSeq(boxInfo.getBoxSeq());

			// 물류재고에 같은 바택으로 등록된 정보가 있는지 조회
			DistributionStorage distributionStorage = distributionStorageRepository.findByProductionStorageProductionStorageSeq(productionRfidTag.getProductionStorageSeq());

			if (distributionStorage == null) {
				ProductionStorage productionStorage = productionStorageRepository.findOne(productionRfidTag.getProductionStorageSeq());
				distributionStorage = distributionStorageRepository.save(new DistributionStorage(productionStorage, boxInfo.getEndCompanyInfo(), userInfo));
			}

			// 물류 입고 정보 저장
			RfidTagMaster tag = rfidTagMasterRepository.findByRfidTag(productionRfidTag.getRfidTag());

			DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();
			distributionTag.CopyData(tag);
			distributionTag.setBoxInfo(boxInfo);
			distributionTag.setStat("1");
			distributionTag.setDistributionStorageSeq(distributionStorage.getDistributionStorageSeq());
			distributionTag.setRegDate(new Date());
			distributionTag.setRegUserInfo(userInfo);

			distributionStorageRfidTagList.add(distributionTag);

			// 생산 출고 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("6");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 물류 입고 예정 태그 히스토리 저장
			rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("11");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			distributionStorageSeqList.add(distributionStorage.getDistributionStorageSeq());
			productionStorageSeqList.add(productionRfidTag.getProductionStorageSeq());

		}

		for(BoxInfo boxInfo : boxInfoList) {

			boxInfo.setInvoiceNum(boxInfo.getTempInvoiceNum());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setUpdDate(new Date());
			boxInfo.setStat("2");

			Date arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseBean.getArrivalDate());
			boxInfo.setArrivalDate(arrivalDate);
		}

		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		boxInfoRepository.save(boxInfoList);

		boxInfoRepository.flush();
		productionStorageRfidTagRepository.flush();
		distributionStorageRfidTagRepository.flush();

		// 생산 수량 로그 업데이트
		productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");

		// 물류 수량 로그 업데이트
		distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "1", "2");

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), flag, companySeq);

		workLine ++;

		List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogService.save(boxInfoList, userInfo, workLine, flag, null);

		storageScheduleLogService.updateStoreScheduleExceptionComplete(storageScheduleLogList, userInfo, releaseBean.getType());

		// 알림 추가
//		userNotiService.save("RFID 태그 생산 출고 완료되었습니다.", userInfo, "production", productionStorage.getProductionStorageSeq());

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> exceptionReleaseSchedule(String barcode) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if(barcode.length() != 18){
			map.put("resultCode", ApiResultConstans.NOT_VALID_BOX_BARCODE);
			map.put("resultMessage", ApiResultConstans.NOT_VALID_BOX_BARCODE_MESSAGE);
			return map;
		}

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

		if(boxInfo == null){
			map.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return map;
		}

		if(!boxInfo.getType().equals("01")){
			map.put("resultCode", ApiResultConstans.ONLY_PRODUCTION_BOX_POSSIBLE);
			map.put("resultMessage", ApiResultConstans.ONLY_PRODUCTION_BOX_POSSIBLE_MESSAGE);
			return map;
		}

		if(!boxInfo.getStat().equals("1")){
			map.put("resultCode", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE);
			map.put("resultMessage", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE_MESSAGE);
			return map;
		}

		Specifications<ProductionStorage> specifications = Specifications.where(ProductionStorageSpecification.companySeqEqual(boxInfo.getStartCompanyInfo().getCompanySeq()))
																		 .and(ProductionStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)));

		List<ProductionStorage> productionStorageList = productionStorageRepository.findAll(specifications);

		if(productionStorageList.size() == 0){
			map.put("resultCode", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT);
			map.put("resultMessage", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT_MESSAGE);
			return map;
		}

		ArrayList<Map<String, Object>> bartagList = new ArrayList<Map<String, Object>>();

		for(ProductionStorage storage : productionStorageList){

			boolean pushFlag = true;

			for(Map<String, Object> obj : bartagList){
				if(obj.get("erpKey").toString().equals(storage.getBartagMaster().getErpKey()) &&
				   obj.get("style").toString().equals(storage.getBartagMaster().getStyle()) &&
				   obj.get("color").toString().equals(storage.getBartagMaster().getColor()) &&
				   obj.get("style").toString().equals(storage.getBartagMaster().getSize())){
					pushFlag = false;
				}
			}

			if(pushFlag){

				Map<String, Object> tempMap = new LinkedHashMap<String, Object>();

				tempMap.put("erpKey", storage.getBartagMaster().getErpKey());
				tempMap.put("style", storage.getBartagMaster().getStyle());
				tempMap.put("color", storage.getBartagMaster().getColor());
				tempMap.put("size", storage.getBartagMaster().getSize());

				bartagList.add(tempMap);
			}
		}

		//bartag_master에 여러 값 가지는 경우 해외입고 수량체크가 가지는 값만큼 증가됨.... 다른 쪽 영향도 고려해 일단 중복 값 제거하는 방법으로 처리 - Cesil
		HashSet<Map<String, Object>> uniqueBartagList = new HashSet<Map<String, Object>>(bartagList);
		ArrayList<Map<String, Object>> ResultBartagList = new ArrayList<Map<String, Object>>(uniqueBartagList);

		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		map.put("startCompanyName", boxInfo.getStartCompanyInfo().getName());
		map.put("endCompanyName", boxInfo.getEndCompanyInfo().getName());
		//map.put("bartagList", bartagList);
		map.put("bartagList", ResultBartagList);

		return map;
	}

	@Transactional
	@Override
	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception {
		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.distribution_storage_rfid_tag SET stat = '2', upd_user_seq = ?, upd_date = getdate(), box_seq = null, box_barcode = null " +
						"WHERE box_seq = ?";

		template.update(query, userSeq, boxSeq);
	}

	@Transactional
	@Override
	public void deleteBoxInfo(Long boxSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "DELETE FROM dbo.distribution_storage_rfid_tag WHERE box_seq = ?";

		template.update(query, boxSeq);
	}

	@Transactional
	@Override
	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dsrt " +
						  "SET dsrt.stat = '7', " +
						  	  "dsrt.upd_date = getdate(), " +
						  	  "dsrt.upd_user_seq = ? " +
						 "FROM distribution_storage_rfid_tag dsrt " +
				   "INNER JOIN rfid_tag_reissue_request_detail rtrr " +
				   		   "ON dsrt.rfid_tag = rtrr.rfid_tag " +
				   		  "AND rtrr.rfid_tag_reissue_request_seq = ?";

    	template.update(query, userSeq, rfidTagReissueSeq);
	}

	@Transactional
	@Override
	public DistributionStorageRfidTag moveRfidTag(DistributionStorageRfidTag rfidTag, DistributionStorage storage, UserInfo userInfo) throws Exception {

		rfidTag.setDistributionStorageSeq(storage.getDistributionStorageSeq());
		rfidTag.setStat("2");
		rfidTag.setCustomerCd(storage.getDistributionCompanyInfo().getCode());
		rfidTag.setUpdUserInfo(userInfo);
		rfidTag.setUpdDate(new Date());

		return rfidTag;
	}

	@Transactional
	@Override
	public DistributionStorageRfidTag updateRfidTag(DistributionStorageRfidTag rfidTag, UserInfo userInfo) throws Exception {

		rfidTag.setStat("2");
		rfidTag.setUpdUserInfo(userInfo);
		rfidTag.setUpdDate(new Date());

		return rfidTag;
	}

	@Transactional(readOnly = true)
	@Override
	public List<DistributionStorageRfidTag> findAll(String customerCode, String style, String color, String size) throws Exception {

		List<DistributionStorageRfidTag> rfidTagList = new ArrayList<DistributionStorageRfidTag>();

		Specifications<DistributionStorage> specifications = Specifications.where(DistributionStorageSpecification.customerCodeEqual(customerCode))
																		    .and(DistributionStorageSpecification.styleEqual(style))
																		    .and(DistributionStorageSpecification.colorEqual(color))
																		    .and(DistributionStorageSpecification.sizeEqual(size));


		List<DistributionStorage> distributionStorageList = distributionStorageRepository.findAll(specifications);

		for(DistributionStorage distributionStorage : distributionStorageList) {
			List<DistributionStorageRfidTag> tempRfidTagList = distributionStorageRfidTagRepository.findByDistributionStorageSeq(distributionStorage.getDistributionStorageSeq());

			tempRfidTagList.forEach((DistributionStorageRfidTag rfidTag) -> rfidTagList.add(rfidTag));
		}

		return rfidTagList;
	}


	@Transactional(readOnly = true)
	@Override
	public List<Long> findByBoxDistributionSeq(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT dsrt.distribution_storage_seq " +
						 "FROM distribution_storage_rfid_tag dsrt " +
					    "WHERE dsrt.box_seq = :boxSeq " +
					 "GROUP BY dsrt.distribution_storage_seq";

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query, params,
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int row) throws SQLException {
				return new Long(rs.getLong("distribution_storage_seq"));
			}
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> existsStorageDistriubtionRfidTag(String barcode) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT temp.rfid_tag " +
						"	FROM ( " +
						"		SELECT sssdl.rfid_tag " +
						"		  FROM storage_schedule_log ssl " +
						"	INNER JOIN storage_schedule_detail_log sssl " +
						"	        ON ssl.storage_schedule_log_seq = sssl.storage_schedule_log_seq " +
						"	INNER JOIN storage_schedule_sub_detail_log sssdl " +
						"	        ON sssl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq" +
						"    INNER JOIN box_info bi" +
						"            ON ssl.box_seq = bi.box_seq" +
						"		 WHERE bi.barcode = :barcode ) temp " +
						"		 WHERE EXISTS ( SELECT rfid_tag " +
						"							  FROM distribution_storage_rfid_tag dsrt " +
						"							 WHERE temp.rfid_tag = dsrt.rfid_tag " +
						"					    	  AND dsrt.stat = '1') ";


		params.put("barcode", barcode);

		return nameTemplate.query(query, params,
				new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return new String(rs.getString("rfid_tag"));
			}
		});
	}


	@Transactional(readOnly = true)
	@Override
	public List<String> existsReleaseDistriubtionRfidTag(TempDistributionReleaseBox tempReleaseBox) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT temp.rfid_tag " +
						 "FROM ( " +
						 	"SELECT tdrt.rfid_tag " +
						 	  "FROM temp_distribution_release_box tdrb " +
						"INNER JOIN temp_distribution_release_style tdrs ON tdrb.temp_box_seq = tdrs.temp_box_seq " +
						"INNER JOIN temp_distribution_release_tag tdrt ON tdrs.temp_style_seq = tdrt.temp_style_seq " +
							 "WHERE tdrb.temp_box_seq = :tempBoxSeq ) temp " +
					   "INNER JOIN production_storage_rfid_tag psrt " +
					      "ON (psrt.rfid_tag = temp.rfid_tag AND psrt.stat = '2') " +
					   "WHERE NOT EXISTS ( SELECT rfid_tag " +
					   					    "FROM distribution_storage_rfid_tag dsrt " +
					   					   "WHERE temp.rfid_tag = dsrt.rfid_tag " +
					   					     "AND dsrt.stat = '2')";


		params.put("tempBoxSeq", tempReleaseBox.getTempBoxSeq());

		return nameTemplate.query(query, params,
				new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return new String(rs.getString("rfid_tag"));
			}
		});
	}

	@Transactional
	@Override
	public void productionRfidTagMove(TempDistributionReleaseBox tempReleaseBox) throws Exception {

		Date startDate = new Date();

		List<String> tempRfidTagList = existsReleaseDistriubtionRfidTag(tempReleaseBox);

		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<Long> productionStorageSeqList = new ArrayList<Long>();
		List<Long> distributionStorageSeqList = new ArrayList<Long>();

		// 해당사항 없을 시 Return
		if(tempRfidTagList == null || tempRfidTagList.size() == 0) {
			return;
		}

		// 정상창고
		CompanyInfo distributionCompanyInfo = companyInfoRepository.findByCustomerCode("100000");

		UserInfo userInfo = userInfoRepository.findOne(tempReleaseBox.getUserSeq());

		for(String rfidTag : tempRfidTagList) {

			ProductionStorageRfidTag productionStorageRfidTag = productionStorageRfidTagRepository.findByRfidTag(rfidTag);

			productionStorageRfidTag.setStat("3");
			productionStorageRfidTag.setUpdDate(new Date());
			productionStorageRfidTag.setUpdUserInfo(userInfo);
			productionStorageRfidTag.setBoxBarcode("M");

			productionStorageSeqList.add(productionStorageRfidTag.getProductionStorageSeq());
			productionStorageRfidTagList.add(productionStorageRfidTag);

			DistributionStorage distributionStorage = distributionStorageRepository.findByProductionStorageProductionStorageSeq(productionStorageRfidTag.getProductionStorageSeq());

			if (distributionStorage == null) {
				ProductionStorage productionStorage = productionStorageRepository.findOne(productionStorageRfidTag.getProductionStorageSeq());
				distributionStorage = distributionStorageRepository.save(new DistributionStorage(productionStorage, distributionCompanyInfo, userInfo));
			}

			// 물류 입고예정 정보 저장
			RfidTagMaster tag = StringUtil.setRfidTagMaster(rfidTag);

			DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();
			distributionTag.CopyData(tag);
			distributionTag.setCustomerCd(distributionCompanyInfo.getCode());
			distributionTag.setStat("2");
			distributionTag.setDistributionStorageSeq(distributionStorage.getDistributionStorageSeq());
			distributionTag.setRegDate(new Date());
			distributionTag.setRegUserInfo(userInfo);

			distributionStorageRfidTagList.add(distributionTag);

			// 생산 출고 태그 히스토리 등록
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("3");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 물류 입고예정 태그 히스토리 저장
			rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("11");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			// 물류 입고완료 태그 히스토리 저장
			rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("12");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

			distributionStorageSeqList.add(distributionStorage.getDistributionStorageSeq());
		}

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);

		if(productionStorageSeqList.size() > 0) {
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");
		}

		if(distributionStorageSeqList.size() > 0) {
			distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "2", "1");
		}
	}

	//컨베이어 불일치 RFID TAG에 대한 스타일, 컬러, 사이즈 정보 조회
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findMasterNonCompleteTag(String rfidTag) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();

		//태그 길이가 32자리가 아니면
		if(rfidTag.length() != 32) {

			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
            obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);

		}else {

			Long myBartagSeq = rfidTagMasterRepository.findBartagSeqByRfidTag(rfidTag);
			//해당 RFID 태그에 해당하는 bartag_seq가 존재하지 않는 경우
			if(myBartagSeq==null) {

				obj.put("resultCode", ApiResultConstans.NOT_FIND_RFID_TAG);
	            obj.put("resultMessage", ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);

			}else {

				BartagMaster bm = bartagMasterRepository.findByBartagSeq(myBartagSeq);

				obj.put("style", bm.getStyle());
				obj.put("color", bm.getColor());
				obj.put("size", bm.getSize());

				obj.put("resultCode", ApiResultConstans.SUCCESS);
		        obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
			}
		}

		return obj;

	}

	@Transactional
	@Override
	public void releaseProcess(List<String> rfidList, UserInfo userInfo, BoxInfo boxInfo, Long seq, String type, String flag) throws Exception {

		Date startDate = new Date();

		List<Long> distributionStorageSeqList = new ArrayList<Long>();
		List<Long> storeStorageSeqList = new ArrayList<Long>();

		List<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

		List<DistributionStorageRfidTag> distributionRfidTagList = new ArrayList<DistributionStorageRfidTag>();

		// 물류 재고 > 출고로 업데이트
		//TODO 매장입고시 추가되는 태그에 대한 업데이트 구분 필요

		if(flag.equals("S")) {
			tempDistributionReleaseBoxRepository.updateExistsReleaseTag("3", userInfo.getUserSeq(), boxInfo.getBoxSeq(), seq);

			distributionRfidTagList = distributionStorageRfidTagRepository.existsReleaseTagList(seq);
		} else if(flag.equals("E")) {
			releaseScheduleLogRepository.updateExistsReleaseTag("3", userInfo.getUserSeq(), boxInfo.getBoxSeq(), seq);

			distributionRfidTagList = distributionStorageRfidTagRepository.findByRfidTagIn(rfidList);
		}

		for (DistributionStorageRfidTag distributionRfidTag : distributionRfidTagList) {

			// 매장재고에 같은 바택으로 등록된 정보가 있는지 조회
			Specifications<StoreStorage> storeStorageSpec = Specifications.where(StoreStorageSpecification.distributionStorageSeqEqual(distributionRfidTag.getDistributionStorageSeq()))
					  													  .and(StoreStorageSpecification.companySeqEqual(boxInfo.getEndCompanyInfo().getCompanySeq()));

			StoreStorage storeStorage = storeStorageRepository.findOne(storeStorageSpec);

			if (storeStorage == null) {

				storeStorage = new StoreStorage();

				DistributionStorage distributionStorage = distributionStorageRepository.findOne(distributionRfidTag.getDistributionStorageSeq());

				storeStorage.setDistributionStorage(distributionStorage);
				storeStorage.setTotalAmount(Long.valueOf(0));
				storeStorage.setStat1Amount(Long.valueOf(0));
				storeStorage.setStat2Amount(Long.valueOf(0));
				storeStorage.setStat3Amount(Long.valueOf(0));
				storeStorage.setStat4Amount(Long.valueOf(0));
				storeStorage.setStat5Amount(Long.valueOf(0));
				storeStorage.setStat6Amount(Long.valueOf(0));
				storeStorage.setStat7Amount(Long.valueOf(0));
				storeStorage.setCompanyInfo(boxInfo.getEndCompanyInfo());
				storeStorage.setRegDate(new Date());
				storeStorage.setRegUserInfo(userInfo);

				storeStorage = storeStorageRepository.save(storeStorage);
			}

			// 판매 입고예정 정보 저장
			RfidTagMaster tag = StringUtil.setRfidTagMaster(distributionRfidTag.getRfidTag());

			StoreStorageRfidTag storeTag = storeStorageRfidTagRepository.findByRfidTag(distributionRfidTag.getRfidTag());

			if (storeTag == null) {
				storeTag = new StoreStorageRfidTag();
				storeTag.CopyData(tag);
				storeTag.setBoxInfo(boxInfo);
				storeTag.setBoxBarcode(boxInfo.getBarcode());
				storeTag.setCustomerCd(boxInfo.getEndCompanyInfo().getCode());
				storeTag.setStat("1");
				storeTag.setStoreStorageSeq(storeStorage.getStoreStorageSeq());
				storeTag.setRegDate(new Date());
				storeTag.setRegUserInfo(userInfo);
			} else {
				storeTag.setBoxInfo(boxInfo);
				storeTag.setBoxBarcode(boxInfo.getBarcode());
				storeTag.setStat("1");
				storeTag.setUpdDate(new Date());
				storeTag.setUpdUserInfo(userInfo);
			}

			storeStorageRfidTagList.add(storeTag);

			// 판매 입고예정 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("21");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryList.add(rfidTagHistory);

//			distributionStorageSeqList.add(distributionRfidTag.getDistributionStorageSeq());
//			storeStorageSeqList.add(storeStorage.getStoreStorageSeq());
		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
//		storeStorageRfidTagRepository.flush();
//		distributionStorageRfidTagRepository.flush();

		// 물류 수량 로그 저장(속도 이슈로 삭제)
		// distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "3", type);

		// 매장 수량 로그 저장(속도 이슈로 삭제)
		// storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "1", type);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Override
	public Map<String, Object> findByReleaseSchedulePost(ReferenceBean referenceBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<ReleaseScheduleResult> scheduleList = new ArrayList<ReleaseScheduleResult>();
		List<ErpStoreSchedule> erpStoreScheduleList = new ArrayList<ErpStoreSchedule>();

		//TODO 동일한 스타일, 컬러, 사이즈, ReferenceNo 일 경우 하나로 합치는 로직 필요

		// 일반 매장일 경우 custOrderType == 'C', 온라인일 경우 custOrderType == 'C1'
		if(referenceBean.getCustOrderTypeCd() == null || referenceBean.getCustOrderTypeCd().equals("C")) {

			Specifications<ErpStoreSchedule> erpStoreScheduleSpec = null;

			for(ReferenceBoxListBean tempBox : referenceBean.getBarcodeList()) {

				// 중복된 스타일, 컬러, 사이즈, ReferenceNo Merge
				tempBox.mergeStyle();

				for(ReferenceStyleListBean tempStyle : tempBox.getStyleList()) {

					// ReferenceNo 검증
					if(tempStyle.getReferenceNo().length() != 17){
						obj.put("resultCode", ApiResultConstans.NOT_VALID_REPERENCE_NO);
						obj.put("resultMessage", ApiResultConstans.NOT_VALID_REPERENCE_NO_MESSAGE);
						return obj;
					}
					erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.referenceNoEqual(tempStyle.getReferenceNo()))
														 .and(ErpStoreScheduleSpecification.styleEqual(tempStyle.getStyle()))
														 .and(ErpStoreScheduleSpecification.colorEqual(tempStyle.getColor()))
														 .and(ErpStoreScheduleSpecification.sizeEqual(tempStyle.getSize()));

					ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(erpStoreScheduleSpec);

					// 하나라도 조회된 데이터가 없으면 ERP 출고 예정 정보 없음 리턴
					if(erpStoreSchedule == null) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER_MESSAGE);
						return obj;
					}

					erpStoreScheduleList.add(erpStoreSchedule);
				}
			}

			for(ErpStoreSchedule erpSchedule : erpStoreScheduleList){

				// ErpStoreSchedule 셋팅
				ReleaseScheduleResult result = new ReleaseScheduleResult(erpSchedule);

				// 해당 스타일, 컬러, 사이즈의 바택정보가 있는지 확인
				if(erpSchedule.getRfidYn().equals("Y")) {

					BartagMinMaxResult minMaxResult = bartagService.findByMinMax(result.getStyle(), result.getColor(), result.getSize());

					if (minMaxResult == null) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
						return obj;
					}

					// StartRfidSeq, EndRfidSeq, ErpKey 셋팅
					result.CopyData(minMaxResult);

				} else {

					result.setErpKey("-");
					result.setStartRfidSeq("-");
					result.setEndRfidSeq("-");
				}

				result.setStockAmount(0L);

				scheduleList.add(result);
			}

		} else if(referenceBean.getCustOrderTypeCd().equals("C1")) {
			 // TODO 온라인출고 개발
		}

		obj.put("releaseScheduleList", scheduleList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;

	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> releaseCompleteDummy(ReferenceBean referenceBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		DistributionReleaseCompleteBean releaseCompleteBean = new DistributionReleaseCompleteBean();
		List<StyleBean> styleList = new ArrayList<StyleBean>();

		// 사용자 셋팅
		releaseCompleteBean.setUserSeq(1L);

		// 박스 정보 셋팅
		BoxBean boxInfo = new BoxBean();
		boxInfo.setBarcode(referenceBean.getBarcodeList().get(0).getBarcode());

		for(ReferenceBoxListBean tempBox : referenceBean.getBarcodeList()) {
			for(ReferenceStyleListBean tempStyle : tempBox.getStyleList()) {
				ProductMaster product = productMasterRepository.findByStyleAndColorAndSize(tempStyle.getStyle(), tempStyle.getColor(), tempStyle.getSize());

				StyleBean style = new StyleBean();

				style.setStyle(tempStyle.getStyle());
				style.setColor(tempStyle.getColor());
				style.setSize(tempStyle.getSize());
				style.setReferenceNo(tempStyle.getReferenceNo());
				style.setCount(tempStyle.getAmount());
				style.setErpKey(product != null ? product.getErpKey() : "-");
				style.setRfidYn(product != null ? "Y" : "N");

				if(product != null) {

					style.setRfidTagList(new ArrayList<RfidTagBean>());

					List<DistributionStorageRfidTag> tempTagList = distributionStorageRfidTagRepository.findByErpKeyAndStat(product.getErpKey(), "2", new PageRequest(0, tempStyle.getAmount().intValue()));

					for(DistributionStorageRfidTag tempTag : tempTagList) {
						RfidTagBean tag = new RfidTagBean();
						tag.setRfidTag(tempTag.getRfidTag());
						style.getRfidTagList().add(tag);
					}
				}

				styleList.add(style);
			}
		}

		boxInfo.setStyleList(styleList);

		releaseCompleteBean.setBoxInfo(boxInfo);

		obj.put("releaseCompleteBean", releaseCompleteBean);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}
}
