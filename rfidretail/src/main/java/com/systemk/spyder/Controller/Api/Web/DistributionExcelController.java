package com.systemk.spyder.Controller.Api.Web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.systemk.spyder.Entity.External.RfidIb01If;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.CustomBean.ReturnBoxModel;

@RestController
@RequestMapping("/distribution/distributionExcel")
public class DistributionExcelController {
	
	@Autowired
	private DistributionStorageLogService distributionStorageLogService;
	

	/**
	 * 해당월 박스 입고내역 엑셀 다운로드
	 * @param startDate, endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/completeBoxInfo")
	public AbstractXlsView findCompleteBoxInfo() throws Exception{
		
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		String endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+"";
		String nowMonth = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		String startDate = nowMonth + "01";
		String endDate = nowMonth + endDay;
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
								
				List<RfidIb01If> rfidIb01IfList = distributionStorageLogService.findCompleteBoxInfo(startDate, endDate);
				
				
				//String nowMonth = calendar.get(Calendar.MONTH)+1+"월 입고 내역";
				String nowMonth = calendar.get(Calendar.MONTH)+1+"";
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet(nowMonth);
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
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 6000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 14) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 15) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 16) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 17) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            }
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        String[] headerName = {"입고일","박스번호","라인번호","입고타입","생산업체명","창고명","스타일","컬러","사이즈","오더차수","수량","입고일시"};
		        for(int i=0;i<headerName.length;i++) {
		        	row.createCell(i).setCellValue(headerName[i]);
		        }
		        
		         
		        int rowIndex = 1;
		        		        
		        String excelName = nowMonth;
		        
		        for(RfidIb01If vo : rfidIb01IfList) {
		        	
		        	int cellIndex = 0;
		        				        
			        row = worksheet.createRow(rowIndex++);
			        row.createCell(cellIndex++).setCellValue(vo.getKey().getLb01Ipdt());
			        row.createCell(cellIndex++).setCellValue(vo.getKey().getLb01Bxno());
			        row.createCell(cellIndex++).setCellValue(vo.getKey().getLb01Ipsno().doubleValue());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Emgb());
			        //row.createCell(cellIndex++).setCellValue(companyInfoRepository.findByCustomerCode(vo.getLb01Cgcd()).getName());
			        //row.createCell(cellIndex++).setCellValue(companyInfoRepository.findByCustomerCode(vo.getLb01Prod()).getName());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Cgcd());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Prod());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Styl());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01It06()); 
			        row.createCell(cellIndex++).setCellValue(vo.getLb01It07());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Jjch());
			        row.createCell(cellIndex++).setCellValue(vo.getLb01Ipqt().doubleValue());
		        	row.createCell(cellIndex++).setCellValue(vo.getLb01Endt().toString());
		        }
		        
		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		//downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        		downName = excelName;
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
	 * 해당월 박스 반품내역 엑셀 다운로드
	 * @param startDate, endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/completeReturnBoxInfo")
	public AbstractXlsView findReturnCompleteBoxInfo() throws Exception{
		
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		String endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+"";
		String nowMonth = new SimpleDateFormat("yyyyMM", Locale.KOREA).format(new Date());
		String startDate = nowMonth + "01";
		String endDate = nowMonth + endDay;
		
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
								
				List<ReturnBoxModel> findReturnCompleteBoxList = distributionStorageLogService.findReturnCompleteBoxInfo(startDate, endDate);
				
				
				//String nowMonth = calendar.get(Calendar.MONTH)+1+"월 입고 내역";
				String nowMonth = calendar.get(Calendar.MONTH)+1+"";
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet(nowMonth);
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
		                worksheet.setColumnWidth(columnIndex, 6000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 6000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 4000);
		            } else if (columnIndex == 6){
		            	worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 4000);
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
		                worksheet.setColumnWidth(columnIndex, 5000);
		            }
		            
		            columnIndex++;
		        }
		        
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        String[] headerName = {"미결번호","수량","스타일","컬러","사이즈","실적 날짜","반품 매장"};
		        for(int i=0;i<headerName.length;i++) {
		        	row.createCell(i).setCellValue(headerName[i]);
		        }
		        
		         
		        int rowIndex = 1;
		        		        
		        String excelName = nowMonth;
		        
		        for(ReturnBoxModel vo : findReturnCompleteBoxList) {
		        	
		        	int cellIndex = 0;
		        				        
			        row = worksheet.createRow(rowIndex++);
			        row.createCell(cellIndex++).setCellValue(vo.getBarcode());
			        row.createCell(cellIndex++).setCellValue(vo.getAmount());
			        row.createCell(cellIndex++).setCellValue(vo.getStyle());
			        row.createCell(cellIndex++).setCellValue(vo.getColor());
			        row.createCell(cellIndex++).setCellValue(vo.getSize());
			        row.createCell(cellIndex++).setCellValue(vo.getCompleteDate());
			        row.createCell(cellIndex++).setCellValue(vo.getCompanyName());
		        }
		        
		        try {
		        	String downName = null;
		        	String browser = request.getHeader("User-Agent");
		        	if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
		        		//downName = URLEncoder.encode(excelName,"UTF-8").replaceAll("\\+", "%20");
		        		downName = excelName;
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
}
