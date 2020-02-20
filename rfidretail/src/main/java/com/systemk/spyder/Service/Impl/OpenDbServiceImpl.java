package com.systemk.spyder.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleDetailKey;
import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleHeaderKey;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleDetailRepository;
import com.systemk.spyder.Repository.OpenDb.OpenDbScheduleHeaderRepository;
import com.systemk.spyder.Service.BoxService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.OpenDbService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class OpenDbServiceImpl implements OpenDbService {

	@Autowired
	private OpenDbScheduleHeaderRepository openDbScheduleHeaderRepository;

	@Autowired
	private OpenDbScheduleDetailRepository openDbScheduleDetailRepository;

	@Autowired
	private BoxService boxService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Transactional
	@Override
	public void saveOpenDbSchedule(List<BoxInfo> boxInfoList, UserInfo userInfo, ReleaseBean releaseBean, Long workLine, String flag) throws Exception {

		String nowDate = CalendarUtil.convertFormat("yyyyMMddHHmmss");
		String arrivalDate = releaseBean.getArrivalDate().replaceAll("-", "");

		for(BoxInfo boxInfo : boxInfoList){

			OpenDbScheduleHeader openDbHeader = setOpenDbScheduleHeader(boxInfo, nowDate, arrivalDate, flag);

//			openDbScheduleHeaderRepository.save(openDbHeader);

			List<StyleModel> styleCountList = null;

			if(flag.equals("OP-R") || flag.equals("OP-R2")){
				styleCountList = boxService.boxStyleCountDistributionList(boxInfo.getBoxSeq());
			} else if(flag.equals("10-R")){
				styleCountList = boxService.boxStyleCountProductionList(boxInfo.getBoxSeq());
			}

			int lineNo = 10;

			for(StyleModel style : styleCountList){

				OpenDbScheduleDetail openDbDetail = setOpenDbScheduleDetail(boxInfo, lineNo, style, nowDate, arrivalDate, boxInfo.getInvoiceNum(), flag);

//				openDbScheduleDetailRepository.save(openDbDetail);

				// ERP 로그 저장
				if(flag.equals("OP-R") || flag.equals("OP-R2")){
//					erpService.saveStoreSchedule(boxInfo, lineNo, style, userInfo, nowDate, boxInfo.getInvoiceNum(), flag);
				} else {

				}

				lineNo += 10;
			}
		}
	}

	@Transactional
	@Override
	public OpenDbScheduleHeader saveOpenDbScheduleHeader(OpenDbScheduleHeader openDbHeader) throws Exception {
		return openDbScheduleHeaderRepository.save(openDbHeader);
	}

	@Transactional
	@Override
	public OpenDbScheduleDetail saveOpenDbScheduleDetail(OpenDbScheduleDetail openDbDetail) throws Exception {
		return openDbScheduleDetailRepository.save(openDbDetail);
	}

	@Override
	public OpenDbScheduleHeader setOpenDbScheduleHeader(BoxInfo boxInfo, String nowDate, String arrivalDate, String flag) throws Exception {

		OpenDbScheduleHeaderKey openDbHeaderKey = new OpenDbScheduleHeaderKey();
		openDbHeaderKey.setCustCd("R2455");
		openDbHeaderKey.setCustOrdNo(boxInfo.getBarcode());

		OpenDbScheduleHeader openDbHeader = new OpenDbScheduleHeader();
		openDbHeader.setOpenDbScheduleHeaderKey(openDbHeaderKey);
		openDbHeader.setCustOrdTypeCd(flag);
		openDbHeader.setDptArCd(boxInfo.getStartCompanyInfo().getCustomerCode() + boxInfo.getStartCompanyInfo().getCornerCode());

		if(flag.equals("OP-R") || flag.equals("OP-R2")){

			openDbHeader.setWorkSctnCd("I");
			openDbHeader.setReturnGoodsYn("N");
			openDbHeader.setAptdReqYmd(arrivalDate);
			openDbHeader.setArvArOfcrNm("윤성준");
			openDbHeader.setRmk("");
			openDbHeader.setDelYn("N");
			openDbHeader.setProgStatCd("00");
			openDbHeader.setCustIfYn("Y");
			openDbHeader.setIfYn("N");
			openDbHeader.setIfMsg("");
			openDbHeader.setRegrId("SPYDER001");
			openDbHeader.setRegDate(nowDate);
			openDbHeader.setMdfrId("SPYDER001");
			openDbHeader.setMdfDate(nowDate);
			openDbHeader.setTrIfYn("Y");

			if(boxInfo.getEndCompanyInfo().getCustomerCode().equals("100016")) {
				openDbHeader.setArvArCd("2945");
				openDbHeader.setWorkCntrCd("2945");
			} else {
				openDbHeader.setArvArCd("2930");
				openDbHeader.setWorkCntrCd("2930");
			}

		} else if(flag.equals("10-R")){

			openDbHeader.setWorkSctnCd("C");
			openDbHeader.setReturnGoodsYn("N");
			openDbHeader.setAptdReqYmd(arrivalDate);
			openDbHeader.setRmk("일반반품대기");
			openDbHeader.setDelYn("N");
			openDbHeader.setProgStatCd("00");
			openDbHeader.setCustIfYn("Y");
			openDbHeader.setIfYn("N");
			openDbHeader.setIfMsg("");
			openDbHeader.setRegrId("SPYDER001");
			openDbHeader.setRegDate(nowDate);
			openDbHeader.setMdfrId("SPYDER001");
			openDbHeader.setMdfDate(nowDate);
			openDbHeader.setTrIfYn("Y");

			openDbHeader.setArvArCd("2935");
			openDbHeader.setWorkCntrCd("2935");

		}

		return openDbHeader;
	}

	@Override
	public OpenDbScheduleDetail setOpenDbScheduleDetail(BoxInfo boxInfo, int lineNo, StyleModel style, String nowDate, String arrivalDate, String invoiceNum, String flag) throws Exception{

		OpenDbScheduleDetailKey openDbDetailKey = new OpenDbScheduleDetailKey();
		openDbDetailKey.setCustCd("R2455");
		openDbDetailKey.setCustOrdNo(boxInfo.getBarcode());
		openDbDetailKey.setCustOrdLineNo(Integer.toString(lineNo));

		OpenDbScheduleDetail openDbDetail = new OpenDbScheduleDetail();
		openDbDetail.setOpenDbScheduleDetailKey(openDbDetailKey);
		openDbDetail.setCsnItemCd(style.getStyle() + style.getColor() + style.getSize());
		openDbDetail.setOrdQty(style.getCount());

		if(flag.equals("OP-R") || flag.equals("OP-R2")){

			openDbDetail.setQtyUnitCd("EA");
			openDbDetail.setOrdWgt("0");
			openDbDetail.setAptdReqYmd(arrivalDate);
			openDbDetail.setCbm("0");
			openDbDetail.setRmk("STAGE");
			openDbDetail.setDelYn("N");
			openDbDetail.setIfYn("N");
			openDbDetail.setRegrId("SPYDER001");
			openDbDetail.setRegDate(nowDate);
			openDbDetail.setMdfrId("SPYDER001");
			openDbDetail.setMdfDate(nowDate);

			// 차수 입력
			openDbDetail.setCol01(style.getOrderDegree());

			if(flag.equals("OP-R") ){

				// BL 넘버
				openDbDetail.setCol03("");

			} else if(flag.equals("OP-R2")){

				// BL 넘버
				openDbDetail.setCol03(invoiceNum);

			}

		} else if(flag.equals("10-R")){

			openDbDetail.setSctnCd("C");
			openDbDetail.setQtyUnitCd("EA");
			openDbDetail.setOrdWgt("1");
			openDbDetail.setWgtUnitCd("KG");
			openDbDetail.setAptdReqYmd(arrivalDate);
			openDbDetail.setCustRefNo(boxInfo.getBarcode());
			openDbDetail.setRmk("STAGE");
			openDbDetail.setDelYn("N");
			openDbDetail.setIfYn("N");
			openDbDetail.setRegrId("SPYDER001");
			openDbDetail.setRegDate(nowDate);
			openDbDetail.setMdfrId("SPYDER001");
			openDbDetail.setMdfDate(nowDate);

			// 차수 입력
			openDbDetail.setCol01(style.getOrderDegree());

			// BL 넘버
			openDbDetail.setCol03("");

		}

		return openDbDetail;
	}

	@Override
	public OpenDbScheduleDetail setOpenDbScheduleDetail(BoxInfo boxInfo, StorageScheduleDetailLogModel detailLog, String nowDate, String arrivalDate, String flag) throws Exception{

		OpenDbScheduleDetailKey openDbDetailKey = new OpenDbScheduleDetailKey();
		openDbDetailKey.setCustCd("R2455");
		openDbDetailKey.setCustOrdNo(boxInfo.getBarcode());
		openDbDetailKey.setCustOrdLineNo(detailLog.getLineNum().toString());

		OpenDbScheduleDetail openDbDetail = new OpenDbScheduleDetail();
		openDbDetail.setOpenDbScheduleDetailKey(openDbDetailKey);
		openDbDetail.setCsnItemCd(detailLog.getStyle() + detailLog.getColor() + detailLog.getSize());
		openDbDetail.setOrdQty(detailLog.getAmount());

		if(flag.equals("OP-R") || flag.equals("OP-R2")){

			openDbDetail.setQtyUnitCd("EA");
			openDbDetail.setOrdWgt("0");
			openDbDetail.setAptdReqYmd(arrivalDate);
			openDbDetail.setCbm("0");
			openDbDetail.setRmk("STAGE");
			openDbDetail.setDelYn("N");
			openDbDetail.setIfYn("N");
			openDbDetail.setRegrId("SPYDER001");
			openDbDetail.setRegDate(nowDate);
			openDbDetail.setMdfrId("SPYDER001");
			openDbDetail.setMdfDate(nowDate);

			// 차수 입력
			openDbDetail.setCol01(detailLog.getOrderDegree());

			if(flag.equals("OP-R") ){
				// BL 넘버
				openDbDetail.setCol03("");

			} else if(flag.equals("OP-R2")){
				// BL 넘버
				openDbDetail.setCol03(boxInfo.getInvoiceNum());
			}

		} else if(flag.equals("10-R")){

			openDbDetail.setSctnCd("C");
			openDbDetail.setQtyUnitCd("EA");
			openDbDetail.setOrdWgt("1");
			openDbDetail.setWgtUnitCd("KG");
			openDbDetail.setAptdReqYmd(arrivalDate);
			openDbDetail.setCustRefNo(boxInfo.getBarcode());
			openDbDetail.setRmk("STAGE");
			openDbDetail.setDelYn("N");
			openDbDetail.setIfYn("N");
			openDbDetail.setRegrId("SPYDER001");
			openDbDetail.setRegDate(nowDate);
			openDbDetail.setMdfrId("SPYDER001");
			openDbDetail.setMdfDate(nowDate);

			// 차수 입력
			openDbDetail.setCol01(detailLog.getOrderDegree());

			// BL 넘버
			openDbDetail.setCol03("");

		}

		return openDbDetail;
	}

	@Transactional
	@Override
	public OpenDbScheduleHeader disuseOpenDbScheduleHeader(StorageScheduleLog storageScheduleLog) throws Exception {

		OpenDbScheduleHeaderKey openDbHeaderKey = new OpenDbScheduleHeaderKey();
		openDbHeaderKey.setCustCd("R2455");
		openDbHeaderKey.setCustOrdNo(storageScheduleLog.getBoxInfo().getBarcode());


		OpenDbScheduleHeader header = openDbScheduleHeaderRepository.findOne(openDbHeaderKey);

		if(header != null){
			header.setWorkSctnCd("U");
			header = openDbScheduleHeaderRepository.save(header);
		}

		return header;
	}
}
