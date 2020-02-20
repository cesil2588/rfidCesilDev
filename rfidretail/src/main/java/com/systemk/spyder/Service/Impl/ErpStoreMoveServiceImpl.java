package com.systemk.spyder.Service.Impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.ErpWorkBean;
import com.systemk.spyder.Dto.Request.StoreMoveDetailSendBean;
import com.systemk.spyder.Dto.Request.StoreMoveSendBean;
import com.systemk.spyder.Dto.Response.ErpReturnWorkBoxResult;
import com.systemk.spyder.Dto.Response.ProcedureResult;
import com.systemk.spyder.Entity.External.RfidSd01If;
import com.systemk.spyder.Entity.External.RfidSd06RtIf;
import com.systemk.spyder.Entity.External.RfidSd15If;
import com.systemk.spyder.Entity.External.Key.RfidSd01IfKey;
import com.systemk.spyder.Entity.External.Key.RfidSd06RtIfKey;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreMove;
import com.systemk.spyder.Entity.Main.ErpStoreMoveDetail;
import com.systemk.spyder.Entity.Main.ErpStoreMoveTag;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSubInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnTag;
import com.systemk.spyder.Repository.External.RfidSd01IfRepository;
import com.systemk.spyder.Repository.External.RfidSd06RtIfRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveDetailRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveTagRepository;
import com.systemk.spyder.Repository.Main.Specification.ErpStoreMoveSpecification;
import com.systemk.spyder.Service.ErpStoreMoveService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.ResultUtil;

@Service
public class ErpStoreMoveServiceImpl implements ErpStoreMoveService {

	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private ErpStoreMoveRepository erpStoreMoveRepository;
	
	@Autowired
	private ErpStoreMoveDetailRepository erpStoreMoveDetailRepository;
	
	@Autowired
	private ErpStoreMoveTagRepository erpStoreMoveTagRepository;
	
	@Autowired
	private RfidSd06RtIfRepository rfidSd06RtIfRepository;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;
	
	@Autowired
	private RfidSd01IfRepository rfidSd01IfRepository;
	
	@Autowired
	@Qualifier("externalEntityManager")
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private JdbcTemplate template;
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findAll(String startDate, String endDate, String completeYn, String customerCode, String moveType, String moveKind)
			throws Exception {
		Date fromDate = CalendarUtil.convertStartDate(startDate);
		Date toDate = CalendarUtil.convertEndDate(endDate);

		if(customerCode.equals("")) {
			return ResultUtil.setCommonResult("E", "매장 코드가 필요합니다");
		}

		Specifications<ErpStoreMove> specifications = Specifications.where(ErpStoreMoveSpecification.regDateBetween(fromDate, toDate));

		CompanyInfo companyInfo = companyInfoRepository.findByCustomerCode(customerCode);
		if(companyInfo==null) {
			return ResultUtil.setCommonResult("E", "사용자에 해당하는 매장 정보가 없습니다");
		} else {
			String cornerCode = companyInfo.getCornerCode();
			if(moveKind.equals("1"))
				specifications = specifications.and(Specifications.where(ErpStoreMoveSpecification.fromCustomerCode(customerCode)))
					.and(Specifications.where(ErpStoreMoveSpecification.fromCornerCode(cornerCode)));
			else
				specifications = specifications.and(Specifications.where(ErpStoreMoveSpecification.toCustomerCode(customerCode)))
				.and(Specifications.where(ErpStoreMoveSpecification.toCornerCode(cornerCode)));
		}
		
		if(moveType!=null && !moveType.equals("")) {	
			if(moveType.equals("1")) {
				specifications = specifications.and(Specifications.where(ErpStoreMoveSpecification.moveType(moveType)));
			}else {
				specifications = specifications.and(Specifications.where(ErpStoreMoveSpecification.NotMoveType("1")));
			}
		}

		if(completeYn!=null && !completeYn.equals("all")) {
			specifications = specifications.and(Specifications.where(ErpStoreMoveSpecification.completeYn(completeYn)));
		}
				
		List<ErpStoreMove> erpStoreMove = erpStoreMoveRepository.findAll(specifications);
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, erpStoreMove);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findDetail(Long moveSeq) throws Exception {
		Map<String, Object> workResult = new HashMap<String, Object>();

		List<ErpStoreMoveDetail> moveStyle = erpStoreMoveDetailRepository.findByMoveSeq(moveSeq);

		List<ErpReturnWorkBoxResult> workBoxList = new ArrayList<ErpReturnWorkBoxResult>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mainDataSourceConfig.mainDataSource());
		String query = "SELECT COUNT(work_box_num) as sumByBox, work_box_num, complete_yn, erp_return_invoice_num FROM erp_store_move_tag WHERE move_detail_seq ";
		query += ("IN (SELECT move_detail_seq FROM erp_store_move_detail WHERE move_seq = " + moveSeq + ") GROUP BY work_box_num, complete_yn, erp_return_invoice_num");
		List<Map<String, Object>> workBoxListResult = jdbcTemplate.queryForList(query);
		for(Map<String,Object> box : workBoxListResult) {
			ErpReturnWorkBoxResult boxResult = new ErpReturnWorkBoxResult();
			boxResult.setSumPerBox(Integer.parseInt(box.get("sumByBox").toString()));
			boxResult.setWorkBoxNum(Long.parseLong(box.get("work_box_num").toString()));
			boxResult.setCompleteYn(box.get("complete_yn").toString());
			boxResult.setErpInvoiceNum(box.get("erp_return_invoice_num")==null ? "" : box.get("erp_return_invoice_num").toString());
			workBoxList.add(boxResult);
		}
		
		ErpStoreMove moveInfo = erpStoreMoveRepository.findByMoveSeq(moveSeq);
		//매장이동 정보 목록
		workResult.put("moveInfo", moveInfo);
		//매장이동 스타일 목록
		workResult.put("moveStyle", moveStyle);
		//매장이동 작업 목록
		workResult.put("moveJob", workBoxList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, workResult);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> getBoxDetailInfo(Long boxNum) throws Exception {
		List<ErpStoreMoveDetail> boxDetailList = erpStoreMoveDetailRepository.findByWorkBoxNum(boxNum);
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, boxDetailList);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> getMaxBoxNo() throws Exception {

		Map<String,Object> result = new HashMap<String,Object>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mainDataSourceConfig.mainDataSource());
		Long maxNo = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(work_box_num),0) +1 FROM erp_store_move_tag",Long.class);

		result.put("boxNo", maxNo);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, result);
	}

	@Transactional
	@Override
	public Map<String, Object> saveWork(List<ErpWorkBean> erpWorkBean) throws Exception{
		
		if(erpWorkBean == null || erpWorkBean.size()==0) {
			return ResultUtil.setCommonResult("E", "저장할 작업 내용이 존재하지 않습니다");
		}

		List<ErpStoreMoveTag> returnTagList = new ArrayList<ErpStoreMoveTag>();
		Set<HashMap<String, Object>> usedTagList = new HashSet<HashMap<String,Object>>();
		
		for(ErpWorkBean bean : erpWorkBean) {

			int chkBoxNum = erpStoreMoveTagRepository.countByWorkBoxNum(bean.getWorkBoxNum());
			
			Long anotherSubSeq = 0L;

			if(chkBoxNum > 0) {
				return ResultUtil.setCommonResult("E", "이미 작업에 사용된 박스번호 입니다");
			}
			
			if(bean.getAnotherYn().equals("Y")) {
				//신규 스타일이 또 다시 읽혔을경우는 수량만 업데이트
				List<ErpStoreMoveDetail> usedAnother = erpStoreMoveDetailRepository.findByMoveStyleAndMoveColorAndMoveSize(bean.getStyle(), bean.getColor(), bean.getSize());
				if(!usedAnother.isEmpty()) {
					for(int i=0;i<usedAnother.size();i++) {
						ErpStoreReturnSubInfo subInfo = new ErpStoreReturnSubInfo();
						subInfo.setReturnInfoSubSeq(usedAnother.get(i).getMoveDetailSeq());
						subInfo.setExecuteAmount(usedAnother.get(i).getExecuteAmount());
						erpStoreMoveDetailRepository.save(usedAnother);
						anotherSubSeq = subInfo.getReturnInfoSubSeq();
					}
				}else {
					ErpStoreMoveDetail emd = new ErpStoreMoveDetail();
					emd.setMoveSeq(bean.getSeq());
					emd.setMoveStyle(bean.getStyle());
					emd.setMoveColor(bean.getColor());
					emd.setMoveSize(bean.getSize());
					emd.setOrderAmount(0L);
					emd.setExecuteAmount(bean.getAmount());
					emd.setConfirmAmount(0L);
					emd.setRfidYn(bean.getRfidYn());
					emd.setAnotherYn("Y");
					erpStoreMoveDetailRepository.save(emd);
					//sub_seq받아가기
					anotherSubSeq = emd.getMoveDetailSeq();
				}
			}

			Long addAmount = bean.getAmount();
			for(int i=0;i<addAmount;i++) {
				ErpStoreMoveTag et = new ErpStoreMoveTag();
				if(bean.getAnotherYn().equals("Y"))
					et.setMoveDetailSeq(anotherSubSeq);
				else
					et.setMoveDetailSeq(bean.getSubSeq());
				if(bean.getRfidTagList()!=null && bean.getRfidTagList().size()>0) {
					ErpStoreMoveTag chkExistTag = erpStoreMoveTagRepository.findByRfidTag(bean.getRfidTagList().get(i).get("rfidTag").toString());
					if(chkExistTag==null) {
						et.setRfidTag(bean.getRfidTagList().get(i).get("rfidTag").toString());
					}else {
						HashMap<String, Object> usedTag = new HashMap<String, Object>();
						ErpStoreMoveDetail subInfo = erpStoreMoveDetailRepository.findByMoveDetailSeq(chkExistTag.getMoveDetailSeq());
						usedTag.put("style", subInfo.getMoveStyle());
						usedTag.put("color", subInfo.getMoveColor());
						usedTag.put("size", subInfo.getMoveSize());
						usedTag.put("rfidSeq", chkExistTag.getRfidTag().substring(27));
						usedTagList.add(usedTag);
					}
				}
					
				et.setWorkBoxNum(bean.getWorkBoxNum());
				et.setCompleteYn("N");
				et.setUpdUserSeq(bean.getUserSeq());
				returnTagList.add(et);
			}
			
				//지시 이행수량 업데이트(erp_store_return_sub_info)
				erpStoreMoveDetailRepository.updateExecuteAmount(bean.getAmount(), bean.getSubSeq());
				
				//지시 테이블 시퀀스 추출
				Long subSeq = (bean.getAnotherYn().equals("Y")) ? anotherSubSeq : bean.getSubSeq();
				Long moveSeq = erpStoreMoveDetailRepository.findMoveSeqByDetailSeq(subSeq);
				
				//지시 테이블 이행수량 업데이트
				erpStoreMoveRepository.updateExecuteAmount(bean.getAmount(), moveSeq);
			

		}
		
		if(usedTagList.size()>0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtil.setCommonResult("E", "이미 작업에 사용된 태그 정보가 존재합니다", usedTagList);
			
		}
		erpStoreMoveTagRepository.save(returnTagList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
		
	}

	@Transactional
	@Override
	public Map<String, Object> DeleteWork(List<ErpWorkBean> erpWorkBean) throws Exception {
		
		if(erpWorkBean == null || erpWorkBean.size()==0) {
			return ResultUtil.setCommonResult("E", "해당 박스의 작업 정보가 존재하지 않습니다");
		}

		for(ErpWorkBean bean : erpWorkBean) {

			List<ErpStoreMoveTag> tagList = erpStoreMoveTagRepository.findByWorkBoxNum(bean.getWorkBoxNum());
			
			int chkCnt = erpStoreMoveTagRepository.chkConfirmYnByBoxNum(bean.getWorkBoxNum());

			if(chkCnt > 0) {

				return ResultUtil.setCommonResult("E", "확정된 작업은 삭제할 수 없습니다");

			}else {
				
				for(int i=0;i<tagList.size();i++) {
					Long subSeq = tagList.get(i).getMoveDetailSeq();
					
						for(ErpStoreMoveTag et : tagList) {
							erpStoreMoveTagRepository.delete(et);
						}
						//지시 이행 삭제 수량 업데이트(erp_store_move_detail)
						erpStoreMoveDetailRepository.updateDelExecuteAmount(1, subSeq);
						erpStoreMoveDetailRepository.flush();	
						
						//지시 테이블 시퀀스 추출
						Long moveSeq = erpStoreMoveDetailRepository.findMoveSeqByDetailSeq(subSeq);
						//지시 이행 삭제 수량 업데이트(erp_store_move)
						erpStoreMoveRepository.updateDelExecuteAmount(1, moveSeq);
					}
				}
			
			erpStoreMoveDetailRepository.deleteAnotherYN();
			
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
		
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> completeWork(ErpWorkBean erpWorkBean) throws Exception {
		
		Long boxNum = erpWorkBean.getWorkBoxNum();
		List<ErpStoreMoveTag> tagList = erpStoreMoveTagRepository.findByWorkBoxNum(boxNum);
		List<ErpStoreMoveDetail> detailList = new ArrayList<ErpStoreMoveDetail>();
		//작업자 정보 추출... 해당 매장에 대한 작업만 이루어지므로  tagList의 모든 user_seq는 같다고 봄
		Long userSeq = tagList.get(0).getUpdUserSeq();
		
		if(tagList==null || tagList.size()==0) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_NOT_FIND_BOXNUM_MESSAGE);
		}else {
			//확정되어진 박스번호인지 확인
			int chkConfirm = erpStoreMoveTagRepository.chkConfirmYnByBoxNum(boxNum);
			if(chkConfirm!=0) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_COMPLETED_WORK_MESSAGE);
			}else {

				for(ErpStoreMoveTag et : tagList) {
					
					erpStoreMoveTagRepository.updateCompleteYn(et.getMoveTagSeq());
					//지시 확정 수량 업데이트(erp_store_move_detail)
					erpStoreMoveDetailRepository.updateConfirmAmount(1, et.getMoveDetailSeq());
					//지시 테이블 시퀀스 추출
					Long moveSeq = erpStoreMoveDetailRepository.findMoveSeqByDetailSeq(et.getMoveDetailSeq());
					//지시 확정 수량 업데이트(erp_store_move)
					erpStoreMoveRepository.updateConfirmAmount(1, moveSeq);
					
					//Erp I/F에서 활용할 ErpStoreMoveDetail생성
					ErpStoreMoveDetail moveDetail = erpStoreMoveDetailRepository.findByMoveDetailSeq(et.getMoveDetailSeq());
					detailList.add(moveDetail);
				}
			}

			erpStoreMoveTagRepository.flush();

			List<ErpStoreMoveDetail> subList = erpStoreMoveDetailRepository.findByWorkBoxNum(boxNum);
			for(ErpStoreMoveDetail sub : subList) {

				int chkCount = erpStoreMoveTagRepository.chkCompleteYn(sub.getMoveSeq());
			
				//지시에서 내려진 상세 시퀀스가 모두 작업이 되었는지 확인후 완료여부 처리
				if(chkCount==0)
					erpStoreMoveRepository.updateConfirmYn(sub.getMoveSeq());
			}

			//ERP I/F 호출
			updateErpMoveOrder(detailList, boxNum);
		}
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> saveOrder(StoreMoveSendBean moveBean) throws Exception {
		
		if(moveBean==null) {
			return ResultUtil.setCommonResult("E", "등록할 정보가 존재하지 않습니다");
		}
		
		Long orderAmount = 0L;
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		List<ErpStoreMoveDetail> moveDetailList = new ArrayList<ErpStoreMoveDetail>();
		
		ErpStoreMove esMove = new ErpStoreMove();
		esMove.setOrderRegDate(sFormat.format(new Date()));
		esMove.setOrderSeq(0L);
		esMove.setOrderSerial(0L);
		esMove.setFromCustomerCode(moveBean.getFromCustomerCode());
		esMove.setFromCornerCode(moveBean.getFromCornerCode());
		esMove.setToCustomerCode(moveBean.getToCustomerCode());
		esMove.setToCornerCode(moveBean.getToCornerCode());
		esMove.setFromCompleteYn("N");
		esMove.setToCompleteYn("N");
		esMove.setOrderAmount(orderAmount);
		esMove.setExecuteAmount(0L);
		esMove.setConfirmAmount(0L);
		esMove.setMoveType("3");
		
		erpStoreMoveRepository.save(esMove);
		//생성되는 move_seq 받기
		Long moveSeq = esMove.getMoveSeq();
		
		if(moveBean.getDetailBeanList().size()==0) {
			return ResultUtil.setCommonResult("E", "등록할 스타일 정보가 존재하지 않습니다");
		}
		
		for(StoreMoveDetailSendBean bean : moveBean.getDetailBeanList()) {
			ErpStoreMoveDetail moveDetail = new ErpStoreMoveDetail();
			moveDetail.setMoveSeq(moveSeq);
			moveDetail.setMoveStyle(bean.getStyle());
			moveDetail.setMoveColor(bean.getColor());
			moveDetail.setMoveSize(bean.getSize());
			moveDetail.setOrderAmount(bean.getOrderAmount());
			moveDetail.setExecuteAmount(0L); 
			moveDetail.setConfirmAmount(0L);
			moveDetail.setRfidYn(bean.getRfidYn());
			moveDetail.setAnotherYn("N");
			moveDetail.setRegUserSeq(bean.getRegUserSeq());
			orderAmount += bean.getOrderAmount();
			moveDetailList.add(moveDetail);
		}
		
		erpStoreMoveRepository.updateOrderAmount(moveSeq, orderAmount);
		erpStoreMoveDetailRepository.save(moveDetailList);
		
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> deleteOrder(List<ErpWorkBean> erpWorkBean) throws Exception {
		
		if(erpWorkBean==null || erpWorkBean.isEmpty()) {
			return ResultUtil.setCommonResult("E", "삭제할 매장 등록 정보 번호가 필요합니다");
		}
		
		for(ErpWorkBean bean : erpWorkBean) {
			
			ErpStoreMove storeMove = erpStoreMoveRepository.findByMoveSeq(bean.getSeq());
			
			if(storeMove.getMoveType().equals("2")) {
				return ResultUtil.setCommonResult("E", "확정된 매장등록 정보는 삭제할 수 없습니다");
			}
			
			List<ErpStoreMoveDetail> moveDetailList = erpStoreMoveDetailRepository.findByMoveSeq(bean.getSeq());
			
			for(ErpStoreMoveDetail detail : moveDetailList) {
				erpStoreMoveDetailRepository.delete(detail);
			}
			
			erpStoreMoveRepository.delete(storeMove);
			
		}
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> completeOrder(ErpWorkBean erpWorkBean) throws Exception {
		
		Long moveSeq = erpWorkBean.getSeq();
		ErpStoreMove storeMove = erpStoreMoveRepository.findByMoveSeq(moveSeq);
		storeMove.setMoveType("2");
		erpStoreMoveRepository.save(storeMove);
		
		SimpleDateFormat transformat = new SimpleDateFormat("yyyyMMdd");
		
		//ERP I/F(rfid_sd01_if테이블 insert, UP_RFID_PDA_SD01프로시저 호출)
		List<ErpStoreMoveDetail> storeMoveDetailList = erpStoreMoveDetailRepository.findByMoveSeq(moveSeq);
		Long completeSerial = 0L;
		List<RfidSd01If> rfidSd01IfList = new ArrayList<RfidSd01If>();
		for(ErpStoreMoveDetail moveDetail : storeMoveDetailList) {
			RfidSd01IfKey sd01IfKey = new RfidSd01IfKey();
			sd01IfKey.setSd01Endt(storeMove.getOrderRegDate());
			sd01IfKey.setSd01Frcd(storeMove.getFromCustomerCode());
			sd01IfKey.setSd01Frco(storeMove.getFromCornerCode());
			sd01IfKey.setSd01Ensq(storeMove.getOrderSeq());
			sd01IfKey.setSd01Ensr(storeMove.getOrderSerial());
			RfidSd01If sd01If = new RfidSd01If();
			completeSerial++;
			sd01If.setKey(sd01IfKey);
			sd01If.setSd01Mggb("15");
			sd01If.setSd01Tocd(storeMove.getToCustomerCode());
			sd01If.setSd01Toco(storeMove.getToCornerCode());
			sd01If.setSd01Styl(moveDetail.getMoveStyle());
			sd01If.setSd01Stcd(moveDetail.getMoveColor()+moveDetail.getMoveSize());
			sd01If.setSd01Mqty(storeMove.getOrderAmount());
			sd01If.setSd01Mgdt(storeMove.getFromCompleteDate()==null ? "" : transformat.format(storeMove.getFromCompleteDate()));
			sd01If.setSd01Mgsq(completeSerial);
			sd01If.setSd01Enid(moveDetail.getRegUserSeq()+"");
			sd01If.setSd01Entm(transformat.format(new Date()));
			rfidSd01IfList.add(sd01If);
		}
		
		rfidSd01IfRepository.save(rfidSd01IfList);
		rfidSd01IfRepository.flush();
		try {
			template = new JdbcTemplate(externalDataSourceConfig.externalDataSource());
			template.setResultsMapCaseInsensitive(true);
			
			SimpleJdbcCall call = new SimpleJdbcCall(template).withFunctionName("UP_RFID_PDA_SD01");
			
				RfidSd01If sd01If = rfidSd01IfList.get(0);
				SqlParameterSource in = new MapSqlParameterSource().addValue("as_endt", sd01If.getKey().getSd01Endt(), Types.NVARCHAR)
																.addValue("an_ensq", sd01If.getKey().getSd01Ensq(), Types.NUMERIC)
																.addValue("as_frcd", sd01If.getKey().getSd01Frcd(), Types.NVARCHAR)
																.addValue("as_frco", sd01If.getKey().getSd01Frco(), Types.NVARCHAR)
																.addValue("as_mgdt", Types.NVARCHAR)
																.addValue("an_mgsq", Types.NUMERIC)
																.addValue("al_ecod", Types.INTEGER)
																.addValue("as_emsg", Types.NVARCHAR);
				Map out = call.execute(in);
				storeMove.setOrderSeq(Long.valueOf(out.get("an_mgsq").toString()));
				erpStoreMoveRepository.save(storeMove);
				
				if(Integer.parseInt(out.get("al_ecod").toString())!=0)	throw new RuntimeException();
		}catch(Exception ex) {
			
			ex.printStackTrace();
			throw new RuntimeException(ex);
			
		}
		
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}
	
	@Transactional
	public void updateErpMoveOrder(List<ErpStoreMoveDetail> moveDetailList, Long boxNum) {
		
		List<RfidSd06RtIf> sd06rtList = new ArrayList<RfidSd06RtIf>();
		ErpStoreMove storeMove = erpStoreMoveRepository.findByMoveSeq(moveDetailList.get(0).getMoveSeq());
		for(ErpStoreMoveDetail ed : moveDetailList) {
			
			RfidSd06RtIfKey sd06rtKey = new RfidSd06RtIfKey();
			sd06rtKey.setSd06RtIddt(storeMove.getOrderRegDate());
			sd06rtKey.setSd06RtIdsq(storeMove.getOrderSeq());
			sd06rtKey.setSd06RtFrcd(storeMove.getFromCustomerCode());
			sd06rtKey.setSd06RtFrco(storeMove.getFromCornerCode());
			sd06rtKey.setSd06RtStyl(ed.getMoveStyle());
			sd06rtKey.setSd06RtStcd(ed.getMoveColor()+ed.getMoveSize());
			sd06rtKey.setSd06RtBxno(boxNum);
			RfidSd06RtIf sd06rt = new RfidSd06RtIf();
			sd06rt.setKey(sd06rtKey);
			sd06rt.setSd06rtBqty(ed.getExecuteAmount());
			sd06rt.setSd06rtTocd(storeMove.getToCustomerCode());
			sd06rt.setSd06rtToco(storeMove.getToCornerCode());
			sd06rt.setSd06rtEndt(new Date());
			sd06rt.setSd06rtEnid(ed.getRegUserSeq()+"");
			sd06rtList.add(sd06rt);
		}
		
		rfidSd06RtIfRepository.save(sd06rtList);
		rfidSd06RtIfRepository.flush();
		
		try {
			template = new JdbcTemplate(externalDataSourceConfig.externalDataSource());
			template.setResultsMapCaseInsensitive(true);
			
			SimpleJdbcCall call = new SimpleJdbcCall(template).withFunctionName("UP_RFID_PDA_SD06");
			
			RfidSd06RtIf sd06rtIf = sd06rtList.get(0);
				SqlParameterSource in = new MapSqlParameterSource().addValue("as_iddt", sd06rtIf.getKey().getSd06RtIddt(), Types.NVARCHAR)
																.addValue("an_idsq", sd06rtIf.getKey().getSd06RtIdsq(), Types.NUMERIC)
																.addValue("as_frcd", sd06rtIf.getKey().getSd06RtFrcd(), Types.NVARCHAR)
																.addValue("as_frco", sd06rtIf.getKey().getSd06RtFrco(), Types.NVARCHAR)
																.addValue("an_bxno", sd06rtIf.getKey().getSd06RtBxno(), Types.NUMERIC)
																.addValue("as_mgdt", Types.NVARCHAR)
																.addValue("an_mgsq", Types.NUMERIC)
																.addValue("al_ecod", Types.INTEGER)
																.addValue("as_emsg", Types.NVARCHAR);
				Map out = call.execute(in);
				storeMove.setOrderSeq(Long.valueOf(out.get("an_mgsq").toString()));
				erpStoreMoveRepository.save(storeMove);
				
				if(Integer.parseInt(out.get("al_ecod").toString())!=0)	throw new RuntimeException();
				
		}catch(Exception ex) {
			
			ex.printStackTrace();
			throw new RuntimeException(ex);
			
		}
	}

}
