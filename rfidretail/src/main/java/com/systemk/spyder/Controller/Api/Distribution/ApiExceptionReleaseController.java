package com.systemk.spyder.Controller.Api.Distribution;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

@RestController
@RequestMapping("/api/distributionRfidTag")
public class ApiExceptionReleaseController {

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	/**
	 * 해외입고 박스 정보 조회 후 해당 업체 입고 스타일 전달
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/exceptionReleaseSchedule")
	public ResponseEntity<Map<String, Object>> exceptionReleaseSchedule(@RequestParam(value = "barcode", required = false, defaultValue = "") String barcode) throws Exception{

		Map<String, Object> map = distributionStorageRfidTagService.exceptionReleaseSchedule(barcode);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * 해외입고정보 추가
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/exceptionRelease/{flag}")
	public ResponseEntity<Map<String, Object>> exceptionRelease(@RequestBody ReleaseBean releaseBean,
														 @PathVariable(value = "flag") String flag) throws Exception{

		Map<String, Object> obj = distributionStorageRfidTagService.exceptionReleaseComplete(releaseBean, flag);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
}
