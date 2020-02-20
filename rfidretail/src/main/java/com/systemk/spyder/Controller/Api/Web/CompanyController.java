package com.systemk.spyder.Controller.Api.Web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Service.CompanyService;
import com.systemk.spyder.Service.RedisService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyInfoRepository companyInfoRepository;
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<CompanyInfo>> getCompanyList(@PageableDefault(sort = {"companySeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
															@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
															@RequestParam(value = "type", required = false, defaultValue = "") String type,
															@RequestParam(value = "search", required = false, defaultValue = "") String search,
			  											  	@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<CompanyInfo>>(companyService.findAll(companySeq, type, search, option, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CompanyInfo> addCompany(@RequestBody CompanyInfo companyInfo) throws Exception {

		CompanyInfo tempCompanyInfo = companyInfoRepository.findByCode(companyInfo.getCode());
		if(tempCompanyInfo != null){
			return new ResponseEntity<CompanyInfo>(companyInfo, HttpStatus.CONFLICT);
		}
		
		companyInfo.setUseYn("Y");
		companyInfo.setRegDate(new Date());
		companyInfo.setErpYn("N");
		
		companyInfo = companyInfoRepository.save(companyInfo);
		
		companyService.saveAll();
		
        return new ResponseEntity<CompanyInfo>(companyInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CompanyInfo> modCompany(@RequestBody CompanyInfo companyInfo) throws Exception {
		
		CompanyInfo tempCompanyInfo = companyInfoRepository.findByCode(companyInfo.getCode());
		
		if(tempCompanyInfo != null && 
			companyInfo.getCode().equals(tempCompanyInfo.getCode()) &&
			companyInfo.getCompanySeq() != tempCompanyInfo.getCompanySeq()){
			return new ResponseEntity<CompanyInfo>(companyInfo, HttpStatus.CONFLICT);
		}
		
		companyInfo.setUpdDate(new Date());
		
		companyInfo = companyInfoRepository.save(companyInfo);
		
		companyService.saveAll();
		
        return new ResponseEntity<CompanyInfo>(companyInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<CompanyInfo> delCompany(@RequestBody CompanyInfo companyInfo) throws Exception {

		companyInfo.setUseYn("N");
		
		companyInfo = companyInfoRepository.save(companyInfo);
		
		companyService.saveAll();
		
        return new ResponseEntity<CompanyInfo>(companyInfo, HttpStatus.OK);
    }
	
	/*
	@RequestMapping(value = "/getCompanyList", method = RequestMethod.GET)
	public List<CompanyInfo> getCompanyListAll(@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception {
		if(type.equals("")){
			return companyInfoRepository.findAllByOrderByNameAsc();
		} else {
			return companyInfoRepository.findByTypeOrderByNameAsc(type);
		}
	}
	*/
	@RequestMapping(value = "/getCompanyList", method = RequestMethod.GET)
	public String getCompanyListAll() throws Exception {
		return companyService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public CompanyInfo getCompanyInfo(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return companyInfoRepository.findOne(seq); 
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customerCode/{customerCode}")
	public CompanyInfo customerInfo(@PathVariable(value = "customerCode", required = false) String customerCode) throws Exception {
		return companyInfoRepository.findByCustomerCode(customerCode);
	}
}
