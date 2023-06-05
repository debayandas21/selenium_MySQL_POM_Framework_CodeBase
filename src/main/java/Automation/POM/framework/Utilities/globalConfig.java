package Automation.POM.framework.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Automation.POM.framework.core.BaseClass;

public class globalConfig extends BaseClass{

	public  static Logger logger;
	public  static ExcelUtil xlUtil;
	public 	static HashMap<String, String> globalParameter;
	
	public globalConfig() throws Exception
	{
		logger= LogManager.getLogger(globalConfig.class);
		xlUtil= new ExcelUtil();
	}
	
	
	public void getGlobalConfig(String file) throws FileNotFoundException {
		globalParameter= new HashMap<>();
		{
			try {
				if (file != null) {
					xlUtil.openWorkBook(new File(file));
					logger.info("Open Workbook method starts- ");
					String sheetName = xlUtil.getSheetName(0);
					logger.info("WorkBook sheet name: " + sheetName);

					if (sheetName.equals("Global_Config_Data")) {
						logger.info("Work book active sheet name: " + sheetName
								+ " is matched with actual file sheet name");
						int RowCount = xlUtil.getTotalRownCount(sheetName);

						for (int i = 0; i < RowCount; i++) {
							String globalParamKey = xlUtil.getCellValue(sheetName, i, 0);
							String globalParamValue = xlUtil.getCellValue(sheetName, i, 1);

							globalParameter.put(globalParamKey, globalParamValue);
							logger.info("global config Key: " + globalParamKey + " ---- global config value: "
									+ globalParamValue);
						}
					} else {
						logger.error("Active sheet name is not matched with actual sheet name: Global_Config_Data");
						throw new FileNotFoundException("Active file is not present in the given path");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Failed to capture the data from the global config file");
			}
		}
	}
}
