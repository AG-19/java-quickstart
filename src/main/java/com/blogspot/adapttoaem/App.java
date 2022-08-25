package com.blogspot.adapttoaem;

import java.io.IOException;

public class App {

	public static void main( String[] args ) throws IOException {
		System.out.println(ExcelUtils.excelToJSONI18N("test.xlsx", 0).toString());
	}

}
