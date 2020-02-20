package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Service.RfidTagHistoryService;

@RestController
@RequestMapping("/rfidTagHistory")
public class RfidTagHistoryController {

	@Autowired
	private RfidTagHistoryService rfidTagHistoryService;
	
	/**
	 * 태그 히스토리 페이징
	 * @param pageable
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{barcode}")
	public Page<RfidTagHistory> findAll(@PageableDefault(sort = {"rfidTagHistorySeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
										@PathVariable(value = "barcode", required = true) String barcode) throws Exception {
		return rfidTagHistoryService.findAll(barcode, pageable);
	}
	
	/**
	 * 태그 상세 이력 조회
	 * @param rfidTag
	 * @return@throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/search/{rfidTag}")
	public List<RfidTagHistory> findRfidTagHistory(@PathVariable(value = "rfidTag", required = true) String rfidTag) throws Exception{
		return rfidTagHistoryService.findByRfidTag(rfidTag);
	}
}
