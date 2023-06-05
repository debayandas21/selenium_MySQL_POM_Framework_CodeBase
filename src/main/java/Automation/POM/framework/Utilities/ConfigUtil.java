package Automation.POM.framework.Utilities;

import Automation.POM.framework.core.BaseClass;

public class ConfigUtil extends BaseClass{

	public ConfigUtil() throws Exception{
		// TODO Auto-generated constructor stub
	}

	/***
	 * Define the Wait logic in this class
	 */
	
	public  static long VeryLow= Long.parseLong(globalConfig.globalParameter.get("wait.Vrlow"));
	public 	static long Low= Long.parseLong(globalConfig.globalParameter.get("wait.low"));
	public  static long Medium= Long.parseLong(globalConfig.globalParameter.get("wait.medium"));
	public  static long High= Long.parseLong(globalConfig.globalParameter.get("wait.high"));
	public  static long VeryHigh= Long.parseLong(globalConfig.globalParameter.get("wait.VrHigh"));
	
}
