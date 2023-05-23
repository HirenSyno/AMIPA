package com.sample.test;

import com.sample.test.Constants;
import java.io.FileInputStream;
import java.util.Properties;
//import org.monte.screenrecorder.ScreenRecorder;
import com.sample.test.DriverScript;

public class Executor{

	public static String browser="Chrome";
	public static String version="24";
	public static String module="All";
	public static String branch="config";
	//public static String release="AG";
	public static String buildNumber="New Build";
	public static String testcase="All";
	public static String jenkinsJobID="0";
	public static String BranchName="";
	public static String BuildDate="";
	public static String emailDeliver="";
	public static String author="";
	public static String to_email="";
	public static String cc_email="";
	public static boolean result=true;
	//public static WebDriver driver=null;
	//public ScreenRecorder screenRecorder;
	public String result1=Constants.KEYWORD_FAIL;
	
	public static void main(String[] args) throws Exception {


		browser=args[0];
		version=args[1];
		module=args[2];
		testcase=args[3];
		emailDeliver=args[4];
		to_email=args[5];
		author="Automation Team.";
		Properties configinfo = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//config//config.properties");
		configinfo.load(fs);
		browser=configinfo.getProperty("browserType");
		version=configinfo.getProperty("browserVersion");
		emailDeliver=configinfo.getProperty("emailDeliver").toString();
		to_email=configinfo.getProperty("to").toString();
		DriverScript driverScript=new DriverScript();
		//result=DriverScript.callExecutor(browser, version, module,branch,testcase,emailDeliver,to_email, BranchName);
		result=driverScript.callExecutor(browser, version, module,branch,testcase,emailDeliver,to_email,cc_email,author);

		if(result==false)
		{
			System.exit(1);
		}
	}
	

}
