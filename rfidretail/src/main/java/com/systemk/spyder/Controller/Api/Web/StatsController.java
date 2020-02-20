package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Service.StatsService;
import com.systemk.spyder.Service.CustomBean.CompanyBean;
import com.systemk.spyder.Service.CustomBean.Stats.BartagPieGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.CompanyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.CompanyStyleGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.DailyBartagOrderGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.DailyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ProductionReleaseGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ProductionStyleGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.ReleaseCompanyGroupStats;
import com.systemk.spyder.Service.CustomBean.Stats.StorageCompanyGroupStats;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/stats")
public class StatsController {
	
	@Autowired
	private StatsService statsService;
	
	@RequestMapping(method = RequestMethod.GET, value="/dailyGroupStats")
	public ResponseEntity<List<DailyGroupStats>> findDailyGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
				  							   		 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate) throws Exception {
		return new ResponseEntity<List<DailyGroupStats>>(statsService.findDailyGroupStats(startDate, endDate), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value="/companyGroupStats")
	public ResponseEntity<List<CompanyGroupStats>> findCompanyGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  	 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate) throws Exception {
		return new ResponseEntity<List<CompanyGroupStats>>(statsService.findCompanyGroupStats(startDate, endDate), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/companyStyleGroupStats")
	public ResponseEntity<List<CompanyStyleGroupStats>> findCompanyStyleGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  			   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								   					  			   @RequestParam(value = "companySeq", required = false) Long companySeq) throws Exception {
		return new ResponseEntity<List<CompanyStyleGroupStats>>(statsService.findCompanyStyleGroupStats(startDate, endDate, companySeq), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/bartagPieGroupStats")
	public ResponseEntity<List<BartagPieGroupStats>> findBartagPieGroupStats(@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
								   					  			   @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
								   					  			   @RequestParam(value = "companySeq", required = false) Long companySeq) throws Exception {
		return new ResponseEntity<List<BartagPieGroupStats>>(statsService.findBartagPieGroupStats(productYy, productSeason, companySeq), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/companyStyle")
	public ResponseEntity<List<CompanyBean>> findCompanyStyle() throws Exception {
		return new ResponseEntity<List<CompanyBean>>(statsService.findCompanyStyle(), HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/dailyBartagOrderGroupStats")
	public ResponseEntity<List<DailyBartagOrderGroupStats>> findDailyBartagOrderGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
				  							   		 					   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
				  							   		 					   @RequestParam(value = "companySeq", required = false) Long companySeq) throws Exception {
		return new ResponseEntity<List<DailyBartagOrderGroupStats>>(statsService.findDailyBartagOrderGroupStats(startDate, endDate, companySeq), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/productionStyleGroupStats")
	public ResponseEntity<List<ProductionStyleGroupStats>> findProductionyStyleGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  					  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								   					  					  @RequestParam(value = "companySeq", required = false) Long companySeq) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<List<ProductionStyleGroupStats>>(statsService.findProductionStyleGroupStats(startDate, endDate, companySeq), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/productionReleaseGroupStats")
	public ResponseEntity<List<ProductionReleaseGroupStats>> findProductionReleaseGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  					  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								   					  					  @RequestParam(value = "companySeq", required = false) Long companySeq) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<List<ProductionReleaseGroupStats>>(statsService.findProductionReleaseGroupStats(startDate, endDate, companySeq), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/distributionStorageCompanyGroupStats")
	public ResponseEntity<List<StorageCompanyGroupStats>> distributionStorageCompanyGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  					  						  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								   					  					  						  @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType) throws Exception {
		
		return new ResponseEntity<List<StorageCompanyGroupStats>>(statsService.findDistributionStorageCompanyGroupStats(startDate, endDate, searchType), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/distributionReleaseCompanyGroupStats")
	public ResponseEntity<List<ReleaseCompanyGroupStats>> distributionReleaseCompanyGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  					  						  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate) throws Exception {
		
		return new ResponseEntity<List<ReleaseCompanyGroupStats>>(statsService.generateDistributionReleaseCompanyGroupStats(startDate, endDate), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/storeStorageCompanyGroupStats")
	public ResponseEntity<List<StorageCompanyGroupStats>> storeStorageCompanyGroupStats(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
								   					  					  						  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
								   					  					  						  @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType) throws Exception {
		
		return new ResponseEntity<List<StorageCompanyGroupStats>>(statsService.findStoreStorageCompanyGroupStats(startDate, endDate, searchType), HttpStatus.OK);
	}
}
