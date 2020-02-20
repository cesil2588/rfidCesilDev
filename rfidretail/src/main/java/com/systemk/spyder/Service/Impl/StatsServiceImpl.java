package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Service.StatsService;
import com.systemk.spyder.Service.CustomBean.CompanyBean;
import com.systemk.spyder.Service.CustomBean.Stats.BartagPieGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.CompanyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.CompanyStyleGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.DailyBartagOrderGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.DailyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ProductionReleaseGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ProductionStyleGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ReleaseCompanyDetailGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ReleaseCompanyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.StorageCompanyGroupStats;
import com.systemk.spyder.Service.Mapper.Stats.BartagPieGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.CompanyBeanRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.CompanyGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.CompanyStyleGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.DailyBartagOrderGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.DailyGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.DistributionStorageCompanyGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.ProductionReleaseGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.ProductionStyleGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.ReleaseCompanyDetailGroupStatsRowMapper;
import com.systemk.spyder.Service.Mapper.Stats.StoreStorageCompanyGroupStatsRowMapper;

@Service
public class StatsServiceImpl implements StatsService {
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Transactional(readOnly = true)
	@Override
	public List<DailyGroupStats> findDailyGroupStats(String startDate, String endDate) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(amount) AS amount, " +
							  "SUM(total_amount) AS total_amount, " +
							  "SUM(stat1_amount) AS stat1_amount, " +
							  "SUM(stat2_amount) AS stat2_amount, " +
							  "SUM(stat3_amount) AS stat3_amount, " +
							  "SUM(stat4_amount) AS stat4_amount, " +
							  "SUM(stat5_amount) AS stat5_amount, " +
							  "SUM(stat6_amount) AS stat6_amount, " +
							  "SUM(stat7_amount) AS stat7_amount, " +
							  "CONVERT(VARCHAR(8), b.reg_date, 112) AS convert_reg_date, " +
							  "c.company_seq, " +
							  "c.name " +
						 "FROM bartag_master b " +
				   "INNER JOIN company_info c " +
				   		   "ON b.production_company_seq = c.company_seq " +
				   		"WHERE CONVERT(VARCHAR(8), b.reg_date, 112) between ? and ? " +
				   		  "AND b.stat > 1 " +
				   	 "GROUP BY CONVERT(VARCHAR(8), b.reg_date, 112), c.company_seq, c.name " +
				   	 "ORDER BY convert_reg_date ";
		
		return template.query(query, new DailyGroupStatsRowMapper(), startDate, endDate);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CompanyGroupStats> findCompanyGroupStats(String startDate, String endDate) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(amount) AS amount, " +
							  "SUM(total_amount) AS total_amount, " +
							  "SUM(stat1_amount) AS stat1_amount, " +
							  "SUM(stat2_amount) AS stat2_amount, " +
							  "SUM(stat3_amount) AS stat3_amount, " +
							  "SUM(stat4_amount) AS stat4_amount, " +
							  "SUM(stat5_amount) AS stat5_amount, " +
							  "SUM(stat6_amount) AS stat6_amount, " +
							  "SUM(stat7_amount) AS stat7_amount, " +
							  "c.name, " +
							  "c.company_seq " +
						 "FROM bartag_master b " +
				   "INNER JOIN company_info c " +
				   		   "ON b.production_company_seq = c.company_seq " +
				   		"WHERE CONVERT(VARCHAR(8), b.reg_date, 112) between ? and ? " +
				   		  "AND b.stat > 1 " +
				   	 "GROUP BY c.name,  c.company_seq " +
					 "ORDER BY c.name ASC ";
		return template.query(query, new CompanyGroupStatsRowMapper(), startDate, endDate);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CompanyStyleGroupStats> findCompanyStyleGroupStats(String startDate, String endDate, Long companySeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(amount) AS amount, " +
							  "SUM(total_amount) AS total_amount, " +
							  "SUM(stat1_amount) AS stat1_amount, " +
							  "SUM(stat2_amount) AS stat2_amount, " +
							  "SUM(stat3_amount) AS stat3_amount, " +
							  "SUM(stat4_amount) AS stat4_amount, " +
							  "SUM(stat5_amount) AS stat5_amount, " +
							  "SUM(stat6_amount) AS stat6_amount, " +
							  "SUM(stat7_amount) AS stat7_amount, " +
							  "CONVERT(VARCHAR(8), b.reg_date, 112) AS convert_reg_date, " +
							  "b.style, " +
							  "b.color, " +
							  "b.size, " +
							  "b.bartag_seq, " + 
							  "c.name, " +
							  "c.company_seq " +
						 "FROM bartag_master b " +
				   "INNER JOIN company_info c " + 
				   		   "ON b.production_company_seq = c.company_seq " +
				   		"WHERE CONVERT(VARCHAR(8), b.reg_date, 112) BETWEEN ? AND ? " +
				   		  "AND c.company_seq = ? " +
				   		  "AND b.stat > 1 " +
				   	 "GROUP BY CONVERT(VARCHAR(8), b.reg_date, 112), b.style, b.color, b.size, b.bartag_seq, c.name, c.company_seq " +
				   	 "ORDER BY convert_reg_date";
		
		return template.query(query, new CompanyStyleGroupStatsRowMapper(), startDate, endDate, companySeq);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<BartagPieGroupStats> findBartagPieGroupStats(String productYy, String productSeason, Long companySeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT SUM(bm.amount) AS amount, ");
		query.append("SUM(bm.total_amount) AS total_amount, ");
		query.append("SUM(bm.stat1_amount) AS stat1_amount, ");
		query.append("SUM(bm.stat2_amount) AS stat2_amount, ");
		query.append("SUM(bm.stat3_amount) AS stat3_amount, ");
		query.append("SUM(bm.stat4_amount) AS stat4_amount, ");
		query.append("SUM(bm.stat5_amount) AS stat5_amount, ");
		query.append("SUM(bm.stat6_amount) AS stat6_amount, ");
		query.append("SUM(bm.stat7_amount) AS stat7_amount, ");
		query.append("bm.product_yy, ");
		query.append("bm.product_season, ");
		query.append("ci.name, ");
		query.append("ci.company_seq ");
		query.append("FROM bartag_master bm ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bm.production_company_seq = ci.company_seq ");
		
		if(!productYy.equals("all")){
			query.append("AND bm.product_yy = :productYy ");
			params.put("productYy", productYy);
		}
		
		if(!productSeason.equals("all")){
			query.append("AND bm.product_season = :productSeason ");
			params.put("productSeason", productSeason);
		}
		
		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		} 
		
		query.append("AND bm.stat > 1 ");
		
		query.append("GROUP BY bm.product_yy, bm.product_season, ci.name, ci.company_seq ");
		query.append("ORDER BY bm.product_yy ");
		
		return nameTemplate.query(query.toString(), params, new BartagPieGroupStatsRowMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CompanyBean> findCompanyStyle() throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT c.name, " +
							  "c.company_seq " +
					    "FROM bartag_master b " +
				  "INNER JOIN company_info c " + 
				  		  "ON b.production_company_seq = c.company_seq " +
				  	"GROUP BY c.name, c.company_seq " +
				  	"ORDER BY c.name";
		
		return template.query(query, new CompanyBeanRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<DailyBartagOrderGroupStats> findDailyBartagOrderGroupStats(String startDate, String endDate, Long companySeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT SUM(order_amount) AS order_amount, ");
		query.append("SUM(non_check_complete_amount) AS non_check_complete_amount, ");
		query.append("SUM(complete_amount) AS complete_amount,  ");
		query.append("SUM(addition_amount) AS addition_amount, ");
		query.append("SUM(non_check_addition_amount) AS non_check_addition_amount, ");
		query.append("bo.create_date, ");
		query.append("ci.company_seq, ");
		query.append("ci.name ");
		query.append("FROM bartag_order bo ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON bo.production_company_seq = ci.company_seq ");
		query.append("WHERE bo.create_date between :startDate and :endDate ");
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		if(companySeq != 0){
			query.append("AND ci.company_seq = :companySeq ");
			params.put("companySeq", companySeq);
		}
		
		query.append("GROUP BY bo.create_date, ci.company_seq, ci.name ");
		query.append("ORDER BY bo.create_date  ");
		
		return nameTemplate.query(query.toString(), params, new DailyBartagOrderGroupStatsRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductionStyleGroupStats> findProductionStyleGroupStats(String startDate, String endDate, Long companySeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT SUM(ps.total_amount) AS total_amount, " +
							  "SUM(ps.non_check_amount) AS stat1_amount,  " +
							  "SUM(ps.stock_amount) AS stat2_amount,  " +
							  "SUM(ps.release_amount) AS stat3_amount,  " +
							  "SUM(ps.reissue_amount) AS stat4_amount,  " +
							  "SUM(ps.disuse_amount) AS stat5_amount,  " +
							  "SUM(ps.return_non_check_amount) AS stat6_amount, " +
							  "SUM(ps.return_amount) AS stat7_amount,  " +
							  "CONVERT(VARCHAR(8), ps.reg_date, 112) AS convert_reg_date, " +
							  "b.style, " +
							  "b.color, " +
							  "b.size, " +
							  "ps.production_storage_seq, " + 
							  "c.name, " +
							  "c.company_seq " +
						 "FROM bartag_master b " +
				   "INNER JOIN production_storage ps " +
						   "ON b.bartag_seq = ps.bartag_seq " +
				   "INNER JOIN company_info c " + 
				   		   "ON b.production_company_seq = c.company_seq " +
				   		"WHERE CONVERT(VARCHAR(8), ps.reg_date, 112) BETWEEN ? AND ? " +
				   		  "AND c.company_seq = ? " +
				   	 "GROUP BY CONVERT(VARCHAR(8), ps.reg_date, 112), b.style, b.color, b.size, ps.production_storage_seq, c.name, c.company_seq " +
				   	 "ORDER BY convert_reg_date";
		
		return template.query(query, new ProductionStyleGroupStatsRowMapper(), startDate, endDate, companySeq);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductionReleaseGroupStats> findProductionReleaseGroupStats(String startDate, String endDate, Long companySeq) throws Exception {

		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ");
		query.append("COUNT(*) AS amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'N' AND ssl.complete_yn = 'N' AND ssl.disuse_yn = 'N' THEN ssl.confirm_yn END) AS non_confirm_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' AND ssl.disuse_yn = 'N' THEN ssl.confirm_yn END) AS confirm_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' AND ssl.disuse_yn = 'N' THEN ssl.confirm_yn END) AS complete_amount, ");
		query.append("COUNT(CASE WHEN ssl.disuse_yn = 'Y' THEN ssl.disuse_yn END) AS disuse_amount, ");
		query.append("ci.name, ");
		query.append("ci.company_seq ");
		query.append("FROM storage_schedule_log ssl ");
		query.append("INNER JOIN storage_schedule_detail_log ssdl ON ");
		query.append("ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		query.append("INNER JOIN storage_schedule_sub_detail_log sssdl ON ");
		query.append("ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq ");
		query.append("INNER JOIN box_info bi ON ");
		query.append("ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ON ");
		query.append("bi.start_company_seq = ci.company_seq ");
		
		query.append("WHERE ssl.create_date BETWEEN :startDate AND :endDate ");
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		query.append("AND ci.company_seq = :companySeq ");
		params.put("companySeq", companySeq);
		
		query.append("GROUP BY ci.name, ci.company_seq");
		
		return nameTemplate.query(query.toString(), params, new ProductionReleaseGroupStatsRowMapper());
	}

	@Transactional(readOnly = true)
	@Override
	public List<StorageCompanyGroupStats> findDistributionStorageCompanyGroupStats(String startDate, String endDate, String searchType) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ");
		query.append("COUNT(*) AS total_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'N' AND ssl.disuse_yn = 'N' THEN ssl.confirm_yn END) AS confirm_amount, ");
		query.append("COUNT(CASE WHEN ssl.confirm_yn = 'Y' AND ssl.complete_yn = 'Y' AND ssl.disuse_yn = 'N' THEN ssl.confirm_yn END) AS complete_amount, ");
		query.append("ci.name AS company_name, ");
		query.append("ci.company_seq ");
		query.append("FROM storage_schedule_log ssl ");
		
		if(searchType.equals("style")) {
			query.append("INNER JOIN storage_schedule_detail_log ssdl ON ");
			query.append("ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
		} else if(searchType.equals("tag")) {
			query.append("INNER JOIN storage_schedule_detail_log ssdl ON ");
			query.append("ssl.storage_schedule_log_seq = ssdl.storage_schedule_log_seq ");
			query.append("INNER JOIN storage_schedule_sub_detail_log sssdl ON ");
			query.append("ssdl.storage_schedule_detail_log_seq = sssdl.storage_schedule_detail_log_seq ");
		}
		query.append("INNER JOIN box_info bi ON ");
		query.append("ssl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ON ");
		query.append("bi.start_company_seq = ci.company_seq ");
		
		query.append("WHERE ssl.arrival_date BETWEEN :startDate AND :endDate ");
		query.append("AND ssl.disuse_yn = 'N' ");
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		query.append("GROUP BY ci.name, ci.company_seq");
		
		return nameTemplate.query(query.toString(), params, new DistributionStorageCompanyGroupStatsRowMapper());
	}
	
	@Override
	public List<ReleaseCompanyGroupStats> generateDistributionReleaseCompanyGroupStats(String startDate, String endDate) throws Exception {
		
		List<ReleaseCompanyDetailGroupStats> tempReleaseList = findDistributionReleaseCompanyDetailGroupStats(startDate, endDate);
		
		List<ReleaseCompanyGroupStats> releaseList = new ArrayList<ReleaseCompanyGroupStats>();
		
		for(ReleaseCompanyDetailGroupStats temp : tempReleaseList) {
			
			boolean companyPushFlag = true;
			
			for(ReleaseCompanyGroupStats release : releaseList) {
				if(temp.getCompanySeq().equals(release.getCompanySeq())) {
					companyPushFlag = false;
					
					release.getDetailList().add(temp);
				}
			}
			
			if(companyPushFlag) {
				ReleaseCompanyGroupStats release = new ReleaseCompanyGroupStats();
				release.setCompanySeq(temp.getCompanySeq());
				release.setCompanyName(temp.getCompanyName());
				release.getDetailList().add(temp);
				
				releaseList.add(release);
			}
		}
		
		return releaseList;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ReleaseCompanyDetailGroupStats> findDistributionReleaseCompanyDetailGroupStats(String startDate, String endDate) throws Exception{
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ci.name, ci.company_seq, ess.style, ess.color, ess.size, ess.order_amount, ess.release_amount, ess.complete_amount, ess.rfid_yn ");
		query.append("FROM erp_store_schedule ess ");
		query.append("INNER JOIN company_info ci ");
		query.append("ON ess.end_company_seq = ci.company_seq ");
		
		query.append("WHERE ess.complete_date BETWEEN :startDate AND :endDate ");
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		return nameTemplate.query(query.toString(), params, new ReleaseCompanyDetailGroupStatsRowMapper());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<StorageCompanyGroupStats> findStoreStorageCompanyGroupStats(String startDate, String endDate, String searchType) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource());
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ");
		query.append("COUNT(*) AS total_amount, ");
		query.append("COUNT(CASE WHEN rsl.release_yn = 'Y' AND rsl.complete_yn = 'N' AND rsl.disuse_yn = 'N' THEN rsl.release_yn END) AS release_amount, ");
		query.append("COUNT(CASE WHEN rsl.release_yn = 'Y' AND rsl.complete_yn = 'Y' AND rsl.disuse_yn = 'N' THEN rsl.release_yn END) AS complete_amount, ");
		query.append("ci.name AS company_name, ");
		query.append("ci.company_seq ");
		query.append("FROM release_schedule_log rsl ");
		
		if(searchType.equals("style")) {
			query.append("INNER JOIN release_schedule_detail_log rsdl ON ");
			query.append("rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
		} else if(searchType.equals("tag")) {
			query.append("INNER JOIN release_schedule_detail_log rsdl ON ");
			query.append("rsl.release_schedule_log_seq = rsdl.release_schedule_log_seq ");
			query.append("INNER JOIN release_schedule_sub_detail_log rssdl ON ");
			query.append("rsdl.release_schedule_detail_log_seq = rssdl.release_schedule_detail_log_seq ");
		}
		query.append("INNER JOIN box_info bi ON ");
		query.append("rsl.box_seq = bi.box_seq ");
		query.append("INNER JOIN company_info ci ON ");
		query.append("bi.end_company_seq = ci.company_seq ");
		
		query.append("WHERE rsl.create_date BETWEEN :startDate AND :endDate ");
		query.append("AND rsl.disuse_yn = 'N' ");
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		query.append("GROUP BY ci.name, ci.company_seq");
		
		return nameTemplate.query(query.toString(), params, new StoreStorageCompanyGroupStatsRowMapper());
	}

}
