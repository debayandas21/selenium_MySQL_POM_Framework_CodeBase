package Automation.POM.framework.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataRead {

	public static Logger logger = LogManager.getLogger(DataRead.class);

	public DataRead() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public static Object[][] getConsolidatedtestDataArray(String testcaseName, String testDataSheetName)
			throws Exception {

		Object[][] testDataArray = null;
		Hashtable<String, String> hashtable = null;
		String Execute;
		ExcelUtil xlUtil = new ExcelUtil();

		try {
			String filePath = System.getProperty("user.dir") + "\\testData\\Automation_TestData.xlsx";
			System.out.println(filePath);

			if (filePath != null) {

				xlUtil.openWorkBook(new File(filePath));

				File file = new File(System.getProperty("user.dir") + "\\testData\\Automation_TestData.xlsx");
				FileInputStream fi = new FileInputStream(file);
				try (XSSFWorkbook wrb = new XSSFWorkbook(fi)) {

					int index = xlUtil.getSheetIndex(testDataSheetName);
					String sheetName = xlUtil.getSheetName(index);
					if (sheetName.equals(testDataSheetName)) {
						XSSFRow row = wrb.getSheet(testDataSheetName).getRow(0);

						int RowCount = wrb.getSheet(testDataSheetName).getLastRowNum();
						System.out.println("Row Count: " + RowCount);

						int ColoumnCout = row.getLastCellNum();
						System.out.println("Total Column count: " + ColoumnCout);

						if (xlUtil.isSheetExist(sheetName)) {
							logger.info(testDataSheetName + "  sheet exist in Automation_TestData.xlsx file");

							int ai;
							int aj;
							int startRow = 0;
							int Startcol = 0;

							boolean isSkipped = false;

							int totalExecutableRowCount = xlUtil.getTotalRowCountWithExecuteFlag(testcaseName,
									testDataSheetName);
							int totalRows = xlUtil.getTotalRownCount(testDataSheetName);

							testDataArray = new Object[totalExecutableRowCount][1];
							ai = 0;

							for (int i = startRow; i < totalRows; i++, ai++) {
								aj = 0;
								Execute = xlUtil.getCellValue(testDataSheetName, i, 0);
								if ("END".equals(Execute)) {
									break;
								}
								hashtable = new Hashtable<String, String>();
								for (int j = Startcol; j < ColoumnCout; j++, aj++) {
									if (xlUtil.getCellValue(testDataSheetName, i, 0).equalsIgnoreCase("Y") && (xlUtil
											.getCellValue(testDataSheetName, i, 1).equalsIgnoreCase(testcaseName))) {
										System.out.println(xlUtil.getCellValue(testDataSheetName, 0, j) + " : "
												+ xlUtil.getCellValue(testDataSheetName, i, j));
										hashtable.put(xlUtil.getCellValue(testDataSheetName, 0, j),
												xlUtil.getCellValue(testDataSheetName, i, j));

									} else {
										isSkipped = true;
										ai = ai - 1;
										break;
									}
									
								}
								if (isSkipped) {
									logger.info("Row " + i + 1 + " has been skipped");
									isSkipped = !isSkipped;

								} else {
									testDataArray[ai][0] = hashtable;
									logger.info(testDataArray[ai][0].toString());

								}
							}

						} else {
							logger.error("Testdata file provided work sheet  doesn't exist");
							throw new Exception("Testdata file provided work sheet  doesn't exist");
						}
					} else {
						logger.error("Testdata file doesn't exist in the given path");
						throw new FileNotFoundException("Testdata file doesn't exist in the given path");
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return testDataArray;
	}
}
