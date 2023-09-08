package com.blogspot.adapttoaem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.JsonObject;

public class ExcelUtils {

	public static Workbook createWorkBook () {
		return new XSSFWorkbook();
	}

	public static List<String> getExcelCoulmnAsList (String fileName, int sheetIndex, int columnIndex) {

		List<String> list = new ArrayList<String>();
		try {
			InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(fileName);
			XSSFWorkbook workBook = new XSSFWorkbook (is);
			XSSFSheet sheet = workBook.getSheetAt(sheetIndex);

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell cell = row.getCell(columnIndex);
				list.add(cell.getStringCellValue());
			}

			workBook.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public static void createSheet (Workbook workbook, String sheetName) {
		Sheet sheet = workbook.createSheet(sheetName);

		Row titlesRow = sheet.createRow(0);
		Cell pathCell= titlesRow.createCell(0);
		pathCell.setCellValue("Page URL");
		Cell siteCell = titlesRow.createCell(1);
		siteCell.setCellValue("Site");
		Cell propVlauesCell = titlesRow.createCell(2);
		propVlauesCell.setCellValue("Meta Robot Values");

		/*
		int i = 1;
		while (nodeItr.hasNext()) {
			propMetaRobots = nodeItr.nextNode().getProperty("metaRobotsValue");
			Row row = sheet.createRow(i++);
			CreationHelper helper = workbook.getCreationHelper();
			Cell cell= row.createCell(0);

			String url = propMetaRobots.getPath().replace("/jcr:content/metaRobotsValue", "");

			XSSFHyperlink link = (XSSFHyperlink)helper.createHyperlink(HyperlinkType.URL);
			link.setAddress(url);

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Calibri");
			font.setUnderline(Font.U_SINGLE);
			font.setColor(HSSFColorPredefined.BLUE.getIndex());
			style.setFont(font);		  
			cell.setCellStyle(style);

			cell.setCellValue(url);
			cell.setHyperlink(link);

			String noContentPath = propMetaRobots.getPath().replace("/content/", "");
			row.createCell(1).setCellValue(noContentPath.substring(0, noContentPath.indexOf("/")));

			String fieldVal = "";
			if (propMetaRobots.isMultiple()) {
				values = propMetaRobots.getValues();
				for (Value value : values) {
					fieldVal += value.getString() + ", ";
				}
				row.createCell(2).setCellValue(fieldVal.substring(0, fieldVal.lastIndexOf(", ")));
			} else {
				row.createCell(2).setCellValue(propMetaRobots.getValue().getString());
			}
		}
		 */
	}

	public void createExcelFile (String fileName) {
		try {
			Workbook workbook = createWorkBook();
			createSheet(workbook, "Sheet 1");
			OutputStream fileOut = new FileOutputStream(fileName+".xlsx");
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static JsonObject excelToJSONI18N (String fileName, int sheetIndex) throws IOException {

		InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(fileName);
		XSSFWorkbook workBook = new XSSFWorkbook (is);
		XSSFSheet sheet = workBook.getSheetAt(sheetIndex);

		Iterator<Row> rowIterator = sheet.iterator();

		JsonObject jsonObject = new JsonObject();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			jsonObject.addProperty(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());            
		}

		workBook.close();
		return jsonObject;
	}

	public static JsonObject excelToJSON (String fileName, int sheetIndex) throws IOException {

		InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(fileName);
		XSSFWorkbook workBook = new XSSFWorkbook (is);
		XSSFSheet sheet = workBook.getSheetAt(sheetIndex);

		Iterator<Row> rowIterator = sheet.iterator();

		JsonObject jsonObject = new JsonObject();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
			}
		}

		workBook.close();
		return jsonObject;
	}

}
