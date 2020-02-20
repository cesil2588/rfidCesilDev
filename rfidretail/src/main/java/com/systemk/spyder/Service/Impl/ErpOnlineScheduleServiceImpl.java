package com.systemk.spyder.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.ErpOnlineScheduleRepository;
import com.systemk.spyder.Service.ErpOnlineScheduleService;

@Service
public class ErpOnlineScheduleServiceImpl implements ErpOnlineScheduleService{

	@Autowired
	private ErpOnlineScheduleRepository erpOnlineScheduleRepository;
	
	@Autowired
	private BartagMasterRepository bartagMasterRepository;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private DistributionStorageRepository distributionStorageRepository;
	
	/*
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByReleaseSchedule(String referenceNo) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
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
			
			scheduleList.add(detailMap);
		}
		
		map.put("releaseScheduleList", scheduleList);
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> findByReleaseSchedulePost(ReferenceBean referenceBean) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<ErpStoreScheduleDetail> insertErpStoreScheduleDetailList = new ArrayList<ErpStoreScheduleDetail>();
		
		if(referenceBean.getReferenceNo().length() != 17){
			map.put("resultCode", ApiResultConstans.NOT_VALID_REPERENCE_NO);
			map.put("resultMessage", ApiResultConstans.NOT_VALID_REPERENCE_NO_MESSAGE);
			return map;
		}
		
		String completeDate = referenceBean.getReferenceNo().substring(0, 8);
		String endCustomerCd = referenceBean.getReferenceNo().substring(8, 14);
		String cornerCd = referenceBean.getReferenceNo().substring(14, 15);
		String completeSeq = referenceBean.getReferenceNo().substring(15);
		
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
		
		List<ErpStoreScheduleDetail> erpStoreScheduleDetailList = erpStoreScheduleDetailRepository.findByKeyReferenceNo(referenceBean.getReferenceNo());
		
		// WMS tboxpick 바코드 번호 추출
		for(ReferenceBoxListBean barcode : referenceBean.getBarcodeList()){
			
			for(ErpStoreScheduleDetail scheduleList : erpStoreScheduleDetailList){
				if(barcode.getBarcode().equals(scheduleList.getKey().getBarcode())){
					barcode.setCheckYn("Y");
				}
			}
		}
		
		// WMS에서 조회한 데이터가 없을경우 저장
		for(ReferenceBoxListBean barcode : referenceBean.getBarcodeList()){
			if(barcode.getCheckYn() == null || barcode.getCheckYn().equals("")){
				
				ErpStoreScheduleDetailKey key = new ErpStoreScheduleDetailKey();
				key.setBarcode(barcode.getBarcode());
				key.setReferenceNo(referenceBean.getReferenceNo());
				
				ErpStoreScheduleDetail detail = new ErpStoreScheduleDetail();
				detail.setCompleteYn("N");
				detail.setKey(key);
				
				insertErpStoreScheduleDetailList.add(detail);
			}
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
			
			scheduleList.add(detailMap);
		}
		
		erpStoreScheduleDetailRepository.save(insertErpStoreScheduleDetailList);
		
		map.put("releaseScheduleList", scheduleList);
		map.put("resultCode", ApiResultConstans.SUCCESS);
		map.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		
		return map;
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
	public List<ErpStoreSchedule> findByReleaseScheduleList(String referenceNo) throws Exception {
		
		String completeDate = referenceNo.substring(0, 8);
		String endCustomerCd = referenceNo.substring(8, 14);
		String cornerCd = referenceNo.substring(14, 15);
		String completeSeq = referenceNo.substring(15);
		
		Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(completeDate))
				  .and(ErpStoreScheduleSpecification.endCompanyCustomerCdEqual(endCustomerCd))
				  .and(ErpStoreScheduleSpecification.endCompanyCornerCdEqual(cornerCd))
				  .and(ErpStoreScheduleSpecification.completeSeqEqual(completeSeq));

		return erpStoreScheduleRepository.findAll(erpStoreScheduleSpec);
	}
	*/
}
