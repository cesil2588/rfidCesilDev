package com.systemk.spyder.Service;

import java.util.List;

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

public interface StatsService {
	
	public List<DailyGroupStats> findDailyGroupStats(String startDate, String endDate) throws Exception;

	public List<CompanyGroupStats> findCompanyGroupStats(String startDate, String endDate) throws Exception;
	
	public List<CompanyBean> findCompanyStyle() throws Exception;
	
	public List<CompanyStyleGroupStats> findCompanyStyleGroupStats(String startDate, String endDate, Long companySeq) throws Exception;
	
	public List<BartagPieGroupStats> findBartagPieGroupStats(String productYy, String producySeason, Long companySeq) throws Exception;
	
	public List<DailyBartagOrderGroupStats> findDailyBartagOrderGroupStats(String startDate, String endDate, Long companySeq) throws Exception;
	
	public List<ProductionStyleGroupStats> findProductionStyleGroupStats(String startDate, String endDate, Long companySeq) throws Exception;
	
	public List<ProductionReleaseGroupStats> findProductionReleaseGroupStats(String startDate, String endDate, Long companySeq) throws Exception;
	
	public List<StorageCompanyGroupStats> findDistributionStorageCompanyGroupStats(String startDate, String endDate, String searchType) throws Exception;
	
	public List<ReleaseCompanyGroupStats> generateDistributionReleaseCompanyGroupStats(String startDate, String endDate) throws Exception;
	
	public List<ReleaseCompanyDetailGroupStats> findDistributionReleaseCompanyDetailGroupStats(String startDate, String endDate) throws Exception;
	
	public List<StorageCompanyGroupStats> findStoreStorageCompanyGroupStats(String startDate, String endDate, String searchType) throws Exception;
}
