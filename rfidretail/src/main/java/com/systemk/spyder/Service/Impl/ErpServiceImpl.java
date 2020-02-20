package com.systemk.spyder.Service.Impl;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.Key.*;
import com.systemk.spyder.Entity.External.*;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.External.RfidMd14RtIfRepository;
import com.systemk.spyder.Repository.External.RfidSd02IfRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnScheduleRepository;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;

@Service
public class ErpServiceImpl implements ErpService{

	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private RfidSd02IfRepository rfidSd02IfRepository;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository;

	@Autowired
	private RfidMd14RtIfRepository rfidMd14RtIfRepository;

	@Autowired
	@Qualifier(value = "externalEntityManager")
    private EntityManagerFactory entityManagerFactory;

	@Override
	public RfidIb10If saveStoreSchedule(BoxInfo boxInfo, Long lineNo, StorageScheduleDetailLogModel detailLog, UserInfo userInfo, String nowDate, String invoiceNum, String flag) throws Exception {

		// ERP 로그 저장
		RfidIb10IfKey tbIb10IfKey = new RfidIb10IfKey();
		tbIb10IfKey.setLb10Bxno(boxInfo.getBarcode());
		tbIb10IfKey.setLb10Bxsq(new BigDecimal(lineNo));

		RfidIb10If tbIb10If = new RfidIb10If();
		tbIb10If.setKey(tbIb10IfKey);
		tbIb10If.setLb10Styl(detailLog.getStyle());
		tbIb10If.setLb10Stcd(detailLog.getColor() + detailLog.getSize());
		tbIb10If.setLb10Jjch(detailLog.getOrderDegree());
		tbIb10If.setLb10Prod(boxInfo.getStartCompanyInfo().getCustomerCode());
		tbIb10If.setLb10Prco(boxInfo.getStartCompanyInfo().getCornerCode());
		tbIb10If.setLb10Bigo("");
		tbIb10If.setLb10Tryn("N");
		tbIb10If.setLb10Stat("C");
		tbIb10If.setLb10Trdt("");
		tbIb10If.setLb10Trmh("");
		tbIb10If.setLb10Errt("");
		tbIb10If.setLb10Cfqt(new BigDecimal(detailLog.getAmount()));
		tbIb10If.setLb10Endt(nowDate);
		tbIb10If.setLb10Enid(userInfo.getUserId());
		tbIb10If.setLb10Updt("");
		tbIb10If.setLb10Upid("");
		tbIb10If.setLb10Jbdt(CalendarUtil.convertFormat("yyyyMMdd", boxInfo.getUpdDate()));

		if (flag.equals("OP-R")) {
			tbIb10If.setLb10Blno("");
		} else if (flag.equals("OP-R2")) {
			tbIb10If.setLb10Blno(invoiceNum);
		}

		return tbIb10If;
	}

	//일반 물류 입고 실적 반영 (rfid_lb01_if)
	@Transactional
	@Override
	public List<RfidIb01If> saveStorageListComplete(List<StorageScheduleLog> storageScheduleLogList) throws Exception {

		List<RfidIb01If> rfidIb01IfList = new ArrayList<RfidIb01If>();

		for(StorageScheduleLog storageScheduleLog : storageScheduleLogList){

			int lineNum = 1;

			Set<StorageScheduleDetailLogModel> tempErpDetailList = storageScheduleLogService.generateScheduleLog(storageScheduleLog, "ERP");

			for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {

				RfidIb01IfKey rfidIb01IfKey = new RfidIb01IfKey();
				rfidIb01IfKey.setLb01Ipdt(CalendarUtil.convertFormat("yyyyMMdd"));
				rfidIb01IfKey.setLb01Bxno(storageScheduleLog.getBoxInfo().getBarcode());
				rfidIb01IfKey.setLb01Ipsno(BigDecimal.valueOf(lineNum));

				RfidIb01If rfidIb01If = new RfidIb01If();
				rfidIb01If.setKey(rfidIb01IfKey);
				rfidIb01If.setLb01Emgb("A");
				rfidIb01If.setLb01Cgcd(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode());
				rfidIb01If.setLb01Cgco(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode());
				rfidIb01If.setLb01Prod(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCustomerCode());
				rfidIb01If.setLb01Prco(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCornerCode());
				rfidIb01If.setLb01Styl(detailLog.getStyle());
				rfidIb01If.setLb01Stcd(detailLog.getColor() + detailLog.getSize());
				rfidIb01If.setLb01It06(detailLog.getColor());
				rfidIb01If.setLb01It07(detailLog.getSize());
				rfidIb01If.setLb01Jjch(detailLog.getOrderDegree());
				rfidIb01If.setLb01Ipqt(BigDecimal.valueOf(detailLog.getAmount()));
				rfidIb01If.setLb01Tryn("N");
				rfidIb01If.setLb01Stat("C");
				rfidIb01If.setLb01Time("");
				rfidIb01If.setLb01Ipsq(BigDecimal.valueOf(0));
				rfidIb01If.setLb01Ipsr(BigDecimal.valueOf(0));
				rfidIb01If.setLb01Bigo("");

				if(storageScheduleLog.getOrderType().equals("OP-R2")){
					rfidIb01If.setLb01Blno(storageScheduleLog.getInvoiceNum());
				} else {
					rfidIb01If.setLb01Blno("");
				}

				rfidIb01If.setLb01Endt(new Date());

				rfidIb01IfList.add(rfidIb01If);
				}

				lineNum += 1;
			}

		return rfidIb01IfList;
	}

	@Transactional
	@Override
	public List<RfidIb01If> saveStorageListComplete(StorageScheduleLog storageScheduleLog) throws Exception {

		List<RfidIb01If> rfidIb01IfList = new ArrayList<RfidIb01If>();

		int lineNum = 1;

		Set<StorageScheduleDetailLogModel> tempErpDetailList = storageScheduleLogService.generateScheduleLog(storageScheduleLog, "ERP");

		for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {

			RfidIb01IfKey rfidIb01IfKey = new RfidIb01IfKey();
			rfidIb01IfKey.setLb01Ipdt(CalendarUtil.convertFormat("yyyyMMdd"));
			rfidIb01IfKey.setLb01Bxno(storageScheduleLog.getBoxInfo().getBarcode());
			rfidIb01IfKey.setLb01Ipsno(BigDecimal.valueOf(lineNum));

			RfidIb01If rfidIb01If = new RfidIb01If();
			rfidIb01If.setKey(rfidIb01IfKey);
			rfidIb01If.setLb01Emgb("A");
			rfidIb01If.setLb01Cgcd(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode());
			rfidIb01If.setLb01Cgco(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode());
			rfidIb01If.setLb01Prod(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCustomerCode());
			rfidIb01If.setLb01Prco(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCornerCode());
			rfidIb01If.setLb01Styl(detailLog.getStyle());
			rfidIb01If.setLb01Stcd(detailLog.getColor() + detailLog.getSize());
			rfidIb01If.setLb01It06(detailLog.getColor());
			rfidIb01If.setLb01It07(detailLog.getSize());
			rfidIb01If.setLb01Jjch(detailLog.getOrderDegree());
			rfidIb01If.setLb01Ipqt(BigDecimal.valueOf(detailLog.getAmount()));
			rfidIb01If.setLb01Tryn("N");
			rfidIb01If.setLb01Stat("C");
			rfidIb01If.setLb01Time("");
			rfidIb01If.setLb01Ipsq(BigDecimal.valueOf(0));
			rfidIb01If.setLb01Ipsr(BigDecimal.valueOf(0));
			rfidIb01If.setLb01Bigo("");

			if(storageScheduleLog.getOrderType().equals("OP-R2")){
				rfidIb01If.setLb01Blno(storageScheduleLog.getInvoiceNum());
			} else {
				rfidIb01If.setLb01Blno("");
			}

			rfidIb01If.setLb01Endt(new Date());

			rfidIb01IfList.add(rfidIb01If);

			lineNum += 1;
		}

		return rfidIb01IfList;
	}

	@Transactional
	@Override
	public List<RfidIb01If> saveStorageListRollback(StorageScheduleLog storageScheduleLog) throws Exception {

		List<RfidIb01If> rfidIb01IfList = new ArrayList<RfidIb01If>();

		int lineNum = 1;

		Set<StorageScheduleDetailLogModel> tempErpDetailList = storageScheduleLogService.generateScheduleLog(storageScheduleLog, "ERP");

		for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {

			RfidIb01IfKey rfidIb01IfKey = new RfidIb01IfKey();
			rfidIb01IfKey.setLb01Ipdt(CalendarUtil.convertFormat("yyyyMMdd"));
			rfidIb01IfKey.setLb01Bxno(storageScheduleLog.getBoxInfo().getBarcode() + "B");
			rfidIb01IfKey.setLb01Ipsno(BigDecimal.valueOf(lineNum));

			RfidIb01If rfidIb01If = new RfidIb01If();
			rfidIb01If.setKey(rfidIb01IfKey);
			rfidIb01If.setLb01Emgb("B");
			rfidIb01If.setLb01Cgcd(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode());
			rfidIb01If.setLb01Cgco(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode());
			rfidIb01If.setLb01Prod(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCustomerCode());
			rfidIb01If.setLb01Prco(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCornerCode());
			rfidIb01If.setLb01Styl(detailLog.getStyle());
			rfidIb01If.setLb01Stcd(detailLog.getColor() + detailLog.getSize());
			rfidIb01If.setLb01It06(detailLog.getColor());
			rfidIb01If.setLb01It07(detailLog.getSize());
			rfidIb01If.setLb01Jjch(detailLog.getOrderDegree());
			rfidIb01If.setLb01Ipqt(BigDecimal.valueOf(-detailLog.getAmount()));
			rfidIb01If.setLb01Tryn("N");
			rfidIb01If.setLb01Stat("C");
			rfidIb01If.setLb01Time("");
			rfidIb01If.setLb01Ipsq(BigDecimal.valueOf(0));
			rfidIb01If.setLb01Ipsr(BigDecimal.valueOf(0));
			rfidIb01If.setLb01Bigo("");

			if(storageScheduleLog.getOrderType().equals("OP-R2")){
				rfidIb01If.setLb01Blno(storageScheduleLog.getInvoiceNum());
			} else {
				rfidIb01If.setLb01Blno("");
			}

			rfidIb01If.setLb01Endt(new Date());

			rfidIb01IfList.add(rfidIb01If);

			lineNum += 1;
		}

		return rfidIb01IfList;
	}

	@Transactional
	@Override
	public int updateRfidTagStatUseYn(String rfidTag, String stat, String useYn) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(externalDataSourceConfig.externalDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("UPDATE rfid_tag_if ");
		query.append("SET tag_stat = :stat, ");
		query.append("tag_usyn = :useYn, ");
		query.append("tag_updt = getDate() ");
		query.append("WHERE tag_rfid = :rfidTag ");

		params.put("stat", stat);
		params.put("useYn", useYn);
		params.put("rfidTag", rfidTag);

		return nameTemplate.update(query.toString(), params);

	}

	@Transactional
	@Override
	public int disuseErpStorageSchedule(StorageScheduleLog storageScheduleLog) throws Exception {

		template.setDataSource(externalDataSourceConfig.externalDataSource());

		String query = "UPDATE rfid_lb10_if SET lb10_stat = 'D', lb10_upid = ?, lb10_updt = ? WHERE lb10_bxno = ? AND lb10_tryn = 'N'";

        return template.update(query, storageScheduleLog.getUpdUserInfo().getUserId(),
        							  CalendarUtil.convertFormat("yyyyMMddHHmmss", storageScheduleLog.getUpdDate()),
        							  storageScheduleLog.getBoxInfo().getBarcode());
	}

	@Transactional
	@Override
	public List<RfidSd02If> saveReturnSchedule(StorageScheduleLog storageScheduleLog) throws Exception{

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		int lineNum = 1;

		for (StorageScheduleDetailLog detailLog : storageScheduleLog.getStorageScheduleDetailLog()) {

			// TODO subDetailLog 필요여부 확인
			for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){

				RfidSd02IfKey rfidSd02IfKey = new RfidSd02IfKey();

				rfidSd02IfKey.setSd02Orno(storageScheduleLog.getBoxInfo().getBarcode());
				rfidSd02IfKey.setSd02Lino(BigDecimal.valueOf(lineNum).toString());

				RfidSd02If rfidSd02If = new RfidSd02If();

				rfidSd02If.setKey(rfidSd02IfKey);
				rfidSd02If.setSd02Mgdt(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));
				rfidSd02If.setSd02Frcd(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCustomerCode());
				rfidSd02If.setSd02Frco(storageScheduleLog.getBoxInfo().getStartCompanyInfo().getCornerCode());
				rfidSd02If.setSd02Mgsq(BigDecimal.valueOf(0));	//chk
				rfidSd02If.setSd02Mgsr(BigDecimal.valueOf(0));	//chk
				rfidSd02If.setSd02Mggb(storageScheduleLog.getReturnType());
				rfidSd02If.setSd02Tocd(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode());
				rfidSd02If.setSd02Toco(storageScheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode());
				rfidSd02If.setSd02Styl(detailLog.getStyle());
				rfidSd02If.setSd02Stcd(detailLog.getColor() + detailLog.getSize());
				rfidSd02If.setSd02Mtag("N");	//chk
				rfidSd02If.setSd02Mkrn(BigDecimal.valueOf(0));	//chk
				rfidSd02If.setSd02Mqty(BigDecimal.valueOf(1));	//chk
				rfidSd02If.setSd02Bigo("");
				rfidSd02If.setSd02Tryn("R");
				rfidSd02If.setSd02Trdt("");
				rfidSd02If.setSd02Cfyn("N");
				rfidSd02If.setSd02Cfdt("");
				rfidSd02If.setSd02Cfid("");
				rfidSd02If.setSd02Cfqt(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Sbdt("");
				rfidSd02If.setSd02Chsq(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Bpsq(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Engb("R");
				rfidSd02If.setSd02Enid(storageScheduleLog.getRegUserInfo().getUserId());
				rfidSd02If.setSd02Entm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));
				rfidSd02If.setSd02Upid("");
				rfidSd02If.setSd02Uptm("");

				rfidSd02IfList.add(rfidSd02If);
			}

			lineNum += 1;
		}

		return rfidSd02IfList;
	}

	@Transactional
	@Override
	public boolean storeMoveProccess(List<StoreMoveLog> storeMoveLogList) throws Exception{

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		boolean success = false;

		for (StoreMoveLog storeMoveLog : storeMoveLogList){

			if(storeMoveLog.getErpYn().equals("N")){

				rfidSd02IfList.addAll(saveStoreMove(storeMoveLog));

			} else if(storeMoveLog.getErpYn().equals("Y")){

				rfidSd02IfList.addAll(updateStoreMove(storeMoveLog));

			}

		}

		rfidSd02IfRepository.save(rfidSd02IfList);

		success = true;

		return success;
	}

	@Transactional
	public List<RfidSd02If> saveStoreMove(StoreMoveLog storeMoveLog) throws Exception{

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

			for(StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()){

				RfidSd02IfKey rfidSd02IfKey = new RfidSd02IfKey();

				rfidSd02IfKey.setSd02Orno(storeMoveLog.getBoxInfo().getBarcode());
				rfidSd02IfKey.setSd02Lino(subDetailLog.getRfidLineNum().toString());

				RfidSd02If rfidSd02If = new RfidSd02If();

				rfidSd02If.setKey(rfidSd02IfKey);
				rfidSd02If.setSd02Mgdt(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));
				rfidSd02If.setSd02Frcd(storeMoveLog.getBoxInfo().getStartCompanyInfo().getCustomerCode());
				rfidSd02If.setSd02Frco(storeMoveLog.getBoxInfo().getStartCompanyInfo().getCornerCode());
				rfidSd02If.setSd02Mgsq(BigDecimal.valueOf(145));	//chk
				rfidSd02If.setSd02Mgsr(BigDecimal.valueOf(1));	//chk
				rfidSd02If.setSd02Mggb(storeMoveLog.getReturnType());
				rfidSd02If.setSd02Tocd(storeMoveLog.getBoxInfo().getEndCompanyInfo().getCustomerCode());
				rfidSd02If.setSd02Toco(storeMoveLog.getBoxInfo().getEndCompanyInfo().getCornerCode());
				rfidSd02If.setSd02Styl(detailLog.getStyle());
				rfidSd02If.setSd02Stcd(detailLog.getColor() + detailLog.getSize());
				rfidSd02If.setSd02Mtag("N");	//chk
				rfidSd02If.setSd02Mkrn(BigDecimal.valueOf(96));	//chk
				rfidSd02If.setSd02Mqty(BigDecimal.valueOf(1));	//chk
				rfidSd02If.setSd02Bigo("");
				rfidSd02If.setSd02Tryn("R");
				rfidSd02If.setSd02Trdt("");
				rfidSd02If.setSd02Cfyn("N");
				rfidSd02If.setSd02Cfdt("");
				rfidSd02If.setSd02Cfid("");
				rfidSd02If.setSd02Cfqt(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Sbdt("");
				rfidSd02If.setSd02Chsq(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Bpsq(BigDecimal.valueOf(0));
				rfidSd02If.setSd02Engb("R");
				rfidSd02If.setSd02Enid(storeMoveLog.getRegUserInfo().getUserId());
				rfidSd02If.setSd02Entm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));
				rfidSd02If.setSd02Upid("");
				rfidSd02If.setSd02Uptm("");

				rfidSd02IfList.add(rfidSd02If);
			}
		}

		return rfidSd02IfList;
	}

	@Transactional
	public List<RfidSd02If> updateStoreMove(StoreMoveLog storeMoveLog) throws Exception{

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

			for(StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()){

				RfidSd02IfKey rfidSd02IfKey = new RfidSd02IfKey();

				rfidSd02IfKey.setSd02Orno(storeMoveLog.getBoxInfo().getBarcode());
				rfidSd02IfKey.setSd02Lino(subDetailLog.getRfidLineNum().toString());

				RfidSd02If rfidSd02If = rfidSd02IfRepository.findOne(rfidSd02IfKey);

				if(rfidSd02If == null){
					continue;
				}

//				rfidSd02If.setSd02TrynF("R");
				rfidSd02If.setSd02Upid(storeMoveLog.getUpdUserInfo().getUserId());
				rfidSd02If.setSd02Uptm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));

				rfidSd02IfList.add(rfidSd02If);

			}
		}

		return rfidSd02IfList;
	}

	@Transactional
	@Override
	public boolean completeStoreMove(List<StoreMoveLog> storeMoveLogList) throws Exception{

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		boolean success = false;

		for (StoreMoveLog storeMoveLog : storeMoveLogList){

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

				for(StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()){

					RfidSd02IfKey rfidSd02IfKey = new RfidSd02IfKey();

					rfidSd02IfKey.setSd02Orno(storeMoveLog.getBoxInfo().getBarcode());
					rfidSd02IfKey.setSd02Lino(subDetailLog.getRfidLineNum().toString());

					RfidSd02If rfidSd02If = rfidSd02IfRepository.findOne(rfidSd02IfKey);

					if(rfidSd02If == null){
						return success;
					}

					if(subDetailLog.getStat().equals("1")){
						rfidSd02If.setSd02Cfqt(BigDecimal.valueOf(0));
					}

					rfidSd02If.setSd02Tryn("R");
					rfidSd02If.setSd02Cfyn("Y");
					rfidSd02If.setSd02Cfdt(CalendarUtil.convertFormat("yyyyMMdd", new Date()));

					if(subDetailLog.getStat().equals("2")){

						rfidSd02If.setSd02Cfid(storeMoveLog.getUpdUserInfo().getUserId());
						rfidSd02If.setSd02Cfqt(BigDecimal.valueOf(1));
						rfidSd02If.setSd02Sbdt(CalendarUtil.convertFormat("yyyyMMdd", new Date()));
					}

					rfidSd02If.setSd02Enid(storeMoveLog.getRegUserInfo().getUserId());
					rfidSd02If.setSd02Entm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));
					rfidSd02If.setSd02Upid(storeMoveLog.getUpdUserInfo().getUserId());
					rfidSd02If.setSd02Uptm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));

					rfidSd02IfList.add(rfidSd02If);
				}
			}
		}

		rfidSd02IfRepository.save(rfidSd02IfList);

		success = true;

		return success;
	}

	@Transactional
	@Override
	public boolean disuseStoreMove(List<StoreMoveLog> storeMoveLogList) throws Exception {

		List<RfidSd02If> rfidSd02IfList = new ArrayList<RfidSd02If>();

		boolean success = false;

		for (StoreMoveLog storeMoveLog : storeMoveLogList){

			for (StoreMoveDetailLog detailLog : storeMoveLog.getStoreMoveDetailLog()) {

				for(StoreMoveSubDetailLog subDetailLog : detailLog.getStoreMoveSubDetailLog()){

					RfidSd02IfKey rfidSd02IfKey = new RfidSd02IfKey();

					rfidSd02IfKey.setSd02Orno(storeMoveLog.getBoxInfo().getBarcode());
					rfidSd02IfKey.setSd02Lino(subDetailLog.getRfidLineNum().toString());

					RfidSd02If rfidSd02If = rfidSd02IfRepository.findOne(rfidSd02IfKey);

					if(rfidSd02If == null){
						return success;
					}

					rfidSd02If.setSd02Cfqt(BigDecimal.valueOf(0));
					rfidSd02If.setSd02Upid(storeMoveLog.getUpdUserInfo().getUserId());
					rfidSd02If.setSd02Uptm(CalendarUtil.convertFormat("yyyyMMddHHmmss", new Date()));

					rfidSd02IfList.add(rfidSd02If);
				}
			}
		}

		rfidSd02IfRepository.save(rfidSd02IfList);

		success = true;

		return success;
	}

	@Transactional
	@Override
	public RfidLe01If saveReleaseComplete(ErpStoreSchedule erpStoreSchedule, Long releaseAmount, Long sortingAmount) throws Exception {

		RfidLe01If rfidLe01If = new RfidLe01If();

		RfidLe01IfKey rfidLe01IfKey = new RfidLe01IfKey();
		rfidLe01IfKey.setLe01Chdt(CalendarUtil.convertFormat("yyyyMMdd"));
		rfidLe01IfKey.setLe01Mgdt(erpStoreSchedule.getCompleteDate());
		rfidLe01IfKey.setLe01Mggb(erpStoreSchedule.getCompleteType());
		rfidLe01IfKey.setLe01Mgsq(erpStoreSchedule.getCompleteSeq());
		rfidLe01IfKey.setLe01Seqn(erpStoreSchedule.getCompleteIfSeq());
		rfidLe01IfKey.setLe01Mjcd(erpStoreSchedule.getEndCompanyInfo().getCustomerCode());
		rfidLe01IfKey.setLe01Mjco(erpStoreSchedule.getEndCompanyInfo().getCornerCode());

		rfidLe01If.setKey(rfidLe01IfKey);
		rfidLe01If.setLe01Styl(erpStoreSchedule.getStyle());
		rfidLe01If.setLe01Stcd(erpStoreSchedule.getAnotherStyle());

		// 출고 수량 입력
		rfidLe01If.setLe01Chqt(new BigDecimal(releaseAmount));
		// sorting 수량 입력
		rfidLe01If.setLe01Sqty(new BigDecimal(sortingAmount));

		rfidLe01If.setLe01Chsq(new BigDecimal(0));
		rfidLe01If.setLe01Chsr(new BigDecimal(0));
		rfidLe01If.setLe01Emgb("");
		rfidLe01If.setLe01Crg1("");
		rfidLe01If.setLe01Crg2("");

		rfidLe01If.setLe01Tryn("N");
		rfidLe01If.setLe01Time(CalendarUtil.convertFormat("HHmm"));
		rfidLe01If.setLe01Chyn("N");
		rfidLe01If.setLe01Bigo("");
		rfidLe01If.setLe01Endt(new Date());

		return rfidLe01If;
	}

	@Transactional
	@Override
	public boolean saveBartag(List<BartagMaster> bartagList) throws Exception {

		boolean success = false;

		List<RfidAc18If> rfidAc18IfList = new ArrayList<RfidAc18If>();

		for(BartagMaster bartagMaster : bartagList){

			RfidAc18IfKey key = new RfidAc18IfKey();
			RfidAc18If rfidAc18If = new RfidAc18If();

			key.setAc18Crdt(bartagMaster.getCreateDate());
			key.setAc18Crsq(new BigDecimal(bartagMaster.getSeq()));
			key.setAc18Crno(new BigDecimal(bartagMaster.getLineSeq()));

			rfidAc18If.setKey(key);
			rfidAc18If.setAc18Styl(bartagMaster.getStyle());
			rfidAc18If.setAc18It06(bartagMaster.getColor());
			rfidAc18If.setAc18It07(bartagMaster.getSize());
			rfidAc18If.setAc18Jjch(bartagMaster.getOrderDegree());
			rfidAc18If.setAc18Stcd(bartagMaster.getAnnotherStyle());
			rfidAc18If.setAc18Erpk(bartagMaster.getErpKey());
			rfidAc18If.setAc18Prod(bartagMaster.getProductionCompanyInfo().getCustomerCode());
			rfidAc18If.setAc18Cor1(bartagMaster.getProductionCompanyInfo().getCornerCode());
			rfidAc18If.setAc18Jgrs(bartagMaster.getDetailProductionCompanyName());
			rfidAc18If.setAc18Btqt(new BigDecimal(bartagMaster.getAmount()));
			rfidAc18If.setAc18Time(CalendarUtil.convertFormat("HHmmssSSSS", bartagMaster.getRegDate()));
			rfidAc18If.setAc18Tryn("R");
			rfidAc18If.setAc18Trdt(new Date());
			rfidAc18If.setAc18Data(bartagMaster.getData());
			rfidAc18If.setAc18Snyn("N");
			rfidAc18If.setAc18Pryn("N");

			rfidAc18IfList.add(rfidAc18If);
		}

		rfidAc18IfRepository.save(rfidAc18IfList);

		success = true;

		return success;
	}

	@Override
	//real 반품 실적반영
	public List<RfidLf01If> saveStorageListReturnComplete(StorageScheduleLog scheduleLog) throws Exception {
		List<RfidLf01If> rfidLf01IfList = new ArrayList<RfidLf01If>();

		List<StorageScheduleLog> storageList = new ArrayList<StorageScheduleLog>();
		storageList.add(scheduleLog);

		Set<StorageScheduleDetailLogModel> tempErpDetailList = storageScheduleLogService.generateScheduleLog(scheduleLog, "ERP");

		for (StorageScheduleDetailLogModel detailLog : tempErpDetailList) {

			for (StorageScheduleLog schedule : storageList) {
				RfidLf01IfKey rfidLf01IfKey = new RfidLf01IfKey();
				rfidLf01IfKey.setLf01Bpdt(CalendarUtil.convertFormat("yyyyMMdd"));
				rfidLf01IfKey.setLf01Orno(schedule.getBoxInfo().getBarcode());
				rfidLf01IfKey.setLf01Lino(detailLog.getOrderDegree());

				RfidLf01If rfidLf01If = new RfidLf01If();
				rfidLf01If.setKey(rfidLf01IfKey);
				rfidLf01If.setLf01Mgdt(schedule.getCreateDate());
				rfidLf01If.setLf01Mjcd(schedule.getBoxInfo().getStartCompanyInfo().getCustomerCode());
				rfidLf01If.setLf01Mjco(schedule.getBoxInfo().getStartCompanyInfo().getCornerCode());

				ErpStoreReturnSchedule esReturn = erpStoreReturnScheduleRepository.findByReturnOrderNoAndReturnOrderLiNo(schedule.getBoxInfo().getBarcode(), detailLog.getOrderDegree());

				rfidLf01If.setLf01Mgsq(esReturn.getReturnReleaseSeq());
				rfidLf01If.setLf01Mgsr(esReturn.getReturnOrderSerial());
				rfidLf01If.setLf01Mggb(esReturn.getReturnType());
				rfidLf01If.setLf01Styl(detailLog.getStyle());
				rfidLf01If.setLf01Stcd(detailLog.getColor() + detailLog.getSize());
				rfidLf01If.setLf01Mqty(BigDecimal.valueOf(detailLog.getAmount()));
				rfidLf01If.setLf01Bpqt(BigDecimal.valueOf(detailLog.getAmount()));

				rfidLf01IfList.add(rfidLf01If);

			}

		}

		return rfidLf01IfList;
	}

	@Transactional
	@Override
	public Map<String, Object> storeStorageFunctionCall(String erpCreateDate, Long releaseSeq, CompanyInfo companyInfo, String barcode) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<>();

		template = new JdbcTemplate(externalDataSourceConfig.externalDataSource());
		template.setResultsMapCaseInsensitive(true);

		SimpleJdbcCall call = new SimpleJdbcCall(template).withFunctionName("UP_RFID_PDA_LE10");

		SqlParameterSource in = new MapSqlParameterSource().addValue("as_chdt", erpCreateDate, Types.NVARCHAR)
															.addValue("an_chsq", new BigDecimal(releaseSeq), Types.NUMERIC)
															.addValue("as_gras", companyInfo.getCustomerCode(), Types.NVARCHAR)
															.addValue("as_grco", companyInfo.getCornerCode(), Types.NVARCHAR)
															.addValue("as_bxno", barcode, Types.NVARCHAR)
															.addValue("al_ecod", Types.INTEGER)
															.addValue("as_emsg", Types.NVARCHAR);

		Map out = call.execute(in);

		obj.put("resultCode", out.get("al_ecod"));
		obj.put("resultMessage", out.get("as_emsg"));

		return obj;
	}

	@Transactional
	@Override
	public List<RfidMd14RtIf> saveStoreInventory(List<InventoryScheduleHeader> headerList, UserInfo userInfo) throws Exception {

		List<RfidMd14RtIf> rfidMd14RtIfList = new ArrayList<>();

		// Header 리스트 loop
		for(InventoryScheduleHeader header : headerList){

			// ERP 삭제처리
			rfidMd14RtIfRepository.batchDelete(header.getCompanyInfo().getCustomerCode(), header.getCompanyInfo().getCornerCode(), header.getCreateDate());

			// style 리스트 loop
			for(InventoryScheduleStyle style : header.getStyleList()){
				// RFID 부착시 시리얼별로 저장
				if(style.getRfidYn().equals("Y")){
					for(InventoryScheduleTag tag : style.getRfidTagList()){
						rfidMd14RtIfList.add(new RfidMd14RtIf(header, style, tag, userInfo));
					}

				// RFID 미부착시 스타일별로 저장
				} else {
					rfidMd14RtIfList.add(new RfidMd14RtIf(header, style, userInfo));
				}
			}
		}
		return rfidMd14RtIfRepository.save(rfidMd14RtIfList);
	}

	@Transactional
	@Override
	public Map<String, Object> functionTestCall() throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		// TODO Auto-generated method stub
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		StoredProcedureQuery proc = em.createStoredProcedureQuery("UP_RFID_PDA_TEST");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.OUT);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		try {

			transaction.begin();

			//erp 프로시저  호출
			proc.execute();

			obj.put("resultCode", proc.getOutputParameterValue(1).toString());
			obj.put("resultMessage", proc.getOutputParameterValue(2).toString());

			transaction.commit();
			em.close();

		} catch(Exception e) {

			e.printStackTrace();
			transaction.rollback();
			em.close();
		}

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> functionTestCallJdbc(String name) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<>();

		template = new JdbcTemplate(externalDataSourceConfig.externalDataSource());
		template.setResultsMapCaseInsensitive(true);

		SimpleJdbcCall call = new SimpleJdbcCall(template).withFunctionName("UP_RFID_PDA_TEST");

		SqlParameterSource in = new MapSqlParameterSource().addValue("as_name", name, Types.NVARCHAR)
															.addValue("al_ecod", Types.INTEGER)
															.addValue("as_emsg", Types.NVARCHAR);

		Map out = call.execute(in);

		obj.put("resultCode", out.get("al_ecod"));
		obj.put("resultMessage", out.get("as_emsg"));

		return obj;
	}
}
