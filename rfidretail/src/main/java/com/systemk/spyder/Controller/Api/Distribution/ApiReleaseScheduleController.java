package com.systemk.spyder.Controller.Api.Distribution;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.Request.DistributionReleaseCompleteBean;
import com.systemk.spyder.Dto.Request.ReferenceBean;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

@RestController
@RequestMapping("/api/distributionRfidTag")
public class ApiReleaseScheduleController {

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	/**
	 * 물류출고예정번호 검색(POST 방식)
	 * @param referenceNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseSchedule")
	public ResponseEntity<Map<String, Object>> releaseSchedulePost(@RequestBody ReferenceBean referenceBean) throws Exception{
		return new ResponseEntity<Map<String, Object>>(distributionStorageRfidTagService.findByReleaseSchedulePost(referenceBean), HttpStatus.OK);
	}

	/**
	 * 물류 출고 검수
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseComplete")
	public ResponseEntity<Map<String, Object>> releaseComplete(@RequestBody DistributionReleaseCompleteBean distributionReleaseCompleteBean) throws Exception{
		return new ResponseEntity<Map<String, Object>>(distributionStorageRfidTagService.releaseComplete(distributionReleaseCompleteBean), HttpStatus.OK);
	}

	/**
	 * 물류 출고 검수 더미 생성
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/releaseCompleteDummy")
	public ResponseEntity<Map<String, Object>> releaseCompleteDummy(@RequestBody ReferenceBean referenceBean) throws Exception{
		return new ResponseEntity<Map<String, Object>>(distributionStorageRfidTagService.releaseCompleteDummy(referenceBean), HttpStatus.OK);
	}

	/**
	 * ERP 출고실적 업데이트, 매장 입고 예정 정보 추가(사용 안함)
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/releaseCompleteAfter")
	public ResponseEntity<Map<String, Object>> releaseCompleteAfter(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode) throws Exception{
		return new ResponseEntity<Map<String, Object>>(distributionStorageRfidTagService.releaseCompleteAfter(barcode), HttpStatus.OK);
	}

	/**
	 * 불일치 RFID TAG값의 스타일, 컬러, 사이즈 정보 보내주기
	 * @param rfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/findMasterNonCompleteTag")
	public ResponseEntity<Map<String, Object>> findMasterNonCompeteTag(@RequestParam(value = "rfidTag", required = false, defaultValue = "") String rfidTag) throws Exception{
		Map<String, Object> obj = distributionStorageRfidTagService.findMasterNonCompleteTag(rfidTag);
		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
}
