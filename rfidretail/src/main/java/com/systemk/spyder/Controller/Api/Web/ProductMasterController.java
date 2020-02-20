package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Service.ProductMasterService;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

@RestController
@RequestMapping("/productMaster")
public class ProductMasterController {
	
	@Autowired
	private ProductMasterRepository productMasterRepository;
	
	@Autowired
	private ProductMasterService productMasterService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProductMaster>> findAll(@PageableDefault(sort = {"productSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
													   @RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
													   @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
													   @RequestParam(value = "style", required = false, defaultValue = "") String style,
													   @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  										   @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		return new ResponseEntity<Page<ProductMaster>>(productMasterService.findAll(productYy, productSeason, style, search, option, pageable), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ProductMaster findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return productMasterRepository.findOne(seq); 
	}

	@RequestMapping(method = RequestMethod.GET, value = "/selectList")
	public List<SelectGroupBy> findSelectList() throws Exception {
		return productMasterService.findSelectGroupBy();
	}
}
