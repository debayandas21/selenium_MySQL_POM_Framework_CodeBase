package Automation.POM.framework.Utilities;

public enum ExecutionStatus {

	PASS("Pass"), FAIL("Fail"), INFO("Info"), WARNING("Warning");
	
	public String logtype;
	
	ExecutionStatus(String logtype) {
		this.logtype=logtype;
	}
	
	public String getStatus() {
		return logtype;
	}
	
	public String toString() {
		return logtype;
	}
}
