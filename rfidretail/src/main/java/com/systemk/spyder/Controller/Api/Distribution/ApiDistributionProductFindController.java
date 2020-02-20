package com.systemk.spyder.Controller.Api.Distribution;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Service.DistributionStorageService;

@RestController
@RequestMapping("/api/distributionRfidTag")
public class ApiDistributionProductFindController {

	@Autowired
	private DistributionStorageService distributionStorageService;

	/**
	 * 스타일, 컬러, 사이즈 전달
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/findProduct")
	public ResponseEntity<Map<String, Object>> findProduct(@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		obj.put("styleList", distributionStorageService.findStyleList());
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
}
