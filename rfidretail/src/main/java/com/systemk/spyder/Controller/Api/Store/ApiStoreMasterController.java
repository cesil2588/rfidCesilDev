package com.systemk.spyder.Controller.Api.Store;

import com.systemk.spyder.Service.ProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/store/master")
public class ApiStoreMasterController {

	private final ProductMasterService productMasterService;

	@Autowired
	public ApiStoreMasterController(ProductMasterService productMasterService) {
		this.productMasterService = productMasterService;
	}

	/**
	 * ProductMaster 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findMaster(@RequestParam(value = "version", required = false, defaultValue = "") String version,
														  @RequestParam(value = "appType", required = false, defaultValue = "") String appType) throws Exception{
		return new ResponseEntity<Map<String, Object>>(productMasterService.findByMasterList(version, appType), HttpStatus.OK);
	}
}