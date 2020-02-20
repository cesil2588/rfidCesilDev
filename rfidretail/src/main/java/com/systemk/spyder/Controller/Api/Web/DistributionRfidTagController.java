package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.json.JSONObject;
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

import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

@RestController
@RequestMapping("/distributionRfidTag")
public class DistributionRfidTagController {

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;
	
	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/findTag")
	public List<DistributionStorageRfidTag> findTag(@RequestParam(value = "seq", required = true) long seq) throws Exception{
		return distributionStorageRfidTagRepository.findByDistributionStorageSeq(seq);
	}
	
	/**
	 * 물류 재고 상세 RFID 리스트 조회
	 * @param seq
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<Page<DistributionStorageRfidTag>> findTagList(@PathVariable(value = "seq", required = true) long seq,
																	  @PageableDefault(sort = {"rfidTagSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
																	  @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
																	  @RequestParam(value = "search", required = false, defaultValue = "") String search,
																	  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return new ResponseEntity<Page<DistributionStorageRfidTag>>(distributionStorageRfidTagService.findAll(seq, stat, search, option, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 물류 재고 상세 RFID 상태별 조회
	 * @param seq
	 * @param stat
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stat")
	public ResponseEntity<Page<DistributionStorageRfidTag>> findTagStatList(@RequestParam(value = "seq", required = true) long seq,
														 @RequestParam(value = "stat", required = true) String stat,
														 @PageableDefault(sort = {"rfidSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) throws Exception {
		return new ResponseEntity<Page<DistributionStorageRfidTag>>(distributionStorageRfidTagService.findStat(seq, stat, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 전체 입고 검수
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inspectionAll/{seq}")
	public void inspectionAll(@PathVariable(value = "seq", required = true) long seq) throws Exception{
		distributionStorageRfidTagService.inspectionAll(seq);
	}
	
	/**
	 * 물류 입고 수량 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count")
	public ResponseEntity<String> getDistributionStatCount(@RequestParam(value = "seq", required = true) long seq) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("totalAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeq(seq));
		obj.put("nonCheckAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "1"));
		obj.put("stockAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "2"));
		obj.put("releaseAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "3"));
		obj.put("reissueAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "4"));
		obj.put("disuseAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "5"));
		obj.put("returnNonCheckAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "6"));
		obj.put("returnAmount", distributionStorageRfidTagRepository.countByDistributionStorageSeqAndStat(seq, "7"));
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 물류출고예정태그 맵핑
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/mapping/select")
	public ResponseEntity<List<DistributionStorageRfidTag>> selectMappingStorageBartag(
																	   @RequestParam(value = "customerCode", required = false, defaultValue = "100000") String customerCode,
																	   @RequestParam(value = "style", required = false, defaultValue = "") String style,
																	   @RequestParam(value = "color", required = false, defaultValue = "") String color,
																	   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		return new ResponseEntity<List<DistributionStorageRfidTag>>(distributionStorageRfidTagService.findAll(customerCode, style, color, styleSize), HttpStatus.OK);
	}
}
