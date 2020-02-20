package com.systemk.spyder.Controller.Api.Web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Repository.Main.InventoryScheduleHeaderRepository;
import com.systemk.spyder.Service.InventoryScheduleService;
import com.systemk.spyder.Service.CustomBean.Group.InventoryScheduleGroupModel;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/inventorySchedule")
public class InventoryScheduleController {

	@Autowired
	private InventoryScheduleService inventoryScheduleService;
	
	@Autowired
	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;
	
	/**
	 * 재고조사 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/inventoryGroup")
	public ResponseEntity<Map<String,Object>> groupList(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
															  @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
															  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
															  @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
															  @RequestParam(value = "search", required = false, defaultValue = "") String search,
															  @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		List<InventoryScheduleGroupModel> groupList = inventoryScheduleService.findGroupList(startDate, endDate, companySeq, pageable);
		Long totaElements = inventoryScheduleService.CountGroupList(startDate, endDate, companySeq);
		
		obj.put("content", groupList);
		
		obj = PagingUtil.pagingModel(pageable, totaElements, obj);
		
		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}
	
	/**
	 * 재고조사 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<InventoryScheduleHeader>> findAll(@PageableDefault(sort = {"inventoryScheduleHeaderSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "createDate", required = false, defaultValue = "") String createDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "confirmYn", required = false, defaultValue = "all") String confirmYn,
												 @RequestParam(value = "completeYn", required = false, defaultValue = "all") String completeYn,
												 @RequestParam(value = "disuseYn", required = false, defaultValue = "all") String disuseYn,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		
		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		
		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("sales") || SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("special")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}
		
		return new ResponseEntity<Page<InventoryScheduleHeader>>(inventoryScheduleService.findAll(createDate, companySeq, confirmYn, completeYn, disuseYn, pageable), HttpStatus.OK);
	}
	
	
	/**
	 * 재고조사 상세 조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public ResponseEntity<InventoryScheduleHeader> findOne(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return new ResponseEntity<InventoryScheduleHeader>(inventoryScheduleHeaderRepository.findOne(seq), HttpStatus.OK); 
	}
}
