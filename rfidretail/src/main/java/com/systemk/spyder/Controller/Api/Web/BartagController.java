package com.systemk.spyder.Controller.Api.Web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.view.AbstractView;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BartagOrderService;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.CustomBean.BartagSubCompany;
import com.systemk.spyder.Service.CustomBean.Group.BartagGroupModel;
import com.systemk.spyder.Service.CustomBean.Group.BartagOrderGroupModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;
import com.systemk.spyder.Util.PagingUtil;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/bartag")
public class BartagController {

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	@Autowired
	private RfidTagService rfidTagService;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private BartagOrderService bartagOrderService;

	private final Path rootLocation = Paths.get("d://upload");

	/**
	 * 바택 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/bartagGroup")
	public ResponseEntity<Map<String,Object>> bartagGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {

		Map<String,Object> obj = new HashMap<String,Object>();

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		List<BartagGroupModel> bartagGroupList = bartagService.findBartagGroupList(startDate, endDate, companySeq, search, option, pageable);
		Long totaElements = bartagService.CountBartagGroupList(startDate, endDate, companySeq, search, option);

		obj.put("content", bartagGroupList);

		obj = PagingUtil.pagingModel(pageable, totaElements, obj);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 바택정보 페이징
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Page<BartagMaster> bartagList(@PageableDefault(sort = {"bartagSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
			  								@RequestParam(value = "search", required = false, defaultValue = "") String search,
			  								@RequestParam(value = "option", required = false, defaultValue = "") String option,
			  								@RequestParam(value = "regDate", required = false, defaultValue = "") String regDate,
			  								@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
			  								@RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
			  								@RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn,
			  								@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
			  								@RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
											@RequestParam(value = "style", required = false, defaultValue = "") String style,
											@RequestParam(value = "color", required = false, defaultValue = "") String color,
											@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		return bartagService.findAll(regDate, companySeq, subCompanyName, completeYn, productYy, productSeason, style, color, styleSize, search, option, pageable);
	}

	/**
	 * 발행 수량 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/countAll")
	public ResponseEntity<String> countAll(@RequestParam(value = "search", required = false, defaultValue = "") String search,
										@RequestParam(value = "option", required = false, defaultValue = "") String option,
										@RequestParam(value = "regDate", required = false, defaultValue = "") String regDate,
										@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
										@RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
										@RequestParam(value = "defaultDateType", required = false, defaultValue = "1") String defaultDateType,
										@RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn) throws Exception {

		JSONObject obj = bartagService.countAll(regDate, regDate, companySeq, subCompanyName, defaultDateType, completeYn, search, option);

		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}

	/**
	 * 바택정보 상세조회
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public BartagMaster bartagDetail(@PathVariable(value = "seq", required = true) long seq) throws Exception {
		return bartagMasterRepository.findOne(seq);
	}

	/**
	 * 엑셀다운로드시 바택시작날짜 UPDATE
	 * @param bartagStartDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/bartagDate")
	public ResponseEntity<BartagMaster> bartagDate(@RequestBody BartagMaster barTagParam) throws Exception{
		BartagMaster bartagMaster = bartagMasterRepository.findOne(barTagParam.getBartagSeq());

		RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
		rfidAc18IfKey.setAc18Crdt(bartagMaster.getCreateDate());
		rfidAc18IfKey.setAc18Crsq(new BigDecimal(bartagMaster.getSeq()));
		rfidAc18IfKey.setAc18Crno(new BigDecimal(bartagMaster.getLineSeq()));
		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

		Date startDate = barTagParam.getBartagStartDate();
		if (bartagMaster.getBartagStartDate() == null || (bartagMaster.getBartagStartDate()).equals("")){
			bartagMaster.setStat("3");
			bartagMaster.setBartagStartDate(startDate);
			bartagMasterRepository.save(bartagMaster);
			rfidAc18.setAc18Sndt(startDate);
			rfidAc18.setAc18Snyn("Y");
			rfidAc18IfRepository.save(rfidAc18);
		}

		return new ResponseEntity<BartagMaster>(bartagMaster,HttpStatus.OK);
	}

	/**
	 * 업체정보 조회
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/company/{customerCode}")
	public CompanyInfo companyDetail(@PathVariable(value = "customerCode", required = true) String customerCode) throws Exception {
		return companyInfoRepository.findByCustomerCode(customerCode);
	}

	/**
	 * 발행일자별 엑셀다운로드시 바택발행시작 UPDATE
	 * @param createDate
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/date/{createDate}")
	public void updateCreateDate(@PathVariable(value = "createDate", required = true) String createDate) throws Exception {
		List<BartagMaster> list = bartagMasterRepository.findByCreateDate(createDate);

		Date today = new Date();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getBartagStartDate() == null || ("").equals(list.get(i).getBartagStartDate())) {

				RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
				rfidAc18IfKey.setAc18Crdt(list.get(i).getCreateDate());
				rfidAc18IfKey.setAc18Crsq(new BigDecimal(list.get(i).getSeq()));
				rfidAc18IfKey.setAc18Crno(new BigDecimal(list.get(i).getLineSeq()));

				RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

				list.get(i).setBartagStartDate(today);
				bartagMasterRepository.save(list.get(i));

				rfidAc18.setAc18Sndt(today);
				rfidAc18.setAc18Snyn("Y");
				rfidAc18IfRepository.save(rfidAc18);
			}
		}
	}

	/**
	 * 샘플업로드텍스트
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/filedownload")
	public AbstractView bartagFileDownload()throws Exception{
		return new AbstractView() {
			@Override
			protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
					throws Exception {

				response.setContentType(getContentType());

				File file = new File(rootLocation + "/sample.txt");
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ new String(file.getName().getBytes("UTF-8"), "8859_1") + "\";charset=\"UTF-8\"");
				int read = 0;
				byte[] b = new byte[(int)file.length()];

				if (file.isFile()){
					BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
					BufferedOutputStream outs =	new BufferedOutputStream(response.getOutputStream());
					try {
						while((read=fin.read(b)) != -1){
							outs.write(b, 0, read);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally{
						if (outs != null) {outs.close();}
					    if (fin != null)  {fin.close();}
					}
				}
			}
		};
	}

	/**
	 * 하위 생산공장 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/subCompanyList")
	public ResponseEntity<List<BartagSubCompany>> subCompanyList(@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		return new ResponseEntity<List<BartagSubCompany>>(bartagService.findSubCompanyList(companySeq), HttpStatus.OK);
	}

	/**
	 * 바택 1개 테스트 발행
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/testBartagComplete")
	public void testBartagComplete(@RequestBody List<BartagMaster> bartagList,
								   @RequestParam(value = "publishDegree", required = false, defaultValue = "") String publishDegree,
								   @RequestParam(value = "publishRegDate", required = false, defaultValue = "") String publishRegDate) throws Exception {
		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			rfidTagService.textLoadTestList(publishDegree, publishRegDate, user.getUserSeq(), bartagList);
		}
	}

	/**
	 * 바택 전부 테스트 발행
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/testBartagCompleteAll")
	public void testBartagCompleteAll(@RequestParam(value = "publishDegree", required = false, defaultValue = "") String publishDegree,
									  @RequestParam(value = "publishRegDate", required = false, defaultValue = "") String publishRegDate) throws Exception {

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			rfidTagService.textLoadTestAll(publishDegree, publishRegDate, user.getUserSeq());
		}
	}

	/**
	 * RFID 생산 작업 그룹 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/bartagOrderGroup")
	public ResponseEntity<Map<String,Object>> bartagOrderGroup(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable,
												 @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
												 @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,	//생산 업체 요청으로 추가..20191127..Cesil
												 @RequestParam(value = "searchDate", required = false, defaultValue = "") String searchDate,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {

		Map<String,Object> obj = new HashMap<String,Object>();

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		List<BartagOrderGroupModel> bartagOrderGroupList = bartagOrderService.findBartagOrderGroupList(startDate, endDate, companySeq, searchDate, search, option, pageable, style);
		Long totaElements = bartagOrderService.CountBartagOrderGroupList(startDate, endDate, companySeq, searchDate, search, option, style);

		obj.put("content", bartagOrderGroupList);

		obj = PagingUtil.pagingModel(pageable, totaElements, obj);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * RFID 생산 리스트 조회
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/bartagOrder")
	public ResponseEntity<Page<BartagOrder>> bartagOrder(@PageableDefault(sort = {"updDate"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
												 @RequestParam(value = "date", required = false, defaultValue = "") String date,
												 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
												 @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
												 @RequestParam(value = "stat", required = false, defaultValue = "") String stat,
												 @RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
												 @RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
												 @RequestParam(value = "style", required = false, defaultValue = "") String style,
												 @RequestParam(value = "color", required = false, defaultValue = "") String color,
												 @RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize,
												 @RequestParam(value = "searchDate", required = false, defaultValue = "") String searchDate,
												 @RequestParam(value = "search", required = false, defaultValue = "") String search,
			  									 @RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		//return new ResponseEntity<Page<BartagOrder>>(bartagOrderService.findAll(date, companySeq, detailCompanyName, stat, productYy, productSeason, style, color, styleSize, searchDate, search, option, pageable), HttpStatus.OK);
		return new ResponseEntity<Page<BartagOrder>>(bartagOrderService.findAll(date, companySeq, subCompanyName, stat, productYy, productSeason, style, color, styleSize, searchDate, search, option, pageable), HttpStatus.OK);
	}

	/**
	 * 연도, 시즌, 스타일, 컬러, 사이즈, 오더차수, 추가발주 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/bartagOrder/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectBartagOrder(@PathVariable(value = "flag", required = true) String flag,
																@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
																@RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
																@RequestParam(value = "style", required = false, defaultValue = "") String style,
																@RequestParam(value = "color", required = false, defaultValue = "") String color,
																@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();

		switch(flag){
			case "productYy" :
				selectList = bartagOrderService.selectBartagOrderYy(companySeq);
				break;
			case "productSeason" :
				selectList = bartagOrderService.selectBartagOrderSeason(companySeq, productYy);
				break;
			case "style" :
				selectList = bartagOrderService.selectBartagOrderStyle(companySeq, productYy, productSeason);
				break;
			case "color" :
				selectList = bartagOrderService.selectBartagOrderColor(companySeq, productYy, productSeason, style);
				break;
			case "size" :
				selectList = bartagOrderService.selectBartagOrderSize(companySeq, productYy, productSeason, style, color);
				break;
			case "stylePerCom" :
				selectList = bartagOrderService.selectBartagOrderStylePerCom(companySeq);
				break;
		}

		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}

	/**
	 * 연도, 시즌, 스타일, 컬러, 사이즈, 오더차수, 추가발주 조회
	 * @param companySeq
	 */
	@RequestMapping(method = RequestMethod.GET, value="/select/{flag}")
	public ResponseEntity<List<SelectBartagModel>> selectBartag(@PathVariable(value = "flag", required = true) String flag,
																@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
																@RequestParam(value = "productYy", required = false, defaultValue = "") String productYy,
																@RequestParam(value = "productSeason", required = false, defaultValue = "") String productSeason,
																@RequestParam(value = "style", required = false, defaultValue = "") String style,
																@RequestParam(value = "color", required = false, defaultValue = "") String color,
																@RequestParam(value = "styleSize", required = false, defaultValue = "") String styleSize) throws Exception {

		if(SecurityUtil.getCustomUser().getCompanyInfo() == null) {
			new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
			companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
		}

		List<SelectBartagModel> selectList = new ArrayList<SelectBartagModel>();

		switch(flag){
			case "productYy" :
				selectList = bartagService.selectBartagYy(companySeq);
				break;
			case "productSeason" :
				selectList = bartagService.selectBartagSeason(companySeq, productYy);
				break;
			case "style" :
				selectList = bartagService.selectBartagStyle(companySeq, productYy, productSeason);
				break;
			case "color" :
				selectList = bartagService.selectBartagColor(companySeq, productYy, productSeason, style);
				break;
			case "size" :
				selectList = bartagService.selectBartagSize(companySeq, productYy, productSeason, style, color);
				break;
		}

		return new ResponseEntity<List<SelectBartagModel>>(selectList, HttpStatus.OK);
	}


	/**
	 * RFID 생산 RFID 생산 수량 입력, 추가 수량 입력(미확정)
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/bartagOrder")
	public ResponseEntity<Map<String,Object>> bartagOrderAmount(@RequestBody List<BartagOrder> bartagOrderList) throws Exception {

		Map<String,Object> obj = bartagOrderService.bartagOrderAmount(bartagOrderList);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * RFID 생산 RFID 생산 수량, 추가 수량 확정
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/bartagOrder")
	public ResponseEntity<Map<String,Object>> bartagOrderComplete(@RequestBody List<BartagOrder> bartagOrderList) throws Exception {

		Map<String,Object> obj = bartagOrderService.bartagOrderComplete(bartagOrderList);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * RFID 생산 요청정보 디테일
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/bartagOrder/{seq}")
	public ResponseEntity<Map<String,Object>> bartagOrderDetail(@PathVariable(value = "seq", required = true) Long seq,
																@PageableDefault(sort = {"bartagSeq"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) throws Exception {

		return new ResponseEntity<Map<String,Object>>(bartagOrderService.findBartagOrderDetail(seq, pageable), HttpStatus.OK);
	}


	/**
	 * RFID 생산 요청 하위 생산공장 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/bartagOrder/subCompanyList")
	public ResponseEntity<List<BartagSubCompany>> bartagOrderSubCompanyList(@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq) throws Exception {
		return new ResponseEntity<List<BartagSubCompany>>(bartagOrderService.findSubCompanyList(companySeq), HttpStatus.OK);
	}
	/**
	 * 바택 마스터 삭제
	 * @param bartagMasterList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Map<String,Object>> delete(@RequestBody List<BartagMaster> bartagMasterList) throws Exception {

		Map<String,Object> obj = bartagService.delete(bartagMasterList);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 바택 마스터 수정
	 * @param bartagMasterList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String,Object>> update(@RequestBody List<BartagMaster> bartagMasterList) throws Exception {

		Map<String,Object> obj = bartagService.update(bartagMasterList);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 바택 마스터 확정 완료
	 * @param bartagMasterList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> complete(@RequestBody List<BartagMaster> bartagMasterList) throws Exception {

		Map<String,Object> obj = bartagService.complete(bartagMasterList);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);
	}

	/**
	 * 해당 업체, 스타일, 컬러, 사이즈에 해당하는 태그정보 조회
	 * @param companySeq, style, color, size
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/bartagOrder/bartagListBySku")
	public ResponseEntity<Map<String,Object>> getBartagList (@RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
															 @RequestParam(value = "style", required = false, defaultValue = "") String style,
															 @RequestParam(value = "color", required = false, defaultValue = "") String color,
															 @RequestParam(value = "size", required = false, defaultValue = "") String size) throws Exception {

		Map<String,Object> obj = bartagService.getBartagList(companySeq, style, color, size);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);

	}

	/**
	 * 태그 이력 조회
	 * @param rfidTag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/bartagHistoryList")
	public ResponseEntity<Map<String,Object>> getBartagHistory (@RequestParam(value = "rfidTag", required = false, defaultValue="") String rfidTag) throws Exception {

		Map<String,Object> obj = bartagService.getBartagHistory(rfidTag);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);

	}

	/**
	 * 업체별 스타일, 컬러, 사이즈 정보 가져오기
	 * @param companySeq
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(method = RequestMethod.GET, value="/selectSkuPerCompany")
	public ResponseEntity<Map<String,Object>> selectSkuPerCompany (@RequestParam(value = "companySeq", required = false, defaultValue="0") Long companySeq) throws Exception {

		Map<String,Object> obj = bartagService.findSkuPerComany(companySeq);

		return new ResponseEntity<Map<String,Object>>(obj, HttpStatus.OK);

	}*/
	@RequestMapping(method = RequestMethod.GET, value = "/selectSkuList")
	public List<SelectGroupBy> selectSkuPerCompany(@RequestParam(value = "companySeq", required = false, defaultValue="0") Long companySeq) throws Exception {
		return bartagService.findSkuPerComany(companySeq);
	}

}
