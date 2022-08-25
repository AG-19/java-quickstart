package com.assaabloy;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.commons.JcrUtils;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App {

	static Session session;

	static String domain = "https://author-p31367-e132744.adobeaemcloud.com";

	public static void main( String[] args ) {
		try {
			
			domain += "/mnt/overlay/wcm/core/content/sites/properties.html?item=";
			
			//Create a Node
			session = getJCRSession();			
			String queryString = "SELECT * FROM [cq:PageContent] AS nodes WHERE ISDESCENDANTNODE ([/content]) AND nodes.[metaRobotsValue] IS NOT NULL";
			Query query = session.getWorkspace().getQueryManager().createQuery(queryString, Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator nodeItr = result.getNodes();
			Property propMetaRobots;
			Value[] values;

			Workbook workbook = new XSSFWorkbook();
			OutputStream fileOut = new FileOutputStream("seo-props.xlsx");
			Sheet sheet = workbook.createSheet("MetaRobotsValue Prop");
			
			Row titlesRow = sheet.createRow(0);
			Cell pathCell= titlesRow.createCell(0);
			pathCell.setCellValue("Page URL");
			Cell siteCell = titlesRow.createCell(1);
			siteCell.setCellValue("Site");
			Cell propVlauesCell = titlesRow.createCell(2);
			propVlauesCell.setCellValue("Meta Robot Values");

			int i = 1;
			while (nodeItr.hasNext()) {
				propMetaRobots = nodeItr.nextNode().getProperty("metaRobotsValue");
				Row row = sheet.createRow(i++);
				CreationHelper helper = workbook.getCreationHelper();
				Cell cell= row.createCell(0);
				
				String url = domain + propMetaRobots.getPath().replace("/jcr:content/metaRobotsValue", "");

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
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.logout();
		}
	}

	private static Session getJCRSession () {
		try {
			//Create a connection to the AEM JCR repository running on local host
			Repository repository = JcrUtils.getRepository("http://localhost:4502/crx/server");
			//Create a Session instance
			session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
}
