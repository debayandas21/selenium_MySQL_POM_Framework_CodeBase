package Automation.POM.framework.Utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	/* The constant logger */
	public static Logger logger = LogManager.getLogger(ExcelUtil.class);

	public static Workbook wb;

	/**
	 * This method is designed to open work book
	 */

	public void openWorkBook(File WorkBookpath) throws FileNotFoundException {
		if (logger.isDebugEnabled()) {
			logger.info("openWorkBook(File)- start");
		}

		InputStream inputStream = null;
		logger.info("opening workbook");

		try {
			inputStream = new BufferedInputStream(new FileInputStream(WorkBookpath));
			wb = WorkbookFactory.create(inputStream);
		} catch (FileNotFoundException exception) {
			exception.getMessage();
		} catch (Exception e) {
			logger.error("Open Workbook file: " + e);
			logger.error("Opening workbook: " + WorkBookpath + ", " + e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					logger.error("Open Workbook file: " + e);
					logger.error("Opening workbook: " + WorkBookpath + ", " + e);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.info("open Workbook file: -end");
		}
	}

	/**
	 * Check the WorkBook is open
	 * 
	 * @throws Exception
	 */
	public void closeIfOpen() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("CloseifOpen()- start");
		}

		try {

			if (wb != null) {

			}
		} catch (Exception e) {
			logger.error("CloseifOpen(): " + e);

		}
		if (logger.isDebugEnabled()) {
			logger.info("CloseifOpen(): -end");
		}
	}

	/**
	 * Get the sheet Count -getSheetCount
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getSheetCount() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("getSheetCount()- start");
		}

		int sheetCount = 0;
		sheetCount = wb.getNumberOfSheets();

		if (logger.isDebugEnabled()) {
			logger.info("getSheetCount(): -end");
		}
		return sheetCount;
	}

	
	/**
	 * getSheetIndex
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public int getSheetIndex(String sheetName) throws Exception
	{
		if (logger.isDebugEnabled()) {
			logger.info("getSheetIndex()- start");
		}
		
		int sheetIndex=0;
		sheetIndex= wb.getSheetIndex(sheetName);
		
		if (logger.isDebugEnabled()) {
			logger.info("getSheetIndex(): -end");
		} return sheetIndex;
	}
	/**
	 * getSheetName
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public String getSheetName(int index) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("getSheetName()- start");
		}

		String getSheetName = null;
		getSheetName = wb.getSheetName(index);

		if (logger.isDebugEnabled()) {
			logger.info("getSheetName(): -end");
		}
		return getSheetName;
	}

	/**
	 * isSheetExist
	 * @param sheetName
	 * @return
	 */
	public boolean isSheetExist(String sheetName) {
		if (logger.isDebugEnabled()) {
			logger.info("isSheetExist()- start");
		}
		
		Sheet Xlsheet= null;
		if(sheetName!=null) {
			Xlsheet= wb.getSheet(sheetName);
			
		} 
		if(Xlsheet!=null) {
			if (logger.isDebugEnabled()) {
				logger.info("isSheetExist()- End");
			} return true;
		} else {
			if (logger.isDebugEnabled()) {
				logger.info("isSheetExist()- End");
			} return false;
		}
		
	}
	/**
	 * getCell
	 * 
	 * @param wb
	 * @param SheetName
	 * @param row
	 * @param Column
	 * @return
	 * @throws Exception
	 */
	public Cell getCell(Workbook wb, String SheetName, int row, int Column) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("getCell(WorkBook, String, int, int )- start");
		}

		Sheet xlSheet;
		Row opRow;
		Cell opCell;

		xlSheet = wb.getSheet(SheetName);
		opRow = xlSheet.getRow(row);
		opCell = opRow.getCell(Column);

		if (logger.isDebugEnabled()) {
			logger.info("getCell(WorkBook, String, int, int )-: -end");
		}
		return opCell;

	}

	public String getCellValue(String sheetName, int row, int Column) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("getCellValue()- start");
		}

		Cell xlCell = null;
		String cellValue = null;

		try {
			xlCell = getCell(wb, sheetName, row, Column);
			if (xlCell == null) {
				cellValue = "";
			} else {
				cellValue = xlCell.getStringCellValue();
			}

		} catch (IllegalStateException e) {
			cellValue = String.valueOf((int) xlCell.getNumericCellValue());
		} catch (java.lang.Exception e) {
			cellValue = "";
		}

		if (cellValue != null) {
			if (logger.isDebugEnabled()) {
				logger.info("getCellValue(String, int, int )-: -end");
			}
			return cellValue;
		} else {
			if (logger.isDebugEnabled()) {
				logger.info("getCellValue(String, int, int )-: -end");
			}
		}
		return cellValue;
	}

	/**
	 * getTotalRownCount
	 * 
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public int getTotalRownCount(String sheetName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("getTotalRownCount(String)- start");
		}

		Sheet xlSheet;
		xlSheet = wb.getSheet(sheetName);

		if (xlSheet == null) {
			throw new FileNotFoundException(sheetName + " does not exists");
		}

		int rowCount = xlSheet.getLastRowNum();
		if (rowCount > 0) {
			if (logger.isDebugEnabled()) {
				logger.info("getTotalRownCount(String)-: -end");
			}
		} else {
			logger.error("Total row count is: " + rowCount);
		}
		return rowCount;
	}
	
	
    /**
     * getTotalRowCountWithExecuteFlag
     * @param TestCaseName
     * @param SheetName
     * @return
     * @throws Exception
     */
	public int getTotalRowCountWithExecuteFlag(String TestCaseName, String SheetName) throws Exception
	{
		int totalExecutableRow=0;
		try {
			if (logger.isDebugEnabled()) {
				logger.info("getTotalRowCountWithExecuteFlag(String, String)- start");
			}
			
			Sheet xlsheet;
			xlsheet= wb.getSheet(SheetName);
			
			if(xlsheet==null) {
				throw new FileNotFoundException("No such sheet exist");
			} 
			
			int returnint= xlsheet.getLastRowNum();
			
			for(int i=0; i<returnint; i++) {
				
				if("Y".equalsIgnoreCase(getCellValue(SheetName, i, 0)) && TestCaseName.equalsIgnoreCase(getCellValue(SheetName, i, 1))) {
					totalExecutableRow++;
				}else if("End".equalsIgnoreCase(getCellValue(SheetName, i, 0))) {
					break;
				}
			}if (logger.isDebugEnabled()) {
				logger.info("getTotalRowCountWithExecuteFlag(String, String)- End");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} return totalExecutableRow;
	}
	
//	/**
//     * getTotalRowCountWithExecuteFlag
//     * @param TestCaseName
//     * @param SheetName
//     * @return
//     * @throws Exception
//     */
//	public int getTotalRowCountWithExecuteFlag(String TestCaseName, String SheetName) throws Exception
//	{
//		int totalExecutableRow=0;
//		try {
//			if (logger.isDebugEnabled()) {
//				logger.info("getTotalRowCountWithExecuteFlag(String, String)- start");
//			}
//			
//			Sheet xlsheet;
//			xlsheet= wb.getSheet(SheetName);
//			
//			if(xlsheet==null) {
//				throw new FileNotFoundException("No such sheet exist");
//			} 
//			
//			int returnint= xlsheet.getLastRowNum();
//			
//			for(int i=0; i<returnint; i++) {
//				
//				if(TestCaseName.equalsIgnoreCase(getCellValue(SheetName, i, 1))) {
//					totalExecutableRow++;
//				}else if("End".equalsIgnoreCase(getCellValue(SheetName, i, 0))) {
//					break;
//				}
//			}if (logger.isDebugEnabled()) {
//				logger.info("getTotalRowCountWithExecuteFlag(String, String)- End");
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		} return totalExecutableRow;
//	}
}
