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

import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;
import com.systemk.spyder.Repository.Main.ProductionStorageRfidTagRepository;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.RfidTagReissueRequestService;

@RestController
@RequestMapping("/productionRfidTag")
public class ProductionRfidTagController {

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	@Autowired
	private ProductionStorageRfidTagRepository productionStorageRfidTagRepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/findTag")
	public List<ProductionStorageRfidTag> findTag(@RequestParam(value = "seq", required = true) long seq) throws Exception{
		return productionStorageRfidTagRepository.findByProductionStorageSeq(seq);
	}
	
	/**
	 * 생산 재고 상세 RFID 리스트 조회
	 * @param seq
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<Page<ProductionStorageRfidTag>> findTagList(@PathVariable(value = "seq", required = true) long seq,
																	  @PageableDefault(sort = {"rfidSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
																	  @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
																	  @RequestParam(value = "search", required = false, defaultValue = "") String search,
																	  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return new ResponseEntity<Page<ProductionStorageRfidTag>>(productionStorageRfidTagService.findAll(seq, stat, search, option, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 생산 재고 상세 RFID 상태별 조회
	 * @param seq
	 * @param stat
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stat")
	public ResponseEntity<Page<ProductionStorageRfidTag>> findTagStatList(@RequestParam(value = "seq", required = true) long seq,
														 @RequestParam(value = "stat", required = true) String stat,
														 @PageableDefault(sort = {"rfidSeq"}, direction = Sort.Direction.ASC, size = 100) Pageable pageable) throws Exception {
		return new ResponseEntity<Page<ProductionStorageRfidTag>>(productionStorageRfidTagService.findStat(seq, stat, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 스타일 1개 입고 검수
	 * @param productionStorageRfidTag
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inspectionWeb/{seq}")
	public void inspectionWeb(@PathVariable(value = "seq", required = true) long seq) throws Exception{
		productionStorageRfidTagService.inspectionWeb(seq);
	}
	
	/**
	 * 선택된 스타일 입고 검수
	 * @param seq
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/inspectionWebList")
	public void inspectionWebList(@RequestBody List<ProductionStorage> productionStorageList) throws Exception{
		productionStorageRfidTagService.inspectionWebList(productionStorageList);
	}
	
	/**
	 * 생산업체 입고 수량 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count")
	public ResponseEntity<String> getProductionStatCount(@RequestParam(value = "seq", required = true) long seq) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("totalAmount", productionStorageRfidTagRepository.countByProductionStorageSeq(seq));
		obj.put("nonCheckAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "1"));
		obj.put("stockAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "2"));
		obj.put("releaseAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "3"));
		obj.put("reissueAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "4"));
		obj.put("disuseAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "5"));
		obj.put("returnNonCheckAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "6"));
		obj.put("returnAmount", productionStorageRfidTagRepository.countByProductionStorageSeqAndStat(seq, "7"));
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 재고 태그 리스트
	 * @param seq
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stockTagList")
	public ResponseEntity<Page<ProductionStorageRfidTag>> findStockTagList(
																	  @PageableDefault(sort = {"rfidSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
																	  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		return new ResponseEntity<Page<ProductionStorageRfidTag>>(productionStorageRfidTagService.findAll(companySeq, pageable), HttpStatus.OK); 
	}
	
	/**
	 * 생산출고예정태그 맵핑(테스트)
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/mapping/select")
	public ResponseEntity<List<ProductionStorageRfidTag>> selectMappingStorageBartag(
																	   @RequestParam(value = "style", required = false, defaultValue = "") String style,
																	   @RequestParam(value = "color", required = false, defaultValue = "") String color,
																	   @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {
		
		return new ResponseEntity<List<ProductionStorageRfidTag>>(productionStorageRfidTagService.findAll(style, color, styleSize), HttpStatus.OK);
	}
}
