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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.systemk.spyder.Dto.Request.BoxTagListBean;
import com.systemk.spyder.Dto.Request.InspectionBean;
import com.systemk.spyder.Dto.Request.ProductionTagListBean;
import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Dto.Request.ReleaseUpdateBean;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Entity.External.RfidTagIf;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleSubDetailLog;
import com.systemk.spyder.Entity.Main.TempProductionReleaseBox;
import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Entity.Main.TempProductionReleaseStyle;
import com.systemk.spyder.Entity.Main.TempProductionReleaseTag;
import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;
import com.systemk.spyder.Entity.Main.TempProductionStorageStyle;
import com.systemk.spyder.Entity.Main.TempProductionStorageTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidIb01IfRepository;
import com.systemk.spyder.Repository.External.RfidTagIfRepository;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.TempProductionReleaseHeaderRepository;
import com.systemk.spyder.Repository.Main.TempProductionStorageHeaderRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.ProductionStorageRfidTagSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.OpenDbService;
import com.systemk.spyder.Service.ProductionStorageLogService;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.TempProductionService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.ValidBarcode;
import com.systemk.spyder.Service.CustomBean.ValidTag;
import com.systemk.spyder.Service.Mapper.RfidCountRowMapper;
import com.systemk.spyder.Util.SecurityUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class ProductionStorageRfidTagServiceImpl implements ProductionStorageRfidTagService{

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private RfidTagIfRepository rfidTagIfRepository;

	@Autowired
	private TempProductionStorageHeaderRepository tempProductionStorageHeaderRepository;

	@Autowired
	private TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository;

	@Autowired
	private TempProductionService tempProductionService;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private RfidIb01IfRepository rfidIb01IfRepository;

	@Autowired
	private ErpService erpService;

	@Autowired
	private OpenDbService openDbService;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorageRfidTag> findAll(Long seq, String stat, String search, String option, Pageable pageable) throws Exception {

		Page<ProductionStorageRfidTag> page = null;

		Specifications<ProductionStorageRfidTag> specifications = Specifications.where(ProductionStorageRfidTagSpecification.productionStorageSeqEqual(seq));

		if(!stat.equals("all")){
			specifications = specifications.and(ProductionStorageRfidTagSpecification.statEqual(stat));
		}

		if(!search.equals("") && option.equals("barcode")){
			specifications = specifications.and(ProductionStorageRfidTagSpecification.barcodeContaining(search));
		} else if(!search.equals("") && option.equals("rfidTag")){
			specifications = specifications.and(ProductionStorageRfidTagSpecification.rfidTagContaining(search));
		}

		page = productionStorageRfidTagRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductionStorageRfidTag> findAll(String style, String color, String size) throws Exception {

		ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSize(style, color, size);

		Specifications<ProductionStorageRfidTag> specifications = Specifications.where(ProductionStorageRfidTagSpecification.erpKeyEqual(productMaster.getErpKey()))
																				.and(ProductionStorageRfidTagSpecification.statEqual("2"));

		return productionStorageRfidTagRepository.findAll(specifications);
	}


	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorageRfidTag> findAll(Long companySeq, Pageable pageable) throws Exception {

		Page<ProductionStorageRfidTag> page = null;

		CompanyInfo companyInfo = companyInfoRepository.findOne(companySeq);

		page = productionStorageRfidTagRepository.findByStatAndCustomerCd("2", companyInfo.getCode(), pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorageRfidTag> findStat(long seq, String stat, Pageable pageable) throws Exception {
		return productionStorageRfidTagRepository.findByProductionStorageSeqAndStat(seq, stat, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProductionStorageRfidTag> findStartEndRfidSeq(Long seq, String startRfidSeq, String endRfidSeq, Pageable pageable) throws Exception {

		Page<ProductionStorageRfidTag> page = null;

		Specifications<ProductionStorageRfidTag> specifications = Specifications.where(ProductionStorageRfidTagSpecification.productionStorageSeqEqual(seq));

		specifications = specifications.and(ProductionStorageRfidTagSpecification.startEndRfidSeqBetween(startRfidSeq, endRfidSeq));

		page = productionStorageRfidTagRepository.findAll(specifications, pageable);

		return page;
	}

	/**
	 * 웹, 스타일 하나에 대한 전부 입고 요청
	 */
	@Transactional
	@Override
	public void inspectionWeb(long seq) throws Exception {

		Date startDate = new Date();

		ProductionStorage production = productionStorageRepository.findOne(seq);
		List<ProductionStorageRfidTag> checkRfidList = productionStorageRfidTagRepository.findByProductionStorageSeqAndStat(production.getProductionStorageSeq(), "1");

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

		if(checkRfidList.size() > 0){

			for (ProductionStorageRfidTag tag : checkRfidList) {
				// 생산 입고 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(tag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("2");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);
			}

			rfidTagHistoryRepository.save(rfidTagHistoryList);

			// 생산 미검수 > 입고 업데이트
			update(production.getProductionStorageSeq(), user.getUserSeq(), "2", "1");

			ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
			productionStorageSeqList.add(production.getProductionStorageSeq());

			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "1");
		}
	}


	/**
	 * 모바일 입고 완료 업데이트
	 * ERP 부분만 업데이트에서 우리가 다 처리하도록 수정
	 */
	@Transactional
	@Override
	public Map<String, Object> inspectionMobile(InspectionBean inspectionBean) throws Exception{

		Date startDate = new Date();

		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();

		boolean success = false;

		int count = 0;

		ArrayList<ProductionTagListBean> returnTagList = new ArrayList<ProductionTagListBean>();

		for (ProductionTagListBean tempProduction : inspectionBean.getInspectionList()){

			ProductionStorage production = productionStorageRepository.findOne(tempProduction.getProductionStorageSeq());

			UserInfo userInfo = userInfoRepository.findOne(inspectionBean.getUserSeq());

			ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
			ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();

			ProductionTagListBean returnTag = new ProductionTagListBean();
			returnTag.setProductionStorageSeq(tempProduction.getProductionStorageSeq());
			List<String> returnStrList = new ArrayList<String>();

			for (String tagStr : tempProduction.getTagList()) {

				ProductionStorageRfidTag tag = productionStorageRfidTagRepository.findByRfidTagAndStat(tagStr, "1");

				if(tag != null){

					// 생산 입고 태그 히스토리 등록
					RfidTagHistory rfidTagHistory = new RfidTagHistory();

					rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
					rfidTagHistory.setErpKey(tag.getErpKey());
					rfidTagHistory.setRfidTag(tag.getRfidTag());
					rfidTagHistory.setRfidSeq(tag.getRfidSeq());
					rfidTagHistory.setWork("2");
					rfidTagHistory.setRegUserInfo(userInfo);
					rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
					rfidTagHistory.setRegDate(new Date());

					rfidTagHistoryList.add(rfidTagHistory);

					// ERP 태그 테이블 업데이트
//					RfidTagIf rfidTagIf = rfidTagIfRepository.findByRfidTagIfKeyTagRfid(tag.getRfidTag());
//					rfidTagIf.setTagStat("생산입고완료");
//					rfidTagIfList.add(rfidTagIf);

					// 생산 미검수 > 입고 업데이트
					tag.setStat("2");
					tag.setUpdUserInfo(userInfo);
					tag.setUpdDate(new Date());

					productionStorageRfidTagList.add(tag);

					count ++;
				} else {
					returnStrList.add(tagStr);
				}
			}

			if(returnStrList.size() > 0){
				returnTag.setTagList(returnStrList);
				returnTagList.add(returnTag);
			}

			rfidTagHistoryRepository.save(rfidTagHistoryList);
			productionStorageRfidTagRepository.save(productionStorageRfidTagList);
			rfidTagIfRepository.save(rfidTagIfList);

			if(count > 0){

//				productionStorageRfidTagRepository.flush();

				ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
				productionStorageSeqList.add(production.getProductionStorageSeq());

				productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "1");
			}
		}

		success = true;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", success ? ApiResultConstans.SUCCESS : ApiResultConstans.ERROR);
		map.put("resultMessage", success ? ApiResultConstans.SUCCESS_MESSAGE : ApiResultConstans.ERROR_MESSAGE);

		if(!returnTagList.isEmpty()){
			map.put("returnTagList", returnTagList);
		}

		return map;
	}

	/**
	 * 웹, 체크된 스타일 입고 처리
	 */
	@Transactional
	@Override
	public boolean inspectionWebList(List<ProductionStorage> productionStorageList) throws Exception {

		Date startDate = new Date();

		boolean success = false;

		LoginUser user = SecurityUtil.getCustomUser();
		UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

		int count = 0;

		for (ProductionStorage production : productionStorageList){

			ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

			List<ProductionStorageRfidTag> rfidTagList = productionStorageRfidTagRepository.findByProductionStorageSeqAndStat(production.getProductionStorageSeq(), "1");

			for (ProductionStorageRfidTag tag : rfidTagList) {

				// 생산 입고 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(tag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("2");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);


				count ++;
			}

			// 생산 미검수 > 입고 업데이트
			update(production.getProductionStorageSeq(), user.getUserSeq(), "2", "1");

			// ERP 태그 테이블 업데이트
//			erpService.updateRfidTagAllStat("생산입고완료", production.getBartagMaster());

			rfidTagHistoryRepository.save(rfidTagHistoryList);

			if(count > 0){

				ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
				productionStorageSeqList.add(production.getProductionStorageSeq());

				productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "1");
			}
		}

		success = true;

		return success;
	}

	/**
	 * 일괄 입고 처리, 배치로 작동
	 */
	@Transactional
	@Override
	public boolean inspectionBatch(BatchTrigger trigger) throws Exception {

		Date startDate = new Date();

		boolean success = false;

		UserInfo userInfo = trigger.getRegUserInfo();

		int count = 0;

		List<ProductionStorage> productionStorageList = productionStorageRepository.findByCompanyInfoCompanySeq(Long.valueOf(trigger.getExplanatory()));

		for (ProductionStorage production : productionStorageList){

			ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

			List<ProductionStorageRfidTag> rfidTagList = productionStorageRfidTagRepository.findByProductionStorageSeqAndStat(production.getProductionStorageSeq(), "1");

			for (ProductionStorageRfidTag tag : rfidTagList) {

				// 생산 입고 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(tag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("2");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

				count ++;
			}

			// 생산 미검수 > 입고 업데이트
			update(production.getProductionStorageSeq(), userInfo.getUserSeq(), "2", "1");

			// ERP 태그 테이블 업데이트
//			erpService.updateRfidTagAllStat("생산입고완료", production.getBartagMaster());

			rfidTagHistoryRepository.save(rfidTagHistoryList);

			if(count > 0){

				ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
				productionStorageSeqList.add(production.getProductionStorageSeq());

				productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "1");
			}
		}

		success = true;

		return success;
	}

	/**
	 * 생산 출고 완료
	 * 현재 테스트 용도로만 사용
	 */
	@Transactional
	@Override
	public Map<String, Object> releaseComplete(ReleaseBean releaseBean) throws Exception {

		Date startDate = new Date();

		boolean success = false;

		UserInfo userInfo = userInfoRepository.findOne(releaseBean.getUserSeq());

		ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();

		ArrayList<ProductionStorageRfidTag> productionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();

		ArrayList<BoxTagListBean> returnBoxList = new ArrayList<BoxTagListBean>();

		Long companySeq = Long.valueOf(0);

		for(BoxTagListBean box : releaseBean.getBoxList()){

			BoxInfo boxInfo = boxInfoRepository.findByBoxSeqAndStat(box.getBoxSeq(), "1");

			int count = 0;

			if(count == 0){
				companySeq = boxInfo.getStartCompanyInfo().getCompanySeq();
			}

			List<RfidTagBean> returnRfidTagList = new ArrayList<RfidTagBean>();

			if(boxInfo != null){

				boolean duplicationCheck = true;

				ArrayList<ProductionStorageRfidTag> tempProductionStorageRfidTagList = new ArrayList<ProductionStorageRfidTag>();

				// 중복태그 확인
				for(RfidTagBean rfidTag : box.getRfidTagList()){
					ProductionStorageRfidTag productionRfidTag = productionStorageRfidTagRepository.findByRfidTagAndStat(rfidTag.getRfidTag(), "2");

					if(productionRfidTag == null){
						duplicationCheck = false;
						returnRfidTagList.add(rfidTag);
					} else {
						tempProductionStorageRfidTagList.add(productionRfidTag);
					}
				}

				// 중복태그가 없을 경우 출고 로직타기
				if(duplicationCheck){

					for(ProductionStorageRfidTag productionRfidTag : tempProductionStorageRfidTagList){

						// 생산 재고 > 출고로 업데이트
						productionRfidTag.setStat("3");
						productionRfidTag.setUpdUserInfo(userInfo);
						productionRfidTag.setUpdDate(new Date());
						productionRfidTag.setBoxBarcode(boxInfo.getBarcode());
						productionRfidTag.setBoxSeq(box.getBoxSeq());

						productionStorageRfidTagList.add(productionRfidTag);
						productionStorageSeqList.add(productionRfidTag.getProductionStorageSeq());

						// 생산 출고 태그 히스토리 저장
						RfidTagMaster tag = rfidTagMasterRepository.findByRfidTag(productionRfidTag.getRfidTag());

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

						count ++;

					}
				}

				// 박스 검증 완료됐으며 작업한 태그가 있을 경우 업데이트
				if(count > 0){
					boxInfo.setUpdUserInfo(userInfo);
					boxInfo.setUpdDate(new Date());
					boxInfo.setStat("2");

					Date arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseBean.getArrivalDate());
					boxInfo.setArrivalDate(arrivalDate);

					boxInfoList.add(boxInfo);

				} else {
					box.setBoxResultCode(ApiResultConstans.NOT_FIND_RFID_TAG);
					box.setBoxResultMessage(ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);
					box.setRfidTagList(returnRfidTagList);
					returnBoxList.add(box);
				}
			} else {
				box.setBoxResultCode(ApiResultConstans.NOT_FIND_BOX);
				box.setBoxResultMessage(ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				returnBoxList.add(box);
			}
		}

		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		boxInfoRepository.save(boxInfoList);
		rfidTagIfRepository.save(rfidTagIfList);

		boxInfoRepository.flush();
		productionStorageRfidTagRepository.flush();

		productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", companySeq);

		workLine ++;

		// 물류 입고 예정 정보 저장
		storageScheduleLogService.save(boxInfoList, userInfo, workLine, "OP-R", null);

		success = true;

		// 알림 추가
		userNotiService.save("RFID 태그 생산 출고 완료되었습니다.", userInfo, "production", Long.valueOf(0));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", success ? ApiResultConstans.SUCCESS : ApiResultConstans.ERROR);
		map.put("resultMessage", success ? ApiResultConstans.SUCCESS_MESSAGE : ApiResultConstans.ERROR_MESSAGE);

		if(!returnBoxList.isEmpty()){
			map.put("returnBoxList", returnBoxList);
		}

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> releaseMiddleWareTest(ReleaseBean releaseBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		TempProductionReleaseHeader header = new TempProductionReleaseHeader();

		header.setBatchYn("N");
		header.setCompleteYn("N");
		header.setRequestYn("N");
		header.setRegDate(new Date());
		header.setStat("1");
		header.setType("2");
		header.setUserSeq(releaseBean.getUserSeq());
		header.setArrivalDate(releaseBean.getArrivalDate().replaceAll("-", ""));
		header.setBoxList(new HashSet<TempProductionReleaseBox>());

		for(BoxTagListBean tempBox : releaseBean.getBoxList()) {

			TempProductionReleaseBox releaseBox = new TempProductionReleaseBox();
			releaseBox.setBarcode(tempBox.getBarcode());
			releaseBox.setStyleList(new HashSet<TempProductionReleaseStyle>());

			for(RfidTagBean tempRfidTag : tempBox.getRfidTagList()) {
				String erpKey = tempRfidTag.getRfidTag().substring(0, 10);
				String rfidSeq = tempRfidTag.getRfidTag().substring(tempRfidTag.getRfidTag().length() -5, tempRfidTag.getRfidTag().length());

				boolean pushFlag = true;

				for(TempProductionReleaseStyle releaseStyle : releaseBox.getStyleList()) {
					if(releaseStyle.getErpKey().equals(erpKey) && startEndRfidSeqCheck(releaseStyle, rfidSeq)) {

						TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();
						releaseTag.setRfidTag(tempRfidTag.getRfidTag());
						releaseStyle.getTagList().add(releaseTag);

						pushFlag = false;
						break;
					}
				}

				if(pushFlag) {
					ProductionStorageRfidTag productionRfidTag = productionStorageRfidTagRepository.findByErpKeyAndRfidSeq(erpKey, rfidSeq);
					ProductionStorage productionStorage = productionStorageRepository.findOne(productionRfidTag.getProductionStorageSeq());

					TempProductionReleaseStyle releaseStyle = new TempProductionReleaseStyle();

					releaseStyle.setProductionStorageSeq(productionStorage.getProductionStorageSeq());
					releaseStyle.setStyle(productionStorage.getBartagMaster().getStyle());
					releaseStyle.setColor(productionStorage.getBartagMaster().getColor());
					releaseStyle.setSize(productionStorage.getBartagMaster().getSize());
					releaseStyle.setErpKey(productionStorage.getBartagMaster().getErpKey());
					releaseStyle.setStartRfidSeq(productionStorage.getBartagMaster().getStartRfidSeq());
					releaseStyle.setEndRfidSeq(productionStorage.getBartagMaster().getEndRfidSeq());
					releaseStyle.setTagList(new HashSet<TempProductionReleaseTag>());

					TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();

					releaseTag.setRfidTag(tempRfidTag.getRfidTag());
					releaseStyle.getTagList().add(releaseTag);

					releaseBox.getStyleList().add(releaseStyle);
				}
			}

			header.getBoxList().add(releaseBox);
		}

		tempProductionReleaseHeaderRepository.save(header);

		for(TempProductionReleaseBox releaseBox : header.getBoxList()) {
			releaseBox.setTempHeaderSeq(header.getTempHeaderSeq());

			for(TempProductionReleaseStyle releaseStyle : releaseBox.getStyleList()) {
				releaseStyle.setTempHeaderSeq(header.getTempHeaderSeq());

				for(TempProductionReleaseTag releaseTag : releaseStyle.getTagList()) {
					releaseTag.setTempHeaderSeq(header.getTempHeaderSeq());
				}
			}
		}

		tempProductionReleaseHeaderRepository.save(header);
		tempProductionReleaseHeaderRepository.flush();

		obj.put("header", header);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> barcodeValid(String barcode, String type) throws Exception {

		// TODO Auto-generated method stub
		Map<String, Object> obj = new HashMap<String, Object>();

		if(barcode.equals("") || type.equals("")){
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		if(type.equals("1")){

			StorageScheduleLog targetScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

			// 입고예정정보 확인
			if(targetScheduleLog == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

			// 폐기 처리된 것인지 확인
			if(targetScheduleLog.getDisuseYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
				obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
				return obj;
			}

			// 이전 바코드 출고여부 확인..9/25일 수정...이전 바코드 출고 이전상태에서만 상품 수정 가능하도록 - Cesil
			if(targetScheduleLog.getBoxInfo().getStat().equals("3")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return obj;
			}


			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

			for(StorageScheduleDetailLog detailLog : targetScheduleLog.getStorageScheduleDetailLog()){

				Map<String, Object> detailObj = new HashMap<String, Object>();

				detailObj.put("style", detailLog.getStyle());
				detailObj.put("color", detailLog.getColor());
				detailObj.put("size", detailLog.getSize());
				detailObj.put("orderDegree", detailLog.getOrderDegree());
				detailObj.put("amount", detailLog.getAmount());

				List<Map<String, Object>> rfidTagList = new ArrayList<Map<String, Object>>();

				for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){

					Map<String, Object> subDetailObj = new HashMap<String, Object>();
					subDetailObj.put("rfidTag", subDetailLog.getRfidTag());

					rfidTagList.add(subDetailObj);
				}

				detailObj.put("rfidTagList", rfidTagList);

				styleList.add(detailObj);
			}

			obj.put("resultCode", ApiResultConstans.SUCCESS);
			obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
			obj.put("styleList", styleList);

		} else if(type.equals("2")){

			StorageScheduleLog checkScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

			// 신규 바코드가 입고예정정보에 있는지 확인
			if(checkScheduleLog != null){
				obj.put("resultCode", ApiResultConstans.MAPPING_BOX);
				obj.put("resultMessage", ApiResultConstans.MAPPING_BOX_MESSAGE);
				return obj;
			}

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

			// 신규 바코드가 유효한지 확인
			if(boxInfo == null){
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return obj;
			}

			// 신규 바코드가 맵핑 가능한지 확인
			if(!boxInfo.getStat().equals("1")){
				obj.put("resultCode", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE);
				obj.put("resultMessage", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE_MESSAGE);
				return obj;
			}

			List<ProductionStorage> productionStorageList = productionStorageRepository.findByCompanyInfoCompanySeq(boxInfo.getStartCompanyInfo().getCompanySeq());
			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

			for(ProductionStorage production : productionStorageList){

				Map<String, Object> tempObj = new HashMap<String, Object>();

				tempObj.put("style", production.getBartagMaster().getStyle());
				tempObj.put("color", production.getBartagMaster().getColor());
				tempObj.put("size", production.getBartagMaster().getSize());
				tempObj.put("erpKey", production.getBartagMaster().getErpKey());
				tempObj.put("orderDegree", production.getBartagMaster().getOrderDegree());

				styleList.add(tempObj);
			}

			obj.put("resultCode", ApiResultConstans.SUCCESS);
			obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
			obj.put("styleList", styleList);
		}

		return obj;
	}

	/**
	 * 상품 수정
	 */
	@Transactional
	@Override
	public Map<String, Object> releaseUpdate(ReleaseUpdateBean releaseBean) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		List<Long> productionStorageSeqList = new ArrayList<Long>();

		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<String> rfidTagList = new ArrayList<String>();
		List<Map<String, Object>> returnTagList = new ArrayList<Map<String, Object>>();

		UserInfo userInfo = userInfoRepository.findOne(releaseBean.getUserSeq());

		// 사용자 정보 확인
		if(userInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		StorageScheduleLog targetScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(releaseBean.getTargetBoxBarcode());

		// 입고예정정보 확인
		if(targetScheduleLog == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		// 폐기 처리된 것인지 확인
		if(targetScheduleLog.getDisuseYn().equals("Y")){
			obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
			obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
			return obj;
		}

		StorageScheduleLog checkScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(releaseBean.getBoxInfo().getBarcode());

		// 신규 바코드가 입고예정정보에 있는지 확인
		if(checkScheduleLog != null){
			obj.put("resultCode", ApiResultConstans.MAPPING_BOX);
			obj.put("resultMessage", ApiResultConstans.MAPPING_BOX_MESSAGE);
			return obj;
		}

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(releaseBean.getBoxInfo().getBarcode());

		// 신규 바코드가 유효한지 확인
		if(boxInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		// 신규 바코드가 맵핑 가능한지 확인
		if(!boxInfo.getStat().equals("1")){
			obj.put("resultCode", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE);
			obj.put("resultMessage", ApiResultConstans.ONLY_RELEASE_READY_BOX_POSSIBLE_MESSAGE);
			return obj;
		}

		// 이전 바코드와 신규 바코드 생성 업체가 같은지 확인
		if(targetScheduleLog.getBoxInfo().getStartCompanyInfo().getCompanySeq() != boxInfo.getStartCompanyInfo().getCompanySeq()){
			obj.put("resultCode", ApiResultConstans.EQAUL_COMPANY_BOX_POSSIBLE);
			obj.put("resultMessage", ApiResultConstans.EQAUL_COMPANY_BOX_POSSIBLE_MESSAGE);
			return obj;
		}

		// List<String> 형태로 변경
		for(RfidTagBean rfidTag : releaseBean.getBoxInfo().getRfidTagList()){
			rfidTagList.add(rfidTag.getRfidTag());
		}

		// productionStorageRfidTag 검증
		List<ProductionStorageRfidTag> validRfidTagList = productionStorageRfidTagRepository.findByRfidTagIn(rfidTagList);

		for(ProductionStorageRfidTag productionRfidTag : validRfidTagList){

			if(productionRfidTag != null &&
				productionRfidTag.getStat().equals("2") || (productionRfidTag.getStat().equals("3") && productionRfidTag.getBoxBarcode().equals(releaseBean.getTargetBoxBarcode()))){
				continue;
			}

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", productionRfidTag.getRfidTag());
			returnTagObj.put("stat", productionRfidTag.getStat());
			returnTagObj.put("boxBarcode", productionRfidTag.getBoxBarcode() == null ? "" : productionRfidTag.getBoxBarcode());

			returnTagList.add(returnTagObj);
		}

		if (!returnTagList.isEmpty()) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		// 생산 출고예정 태그 productionStorageSeq 가져오기
		List<Long> initProductionStorageSeqList = findByBoxProductionSeq(targetScheduleLog.getBoxInfo().getBoxSeq());

		// 생산 출고된 박스 정보 삭제
		deleteBoxInfo(userInfo.getUserSeq(), targetScheduleLog.getBoxInfo().getBoxSeq());

		// 물류 입고 예정 태그 목록 조회
		List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(targetScheduleLog.getBoxInfo().getBoxSeq());

		// 물류 입고예정 태그 distributionStorageSeq 가져오기
		List<Long> initDistributionStorageSeqList = distributionStorageRfidTagService.findByBoxDistributionSeq(targetScheduleLog.getBoxInfo().getBoxSeq());

		// 믈루 입고 정보 삭제
		distributionStorageRfidTagService.deleteBoxInfo(targetScheduleLog.getBoxInfo().getBoxSeq());

		targetScheduleLog.setDisuseYn("Y");
		targetScheduleLog.setUpdDate(new Date());
		targetScheduleLog.setUpdUserInfo(userInfo);

		targetScheduleLog.getBoxInfo().setStat("4");
		targetScheduleLog.getBoxInfo().setUpdDate(new Date());
		targetScheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

		// 생산 출고예정 정보 사용안함 처리
		storageScheduleLogRepository.save(targetScheduleLog);

		// 박스 사용 안함 처리
		boxInfoRepository.save(targetScheduleLog.getBoxInfo());

		// 타겟 생산, 물류 수량 변경
		productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
		distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");

		for(ProductionStorageRfidTag productionRfidTag : validRfidTagList){

			// 생산 출고 태그 히스토리 저장
			RfidTagHistory rfidTagHistory = new RfidTagHistory();

			rfidTagHistory.setBarcode(productionRfidTag.getErpKey() + productionRfidTag.getRfidSeq());
			rfidTagHistory.setErpKey(productionRfidTag.getErpKey());
			rfidTagHistory.setRfidTag(productionRfidTag.getRfidTag());
			rfidTagHistory.setRfidSeq(productionRfidTag.getRfidSeq());
			rfidTagHistory.setWork("3");
			rfidTagHistory.setRegUserInfo(userInfo);
			rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
			rfidTagHistory.setRegDate(new Date());

			productionStorageSeqList.add(productionRfidTag.getProductionStorageSeq());
			rfidTagHistoryList.add(rfidTagHistory);
		}

		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setUpdDate(new Date());
		boxInfo.setStat("2");
		boxInfo.setArrivalDate(new Date());

		boxInfoList.add(boxInfo);

		boxInfoRepository.save(boxInfo);
		boxInfoRepository.flush();

		// 생산 재고 > 출고로 업데이트
		productionStorageRfidTagRepository.updateBoxMappingTag(rfidTagList, boxInfo.getBoxSeq(), boxInfo.getBarcode(), userInfo.getUserSeq());

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", boxInfo.getStartCompanyInfo().getCompanySeq());
		workLine ++;

		// 물류 입고 예정 정보 저장
		List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogService.save(boxInfoList, userInfo, workLine, "OP-R", null);

		rfidTagHistoryRepository.save(rfidTagHistoryList);

		// 신규 생산 수량 변경
		productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");

		boolean confirmFlag = false;

		// 확정 완료된 상품출고를 수정할 경우
		if(targetScheduleLog.getConfirmYn().equals("Y") && targetScheduleLog.getBatchYn().equals("Y") &&
		   targetScheduleLog.getCompleteYn().equals("N") && targetScheduleLog.getCompleteBatchYn().equals("N")){

			distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "1", "2");

			confirmFlag = true;

			// ERP 업데이트 처리(사용안함 바코드)
			erpService.disuseErpStorageSchedule(targetScheduleLog);

			// WMS 업데이트 처리(사용안함 바코드)
			openDbService.disuseOpenDbScheduleHeader(targetScheduleLog);

		// 반송 처리된 상품출고를 수정할 경우
		} else if(targetScheduleLog.getConfirmYn().equals("Y") && targetScheduleLog.getBatchYn().equals("Y") &&
		   targetScheduleLog.getCompleteYn().equals("Y") && targetScheduleLog.getCompleteBatchYn().equals("Y")) {

			for (DistributionStorageRfidTag tempRfidTag : distributionRfidTagList) {

				// 물류 입고 반품 히스토리 저장
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tempRfidTag.getErpKey() + tempRfidTag.getRfidSeq());
				rfidTagHistory.setErpKey(tempRfidTag.getErpKey());
				rfidTagHistory.setRfidTag(tempRfidTag.getRfidTag());
				rfidTagHistory.setRfidSeq(tempRfidTag.getRfidSeq());
				rfidTagHistory.setWork("18");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);
			}

			rfidIb01IfRepository.save(erpService.saveStorageListRollback(targetScheduleLog));

			confirmFlag = true;
		}

		// 확정 완료, 물류 입고 반품 출고 수정일 경우 신규 바코드 자동 확정 처리
		if(confirmFlag) {
			// 신규 바코드 자동 컨펌
			for (StorageScheduleLog scheduleLog : storageScheduleLogList) {

				scheduleLog.setConfirmYn("Y");
				scheduleLog.setConfirmDate(new Date());
				scheduleLog.setUpdDate(new Date());
				scheduleLog.setUpdUserInfo(userInfo);
			}
		}

		storageScheduleLogRepository.save(storageScheduleLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public void update(long seq, long userSeq, String updateStat, String targetStat) {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.production_storage_rfid_tag SET stat = ?, upd_user_seq = ?, upd_date = getdate() WHERE production_storage_seq = ? AND stat = ?";
		template.update(query,
				updateStat,
				userSeq,
				seq,
				targetStat);

	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(long productionStorageSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productionStorageSeq", productionStorageSeq);

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN production_storage_rfid_tag.stat = '1' THEN stat END) stat1_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '2' THEN stat END) stat2_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '3' THEN stat END) stat3_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '4' THEN stat END) stat4_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '5' THEN stat END) stat5_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '6' THEN stat END) stat6_amount, ");
		query.append("COUNT(CASE WHEN production_storage_rfid_tag.stat = '7' THEN stat END) stat7_amount ");
		query.append("FROM production_storage_rfid_tag ");
		query.append("WHERE production_storage_seq = :productionStorageSeq ");

		return nameTemplate.queryForObject(query.toString(), params, new RfidCountRowMapper());
	}


	@Transactional
	@Override
	public void deleteBoxInfo(Long userSeq, Long boxSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.production_storage_rfid_tag SET stat = '2', upd_user_seq = ?, upd_date = getdate(), box_barcode = null, box_seq = null " +
						"WHERE box_seq = ? ";

		template.update(query,
						userSeq,
						boxSeq);
	}

	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> storageComplete(Long seq) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<Map<String, Object>> returnTagList = new ArrayList<Map<String, Object>>();

		TempProductionStorageHeader header = tempProductionStorageHeaderRepository.findOne(seq);

		if(header == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_TEMP_HEADER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_TEMP_HEADER_MESSAGE);
			return obj;
		}

		// M/W Request 정보 수신
		header.setRequestYn("Y");
		header.setRequestDate(new Date());

		UserInfo userInfo = userInfoRepository.findOne(header.getUserSeq());

		// 사용자 검증
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		// productStorageSeq 검증
		List<Long> productionStorageSeqList = tempProductionService.validStorageAllSeq(header.getTempHeaderSeq());

		for(Long productionStorageSeq : productionStorageSeqList){

			ProductionStorage production = productionStorageRepository.findOne(productionStorageSeq);

			if (production == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
				return obj;
			}

			// 종결 여부 체크
			if (production.getBartagMaster().getStat().equals("6")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_BARTAG);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BARTAG_MESSAGE);
				return obj;
			}

			// 폐기 여부 체크
			if (production.getBartagMaster().getStat().equals("7")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_BARTAG);
				obj.put("resultMessage", ApiResultConstans.DISUSE_BARTAG_MESSAGE);
				return obj;
			}
		}

		// 태그 검증
		List<ValidTag> rfidTagList = tempProductionService.validStorageAllTag(header.getTempHeaderSeq());

		for (ValidTag validTag : rfidTagList) {

			if(validTag.getTargetStat() != null && validTag.getTargetStat().equals("1")) {
				continue;
			}

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", validTag.getRfidTag());
			returnTagObj.put("stat", validTag.getTargetStat() == null ? "" : validTag.getTargetStat());
			returnTagObj.put("boxBarcode", validTag.getTargetBoxBarcode() == null ? "" : validTag.getTargetBoxBarcode());

			returnTagList.add(returnTagObj);
		}

		if(!returnTagList.isEmpty()) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		// temp 태그 검증
		List<ValidTag> tempTagList = tempProductionService.validTempStorageAllTag(header.getTempHeaderSeq());

		for(ValidTag validTag : tempTagList) {

			if(validTag.getTargetRfidTag() == null) {
				continue;
			}

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", validTag.getRfidTag());
			returnTagObj.put("stat", "0");
			returnTagObj.put("boxBarcode", "");

			returnTagList.add(returnTagObj);
		}

		if(!returnTagList.isEmpty()) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		/*
		for (TempProductionStorageStyle style : header.getStyleList()){

			for(TempProductionStorageTag rfidTag : style.getTagList()) {

				RfidTagMaster tag = StringUtil.setRfidTagMaster(rfidTag.getRfidTag());

				// 생산 입고 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(rfidTag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("2");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);
			}
		}

		header.setCompleteYn("Y");
		header.setCompleteDate(new Date());

		tempProductionService.updateStorageAllTag(header.getTempHeaderSeq(), header.getUserSeq());

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		rfidTagIfRepository.save(rfidTagIfList);
		tempProductionStorageHeaderRepository.save(header);

		if(productionStorageSeqList.size() > 0){
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "1");
		}

		userNotiService.save("RFID 태그 생산 입고 완료되었습니다.", userInfo, "production", Long.valueOf(0));
		*/

		header.setCompleteYn("Y");
		header.setCompleteDate(new Date());
		header.setUpdDate(new Date());

		tempProductionStorageHeaderRepository.save(header);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> releaseComplete(Long seq) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<BoxTagListBean> returnBoxList = new ArrayList<BoxTagListBean>();
		List<Map<String, Object>> returnTagList = new ArrayList<Map<String, Object>>();

		TempProductionReleaseHeader header = tempProductionReleaseHeaderRepository.findOne(seq);

		// 헤더값 검증
		if(header == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_TEMP_HEADER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_TEMP_HEADER_MESSAGE);
			return obj;
		}

		header.setRequestYn("Y");
		header.setRequestDate(new Date());

		UserInfo userInfo = userInfoRepository.findOne(header.getUserSeq());

		// 사용자 검증
		if(userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		// 박스 검증
		for (TempProductionReleaseBox box : header.getBoxList()) {

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(box.getBarcode());

			if (boxInfo.getStat().equals("1")) {
				box.setBoxInfo(boxInfo);
				continue;
			}

			BoxTagListBean returnBox = new BoxTagListBean();
			returnBox.setBarcode(box.getBarcode());
			returnBox.setStat(boxInfo.getStat());
			returnBoxList.add(returnBox);
		}

		// 박스 데이터에 문제 있을시 리턴
		if (!returnBoxList.isEmpty()) {

			obj.put("returnBoxList", returnBoxList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_BOX_BARCODE_MESSAGE);
			return obj;
		}

		// temp 테이블에 검증, 배치작업 전 박스 데이터가 있는지 확인.
		List<ValidBarcode> tempBarcodeList = tempProductionService.validTempReleaseAllBox(header.getTempHeaderSeq());

		for(ValidBarcode tempBarcode : tempBarcodeList) {
			if(tempBarcode.getTargetBarcode() == null) {
				continue;
			}

			BoxTagListBean returnBox = new BoxTagListBean();
			returnBox.setBarcode(tempBarcode.getBarcode());
			returnBox.setStat("0");
			returnBoxList.add(returnBox);
		}

		// 박스 데이터에 문제 있을시 리턴
		if (!returnBoxList.isEmpty()) {

			obj.put("returnBoxList", returnBoxList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_BOX_BARCODE_MESSAGE);
			return obj;
		}

		// productStorageSeq 검증
		List<Long> productionStorageSeqList = tempProductionService.validReleaseAllSeq(header.getTempHeaderSeq());

		for (Long productionStorageSeq : productionStorageSeqList) {

			ProductionStorage production = productionStorageRepository.findOne(productionStorageSeq);

			if (production == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
				return obj;
			}

			// 종결 여부 체크
			if (production.getBartagMaster().getStat().equals("6")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_BARTAG);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_BARTAG_MESSAGE);
				return obj;
			}

			// 폐기 여부 체크
			if (production.getBartagMaster().getStat().equals("7")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_BARTAG);
				obj.put("resultMessage", ApiResultConstans.DISUSE_BARTAG_MESSAGE);
				return obj;
			}
		}

		// 태그 검증
		List<ValidTag> rfidTagList = tempProductionService.validReleaseAllTag(header.getTempHeaderSeq());

		for(ValidTag validTag : rfidTagList) {

			if(validTag.getTargetStat() != null && validTag.getTargetStat().equals("2")) {
				continue;
			}

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", validTag.getRfidTag());
			returnTagObj.put("stat", validTag.getTargetStat() == null ? "" : validTag.getTargetStat());
			returnTagObj.put("boxBarcode", validTag.getTargetBoxBarcode() == null ? "" : validTag.getTargetBoxBarcode());

			returnTagList.add(returnTagObj);
		}

		if(!returnTagList.isEmpty()) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		// temp 테이블에 검증, 배치작업 전 태그 데이터가 있는지 확인.
		List<ValidTag> tempTagList = tempProductionService.validTempReleaseAllTag(header.getTempHeaderSeq());

		for(ValidTag validTag : tempTagList) {

			if(validTag.getTargetRfidTag() == null) {
				continue;
			}

			Map<String, Object> returnTagObj = new HashMap<String, Object>();

			returnTagObj.put("rfidTag", validTag.getRfidTag());
			returnTagObj.put("stat", "0");
			returnTagObj.put("boxBarcode", validTag.getTargetBoxBarcode());

			returnTagList.add(returnTagObj);
		}

		if(!returnTagList.isEmpty()) {
			obj.put("returnTagList", returnTagList);
			obj.put("resultCode", ApiResultConstans.NOT_VALID_REQUEST_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_REQUEST_TAG_MESSAGE);
			return obj;
		}

		/*
		// 박스 상태 업데이트
		for(TempProductionReleaseBox box : header.getBoxList()){

			BoxInfo boxInfo = box.getBoxInfo();
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setUpdDate(new Date());
			boxInfo.setStat("2");

			Date arrivalDate = new SimpleDateFormat("yyyyMMdd").parse(header.getArrivalDate());
			boxInfo.setArrivalDate(arrivalDate);

			boxInfoList.add(boxInfo);

			// 태그 상태 업데이트
			for(TempProductionReleaseStyle style : box.getStyleList()) {
				tempProductionService.updateReleaseBoxTag(style.getTempStyleSeq(), header.getUserSeq(), boxInfo);

				productionStorageSeqList.add(style.getProductionStorageSeq());

				for(TempProductionReleaseTag rfidTag : style.getTagList()) {

					RfidTagMaster tag = StringUtil.setRfidTagMaster(rfidTag.getRfidTag());

					// 생산 출고 태그 히스토리 등록
					RfidTagHistory rfidTagHistory = new RfidTagHistory();

					rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
					rfidTagHistory.setErpKey(tag.getErpKey());
					rfidTagHistory.setRfidTag(rfidTag.getRfidTag());
					rfidTagHistory.setRfidSeq(tag.getRfidSeq());
					rfidTagHistory.setWork("3");
					rfidTagHistory.setRegUserInfo(userInfo);
					rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
					rfidTagHistory.setRegDate(new Date());

					rfidTagHistoryList.add(rfidTagHistory);
				}
			}
		}

		header.setCompleteYn("Y");
		header.setCompleteDate(new Date());

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		boxInfoRepository.save(boxInfoList);
		tempProductionReleaseHeaderRepository.save(header);

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", companySeq);

		workLine ++;

		// 물류 입고 예정 정보 저장
		storageScheduleLogService.save(boxInfoList, userInfo, workLine, "OP-R", null);

		if(productionStorageSeqList.size() > 0) {
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");
		}
		*/

		header.setCompleteYn("Y");
		header.setCompleteDate(new Date());
		header.setUpdDate(new Date());

		tempProductionReleaseHeaderRepository.save(header);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE psrt " +
						  "SET psrt.stat = '5', " +
						  	  "psrt.upd_date = getdate(), " +
						  	  "psrt.upd_user_seq = ? " +
						 "FROM production_storage_rfid_tag psrt " +
				   "INNER JOIN rfid_tag_reissue_request_detail rtrr " +
				   		   "ON psrt.rfid_tag = rtrr.rfid_tag " +
				   		  "AND rtrr.rfid_tag_reissue_request_seq = ?";

    	template.update(query, userSeq, rfidTagReissueSeq);
	}

	@Transactional
	@Override
	public List<Long> storageCompleteBatch(TempProductionStorageHeader header) throws Exception {

		Date startDate = new Date();

		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

		UserInfo userInfo = userInfoRepository.findOne(header.getUserSeq());
		List<Long> productionStorageSeqList = tempProductionService.validStorageAllSeq(header.getTempHeaderSeq());

		for (TempProductionStorageStyle style : header.getStyleList()){

			for(TempProductionStorageTag rfidTag : style.getTagList()) {

				RfidTagMaster tag = StringUtil.setRfidTagMaster(rfidTag.getRfidTag());

				// 생산 입고 태그 히스토리 등록
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(rfidTag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("2");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);
			}
		}

		tempProductionService.updateStorageAllTag(header.getTempHeaderSeq(), header.getUserSeq());
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		tempProductionStorageHeaderRepository.save(header);

		if(productionStorageSeqList.size() > 0){
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "2", "2");
		}

		// 알림 추가
		userNotiService.save("RFID 태그 생산 입고 완료되었습니다.", userInfo, "production", Long.valueOf(0));

		return productionStorageSeqList;
	}

	@Transactional
	@Override
	public List<Long> releaseCompleteBatch(TempProductionReleaseHeader header) throws Exception {

		List<Long> productionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		Date startDate = new Date();

		UserInfo userInfo = userInfoRepository.findOne(header.getUserSeq());
		Long companySeq = userInfo.getCompanyInfo().getCompanySeq();

		// 박스 상태 업데이트
		for(TempProductionReleaseBox box : header.getBoxList()){

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(box.getBarcode());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setUpdDate(new Date());
			boxInfo.setStat("2");

			Date arrivalDate = new SimpleDateFormat("yyyyMMdd").parse(header.getArrivalDate());
			boxInfo.setArrivalDate(arrivalDate);

			boxInfoList.add(boxInfo);

			// 태그 상태 업데이트
			for(TempProductionReleaseStyle style : box.getStyleList()) {

				tempProductionService.updateReleaseBoxTag(style.getTempStyleSeq(), header.getUserSeq(), boxInfo);

				for(TempProductionReleaseTag rfidTag : style.getTagList()) {

					RfidTagMaster tag = StringUtil.setRfidTagMaster(rfidTag.getRfidTag());

					// 생산 출고 태그 히스토리 등록
					RfidTagHistory rfidTagHistory = new RfidTagHistory();

					rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
					rfidTagHistory.setErpKey(tag.getErpKey());
					rfidTagHistory.setRfidTag(rfidTag.getRfidTag());
					rfidTagHistory.setRfidSeq(tag.getRfidSeq());
					rfidTagHistory.setWork("3");
					rfidTagHistory.setRegUserInfo(userInfo);
					rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
					rfidTagHistory.setRegDate(new Date());

					rfidTagHistoryList.add(rfidTagHistory);
				}
			}
		}

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		boxInfoRepository.save(boxInfoList);

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", companySeq);

		workLine ++;

		// 물류 입고 예정 정보 저장
		storageScheduleLogService.save(boxInfoList, userInfo, workLine, "OP-R", null);

		tempProductionReleaseHeaderRepository.save(header);

		if(productionStorageSeqList.size() > 0) {
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");
		}

		// 알림 추가
		userNotiService.save("RFID 태그 생산 출고 완료되었습니다.", userInfo, "production", Long.valueOf(0));

		return productionStorageSeqList;
	}

	public boolean startEndRfidSeqCheck(TempProductionReleaseStyle style, String rfidSeq) throws Exception {

		int startRfidSeq = Integer.parseInt(style.getStartRfidSeq());
		int endRfidSeq = Integer.parseInt(style.getEndRfidSeq());

		int checkSeq = Integer.parseInt(rfidSeq);

		boolean flag = false;

		for(int i=startRfidSeq; i <= endRfidSeq; i++) {
			if(i == checkSeq) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Long> findByBoxProductionSeq(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		String query = "SELECT psrt.production_storage_seq " +
						 "FROM production_storage_rfid_tag psrt " +
					    "WHERE psrt.box_seq = :boxSeq " +
					 "GROUP BY psrt.production_storage_seq";

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query, params,
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int row) throws SQLException {
				return new Long(rs.getLong("production_storage_seq"));
			}
		});
	}


}
