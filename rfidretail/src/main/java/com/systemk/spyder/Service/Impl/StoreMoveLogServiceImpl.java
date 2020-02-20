package com.systemk.spyder.Service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.BoxBean;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Dto.Request.StoreMoveBean;
import com.systemk.spyder.Dto.Request.StoreMoveListBean;
import com.systemk.spyder.Dto.Request.StyleBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StoreMoveDetailLog;
import com.systemk.spyder.Entity.Main.StoreMoveLog;
import com.systemk.spyder.Entity.Main.StoreMoveSubDetailLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.StoreMoveLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.StoreMoveLogSpecification;
import com.systemk.spyder.Repository.Main.Specification.StoreStorageSpecification;
import com.systemk.spyder.Service.BoxService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.StoreMoveLogService;
import com.systemk.spyder.Service.StoreStorageLogService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.RfidModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Service.CustomBean.Group.StoreMoveGroupModel;
import com.systemk.spyder.Service.Mapper.Group.StoreMoveGroupModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class StoreMoveLogServiceImpl implements StoreMoveLogService {

	@Autowired
	private StoreMoveLogRepository storeMoveLogRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private StoreStorageLogService storeStorageLogService;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private BoxService boxService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private ErpService erpService;

	@Transactional(readOnly = true)
	@Override
	public Page<StoreMoveLog> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq,
			String workYn, String confirmYn, String disuseYn, String type, Pageable pageable) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Page<StoreMoveLog> page = null;

		Specifications<StoreMoveLog> specifications = Specifications
				.where(StoreMoveLogSpecification.createDateBetween(startDate, endDate));

		if (type.equals("start") && startCompanySeq != 0) {
			specifications = specifications.and(StoreMoveLogSpecification.startCompanySeqEqual(startCompanySeq));

			if (endCompanySeq != 0) {
				specifications = specifications.and(StoreMoveLogSpecification.endCompanySeqEqual(endCompanySeq));
			}
		} else if (type.equals("end") && endCompanySeq != 0) {
			specifications = specifications.and(StoreMoveLogSpecification.endCompanySeqEqual(endCompanySeq));

			if (startCompanySeq != 0) {
				specifications = specifications.and(StoreMoveLogSpecification.startCompanySeqEqual(startCompanySeq));
			}
		}

		if (!workYn.equals("all") && type.equals("start")) {
			specifications = specifications.and(StoreMoveLogSpecification.startWorkYnEqual(workYn));
		} else if (!workYn.equals("all") && type.equals("end")) {
			specifications = specifications.and(StoreMoveLogSpecification.endWorkYnEqual(workYn));
		}

		if (!confirmYn.equals("all") && type.equals("start")) {
			specifications = specifications.and(StoreMoveLogSpecification.startConfirmYnEqual(confirmYn));
		} else if (!confirmYn.equals("all") && type.equals("end")) {
			specifications = specifications.and(StoreMoveLogSpecification.endConfirmYnEqual(confirmYn));
		}

		if (!disuseYn.equals("all")) {
			specifications = specifications.and(StoreMoveLogSpecification.disuseYnEqual(disuseYn));
		}

		page = storeMoveLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> detail(Long seq) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findOne(seq);

		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_DATA);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
			return obj;
		}

		Specifications<StoreStorage> specifications = Specifications.where(StoreStorageSpecification.companySeqEqual(storeMoveLog.getBoxInfo().getStartCompanyInfo().getCompanySeq()))
																	.and(StoreStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)))
																	.and(StoreStorageSpecification.totalAmountGreaterThan(Long.valueOf(0)));

		List<StoreStorage> storeStorageList = storeStorageRepository.findAll(specifications);

		ArrayList<Map<String, Object>> bartagList = new ArrayList<Map<String, Object>>();
		for (StoreStorage storage : storeStorageList) {

			Map<String, Object> tempMap = new LinkedHashMap<String, Object>();

			tempMap.put("erpKey",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
			tempMap.put("style",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
			tempMap.put("color",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
			tempMap.put("size",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
			tempMap.put("orderDegree",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getOrderDegree());
			tempMap.put("additionOrderDegree", storage.getDistributionStorage().getProductionStorage()
					.getBartagMaster().getAdditionOrderDegree());
			tempMap.put("startRfidSeq",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStartRfidSeq());
			tempMap.put("endRfidSeq",
					storage.getDistributionStorage().getProductionStorage().getBartagMaster().getEndRfidSeq());
			tempMap.put("stockAmount", storage.getStat2Amount());

			bartagList.add(tempMap);
		}

		obj.put("storeMoveDetail", storeMoveLog);
		obj.put("bartagList", bartagList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveSave(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (storeMoveBean.getCompanyType().equals("start")) {

			obj = storeMoveStartSave(storeMoveBean);

		} else if (storeMoveBean.getCompanyType().equals("end")) {

			obj = storeMoveEndSave(storeMoveBean);
		}

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveStartSave(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		// 박스 정보 확인
		if (boxInfoRepository.existsByBarcode(storeMoveBean.getBoxInfo().getBarcode())) {
			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);
			return obj;
		}

		CompanyInfo startCompanyInfo = companyInfoRepository.findOne(storeMoveBean.getStartCompanySeq());

		// 출발 업체 정보 확인
		if (startCompanyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_START_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_START_COMPANY_MESSAGE);
			return obj;
		}

		CompanyInfo endCompanyInfo = companyInfoRepository.findOne(storeMoveBean.getEndCompanySeq());

		// 도착 업체 정보 확인
		if (endCompanyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_END_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_END_COMPANY_MESSAGE);
			return obj;
		}

		// 해당 업체 태그인지 확인
		for (StyleBean style : storeMoveBean.getBoxInfo().getStyleList()) {

			for (RfidTagBean rfidTag : style.getRfidTagList()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				if (!storeRfidTag.getCustomerCd().equals(startCompanyInfo.getCode())) {
					obj.put("resultCode", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY);
					obj.put("resultMessage", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
					return obj;
				}
			}
		}

		BoxInfo boxInfo = new BoxInfo();

		boxInfo.setBarcode(storeMoveBean.getBoxInfo().getBarcode());
		boxInfo.setStartCompanyInfo(startCompanyInfo);
		boxInfo.setEndCompanyInfo(endCompanyInfo);

		boxInfo.setReturnYn("N");
		boxInfo.setRegDate(new Date());
		boxInfo.setRegUserInfo(userInfo);
		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setType("03");
		boxInfo.setStat("2");
		boxInfo.setCreateDate(CalendarUtil.convertFormat("yyyyMMdd"));

		boxInfo = boxInfoRepository.save(boxInfo);
		boxInfoList.add(boxInfo);

		for (StyleBean style : storeMoveBean.getBoxInfo().getStyleList()) {

			for (RfidTagBean rfidTag : style.getRfidTagList()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				storeRfidTag.setUpdUserInfo(userInfo);
				storeRfidTag.setUpdDate(new Date());
				storeRfidTag.setBoxInfo(boxInfo);

				storeStorageRfidTagList.add(storeRfidTag);
				storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
			}
		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		storeStorageRfidTagRepository.flush();

		Long workLine = maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()),
				boxInfo.getStartCompanyInfo().getCompanySeq());

		// 매장간 이동 예정 정보 저장
		save(boxInfoList, userInfo, ++workLine, storeMoveBean.getReturnType());

		// storeStorageLogService.save(storeStorageSeqList, userInfo, startDate,
		// "6SW", storeMoveBean.getType());

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveEndSave(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		ArrayList<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveBean.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		CompanyInfo startCompanyInfo = companyInfoRepository.findOne(storeMoveBean.getStartCompanySeq());

		// 출발 업체 정보 확인
		if (startCompanyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_START_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_START_COMPANY_MESSAGE);
			return obj;
		}

		CompanyInfo endCompanyInfo = companyInfoRepository.findOne(storeMoveBean.getEndCompanySeq());

		// 도착 업체 정보 확인
		if (endCompanyInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_END_COMPANY);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_END_COMPANY_MESSAGE);
			return obj;
		}

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(storeMoveBean.getBoxInfo().getBarcode());

		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 종결 여부 확인
		if (storeMoveLog.getDisuseYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 작업 여부 확인
		if (storeMoveLog.getStartWorkYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 확정 여부 확인
		if (storeMoveLog.getStartConfirmYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 받는 매장 작업 여부 확인
		if (storeMoveLog.getEndWorkYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 받는 매장 확정 여부 확인
		if (storeMoveLog.getEndConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		BoxInfo boxInfo = storeMoveLog.getBoxInfo();

		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);

		boxInfo = boxInfoRepository.save(boxInfo);
		boxInfoList.add(boxInfo);

		// 매장간 이동 도착 받는 작업 업데이트
		matching(storeMoveLog, storeMoveBean, userInfo);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;

	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveErp(String barcode, Long userSeq, String type) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(barcode);

		// 박스 정보 확인
		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		// ERP에서 요청된 매장간 이동인지 확인
		if (storeMoveLog.getErpYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.ONLY_ERP_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.ONLY_ERP_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 작업 여부 확인
		if (storeMoveLog.getStartWorkYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WORK_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 확정 여부 확인
		if (storeMoveLog.getStartConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 받는 매장 작업 여부 확인
		if (storeMoveLog.getEndWorkYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 받는 매장 확정 여부 확인
		if (storeMoveLog.getEndConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 종결 여부 확인
		if (storeMoveLog.getDisuseYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 해당 업체 태그인지 확인
		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

			for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository
						.findByRfidTag(subDetailLog.getRfidTag());

				if (!storeRfidTag.getCustomerCd().equals(storeMoveLog.getBoxInfo().getStartCompanyInfo().getCode())) {
					obj.put("resultCode", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY);
					obj.put("resultMessage", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
					return obj;
				}
			}
		}

		storeMoveLog.getBoxInfo().setUpdDate(new Date());
		storeMoveLog.getBoxInfo().setUpdUserInfo(userInfo);
		storeMoveLog.getBoxInfo().setStat("2");

		BoxInfo boxInfo = boxInfoRepository.save(storeMoveLog.getBoxInfo());

		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

			for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository
						.findByRfidTag(subDetailLog.getRfidTag());

				storeRfidTag.setUpdUserInfo(userInfo);
				storeRfidTag.setUpdDate(new Date());
				storeRfidTag.setBoxInfo(boxInfo);

				storeStorageRfidTagList.add(storeRfidTag);
				storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
			}
		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
//		storeStorageRfidTagRepository.flush();
		// boxInfoRepository.flush();

		// storeStorageLogService.save(storeStorageSeqList, userInfo, startDate,
		// "6SW", "2");

		storeMoveLog.setUpdDate(new Date());
		storeMoveLog.setUpdUserInfo(userInfo);
		storeMoveLog.setStartWorkYn("Y");
		storeMoveLog.setStartWorkDate(new Date());

		storeMoveLogRepository.save(storeMoveLog);

		// 알림 추가
		// userNotiService.save("RFID 매장간 이동 작업 저장되었습니다.", userInfo, "store",
		// Long.valueOf(0));

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveUpdate(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (storeMoveBean.getCompanyType().equals("start")) {

			obj = storeMoveStartUpdate(storeMoveBean);

		} else if (storeMoveBean.getCompanyType().equals("end")) {

			obj = storeMoveEndUpdate(storeMoveBean);
		}

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveStartUpdate(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveBean.getUserSeq());

		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		if (storeMoveBean.getStoreMoveSeq() == null) {
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findOne(storeMoveBean.getStoreMoveSeq());

		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 종결 여부 확인
		if (storeMoveLog.getDisuseYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		if (storeMoveLog.getErpYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.UPDATE_ERP_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.UPDATE_ERP_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		if (storeMoveLog.getStartConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 해당 업체 태그인지 확인
		for (StyleBean style : storeMoveBean.getBoxInfo().getStyleList()) {

			for (RfidTagBean rfidTag : style.getRfidTagList()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				if (!storeRfidTag.getCustomerCd().equals(storeMoveLog.getBoxInfo().getStartCompanyInfo().getCode())) {
					obj.put("resultCode", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY);
					obj.put("resultMessage", ApiResultConstans.NOT_APPLY_RFID_TAG_IN_COMPANY_MESSAGE);
					return obj;
				}
			}
		}

		storeStorageRfidTagService.deleteBoxInfo(storeMoveBean.getUserSeq(), storeMoveLog.getBoxInfo().getBoxSeq());

		for (StyleBean style : storeMoveBean.getBoxInfo().getStyleList()) {

			for (RfidTagBean rfidTag : style.getRfidTagList()) {

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

				storeRfidTag.setUpdUserInfo(userInfo);
				storeRfidTag.setUpdDate(new Date());
				storeRfidTag.setBoxInfo(storeMoveLog.getBoxInfo());

				storeStorageRfidTagList.add(storeRfidTag);
				storeStorageSeqList.add(storeRfidTag.getStoreStorageSeq());
			}
		}

		storeMoveLog.getBoxInfo().setUpdDate(new Date());
		storeMoveLog.getBoxInfo().setUpdUserInfo(userInfo);

		boxInfoRepository.save(storeMoveLog.getBoxInfo());
		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		storeStorageRfidTagRepository.flush();
		// boxInfoRepository.flush();

		// storeStorageLogService.save(storeStorageSeqList, userInfo, startDate,
		// "6SU", storeMoveBean.getType());

		// 매장간이동 수정
		update(storeMoveLog, userInfo);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveEndUpdate(StoreMoveBean storeMoveBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveBean.getUserSeq());

		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		if (storeMoveBean.getStoreMoveSeq() == null) {
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findOne(storeMoveBean.getStoreMoveSeq());

		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 종결 여부 확인
		if (storeMoveLog.getDisuseYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 작업 여부 확인
		if (storeMoveLog.getStartWorkYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 보내는 매장 확정 여부 확인
		if (storeMoveLog.getStartConfirmYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 받는 매장 작업 여부 확인
		if (storeMoveLog.getEndWorkYn().equals("N")) {
			obj.put("resultCode", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 확정된 작업 여부 확인
		if (storeMoveLog.getEndConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		BoxInfo boxInfo = storeMoveLog.getBoxInfo();

		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);

		boxInfo = boxInfoRepository.save(boxInfo);

		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
			detailLog.setCompleteAmount(Long.valueOf(0));

			for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {
				subDetailLog.setStat("1");
			}
		}

		storeMoveLogRepository.save(storeMoveLog);

		// 매장간 이동 도착 받는 작업 업데이트
		matching(storeMoveLog, storeMoveBean, userInfo);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveDelete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (storeMoveListBeanList.getCompanyType().equals("start")) {

			obj = storeMoveStartDelete(storeMoveListBeanList);

		} else if (storeMoveListBeanList.getCompanyType().equals("end")) {

			obj = storeMoveEndDelete(storeMoveListBeanList);
		}

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveStartDelete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();
		List<StoreMoveLog> storeMoveLogErpList = new ArrayList<StoreMoveLog>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();
		List<BoxInfo> boxErpList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean boxInfo : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 매장간 이동 박스 정보 삭제
			storeStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), storeMoveLog.getBoxInfo().getBoxSeq());

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
				storeStorageSeqList.add(detailLog.getStyleSeq());
			}

			// ERP 데이터 & PDA 작업에 따른 분기처리
			if (storeMoveLog.getErpYn().equals("Y")) {

				storeMoveLog.setStartWorkYn("N");
				storeMoveLog.setStartWorkDate(null);
				storeMoveLog.setUpdDate(new Date());
				storeMoveLog.setUpdUserInfo(userInfo);

				storeMoveLog.getBoxInfo().setArrivalDate(null);
				storeMoveLog.getBoxInfo().setStat("1");
				storeMoveLog.getBoxInfo().setUpdDate(new Date());
				storeMoveLog.getBoxInfo().setUpdUserInfo(userInfo);

				storeMoveLogErpList.add(storeMoveLog);
				boxErpList.add(storeMoveLog.getBoxInfo());

			} else if (storeMoveLog.getErpYn().equals("N")) {

				storeMoveLogList.add(storeMoveLog);
				boxList.add(storeMoveLog.getBoxInfo());

			}
		}

		// 매장간이동 정보 초기화(ERP 작업일 경우)
		storeMoveLogRepository.save(storeMoveLogErpList);

		// 매장간이동 정보 삭제(PDA 작업일 경우)
		storeMoveLogRepository.delete(storeMoveLogList);

		// 박스 상태 초기화(ERP 작업일 경우)
		boxInfoRepository.save(boxErpList);

		// 박스 상태 삭제(PDA 작업일 경우)
		boxInfoRepository.delete(boxList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveEndDelete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean boxInfo : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 작업 여부 확인
			if (storeMoveLog.getStartWorkYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 확정 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 받는 매장 작업 여부 확인
			if (storeMoveLog.getEndWorkYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getEndConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 매장간 이동 도착 태그 초기화
			storeStorageRfidTagService.rollbackNonCheck(storeMoveListBeanList.getUserSeq(), storeMoveLog.getBoxInfo().getBoxSeq());

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
				storeStorageSeqList.add(detailLog.getCompleteStyleSeq());
			}

			storeMoveLog.setEndWorkYn("N");
			storeMoveLog.setEndWorkDate(null);
			storeMoveLog.setUpdDate(new Date());
			storeMoveLog.setUpdUserInfo(userInfo);

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
				detailLog.setCompleteAmount(Long.valueOf(0));

				for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {
					subDetailLog.setStat("1");
				}
			}

			storeMoveLogList.add(storeMoveLog);
		}

		// 받는 매장 정보 초기화
		storeMoveLogRepository.save(storeMoveLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveComplete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (storeMoveListBeanList.getCompanyType().equals("start")) {

			obj = storeMoveStartComplete(storeMoveListBeanList);

		} else if (storeMoveListBeanList.getCompanyType().equals("end")) {

			obj = storeMoveEndComplete(storeMoveListBeanList);
		}

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveStartComplete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> storeStorageStartSeqList = new ArrayList<Long>();
		ArrayList<Long> storeStorageEndSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean box : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(box.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			BoxInfo boxInfo = storeMoveLog.getBoxInfo();

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

				// 매장간이동 도착매장에도 같은 바택으로 등록된 정보가 있는지 조회
				StoreStorage startStoreStorage = storeStorageRepository.findOne(detailLog.getStyleSeq());

				if (startStoreStorage == null) {
					obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
					return obj;
				}

				storeStorageStartSeqList.add(startStoreStorage.getStoreStorageSeq());

				Specifications<StoreStorage> specifications = Specifications
						.where(StoreStorageSpecification.styleEqual(startStoreStorage.getDistributionStorage()
								.getProductionStorage().getBartagMaster().getStyle()))
						.and(StoreStorageSpecification.colorEqual(startStoreStorage.getDistributionStorage()
								.getProductionStorage().getBartagMaster().getColor()))
						.and(StoreStorageSpecification.sizeEqual(startStoreStorage.getDistributionStorage()
								.getProductionStorage().getBartagMaster().getSize()))
						.and(StoreStorageSpecification.orderDegreeEqual(startStoreStorage.getDistributionStorage()
								.getProductionStorage().getBartagMaster().getOrderDegree()))
						.and(StoreStorageSpecification
								.additionOrderDegreeEqual(startStoreStorage.getDistributionStorage()
										.getProductionStorage().getBartagMaster().getAdditionOrderDegree()))
						.and(StoreStorageSpecification
								.companySeqEqual(storeMoveLog.getBoxInfo().getEndCompanyInfo().getCompanySeq()));

				StoreStorage endStoreStorage = storeStorageRepository.findOne(specifications);

				if (endStoreStorage == null) {
					endStoreStorage = storeStorageRepository.save(new StoreStorage(startStoreStorage.getDistributionStorage(), storeMoveLog.getBoxInfo().getEndCompanyInfo(), userInfo));
				}

				detailLog.setCompleteStyleSeq(endStoreStorage.getStoreStorageSeq());
				storeStorageEndSeqList.add(endStoreStorage.getStoreStorageSeq());

				for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {

					// 판매 RFID 태그 정보가 있는지 확인
					StoreStorageRfidTag storeStorageRfidTag = storeStorageRfidTagRepository.findByRfidTag(subDetailLog.getRfidTag());

					if (storeStorageRfidTag != null) {

						// 매장간이동 입고예정 정보 저장
						RfidTagMaster tag = StringUtil.setRfidTagMaster(subDetailLog.getRfidTag());

						storeStorageRfidTag.setCustomerCd(storeMoveLog.getBoxInfo().getEndCompanyInfo().getCode());
						storeStorageRfidTag.setStat("1");
						storeStorageRfidTag.setStoreStorageSeq(endStoreStorage.getStoreStorageSeq());

						storeStorageRfidTagList.add(storeStorageRfidTag);

						// 판매 매장간이동 태그 히스토리 저장
						RfidTagHistory rfidTagHistory = new RfidTagHistory();

						rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
						rfidTagHistory.setErpKey(tag.getErpKey());
						rfidTagHistory.setRfidTag(tag.getRfidTag());
						rfidTagHistory.setRfidSeq(tag.getRfidSeq());
						rfidTagHistory.setWork("36S");
						rfidTagHistory.setRegUserInfo(userInfo);
						rfidTagHistory.setCompanyInfo(boxInfo.getStartCompanyInfo());
						rfidTagHistory.setRegDate(new Date());

						rfidTagHistoryList.add(rfidTagHistory);
					}
				}
			}

			storeMoveLog.setStartConfirmDate(new Date());
			storeMoveLog.setStartConfirmYn("Y");
			storeMoveLog.setUpdDate(new Date());
			storeMoveLog.setUpdUserInfo(userInfo);

			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setCompleteDate(new Date());

			storeMoveLog.setBoxInfo(boxInfo);

			boxInfo = boxInfoRepository.save(boxInfo);

			storeMoveLogList.add(storeMoveLog);

		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		storeMoveLogRepository.save(storeMoveLogList);

		// ERP 데이터 저장 및 업데이트
		erpService.storeMoveProccess(storeMoveLogList);

//		storeStorageRfidTagRepository.flush();

		storeStorageLogService.save(storeStorageStartSeqList, userInfo, startDate, "6SC",
				storeMoveListBeanList.getType());
		storeStorageLogService.save(storeStorageEndSeqList, userInfo, startDate, "6EI",
				storeMoveListBeanList.getType());

		userNotiService.save("RFID 태그 매장간 이동 보내기 확정되었습니다.", userInfo, "store", Long.valueOf(0));

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveEndComplete(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		ArrayList<StoreStorageRfidTag> storeStorageRfidTagList = new ArrayList<StoreStorageRfidTag>();
		ArrayList<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		ArrayList<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean box : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(box.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 작업 여부 확인
			if (storeMoveLog.getStartWorkYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 확정 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 받는 매장 작업 여부 확인
			if (storeMoveLog.getEndWorkYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getEndConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			BoxInfo boxInfo = storeMoveLog.getBoxInfo();

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

				// 매장간이동 도착매장에도 같은 바택으로 등록된 정보가 있는지 조회
				StoreStorage startStoreStorage = storeStorageRepository.findOne(detailLog.getStyleSeq());

				if (startStoreStorage == null) {
					obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
					return obj;
				}

				for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {

					// 판매 RFID 태그 정보가 있는지 확인
					StoreStorageRfidTag storeStorageRfidTag = storeStorageRfidTagRepository
							.findByRfidTag(subDetailLog.getRfidTag());

					if (storeStorageRfidTag != null) {

						// 매장간이동 입고예정 정보 저장
						RfidTagMaster tag = StringUtil.setRfidTagMaster(subDetailLog.getRfidTag());

						storeStorageRfidTag.setStat("2");
						storeStorageRfidTag.setUpdDate(new Date());
						storeStorageRfidTag.setUpdUserInfo(userInfo);

						storeStorageSeqList.add(storeStorageRfidTag.getStoreStorageSeq());
						storeStorageRfidTagList.add(storeStorageRfidTag);

						// 판매 매장간이동 태그 히스토리 저장
						RfidTagHistory rfidTagHistory = new RfidTagHistory();

						rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
						rfidTagHistory.setErpKey(tag.getErpKey());
						rfidTagHistory.setRfidTag(tag.getRfidTag());
						rfidTagHistory.setRfidSeq(tag.getRfidSeq());
						rfidTagHistory.setWork("36E");
						rfidTagHistory.setRegUserInfo(userInfo);
						rfidTagHistory.setCompanyInfo(boxInfo.getStartCompanyInfo());
						rfidTagHistory.setRegDate(new Date());

						rfidTagHistoryList.add(rfidTagHistory);
					}
				}
			}

			storeMoveLog.setEndConfirmDate(new Date());
			storeMoveLog.setEndConfirmYn("Y");
			storeMoveLog.setUpdDate(new Date());
			storeMoveLog.setUpdUserInfo(userInfo);

			boxInfo.setStat("3");
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			storeMoveLog.setBoxInfo(boxInfo);

			boxInfo = boxInfoRepository.save(boxInfo);

			storeMoveLogList.add(storeMoveLog);

		}

		storeStorageRfidTagRepository.save(storeStorageRfidTagList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);
		storeMoveLogRepository.save(storeMoveLogList);

//		storeStorageRfidTagRepository.flush();
		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "6EC", storeMoveListBeanList.getType());

		// ERP 확정 처리
		erpService.completeStoreMove(storeMoveLogList);

		userNotiService.save("RFID 태그 매장간 이동 받기 확정되었습니다.", userInfo, "store", Long.valueOf(0));

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;

	}

	@Transactional
	@Override
	public Map<String, Object> storeMoveDisuse(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (storeMoveListBeanList.getCompanyType().equals("start")) {

			obj = storeMoveStartDisuse(storeMoveListBeanList);

		} else if (storeMoveListBeanList.getCompanyType().equals("end")) {

			obj = storeMoveEndDisuse(storeMoveListBeanList);
		}

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveStartDisuse(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Date startDate = new Date();

		List<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean boxInfo : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 매장간 이동 박스 정보 삭제
			storeStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), storeMoveLog.getBoxInfo().getBoxSeq());

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
				storeStorageSeqList.add(detailLog.getStyleSeq());
			}

			storeMoveLog.getBoxInfo().setArrivalDate(null);
			storeMoveLog.getBoxInfo().setStat("1");
			storeMoveLog.getBoxInfo().setUpdDate(new Date());
			storeMoveLog.getBoxInfo().setUpdUserInfo(userInfo);

			storeMoveLog.setDisuseYn("Y");
			storeMoveLog.setUpdDate(new Date());
			storeMoveLog.setUpdUserInfo(userInfo);

			boxList.add(storeMoveLog.getBoxInfo());
			storeMoveLogList.add(storeMoveLog);

		}

		// 판매 수량 변경
		storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "6SF", storeMoveListBeanList.getType());

		// 매장간이동 정보 종결 업데이트
		storeMoveLogRepository.save(storeMoveLogList);

		// 박스 상태 초기화
		boxInfoRepository.save(boxList);

		// ERP 종결처리
		erpService.disuseStoreMove(storeMoveLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	public Map<String, Object> storeMoveEndDisuse(StoreMoveListBean storeMoveListBeanList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Date startDate = new Date();

		List<Long> storeStorageStartSeqList = new ArrayList<Long>();
		List<Long> storeStorageEndSeqList = new ArrayList<Long>();
		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		UserInfo userInfo = userInfoRepository.findOne(storeMoveListBeanList.getUserSeq());

		// 사용자 정보 확인
		if (userInfo == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		for (BoxBean boxInfo : storeMoveListBeanList.getBoxList()) {

			StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(boxInfo.getBarcode());

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 작업 여부 확인
			if (storeMoveLog.getStartWorkYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 보내는 매장 확정 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getEndConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_CONFIRM_END_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

				storeStorageStartSeqList.add(detailLog.getStyleSeq());
				storeStorageEndSeqList.add(detailLog.getCompleteStyleSeq());

				// 매장간 이동 도착 태그 초기화
				storeStorageRfidTagService.rollbackStoreMove(userInfo.getUserSeq(),
						storeMoveLog.getBoxInfo().getStartCompanyInfo().getCode(), detailLog.getStyleSeq(),
						detailLog.getCompleteStyleSeq());
			}

			storeMoveLog.setDisuseYn("Y");
			storeMoveLog.setUpdDate(new Date());
			storeMoveLog.setUpdUserInfo(userInfo);

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {
				detailLog.setCompleteAmount(Long.valueOf(0));

				for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {
					subDetailLog.setStat("1");
				}
			}

			storeMoveLogList.add(storeMoveLog);
		}

		// 판매 수량 변경
		storeStorageLogService.save(storeStorageStartSeqList, userInfo, startDate, "6EF",
				storeMoveListBeanList.getType());
		storeStorageLogService.save(storeStorageEndSeqList, userInfo, startDate, "6EF",
				storeMoveListBeanList.getType());

		// 받는 매장 정보 초기화
		storeMoveLogRepository.save(storeMoveLogList);

		// ERP 종결처리
		erpService.disuseStoreMove(storeMoveLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;

	}

	@Transactional
	@Override
	public List<StoreMoveLog> save(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String returnType)
			throws Exception {

		List<StoreMoveLog> storeMoveLogList = new ArrayList<StoreMoveLog>();

		for (BoxInfo boxInfo : boxInfoList) {

			// 매장간 이동 예정 로그 셋팅
			StoreMoveLog storeMoveLog = setStoreMoveLog(boxInfo, userInfo, workLine);

			Long maxRfidWorkLine = maxRfidWorkLine(storeMoveLog.getCreateDate(), boxInfo.getBarcode(),
					boxInfo.getStartCompanyInfo().getCompanySeq());

			HashSet<StoreMoveDetailLog> storeMoveDetailLogSet = new HashSet<StoreMoveDetailLog>();

			List<StyleModel> styleCountList = boxService.boxStyleCountStoreList(boxInfo.getBoxSeq());

			storeMoveLog.setReturnType(returnType);

			for (StyleModel style : styleCountList) {

				// 매장간 이동 detail 로그 설정
				StoreMoveDetailLog storeMoveDetailLog = new StoreMoveDetailLog();
				storeMoveDetailLog.setBarcode(boxInfo.getBarcode());
				storeMoveDetailLog.setStyle(style.getStyle());
				storeMoveDetailLog.setColor(style.getColor());
				storeMoveDetailLog.setSize(style.getSize());
				storeMoveDetailLog.setOrderDegree(style.getOrderDegree());
				storeMoveDetailLog.setAmount(style.getCount());
				storeMoveDetailLog.setStyleSeq(style.getStyleSeq());
				storeMoveDetailLog.setCompleteAmount(Long.valueOf(0));

				storeMoveDetailLogSet.add(storeMoveDetailLog);

				HashSet<StoreMoveSubDetailLog> storeMoveSubDetailLogSet = new HashSet<StoreMoveSubDetailLog>();

				List<RfidModel> rfidList = boxService.boxStyleRfidStoreList(boxInfo.getBoxSeq(), style);

				for (RfidModel rfid : rfidList) {
					StoreMoveSubDetailLog storeMoveSubDetailLog = new StoreMoveSubDetailLog();
					storeMoveSubDetailLog.setRfidTag(rfid.getRfidTag());
					storeMoveSubDetailLog.setStat("1");
					storeMoveSubDetailLog.setRfidLineNum(++maxRfidWorkLine);
					storeMoveSubDetailLog.setRfidCreateDate(storeMoveLog.getCreateDate());

					storeMoveSubDetailLogSet.add(storeMoveSubDetailLog);
				}

				storeMoveDetailLog.setStoreMoveSubDetailLog(storeMoveSubDetailLogSet);
			}

			storeMoveLog.setStoreMoveDetailLog(storeMoveDetailLogSet);

			storeMoveLogList.add(storeMoveLog);
		}

		return storeMoveLogRepository.save(storeMoveLogList);
	}

	private StoreMoveLog setStoreMoveLog(BoxInfo boxInfo, UserInfo userInfo, Long workLine) throws Exception {

		StoreMoveLog obj = new StoreMoveLog();

		obj.setRegDate(new Date());
		obj.setRegUserInfo(userInfo);
		obj.setStartConfirmYn("N");
		obj.setStartWorkYn("Y");
		obj.setEndConfirmYn("N");
		obj.setEndWorkYn("N");
		obj.setBoxInfo(boxInfo);
		obj.setStartWorkDate(new Date());
		obj.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		obj.setWorkLine(workLine);
		obj.setDisuseYn("N");
		obj.setErpYn("N");

		return obj;
	}

	@Transactional
	@Override
	public StoreMoveLog update(StoreMoveLog storeMoveLog, UserInfo userInfo) throws Exception {

		List<StyleModel> styleCountList = boxService.boxStyleCountStoreList(storeMoveLog.getBoxInfo().getBoxSeq());

		Long maxRfidWorkLine = maxRfidWorkLine(storeMoveLog.getCreateDate(), storeMoveLog.getBoxInfo().getBarcode(),
				storeMoveLog.getBoxInfo().getStartCompanyInfo().getCompanySeq());

		storeMoveLog.getStoreMoveDetailLog().clear();

		for (StyleModel style : styleCountList) {

			// 물류 입고 detail 로그 설정
			StoreMoveDetailLog storeMoveDetailLog = new StoreMoveDetailLog();
			storeMoveDetailLog.setBarcode(storeMoveLog.getBoxInfo().getBarcode());
			storeMoveDetailLog.setStyle(style.getStyle());
			storeMoveDetailLog.setColor(style.getColor());
			storeMoveDetailLog.setSize(style.getSize());
			storeMoveDetailLog.setOrderDegree(style.getOrderDegree());
			storeMoveDetailLog.setAmount(style.getCount());
			storeMoveDetailLog.setStyleSeq(style.getStyleSeq());
			storeMoveDetailLog.setCompleteAmount(Long.valueOf(0));

			List<RfidModel> rfidList = boxService.boxStyleRfidStoreList(storeMoveLog.getBoxInfo().getBoxSeq(), style);

			storeMoveDetailLog.setStoreMoveSubDetailLog(new HashSet<StoreMoveSubDetailLog>());

			for (RfidModel rfid : rfidList) {
				StoreMoveSubDetailLog subDetailLog = new StoreMoveSubDetailLog();
				subDetailLog.setRfidTag(rfid.getRfidTag());
				subDetailLog.setStat("1");
				subDetailLog.setRfidLineNum(++maxRfidWorkLine);
				subDetailLog.setRfidCreateDate(storeMoveLog.getCreateDate());

				storeMoveDetailLog.getStoreMoveSubDetailLog().add(subDetailLog);
			}

			storeMoveLog.getStoreMoveDetailLog().add(storeMoveDetailLog);
		}

		storeMoveLog.setUpdDate(new Date());
		storeMoveLog.setUpdUserInfo(userInfo);

		return storeMoveLogRepository.save(storeMoveLog);
	}

	@Transactional
	public StoreMoveLog matching(StoreMoveLog storeMoveLog, StoreMoveBean storeMoveBean, UserInfo userInfo) throws Exception {

		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

			for (StyleBean style : storeMoveBean.getBoxInfo().getStyleList()) {

				if (detailLog.getStyle().equals(style.getStyle()) && detailLog.getColor().equals(style.getColor())
						&& detailLog.getSize().equals(style.getSize())) {
					detailLog.setCompleteAmount(style.getAmount());

					for (StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()) {

						boolean checkFlag = false;

						for (RfidTagBean rfidTag : style.getRfidTagList()) {

							if (subDetailLog.getRfidTag().equals(rfidTag.getRfidTag())) {
								checkFlag = true;
								break;
							}
						}

						if (checkFlag) {
							subDetailLog.setStat("2");
						}
					}

					break;
				}
			}

		}

		storeMoveLog.setUpdDate(new Date());
		storeMoveLog.setUpdUserInfo(userInfo);
		storeMoveLog.setEndWorkYn("Y");
		storeMoveLog.setEndWorkDate(new Date());

		if(storeMoveBean.getExplanatory() != null && storeMoveBean.getExplanatory().trim().length() > 0){
			storeMoveLog.setExplanatory(storeMoveBean.getExplanatory());
		}

		return storeMoveLogRepository.save(storeMoveLog);
	}

	@Transactional(readOnly = true)
	@Override
	public Long maxWorkLine(String createDate, Long companySeq) throws Exception {

		StoreMoveLog storeMoveLog = storeMoveLogRepository
				.findTop1ByCreateDateAndBoxInfoStartCompanyInfoCompanySeqOrderByWorkLineDesc(createDate, companySeq);

		Long maxWorkLine;

		if (storeMoveLog == null) {
			maxWorkLine = Long.valueOf(0);
		} else {
			maxWorkLine = storeMoveLog.getWorkLine();
		}
		return maxWorkLine;
	}

	@Transactional(readOnly = true)
	@Override
	public Long maxRfidWorkLine(String createDate, String boxBarcode, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		String query = "SELECT ISNULL(MAX(smsdl.rfid_line_num), 0) " + "FROM store_move_log sml "
				+ "INNER JOIN store_move_detail_log smdl " + "ON sml.store_move_log_seq = smdl.store_move_log_seq "
				+ "INNER JOIN store_move_sub_detail_log smsdl "
				+ "ON smdl.store_move_detail_log_seq = smsdl.store_move_detail_log_seq " + "INNER JOIN box_info bi "
				+ "ON sml.box_seq = bi.box_seq " + "WHERE sml.create_date = :createDate "
				+ "AND bi.barcode = :boxBarcode " + "AND bi.start_company_seq = :companySeq ";

		params.put("createDate", createDate);
		params.put("boxBarcode", boxBarcode);
		params.put("companySeq", companySeq);

		return nameTemplate.queryForObject(query, params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> storeMoveBarcode(String barcode, Long companySeq, String companyType, String erpYn)
			throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if (companyType.equals("start")) {

			obj = storeMoveStartBarcode(barcode, companySeq, erpYn);

		} else if (companyType.equals("end")) {

			obj = storeMoveEndBarcode(barcode);
		}

		return obj;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> storeMoveStartBarcode(String barcode, Long companySeq, String erpYn) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(barcode);

		if (erpYn.equals("Y")) {

			// 매장간 이동 정보 확인
			if (storeMoveLog == null) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// ERP 여부 확인
			if (storeMoveLog.getErpYn().equals("N")) {
				obj.put("resultCode", ApiResultConstans.ONLY_ERP_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.ONLY_ERP_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 작업된 박스 여부 확인
			if (storeMoveLog.getStartWorkYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.COMPLETE_WORK_START_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_WORK_START_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 확정된 박스 여부 확인
			if (storeMoveLog.getStartConfirmYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			// 종결 여부 확인
			if (storeMoveLog.getDisuseYn().equals("Y")) {
				obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
				obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
				return obj;
			}

			obj.put("storeMoveDetail", storeMoveLog);

		} else if (erpYn.equals("N")) {

			// 매장간 이동 정보 확인
			if (storeMoveLog != null) {
				obj.put("resultCode", ApiResultConstans.EXIST_BOX_BARCODE);
				obj.put("resultMessage", ApiResultConstans.EXIST_BOX_BARCODE_MESSAGE);
				return obj;
			}

			Specifications<StoreStorage> specifications = Specifications
					.where(StoreStorageSpecification.companySeqEqual(companySeq))
					.and(StoreStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)))
					.and(StoreStorageSpecification.totalAmountGreaterThan(Long.valueOf(0)));

			List<StoreStorage> storeStorageList = storeStorageRepository.findAll(specifications);

			if (storeStorageList.size() == 0) {
				obj.put("resultCode", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT);
				obj.put("resultMessage", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT_MESSAGE);
				return obj;
			}

			ArrayList<Map<String, Object>> bartagList = new ArrayList<Map<String, Object>>();
			for (StoreStorage storage : storeStorageList) {

				Map<String, Object> tempMap = new LinkedHashMap<String, Object>();

				tempMap.put("erpKey",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
				tempMap.put("style",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
				tempMap.put("color",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
				tempMap.put("size",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
				tempMap.put("orderDegree",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getOrderDegree());
				tempMap.put("additionOrderDegree", storage.getDistributionStorage().getProductionStorage()
						.getBartagMaster().getAdditionOrderDegree());
				tempMap.put("startRfidSeq",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStartRfidSeq());
				tempMap.put("endRfidSeq",
						storage.getDistributionStorage().getProductionStorage().getBartagMaster().getEndRfidSeq());
				tempMap.put("stockAmount", storage.getStat2Amount());

				bartagList.add(tempMap);
			}

			obj.put("bartagList", bartagList);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> storeMoveEndBarcode(String barcode) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		StoreMoveLog storeMoveLog = storeMoveLogRepository.findByBoxInfoBarcode(barcode);

		// 매장간 이동 정보 확인
		if (storeMoveLog == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 작업된 박스 여부 확인
		if (storeMoveLog.getEndWorkYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_WORK_END_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 확정된 박스 여부 확인
		if (storeMoveLog.getEndConfirmYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.CONFIRM_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		// 종결 여부 확인
		if (storeMoveLog.getDisuseYn().equals("Y")) {
			obj.put("resultCode", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST);
			obj.put("resultMessage", ApiResultConstans.DISUSE_STORE_MOVE_REQUEST_MESSAGE);
			return obj;
		}

		obj.put("storeMoveDetail", storeMoveLog);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreMoveGroupModel> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq,
			String companyType, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "sml.create_date DESC ");

		StringBuffer query = new StringBuffer();

		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append(
				"SELECT TOP(:groupCount) sml.create_date, sci.company_seq AS start_company_seq, sci.name AS start_company_name, eci.company_seq AS end_company_seq, eci.name AS end_company_name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.start_work_yn = 'N' AND csml.start_confirm_yn = 'N'");
		query.append("AND csml.end_work_yn = 'N' AND csml.end_confirm_yn = 'N' AND csml.disuse_yn = 'N'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat1_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.start_work_yn = 'Y' AND csml.start_confirm_yn = 'N'");
		query.append("AND csml.end_work_yn = 'N' AND csml.end_confirm_yn = 'N' AND csml.disuse_yn = 'N'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat2_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.start_work_yn = 'Y' AND csml.start_confirm_yn = 'Y'");
		query.append("AND csml.end_work_yn = 'N' AND csml.end_confirm_yn = 'N' AND csml.disuse_yn = 'N'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat3_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.start_work_yn = 'Y' AND csml.start_confirm_yn = 'Y'");
		query.append("AND csml.end_work_yn = 'Y' AND csml.end_confirm_yn = 'N' AND csml.disuse_yn = 'N'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat4_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.start_work_yn = 'Y' AND csml.start_confirm_yn = 'Y'");
		query.append("AND csml.end_work_yn = 'Y' AND csml.end_confirm_yn = 'Y' AND csml.disuse_yn = 'N'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat5_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.disuse_yn = 'Y'");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS stat6_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM store_move_log csml ");
		query.append("INNER JOIN store_move_detail_log csmld ");
		query.append("ON csml.store_move_log_seq = csmld.store_move_log_seq ");
		query.append("INNER JOIN store_move_sub_detail_log csmlsd ");
		query.append("ON csmld.store_move_detail_log_seq = csmlsd.store_move_detail_log_seq ");
		query.append("AND csml.create_date = sml.create_date ");
		query.append(") AS total_count ");

		query.append("FROM store_move_log sml ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON sml.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info sci ");
		query.append("ON bi.start_company_seq = sci.company_seq ");
		query.append("INNER JOIN company_info eci ");
		query.append("ON bi.end_company_seq = eci.company_seq ");

		query.append("WHERE sml.create_date BETWEEN :startDate AND :endDate ");

		if ((companyType.equals("start") || companyType.equals("all")) && startCompanySeq != 0) {
			query.append("AND sci.company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		} else if ((companyType.equals("end") || companyType.equals("all")) && endCompanySeq != 0) {
			query.append("AND eci.company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		query.append("GROUP BY sml.create_date, sci.company_seq, sci.name, eci.company_seq, eci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append(
				"SELECT create_date, start_company_seq, start_company_name, end_company_seq, end_company_name, stat1_count, stat2_count, stat3_count, stat4_count, stat5_count, stat6_count, total_count ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StoreMoveGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountGroupList(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq,
			String companyType) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		StringBuffer query = new StringBuffer();

		query.append("SELECT COUNT(*) AS total_count ");
		query.append(
				"FROM (SELECT sml.create_date, sci.company_seq AS start_company_seq, eci.company_seq AS end_company_seq ");
		query.append("FROM store_move_log sml ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON sml.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info sci ");
		query.append("ON bi.start_company_seq = sci.company_seq ");
		query.append("INNER JOIN company_info eci ");
		query.append("ON bi.end_company_seq = eci.company_seq ");

		query.append("WHERE sml.create_date BETWEEN :startDate AND :endDate ");

		if ((companyType.equals("start") || companyType.equals("all")) && startCompanySeq != 0) {
			query.append("AND sci.company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		} else if ((companyType.equals("end") || companyType.equals("all")) && endCompanySeq != 0) {
			query.append("AND eci.company_seq = :endCompanySeq ");
			params.put("endCompanySeq", endCompanySeq);
		}

		query.append("GROUP BY sml.create_date, sci.company_seq, eci.company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StoreMoveLog> findAll(String createDate, Long startCompanySeq, Long endCompanySeq, String workYn,
			String confirmYn, String disuseYn, String companyType, String search, String option, Pageable pageable)
			throws Exception {

		Page<StoreMoveLog> page = null;

		Specifications<StoreMoveLog> specifications = Specifications
				.where(StoreMoveLogSpecification.createDateEqual(createDate));

		if ((companyType.equals("start") || companyType.equals("all")) && startCompanySeq != 0) {
			specifications = specifications.and(StoreMoveLogSpecification.startCompanySeqEqual(startCompanySeq));
		} else if ((companyType.equals("end") || companyType.equals("all")) && endCompanySeq != 0) {
			specifications = specifications.and(StoreMoveLogSpecification.endCompanySeqEqual(endCompanySeq));
		}

		if (!workYn.equals("all") && companyType.equals("start")) {
			specifications = specifications.and(StoreMoveLogSpecification.startWorkYnEqual(workYn));
		} else if (!workYn.equals("all") && companyType.equals("end")) {
			specifications = specifications.and(StoreMoveLogSpecification.endWorkYnEqual(workYn));
		}

		if (!confirmYn.equals("all") && companyType.equals("start")) {
			specifications = specifications.and(StoreMoveLogSpecification.startConfirmYnEqual(confirmYn));
		} else if (!confirmYn.equals("all") && companyType.equals("end")) {
			specifications = specifications.and(StoreMoveLogSpecification.endConfirmYnEqual(confirmYn));
		}

		if (!disuseYn.equals("all")) {
			specifications = specifications.and(StoreMoveLogSpecification.disuseYnEqual(disuseYn));
		}

		page = storeMoveLogRepository.findAll(specifications, pageable);

		return page;
	}

}
