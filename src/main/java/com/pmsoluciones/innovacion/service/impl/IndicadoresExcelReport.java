package com.pmsoluciones.innovacion.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pmsoluciones.innovacion.service.ReportAbstract;

@Service
public class IndicadoresExcelReport extends ReportAbstract{

	public ResponseEntity<ByteArrayResource> exportToExcel() throws Exception {
        newReportExcel();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // write sheet, title & header
        String[] headers = new String[]{"No", "username", "Password", "Roles", "Permission", "Active", "Bloked", "Created By", "Created Date", "Update By", "Update Date"};
        writeTableHeaderExcel("Sheet User", "Report User", headers);

        Object data = "String";

        writeTableData(data);

        outputStream.close();
        
        
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProductTemplate.xlsx");
        workbook.write(outputStream);
        workbook.close();
        return new ResponseEntity<>(new ByteArrayResource(outputStream.toByteArray()),
                header, HttpStatus.CREATED);
        
        
        
    }
	
	public void writeTableData(Object data)  throws Exception {
     
        // font style content
        CellStyle style = getFontContentExcel();

        // starting write on row
        int startRow = 2;

        // write content
        
        for (int i = 0; i < 11; i++) {
        	Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
            createCell(row, columnCount++, "test", style);
		}
        
//        for (Object UserDTO : list) {
//            Row row = sheet.createRow(startRow++);
//            int columnCount = 0;
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//            createCell(row, columnCount++, "test", style);
//
//        }
    }
	
}
