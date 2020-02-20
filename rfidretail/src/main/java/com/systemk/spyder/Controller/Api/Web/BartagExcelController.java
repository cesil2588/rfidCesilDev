package com.systemk.spyder.Controller.Api.Web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.Key.RfidAc18IfKey;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.CustomBean.Group.BartagGroupModel;
import com.systemk.spyder.Util.SecurityUtil;

@RestController
@RequestMapping("/bartag/bartagExcel")
public class BartagExcelController {

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	/**
	 * 개별 엑셀 다운로드
	 * @param seq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{seq}")
	public AbstractXlsView bartagExcelDownload(@PathVariable(value = "seq", required = true) long seq) throws Exception {

		BartagMaster bartagMaster = bartagMasterRepository.findOne(seq);
//		RfidTagMaster rfidTag = rfidTagMasterRepository.findTopByBartagSeq(seq);
		String location = "1";

		return new AbstractXlsView() {

			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바택 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);

				String sCurTime = null;
		        sCurTime = bartagMaster.getCreateDate();

		        String seqStr = bartagService.bartagDownloadSeq(sCurTime + "-" + bartagMaster.getProductionCompanyInfo().getCode(), SecurityUtil.getCustomUser());

		        String excelName = sCurTime + "-" + bartagMaster.getProductionCompanyInfo().getCode() + "-" + seqStr;

		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {

		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            }

		            columnIndex++;
		        }

		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("생산업체명");
		        row.createCell(7).setCellValue("생산공장명");
		        row.createCell(8).setCellValue("ERPKey");
		        row.createCell(9).setCellValue("시즌");
		        row.createCell(10).setCellValue("오더차수");
		        row.createCell(11).setCellValue("생산업체코드");
		        row.createCell(12).setCellValue("발행장소");
		        row.createCell(13).setCellValue("발행날짜");
		        row.createCell(14).setCellValue("발행차수");
		        row.createCell(15).setCellValue("일련번호 시작");
		        row.createCell(16).setCellValue("일련번호 끝");
		        row.createCell(17).setCellValue("발행수량");

		        int rowIndex = 1;

		        row = worksheet.createRow(rowIndex);
		        row.createCell(0).setCellValue(bartagMaster.getCreateDate());
		        row.createCell(1).setCellValue(bartagMaster.getSeq());
		        row.createCell(2).setCellValue(bartagMaster.getLineSeq());
	            row.createCell(3).setCellValue(bartagMaster.getStyle());
	            row.createCell(4).setCellValue(bartagMaster.getColor());
	            row.createCell(5).setCellValue(bartagMaster.getSize());
	            row.createCell(6).setCellValue(bartagMaster.getProductionCompanyInfo().getName());
	            row.createCell(7).setCellValue(bartagMaster.getDetailProductionCompanyName());
	            row.createCell(8).setCellValue(bartagMaster.getErpKey());
	            row.createCell(9).setCellValue(bartagMaster.getProductRfidYySeason());
	            String subOrder = (bartagMaster.getOrderDegree()).substring(1, bartagMaster.getOrderDegree().length());
	            row.createCell(10).setCellValue(subOrder);
	            row.createCell(11).setCellValue(bartagMaster.getProductionCompanyInfo().getCode());
		        row.createCell(12).setCellValue(location);
		        row.createCell(13).setCellValue("");
		        row.createCell(14).setCellValue("");
		        row.createCell(15).setCellValue(bartagMaster.getStartRfidSeq());
		        row.createCell(16).setCellValue(bartagMaster.getEndRfidSeq());
		        row.createCell(17).setCellValue(bartagMaster.getAmount());

		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	System.out.println("browser - " + browser);
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "ISO-8859-1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }
			}

		};
	}

	/**
	 * 등록일자별 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/groupDate")
	public AbstractXlsView excelGroupDate(@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
										  @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate)throws Exception{

		return new AbstractXlsView() {

			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

				Long companySeq = Long.valueOf(0);
				String location = "1";

				List<RfidAc18If> rfidAc18IfList = new ArrayList<RfidAc18If>();
				List<BartagMaster> bartagList = bartagService.findAll(startDate, endDate, companySeq);

				if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("production")){
					companySeq = SecurityUtil.getCustomUser().getCompanyInfo().getCompanySeq();
				}

				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바택 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);

		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {

		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            }

		            columnIndex++;
		        }

		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("생산업체명");
		        row.createCell(7).setCellValue("생산공장명");
		        row.createCell(8).setCellValue("ERPKey");
		        row.createCell(9).setCellValue("시즌");
		        row.createCell(10).setCellValue("오더차수");
		        row.createCell(11).setCellValue("생산업체코드");
		        row.createCell(12).setCellValue("발행장소");
		        row.createCell(13).setCellValue("발행날짜");
		        row.createCell(14).setCellValue("발행차수");
		        row.createCell(15).setCellValue("일련번호 시작");
		        row.createCell(16).setCellValue("일련번호 끝");
		        row.createCell(17).setCellValue("발행수량");

		        int rowIndex = 1;

		        String createDate = bartagList.get(0).getCreateDate();

		        String seqStr = bartagService.bartagDownloadSeq(createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode(), SecurityUtil.getCustomUser());

		        String excelName = createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode() + "-" + seqStr;

		        for(BartagMaster vo : bartagList) {

		        	int cellIndex = 0;

			        row = worksheet.createRow(rowIndex++);
			        row.createCell(cellIndex++).setCellValue(vo.getCreateDate());
			        row.createCell(cellIndex++).setCellValue(vo.getSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getLineSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getStyle());
			        row.createCell(cellIndex++).setCellValue(vo.getColor());
			        row.createCell(cellIndex++).setCellValue(vo.getSize());
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getName());
			        row.createCell(cellIndex++).setCellValue(vo.getDetailProductionCompanyName());
			        row.createCell(cellIndex++).setCellValue(vo.getErpKey());
			        row.createCell(cellIndex++).setCellValue(vo.getProductRfidYySeason());
			        String subOrder = (vo.getOrderDegree()).substring(1, vo.getOrderDegree().length());
			        row.createCell(cellIndex++).setCellValue(subOrder);
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getCode());
			        row.createCell(cellIndex++).setCellValue(location);
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue(vo.getStartRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getEndRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getAmount());

		        	if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("publish")){
		        		if (vo.getBartagStartDate() == null || (vo.getBartagStartDate()).equals("")){
		        			RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
			        		rfidAc18IfKey.setAc18Crdt(vo.getCreateDate());
			        		rfidAc18IfKey.setAc18Crsq(new BigDecimal(vo.getSeq()));
			        		rfidAc18IfKey.setAc18Crno(new BigDecimal(vo.getLineSeq()));
			        		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

			        		rfidAc18.setAc18Sndt(new Date());
		        			rfidAc18.setAc18Snyn("Y");
		        			rfidAc18IfList.add(rfidAc18);

		        			vo.setStat("3");
		        			vo.setBartagStartDate(new Date());
		        		}
		        	}
		        }

		        rfidAc18IfRepository.save(rfidAc18IfList);
		        bartagMasterRepository.save(bartagList);

		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }

			}
		};
	}

	/**
	 * 발행일자별 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/date")
	public AbstractXlsView excelDate(@RequestParam(value = "regDate", required = false, defaultValue = "") String regDate,
									 @RequestParam(value = "companySeq", required = false, defaultValue = "0") Long companySeq,
									 @RequestParam(value = "subCompanyName", required = false, defaultValue = "") String subCompanyName,
									 @RequestParam(value = "defaultDateType", required = false, defaultValue = "1") String defaultDateType,
									 @RequestParam(value = "completeYn", required = false, defaultValue = "") String completeYn)throws Exception{

		return new AbstractXlsView() {

			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

	    		String location = "1";

	    		List<RfidAc18If> rfidAc18IfList = new ArrayList<RfidAc18If>();
				List<BartagMaster> bartagList = bartagService.findAll(regDate, companySeq, defaultDateType, subCompanyName, completeYn);

				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바택 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);

		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {

		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            }

		            columnIndex++;
		        }

		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("생산업체명");
		        row.createCell(7).setCellValue("생산공장명");
		        row.createCell(8).setCellValue("ERPKey");
		        row.createCell(9).setCellValue("시즌");
		        row.createCell(10).setCellValue("오더차수");
		        row.createCell(11).setCellValue("생산업체코드");
		        row.createCell(12).setCellValue("발행장소");
		        row.createCell(13).setCellValue("발행날짜");
		        row.createCell(14).setCellValue("발행차수");
		        row.createCell(15).setCellValue("일련번호 시작");
		        row.createCell(16).setCellValue("일련번호 끝");
		        row.createCell(17).setCellValue("발행수량");

		        int rowIndex = 1;

		        String createDate = bartagList.get(0).getCreateDate();

		        String seqStr = bartagService.bartagDownloadSeq(createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode(), SecurityUtil.getCustomUser());

		        String excelName = createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode() + "-" + seqStr;

		        for(BartagMaster vo : bartagList) {

		        	int cellIndex = 0;

			        row = worksheet.createRow(rowIndex++);
			        row.createCell(cellIndex++).setCellValue(vo.getCreateDate());
			        row.createCell(cellIndex++).setCellValue(vo.getSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getLineSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getStyle());
			        row.createCell(cellIndex++).setCellValue(vo.getColor());
			        row.createCell(cellIndex++).setCellValue(vo.getSize());
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getName());
			        row.createCell(cellIndex++).setCellValue(vo.getDetailProductionCompanyName());
			        row.createCell(cellIndex++).setCellValue(vo.getErpKey());
			        row.createCell(cellIndex++).setCellValue(vo.getProductRfidYySeason());
			        String subOrder = (vo.getOrderDegree()).substring(1, vo.getOrderDegree().length());
			        row.createCell(cellIndex++).setCellValue(subOrder);
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getCode());
			        row.createCell(cellIndex++).setCellValue(location);
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue(vo.getStartRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getEndRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getAmount());

		        	if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("publish")){
		        		if (vo.getBartagStartDate() == null || (vo.getBartagStartDate()).equals("")){
		        			RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
			        		rfidAc18IfKey.setAc18Crdt(vo.getCreateDate());
			        		rfidAc18IfKey.setAc18Crsq(new BigDecimal(vo.getSeq()));
			        		rfidAc18IfKey.setAc18Crno(new BigDecimal(vo.getLineSeq()));
			        		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

		        			rfidAc18.setAc18Sndt(new Date());
		        			rfidAc18.setAc18Snyn("Y");

		        			rfidAc18IfList.add(rfidAc18);

		        			vo.setStat("3");
		        			vo.setBartagStartDate(new Date());
		        		}
		        	}
		        }

		        rfidAc18IfRepository.save(rfidAc18IfList);
		        bartagMasterRepository.save(bartagList);

		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }

			}
		};
	}

	/**
	 * 체크된 바택 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/check")
	public AbstractXlsView excelCheck(@RequestBody List<BartagMaster> bartagList)throws Exception{

		return new AbstractXlsView() {

			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

				String location = "1";

				List<RfidAc18If> rfidAc18IfList = new ArrayList<RfidAc18If>();

				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바택 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);

		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {

		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            }

		            columnIndex++;
		        }

		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("생산업체명");
		        row.createCell(7).setCellValue("생산공장명");
		        row.createCell(8).setCellValue("ERPKey");
		        row.createCell(9).setCellValue("시즌");
		        row.createCell(10).setCellValue("오더차수");
		        row.createCell(11).setCellValue("생산업체코드");
		        row.createCell(12).setCellValue("발행장소");
		        row.createCell(13).setCellValue("발행날짜");
		        row.createCell(14).setCellValue("발행차수");
		        row.createCell(15).setCellValue("일련번호 시작");
		        row.createCell(16).setCellValue("일련번호 끝");
		        row.createCell(17).setCellValue("발행수량");

		        int rowIndex = 1;

		        String createDate = bartagList.get(0).getCreateDate();

		        String seqStr = bartagService.bartagDownloadSeq(createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode(), SecurityUtil.getCustomUser());

		        String excelName = createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode() + "-" + seqStr;

		        for(BartagMaster vo : bartagList) {

		        	if(vo.getGenerateSeqYn().equals("N")) {
		        		continue;
		        	}

		        	int cellIndex = 0;

			        row = worksheet.createRow(rowIndex++);
			        row.createCell(cellIndex++).setCellValue(vo.getCreateDate());
			        row.createCell(cellIndex++).setCellValue(vo.getSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getLineSeq());
			        row.createCell(cellIndex++).setCellValue(vo.getStyle());
			        row.createCell(cellIndex++).setCellValue(vo.getColor());
			        row.createCell(cellIndex++).setCellValue(vo.getSize());
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getName());
			        row.createCell(cellIndex++).setCellValue(vo.getDetailProductionCompanyName());
			        row.createCell(cellIndex++).setCellValue(vo.getErpKey());
			        row.createCell(cellIndex++).setCellValue(vo.getProductRfidYySeason());
			        String subOrder = (vo.getOrderDegree()).substring(1, vo.getOrderDegree().length());
			        row.createCell(cellIndex++).setCellValue(subOrder);
			        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getCode());
			        row.createCell(cellIndex++).setCellValue(location);
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue("");
			        row.createCell(cellIndex++).setCellValue(vo.getStartRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getEndRfidSeq());
		        	row.createCell(cellIndex++).setCellValue(vo.getAmount());

		        	if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("publish")){
		        		if (vo.getBartagStartDate() == null || (vo.getBartagStartDate()).equals("")){

		        			RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
			        		rfidAc18IfKey.setAc18Crdt(vo.getCreateDate());
			        		rfidAc18IfKey.setAc18Crsq(new BigDecimal(vo.getSeq()));
			        		rfidAc18IfKey.setAc18Crno(new BigDecimal(vo.getLineSeq()));
			        		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

		        			rfidAc18.setAc18Sndt(new Date());
		        			rfidAc18.setAc18Snyn("Y");

		        			rfidAc18IfList.add(rfidAc18);

		        			vo.setStat("3");
		        			vo.setBartagStartDate(new Date());
		        		}
		        	}
		        }

		        rfidAc18IfRepository.save(rfidAc18IfList);
		        bartagMasterRepository.save(bartagList);

		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }

			}
		};
	}

	/**
	 * 체크된 바택 그룹 엑셀다운로드
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/groupCheck")
	public AbstractXlsView excelGroupCheck(@RequestBody List<BartagGroupModel> groupList)throws Exception{

		return new AbstractXlsView() {

			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

				List<RfidAc18If> rfidAc18IfList = new ArrayList<RfidAc18If>();

				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바택 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);

		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);

		        row = worksheet.createRow(0);

		        int columnIndex = 0;
		        while (columnIndex < 18) {

		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            }

		            columnIndex++;
		        }

		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("발행일자");
		        row.createCell(1).setCellValue("일련번호");
		        row.createCell(2).setCellValue("라인번호");
		        row.createCell(3).setCellValue("스타일");
		        row.createCell(4).setCellValue("컬러");
		        row.createCell(5).setCellValue("사이즈");
		        row.createCell(6).setCellValue("생산업체명");
		        row.createCell(7).setCellValue("생산공장명");
		        row.createCell(8).setCellValue("ERPKey");
		        row.createCell(9).setCellValue("시즌");
		        row.createCell(10).setCellValue("오더차수");
		        row.createCell(11).setCellValue("생산업체코드");
		        row.createCell(12).setCellValue("발행장소");
		        row.createCell(13).setCellValue("발행날짜");
		        row.createCell(14).setCellValue("발행차수");
		        row.createCell(15).setCellValue("일련번호 시작");
		        row.createCell(16).setCellValue("일련번호 끝");
		        row.createCell(17).setCellValue("발행수량");

		        int rowIndex = 1;
		        int count = 0;

		        String createDate = "";
		        String seqStr = "";
		        String excelName = "";
		        String location = "1";

		        for(BartagGroupModel group : groupList){

		        	List<BartagMaster> bartagList = bartagService.findAll(group.getRegDate(), group.getRegDate(), group.getCompanySeq(), group.getProductYy(), group.getProductSeason());

		        	if(count == 0){
		        		createDate = bartagList.get(0).getCreateDate();
		        		seqStr = bartagService.bartagDownloadSeq(createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode(), SecurityUtil.getCustomUser());
		        		excelName = createDate + "-" + bartagList.get(0).getProductionCompanyInfo().getCode() + "-" + seqStr;

		        		count ++;
		        	}

		        	for(BartagMaster vo : bartagList) {

			        	int cellIndex = 0;

				        row = worksheet.createRow(rowIndex++);
				        row.createCell(cellIndex++).setCellValue(vo.getCreateDate());
				        row.createCell(cellIndex++).setCellValue(vo.getSeq());
				        row.createCell(cellIndex++).setCellValue(vo.getLineSeq());
				        row.createCell(cellIndex++).setCellValue(vo.getStyle());
				        row.createCell(cellIndex++).setCellValue(vo.getColor());
				        row.createCell(cellIndex++).setCellValue(vo.getSize());
				        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getName());
				        row.createCell(cellIndex++).setCellValue(vo.getDetailProductionCompanyName());
				        row.createCell(cellIndex++).setCellValue(vo.getErpKey());
				        row.createCell(cellIndex++).setCellValue(vo.getProductRfidYySeason());
				        String subOrder = (vo.getOrderDegree()).substring(1, vo.getOrderDegree().length());
				        row.createCell(cellIndex++).setCellValue(subOrder);
				        row.createCell(cellIndex++).setCellValue(vo.getProductionCompanyInfo().getCode());
				        row.createCell(cellIndex++).setCellValue(location);
				        row.createCell(cellIndex++).setCellValue("");
				        row.createCell(cellIndex++).setCellValue("");
				        row.createCell(cellIndex++).setCellValue(vo.getStartRfidSeq());
			        	row.createCell(cellIndex++).setCellValue(vo.getEndRfidSeq());
			        	row.createCell(cellIndex++).setCellValue(vo.getAmount());

			        	if(SecurityUtil.getCustomUser().getCompanyInfo().getRoleInfo().getRole().equals("publish")){
			        		if (vo.getBartagStartDate() == null || (vo.getBartagStartDate()).equals("")){

			        			RfidAc18IfKey rfidAc18IfKey = new RfidAc18IfKey();
				        		rfidAc18IfKey.setAc18Crdt(vo.getCreateDate());
				        		rfidAc18IfKey.setAc18Crsq(new BigDecimal(vo.getSeq()));
				        		rfidAc18IfKey.setAc18Crno(new BigDecimal(vo.getLineSeq()));
				        		RfidAc18If rfidAc18 = rfidAc18IfRepository.findOne(rfidAc18IfKey);

			        			rfidAc18.setAc18Sndt(new Date());
			        			rfidAc18.setAc18Snyn("Y");

			        			rfidAc18IfList.add(rfidAc18);

			        			vo.setStat("3");
			        			vo.setBartagStartDate(new Date());
			        		}
			        	}
			        }

		        	bartagMasterRepository.save(bartagList);
		        }

		        rfidAc18IfRepository.save(rfidAc18IfList);

		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        	} else {
		        		downName = new String(excelName.getBytes("UTF-8"), "8859_1");
		        	}

		        	response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
		        	response.setContentType(getContentType());
		        	response.setHeader("Content-Transfer-Encoding", "binary;");

		        } catch (UnsupportedEncodingException e) {
		        	e.printStackTrace();
		        }

			}
		};
	}
}
