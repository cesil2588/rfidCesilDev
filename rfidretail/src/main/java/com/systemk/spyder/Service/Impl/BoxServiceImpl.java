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
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleSubDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleSubDetailLog;
import com.systemk.spyder.Entity.Main.StoreStorage;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StoreStorageRepository;
import com.systemk.spyder.Repository.Main.Specification.BoxInfoSpecification;
import com.systemk.spyder.Repository.Main.Specification.StoreStorageSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BoxService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.RfidModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleChildModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleParentModel;
import com.systemk.spyder.Service.CustomBean.StoreScheduleSubChildModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;
import com.systemk.spyder.Service.CustomBean.Group.BoxGroupModel;
import com.systemk.spyder.Service.Mapper.BoxCountAllRowMapper;
import com.systemk.spyder.Service.Mapper.BoxCountRowMapper;
import com.systemk.spyder.Service.Mapper.BoxStoreScheduleChildRowMapper;
import com.systemk.spyder.Service.Mapper.BoxStoreScheduleParentRowMapper;
import com.systemk.spyder.Service.Mapper.BoxStoreScheduleSubChildRowMapper;
import com.systemk.spyder.Service.Mapper.BoxStyleCountRowMapper;
import com.systemk.spyder.Service.Mapper.BoxStyleRfidRowMapper;
import com.systemk.spyder.Service.Mapper.Group.BoxGroupModelMapper;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class BoxServiceImpl implements BoxService {

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Autowired
	private StoreStorageRepository storeStorageRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<BoxInfo> findAll(Long seq, String type, String stat, String search, String option, Pageable pageable) throws Exception {

		Page<BoxInfo> page = null;

		Specifications<BoxInfo> specifications = Specifications.where(BoxInfoSpecification.boxWorkGroupSeqEqual(seq));


		if(!type.equals("all")){
			specifications = specifications.and(BoxInfoSpecification.typeEqual(type));
		}

		if(!stat.equals("all")){
			specifications = specifications.and(BoxInfoSpecification.statEqual(stat));
		}

		if(!search.equals("") && option.equals("boxNum")){
			specifications = specifications.and(BoxInfoSpecification.boxNumContain(search));
		}

		page = boxInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BoxInfo> findAll(String startDate, String endDate, Long startCompanySeq, String search, String option, Pageable pageable) throws Exception{

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<BoxInfo> page = null;

		Specifications<BoxInfo> specifications = Specifications.where(BoxInfoSpecification.arrivalDateBetween(start, end));

		if(startCompanySeq != 0) {
			specifications = specifications.and(BoxInfoSpecification.startCompanySeqEqual(startCompanySeq));
		}

		specifications = specifications.and(BoxInfoSpecification.typeEqual("01"));
		specifications = specifications.and(BoxInfoSpecification.statEqual("2"));

		if(!search.equals("") && option.equals("boxNum")){
			specifications = specifications.and(BoxInfoSpecification.boxNumContain(search));
		}

		page = boxInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BoxInfo> findAll(Long companySeq, Pageable pageable) throws Exception {

		Page<BoxInfo> page = null;

		Specifications<BoxInfo> specifications = Specifications.where(BoxInfoSpecification.typeEqual("01"));
		specifications = specifications.and(BoxInfoSpecification.startCompanySeqEqual(companySeq));
		specifications = specifications.and(BoxInfoSpecification.statEqual("1"));

		page = boxInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BoxInfo> findAll(String createDate, Long companySeq, String type, String search, String option, Pageable pageable) throws Exception {

		Page<BoxInfo> page = null;

		Specifications<BoxInfo> specifications = Specifications.where(BoxInfoSpecification.createDateEqual(createDate))
															   .and(BoxInfoSpecification.startCompanySeqEqual(companySeq))
															   .and(BoxInfoSpecification.typeEqual(type));

		page = boxInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional
	@Override
	public void saveAll(List<BoxInfo> boxList, UserInfo regUserInfo) throws Exception {

		for(BoxInfo box: boxList){

			Long count = boxInfoRepository.countByBarcode(box.getBarcode());

			if(count > 0){
				continue;
			}
			box.setRegDate(new Date());
			box.setRegUserInfo(regUserInfo);

			boxInfoRepository.save(box);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel boxCount(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN b.stat = '2' THEN b.stat END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN b.stat = '3' THEN b.stat END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM box_info b ");
		query.append("WHERE b.stat IN ('2', '3') ");
		query.append("AND arrival_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", start);
		params.put("endDate", end);

		if(companySeq != 0){
			query.append("AND start_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel boxCountAll(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN b.stat = '1' THEN b.stat END) AS stat1_amount, ");
		query.append("COUNT(CASE WHEN b.stat = '2' THEN b.stat END) AS stat2_amount, ");
		query.append("COUNT(CASE WHEN b.stat = '3' THEN b.stat END) AS stat3_amount, ");
		query.append("COUNT(*) AS total_amount ");
		query.append("FROM box_info b ");
		query.append("WHERE reg_date BETWEEN :startDate AND :endDate ");

		params.put("startDate", start);
		params.put("endDate", end);

		if(companySeq != 0){
			query.append("AND start_company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}

		return nameTemplate.queryForObject(query.toString(), params, new BoxCountAllRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StyleModel> boxStyleCountProductionList(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("COUNT(*) AS count, ");
		query.append("ps.production_storage_seq AS style_seq ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN production_storage_rfid_tag p ");
		query.append("ON b.box_seq = p.box_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON p.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, ps.production_storage_seq ");

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query.toString(), params, new BoxStyleCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<RfidModel> boxStyleRfidProductionList(Long boxSeq, StyleModel style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("d.rfid_tag ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN production_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN production_storage ds ");
		query.append("ON d.production_storage_seq = ds.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ds.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		query.append("AND bm.order_degree = :orderDegree ");
		query.append("AND ds.production_storage_seq = :styleSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, d.rfid_tag ");

		params.put("boxSeq", boxSeq);
		params.put("style", style.getStyle());
		params.put("color", style.getColor());
		params.put("size", style.getSize());
		params.put("orderDegree", style.getOrderDegree());
		params.put("styleSeq", style.getStyleSeq());

		return nameTemplate.query(query.toString(), params, new BoxStyleRfidRowMapper());
	}


	@Transactional(readOnly = true)
	@Override
	public List<StyleModel> boxStyleCountDistributionList(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("COUNT(*) AS count, ");
		query.append("ds.distribution_storage_seq AS style_seq ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN distribution_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON d.distribution_storage_seq = ds.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, ds.distribution_storage_seq ");

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query.toString(), params, new BoxStyleCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StyleModel> boxStyleCountStoreList(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("COUNT(*) AS count, ");
		query.append("ss.store_storage_seq AS style_seq ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN store_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN store_storage ss ");
		query.append("ON d.store_storage_seq = ss.store_storage_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON ds.distribution_storage_seq = ss.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ps.production_storage_seq = ds.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON bm.bartag_seq = ps.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, ss.store_storage_seq ");

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query.toString(), params, new BoxStyleCountRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<RfidModel> boxStyleRfidDistributionList(Long boxSeq, StyleModel style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("d.rfid_tag ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN distribution_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON d.distribution_storage_seq = ds.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		query.append("AND bm.order_degree = :orderDegree ");
		query.append("AND ds.distribution_storage_seq = :styleSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, d.rfid_tag ");

		params.put("boxSeq", boxSeq);
		params.put("style", style.getStyle());
		params.put("color", style.getColor());
		params.put("size", style.getSize());
		params.put("orderDegree", style.getOrderDegree());
		params.put("styleSeq", style.getStyleSeq());

		return nameTemplate.query(query.toString(), params, new BoxStyleRfidRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<RfidModel> boxStyleRfidStoreList(Long boxSeq, StyleModel style) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bm.style, ");
		query.append("bm.color, ");
		query.append("bm.size, ");
		query.append("bm.order_degree, ");
		query.append("d.rfid_tag ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN store_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN store_storage ss ");
		query.append("ON d.store_storage_seq = ss.store_storage_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON ss.distribution_storage_seq = ds.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.box_seq = :boxSeq ");
		query.append("AND bm.style = :style ");
		query.append("AND bm.color = :color ");
		query.append("AND bm.size = :size ");
		query.append("AND bm.order_degree = :orderDegree ");
		query.append("AND ss.store_storage_seq = :styleSeq ");
		query.append("GROUP BY bm.style, bm.color, bm.size, bm.order_degree, d.rfid_tag ");

		params.put("boxSeq", boxSeq);
		params.put("style", style.getStyle());
		params.put("color", style.getColor());
		params.put("size", style.getSize());
		params.put("orderDegree", style.getOrderDegree());
		params.put("styleSeq", style.getStyleSeq());

		return nameTemplate.query(query.toString(), params, new BoxStyleRfidRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreScheduleParentModel> boxStoreScheduleParentList() throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT b.box_seq, ");
		query.append("b.barcode, ");
		query.append("o.open_db_code, ");
		query.append("COUNT(*) AS count ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN open_db_store_schedule_log o ");
		query.append("ON b.barcode = o.box_barcode ");
		query.append("INNER JOIN distribution_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON d.distribution_storage_seq = ds.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.stat = '2' ");
		query.append("GROUP BY b.box_seq, b.barcode, o.open_db_code ");

		return nameTemplate.query(query.toString(), params, new BoxStoreScheduleParentRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StoreScheduleChildModel> boxStoreScheduleChildList() throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT b.box_seq, ");
		query.append("b.barcode, ");
		query.append("o.open_db_code, ");
		query.append("do.line_num, ");
		query.append("do.style, ");
		query.append("do.color, ");
		query.append("do.size, ");
		query.append("do.order_degree, ");
		query.append("do.amount as count ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN open_db_store_schedule_log o ");
		query.append("ON b.barcode = o.box_barcode ");
		query.append("INNER JOIN open_db_store_schedule_detail_log do ");
		query.append("ON o.open_db_store_schedule_log_seq = do.open_db_store_schedule_log_seq ");
		query.append("INNER JOIN distribution_storage_rfid_tag d ");
		query.append("ON b.box_seq = d.box_seq ");
		query.append("INNER JOIN distribution_storage ds ");
		query.append("ON d.distribution_storage_seq = ds.distribution_storage_seq ");
		query.append("INNER JOIN production_storage ps ");
		query.append("ON ds.production_storage_seq = ps.production_storage_seq ");
		query.append("INNER JOIN bartag_master bm ");
		query.append("ON ps.bartag_seq = bm.bartag_seq ");
		query.append("WHERE b.stat = '2' ");
		query.append("GROUP BY b.box_seq, b.barcode, o.open_db_code, do.line_num, do.style, do.color, do.size, do.order_degree, do.amount");

		return nameTemplate.query(query.toString(), params, new BoxStoreScheduleChildRowMapper());
	}

	@Override
	public List<StoreScheduleSubChildModel> boxStoreScheduleSubChildList() throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT b.box_seq, ");
		query.append("b.barcode, ");
		query.append("o.open_db_code, ");
		query.append("do.line_num, ");
		query.append("do.style, ");
		query.append("do.color, ");
		query.append("do.size, ");
		query.append("do.order_degree, ");
		query.append("opsdl.rfid_tag ");
		query.append("FROM box_info b ");
		query.append("INNER JOIN open_db_store_schedule_log o ");
		query.append("ON b.barcode = o.box_barcode ");
		query.append("INNER JOIN open_db_store_schedule_detail_log do ");
		query.append("ON o.open_db_store_schedule_log_seq = do.open_db_store_schedule_log_seq ");
		query.append("INNER JOIN open_db_store_schedule_sub_detail_log opsdl ");
		query.append("ON do.open_db_store_schedule_detail_log_seq = opsdl.open_db_store_schedule_detail_log_seq  ");
		query.append("WHERE b.stat = '2' ");
		query.append("GROUP BY b.box_seq, b.barcode, o.open_db_code, do.line_num, do.style, do.color, do.size, do.order_degree, opsdl.rfid_tag");

		return nameTemplate.query(query.toString(), params, new BoxStoreScheduleSubChildRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<BoxGroupModel> findBoxGroupList(String startDate, String endDate, Long startCompanySeq, String type, String search, String option, Pageable pageable) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		String sortQuery = PagingUtil.sortSetting(pageable, "bi.create_date DESC ");

		StringBuffer query = new StringBuffer();

		query.append("WITH query AS ( ");
		query.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row ");
		query.append("FROM (  ");
		query.append("SELECT TOP(:groupCount) bi.create_date, bi.type, bi.start_company_seq, ci.name, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM box_info cbi ");
		query.append("WHERE cbi.start_company_seq = bi.start_company_seq ");
		query.append("AND cbi.stat = '1' ");
		query.append("AND cbi.create_date = bi.create_date ");
		query.append(") AS stat1_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM box_info cbi ");
		query.append("WHERE cbi.start_company_seq = bi.start_company_seq ");
		query.append("AND cbi.stat = '2' ");
		query.append("AND cbi.create_date = bi.create_date ");
		query.append(") AS stat2_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM box_info cbi ");
		query.append("WHERE cbi.start_company_seq = bi.start_company_seq ");
		query.append("AND cbi.stat = '3' ");
		query.append("AND cbi.create_date = bi.create_date ");
		query.append(") AS stat3_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM box_info cbi ");
		query.append("WHERE cbi.start_company_seq = bi.start_company_seq ");
		query.append("AND cbi.stat = '4' ");
		query.append("AND cbi.create_date = bi.create_date ");
		query.append(") AS stat4_count, ");
		query.append("(SELECT COUNT(*) ");
		query.append("FROM box_info cbi ");
		query.append("WHERE cbi.start_company_seq = bi.start_company_seq ");
		query.append("AND cbi.create_date = bi.create_date ");
		query.append(") AS total_count ");

		query.append("FROM box_info bi ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bi.start_company_seq = ci.company_seq ");
		query.append("WHERE bi.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND bi.type = :type ");

		params.put("type", type);

		if(startCompanySeq != 0){
			query.append("AND ci.company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		}

		query.append("GROUP BY bi.create_date, bi.type, bi.start_company_seq, ci.name ");
		query.append("ORDER BY " + sortQuery + " ) inner_query ) ");
		query.append("SELECT create_date, type, start_company_seq, name, stat1_count, stat2_count, stat3_count, stat4_count, total_count ");
		query.append("FROM QUERY ");
		query.append("WHERE row >= :startRow AND row < :endRow ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		params = PagingUtil.pagingSetting(pageable, params);

		return nameTemplate.query(query.toString(), params, new BoxGroupModelMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public Long CountBoxGroupList(String startDate, String endDate, Long startCompanySeq, String type, String search, String option) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		startDate = CalendarUtil.initStartDate(startDate);
		endDate = CalendarUtil.initEndDate(endDate);

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(*) AS total_count ");
		query.append("FROM (SELECT bi.create_date ");
		query.append("FROM box_info bi ");
		query.append("WHERE bi.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND bi.type = :type ");

		params.put("type", type);

		if(startCompanySeq != 0){
			query.append("AND bi.start_company_seq = :startCompanySeq ");
			params.put("startCompanySeq", startCompanySeq);
		}

		query.append("GROUP BY bi.create_date ");
		query.append(") AS temp_table ");

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		return nameTemplate.queryForObject(query.toString(), params, Long.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findBoxMappingTagList(String type, Long boxSeq) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		if(type.equals("01") || type.equals("03")){

			StorageScheduleLog storageScheduleLog = storageScheduleLogRepository.findByBoxInfoBoxSeq(boxSeq);

			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

			for(StorageScheduleDetailLog detailLog : storageScheduleLog.getStorageScheduleDetailLog()){

				Map<String, Object> styleObj = new HashMap<String, Object>();

				styleObj.put("style", detailLog.getStyle());
				styleObj.put("color", detailLog.getColor());
				styleObj.put("size", detailLog.getSize());
				styleObj.put("amount", detailLog.getAmount());
				styleObj.put("orderDegree", detailLog.getOrderDegree());

				List<Map<String, Object>> rfidTagList = new ArrayList<Map<String, Object>>();

				for(StorageScheduleSubDetailLog subDetailLog : detailLog.getStorageScheduleSubDetailLog()){

					Map<String, Object> tagObj = new HashMap<String, Object>();

					tagObj.put("rfidTag", subDetailLog.getRfidTag());

					rfidTagList.add(tagObj);
				}

				styleObj.put("rfidTagList", rfidTagList);

				styleList.add(styleObj);
			}

			obj.put("list", styleList);

		} else {

			ReleaseScheduleLog releaseScheduleLog = releaseScheduleLogRepository.findByBoxInfoBoxSeq(boxSeq);

			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();

			for(ReleaseScheduleDetailLog detailLog : releaseScheduleLog.getReleaseScheduleDetailLog()){

				Map<String, Object> styleObj = new HashMap<String, Object>();

				styleObj.put("style", detailLog.getStyle());
				styleObj.put("color", detailLog.getColor());
				styleObj.put("size", detailLog.getSize());
				styleObj.put("amount", detailLog.getAmount());

				List<Map<String, Object>> rfidTagList = new ArrayList<Map<String, Object>>();

				for(ReleaseScheduleSubDetailLog subDetailLog : detailLog.getReleaseScheduleSubDetailLog()){

					Map<String, Object> tagObj = new HashMap<String, Object>();

					tagObj.put("rfidTag", subDetailLog.getRfidTag());

					rfidTagList.add(tagObj);
				}

				styleObj.put("rfidTagList", rfidTagList);

				styleList.add(styleObj);
			}

			obj.put("list", styleList);
		}


		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findBarcode(String barcode) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		BoxInfo boxInfo = boxInfoRepository.findByBarcode(barcode);

		if(boxInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BOX);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BOX_MESSAGE);
			return obj;
		}

		if(boxInfo.getStat().equals("2")){
			obj.put("resultCode", ApiResultConstans.MAPPING_BOX);
			obj.put("resultMessage", ApiResultConstans.MAPPING_BOX_MESSAGE);
			return obj;
		}

		if(boxInfo.getStat().equals("3")){
			obj.put("resultCode", ApiResultConstans.COMPLETE_BOX);
			obj.put("resultMessage", ApiResultConstans.COMPLETE_BOX_MESSAGE);
			return obj;
		}

		if(boxInfo.getStat().equals("4")){
			obj.put("resultCode", ApiResultConstans.DISUSE_BOX);
			obj.put("resultMessage", ApiResultConstans.DISUSE_BOX_MESSAGE);
			return obj;
		}

		Specifications<StoreStorage> specifications = Specifications
				.where(StoreStorageSpecification.companySeqEqual(boxInfo.getStartCompanyInfo().getCompanySeq()))
				.and(StoreStorageSpecification.stockAmountGreaterThan(Long.valueOf(0)))
				.and(StoreStorageSpecification.totalAmountGreaterThan(Long.valueOf(0)));

		List<StoreStorage> storeStorageList = storeStorageRepository.findAll(specifications);

		if (storeStorageList.size() == 0) {
			obj.put("resultCode", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT);
			obj.put("resultMessage", ApiResultConstans.NOT_ENOUGH_STOCK_AMOUNT_MESSAGE);
			return obj;
		}

		ArrayList<Map<String, Object>> bartagList = new ArrayList<Map<String, Object>>();
		for (StoreStorage storage : storeStorageList) {

			Map<String, Object> tempMap = new HashMap<String, Object>();

			tempMap.put("erpKey", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getErpKey());
			tempMap.put("style", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStyle());
			tempMap.put("color", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getColor());
			tempMap.put("size", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getSize());
			tempMap.put("orderDegree", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getOrderDegree());
			tempMap.put("additionOrderDegree", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getAdditionOrderDegree());
			tempMap.put("startRfidSeq", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getStartRfidSeq());
			tempMap.put("endRfidSeq", storage.getDistributionStorage().getProductionStorage().getBartagMaster().getEndRfidSeq());
			tempMap.put("stockAmount", storage.getStat2Amount());

			bartagList.add(tempMap);
		}

		obj.put("boxInfo", boxInfo);
		obj.put("bartagList", bartagList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> save(BoxInfo boxInfo) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		Long count = boxInfoRepository.countByBarcode(boxInfo.getBarcode());

		if(count > 0){
			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);
			return obj;
		}

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			boxInfo.setRegUserInfo(regUserInfo);
			boxInfo.setRegDate(new Date());

			boxInfoRepository.save(boxInfo);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> update(BoxInfo boxInfo) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		BoxInfo tempBoxInfo = boxInfoRepository.findByBarcode(boxInfo.getBarcode());

		if(tempBoxInfo != null &&
				boxInfo.getBarcode().equals(tempBoxInfo.getBarcode()) &&
				boxInfo.getBoxSeq().compareTo(tempBoxInfo.getBoxSeq()) != 0){
			obj.put("resultCode", ApiResultConstans.DUPLICATION_BOX_BARCODE);
			obj.put("resultMessage", ApiResultConstans.DUPLICATION_BOX_BARCODE_MESSAGE);
			return obj;
		}

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo updUserInfo = new UserInfo();
			updUserInfo.setUserSeq(user.getUserSeq());
			boxInfo.setUpdUserInfo(updUserInfo);
			boxInfo.setUpdDate(new Date());

			boxInfoRepository.save(boxInfo);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StyleModel> returnStyleCountStoreList(Long boxSeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());

		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer query = new StringBuffer();

		query.append("select style, LEFT(return_another_code,3) as color, RIGHT(return_another_code,3) as size, return_order_li_no as order_degree, ");
		query.append("return_amount AS count, erp_store_return_schedule_seq as style_seq ");
		query.append("from erp_store_return_schedule a inner join ");
		query.append("box_info b on a.return_order_no = b.barcode and b.box_seq = :boxSeq ");

		params.put("boxSeq", boxSeq);

		return nameTemplate.query(query.toString(), params, new BoxStyleCountRowMapper());
	}
}