package com.systemk.spyder.Service.Impl;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.ReissueBean;
import com.systemk.spyder.Dto.Request.ReissueRequestBean;
import com.systemk.spyder.Dto.Request.StoreReissueBean;
import com.systemk.spyder.Dto.Request.StoreReissueListBean;
import com.systemk.spyder.Dto.Response.ReissueResult;
import com.systemk.spyder.Entity.External.Key.RfidTagIfKey;
import com.systemk.spyder.Entity.External.RfidTagIf;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.External.RfidTagIfRepository;
import com.systemk.spyder.Repository.Main.*;
import com.systemk.spyder.Repository.Main.Specification.RfidTagReissueRequestSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.*;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Group.ReissueTagGroupModel;
import com.systemk.spyder.Service.Mapper.Group.ReissueTagGroupModelMapper;
import com.systemk.spyder.Service.Mapper.ReissueTagRowMapper;
import com.systemk.spyder.Util.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RfidTagReissueRequestServiceImpl implements RfidTagReissueRequestService{

	@Autowired
	private RfidTagReissueRequestRepository rfidTagReissueRequestRepository;

	@Autowired
	private RfidTagReissueRequestDetailRepository rfidTagReissueRequestDetailRepository;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private RfidTagService rfidTagService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private ErpService erpService;

	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private BartagLogService bartagLogService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private StoreStorageLogService storeStorageLogService;

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;

	@Autowired
	private RfidTagReissuePrintLogRepository rfidTagReissuePrintLogRepository;

	@Autowired
	private RfidTagReissuePrintLogService rfidTagReissuePrintLogService;

	@Autowired
	private RfidTagIfRepository rfidTagIfRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private JdbcTemplate template;

	@Transactional
	@Override
	public Map<String, Object> save(RfidTagReissueRequest rfidTagReissueRequest, String publishLocation) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		rfidTagReissueRequest.setRegUserInfo(userInfo);
		rfidTagReissueRequest.setRegDate(new Date());
		rfidTagReissueRequest.setPublishLocation(publishLocation);
		rfidTagReissueRequest.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		rfidTagReissueRequest.setCompleteYn("N");
		rfidTagReissueRequest.setConfirmYn("N");

		if(publishLocation.equals("1") && rfidTagReissueRequest.getCompanyInfo().getType().equals("3")){

			rfidTagReissueRequest.setType("1");

		} else if(publishLocation.equals("2") && rfidTagReissueRequest.getCompanyInfo().getType().equals("3")){

			rfidTagReissueRequest.setType("2");

		} else if(publishLocation.equals("2") && rfidTagReissueRequest.getCompanyInfo().getType().equals("4")){

			rfidTagReissueRequest.setType("3");

		} else if(publishLocation.equals("2") && (rfidTagReissueRequest.getCompanyInfo().getType().equals("5") || rfidTagReissueRequest.getCompanyInfo().getType().equals("6"))){

			rfidTagReissueRequest.setType("4");
		}

		Long max = maxWorkLine(rfidTagReissueRequest.getCreateDate(), rfidTagReissueRequest.getType(), rfidTagReissueRequest.getCompanyInfo().getCompanySeq());

		max ++;

		rfidTagReissueRequest.setWorkLine(max);

		if(rfidTagReissueRequest.getRfidTagReissueRequestDetail().size() > 0){

			for(RfidTagReissueRequestDetail detail : rfidTagReissueRequest.getRfidTagReissueRequestDetail()){

				detail.setStat("1");

				List<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailList = rfidTagReissueRequestDetailRepository.findAllByRfidTag(detail.getRfidTag());

				if(rfidTagReissueRequestDetailList.size() > 0){
					obj.put("resultCode", ApiResultConstans.DUPLICATION_RFID_TAG);
					obj.put("resultMessage", ApiResultConstans.DUPLICATION_RFID_TAG_MESSAGE);
					return obj;
				}

				RfidTagMaster rfidTag = rfidTagMasterRepository.findByRfidTag(detail.getRfidTag());
				BartagMaster bartag = bartagMasterRepository.findByBartagSeq(rfidTag.getBartagSeq());

				if(rfidTag != null && bartag != null){
					detail.setRfidSeq(rfidTag.getRfidSeq());
					detail.setCreateDate(rfidTag.getCreateDate());
					detail.setLineSeq(rfidTag.getLineSeq());
					detail.setSeq(rfidTag.getSeq());
					detail.setStyle(bartag.getStyle());
					detail.setColor(bartag.getColor());
					detail.setSize(bartag.getSize());
					detail.setOrderDegree(bartag.getOrderDegree());
					detail.setProductRfidSeason(bartag.getProductRfidYySeason());

					ProductionStorage productionStorage = productionStorageRepository.findByBartagMasterBartagSeq(bartag.getBartagSeq());

					detail.setProductionStorage(productionStorage);
				}
			}
			rfidTagReissueRequestRepository.save(rfidTagReissueRequest);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> reissueSave(RfidTagReissueRequest rfidTagReissueRequest) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		String publishLocation = rfidTagReissueRequest.getPublishLocation();

		switch(publishLocation){
			case "1" :
				if(!rfidTagReissueRequest.getCompanyInfo().getType().equals("3")){
					obj.put("resultCode", ApiResultConstans.ONLY_PRODUCTION_COMPANY);
					obj.put("resultMessage", ApiResultConstans.ONLY_PRODUCTION_COMPANY_MESSAGE);
					break;
				}

				obj = save(rfidTagReissueRequest, "1");
				break;

			case "2" :

				obj = save(rfidTagReissueRequest, "2");
				break;
		}

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> reissueConfirm(List<ReissueTagGroupModel> groupList) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();

		for(ReissueTagGroupModel groupModel : groupList){

			List<RfidTagReissueRequest> rfidTagReissueRequestList = rfidTagReissueRequestRepository.findByCreateDateAndCompanyInfoCompanySeqAndConfirmYn(groupModel.getCreateDate(), groupModel.getCompanySeq(), "N");

			if(groupModel.getPublishLocation().equals("1")){

				obj = reissuePublish(rfidTagReissueRequestList);

			} else if(groupModel.getPublishLocation().equals("2")){

				obj = reissueDistribution(rfidTagReissueRequestList);
			}
		}

		return obj;
	}

	/**
	 * 생산업체 > 발행업체 재발행 요청
	 */
	@Transactional
	@Override
	public Map<String, Object> reissuePublish(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		List<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		List<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		List<Long> productionStorageSeqList = new ArrayList<Long>();
		List<Long> bartagSeqList = new ArrayList<Long>();

		List<RfidTagReissueRequest> tempRfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for(RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList){

			rfidTagReissueRequest.setConfirmYn("Y");
			rfidTagReissueRequest.setConfirmDate(new Date());
			rfidTagReissueRequest.setUpdUserInfo(userInfo);
			rfidTagReissueRequest.setUpdDate(new Date());

			tempRfidTagReissueRequestList.add(rfidTagReissueRequest);

			for(RfidTagReissueRequestDetail tag : rfidTagReissueRequest.getRfidTagReissueRequestDetail()) {

				ProductionStorageRfidTag productionRfid = productionStorageRfidTagRepository.findByRfidTag(tag.getRfidTag());

				productionRfid.setStat("4");
				productionRfid.setUpdDate(new Date());
				productionRfid.setUpdUserInfo(userInfo);
				productionStorageRfidTagList.add(productionRfid);

				// 바택 태그 상태 업데이트
				RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(productionRfid.getRfidTag());
				rfidTagMaster.setStat("6");
				rfidTagMaster.setUpdDate(new Date());
				rfidTagMaster.setUpdUserInfo(userInfo);
				rfidTagList.add(rfidTagMaster);

				// ERP 태그 테이블 업데이트
				erpService.updateRfidTagStatUseYn(productionRfid.getRfidTag(), "재발행 요청", "N");

				// 생산 재발행 요청 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(productionRfid.getErpKey() + productionRfid.getRfidSeq());
				rfidTagHistory.setErpKey(productionRfid.getErpKey());
				rfidTagHistory.setRfidTag(productionRfid.getRfidTag());
				rfidTagHistory.setRfidSeq(productionRfid.getRfidSeq());
				rfidTagHistory.setWork("4");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(rfidTagReissueRequest.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				// 생산 재발행 요청 태그 상태값 > 폐기 업데이트
				RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(productionRfid.getRfidTag());
				rfidTagStatus.setStat("2");
				rfidTagStatus.setUpdDate(new Date());
				rfidTagStatus.setUpdUserInfo(userInfo);

				rfidTagStatusList.add(rfidTagStatus);

				bartagSeqList.add(rfidTagMaster.getBartagSeq());
				productionStorageSeqList.add(productionRfid.getProductionStorageSeq());
			}

		}

		rfidTagReissueRequestRepository.save(tempRfidTagReissueRequestList);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagMasterRepository.save(rfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);

//		productionStorageRfidTagRepository.flush();
//		rfidTagMasterRepository.flush();

		if(bartagSeqList.size() > 0) {
			bartagLogService.save(bartagSeqList, userInfo, startDate, "6");
		}

		if(productionStorageSeqList.size() > 0){
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "4", "1");
		}

		HashSet<Long> distinctData1 = new HashSet<Long>(productionStorageSeqList);
		productionStorageSeqList = new ArrayList<Long>(distinctData1);

		for(Long bartagSeq : bartagSeqList){

			// 바택 재발행 요청 여부 업데이트
			BartagMaster bartag = bartagMasterRepository.findOne(bartagSeq);

			bartag.setReissueRequestYn("Y");
			bartag.setUpdDate(new Date());

			bartagMasterRepository.save(bartag);

		}

		// 태그발급업체 우선은 한곳만 가져올수 있도록 처리
		UserInfo emailInfo = userInfoRepository.findTop1ByCompanyInfoRoleInfoRole("publish");

		if (emailInfo != null) {

			for (UserEmailInfo email : emailInfo.getUserEmailInfo()) {
				mailService.sendMail(email.getEmail(), "재발행요청", "RFID 태그 재발행 요청되었습니다.<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 재발행 태그를 확인해주세요.", "5");
			}

			// 알림 추가
			userNotiService.save("RFID 태그 재발행 요청되었습니다.", emailInfo, "bartag", Long.valueOf(0));
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	/**
	 * 생산, 물류, 판매 > 물류센터 재발행 요청
	 */
	@Transactional
	@Override
	public Map<String, Object> reissueDistribution(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception {


		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<ProductionStorageRfidTag> productionRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagReissueRequest> tempRfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		ArrayList<Long> bartagSeqList = new ArrayList<Long>();
		ArrayList<Long> productionSeqList = new ArrayList<Long>();
		ArrayList<Long> distributionSeqList = new ArrayList<Long>();
		ArrayList<Long> storeSeqList = new ArrayList<Long>();


		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for(RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList){

			rfidTagReissueRequest.setConfirmYn("Y");
			rfidTagReissueRequest.setConfirmDate(new Date());
			rfidTagReissueRequest.setUpdUserInfo(userInfo);
			rfidTagReissueRequest.setUpdDate(new Date());

			tempRfidTagReissueRequestList.add(rfidTagReissueRequest);

			// 바택 태그 상태 업데이트
			rfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 생산 태그 상태 업데이트
			productionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 물류 태그 상태 업데이트
			distributionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 판매 태그 상태 업데이트
			storeStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			for(RfidTagReissueRequestDetail tag : rfidTagReissueRequest.getRfidTagReissueRequestDetail()){

				RfidTagMaster rfidTagMaster = StringUtil.setRfidTagMaster(tag.getRfidTag());

				// ERP 태그 테이블 업데이트
				erpService.updateRfidTagStatUseYn(tag.getRfidTag(), "폐기", "N");

				// 물류 재발행 요청 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(rfidTagMaster.getErpKey() + rfidTagMaster.getRfidSeq());
				rfidTagHistory.setErpKey(rfidTagMaster.getErpKey());
				rfidTagHistory.setRfidTag(rfidTagMaster.getRfidTag());
				rfidTagHistory.setRfidSeq(rfidTagMaster.getRfidSeq());

				if(rfidTagReissueRequest.getType().equals("2")){
					rfidTagHistory.setWork("7");
				} else if(rfidTagReissueRequest.getType().equals("3")){
					rfidTagHistory.setWork("17");
				} else if(rfidTagReissueRequest.getType().equals("4")){
					rfidTagHistory.setWork("27");
				}

				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(rfidTagReissueRequest.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				// 물류 재발행 요청 태그 상태값 > 폐기 업데이트
				RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(tag.getRfidTag());

				rfidTagStatus.setStat("2");
				rfidTagStatus.setUpdUserInfo(userInfo);
				rfidTagStatus.setUpdDate(new Date());


				JSONObject checkObj = checkStorageSeq(tag.getRfidTag());

				bartagSeqList.add(tag.getProductionStorage().getBartagMaster().getBartagSeq());
				productionSeqList.add(tag.getProductionStorage().getProductionStorageSeq());

				if(checkObj.optBoolean("distributionStorageSeq")){
					distributionSeqList.add(checkObj.getLong("distributionStorageSeq"));
				}

				if(checkObj.optBoolean("storeStorageSeq")){
					storeSeqList.add(checkObj.getLong("storeStorageSeq"));
				}
			}
		}

		rfidTagReissueRequestRepository.save(tempRfidTagReissueRequestList);
		rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);

//		rfidTagMasterRepository.flush();
//		productionStorageRfidTagRepository.flush();
//		distributionStorageRfidTagRepository.flush();
//		storeStorageRfidTagRepository.flush();

		if(bartagSeqList.size() > 0){
			bartagLogService.save(bartagSeqList, userInfo, startDate, "7");
		}

		if(productionSeqList.size() > 0){
			productionStorageLogService.save(productionSeqList, userInfo, startDate, "5", "1");
		}

		if(distributionSeqList.size() > 0){
			distributionStorageLogService.save(distributionSeqList, userInfo, startDate, "7", "1");
		}

		if(storeSeqList.size() > 0){
			storeStorageLogService.save(storeSeqList, userInfo, startDate, "7", "1");
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	/**
	 * 재발행 완료
	 */
	@Transactional
	@Override
	public Map<String, Object> reissueComplete(List<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailList) throws Exception {

		Date startDate = new Date();

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		ArrayList<RfidTagReissuePrintLog> printLogList = new ArrayList<RfidTagReissuePrintLog>();
		ArrayList<RfidTagReissueRequestDetail> requestDetailList = new ArrayList<RfidTagReissueRequestDetail>();
		ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();
		ArrayList<DistributionStorageRfidTag> distributionTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<Long> bartagSeqList = new ArrayList<Long>();
		ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
		ArrayList<Long> distributionStorageSeqList = new ArrayList<Long>();
		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<Long> reissueRequestSeqList = new ArrayList<Long>();
		ArrayList<RfidTagReissueRequest> reissueRequestList = new ArrayList<RfidTagReissueRequest>();

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		// 물류 프린터로그 일련번호 가져오기
		Long printSeq = rfidTagReissuePrintLogService.maxSeq(CalendarUtil.convertFormat("yyyyMMdd"));

		for(RfidTagReissueRequestDetail requestDetail : rfidTagReissueRequestDetailList){

			if(!reissueRequestSeqList.contains(requestDetail.getRfidTagReissueRequest().getRfidTagReissueRequestSeq())){
				reissueRequestSeqList.add(requestDetail.getRfidTagReissueRequest().getRfidTagReissueRequestSeq());
			}

			// 물류 프린터로그 저장(재발행 예정 태그)
			RfidTagReissuePrintLog printLog = new RfidTagReissuePrintLog();

			RfidTagMaster temp = StringUtil.setRfidTagMaster(requestDetail.getReissueRfidTag());

			printSeq ++;

			printLog.setCreateDate(CalendarUtil.convertFormat("yyyyMMdd"));
			printLog.setCreateSeq(printSeq);
			printLog.setCustomerCd(temp.getCustomerCd());
			printLog.setErpKey(temp.getErpKey());
			printLog.setOrderDegree(temp.getOrderDegree());
			printLog.setTargetRfidTag(temp.getRfidTag());
			printLog.setRfidSeq(temp.getRfidSeq());
			printLog.setReissueRfidTag(requestDetail.getReissueRfidTag());
			printLog.setRequestCompanyInfo(userInfo.getCompanyInfo());
			printLog.setRegDate(new Date());
			printLog.setRegUserInfo(userInfo);
			printLog.setPublishLocation("2");
			printLog.setPublishRegDate(temp.getPublishRegDate());
			printLog.setPublishDegree(temp.getPublishDegree());
			printLog.setSeason(temp.getSeason());
			printLog.setStat("2");
			printLog.setUpdDate(new Date());
			printLog.setUpdUserInfo(userInfo);
			printLog.setReissueCompleteDate(new Date());

			requestDetail.setStat("2");

			RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(printLog.getReissueRfidTag());

			String historyStat = "";

			if(rfidTagMaster == null){

				RfidTagMaster tempRfidTag = rfidTagMasterRepository.findTopByErpKeyAndSeasonAndOrderDegreeAndCustomerCdAndRfidSeq(printLog.getErpKey(), printLog.getSeason(), printLog.getOrderDegree(), printLog.getCustomerCd(), printLog.getRfidSeq());

				RfidTagMaster insertRfidTag = new RfidTagMaster();
				insertRfidTag.CopyData(tempRfidTag);
				insertRfidTag.setPublishLocation("2");
				insertRfidTag.setPublishRegDate(printLog.getPublishRegDate());
				insertRfidTag.setPublishDegree(printLog.getPublishDegree());
				insertRfidTag.setRegDate(new Date());
				insertRfidTag.setStat("5");
				insertRfidTag.setRfidTag(printLog.getReissueRfidTag());

				rfidTagList.add(insertRfidTag);

				bartagSeqList.add(tempRfidTag.getBartagSeq());

				if(requestDetail.getRfidTagReissueRequest().getType().equals("1")){

					// 생산 재발행 태그 > 저장
					ProductionStorageRfidTag tempProductionTag = productionStorageRfidTagRepository.findTopByBarcode(printLog.getErpKey() + printLog.getRfidSeq());

					ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();

					productionTag.setProductionStorageSeq(tempProductionTag.getProductionStorageSeq());
					productionTag.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
					productionTag.setErpKey(printLog.getErpKey());
					productionTag.setRfidTag(printLog.getReissueRfidTag());
					productionTag.setRfidSeq(printLog.getRfidSeq());
			    	productionTag.setRegUserInfo(userInfo);
					productionTag.setStat("1");
					productionTag.setRegDate(new Date());

					productionStorageRfidTagList.add(productionTag);

					productionStorageSeqList.add(tempProductionTag.getProductionStorageSeq());

					historyStat = "5";

				} else if(requestDetail.getRfidTagReissueRequest().getType().equals("2")){

					// 생산 재발행 태그 > 입고 저장
					ProductionStorageRfidTag tempProductionTag = productionStorageRfidTagRepository.findTopByBarcode(printLog.getErpKey() + printLog.getRfidSeq());

					ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();

					productionTag.setProductionStorageSeq(tempProductionTag.getProductionStorageSeq());
					productionTag.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
					productionTag.setErpKey(printLog.getErpKey());
					productionTag.setRfidTag(printLog.getReissueRfidTag());
					productionTag.setRfidSeq(printLog.getRfidSeq());
			    	productionTag.setRegUserInfo(userInfo);
					productionTag.setStat("2");
					productionTag.setRegDate(new Date());
					productionTag.setCustomerCd(tempProductionTag.getCustomerCd());

					productionStorageRfidTagList.add(productionTag);

					productionStorageSeqList.add(tempProductionTag.getProductionStorageSeq());

					historyStat = "5";

				} else if(requestDetail.getRfidTagReissueRequest().getType().equals("3")){

					// 생산 재발행 태그 > 출고 저장
					ProductionStorageRfidTag tempProductionTag = productionStorageRfidTagRepository.findTopByBarcode(printLog.getErpKey() + printLog.getRfidSeq());

					ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();

					productionTag.setProductionStorageSeq(tempProductionTag.getProductionStorageSeq());
					productionTag.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
					productionTag.setErpKey(printLog.getErpKey());
					productionTag.setRfidTag(printLog.getReissueRfidTag());
					productionTag.setRfidSeq(printLog.getRfidSeq());
			    	productionTag.setRegUserInfo(userInfo);
					productionTag.setStat("3");
					productionTag.setRegDate(new Date());

					productionStorageRfidTagList.add(productionTag);

					productionStorageSeqList.add(tempProductionTag.getProductionStorageSeq());

					// 물류 재발행 태그 > 입고 저장
					DistributionStorageRfidTag tempDistributionTag = distributionStorageRfidTagRepository.findTopByErpKeyAndRfidSeq(printLog.getErpKey(), printLog.getRfidSeq());

					DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();

					distributionTag.CopyData(insertRfidTag);
					distributionTag.setDistributionStorageSeq(tempDistributionTag.getDistributionStorageSeq());
					distributionTag.setStat("2");

					distributionTagList.add(distributionTag);

					distributionStorageSeqList.add(tempDistributionTag.getDistributionStorageSeq());

					historyStat = "15";

				} else if(requestDetail.getRfidTagReissueRequest().getType().equals("4")){

					// 생산 재발행 태그 > 출고 저장
					ProductionStorageRfidTag tempProductionTag = productionStorageRfidTagRepository.findTopByBarcode(printLog.getErpKey() + printLog.getRfidSeq());

					ProductionStorageRfidTag productionTag = new ProductionStorageRfidTag();

					productionTag.setProductionStorageSeq(tempProductionTag.getProductionStorageSeq());
					productionTag.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
					productionTag.setErpKey(printLog.getErpKey());
					productionTag.setRfidTag(printLog.getReissueRfidTag());
					productionTag.setRfidSeq(printLog.getRfidSeq());
			    	productionTag.setRegUserInfo(userInfo);
					productionTag.setStat("3");
					productionTag.setRegDate(new Date());

					productionStorageRfidTagList.add(productionTag);

					productionStorageSeqList.add(tempProductionTag.getProductionStorageSeq());

					// 물류 재발행 태그 > 입고 저장
					DistributionStorageRfidTag tempDistributionTag = distributionStorageRfidTagRepository.findTopByErpKeyAndRfidSeq(printLog.getErpKey(), printLog.getRfidSeq());

					DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();

					distributionTag.CopyData(insertRfidTag);
					distributionTag.setDistributionStorageSeq(tempDistributionTag.getDistributionStorageSeq());
					distributionTag.setStat("3");

					distributionTagList.add(distributionTag);

					distributionStorageSeqList.add(tempDistributionTag.getDistributionStorageSeq());

					// 판매 재발행 태그 > 저장
					StoreStorageRfidTag tempStoreTag = storeStorageRfidTagRepository.findTopByErpKeyAndRfidSeq(printLog.getErpKey(), printLog.getRfidSeq());

					StoreStorageRfidTag storeTag = new StoreStorageRfidTag();

					storeTag.CopyData(distributionTag);
					storeTag.setStoreStorageSeq(tempStoreTag.getStoreStorageSeq());
					storeTag.setStat("3");

					storeStorageRfidTagList.add(storeTag);

					storeStorageSeqList.add(tempStoreTag.getStoreStorageSeq());

					historyStat = "25";
				}

				// 생산 재발행 태그 히스토리 저장
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
				rfidTagHistory.setErpKey(printLog.getErpKey());
				rfidTagHistory.setRfidTag(printLog.getReissueRfidTag());
				rfidTagHistory.setRfidSeq(printLog.getRfidSeq());
				rfidTagHistory.setWork(historyStat);
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				// 물류 재발행 태그 상태값 저장
				RfidTagStatus rfidTagStatus = new RfidTagStatus();
				rfidTagStatus.setBarcode(printLog.getErpKey() + printLog.getRfidSeq());
				rfidTagStatus.setErpKey(printLog.getErpKey());
				rfidTagStatus.setRfidTag(printLog.getReissueRfidTag());
				rfidTagStatus.setRfidSeq(printLog.getRfidSeq());
				rfidTagStatus.setStat("1");
				rfidTagStatus.setRegUserInfo(userInfo);
				rfidTagStatus.setRegDate(new Date());

				rfidTagStatusList.add(rfidTagStatus);

				// 생산 재발행 태그 ERP 태그 테이블 저장
				RfidTagIf erpTag = new RfidTagIf();

				RfidTagIfKey rfidTagIfKey = new RfidTagIfKey();
				rfidTagIfKey.setTagRfid(printLog.getReissueRfidTag());
				rfidTagIfKey.setTagCrdt(insertRfidTag.getCreateDate());
				rfidTagIfKey.setTagCrsq(new BigDecimal(insertRfidTag.getSeq()));
				rfidTagIfKey.setTagCrno(new BigDecimal(insertRfidTag.getLineSeq()));

				erpTag.setKey(rfidTagIfKey);
				erpTag.setTagRfbc(printLog.getErpKey() + printLog.getRfidSeq());
				erpTag.setTagErpk(printLog.getErpKey());
				erpTag.setTagYyss(printLog.getSeason());
				erpTag.setTagOrdq(printLog.getOrderDegree());
				erpTag.setTagPrcd(printLog.getCustomerCd());
				erpTag.setTagPrtj(printLog.getPublishLocation());
				erpTag.setTagPrdt(printLog.getPublishRegDate());
				erpTag.setTagPrch(printLog.getPublishDegree());
				erpTag.setTagSeqn(printLog.getRfidSeq());
				erpTag.setTagUsyn("Y");
				erpTag.setTagStat("발행완료");
				erpTag.setTagStyl(requestDetail.getStyle());
				erpTag.setTagStcd(requestDetail.getColor() + requestDetail.getSize());
				erpTag.setTagJjch(requestDetail.getOrderDegree());
				erpTag.setTagEndt(new Date());

				rfidTagIfList.add(erpTag);

			}

			printLogList.add(printLog);
			requestDetailList.add(requestDetail);
		}

		rfidTagReissuePrintLogRepository.save(printLogList);
		rfidTagReissueRequestDetailRepository.save(requestDetailList);
		rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagIfRepository.save(rfidTagIfList);
		distributionStorageRfidTagRepository.save(distributionTagList);
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);

//		rfidTagReissueRequestDetailRepository.flush();
//		rfidTagMasterRepository.flush();
//		productionStorageRfidTagRepository.flush();
//		distributionStorageRfidTagRepository.flush();
//		storeStorageRfidTagRepository.flush();

		for(Long reissueRequestSeq : reissueRequestSeqList){
			RfidTagReissueRequest tempReissueRequest = rfidTagReissueRequestRepository.findOne(reissueRequestSeq);

			boolean check = true;

			for(RfidTagReissueRequestDetail tempReissueRequestDetail : tempReissueRequest.getRfidTagReissueRequestDetail()){
				if(tempReissueRequestDetail.getStat().equals("1")){
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

		logProcess(bartagSeqList, productionStorageSeqList, distributionStorageSeqList, storeStorageSeqList, userInfo, startDate, rfidTagReissueRequestDetailList.get(0).getRfidTagReissueRequest().getType());

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}


	public void logProcess(ArrayList<Long> bartagSeqList,
						   ArrayList<Long> productionStorageSeqList,
						   ArrayList<Long> distributionStorageSeqList,
						   ArrayList<Long> storeStorageSeqList,
						   UserInfo userInfo,
						   Date startDate,
						   String type) throws Exception {

		String productionVal = "";
		String distributionVal = "";
		String storeVal = "";

		if(type.equals("1")){
			productionVal = "1";
		} else if(type.equals("2")){
			productionVal = "2";
		} else if(type.equals("3")){
			productionVal = "3";
			distributionVal = "2";
		} else if(type.equals("4")){
			productionVal = "3";
			distributionVal = "3";
			storeVal = "2";
		}

		if(bartagSeqList.size() > 0){
			bartagLogService.save(bartagSeqList, userInfo, startDate, "5");
		}

		if(productionStorageSeqList.size() > 0){
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, productionVal, "1");
		}

		if(distributionStorageSeqList.size() > 0){
			distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, distributionVal, "1");
		}

		if(storeStorageSeqList.size() > 0){
			storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, storeVal, "1");
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findAll(String startDate, String endDate, String customerCode, String confirmYn) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Specifications<RfidTagReissueRequest> specifications = Specifications.where(RfidTagReissueRequestSpecification.regDateBetween(start, end));

		if(!customerCode.equals("")) {
			specifications = specifications.and(RfidTagReissueRequestSpecification.customerCodeEqual(customerCode));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(RfidTagReissueRequestSpecification.confirmYnEqual(confirmYn));
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE,
											rfidTagReissueRequestRepository.findAll(specifications)
																		   .stream()
																		   .map(obj -> new ReissueResult(obj, false))
																		   .collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<RfidTagReissueRequest> findAll(String createDate, Long companySeq, String confirmYn, String completeYn, String search, String option, Pageable pageable) throws Exception {

		Page<RfidTagReissueRequest> page = null;

		Specifications<RfidTagReissueRequest> specifications = Specifications.where(RfidTagReissueRequestSpecification.createDateEqual(createDate));

		if(companySeq != 0) {
			specifications = specifications.and(RfidTagReissueRequestSpecification.companySeqEqual(companySeq));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(RfidTagReissueRequestSpecification.confirmYnEqual(confirmYn));
		}

		if(!completeYn.equals("all")){
			specifications = specifications.and(RfidTagReissueRequestSpecification.completeYnEqual(completeYn));
		}

		page = rfidTagReissueRequestRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> storeDetail(Long seq) throws Exception {

		RfidTagReissueRequest obj = rfidTagReissueRequestRepository.findOne(seq);

		if(obj == null) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, new ReissueResult(obj, true));
	}

	@Transactional
	@Override
	public Map<String, Object> storeSave(StoreReissueBean reissueBean) throws Exception {

		Set<RfidTagReissueRequestDetail> rfidTagReissueRequestDetailSet = new HashSet<RfidTagReissueRequestDetail>();
		List<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();

		// 검증 확인
		if(reissueBean.getUserSeq() == null ||
				reissueBean.getType() == null ||
				reissueBean.getContent() == null ||
				reissueBean.getExplanatory() == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		UserInfo userInfo = userInfoRepository.findOne(reissueBean.getUserSeq());

		if(userInfo == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		// 재발행 요청 정보 셋팅
		RfidTagReissueRequest rfidTagReissueRequest = new RfidTagReissueRequest(userInfo, reissueBean.getExplanatory());

		Long max = maxWorkLine(rfidTagReissueRequest.getCreateDate(), rfidTagReissueRequest.getType(), rfidTagReissueRequest.getCompanyInfo().getCompanySeq());

		max ++;

		rfidTagReissueRequest.setWorkLine(max);

		CompanyInfo companyInfo = userInfo.getCompanyInfo();

		for(ReissueBean reissue : reissueBean.getContent()){

			RfidTagMaster rfidTag = rfidTagMasterRepository.findTopByErpKeyAndRfidSeq(reissue.getBarcode().substring(0, 10), reissue.getRfidSeq());

			if(rfidTag == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);
			}

			RfidTagReissueRequestDetail checkReissueDetail = rfidTagReissueRequestDetailRepository.findByRfidTag(rfidTag.getRfidTag());

			if(checkReissueDetail != null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.DUPLICATION_RFID_TAG_MESSAGE);
			}

			StoreStorageRfidTag storeStorageRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

			if(storeStorageRfidTag == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);
			}

			/* 업체코드 체크시 RFID 검증 영량력이 많은 관계로 삭제
			if(!storeStorageRfidTag.getCustomerCd().equals(companyInfo.getCode())){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
			}
			 */

			rfidTagList.add(rfidTag);

		}

		for(RfidTagMaster rfidTag : rfidTagList){
			BartagMaster bartag = bartagMasterRepository.findByBartagSeq(rfidTag.getBartagSeq());

			rfidTagReissueRequestDetailSet.add(new RfidTagReissueRequestDetail(rfidTag, bartag));
		}

		rfidTagReissueRequest.setRfidTagReissueRequestDetail(rfidTagReissueRequestDetailSet);

		rfidTagReissueRequestRepository.save(rfidTagReissueRequest);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> storeDelete(StoreReissueListBean reissueListBean) throws Exception {

		UserInfo userInfo = userInfoRepository.findOne(reissueListBean.getUserSeq());

		if(userInfo == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		List<RfidTagReissueRequest> rfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		for(ReissueRequestBean reissue : reissueListBean.getContent()){

			RfidTagReissueRequest rfidTagReissueRequest = rfidTagReissueRequestRepository.findOne(reissue.getReissueRequestSeq());

			if(rfidTagReissueRequest == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
			}

			if(rfidTagReissueRequest.getConfirmYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST_MESSAGE);
			}

			if(rfidTagReissueRequest.getCompleteYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST_MESSAGE);
			}

			rfidTagReissueRequestList.add(rfidTagReissueRequest);
		}

		rfidTagReissueRequestRepository.delete(rfidTagReissueRequestList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> storeComplete(StoreReissueListBean reissueListBean) throws Exception {

		Date startDate = new Date();

		UserInfo userInfo = userInfoRepository.findOne(reissueListBean.getUserSeq());

		if(userInfo == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		List<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		List<ProductionStorageRfidTag> productionRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		List<RfidTagReissueRequest> rfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		// 검증 및 Entity 가져오기
		for(ReissueRequestBean reissue : reissueListBean.getContent()){

			RfidTagReissueRequest rfidTagReissueRequest = rfidTagReissueRequestRepository.findOne(reissue.getReissueRequestSeq());

			if(rfidTagReissueRequest == null){
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
			}

			if(rfidTagReissueRequest.getConfirmYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST_MESSAGE);
			}

			if(rfidTagReissueRequest.getCompleteYn().equals("Y")){
				return ResultUtil.setCommonResult("E", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST_MESSAGE);
			}

			rfidTagReissueRequestList.add(rfidTagReissueRequest);
		}

		// 비지니스 로직 처리
		// TODO 로직 수정해야함. 매무 문제있는 로직!!
		for(RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList) {

			rfidTagReissueRequest.setConfirmYn("Y");
			rfidTagReissueRequest.setConfirmDate(new Date());
			rfidTagReissueRequest.setUpdUserInfo(userInfo);
			rfidTagReissueRequest.setUpdDate(new Date());

			// 바택 태그 상태 업데이트
			rfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 생산 태그 상태 업데이트
			productionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 물류 태그 상태 업데이트
			distributionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 매장 태그 상태 업데이트
			storeStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			for(RfidTagReissueRequestDetail tag : rfidTagReissueRequest.getRfidTagReissueRequestDetail()){

				RfidTagMaster rfidTagMaster = StringUtil.setRfidTagMaster(tag.getRfidTag());

				// ERP 태그 테이블 업데이트
				erpService.updateRfidTagStatUseYn(tag.getRfidTag(), "폐기", "N");

				// 물류 재발행 요청 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(rfidTagMaster.getErpKey() + rfidTagMaster.getRfidSeq());
				rfidTagHistory.setErpKey(rfidTagMaster.getErpKey());
				rfidTagHistory.setRfidTag(rfidTagMaster.getRfidTag());
				rfidTagHistory.setRfidSeq(rfidTagMaster.getRfidSeq());
				rfidTagHistory.setWork("27");

				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				// 물류 재발행 요청 태그 상태값 > 폐기 업데이트
				RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(tag.getRfidTag());

				rfidTagStatus.setStat("2");
				rfidTagStatus.setUpdUserInfo(userInfo);
				rfidTagStatus.setUpdDate(new Date());
			}
		}

		rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagReissueRequestRepository.save(rfidTagReissueRequestList);

		/* 속도이슈로 삭제
		if(bartagSeqList.size() > 0){
			bartagLogService.save(bartagSeqList, userInfo, startDate, "7");
		}

		if(productionSeqList.size() > 0){
			productionStorageLogService.save(productionSeqList, userInfo, startDate, "5", "1");
		}

		if(distributionSeqList.size() > 0){
			distributionStorageLogService.save(distributionSeqList, userInfo, startDate, "7", "1");
		}

		if(storeSeqList.size() > 0){
			storeStorageLogService.save(storeSeqList, userInfo, startDate, "7", "1");
		}

		userNotiService.save("RFID 태그 재발행 요청 확정되었습니다.", userInfo, "store", Long.valueOf(0));

		 */

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional(readOnly = true)
	public JSONObject checkStorageSeq(String rfidTag) throws Exception{

		JSONObject obj = new JSONObject();

		DistributionStorageRfidTag distributionStorageRfidTag = distributionStorageRfidTagRepository.findByRfidTag(rfidTag);

		if(distributionStorageRfidTag != null){
			obj.put("distributionStorageSeq", distributionStorageRfidTag.getDistributionStorageSeq());
		}

		StoreStorageRfidTag storeStorageRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag);

		if(storeStorageRfidTag != null){
			obj.put("storeStorageSeq", storeStorageRfidTag.getStoreStorageSeq());
		}

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Long maxWorkLine(String createDate, String reissueType, Long companySeq) throws Exception{
		RfidTagReissueRequest rfidTagReissueRequest = rfidTagReissueRequestRepository.findTop1ByCreateDateAndCompanyInfoCompanySeqAndTypeOrderByWorkLineDesc(createDate, companySeq, reissueType);
		Long maxWorkLine;

		if(rfidTagReissueRequest == null){
			maxWorkLine = Long.valueOf(0);
		} else {
			maxWorkLine = rfidTagReissueRequest.getWorkLine();
		}
		return maxWorkLine;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReissueTagGroupModel> findAll(String startDate, String endDate, Long companySeq, String type, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);
		List<String> typeList = new ArrayList<>();

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "rtrr.create_date DESC ");

		StringBuffer query = new StringBuffer();

		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) rtrr.create_date, rtrr.company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM rfid_tag_reissue_request crtrr ");
		query.append("INNER JOIN rfid_tag_reissue_request_detail crtrrd ");
		query.append("ON crtrr.rfid_tag_reissue_request_seq = crtrrd.rfid_tag_reissue_request_seq ");
		query.append("AND crtrr.confirm_yn = 'N' AND crtrr.complete_yn = 'N'");
		query.append("AND crtrr.create_date = rtrr.create_date ");
		query.append(") AS stat1_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM rfid_tag_reissue_request crtrr ");
		query.append("INNER JOIN rfid_tag_reissue_request_detail crtrrd ");
		query.append("ON crtrr.rfid_tag_reissue_request_seq = crtrrd.rfid_tag_reissue_request_seq ");
		query.append("AND crtrr.confirm_yn = 'Y' AND crtrr.complete_yn = 'N'");
		query.append("AND crtrr.create_date = rtrr.create_date ");
		query.append(") AS stat2_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM rfid_tag_reissue_request crtrr ");
		query.append("INNER JOIN rfid_tag_reissue_request_detail crtrrd ");
		query.append("ON crtrr.rfid_tag_reissue_request_seq = crtrrd.rfid_tag_reissue_request_seq ");
		query.append("AND crtrr.confirm_yn = 'Y' AND crtrr.complete_yn = 'Y'");
		query.append("AND crtrr.create_date = rtrr.create_date ");
		query.append(") AS stat3_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM rfid_tag_reissue_request crtrr ");
		query.append("INNER JOIN rfid_tag_reissue_request_detail crtrrd ");
		query.append("ON crtrr.rfid_tag_reissue_request_seq = crtrrd.rfid_tag_reissue_request_seq ");
		query.append("AND crtrr.create_date = rtrr.create_date ");
		query.append(") AS total_count ");

		query.append("FROM rfid_tag_reissue_request rtrr ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON rtrr.company_seq = ci.company_seq ");
		query.append("WHERE rtrr.create_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!type.equals("") && type.equals("publish")){

			query.append("AND rtrr.type =:type ");
			params.put("type", "1");

		} else if(!type.equals("") && type.equals("production")) {

			typeList.add("1");
			typeList.add("2");

			query.append("AND rtrr.type IN (:type) ");
			params.put("type", typeList);

		} else if(!type.equals("") && type.equals("distribution")) {

			typeList.add("3");
			typeList.add("4");

			query.append("AND rtrr.type IN (:type) ");
			params.put("type", typeList);

		} else if(!type.equals("") && type.equals("store")) {

			query.append("AND rtrr.type =:type ");
			params.put("type", "4");

		}

		query.append("GROUP BY rtrr.create_date, rtrr.company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, company_seq, name, stat1_count, stat2_count, stat3_count, total_count ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new ReissueTagGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountReissueTagGroupList(String startDate, String endDate, Long companySeq, String type, String search, String option) throws Exception{

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);
		List<String> typeList = new ArrayList<>();

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT rtrr.create_date, rtrr.confirm_yn, rtrr.company_seq ");
		query.append("FROM rfid_tag_reissue_request rtrr ");
		query.append("WHERE rtrr.create_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND rtrr.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		if(!type.equals("") && type.equals("publish")){

			query.append("AND rtrr.type =:type ");
			params.put("type", "1");

		} else if(!type.equals("") && type.equals("production")) {

			typeList.add("1");
			typeList.add("2");

			query.append("AND rtrr.type IN (:type) ");
			params.put("type", typeList);

		} else if(!type.equals("") && type.equals("distribution")) {

			typeList.add("3");
			typeList.add("4");

			query.append("AND rtrr.type IN (:type) ");
			params.put("type", typeList);

		} else if(!type.equals("") && type.equals("store")) {

			query.append("AND rtrr.type =:type ");
			params.put("type", "4");

		}

		query.append("GROUP BY rtrr.create_date, rtrr.confirm_yn, rtrr.company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel reissueTagCount(String createDate, Long companySeq, String confirmYn, String completeYn) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rtrr.confirm_yn = 'N' AND rtrr.complete_yn = 'N' THEN rtrr.confirm_yn END) AS stat1_amount,  ");
		query.append("COUNT(CASE WHEN rtrr.confirm_yn = 'Y' AND rtrr.complete_yn = 'N' THEN rtrr.confirm_yn END) AS stat2_amount,  ");
		query.append("COUNT(CASE WHEN rtrr.confirm_yn = 'Y' AND rtrr.complete_yn = 'Y' THEN rtrr.confirm_yn END) AS stat3_amount,  ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM rfid_tag_reissue_request rtrr ");
		query.append("INNER JOIN rfid_tag_reissue_request_detail rtrrd ");
		query.append("ON rtrr.rfid_tag_reissue_request_seq = rtrrd.rfid_tag_reissue_request_seq ");
		query.append("AND rtrr.create_date = :createDate ");
		params.put("createDate", createDate);

		if(!confirmYn.equals("all")){
			query.append("AND rtrr.confirm_yn = :confirmYn ");
			params.put("confirmYn", confirmYn);
		}

		if(!completeYn.equals("all")){
			query.append("AND rtrr.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		if(companySeq != 0){
			query.append("AND rtrr.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new ReissueTagRowMapper());
	}

	@Transactional
	@Override
	public Map<String, Object> reissueGroupUpdate(List<ReissueTagGroupModel> groupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<ProductionStorageRfidTag> productionRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagReissueRequest> rfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		ArrayList<Long> bartagSeqList = new ArrayList<Long>();
		ArrayList<Long> productionSeqList = new ArrayList<Long>();
		ArrayList<Long> distributionSeqList = new ArrayList<Long>();
		ArrayList<Long> storeSeqList = new ArrayList<Long>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (ReissueTagGroupModel group : groupList) {

			List<RfidTagReissueRequest> tempRfidTagReissueRequestList = rfidTagReissueRequestRepository.findByCreateDateAndCompanyInfoCompanySeqAndConfirmYn(group.getCreateDate(), group.getCompanySeq(), "N");

			for (RfidTagReissueRequest rfidTagReissueRequest : tempRfidTagReissueRequestList) {

				rfidTagReissueRequest.setConfirmYn("Y");
				rfidTagReissueRequest.setConfirmDate(new Date());
				rfidTagReissueRequest.setUpdUserInfo(userInfo);
				rfidTagReissueRequest.setUpdDate(new Date());

				// 바택 태그 상태 업데이트
				rfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

				// 생산 태그 상태 업데이트
				productionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

				// 물류 태그 상태 업데이트
				distributionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

				// 판매 태그 상태 업데이트
				storeStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

				for (RfidTagReissueRequestDetail tag : rfidTagReissueRequest.getRfidTagReissueRequestDetail()) {

					RfidTagMaster rfidTagMaster = StringUtil.setRfidTagMaster(tag.getRfidTag());

					// ERP 태그 테이블 업데이트
					erpService.updateRfidTagStatUseYn(tag.getRfidTag(), "폐기", "N");

					// 물류 재발행 요청 태그 히스토리 등록
					RfidTagHistory rfidTagHistory = new RfidTagHistory();

					rfidTagHistory.setBarcode(rfidTagMaster.getErpKey() + rfidTagMaster.getRfidSeq());
					rfidTagHistory.setErpKey(rfidTagMaster.getErpKey());
					rfidTagHistory.setRfidTag(rfidTagMaster.getRfidTag());
					rfidTagHistory.setRfidSeq(rfidTagMaster.getRfidSeq());
					rfidTagHistory.setWork("27");

					rfidTagHistory.setRegUserInfo(userInfo);
					rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
					rfidTagHistory.setRegDate(new Date());

					rfidTagHistoryList.add(rfidTagHistory);

					// 물류 재발행 요청 태그 상태값 > 폐기 업데이트
					RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(tag.getRfidTag());

					rfidTagStatus.setStat("2");
					rfidTagStatus.setUpdUserInfo(userInfo);
					rfidTagStatus.setUpdDate(new Date());

					JSONObject checkObj = checkStorageSeq(tag.getRfidTag());

					bartagSeqList.add(tag.getProductionStorage().getBartagMaster().getBartagSeq());
					productionSeqList.add(tag.getProductionStorage().getProductionStorageSeq());

					if (checkObj.optBoolean("distributionStorageSeq")) {
						distributionSeqList.add(checkObj.getLong("distributionStorageSeq"));
					}

					if (checkObj.optBoolean("storeStorageSeq")) {
						storeSeqList.add(checkObj.getLong("storeStorageSeq"));
					}
				}

				rfidTagReissueRequestList.add(rfidTagReissueRequest);

			}
		}

	    rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagReissueRequestRepository.save(rfidTagReissueRequestList);

//		rfidTagMasterRepository.flush();
//		productionStorageRfidTagRepository.flush();
//		distributionStorageRfidTagRepository.flush();
//		storeStorageRfidTagRepository.flush();

		if(bartagSeqList.size() > 0){
			bartagLogService.save(bartagSeqList, userInfo, startDate, "7");
		}

		if(productionSeqList.size() > 0){
			productionStorageLogService.save(productionSeqList, userInfo, startDate, "5", "1");
		}

		if(distributionSeqList.size() > 0){
			distributionStorageLogService.save(distributionSeqList, userInfo, startDate, "7", "1");
		}

		if(storeSeqList.size() > 0){
			storeStorageLogService.save(storeSeqList, userInfo, startDate, "7", "1");
		}

		userNotiService.save("RFID 태그 재발행 요청 확정되었습니다.", userInfo, "store", Long.valueOf(0));

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> reissueGroupDelete(List<ReissueTagGroupModel> groupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		List<RfidTagReissueRequest> rfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		for (ReissueTagGroupModel group : groupList) {

			List<RfidTagReissueRequest> tempRfidTagReissueRequestList = rfidTagReissueRequestRepository.findByCreateDateAndCompanyInfoCompanySeqAndConfirmYn(group.getCreateDate(), group.getCompanySeq(), "N");

			for (RfidTagReissueRequest rfidTagReissueRequest : tempRfidTagReissueRequestList) {

				if(rfidTagReissueRequest == null){
					obj.put("resultCode", ApiResultConstans.NOT_FIND_REISSUE_TAG_REQEUST);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_REISSUE_TAG_REQEUST_MESSAGE);
					return obj;
				}

				if(rfidTagReissueRequest.getConfirmYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST);
					obj.put("resultMessage", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST_MESSAGE);
					return obj;
				}

				if(rfidTagReissueRequest.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST_MESSAGE);
					return obj;
				}

				rfidTagReissueRequestList.add(rfidTagReissueRequest);
			}
		}

		rfidTagReissueRequestRepository.delete(rfidTagReissueRequestList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RfidTagReissueRequestDetail> findDetailList(List<ReissueTagGroupModel> groupList) throws Exception {

		List<RfidTagReissueRequestDetail> reissueList = new ArrayList<RfidTagReissueRequestDetail>();

		for(ReissueTagGroupModel group : groupList){
			Specifications<RfidTagReissueRequest> specifications = Specifications.where(RfidTagReissueRequestSpecification.createDateEqual(group.getCreateDate()))
																				 .and(RfidTagReissueRequestSpecification.companySeqEqual(group.getCompanySeq()))
																				 .and(RfidTagReissueRequestSpecification.publishLocationEqual("2"));

			List<RfidTagReissueRequest> tempReissueList = rfidTagReissueRequestRepository.findAll(specifications);

			for(RfidTagReissueRequest tempReissue : tempReissueList){

				for(RfidTagReissueRequestDetail tempReissueDetail : tempReissue.getRfidTagReissueRequestDetail()){

					reissueList.add(tempReissueDetail);
				}
			}
		}

		return reissueList;
	}

	@Transactional
	@Override
	public Map<String, Object> reissueUpdate(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<RfidTagStatus> rfidTagStatusList = new ArrayList<RfidTagStatus>();
		ArrayList<ProductionStorageRfidTag> productionRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagReissueRequest> tempRfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		ArrayList<Long> bartagSeqList = new ArrayList<Long>();
		ArrayList<Long> productionSeqList = new ArrayList<Long>();
		ArrayList<Long> distributionSeqList = new ArrayList<Long>();
		ArrayList<Long> storeSeqList = new ArrayList<Long>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList) {

			if(rfidTagReissueRequest.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST_MESSAGE);
				return obj;
			}

			if(rfidTagReissueRequest.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST_MESSAGE);
				return obj;
			}
		}

		for (RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList) {

			rfidTagReissueRequest.setConfirmYn("Y");
			rfidTagReissueRequest.setConfirmDate(new Date());
			rfidTagReissueRequest.setUpdUserInfo(userInfo);
			rfidTagReissueRequest.setUpdDate(new Date());

			// 바택 태그 상태 업데이트
			rfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 생산 태그 상태 업데이트
			productionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 물류 태그 상태 업데이트
			distributionStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			// 판매 태그 상태 업데이트
			storeStorageRfidTagService.disuseRfidTag(userInfo.getUserSeq(), rfidTagReissueRequest.getRfidTagReissueRequestSeq());

			for (RfidTagReissueRequestDetail tag : rfidTagReissueRequest.getRfidTagReissueRequestDetail()) {

				RfidTagMaster rfidTagMaster = StringUtil.setRfidTagMaster(tag.getRfidTag());

				// ERP 태그 테이블 업데이트
				erpService.updateRfidTagStatUseYn(tag.getRfidTag(), "폐기", "N");

				// 물류 재발행 요청 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(rfidTagMaster.getErpKey() + rfidTagMaster.getRfidSeq());
				rfidTagHistory.setErpKey(rfidTagMaster.getErpKey());
				rfidTagHistory.setRfidTag(rfidTagMaster.getRfidTag());
				rfidTagHistory.setRfidSeq(rfidTagMaster.getRfidSeq());
				rfidTagHistory.setWork("27");

				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				// 물류 재발행 요청 태그 상태값 > 폐기 업데이트
				RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(tag.getRfidTag());

				rfidTagStatus.setStat("2");
				rfidTagStatus.setUpdUserInfo(userInfo);
				rfidTagStatus.setUpdDate(new Date());

				JSONObject checkObj = checkStorageSeq(tag.getRfidTag());

				bartagSeqList.add(tag.getProductionStorage().getBartagMaster().getBartagSeq());
				productionSeqList.add(tag.getProductionStorage().getProductionStorageSeq());

				if (checkObj.optBoolean("distributionStorageSeq")) {
					distributionSeqList.add(checkObj.getLong("distributionStorageSeq"));
				}

				if (checkObj.optBoolean("storeStorageSeq")) {
					storeSeqList.add(checkObj.getLong("storeStorageSeq"));
				}
			}

			tempRfidTagReissueRequestList.add(rfidTagReissueRequest);

		}

	    rfidTagMasterRepository.save(rfidTagList);
		productionStorageRfidTagRepository.save(productionRfidTagList);
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagStatusRepository.save(rfidTagStatusList);
		rfidTagReissueRequestRepository.save(tempRfidTagReissueRequestList);

//		rfidTagMasterRepository.flush();
//		productionStorageRfidTagRepository.flush();
//		distributionStorageRfidTagRepository.flush();
//		storeStorageRfidTagRepository.flush();

		if(bartagSeqList.size() > 0){
			bartagLogService.save(bartagSeqList, userInfo, startDate, "7");
		}

		if(productionSeqList.size() > 0){
			productionStorageLogService.save(productionSeqList, userInfo, startDate, "5", "1");
		}

		if(distributionSeqList.size() > 0){
			distributionStorageLogService.save(distributionSeqList, userInfo, startDate, "7", "1");
		}

		if(storeSeqList.size() > 0){
			storeStorageLogService.save(storeSeqList, userInfo, startDate, "7", "1");
		}

		userNotiService.save("RFID 태그 재발행 요청 확정되었습니다.", userInfo, "store", Long.valueOf(0));

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> reissueDelete(List<RfidTagReissueRequest> rfidTagReissueRequestList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		List<RfidTagReissueRequest> tempRfidTagReissueRequestList = new ArrayList<RfidTagReissueRequest>();

		for (RfidTagReissueRequest rfidTagReissueRequest : rfidTagReissueRequestList) {

			if(rfidTagReissueRequest == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_REISSUE_TAG_REQEUST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_REISSUE_TAG_REQEUST_MESSAGE);
				return obj;
			}

			if(rfidTagReissueRequest.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_REISSUE_TAG_REQUEST_MESSAGE);
				return obj;
			}

			if(rfidTagReissueRequest.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_REISSUE_TAG_REQUEST_MESSAGE);
				return obj;
			}

			tempRfidTagReissueRequestList.add(rfidTagReissueRequest);
		}

		rfidTagReissueRequestRepository.delete(tempRfidTagReissueRequestList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public void deleteReissueRequestDetail(Long rfidTagReissueRequestSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "DELETE FROM rfid_tag_reissue_request_detail WHERE rfid_tag_reissue_request_seq = ? ";

		template.update(query, rfidTagReissueRequestSeq);
	}

}