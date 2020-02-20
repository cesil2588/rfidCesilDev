package com.systemk.spyder.Controller.Api.Production;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Dto.Request.ReleaseUpdateBean;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;

@RestController
@RequestMapping("/api/productionRfidTag")
public class ApiReleaseController {

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	/**
	 * 모바일 출고 검수 후 ERP 업데이트, 물류 입고 예정 정보 추가
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseList")
	public ResponseEntity<Map<String, Object>> releaseList(@RequestBody ReleaseBean releaseBean) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.releaseComplete(releaseBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 모바일 출고 M/W 프로세서 테스트
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseMiddleWareTest")
	public ResponseEntity<Map<String, Object>> releaseMiddleWareTest(@RequestBody ReleaseBean releaseBean) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.releaseMiddleWareTest(releaseBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 모바일 출고 검수 후 ERP 업데이트, 물류 입고 예정 정보 추가(테이블 저장 후 호출 방식으로 변경)
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseComplete")
	public ResponseEntity<Map<String, Object>> releaseComplete(@RequestBody Map<String, Object> obj) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.releaseComplete(Long.valueOf(obj.get("seq").toString()));

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}


	/**
	 * 상품 출고 수정 > 기존, 신규 바코드 검색
	 * @param releaseUpdateBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/barcodeValid")
	public ResponseEntity<Map<String, Object>> barcodeValid(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
															@RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.barcodeValid(barcode, type);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 상품 출고 수정
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseUpdate")
	public ResponseEntity<Map<String, Object>> releaseUpdate(@RequestBody ReleaseUpdateBean releaseUpdateBean) throws Exception{

		Map<String, Object> map = productionStorageRfidTagService.releaseUpdate(releaseUpdateBean);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
