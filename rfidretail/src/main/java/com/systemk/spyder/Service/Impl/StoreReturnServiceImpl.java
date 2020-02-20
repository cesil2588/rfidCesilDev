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
import javax.persistence.EntityTransaction;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.systemk.spyder.Dto.Request.ErpReturnWorkBean;
import com.systemk.spyder.Dto.Response.ErpReturnWorkBoxResult;
import com.systemk.spyder.Entity.External.RfidSd15If;
import com.systemk.spyder.Entity.External.Key.RfidSd15IfKey;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSubInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnTag;
import com.systemk.spyder.Repository.External.RfidSd15IfRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnSubInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnTagRepository;
import com.systemk.spyder.Repository.Main.Specification.ErpStoreReturnInfoSpecification;
import com.systemk.spyder.Service.StoreReturnService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.ResultUtil;

@Service
public class StoreReturnServiceImpl implements StoreReturnService {

	private static final Logger log = LoggerFactory.getLogger(StoreReturnServiceImpl.class);
	
	@Autowired
	private ErpStoreReturnInfoRepository erpStoreReturnInfoRepository;

	@Autowired
	private ErpStoreReturnSubInfoRepository erpStoreReturnSubInfoRepository;

	@Autowired
	private ErpStoreReturnTagRepository erpStoreReturnTagRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	@Qualifier("externalEntityManager")
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private RfidSd15IfRepository rfidSd15IfRepository;
	
	@Autowired
	private JdbcTemplate template;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findErpReturnInfoAll(String startDate, String endDate, String returnType, String customerCode) throws Exception {

		Date fromDate = CalendarUtil.convertStartDate(startDate);
		Date toDate = CalendarUtil.convertEndDate(endDate);

		if(customerCode.equals("")) {
			return ResultUtil.setCommonResult("E", "매장 코드가 필요합니다");
		}

		Specifications<ErpStoreReturnInfo> specifications = Specifications.where(ErpStoreReturnInfoSpecification.regDateBetween(fromDate, toDate));

		CompanyInfo companyInfo = companyInfoRepository.findByCustomerCode(customerCode);
		if(companyInfo==null) {
			return ResultUtil.setCommonResult("E", "사용자에 해당하는 매장 정보가 없습니다");
		} else {
			String cornerCode = companyInfo.getCornerCode();

			specifications = specifications.and(Specifications.where(ErpStoreReturnInfoSpecification.fromCustomerCode(customerCode)))
					.and(Specifications.where(ErpStoreReturnInfoSpecification.fromCornerCode(cornerCode)));
		}

		if(returnType!=null && !returnType.equals("all")) {
			specifications = specifications.and(Specifications.where(ErpStoreReturnInfoSpecification.returnType(returnType)));
		}
				
		List<ErpStoreReturnInfo> erpStoreReturnInfo = erpStoreReturnInfoRepository.findAll(specifications);
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, erpStoreReturnInfo);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findErpReturnDetailInfo(Long returnInfoSeq) {

		Map<String, Object> workResult = new HashMap<String, Object>();

		List<ErpStoreReturnSubInfo> erpStoreReturnSubInfo = erpStoreReturnSubInfoRepository.findByReturnInfoSeq(returnInfoSeq);

		//List<ErpReturnWorkBoxResult> workBoxList = erpStoreReturnTagRepository.findReturnWorkBoxInfo(returnInfoSeq);
		List<ErpReturnWorkBoxResult> workBoxList = new ArrayList<ErpReturnWorkBoxResult>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mainDataSourceConfig.mainDataSource());
		String query = "SELECT COUNT(work_box_num) as sumByBox, work_box_num, complete_yn, erp_return_invoice_num FROM erp_store_return_tag WHERE return_info_sub_seq ";
		query += ("IN (SELECT return_info_sub_seq FROM erp_store_return_sub_info WHERE return_info_seq = " + returnInfoSeq + ") GROUP BY work_box_num, complete_yn, erp_return_invoice_num");
		List<Map<String, Object>> workBoxListResult = jdbcTemplate.queryForList(query);
		for(Map<String,Object> box : workBoxListResult) {
			ErpReturnWorkBoxResult boxResult = new ErpReturnWorkBoxResult();
			boxResult.setSumPerBox(Integer.parseInt(box.get("sumByBox").toString()));
			boxResult.setWorkBoxNum(Long.parseLong(box.get("work_box_num").toString()));
			boxResult.setCompleteYn(box.get("complete_yn").toString());
			boxResult.setErpInvoiceNum(box.get("erp_return_invoice_num")==null ? "" : box.get("erp_return_invoice_num").toString());
			workBoxList.add(boxResult);
		}
		
		ErpStoreReturnInfo orderList = erpStoreReturnInfoRepository.findByReturnInfoSeq(returnInfoSeq);
		//지시반품 헤드 목록
		workResult.put("orderInfoList", orderList);
		//지시반품 스타일 목록
		workResult.put("detailInfoList", erpStoreReturnSubInfo);
		//지시반품 작업 목록
		workResult.put("detailWorkList", workBoxList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, workResult);
	}
	
	@Transactional
	@Override
	public Map<String, Object> erpReturnWorkSave(List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception {

		if(erpReturnWorkBean == null || erpReturnWorkBean.size()==0) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_WORKED_DATA_NONE_MESSAGE);
		}

		List<ErpStoreReturnTag> returnTagList = new ArrayList<ErpStoreReturnTag>();
		List<ErpStoreReturnTag> chkUsedTagList = new ArrayList<ErpStoreReturnTag>();
		Set<HashMap<String, Object>> usedTagList = new HashSet<HashMap<String,Object>>();
		for(ErpReturnWorkBean bean : erpReturnWorkBean) {

			int chkBoxNum = erpStoreReturnTagRepository.countByWorkBoxNum(bean.getWorkBoxNum());
			
			Long anotherSubSeq = 0L;

			if(chkBoxNum > 0) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_WORKED_BOXNUM_MESSAGE);
			}
			
			if(bean.getAnotherYn().equals("Y")) {
				List<ErpStoreReturnSubInfo> usedAnother = erpStoreReturnSubInfoRepository.findByReturnStyleAndReturnColorAndReturnSize(bean.getStyle(), bean.getColor(), bean.getSize());
				if(!usedAnother.isEmpty()) {
					for(int i=0;i<usedAnother.size();i++) {
						ErpStoreReturnSubInfo subInfo = new ErpStoreReturnSubInfo();
						subInfo.setReturnInfoSubSeq(usedAnother.get(i).getReturnInfoSubSeq());
						subInfo.setExecuteAmount(usedAnother.get(i).getExecuteAmount());
						erpStoreReturnSubInfoRepository.save(usedAnother);
						anotherSubSeq = subInfo.getReturnInfoSubSeq();
					}
				}else {
					ErpStoreReturnSubInfo esi = new ErpStoreReturnSubInfo();
					esi.setReturnInfoSeq(bean.getReturnInfoSeq());
					esi.setReturnStyle(bean.getStyle());
					esi.setReturnColor(bean.getColor());
					esi.setReturnSize(bean.getSize());
					esi.setOrderAmount(0L);
					esi.setExecuteAmount(bean.getAmount());
					esi.setConfirmAmount(0L);
					esi.setRfidYn(bean.getRfidYn());
					esi.setAnotherYn("Y");
					erpStoreReturnSubInfoRepository.save(esi);
					//sub_seq받아가기
					anotherSubSeq = esi.getReturnInfoSubSeq();
				}
			}

			
			Long addAmount = bean.getAmount();
			for(int i=0;i<addAmount;i++) {
				ErpStoreReturnTag et = new ErpStoreReturnTag();
				if(bean.getAnotherYn().equals("Y"))
					et.setReturnInfoSubSeq(anotherSubSeq);
				else
					et.setReturnInfoSubSeq(bean.getReturnSubSeq());
				if(bean.getRfidTagList()!=null && bean.getRfidTagList().size()>0) {
					ErpStoreReturnTag chkExistTag = erpStoreReturnTagRepository.findByRfidTag(bean.getRfidTagList().get(i).get("rfidTag").toString());
					if(chkExistTag==null) {
						et.setRfidTag(bean.getRfidTagList().get(i).get("rfidTag").toString());
					}else {
						chkUsedTagList.add(chkExistTag);
						HashMap<String, Object> usedTag = new HashMap<String, Object>();
						ErpStoreReturnSubInfo subInfo = erpStoreReturnSubInfoRepository.findByReturnInfoSubSeq(chkExistTag.getReturnInfoSubSeq());
						usedTag.put("style", subInfo.getReturnStyle());
						usedTag.put("color", subInfo.getReturnColor());
						usedTag.put("size", subInfo.getReturnSize());
						usedTag.put("rfidSeq", chkExistTag.getRfidTag().substring(27));
						usedTagList.add(usedTag);	
					}
						
				}
				et.setWorkBoxNum(bean.getWorkBoxNum());
				et.setCompleteYn("N");
				et.setUpdUserSeq(bean.getUserSeq());
				returnTagList.add(et);
			}
			
			
			
			if(usedTagList.size()==0) {
				
				//지시 이행수량 업데이트(erp_store_return_sub_info)
				erpStoreReturnSubInfoRepository.updateExecuteAmount(bean.getAmount(), bean.getReturnSubSeq());
				
				//지시 테이블 시퀀스 추출
				Long returnSubSeq = (bean.getAnotherYn().equals("Y")) ? anotherSubSeq : bean.getReturnSubSeq();
				Long returnInfoSeq = erpStoreReturnSubInfoRepository.findReturnInfoSeqBySubSeq(returnSubSeq);
				
				//지시 테이블 이행수량 업데이트
				erpStoreReturnInfoRepository.updateExecuteAmount(bean.getAmount(), returnInfoSeq);
			}

		}
		if(usedTagList.size()>0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtil.setCommonResult("E", "이미 작업에 사용된 태그 정보가 존재합니다", usedTagList);
			
		}
		erpStoreReturnTagRepository.save(returnTagList);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional
	@Override
	public Map<String, Object> DeleteErpReturnWork(List<ErpReturnWorkBean> erpReturnWorkBean) throws Exception {

		if(erpReturnWorkBean == null || erpReturnWorkBean.size()==0) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_WORKED_DATA_NONE_MESSAGE);
		}

		for(ErpReturnWorkBean bean : erpReturnWorkBean) {

			List<ErpStoreReturnTag> returnTagList = erpStoreReturnTagRepository.findByWorkBoxNum(bean.getWorkBoxNum());
			
			int chkCnt = erpStoreReturnTagRepository.chkConfirmYnByBoxNum(bean.getWorkBoxNum());

			if(chkCnt > 0) {

				return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_COMPLETED_WORK_MESSAGE);

			}else {
				
				for(int i=0;i<returnTagList.size();i++) {
					Long returnSubSeq = returnTagList.get(i).getReturnInfoSubSeq();
					
						List<ErpStoreReturnTag> tagList = erpStoreReturnTagRepository.findByWorkBoxNum(bean.getWorkBoxNum());
						for(ErpStoreReturnTag et : tagList) {
							erpStoreReturnTagRepository.delete(et);
						}
						
						ErpStoreReturnSubInfo subInfo = erpStoreReturnSubInfoRepository.findByReturnInfoSubSeq(returnSubSeq);
						
						
						//지시 이행 삭제 수량 업데이트(erp_store_return_sub_info)
						erpStoreReturnSubInfoRepository.updateDelExecuteAmount(1, returnSubSeq);
						erpStoreReturnSubInfoRepository.flush();
						//지시 테이블 시퀀스 추출
						ErpStoreReturnSubInfo returnSubInfo = erpStoreReturnSubInfoRepository.findByReturnInfoSubSeq(returnSubSeq);
						//지시 이행 삭제 수량 업데이트(erp_store_return_info)
						erpStoreReturnInfoRepository.updateDelExecuteAmount(1, returnSubInfo.getReturnInfoSeq());
					
					}
				}
			erpStoreReturnSubInfoRepository.deleteAnotherYN();
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> completeReturnWork(ErpReturnWorkBean erpReturnWorkBean) {

		List<ErpStoreReturnTag> tagList = erpStoreReturnTagRepository.findByWorkBoxNum(erpReturnWorkBean.getWorkBoxNum());
		//작업자 정보 추출... 해당 매장에 대한 작업만 이루어지므로  tagList의 모든 user_seq는 같다고 봄
		Long userSeq = tagList.get(0).getUpdUserSeq();
		
		if(tagList==null || tagList.size()==0) {
			return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_NOT_FIND_BOXNUM_MESSAGE);
		}else {
			//확정되어진 박스번호인지 확인
			int chkConfirm = erpStoreReturnTagRepository.chkConfirmYnByBoxNum(erpReturnWorkBean.getWorkBoxNum());
			if(chkConfirm!=0) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.STORE_PDA_RETURN_COMPLETED_WORK_MESSAGE);
			}else {

				for(ErpStoreReturnTag et : tagList) {
					
						erpStoreReturnTagRepository.updateCompleteYn(et.getReturnTagSeq());
						//지시 확정 수량 업데이트(erp_store_return_sub_info)
						erpStoreReturnSubInfoRepository.updateConfirmAmount(1, et.getReturnInfoSubSeq());
						//지시 테이블 시퀀스 추출
						Long returnInfoSeq = erpStoreReturnSubInfoRepository.findReturnInfoSeqBySubSeq(et.getReturnInfoSubSeq());
						//지시 확정 수량 업데이트(erp_store_return_info)
						erpStoreReturnInfoRepository.updateConfirmAmount(1, returnInfoSeq);
				}
			}

			erpStoreReturnTagRepository.flush();


			List<ErpStoreReturnSubInfo> subList = erpStoreReturnSubInfoRepository.findByWorkBoxNum(erpReturnWorkBean.getWorkBoxNum());
			for(ErpStoreReturnSubInfo sub : subList) {

				int orderAmount, chkAmount = 0;
				//지시에서 내려진 지시수량
				orderAmount = erpStoreReturnInfoRepository.findOrderAmount(sub.getReturnInfoSeq());
				//확정 작업 수량
				chkAmount = erpStoreReturnSubInfoRepository.findSumConfirmAmount(sub.getReturnInfoSeq());
				//지시수량보다 확정 작업 수량이 많거나 같으면 지시테이블의 처리여부를 Y로 업데이트
				if(chkAmount >= orderAmount)
					erpStoreReturnInfoRepository.updateConfirmYn(sub.getReturnInfoSeq());
			}

			//확정 지을때 userId 필요
			updateErpConfirmReturnOrder(subList, erpReturnWorkBean.getWorkBoxNum(), tagList.size(), userSeq);
		}

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE);
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> getMaxBoxNo() throws Exception {

		Map<String,Object> result = new HashMap<String,Object>();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(mainDataSourceConfig.mainDataSource());
		Long maxNo = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(work_box_num),0) +1 FROM erp_store_return_tag",Long.class);

		result.put("boxNo", maxNo);

		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, result);
	}

	@Override
	public Map<String, Object> getBoxDetailInfo(Long boxNum) throws Exception {
		List<ErpStoreReturnSubInfo> subList = erpStoreReturnSubInfoRepository.findByWorkBoxNum(boxNum);
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE, subList);
	}
	
	@Transactional
	public void updateErpConfirmReturnOrder(List<ErpStoreReturnSubInfo> storeReturnSubList, Long boxNum, int amount, Long userSeq) {
			
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			
			List<RfidSd15If> sd15List = new ArrayList<RfidSd15If>();
	
			
			try {	
				for(ErpStoreReturnSubInfo si : storeReturnSubList) {
					
					ErpStoreReturnInfo returnInfo = erpStoreReturnInfoRepository.findByReturnInfoSeq(si.getReturnInfoSeq());
					RfidSd15IfKey rfidSd15IfKey = new RfidSd15IfKey();
					rfidSd15IfKey.setSd15Jsdt(sFormat.format(returnInfo.getErpRegDate()));
					rfidSd15IfKey.setSd15Jssq(returnInfo.getErpReturnNo());
					rfidSd15IfKey.setSd15Mggb(returnInfo.getReturnType());
					rfidSd15IfKey.setSd15Frcd(returnInfo.getFromCustomerCode());
					rfidSd15IfKey.setSd15Frco(returnInfo.getFromCornerCode());
					rfidSd15IfKey.setSd15Styl(si.getReturnStyle());
					rfidSd15IfKey.setSd15Stcd(si.getReturnColor()+si.getReturnSize());
					rfidSd15IfKey.setSd15Bxno(boxNum);
					RfidSd15If rfidSd15If = new RfidSd15If();
					rfidSd15If.setRfidSd15IfKey(rfidSd15IfKey);
					rfidSd15If.setSd15Bqty((long)amount);
					if(si.getAnotherYn().equals("Y"))
						rfidSd15If.setSd15Gubn("NEW");
					else
						rfidSd15If.setSd15Gubn(si.getAnotherYn());
					rfidSd15If.setSd15Endt(new Date());
					rfidSd15If.setSd15Enid(userSeq.toString());
					
					sd15List.add(rfidSd15If);
				}
				
				
				rfidSd15IfRepository.save(sd15List);
				rfidSd15IfRepository.flush();
				log.info("ERP RFID 반품지시 실적 반영");
				
				
					template = new JdbcTemplate(externalDataSourceConfig.externalDataSource());
					template.setResultsMapCaseInsensitive(true);
					
					SimpleJdbcCall call = new SimpleJdbcCall(template).withFunctionName("UP_RFID_PDA_SD15");
					RfidSd15If sd15If = sd15List.get(0);
					SqlParameterSource in = new MapSqlParameterSource().addValue("as_jsdt", sd15If.getRfidSd15IfKey().getSd15Jsdt(), Types.NVARCHAR)
							.addValue("an_jssq", sd15If.getRfidSd15IfKey().getSd15Jssq(), Types.NUMERIC)
							.addValue("as_mggb", sd15If.getRfidSd15IfKey().getSd15Mggb(), Types.NVARCHAR)
							.addValue("as_frcd", sd15If.getRfidSd15IfKey().getSd15Frcd(), Types.NVARCHAR)
							.addValue("as_frco", sd15If.getRfidSd15IfKey().getSd15Frco(), Types.NVARCHAR)
							.addValue("an_bxno", boxNum, Types.NUMERIC)
							.addValue("as_mgdt", Types.NVARCHAR)
							.addValue("an_mgsq", Types.NUMERIC)
							.addValue("al_ecod", Types.INTEGER)
							.addValue("as_emsg", Types.NVARCHAR);
		
					Map out = call.execute(in);
					if(Integer.parseInt(out.get("al_ecod").toString())!=0) throw new RuntimeException();
					erpStoreReturnTagRepository.updateErpReturnInvoiceNum(out.get("an_mgsq").toString(), boxNum);

			}catch(Exception ex) {
				
				ex.printStackTrace();
				throw new RuntimeException(ex);
				
			}
	
		}

}
