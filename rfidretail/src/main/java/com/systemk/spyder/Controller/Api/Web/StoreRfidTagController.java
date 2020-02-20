package com.systemk.spyder.Controller.Api.Web;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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

import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;
import com.systemk.spyder.Repository.Main.StoreStorageRfidTagRepository;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import com.systemk.spyder.Service.StoreStorageRfidTagService;

@RestController
@RequestMapping("/storeRfidTag")
public class StoreRfidTagController {

	@Autowired
	private StoreStorageRfidTagService storeStorageRfidTagService;
	
	@Autowired
	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;
	
	@Autowired
	private ReleaseScheduleLogService releaseScheduleLogService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/findTag")
	public List<StoreStorageRfidTag> findTag(@RequestParam(value = "seq", required = true) long seq) throws Exception{
		return storeStorageRfidTagRepository.findByStoreStorageSeq(seq);
	}
	
	/**
	 * 판매 재고 상세 RFID 리스트 조회
	 * @param seq
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<Page<StoreStorageRfidTag>> findTagList(@PathVariable(value = "seq", required = true) long seq,
																	  @PageableDefault(sort = {"rfidTagSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
																	  @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
																	  @RequestParam(value = "search", required = false, defaultValue = "") String search,
																	  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return new ResponseEntity<Page<StoreStorageRfidTag>>(storeStorageRfidTagService.findAll(seq, stat, search, option, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 판매 재고 상세 RFID 상태별 조회
	 * @param seq
	 * @param stat
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stat")
	public ResponseEntity<Page<StoreStorageRfidTag>> findTagStatList(@RequestParam(value = "seq", required = true) long seq,
														 @RequestParam(value = "stat", required = true) String stat,
														 @PageableDefault(sort = {"rfidSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) throws Exception {
		return new ResponseEntity<Page<StoreStorageRfidTag>>(storeStorageRfidTagService.findStat(seq, stat, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 전체 입고 검수
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inspectionAll/{seq}")
	public void inspectionAll(@PathVariable(value = "seq", required = true) long seq) throws Exception{
		storeStorageRfidTagService.inspectionAll(seq);
	}
	
	/**
	 * 판매 입고 수량 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count")
	public ResponseEntity<String> getStoreStatCount(@RequestParam(value = "seq", required = true) long seq) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("totalAmount", storeStorageRfidTagRepository.countByStoreStorageSeq(seq));
		obj.put("stat1Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "1"));
		obj.put("stat2Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "2"));
		obj.put("stat3Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "3"));
		obj.put("stat4Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "4"));
		obj.put("stat5Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "5"));
		obj.put("stat6Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "6"));
		obj.put("stat7Amount", storeStorageRfidTagRepository.countByStoreStorageSeqAndStat(seq, "7"));
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 판매 입고 박스 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleComplete")
	public ResponseEntity<Map<String, Object>> storeScheduleComplete(
								@RequestBody List<Map<String, Object>> barcodeList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = releaseScheduleLogService.storeScheduleComplete(barcodeList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 판매 입고 작업 완료 처리
	 * @param boxBarcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/storeScheduleGroupComplete")
	public ResponseEntity<Map<String, Object>> storeScheduleGroupComplete(
								@RequestBody List<Map<String, Object>> scheduleGroupList,
								@RequestParam(value = "userSeq", required = false, defaultValue = "0") Long userSeq,
								@RequestParam(value = "type", required = false, defaultValue = "3") String type) throws Exception{
		
		Map<String, Object> map = releaseScheduleLogService.storeScheduleGroupComplete(scheduleGroupList, userSeq, type);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
