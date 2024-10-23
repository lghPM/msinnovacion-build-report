package com.pmsoluciones.innovacion.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;

public abstract class ReportAbstract {
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;

	public void newReportExcel() {
		workbook = new XSSFWorkbook();
	}

	public HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		return response;
	}

	public void writeTableHeaderExcel(List<String> headers, Integer rowPos, Integer mergeCols) {
		this.writeTableHeaderExcel(headers, rowPos, mergeCols, true);
	}

//    GENERA LOS HEADER
	public void writeTableHeaderExcel(List<String> headers, Integer posRow, Integer mergeCols, boolean isMerge) {

		// sheet
//        sheet = workbook.createSheet(sheetName);
		Row row = sheet.createRow(posRow);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);

		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);

		Integer celPos = 0;

		if (headers.size() == 1) {
			createCell(row, celPos, headers.get(0), style);
			if (isMerge) {
				CellRangeAddress region = new CellRangeAddress(posRow, posRow, celPos, celPos + mergeCols);
        		RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        		RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        		RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        		RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
				sheet.addMergedRegion(region);


				celPos = celPos + mergeCols + 1;
			}
		} else {
			for (String tituloH : headers) {
				createCell(row, celPos, tituloH, style);

				if (celPos > 0 && isMerge) {					
					CellRangeAddress region = new CellRangeAddress(posRow, posRow, celPos, celPos + mergeCols);
					sheet.addMergedRegion(region);
					RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
	        		RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
	        		RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
	        		RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
					celPos = celPos + mergeCols + 1;
				} else {
					celPos += 1;
				}

			}
		}

	}

	public Cell createCell(org.apache.poi.ss.usermodel.Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof BigDecimal) {
			cell.setCellValue((String) value.toString());
		} else {
			cell.setCellValue((String) value);
		}
		 cell.setCellStyle(style);
		 
		 return cell;
	}

	public CellStyle getFontContentExcel() {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		return style;
	}
}
