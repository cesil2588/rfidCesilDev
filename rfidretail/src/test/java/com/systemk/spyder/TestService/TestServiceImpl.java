package com.systemk.spyder.TestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxHeader;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxStyle;
import com.systemk.spyder.Dto.Response.TempForceReleaseBoxTag;
import com.systemk.spyder.Entity.External.Key.*;
import com.systemk.spyder.Entity.External.*;
import com.systemk.spyder.Entity.Lepsilon.Key.TboxPickKey;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptDetailKey;
import com.systemk.spyder.Entity.Lepsilon.Key.TreceiptKey;
import com.systemk.spyder.Entity.Lepsilon.Key.TshipmentKey;
import com.systemk.spyder.Entity.Lepsilon.TboxPick;
import com.systemk.spyder.Entity.Lepsilon.Treceipt;
import com.systemk.spyder.Entity.Lepsilon.TreceiptDetail;
import com.systemk.spyder.Entity.Lepsilon.Tshipment;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Repository.External.*;
import com.systemk.spyder.Repository.Lepsilon.TboxPickRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptDetailRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptRepository;
import com.systemk.spyder.Repository.Lepsilon.TshipmentRepository;
import com.systemk.spyder.Repository.Main.*;
import com.systemk.spyder.Repository.Main.Specification.BartagOrderSpecification;
import com.systemk.spyder.Repository.Main.Specification.ErrorLogSpecification;
import com.systemk.spyder.Repository.Main.Specification.StorageScheduleLogSpecification;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleDetailRepository;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleHeaderRepository;
import com.systemk.spyder.Service.*;
import com.systemk.spyder.Service.CustomBean.*;
import com.systemk.spyder.TestMapper.DuplicationBartagModelMapper;
import com.systemk.spyder.TestModel.DuplicationBartagModel;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Transactional
@Service
public class TestServiceImpl implements TestService{

	private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

	private final Path rootLocation = Paths.get("d://upload");

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private ProductionStorageLogRepository productionStorageLogRepository;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private BartagMasterLogRepository bartagLogRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private RfidTagReissueRequestRepository rfidTagReissueRequestRepository;

	@Autowired
	private RfidTagReissueRequestDetailRepository rfidTagReissueRequestDetailRepository;

	@Autowired
	private TempProductionStorageHeaderRepository tempProductionStorageHeaderRepository;

	@Autowired
	private TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository;

	@Autowired
	private TempProductionReleaseBoxRepository tempProductionReleaseBoxRepository;

	@Autowired
	private TempProductionReleaseStyleRepository tempProductionReleaseStyleRepository;

	@Autowired
	private TempProductionReleaseTagRepository tempProductionReleaseTagRepository;

	@Autowired
	private TreceiptRepository treceiptRepository;

	@Autowired
	private TreceiptDetailRepository treceiptDetailRepository;

	@Autowired
	private TboxPickRepository tboxPickRepository;

	@Autowired
	private TshipmentRepository tshipmentRepository;

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private TempProductionService tempProductionService;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private RfidTagIfRepository rfidTagIfRepository;

	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private BartagOrderRepository bartagOrderRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private BoxWorkGroupRepository boxWorkGroupRepository;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Autowired
	private RedisService redisService;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Autowired
	private RfidTagReissueRequestService rfidTagReissueRequestService;

	@Autowired
	private BartagMasterLogRepository bartagMasterLogRepository;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private ProductionStorageLogService productionStorageLogService;

	@Autowired
	private BartagLogService bartagLogService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private OpenDbService openDbService;

	@Autowired
	private RfidIb10IfRepository rfidIb10IfRepository;

	@Autowired
	private OpenDbScheduleHeaderRepository openDbScheduleHeaderRepository;

	@Autowired
	private OpenDbScheduleDetailRepository openDbScheduleDetailRepository;

	@Autowired
	private BoxService boxService;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private RfidIb01IfRepository rfidIb01IfRepository;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@Autowired
	private RfidAa06IfRepository rfidAa06IfRepository;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ErrorLogRepository errorLogRepository;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private TempForceReleaseBoxRepository tempForceReleaseBoxRepository;

	@Autowired
	private RfidLe10IfRepository rfidLe10IfRepository;

	@Autowired
	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	@Autowired
	private RfidMd14IfRepository rfidMd14IfRepository;

	@Autowired
	private TempRfidRepository tempRfidRepository;

	@Autowired
	private RfidTempRepository rfidTempRepository;

	@Override
	public void initTestDate(Long seq) {

		BartagMaster bartag = bartagMasterRepository.findOne(seq);

		List<ProductionStorageLog> productionStorageLogList = productionStorageLogRepository.findByProductionStorage(bartag.getProductionStorage());

		productionStorageLogRepository.delete(productionStorageLogList);

		if(bartag.getProductionStorage() != null){
			productionStorageRfidTagRepository.deleteByProductionStorageSeq(bartag.getProductionStorage().getProductionStorageSeq());
			ProductionStorage tempProductionStorage = bartag.getProductionStorage();

			bartag.setProductionStorage(null);
			bartag.setStat("1");
			bartag.setGenerateSeqYn("N");
			bartag.setBartagStartDate(null);
			bartag.setBartagEndDate(null);

			bartagMasterRepository.save(bartag);

			productionStorageRepository.delete(tempProductionStorage);
		} else {
			bartag.setProductionStorage(null);
			bartag.setStat("1");
			bartag.setGenerateSeqYn("N");
			bartag.setBartagStartDate(null);
			bartag.setBartagEndDate(null);

			bartagMasterRepository.save(bartag);
		}


		bartagLogRepository.deleteByBartagMasterBartagSeq(seq);

		List<RfidTagMaster> rfidTagList = rfidTagMasterRepository.findByBartagSeq(seq);

		for(RfidTagMaster tag : rfidTagList){
			rfidTagHistoryRepository.deleteByRfidTag(tag.getRfidTag());
			rfidTagStatusRepository.deleteByRfidTag(tag.getRfidTag());
		}

		rfidTagMasterRepository.deleteByBartagSeq(seq);
	}

	@Override
	public void transactionalTest(String data) throws Exception{
		/*
		MainTestTable main = new MainTestTable();
		ExternalTestTable external = new ExternalTestTable();
		OpenDbTestTable openDb = new OpenDbTestTable();

		openDb.setData("openDb " + data);
		openDbTestTableRepository.save(openDb);

//		external.setData("external " + data);
		externalTestTableRepository.save(external);

		main.setData("main " + data);
		mainTestTableRepository.save(main);
		*/

	}

	@Override
	public void reissueTagList() throws Exception {
		List<RfidTagReissueRequestDetail> reissueList = rfidTagReissueRequestDetailRepository.findByStat("1");

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(RfidTagReissueRequestDetail reissue : reissueList){
			RfidTagMaster rfidTag = rfidTagMasterRepository.findByRfidTag(reissue.getRfidTag());
			BartagMaster bartag = bartagMasterRepository.findByBartagSeq(rfidTag.getBartagSeq());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("createDate", bartag.getCreateDate());
			map.put("lineSeq", bartag.getLineSeq());
			map.put("seq", bartag.getSeq());
			map.put("companyCode", bartag.getProductionCompanyInfo().getCode());
			map.put("productRfidSeason", bartag.getProductRfidYySeason());
			map.put("orderDegree", bartag.getOrderDegree());
			map.put("style", bartag.getStyle());
			map.put("color", bartag.getColor());
			map.put("size", bartag.getSize());
			map.put("rfidSeq", rfidTag.getRfidSeq());
			map.put("rfidTag", reissue.getRfidTag());

			list.add(map);
		}

		for(Map<String, Object> map : list){
			System.out.println(map.toString());
		}
	}

	@Override
	public void reissueTagRequestUpdate() throws Exception {
		// TODO Auto-generated method stub
		List<RfidTagReissueRequest> requestList = rfidTagReissueRequestRepository.findAll();

		for(RfidTagReissueRequest request : requestList){

			Long workLine = rfidTagReissueRequestService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(request.getRegDate()), "1", request.getCompanyInfo().getCompanySeq());

			request.setWorkLine(++workLine);

			request.setPublishLocation("1");
			request.setType("1");
			request.setConfirmYn("Y");
			request.setConfirmDate(request.getRegDate());
			request.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(request.getRegDate()));

			if(request.getUpdUserInfo() != null){
				request.setCompleteYn("Y");
				request.setCompleteDate(request.getUpdDate());
			} else {
				request.setCompleteYn("N");
			}
		}

		rfidTagReissueRequestRepository.save(requestList);
	}

	@Override
	public void reissueTagUpdate() throws Exception {
		List<RfidTagReissueRequestDetail> reissueList = rfidTagReissueRequestDetailRepository.findByColorIsNull();

		for(RfidTagReissueRequestDetail reissue : reissueList){

			RfidTagMaster rfidTag = rfidTagMasterRepository.findByRfidTag(reissue.getRfidTag());
			BartagMaster bartag = bartagMasterRepository.findByBartagSeq(rfidTag.getBartagSeq());

			if(rfidTag != null && bartag != null){
				reissue.setRfidSeq(rfidTag.getRfidSeq());
				reissue.setCreateDate(rfidTag.getCreateDate());
				reissue.setLineSeq(rfidTag.getLineSeq());
				reissue.setSeq(rfidTag.getSeq());
				reissue.setStyle(bartag.getStyle());
				reissue.setColor(bartag.getColor());
				reissue.setSize(bartag.getSize());
				reissue.setOrderDegree(bartag.getOrderDegree());
				reissue.setProductRfidSeason(bartag.getProductRfidYySeason());
				reissue.setProductionStorage(bartag.getProductionStorage());

			}
		}

		rfidTagReissueRequestDetailRepository.save(reissueList);
	}


	@Override
	public void tempReleaseInit() throws Exception {

		TempProductionReleaseHeader header = new TempProductionReleaseHeader();
		header.setStat("1");
		header.setType("2");
		header.setRegDate(new Date());
		header.setUserSeq(Long.valueOf(1));
		header.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd"));

		Set<TempProductionReleaseBox> boxSet = new HashSet<TempProductionReleaseBox>();

		List<BoxInfo> boxList = boxInfoRepository.findTop1000ByCreateDateAndStartCompanyInfoCompanySeq("20180912", Long.valueOf(370379));

		int tagPage = 0;

	    int pageSize = 60;

	    int boxCount = 0;

		for(BoxInfo boxInfo : boxList){

			if(boxCount > 1000){
				break;
			}

			TempProductionReleaseBox releaseBox = new TempProductionReleaseBox();
//			releaseBox.setBarcode(boxInfo.getBarcode());
			Set<TempProductionReleaseStyle> styleSet = new HashSet<TempProductionReleaseStyle>();

			List<ProductionStorageRfidTag> tempRfidTagList = productionStorageRfidTagRepository.findByCustomerCdAndStat("001", "2", new PageRequest(tagPage, pageSize));

			TempProductionReleaseStyle releaseStyle = new TempProductionReleaseStyle();
			releaseStyle.setProductionStorageSeq(tempRfidTagList.get(0).getProductionStorageSeq());

			Set<TempProductionReleaseTag> tagSet = new HashSet<TempProductionReleaseTag>();

			for(ProductionStorageRfidTag rfidTag : tempRfidTagList){
				TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();
				releaseTag.setRfidTag(rfidTag.getRfidTag());
				tagSet.add(releaseTag);
			}

			tagPage ++;

			releaseStyle.setTagList(tagSet);
			styleSet.add(releaseStyle);
			releaseBox.setStyleList(styleSet);
			boxSet.add(releaseBox);

			boxCount ++;
		}

		header.setBoxList(boxSet);

		header = tempProductionReleaseHeaderRepository.saveAndFlush(header);

		for(TempProductionReleaseBox releaseBox : header.getBoxList()){

//			tempProductionService.updateReleaseStyleHeaderSeq(header.getTempHeaderSeq(), releaseBox.getTempBoxSeq());

			for(TempProductionReleaseStyle releaseStyle : releaseBox.getStyleList()){

				tempProductionService.updateReleaseTagHeaderSeq(header.getTempHeaderSeq(), releaseStyle.getTempStyleSeq());
			}
		}
	}

	@Override
	public void tempReleaseInitTest() throws Exception {

		TempProductionReleaseHeader header = new TempProductionReleaseHeader();
		header.setStat("1");
		header.setType("2");
		header.setRegDate(new Date());
		header.setUserSeq(Long.valueOf(1));
		header.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd"));

		header = tempProductionReleaseHeaderRepository.save(header);

		List<BoxInfo> boxList = boxInfoRepository.findTop1000ByCreateDateAndStartCompanyInfoCompanySeq("20180912", Long.valueOf(370379));

		int tagPage = 0;

	    int pageSize = 120;

	    int boxCount = 0;

	    List<TempProductionReleaseTag> tagList = new ArrayList<TempProductionReleaseTag>();

		for(BoxInfo boxInfo : boxList){

			if(boxCount > 1000){
				break;
			}

			TempProductionReleaseBox releaseBox = new TempProductionReleaseBox();
//			releaseBox.setBarcode(boxInfo.getBarcode());
			releaseBox.setTempHeaderSeq(header.getTempHeaderSeq());
			releaseBox = tempProductionReleaseBoxRepository.save(releaseBox);

			List<ProductionStorageRfidTag> tempRfidTagList = productionStorageRfidTagRepository.findByCustomerCdAndStat("001", "2", new PageRequest(tagPage, pageSize));

			TempProductionReleaseStyle releaseStyle = new TempProductionReleaseStyle();
			releaseStyle.setProductionStorageSeq(tempRfidTagList.get(0).getProductionStorageSeq());
			releaseStyle.setTempHeaderSeq(header.getTempHeaderSeq());

			releaseStyle = tempProductionReleaseStyleRepository.save(releaseStyle);

			for(ProductionStorageRfidTag rfidTag : tempRfidTagList){
				TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();
				releaseTag.setRfidTag(rfidTag.getRfidTag());
				releaseTag.setTempHeaderSeq(header.getTempHeaderSeq());
				releaseTag.setTempStyleSeq(releaseStyle.getTempStyleSeq());
				tagList.add(releaseTag);
			}

			tagPage ++;

			boxCount ++;
		}

		tempProductionReleaseTagRepository.save(tagList);

	}

	@Override
	public void tempStorageInit() throws Exception {
		List<ProductionStorage> productionStorageList = productionStorageRepository.findByCompanyInfoCompanySeq(Long.valueOf(370379));

		TempProductionStorageHeader header = new TempProductionStorageHeader();
		header.setStat("1");
		header.setType("2");
		header.setRegDate(new Date());
		header.setUserSeq(Long.valueOf(1));
		Set<TempProductionStorageStyle> styleSet = new HashSet<TempProductionStorageStyle>();

		for(ProductionStorage productionStorage : productionStorageList){

			if(productionStorage.getNonCheckAmount() > 0){

				TempProductionStorageStyle style = new TempProductionStorageStyle();
				style.setProductionStorageSeq(productionStorage.getProductionStorageSeq());

				Set<TempProductionStorageTag> tagSet = new HashSet<TempProductionStorageTag>();

				List<ProductionStorageRfidTag> rfidTagList = productionStorageRfidTagRepository.findByProductionStorageSeqAndStat(productionStorage.getProductionStorageSeq(), "1");

				for(ProductionStorageRfidTag rfidTag : rfidTagList){
					TempProductionStorageTag tag = new TempProductionStorageTag();
					tag.setRfidTag(rfidTag.getRfidTag());

					tagSet.add(tag);
				}
				style.setTagList(tagSet);

				styleSet.add(style);

			}
		}
		header.setStyleList(styleSet);

		header = tempProductionStorageHeaderRepository.saveAndFlush(header);

		for(TempProductionStorageStyle style : header.getStyleList()){

			tempProductionService.updateStorageHeaderSeq(header.getTempHeaderSeq(), style.getTempStyleSeq());
		}

//		tempProductionStorageHeaderRepository.save(header);
	}

	@Override
	public Map<String, Object> parseRfidTag(String rfidTag) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		String erpKey = rfidTag.substring(0, 10);
		String productYy = rfidTag.substring(10, 12);
		String productSeason = rfidTag.substring(12, 13);
		String orderDegree = rfidTag.substring(13, 15);
		String productionCompanyCode = rfidTag.substring(15, 18);
		String publishLocation = rfidTag.substring(18, 19);
		String publishDate = rfidTag.substring(19, 25);
		String publishDegree = rfidTag.substring(25, 27);
		String rfidSeq = rfidTag.substring(27, 32);

		obj.put("erpKey", erpKey);
		obj.put("productYy", productYy);
		obj.put("productSeason", productSeason);
		obj.put("orderDegree", orderDegree);
		obj.put("productionCompanyCode", productionCompanyCode);
		obj.put("publishLocation", publishLocation);
		obj.put("publishDate", publishDate);
		obj.put("publishDegree", publishDegree);
		obj.put("rfidSeq", rfidSeq);

		return obj;
	}

	@Override
	public void storageInit(StorageScheduleLog storageScheduleLog) throws Exception {

		TreceiptKey key = new TreceiptKey();
		key.setReceiptno(storageScheduleLog.getBoxInfo().getBarcode());
		key.setwCode("LW02");

		Treceipt treceipt = new Treceipt();
		treceipt.setKey(key);

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

	@Override
	public void releaseInit() throws Exception {
		// TODO Auto-generated method stub
		// 1752319, 1752320

		List<String> barcodeList = new ArrayList<String>();

		barcodeList.add("011808130001245100");
		barcodeList.add("011808130002245100");
		/*
		barcodeList.add("011808130003245100");
		barcodeList.add("011808130004245100");
		barcodeList.add("011808130005245100");
		barcodeList.add("011808130006245100");
		barcodeList.add("011808130007245100");
		barcodeList.add("011808130008245100");
		barcodeList.add("011808130009245100");
		barcodeList.add("011808130010245100");
		barcodeList.add("011808130011245100");
		barcodeList.add("011808130012245100");
		*/

		ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(Long.valueOf(1752319));

		TshipmentKey key = new TshipmentKey();
		key.setShipmentNo("10000000");
		key.setwCode("LW02");

		Tshipment tshipment = new Tshipment();
		tshipment.setKey(key);
		tshipment.setStatus("999");
		tshipment.setReferenceNo(erpStoreSchedule.getCompleteDate() + erpStoreSchedule.getEndCompanyInfo().getCustomerCode() + erpStoreSchedule.getEndCompanyInfo().getCornerCode() + erpStoreSchedule.getCompleteSeq());

		tshipmentRepository.save(tshipment);

		int calc = (int) (erpStoreSchedule.getOrderAmount() / barcodeList.size());

		int lineNum = 10;

		for(int i=0; i<barcodeList.size(); i++){

			TboxPickKey tboxKey = new TboxPickKey();
			tboxKey.setShipmentNo("10000000");
			tboxKey.setLineNo(Integer.toString(lineNum));

			TboxPick tboxPick = new TboxPick();
			tboxPick.setKey(tboxKey);
			tboxPick.setRfidFlag("N");
			tboxPick.setStockNm(erpStoreSchedule.getStyle() + erpStoreSchedule.getColor() + erpStoreSchedule.getSize());
			tboxPick.setPickQty(new BigDecimal(calc));
			tboxPick.setOrderQty(new BigDecimal(barcodeList.get(i)));
			tboxPick.setwCode("LW02");

			lineNum += 10;

			tboxPickRepository.save(tboxPick);
		}
	}

	@Override
	public void deleteStoreRelease(Long seq) throws Exception {
		releaseScheduleLogRepository.delete(seq);
	}

	@Override
	public void erpTestTagUpload() throws Exception {

		ArrayList<String> tagList = new ArrayList<String>();

		tagList.add("18002327671840020211808090100001");
		tagList.add("18002327671840020211808090100002");
		tagList.add("18055434021840020211808090100001");
		tagList.add("18055434021840020211808090100002");
		tagList.add("18073023301840020211808090100001");
		tagList.add("18073023301840020211808090100002");
		tagList.add("18102304771840020211808090100001");
		tagList.add("18102304771840020211808090100002");
		tagList.add("18144444771840020211808090100001");
		tagList.add("18144444771840020211808090100002");
		tagList.add("18204362341840020211808090100001");
		tagList.add("18204362341840020211808090100002");
		tagList.add("18271563661840020211808090100001");
		tagList.add("18271563661840020211808090100002");
		tagList.add("18304205601840020211808090100001");
		tagList.add("18304205601840020211808090100002");
		tagList.add("18360560411840020211808090100001");
		tagList.add("18360560411840020211808090100002");
		tagList.add("18465116431840020211808090100001");
		tagList.add("18465116431840020211808090100002");
		tagList.add("18476523161840020211808090100001");
		tagList.add("18476523161840020211808090100002");
		tagList.add("18521755641840020211808090100001");
		tagList.add("18521755641840020211808090100002");
		tagList.add("18537731131840020211808090100001");
		tagList.add("18537731131840020211808090100002");
		tagList.add("18654463501840020211808090100001");
		tagList.add("18654463501840020211808090100002");
		tagList.add("18672542071840020211808090100001");
		tagList.add("18672542071840020211808090100002");
		tagList.add("18701551531840020211808090100001");
		tagList.add("18701551531840020211808090100002");
		tagList.add("18741706351840020211808090100001");
		tagList.add("18741706351840020211808090100002");

		ArrayList<RfidTagIf> rfidTagIfList = new ArrayList<RfidTagIf>();

		for(String tempTag : tagList){

			RfidTagMaster tag = StringUtil.setRfidTagMaster(tempTag);

			BartagMaster bartag = bartagMasterRepository.findTopByErpKeyAndOrderDegreeOrderByAdditionOrderDegreeDesc(tag.getErpKey(), "0" + tag.getOrderDegree());
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

		rfidTagIfRepository.save(rfidTagIfList);
	}

	@Override
	public void erpTestTagClean() throws Exception {

		List<String> erpTagDupList = erpTagDuplicationList();

		for(String barcode : erpTagDupList){
			List<RfidTagIf> rfidTagIfList = rfidTagIfRepository.findByTagRfbcOrderByTagPrdtDesc(barcode);

			int index = 0;

			for(RfidTagIf tag : rfidTagIfList){
				if(index != 0){
					tag.setTagUsyn("N");
				}

				index ++;
			}

			rfidTagIfRepository.save(rfidTagIfList);
		}
	}

	@Override
	public List<String> erpTagDuplicationList() throws Exception {

		template.setDataSource(externalDataSourceConfig.externalDataSource());

		String query = "SELECT tag_rfbc " +
						 "FROM rfid_tag_if " +
					 "GROUP BY tag_rfbc, tag_usyn, tag_seqn " +
				       "HAVING COUNT(tag_rfbc) > 1 ";

		return template.query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	@Override
	public void bartagOrderInit() throws Exception {

		Specifications<BartagOrder> specifications = null;

		specifications = Specifications.where(BartagOrderSpecification.regDateBetween(CalendarUtil.convertStartDate("20190311"), CalendarUtil.convertEndDate("20190311")));

		List<BartagOrder> tempBartagOrderList = bartagOrderRepository.findAll(specifications);
		List<BartagOrder> bartagOrderList = new ArrayList<BartagOrder>();

		for(BartagOrder bartagOrder : tempBartagOrderList){

			if(bartagOrder.getProductionCompanyInfo() == null) {
				continue;
			}

			List<BartagMaster> bartagList = bartagMasterRepository.findByErpKeyAndOrderDegree(bartagOrder.getErpKey(), bartagOrder.getOrderDegree());

			if(bartagList.size() == 0) {
				continue;
			}

			Long completeAmount = Long.valueOf(0);
			Date completeDate = new Date();
			Long additionOrderAmount = Long.valueOf(0);
			Date updDate = new Date();
			int additionOrderDegreeCount = 0;
			String stat = "";

			List<UserInfo> userList = userInfoRepository.findByCompanyInfoCompanySeq(bartagOrder.getProductionCompanyInfo().getCompanySeq());

			UserInfo userInfo = new UserInfo();

			if(userList.size() == 0){
				userInfo = userInfoRepository.findOne(Long.valueOf(1));
			} else {
				userInfo = userList.get(0);
			}

			for(BartagMaster bartag : bartagList){
				if(bartag.getAdditionOrderDegree().equals("0")){
					completeAmount = bartag.getAmount();
					completeDate = bartag.getRegDate();
					stat = "3";
				} else {
					additionOrderAmount += bartag.getAmount();
					updDate = bartag.getRegDate();
					additionOrderDegreeCount ++;
					stat = "5";
				}
			}

			if(bartagList.size() > 0){

				List<BartagOrder> checkBartagOrderList = bartagOrderRepository.findByErpKeyAndOrderDegreeGreaterThan(bartagOrder.getErpKey(), bartagOrder.getOrderDegree());

				if(checkBartagOrderList.size() > 0){
					stat = "6";
				}

				bartagOrder.setAdditionAmount(additionOrderAmount);
				bartagOrder.setAdditionOrderDegree(Integer.toString(additionOrderDegreeCount));
				bartagOrder.setCompleteAmount(completeAmount);
				bartagOrder.setCompleteDate(completeDate);
				bartagOrder.setCompleteYn("Y");
				bartagOrder.setUpdDate(updDate);
				bartagOrder.setUpdUserInfo(userInfo);
				bartagOrder.setStat(stat);

				bartagOrderList.add(bartagOrder);
			}
		}

		bartagOrderRepository.save(bartagOrderList);
	}

	@Override
	public void boxWorkGroupCreateDateInit() throws Exception {

		List<BoxWorkGroup> tempBoxWorkGroupList = new ArrayList<BoxWorkGroup>();

		List<BoxWorkGroup> boxWorkGroupList = boxWorkGroupRepository.findAll();

		for(BoxWorkGroup boxWorkGroup : boxWorkGroupList){

			String createDate = "";

			for(BoxInfo boxInfo : boxWorkGroup.getBoxInfo()){
				createDate = boxInfo.getCreateDate();
				break;
			}

			boxWorkGroup.setCreateDate(createDate);
			boxWorkGroup.setType("01");

			tempBoxWorkGroupList.add(boxWorkGroup);
		}

		boxWorkGroupRepository.save(tempBoxWorkGroupList);
	}

	@Override
	public void checkCustomerCd() throws Exception {
		// TODO Auto-generated method stub
		List<String> customerCdList = new ArrayList<String>();
		List<RfidAc18If> rfidAc18IfList = rfidAc18IfRepository.findAll();

		for(RfidAc18If rfidAc18If : rfidAc18IfList){
			CompanyInfo companyInfo = companyInfoRepository.findByCustomerCode(rfidAc18If.getAc18Prod());

			if(companyInfo == null){
				if(!customerCdList.contains(rfidAc18If.getAc18Prod())){
					customerCdList.add(rfidAc18If.getAc18Prod());
				}
			}
		}

		customerCdList.stream().forEach(System.out::println);
	}

	@Override
	public void updateBartagCustomerCd() throws Exception {

		List<BartagMaster> bartagList = bartagMasterRepository.findByProductionCompanyInfoIsNull();

		for(BartagMaster bartag : bartagList){

			RfidAc18IfKey key = new RfidAc18IfKey();

			key.setAc18Crdt(bartag.getCreateDate());
			key.setAc18Crsq(new BigDecimal(bartag.getSeq()));
			key.setAc18Crno(new BigDecimal(bartag.getLineSeq()));

			RfidAc18If rfidAc18If = rfidAc18IfRepository.findOne(key);

			CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(rfidAc18If.getAc18Prod());
	    	bartag.setProductionCompanyInfo(productionCompanyInfo);
		}

		bartagMasterRepository.save(bartagList);
	}

	@Override
	public void checkBartagNullCustomerCd() throws Exception {
		List<BartagMaster> bartagList = bartagMasterRepository.findByProductionCompanyInfoIsNull();

		bartagList.stream().forEach(System.out::println);
	}

	@Override
	public void productRedisInit() throws Exception {

		List<ProductMaster> productMasterList = productMasterRepository.findAll();

		for(ProductMaster productMaster : productMasterList){
			redisService.save("productList", productMaster.getErpKey(), StringUtil.convertJsonString(productMaster));
		}
	}

	@Override
	public void rfidTagCustomerCdInit() throws Exception {

		List<DistributionStorage> distributionStorageList = distributionStorageRepository.findAll();

		for(DistributionStorage distributionStorage : distributionStorageList){
			updateCustomerCd(distributionStorage.getDistributionCompanyInfo().getCode(), distributionStorage.getDistributionStorageSeq(), "distribution");
		}

		List<StoreStorage> storeStorageList = storeStorageRepository.findAll();

		for(StoreStorage storeStorage : storeStorageList){
			updateCustomerCd(storeStorage.getCompanyInfo().getCode(), storeStorage.getStoreStorageSeq(), "store");
		}
	}

	public void updateCustomerCd(String companyCode, Long seq, String type) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "";

		if(type.equals("distribution")){
			query = "UPDATE distribution_storage_rfid_tag SET customer_cd = ? WHERE distribution_storage_seq = ?";
		} else if(type.equals("store")){
			query = "UPDATE store_storage_rfid_tag SET customer_cd = ? WHERE store_storage_seq = ?";
		}

		template.update(query, companyCode, seq);
	}

	@Override
	public void distributionStorageScheduleDelete() throws Exception {


	}

	@Override
	public List<DuplicationBartagModel> duplicateBartag(String createDate) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.bartag_seq, bm.create_date, bm.style, bm.color, bm.size, bm.order_degree, bm.addition_order_degree, bm.erp_key, bm.start_rfid_seq, bm.end_rfid_seq, bm.amount ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN (SELECT style, color, size, order_degree, addition_order_degree, erp_key, start_rfid_seq ");
		query.append("FROM bartag_master ");
		query.append("GROUP BY style, color, size, order_degree, addition_order_degree, erp_key, start_rfid_seq ");
		query.append("HAVING COUNT(*) > 1) t ");
		query.append("ON bm.style = t.style ");
		query.append("AND bm.color = t.color ");
		query.append("AND bm.size = t.size ");
		query.append("AND bm.order_degree = t.order_degree ");
		query.append("AND bm.addition_order_degree = t.addition_order_degree ");
		query.append("AND bm.erp_key = t.erp_key ");
		query.append("AND bm.start_rfid_seq = t.start_rfid_seq ");

		if(!createDate.equals("")){
			query.append("WHERE bm.create_date = :createDate ");
			params.put("createDate", createDate);
		}

		query.append("ORDER BY bm.style, bm.color, bm.size, bm.amount DESC ");

		return nameTemplate.query(query.toString(), params, new DuplicationBartagModelMapper());
	}

	@Override
	public void duplicationBartagInit() throws Exception {

		List<DuplicationBartagModel> duplicationBartagList = duplicateBartag("20190110");

		DuplicationBartagModel tempModel = null;
		for(DuplicationBartagModel model : duplicationBartagList){

			if(tempModel == null){
				tempModel = model;
			} else {
				if(tempModel.getStyle().equals(model.getStyle()) &&
				   tempModel.getColor().equals(model.getColor()) &&
				   tempModel.getSize().equals(model.getSize()) &&
				   tempModel.getErpKey().equals(model.getErpKey()) &&
				   tempModel.getOrderDegree().equals(model.getOrderDegree()) &&
				   tempModel.getAdditionOrderDegree().equals(model.getAdditionOrderDegree()) &&
				   tempModel.getStartRfidSeq().equals(model.getStartRfidSeq())){

					BartagMaster bartag = bartagMasterRepository.findOne(model.getBartagSeq());

					int start = Integer.parseInt(tempModel.getEndRfidSeq()) + 1;
					int end = (start + bartag.getAmount().intValue()) - 1;
					int additionOrderDegree = Integer.parseInt(tempModel.getAdditionOrderDegree()) + 1;

					bartag.setStartRfidSeq(StringUtil.convertCipher("5", start));
					bartag.setEndRfidSeq(StringUtil.convertCipher("5", end));
					bartag.setGenerateSeqYn("N");
					bartag.setStat("1");
					bartag.setAdditionOrderDegree(Integer.toString(additionOrderDegree));

					rfidTagMasterRepository.deleteByBartagSeq(model.getBartagSeq());

					bartagMasterRepository.save(bartag);

//					System.out.println(bartag.toString());
//					System.out.println("start: " + StringUtil.convertCipher("5", start));
//					System.out.println("end: " + StringUtil.convertCipher("5", end));
//					System.out.println("additionOrderDegree: " + additionOrderDegree);

					tempModel = null;
				}
			}
		}

	}

	@Override
	public void jpqlCountTest(Long seq) throws Exception {

		CountModel count = bartagMasterLogRepository.findCountAll(seq);

		System.out.println(count.toString());
	}

	@Override
	public void storageScheduleBatchUpdate() throws Exception {
		// TODO Auto-generated method stub
		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findAll();

		for(StorageScheduleLog scheduleLog : scheduleList) {
			if(scheduleLog.getConfirmYn().equals("N")) {
				scheduleLog.setBatchYn("N");
				scheduleLog.setDisuseYn("N");
			}
		}

		storageScheduleLogRepository.save(scheduleList);
	}

	@Override
	public void storageLogInit(String createDate, Long companySeq) throws Exception {

		List<Long> bartagSeqList = new ArrayList<Long>();
		List<Long> productionSeqList = new ArrayList<Long>();

		// TODO Auto-generated method stub
		List<RfidTagReissueRequest> requestList = rfidTagReissueRequestRepository.findByCreateDateAndCompanyInfoCompanySeq(createDate, companySeq);

		UserInfo userInfo = requestList.get(0).getUpdUserInfo();

		for(RfidTagReissueRequest request : requestList){

			for(RfidTagReissueRequestDetail detail : request.getRfidTagReissueRequestDetail()) {
				productionSeqList.add(detail.getProductionStorage().getProductionStorageSeq());
				bartagSeqList.add(detail.getProductionStorage().getBartagMaster().getBartagSeq());
			}
		}

		productionStorageLogService.initTest(productionSeqList, userInfo);
		bartagLogService.initTest(bartagSeqList, userInfo);
	}

	@Override
	public void releaseErp() throws Exception {
		// TODO Auto-generated method stub
		// ERP 출고실적 전달

		/*
		UserInfo userInfo = userInfoRepository.findOne(Long.valueOf(1));

		List<TempDistributionReleaseHeader> headerList = tempDistributionReleaseHeaderRepository.findAll();

		for(TempDistributionReleaseHeader tempHeader : headerList) {

			List<ErpStoreSchedule> erpStoreScheduleList = erpStoreScheduleService.findByReleaseScheduleList(tempHeader.getReferenceNo());

			for (ErpStoreSchedule storeSchedule : erpStoreScheduleList) {

				int releaseCount = 0;

				for (TempDistributionReleaseBox tempBox : tempHeader.getBoxList()) {
					for (TempDistributionReleaseStyle style : tempBox.getStyleList()) {
						if (storeSchedule.getStyle().equals(style.getStyle())
								&& storeSchedule.getColor().equals(style.getColor())
								&& storeSchedule.getSize().equals(style.getSize())) {
							releaseCount += style.getTagList().size();
						}
					}
				}

				storeSchedule.setReleaseAmount(Long.valueOf(releaseCount));
				storeSchedule.setReleaseDate(CalendarUtil.convertFormat("yyyyMMdd"));
				storeSchedule.setUpdDate(new Date());
				storeSchedule.setReleaseUserId(userInfo.getUserId());

				// ERP 데이터 전송
				erpService.saveReleaseComplete(storeSchedule, storeSchedule.getReleaseAmount(), storeSchedule.getSortingAmount());
			}
		}
		*/
	}

	@Override
	public void productionReleaseUpdate(String boxBarcode, String targetBoxBarcode, Long userSeq) throws Exception {

		// TODO Auto-generated method stub
		List<DistributionStorageRfidTag> distributionStorageRfidTagList = new ArrayList<DistributionStorageRfidTag>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		List<RfidIb10If> rfidIb10IfList = new ArrayList<RfidIb10If>();
		List<OpenDbScheduleDetail> openDbScheduleDetailList = new ArrayList<OpenDbScheduleDetail>();

		List<Long> distributionStorageSeqList = new ArrayList<Long>();
		List<Long> productionStorageSeqList = new ArrayList<Long>();

		Date startDate = new Date();

		UserInfo userInfo = userInfoRepository.findOne(userSeq);
		BoxInfo boxInfo = boxInfoRepository.findByBarcode(boxBarcode);

		boxInfoList.add(boxInfo);

		StorageScheduleLog targetScheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(targetBoxBarcode);

		List<ProductionStorageRfidTag> productionStorageRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(boxInfo.getBoxSeq());

		// 확정 완료된 상품출고를 수정할 경우
		if (targetScheduleLog.getConfirmYn().equals("Y") && targetScheduleLog.getBatchYn().equals("Y")) {

			for (ProductionStorageRfidTag productionRfidTag : productionStorageRfidTagList) {


				DistributionStorage distributionStorage = distributionStorageRepository.findByProductionStorageProductionStorageSeq(productionRfidTag.getProductionStorageSeq());

				if (distributionStorage == null) {

					distributionStorage = new DistributionStorage();

					ProductionStorage productionStorage = productionStorageRepository.findOne(productionRfidTag.getProductionStorageSeq());

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

				// 물류에 똑같은 정보가 있는지 확인
				DistributionStorageRfidTag distributionStorageRfidTag = distributionStorageRfidTagRepository.findByRfidTag(productionRfidTag.getRfidTag());

				if (distributionStorageRfidTag == null) {

					// 물류 입고예정 정보 저장
					RfidTagMaster tag = StringUtil.setRfidTagMaster(productionRfidTag.getRfidTag());

					DistributionStorageRfidTag distributionTag = new DistributionStorageRfidTag();
					distributionTag.CopyData(tag);
					distributionTag.setBoxInfo(boxInfo);
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

					distributionStorageSeqList.add(distributionStorage.getDistributionStorageSeq());
				}

				productionStorageSeqList.add(productionRfidTag.getProductionStorageSeq());
			}
		}

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", boxInfo.getStartCompanyInfo().getCompanySeq());
		workLine++;

		// 물류 입고 예정 정보 저장
		List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogService.save(boxInfoList, userInfo,workLine, "OP-R", null);

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList);
		productionStorageRfidTagRepository.flush();
		distributionStorageRfidTagRepository.save(distributionStorageRfidTagList);
		distributionStorageRfidTagRepository.flush();

		// 신규 생산, 물류 수량 변경
		productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");

		// 확정 완료된 상품출고를 수정할 경우
		if (targetScheduleLog.getConfirmYn().equals("Y") && targetScheduleLog.getBatchYn().equals("Y")) {

			distributionStorageLogService.save(distributionStorageSeqList, userInfo, startDate, "1", "2");

			// ERP 업데이트 처리(사용안함 바코드)
			erpService.disuseErpStorageSchedule(targetScheduleLog);

			// WMS 업데이트 처리(사용안함 바코드)
			openDbService.disuseOpenDbScheduleHeader(targetScheduleLog);

			// 신규 바코드 ERP, WMS 등록
			for (StorageScheduleLog scheduleLog : storageScheduleLogList) {

				scheduleLog.setConfirmYn("Y");
				scheduleLog.setConfirmDate(new Date());
				scheduleLog.setBatchYn("Y");
				scheduleLog.setBatchDate(new Date());
				scheduleLog.setUpdDate(new Date());
				scheduleLog.setUpdUserInfo(userInfo);

				String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");
				String arrivalDate = CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getArrivalDate());

				Set<StorageScheduleDetailLogModel> tempErpDetailList = storageScheduleLogService.generateScheduleLog(scheduleLog, "ERP");
				Set<StorageScheduleDetailLogModel> tempWmsDetailList = storageScheduleLogService.generateScheduleLog(scheduleLog, "WMS");

				// ERP 데이터 기록
				for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {
					rfidIb10IfList.add(erpService.saveStoreSchedule(boxInfo, detailLog.getLineNum(), detailLog, userInfo, nowDate, boxInfo.getInvoiceNum(), scheduleLog.getOrderType()));
				}

				// openDb 헤더 데이터 기록
				OpenDbScheduleHeader openDbHeader = openDbService.setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, scheduleLog.getOrderType());

				// openDb 디테일 데이터 기록
				for (StorageScheduleDetailLogModel detailLog : tempWmsDetailList) {
					openDbScheduleDetailList.add(openDbService.setOpenDbScheduleDetail(boxInfo, detailLog, nowDate, arrivalDate, scheduleLog.getOrderType()));
				}

				// ERP 입고예정 정보 저장
				rfidIb10IfRepository.save(rfidIb10IfList);

				// openDB 헤더, 디테일 정보 저장
				openDbScheduleHeaderRepository.save(openDbHeader);
				openDbScheduleDetailRepository.save(openDbScheduleDetailList);

				if (rfidIb10IfList.size() > 0) {
					scheduleLog.setErpScheduleYn("Y");
					scheduleLog.setErpScheduleDate(new Date());
				}

				if (openDbScheduleDetailList.size() > 0) {
					scheduleLog.setOpenDbScheduleYn("Y");
					scheduleLog.setOpenDbScheduleDate(new Date());
				}
			}
		}

		storageScheduleLogRepository.save(storageScheduleLogList);
	}

	@Override
	public void reissueTagRollback(String rfidTag) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void distributionStorageInspection() throws Exception{
		// TODO Auto-generated method stub
		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findByCompleteYn("Y");

		for(StorageScheduleLog schedule : scheduleList) {

			distributionStorageRfidTagService.inspectionBox(schedule.getBoxInfo(), schedule.getUpdUserInfo(), "3");
		}
	}

	@Override
	public void storageScheduleRfidTagInit(List<Long> scheduleSeqList) throws Exception{
		// TODO Auto-generated method stub

		for(Long seq : scheduleSeqList) {
			StorageScheduleLog scheduleLog = storageScheduleLogRepository.findOne(seq);

			for(StorageScheduleDetailLog detailLog : scheduleLog.getStorageScheduleDetailLog()) {

				StyleModel style = new StyleModel();

				style.setStyle(detailLog.getStyle());
				style.setColor(detailLog.getColor());
				style.setSize(detailLog.getSize());
				style.setOrderDegree(detailLog.getOrderDegree());
				style.setStyleSeq(detailLog.getStyleSeq());

				detailLog.getStorageScheduleSubDetailLog().clear();

				List<RfidModel> rfidList = boxService.boxStyleRfidProductionList(scheduleLog.getBoxInfo().getBoxSeq(), style);

				for(RfidModel rfid : rfidList) {
					StorageScheduleSubDetailLog storageScheduleSubDetailLog = new StorageScheduleSubDetailLog();
					storageScheduleSubDetailLog.setRfidTag(rfid.getRfidTag());

					detailLog.getStorageScheduleSubDetailLog().add(storageScheduleSubDetailLog);
				}
			}

			storageScheduleLogRepository.save(scheduleLog);
		}
	}

	@Override
	public void rfidAndErpStorageCompleteMatch() throws Exception {

		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findByCompleteYn("Y");

		int count = 0;
		for(StorageScheduleLog schedule : scheduleList) {
			List<RfidIb01If> rfidIb01IfList = rfidIb01IfRepository.findRfidIb01If(schedule.getBoxInfo().getBarcode());

			if(rfidIb01IfList == null) {
				count ++;
				log.info("수량: " + count);
				log.info("RFID 바코드: " + schedule.getBoxInfo().getBarcode() + " ERP 입고실적에 없음");
			}

		}
	}

	@Override
	public void bartagOrderGet(String createDate) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void allRequestMappingUrl() throws Exception {
		// TODO Auto-generated method stub

		Map<RequestMappingInfo, HandlerMethod> mappings = handlerMapping.getHandlerMethods();
		Set<RequestMappingInfo> keys = mappings.keySet();
		Iterator<RequestMappingInfo> iterator = keys.iterator();

		List<String> urlList = new ArrayList<String>();
		while (iterator.hasNext()) {
			RequestMappingInfo key = iterator.next();
			HandlerMethod value = mappings.get(key);
			urlList.add(key.getPatternsCondition().toString());

//			log.info(key.getPatternsCondition()  + "[" + key.getMethodsCondition()  + "] : " + value);
//			log.info(key.getPatternsCondition().toString());
		}

		for(String url : urlList) {
			log.info(url.replaceAll("[\\[\\]]", ""));
		}
		log.info("###############################################");
	}

	@Override
	public void bartagOrderCreateNoInit() throws Exception {
		// TODO Auto-generated method stub
		/*
		List<BartagOrder> bartagOrderList = bartagOrderRepository.findAll();

		for(BartagOrder bartagOrder : bartagOrderList) {
			RfidAa06If rfidAa06If = rfidAa06IfRepository.findData(bartagOrder.getStyle(), bartagOrder.getColor(), bartagOrder.getSize(), bartagOrder.getOrderDegree());

			if(rfidAa06If == null) {
				log.info("ERP 데이터 확인 안됨 : " + bartagOrder.getStyle() + " | " + bartagOrder.getColor() + " | " + bartagOrder.getSize() + " | " + bartagOrder.getOrderDegree());
				continue;
			}
//			log.info("ERP 데이터 확인 완료 : " + bartagOrder.getStyle() + " | " + bartagOrder.getColor() + " | " + bartagOrder.getSize() + " | " + bartagOrder.getOrderDegree());
			bartagOrder.setCreateSeq(rfidAa06If.getKey().getAa06Crsq().longValue());
		}

		bartagOrderRepository.save(bartagOrderList);

		log.info("ERP bartagOrder createSeq 체크 완료");
		*/

		StringBuilder sb = new StringBuilder();

		List<BartagOrder> bartagOrderInsertList = new ArrayList<BartagOrder>();
		List<RfidAa06If> rfidAa06IfList = rfidAa06IfRepository.findAll();

		for(RfidAa06If rfidAa06If : rfidAa06IfList) {

			if(rfidAa06If.getAa06Tryn().equals("Y")) {
				BartagOrder checkBartagOrder = bartagOrderRepository.findByCreateDateAndCreateSeqAndCreateNo(
						rfidAa06If.getKey().getAa06Crdt(),
						rfidAa06If.getKey().getAa06Crsq().longValue(),
						rfidAa06If.getKey().getAa06Crno().longValue());

				if (checkBartagOrder == null) {
//					sb.append("바택오더 데이터 확인 안됨 : " + rfidAa06If.getAa06Styl() + " | " + rfidAa06If.getAa06It06() + " | "
//							+ rfidAa06If.getAa06It07() + " | " + rfidAa06If.getAa06Jjch());
//					sb.append(System.getProperty("line.separator"));

					BartagOrder bartagOrder = new BartagOrder();
			    	bartagOrder.setCreateDate(rfidAa06If.getKey().getAa06Crdt());
			    	bartagOrder.setCreateSeq(rfidAa06If.getKey().getAa06Crsq().longValue());
			    	bartagOrder.setCreateNo(rfidAa06If.getKey().getAa06Crno().longValue());

			    	ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSizeAndStatNot(rfidAa06If.getAa06Styl(), rfidAa06If.getAa06It06(), rfidAa06If.getAa06It07(), "D");

			    	bartagOrder.setProductYy(productMaster.getProductYy());
			    	bartagOrder.setProductSeason(productMaster.getProductSeason());
			    	bartagOrder.setErpKey(productMaster.getErpKey());

			    	bartagOrder.setStyle(rfidAa06If.getAa06Styl());
			    	bartagOrder.setColor(rfidAa06If.getAa06It06());
			    	bartagOrder.setSize(rfidAa06If.getAa06It07());
			    	bartagOrder.setAnotherStyle(rfidAa06If.getAa06Stcd());
			    	bartagOrder.setOrderAmount(rfidAa06If.getAa06Cfqt().longValue());
			    	bartagOrder.setOrderDegree(rfidAa06If.getAa06Jjch());
			    	bartagOrder.setOrderDate(rfidAa06If.getAa06Jmdt());
			    	bartagOrder.setOrderTime(rfidAa06If.getAa06Time());
			    	bartagOrder.setOrderSeq(rfidAa06If.getAa06Jmno().longValue());
			    	bartagOrder.setCompleteAmount(Long.valueOf(0));

			    	CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(rfidAa06If.getAa06Prod());
			    	bartagOrder.setProductionCompanyInfo(productionCompanyInfo);

			    	bartagOrder.setDetailProductionCompanyName(rfidAa06If.getAa06Jgrs());

			    	bartagOrder.setErpStat(rfidAa06If.getAa06Stat());
			    	bartagOrder.setRegDate(new Date());

			    	bartagOrder.setStat("1");
			    	bartagOrder.setAdditionAmount(Long.valueOf(0));
			    	bartagOrder.setAdditionOrderDegree("0");
			    	bartagOrder.setTempAdditionAmount(Long.valueOf(0));
			    	bartagOrder.setCompleteYn("N");
			    	bartagOrder.setNonCheckAdditionAmount(Long.valueOf(0));
			    	bartagOrder.setNonCheckCompleteAmount(Long.valueOf(0));

			    	bartagOrderInsertList.add(bartagOrder);

					continue;
				}
			}
//			log.info("바택오더 데이터 확인 완료 : " + rfidAa06If.getAa06Styl() + " | " + rfidAa06If.getAa06It06() + " | " + rfidAa06If.getAa06It07() + " | " + rfidAa06If.getAa06Jjch());
		}

		bartagOrderRepository.save(bartagOrderInsertList);

		log.info(sb.toString());

		log.info("바택오더 체크 완료");
	}

	@Override
	public void storageLogRollback() throws Exception {

		List<Long> productionStorageSeqList = new ArrayList<Long>();

		productionStorageSeqList.add(Long.valueOf(286444611));
		productionStorageSeqList.add(Long.valueOf(286455137));
		productionStorageSeqList.add(Long.valueOf(286442204));
		productionStorageSeqList.add(Long.valueOf(286428065));
		productionStorageSeqList.add(Long.valueOf(286437691));
		productionStorageSeqList.add(Long.valueOf(286449724));
		productionStorageSeqList.add(Long.valueOf(286431075));

		List<Long> distributionStorageSeqList = new ArrayList<Long>();

		distributionStorageSeqList.add(Long.valueOf(288041691));
		distributionStorageSeqList.add(Long.valueOf(288041893));
		distributionStorageSeqList.add(Long.valueOf(288042095));
		distributionStorageSeqList.add(Long.valueOf(288042548));
		distributionStorageSeqList.add(Long.valueOf(288045254));
		distributionStorageSeqList.add(Long.valueOf(288046410));
		distributionStorageSeqList.add(Long.valueOf(288047063));

		UserInfo userInfo = userInfoRepository.findOne(Long.valueOf(1));

		productionStorageLogService.save(productionStorageSeqList, userInfo, new Date(), "3D", "1");
		distributionStorageLogService.save(distributionStorageSeqList, userInfo, new Date(), "2D", "1");
	}

	@Override
	public void storageErpComplete() throws Exception {
		// TODO Auto-generated method stub

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.confirmYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.batchYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.completeYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.completeBatchYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.erpCompleteYnEqual("N"));

		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findAll(specifications);

		for(StorageScheduleLog schedule : scheduleList) {
			if(schedule.getOrderType().equals("OP-R") || schedule.getOrderType().equals("OP-R2")) {

				// ERP 물류 입고 실적 저장
				List<RfidIb01If> rfidIb01IfList = erpService.saveStorageListComplete(schedule);

				rfidIb01IfRepository.save(rfidIb01IfList);

				if(rfidIb01IfList.size() > 0) {
					schedule.setErpCompleteYn("Y");
					schedule.setErpCompleteDate(new Date());
				}
			}
		}

		storageScheduleLogRepository.save(scheduleList);
	}

	@Override
	public void storageCompleteRfidStatInit() throws Exception {
		// TODO Auto-generated method stub
		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.confirmYnEqual("Y"))
				  .and(StorageScheduleLogSpecification.batchYnEqual("Y"))
				  .and(StorageScheduleLogSpecification.completeYnEqual("Y"))
				  .and(StorageScheduleLogSpecification.completeBatchYnEqual("Y"))
				  .and(StorageScheduleLogSpecification.erpCompleteYnEqual("Y"));

		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findAll(specifications);

		for(StorageScheduleLog schedule : scheduleList) {

			UserInfo userInfo = schedule.getUpdUserInfo();
			// 물류 입고 업데이트
			List<Long> tempSeqList = distributionStorageRfidTagService.inspectionBoxBatchTest(schedule.getBoxInfo(), userInfo);

			distributionStorageLogService.save(tempSeqList, schedule.getUpdUserInfo(), new Date(), "2", "1");
		}
	}

	@Override
	public void readLogParse(String fileName, String format) throws Exception {
		// TODO Auto-generated method stub
		Path file = rootLocation.resolve(fileName + "." + format);

		Stream<String> lines = Files.lines(file, StandardCharsets.ISO_8859_1);

		StringBuilder tagSb = new StringBuilder();
		StringBuilder boxSb = new StringBuilder();

        for( String lineStr : (Iterable<String>) lines::iterator ){

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_TAG")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_TAG", "TEST_TEMP_PRODUCTION_RELEASE_TAG");
        		tagSb.append(tempStr);
        		tagSb.append(System.getProperty("line.separator"));

        		continue;
        	}

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_BOX")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_BOX", "TEST_TEMP_PRODUCTION_RELEASE_BOX");
        		boxSb.append(tempStr);
        		boxSb.append(System.getProperty("line.separator"));

        		continue;
        	}
        }

        String tempTagName = fileName + "_tag_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");
        String tempBoxName = fileName + "_box_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");

        generateFile(tempTagName, tagSb, "txt");
        generateFile(tempBoxName, boxSb, "txt");
	}

	private void generateFile(String fileName, StringBuilder sb, String format) throws Exception{

		Path path = rootLocation.resolve(fileName + "." + format);
        Files.createDirectories(path.getParent());

        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        Charset charset = Charset.defaultCharset();
        ByteBuffer byteBuffer = charset.encode(sb.toString());

        fileChannel.write(byteBuffer);
	}

	@Override
	public void readLogSqlParse(String fileName, String format) throws Exception {

		// TODO Auto-generated method stub
		Path file = rootLocation.resolve(fileName + "." + format);

		Stream<String> lines = Files.lines(file, StandardCharsets.ISO_8859_1);

		StringBuilder tagSb = new StringBuilder();
		StringBuilder boxSb = new StringBuilder();

        for( String lineStr : (Iterable<String>) lines::iterator ){

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_HEADER")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_TAG", "TEST_TEMP_PRODUCTION_RELEASE_TAG");
        		tagSb.append(tempStr);
        		tagSb.append(System.getProperty("line.separator"));

        		continue;
        	}

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_BOX")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_TAG", "TEST_TEMP_PRODUCTION_RELEASE_TAG");
        		tagSb.append(tempStr);
        		tagSb.append(System.getProperty("line.separator"));

        		continue;
        	}

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_STYLE")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_BOX", "TEST_TEMP_PRODUCTION_RELEASE_BOX");
        		boxSb.append(tempStr);
        		boxSb.append(System.getProperty("line.separator"));

        		continue;
        	}

        	if(lineStr.contains("TEMP_PRODUCTION_RELEASE_TAG")) {
        		int startIdx = lineStr.indexOf("INSERT");
        		String tempStr = lineStr.substring(startIdx).replaceAll("TEMP_PRODUCTION_RELEASE_TAG", "TEST_TEMP_PRODUCTION_RELEASE_TAG");
        		tagSb.append(tempStr);
        		tagSb.append(System.getProperty("line.separator"));

        		continue;
        	}

        }

        String tempTagName = fileName + "_tag_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");
        String tempBoxName = fileName + "_box_" + CalendarUtil.convertFormat("yyyyMMddHHmmss");

        generateFile(tempTagName, tagSb, "txt");
        generateFile(tempBoxName, boxSb, "txt");
	}

	@Override
	public void compulsionRelease(Long seq) throws Exception {

		List<Long> productionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		Date startDate = new Date();

		TempProductionReleaseHeader header = tempProductionReleaseHeaderRepository.findOne(seq);

		header.setRequestYn("Y");
		header.setRequestDate(new Date());

		UserInfo userInfo = userInfoRepository.findOne(header.getUserSeq());

		Long companySeq = userInfo.getCompanyInfo().getCompanySeq();

		// 박스 상태 업데이트
		for(TempProductionReleaseBox box : header.getBoxList()){

			BoxInfo boxInfo = boxInfoRepository.findByBarcodeAndStat(box.getBarcode(), "1");

			if(boxInfo == null) {
				continue;
			}

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

		rfidTagHistoryRepository.save(rfidTagHistoryList);
		boxInfoRepository.save(boxInfoList);

		Long workLine = storageScheduleLogService.maxWorkLine(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()), "OP-R", companySeq);

		workLine ++;

		// 물류 입고 예정 정보 저장
		storageScheduleLogService.save(boxInfoList, userInfo, workLine, "OP-R", null);

		if(productionStorageSeqList.size() > 0) {
			productionStorageLogService.save(productionStorageSeqList, userInfo, startDate, "3", "2");
		}
	}

	@Override
	public void compulsionReleaseChack(Long userSeq) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> boxObjList = new ArrayList<Map<String, Object>>();

		List<TempProductionReleaseHeader> headerList = tempProductionReleaseHeaderRepository.findByUserSeq(userSeq);

		for(TempProductionReleaseHeader header : headerList) {
			// 박스 상태 업데이트
			for(TempProductionReleaseBox box : header.getBoxList()){

				BoxInfo boxInfo = boxInfoRepository.findByBarcodeAndStat(box.getBarcode(), "1");

				if(boxInfo == null) {
					continue;
				}

				boolean pushFlag = true;

				for(Map<String, Object> tempBoxObj : boxObjList) {
					if(tempBoxObj.get("barcode").toString().equals(box.getBarcode())) {
						pushFlag = false;
					}
				}

				if(pushFlag) {
					Map<String, Object> boxObj = new LinkedHashMap<String, Object>();
					boxObj.put("barcode", boxInfo.getBarcode());

					List<Map<String, Object>> tagObjList = new ArrayList<Map<String, Object>>();

					// 태그 상태 업데이트
					for(TempProductionReleaseStyle style : box.getStyleList()) {

						for(TempProductionReleaseTag rfidTag : style.getTagList()) {

							Map<String, Object> tagObj = new LinkedHashMap<String, Object>();

							ProductionStorageRfidTag checkRfidTag = productionStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag());

							tagObj.put("rfidTag", rfidTag.getRfidTag());
							tagObj.put("stat", checkRfidTag != null ? checkRfidTag.getStat() : "없음");
							tagObjList.add(tagObj);
						}
					}

					boxObj.put("tagList", tagObjList);

					boxObjList.add(boxObj);
				}
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(boxObjList);
		mapper.writerWithDefaultPrettyPrinter().writeValueAsString(boxObjList);
		mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, boxObjList);
	}

	@Override
	public void storageRollBack(String startBarcode, String endBarcode) throws Exception {
		// TODO Auto-generated method stub

		Date startDate = new Date();

		Specifications<StorageScheduleLog> specifications = Specifications.where(StorageScheduleLogSpecification.confirmYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.batchYnEqual("Y"))
																		  .and(StorageScheduleLogSpecification.completeYnEqual("N"))
																		  .and(StorageScheduleLogSpecification.completeBatchYnEqual("N"))
																		  .and(StorageScheduleLogSpecification.erpCompleteYnEqual("N"))
																		  .and(StorageScheduleLogSpecification.barcodeBetween(startBarcode, endBarcode));

		List<StorageScheduleLog> scheduleList = storageScheduleLogRepository.findAll(specifications);

		for(StorageScheduleLog targetScheduleLog : scheduleList) {

			UserInfo userInfo = targetScheduleLog.getUpdUserInfo();

			List<Long> initProductionStorageSeqList = new ArrayList<Long>();
			List<Long> initDistributionStorageSeqList = new ArrayList<Long>();

			// 생산 출고 > 입고로 변경
			List<ProductionStorageRfidTag> productionRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(targetScheduleLog.getBoxInfo().getBoxSeq());

			for (ProductionStorageRfidTag tempRfidTag : productionRfidTagList) {
				initProductionStorageSeqList.add(tempRfidTag.getProductionStorageSeq());
			}

			// 생산 출고된 박스 정보 삭제
			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), targetScheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고 예정정보 삭제
			List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(targetScheduleLog.getBoxInfo().getBoxSeq());

			for (DistributionStorageRfidTag tempRfidTag : distributionRfidTagList) {
				initDistributionStorageSeqList.add(tempRfidTag.getDistributionStorageSeq());
			}

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
		}
	}

	@Override
	public void storageRollBack(String barcode) throws Exception {
		// TODO Auto-generated method stub
		Date startDate = new Date();

		StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

		UserInfo userInfo = scheduleLog.getUpdUserInfo();

		List<Long> initProductionStorageSeqList = new ArrayList<Long>();
		List<Long> initDistributionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();

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
		scheduleLog.setUpdDate(new Date());
		scheduleLog.setUpdUserInfo(userInfo);

		scheduleLog.setReturnYn("Y");
		scheduleLog.setReturnDate(new Date());

		scheduleLog.getBoxInfo().setStat("4");
		scheduleLog.getBoxInfo().setUpdDate(new Date());
		scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);

		// 생산 출고예정 정보 사용안함 처리
		storageScheduleLogRepository.save(scheduleLog);

		// 박스 사용 안함 처리
		boxInfoRepository.save(scheduleLog.getBoxInfo());
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		// 타겟 생산, 물류 수량 변경
		productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
		distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");

		// ERP 입고반송 처리
		rfidIb01IfRepository.save(erpService.saveStorageListRollback(scheduleLog));
	}

	@Override
	public void storageRollBack(List<String> barcodeList, Long userSeq) throws Exception {
		// TODO Auto-generated method stub
		Date startDate = new Date();

		List<Long> initProductionStorageSeqList = new ArrayList<Long>();
		List<Long> initDistributionStorageSeqList = new ArrayList<Long>();
		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<RfidTagHistory>();
		List<StorageScheduleLog> scheduleLogList = new ArrayList<StorageScheduleLog>();
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();

		UserInfo userInfo = userInfoRepository.findOne(userSeq);

		for(String barcode : barcodeList) {

			StorageScheduleLog scheduleLog = storageScheduleLogRepository.findByBoxInfoBarcode(barcode);

			// 생산 출고 > 입고로 변경
			List<ProductionStorageRfidTag> productionRfidTagList = productionStorageRfidTagRepository.findByBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			for (ProductionStorageRfidTag tempRfidTag : productionRfidTagList) {
				initProductionStorageSeqList.add(tempRfidTag.getProductionStorageSeq());
			}

			// 생산 출고된 태그 정보 롤백
			productionStorageRfidTagService.deleteBoxInfo(userInfo.getUserSeq(), scheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고 예정정보 삭제
			List<DistributionStorageRfidTag> distributionRfidTagList = distributionStorageRfidTagRepository.findByBoxInfoBoxSeq(scheduleLog.getBoxInfo().getBoxSeq());

			// 물류 입고된 태그 정보 삭제
			distributionStorageRfidTagService.deleteBoxInfo(scheduleLog.getBoxInfo().getBoxSeq());


			scheduleLog.setDisuseYn("Y");
			scheduleLog.setUpdDate(new Date());
			scheduleLog.setUpdUserInfo(userInfo);

			scheduleLog.setReturnYn("Y");
			scheduleLog.setReturnDate(new Date());

			scheduleLog.getBoxInfo().setStat("4");
			scheduleLog.getBoxInfo().setUpdDate(new Date());
			scheduleLog.getBoxInfo().setUpdUserInfo(userInfo);


			boxInfoList.add(scheduleLog.getBoxInfo());
			scheduleLogList.add(scheduleLog);

			// ERP 입고반송 처리
			if(scheduleLog.getCompleteYn().equals("Y")) {

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

				rfidIb01IfRepository.save(erpService.saveStorageListRollback(scheduleLog));
			}
		}

		// 생산 출고예정 정보 사용안함 처리
		storageScheduleLogRepository.save(scheduleLogList);

		// 박스 사용 안함 처리
		boxInfoRepository.save(boxInfoList);
		rfidTagHistoryRepository.save(rfidTagHistoryList);

		// 타겟 생산, 물류 수량 변경
		productionStorageLogService.save(initProductionStorageSeqList, userInfo, startDate, "8", "2");
		distributionStorageLogService.save(initDistributionStorageSeqList, userInfo, startDate, "8", "2");
	}

	@Override
	public void mailTest(String email) throws Exception {
		// TODO Auto-generated method stub
		mailService.sendMail(email, "테스트", "여전히 테스트", "");
	}

	@Override
	public void tempDataCheck(Long seq) throws Exception {
		// TODO Auto-generated method stub
		TempProductionReleaseHeader header = tempProductionReleaseHeaderRepository.findOne(seq);

		int treeCount = 0;
		for(TempProductionReleaseBox box : header.getBoxList()) {
			for(TempProductionReleaseStyle style : box.getStyleList()) {
				for(TempProductionReleaseTag tag : style.getTagList()) {
					treeCount ++;
				}
			}
		}
		List<TempProductionReleaseTag> tagList = tempProductionReleaseTagRepository.findByTempHeaderSeq(seq);

		int headerCount = 0;
		for(TempProductionReleaseTag rfidTag : tagList) {
			headerCount ++;
		}

		log.info("treeCount: " + treeCount);
		log.info("headerCount: " + headerCount);
	}

	@Override
	public void errorComplete() throws Exception {

		String startDate = CalendarUtil.initStartDate("20190502");
		String endDate = CalendarUtil.initEndDate("20190502");

		Specifications<ErrorLog> spec = Specifications.where(ErrorLogSpecification.createTimeBetween(startDate, endDate))
													  .and(ErrorLogSpecification.requestUrlContain("/api/distributionRfidTag/storeScheduleComplete"));

		List<ErrorLog> errorLogList = errorLogRepository.findAll(spec);

		for(ErrorLog errorLog : errorLogList) {
			log.info(errorLog.getRequestUrl() + errorLog.getRequestQuery());

			String barcode = "";
			Long userSeq = Long.valueOf(0);

			Map<String, String> map = StringUtil.getQueryMap(errorLog.getRequestQuery());
			Set<String> keys = map.keySet();
			for (String key : keys)
			{
				if(key.equals("barcode")) {
					barcode = map.get(key);
				}

				if(key.equals("userSeq")) {
					userSeq = Long.valueOf(map.get(key).toString());
				}
			}

			log.info("barcode=" + barcode);
			log.info("userSeq=" + userSeq);

			storageScheduleLogService.storeScheduleComplete(barcode, userSeq, "3");

//			restTemplate.getForObject("https://spyderrfid.co.kr" + errorLog.getRequestUrl() + errorLog.getRequestQuery(), String.class);
//
////			restTemplate.getForObject("http://localhost:8080" + errorLog.getRequestUrl() + errorLog.getRequestQuery(), String.class);
		}
	}

	@Override
	public void bartagOrderProductionNullCheck() throws Exception {
		// TODO Auto-generated method stub
		List<BartagOrder> bartagOrderList = bartagOrderRepository.findByProductionCompanyInfoCompanySeqIsNull();

		for(BartagOrder bartagOrder : bartagOrderList) {

			RfidAa06IfKey key = new RfidAa06IfKey();

			key.setAa06Crdt(bartagOrder.getCreateDate());
			key.setAa06Crsq(new BigDecimal(bartagOrder.getCreateSeq()));
			key.setAa06Crno(new BigDecimal(bartagOrder.getCreateNo()));

			RfidAa06If rfidAa06If = rfidAa06IfRepository.findOne(key);

			CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(rfidAa06If.getAa06Prod());
	    	bartagOrder.setProductionCompanyInfo(productionCompanyInfo);
		}


		bartagOrderRepository.save(bartagOrderList);
	}

	@Override
	public void getResultProcedure(String testNo, int testPw) {

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO spyder_erp.dbo.rfid_za11_if (za11_crdt, za11_crno, za11_gras, za11_corn, za11_grnm, za11_cnam, za11_grac, za11_grst, za11_teln, za11_adr1, za11_adr2, za11_time, za11_stat, za11_tryn, za11_trdt) ");
        query.append(" VALUES('20191216', 0, 'PT0001', '0', '테스트', '정상', '40', '', '', '', '', '', '', '', '')");


        StringBuilder query2 = new StringBuilder();
		query2.append("INSERT INTO spyder_new_dev.dbo.role_info (role, role_name, use_yn) VALUES ('rfidTest','procedure test', 'N')");

		StoredProcedureQuery proc = em.createStoredProcedureQuery("spyder_erp.dbo.test_realErp");
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, testNo);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN).setParameter(2, testPw);

		try {

			transaction.begin();

			//rfid 서버 테이블 insert
			em.createNativeQuery(query2.toString()).executeUpdate();
			//erp 서버 테이블 insert
			em.createNativeQuery(query.toString()).executeUpdate();
			//erp 프로시저  호출
			proc.execute();

			transaction.commit();
			em.close();

		}catch(Exception e) {

			e.printStackTrace();
			transaction.rollback();
			em.close();

		}

	}

	@Override
	public void deleteTestData() throws Exception{

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM role_info WHERE role = 'rfidTest'");


        StringBuilder query2 = new StringBuilder();
		query2.append("DELETE FROM spyder_erp.dbo.rfid_za11_if WHERE za11_gras = 'PT0001'");

		StringBuilder query3 = new StringBuilder();
		query3.append("DELETE FROM spyder_erp.dbo.rfid_test_if WHERE test_name = 'test5'");

		transaction.begin();
		em.createNativeQuery(query.toString()).executeUpdate();
		em.createNativeQuery(query2.toString()).executeUpdate();
		em.createNativeQuery(query3.toString()).executeUpdate();
		transaction.commit();

		em.close();

	}

	@Override
	public void forceReleaseBoxToJson() throws Exception {
		// TODO Auto-generated method stub
		List<TempForceReleaseBox> tempReleaseBoxList = tempForceReleaseBoxRepository.findAll();
		List<TempForceReleaseBoxHeader> headerList = new ArrayList<TempForceReleaseBoxHeader>();
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();
		List<ReleaseScheduleLog> releaseScheduleList = new ArrayList<ReleaseScheduleLog>();

		for(TempForceReleaseBox box : tempReleaseBoxList) {

			if(box.getStyleFlag().equals("T")) {
				continue;
			}

			boolean headerFlag = true;

			for(TempForceReleaseBoxHeader header : headerList) {

				if(box.getBoxBarcode().equals(header.getBoxBarcode())) {

					boolean styleFlag = true;

					for(TempForceReleaseBoxStyle style : header.getStyleList()) {

						if(box.getStyle().equals(style.getStyle()) &&
						   box.getColor().equals(style.getColor()) &&
						   box.getSize().equals(style.getSize())) {

							if(box.getStyleFlag().equals("F")) {
								continue;
							}

							style.getTagList().add(new TempForceReleaseBoxTag(box));

							styleFlag = false;
						}
					}

					if(styleFlag) {
						header.getStyleList().add(new TempForceReleaseBoxStyle(box));
					}

					headerFlag = false;
				}

			}

			if(headerFlag) {
				headerList.add(new TempForceReleaseBoxHeader(box));
			}
		}

		String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(headerList);

		System.out.println(json);

		for(TempForceReleaseBoxHeader header : headerList) {

			// 관리자 계정 셋팅
    		UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(1L);

    		// TODO 현재는 물류센터 하드코딩, 추가 생성된 물류센터가 있다면 동작으로 변경
    		CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode("100000", "0");
        	CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode("S11076", "0");

    		// erpKey 조회
        	for(TempForceReleaseBoxStyle style : header.getStyleList()) {
        		if(!style.getFlag().equals("F")) {
        			ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSize(style.getStyle(), style.getColor(), style.getSize());

        			if(productMaster != null) {
        				style.setErpKey(productMaster.getErpKey());
        			}
        		}
        	}

        	BoxInfo boxInfo = new BoxInfo(header.getBoxBarcode(), startCompanyInfo, endCompanyInfo, userInfo);
        	boxList.add(boxInfo);

        	releaseScheduleList.add(new ReleaseScheduleLog(header, boxInfo, userInfo));
		}

		boxInfoRepository.save(boxList);
		releaseScheduleLogRepository.save(releaseScheduleList);
	}

	@Override
	public void deleteForceReleaseBox(String createDate) throws Exception {

		List<ReleaseScheduleLog> releaseScheduleList = releaseScheduleLogRepository.findByCreateDate(createDate);
		List<BoxInfo> boxList = new ArrayList<BoxInfo>();

		for(ReleaseScheduleLog scheduleLog : releaseScheduleList) {
			boxList.add(scheduleLog.getBoxInfo());
		}

		releaseScheduleLogRepository.delete(releaseScheduleList);
		boxInfoRepository.delete(boxList);

		releaseScheduleLogRepository.flush();
	}

	@Override
	public void erpStoreStorageComplete() throws Exception {

		ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBarcode("397933222356");

		// ERP Dev Function 소스 생성 후 주석 풀 예정
		List<RfidLe10If> rfidLe10IfList = new ArrayList<RfidLe10If>();
		List<RfidLe10If> rfidLe10IfUpdateList = new ArrayList<RfidLe10If>();
		List<RfidLe10If> rfidLe10IfInsertList = new ArrayList<RfidLe10If>();

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
				rfidLe10IfUpdateList.add(rfidLe10If);

				// 신규 스타일 추가시
			} else {
				RfidLe10If rfidLe10If = new RfidLe10If(releaseScheduleLog, detailLog, releaseScheduleLog.getErpReleaseSeq());

				rfidLe10IfList.add(rfidLe10If);
				rfidLe10IfInsertList.add(rfidLe10If);
			}
		}

		// ERP I/F 테이블 저장
		rfidLe10IfRepository.save(rfidLe10IfList);

		// TODO Exception 발생시 트랜잭션 문제는 어떻게 처리할것인가? 현재는 안됨
		// 아래 Function 호출시 ERP 엔티티매니저에서 이전 작업데 대한 커밋 이벤트를 발생시킨다.
		// 어떻게 처리해햘지는 고민해야 함
		// ERP I/F Function 호출
		try {
			Map<String, Object> obj = erpService.storeStorageFunctionCall(releaseScheduleLog.getErpReleaseDate(),
					releaseScheduleLog.getErpReleaseSeq(),
					releaseScheduleLog.getBoxInfo().getEndCompanyInfo(),
					releaseScheduleLog.getBoxInfo().getBarcode());

			if(!obj.get("resultCode").toString().equals("0")) {
				// 강제 에러 처리 테스트
				throw new Exception();
			}

			log.info("result: " + obj.get("resultCode"));

		} catch (Exception e){

			e.printStackTrace();

			for(RfidLe10If list : rfidLe10IfUpdateList){
				list.init();
			}

			rfidLe10IfRepository.save(rfidLe10IfUpdateList);
			rfidLe10IfRepository.delete(rfidLe10IfInsertList);
		}
	}

	@Override
	public void erpStoreStorageRepository() throws Exception {

		/*
		ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBarcode("397933222356");

		// ERP Dev Function 소스 생성 후 주석 풀 예정
		List<RfidLe10If> rfidLe10IfList = new ArrayList<RfidLe10If>();
		List<RfidLe10If> rfidLe10IfUpdateList = new ArrayList<RfidLe10If>();
		List<RfidLe10If> rfidLe10IfInsertList = new ArrayList<RfidLe10If>();

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
				rfidLe10IfUpdateList.add(rfidLe10If);

				// 신규 스타일 추가시
			} else {
				RfidLe10If rfidLe10If = new RfidLe10If(releaseScheduleLog, detailLog, releaseScheduleLog.getErpReleaseSeq());

				rfidLe10IfList.add(rfidLe10If);
				rfidLe10IfInsertList.add(rfidLe10If);
			}
		}

		// ERP I/F 테이블 저장
		rfidLe10IfRepository.save(rfidLe10IfList);

		try {
			FunctionResult result = rfidLe10IfRepository.storeStorageFunctionCall(releaseScheduleLog.getErpReleaseDate(),
					releaseScheduleLog.getErpReleaseSeq().intValue(),
					releaseScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode(),
					releaseScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode(),
					releaseScheduleLog.getBoxInfo().getBarcode());

			if(!result.getAlEcod().equals("0")) {
				// 강제 에러 처리 테스트
				throw new Exception();
			}

			log.info("result: " + result);

		} catch (Exception e){
			e.printStackTrace();

			for(RfidLe10If list : rfidLe10IfUpdateList){
				list.init();
			}

			rfidLe10IfRepository.save(rfidLe10IfUpdateList);
			rfidLe10IfRepository.delete(rfidLe10IfInsertList);
		}
		 */
	}

	@Override
	public void forceInventory() throws Exception {
		List<InventoryScheduleHeader> headerList = new ArrayList<InventoryScheduleHeader>();
		List<ForceBoxGroup> tempForceList = tempForceReleaseBoxRepository.findByStyleGroupBy();

		UserInfo userInfo = userInfoRepository.findOne(1L);
		CompanyInfo companyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode("S11076", "0");

		for(int i=20200101; i<20200131; i++){

			String createDate = Integer.toString(i);

			InventoryScheduleHeader header = new InventoryScheduleHeader();
			header.setCreateDate(createDate);
			header.setWorkLine(0L);
			header.setCompanyInfo(companyInfo);
			header.setCompanyType("store");
			header.setFlag("N");
			header.setConfirmYn("N");
			header.setCompleteYn("N");
			header.setBatchYn("N");
			header.setDisuseYn("N");
			header.setExplanatory(i + " 재고실사");
			header.setRegUserInfo(userInfo);
			header.setRegDate(new Date());
			header.setStyleList(new HashSet<InventoryScheduleStyle>());
			header.setTotalCompleteAmount(0L);

			Long totalAmount = 0L;

			for(ForceBoxGroup temp : tempForceList){

				if(temp.getStyle().equals("SPEFFNRN231U") && temp.getColor().equals("WHT") && temp.getSize().equals("235")){
					continue;
				}

				InventoryScheduleStyle style = new InventoryScheduleStyle();
				style.setStyle(temp.getStyle());
				style.setColor(temp.getColor());
				style.setSize(temp.getSize());
				style.setCompleteAmount(0L);

				if(createDate.contains("2020010")) {
					style.setAmount(20L);
					style.setDisuseAmount(20L);

					totalAmount += 20L;
				} else if(createDate.contains("2020011")){

					style.setAmount(50L);
					style.setDisuseAmount(50L);

					totalAmount += 50L;
				} else {
					style.setAmount(temp.getCount());
					style.setDisuseAmount(temp.getCount());

					totalAmount += temp.getCount();
				}

				ProductMaster product = productMasterRepository.findByStyleAndColorAndSize(temp.getStyle(), temp.getColor(), temp.getSize());

				if(product == null){
					style.setRfidYn("N");
					style.setErpKey("-");
				} else {
					style.setRfidYn("Y");
					style.setErpKey(product.getErpKey());
				}

				header.getStyleList().add(style);
			}

			header.setTotalAmount(totalAmount);
			header.setTotalDisuseAmount(totalAmount);

			headerList.add(header);
		}

		inventoryScheduleHeaderRepository.save(headerList);
	}

	@Override
	public void deleteInventory() throws Exception {
		inventoryScheduleHeaderRepository.deleteAll();
	}

	@Override
	public void forceErpInventory() throws Exception {

		List<RfidMd14If> rfidMd14IfList = new ArrayList<>();

		// 초기화
		rfidMd14IfRepository.deleteAll();

		List<ForceBoxGroup> tempForceList = tempForceReleaseBoxRepository.findByStyleGroupBy();

		CompanyInfo companyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode("S11076", "0");

		for(int i=20200101; i<20200131; i++){

			String createDate = Integer.toString(i);

			for(ForceBoxGroup temp : tempForceList){

				RfidMd14IfKey key = new RfidMd14IfKey();
				key.setMd14Mjcd(companyInfo.getCustomerCode());
				key.setMd14Corn(companyInfo.getCornerCode());
				key.setMd14Bsdt(createDate);
				key.setMd14Engb("B");
				key.setMd14Styl(temp.getStyle());
				key.setMd14Stcd(temp.getColor() + temp.getSize());

				RfidMd14If rfidMd14If = new RfidMd14If();
				rfidMd14If.setKey(key);
				if(createDate.contains("2020010")) {
					rfidMd14If.setMd14Mjlq(new BigDecimal(20));

				} else if(createDate.contains("2020011")){
					rfidMd14If.setMd14Mjlq(new BigDecimal(50));
				} else {
					rfidMd14If.setMd14Mjlq(new BigDecimal(temp.getCount()));
				}

//				rfidMd14If.setMd14Endt(new Date());
				rfidMd14If.setMd14Tryn("N");

				rfidMd14IfList.add(rfidMd14If);
			}
		}

		rfidMd14IfRepository.save(rfidMd14IfList);
	}

	@Override
	public void transactionTest() throws Exception {

		tempRfidRepository.deleteAll();
		rfidTempRepository.deleteAll();

		List<TempRfid> tempRfidList = new ArrayList<>();
		List<RfidTemp> rfidTempList = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			TempRfid tempRfid = new TempRfid();
			tempRfid.setName("test" + i);
			tempRfid.setRegDate(new Date());
			tempRfid.setUpdDate(new Date());

			tempRfidList.add(tempRfid);

			RfidTemp rfidTemp = new RfidTemp();
			rfidTemp.setSeq(Long.valueOf(i));
			rfidTemp.setName("test" + i);
			rfidTemp.setRegDate(new Date());
			rfidTemp.setFunctionYn("N");

			rfidTempList.add(rfidTemp);
		}

		// 메인 DB 저장
		tempRfidRepository.save(tempRfidList);

		// ERP DB 저장
		rfidTempRepository.save(rfidTempList);

		rfidTempRepository.flush();

		log.info("저장 완료");

		// Function 콜
//		erpService.functionTestCall();

		for(RfidTemp temp : rfidTempList){
			Map<String, Object> obj = erpService.functionTestCallJdbc(temp.getName());

			log.info("resultCode: " + obj.get("resultCode").toString() + " resultMessage: " +obj.get("resultMessage").toString());
		}

//		throw new RuntimeException();
	}
}

