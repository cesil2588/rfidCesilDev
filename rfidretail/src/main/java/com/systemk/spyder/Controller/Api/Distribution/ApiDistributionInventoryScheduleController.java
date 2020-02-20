package com.systemk.spyder.Controller.Api.Distribution;

import com.fasterxml.jackson.annotation.JsonView;
import com.systemk.spyder.Dto.Request.InventoryScheduleBean;
import com.systemk.spyder.Dto.Request.InventoryScheduleListBean;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.View.View;
import com.systemk.spyder.Service.InventoryScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/distributionRfidTag")
public class ApiDistributionInventoryScheduleController {

	@Autowired
	private InventoryScheduleService inventoryScheduleService;

	/**
	 * 재고조사 내역 목록 조회
	 * @param pageable
	 * @param startDate
	 * @param endDate
	 * @param companySeq
	 * @param confirmYn
	 * @param completeYn
	 * @param disuseYn
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileList.class)
	@RequestMapping(method = RequestMethod.GET, value = "/inventorySchedule")
	public ResponseEntity<Page<InventoryScheduleHeader>> distributionInventoryScheduleList(@PageableDefault(sort = {"inventoryScheduleHeaderSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
																						   @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
																						   @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
																						   @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																						   @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn,
																						   @RequestParam(value = "completeYn", required = false, defaultValue = "all") String completeYn,
																						   @RequestParam(value = "disuseYn", required = false, defaultValue = "all") String disuseYn,
																						   @RequestParam(value = "type", required = false, defaultValue = "") String type) throws Exception{
		return new ResponseEntity<Page<InventoryScheduleHeader>>(inventoryScheduleService.findAll(startDate, endDate, companySeq, confirmYn, completeYn, disuseYn, type, pageable), HttpStatus.OK);
	}

	/**
	 * 재고조사 내역 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/inventorySchedule/{seq}")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleDetail(@PathVariable(value = "seq", required = true) Long seq) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.detail(seq);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 재고 조사 등록
	 * @param inventoryScheduleBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inventorySchedule")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleSave(@RequestBody InventoryScheduleBean inventoryScheduleBean) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.save(inventoryScheduleBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 재고 조사 수정
	 * @param inventoryScheduleBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/inventorySchedule")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleUpdate(@RequestBody InventoryScheduleBean inventoryScheduleBean) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.update(inventoryScheduleBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 재고 조사 삭제
	 * @param inventoryScheduleListBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/inventorySchedule")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleDelete(@RequestBody InventoryScheduleListBean inventoryScheduleListBean) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.delete(inventoryScheduleListBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 재고조사 확정
	 * @param inventoryScheduleListBean
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inventoryScheduleComplete")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleComplete(@RequestBody InventoryScheduleListBean inventoryScheduleListBean) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.complete(inventoryScheduleListBean);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 재고조사 대상 목록 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@JsonView(View.MobileDetail.class)
	@RequestMapping(method = RequestMethod.GET, value = "/inventoryScheduleStorage")
	public ResponseEntity<Map<String, Object>> distributionInventoryScheduleStorage(@RequestParam(value = "userSeq", required = false) Long userSeq,
																		@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception{

		Map<String, Object> obj = inventoryScheduleService.distributionStorageDetail(userSeq, companySeq);

		return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
	}
}
