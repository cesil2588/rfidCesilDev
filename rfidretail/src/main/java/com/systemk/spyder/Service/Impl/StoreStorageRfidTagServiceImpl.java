package com.systemk.spyder.Service.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.BoxBean;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Dto.Request.StoreReturnBean;
import com.systemk.spyder.Dto.Request.StoreReturnListBean;
import com.systemk.spyder.Dto.Request.StyleBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleSubDetailLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.StoreStorageRfidTagSpecification;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.OpenDbService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.StoreStorageLogService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.Mapper.BoxCountAllRowMapper;
import com.systemk.spyder.Service.Mapper.RfidCountRowMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class StoreStorageRfidTagServiceImpl implements StoreStorageRfidTagService{

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StoreStorageLogService storeStorageLogService;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private OpenDbService openDbService;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<StoreStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception {

		Page<StoreStorageRfidTag> page = null;

		Specifications<StoreStorageRfidTag> specifications = Specifications.where(StoreStorageRfidTagSpecification.storeStorageSeqEqual(seq));

		if(!stat.equals("all")){
			specifications = specifications.and(StoreStorageRfidTagSpecification.statEqual(stat));
		}

		if(!search.equals("") && option.equals("rfidTag")){
			specifications = specifications.and(StoreStorageRfidTagSpecification.rfidTagContaining(search));
		}

		page = storeStorageRfidTagRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StoreStorageRfidTag> findStat(Long seq, String stat, Pageable pageable) throws Exception {
		return storeStorageRfidTagRepository.findByStoreStorageSeqAndStat(seq, stat, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StoreStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception {

		Page<StoreStorageRfidTag> page = null;

		Specifications<StoreStorageRfidTag> specifications = Specifications.where(StoreStorageRfidTagSpecification.storeStorageSeqEqual(seq));

		specifications = specifications.and(StoreStorageRfidTagSpecification.startEndRfidSeqBetween(startRfidSeq, endRfidSeq));

		page = storeStorageRfidTagRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional
	@Override
	public void inspectionAll(Long StoreStorageSeq) {
		// TODO Auto-generated method stub

	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(Long storeStorageSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("storeStorageSeq", storeStorageSeq);

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN store_storage_rfid_tag.stat = '1' THEN stat END) stat1_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '2' THEN stat END) stat2_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '3' THEN stat END) stat3_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '4' THEN stat END) stat4_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '5' THEN stat END) stat5_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '6' THEN stat END) stat6_amount, ");
		query.append("COUNT(CASE WHEN store_storage_rfid_tag.stat = '7' THEN stat END) stat7_amount ");
		query.append("FROM store_storage_rfid_tag ");
		query.append("WHERE store_storage_seq = :storeStorageSeq ");

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
		query.append("FROM store_storage_rfid_tag d ");
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
	public void inspectionBox(BoxInfo boxInfo) throws Exception {

		Date startDate = new Date();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();

		List<StoreStorageRfidTag> storeStorageRfidTagList = storeStorageRfidTagRepository.findByBoxInfoBarcodeAndStat(boxInfo.getBarcode(), "1");

		for(StoreStorageRfidTag tag : storeStorageRfidTagList){

			tag.setStat("2");
			tag.setUpdUserInfo(boxInfo.getUpdUserInfo());
			tag.setUpdDate(new Date());

			storeStorageRfidTagRepository.save(tag);

			// 판매 입고완료 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("22");
			rfidTagHistory.setRegUserInfo(boxInfo.getUpdUserInfo());
			rfidTagHistory.setCompanyInfo(boxInfo.getStartCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			rfidTagHistoryRepository.save(rfidTagHistory);

			storeStorageSeqList.add(tag.getStoreStorageSeq());
		}

//		storeStorageRfidTagRepository.flush();

		// 판매 수량 업데이트
		storeStorageLogService.save(storeStorageSeqList, boxInfo.getUpdUserInfo(), startDate, "2", "2");
	}

	@Transactional
	@Override
	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE ssrt " +
						  "SET ssrt.stat = '7', " +
						  	  "ssrt.upd_date = getdate(), " +
						  	  "ssrt.upd_user_seq = ? " +
						 "FROM store_storage_rfid_tag ssrt " +
				   "INNER JOIN rfid_tag_reissue_request_detail rtrr " +
				   		   "ON ssrt.rfid_tag = rtrr.rfid_tag " +
				   		  "AND rtrr.rfid_tag_reissue_request_seq = ?";

    	template.update(query, userSeq, rfidTagReissueSeq);
	}

	@Transactional
	@Override
	public Map<String, Object> inspectionBox(BoxInfo boxInfo, UserInfo userInfo, String type) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();

		List<StoreStorageRfidTag> storeStorageRfidTagList = storeStorageRfidTagRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

		ArrayList<StoreStorageRfidTag> tempRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagHistory> tempRfidTagHistoryList = new ArrayList<RfidTagHistory>();

		for(StoreStorageRfidTag tag : storeStorageRfidTagList){

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

			tag.setStat("2");
			tag.setUpdUserInfo(userInfo);
			tag.setUpdDate(new Date());

			tempRfidTagList.add(tag);

			// 매장 입고완료 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
			rfidTagHistory.setErpKey(tag.getErpKey());
			rfidTagHistory.setRfidTag(tag.getRfidTag());
			rfidTagHistory.setRfidSeq(tag.getRfidSeq());
			rfidTagHistory.setWork("22");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(boxInfo.getEndCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			tempRfidTagHistoryList.add(rfidTagHistory);

			storeStorageSeqList.add(tag.getStoreStorageSeq());
		}

		storeStorageRfidTagRepository.save(tempRfidTagList);
		rfidTagHistoryRepository.save(tempRfidTagHistoryList);

//		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "2", type);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> returnSave(StoreReturnBean returnBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(returnBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(returnBean.getBoxInfo().getBarcode());

		if(boxInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		if(!boxInfo.getStat().equals("1")){
			obj.put("resultCode", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE);
			obj.put("resultMessage", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE_MESSAGE);
			return obj;
		}

		Long companySeq = boxInfo.getStartCompanyInfo().getCompanySeq();
		CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCode("100000");

		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setUpdDate(new Date());
		boxInfo.setStat("2");

		Date arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnBean.getArrivalDate());
		boxInfo.setArrivalDate(arrivalDate);
		boxInfo.setEndCompanyInfo(endCompanyInfo);

		boxInfoList.add(boxInfo);

		for(StyleBean style : returnBean.getBoxInfo().getStyleList()){

			for(RfidTagBean rfidTag : style.getRfidTagList()){

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				if(!storeRfidTag.getCustomerCd().equals(boxInfo.getStartCompanyInfo().getCode())){
					obj.put("resultCode", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY);
					obj.put("resultMessage", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
					return obj;
				}

				// 판매 재고 > 반품 요청으로 업데이트
//				storeRfidTag.setStat("4");
				storeRfidTag.setUpdUserInfo(userInfo);
				storeRfidTag.setUpdDate(new Date());
				storeRfidTag.setBoxInfo(boxInfo);
				storeRfidTag.setBoxBarcode(boxInfo.getBarcode());

				storeStorageRfidTagList.add(storeRfidTag);
				storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
			}
		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		boxInfoRepository.save(boxInfoList);

		storeStorageRfidTagRepository.flush();

//		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "2");

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "10-R", companySeq);

		workLine ++;
		// 물류 입고 예정 정보 저장
		storageScheduleLogService.save(boxInfoList, userInfo, workLine, "10-R", returnBean.getReturnType());

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> returnUpdate(StoreReturnBean returnBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();

		UserInfo userInfo = userInfoRepository.findOne(returnBean.getUserSeq());

		// 사용자 정보 확인
		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(returnBean.getBoxInfo().getBarcode());

		// 입고 예정 정보 확인
		if(scheduleLog == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		// 확정된 박스 여부 확인
		if(scheduleLog.getConfirmYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.CONFIRM_BOX);
			obj.put("resultMessage", ApiResultConstans.CONFIRM_BOX_MESSAGE);
			return obj;
		}

		// 이미 반품완료된 박스 여부 확인
		if(scheduleLog.getCompleteYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
			return obj;
		}

		// 판매 반품된 박스 정보 삭제
		deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

		for(StorageScheduleDetailLog detailLog : scheduleLog.getStorageScheduleDetailLog()){
			storeStorageSeqList.add(detailLog.getStyleSeq());
		}

		scheduleLog.setReturnType(returnBean.getReturnType());

		scheduleLog.getBoxInfo().setArrivalDate(CalendarUtil.convertStartDate(returnBean.getArrivalDate()));
		scheduleLog.getBoxInfo().setUpdDate(new Date());
		scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

		for(StyleBean style : returnBean.getBoxInfo().getStyleList()){

			for(RfidTagBean rfidTag : style.getRfidTagList()){

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				if(!storeRfidTag.getCustomerCd().equals(scheduleLog.getBoxInfo().getStartCompanyInfo().getCode())){
					obj.put("resultCode", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY);
					obj.put("resultMessage", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
					return obj;
				}

				// 판매 재고 > 반품으로 업데이트
//				storeRfidTag.setStat("4");
				storeRfidTag.setUpdUserInfo(userInfo);
				storeRfidTag.setUpdDate(new Date());
				storeRfidTag.setBoxInfo(scheduleLog.getBoxInfo());
				storeRfidTag.setBoxBarcode(scheduleLog.getBoxInfo().getBarcode());

				storeStorageRfidTagList.add(storeRfidTag);
				storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
			}
		}

		boxInfoRepository.save(scheduleLog.getBoxInfo());

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		storeStorageRfidTagRepository.flush();

		// 판매 수량 변경
//		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "2");

		// 물류 입고 예정 정보 수정
		storageScheduleLogService.update(scheduleLog, userInfo, "10-R");

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> returnDelete(StoreReturnListBean returnBeanList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(returnBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for(BoxBean boxInfo : returnBeanList.getBoxList()){

			StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			// 입고 예정 정보 확인
			if (scheduleLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (scheduleLog.getConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.CONFIRM_BOX);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_BOX_MESSAGE);
				return obj;
			}

			// 이미 반품완료된 박스 여부 확인
			if (scheduleLog.getCompleteYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return obj;
			}

			// 판매 반품된 박스 정보 삭제
			deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

			for(StorageScheduleDetailLog detailLog : scheduleLog.getStorageScheduleDetailLog()){
				storeStorageSeqList.add(detailLog.getStyleSeq());
			}

			scheduleLogList.add(scheduleLog);

			scheduleLog.getBoxInfo().setArrivalDate(null);
			scheduleLog.getBoxInfo().setStat("1");
			scheduleLog.getBoxInfo().setUpdDate(new Date());
			scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

			boxList.add(scheduleLog.getBoxInfo());
		}

		// 판매 수량 변경
//		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "2");

		// 입고 예정 정보 삭제
		storageScheduleLogRepository.delete(scheduleLogList);

		// 박스 상태 초기화
		boxInfoRepository.save(boxList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> returnComplete(StoreReturnListBean returnBeanList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> distributionStorageSeqList = new ArrayList<Long>();
		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

	    UserInfo userInfo = userInfoRepository.findOne(returnBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

	    String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");

	    for(BoxBean box : returnBeanList.getBoxList()){

	    	StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(box.getBarcode());

			// 입고 예정 정보 확인
			if (scheduleLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

	    	// 확정된 박스 여부 확인
	    	if(scheduleLog.getConfirmYn().equals("Y")){
	    		obj.put("resultCode", ApiResultConstans.CONFIRM_BOX);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_BOX_MESSAGE);
				return obj;
	    	}

			// 이미 반품완료된 박스 여부 확인
			if (scheduleLog.getCompleteYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return obj;
			}

	    	scheduleLog.setConfirmDate(new Date());
			scheduleLog.setConfirmYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			scheduleLog.setBoxInfo(boxInfo);

			String arrivalDate = CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate());

			/**
			 * 출고 작업 완료시 물류센터 데이터 전송으로 수정
			 * 2018-08-07
			 */
			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){

				for(StorageScheduleSubDetailLog scheduleSubDetailLog : scheduleDetailLog.getStorageScheduleSubDetailLog()){

					// 물류에 똑같은 정보가 있는지 확인
					DistributionStorageRfidTag distributionStorageRfidTag = distributionStorageRfidTagRepository.findByRfidTag(scheduleSubDetailLog.getRfidTag());

					if(distributionStorageRfidTag != null){

						// 물류 입고예정 정보 저장
						RfidTagMaster tag = StringUtil.setRfidTagMaster(scheduleSubDetailLog.getRfidTag());

						distributionStorageRfidTag.setBoxInfo(boxInfo);
						distributionStorageRfidTag.setBoxBarcode(boxInfo.getBarcode());
						distributionStorageRfidTag.setStat("1");
						distributionStorageRfidTag.setUpdDate(new Date());
						distributionStorageRfidTag.setUpdUserInfo(userInfo);

						distributionStorageRfidTagList.add(distributionStorageRfidTag);

						// 매장 반품 태그 히스토리 저장
						RfidTagHistory rfidTagHistory = new RfidTagHistory();

						rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
						rfidTagHistory.setErpKey(tag.getErpKey());
						rfidTagHistory.setRfidTag(tag.getRfidTag());
						rfidTagHistory.setRfidSeq(tag.getRfidSeq());
						rfidTagHistory.setWork("24");
						rfidTagHistory.setRegUserInfo(userInfo);
						rfidTagHistory.setCompanyInfo(boxInfo.getEndCompanyInfo());
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

						distributionStorageSeqList.add(distributionStorageRfidTag.getDistributionStorageSeq());
					}

					StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(scheduleSubDetailLog.getRfidTag());

					if(storeRfidTag != null){
						// 매장 재고 > 반품 요청으로 업데이트
						storeRfidTag.setStat("4");

						storeRfidTag.setUpdUserInfo(userInfo);
						storeRfidTag.setUpdDate(new Date());

						storeStorageRfidTagList.add(storeRfidTag);
						storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
					}
				}
			}

			boxInfo = boxInfoRepository.save(boxInfo);

			scheduleLogList.add(scheduleLog);

			// 테스트 코드
			/**
			 * to do 서버 반영시 반드시 삭제
			 */
			storageScheduleLogService.storageInit(scheduleLog);

			Set<StorageScheduleDetailLogModel> tempWmsDetailList = storageScheduleLogService.generateScheduleLog(scheduleLog, "WMS");

			OpenDbScheduleHeader openDbHeader = openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, scheduleLog.getOrderType());
			openDbService.saveOpenDbScheduleHeader(openDbHeader);

			// openDb, ERP 데이터 기록
			for(StorageScheduleDetailLogModel detailLog : tempWmsDetailList){

				OpenDbScheduleDetail openDbDetail = openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, arrivalDate, scheduleLog.getOrderType());
				openDbService.saveOpenDbScheduleDetail(openDbDetail);
			}

			erpService.saveReturnSchedule(scheduleLog);
		}

	    storageScheduleLogRepository.save(scheduleLogList);

	    storeStorageRfidTagRepository.save(storeStorageRfidTagList);
    	distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);

//    	storeStorageRfidTagRepository.flush();
//    	distributionStorageRfidTagRepository.flush();

    	storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "2");
		distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "1", "2");

		// 알림 추가
		userNotiService.save("RFID 반품 출고 완료되었습니다.", userInfo, "store", Long.valueOf(0));

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> returnDetail(String barcode) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

		// 입고 예정 정보 확인
		if (scheduleLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.store_storage_rfid_tag SET stat = '2', upd_user_seq = ?, upd_date = getdate(), box_seq = null, box_barcode = null " +
						"WHERE box_seq = ? ";

		template.update(query,
						userSeq,
						boxSeq);
	}

	@Transactional
	@Override
	public void rollbackNonCheck(Long userSeq, Long boxSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.store_storage_rfid_tag SET stat = '1', upd_user_seq = ?, upd_date = getdate() " +
						"WHERE box_seq = ? ";

		template.update(query,
						userSeq,
						boxSeq);
	}

	@Transactional
	@Override
	public void rollbackStoreMove(Long userSeq, String companyCode, Long startStorageSeq, Long endStorageSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.store_storage_rfid_tag SET stat = '2', upd_user_seq = ?, upd_date = getdate(), customer_cd = ?, store_storage_seq = ?, box_seq = null " +
						"WHERE store_storage_seq = ? ";

		template.update(query,
						userSeq,
						companyCode,
						startStorageSeq,
						endStorageSeq);
	}

	@Transactional
	@Override
	public void rollbackReissueRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE ssrt " +
						  "SET ssrt.stat = '2', " +
						  	  "ssrt.upd_date = getdate(), " +
						  	  "ssrt.upd_user_seq = ? " +
						 "FROM store_storage_rfid_tag ssrt " +
				   "INNER JOIN rfid_tag_reissue_request_detail rtrr " +
				   		   "ON ssrt.rfid_tag = rtrr.rfid_tag " +
				   		  "AND rtrr.rfid_tag_reissue_request_seq = ?";

    	template.update(query, userSeq, rfidTagReissueSeq);
	}

	@Transactional
	@Override
	public StoreStorageRfidTag moveRfidTag(StoreStorageRfidTag rfidTag, StoreStorage storeStorage, UserInfo userInfo) throws Exception {

		rfidTag.setStoreStorageSeq(storeStorage.getStoreStorageSeq());
		rfidTag.setStat("2");
		rfidTag.setCustomerCd(storeStorage.getCompanyInfo().getCode());
		rfidTag.setUpdUserInfo(userInfo);
		rfidTag.setUpdDate(new Date());

		return rfidTag;
	}

	@Transactional
	@Override
	public StoreStorageRfidTag updateRfidTag(StoreStorageRfidTag rfidTag, UserInfo userInfo) throws Exception {

		rfidTag.setStat("2");
		rfidTag.setUpdUserInfo(userInfo);
		rfidTag.setUpdDate(new Date());

		return rfidTag;
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> existsNotStoreRfidTag(ReleaseScheduleLog releaseScheduleLog) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuilder query = new StringBuilder();

		query.append("SELECT rssdl.rfid_tag ");
		query.append("  FROM release_schedule_log rsl ");
		query.append(" INNER JOIN release_schedule_detail_log rsdl ");
		query.append("    ON rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		query.append(" INNER JOIN release_schedule_sub_detail_log rssdl ");
		query.append("    ON rsdl.release_schedule_detail_log_seq = rssdl.release_schedule_detail_log_seq ");
		query.append(" WHERE NOT EXISTS (SELECT ssrt.rfid_tag ");
		query.append("    			   FROM store_storage_rfid_tag ssrt ");
		query.append("		          WHERE ssrt.rfid_tag  = rssdl.rfid_tag ");
		query.append("                  AND rsl.release_schedule_log_seq = :seq ) ");
		query.append("   AND rsl.release_schedule_log_seq = :seq  ");

		params.put("seq", releaseScheduleLog.getReleaseScheduleLogSeq());

		List<String> list = nameTemplate.query(query.toString(), params,
			new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int row) throws SQLException {
					return new String(rs.getString("rfid_tag"));
				}
			});

		return list;
	}
}
