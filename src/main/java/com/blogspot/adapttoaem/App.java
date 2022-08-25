package com.blogspot.adapttoaem;

import java.io.IOException;

public class App {

	public static void main( String[] args ) throws IOException {
		System.out.println(ExcelUtils.excelToJSON("seo-props.xlsx").toString());
	}

}
