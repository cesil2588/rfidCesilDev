package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.BoxTagListBean;
import com.systemk.spyder.Dto.Request.ReferenceBean;
import com.systemk.spyder.Dto.Request.ReferenceBoxListBean;
import com.systemk.spyder.Dto.Request.ReferenceStyleListBean;
import com.systemk.spyder.Dto.Request.RfidTagBean;
import com.systemk.spyder.Dto.Response.BartagMinMaxResult;
import com.systemk.spyder.Dto.Response.ReleaseScheduleResult;
import com.systemk.spyder.Entity.External.RfidLe01If;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidLe01IfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnScheduleRepository;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.Specification.BartagMasterSpecification;
import com.systemk.spyder.Repository.Main.Specification.DistributionStorageSpecification;
import com.systemk.spyder.Repository.Main.Specification.ErpStoreScheduleSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.ErpService;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.CustomBean.Group.StoreScheduleGroupModel;
import com.systemk.spyder.Service.Mapper.Group.StoreScheduleGroupModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class ErpStoreScheduleServiceImpl implements ErpStoreScheduleService{

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepositry;

	@Autowired
	private ErpService erpService;

	@Autowired
	private RfidLe01IfRepository rfidLe01IfRepository;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByReleaseSchedule(String referenceNo) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if(referenceNo.length() != 17){
			map.put("resultCode", ApiResultConstans.NOT_VALID_REPERENCE_NO);
			map.put("resultMessage", ApiResultConstans.NOT_VALID_REPERENCE_NO_MESSAGE);
			return map;
		}

		String completeDate = referenceNo.substring(0, 8);
		String endCustomerCd = referenceNo.substring(8, 14);
		String cornerCd = referenceNo.substring(14, 15);
		String completeSeq = referenceNo.substring(15);

		Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(completeDate))
															  .and(ErpStoreScheduleSpecification.endCompanyCustomerCdEqual(endCustomerCd))
															  .and(ErpStoreScheduleSpecification.endCompanyCornerCdEqual(cornerCd))
															  .and(ErpStoreScheduleSpecification.completeSeqEqual(completeSeq));

		List<ErpStoreSchedule> erpStoreScheduleList = erpStoreScheduleRepository.findAll(erpStoreScheduleSpec);

		if(erpStoreScheduleList.size() == 0){
			map.put("resultCode", ApiResultConstans.NOT_FIND_DATA);
			map.put("resultMessage", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
			return map;
		}

		ArrayList<Map<String, Object>> scheduleList = new ArrayList<Map<String, Object>>();

		for(ErpStoreSchedule erpSchedule : erpStoreScheduleList){

			Map<String, Object> detailMap = new HashMap<String, Object>();

			detailMap.put("startCompanyName", erpSchedule.getStartCompanyInfo().getName());
			detailMap.put("startCustomerCode", erpSchedule.getStartCompanyInfo().getCustomerCode());
			detailMap.put("startCompanySeq", erpSchedule.getStartCompanyInfo().getCompanySeq());
			detailMap.put("endCompanyName", erpSchedule.getEndCompanyInfo().getName());
			detailMap.put("endCustomerCode", erpSchedule.getEndCompanyInfo().getCustomerCode());
			detailMap.put("endCompanySeq", erpSchedule.getEndCompanyInfo().getCompanySeq());
			detailMap.put("orderAmount", erpSchedule.getOrderAmount());
			detailMap.put("sortingAmount", erpSchedule.getSortingAmount());


			// 해당 스타일, 컬러, 사이즈의 바택정보가 있는지 확인
			Specifications<BartagMaster> bartagMasterSpec = Specifications.where(BartagMasterSpecification.styleEqual(erpSchedule.getStyle()))
					  													  .and(BartagMasterSpecification.colorEqual(erpSchedule.getColor()))
					  													  .and(BartagMasterSpecification.sizeEqual(erpSchedule.getSize()));

			List<BartagMaster> bartagMasterList = bartagMasterRepository.findAll(bartagMasterSpec);

			int index = 0;

			if(bartagMasterList.size() == 0){
				map.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
				map.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
				return map;
			}

			for(BartagMaster bartag : bartagMasterList){
				if(index == 0){
					detailMap.put("style", bartag.getStyle());
					detailMap.put("color", bartag.getColor());
					detailMap.put("size", bartag.getSize());
					detailMap.put("erpKey", bartag.getErpKey());
					detailMap.put("startRfidSeq", bartag.getStartRfidSeq());
				}

				if(index == (bartagMasterList.size()-1)){
					detailMap.put("endRfidSeq", bartag.getEndRfidSeq());
				}

				index ++;
			}

			// 해당 스타일, 컬러, 사이즈가 물류센터에 있는지 확인
			Specifications<DistributionStorage> distributionSpec = Specifications.where(DistributionStorageSpecification.styleEqual(erpSchedule.getStyle()))
																				 .and(DistributionStorageSpecification.colorEqual(erpSchedule.getColor()))
																				 .and(DistributionStorageSpecification.sizeEqual(erpSchedule.getSize()));

			List<DistributionStorage> distributionBartagMasterList = distributionStorageRepository.findAll(distributionSpec);

			if(distributionBartagMasterList.size() == 0){
				map.put("resultCode", ApiResultConstans.NOT_FIND_DISTRIBUTION_BARTAG);
				map.put("resultMessage", ApiResultConstans.NOT_FIND_DISTRIBUTION_BARTAG_MESSAGE);
				return map;
			}

			// 해당 스타일, 컬러, 사이즈가 물류센터 재고에 있는지 확인
			int stockAmount = 0;

			for(DistributionStorage storage : distributionBartagMasterList){
				stockAmount += storage.getStat2Amount();
			}

			detailMap.put("stockAmount", stockAmount);

			/*
			int stockAmount = 60;

			detailMap.put("stockAmount", stockAmount);
			*/
			/*
			 * 부분출고가 없을시 사용(현재 사용안함)
			 *
			if(amount < erpSchedule.getOrderAmount()){
				map.put("resultCode", ApiResultConstans.NOT_ENOUGH_AMOUNT);
				map.put("resultMessage", ApiResultConstans.NOT_ENOUGH_AMOUNT_MESSAGE);
				return map;
			}
			*/

			scheduleList.add(detailMap);
		}

		map.put("releaseScheduleList", scheduleList);
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return map;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Override
	public Map<String, Object> findByReleaseSchedulePost(ReferenceBean referenceBean) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<ReleaseScheduleResult> scheduleList = new ArrayList<ReleaseScheduleResult>();
		List<ErpStoreSchedule> erpStoreScheduleList = new ArrayList<ErpStoreSchedule>();

		//TODO 동일한 스타일, 컬러, 사이즈, ReferenceNo 일 경우 하나로 합치는 로직 필요

		// 일반 매장일 경우 custOrderType == 'C', 온라인일 경우 custOrderType == 'C1'
		if(referenceBean.getCustOrderTypeCd() == null || referenceBean.getCustOrderTypeCd().equals("C")) {

			Specifications<ErpStoreSchedule> erpStoreScheduleSpec = null;

			for(ReferenceBoxListBean tempBox : referenceBean.getBarcodeList()) {

				// 중복된 스타일, 컬러, 사이즈, ReferenceNo Merge
				tempBox.mergeStyle();

				for(ReferenceStyleListBean tempStyle : tempBox.getStyleList()) {

					// ReferenceNo 검증
					if(tempStyle.getReferenceNo().length() != 17){
						obj.put("resultCode", ApiResultConstans.NOT_VALID_REPERENCE_NO);
						obj.put("resultMessage", ApiResultConstans.NOT_VALID_REPERENCE_NO_MESSAGE);
						return obj;
					}
					erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.referenceNoEqual(tempStyle.getReferenceNo()))
														 .and(ErpStoreScheduleSpecification.styleEqual(tempStyle.getStyle()))
														 .and(ErpStoreScheduleSpecification.colorEqual(tempStyle.getColor()))
														 .and(ErpStoreScheduleSpecification.sizeEqual(tempStyle.getSize()));

					ErpStoreSchedule erpStoreSchedule = erpStoreScheduleRepository.findOne(erpStoreScheduleSpec);

					// 하나라도 조회된 데이터가 없으면 ERP 출고 예정 정보 없음 리턴
					if(erpStoreSchedule == null) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER_MESSAGE);
						return obj;
					}

					erpStoreScheduleList.add(erpStoreSchedule);
				}
			}

			for(ErpStoreSchedule erpSchedule : erpStoreScheduleList){

				// ErpStoreSchedule 셋팅
				ReleaseScheduleResult result = new ReleaseScheduleResult(erpSchedule);

				// 해당 스타일, 컬러, 사이즈의 바택정보가 있는지 확인
				if(erpSchedule.getRfidYn().equals("Y")) {

					BartagMinMaxResult minMaxResult = bartagService.findByMinMax(result.getStyle(), result.getColor(), result.getSize());

					if (minMaxResult == null) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
						return obj;
					}

					// StartRfidSeq, EndRfidSeq, ErpKey 셋팅
					result.CopyData(minMaxResult);

				} else {

					result.setErpKey("-");
					result.setStartRfidSeq("-");
					result.setEndRfidSeq("-");
				}

				/**
				 *  2019-05-16 최의선 물류입고 되지 않은 태그들을 강제로 출고 나가기 위하여 주석 처리
				 */
				// 해당 스타일, 컬러, 사이즈가 물류센터에 있는지 확인
				/*
				Specifications<DistributionStorage> distributionSpec = Specifications.where(DistributionStorageSpecification.styleEqual(erpSchedule.getStyle()))
																					 .and(DistributionStorageSpecification.colorEqual(erpSchedule.getColor()))
																					 .and(DistributionStorageSpecification.sizeEqual(erpSchedule.getSize()));

				List<DistributionStorage> distributionBartagMasterList = distributionStorageRepository.findAll(distributionSpec);

				if(distributionBartagMasterList.size() == 0){
					obj.put("resultCode", ApiResultConstans.NOT_FIND_DISTRIBUTION_BARTAG);
					obj.put("resultMessage", ApiResultConstans.NOT_FIND_DISTRIBUTION_BARTAG_MESSAGE);
					return obj;
				}

				// 해당 스타일, 컬러, 사이즈가 물류센터 재고에 있는지 확인
				int stockAmount = 0;

				for(DistributionStorage storage : distributionBartagMasterList){
					stockAmount += storage.getStat2Amount();
				}
				*/

				result.setStockAmount(0L);

				/*
				 * 부분출고가 없을시 사용(현재 사용안함)
				 *
				if(amount < erpSchedule.getOrderAmount()){
					map.put("resultCode", ApiResultConstans.NOT_ENOUGH_AMOUNT);
					map.put("resultMessage", ApiResultConstans.NOT_ENOUGH_AMOUNT_MESSAGE);
					return map;
				}
				*/

				scheduleList.add(result);
			}

		} else if(referenceBean.getCustOrderTypeCd().equals("C1")) {
			 // TODO 온라인출고 개발
		}

		obj.put("releaseScheduleList", scheduleList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public ErpStoreSchedule findByReleaseSchedule(String referenceNo, String style, String color, String size) throws Exception {

		String completeDate = referenceNo.substring(0, 8);
		String endCustomerCd = referenceNo.substring(8, 14);
		String cornerCd = referenceNo.substring(14, 15);
		String completeSeq = referenceNo.substring(15);

		Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(completeDate))
															  .and(ErpStoreScheduleSpecification.endCompanyCustomerCdEqual(endCustomerCd))
															  .and(ErpStoreScheduleSpecification.endCompanyCornerCdEqual(cornerCd))
															  .and(ErpStoreScheduleSpecification.completeSeqEqual(completeSeq))
															  .and(ErpStoreScheduleSpecification.styleEqual(style))
															  .and(ErpStoreScheduleSpecification.anotherStyleEqual(color, size));

		return erpStoreScheduleRepository.findOne(erpStoreScheduleSpec);
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreScheduleGroupModel> findStoreScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "ess.complete_date DESC ");

		StringBuffer query = new StringBuffer();
		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) ess.complete_date, ");
		query.append("ess.complete_type, ");
		query.append("eci.company_seq, ");
		query.append("eci.name, ");
		query.append("eci.customer_code, ");
		query.append("eci.corner_code, ");
		query.append("ess.complete_seq, ");
		query.append("ess.style, ");
		query.append("ess.color, ");
		query.append("ess.size, ");
		query.append("SUM(ess.order_amount) AS order_amount, ");
		query.append("SUM(ess.release_amount) AS release_amount ");
		query.append("FROM erp_store_schedule ess ");
		query.append("INNER JOIN company_info sci ");
		query.append("ON ess.start_company_seq = sci.company_seq ");
		query.append("INNER JOIN company_info eci ");
		query.append("ON ess.end_company_seq = eci.company_seq ");
		query.append("WHERE ess.complete_date BETWEEN :startDate AND :endDate ");

		if(companySeq > 0){
			query.append("AND eci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		query.append("GROUP BY ess.complete_date, ess.complete_type, ess.complete_seq, eci.company_seq, eci.name, eci.customer_code, eci.corner_code, ess.style, ess.color, ess.size ");
		query.append("ORDER BY " + sortQuery + ") inner_query) ");
		query.append("SELECT complete_date, complete_type, complete_seq, company_seq, name, customer_code, corner_code, style, color, size, order_amount, release_amount, CAST(CAST(release_amount AS FLOAT)/ CAST((order_amount) AS FLOAT)*100 AS DECIMAL) AS batch_percent ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new StoreScheduleGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountStoreScheduleGroupList(String startDate, String endDate, Long companySeq, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT ess.complete_date, ess.complete_type, ess.complete_seq, eci.company_seq, eci.name, eci.customer_code, eci.corner_code, ess.style, ess.color, ess.size ");
		query.append("FROM erp_store_schedule ess ");
		query.append("INNER JOIN company_info sci ");
		query.append("ON ess.start_company_seq = sci.company_seq ");
		query.append("INNER JOIN company_info eci ");
		query.append("ON ess.end_company_seq = eci.company_seq ");

		query.append("WHERE ess.complete_date BETWEEN :startDate AND :endDate ");

		if(companySeq > 0){
			query.append("AND eci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY ess.complete_date, ess.complete_type, ess.complete_seq, eci.company_seq, eci.name, eci.customer_code, eci.corner_code, ess.style, ess.color, ess.size ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ErpStoreSchedule> findAll(String completeDate, String completeSeq, String completeType, String style, String color, String size, String search, String option, Pageable pageable) throws Exception {

		Page<ErpStoreSchedule> page = null;

		Specifications<ErpStoreSchedule> specifications = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(completeDate))
																		.and(ErpStoreScheduleSpecification.completeSeqEqual(completeSeq))
																		.and(ErpStoreScheduleSpecification.completeTypeEqual(completeType))
																		.and(ErpStoreScheduleSpecification.styleEqual(style))
																		.and(ErpStoreScheduleSpecification.colorEqual(color))
																		.and(ErpStoreScheduleSpecification.sizeEqual(size));

		page = erpStoreScheduleRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ErpStoreSchedule> findByReleaseScheduleList(String referenceNo, String style, String color, String size) throws Exception {

		String completeDate = referenceNo.substring(0, 8);
		String endCustomerCd = referenceNo.substring(8, 14);
		String cornerCd = referenceNo.substring(14, 15);
		String completeSeq = referenceNo.substring(15);

		Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(completeDate))
				  .and(ErpStoreScheduleSpecification.endCompanyCustomerCdEqual(endCustomerCd))
				  .and(ErpStoreScheduleSpecification.endCompanyCornerCdEqual(cornerCd))
				  .and(ErpStoreScheduleSpecification.completeSeqEqual(completeSeq))
				  .and(ErpStoreScheduleSpecification.styleEqual(style))
				  .and(ErpStoreScheduleSpecification.colorEqual(color))
				  .and(ErpStoreScheduleSpecification.sizeEqual(size));

		return erpStoreScheduleRepository.findAll(erpStoreScheduleSpec);
	}

	@Transactional
	@Override
	public Map<String, Object> save(ErpStoreSchedule erpStoreSchedule) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user == null) {
			obj.put("resultCode", ApiResultConstans.NOT_FIND_USER);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_USER_MESSAGE);
			return obj;
		}

		Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(erpStoreSchedule.getCompleteDate()))
																			  .and(ErpStoreScheduleSpecification.endCompanyCustomerCdEqual(erpStoreSchedule.getEndCompanyInfo().getCustomerCode()))
																			  .and(ErpStoreScheduleSpecification.endCompanyCornerCdEqual(erpStoreSchedule.getEndCompanyInfo().getCornerCode()))
																			  .and(ErpStoreScheduleSpecification.completeSeqEqual(erpStoreSchedule.getCompleteSeq()))
																			  .and(ErpStoreScheduleSpecification.styleEqual(erpStoreSchedule.getStyle()))
																			  .and(ErpStoreScheduleSpecification.colorEqual(erpStoreSchedule.getColor()))
																			  .and(ErpStoreScheduleSpecification.sizeEqual(erpStoreSchedule.getSize()));

		List<ErpStoreSchedule> erpStoreScheduleList = erpStoreScheduleRepository.findAll(erpStoreScheduleSpec);

		if(erpStoreScheduleList.size() > 0){
			obj.put("resultCode", ApiResultConstans.DUPLICATION_ERP_REFERENCENO);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_ERP_REFERENCENO_MESSAGE);
			return obj;
		}

		Long completeIfSeq = erpStoreScheduleRepository.maxCompleteIf(erpStoreSchedule.getCompleteDate(), erpStoreSchedule.getEndCompanyInfo().getCompanySeq(), erpStoreSchedule.getCompleteSeq());

		erpStoreSchedule.setCompleteIfSeq(completeIfSeq.toString());

		CompanyInfo startCompanyInfo = companyInfoRepositry.findByCustomerCode(erpStoreSchedule.getTempStartCustomerCode());

		erpStoreSchedule.setRegDate(new Date());
		erpStoreSchedule.setRegTime(CalendarUtil.convertFormat("HHmm"));
		erpStoreSchedule.setRegUserId(user.getUserId());
		erpStoreSchedule.setErpRegDate(CalendarUtil.convertFormat("yyyyMMdd"));
		erpStoreSchedule.setStartCompanyInfo(startCompanyInfo);
		erpStoreSchedule.setCompleteCheckYn("N");
		erpStoreSchedule.setBatchYn("N");
		erpStoreSchedule.setErpCompleteYn("N");
		erpStoreSchedule.setReferenceNo(erpStoreSchedule.getCompleteDate() + erpStoreSchedule.getEndCompanyInfo().getCustomerCode() + erpStoreSchedule.getEndCompanyInfo().getCornerCode() + erpStoreSchedule.getCompleteSeq());

		erpStoreScheduleRepository.save(erpStoreSchedule);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> delete(List<ErpStoreSchedule> storeScheduleList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		erpStoreScheduleRepository.delete(storeScheduleList);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		return obj;
	}

	@Transactional
	@Override
	public boolean releaseCompleteAfterErpBatch(ErpStoreSchedule storeSchedule) throws Exception {

		boolean success = false;

		RfidLe01If rfidLe01If = erpService.saveReleaseComplete(storeSchedule, storeSchedule.getReleaseAmount(), storeSchedule.getSortingAmount());

		if(rfidLe01If != null) {
			rfidLe01IfRepository.save(rfidLe01If);

			success = true;
		}

		return success;
	}

	@Transactional
	@Override
	public void StorageReturnScheduleLog(ErpStoreReturnSchedule erpStoreReturnSchedule) {

		StorageScheduleLog returnLog = new StorageScheduleLog();

    	BoxInfo boxInfo = boxInfoRepository.findByBarcode(erpStoreReturnSchedule.getReturnOrderNo());
    	boxInfo.setBarcode(erpStoreReturnSchedule.getReturnOrderNo());

		returnLog.setArrivalCode("2935");
		returnLog.setCompleteYn("N");
		returnLog.setConfirmYn("Y");
		returnLog.setOpenDbCode("R2455");
		returnLog.setOrderType("10-R");
		returnLog.setBoxInfo(boxInfo);
		returnLog.setRegDate(new Date());
		returnLog.setCreateDate(erpStoreReturnSchedule.getOrderRegDate());
		returnLog.setDisuseYn("N");
		returnLog.setReturnYn("N");
		returnLog.setBatchYn("N");
		returnLog.setCompleteBatchYn("N");
		returnLog.setErpCompleteYn("N");
		returnLog.setErpScheduleYn("N");
		returnLog.setOpenDbScheduleYn("N");
		returnLog.setWmsCompleteYn("N");

		storageScheduleLogRepository.save(returnLog);
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> findByStoreReturnSchedule(String barcode) throws Exception {

		List<Map<String, Object>> scheduleList = new ArrayList<Map<String, Object>>();
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		List<ErpStoreReturnSchedule> erpStoreReturnScheduleList = new ArrayList<ErpStoreReturnSchedule>();

					erpStoreReturnScheduleList = erpStoreReturnScheduleRepository.findByReturnOrderNo(barcode);
					// 하나라도 조회된 데이터가 없으면 ERP 출고 예정 정보 없음 리턴
					if(erpStoreReturnScheduleList==null || erpStoreReturnScheduleList.size()==0) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_ERP_RELEASE_ORDER_MESSAGE);
						return obj;
					}


			for(ErpStoreReturnSchedule erpReturnSchedule : erpStoreReturnScheduleList){

				Map<String, Object> detailMap = new HashMap<String, Object>();

				detailMap.put("startCompanyName", erpReturnSchedule.getStartCompanyInfo().getName());
				detailMap.put("startCustomerCode", erpReturnSchedule.getStartCompanyInfo().getCustomerCode());
				detailMap.put("startCompanySeq", erpReturnSchedule.getStartCompanyInfo().getCompanySeq());
				detailMap.put("endCompanyName", erpReturnSchedule.getEndCompanyInfo().getName());
				detailMap.put("endCustomerCode", erpReturnSchedule.getEndCompanyInfo().getCustomerCode());
				detailMap.put("endCompanySeq", erpReturnSchedule.getEndCompanyInfo().getCompanySeq());
				detailMap.put("orderAmount", erpReturnSchedule.getReturnAmount());
				detailMap.put("sortingAmount", erpReturnSchedule.getReturnAmount());
				detailMap.put("rfidYn", "Y");
				detailMap.put("referenceNo", erpReturnSchedule.getReturnOrderNo());

				// 해당 스타일, 컬러, 사이즈의 바택정보가 있는지 확인
					Specifications<BartagMaster> bartagMasterSpec = Specifications.where(BartagMasterSpecification.styleEqual(erpReturnSchedule.getStyle()))
																				  .and(BartagMasterSpecification.colorEqual(erpReturnSchedule.getReturnAnotherCode().substring(0, 3)))
																				  .and(BartagMasterSpecification.sizeEqual(erpReturnSchedule.getReturnAnotherCode().substring(3, 6)));

					List<BartagMaster> bartagMasterList = bartagMasterRepository.findAll(bartagMasterSpec);

					int index = 0;

					if (bartagMasterList.size() == 0) {
						obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
						obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
						return obj;
					}

					for (BartagMaster bartag : bartagMasterList) {
						if (index == 0) {
							detailMap.put("style", bartag.getStyle());
							detailMap.put("color", bartag.getColor());
							detailMap.put("size", bartag.getSize());
							detailMap.put("erpKey", bartag.getErpKey());
							detailMap.put("startRfidSeq", bartag.getStartRfidSeq());

							//erpReturnSchedule.setTempErpkey(bartag.getErpKey()); *******************일단 erp값 없이 진행해 본다
						}

						if (index == (bartagMasterList.size() - 1)) {
							detailMap.put("endRfidSeq", bartag.getEndRfidSeq());
						}

						index++;
					}

				scheduleList.add(detailMap);
			}


		obj.put("returnScheduleList", scheduleList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	//반품 입고된 RFID_TAG 값의 상태변경 과 이력 추가
	@Transactional
	@Override
	public Map<String, Object> updateReturnRfidTag(BoxTagListBean tagBean) {

		List<RfidTagHistory> rfidTagList = new ArrayList<RfidTagHistory>();

			for (RfidTagBean bean : tagBean.getRfidTagList())
		    {
				String chkRfidTag = bean.getRfidTag();

				storeStorageRfidTagRepository.updateRfidStat(chkRfidTag);	//rfid_tag 상태 값 변경(store_storage_rfid_tag)
				distributionStorageRfidTagRepository.updateRfidStat(chkRfidTag);	//rfid_tag 상태 값 변경(distribution_storage_rfid_tag)

				String chkErpKey = rfidTagMasterRepository.findErpkeyByRfidTag(chkRfidTag);	//해당 rfid_tag 값에 해당하는 erp_key 추출

				//반품 이력 등록
				RfidTagHistory preRfidTagHistory = new RfidTagHistory();

				UserInfo userInfo  = new UserInfo();
				userInfo.setUserSeq(286667745);	//물류에서 반품 입고 처리되었으므로 등록자 Hansol

				preRfidTagHistory.setErpKey(chkErpKey);
				preRfidTagHistory.setRfidTag(chkRfidTag);
				preRfidTagHistory.setBarcode("");
				preRfidTagHistory.setRfidSeq(chkRfidTag.substring(27));
				preRfidTagHistory.setWork("24");
				preRfidTagHistory.setRegDate(new Date());
				preRfidTagHistory.setRegUserInfo(userInfo);

				rfidTagList.add(preRfidTagHistory);
		    }

			rfidTagHistoryRepository.save(rfidTagList);
			rfidTagHistoryRepository.flush();

			Map<String, Object> obj = new HashMap<String, Object>();

			obj.put("resultCode", ApiResultConstans.SUCCESS);
			obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

			return obj;
		}
}
