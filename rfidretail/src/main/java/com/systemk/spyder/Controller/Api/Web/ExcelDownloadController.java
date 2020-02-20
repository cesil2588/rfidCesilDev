package com.systemk.spyder.Controller.Api.Web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractXlsView;

@Controller
public class ExcelDownloadController {
	
	//@Autowired
	//private MailLogRepository mailLogRepository;
	
	@RequestMapping("/excel/downloadAction")
	public AbstractXlsView excelAction(Model model) throws Exception{
		return new AbstractXlsView() {
			
			@SuppressWarnings("deprecation")
			@Override
			protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				HSSFSheet worksheet = (HSSFSheet) workbook.createSheet("바텍 정보");
				worksheet.setGridsPrinted(true);
				worksheet.setFitToPage(true);
				worksheet.setDisplayGuts(true);
				
				String sCurTime = null;
		        sCurTime = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
		     
		        String excelName = sCurTime + "_바텍정보엑셀다운로드.xls";
		        Row row = null;
		        CellStyle style = workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		         
		        // 가장 첫번째 줄에 제목을 만든다.
		        row = worksheet.createRow(0);
		         
		        // 칼럼 길이 설정
		        int columnIndex = 0;
		        while (columnIndex < 14) {
		             
		            if(columnIndex == 0) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 1) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 2) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 3) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 4) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 5) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 6) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 7) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 8) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 9) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 10) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 11) {
		                worksheet.setColumnWidth(columnIndex, 3000);
		            } else if (columnIndex == 12) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } else if (columnIndex == 13) {
		                worksheet.setColumnWidth(columnIndex, 5000);
		            } 		            
		            
		            columnIndex++;
		        }
		         
		        // 헤더 설정
		        row = worksheet.createRow(0);
		        row.createCell(0).setCellValue("순서");
		        row.createCell(1).setCellValue("ERP key");
		        row.createCell(2).setCellValue("시즌");
		        row.createCell(3).setCellValue("오더차수");
		        row.createCell(4).setCellValue("생산업체코드");
		        row.createCell(5).setCellValue("발행장소");
		        row.createCell(6).setCellValue("발행날짜");
		        row.createCell(7).setCellValue("발행차수");
		        row.createCell(8).setCellValue("등록자");
		        row.createCell(9).setCellValue("등록일시");
		        row.createCell(10).setCellValue("발행일자");
		        row.createCell(11).setCellValue("일련번호");
		        row.createCell(12).setCellValue("RFID 일련번호 시작");
		        row.createCell(13).setCellValue("RFID 일력번호 끝");
		         
		        int rowIndex = 1;
		        
		        //TEST DATA
		        row = worksheet.createRow(rowIndex);
	            row.createCell(0).setCellValue(rowIndex);
	            row.createCell(1).setCellValue("ERP0000001");
	            row.createCell(2).setCellValue("18W"); 
	            row.createCell(3).setCellValue("3");
	            row.createCell(4).setCellValue("CD1");
	            row.createCell(5).setCellValue("K");
	            row.createCell(6).setCellValue(new Date());
		        row.createCell(7).setCellValue("3");
		        row.createCell(8).setCellValue("1");
		        row.createCell(9).setCellValue(new Date());
		        row.createCell(10).setCellValue(new Date());
		        row.createCell(11).setCellValue("123456789");
		        row.createCell(12).setCellValue("1");
		        row.createCell(13).setCellValue("100");
		        
		        // 셀 병합 CellRangeAddress(시작 행, 끝 행, 시작 열, 끝 열)
		        worksheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 1, 0, 12));
		         
		        row = worksheet.createRow(rowIndex + 1);
		        row.createCell(0).setCellValue("바텍정보 엑셀 다운로드");
		        row.getCell(0).setCellStyle(style);

		        try {
		            response.setHeader("Content-Disposition", "attachement; filename=\""
		                + java.net.URLEncoder.encode(excelName, "UTF-8") + "\";charset=\"UTF-8\"");
		          } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		          }
			}

		};
	}
	
}

	
