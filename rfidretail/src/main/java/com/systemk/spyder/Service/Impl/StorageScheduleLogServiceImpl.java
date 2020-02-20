package com.systemk.spyder.Service.Impl;

import java.math.BigDecimal;
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

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Response.ApiStorecheduleCompleteResult;
import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Entity.External.RfidIb10If;
import com.systemk.spyder.Entity.External.RfidLf01If;
import com.systemk.spyder.Entity.Lepsilon.Treceipt;
import com.systemk.spyder.Entity.Lepsilon.TreceiptDetail;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptDetailKey;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptKey;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleSubDetailLog;
import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Repository.External.RfidIb01IfRepository;
import com.systemk.spyder.Repository.External.RfidIb10IfRepository;
import com.systemk.spyder.Repository.External.RfidLf01IfRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptDetailRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptRepository;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleDetailLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleSubDetailLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.StorageScheduleLogSpecification;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleDetailRepository;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleHeaderRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BoxService;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.LepsilonService;
import com.systemk.spyder.Service.OpenDbService;
import com.systemk.spyder.Service.ProductionStorageLogService;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.StoreStorageLogService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.RfidModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;
import com.systemk.spyder.Service.CustomBean.StorageScheduleSubDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleChildModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleParentModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleSubChildModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Service.CustomBean.Group.ReturnGroupModel;
import com.systemk.spyder.Service.CustomBean.Group.StorageScheduleGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.DistributionStockStorageScheduleRowMapper;
import com.systemk.spyder.Service.Mapper.StorageScheduleCountMapper;
import com.systemk.spyder.Service.Mapper.StorageScheduleModelMapper;
import com.systemk.spyder.Service.Mapper.StorageScheduleRowMapper;
import com.systemk.spyder.Service.Mapper.Group.ReturnGroupModelMapper;
import com.systemk.spyder.Service.Mapper.Group.StorageScheduleGroupModelMapper;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class StorageScheduleLogServiceImpl implements StorageScheduleLogService {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogServiceImpl.class);

	@Autowired
	private BoxService boxService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private StorageScheduleDetailLogRepository storageScheduleDetailLogRepository;

	@Autowired
	private StorageScheduleSubDetailLogRepository storageScheduleSubDetailLogRepository;

	@Autowired
	private OpenDbService openDbService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private TreceiptRepository treceiptRepository;

	@Autowired
	private TreceiptDetailRepository treceiptDetailRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private StoreStorageLogService storeStorageLogService;

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;

	@Autowired
    private Environment env;

	@Autowired
	private LepsilonService lepsilonService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RfidIb10IfRepository rfidIb10IfRepository;

	@Autowired
	private OpenDbScheduleHeaderRepository openDbScheduleHeaderRepository;

	@Autowired
	private OpenDbScheduleDetailRepository openDbScheduleDetailRepository;

	@Autowired
	private RfidIb01IfRepository rfidIb01IfRepository;

	@Autowired
	private RfidLf01IfRepository rfidLf01IfRepository;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<StorageScheduleLog> findAll(String startDate, String endDate, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String search, String option, Pageable pageable, String orderType) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Page<StorageScheduleLog> page = null;

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.createDateBetween(startDate, endDate))
																		  .and(StorageScheduleLogSpecification.disuseYnEqual("N"));

		if(startCompanySeq != 0) {
			specifications = specifications.and(StorageScheduleLogSpecification.startCompanySeqEqual(startCompanySeq));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.confirmYnEqual(confirmYn));
		}

		if(!completeYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.completeYnEqual(completeYn));
		}

		specifications = specifications.and(StorageScheduleLogSpecification.styleSearchEqual(style, color, size));

		if(option.equals("boxNum") && !search.equals("")) {
			specifications = specifications.and(StorageScheduleLogSpecification.barcodeContain(search));
		}

		if(orderType!=null) {
			specifications = specifications.and(StorageScheduleLogSpecification.orderTypeNotEqual(orderType));
		}

		page = storageScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StorageScheduleLog> findAll(String arrivalDate, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String search, String option, Pageable pageable) throws Exception {

		String startArrival = CalendarUtil.initStartDate(arrivalDate);
		String endArrival = CalendarUtil.initEndDate(arrivalDate);

		Page<StorageScheduleLog> page = null;

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.arrivalDateBetween(startArrival, endArrival));

		specifications = specifications.and(StorageScheduleLogSpecification.disuseYnEqual("N"));

		if(startCompanySeq != 0) {
			specifications = specifications.and(StorageScheduleLogSpecification.startCompanySeqEqual(startCompanySeq));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.confirmYnEqual(confirmYn));
		}

		if(!completeYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.completeYnEqual(completeYn));
		}

		specifications = specifications.and(StorageScheduleLogSpecification.styleSearchEqual(style, color, size));

		if(option.equals("boxNum") && !search.equals("")) {
			specifications = specifications.and(StorageScheduleLogSpecification.barcodeContain(search));
		}

		page = storageScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StorageScheduleLog> findAll(String startDate, String endDate, Long startCompanySeq, String confirmYn, String completeYn, String orderType, Pageable pageable) throws Exception {

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Page<StorageScheduleLog> page = null;

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.createDateBetween(startDate, endDate));

		specifications = specifications.and(StorageScheduleLogSpecification.disuseYnEqual("N"))
									   .and(StorageScheduleLogSpecification.orderTypeEqual(orderType));

		if(startCompanySeq != 0) {
			specifications = specifications.and(StorageScheduleLogSpecification.startCompanySeqEqual(startCompanySeq));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.confirmYnEqual(confirmYn));
		}

		page = storageScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}



	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleLog> findAll(String arrivalDate, String confirmYn, String completeYn) throws Exception {

		String startArrival = CalendarUtil.initStartDate(arrivalDate);
		String endArrival = CalendarUtil.initEndDate(arrivalDate);

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.arrivalDateBetween(startArrival, endArrival));
		specifications = specifications.and(StorageScheduleLogSpecification.disuseYnEqual("N"));

		if(!confirmYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.confirmYnEqual(confirmYn));
		}

		if(!completeYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.completeYnEqual(completeYn));
		}

		return storageScheduleLogRepository.findAll(specifications);
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleLog> findAll(String createDate, String workLine, Long companySeq) throws Exception {

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.createDateEqual(createDate))
																		  .and(StorageScheduleLogSpecification.workLineEqual(workLine))
																		  .and(StorageScheduleLogSpecification.disuseYnEqual("N"))
																		  .and(StorageScheduleLogSpecification.startCompanySeqEqual(companySeq));

		return storageScheduleLogRepository.findAll(specifications);
	}

	@Transactional
	@Override
	public List<StorageScheduleLog> save(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String flag, String returnType) throws Exception {

		List<StorageScheduleLog> storageScheduleLogList = new ArrayList<StorageScheduleLog>();
		for(BoxInfo boxInfo : boxInfoList){
			// 물류 입고 예정 로그 셋팅
			StorageScheduleLog storageScheduleLog = setStorageScheduleLog(boxInfo, userInfo, workLine, flag);

			HashSet<StorageScheduleDetailLog> storageScheduleDetailLogSet = new HashSet<StorageScheduleDetailLog>();

			List<StyleModel> styleCountList = null;

			if(flag.equals("OP-R") || flag.equals("OP-R2")){
				styleCountList = boxService.boxStyleCountProductionList(boxInfo.getBoxSeq());
			} else if(flag.equals("10-R")){
				styleCountList = boxService.returnStyleCountStoreList(boxInfo.getBoxSeq());
				storageScheduleLog.setReturnType(returnType);
			}

			for(StyleModel style : styleCountList){

				// 물류 입고 detail 로그 설정
				StorageScheduleDetailLog storageScheduleDetailLog = new StorageScheduleDetailLog();
				storageScheduleDetailLog.setOpenDbCode("R2455");
				storageScheduleDetailLog.setLineNum(Long.parseLong(style.getOrderDegree()));
				storageScheduleDetailLog.setBarcode(boxInfo.getBarcode());
				storageScheduleDetailLog.setStyle(style.getStyle());
				storageScheduleDetailLog.setColor(style.getColor());
				storageScheduleDetailLog.setSize(style.getSize());
				storageScheduleDetailLog.setOrderDegree(style.getOrderDegree());
				storageScheduleDetailLog.setAmount(style.getCount());
				storageScheduleDetailLog.setStyleSeq(style.getStyleSeq());

				storageScheduleDetailLogSet.add(storageScheduleDetailLog);

				HashSet<StorageScheduleSubDetailLog> storageScheduleSubDetailLogSet = new HashSet<StorageScheduleSubDetailLog>();


				List<RfidModel> rfidList = null;

				if(flag.equals("OP-R") || flag.equals("OP-R2")){
					rfidList = boxService.boxStyleRfidProductionList(boxInfo.getBoxSeq(), style);
				} else if(flag.equals("10-R")){
					rfidList = boxService.boxStyleRfidStoreList(boxInfo.getBoxSeq(), style);
				}

				for(RfidModel rfid : rfidList){
					StorageScheduleSubDetailLog storageScheduleSubDetailLog = new StorageScheduleSubDetailLog();
					storageScheduleSubDetailLog.setRfidTag(rfid.getRfidTag());

					storageScheduleSubDetailLogSet.add(storageScheduleSubDetailLog);
				}

				storageScheduleDetailLog.setStorageScheduleSubDetailLog(storageScheduleSubDetailLogSet);

			}

			storageScheduleLog.setStorageScheduleDetailLog(storageScheduleDetailLogSet);

			storageScheduleLogList.add(storageScheduleLog);
		}

		return storageScheduleLogRepository.save(storageScheduleLogList);
	}

	@Transactional
	@Override
	public void saveTest(List<BoxInfo> boxInfoList, UserInfo userInfo, Long workLine, String flag, String returnType) throws Exception {

		List<StorageScheduleLog> storageScheduleLogList = new ArrayList<StorageScheduleLog>();

		for(BoxInfo boxInfo : boxInfoList){

			// 물류 입고 예정 로그 셋팅
			StorageScheduleLog storageScheduleLog = setStorageScheduleLog(boxInfo, userInfo, workLine, flag);

			List<StyleModel> styleCountList = null;

			if(flag.equals("OP-R") || flag.equals("OP-R2")){
				styleCountList = boxService.boxStyleCountProductionList(boxInfo.getBoxSeq());
			} else if(flag.equals("10-R")){
				styleCountList = boxService.boxStyleCountStoreList(boxInfo.getBoxSeq());
				storageScheduleLog.setReturnType(returnType);
			}

			storageScheduleLog = storageScheduleLogRepository.save(storageScheduleLog);

			int lineNo = 10;

			for(StyleModel style : styleCountList){

				// 물류 입고 detail 로그 설정
				StorageScheduleDetailLog storageScheduleDetailLog = new StorageScheduleDetailLog();
				storageScheduleDetailLog.setStorageScheduleLogSeq(storageScheduleLog.getStorageScheduleLogSeq());
				storageScheduleDetailLog.setOpenDbCode("R2455");
				storageScheduleDetailLog.setLineNum(Long.valueOf(lineNo));
				storageScheduleDetailLog.setBarcode(boxInfo.getBarcode());
				storageScheduleDetailLog.setStyle(style.getStyle());
				storageScheduleDetailLog.setColor(style.getColor());
				storageScheduleDetailLog.setSize(style.getSize());
				storageScheduleDetailLog.setOrderDegree(style.getOrderDegree());
				storageScheduleDetailLog.setAmount(style.getCount());
				storageScheduleDetailLog.setStyleSeq(style.getStyleSeq());

				storageScheduleDetailLog = storageScheduleDetailLogRepository.save(storageScheduleDetailLog);

				List<StorageScheduleSubDetailLog> storageScheduleSubDetailLogList = new ArrayList<StorageScheduleSubDetailLog>();

				List<RfidModel> rfidList = null;

				if(flag.equals("OP-R") || flag.equals("OP-R2")){
					rfidList = boxService.boxStyleRfidProductionList(boxInfo.getBoxSeq(), style);
				} else if(flag.equals("10-R")){
					rfidList = boxService.boxStyleRfidStoreList(boxInfo.getBoxSeq(), style);
				}

				for(RfidModel rfid : rfidList){
					StorageScheduleSubDetailLog storageScheduleSubDetailLog = new StorageScheduleSubDetailLog();
					storageScheduleSubDetailLog.setStorageScheduleDetailLogSeq(storageScheduleDetailLog.getStorageScheduleDetailLogSeq());
					storageScheduleSubDetailLog.setRfidTag(rfid.getRfidTag());

					storageScheduleSubDetailLogList.add(storageScheduleSubDetailLog);
				}

				storageScheduleSubDetailLogRepository.save(storageScheduleSubDetailLogList);

				lineNo += 10;
			}

			storageScheduleLogList.add(storageScheduleLog);
		}
	}

	private StorageScheduleLog setStorageScheduleLog(BoxInfo boxInfo, UserInfo userInfo, Long workLine, String flag) throws Exception {

		StorageScheduleLog storeScheduleLog = new StorageScheduleLog();
		storeScheduleLog.setOpenDbCode("R2455");
		storeScheduleLog.setOrderType(flag);
		storeScheduleLog.setRegDate(new Date());
		storeScheduleLog.setRegUserInfo(userInfo);
		storeScheduleLog.setCompleteYn("N");
		storeScheduleLog.setBoxInfo(boxInfo);
		storeScheduleLog.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		storeScheduleLog.setWorkLine(workLine);
		storeScheduleLog.setDisuseYn("N");
		storeScheduleLog.setBatchYn("N");
		storeScheduleLog.setCompleteBatchYn("N");
		storeScheduleLog.setErpScheduleYn("N");
		storeScheduleLog.setOpenDbScheduleYn("N");
		storeScheduleLog.setErpCompleteYn("N");
		storeScheduleLog.setWmsCompleteYn("N");
		String arrivalDate = boxInfo.getArrivalDate()==null? "" : (new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(boxInfo.getArrivalDate()));
		storeScheduleLog.setArrivalDate(arrivalDate);
		storeScheduleLog.setReturnYn("N");

		if(flag.equals("OP-R")){
			storeScheduleLog.setConfirmYn("N");
			storeScheduleLog.setCompleteYn("N");
			storeScheduleLog.setArrivalCode("2930");

		} else if(flag.equals("OP-R2")){

			storeScheduleLog.setConfirmYn("Y");
			storeScheduleLog.setConfirmDate(new Date());
			storeScheduleLog.setCompleteYn("Y");
			storeScheduleLog.setCompleteDate(new Date());
			storeScheduleLog.setArrivalCode("2930");
			storeScheduleLog.setInvoiceNum(boxInfo.getInvoiceNum());

		} else if(flag.equals("10-R")){

			storeScheduleLog.setConfirmYn("Y");
			storeScheduleLog.setCompleteYn("N");
			storeScheduleLog.setArrivalCode("2935");
		}

		return storeScheduleLog;
	}

	@Transactional
	@Override
	public StorageScheduleLog update(StorageScheduleLog storageScheduleLog, UserInfo userInfo, String flag) throws Exception {

		storageScheduleLog.getStorageScheduleDetailLog().clear();

		List<StyleModel> styleCountList = null;

		if (flag.equals("OP-R") || flag.equals("OP-R2")) {
			styleCountList = boxService.boxStyleCountProductionList(storageScheduleLog.getBoxInfo().getBoxSeq());
		} else if (flag.equals("10-R")) {
			styleCountList = boxService.boxStyleCountStoreList(storageScheduleLog.getBoxInfo().getBoxSeq());
		}

		int lineNo = 10;

		for (StyleModel style : styleCountList) {

			// 물류 입고 detail 로그 설정
			StorageScheduleDetailLog storageScheduleDetailLog = new StorageScheduleDetailLog();
			storageScheduleDetailLog.setOpenDbCode("R2455");
			storageScheduleDetailLog.setLineNum(Long.valueOf(lineNo));
			storageScheduleDetailLog.setBarcode(storageScheduleLog.getBoxInfo().getBarcode());
			storageScheduleDetailLog.setStyle(style.getStyle());
			storageScheduleDetailLog.setColor(style.getColor());
			storageScheduleDetailLog.setSize(style.getSize());
			storageScheduleDetailLog.setOrderDegree(style.getOrderDegree());
			storageScheduleDetailLog.setAmount(style.getCount());
			storageScheduleDetailLog.setStyleSeq(style.getStyleSeq());

			HashSet<StorageScheduleSubDetailLog> storageScheduleSubDetailLogSet = new HashSet<StorageScheduleSubDetailLog>();

			List<RfidModel> rfidList = null;

			if (flag.equals("OP-R") || flag.equals("OP-R2")) {
				rfidList = boxService.boxStyleRfidProductionList(storageScheduleLog.getBoxInfo().getBoxSeq(), style);
			} else if (flag.equals("10-R")) {
				rfidList = boxService.boxStyleRfidStoreList(storageScheduleLog.getBoxInfo().getBoxSeq(), style);
			}

			for (RfidModel rfid : rfidList) {
				StorageScheduleSubDetailLog storageScheduleSubDetailLog = new StorageScheduleSubDetailLog();
				storageScheduleSubDetailLog.setRfidTag(rfid.getRfidTag());

				storageScheduleSubDetailLogSet.add(storageScheduleSubDetailLog);
			}

			storageScheduleDetailLog.setStorageScheduleSubDetailLog(storageScheduleSubDetailLogSet);

			storageScheduleLog.getStorageScheduleDetailLog().add(storageScheduleDetailLog);

			lineNo += 10;
		}

		storageScheduleLog.setUpdDate(new Date());
		storageScheduleLog.setUpdUserInfo(userInfo);

		return storageScheduleLogRepository.save(storageScheduleLog);
	}

	@Transactional(readOnly = true)
	@Override
	public StorageScheduleModel storageScheduleLogCount(String startDate, String endDate, Long startCompanySeq, String confirmYn, String completeYn, String style, String color, String size, String option, String search, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		StringBuffer query = new StringBuffer();
		query.append("SELECT ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat1_box_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat2_box_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat3_box_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat1_style_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat2_style_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat3_style_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat1_tag_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat2_tag_count") + ", ");
		query.append(generateCountQuery(startDate, endDate, startCompanySeq, style, color, size, option, search, type, "stat3_tag_count"));

		Map<String,Object> params = generateCountParam(startDate, endDate, startCompanySeq, style, color, size, option, search, type);

		return nameTemplate.queryForObject(query.toString(), params, new StorageScheduleCountMapper());
	}

	// 통계 쿼리 생성기
	private String generateCountQuery(String startDate, String endDate, Long startCompanySeq, String style, String color, String size, String option, String search, String type, String asName) throws Exception {

		StringBuffer query = new StringBuffer();
		StringBuffer selectQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		if(asName.contains("_box_")) {
			selectQuery.append("DISTINCT COUNT(DISTINCT tssl.storage_schedule_log_seq) ");
		} else if(asName.contains("_style_")) {
			selectQuery.append("COUNT(*) ");
		} else if(asName.contains("_tag_")) {
			selectQuery.append("ISNULL(SUM(tssdl.amount), 0) ");
		}

		if(asName.contains("stat1_")) {
			whereQuery.append("AND tssl.confirm_yn = 'N' ");
			whereQuery.append("AND tssl.complete_yn = 'N' ");
		} else if(asName.contains("stat2_")) {
			whereQuery.append("AND tssl.confirm_yn = 'Y' ");
			whereQuery.append("AND tssl.complete_yn = 'N' ");
		} else if(asName.contains("stat3_")) {
			whereQuery.append("AND tssl.confirm_yn = 'Y' ");
			whereQuery.append("AND tssl.complete_yn = 'Y' ");
		}

		query.append("(SELECT " +
						selectQuery.toString() +
					    "FROM storage_schedule_log tssl " +
				 "INNER JOIN storage_schedule_detail_log tssdl " +
					    "ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq " +
			     "INNER JOIN box_info tbi " +
					    "ON tssl.box_seq = tbi.box_seq " +
					 "WHERE tssl.create_date BETWEEN :startDate AND :endDate " +
					   "AND tssl.disuse_yn = 'N' " +
					   "AND tbi.type = :type ");

		query.append(whereQuery.toString());

		if(startCompanySeq != 0) {
			query.append("AND tbi.start_company_seq = :startCompanySeq ");
		}

		if(!style.equals("")) {
			query.append("AND tssdl.style = :style ");
		}

		if(!color.equals("")) {
			query.append("AND tssdl.color = :color ");
		}

		if(!size.equals("")) {
			query.append("AND tssdl.size = :size ");
		}

		if(option.equals("boxNum") && !search.equals("")) {
			query.append("AND tbi.barcode LIKE :search ");
		}

		query.append(") " + asName + " ");

		return query.toString();
	}

	// 통계 쿼리 파라미터 생성
	private Map<String, Object> generateCountParam(String startDate, String endDate, Long startCompanySeq, String style, String color, String size, String option, String search, String type) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();

		if(!startDate.equals("")) {
			params.put("startDate", startDate);
		}

		if(!endDate.equals("")) {
			params.put("endDate", endDate);
		}

		if(startCompanySeq != 0) {
			params.put("startCompanySeq", startCompanySeq);
		}

		if(!style.equals("")) {
			params.put("style", style);
		}

		if(!color.equals("")) {
			params.put("color", color);
		}

		if(!size.equals("")) {
			params.put("size", size);
		}

		if(!type.equals("")) {
			params.put("type", type);
		}

		if(option.equals("boxNum") && !search.equals("")) {
			params.put("search", "%" + search + "%");
		}

		return params;
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storageScheduleLogBoxCount(String startDate, String endDate, Long startCompanySeq, String flag, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'N' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");

		query.append("WHERE bi.type = :type ");
		params.put("type", type);

		query.append("AND ssl.disuse_yn = 'N' ");

		if(flag.equals("")){
			query.append("AND bi.arrival_date BETWEEN :startDate AND :endDate ");

			params.put("startDate", start);
			params.put("endDate", end);
		}

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", startCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new StorageScheduleRowMapper());
	}

	@Override
	public CountModel storageScheduleLogTagCount(String startDate, String endDate, Long startCompanySeq, String flag, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'N' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat1_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssdl.amount END), 0) AS stat3_amount, ");
		query.append("ISNULL(SUM(ssdl.amount), 0) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");

		query.append("WHERE bi.type = :type ");
		params.put("type", type);

		query.append("AND ssl.disuse_yn = 'N' ");

		if(flag.equals("")){
			query.append("AND bi.arrival_date BETWEEN :startDate AND :endDate ");

			params.put("startDate", start);
			params.put("endDate", end);
		}

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", startCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new StorageScheduleRowMapper());
	}


	@Transactional(readOnly = true)
	@Override
	public CountModel storageScheduleLogBoxCount(String createDate, String confirmYn, String completeYn, Long startCompanySeq, String orderType) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'N' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN storage_schedule_sub_detail_log sssdl ");
		query.append("ON ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");

		query.append("WHERE ssl.order_type = :orderType ");
		params.put("orderType", orderType);

		if(!confirmYn.equals("all")){
			query.append("AND ssl.confirm_yn = :confirmYn ");
			params.put("confirmYn", confirmYn);
		}

		if(!completeYn.equals("all")){
			query.append("AND ssl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.create_date = :createDate ");
		params.put("createDate", createDate);

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", startCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new StorageScheduleRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel storageScheduleLogTagCount(String createDate, String confirmYn, String completeYn, Long startCompanySeq, String orderType) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'N' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat1_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssdl.amount END), 0) AS stat3_amount, ");
		query.append("ISNULL(SUM(ssdl.amount), 0) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");

		query.append("WHERE ssl.order_type = :orderType ");
		params.put("orderType", orderType);

		if(!confirmYn.equals("all")){
			query.append("AND ssl.confirm_yn = :confirmYn ");
			params.put("confirmYn", confirmYn);
		}

		if(!completeYn.equals("all")){
			query.append("AND ssl.complete_yn = :completeYn ");
			params.put("completeYn", completeYn);
		}

		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.create_date = :createDate ");
		params.put("createDate", createDate);

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", startCompanySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new StorageScheduleRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageScheduleLogBoxCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' THEN ssl.confirm_yn END) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type != '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}


	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageScheduleLogStyleCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' THEN ssl.confirm_yn END) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type != '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageScheduleLogTagCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat1_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' THEN ssdl.amount END), 0) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type != '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long maxWorkLine(String createDate, String orderType, Long companySeq) throws Exception{
		StorageScheduleLog storageScheduleLog = storageScheduleLogRepository.findTop1ByCreateDateAndOrderTypeAndBoxInfoStartCompanyInfoCompanySeqOrderByWorkLineDesc(createDate, orderType, companySeq);

		Long maxWorkLine;

		if(storageScheduleLog == null){
			maxWorkLine = Long.valueOf(0);
		} else {
			maxWorkLine = storageScheduleLog.getWorkLine();
		}
		return maxWorkLine;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> storeScheduleBatch(String startDate, String endDate, Long companySeq) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> boxCountObj = new HashMap<String, Object>();
		Map<String, Object> boxTagCountObj = new HashMap<String, Object>();

		CountModel boxCountModel = storageScheduleLogService.distributionStockStorageScheduleLogBoxCount(startDate, endDate, companySeq);
		boxCountObj.put("stat1Amount", boxCountModel.getStat1_amount());
		boxCountObj.put("stat2Amount", boxCountModel.getStat2_amount());
		boxCountObj.put("totalAmount", boxCountModel.getTotal_amount());

		CountModel boxTagCountModel = storageScheduleLogService.distributionStockStorageScheduleLogTagCount(startDate, endDate, companySeq);
		boxTagCountObj.put("stat1Amount", boxTagCountModel.getStat1_amount());
		boxTagCountObj.put("stat2Amount", boxTagCountModel.getStat2_amount());
		boxTagCountObj.put("totalAmount", boxTagCountModel.getTotal_amount());

		obj.put("boxCount", boxCountObj);
		obj.put("boxTagCount", boxTagCountObj);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleModel> findReleaseGroupList(String startDate,
														String endDate,
														Long startCompanySeq,
														String confirmYn,
														String type,
														String search,
														String option,
														Pageable pageable) throws Exception{

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "ssl.create_date DESC, ssl.work_line DESC");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ssl.create_date, ssl.work_line, ssl.arrival_date, bi.start_company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'N' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat3_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'N' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat3_style_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'N' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat2_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS stat3_tag_count ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");

		query.append("AND bi.type = :type ");
		params.put("type", type);

		query.append("AND ssl.disuse_yn = 'N' ");

		if(startCompanySeq != 0){
			query.append("AND ci.company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		}
		query.append("GROUP BY ssl.create_date, ssl.work_line, ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, work_line, arrival_date, start_company_seq, name, stat1_box_count, stat2_box_count, stat3_box_count, stat1_style_count, stat2_style_count, stat3_style_count, stat1_tag_count, stat2_tag_count, stat3_tag_count ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StorageScheduleModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleModel> findReleaseGroupList(String createDate, Long startCompanySeq, String confirmYn, String completeYn, String type) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ssl.create_date, ssl.work_line, ssl.confirm_yn, ssl.create_date, bi.start_company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = ssl.confirm_yn ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = ssl.confirm_yn ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS style_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date ");
		query.append("AND tssl.work_line = ssl.work_line ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append("AND tssl.confirm_yn = ssl.confirm_yn ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append(") AS tag_count ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");

		query.append("WHERE ssl.create_date = :createDate ");
		params.put("createDate", createDate);

		query.append("AND bi.type = :type ");
		params.put("type", type);

		query.append("AND ssl.disuse_yn = 'N' ");

		query.append("AND ci.company_seq = :startCompanySeq ");
		params.put("startCompanySeq", startCompanySeq);

		query.append("AND ssl.confirm_yn = :confirmYn ");
		params.put("confirmYn", confirmYn);

		query.append("AND ssl.complete_yn = :completeYn ");
		params.put("completeYn", completeYn);

		query.append("GROUP BY ssl.create_date, ssl.work_line, ssl.confirm_yn, ssl.create_date, bi.start_company_seq, ci.name ");
		query.append("ORDER BY ssl.create_date DESC, ssl.work_line ");

		return nameTemplate.query(query.toString(), params, new StorageScheduleModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountReleaseGroupList(String startDate, String endDate, Long startCompanySeq, String confirmYn, String type, String search, String option) throws Exception{

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ssl.create_date, ssl.work_line, ssl.confirm_yn, bi.start_company_seq ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.arrival_date BETWEEN :startDate AND :endDate ");

		query.append("AND bi.type = :type ");
		params.put("type", type);

		query.append("AND ssl.disuse_yn = 'N' ");

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		}

		if(!confirmYn.equals("all")){
			query.append("AND ssl.confirm_yn = :confirmYn ");
			params.put("confirmYn", confirmYn);
		}
		query.append("GROUP BY ssl.create_date, ssl.work_line, ssl.confirm_yn, bi.start_company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", start);
		params.put("endDate", end);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleGroupModel> findStorageScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "arrival_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ssl.arrival_date, bi.start_company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type != '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_tag_count ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.order_type != '10-R' ");
		query.append("AND ssl.confirm_yn = 'Y' ");
		query.append("AND ssl.batch_yn = 'Y' ");
		query.append("AND ssl.disuse_yn = 'N' ");

		if(companySeq != 0) {
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssl.confirm_yn, ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT start_company_seq, name, arrival_date, stat1_box_count, stat2_box_count, (stat1_box_count + stat2_box_count) AS total_box_count, ");
		query.append("stat1_style_count, stat2_style_count, (stat1_style_count + stat2_style_count) AS total_style_count, ");
		query.append("stat1_tag_count, stat2_tag_count, (stat1_tag_count + stat2_tag_count) AS total_tag_count, ");
		query.append("CAST(CAST(stat2_tag_count AS FLOAT)/CAST((stat1_tag_count + stat2_tag_count) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StorageScheduleGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountStorageScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.order_type != '10-R' ");
		query.append("AND ssl.confirm_yn = 'Y' ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.batch_yn = 'Y' ");

		if(companySeq != 0) {
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional
	@Override
	public Map<String, Object> updateReleaseGroup(List<StorageScheduleModel> groupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();
	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleModel group : groupList){

			List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogRepository.findByCreateDateAndWorkLineAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndDisuseYn(group.getCreateDate(), Long.valueOf(group.getWorkLine()), group.getStartCompanySeq(), "N", "N");

			for(StorageScheduleLog scheduleLog : storageScheduleLogList){

				if(scheduleLog.getConfirmYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
					obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
					return obj;
				}

				if(scheduleLog.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
					return obj;
				}

				Long checkCount = storageScheduleLogRepository.validProductionRfidTag(scheduleLog.getStorageScheduleLogSeq(), "3");
				Long totalAmount = Long.valueOf(0);

				for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
					totalAmount += scheduleDetailLog.getAmount();
				}

				if(!checkCount.equals(totalAmount)) {
					obj.put("resultCode", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG_MESSAGE);

					return obj;
				}

				scheduleLog.setTempArrivalDate(group.getArrivalDate());

				scheduleLogList.add(scheduleLog);
			}
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setArrivalDate(CalendarUtil.convertStartDate(scheduleLog.getTempArrivalDate()));
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			scheduleLog.setBoxInfo(boxInfo);

			scheduleLog.setArrivalDate(scheduleLog.getTempArrivalDate().replaceAll("-", ""));
	    	scheduleLog.setConfirmDate(new Date());
			scheduleLog.setConfirmYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			boxInfoList.add(scheduleLog.getBoxInfo());
	    }

	    boxInfoRepository.save(boxInfoList);
		storageScheduleLogRepository.save(scheduleLogList);

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> deleteReleaseGroup(List<StorageScheduleModel> releaseGroupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleModel releaseGroup : releaseGroupList){

			List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogRepository.findByCreateDateAndWorkLineAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndDisuseYn(releaseGroup.getCreateDate(), Long.valueOf(releaseGroup.getWorkLine()), releaseGroup.getStartCompanySeq(), "N", "N");

			for(StorageScheduleLog scheduleLog : storageScheduleLogList){

				if(scheduleLog.getConfirmYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
					obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
					return obj;
				}

				if(scheduleLog.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
					return obj;
				}

				scheduleLogList.add(scheduleLog);
			}
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setArrivalDate(null);
			boxInfo.setStat("1");
			boxInfoRepository.save(boxInfo);

			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), boxInfo.getBoxSeq());

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				productionStorageSeqList.add(scheduleDetailLog.getStyleSeq());
			}

	    }

	    storageScheduleLogRepository.delete(scheduleLogList);

    	productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "8", "1");

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}


	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional
	@Override
	public Map<String, Object> updateRelease(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();
	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleLog tempScheduleLog : storageScheduleLogList){

	    	StorageScheduleLog scheduleLog = storageScheduleLogRepository.findOne(tempScheduleLog.getStorageScheduleLogSeq());

			if(scheduleLog.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
				return obj;
			}

			if(scheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
				return obj;
			}

			Long checkCount = storageScheduleLogRepository.validProductionRfidTag(scheduleLog.getStorageScheduleLogSeq(), "3");
			Long totalAmount = Long.valueOf(0);

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				totalAmount += scheduleDetailLog.getAmount();
			}

			if(!checkCount.equals(totalAmount)) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG_MESSAGE);

				return obj;
			}

			scheduleLogList.add(tempScheduleLog);
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			scheduleLog.setBoxInfo(boxInfo);

			scheduleLog.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate()));
	    	scheduleLog.setConfirmDate(new Date());
			scheduleLog.setConfirmYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			boxInfoList.add(scheduleLog.getBoxInfo());
	    }

	    boxInfoRepository.save(boxInfoList);
		storageScheduleLogRepository.save(scheduleLogList);

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional
	@Override
	public Map<String, Object> deleteRelease(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> productionStorageSeqList = new ArrayList<Long>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleLog tempScheduleLog : storageScheduleLogList){

	    	StorageScheduleLog scheduleLog = storageScheduleLogRepository.findOne(tempScheduleLog.getStorageScheduleLogSeq());

	    	if(scheduleLog.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
				return obj;
			}

			if(scheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
				return obj;
			}

			scheduleLogList.add(scheduleLog);
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setArrivalDate(null);
			boxInfo.setStat("1");
			boxInfoRepository.save(boxInfo);

			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), boxInfo.getBoxSeq());

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				productionStorageSeqList.add(scheduleDetailLog.getStyleSeq());
			}
	    }

	    storageScheduleLogRepository.delete(scheduleLogList);

    	productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "8", "1");

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional
	@Override
	public Map<String, Object> deleteReleaseConfirm(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();
		List<Long> initProductionStorageSeqList = new ArrayList<Long>();
		List<Long> initDistributionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleLog tempScheduleLog : storageScheduleLogList){

	    	StorageScheduleLog scheduleLog = storageScheduleLogRepository.findOne(tempScheduleLog.getStorageScheduleLogSeq());

			if(scheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
				return obj;
			}

			scheduleLogList.add(scheduleLog);
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

			// 생산 출고 > 입고로 변경
			List<ProductionStorageRfidTag> productionRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (ProductionStorageRfidTag tempRfidTag : productionRfidTagList) {
				initProductionStorageSeqList.add(tempRfidTag.getProductionStorageSeq());
			}

			// 생산 출고된 태그 정보 롤백
			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고 예정정보 삭제
			List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (DistributionStorageRfidTag tempRfidTag : distributionRfidTagList) {
				initDistributionStorageSeqList.add(tempRfidTag.getDistributionStorageSeq());
			}

			// 물류 입고된 태그 정보 삭제
			distributionStorageRfidTagService.deleteBoxInfo(scheduleLog.getBoxInfo().getBoxSeq());

			scheduleLog.setDisuseYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			scheduleLog.getBoxInfo().setStat("4");
			scheduleLog.getBoxInfo().setUpdDate(new Date());
			scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

			boxInfoList.add(scheduleLog.getBoxInfo());
	    }

	    // 생산 출고예정 정보 사용안함 처리
	 	storageScheduleLogRepository.save(scheduleLogList);

	 	// 박스 사용 안함 처리
	 	boxInfoRepository.save(boxInfoList);
	 	rfidTagHistoryRepository.save(rfidTagHistoryList);

	 	// 타겟 생산, 물류 수량 변경
	 	productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
	 	distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> updateReturnGroup(List<StorageScheduleModel> groupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();
	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleModel group : groupList){

			List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogRepository.findByCreateDateAndWorkLineAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndDisuseYn(group.getCreateDate(), Long.valueOf(group.getWorkLine()), group.getStartCompanySeq(), "N", "N");

			for(StorageScheduleLog scheduleLog : storageScheduleLogList){

				if(scheduleLog.getConfirmYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
					obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
					return obj;
				}

				if(scheduleLog.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
					return obj;
				}

				Long checkCount = storageScheduleLogRepository.validStoreRfidTag(scheduleLog.getStorageScheduleLogSeq());
				Long totalAmount = Long.valueOf(0);

				for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
					totalAmount += scheduleDetailLog.getAmount();
				}

				if(checkCount != totalAmount) {
					obj.put("resultCode", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG_MESSAGE);

					return obj;
				}

				scheduleLog.setTempArrivalDate(group.getArrivalDate());

				scheduleLogList.add(scheduleLog);
			}
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
	    	boxInfo.setArrivalDate(CalendarUtil.convertStartDate(scheduleLog.getTempArrivalDate()));
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			scheduleLog.setBoxInfo(boxInfo);

			scheduleLog.setArrivalDate(scheduleLog.getTempArrivalDate().replaceAll("-", ""));
	    	scheduleLog.setConfirmDate(new Date());
			scheduleLog.setConfirmYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			boxInfoList.add(scheduleLog.getBoxInfo());
	    }

	    boxInfoRepository.save(boxInfoList);
		storageScheduleLogRepository.save(scheduleLogList);

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> deleteReturnGroup(List<StorageScheduleModel> groupList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleModel group : groupList){

			List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogRepository.findByCreateDateAndWorkLineAndBoxInfoStartCompanyInfoCompanySeqAndConfirmYnAndDisuseYn(group.getCreateDate(), Long.valueOf(group.getWorkLine()), group.getStartCompanySeq(), "N", "N");

			for(StorageScheduleLog scheduleLog : storageScheduleLogList){

				if(scheduleLog.getConfirmYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
					obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
					return obj;
				}

				if(scheduleLog.getCompleteYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
					obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
					return obj;
				}

				scheduleLogList.add(scheduleLog);
			}
		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

			BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setArrivalDate(null);
			boxInfo.setStat("1");
			boxInfoRepository.save(boxInfo);

			storeStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), boxInfo.getBoxSeq());

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				storeStorageSeqList.add(scheduleDetailLog.getStyleSeq());
			}

			storageScheduleLogRepository.delete(scheduleLog);
	    }

    	storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "1");

    	obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> updateReturn(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();
	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

		for (StorageScheduleLog tempScheduleLog : storageScheduleLogList) {

			StorageScheduleLog scheduleLog = storageScheduleLogRepository.findOne(tempScheduleLog.getStorageScheduleLogSeq());

			if(scheduleLog.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
				return obj;
			}

			if(scheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
				return obj;
			}

			Long checkCount = storageScheduleLogRepository.validStoreRfidTag(scheduleLog.getStorageScheduleLogSeq());
			Long totalAmount = Long.valueOf(0);

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				totalAmount += scheduleDetailLog.getAmount();
			}

			if(checkCount != totalAmount) {
				obj.put("resultCode", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG);
				obj.put("resultMessage", ApiResultConstans.NOT_FIND_RELEASE_RFID_TAG_MESSAGE);

				return obj;
			}

			scheduleLogList.add(tempScheduleLog);
		}

		for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);

			scheduleLog.setBoxInfo(boxInfo);

			scheduleLog.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate()));
	    	scheduleLog.setConfirmDate(new Date());
			scheduleLog.setConfirmYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			boxInfoList.add(scheduleLog.getBoxInfo());
	    }

		boxInfoRepository.save(boxInfoList);
		storageScheduleLogRepository.save(scheduleLogList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

	    return obj;
	}

	@Override
	public Map<String, Object> deleteReturn(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Date startDate = new Date();

		ArrayList<Long> storeStorageSeqList = new ArrayList<Long>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

		LoginUser user = SecurityUtil.getCustomUser();

	    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());

	    if(userInfo == null){
	    	obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
	    }

	    for(StorageScheduleLog scheduleLog : storageScheduleLogList){

    		if(scheduleLog.getConfirmYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.CONFIRM_ORDER);
				obj.put("resultMessage", ApiResultConstans.CONFIRM_ORDER_MESSAGE);
				return obj;
			}

			if(scheduleLog.getCompleteYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.COMPLETE_ORDER);
				obj.put("resultMessage", ApiResultConstans.COMPLETE_ORDER_MESSAGE);
				return obj;
			}

			scheduleLogList.add(scheduleLog);

		}

	    for(StorageScheduleLog scheduleLog : scheduleLogList) {

	    	BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setArrivalDate(null);
			boxInfo.setStat("1");
			boxInfoRepository.save(boxInfo);

			storeStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), boxInfo.getBoxSeq());

			for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){
				storeStorageSeqList.add(scheduleDetailLog.getStyleSeq());
			}
	    }

	    storageScheduleLogRepository.delete(scheduleLogList);

    	storeStorageLogService.save(storeStorageSeqList, userInfo, startDate, "4", "1");

	    obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}


	@Retryable(value = {SQLException.class}, maxAttempts = 3)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public ApiStorecheduleCompleteResult storeScheduleComplete(String barcode, Long userSeq, String type) throws Exception {

		String nowDate = CalendarUtil.convertFormat("yyyyMMdd");

		boolean success = true;

		if(userSeq == 0){
			return new ApiStorecheduleCompleteResult(ApiResultConstans.NOT_USE_USER_MESSAGE, ApiResultConstans.NOT_FIND_USER);
		}

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		if(userInfo == null){
			return new ApiStorecheduleCompleteResult(ApiResultConstans.NOT_USE_USER_MESSAGE, ApiResultConstans.NOT_FIND_USER);
		}

		StorageScheduleLog storageScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

		if(storageScheduleLog == null){
			return new ApiStorecheduleCompleteResult(ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE, ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE);
		}

		if(storageScheduleLog.getCompleteYn().equals("Y")) {
			return new ApiStorecheduleCompleteResult(ApiResultConstans.COMPLETE_BOX_MESSAGE, ApiResultConstans.COMPLETE_BOX);
		}

		/**
		 * 2019-05-30 최의선
		 * 물류 태그 체크 로직 삭제
		 * 트랜잭션 데드락 문제 주 요인. 여기서 검증은 의미없는걸로 판단함
		List<String> distributionStorageRfidTagList = distributionStorageRfidTagService.existsStorageDistriubtionRfidTag(storageScheduleLog.getBoxInfo().getBarcode());

		int count = 0;

		for(StorageScheduleDetailLog detailLog : storageScheduleLog.getStorageScheduleDetailLog()) {
			count += detailLog.getStorageScheduleSubDetailLog().size();
		}

		if(distributionStorageRfidTagList.size() != count) {
			return new ApiStorecheduleCompleteResult(ApiResultConstans.NOT_VALID_RFID_TAG_MESSAGE, ApiResultConstans.NOT_VALID_RFID_TAG, storageScheduleLogService.storeScheduleBatch(nowDate, nowDate, storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCompanySeq()));
		}
		*/

		// WMS 물류 입고 작업(테스트)
		if (type.equals("1")) {
			success = storageUpdate(storageScheduleLog);
		}

		if(!success) {
			return new ApiStorecheduleCompleteResult(ApiResultConstans.ERROR_MESSAGE, ApiResultConstans.ERROR, storageScheduleLogService.storeScheduleBatch(nowDate, nowDate, storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCompanySeq()));
		}

		BoxInfo boxInfo = storageScheduleLog.getBoxInfo();
		boxInfo.setStat("3");
		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setCompleteDate(new Date());

		storageScheduleLog.setCompleteYn("Y");
		storageScheduleLog.setCompleteDate(new Date());
		storageScheduleLog.setUpdDate(new Date());
		storageScheduleLog.setUpdUserInfo(userInfo);
		storageScheduleLog.setWmsCompleteYn("Y");
		storageScheduleLog.setWmsCompleteDate(new Date());

		// 박스 정보 업데이트
		boxInfoRepository.save(boxInfo);

		// 물류 예정 정보 업데이트
		storageScheduleLogRepository.save(storageScheduleLog);

		return new ApiStorecheduleCompleteResult(ApiResultConstans.SUCCESS_MESSAGE, ApiResultConstans.SUCCESS, storageScheduleLogService.storeScheduleBatch(nowDate, nowDate, boxInfo.getStartCompanyInfo().getCompanySeq()));

	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleComplete(List<Map<String, Object>> barcodeList, Long userSeq, String type) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> storageScheduleLogList = new ArrayList<StorageScheduleLog>();

		if(userSeq == 0){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return map;
		}

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		if(userInfo == null){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return map;
		}


		for(Map<String, Object> obj : barcodeList){

			String barcode = obj.get("barcode").toString();

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

			if(boxInfo == null){
				map.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
				map.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
				return map;
			}

			StorageScheduleLog storageScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

			if(storageScheduleLog == null){
				map.put("resultCode", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE);
				map.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
				return map;
			}

			storageScheduleLogList.add(storageScheduleLog);
			boxList.add(boxInfo);
		}
		/*
		boolean success = true;

		// WMS 물류 입고 작업(테스트)
		if(type.equals("1")){
			success = storageUpdate(storageScheduleLogList);
		}

		if(!success) {
			map.put("resultCode", ApiResultConstans.NOT_FIND_WMS_BARCODE);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_WMS_BARCODE_MESSAGE);
			return map;
		}*/

		for(StorageScheduleLog storageScheduleLog : storageScheduleLogList){

			BoxInfo boxInfo = storageScheduleLog.getBoxInfo();
			boxInfo.setStat("3");
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setCompleteDate(new Date());

			boxList.add(boxInfo);

			storageScheduleLog.setCompleteYn("Y");
			storageScheduleLog.setCompleteDate(new Date());
			storageScheduleLog.setUpdDate(new Date());
			storageScheduleLog.setUpdUserInfo(userInfo);
			storageScheduleLog.setWmsCompleteYn("Y");
			storageScheduleLog.setWmsCompleteDate(new Date());
		}

		boxInfoRepository.save(boxList);

		// 물류 예정 정보 업데이트
		storageScheduleLogRepository.save(storageScheduleLogList);

		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleCompleteGroup(List<Map<String, Object>> scheduleGroupList, Long userSeq, String type) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> storageScheduleLogList = new ArrayList<StorageScheduleLog>();

		if(userSeq == 0){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return map;
		}

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		if(userInfo == null){
			map.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			map.put("resultMessage", ApiResultConstans.NOT_USE_USER_MESSAGE);
			return map;
		}

		for(Map<String, Object> obj : scheduleGroupList){

			String arrivalDate = obj.get("arrivalDate").toString();

			List<StorageScheduleLog> storageScheduleList = findAll(arrivalDate, "Y", "N");

			for(StorageScheduleLog scheduleLog : storageScheduleList){

				String barcode = scheduleLog.getBoxInfo().getBarcode();

				BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

				if(boxInfo == null){
					map.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
					map.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
					return map;
				}

				if(boxInfo != null){

					StorageScheduleLog storageScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

					if(storageScheduleLog == null){
						map.put("resultCode", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE);
						map.put("resultMessage", ApiResultConstans.NOT_VALID_STORAGE_SCHEDULE_MESSAGE);
						return map;
					}
				}

				storageScheduleLogList.add(scheduleLog);

				boxList.add(boxInfo);
			}
		}
		/*
		boolean success = true;

		// WMS 물류 입고 작업(테스트)
		if(type.equals("1")){
			success = storageUpdate(storageScheduleLogList);
		}

		if(!success) {
			map.put("resultCode", ApiResultConstans.NOT_FIND_WMS_BARCODE);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_WMS_BARCODE_MESSAGE);
			return map;
		}*/

		for(StorageScheduleLog storageScheduleLog : storageScheduleLogList){

			BoxInfo boxInfo = storageScheduleLog.getBoxInfo();
			boxInfo.setStat("3");
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setCompleteDate(new Date());

			boxList.add(boxInfo);

			storageScheduleLog.setCompleteYn("Y");
			storageScheduleLog.setCompleteDate(new Date());
			storageScheduleLog.setUpdDate(new Date());
			storageScheduleLog.setUpdUserInfo(userInfo);
			storageScheduleLog.setWmsCompleteYn("Y");
			storageScheduleLog.setWmsCompleteDate(new Date());
		}

		boxInfoRepository.save(boxList);

		// 물류 예정 정보 업데이트
		storageScheduleLogRepository.save(storageScheduleLogList);

		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleReturn(List<StorageScheduleLog> storageScheduleLogList, Long userSeq, String type) throws Exception {

		Date startDate = new Date();

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<Long> initProductionStorageSeqList = new ArrayList<Long>();
		List<Long> initDistributionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfList = new ArrayList<BoxInfo>();

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

		for(StorageScheduleLog scheduleLog : storageScheduleLogList) {

			StorageScheduleLog tempScheduleLog = storageScheduleLogRepository.findOne(scheduleLog.getStorageScheduleLogSeq());

			if(tempScheduleLog.getConfirmYn().equals("N")){
				obj.put("resultCode", ApiResultConstans.NOT_CONFIRM_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_CONFIRM_BOX_MESSAGE);
				return obj;
			}

			if(tempScheduleLog.getCompleteYn().equals("N")){
				obj.put("resultCode", ApiResultConstans.NOT_COMPLETE_BOX);
				obj.put("resultMessage", ApiResultConstans.NOT_COMPLETE_BOX_MESSAGE);
				return obj;
			}

			if(tempScheduleLog.getReturnYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.STORAGE_RETURN_BOX);
				obj.put("resultMessage", ApiResultConstans.STORAGE_RETURN_BOX_MESSAGE);
				return obj;
			}

			if(tempScheduleLog.getDisuseYn().equals("Y")){
				obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
				obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
				return obj;
			}
		}

		for(StorageScheduleLog scheduleLog : storageScheduleLogList) {

			// 생산 출고 > 입고로 변경
			List<ProductionStorageRfidTag> productionRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (ProductionStorageRfidTag tempRfidTag : productionRfidTagList) {
				initProductionStorageSeqList.add(tempRfidTag.getProductionStorageSeq());
			}

			// 생산 출고된 태그 정보 롤백
			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고 예정정보 삭제
			List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (DistributionStorageRfidTag tempRfidTag : distributionRfidTagList) {
				initDistributionStorageSeqList.add(tempRfidTag.getDistributionStorageSeq());

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

			// 물류 입고된 태그 정보 삭제
			distributionStorageRfidTagService.deleteBoxInfo(scheduleLog.getBoxInfo().getBoxSeq());

			scheduleLog.setReturnYn("Y");
			scheduleLog.setDisuseYn("Y");
			scheduleLog.setReturnDate(new Date());
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			scheduleLog.getBoxInfo().setStat("4");
			scheduleLog.getBoxInfo().setUpdDate(new Date());
			scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

			boxInfList.add(scheduleLog.getBoxInfo());

			// ERP 입고반송 처리
			rfidIb01IfRepository.save(erpService.saveStorageListRollback(scheduleLog));
		}

		// 생산 출고예정 정보 사용안함 처리
		storageScheduleLogRepository.save(storageScheduleLogList);

		// 박스 사용 안함 처리
		boxInfoRepository.save(boxInfList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		// 타겟 생산, 물류 수량 변경
		productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
		distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> storeScheduleReturnGroup(List<StorageScheduleModel> groupList, Long userSeq, String type) throws Exception {

		Date startDate = new Date();

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<Long> initProductionStorageSeqList = new ArrayList<Long>();
		List<Long> initDistributionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfList = new ArrayList<BoxInfo>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();

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

		for(StorageScheduleModel group : groupList){

			Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.arrivalDateEqual(group.getArrivalDate()))
																			  .and(StorageScheduleLogSpecification.startCompanySeqEqual(group.getStartCompanySeq()))
																			  .and(StorageScheduleLogSpecification.disuseYnEqual("N"))
																			  .and(StorageScheduleLogSpecification.confirmYnEqual("Y"));

			List<StorageScheduleLog> tempStorageScheduleLogList = storageScheduleLogRepository.findAll(specifications);

			for(StorageScheduleLog scheduleLog : tempStorageScheduleLogList) {

				if(scheduleLog.getCompleteYn().equals("N")){
					obj.put("resultCode", ApiResultConstans.NOT_COMPLETE_BOX);
					obj.put("resultMessage", ApiResultConstans.NOT_COMPLETE_BOX_MESSAGE);
					return obj;
				}

				if(scheduleLog.getReturnYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.STORAGE_RETURN_BOX);
					obj.put("resultMessage", ApiResultConstans.STORAGE_RETURN_BOX_MESSAGE);
					return obj;
				}

				if(scheduleLog.getDisuseYn().equals("Y")){
					obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
					obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
					return obj;
				}

				scheduleLogList.add(scheduleLog);
			}

		}

		for(StorageScheduleLog scheduleLog : scheduleLogList) {

			// 생산 출고 > 입고로 변경
			List<ProductionStorageRfidTag> productionRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (ProductionStorageRfidTag tempRfidTag : productionRfidTagList) {
				initProductionStorageSeqList.add(tempRfidTag.getProductionStorageSeq());
			}

			// 생산 출고된 태그 정보 롤백
			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고 예정정보 삭제
			List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (DistributionStorageRfidTag tempRfidTag : distributionRfidTagList) {
				initDistributionStorageSeqList.add(tempRfidTag.getDistributionStorageSeq());

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

			// 물류 입고된 태그 정보 삭제
			distributionStorageRfidTagService.deleteBoxInfo(scheduleLog.getBoxInfo().getBoxSeq());

			scheduleLog.setDisuseYn("Y");
			scheduleLog.setReturnYn("Y");
			scheduleLog.setReturnDate(new Date());
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			scheduleLog.getBoxInfo().setStat("4");
			scheduleLog.getBoxInfo().setUpdDate(new Date());
			scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

			boxInfList.add(scheduleLog.getBoxInfo());

			// ERP 입고반송 처리
			rfidIb01IfRepository.save(erpService.saveStorageListRollback(scheduleLog));
		}

		// 생산 출고예정 정보 사용안함 처리
		storageScheduleLogRepository.save(scheduleLogList);

		// 박스 사용 안함 처리
		boxInfoRepository.save(boxInfList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		// 타겟 생산, 물류 수량 변경
		productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
		distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public ApiStorecheduleCompleteResult updateStoreScheduleExceptionComplete(List<StorageScheduleLog> scheduleLogList, UserInfo userInfo, String type) throws Exception {

		String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();
		List<RfidIb10If> rfidIb10IfList = new ArrayList<RfidIb10If>();
		List<OpenDbScheduleHeader> openDbHeaderList = new ArrayList<OpenDbScheduleHeader>();
		List<OpenDbScheduleDetail> openDbScheduleDetailList = new ArrayList<OpenDbScheduleDetail>();

		for(StorageScheduleLog scheduleLog : scheduleLogList) {

			BoxInfo boxInfo = scheduleLog.getBoxInfo();
			boxInfo.setStat("3");
			boxInfo.setUpdDate(new Date());
			boxInfo.setUpdUserInfo(userInfo);
			boxInfo.setCompleteDate(new Date());
			boxInfo.setArrivalDate(new Date());

			boxList.add(boxInfo);

			scheduleLog.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate()));
			scheduleLog.setBatchYn("Y");
			scheduleLog.setBatchDate(new Date());
			scheduleLog.setConfirmDate(new Date());
			scheduleLog.setCompleteYn("Y");
			scheduleLog.setCompleteDate(new Date());
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			Set<StorageScheduleDetailLogModel> tempErpDetailList = generateScheduleLog(scheduleLog, "ERP");
			Set<StorageScheduleDetailLogModel> tempWmsDetailList = generateScheduleLog(scheduleLog, "WMS");

			// ERP 데이터 기록
			for(StorageScheduleDetailLogModel detailLog : tempErpDetailList){
				rfidIb10IfList.add(erpService.saveStoreSchedule(boxInfo, detailLog.getLineNum(), detailLog, userInfo, nowDate, boxInfo.getInvoiceNum(), scheduleLog.getOrderType()));
			}

			// openDb 헤더 데이터 기록
			openDbHeaderList.add(openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, scheduleLog.getArrivalDate(), scheduleLog.getOrderType()));

			// openDb 디테일 데이터 기록
			for(StorageScheduleDetailLogModel detailLog : tempWmsDetailList){
				openDbScheduleDetailList.add(openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, scheduleLog.getArrivalDate(), scheduleLog.getOrderType()));
			}
		}

		boxInfoRepository.save(boxList);

		// ERP 입고예정 정보 저장
		rfidIb10IfRepository.save(rfidIb10IfList);

		// openDB 헤더, 디테일 정보 저장
		openDbScheduleHeaderRepository.save(openDbHeaderList);
		openDbScheduleDetailRepository.save(openDbScheduleDetailList);

		for(StorageScheduleLog scheduleLog : scheduleLogList) {

			if(rfidIb10IfList.size() > 0) {
				scheduleLog.setErpScheduleYn("Y");
				scheduleLog.setErpScheduleDate(new Date());
			}

			if(openDbScheduleDetailList.size() > 0) {
				scheduleLog.setOpenDbScheduleYn("Y");
				scheduleLog.setOpenDbScheduleDate(new Date());
			}

			scheduleLog.setWmsCompleteYn("Y");
			scheduleLog.setWmsCompleteDate(new Date());
		}

		// 물류 예정 정보 업데이트
		storageScheduleLogRepository.save(scheduleLogList);
		return new ApiStorecheduleCompleteResult(ApiResultConstans.SUCCESS_MESSAGE, ApiResultConstans.SUCCESS, storageScheduleLogService.storeScheduleBatch(nowDate, nowDate, boxList.get(0).getStartCompanyInfo().getCompanySeq()));
	}

	@Transactional
	@Override
	public void storageInit(StorageScheduleLog storageScheduleLog) throws Exception {

		String flag = env.getProperty("test.mode.flag");

		if(flag.equals("Y")){

			TreceiptKey key = new TreceiptKey();
			key.setReceiptno(storageScheduleLog.getBoxInfo().getBarcode());
			key.setwCode("LW02");

			Treceipt treceipt = new Treceipt();
			treceipt.setKey(key);
			treceipt.setCustomerPoNo(storageScheduleLog.getBoxInfo().getBarcode());

			treceiptRepository.save(treceipt);

			for(StorageScheduleDetailLog detailLog : storageScheduleLog.getStorageScheduleDetailLog()){

				TreceiptDetailKey detialKey = new TreceiptDetailKey();
				detialKey.setwCode("LW02");
				detialKey.setLineNo(new BigDecimal(detailLog.getLineNum()));
				detialKey.setReceiptNo(storageScheduleLog.getBoxInfo().getBarcode());

				TreceiptDetail treceiptDetail = new TreceiptDetail();
				treceiptDetail.setKey(detialKey);
				treceiptDetail.setDelFlag("N");
				treceiptDetail.setStyle(detailLog.getStyle());
				treceiptDetail.setColor(detailLog.getColor());
				treceiptDetail.setStyleSize(detailLog.getSize());
				treceiptDetail.setAmount(new BigDecimal(detailLog.getAmount()));
				treceiptDetail.setStatus("999");

				treceiptDetailRepository.save(treceiptDetail);
			}
		}
	}

	@Transactional
	@Override
	public boolean storageUpdate(StorageScheduleLog scheduleLog) throws Exception {

		boolean success = true;

		String flag = env.getProperty("prod.before.test.mode.flag");

		if(flag.equals("Y")){

			Treceipt treceipt = lepsilonService.storageBarcode(scheduleLog.getBoxInfo().getBarcode());

			if(treceipt == null) {
				log.error("Trecipt 데이터 없음");
				return success = false;
			}

			boolean detailSuccess = true;

			detailSuccess = lepsilonService.storageUpdate(treceipt.getKey().getReceiptno());

			if(!detailSuccess) {
				return success = false;
			}
		}

		return success;
	}

	@Transactional
	@Override
	public boolean storageUpdate(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		boolean success = true;

		String flag = env.getProperty("prod.before.test.mode.flag");

		List<Treceipt> treceiptList = new ArrayList<Treceipt>();

		if(flag.equals("Y")){

			for(StorageScheduleLog scheduleLog : storageScheduleLogList){

				Treceipt treceipt = lepsilonService.storageBarcode(scheduleLog.getBoxInfo().getBarcode());

				if(treceipt == null) {
					log.error("Trecipt 데이터 없음");
					return success = false;
				}

				treceiptList.add(treceipt);
			}

			boolean detailSuccess = true;

			for(Treceipt treceipt : treceiptList) {
				detailSuccess = lepsilonService.storageUpdate(treceipt.getKey().getReceiptno());

				if(!detailSuccess) {
					return success = false;
				}
			}
		}

		return success;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReturnGroupModel> findReturnGroupList(String startDate, String endDate, Long companySeq, String orderType, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "ssl.create_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ssl.create_date, ci.company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'N') AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'Y') AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'Y' AND tssl.confirm_yn = 'Y') AS stat3_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'N') AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'Y') AS stat2_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'Y' AND tssl.confirm_yn = 'Y') AS stat3_style_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'N') AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'N' AND tssl.confirm_yn = 'Y') AS stat2_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.create_date = ssl.create_date AND tssl.complete_yn = 'Y' AND tssl.confirm_yn = 'Y') AS stat3_tag_count ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.order_type = :orderType ");

		params.put("orderType", orderType);

		if(companySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssl.create_date, ci.name, ci.company_seq ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, name, company_seq, stat1_box_count, stat2_box_count, stat3_box_count, (stat1_box_count + stat2_box_count + stat3_box_count) AS total_box_count, ");
		query.append("stat1_style_count, stat2_style_count, stat3_style_count, (stat1_style_count + stat2_style_count + stat3_style_count) AS total_style_count, ");
		query.append("stat1_tag_count, stat2_tag_count, stat3_tag_count, (stat1_tag_count + stat2_tag_count + stat3_tag_count) AS total_tag_count ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new ReturnGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountReturnGroupList(String startDate, String endDate, Long companySeq, String orderType) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ssl.create_date, ssl.work_line, bi.start_company_seq ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE ssl.create_date BETWEEN :startDate AND :endDate ");

		if(companySeq != 0){
			query.append("AND bi.start_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("AND ssl.order_type = :orderType ");

		params.put("orderType", orderType);

		query.append("GROUP BY ssl.create_date, ssl.work_line, bi.start_company_seq ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<StorageScheduleLog> findReturnAll(String createDate, Long startCompanySeq, String confirmYn, String completeYn, String orderType, String search, String option, Pageable pageable) throws Exception {

		Page<StorageScheduleLog> page = null;

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.createDateEqual(createDate));

		specifications = specifications.and(StorageScheduleLogSpecification.disuseYnEqual("N"))
									   .and(StorageScheduleLogSpecification.orderTypeEqual(orderType));

		if(startCompanySeq != 0) {
			specifications = specifications.and(StorageScheduleLogSpecification.startCompanySeqEqual(startCompanySeq));
		}

		if(!confirmYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.confirmYnEqual(confirmYn));
		}

		if(!completeYn.equals("all")){
			specifications = specifications.and(StorageScheduleLogSpecification.completeYnEqual(completeYn));
		}

		page = storageScheduleLogRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageScheduleModel> findReturnGroupDetailList(List<ReturnGroupModel> groupList) throws Exception {

		List<StorageScheduleModel> returnGroupDetailList = new ArrayList<StorageScheduleModel>();

		for(ReturnGroupModel group : groupList){

			returnGroupDetailList.addAll(findReleaseGroupList(group.getCreateDate(), group.getCompanySeq(), "N", "N", "03"));
		}

		return returnGroupDetailList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(String startDate, String endDate, Long companySeq, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ssdl.style AS data ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");

		if(flag.equals("release")){

			query.append("AND ssl.create_date BETWEEN :startDate AND :endDate ");
			params.put("startDate", startDate);
			params.put("endDate", endDate);

		} else if(flag.equals("storage")){

			query.append("AND ssl.confirm_yn = 'Y' ");
			query.append("AND ssl.batch_yn = 'Y' ");
			query.append("AND ssl.arrival_date = :arrivalDate ");
			params.put("arrivalDate", startDate);
		}

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssdl.style ");
		query.append("ORDER BY ssdl.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(String startDate, String endDate, Long companySeq, String style, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ssdl.color AS data ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");

		if(flag.equals("release")){

			query.append("AND ssl.create_date BETWEEN :startDate AND :endDate ");
			params.put("startDate", startDate);
			params.put("endDate", endDate);

		} else if(flag.equals("storage")){

			query.append("AND ssl.confirm_yn = 'Y' ");
			query.append("AND ssl.batch_yn = 'Y' ");
			query.append("AND ssl.arrival_date = :arrivalDate ");
			params.put("arrivalDate", startDate);

		}

		query.append("AND ssdl.style = :style ");
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssdl.color ");
		query.append("ORDER BY ssdl.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(String startDate, String endDate, Long companySeq, String style, String color, String flag) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT ssdl.size AS data ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");

		if(flag.equals("release")){

			query.append("AND ssl.create_date BETWEEN :startDate AND :endDate ");
			params.put("startDate", startDate);
			params.put("endDate", endDate);

		} else if(flag.equals("storage")){

			query.append("AND ssl.confirm_yn = 'Y' ");
			query.append("AND ssl.batch_yn = 'Y' ");
			query.append("AND ssl.arrival_date = :arrivalDate ");
			params.put("arrivalDate", startDate);

		}

		query.append("AND ssdl.style = :style ");
		params.put("style", style);
		query.append("AND ssdl.color = :color ");
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssdl.size ");
		query.append("ORDER BY ssdl.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Override
	public Set<StorageScheduleDetailLogModel> generateScheduleLog(StorageScheduleLog storageScheduleLog, String flag) throws Exception {

		Set<StorageScheduleDetailLogModel> tempDetailList = new HashSet<StorageScheduleDetailLogModel>();

		Long lineNum = Long.valueOf(10);

		if(flag.equals("ERP")){
			lineNum = Long.valueOf(1);
		}

		for(StorageScheduleDetailLog detailLog : storageScheduleLog.getStorageScheduleDetailLog()){

			boolean pushFlag = true;

			if(flag.equals("ERP")){

				for(StorageScheduleDetailLogModel tempDetailLog : tempDetailList){

					if(tempDetailLog.getStyle().equals(detailLog.getStyle()) &&
					   tempDetailLog.getColor().equals(detailLog.getColor()) &&
					   tempDetailLog.getSize().equals(detailLog.getSize()) &&
					   tempDetailLog.getOrderDegree().equals(detailLog.getOrderDegree()) &&
					   tempDetailLog.getBarcode().equals(detailLog.getBarcode())){

						pushFlag = false;

						tempDetailLog.setAmount(tempDetailLog.getAmount() + detailLog.getAmount());

						for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){

							StorageScheduleSubDetailLogModel addSubDetailLog = modelMapper.map(subDetailLog, StorageScheduleSubDetailLogModel.class);

							boolean check = true;

							for(StorageScheduleSubDetailLogModel tempSubDetailLog : tempDetailLog.getStorageScheduleSubDetailLogModel()) {
								if(tempSubDetailLog.getRfidTag().equals(addSubDetailLog.getRfidTag())){
									check = false;
								}
							}

							if(check) {
								tempDetailLog.getStorageScheduleSubDetailLogModel().add(modelMapper.map(subDetailLog, StorageScheduleSubDetailLogModel.class));
							}
						}
					}
				};

			} else if(flag.equals("WMS")){

				for(StorageScheduleDetailLogModel tempDetailLog : tempDetailList){

					if(tempDetailLog.getStyle().equals(detailLog.getStyle()) &&
					   tempDetailLog.getColor().equals(detailLog.getColor()) &&
					   tempDetailLog.getSize().equals(detailLog.getSize()) &&
					   tempDetailLog.getBarcode().equals(detailLog.getBarcode())){

						pushFlag = false;

						tempDetailLog.setAmount(tempDetailLog.getAmount() + detailLog.getAmount());

						for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){

							StorageScheduleSubDetailLogModel addSubDetailLog = modelMapper.map(subDetailLog, StorageScheduleSubDetailLogModel.class);

							boolean check = true;

							for(StorageScheduleSubDetailLogModel tempSubDetailLog : tempDetailLog.getStorageScheduleSubDetailLogModel()) {
								if(tempSubDetailLog.getRfidTag().equals(addSubDetailLog.getRfidTag())){
									check = false;
								}
							}

							if(check) {
								tempDetailLog.getStorageScheduleSubDetailLogModel().add(modelMapper.map(subDetailLog, StorageScheduleSubDetailLogModel.class));
							}
						}
					}
				};
			}

			if(pushFlag){
				StorageScheduleDetailLogModel detailModel = modelMapper.map(detailLog, StorageScheduleDetailLogModel.class);
				detailModel.setLineNum(lineNum);

				Set<StorageScheduleSubDetailLogModel> subDetailModelList = new HashSet<StorageScheduleSubDetailLogModel>();

				for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){
					subDetailModelList.add(modelMapper.map(subDetailLog, StorageScheduleSubDetailLogModel.class));
				}
				detailModel.setStorageScheduleSubDetailLogModel(subDetailModelList);

				tempDetailList.add(detailModel);

				if(flag.equals("ERP")){
					lineNum ++;
				} else {
					lineNum += 10;
				}
			}
		}

		return tempDetailList;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StoreScheduleModel boxStoreScheduleList(String barcode) throws Exception {

		StoreScheduleModel storeScheduleModel = new StoreScheduleModel();

		List<StorageScheduleLog> storageScheduleList = null;

		//if(barcode.equals("") || barcode.length() != 18){
		if(barcode.equals("") || barcode.length() > 20){
			storeScheduleModel.setResultCode(ApiResultConstans.BAD_PARAMETER);
			storeScheduleModel.setResultMessage(ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return storeScheduleModel;
		}

		storageScheduleList = storageScheduleLogRepository.findByBoxInfoBarcodeAndConfirmYn(barcode, "Y");

		if(storageScheduleList.size() == 0){
			storeScheduleModel.setResultCode(ApiResultConstans.NOT_FIND_BOX);
			storeScheduleModel.setResultMessage(ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return storeScheduleModel;
		}

		List<StoreScheduleParentModel> parentLogList = new ArrayList<StoreScheduleParentModel>();

		for(StorageScheduleLog parentLog : storageScheduleList){

			if(parentLog.getCompleteYn().equals("Y")){
				storeScheduleModel.setResultCode(ApiResultConstans.COMPLETE_BOX);
				storeScheduleModel.setResultMessage(ApiResultConstans.COMPLETE_BOX_MESSAGE);
				return storeScheduleModel;
			}

			if(parentLog.getDisuseYn().equals("Y")){
				storeScheduleModel.setResultCode(ApiResultConstans.DISUSE_BOX);
				storeScheduleModel.setResultMessage(ApiResultConstans.DISUSE_BOX_MESSAGE);
				return storeScheduleModel;
			}

			StoreScheduleParentModel parent = new StoreScheduleParentModel();
			parent.setBarcode(parentLog.getBoxInfo().getBarcode());
			parent.setOpenDbCode(parentLog.getOpenDbCode());
			parent.setType(parentLog.getBoxInfo().getType());
			parent.setStartCompanyName(parentLog.getBoxInfo().getStartCompanyInfo().getName());
			parent.setEndCompanyName(parentLog.getBoxInfo().getEndCompanyInfo().getName());
			parent.setStat(parentLog.getBoxInfo().getStat());
			parent.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd").format(parentLog.getBoxInfo().getArrivalDate()));

			if(parentLog.getBoxInfo().getStat().equals("3")){
				parent.setCompleteDate(new SimpleDateFormat("yyyy-MM-dd").format(parentLog.getBoxInfo().getCompleteDate()));
			}

			List<StoreScheduleChildModel> childLogList = new ArrayList<StoreScheduleChildModel>();

			Set<StorageScheduleDetailLogModel> tempWmsDetailList = generateScheduleLog(parentLog, "WMS");

			for(StorageScheduleDetailLogModel childLog : tempWmsDetailList){

				StoreScheduleChildModel child = new StoreScheduleChildModel();
				child.setLineNum(String.valueOf(childLog.getLineNum()));
				child.setStyle(childLog.getStyle());
				child.setColor(childLog.getColor());
				child.setSize(childLog.getSize());
				child.setOrderDegree(childLog.getOrderDegree());
				child.setCount(String.valueOf(childLog.getAmount()));

				List<StoreScheduleSubChildModel> subChildLogList = new ArrayList<StoreScheduleSubChildModel>();

				for(StorageScheduleSubDetailLogModel subChildLog : childLog.getStorageScheduleSubDetailLogModel()){
					StoreScheduleSubChildModel subChild = new StoreScheduleSubChildModel();
					subChild.setRfidTag(subChildLog.getRfidTag());

					subChildLogList.add(subChild);
				}

				child.setStoreScheduleSubChildList(subChildLogList);

				childLogList.add(child);
			}

			parent.setStoreScheduleChildList(childLogList);

			parentLogList.add(parent);
		}

		storeScheduleModel.setStoreScheduleParentList(parentLogList);
		storeScheduleModel.setResultCode(ApiResultConstans.SUCCESS);
		storeScheduleModel.setResultMessage(ApiResultConstans.SUCCESS_MESSAGE);

		return storeScheduleModel;
	}

	@Transactional
	@Override
	public List<Long> storageScheduleLogConfirmBatch(StorageScheduleLog scheduleLog) throws Exception {

		List<Long> storageSeqList = new ArrayList<Long>();

		if(scheduleLog.getOrderType().equals("OP-R")) {

			storageSeqList = storageScheduleProcess(scheduleLog);

//		} else if(scheduleLog.getOrderType().equals("OP-R2")) {
//
//			storageScheduleExceptionProcess(scheduleLog);

		} else if(scheduleLog.getOrderType().equals("10-R")) {

			storageSeqList = storageScheduleReturn(scheduleLog);
		}

		return storageSeqList;
	}

	@Transactional
	private List<Long> storageScheduleProcess(StorageScheduleLog scheduleLog) throws Exception{

		List<Long> distributionStorageSeqList = new ArrayList<Long>();
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<RfidIb10If> rfidIb10IfList = new ArrayList<RfidIb10If>();
		List<OpenDbScheduleDetail> openDbScheduleDetailList = new ArrayList<OpenDbScheduleDetail>();

		UserInfo userInfo = scheduleLog.getUpdUserInfo();
		BoxInfo boxInfo = scheduleLog.getBoxInfo();

		String arrivalDate = CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate());
		String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");

		Long checkCount = storageScheduleLogRepository.validDistributionRfidTag(scheduleLog.getStorageScheduleLogSeq());

		if(checkCount > 0) {
			return distributionStorageSeqList;
		}

		for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){

			DistributionStorage distributionStorage = distributionStorageRepository.findByProductionStorageProductionStorageSeq(scheduleDetailLog.getStyleSeq());

			if (distributionStorage == null) {

				distributionStorage = new DistributionStorage();

				ProductionStorage productionStorage = productionStorageRepository.findOne(scheduleDetailLog.getStyleSeq());

				distributionStorage.setProductionStorage(productionStorage);
				distributionStorage.setDistributionCompanyInfo(boxInfo.getEndCompanyInfo());
				distributionStorage.setTotalAmount(Long.valueOf(0));
				distributionStorage.setStat1Amount(Long.valueOf(0));
				distributionStorage.setStat2Amount(Long.valueOf(0));
				distributionStorage.setStat3Amount(Long.valueOf(0));
				distributionStorage.setStat4Amount(Long.valueOf(0));
				distributionStorage.setStat5Amount(Long.valueOf(0));
				distributionStorage.setStat6Amount(Long.valueOf(0));
				distributionStorage.setStat7Amount(Long.valueOf(0));
				distributionStorage.setRegDate(new Date());
				distributionStorage.setRegUserInfo(userInfo);
				distributionStorage.setLocation("0000");

				distributionStorage = distributionStorageRepository.save(distributionStorage);
			}

			for(StorageScheduleSubDetailLog scheduleSubDetailLog : scheduleDetailLog.getStorageScheduleSubDetailLog()){

				// 물류 입고예정 정보 저장
				RfidTagMaster tag = StringUtil.setRfidTagMaster(scheduleSubDetailLog.getRfidTag());

				DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();
				distributionTag.CopyData(tag);
				distributionTag.setCustomerCd(boxInfo.getEndCompanyInfo().getCode());
				distributionTag.setBoxInfo(boxInfo);
				distributionTag.setBoxBarcode(boxInfo.getBarcode());
				distributionTag.setStat("1");
				distributionTag.setDistributionStorageSeq(distributionStorage.getDistributionStorageSeq());
				distributionTag.setRegDate(new Date());
				distributionTag.setRegUserInfo(userInfo);

				distributionStorageRfidTagList.add(distributionTag);

				// 물류 입고예정 태그 히스토리 저장
				RfidTagHistory rfidTagHistory = new RfidTagHistory();

				rfidTagHistory.setBarcode(tag.getErpKey() + tag.getRfidSeq());
				rfidTagHistory.setErpKey(tag.getErpKey());
				rfidTagHistory.setRfidTag(tag.getRfidTag());
				rfidTagHistory.setRfidSeq(tag.getRfidSeq());
				rfidTagHistory.setWork("11");
				rfidTagHistory.setRegUserInfo(userInfo);
				rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
				rfidTagHistory.setRegDate(new Date());

				rfidTagHistoryList.add(rfidTagHistory);

			}

			distributionStorageSeqList.add(distributionStorage.getDistributionStorageSeq());
		}

		// 테스트 코드
		/**
		 * to do 서버 반영시 반드시 삭제
		 */
		storageInit(scheduleLog);

		Set<StorageScheduleDetailLogModel> tempErpDetailList = generateScheduleLog(scheduleLog, "ERP");
		Set<StorageScheduleDetailLogModel> tempWmsDetailList = generateScheduleLog(scheduleLog, "WMS");

		// ERP 데이터 기록
		for(StorageScheduleDetailLogModel detailLog : tempErpDetailList){
			rfidIb10IfList.add(erpService.saveStoreSchedule(boxInfo, detailLog.getLineNum(), detailLog, userInfo, nowDate, boxInfo.getInvoiceNum(), scheduleLog.getOrderType()));
		}

		// openDb 헤더 데이터 기록
		OpenDbScheduleHeader openDbHeader = openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, scheduleLog.getOrderType());

		// openDb 디테일 데이터 기록
		for(StorageScheduleDetailLogModel detailLog : tempWmsDetailList){
			openDbScheduleDetailList.add(openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, arrivalDate, scheduleLog.getOrderType()));
		}

		// ERP 입고예정 정보 저장
		rfidIb10IfRepository.save(rfidIb10IfList);

		// openDB 헤더, 디테일 정보 저장
		openDbScheduleHeaderRepository.save(openDbHeader);
		openDbScheduleDetailRepository.save(openDbScheduleDetailList);

		if(rfidIb10IfList.size() > 0) {
			scheduleLog.setErpScheduleYn("Y");
			scheduleLog.setErpScheduleDate(new Date());
		}

		if(openDbScheduleDetailList.size() > 0) {
			scheduleLog.setOpenDbScheduleYn("Y");
			scheduleLog.setOpenDbScheduleDate(new Date());
		}

		storageScheduleLogRepository.save(scheduleLog);

		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);

		return distributionStorageSeqList;
	}

	@Transactional
	private void storageScheduleExceptionProcess(StorageScheduleLog scheduleLog) throws Exception{

		List<StorageScheduleLog> storageScheduleLogList = new ArrayList<StorageScheduleLog>();

		UserInfo userInfo = scheduleLog.getUpdUserInfo();
		BoxInfo boxInfo = scheduleLog.getBoxInfo();

		String arrivalDate = CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate());
		String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");

		// 물류 입고 업데이트
		distributionStorageRfidTagService.inspectionBox(boxInfo, userInfo, "2");

		boxInfo.setStat("3");
		boxInfo.setUpdDate(new Date());
		boxInfo.setUpdUserInfo(userInfo);
		boxInfo.setCompleteDate(new Date());
		boxInfo.setArrivalDate(new Date());

		boxInfoRepository.save(boxInfo);

		Set<StorageScheduleDetailLogModel> tempErpDetailList = generateScheduleLog(scheduleLog, "ERP");
		Set<StorageScheduleDetailLogModel> tempWmsDetailList = generateScheduleLog(scheduleLog, "WMS");

		// ERP 데이터 기록
		for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {
			erpService.saveStoreSchedule(boxInfo, detailLog.getLineNum(), detailLog, userInfo, nowDate, boxInfo.getInvoiceNum(), scheduleLog.getOrderType());
		}

		// openDb 헤더 데이터 기록
		OpenDbScheduleHeader openDbHeader = openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, scheduleLog.getOrderType());
		openDbService.saveOpenDbScheduleHeader(openDbHeader);

		// openDb 디테일 데이터 기록
		for (StorageScheduleDetailLogModel detailLog : tempWmsDetailList) {
			OpenDbScheduleDetail openDbDetail = openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, arrivalDate, scheduleLog.getOrderType());
			openDbService.saveOpenDbScheduleDetail(openDbDetail);
		}

		storageScheduleLogList.add(scheduleLog);

		// ERP 물류 입고 실적 저장
		erpService.saveStorageListComplete(storageScheduleLogList);
	}

	@Transactional
	private List<Long> storageScheduleReturn(StorageScheduleLog scheduleLog) throws Exception {

		List<Long> distributionStorageSeqList = new ArrayList<Long>();
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		//List<OpenDbScheduleDetail> openDbScheduleDetailList = new ArrayList<OpenDbScheduleDetail>();

		UserInfo userInfo = scheduleLog.getUpdUserInfo();
		BoxInfo boxInfo = scheduleLog.getBoxInfo();

		//String arrivalDate = boxInfo.getArrivalDate()==null ? "" : new SimpleDateFormat("yyyyMMdd").format(boxInfo.getArrivalDate());
		//String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");

		for(StorageScheduleDetailLog scheduleDetailLog : scheduleLog.getStorageScheduleDetailLog()){

			for(StorageScheduleSubDetailLog scheduleSubDetailLog : scheduleDetailLog.getStorageScheduleSubDetailLog()){

				StoreStorageRfidTag storeRfidTag = storeStorageRfidTagRepository.findByRfidTag(scheduleSubDetailLog.getRfidTag());

				// 물류에 똑같은 정보가 있는지 확인
				DistributionStorageRfidTag distributionStorageRfidTag = distributionStorageRfidTagRepository.findByRfidTag(storeRfidTag.getRfidTag());

				if(distributionStorageRfidTag != null){

					distributionStorageRfidTag.setStat("1");
					distributionStorageRfidTag.setUpdDate(new Date());
					distributionStorageRfidTag.setUpdUserInfo(userInfo);

					distributionStorageRfidTagList.add(distributionStorageRfidTag);

					// 물류 반품 예정 태그 히스토리 저장
					RfidTagHistory rfidTagHistory = new RfidTagHistory();

					rfidTagHistory.setBarcode(storeRfidTag.getErpKey() + storeRfidTag.getRfidSeq());
					rfidTagHistory.setErpKey(storeRfidTag.getErpKey());
					rfidTagHistory.setRfidTag(storeRfidTag.getRfidTag());
					rfidTagHistory.setRfidSeq(storeRfidTag.getRfidSeq());
					rfidTagHistory.setWork("16");
					rfidTagHistory.setRegUserInfo(userInfo);
					rfidTagHistory.setCompanyInfo(userInfo.getCompanyInfo());
					rfidTagHistory.setRegDate(new Date());

					rfidTagHistoryList.add(rfidTagHistory);

					distributionStorageSeqList.add(distributionStorageRfidTag.getDistributionStorageSeq());
				}
			}
		}

		boxInfo = boxInfoRepository.save(boxInfo);

		/*
		// 테스트 코드
		/**
		 * to do 서버 반영시 반드시 삭제

		storageInit(scheduleLog);

		Set<StorageScheduleDetailLogModel> tempWmsDetailList = generateScheduleLog(scheduleLog, "WMS");

		// openDb 헤더 데이터 기록
		OpenDbScheduleHeader openDbHeader = openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, scheduleLog.getOrderType());

		// openDb 디테일 데이터 기록
		for(StorageScheduleDetailLogModel detailLog : tempWmsDetailList){
			openDbScheduleDetailList.add(openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, arrivalDate, scheduleLog.getOrderType()));
		}

		// openDB 헤더, 디테일 정보 저장
		openDbScheduleHeaderRepository.save(openDbHeader);
		openDbScheduleDetailRepository.save(openDbScheduleDetailList);

		if(openDbScheduleDetailList.size() > 0) {
			scheduleLog.setOpenDbScheduleYn("Y");
			scheduleLog.setOpenDbScheduleDate(new Date());
		}

		storageScheduleLogRepository.save(scheduleLog);
		*/
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);

		return distributionStorageSeqList;
	}

	@Transactional
	@Override
	public List<Long> storageScheduleLogCompleteBatch(StorageScheduleLog scheduleLog) throws Exception {

		List<RfidIb01If> rfidIb01IfList = new ArrayList<RfidIb01If>();	//ERP 일반 입고 실적 반영
		List<RfidLf01If> rfidLf01IfList = new ArrayList<RfidLf01If>();	//ERP 반품 입고 실적 반영
		List<Long> distributionStorageSeqList = new ArrayList<Long>();

		UserInfo userInfo = scheduleLog.getUpdUserInfo();

		// 물류 입고 업데이트
		List<Long> tempSeqList = distributionStorageRfidTagService.inspectionBoxBatch(scheduleLog.getBoxInfo(), userInfo);

		for (Long tempSeq : tempSeqList) {
			distributionStorageSeqList.add(tempSeq);
		}

		//일반, 반품에 따라 ERP 입고 실적 저장 테이블이 나뉘어짐
		if(scheduleLog.getOrderType().equals("10-R")) {
			//ERP 반품 물류 입고 실적 저장
			rfidLf01IfList = erpService.saveStorageListReturnComplete(scheduleLog);
			rfidLf01IfRepository.save(rfidLf01IfList);
			if(rfidLf01IfList.size()>0) {
				scheduleLog.setErpCompleteYn("Y");
				scheduleLog.setErpCompleteDate(new Date());
			}
		}else {
		// ERP 물류 입고 실적 저장
			rfidIb01IfList = erpService.saveStorageListComplete(scheduleLog);
			rfidIb01IfRepository.save(rfidIb01IfList);
			if(rfidIb01IfList.size() > 0) {
				scheduleLog.setErpCompleteYn("Y");
				scheduleLog.setErpCompleteDate(new Date());
			}
		}

		// 물류 예정 정보 업데이트
		storageScheduleLogRepository.save(scheduleLog);

		return distributionStorageSeqList;
	}

	//물류 반품입고 예정 정보 리스트 조회
	@Override
	public List<StorageScheduleGroupModel> findStorageReturnScheduleGroupList(String startDate, String endDate,
			Long companySeq, String search, String option, Pageable pageable) throws Exception {
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "arrival_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ssl.arrival_date, bi.start_company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_box_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_style_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_style_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'N' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat1_tag_count, ");
		query.append("(SELECT ISNULL(SUM(tssdl.amount), 0) ");
		query.append("FROM storage_schedule_log tssl ");
		query.append("INNER JOIN storage_schedule_detail_log tssdl ");
		query.append("ON tssl.storage_schedule_log_seq = tssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info tbi ");
		query.append("ON tssl.box_seq = tbi.box_seq ");
		query.append("WHERE tssl.arrival_date = ssl.arrival_date ");
		query.append("AND tssl.order_type = '10-R' ");
		query.append("AND tssl.complete_yn = 'Y' ");
		query.append("AND tssl.confirm_yn = 'Y' ");
		query.append("AND tssl.disuse_yn = 'N' ");
		query.append("AND tbi.start_company_seq = bi.start_company_seq ");
		query.append(") AS stat2_tag_count ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.order_type = '10-R' ");
		query.append("AND ssl.confirm_yn = 'Y' ");
		query.append("AND ssl.batch_yn = 'Y' ");
		query.append("AND ssl.disuse_yn = 'N' ");

		if(companySeq != 0) {
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssl.confirm_yn, ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT start_company_seq, name, arrival_date, stat1_box_count, stat2_box_count, (stat1_box_count + stat2_box_count) AS total_box_count, ");
		query.append("stat1_style_count, stat2_style_count, (stat1_style_count + stat2_style_count) AS total_style_count, ");
		query.append("stat1_tag_count, stat2_tag_count, (stat1_tag_count + stat2_tag_count) AS total_tag_count, ");
		query.append("CAST(CAST(stat2_tag_count AS FLOAT)/CAST((stat1_tag_count + stat2_tag_count) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StorageScheduleGroupModelMapper());
	}

	//반품 입고 예정정보 수량 조회
	@Override
	public Long CountStorageReturnScheduleGroupList(String startDate, String endDate, Long companySeq, String search,
			String option) throws Exception {
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ON ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.order_type = '10-R' ");
		query.append("AND ssl.confirm_yn = 'Y' ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.batch_yn = 'Y' ");

		if(companySeq != 0) {
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ssl.arrival_date, bi.start_company_seq, ci.name ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	//물류 반품 입고 수량 조회
	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageReturnScheduleLogBoxCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' THEN ssl.confirm_yn END) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type = '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}


	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageReturnScheduleLogStyleCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssl.confirm_yn END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssl.confirm_yn END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' THEN ssl.confirm_yn END) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type = '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel distributionStockStorageReturnScheduleLogTagCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		StringBuffer query = new StringBuffer();
		query.append("SELECT ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' THEN ssdl.amount END), 0) AS stat1_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' THEN ssdl.amount END), 0) AS stat2_amount, ");
		query.append("ISNULL(SUM(CASE WHEN ssl.confirm_yn = 'Y' THEN ssdl.amount END), 0) AS total_amount ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ");
		query.append("ON ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN box_info bi ");
		query.append("ON ssl.box_seq = bi.box_seq ");
		query.append("WHERE bi.type IS NOT NULL ");
		query.append("AND ssl.disuse_yn = 'N' ");
		query.append("AND ssl.order_type = '10-R' ");
		query.append("AND ssl.arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		if(companySeq != 0) {
			query.append("AND bi.start_company_seq = :companySeq");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new DistributionStockStorageScheduleRowMapper());
	}

	@Override
	public Page<StorageScheduleLog> findAll(String createDate, String workLine, Long startCompanySeq, String confirmYn,
			String completeYn, String style, String color, String size, String search, String option, Pageable pageable)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StorageScheduleDetailLog> findReturnScheduleByBarcode(String barcode) throws Exception {
		List<StorageScheduleDetailLog> returnList = new ArrayList<StorageScheduleDetailLog>();
		returnList = storageScheduleDetailLogRepository.findByBarcode(barcode);
		return returnList;
	}
}
