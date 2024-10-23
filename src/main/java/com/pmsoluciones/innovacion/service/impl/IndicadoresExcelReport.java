package com.pmsoluciones.innovacion.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pmsoluciones.innovacion.dto.FechaProgramadaDto;
import com.pmsoluciones.innovacion.dto.MetricaIndicadorDto;
import com.pmsoluciones.innovacion.dto.MetricasDto;
import com.pmsoluciones.innovacion.dto.ProyectoDto;
import com.pmsoluciones.innovacion.dto.ReporteInformeOperativoRequest;
import com.pmsoluciones.innovacion.service.ReportAbstract;

@Service
public class IndicadoresExcelReport extends ReportAbstract {
	
	String[] MESES = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

	public ResponseEntity<ByteArrayResource> exportToExcel(
			ReporteInformeOperativoRequest reporteInformeOperativoRequest) throws Exception {
		newReportExcel();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		for (ProyectoDto proyecto : reporteInformeOperativoRequest.getProyectos()) {
			sheet = workbook.createSheet(proyecto.getNomProyecto());
			sheepProyecto(sheet, proyecto, reporteInformeOperativoRequest);
		}

		outputStream.close();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProductTemplate.xlsx");
		workbook.write(outputStream);
		workbook.close();
		return new ResponseEntity<>(new ByteArrayResource(outputStream.toByteArray()), header, HttpStatus.CREATED);

	}

//	CREA HOJAS 
	void sheepProyecto(XSSFSheet sheep, ProyectoDto proyecto,
			ReporteInformeOperativoRequest reporteInformeOperativoRequest) {
		int posRow = 0;
		posRow = listadoMetricas(sheep, posRow, proyecto, reporteInformeOperativoRequest);
		posRow += 1;
		posRow = listadoPpb(sheep, posRow, proyecto.getLstPpb());
		posRow += 1;
		posRow = listadoAcr(sheep, posRow, proyecto.getLstAcr());

	}

	Integer listadoMetricas(XSSFSheet sheep, Integer posRow, ProyectoDto proyecto,
			ReporteInformeOperativoRequest reporteInformeOperativoRequest) {

		Integer mergeCols = 3;

		List<String> headers = Arrays.asList("ID y Nombre del informe", reporteInformeOperativoRequest.getNomInforme(),
				"Periodo",
				reporteInformeOperativoRequest.getFecFin() + " - " + reporteInformeOperativoRequest.getFecFin());

		writeTableHeaderExcel(headers, posRow, mergeCols);
		posRow += 1;
		List<String> headers2 = Arrays.asList("Nombre del proyecto o servicio", proyecto.getNomProyecto(), "URL",
				reporteInformeOperativoRequest.getRefUrlRepos());
		writeTableHeaderExcel(headers2, posRow, mergeCols);

		posRow += 2;

		// gerenar listado de meses
		List<String> headers3 = new ArrayList<String>();
		headers3.add("Indicadores");
		for (String string : reporteInformeOperativoRequest.getLstFechas()) {
			headers3.add(string);
		}

//		headers3 = (String[]) ArrayUtils.addAll(headers3, reporteInformeOperativoRequest.getLstFechas());
		Boolean isMerge = false;
		writeTableHeaderExcel(headers3, posRow, mergeCols, isMerge);
		posRow += 1;
		// data metricas proyecto.metricas

		for (MetricaIndicadorDto metrica : proyecto.getMetricaIndicadores()) {

			Row row = sheet.createRow(posRow);
			int columnCount = 0;

			CellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);

			createCell(row, columnCount++, metrica.getNomMetrica(), style);

			for (String fecha : reporteInformeOperativoRequest.getLstFechas()) {

				Cell cell = createCell(row, columnCount, "", style);

				if (metrica.getFechasProgramadas() != null) {
					for (FechaProgramadaDto fechProg : metrica.getFechasProgramadas()) {
						if (fecha.equals(fechProg.getFecProgramada())) {
//							createCell(row, columnCount, fechProg.getNumResultMetrica(), style);
							if (null != fechProg.getNumResultMetrica()) {
								cell.setCellValue((String) fechProg.getNumResultMetrica().toString());
							}

						}
					}
				}

				columnCount++;
			}

			posRow += 1;

		}

		return posRow;
	}

	Integer listadoAcr(XSSFSheet sheep, Integer posRow, List<MetricasDto> lstMetricas) {

		if (!lstMetricas.isEmpty()) {
			List<String> headers1 = Arrays.asList("ACR");

			Integer mergeCols = 12;
			writeTableHeaderExcel(headers1, posRow, mergeCols);
			posRow += 1;
			List<String> headers = Arrays.asList("Fecha", "Nombre Metrica", "Nombre documento", "URL");

			mergeCols = 3;
			writeTableHeaderExcel(headers, posRow, mergeCols);
			posRow += 1;
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);

			for (MetricasDto metrica : lstMetricas) {

				Row row = sheet.createRow(posRow);
				int columnCount = 0;

				createCell(row, columnCount, metrica.getFechaDoc(), style);
				columnCount += 1;
				createCell(row, columnCount, metrica.getNomDoc(), style);
				CellRangeAddress region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getNomDoc(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getDesUrl(), style);

				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;

				posRow += 1;

			}
		}

		return posRow;

	}

	Integer listadoPpb(XSSFSheet sheep, Integer posRow, List<MetricasDto> lstMetricas) {

		if (!lstMetricas.isEmpty()) {
			for (MetricasDto metrica : lstMetricas) {

				List<String> headers = Arrays.asList("Metrica: " + metrica.getNomMetrica(), "LSL", "Media", "USL");

				Integer mergeCols = 2;
				writeTableHeaderExcel(headers, posRow, mergeCols);
				posRow += 1;

				Row row = sheet.createRow(posRow);
				int columnCount = 0;

				CellStyle style = workbook.createCellStyle();
				style.setAlignment(HorizontalAlignment.LEFT);
				style.setAlignment(HorizontalAlignment.LEFT);
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
				style.setBorderTop(BorderStyle.THIN);

				createCell(row, columnCount, "Linea base de desempeño", style);

				columnCount += 1;
				createCell(row, columnCount, metrica.getDesLcl(), style);
				CellRangeAddress region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);

				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getDesMedia(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getDesUcl(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				posRow += 1;
				row = sheet.createRow(posRow);
				columnCount = 0;
				createCell(row, columnCount, "Desempeño del proyecto", style);
				columnCount += 1;
				createCell(row, columnCount, metrica.getDesLclIn(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getDesMediaIn(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;
				createCell(row, columnCount, metrica.getDesUclIn(), style);
				region = new CellRangeAddress(posRow, posRow, columnCount, columnCount + mergeCols);
				sheet.addMergedRegion(region);
				RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
				RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

				columnCount = columnCount + mergeCols + 1;

				posRow += 1;

			}
		}

		return posRow;

	}

}
