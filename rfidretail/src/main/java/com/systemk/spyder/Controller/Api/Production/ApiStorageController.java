package com.systemk.spyder.Controller.Api.Production;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.Request.InspectionBean;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;

@RestController
@RequestMapping("/api/productionRfidTag")
public class ApiStorageController {

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	/**
	 * 모바일 입고 검수 후 ERP 업데이트
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inspectionList")
	public ResponseEntity<Map<String, Object>> inspectionList(@RequestBody InspectionBean inspectionBean) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.inspectionMobile(inspectionBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 모바일 입고 검수 후 ERP 업데이트(테이블 저장 후 호출 방식으로 변경)
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storageComplete")
	public ResponseEntity<Map<String, Object>> storageComplete(@RequestBody Map<String, Object> obj) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.storageComplete(Long.valueOf(obj.get("seq").toString()));

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
