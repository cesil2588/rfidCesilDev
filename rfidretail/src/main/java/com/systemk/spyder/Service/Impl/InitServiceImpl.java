package com.systemk.spyder.Service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Lepsilon.TboxPick;
import com.systemk.spyder.Entity.Lepsilon.TreceiptDetail;
import com.systemk.spyder.Entity.Lepsilon.Tshipment;
import com.systemk.spyder.Entity.Lepsilon.Key.TboxPickKey;
import com.systemk.spyder.Entity.Lepsilon.Key.TshipmentKey;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.TempProductionReleaseBox;
import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Entity.Main.TempProductionReleaseStyle;
import com.systemk.spyder.Entity.Main.TempProductionReleaseTag;
import com.systemk.spyder.Repository.Lepsilon.TboxPickRepository;
import com.systemk.spyder.Repository.Lepsilon.TreceiptDetailRepository;
import com.systemk.spyder.Repository.Lepsilon.TshipmentRepository;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageLogRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleDetailLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleSubDetailLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.TempProductionReleaseHeaderRepository;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.InitService;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class InitServiceImpl implements InitService {

	@Autowired
	private TboxPickRepository tboxPickRepository;

	@Autowired
	private TshipmentRepository tshipmentRepository;

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository;

	@Autowired
	private ProductionStorageRepository productionStorageRepository;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private StorageScheduleDetailLogRepository storageScheduleDetailLogRepository;

	@Autowired
	private StorageScheduleSubDetailLogRepository storageScheduleSubDetailLogRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private StoreStorageLogRepository storeStorageLogRepository;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private DistributionStorageLogRepository distributionStorageLogRepository;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private TreceiptDetailRepository treceiptDetailRepository;

	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;

	@Transactional
	@Override
	public Map<String, Object> initStorage(String type) throws Exception {


		storageScheduleSubDetailLogRepository.deleteAll();
		storageScheduleDetailLogRepository.deleteAll();
		storageScheduleLogRepository.deleteAll();

		distributionStorageRfidTagRepository.deleteAll();
		distributionStorageLogRepository.deleteAll();
		distributionStorageRepository.deleteAll();

		List<BoxInfo> updateBoxList = boxInfoRepository.findByStartCompanyInfoCompanySeq(Long.valueOf(282));

		for(BoxInfo boxInfo : updateBoxList){
			boxInfo.setStat("1");
		}

		boxInfoRepository.save(updateBoxList);

		ProductionStorage storage1 = productionStorageRepository.findOne(Long.valueOf(1297290));
		ProductionStorage storage2 = productionStorageRepository.findOne(Long.valueOf(1294408));

		storage1.setStockAmount(storage1.getReleaseAmount());
		storage1.setReleaseAmount(Long.valueOf(0));

		storage2.setStockAmount(storage1.getReleaseAmount());
		storage2.setReleaseAmount(Long.valueOf(0));

		productionStorageRepository.save(storage1);
		productionStorageRepository.save(storage2);

		List<ProductionStorageRfidTag> productionStorageRfidTagList1 = productionStorageRfidTagRepository.findByProductionStorageSeq(Long.valueOf(1297290));

		for(ProductionStorageRfidTag rfidTag : productionStorageRfidTagList1){
			rfidTag.setBarcode("");
			rfidTag.setBoxSeq(null);
			rfidTag.setStat("2");
		}

		List<ProductionStorageRfidTag> productionStorageRfidTagList2 = productionStorageRfidTagRepository.findByProductionStorageSeq(Long.valueOf(1297290));

		for(ProductionStorageRfidTag rfidTag : productionStorageRfidTagList2){
			rfidTag.setBarcode("");
			rfidTag.setBoxSeq(null);
			rfidTag.setStat("2");
		}

		productionStorageRfidTagRepository.save(productionStorageRfidTagList1);
		productionStorageRfidTagRepository.save(productionStorageRfidTagList2);


		List<String> barcodeList = new ArrayList<String>();

		barcodeList.add("011808130001245100");
		barcodeList.add("011808130002245100");

		TempProductionReleaseHeader releaseHeader = new TempProductionReleaseHeader();
		releaseHeader.setArrivalDate(CalendarUtil.convertFormat("yyyyMMdd"));
		releaseHeader.setRegDate(new Date());
		releaseHeader.setType("1");
		releaseHeader.setUserSeq(Long.valueOf(1));
		releaseHeader.setStat("1");
		Set<TempProductionReleaseBox> boxList = new HashSet<TempProductionReleaseBox>();

		if(type.equals("1")){

			for(int i=0; i<barcodeList.size(); i++){
				TempProductionReleaseBox releaseBox = new TempProductionReleaseBox();

				releaseBox.setBarcode(barcodeList.get(i));

				Set<TempProductionReleaseStyle> styleList = new HashSet<TempProductionReleaseStyle>();

				TempProductionReleaseStyle releaseStyle = new TempProductionReleaseStyle();

				Long productionStorageSeq;

				if(i == 0){
					productionStorageSeq = Long.valueOf(1297290);
				} else {
					productionStorageSeq = Long.valueOf(1294408);
				}
				releaseStyle.setProductionStorageSeq(productionStorageSeq);

				Set<TempProductionReleaseTag> tagList = new HashSet<TempProductionReleaseTag>();

				List<ProductionStorageRfidTag> rfidTagList = productionStorageRfidTagRepository.findByProductionStorageSeq(productionStorageSeq);

				for(ProductionStorageRfidTag rfidTag : rfidTagList){
					TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();
					releaseTag.setRfidTag(rfidTag.getRfidTag());

					tagList.add(releaseTag);
				}

				releaseStyle.setTagList(tagList);

				styleList.add(releaseStyle);

				releaseBox.setStyleList(styleList);

				boxList.add(releaseBox);
			}

		} else if(type.equals("2")){

			TempProductionReleaseBox releaseBox = new TempProductionReleaseBox();

			releaseBox.setBarcode(barcodeList.get(0));

			Set<TempProductionReleaseStyle> styleList = new HashSet<TempProductionReleaseStyle>();

			Long productionStorageSeq1 = Long.valueOf(1297290);
			Long productionStorageSeq2 = Long.valueOf(1294408);

			for(int j=0; j<2; j++){
				TempProductionReleaseStyle releaseStyle = new TempProductionReleaseStyle();

				if(j == 0){
					releaseStyle.setProductionStorageSeq(productionStorageSeq1);
				} else if(j == 1){
					releaseStyle.setProductionStorageSeq(productionStorageSeq2);
				}

				Set<TempProductionReleaseTag> tagList = new HashSet<TempProductionReleaseTag>();

				List<ProductionStorageRfidTag> rfidTagList = productionStorageRfidTagRepository.findByProductionStorageSeq(releaseStyle.getProductionStorageSeq());

				for(ProductionStorageRfidTag rfidTag : rfidTagList){
					TempProductionReleaseTag releaseTag = new TempProductionReleaseTag();
					releaseTag.setRfidTag(rfidTag.getRfidTag());

					tagList.add(releaseTag);
				}

				releaseStyle.setTagList(tagList);
				styleList.add(releaseStyle);
			}

			releaseBox.setStyleList(styleList);

			boxList.add(releaseBox);
		}
		releaseHeader.setBoxList(boxList);

		releaseHeader = tempProductionReleaseHeaderRepository.save(releaseHeader);

		for(TempProductionReleaseBox releaseBox : releaseHeader.getBoxList()){
			releaseBox.setTempHeaderSeq(releaseHeader.getTempHeaderSeq());

			for(TempProductionReleaseStyle releaseStyle : releaseBox.getStyleList()){
				releaseStyle.setTempHeaderSeq(releaseHeader.getTempHeaderSeq());

				for(TempProductionReleaseTag releaseTag : releaseStyle.getTagList()){
					releaseTag.setTempHeaderSeq(releaseHeader.getTempHeaderSeq());
				}
			}
		}

		tempProductionReleaseHeaderRepository.save(releaseHeader);

		productionStorageRfidTagService.releaseComplete(releaseHeader.getTempHeaderSeq());

		List<StorageScheduleLog> storageScheduleLogList = storageScheduleLogRepository.findAll();

		storageScheduleLogService.updateRelease(storageScheduleLogList);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> resetStorage() throws Exception {

		List<TreceiptDetail> treceiptDetailList = treceiptDetailRepository.findAll();

		for(TreceiptDetail treceiptDetail : treceiptDetailList){
			treceiptDetail.setStatus("999");
		}

		treceiptDetailRepository.save(treceiptDetailList);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> initRelease(String type) throws Exception {

		tshipmentRepository.deleteAll();
		tboxPickRepository.deleteAll();
		releaseScheduleLogRepository.deleteAll();

		storeStorageRfidTagRepository.deleteAll();
		storeStorageLogRepository.deleteAll();
		storeStorageRepository.deleteAll();

		if(type.equals("1")){

			List<String> barcodeList = new ArrayList<String>();

			barcodeList.add("011808130001245100");
			barcodeList.add("011808130002245100");

			erpStoreScheduleRepository.deleteByEndCompanyInfoCompanySeq(Long.valueOf(328889));

			ErpStoreSchedule erpStoreSchedule = saveErpStoreSchedule("1");

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

		} else if(type.equals("2")){

			List<String> barcodeList = new ArrayList<String>();

			barcodeList.add("011808130001245100");
			barcodeList.add("011808130002245100");

			erpStoreScheduleRepository.deleteByEndCompanyInfoCompanySeq(Long.valueOf(328889));

			ErpStoreSchedule erpStoreSchedule1 = saveErpStoreSchedule("1");
			ErpStoreSchedule erpStoreSchedule2 = saveErpStoreSchedule("2");

			TshipmentKey key = new TshipmentKey();
			key.setShipmentNo("10000000");
			key.setwCode("LW02");

			Tshipment tshipment = new Tshipment();
			tshipment.setKey(key);
			tshipment.setStatus("999");
			tshipment.setReferenceNo(erpStoreSchedule1.getCompleteDate() + erpStoreSchedule1.getEndCompanyInfo().getCustomerCode() + erpStoreSchedule1.getEndCompanyInfo().getCornerCode() + erpStoreSchedule1.getCompleteSeq());

			tshipmentRepository.save(tshipment);

			int calc1 = (int) (erpStoreSchedule1.getOrderAmount() / 2);
			int calc2 = (int) (erpStoreSchedule2.getOrderAmount() / 2);

			int lineNum = 10;

			for(int i=0; i<barcodeList.size(); i++){

				for(int j=0; j<2; j++){

					TboxPickKey tboxKey = new TboxPickKey();
					tboxKey.setShipmentNo("10000000");
					tboxKey.setLineNo(Integer.toString(lineNum));

					TboxPick tboxPick = new TboxPick();
					tboxPick.setKey(tboxKey);
					tboxPick.setRfidFlag("N");
					tboxPick.setwCode("LW02");
					tboxPick.setOrderQty(new BigDecimal(barcodeList.get(i)));

					if(j == 0){
						tboxPick.setStockNm(erpStoreSchedule1.getStyle() + erpStoreSchedule1.getColor() + erpStoreSchedule1.getSize());
						tboxPick.setPickQty(new BigDecimal(calc1));
					} else if(j == 1){
						tboxPick.setStockNm(erpStoreSchedule2.getStyle() + erpStoreSchedule2.getColor() + erpStoreSchedule2.getSize());
						tboxPick.setPickQty(new BigDecimal(calc2));
					}

					lineNum += 10;

					tboxPickRepository.save(tboxPick);
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> resetRelease() throws Exception {
		List<Tshipment> tshipmentList = tshipmentRepository.findAll();

		for(Tshipment tshipment : tshipmentList){
			tshipment.setStatus("999");
		}

		tshipmentRepository.save(tshipmentList);

		List<TboxPick> tboxPickList = tboxPickRepository.findAll();

		for(TboxPick tboxPick : tboxPickList){
			tboxPick.setRfidFlag("N");
		}

		tboxPickRepository.save(tboxPickList);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	public ErpStoreSchedule saveErpStoreSchedule(String type){

		ErpStoreSchedule storeSchedule = new ErpStoreSchedule();

		if(type.equals("1")){

			storeSchedule.setCompleteDate("20180813");
	    	storeSchedule.setCompleteType("R");
	    	storeSchedule.setCompleteSeq("01");

	    	CompanyInfo startCompanyInfo = companyInfoRepository.findOne(Long.valueOf(328756));
	    	CompanyInfo endCompanyInfo = companyInfoRepository.findOne(Long.valueOf(328889));

	    	storeSchedule.setStartCompanyInfo(startCompanyInfo);
	    	storeSchedule.setEndCompanyInfo(endCompanyInfo);

	    	storeSchedule.setStyle("SPDFCNFT501M");
	    	storeSchedule.setAnotherStyle("DGR095");
	    	storeSchedule.setBundleCd(" ");

	    	storeSchedule.setCompleteIfSeq("1");
	    	storeSchedule.setColor("DGR");
	    	storeSchedule.setSize("095");
	    	storeSchedule.setOrderAmount(Long.valueOf(60));
	    	storeSchedule.setSortingAmount(Long.valueOf(60));
	    	storeSchedule.setReleaseAmount(Long.valueOf(0));
	    	storeSchedule.setReleaseDate("");
	    	storeSchedule.setReleaseSeq(Long.valueOf(0));
	    	storeSchedule.setReleaseSerial(Long.valueOf(0));
	    	storeSchedule.setReleaseUserId("");
	    	storeSchedule.setStat("I");
	    	storeSchedule.setErpRegDate("20180813");
	    	storeSchedule.setRegUserId("SYSTST");
	    	storeSchedule.setRegDate(new Date());

		} else if(type.endsWith("2")){

			storeSchedule.setCompleteDate("20180813");
	    	storeSchedule.setCompleteType("R");
	    	storeSchedule.setCompleteSeq("01");

	    	CompanyInfo startCompanyInfo = companyInfoRepository.findOne(Long.valueOf(328756));
	    	CompanyInfo endCompanyInfo = companyInfoRepository.findOne(Long.valueOf(328889));

	    	storeSchedule.setStartCompanyInfo(startCompanyInfo);
	    	storeSchedule.setEndCompanyInfo(endCompanyInfo);

	    	storeSchedule.setStyle("SPDFCNFT503M");
	    	storeSchedule.setAnotherStyle("BLK105");
	    	storeSchedule.setBundleCd(" ");

	    	storeSchedule.setCompleteIfSeq("1");
	    	storeSchedule.setColor("BLK");
	    	storeSchedule.setSize("105");
	    	storeSchedule.setOrderAmount(Long.valueOf(60));
	    	storeSchedule.setSortingAmount(Long.valueOf(60));
	    	storeSchedule.setReleaseAmount(Long.valueOf(0));
	    	storeSchedule.setReleaseDate("");
	    	storeSchedule.setReleaseSeq(Long.valueOf(0));
	    	storeSchedule.setReleaseSerial(Long.valueOf(0));
	    	storeSchedule.setReleaseUserId("");
	    	storeSchedule.setStat("I");
	    	storeSchedule.setErpRegDate("20180813");
	    	storeSchedule.setRegUserId("SYSTST");
	    	storeSchedule.setRegDate(new Date());

		}

    	return erpStoreScheduleRepository.save(storeSchedule);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> tboxList(List<String> referenceList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<TboxPick> tboxPickList = new ArrayList<TboxPick>();

		for(String referenceNo : referenceList){
			Tshipment tshipment = tshipmentRepository.findByReferenceNo(referenceNo);

			if(tshipment != null){

				List<TboxPick> tempTboxPickList = tboxPickRepository.findByKeyShipmentNo(tshipment.getKey().getShipmentNo());

				for(TboxPick tboxPick : tempTboxPickList){
					tboxPick.setReferenceNo(referenceNo);
					tboxPickList.add(tboxPick);
				}
			}
		}

		obj.put("tboxPickList", tboxPickList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> tboxBarcodeCheck(String referenceNo, String barcode) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Tshipment tshipment = tshipmentRepository.findByReferenceNo(referenceNo);

		if(tshipment != null){

			List<TboxPick> tempTboxPickList = tboxPickRepository.findByKeyShipmentNoAndOrderQty(tshipment.getKey().getShipmentNo(), new BigDecimal(barcode));

			if(tempTboxPickList.size() > 0){

				obj.put("resultCode", ApiResultConstans.DUPLICATION_WMS_BOX_BARCODE);
				obj.put("resultMessage", ApiResultConstans.DUPLICATION_WMS_BOX_BARCODE_MESSAGE);

				return obj;
			}
		}

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

		if(boxInfo != null){

			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);

			return obj;
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tboxUpdate(List<TboxPick> tboxPickList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		tboxPickRepository.save(tboxPickList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tboxDelete(List<TboxPick> tboxPickList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		tboxPickRepository.delete(tboxPickList);

		List<TboxPick> tempTboxPickList = tboxPickRepository.findByKeyShipmentNo(tboxPickList.get(0).getKey().getShipmentNo());

		if(tboxPickList.size() == 0) {

			TshipmentKey headerKey = new TshipmentKey();
			headerKey.setShipmentNo(tboxPickList.get(0).getKey().getShipmentNo());
			headerKey.setwCode("LW02");

			tshipmentRepository.delete(headerKey);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tboxSave(TboxPick tboxPick) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(tboxPick.getOrderQty().toString());

		if(boxInfo != null){

			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);

			return obj;
		}

		Tshipment tshipment = tshipmentRepository.findByReferenceNo(tboxPick.getReferenceNo());

		if(tshipment != null){

			List<TboxPick> tempTboxPickList = tboxPickRepository.findByKeyShipmentNoAndOrderQty(tshipment.getKey().getShipmentNo(), tboxPick.getOrderQty());

			if(tempTboxPickList.size() > 0){

				obj.put("resultCode", ApiResultConstans.DUPLICATION_WMS_BOX_BARCODE);
				obj.put("resultMessage", ApiResultConstans.DUPLICATION_WMS_BOX_BARCODE_MESSAGE);

				return obj;
			}
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> tboxPickList() throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		List<TboxPick> tboxPickList = tboxPickRepository.findByOrderByRfidFlagAsc();

		for(TboxPick tboxPick : tboxPickList){

			String style = tboxPick.getStock().substring(0, 12);
			String color = tboxPick.getStock().substring(12, 15);
			String size = tboxPick.getStock().substring(15, 18);

			TshipmentKey key = new TshipmentKey();
			key.setwCode("LW02");
			key.setShipmentNo(tboxPick.getKey().getShipmentNo());

			Tshipment tshipment = tshipmentRepository.findOne(key);

			tboxPick.setTshipment(tshipment);

			ErpStoreSchedule erpStoreSchedule = erpStoreScheduleService.findByReleaseSchedule(tshipment.getReferenceNo(), style, color, size);

			tboxPick.setErpStoreSchedule(erpStoreSchedule);

		}

		obj.put("tboxPickList", tboxPickList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> tshipment(String referenceNo) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		Tshipment tshipment = tshipmentRepository.findByReferenceNo(referenceNo);

		obj.put("tshipment", tshipment);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tshipmentUpdate(Tshipment tshipment) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		tshipmentRepository.save(tshipment);

//		for(TboxPick tboxPick : tshipment.getTboxPickList()) {
//			tboxPick.setRfidFlag("Y");
//			tboxPickRepository.save(tboxPick);
//		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tshipmentDelete(List<TboxPick> tboxPickList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		for(TboxPick tboxPick : tboxPickList) {
			tboxPickRepository.delete(tboxPick.getKey());

			tshipmentRepository.delete(tboxPick.getTshipment());
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tshipmentSave(List<TboxPick> tboxPickList) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		for(TboxPick tboxPick : tboxPickList) {

			BoxInfo boxInfo = boxInfoRepository.findByBarcode(tboxPick.getOrderQty().toString());

			if(boxInfo != null){

				obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
				obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);

				return obj;
			}

			// 송장번호 하나에 다수의 ReferenceNo가 들어갈 수 있어 중복체크 제거

//			Tshipment tempTshipment = tshipmentRepository.findByReferenceNo(tboxPick.getReferenceNo());
//
//			if(tempTshipment != null){
//
//				obj.put("resultCode", ApiResultConstans.DUPLICATION_WMS_REFERENCENO);
//				obj.put("resultMessage", ApiResultConstans.DUPLICATION_WMS_REFERENCENO_MESSAGE);
//
//				return obj;
//			}

			tshipmentRepository.save(tboxPick.getTshipment());
		}

		tboxPickRepository.save(tboxPickList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tshipmentInit(Tshipment tshipment) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		tshipment.setStatus("999");
		tshipmentRepository.save(tshipment);

		for(TboxPick tboxPick : tshipment.getTboxPickList()) {
			tboxPick.setRfidFlag("N");
			tboxPickRepository.save(tboxPick);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> tshipmentTotalUpdate(Tshipment tshipment) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		tshipmentRepository.save(tshipment);

		for(TboxPick tboxPick : tshipment.getTboxPickList()) {
			tboxPick.setRfidFlag("Y");
			tboxPickRepository.save(tboxPick);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

}
