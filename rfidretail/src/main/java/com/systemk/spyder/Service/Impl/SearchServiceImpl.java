package com.systemk.spyder.Service.Impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.Specification.ReleaseScheduleLogSpecification;
import com.systemk.spyder.Repository.Main.Specification.StorageScheduleLogSpecification;
import com.systemk.spyder.Service.SearchService;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.Mapper.Select.SelectBartagModelMapper;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> searchAll(String search, String searchType, Long companySeq, String style, String color, String size) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if(searchType.equals("boxBarcode")) {
			// 박스 바코드 검색

			Specifications<StorageScheduleLog> storageSpecifications = Specifications.where(StorageScheduleLogSpecification.barcodeContain(search));

			if (companySeq != 0) {
				storageSpecifications = storageSpecifications.and(StorageScheduleLogSpecification.startCompanySeqEqual(companySeq));
			}

			List<StorageScheduleLog> storageScheduleList = storageScheduleLogRepository.findAll(storageSpecifications);


			Specifications<ReleaseScheduleLog> releaseSpecifications = Specifications.where(ReleaseScheduleLogSpecification.barcodeContain(search));

			if (companySeq != 0) {
				releaseSpecifications = releaseSpecifications.and(ReleaseScheduleLogSpecification.endCompanySeqEqual(companySeq));
			}

			List<ReleaseScheduleLog> releaseScheduleList = releaseScheduleLogRepository.findAll(releaseSpecifications);

			obj.put("storageScheduleList", storageScheduleList);
			obj.put("releaseScheduleList", releaseScheduleList);

		} else if(searchType.equals("rfidTag")) {

			SelectBartagModel model = selectBartagErpkey(style, color, size);

			List<RfidTagMaster> rfidTagList = rfidTagMasterRepository.findByErpKeyAndRfidSeqLike(model.getData(), "%" + search + "%");

			obj.put("rfidTagList", rfidTagList);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> searchRfidTag(RfidTagMaster rfidTag) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		rfidTag.setBartagMaster(bartagMasterRepository.findOne(rfidTag.getBartagSeq()));
		rfidTag.setProductionStorageRfidTag(productionStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag()));
		rfidTag.setDistributionStorageRfidTag(distributionStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag()));
		rfidTag.setStoreStorageRfidTag(storeStorageRfidTagRepository.findByRfidTag(rfidTag.getRfidTag()));
		rfidTag.setRfidTagHistoryList(rfidTagHistoryRepository.findByRfidTag(rfidTag.getRfidTag()));

		obj.put("rfidTagDetail", rfidTag);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
	    obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagStyle(Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style AS data ");
		query.append("FROM bartag_master bm ");

		if(companySeq != 0){
			query.append("WHERE bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.style ");
		query.append("ORDER BY bm.style ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagColor(Long companySeq, String style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.color AS data ");
		query.append("FROM bartag_master bm ");
		query.append("WHERE bm.style = :style ");
		params.put("style", style);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.color ");
		query.append("ORDER BY bm.color ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<SelectBartagModel> selectBartagSize(Long companySeq, String style, String color) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.size AS data ");
		query.append("FROM bartag_master bm ");

		query.append("WHERE bm.style = :style ");
		params.put("style", style);
		query.append("AND bm.color = :color ");
		params.put("color", color);

		if(companySeq != 0){
			query.append("AND bm.production_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		query.append("GROUP BY bm.size ");
		query.append("ORDER BY bm.size ASC ");

		return nameTemplate.query(query.toString(), params, new SelectBartagModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public SelectBartagModel selectBartagErpkey(String style, String color, String size) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.erp_key AS data ");
		query.append("FROM bartag_master bm ");

		query.append("WHERE bm.style = :style ");
		params.put("style", style);
		query.append("AND bm.color = :color ");
		params.put("color", color);
		query.append("AND bm.size = :size ");
		params.put("size", size);

		query.append("GROUP BY bm.erp_key ");
		query.append("ORDER BY bm.erp_key ASC ");


		return nameTemplate.queryForObject(query.toString(), params, new SelectBartagModelMapper());
	}
}
