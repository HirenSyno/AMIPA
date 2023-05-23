
package com.sample.test;
import java.time.Duration;
import static com.sample.test.DriverScript.APP_LOGS;
import static com.sample.test.DriverScript.CONFIG;
import static com.sample.test.DriverScript.OR;
import static com.sample.test.DriverScript.currentTestDataSetID;
import static com.sample.test.DriverScript.currentTestSuiteXLS;
import com.sample.util.UIThreadLocal;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
//import org.testng.annotations.DataProvider;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import com.eviware.soapui.model.testsuite.TestRunner;
//import com.eviware.soapui.tools.SoapUITestCaseRunner;

//import com.thoughtworks.selenium.Selenium;
import com.sample.util.DownloadManager;
import com.sample.xls.read.Xls_Reader;

//Timir 18thApril2013

public class Keywords {

	public String windowHandle;
	// public Selenium selenium ;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public int startIndex = 0;
	public static FileInputStream fs;
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /IM ";
	/*
	 * String USERNAME = CONFIG.getProperty("gmailUser").toString(); String PASSWORD
	 * = CONFIG.getProperty("gmailPassword").toString();
	 */
	public static Properties MSG;
	public WebDriver driver;
	
	 
	    
	public String result = Constants.KEYWORD_FAIL;
	public static boolean screenshotfoldercreate = true;
	public static String filePath = "";
	public static String globalValue;
	// public static String g_employeecost;
	public static String g_dependentcost;
	public static ArrayList<String> plansFromDB = new ArrayList<String>();
	public static Properties msgCONFIG;

	public String Result;
	public Double ActualResult1;
	

	/*
	 * public Keywords() throws IOException {
	 * 
	 * FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+
	 * "//src//com//sample//config//config.properties");
	 * 
	 * CONFIG= new Properties(); CONFIG.load(fs);
	 * 
	 * 
	 * }
	 */
	int waitforelement = Integer.parseInt(CONFIG.getProperty("waitforelement"));
	int waitfordocUpload = Integer.parseInt(CONFIG.getProperty("waitfordocUpload"));
	private static DecimalFormat df2 = new DecimalFormat(".##");

	public String caluclateAndVerfiyUnsoldResult(String object, JSONArray ja) {
		int size = driver.findElements(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr")).size();
		ArrayList<String> UnsoldcropList = new ArrayList<String>();
		ArrayList<String> UnsoldquantityList = new ArrayList<String>();
		ArrayList<String> UnsoldpriceList = new ArrayList<String>();
		ArrayList<String> UnsoldbasisList = new ArrayList<String>();
		ArrayList<String> UnsoldvalueList = new ArrayList<String>();

		for (int unsold = 1; unsold <= size; unsold++) {

			String crop = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[2]"))
					.getText();
			JSONObject cropObject = ja.getJSONObject(unsold - 1);
			double localPrice = cropObject.getDouble("localPrice");
			String quantity = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[3]"))
					.getText();

			String price = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[4]"))
					.getText();
			String basis = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[5]"))
					.getText();
			String valuelist = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[6]"))
					.getText();

			// write trim code or string split code here

			price = price.replaceAll("[^0.0-9.0]", "").trim();
			quantity = quantity.replaceAll("[^0.0-9.0]", "").trim();
			basis = basis.replaceAll("[^0.0-9.0]", "").trim();
			System.out.println(crop);
			System.out.println(quantity);
			System.out.println(localPrice);
			System.out.println(basis);
			System.out.println(valuelist);
			valuelist = valuelist.replaceAll("[^0.0-9.0]", "").trim();
			UnsoldcropList.add(crop);
			UnsoldquantityList.add(quantity);
			// UnsoldpriceList.add(price);
			UnsoldbasisList.add(basis);
			UnsoldvalueList.add(valuelist);

			// double priceDbl = Double.parseDouble(UnsoldpriceList.get(unsold -
			// 1).toString());
			double basisDbl = Double.parseDouble(UnsoldbasisList.get(unsold - 1).toString());
			double totlDbl = localPrice + basisDbl;

			// To Calculate Quantity

			double quantitycnt = Double.parseDouble(UnsoldquantityList.get(unsold - 1).toString());
			double valuefinal = totlDbl * quantitycnt;

			// if (UnsoldvalueList.get(unsold -
			// 1).toString().contains(String.valueOf(valuefinal))) {

			// } else {
			// set cell data
			/*
			
			*/
			// return Constants.KEYWORD_FAIL + " " + "Actual=" + UnsoldvalueList.get(unsold
			// - 1).toString() + " "
			// + "Expected is" + String.valueOf(valuefinal);

			// }
		}
		return Constants.KEYWORD_PASS;
		// return Constants.KEYWORD_FAIL;
	}

	public String caluclateAndVerfiyUnsoldValue(String object, String data) {
		int size = driver.findElements(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr")).size();
		ArrayList<String> UnsoldcropList = new ArrayList<String>();
		ArrayList<String> UnsoldquantityList = new ArrayList<String>();
		ArrayList<String> UnsoldpriceList = new ArrayList<String>();
		ArrayList<String> UnsoldbasisList = new ArrayList<String>();
		ArrayList<String> UnsoldvalueList = new ArrayList<String>();

		for (int unsold = 1; unsold <= size; unsold++) {

			String crop = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[2]"))
					.getText();
			String quantity = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[3]"))
					.getText();
			String price = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[4]"))
					.getText();
			String basis = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[5]"))
					.getText();
			String valuelist = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']//tbody//tr[" + unsold + "]//td[6]"))
					.getText();

			// write trim code or string split code here

			price = price.replaceAll("[^0.0-9.0]", "").trim();
			quantity = quantity.replaceAll("[^0.0-9.0]", "").trim();
			basis = basis.replaceAll("[^0.0-9.0]", "").trim();
			System.out.println(crop);
			System.out.println(quantity);
			System.out.println(price);
			System.out.println(basis);
			System.out.println(valuelist);
			valuelist = valuelist.replaceAll("[^0.0-9.0]", "").trim();
			UnsoldcropList.add(crop);
			UnsoldquantityList.add(quantity);
			UnsoldpriceList.add(price);
			UnsoldbasisList.add(basis);
			UnsoldvalueList.add(valuelist);

			double priceDbl = Double.parseDouble(UnsoldpriceList.get(unsold - 1).toString());
			double basisDbl = Double.parseDouble(UnsoldbasisList.get(unsold - 1).toString());
			double totlDbl = priceDbl + basisDbl;

			// To Calculate Quantity

			double quantitycnt = Double.parseDouble(UnsoldquantityList.get(unsold - 1).toString());
			double valuefinal = totlDbl * quantitycnt;

			if (UnsoldvalueList.get(unsold - 1).toString().contains(String.valueOf(valuefinal))) {

				return Constants.KEYWORD_PASS;

			} else {
				// set cell data
				/*
				
				*/
				return Constants.KEYWORD_FAIL + " " + "Actual=" + UnsoldvalueList.get(unsold - 1).toString() + " "
						+ "Expected is" + String.valueOf(valuefinal);

			}
		}
		return Constants.KEYWORD_FAIL;
	}
	public String openBrowser(String object,String data){
		APP_LOGS.debug("Opening browser");
		WebDriver driver = UIThreadLocal.getWebDriver();
		try
		{
		System.out.println("Updating Excel References");
		getRefFlag(object, data);
		}
		catch(Exception e)
		{
		System.out.println("Unable to update data in ref sheet");
		System.out.println(e.getMessage());
		result= Constants.KEYWORD_FAIL;
		}
		System.out.println("FINISHED: Updating Excel References");
		try
		{
		if(data.equals("Chrome"))
		{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.navigate().to("https://pieuat.hdfcergo.com/");
		Thread .sleep(4000);

		
		}
		else if(data.equals("IE"))
		{
		//isProcessRunging("IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//IEDriverServer.exe");
		//DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		//caps.setCapability("javascriptEnabled", true);
		//caps.setCapability("nativeEvents", false);
		//caps.setCapability("requireWindowFocus",true);
		//driver=new InternetExplorerDriver(caps);
		//driver.manage().deleteAllCookies();
		/*DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);      
		driver = new InternetExplorerDriver(capabilities);*/
		result=Constants.KEYWORD_PASS;
		}
		else if(data.equals("Mozilla"))
		{
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//geckodriver.exe");
		driver=new FirefoxDriver();
		driver.manage().deleteAllCookies();
		result=Constants.KEYWORD_PASS;
		}
		else if(data.equals("safari"))
		{
		System.out.println("Opening browser");
		driver=new SafariDriver();
		driver.manage().deleteAllCookies();
		result=Constants.KEYWORD_PASS;
		}
		UIThreadLocal.setWebDriver(driver);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		result= Constants.KEYWORD_FAIL;
		//throw new WebDriverException("Could not open browser instance");
		}
		return result;
		}
	
public String closeBrowser(String object, String data)
{			APP_LOGS.debug("Closing the browser");
			object="";
			try{
			WebDriver driver = UIThreadLocal.getWebDriver();
			try
			{
			System.out.println("Updating Excel References");
			getRefFlag(object, data);
			}
			catch(Exception e)
			{
			System.out.println("Unable to update data in ref sheet");
			System.out.println(e.getMessage());
			result= Constants.KEYWORD_FAIL;
			}
			System.out.println("FINISHED: Updating Excel References");
			if(CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")==true)
			{
			driver.quit();
			result=Constants.KEYWORD_PASS;
			}
			else
			{
			driver.close();
			//driver.quit();
			result=Constants.KEYWORD_PASS;
			}
			}catch(Exception e){
			result=Constants.KEYWORD_FAIL;
			}
			return result;
			}
	
	
	
	public String openBrowsers(String object, String data) {
		APP_LOGS.debug("Opening browser");

		try {
			System.out.println("Updating Excel References");
			getRefFlag(object, data);
		} catch (Exception e) {
			System.out.println("Unable to update data in ref sheet");
			System.out.println(e.getMessage());
			result = Constants.KEYWORD_FAIL;
		}
		System.out.println("FINISHED: Updating Excel References");

		try {
			if (data.equals("Mozilla")) {
				System.out.println("Opening browser");
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//geckodriver.exe");
				driver = new FirefoxDriver();

				driver.manage().deleteAllCookies();

				result = Constants.KEYWORD_PASS;
			} else if (data.equals("IE")) {
				// isProcessRunging("IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//IEDriverServer.exe");
				// DesiredCapabilities caps =
				// DesiredCapabilities.internetExplorer();
				// caps.setCapability("javascriptEnabled", true);
				// caps.setCapability("nativeEvents", false);
				// caps.setCapability("requireWindowFocus",true);
				// driver=new InternetExplorerDriver(caps);
				// driver.manage().deleteAllCookies();

				/*
				 * DesiredCapabilities capabilities = new DesiredCapabilities();
				 * capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); driver =
				 * new InternetExplorerDriver(capabilities);
				 */

				result = Constants.KEYWORD_PASS;
			} else if (data.equals("Chrome")) {

				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");

				// DesiredCapabilities caps = DesiredCapabilities.chrome();
				// caps.setCapability("javascriptEnabled", true);
				// caps.setCapability("nativeEvents", true);
				// caps.setCapability("requireWindowFocus",true);
				// driver.manage().deleteAllCookies();

				ChromeOptions options = new ChromeOptions();
				//options.addArguments("window-size=1920,1200","--start-maximized");
				options.addArguments("--start-maximized");
				options.addArguments("force-device-scale-factor=0.75");

				//options.addArguments("--headless",
				// "--window-size=1920,1200","--start-maximized");

				//driver = new ChromeDriver(options);
		        driver = new ChromeDriver(options);

				//driver = new ChromeDriver();
								result = Constants.KEYWORD_PASS;

			}

			else if (data.equals("safari")) {
				System.out.println("Opening browser");
				driver = new SafariDriver();
				driver.manage().deleteAllCookies();

				result = Constants.KEYWORD_PASS;
			}
			// long
			// implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
			// driver.manage()).timeouts().implicitlyWait(implicitWaitTime,
			// TimeUnit.SECONDS);
			//driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
			// throw new WebDriverException("Could not open browser instance");

		}
		return result;

	}
	
	
	/**
	 * This function is used to navigate to a given URL.
	 * 
	 * @param object - This var is used to identify object properties
	 * @param data   - This var is used to as a test data (URL).
	 * @return This function is return the status of current URL to load.
	 */
	public String navigate(String object, String data) {
		APP_LOGS.debug("Navigating to URL");
		try {
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("window-size=1920,1200","--start-maximized");
			//options.addArguments("force-device-scale-factor=0.75");
			options.addArguments("--start-maximized");
			options.addArguments("force-device-scale-factor=2.75");
			//driver.manage().window().maximize();
			driver.navigate().to(data);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Login')]")));
			Thread.sleep(2000);

			// driver.manage().timeouts().implicitlyWait(implicitWaitTime,
			// TimeUnit.SECONDS);
		} catch (Exception e) {
			// return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * This function is used to perform click event on specified link
	 * 
	 * @param object - This var is used to identify object properties
	 * @param data   - This var is used to as a test data
	 * @return This function is return the status of click event perform on link
	 *         object.
	 */
	public String clickLink(String object, String data) {
		APP_LOGS.debug("Clicking on link ");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					// Thread.sleep(5000);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.xpath(OR.getProperty(object))).click();
						Thread.sleep(7000);
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {

					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
					System.out.println("Premium Calculated");

					Thread.sleep(1000);

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(2000);
				}

				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
			// throw new NoSuchElementException("No such element found");
		}

		return result;
	}

	public String clickLink_tw(String object, String data) {
		APP_LOGS.debug("Clicking on link ");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					// Thread.sleep(5000);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.xpath(OR.getProperty(object))).click();
						Thread.sleep(7000);
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(2000);
				}

				result = Constants.KEYWORD_PASS;
			} else {

				WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
				System.out.println("Element is now clickable");
				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
			// throw new NoSuchElementException("No such element found");
		}

		return result;
	}

	public String deleteclickLink(String object, String data) {
		APP_LOGS.debug("Clicking on link ");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object)));
				try {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By
								.xpath("//div[@id='fieldList']//div//a[contains(text(),'FLA-')]//following::div[2]/a"))
								.click();
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
			// throw new NoSuchElementException("No such element found");
		}

		return result;
	}

	public String clickLink_linkText(String object, String data) {
		APP_LOGS.debug("Clicking on link ");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.linkText(OR.getProperty(object)));
				try {
					driver.findElement(By.linkText(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.linkText(OR.getProperty(object))).click();
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {
					driver.findElement(By.linkText(OR.getProperty(object))).click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait3.until(ExpectedConditions.elementToBeClickable(By.linkText(OR.getProperty(object))));
				System.out.println("Element is now clickable");
				Thread.sleep(1000);
				driver.findElement(By.linkText(OR.getProperty(object))).click();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
			// throw new NoSuchElementException("No such element found");
		}
		return result;
	}

	public String verifyLinkText(String object, String data) {
		APP_LOGS.debug("Verifying link Text");
		try {
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected = data;

			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Link text not verified";

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Link text not verified" + e.getMessage();

		}

	}

	public String clickButton(String object, String data) {
		APP_LOGS.debug("Clicking on Button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). click();
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). sendKeys("\n");
				 */
				driver.findElement(By.xpath(OR.getProperty(object)));

				try {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.xpath(OR.getProperty(object))).click();

					} else {
						result = Constants.KEYWORD_PASS;

					}

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					System.out.println("object is clicked");
				}
				sleep(1);
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				sleep(2);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
			;
			// throw new NoSuchElementException("No such element found");

		}

		return result;
	}

	public String clickButtonByCss(String object, String data) {
		APP_LOGS.debug("Clicking on Button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.cssSelector(OR.getProperty(object)));

				try {
					driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.cssSelector(OR.getProperty(object))).click();

					} else {
						result = Constants.KEYWORD_PASS;

					}

				} catch (Exception e) {
					driver.findElement(By.cssSelector(OR.getProperty(object))).click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.cssSelector(OR.getProperty(object))).click();
				// pause(3000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
			;
			// throw new NoSuchElementException("No such element found");
		}
		return result;
	}

	public String clickButtonByID(String object, String data) {
		APP_LOGS.debug("Clicking on Button");
		try {
			boolean vres = true;
			// driver.findElement(By.id(OR.getProperty(object))).click();
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.id(OR.getProperty(object)));
				try {
					// driver.findElement(By.id(OR.getProperty(object))).click();
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
					sleep(2);
					List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.id(OR.getProperty(object))).click();

					} else {
						result = Constants.KEYWORD_PASS;

					}

				} catch (Exception e) {

					driver.findElement(By.id(OR.getProperty(object))).click();
					System.out.println("clicked");
				}
				// sleep(4);
				result = Constants.KEYWORD_PASS;
			}

			else {
				List<WebElement> objButton = driver.findElements(By.id(OR.getProperty(object)));
				if (objButton.size() > 0) {
					ClickWebElement(objButton.get(0));
				}
			}

			sleep(6);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
			;

		}

		return result;
	}

	public String clickButtonByName(String object, String data) {
		APP_LOGS.debug("Clicking on Button");
		try {
			// driver.findElement(By.id(OR.getProperty(object))).click();
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.name(OR.getProperty(object)));
				try {
					driver.findElement(By.name(OR.getProperty(object))).sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver.findElements(By.name(OR.getProperty(object)));
					if (objElements.size() > 0) {
						driver.findElement(By.name(OR.getProperty(object))).click();
					} else {
						result = Constants.KEYWORD_PASS;
					}
				} catch (Exception e) {

					driver.findElement(By.name(OR.getProperty(object))).click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				List<WebElement> objButton = driver.findElements(By.name(OR.getProperty(object)));
				if (objButton.size() > 0) {
					ClickWebElement(objButton.get(0));
				}

				sleep(6);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
			;

		}

		return result;
	}

	public String verifyButtonText(String object, String data) {
		APP_LOGS.debug("Verifying the button text");
		try {
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected = data;

			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Button text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String selectRadio(String object, String data) {
		APP_LOGS.debug("Selecting a radio button");
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0]) + data + OR.getProperty(temp[1]))).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}
		return Constants.KEYWORD_PASS;
	}

	public String selectRadioButton(String object, String data) {
		APP_LOGS.debug("Selecting a radio button");
		try {
			String checked = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if (checked == null) {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}

		return Constants.KEYWORD_PASS;

	}

	public String selectRadioButtonByID(String object, String data) {
		APP_LOGS.debug("Selecting a radio button");
		try {
			String checked = driver.findElement(By.id(OR.getProperty(object))).getAttribute("value");
			if (checked.equals("NO")) {
				driver.findElement(By.id(OR.getProperty(object))).click();
				return Constants.KEYWORD_PASS;
			} else if (checked == "YES") {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";
		}
		return Constants.KEYWORD_PASS;
	}

	public String verifyRadioSelected(String object, String data) {
		APP_LOGS.debug("Verify Radio Selected");
		try {
			String temp[] = object.split(Constants.DATA_SPLIT);
			String checked = driver.findElement(By.xpath(OR.getProperty(temp[0]) + data + OR.getProperty(temp[1])))
					.getAttribute("checked");
			if (checked == null)
				return Constants.KEYWORD_FAIL + "- Radio not selected";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";
		}
		return Constants.KEYWORD_PASS;
	}

	public String checkCheckBox(String object, String data) {
		APP_LOGS.debug("Checking checkbox");
		try {
			// true or null
			sleep(4);
			String checked = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if (checked == null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public String checkCheckBoxByID(String object, String data) {
		APP_LOGS.debug("Checking checkbox");
		try {
			// true or null
			String checked = driver.findElement(By.id(OR.getProperty(object))).getAttribute("checked");
			if (checked == null)// checkbox is unchecked
				driver.findElement(By.id(OR.getProperty(object))).click();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public String unCheckCheckBox(String object, String data) {
		APP_LOGS.debug("Unchecking checkBox");
		try {
			String checked = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if (checked != null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;

	}

	public String verifyCheckBoxSelected(String object, String data) {
		APP_LOGS.debug("Verifying checkbox selected");
		try {
			String checked = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if (checked != null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";

		}

	}

	public String verifyText(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			// sleep(3);
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data;
			System.out.println(actual);
			System.out.println(expected);
			Thread.sleep(2000);
			if (expected.contains(actual))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String verifyText_popup(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			// sleep(3);
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data;
			System.out.println(actual);
			System.out.println(expected);
			// Thread.sleep(5000);
			if (expected.contains(actual)) {

				// showMessageDialog(null, "Plan Description is set as per Plan Selection");
				driver.switchTo().alert().sendKeys("Plan Description is set as per Plan Selection");
				Thread.sleep(2000);
				driver.switchTo().alert().accept();

				return Constants.KEYWORD_PASS;
			} else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String assertIfObjectFound1(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			String objElements = driver.findElement(By.xpath(".//*[@id='accountInfo_CustomerMgmt']/tr[7]/td[2]"))
					.getText();

			if (objElements == "PENDING" || objElements == "VIEWED") {
				result = Constants.KEYWORD_PASS;

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifyvideostatusPending(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {

			WebElement scroll = driver.findElement(
					By.xpath("html/body/div[2]/div[2]/div/div[2]/div[2]/div[3]/div[2]/div[2]/div/div[1]/div"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(250,0)", "");

			String actual = driver.findElement(By.xpath("//table[@id='accountInfo_CustomerMgmt']//tr[7]/td[2]"))
					.getText();
			String expected = "PENDING";
			System.out.println(By.xpath("//table[@id='accountInfo_CustomerMgmt']//tr[7]/td[2]"));
			System.out.println(actual);

			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String verifyTextByCSS(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			String actual = driver.findElement(By.cssSelector(OR.getProperty(object))).getText().trim();
			String expected = data;

			if (actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String verifyTextById(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			String actual = driver.findElement(By.id(OR.getProperty(object))).getAttribute("value").trim();
			String expected = data.trim();

			if (actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String verifyTextContent(String object, String data) {
		APP_LOGS.debug("Verifying the text content");
		try {
			sleep(3);

			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data.trim();

			if (actual.toUpperCase().contains(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String verifyNumericValue(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if (actual != null) {
				actual = actual.substring(0, actual.indexOf("."));
				String expected = data;

				if (actual.equals(expected))
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL + " -- Numeric value not verified " + actual + " -- " + expected;
			} else {
				return Constants.KEYWORD_FAIL + " -- No Value present ";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String writeInInput_pc(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(500);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInput(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
//			System.out.println("Premium Calculated");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

	//		System.out.println("above data is pass");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputwithoutenter(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(3000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data,
			// Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByTAB(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data, Keys.TAB);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByCSS(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.cssSelector(OR.getProperty(object))).clear();

			driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByName(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.name(OR.getProperty(object))).clear();

			driver.findElement(By.name(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String scroll_up_and_writeInInput(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			Thread.sleep(2000);

			
			driver.findElement(By.id(OR.getProperty(object))).clear();
			
			
			
			
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByID_pc(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			
			driver.findElement(By.id(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			System.out.println("wait");
			
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");

			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String writeInInputByID(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			System.out.println(object);
			
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");

			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String scroll_down(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			Thread.sleep(1000);
			WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
			System.out.println(objElement);

			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			Thread.sleep(2000);
			/*
			 * JavascriptExecutor js = (JavascriptExecutor) driver;
			 * js.executeScript("arguments[0].scrollIntoView();", objElement);
			 * 
			 */

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to scroll " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String scroll_up_id(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			Thread.sleep(2000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String scroll_up_xpath(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			Thread.sleep(2000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String movetoelement(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("window.scrollBy(0,-350)", "");

			Thread.sleep(2000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByIDEnter(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);

			Thread.sleep(1000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			// Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInputByXpathEnter(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			/*
			 * Robot robot = new Robot(); robot.keyPress(KeyEvent.VK_DOWN);
			 * robot.keyPress(KeyEvent.VK_COPY); // Thread.sleep(2000);
			 * robot.keyPress(KeyEvent.VK_ENTER);
			 */
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String verifyTextinInput(String object, String data) {
		APP_LOGS.debug("Verifying the text in input box");
		try {
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected = data;

			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	public String verifyTextinInputByID(String object, String data) {
		APP_LOGS.debug("Verifying the text in input box");
		try {
			String actual = driver.findElement(By.id(OR.getProperty(object))).getAttribute("value");
			String expected = data;

			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	/**
	 * This function is used to verify Textbox value By CSS.
	 * 
	 * @param object - This var is used to identify object properties
	 * @param data   - This var is used to as a test data.
	 * @return This function is return the status- Pass- IF Textbox value and Data
	 *         are matching Fail- IF Textbox value and Data are not matching
	 */

	public String verifyTextinInputByCSS(String object, String data) {
		APP_LOGS.debug("Verifying the text in input box");
		try {
			String actual = driver.findElement(By.cssSelector(OR.getProperty(object))).getAttribute("value");
			String expected = data;

			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	public String clickImage() {
		APP_LOGS.debug("Clicking the image");

		return Constants.KEYWORD_PASS;
	}

	public String verifyFileName() {
		APP_LOGS.debug("Verifying inage filename");

		return Constants.KEYWORD_PASS;
	}

	public String verifyTitle(String object, String data) {
		APP_LOGS.debug("Verifying title");
		try {
			String actualTitle = driver.getTitle();
			String expectedTitle = data;
			if (actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Title not verified " + expectedTitle + " -- " + actualTitle;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Error in retrieving title";
		}
	}

	public String exist(String object, String data) {
		APP_LOGS.debug("Checking existance of element");
		try {
			driver.findElement(By.xpath(OR.getProperty(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}

		return Constants.KEYWORD_PASS;
	}

	public String existByID(String object, String data) {
		APP_LOGS.debug("Checking existance of element");
		try {
			sleep(3);
			driver.findElement(By.id(OR.getProperty(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}

		return Constants.KEYWORD_PASS;
	}

	public String click_1(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				try {

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("element is now clickable");
					Thread.sleep(1000);

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(1000);
					// Thread.sleep(20000);

				} catch (Exception e) {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));

					Thread.sleep(2000);
				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {

				Thread.sleep(1000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
				System.out.println("element is now clickable");

				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String click(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(1000);
					// Thread.sleep(20000);

				} catch (Exception e) {
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(5000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {

				Thread.sleep(1000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
				System.out.println("element is now clickable");

				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String click_action(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {
				Thread.sleep(1000);

				WebElement linked_checkbox = driver.findElement(By.xpath(OR.getProperty(object)));

				Actions action = new Actions(driver);

				action.moveToElement(linked_checkbox).build().perform();
				Thread.sleep(1000);
				action.click(linked_checkbox).build().perform();
				Thread.sleep(2000);

				result = Constants.KEYWORD_PASS;
			} else {

				Thread.sleep(1000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
				System.out.println("element is now clickable");

				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String click_checkout_uat(String object, String data) {
		APP_LOGS.debug("Clicking on checkout button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
					wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("element is now clickable");
					Thread.sleep(2000);

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(3000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECKOUT'])[6]")));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					System.out.println("Calculated");

				} catch (Exception e) {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					System.out.println("Calculated");

					Thread.sleep(5000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String dynamic_xpath_for_index(String object, String data) {
		APP_LOGS.debug("Clicking on checkout button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {
				// System.out.println(object);
				int i = 0;
				String s = Integer.toString(i);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("element is now clickable");
					Thread.sleep(1000);

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(3000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					System.out.println("Calculated");

				} catch (Exception e) {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					System.out.println("Calculated");

					Thread.sleep(5000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String click_checkout_family_medical(String object, String data) {
		APP_LOGS.debug("Clicking on checkout button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(3000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Proposal Rejected')]")));
					System.out.println("Calculated");

				} catch (Exception e) {
					Thread.sleep(1000);
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[text()='CHECK
					// OUT'])[6]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Send Offline Consent')]")));
					System.out.println("Calculated");

					Thread.sleep(5000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String click_pass_data(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				try {
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));
					wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");

					// Thread.sleep(1000);
					// driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(3000);
					WebElement e1 = driver.findElement(By.xpath(OR.getProperty(object)));
					e1.sendKeys(data);
					Thread.sleep(1000);
					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
					// Thread.sleep(20000);

				} catch (Exception e) {
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(5000);
				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_with_otp_verification_outlook(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Verify & Proceed')]")));

					((JavascriptExecutor) driver).executeScript("window.open()");
					ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs.get(1));

					driver.get(
							"https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1638734417&rver=7.0.6737.0&wp=MBI_SSL&wreply=https%3a%2f%2foutlook.live.com%2fowa%2f%3fnlp%3d1%26RpsCsrfState%3d2adc7b58-1b09-7c2f-b6da-722c544ae43f&id=292841&aadredir=1&CBCXT=out&lw=1&fl=dob%2cflname%2cwld&cobrandid=90015");
					// driver.findElement(By.xpath("//h4[contains(text(),'Generate
					// Quote')]")).click();

				} catch (Exception e) {
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(5000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String otp_consent_outlook(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				// driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					((JavascriptExecutor) driver).executeScript("window.open()");
					ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					driver.switchTo().window(tabs.get(1));

					Thread.sleep(1000);
					driver.get(
							"https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1638734417&rver=7.0.6737.0&wp=MBI_SSL&wreply=https%3a%2f%2foutlook.live.com%2fowa%2f%3fnlp%3d1%26RpsCsrfState%3d2adc7b58-1b09-7c2f-b6da-722c544ae43f&id=292841&aadredir=1&CBCXT=out&lw=1&fl=dob%2cflname%2cwld&cobrandid=90015");
					Thread.sleep(1000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));

				} catch (Exception e) {
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(5000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	/*
	 * public String login_without_otp_verification_pc(String object, String data) {
	 * APP_LOGS.debug("Clicking on any element"); try {
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {
	 * 
	 * 
	 * String windowHandle = driver.getWindowHandle();
	 * driver.switchTo().window(windowHandle);
	 * 
	 * //Thread.sleep(2000); driver.findElement(By.xpath(OR.getProperty(object)));
	 * //Thread.sleep(1000); try {
	 * 
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver,55);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//h2[contains(text(),'Home')]")));
	 * 
	 * driver.findElement(By.xpath("//p[contains(text(),'Private Car Policy')]")).
	 * click();
	 * 
	 * WebDriverWait wait2 = new WebDriverWait(driver,50);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h2[contains(text(),'Quote - PRIVATE CAR POLICY')]")));
	 * Thread.sleep(2000);
	 * 
	 * } catch (Exception e) { Thread.sleep(2000);
	 * 
	 * System.out.println("GC service error, closing browser for stop execution");
	 * 
	 * try { System.out.println("Updating Excel References"); getRefFlag(object,
	 * data); } catch (Exception e2) {
	 * System.out.println("Unable to update data in ref sheet");
	 * System.out.println(e2.getMessage()); result = Constants.KEYWORD_FAIL; }
	 * System.out.println("FINISHED: Updating Excel References");
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome") == true) {
	 * driver.quit(); result = Constants.KEYWORD_PASS; } else { driver.close();
	 * driver.quit(); result = Constants.KEYWORD_PASS;
	 * 
	 * } } sleep(2); //
	 * driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n"); result =
	 * Constants.KEYWORD_PASS; } else { WebElement objElement =
	 * driver.findElement(By.xpath(OR.getProperty(object)));
	 * driver.getWindowHandle(); Actions builder = new Actions(driver);
	 * builder.moveToElement(objElement).build().perform(); JavascriptExecutor js =
	 * (JavascriptExecutor) driver;
	 * js.executeScript("var evt = document.createEvent('MouseEvents');" +
	 * "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
	 * + "arguments[0].dispatchEvent(evt);", objElement);
	 * 
	 * result = Constants.KEYWORD_PASS; } } catch (Exception e) { result =
	 * Constants.KEYWORD_FAIL + " Not able to click"; } return result; }
	 */
	public String login_without_otp_verification_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Private Car Policy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - PRIVATE CAR POLICY')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");

					// driver.quit();
					// driver.close();
					result = Constants.KEYWORD_PASS;
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_pc_blaze(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					Thread.sleep(2000);

					//driver.findElement(By.xpath("//p[contains(text(),'Private Car Blaze Policy')]")).click();
					driver.findElement(By.xpath("//p[contains(text(),'Private Car Policy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - PRIVATE CAR POLICY')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");

					// driver.quit();
					// driver.close();
					result = Constants.KEYWORD_PASS;
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					Thread.sleep(2000);
					driver.findElement(By.xpath("//p[contains(text(),'My : Health Suraksha')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - My : Health Suraksha')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					System.out.println("GC service error");
					// System.out.println("GC service error, closing browser for stop execution");
					// driver.close();
					// driver.quit();
					// System.exit(1);

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			System.out.println("GC service error");
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String login_without_otp_verification_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("(//p[text()='Two Wheeler Policy'])[2]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("(//button[text()='Create Quote'])[2]")));
					Thread.sleep(1000);

					driver.findElement(By.xpath("(//button[text()='Create Quote'])[2]")).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - TWO WHEELER POLICY')]")));

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					// System.exit(1);
					result = Constants.KEYWORD_PASS;

				}

				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_tw_blaze(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("(//p[text()='Two Wheeler Blaze Policy'])[2]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("(//button[text()='Create Quote'])[1]")));
					Thread.sleep(1000);

					driver.findElement(By.xpath("(//button[text()='Create Quote'])[1]")).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - TWO WHEELER POLICY')]")));

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					// System.exit(1);
					result = Constants.KEYWORD_PASS;

				}

				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String wait_untill_xpath(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Wait Completed");

			Thread.sleep(3000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String wait_untill_xpath_clickable_save_payment(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				try {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");

					Thread.sleep(1000);
				}

				catch (Exception e) {
					driver.findElement(By.xpath(
							"(//button[@ng-click='ShowExceptionMessage = 0; ShowPaymentWizard();ShowButtons=true'])"))
							.click();
					Thread.sleep(1000);
					driver.findElement(By.id("lnkSavePayment")).click();
					Thread.sleep(1000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("Element is now clickable");

				}
			}
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " GC Service error occured";
		}
		return Constants.KEYWORD_PASS;

	}

	public String wait_untill_id_clickable(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String wait_untill_xpath_clickable(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			//WebDriverWait wait3 = new WebDriverWait(driver, 1000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	public String wait_untill_xpath_not_visible(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now not visible");

			Thread.sleep(2000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String wait_untill_id(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(3000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String verify_otp_navigate_homepage(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("(//p[text()='Two Wheeler Policy'])[2]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("(//button[text()='Create Quote'])[2]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("(//button[text()='Create Quote'])[2]")).click();
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - TWO WHEELER POLICY')]")));

				} catch (Exception e) {
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					// Thread.sleep(5000);
				}
				// sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String ReadUnreadEmail_outlook(String object, String data) {
		APP_LOGS.debug("Read Unread Email");

		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='qpqOL'])[1]")));
			Thread.sleep(1000);
			// Click on Subject
			driver.findElement(By.xpath("(//div[@class='qpqOL'])[1]")).click();
			Thread.sleep(2000); // After click on subject wait
			// WebDriverWait waiting = new WebDriverWait(driver, 60);
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//p[@align='justify']//font)[2]")));
			Thread.sleep(1000);
			driver.navigate().refresh();
			wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='qpqOL'])[1]")));
			Thread.sleep(1000);

			driver.findElement(By.xpath("(//div[@class='qpqOL'])[1]")).click();
			Thread.sleep(2000);

			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@align='justify']//font)[2]")).getText();

			System.out.println(EXLVALUEBID);

			Thread.sleep(1000);

			Pattern p = Pattern.compile("(|^)\\d{6}");

			if (EXLVALUEBID != null) {
				Matcher m = p.matcher(EXLVALUEBID);
				if (m.find()) {
					System.out.println(m.group(0));
				} else {
					System.out.println("no match");
				}

				/*
				 * String trim_EXLVALUEBID =
				 * EXLVALUEBID.replace("Your One Time Password for the transaction is ",
				 * "").replace("/bu", ""); System.out.println(trim_EXLVALUEBID);
				 * Thread.sleep(1000);
				 * 
				 * String trim_EXLVALUEBID_final = trim_EXLVALUEBID.
				 * replace(". This is generated on 06/12/2021 16:25:27 and will be valid for 1 minutes. We hope you have a hassle free experience while transacting online."
				 * , "").replace("/bu", "");
				 * 
				 * System.out.println(trim_EXLVALUEBID_final); Thread.sleep(1000);
				 */

				if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, m.group(0))) {
					Thread.sleep(1000);
					result = Constants.KEYWORD_PASS;

				} else {
					result = Constants.KEYWORD_FAIL;
				}

				// ((JavascriptExecutor)driver).executeScript("window.open()");
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

				driver.switchTo().window(tabs.get(0));

			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return result;

	}

	public String calculatepremiumandquotegenerate_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(25000);
				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String calculatepremiumandquotegenerate_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(25000);
				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String calculatepremiumandquotegenerate_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(25000);
				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	/*
	 * public String explicitwait_calculatepremiumandgeneratequote_pc(String object,
	 * String data) { APP_LOGS.debug("Clicking on any element"); try {
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {
	 * 
	 * // driver.SwitchTo().Window(driver.CurrentWindowHandle); //
	 * driver.findElement(By.id(OR.getProperty(object))).click(); String
	 * windowHandle = driver.getWindowHandle();
	 * driver.switchTo().window(windowHandle); //Thread.sleep(2000);
	 * driver.findElement(By.xpath(OR.getProperty(object))); Thread.sleep(1000); try
	 * {
	 * 
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * //Thread.sleep(25000); Thread.sleep(1000); WebDriverWait wait = new
	 * WebDriverWait(driver,120);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h4[contains(text(),'Generate Quote')]"))); Thread.sleep(3000);
	 * driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click
	 * (); //Thread.sleep(2000);
	 * 
	 * WebDriverWait wait2 = new WebDriverWait(driver,120);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//button[contains(text(),'Generate Proposal')]")));
	 * 
	 * } catch (Exception e) {
	 * driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
	 * Thread.sleep(2000);
	 * driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).
	 * click(); WebDriverWait wait = new WebDriverWait(driver,55);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h4[contains(text(),'Generate Quote')]"))); Thread.sleep(3000);
	 * 
	 * 
	 * driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click
	 * (); //Thread.sleep(2000);
	 * 
	 * WebDriverWait wait2 = new WebDriverWait(driver,55);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//button[contains(text(),'Generate Proposal')]")));
	 * 
	 * } sleep(1); //
	 * driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n"); result =
	 * Constants.KEYWORD_PASS; } else {
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * //Thread.sleep(25000);
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver,50);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h4[contains(text(),'Generate Quote')]"))); Thread.sleep(3000);
	 * driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click
	 * (); Thread.sleep(2000); result = Constants.KEYWORD_PASS; } } catch (Exception
	 * e) { result = Constants.KEYWORD_FAIL + " Not able to click";
	 * System.out.println("GC service error, closing browser for stop execution");
	 * 
	 * 
	 * try { System.out.println("Updating Excel References"); getRefFlag(object,
	 * data); } catch (Exception e2) {
	 * System.out.println("Unable to update data in ref sheet");
	 * System.out.println(e2.getMessage()); result = Constants.KEYWORD_FAIL; }
	 * System.out.println("FINISHED: Updating Excel References");
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome") == true) {
	 * driver.quit(); result = Constants.KEYWORD_PASS; } else { driver.close();
	 * driver.quit(); result = Constants.KEYWORD_PASS;
	 * 
	 * } }
	 * 
	 * return result; }
	 */

	public String clickonquote(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		
		try {
			
			driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
			Thread.sleep(7000);

			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[7]")).getText();
			System.out.println("Quote NO: " + EXLVALUEBID);
			Thread.sleep(3000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu","");
			System.out.println("Quote No: " + trim_EXLVALUEBID);
			Thread.sleep(1000);
			
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
	
//			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
//			wait2.until(ExpectedConditions
//					.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));
//			driver.findElement(By.xpath("//button[contains(text(),'Generate Proposal')]")).click();
//			Thread.sleep(25000);
			
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	
	public String explicitwait_calculatepremiumandgeneratequote_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

		if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {



		try {

//					driver.findElement(By.xpath(OR.getProperty(object))).click();

//					Thread.sleep(25000);
//					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
//					wait.until(ExpectedConditions
//							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Quote')]")));
//					Thread.sleep(1000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					Thread.sleep(15000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//button[contains(text(),'Generate Proposal')]")).click();
					Thread.sleep(25000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

//					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
//					wait2.until(ExpectedConditions
//							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_generatequotevisible(String object, String data) 
	{
		APP_LOGS.debug("Clicking on any element");
try {
//
//		if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

		try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(1000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					Thread.sleep(15000);
					
					String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[7]")).getText();
					System.out.println("Quote NO: " + EXLVALUEBID);
					Thread.sleep(15000);
					String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu","");
					System.out.println("Quote No: " + trim_EXLVALUEBID);
					Thread.sleep(5000);
					
					if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

						result = Constants.KEYWORD_PASS;
						Thread.sleep(1000);
					} else {
						result = Constants.KEYWORD_FAIL;
					}
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//button[contains(text(),'Generate Proposal')]")).click();
					Thread.sleep(25000);

				} 
					catch (Exception e) 
				{
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

				WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
//				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;

			}
		
	catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}
	
	
	public String explicitwait_calculatepremiumandgeneratequote_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				} catch (Exception e) {

					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_calculatepremiumandgeneratequote_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(3000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				} catch (Exception e) {

					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String generatequote_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);

				} catch (Exception e) {

					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
				Thread.sleep(3000);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	/*
	 * public String explicitwait_generateproposal_pc(String object, String data) {
	 * APP_LOGS.debug("Clicking on any element"); try {
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {
	 * 
	 * // driver.SwitchTo().Window(driver.CurrentWindowHandle); //
	 * driver.findElement(By.id(OR.getProperty(object))).click(); String
	 * windowHandle = driver.getWindowHandle();
	 * driver.switchTo().window(windowHandle); //Thread.sleep(2000);
	 * driver.findElement(By.xpath(OR.getProperty(object))); //Thread.sleep(1000);
	 * try {
	 * 
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * //Thread.sleep(25000);
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver,120);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//button[contains(text(),'Proceed to Checkout')]")));
	 * 
	 * Thread.sleep(2000);
	 * 
	 * driver.findElement(By.
	 * xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
	 * //Thread.sleep(2000); WebDriverWait wait2 = new WebDriverWait(driver,60);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h2[contains(text(),'Check Out/Consent')]"))); Thread.sleep(2000);
	 * 
	 * 
	 * } catch (Exception e) {
	 * driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
	 * Thread.sleep(2000); WebDriverWait wait = new WebDriverWait(driver,120);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h4[contains(text(),'Generate Proposal')]")));
	 * driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).
	 * click(); WebDriverWait wait2 = new WebDriverWait(driver,60);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//button[contains(text(),'Proceed to Checkout')]")));
	 * 
	 * Thread.sleep(2000);
	 * 
	 * driver.findElement(By.
	 * xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
	 * //Thread.sleep(2000);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.
	 * xpath("//h2[contains(text(),'Check Out/Consent')]"))); Thread.sleep(2000);
	 * 
	 * 
	 * 
	 * 
	 * } sleep(4); //
	 * driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n"); result =
	 * Constants.KEYWORD_PASS; } else { Thread.sleep(2000);
	 * System.out.println("GC service error, closing browser for stop execution");
	 * driver.close(); Thread.sleep(2000); driver.quit(); result =
	 * Constants.KEYWORD_PASS; } } catch (Exception e) {
	 * 
	 * result = Constants.KEYWORD_FAIL + " Not able to click";
	 * System.out.println("GC service error, closing browser for stop execution");
	 * 
	 * try { System.out.println("Updating Excel References"); getRefFlag(object,
	 * data); } catch (Exception e2) {
	 * System.out.println("Unable to update data in ref sheet");
	 * System.out.println(e2.getMessage()); result = Constants.KEYWORD_FAIL; }
	 * System.out.println("FINISHED: Updating Excel References");
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome") == true) {
	 * driver.quit(); result = Constants.KEYWORD_PASS; } else { driver.close();
	 * driver.quit(); result = Constants.KEYWORD_PASS;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return result; }
	 */

	public String explicitwait_generateproposal_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(9000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					// WebDriverWait wait2 = new WebDriverWait(driver,60);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				Thread.sleep(2000);
				System.out.println("GC service error");
				// driver.close();
				Thread.sleep(2000);
				// driver.quit();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_from_quote_to_edit_mode_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");
					Thread.sleep(2000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//div[@id='customerTypeSection']//div[@class='section-name']")));

					Thread.sleep(3000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_generateproposal_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click, due to Service Error";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String explicitwait_generateproposal_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					// Thread.sleep(1000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String explicitwait_from_quote_to_edit_mode_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");

					// Thread.sleep(1000);

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(1000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[text()='Quote No']/following-sibling::b")));

					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String explicitwait_from_quote_to_edit_mode_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("//a[contains(text(),'Edit')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[text()='Quote No']/following-sibling::b")));

					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error, closing browser for stop execution");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String explicitwait_for_navigate_checkout(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	/*
	 * public String explicitwait_for_navigate_payment_pc(String object, String
	 * data) { APP_LOGS.debug("Clicking on any element"); try {
	 * 
	 * if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {
	 * 
	 * // driver.SwitchTo().Window(driver.CurrentWindowHandle); //
	 * driver.findElement(By.id(OR.getProperty(object))).click(); String
	 * windowHandle = driver.getWindowHandle();
	 * driver.switchTo().window(windowHandle); //Thread.sleep(2000);
	 * driver.findElement(By.xpath(OR.getProperty(object))); //Thread.sleep(1000);
	 * try {
	 * 
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * //Thread.sleep(25000);
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver,40);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//button[@id='lnkSavePayment']"))); //Thread.sleep(1000);
	 * 
	 * 
	 * } catch (Exception e) {
	 * driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
	 * Thread.sleep(25000); } sleep(4); //
	 * driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n"); result =
	 * Constants.KEYWORD_PASS; } else {
	 * driver.findElement(By.xpath(OR.getProperty(object))).click();
	 * //Thread.sleep(25000);
	 * 
	 * WebDriverWait wait = new WebDriverWait(driver,30);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//button[@id='lnkSavePayment']"))); Thread.sleep(1000);
	 * 
	 * result = Constants.KEYWORD_PASS; } } catch (Exception e) { result =
	 * Constants.KEYWORD_FAIL + " Not able to click";
	 * System.out.println("GC service error, closing browser for stop execution");
	 * driver.close(); driver.quit(); //System.exit(1); } return result; }
	 */

	public String explicitwait_for_navigate_payment(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='lnkSavePayment']")));
					Thread.sleep(1000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='lnkSavePayment']")));
				Thread.sleep(1000);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_for_navigate_payment_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='lnkSavePayment']")));
					Thread.sleep(1000);

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='lnkSavePayment']")));
				Thread.sleep(1000);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String get_random_engno_insheet(String object, String data) {
		try {
			// It will generate 6 digit random Number.
			// from 0 to 999999
			Random rnd = new Random();
			// int number = rnd.nextInt(999999);

			String s = RandomStringUtils.randomAlphanumeric(12).toUpperCase();

			// String s=Integer.toString(number);

			// this will convert any number sequence into 6 character.
			// String abc = String.format("%06d", number);
			System.out.println(s);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, s)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_random_chasisno_insheet(String object, String data) {
		try {
			// It will generate 6 digit random Number.
			// from 0 to 999999
			Random rnd = new Random();
			// int number = rnd.nextInt(999999);

			String s = RandomStringUtils.randomAlphanumeric(18).toUpperCase();

			// String s=Integer.toString(number);

			// this will convert any number sequence into 6 character.
			// String abc = String.format("%06d", number);
			System.out.println(s);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, s)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getrandom6digitinsheet(String object, String data) {
		try {
			// It will generate 6 digit random Number.
			// from 0 to 999999
			Random rnd = new Random();
			int number = rnd.nextInt(9999);
			String s=Integer.toString(number);
			// String s=Integer.toString(number);

			// this will convert any number sequence into 6 character.
			String abc = String.format("%06d", number);
			System.out.println(abc);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, abc)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_account_no_in_sheet(String object, String data) {
		try {
			// It will generate 6 digit random Number.
			// from 0 to 999999
			Random rnd = new Random();
			int number = rnd.nextInt(2147483647);
			// String s=Integer.toString(number);

			// this will convert any number sequence into 6 character.
			String abc = String.format("%10d", number);
			System.out.println(abc);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, abc)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_customer_id_insheet(String object, String data) {
		try {

			// from 0 to 999999
			Random rnd = new Random();
			// int number = rnd.nextInt(999999);

			String s = RandomStringUtils.randomAlphanumeric(18).toUpperCase();

			// String s=Integer.toString(number);

			// this will convert any number sequence into 6 character.
			// String abc = String.format("%06d", number);
			System.out.println(s);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, s)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_random_4digit_insheet(String object, String data) {
		try {
			// It will generate 6 digit random Number.
			// from 0 to 999999
			Random rnd = new Random();
			 int number = rnd.nextInt(9999);
			 String s=Integer.toString(number);
			//String s = RandomStringUtils.randomAlphanumeric(12).toUpperCase();
            // String s=Integer.toString(number);
			// String abc = String.format("%06d", number);
			System.out.println("Random 4 digit no" + s);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, s)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_random_pan_insheet(String object, String data) {
		try {

			Random rnd = new Random();
			String firstfivechar = RandomStringUtils.randomAlphabetic(5).toUpperCase();

			System.out.println("First str: " + firstfivechar);
			String digit = RandomStringUtils.randomNumeric(4);
			System.out.println("Diget str: " + digit);
			String lastchar = RandomStringUtils.randomAlphabetic(1).toUpperCase();
			System.out.println("last str: " + lastchar);

			String full = firstfivechar + digit + lastchar;
			System.out.println(full);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, full)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String get_max_payment_amount(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				wait3.until(ExpectedConditions.elementToBeSelected(By.xpath("//input[@name='rdoProposalList']")));

				Thread.sleep(3000);
				System.out.println("Element is now clickable");

				driver.findElement(By.xpath("//table[@id='payments']"));
				// Thread.sleep(5000);
				try {

					String max;

					long m = 0;
					long r = 0;
					int rowNum = 0;
					String s;

					// No. of Columns
					List col = driver.findElements(By.xpath("//th[contains(text(),'Balance Amount')]"));
					System.out.println("Total No of columns are : " + col.size());
					// No.of rows
					List rows = driver.findElements(By.xpath("//td[@data-title='Balance Amount']"));
					System.out.println("Total No of rows are : " + rows.size());
					for (int i = 0; i < rows.size(); i++) {
						max = driver.findElement(By.xpath("//tr[" + (i + 1) + "]//td[@data-title='Balance Amount']"))
								.getText();
						m = (long) Double.parseDouble(max); // remove decimal value and convert in long

						System.out.println("xpath for max amount: " + m);
						System.out.print(m);
						System.out.println('\n');
						if (m > r) {
							r = m;
							rowNum = i;
							String p = String.valueOf(r);
							System.out.print(p + '\n');

						}
					}
					System.out.println(r);
					System.out.println(rowNum);
					String id = "txtUseAmount_" + (rowNum);
					WebElement Balance_amount = driver
							.findElement(By.xpath("//tr[" + (rowNum) + "]//td[@data-title='Balance Amount']"));
					System.out.println(Balance_amount);
					Thread.sleep(1000);
					Actions builder = new Actions(driver);
					builder.moveToElement(Balance_amount).build().perform();
					Thread.sleep(1000);

					builder.click().build().perform();
					Thread.sleep(1000);
					builder.sendKeys(Keys.TAB).build().perform();

					Thread.sleep(1000);

					// Balance_amount.sendKeys(Keys.TAB);
					System.out.println("Clicked");

					WebElement Use_amount = driver.findElement(By.id(id));
					System.out.println(Use_amount);
					Thread.sleep(500);
					Use_amount.click();
					String Total_Amount = driver.findElement(By.xpath("(//td[@data-title='Amount'])[1]")).getText();
					System.out.println(Total_Amount);
					WebElement use_amount = driver.findElement(By.id(id));
					System.out.println(use_amount);
					use_amount.sendKeys(Total_Amount);
					Thread.sleep(1000);
					builder.sendKeys(Keys.TAB).build().perform();
					Thread.sleep(1000);

					String s1 = "//*[@id='";
					String s2 = id;
					String s3 = "']//following::td[1]";

					String Use_button = s1 + s2 + s3;
					System.out.println(Use_button);

					// builder.sendKeys(Keys.ENTER).build().perform();
					WebElement Click_use_button = driver.findElement(By.xpath(Use_button));
					Click_use_button.click();

					Thread.sleep(2000);
					System.out.println("clicked on Use button");

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_hsp(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String mannual_payment_and_download_policy(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.xpath(OR.getProperty(object)));

				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String calculatepremiumandquotegeneratebyid(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				/*
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). click();
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). sendKeys("\n");
				 */
				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					// Thread.sleep(60000);

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String verifyTextNotContentSD(String object, String data) {
		APP_LOGS.debug("Verifying the text content");
		try {
			sleep(1);

			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data.toUpperCase().trim();

			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String Addvalueofquoteinsheet_pc(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[7]")).getText();
			System.out.println("Quote NO: " + EXLVALUEBID);
			Thread.sleep(15000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu",
					"");

			System.out.println("Quote No: " + trim_EXLVALUEBID);
			Thread.sleep(5000);

			
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addvalueofpolicyinsheet(String object, String data) {
		try {

			String EXLVALUEBID = driver.findElement(By.xpath("//td[@data-title='Policy Number']")).getText();
			System.out.println(EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(2000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addvalueofproposalinsheet(String object, String data) {
		try {

			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[1]")).getText();
			System.out.println(EXLVALUEBID);

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Proposal number : ", "")
					.replace(" is pending for approval from Underwriter", "").replace("/bu", "");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(2000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addvalueofquoteinsheet_hsp(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[3]")).getText();
			System.out.println(EXLVALUEBID);
			Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Quote No ", "").replace(" is created successfully.", "");

			System.out.println("trim_EXLVALUEBID :" + trim_EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	
	
	public String verify_stp_nstp(String object, String data) {
		try {

			if (driver.findElement(By.xpath("//td[@data-title='Policy Number']")).isDisplayed()) {

				String stp_policy_no = driver.findElement(By.xpath("//td[@data-title='Policy Number']")).getText();
				System.out.println("Flow is stp_and Policy no is : " + stp_policy_no);

				if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, stp_policy_no)) {

					// click on Policy button
					driver.findElement(By.xpath("//button[@class='btn btn-pdf'])[1]")).click();

WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions
							.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
					System.out.println("Element is now clickable");
					Thread.sleep(2000);

					driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					Thread.sleep(3000);
					System.out.println("Policy no store successfully in Sheet");
				}
			}
			else {
				String nstp_proposal_no = driver.findElement(By.xpath("(//p[@class='ng-binding'])[1]")).getText();
				System.out.println("Flow is NSTP and Prposal no is : "+ nstp_proposal_no);

				
				String trim_proposal_no = nstp_proposal_no.replace("Proposal number : ", "")
						.replace(" is pending for approval from Underwriter", "").replace("/bu", "");

				System.out.println("Prposal no is :" +trim_proposal_no);
				

				if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_proposal_no)) {
					
					driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					
					Thread.sleep(3000);
					System.out.println("proposal no store successfully in Sheet");
					//Thread.sleep(2000);
				
			}}
				
			result = Constants.KEYWORD_PASS;
			
			}		 

	
			 catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	
	
	public String Addvalueofquoteinsheet_tw(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[6]")).getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu",
					"");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifycustomeroncheckout_individual_pc(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//input[@name='CustomerName'])[2]"))
					.getAttribute("value").toUpperCase();
			System.out.println(EXLVALUEBID);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Customer name is fetched ",
			// "").replace("/bu", "");

			Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(3000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifycustomeroncheckout(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//input[@name='CustomerName'])[2]"))
					.getAttribute("value").toUpperCase();
			System.out.println(EXLVALUEBID);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Customer name is fetched ",
			// "").replace("/bu", "");

			// Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(3000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Add_application_noin_sheet(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//h5[@class='right quote-text ng-binding'])[4]"))
					.getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Application is created successfully with ", "").trim();

			System.out.println(trim_EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Add_quote_no_in_sheet_heath_wallet(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//h5[@class='right quote-text ng-binding'])[5]"))
					.getText();
			System.out.println(EXLVALUEBID);

			String EXLVALUEBID_replace = EXLVALUEBID.replaceAll(" *\\(.+?\\)", "").trim();
			System.out.println(EXLVALUEBID_replace);
			// Thread.sleep(1000);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Application is created
			// successfully with ", "").trim();

			// System.out.println(trim_EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID_replace)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Add_quote_no_in_sheet_heath_1(String object, String data) {

		try {

			WebElement str = driver.findElement(By.xpath("(//h5[@class='right quote-text ng-binding'])[5]"));
			String s1 = str.getText().trim();

			String[] splitString = s1.split(" ");

			String splitedString = splitString[1].trim();
			System.out.println(splitedString);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, splitedString)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addcartvalueinsheet(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText();
			System.out.println(EXLVALUEBID);
			Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Cart value is ", "").replace("/bu", "");

			System.out.println(trim_EXLVALUEBID);
			Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(10000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addbreakinid(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("//p[contains(text(),'Your BreakIn Reference No - ')]")).getText();
			System.out.println("BreakinID:-"  + EXLVALUEBID);
			Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your BreakIn Reference No -", "").replace("-", "");

			System.out.println("Final Breakin ID = " + trim_EXLVALUEBID);
			Thread.sleep(1000);

			
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(10000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	
	public String Addproposalno(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("//p[contains(text(),'Your Proposal is created successfully. Proposal No -')]")).getText();
			System.out.println("Proposal No:-"  + EXLVALUEBID);
			Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Proposal is created successfully. Proposal No -", "").replace("-", "");

			System.out.println("Final Proposal NO = " + trim_EXLVALUEBID);
			Thread.sleep(1000);

			
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(10000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	public String Addcurrentdateinsheet(String object, String data) {
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = "";
		try

		{

			Date date = new Date();
			// date.setMonth(-1);
			System.out.print(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate2 = sdf2.format(date);
			System.out.println(formattedDate2);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, formattedDate2)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}
	
	
	public String Addcurrentdateplusone(String object, String data) {
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = "";
		try

		{

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Using today's date
			c.add(Calendar.DATE, 1); // Adding 1 day
			String output = sdf.format(c.getTime());
			System.out.println("T+1 Date: " + output);
			
//			Date date = new Date();
//			date.setMonth(-1);
//			System.out.print(date);
////			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//			String formattedDate2 = sdf2.format(date);
//			System.out.println(formattedDate2);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, output)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifycompanyoncheckout(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//input[@name='CompanyName'])[1]")).getAttribute("value")
					.toUpperCase();
			System.out.println(EXLVALUEBID);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Customer name is fetched ",
			// "").replace("/bu", "");

			Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(3000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * ====================== public String paymentby_paymentlink(String object,
	 * String data) {
	 * 
	 * try { driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	 * String script =
	 * "var anchor=document.createElement('a');anchor.target='_blank';anchor.href='%s';anchor.innerHTML='.';document.body.appendChild(anchor);return anchor"
	 * ; Object element = ((JavascriptExecutor)
	 * driver).executeScript(String.format(script, url));
	 * 
	 * if (element instanceof WebElement) { WebElement anchor = (WebElement)
	 * element; anchor.click(); ((JavascriptExecutor)
	 * driver).executeScript("var a=arguments[0];a.parentNode.removeChild(a);",
	 * anchor); } } catch (Exception e) {
	 * 
	 * } }
	 * 
	 * 
	 * =====================
	 * 
	 */

	public String verifypremiumforquoteandcart(String object, String data) {
		try {

			String actuals = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText().trim();
			Double actual = Double.parseDouble(actuals);
			System.out.println("Actual Premium" + actual);
			// Thread.sleep(3000);

			//String expect = driver.findElement(By.xpath("(//span[@class='Amount ng-binding'])[2]")).getText().trim();
			String expect = driver.findElement(By.xpath("//*[@id=\"cartSummary\"]/div[2]/div/div[1]/div[2]/h4")).getText().trim();
			
			
			String expectsrplace = expect.replace("Rs", "").replace("/bu", "");
			//Double expects = Double.parseDouble(expect);
			Double expects = Double.parseDouble(expectsrplace);

			System.out.println("Expected Premium" +  expects);
			// Thread.sleep(3000);
			Thread.sleep(1000);
			if (actual.equals(expects))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expects;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * public String verifycustomer (String object, String data) { try {
	 * 
	 * String actuals=
	 * driver.findElement(By.xpath("(//label[@class='ng-binding'])[5]")).getText().
	 * trim(); //Double actual= Double.parseDouble(actuals);
	 * System.out.println(actuals); Thread.sleep(3000);
	 * 
	 * String
	 * expect=driver.findElement(By.xpath("(//input[@name='CustomerName'])[2]")).
	 * getText().trim(); //String expectsrplace = expect.replace("Rs",
	 * "").replace("/bu", ""); //Double expects= Double.parseDouble(expectsrplace);
	 * 
	 * 
	 * System.out.println(expect); Thread.sleep(3000);
	 * 
	 * if(actuals.equals(expect)) return Constants.KEYWORD_PASS; else return
	 * Constants.KEYWORD_FAIL + " -- text content not verified " + actuals + "--" +
	 * expect; } catch (Exception e) { return Constants.KEYWORD_FAIL +
	 * " Object not found " + e.getMessage(); } }
	 */

	public String Deleteclick(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). click();
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). sendKeys("\n");
				 */
				driver.findElement(By.xpath(OR.getProperty(object)));
				try {

					driver.findElement(
							By.xpath("//div[@id='fieldList']//div//a[contains(text(),'data')]//following::div[2]/a"))
							.click();

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String clickbyID_pc(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). click();
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). sendKeys("\n");
				 */
				driver.findElement(By.id(OR.getProperty(object)));
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					// Thread.sleep(5000);

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
					// Thread.sleep(2000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String clickbyID(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				/*
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). click();
				 * driver.findElement(By.cssSelector(OR.getProperty(object))). sendKeys("\n");
				 */
				driver.findElement(By.id(OR.getProperty(object)));
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					// Thread.sleep(5000);

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
					// Thread.sleep(2000);
				}
				// sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String clearText(String object, String data) {
		APP_LOGS.debug("Clearing the text from input field");
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public String LastContacted(String object, String data) {
		APP_LOGS.debug("Clearing the text from input field");
		try {
			driver.findElement(By.xpath("//th[text()='Last Contacted']")).click();

			Thread.sleep(5000);

			String sDate1 = driver
					.findElement(By.xpath(
							"html/body/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/table/tbody/tr[1]/td[3]"))
					.getText();

			driver.findElement(By.xpath("//th[text()='Last Contacted']")).click();
			System.out.print(sDate1);
			Thread.sleep(5000);
			String sDate2 = driver
					.findElement(By.xpath(
							"html/body/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/table/tbody/tr[1]/td[3]"))
					.getText();

			System.out.print(sDate2);

			Date current = new Date();
			Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate2);
			Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);

			long diff1 = current.getTime() - date1.getTime();
			Integer iDiff1 = (int) TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS);
			System.out.println(iDiff1);
			long diff2 = current.getTime() - date2.getTime();
			Integer iDiff2 = (int) TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS);
			System.out.println(iDiff2);
			if (iDiff1 >= 15 && iDiff2 > 15) {

				return Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public String synchronize(String object, String data) {
		try {
			APP_LOGS.debug("Waiting for page to load");
			((JavascriptExecutor) driver).executeScript("function pageloadingtime()" + "{"
					+ "return 'Page has completely loaded'" + "}" + "return (window.onload=pageloadingtime());");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}

		return Constants.KEYWORD_PASS;
	}

	public String datePicker(String object, String data) {
		try {
			APP_LOGS.debug("Waiting for date selection");

			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1]",
					driver.findElement(By.xpath(OR.getProperty("AddEventDate"))), "2018-02-28 23:55");

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String waitForElementVisibility(String object, String data) {
		APP_LOGS.debug("Waiting for an element to be visible");
		int start = 0;
		// int time=(int)Double.parseDouble(data);
		int time = Integer.parseInt(data);
		try {
			sleep(3);
			while (time == start) {
				if (driver.findElements(By.xpath(OR.getProperty(object))).size() == 0) {
					sleep(1);
					start++;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to find the object" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String closeBrowserd1(String object, String data) {
		APP_LOGS.debug("Closing the browser");
		try {

			try {
				System.out.println("Updating Excel References");
				getRefFlag(object, data);
				System.out.println("object:" + object);
				System.out.println("data:" + data);
			} catch (Exception e) {
				System.out.println("Unable to update data in ref sheet");
				System.out.println(e.getMessage());
				result = Constants.KEYWORD_FAIL;
			}
			System.out.println("FINISHED: Updating Excel References");

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome") == true) {
				driver.quit();
				result = Constants.KEYWORD_PASS;
			} else {
				driver.close();
				driver.quit();
				result = Constants.KEYWORD_PASS;

			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public String sleep(int object) throws NumberFormatException, InterruptedException {
		try {
			result = pause(String.valueOf(object), "MILLISECONDS");
		} catch (Exception e) {
		}
		return result;
	}

	public String pause(String object, String data) throws NumberFormatException, InterruptedException {
		// long time = (long)Double.parseDouble(object);
		try {
			if (object.equals("")) {
				object = CONFIG.getProperty("pauseSeconds");
			}
			int time = Integer.parseInt(object);
			if (data == null) {
				data = "";
			}
			if (data.equalsIgnoreCase("seconds")) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

				//driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
				//driver.manage().timeouts().implicitlyWait(null)
			} else if (data.equalsIgnoreCase("MILLISECONDS")) {
				Thread.sleep(time * 1000);
			} else {
				Thread.sleep(time * 1000);
				// driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String wait(String object, String data) throws NumberFormatException, InterruptedException {
		// long time = (long)Double.parseDouble(object);
		try {
			data = "";
			long time = Long.parseLong(object);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String broswerClose(String object, String data) {
		APP_LOGS.debug("Closing the browser");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome") == true) {
				driver.quit();
				result = Constants.KEYWORD_PASS;
			} else if (CONFIG.getProperty("browserType").equalsIgnoreCase("safari") == true) {
				driver.close();
				result = Constants.KEYWORD_PASS;
			} else {
				driver.close();
				driver.quit();
				result = Constants.KEYWORD_PASS;

			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public String selectList(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("element is now clickable");

			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			// Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			Thread.sleep(1000);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_1(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("element is now clickable");

			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			dropDownListBox.sendKeys(data);

			System.out.println("data select from dropdown");
			Thread.sleep(1000);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_tw(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_hsp(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Thread.sleep(1000);
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);

			droplist.selectByVisibleText(data);
			Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_without_data(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_and__press_Tab_pc_blaze(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			w.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			// WebElement text = driver.findElement(By.id("IDV_SumInsured"));
			dropDownListBox.sendKeys(Keys.TAB);
			Thread.sleep(5000);

			w.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h4[contains(text(),'Cover Detail')])[1]")));
			// w.until(ExpectedConditions.attributeToBeNotEmpty(element, attribute)
			Thread.sleep(3000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL
					+ " - Could not select from list , DUE TO SERVICE EXECUTION TIME EXCEED 17 SEC ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_and__press_Tab_pc_blaze_standalone(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			// WebElement text = driver.findElement(By.id("IDV_SumInsured"));
			dropDownListBox.sendKeys(Keys.TAB);
			Thread.sleep(5000);
			WebDriverWait w= new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			w.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='fa fa-calendar'])[2]")));
			// w.until(ExpectedConditions.attributeToBeNotEmpty(element, attribute)
			Thread.sleep(5000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL
					+ " - Could not select from list , DUE TO SERVICE EXECUTION TIME EXCEED 17 SEC ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_and__press_Tab_pc(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			// WebElement text = driver.findElement(By.id("IDV_SumInsured"));
			dropDownListBox.sendKeys(Keys.TAB);
			Thread.sleep(5000);
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			w.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@id='Risk257_No']")));
			// w.until(ExpectedConditions.attributeToBeNotEmpty(element, attribute)
			Thread.sleep(5000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL
					+ " - Could not select from list , DUE TO SERVICE EXECUTION TIME EXCEED 17 SEC ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_and__press_Tab(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			Thread.sleep(1000);
			dropDownListBox.sendKeys(Keys.TAB);
			/*
			 * WebDriverWait wait = new WebDriverWait(driver,120);
			 * wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("(//h4[contains(text(),'Cover Detail')])[1]")));
			 */
			Thread.sleep(5000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_and__press_Tab_liability(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			dropDownListBox.sendKeys(Keys.TAB);
			/*
			 * WebDriverWait wait = new WebDriverWait(driver,120);
			 * wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("(//h4[contains(text(),'Cover Detail')])[1]")));
			 */
			Thread.sleep(3000);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectListByID_pc(String object, String data) {
		APP_LOGS.debug("Selecting from list");

		try {
			data = data.trim();
			// driver.findElement(By.id(OR.getProperty(object))).clear();

			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectListByID(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			// driver.findElement(By.id(OR.getProperty(object))).clear();

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(1000);
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			Thread.sleep(1000);
			// dropDownListBox.sendKeys(Keys.ENTER);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectListByCSS(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.cssSelector(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectListByName(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			driver.findElement(By.name(OR.getProperty(object))).clear();
			WebElement dropDownListBox = driver.findElement(By.name(OR.getProperty(object)));
			Thread.sleep(3000);
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			Thread.sleep(5000);
			result = Constants.KEYWORD_PASS;
			Thread.sleep(3000);

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectListByLink(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.linkText(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectMatchingDropDown(String object, String data) {
		APP_LOGS.debug("Selecting from list");

		try {
			data = data.toLowerCase();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			dropDownListBox.click();

			Select objDropDown = new Select(dropDownListBox);
			System.out.println(("No of Values in Dropdown:" + objDropDown.getOptions().size()));

			for (int i = 0; i < objDropDown.getOptions().size(); i++) {
				String strOption = objDropDown.getOptions().get(i).getText().toString().trim().toLowerCase();
				System.out.println(strOption);
				if (strOption.contains(data)) {
					// if (strOption.startsWith(data.toLowerCase())){
					System.out.println(strOption + ":" + data);
					objDropDown.selectByIndex(i);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(" - Could not select from list." + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";

		}

		return Constants.KEYWORD_PASS;
	}

	public String uploadPhoto(String object, String data) {
		APP_LOGS.debug("uploading photo...");
		try {
			data = System.getProperty("user.dir") + data;
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.linkText(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Getting error while photo uploading";
		}

		return result;
	}

	public String uploadPhoto_basic(String object, String data) {
		APP_LOGS.debug("uploading photo...");
		try {
			// data = System.getProperty("user.dir") + data;
			WebElement uploadElement = driver.findElement(By.xpath("(//img[@class='ng-scope'])[1]"));

			System.out.println(data);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			uploadElement.sendKeys("C:\\Users\\shahpart\\Desktop\\3 mb image");
			// driver.findElement(By.linkText(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Getting error while photo uploading";
		}

		return result;
	}

	public String uploadDoc(String object, String data) {
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try {
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:" + strPath);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(strPath);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// driver.manage().window().maximize();

			// open upload window
			driver.findElement(By.xpath("(//img[@alt='Upload-document'])[1]")).click();

			// put path to your image in a clipboard
			StringSelection ss = new StringSelection("C:\\Users\\shahpart\\Downloads\\upload");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

			// imitate mouse events like ENTER, CTRL+C, CTRL+V
			Robot robot = new Robot();
			robot.delay(250);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(90);
			robot.keyRelease(KeyEvent.VK_ENTER);

			sleep(3);

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		return Constants.KEYWORD_PASS;
	}

	public String uploadDocumentt(String object, String data) {
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try {

			Thread.sleep(2000);
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			JavascriptExecutor j = (JavascriptExecutor) driver;
			if (j.executeScript("return document.readyState").toString().equals("complete")) {
				System.out.println("Page has loaded");
			}

			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			
			// creating object of Robot class
		    Robot rb = new Robot();
		 
		    // copying File path to Clipboard
//		    StringSelection str = new StringSelection("D:\\Automation\\Web Automation\\PIE-HDFC\\upload\\profile pic_basic detail_page"
//		    		+ "");
//		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		 
		    
		    StringSelection strPath1 = new StringSelection(data);
		    System.out.println("Path on Clipboard: " + strPath1);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strPath1, null);
		    
		    
		     // press Contol+V for pasting
		     rb.keyPress(KeyEvent.VK_CONTROL);
		     rb.keyPress(KeyEvent.VK_V);
		 
		    // release Contol+V for pasting
		    rb.keyRelease(KeyEvent.VK_CONTROL);
		    rb.keyRelease(KeyEvent.VK_V);
		 
		    // for pressing and releasing Enter
		    rb.keyPress(KeyEvent.VK_ENTER);
		    rb.keyRelease(KeyEvent.VK_ENTER);
			

//			//strPath = data;
//			strPath = System.getProperty("user.dir") + data;
//			System.out.println("data:" + data);
//			
//			Thread.sleep(1000);
//			// open upload window
//			driver.findElement(By.xpath(OR.getProperty(object))).click();
//			System.out.println("CLicked on Image Icon..");
//			Thread.sleep(3000);

//			// put path to your image in a clipboard
//			StringSelection ss = new StringSelection(data);
//			System.out.println("Path on Clipboard: " + ss);
//			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
//
//			// imitate mouse events like ENTER, CTRL+C, CTRL+V
//			Robot robot = new Robot();
//			robot.delay(1000);
//
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.delay(250);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.delay(250);
//			robot.keyRelease(KeyEvent.VK_ENTER);
//
//			sleep(3);

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		return Constants.KEYWORD_PASS;
	}

	
	
	public String uploadDoc2(String object, String data)
	{
		String strPath = "";
		//log.debug("uploading Document...");
		try {
		
		strPath = System.getProperty("user.dir");
		System.out.println("path:" + strPath);
		driver.findElement(By.xpath(OR.getProperty(object))).click();
		Thread.sleep(3000);
		
		//StringSelection ss = new StringSelection(data);
		StringSelection ss = new StringSelection(data);
		System.out.println("Path on Clipboard: " + ss);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		Robot robot = new Robot();
		robot.delay(2500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		robot.delay(4000);
		}
		catch (Exception e)
		{
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		    return Constants.KEYWORD_PASS;
	}
	
	
//	protected void fileUpload(String strFilepath) {
//		try {
//			Robot rs = new Robot();
//			File file = new File(strFilepath);
//			String str = file.getAbsolutePath();
//			StringSelection selection = new StringSelection(str);
//			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//			clipboard.setContents(selection, selection);
//			rs.keyPress(KeyEvent.VK_CONTROL);
//			rs.keyPress(KeyEvent.VK_V);
//			rs.keyRelease(KeyEvent.VK_V);
//			rs.keyRelease(KeyEvent.VK_CONTROL);
//			rs.keyPress(KeyEvent.VK_ENTER);
//			rs.keyRelease(KeyEvent.VK_ENTER);
//			rs.keyRelease(KeyEvent.VK_ENTER);
//			rs.delay(5000);
//		} catch (Exception e) {
//			Assert.fail("Unable to upload File: " + strFilepath, e.getCause());
//		}
//	}
		
	//public void PDFfileuploading(String object,String locator){
	public String PDFfileuploading(String object,String locator){
	String strPath = "";
		APP_LOGS.debug("uploading PDF...");
		try {
		strPath = Constants.PDF_FOLDER_PATH;
		System.out.println("path:" + strPath);
		WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));

		objElement.click();
		Thread.sleep(5000);
		
		StringSelection ss = new StringSelection(strPath);
		System.out.println("abc:"+ ss);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		//Toolkit.getDefaultToolkit().getSystemClipboard().getContents(Constants.IMAGE_FOLDER_PATH);

		Robot robot = new Robot();
		robot.delay(2500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		robot.delay(4000);
		
		}
		catch (Exception e)
		{
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		    return Constants.KEYWORD_PASS;
	}
	
	
	
	public String IMAGEfileuploading(String object,String locator){
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try {
		strPath = Constants.IMAGE_FOLDER_PATH;
		System.out.println("path:" + strPath);
		WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));

		objElement.click();
		Thread.sleep(5000);
		
		StringSelection ss = new StringSelection(strPath);
		System.out.println("abc:"+ ss);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		//Toolkit.getDefaultToolkit().getSystemClipboard().getContents(Constants.IMAGE_FOLDER_PATH);

		Robot robot = new Robot();
		robot.delay(2500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		robot.delay(4000);
		
		}
		catch (Exception e)
		{
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		    return Constants.KEYWORD_PASS;
		
	}
	
	
	
	public String uploadDocByCSS(String object, String data) {
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try {
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:" + strPath);
			driver.findElement(By.cssSelector(OR.getProperty(object))).sendKeys(strPath);
			sleep(3);

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		return Constants.KEYWORD_PASS;
	}

	public String uploadDocByID(String object, String data) {
		String strPath = "";
		APP_LOGS.debug("uploading Document...");
		try {
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:" + strPath);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(strPath);
			sleep(3);

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";

		}

		return Constants.KEYWORD_PASS;
	}

	public String verifyAllListElements(String object, String data) {
		APP_LOGS.debug("Verifying the selection of the list");
		try {
			WebElement droplist = driver.findElement(By.xpath(OR.getProperty(object)));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));

			// extract the expected values from OR. properties
			String temp = data;
			String allElements[] = temp.split(",");
			// check if size of array == size if list
			if (allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL + "- size of lists do not match";

			for (int i = 0; i < droplist_cotents.size(); i++) {
				if (!allElements[i].equals(droplist_cotents.get(i).getText())) {
					return Constants.KEYWORD_FAIL + "- Element not found - " + allElements[i];
				}
			}
		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL;

		}

		return Constants.KEYWORD_PASS;
	}

	public String verifyListSelection(String object, String data) {
		APP_LOGS.debug("Verifying all the list elements");
		try {
			String expectedVal = data;
			// System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist = driver.findElement(By.xpath(OR.getProperty(object)));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal = null;
			for (int i = 0; i < droplist_cotents.size(); i++) {
				String selected_status = droplist_cotents.get(i).getAttribute("selected");
				if (selected_status != null)
					actualVal = droplist_cotents.get(i).getText();
			}

			if (!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - " + expectedVal;

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	public static String getURLFromEmail(String USERNAME, String PASSWORD) {
		APP_LOGS.debug("get URL from email");
		String urlStr = null;
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", USERNAME, PASSWORD);
			// Store store = session.getStore("imaps");
			// store.connect("imap.mail.yahoo.com", "satyendramca",
			// "prakash222");
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();

			System.out.println(message[message.length - 1].getContent());
			String emailContent = message[message.length - 1].getContent().toString();
			urlStr = getLinks(emailContent);
			System.out.println("URL from:" + urlStr);

		} catch (Exception e) {
			// return Constants.KEYWORD_FAIL+"not found URL from email";
			// APP_LOGS.debug("URL not found from email");
		}
		return urlStr;
	}

	public static String getLinks(String text) {
		APP_LOGS.debug("get URL from string");
		String urlStr = null;
		try {
			String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			while (m.find()) {
				urlStr = m.group();
				if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
					urlStr = urlStr.substring(1, urlStr.length() - 1);
					System.out.println(urlStr);

				}
			}
		} catch (Exception e) {
			// return Constants.KEYWORD_FAIL+"NOT found URL from string";
		}
		return urlStr;
	}

	/*
	 * public String getPasswordSetupURL(String object,String data){
	 * APP_LOGS.debug("get password setup URL from email"); String strPasswordURL =
	 * null; try{ strPasswordURL = getURLFromEmail(USERNAME, PASSWORD);
	 * System.out.println("Password setup URL:" +strPasswordURL); }catch(Exception
	 * e){ return Constants.KEYWORD_FAIL+" URL not found"; } return
	 * Constants.KEYWORD_PASS; }
	 */

	/*
	 * public String verifySearchResult(String object,String data){
	 * APP_LOGS.debug("Verifying the Search Results"); try{ data=data.toLowerCase();
	 * for(int i=3;i<=5;i++){ String
	 * text=driver.findElement(By.xpath(OR.getProperty(
	 * "search_result_heading_start")+i+OR.getProperty(
	 * "search_result_heading_end"))).getText().toLowerCase(); if(text.indexOf(data)
	 * == -1){ return Constants.KEYWORD_FAIL+ " Got the text - "+text; } }
	 * 
	 * }catch(Exception e){ return Constants.KEYWORD_FAIL+"Error -->"
	 * +e.getMessage(); }
	 * 
	 * return Constants.KEYWORD_PASS;
	 * 
	 * }
	 */

	// not a keyword

	public String verifyErrorMsg(String object, String data) throws IOException {

		try {

			sleep(3);
			APP_LOGS.debug("verify error message");
			FileInputStream fs1 = new FileInputStream(
					System.getProperty("user.dir") + "//src//com//sample//config//message.properties");
			MSG = new Properties();
			MSG.load(fs1);

			for (int index = 0; index <= MSG.size(); index++) {
				String expectedMsg = MSG.getProperty("msg" + index);
				APP_LOGS.debug("Expected Error Message :" + expectedMsg);
				String actgualMsg = driver.findElement(By.xpath(OR.getProperty(object))).getText();
				APP_LOGS.debug("Actual Error Message :" + actgualMsg);
				if (actgualMsg.equals(expectedMsg)) {
					result = Constants.KEYWORD_PASS;
					break;

				} else {
					result = Constants.KEYWORD_FAIL;
				}
			}
			fs1.close();
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Error -->" + e.getMessage();
		}
		return result;

	}

	public String validateAllErrorMessage(String object, String data) throws IOException {

		try {

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(
					System.getProperty("user.dir") + "//src//com//sample//config//message.properties");
			msgCONFIG = new Properties();
			msgCONFIG.load(fs1);

			for (int index = 1; index <= msgCONFIG.size(); index++) {
				lstErrorMessages.add(msgCONFIG.getProperty("msg" + index));

			}
			List<WebElement> objErrors = driver.findElements(By.xpath(OR.getProperty(object)));

			if (objErrors.size() > 0) {
				// System.out.println(objErrors.size());
				for (int i = 0; i < objErrors.size(); i++) {
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println(objErrors.get(i).getText().toString());
				}
			}

			for (String errorMsg : lstActualError) {
				for (int i = 0; i < lstErrorMessages.size(); i++) {
					if (lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg)) {
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						System.out.println("Field validation message found :" + errorMsg);
						result = Constants.KEYWORD_PASS;
						break;
					} else if (i == lstErrorMessages.size()) {
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						System.out.println("Field validation message not found :" + errorMsg);
						result = Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();
			// Comment by Timir
			/*
			 * if (lstErrorMessages.containsAll(lstActualError)){ return
			 * Constants.KEYWORD_PASS; } else{ return Constants.KEYWORD_FAIL +
			 * " Error validation not matched"; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
		}

		return result;
	}

	public String checkString(String object, String data) {
		APP_LOGS.debug("Verifying the string displayed");

		try {

			List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + data + "')]"));

			if (list.size() > 0) {
				return Constants.KEYWORD_PASS + "- String displayed";
			} else {
				return Constants.KEYWORD_FAIL + "- String is not displayed";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Error -->" + e.getMessage();
		}

	}

	public String getUniqueIntegerValue(String object, String data) {
		try {
			String strData = "";

			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhhss");

			strData = "1" + df.format(cal.getTime());
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, strData)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}

		return result;

	}

	public String getIntegerValue() {
		try {

			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhhss");
			result = df.format(cal.getTime());

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}

		return result;
	}

	public String getUniqueEmail(String object, String data) {
		try {
			String email = "";
			email = "email" + getIntegerValue() + "@test.com";

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, email)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getUniqueEntity(String object, String data) {
		try {
			String entity = "";
			entity = "entity" + getIntegerValue();

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, entity)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getUniqueField(String object, String data) {
		try {
			String field = "";
			field = "field" + getIntegerValue();

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, field)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getUniqueStringValue(String object, String data) {
		try {
			String strData = "";
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("mmddhh");

			strData = "TEST" + df.format(cal.getTime());
			// System.out.println(strData + ":" + object + ":" + data + ":" +
			// currentTestDataSetID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, strData)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

		// return "test" + df.format(cal.getTime());
	}

	public String popupClickOk(String object, String data) {

		String strResult = Constants.KEYWORD_FAIL;

		try {
			Alert objPopup = driver.switchTo().alert();
			if (objPopup != null) {
				System.out.println("POPUP MESSAGE:" + objPopup.getText());
				// Perform Event - Click OK
				objPopup.accept();
				strResult = Constants.KEYWORD_PASS;
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return strResult;
		}

		return strResult;

	}

	public String assertIfObjectNotFound(String object, String data) {

		// driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
		try {
			Thread.sleep(5000);

			List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
			if (objElements.size() > 0) {
				return Constants.KEYWORD_FAIL;
			} else {
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				return Constants.KEYWORD_PASS + " - Object not found";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.KEYWORD_FAIL;
	}

	public String assertIfObjectNotFoundById(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_FAIL;
			} else {
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result = Constants.KEYWORD_PASS + " - Object not found";
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectNotFoundByLink(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_FAIL;
			} else {
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result = Constants.KEYWORD_PASS + " - Object not found";
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFound(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.xpath(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_PASS;
			} else {
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result = Constants.KEYWORD_FAIL + " - Object not found";
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFoundByID(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.id(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_PASS;
			} else {
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result = Constants.KEYWORD_FAIL + " - Object not found";
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFoundByLink(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.linkText(OR.getProperty(object)));
			System.out.println(objElements);

			if (objElements.size() > 0) {
				result = Constants.KEYWORD_PASS;
			} else {
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result = Constants.KEYWORD_FAIL + " - Object not found";
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String loginAdmin(String object, String data) {
		APP_LOGS.debug("Logging by user");

		try {

			driver.findElement(By.xpath(OR.getProperty("txt_userID"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txt_userID")))
					.sendKeys(CONFIG.getProperty("admin_userName").toString());
			driver.findElement(By.xpath(OR.getProperty("txt_password"))).clear();
			driver.findElement(By.xpath(OR.getProperty("txt_password")))
					.sendKeys(CONFIG.getProperty("admin_password").toString());
			driver.findElement(By.xpath(OR.getProperty("btn_Go"))).click();
			// driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to login " + e.getMessage();

		}

		return Constants.KEYWORD_PASS;
	}

	public String closeSuccessBox(String object, String data) {

		String status = "";
		try {
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {
				sleep(10);
				waitforElementToLoadByCSS("broker.signup.success.popup", data);

				List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
				if (objFrameContainer.size() > 0) {
					System.out.println("Iframe found");
				} else {
					sleep(5);
					waitforElementToDisplayByCSS("broker.signup.success.popup", data);
				}
				sleep(10);
			}
			waitforElementToDisplayByCss("broker.signup.success.popup", data);
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#success"));
			if (objFrameContainer.size() > 0) {
				System.out.println("IFRAME FOUND");
				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.cssSelector("input#submitRequest"));
				if (objClose.size() > 0) {
					System.out.println("CLOSE BUTTON FOUND");
					// ClickWebElement(objClose.get(0));
					objClose.get(0).click();
					status = Constants.KEYWORD_PASS;
				}
			} else {
				status = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String closePopUpBox(String object, String data) {

		String status = "";
		try {
			sleep(3);
			// List<WebElement> objFrameContainer =
			// driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver
					.findElements(By.cssSelector("iframe#" + OR.getProperty(object) + ""));
			if (objFrameContainer.size() > 0) {
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.cssSelector(".btn.offset2"));

				try {
					if (objClose.size() > 0) {
						System.out.println("CLOSE BUTTON FOUND");
						// ClickWebElement(objClose.get(0));
						objClose.get(0).click();
						status = Constants.KEYWORD_PASS;
					}
				} catch (Exception e) {

					System.out.println(e + "Frame not found");
				}
			} else {
				status = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String switchToFrame(String object, String data) {

		try {
			windowHandle = driver.getWindowHandle();
			List<WebElement> objFrameContainer = driver
					.findElements(By.cssSelector("iframe#" + OR.getProperty(object) + ""));
			if (objFrameContainer.size() > 0) {
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;

		}
		return result;
	}

	public String switchToWidnow(String object, String data) {
		object = "";

		try {
			driver.switchTo().window(windowHandle);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_PASS;

		}
		return result;
	}

	public String ClickWebElement(WebElement objElement) {
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {
				objElement.sendKeys("\n");

				if (objElement.isDisplayed() == true) {
					objElement.click();

				}

			} else {
				String windowHandle = driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				driver.switchTo().window(windowHandle);
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}

		return Constants.KEYWORD_PASS;
	}

	public String verifySearchResults(String object, String data) throws InterruptedException {

		String actualData = "";
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.xpath("//input[@class='input-medium']")).sendKeys(data);
			// driver.findElement(By.xpath(OR.getProperty("admin.manageissuer.go.btn"))).click();
			try {
				driver.findElement(By.xpath("//input[@value='Go']")).click();
			} catch (Exception e) {
				driver.findElement(By.xpath("//input[@value=' Go ']")).click();
			}
			sleep(3);
			int rowSize = driver.findElements(By.xpath("//table//tbody/tr")).size();

			System.out.println("total row size is" + rowSize);
			for (int index = 1; index <= rowSize; index++) {
				int colSize = driver.findElements(By.xpath("//table//tbody/tr[" + index + "]//td")).size();// Timir.n
				for (int ind = 1; ind <= colSize; ind++) {
					// String
					// actualData=driver.findElement(By.xpath("//table//tbody//tr["+index+"]//td")).getText();//Timir.o

					actualData = driver.findElement(By.xpath("//table//tbody//tr[" + index + "]//td[" + ind + "]"))
							.getText();// Timir.n
					System.out.println("Actual data is" + actualData);
					if (actualData.equals(data)) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						result = Constants.KEYWORD_FAIL + " -- text not verified " + actualData + " -- " + data;
					}
					// driver.findElement(By.xpath("//table//tr["+index+"]//td//.[contains(text(),'"+data+"')]"))
					// ;
				}

				if (result.equals(Constants.KEYWORD_PASS)) {
					break;
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- text not verified ";
		}
		return result;
	}

	public String verifySearchResultsByID(String object, String data) throws InterruptedException {

		try {
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.xpath("//input[@class='input-medium']")).sendKeys(data);
			try {
				// driver.findElement(By.xpath("//form[@name='frmfindplan']//div[@class='center']//input[@class='btn']")).click();
				driver.findElement(By.id(OR.getProperty("common.next.btn"))).click();
			} catch (Exception e) {
				sleep(2);
				driver.findElement(By.xpath("//input[contains(@value,'Go')]")).click();
			}
			sleep(3);
			int rowSize = driver.findElements(By.xpath("//table//tbody//tr")).size();
			for (int index = 1; index <= rowSize; index++) {
				int tdSize = driver.findElements(By.xpath("//table//tbody//tr//td")).size();
				for (int tdindex = 1; tdindex <= tdSize; tdindex++) {
					String actualData = driver
							.findElement(By.xpath("//table//tbody//tr[" + index + "]//td[" + tdindex + "]")).getText();
					System.out.println("Actual data is" + actualData);
					System.out.println("expected data is" + data);
					if (actualData.equalsIgnoreCase(data)) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						result = Constants.KEYWORD_FAIL + " -- text not verified " + actualData + " -- " + data;
					}
					// driver.findElement(By.xpath("//table//tr["+index+"]//td//.[contains(text(),'"+data+"')]"))
					// ;
				}

				if (result.equalsIgnoreCase(Constants.KEYWORD_PASS)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL + " -- text not verified ";
		}
		return result;
	}

	public String CustomerList(String object, String data) {

		APP_LOGS.debug("verify table text box");
		try {
			int size = driver.findElements(By.xpath("//table[@id='CRM_customerList_AdvanceSearch']//tbody//tr")).size();
			ArrayList<String> SeedAcress = new ArrayList<String>();

			for (int unsold = 1; unsold <= size; unsold++) {

				String Seed = driver
						.findElement(By.xpath(
								"//table[@id='CRM_customerList_AdvanceSearch']//tbody//tr[" + unsold + "]//td[6]"))
						.getText();

				/*
				 * String SeedValue = driver .findElement(By.xpath(
				 * "//table[@id='CRM_customerList_AdvanceSearch']//tbody//tr[1]//td[6]"))
				 * .getText();
				 */

				System.out.println(Seed);

			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * public String getCustomerListRow(String object, String data) { try { String
	 * Seeds = ""; Seeds = "field" + CustomerList();
	 * 
	 * if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID,
	 * Seeds)) { result = Constants.KEYWORD_PASS; } else { result =
	 * Constants.KEYWORD_FAIL; }
	 * 
	 * } catch (Exception e) { result = Constants.KEYWORD_FAIL; } return result; }
	 */

	public String pressEnterinInputByID(String object, String data) {
		APP_LOGS.debug("Pressing Tab in text box");

		// String newdata = String.valueOf(data);
		try {
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {
				driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
			} else {
				driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			}
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;

		}
		return result;
	}

	public String clickModuleMenuLink(String object, String data) throws InterruptedException {
		object = "";
		try {
			/*
			 * if(driver.findElement(By.linkText(data)).isDisplayed()==true ||
			 * driver.findElement(By.linkText(data)).isEnabled() ==true ) {
			 */

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.linkText(data));
				try {

					driver.findElement(By.linkText(data)).sendKeys("\n");
					sleep(3);

					List<WebElement> objElements = driver.findElements(By.linkText(data));
					if (objElements.size() > 0) {
						driver.findElement(By.linkText(data)).click();
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e1) {
					driver.findElement(By.linkText(data)).click();
				}

				// driver.findElement(By.linkText(data)).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.linkText(data)).click();
			}
			sleep(5);

			result = Constants.KEYWORD_PASS;
			// }
		} catch (Exception e) {
			int mnuCount = driver.findElements(By.xpath("//div[@id='menu']/div/ul/li")).size();
			for (int mnuIndex = 1; mnuIndex <= mnuCount; mnuIndex++) {
				String mnuText = driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]"))
						.getText();
				mnuText = mnuText.toUpperCase();
				data = data.toUpperCase();
				if (mnuText.equals(data)) {
					if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

						String windowHandle = driver.getWindowHandle();
						driver.switchTo().window(windowHandle);
						try {

							driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]//a"))
									.sendKeys("\n");
							sleep(3);
							if (driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]//a"))
									.isDisplayed() == true) {
								driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]//a"))
										.click();
							}
						} catch (Exception e1) {
							driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]//a")).click();
						}
						result = Constants.KEYWORD_PASS;
					} else {
						driver.findElement(By.xpath("//div[@id='menu']/div/ul/li[" + mnuIndex + "]//a")).click();
						result = Constants.KEYWORD_PASS;
					}
					break;
				} else {
					result = Constants.KEYWORD_FAIL + "link not found";
				}

			}

			// throw new AssertionError();
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @date:29th Jan 2013
	 * 
	 * @Purpose: Function verifies the header text by css
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyTextContentByCSS(String object, String data) {
		APP_LOGS.debug("Verifying the text content");
		try {
			sleep(3);
			String actual = driver.findElement(By.cssSelector(OR.getProperty(object))).getText();
			String expected = data;

			if (actual.trim().contains(expected.trim()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/*
	 * @Author=Timir
	 * 
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToLoadByXpath(String object, String data) {

		data = CONFIG.getProperty("implicitwait");
		try {
			sleep(3);
			//WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(data));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by ID to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToLoadByID(String object, String data) {

		data = CONFIG.getProperty("implicitwait");
		try {
			sleep(3);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			//WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			System.out.println("object name is" + object);
			String waitObject = OR.getProperty(object);
			System.out.println("waitObject name is" + waitObject);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(waitObject)));

			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by css to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToLoadByCSS(String object, String data) {

		data = CONFIG.getProperty("implicitwait");
		try {
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			waiting.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty(object))));
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to Get current Date
	 * 
	 * @Return:Current Date DD/MM/YYYY format
	 */

	public String getpreviousyear_from_CurrentDate(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String year = yearFormatter.format(currentDate.getTime());

			int yearcast = Integer.parseInt(year);
			int final_year = yearcast - 1;
			System.out.println(final_year);

			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());

			// date = day + "/" + month + "/" + previous_year;
			date = (day.length() == 1 ? "0" + day : day) + "/" + (month.length() == 1 ? "0" + month : month) + "/"
					+ previous_year;
			System.out.println("current date minus 1 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_only_previousyear_from_CurrentDate(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			// SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String year = yearFormatter.format(currentDate.getTime());

			int yearcast = Integer.parseInt(year);
			int final_year = yearcast - 1;
			System.out.println(final_year);

			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			// String day = dayFormatter.format(currentDate.getTime());

			// date = day + "/" + month + "/" + previous_year;
			date = /* (day.length()==1? "0"+ day:day) + "/" + */(month.length() == 1 ? "0" + month : month) + "/"
					+ previous_year;
			System.out.println("current date minus 1 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_next_year_from_CurrentDate(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast + 3;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			String days = dayFormatter.format(currentDate.getTime());

			date = (days.length() == 1 ? "0" + days : days) + "/" + (month.length() == 1 ? "0" + month : month) + "/"
					+ previous_year;
			System.out.println("current date plus 3 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_next_year_from_next_Date(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast + 1;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast + 3;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			// String days = dayFormatter.format(currentDate.getTime());

			// date = next_day + "/" + "0"+month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (month.length() == 1 ? "0" + month : month) + "/" + previous_year;
			System.out.println("next date plus 3 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_next_5thyear_from_next_Date(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast - 1;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast + 5;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			int monthcast = Integer.parseInt(month);
			int final_month = monthcast - 1;
			System.out.println(final_month);
			String previous_month = Integer.toString(final_month);
			System.out.println(previous_month);
			// String days = dayFormatter.format(currentDate.getTime());

			// date = next_day + "/" + "0"+month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (previous_month.length() == 1 ? "0" + previous_month : previous_month) + "/" + previous_year;
			System.out.println("current date minus(-) 1 day,1 month previous, plus 5 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_next_year_from_previous_Date(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast - 1;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast + 3;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			// String days = dayFormatter.format(currentDate.getTime());

			// date = next_day + "/" +"0"+ month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (month.length() == 1 ? "0" + month : month) + "/" + previous_year;
			System.out.println("next date plus 3 year:" + date);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_previousmonth_and_next_day(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast + 1;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			int monthcast = Integer.parseInt(month);
			int final_month = monthcast - 1;
			System.out.println(final_month);
			String previous_month = Integer.toString(final_month);
			System.out.println(previous_month);
			// String days = dayFormatter.format(currentDate.getTime());

			// date = next_day + "/" +"0"+ previous_month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (previous_month.length() == 1 ? "0" + previous_month : previous_month) + "/" + previous_year;
			System.out.println("current date (-) minus 4 month(+) plus 1 day:" + date);
			Thread.sleep(1000);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_month_and_next_day(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast + 1;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			int monthcast = Integer.parseInt(month);
			int final_month = monthcast - Integer.parseInt(data);
			System.out.println(final_month);
			String previous_month = Integer.toString(final_month);
			System.out.println(previous_month);
			// String days = dayFormatter.format(currentDate.getTime());

			// date = next_day + "/" +"0"+ previous_month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (previous_month.length() == 1 ? "0" + previous_month : previous_month) + "/" + previous_year;
			System.out.println("current date (-) minus 4 month(+) plus 1 day:" + date);
			Thread.sleep(1000);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_previousmonth_and_previous_day(String object, String data) {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String day = dayFormatter.format(currentDate.getTime());
			int daycast = Integer.parseInt(day);
			int final_day = daycast - 5;
			System.out.println(final_day);
			String next_day = Integer.toString(final_day);
			System.out.println(next_day);

			String year = yearFormatter.format(currentDate.getTime());
			int yearcast = Integer.parseInt(year);
			int final_year = yearcast;
			System.out.println(final_year);
			String previous_year = Integer.toString(final_year);
			System.out.println(previous_year);

			String month = monthFormatter.format(currentDate.getTime());
			int monthcast = Integer.parseInt(month);
			int final_month = monthcast - 2;
			System.out.println(final_month);
			String previous_month = Integer.toString(final_month);
			System.out.println(previous_month);
			// String days = dayFormatter.format(currentDate.getTime());

			date = next_day + "/" + "0" + previous_month + "/" + previous_year;
			date = (next_day.length() == 1 ? "0" + next_day : next_day) + "/"
					+ (previous_month.length() == 1 ? "0" + previous_month : previous_month) + "/" + previous_year;
			System.out.println("current date (-) minus 2 month(+) plus 1 day:" + date);
			Thread.sleep(1000);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, date)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String getprevious_month_from_CurrentDate(String object, String data) {
		String date = "";
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -1 * 2);
			// c.add(Calendar.DATE, -1*2 );
			// c.getTime();
			System.out.println(c.getTime());
			Date finaldate = c.getTime();
			SimpleDateFormat formated_date = new SimpleDateFormat("dd/MM/yyyy");
			String formated_new_date = formated_date.format(c.getTime());
			System.out.println("current date minus 2 month :" + formated_new_date);
			// String year = yearFormatter.format(currentDate.getTime());
			// date= formated_date;

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, formated_new_date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String get_next_month_from_CurrentDate(String object, String data) {
		String date = "";
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, 1 * 1);
			// c.add(Calendar.DATE, -1*2 );
			// c.getTime();
			System.out.println(c.getTime());
			Date finaldate = c.getTime();
			SimpleDateFormat formated_date = new SimpleDateFormat("dd/MM/yyyy");
			String formated_new_date = formated_date.format(c.getTime());
			System.out.println("current date minus 2 month :" + formated_new_date);
			// String year = yearFormatter.format(currentDate.getTime());
			// date= formated_date;

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, formated_new_date)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);

			}
		} catch (Exception e) {
		}
		return result;

	}

	public String getCurrentDateandTime() {
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String year = yearFormatter.format(currentDate.getTime());
			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());

			date = day + "/" + month + "/" + year;

		} catch (Exception e) {
		}
		return date;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to Get current Date
	 * 
	 * @Return:Current Date DD/MM/YYYY format
	 */
	public String getCurrentDateMMDDYYYY(String object, String data) {
		object = "";
		data = "";
		String date = "";
		try {
			Calendar currentDate = Calendar.getInstance();

			SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
			SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

			String year = yearFormatter.format(currentDate.getTime());
			String month = monthFormatter.format(currentDate.getTime());
			String day = dayFormatter.format(currentDate.getTime());

			date = month + "/" + day + "/" + year;
		} catch (Exception e) {
		}
		return date;

	}

	public String verifySelectedListBoxValueByXpath(String object, String data) {
		boolean vres = driver.findElement(By.xpath(
				"//select[@id='" + OR.getProperty(object) + "']//option[contains(.,'" + data + "') and @selected='']"))
				.isDisplayed();
		try {
			if (vres) {

				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public static String getDefaultPageWaitTime() {
		return CONFIG.getProperty("implicitwait").toString();
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to click on link which contains data
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String clickByXpath_Containstext(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Clicking on link ");
		try {
			// driver.findElement(By.linkText(OR.getProperty(object))).click();
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath("//a[contains(text(),'" + data + "')]"));
				try {
					driver.findElement(By.xpath("//a[contains(text(),'" + data + "')]")).sendKeys("\n");

					sleep(3);
					List<WebElement> objElements = driver
							.findElements(By.xpath("//a[contains(text(),'" + data + "')]"));
					if (objElements.size() > 0) {
						driver.findElement(By.xpath("//a[contains(text(),'" + data + "')]")).click();
						Thread.sleep(5000);
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {
					driver.findElement(By.xpath("//a[contains(text(),'" + data + "')]")).click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath("//a[contains(text(),'" + data + "')]")).click();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
			// throw new NoSuchElementException("No such element found");
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for text to be present in element
	 * 
	 * @Return:Result=Pass/Fail
	 * 
	 * @object: Element ID
	 * 
	 * @data : Text to be present in element
	 */
	/*
	 * public String waitForTextInElementByID(String object, String data) {
	 * 
	 * String time = CONFIG.getProperty("implicitwait"); try { WebDriverWait waiting
	 * = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
	 * System.out.println("object name is" + object); String waitObject =
	 * OR.getProperty(object); System.out.println("waitObject name is" +
	 * waitObject);
	 * waiting.until(ExpectedConditions.textToBePresentInElement(By.id(waitObject)))
	 * ;
	 * 
	 * waiting.until(ExpectedConditions.textToBePresentInElement(By.id(OR.
	 * getProperty(object)), data));
	 * 
	 * return Constants.KEYWORD_PASS; } catch (Exception e) { result =
	 * Constants.KEYWORD_FAIL; } return result; }
	 */

	public String doLogout(String object, String data) {

		try {

			driver.findElement(By.linkText(OR.getProperty(object))).click();
			sleep(6);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to exact mathc the expected and actual text value
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyexactTextMatch(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data;

			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to swithc to frame and choose address for broker business
	 * address
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String switchToFramewithID(String object, String data) {

		String status = "";
		try {
			sleep(3);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);

			// List<WebElement> objFrameContainer =
			// driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#modalData"));
			if (objFrameContainer.size() > 0) {
				sleep(3);
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));
				// pause(6000);
				driver.findElement(
						By.id(OR.getProperty("broker.certificationinformation.checkyouraddress.likelymatchs.opt")))
						.click();
				driver.findElement(
						By.id(OR.getProperty("broker.certificationinformation.checkyouraddress.submitAddr.btn")))
						.click();
			} else {
				status = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String closePopUpBoxForSelectAddress(String object, String data) {

		String status = "";

		try {
			sleep(3);
			// List<WebElement> objFrameContainer =
			// driver.findElements(By.cssSelector("iframe#success"));
			List<WebElement> objFrameContainer = driver.findElements(By.cssSelector("iframe#modalData"));
			if (objFrameContainer.size() > 0) {
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));

				List<WebElement> objClose = driver.findElements(By.id("userdefault"));

				try {
					if (objClose.size() > 0) {
						System.out.println("OPTION BUTTON FOUND FOR YOU ENTERED ADDRESS");
						// ClickWebElement(objClose.get(0));
						String windowHandle = driver.getWindowHandle();
						WebElement objElement = objClose.get(0);
						Actions builder = new Actions(driver);
						builder.moveToElement(objElement).build().perform();
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("var evt = document.createEvent('MouseEvents');"
								+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
								+ "arguments[0].dispatchEvent(evt);", objElement);

						// driver.switchTo().frame(objFrameContainer.get(0));
						driver.findElement(By.id("submitAddr")).click();

						driver.findElement(By.id("iFrameClose")).click();
						driver.switchTo().window(windowHandle);
						// pause(3000);

						status = Constants.KEYWORD_PASS;
					}
				} catch (Exception e) {

					System.out.println(e + "Frame not found");
				}
			} else {
				status = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			status = Constants.KEYWORD_FAIL + "-Object Not found";
		}

		System.out.println("STATUS:" + status);
		return status;

	}

	public String existByLinkText(String object, String data) {
		APP_LOGS.debug("Checking existance of element");
		try {
			driver.findElement(By.linkText(OR.getProperty(object)));
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}

		return Constants.KEYWORD_PASS;
	}

	public boolean setCellData(String path, String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				// if(row.getCell(i).getStringCellValue().trim().equals(colName))
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to get referrence flag from test case sheet
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String getRefFlag(String object, String Data) throws IOException {
		String refFlag = null;
		String refFlagdata = null;
		int columnid = 0;
		try {
			Xls_Reader a = DriverScript.currentTestSuiteXLS;

			// Xls_Reader currentTestSuiteXLS=new
			// Xls_Reader(obj1.currentTestSuiteXLS) ;

			currentTestSuiteXLS = new Xls_Reader(DriverScript.refexcelfilename);
			String tcSheetName = DriverScript.refexcelsheetname;
			fis = new FileInputStream(currentTestSuiteXLS.path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(tcSheetName);
			// To find out index of refFlag column

			// System.out.println("currentTestSuiteXLS"+currentTestSuiteXLS);

			// System.out.println("cols"+currentTestSuiteXLS.getColumnCount(tcSheetName));
			int colindex = 0;
			int newcolindex = 0;
			startIndex = 0;
			for (colindex = startIndex; colindex <= currentTestSuiteXLS.getColumnCount(tcSheetName); colindex++) {
				// System.out.println("sheetname is"+sheet.getSheetName());

				refFlag = currentTestSuiteXLS.getCellData(tcSheetName, colindex, 1);
				// if refFlag column found goes to inner condition
				// System.out.println("refflag"+refFlag);
				if (refFlag.equalsIgnoreCase("RefFlag") == true && refFlag.isEmpty() == false) {

					newcolindex = colindex;
					colindex = colindex + 1;
					result = "Y";
					// break;
				} else {
					result = "N";

				}

				if (result == "Y") {
					// check for Y flag exist or not under refFlag column

					// System.out.println(currentTestSuiteXLS);
					for (int rowindex = 2; rowindex <= currentTestSuiteXLS.getRowCount(tcSheetName); rowindex++) {
						refFlagdata = currentTestSuiteXLS.getCellData(tcSheetName, newcolindex, rowindex);
						// if reflfagdata=Y goes to inner condition
						// System.out.println("RefFlagData"+refFlagdata);
						if (refFlagdata.equalsIgnoreCase("Y")) {

							// return tcid for that row
							String tcids = currentTestSuiteXLS.getCellData(tcSheetName, newcolindex + 1, rowindex);
							// String
							// tcids=currentTestSuiteXLS.getCellData(tcSheetName,"RefTCID",rowindex);
							// moves for the column referrence name to be
							// reflect
							// for(int
							// firstcolindex=0;firstcolindex<newcolindex;firstcolindex++)
							for (int firstcolindex = startIndex; firstcolindex < newcolindex; firstcolindex++) {

								String tcdata = currentTestSuiteXLS.getCellData(tcSheetName, firstcolindex, rowindex);

								String cols = currentTestSuiteXLS.getCellData(tcSheetName, firstcolindex, 1);
								setRefData(DriverScript.refexcelfilename, tcids, cols, rowindex, tcdata);
							}
						}
					}
					// fis.close();
					startIndex = newcolindex + 2;
				}

				/*
				 * else if(result=="N" || result.equals("N")) { startIndex=startIndex+1; }
				 */

			}
			fis.close();
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println("Error in getRefFlag" + e.getMessage());
		}
		return refFlag;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to setref data for child sheets
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public void setRefData(String path, String ReftcID, String col, int row, String tcdata) throws IOException {
		try {
			// currentTestSuiteXLS=new
			// Xls_Reader(System.getProperty("user.dir")+"//src//"+currentTestSuite+".xlsx");
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			// sheet = workbook.getSheet(tcSheetName);
			// System.out.println("workbook name
			// is"+workbook.getActiveSheetIndex());
			String associatetestCaseIDS = ReftcID;
			String[] items = associatetestCaseIDS.split(",");
			List<String> container = Arrays.asList(items);
			for (int index = 0; index < container.size(); index++) {
				sheet = workbook.getSheet(container.get(index).trim());
				String sheetname = sheet.getSheetName();
				for (int rowchild = row; rowchild <= currentTestSuiteXLS.getRowCount(sheetname); rowchild++) {
					// System.out.println("UPDATING:
					// sheet"+sheetname+"Col:"+col+"Data"+tcdata);
					setCellData(path, sheetname, col, rowchild, tcdata);
					// System.out.println("UPDATION Success");
				}
				// Jump to every parent sheet and get reference datarefereence
				// sheet and set auto generated data
				// String refData=getParentSheetData(String tcID,String col,int
				// row);

			}
		} catch (Exception e) {
			System.out.println("Error in setRefData" + e.getMessage());
		}

	}

	public static String getLinksFromNotices(String text) {
		// ArrayList links = new ArrayList();
		String urlStr = null;
		try {
			String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			while (m.find()) {
				urlStr = m.group();
				System.out.println("urlStr:" + urlStr);
			}
		} catch (Exception e) {
			urlStr = "";
		}
		return urlStr;
	}

	public String verifyTextNotContain(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText().trim();
			String expected = data;

			if (!actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by Link Text to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToLoadByLink(String object, String data) {

		data = CONFIG.getProperty("implicitwait");
		try {
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			waiting.until(ExpectedConditions.presenceOfElementLocated(By.linkText(OR.getProperty(object))));
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public String verifyListSelectionByID(String object, String data) {
		APP_LOGS.debug("Verifying all the list elements");
		try {
			String expectedVal = data;
			// System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist = driver.findElement(By.id(OR.getProperty(object)));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal = null;
			for (int i = 0; i < droplist_cotents.size(); i++) {
				String selected_status = droplist_cotents.get(i).getAttribute("selected");
				if (selected_status != null)
					actualVal = droplist_cotents.get(i).getText();
			}

			if (!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - " + expectedVal;

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: To Open link in New tab
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public void openUrlInTab(String url) {
		try {
			String script = "var anchor=document.createElement('a');anchor.target='_blank';anchor.href='%s';anchor.innerHTML='.';document.body.appendChild(anchor);return anchor";
			Object element = ((JavascriptExecutor) driver).executeScript(String.format(script, url));

			if (element instanceof WebElement) {
				WebElement anchor = (WebElement) element;
				anchor.click();
				((JavascriptExecutor) driver).executeScript("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
			}
		} catch (Exception e) {

		}

	}

	public void setSpeed() {
		try {
			long secTime = Long.parseLong(CONFIG.getProperty("setspeed"));
			driver.manage().timeouts().implicitlyWait(secTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Fail" + e);
		}
	}

	public static void killProcess(String serviceName) throws Exception {
		try {
			Runtime.getRuntime().exec(KILL + serviceName);
		} catch (Exception e) {
		}
	}

	public static boolean isProcessRunging(String serviceName) throws Exception {
		try {
			Process p = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				if (line.contains(serviceName)) {
					killProcess(serviceName);
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public String getAlertText(String object, String data) {
		object = "";
		data = "";

		try {
			driver.switchTo().alert();
			String alerttext = driver.switchTo().alert().getText();
			System.out.println("Alert text is" + alerttext);
			driver.switchTo().alert().accept();
			result = Constants.KEYWORD_PASS;
		}

		catch (Exception e) {
			System.out.println("Not able to get text from alert" + e);
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String concateValue(String object, String data) {
		try {

			currentTestSuiteXLS = new Xls_Reader(DriverScript.refexcelfilename);
			String allElements[] = object.split(",");
			String[] datanew = new String[2];
			String concatdata = null;
			datanew[0] = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, allElements[0].trim(),
					DriverScript.datarowid);
			datanew[1] = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, allElements[1].trim(),
					DriverScript.datarowid);
			concatdata = datanew[0] + " " + datanew[1];
			String actualval = driver.findElement(By.cssSelector(OR.getProperty("common.main.hdr"))).getText();
			if (actualval.equalsIgnoreCase(concatdata)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
			return result;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String assertIfObjectFoundByCSS(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_PASS;
			} else {
				System.out.println(Constants.KEYWORD_FAIL + " - Object not found");
				result = Constants.KEYWORD_FAIL + " - Object not found";
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String reloadPage(String object, String data) {
		try {
			driver.navigate().refresh();
			result = "PASS";
		} catch (Exception e) {
			result = "FAIL";
		}
		return result;
	}

	public String clicklinkData(String object, String data) {
		APP_LOGS.debug("Clicking on Button");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]"));
				try {

					driver.findElement(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]"))
							.sendKeys("\n");
					sleep(3);
					List<WebElement> objElements = driver
							.findElements(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]"));
					if (objElements.size() > 0) {
						driver.findElement(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]"))
								.click();
					} else {
						result = Constants.KEYWORD_PASS;
					}

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]"))
							.click();
				}

				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object) + "//a[contains(text(),'" + data + "')]")).click();
				// td[contains(@id, 'name')]//a[contains(text(),'ISR36011')]
			}
			sleep(8);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
			;
			// throw new NoSuchElementException("No such element found");

		}

		return result;
	}

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to click menu link
	 * 
	 * @Returns: Pass if Menu link found . Returns fail if menu link not found
	 */
	public String clickMenuLink(String mnuLink, String pageTitle) {
		String titleResult = Constants.KEYWORD_FAIL;
		APP_LOGS.debug("Clicking on Menu link ");
		try {
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				driver.findElement(By.linkText(mnuLink));
				driver.findElement(By.linkText(mnuLink)).click();
				// sleep(2);
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.linkText(mnuLink)).click();
			}
			sleep(2);
			result = Constants.KEYWORD_PASS;
			if (result.equalsIgnoreCase(Constants.KEYWORD_PASS)) {
				titleResult = getPageTitle(pageTitle);
			}
			if (titleResult.equalsIgnoreCase("PASS")) {
				result = "PASS";
			}
			return result;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Either not able to click on link " + mnuLink;
			// throw new NoSuchElementException("No such element found");
		}

		return result;
	}

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to veriy page title
	 * 
	 * @Returns: Pass if match found for expected page title else fail
	 */
	public String getPageTitle(String data) {
		try {
			String pageTitle = driver.findElement(By.cssSelector("h1")).getText();
			if (pageTitle.contains(data) || pageTitle.equalsIgnoreCase(data)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL + "Page title does not match";
			}
		}

		catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Object not found";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose:Function to click button by className
	 */

	/*
	 * public String clickButtonByClassName(String object,String data){
	 * APP_LOGS.debug("Clicking on DeleteButton"); try{
	 * driver.findElement(By.className(OR.getProperty(object))).click(); sleep(8);
	 * result=Constants.KEYWORD_PASS;
	 * 
	 * } catch(Exception e) { result=Constants.KEYWORD_FAIL +
	 * " -- Not able to click on Button"+e.getMessage();; // throw new
	 * NoSuchElementException("No such element found");
	 * 
	 * }
	 * 
	 * 
	 * return result;
	 * 
	 * }
	 */

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to veriy text not present
	 * 
	 * @Returns: Pass if text not present else fail
	 */
	public String verifyTextNotPresent(String object, String data) {
		try {
			int totalSize = driver.findElements(By.xpath(OR.getProperty(object))).size();

			for (int index = 1; index <= totalSize; index++) {
				String actualText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
				if (!actualText.contains(data) || !actualText.equalsIgnoreCase(data)) {
					result = Constants.KEYWORD_PASS + "unexpected Text is not exist";
				} else {
					result = Constants.KEYWORD_FAIL + "On Para/List" + index + "unexpected text is present";
					break;
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Object" + OR.getProperty(object) + "is not found or no longer exist";
		}
		return result;

	}

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to verify element is present by contains
	 * 
	 * @Returns: Pass in case of element with contain is present else fail
	 */
	public String verifyElementPresentByContains(String object, String data) {
		APP_LOGS.debug("Verifying Element Present By Text Contains");
		try {
			sleep(3);
			if (driver.findElement(By.xpath(OR.getProperty(object) + "[contains(.,'" + data + "')]")).isDisplayed()
					|| driver.findElement(By.xpath(OR.getProperty(object) + "[contains(.,'" + data + "')]"))
							.isEnabled()) {
				return Constants.KEYWORD_PASS;
			} else {

				return Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to verify element is not present by contains
	 * 
	 * @Returns: Pass in case of element with contain is not present else fail
	 */
	public String verifyElementNotPresentByContains(String object, String data) {
		APP_LOGS.debug("Verifying Element Present By Text Contains");
		try {
			sleep(3);
			if (driver.findElement(By.xpath(OR.getProperty(object) + "[contains(.,'" + data + "')]")).isDisplayed()
					|| driver.findElement(By.xpath(OR.getProperty(object) + "[contains(.,'" + data + "')]"))
							.isEnabled()) {
				return Constants.KEYWORD_FAIL;
			} else {

				return Constants.KEYWORD_PASS;
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: The "object" is searched by ID Function to match the data , if data
	 * is unequal then the function returns PASS
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyTextNotContainById(String object, String data) {
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			String actual = driver.findElement(By.id(OR.getProperty(object))).getText().trim();
			String expected = data;

			if (!actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String getValueByXpath(String object, String data) {
		APP_LOGS.debug("Gets value from text box");
		try {
			String actualtext = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			if (actualtext.equalsIgnoreCase(data)) {
				System.out.print("Current value" + actualtext);
				currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, actualtext);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "- Not able to get text";

		}

		return result;

	}

	public String getNoteDate(String object, String data) {
		APP_LOGS.debug("Gets value from text box");
		try {
			String actualtext = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]//tr[1]//td[1]"))
					.getText();
			// if (actualtext.equalsIgnoreCase(data)) {
			System.out.print("Current value" + actualtext);
			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, actualtext);
			result = Constants.KEYWORD_PASS;
			String actualtext1 = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]//tr[37]//td[1]"))
					.getText();
			System.out.print("Current value" + actualtext1);
			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, actualtext1);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "- Not able to get text";

		}

		return result;

	}

	public String switchToFrameusingID(String object, String data) {

		// String status = "";

		try {
			Set<String> windowids = driver.getWindowHandles();

			Iterator<String> iter = windowids.iterator();
			while (iter.hasNext()) {
				System.out.println("next iterator is" + iter.next());

			}
			List<WebElement> objFrameContainer = driver.findElements(By.id(OR.getProperty(object)));

			if (objFrameContainer.size() > 0) {
				System.out.println("IFRAME FOUND");

				driver.switchTo().frame(objFrameContainer.get(0));
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Frame not fount";
		}

		// System.out.println("STATUS:" + status);
		return result;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose:Function to fill the data for broker status search
	 */

	public String pressTabinInputByID(String object, String data) {
		APP_LOGS.debug("Pressing Tab in text box");

		// String newdata = String.valueOf(data);
		try {

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;

		}
		return result;
	}

	public String getWindowHandles(String object, String data) {
		object = "";

		try {
			windowHandle = driver.getWindowHandle();
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;

		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: clear sata of filds by id
	 */
	public String clearTextByID(String object, String data) {
		APP_LOGS.debug("Clearing the text from input field");
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public String verifyErrorMsgById(String object, String data) throws IOException {

		try {

			sleep(3);
			APP_LOGS.debug("verify error message");
			FileInputStream fs1 = new FileInputStream(
					System.getProperty("user.dir") + "//src//com//sample//config//message.properties");
			MSG = new Properties();
			MSG.load(fs1);

			for (int index = 0; index <= MSG.size(); index++) {
				String expectedMsg = MSG.getProperty("msg" + index);
				APP_LOGS.debug("Expected Error Message :" + expectedMsg);
				String actgualMsg = driver.findElement(By.id(OR.getProperty(object))).getText();
				APP_LOGS.debug("Actual Error Message :" + actgualMsg);
				if (actgualMsg.equals(expectedMsg)) {
					result = Constants.KEYWORD_PASS;
					break;

				} else {
					result = Constants.KEYWORD_FAIL;
				}
			}
			fs1.close();
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Error -->" + e.getMessage();
		}
		return result;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify object is not present by css
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String assertIfObjectNotFoundByCSS(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(5l, TimeUnit.SECONDS);
			List<WebElement> objElements = driver.findElements(By.cssSelector(OR.getProperty(object)));
			if (objElements.size() > 0) {
				result = Constants.KEYWORD_FAIL;
			} else {
				System.out.println(Constants.KEYWORD_PASS + " - Object not found");
				result = Constants.KEYWORD_PASS + " - Object not found";
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify sorted data
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String verifyAllListElementsByID(String object, String data) {
		APP_LOGS.debug("Verifying the selection of the list");
		try {
			sleep(1);
			WebElement droplist = driver.findElement(By.id(OR.getProperty(object)));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));

			// extract the expected values from OR. properties
			String temp = data;
			String allElements[] = temp.split(",");
			// check if size of array == size if list
			if (allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL + "- size of lists do not match";

			for (int i = 0; i < droplist_cotents.size(); i++) {
				if (!((allElements[i]).trim()).equals((droplist_cotents.get(i).getText()).trim())) {
					return Constants.KEYWORD_FAIL + "- Element not found - " + allElements[i];
				}
			}
		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL;

		}

		return Constants.KEYWORD_PASS;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: To verify column Name of grid(Table) present on any page
	 * 
	 * @object:Should be blank
	 * 
	 * @data:Column name to verify
	 * 
	 * @Return: Pass if column is present else Fail
	 */
	public String verifyTableColumnName(String object, String data) {
		APP_LOGS.debug("Verify columns are present in table");
		try {
			int totalColumns = driver.findElements(By.xpath(OR.getProperty("common.table.col.hdr"))).size();

			for (int col = 1; col <= totalColumns; col++) {
				String actualColName = driver
						.findElement(By.xpath(OR.getProperty("common.table.col.hdr") + "[" + col + "]")).getText();

				if (actualColName.trim().equalsIgnoreCase(data.trim())) {
					result = Constants.KEYWORD_PASS;
					break;
				} else {
					result = Constants.KEYWORD_FAIL;
				}
			}

			return result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to clear";
		}
	}

	public String getUrlandNavigate(String object, String data) {
		APP_LOGS.debug("get current URL");
		System.out.println("getting url");

		try {
			String url = driver.getCurrentUrl();
			doLogout("commomn.logout.link", data);
			sleep(3);

			driver.navigate().to(url);
			sleep(3);
			System.out.println("the current url is" + url);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " -- Not able to navigate";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify error messge on base of different valid/invalid
	 * input
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyErrorOnDataInput(String object, String data) {
		try {
			currentTestSuiteXLS = new Xls_Reader(DriverScript.refexcelfilename);
			String errorflag = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, "errorFlag",
					DriverScript.datarowid);
			if (errorflag.equalsIgnoreCase("Y")) {
				result = assertIfObjectFound(object, data);

			} else {
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Error messge not found";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: To verify if the passed VALUE in data parameter is present on the
	 * radio button
	 * 
	 * @object:It should be each radio option
	 * 
	 * @data:Option VALUE to be verified
	 * 
	 * @Return: Pass if VALUE is present else Fail
	 */
	public String verifyRadioButtonValue(String object, String data) {
		APP_LOGS.debug("Selecting a radio button");
		try {

			String radiovalue = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected = data;
			if (radiovalue.equalsIgnoreCase(expected))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "- Radio Button Value does not match";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function verifies the header text by css
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyTextContentByID(String object, String data) {
		APP_LOGS.debug("Verifying the text content");
		try {
			sleep(3);
			String actual = driver.findElement(By.id(OR.getProperty(object))).getText();
			actual = actual.toUpperCase();
			String expected = data;
			expected = expected.toUpperCase();
			if (actual.trim().contains(expected.trim())) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL + " -- text content not verified " + actual + " -- " + expected;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify error messge on base of different valid/invalid
	 * input by ID
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyErrorOnDataInputByID(String object, String data) {
		try {
			currentTestSuiteXLS = new Xls_Reader(DriverScript.refexcelfilename);
			String errorflag = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, "errorFlag",
					DriverScript.datarowid);
			if (errorflag.equalsIgnoreCase("Y")) {
				result = assertIfObjectFoundByID(object, data);

			} else {
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Error messge not found";
		}
		return result;
	}

	// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify if a particular object is displayed/Hidden or
	 * not.
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String isHiddenByID(String object, String data) {
		APP_LOGS.debug("Object is displayed or not");
		try {
			boolean hide;
			hide = driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			if (hide == false)

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + "- Object is not hidden";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Object not found";

		}

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to close AHPX close box
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String closeAHPXSuccessbox(String object, String Data) {

		try {
			sleep(5);
			if (driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed()) {
				driver.findElement(By.cssSelector(OR.getProperty(object))).click();
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_PASS;
		}
		return result;
	}

	/*
	 * /*
	 * 
	 * @author=Timir
	 * 
	 * @Purpose: To connect with database
	 * 
	 * @object:Should be blank
	 * 
	 * @data:Should be blank
	 * 
	 * @Return: connection string with the database
	 */
	public Connection connectToDatabase(String object, String data) {
		APP_LOGS.debug("Establish connection to database");
		Connection conn = null;
		String Host = null;
		String Port = null;
		String SID = null;

		String environment = CONFIG.getProperty("URL_webApp");
		environment = environment.toUpperCase();
		environment = environment.substring(7, environment.lastIndexOf("."));
		System.out.println("environemnt");
		// String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		try {

			String userName = "", password = "";

			// For QANM
			if (environment.equalsIgnoreCase("synoverge")) {
				Host = CONFIG.getProperty("QA_DB_Host");
				Port = CONFIG.getProperty("QA_DB_Port");
				SID = CONFIG.getProperty("QA_DB_SID");
				userName = CONFIG.getProperty("QA_DB_UserName");
				password = CONFIG.getProperty("QA_DB_Password");

			}

			String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
			// Class.forName(driver).newInstance();// create object of Driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, userName, password);
			// connection will be established

			System.out.println("Connected to " + environment + " Database ");

		} catch (Exception e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Unable to close the Connection");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Unable to Connect to Database ");
			e.printStackTrace();
		}
		return conn;
	}

	public Connection DatabaseConnection(String object, String data) {
		APP_LOGS.debug("Establish connection to database");
		Connection conn = null;
		// String Host=null;
		// String Port=null;
		// String SID=null;

		// String environment=CONFIG.getProperty("URL_webApp");
		// environment=environment.toUpperCase();
		// environment=environment.substring(7,environment.lastIndexOf("."));
		// System.out.println("environemnt");
		// String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		try {

			String userName = "", password = "";

			// For QANM
			// if(environment.equalsIgnoreCase("synoverge"))
			// {
			// Host=CONFIG.getProperty("QA_DB_Host");
			// Port=CONFIG.getProperty("QA_DB_Port");
			// SID=CONFIG.getProperty("QA_DB_SID");
			userName = CONFIG.getProperty("QA_DB_UserName");
			password = CONFIG.getProperty("QA_DB_Password");

			// }
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
			// Class.forName(driver).newInstance();// create object of Driver
			// con = DriverManager.getConnection(url, "sa", "Synoverge@1");
			conn = DriverManager.getConnection(url, userName, password);
			// connection will be established

			// System.out.println("Connected to "+environment+" Database ");

		} catch (Exception e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Unable to close the Connection");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Unable to Connect to Database ");
			e.printStackTrace();
		}
		return conn;
	}

	public String verifyWithGlobalVariable(String object, String data) {
		APP_LOGS.debug("Verifying with the Global Variable");
		try {
			String actual = "";
			String blank = "";
			if (object.equals(blank) || (object == null)) {
				actual = data;
			} else {
				actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			}
			System.out.println("actual: " + actual);
			System.out.println("expected: " + globalValue);
			if (globalValue.equalsIgnoreCase(actual))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + globalValue + "- Value does not match " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Value does not match";

		}

	}

	// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to check if the data or the globalvalue are subset of
	 * eachother.
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String verifyContainsGlobalVariable(String object, String data) {
		APP_LOGS.debug("Verifying with the Global Variable");
		try {
			String actual = "";
			String blank = "";
			if (object.equals(blank) || (object == null)) {
				actual = data;
			} else {
				actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			}
			Scanner in = new Scanner(actual).useDelimiter("[^0-9]+");
			actual = String.valueOf(in.nextInt());
			System.out.println("actaal my is " + actual);
			if ((actual.contains(globalValue)) || (globalValue.contains(actual)))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + globalValue + "   - Value doesn't contain in actual -   " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Value does not match";

		}

	}
	// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to compare the data with the value in Global Variable
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String verifyRadioButtonSelected(String object, String data) {
		APP_LOGS.debug("Verifying radio button is clicked");

		try {

			driver.findElement(By.xpath(OR.getProperty(object))).click();

			boolean radresult = driver.findElement(By.xpath(OR.getProperty(object))).isSelected();

			if (radresult == true) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify radio button is not clicked
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String verifyRadioButtonNotSelected(String object, String data) {
		APP_LOGS.debug("Verifying radio button is clicked");

		try {

			driver.findElement(By.xpath(OR.getProperty(object))).click();

			boolean radresult = driver.findElement(By.xpath(OR.getProperty(object))).isSelected();

			if (!radresult) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify if a particular object is not Hidden.
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String isNotHiddenByID(String object, String data) {
		APP_LOGS.debug("Object is displayed");
		try {
			boolean visible;
			visible = driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			if (visible == true)
				return Constants.KEYWORD_PASS + "- Object is not displayed";
			else
				return Constants.KEYWORD_FAIL + "- Object is hidden";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Object not found";

		}

	}// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToDisplayByXpath(String object, String data) {
		int maxWait = waitforelement;

		try {
			if (object.equalsIgnoreCase("individual.plandisplay.planselection.addtocart.btn")) {
				maxWait = 60;
			}

			for (int i = 1; i <= maxWait; i++) {
				try {
					if (driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == maxWait) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String waitForElementNotVisibility(String object, String data) {
		APP_LOGS.debug("Waiting for an element to be visible");
		int start = 0;
		// int time=(int)Double.parseDouble(data);
		// int time=Integer.parseInt(data);
		int time = waitforelement;
		try {
			sleep(3);
			while (time != start) {
				if (driver.findElements(By.xpath(OR.getProperty(object))).size() != 0) {
					sleep(1);
					start++;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to find the object" + e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to create Individual basic information
	 * 
	 * @Return:Result=Pass/Fail
	 */

	public String verifyRadioButtonSelectedByID(String object, String data) {
		APP_LOGS.debug("Verifying radio button is clicked");

		try {

			driver.findElement(By.id(OR.getProperty(object))).click();

			boolean radresult = driver.findElement(By.id(OR.getProperty(object))).isSelected();

			if (radresult == true) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to read error message by id
	 */
	public String validateAllErrorMessageByid(String object, String data) throws IOException {

		try {

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(
					System.getProperty("user.dir") + "//src//com//sample//config//message.properties");
			msgCONFIG = new Properties();
			msgCONFIG.load(fs1);

			for (int index = 0; index <= msgCONFIG.size(); index++) {
				lstErrorMessages.add(msgCONFIG.getProperty("msg" + index));

			}
			List<WebElement> objErrors = driver.findElements(By.id(OR.getProperty(object)));

			if (objErrors.size() > 0) {
				// System.out.println(objErrors.size());
				for (int i = 0; i < objErrors.size(); i++) {
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println(objErrors.get(i).getText().toString());
				}
			}

			for (String errorMsg : lstActualError) {
				for (int i = 1; i <= lstErrorMessages.size(); i++) {
					if (lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg)) {
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						result = Constants.KEYWORD_PASS;
						break;
					} else {
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						result = Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();

		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
		}

		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToDisplayByCSS(String object, String data) {
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					if (driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by name to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToDisplayByName(String object, String data) {
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					if (driver.findElement(By.name(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by id to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToDisplayByID(String object, String data) {
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					if (driver.findElement(By.id(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify if table exist in database
	 * 
	 * @Object=Table name to be checked
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyTableExistInDatabase(String object, String data) {
		Connection conn;
		String tableName = OR.getProperty(object);
		try {
			conn = connectToDatabase("", "");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to connet to database";
		}

		try {
			Statement stmt = conn.createStatement();
			String Query = "select * from " + tableName + " where rownum='1'";
			System.out.println("Query: " + Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_FAIL + " Table does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	// Dhvani
	public String verifyTableContentInDatabase(String object, String data) throws FileNotFoundException {
		Connection conn;
		String tableName = CONFIG.getProperty("Table3_iForms_Country");

		try {
			conn = connectToDatabase("", "");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to connet to database";
		}

		try {

			PrintStream outStream = null;
			PrintStream errStream = null;
			PrintStream fileStream = null;
			outStream = System.out;
			errStream = System.err;
			System.out.println("hi");
			OutputStream os = new FileOutputStream("D:/Dhvani/automation/iFormFactor/src/com/sample/util/result.html",
					false); // only the file output stream
			os = new TeeOutputStream(outStream, os); // create a TeeOutputStream
														// that duplicates data
														// to outStream and os
			fileStream = new PrintStream(os);

			System.setErr(fileStream);
			System.setOut(fileStream);

			Statement stmt = conn.createStatement();
			String Query = "select * from " + tableName + " where rownum='1'";
			System.out.println("Query: " + Query);
			ResultSet rs = stmt.executeQuery(Query);

			int count = 0;
			while (rs.next()) {
				// if (result.next()) {
				String CountryName = rs.getString("CountryName");
				System.out.println("Country Name : " + CountryName);
				// fw.write(System.getProperty("line.separator"));
				// System.getProperty("line.separator");
				// System.out.println( "<br>");
				System.out.println("&nbsp;");
				String CountryId = rs.getString("CountryId");
				// System.out.println( "<br>");
				System.out.println("&nbsp;");
				System.out.println("CountryId : " + CountryId);
				System.out.println("<br>");
				count = count + 1;
			}

			// rs.next();

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_FAIL + " Table does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify table does NOT exist in database
	 * 
	 * @Object=Table name to be checked
	 * 
	 * @Return:Result=Pass if table not present else Fail
	 */
	public String verifyTableNotExistInDatabase(String object, String data) {
		Connection conn;
		String tableName = OR.getProperty(object);
		try {
			conn = connectToDatabase("", "");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to connet to database";
		}

		try {
			Statement stmt = conn.createStatement();
			String Query = "select * from " + tableName + " where rownum='1'";
			System.out.println("Query: " + Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result = Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_PASS + " Table does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify column exist in database
	 * 
	 * @Object=Table name
	 * 
	 * @Data=Column Name
	 * 
	 * @Return:Result=Pass if table not present else Fail
	 */
	public String verifyColumnExistInDatabase(String object, String data) throws FileNotFoundException {

		PrintStream outStream = null;
		PrintStream errStream = null;
		PrintStream fileStream = null;
		outStream = System.out;
		errStream = System.err;
		System.out.println("hi");
		OutputStream os = new FileOutputStream("D:/Dhvani/automation/iFormFactor/src/result.html", false); // only
																											// the
																											// file
																											// output
																											// stream
		os = new TeeOutputStream(outStream, os); // create a TeeOutputStream
													// that duplicates data to
													// outStream and os
		fileStream = new PrintStream(os);

		System.setErr(fileStream);
		System.setOut(fileStream);

		Connection conn;
		String tableName = OR.getProperty(object);
		String columnName = data;
		try {
			conn = connectToDatabase("", "");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to connet to database";
		}

		try {
			Statement stmt = conn.createStatement();
			String Query = "select " + columnName + " from " + tableName + " where rownum='1'";
			System.out.println("Query: " + Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_FAIL + " Table or view does not Exist";
			} else if (e.getMessage().contains("ORA-00904")) {
				result = Constants.KEYWORD_FAIL + " Column does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify column exist in database
	 * 
	 * @Object=Table name
	 * 
	 * @Data=Column Name
	 * 
	 * @Return:Result=Pass if table not present else Fail
	 */
	public String verifyColumnNotExistInDatabase(String object, String data) {
		Connection conn;
		String tableName = OR.getProperty(object);
		String columnName = data;
		try {
			conn = connectToDatabase("", "");
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to connet to database";
		}

		try {
			Statement stmt = conn.createStatement();
			String Query = "select " + columnName + " from " + tableName + " where rownum='1'";
			System.out.println("Query: " + Query);
			ResultSet rs = stmt.executeQuery(Query);
			rs.next();

			result = Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_FAIL + " Table or view does not Exist";
			} else if (e.getMessage().contains("ORA-00904")) {
				result = Constants.KEYWORD_PASS + " Column does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify that file is downloaded
	 * 
	 * @Return:Result=Pass/Fail
	 * 
	 * @Object: Element whose href attribute contains file download link
	 */
	public String verifyDownloadFile(String object, String data) {
		try {

			String downloadLink = driver.findElement(By.linkText(OR.getProperty(object))).getAttribute("href");
			Boolean dwFileresult = DownloadManager.downloadFile(downloadLink);

			if (dwFileresult) {
				result = Constants.KEYWORD_PASS;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify that file is downloaded
	 * 
	 * @Return:Result=Pass/Fail
	 * 
	 * @Object: Element whose href attribute contains file download link
	 */
	public String verifyDownloadFileByXpath(String object, String data) {
		try {

			String downloadLink = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("href");
			Boolean dwFileresult = DownloadManager.downloadFile(downloadLink);

			if (dwFileresult) {
				result = Constants.KEYWORD_PASS;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}
	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify that Monthly cost of the employer
	 * * @Return:Result=Pass/Fail
	 */

	public ResultSet executeQuerryDB(String querry) {
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = connectToDatabase("", "");
			Statement stmt = conn.createStatement();
			String Query = querry;
			System.out.println("Query: " + Query);
			rs = stmt.executeQuery(Query);

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			if (e.getMessage().contains("table or view does not exist")) {
				result = Constants.KEYWORD_PASS + " Table does not Exist";
			} else {
				System.out.println("Unable to execute Query");
				result = Constants.KEYWORD_FAIL + "Wrong Query";
			}
		}

		return rs;
	}

	/*
	 * @author= Timir
	 * 
	 * @Purpose:Function to Verify pop up message , if verified click on OK ie.
	 * accept
	 */
	public String popupClickOkVerifyMessage(String object, String data) {

		String strResult = Constants.KEYWORD_FAIL;

		try {
			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE") == false) {
				Alert objPopup = driver.switchTo().alert();
				if (objPopup != null) {
					String msgstr = objPopup.getText().trim();
					System.out.println("POPUP MESSAGE:" + msgstr);
					String actstr = data.trim();
					// Perform Event - Click OK
					if (msgstr.contains(actstr)) {
						objPopup.accept();
						strResult = Constants.KEYWORD_PASS;
					} else {
						result = Constants.KEYWORD_FAIL + " -- text content not verified " + actstr + " -- " + msgstr;
					}

					return strResult;
				}
			} else {
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				Robot robot = new Robot();

				robot.keyPress(KeyEvent.VK_ENTER);
				strResult = "PASS";
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return strResult;
		}

		return strResult;

	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforElementToDisplayBylinkText(String object, String data) {
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					if (driver.findElement(By.linkText(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String verifySortingOrder(String object, String data) {
		APP_LOGS.debug("Verifying the sort function");

		try {

			String sorting = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, "sorting",
					DriverScript.datarowid);
			driver.findElement(By.linkText("Sort By")).click();
			data = "";
			waitforElementToDisplayBylinkText(sorting, data);

			if (sorting.equalsIgnoreCase("Smart Sort")) {
				driver.findElement(By.linkText(sorting)).click();

			}

			else if (sorting.equalsIgnoreCase(" Estimated Total Costs (Low to High)")) {

			} else if (sorting.equalsIgnoreCase("Monthly Premium (Low to High)")) {
				driver.findElement(By.linkText(sorting)).click();

				int divIndex = driver.findElements(By.xpath("//div[@id='mainSummary']//div")).size();

				int[] arraylist = new int[divIndex];
				for (int index = 1; index <= divIndex; index++) {
					// int planIndex=index+1;
					String priceValue = driver.findElement(By.xpath("//div[@id='mainSummary']//div[" + index + "]//h3"))
							.getText();
					int charindex = priceValue.indexOf("/");
					String priceAfterTrim = priceValue.substring(21, charindex);
					// Timir.sn, if the Premium is in decimal, an exception
					// occurs so to handle it we remove the decimal
					if (priceAfterTrim.contains(".00")) {
						priceAfterTrim = priceAfterTrim.replace(".00", "");
					}
					// Timir.en
					arraylist[index] = Integer.parseInt(priceAfterTrim);
					arraylist[index] = Integer.parseInt(priceAfterTrim);

				}
				// to sort array items in to ascending order
				Arrays.sort(arraylist);
				int arrayinitIindex = 0;

				for (int index = 1; index <= divIndex; index++) {

					String priceValue = driver.findElement(By.xpath("//div[@id='mainSummary']//div[" + index + "]//h3"))
							.getText();
					if (priceValue.contains(String.valueOf(arraylist[arrayinitIindex]))) {
						result = Constants.KEYWORD_PASS;
					} else {
						result = Constants.KEYWORD_FAIL + "sorting fail";
						break;
					}
					arrayinitIindex++;

				}
			}

			else if (sorting.equalsIgnoreCase("Monthly Premium (High to Low)")) {
				driver.findElement(By.linkText("Monthly Premium (Low to High)")).click();
				driver.findElement(By.linkText("Monthly Premium (Low to High)")).click();

				int divIndex = driver.findElements(By.xpath("//div[@id='mainSummary']//div")).size();

				int[] arraylist = new int[divIndex];
				for (int index = 1; index <= divIndex; index++) {
					// int planIndex=index+1;
					String priceValue = driver.findElement(By.xpath("//div[@id='mainSummary']//div[" + index + "]//h3"))
							.getText();
					int charindex = priceValue.indexOf("/");
					String priceAfterTrim = priceValue.substring(21, charindex);
					// Timir.sn, if the Premium is in decimal, an exception
					// occurs so to handle it we remove the decimal
					if (priceAfterTrim.contains(".00")) {
						priceAfterTrim = priceAfterTrim.replace(".00", "");
					}
					// Timir.en
					arraylist[index] = Integer.parseInt(priceAfterTrim);
					arraylist[index] = Integer.parseInt(priceAfterTrim);

				}
				// to sort array items in to ascending order
				Arrays.sort(arraylist);
				int arrayinitIindex = arraylist.length;

				for (int index = 1; index <= divIndex; index++) {

					String priceValue = driver.findElement(By.xpath("//div[@id='mainSummary']//div[" + index + "]//h3"))
							.getText();
					if (priceValue.contains(String.valueOf(arraylist[arrayinitIindex]))) {
						result = Constants.KEYWORD_PASS;
					} else {
						result = Constants.KEYWORD_FAIL + "sorting fail";
						break;
					}
					arrayinitIindex--;

				}
			}

			else if (sorting.equalsIgnoreCase("Overall Quality")) {

			} else if (sorting.equalsIgnoreCase("planswithmydoctor")) {

			}

		}

		catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String validateAllErrorMessageByCSS(String object, String data) throws IOException {

		try {

			ArrayList<String> lstActualError = new ArrayList<String>();
			ArrayList<String> lstErrorMessages = new ArrayList<String>();
			FileInputStream fs1 = new FileInputStream(
					System.getProperty("user.dir") + "//src//com//sample//config//message.properties");
			msgCONFIG = new Properties();
			msgCONFIG.load(fs1);

			for (int index = 0; index <= msgCONFIG.size(); index++) {
				lstErrorMessages.add(msgCONFIG.getProperty("msg" + index));

			}
			List<WebElement> objErrors = driver.findElements(By.cssSelector(OR.getProperty(object)));

			if (objErrors.size() > 0) {
				// System.out.println(objErrors.size());
				for (int i = 0; i < objErrors.size(); i++) {
					lstActualError.add(objErrors.get(i).getText().toString());
					System.out.println(objErrors.get(i).getText().toString());
				}
			}

			for (String errorMsg : lstActualError) {
				for (int i = 1; i <= lstErrorMessages.size(); i++) {
					if (lstErrorMessages.get(i).toString().equalsIgnoreCase(errorMsg)) {
						APP_LOGS.debug("Field validation message found :" + errorMsg);
						result = Constants.KEYWORD_PASS;
						break;
					} else {
						APP_LOGS.debug("Field validation message not found :" + errorMsg);
						result = Constants.KEYWORD_FAIL + " Error validation not matched";
					}
				}
			}

			fs1.close();

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}

		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose:Function to verify confirmation pop up message and click on cancel
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String verifyAlertMessageAndClickCancel(String object, String data) {
		try {
			Alert objPopup = driver.switchTo().alert();
			if (objPopup != null) {
				System.out.println("POPUP MESSAGE:" + objPopup.getText());

				if (objPopup.getText().contains(data)) {
					// Perform Event - Click OK
					objPopup.dismiss();
				}
				result = Constants.KEYWORD_PASS;
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifyRoutingNumberInDb(String object, String data) {

		ResultSet rs;

		Connection conn = null;

		try {
			conn = connectToDatabase("", "");
			Statement stmt = conn.createStatement();
			String Query = "";
			String routingnumber = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, "ABARoutingNo",
					DriverScript.datarowid);
			String accountname = currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, "AccountName",
					DriverScript.datarowid);
			Query = "select  ROUTING_NUMBER from BANK_INFO where NAME_ON_ACCOUNT='" + accountname + "'";
			System.out.println("Query: " + Query);
			rs = stmt.executeQuery(Query);
			rs.next();
			String Routing_number = rs.getString("ROUTING_NUMBER");

			if (Integer.parseInt(routingnumber) == Integer.parseInt(Routing_number)) {
				result = Constants.KEYWORD_PASS;

			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "Routing number coloum does not exist";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Verify with current date
	 * 
	 * @object:The UI date object
	 * 
	 * @Return:Fail/Pass
	 */
	public String verifyCurrentDate(String object, String data) {
		String todaydate;
		APP_LOGS.debug("Verify with current date");
		try {
			todaydate = getCurrentDateMMDDYYYY(object, data);
			globalValue = todaydate;

			// Verify with the global variable which has d/b count
			String verified = verifyContainsGlobalVariable(object, "");
			if (verified == Constants.KEYWORD_PASS) {
				return result = Constants.KEYWORD_PASS;
			} else {
				return result = Constants.KEYWORD_FAIL + " Current date is  " + globalValue
						+ " which is differnt that the UI";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Open html file in browser
	 * 
	 * @Data: file path
	 */
	public String openHtmlFileAndNavigate(String object, String data) {
		try {
			APP_LOGS.debug("Open html file and navigate");
			String htmlFilePath = System.getProperty("user.dir") + data;
			File htmlFile = new File(htmlFilePath);
			driver.navigate().to(htmlFile.getAbsolutePath());

			APP_LOGS.debug("Open file and navigating file" + htmlFilePath);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
		}

		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to Compare start date and current date
	 * 
	 * @Return:Current Date MM/DD/YYYY format
	 */
	public String verifyCurrentDateWithStartDate(String object, String data) {
		String date = "";
		try {
			date = getCurrentDateMMDDYYYY(object, data);
			if (date.contains("/")) {
				date = date.replace("/", "-");
			}
			System.out.println("after replace date is" + date);
			globalValue = date;
			System.out.println("global date is" + globalValue);
			String actual = driver
					.findElement(By.xpath(OR.getProperty("admin.agent.certificationstatus.startdate.byxpath.txt")))
					.getAttribute("value");
			if (globalValue.equalsIgnoreCase(actual)) {
				return Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {

			result = Constants.KEYWORD_FAIL + "both are not same";

		}

		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify if a particular object is not Hidden.
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String isNotHidden(String object, String data) {
		APP_LOGS.debug("Object is displayed");
		try {
			boolean visible = false;

			visible = driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
			if (visible == true) {
				result = Constants.KEYWORD_PASS + "- Object is not displayed";
			} else {
				result = Constants.KEYWORD_FAIL + "- Object is hidden";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Object not found";

		}
		return result;

	}// End of Keyword

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to verify date format of effective date under health plans
	 * 
	 * @Return:Result=return status if pass else Fail
	 */
	public String verifyEffectiveDateFormat(String object, String data) {

		try {
			data = "";
			APP_LOGS.debug("gets date value from effective date label");
			String actualValue = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String dt = actualValue;
			APP_LOGS.debug("verify . sign contains");
			if (actualValue.contains("/")) {
				APP_LOGS.debug("Splits with / sign");
				String dateParts[] = dt.split("/");
				int month = Integer.parseInt(dateParts[0]);
				int day = Integer.parseInt(dateParts[1]);
				String year = dateParts[2];
				System.out.println(month);
				System.out.println(day);
				System.out.println(year);
				APP_LOGS.debug("verifies month date and year value");
				if (month <= 12 && day <= 31 && year.length() == 4) {
					result = Constants.KEYWORD_PASS;
				} else {
					result = Constants.KEYWORD_FAIL + "not in mm/dd/yyyy format";
				}

			} else {
				result = Constants.KEYWORD_FAIL + "date format does not contain / sign";
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "fail to get date value from object";
		}
		return result;
	}

	/*
	 * author=Timir
	 * 
	 * @ Purpose: Function to Read employer enrollment status and verify the status
	 * for given ID
	 * 
	 * @ Precondition : getEmployerEnrollmentID is to be executed and Excel
	 * Datasheet contains the ID
	 * 
	 * @ Data: The keyword passes the column name of the status against which the
	 * status is to be checked.
	 * 
	 * @Return:Result=return status if pass else Fail
	 */

	public String verifyContainsGlobalVariableByID(String object, String data) {
		APP_LOGS.debug("Verifying with the Global Variable");
		try {
			String actual = "";
			String blank = "";
			if (object.equals(blank) || (object == null)) {
				actual = data;
			} else {
				actual = driver.findElement(By.id(OR.getProperty(object))).getText();
			}
			Scanner in = new Scanner(actual).useDelimiter("[^0-9]+");
			actual = String.valueOf(in.nextInt());
			System.out.println("actual  is " + actual);
			if ((actual.contains(globalValue)) || (globalValue.contains(actual)))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + globalValue + "   - Value doesn't contain in actual -   " + actual;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Value does not match";

		}

	}

	public String switchToFrameByXpath(String object, String data) {

		// String status = "";
		try {
			sleep(3);
			WebElement e = driver.findElement(By.xpath(OR.getProperty(object)));
			driver.switchTo().frame(e);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL + "Frame not fount";
		}

		// System.out.println("STATUS:" + status);
		return result;

	}

	public String waitforElementToDisplayByCss(String object, String data) {
		int maxWait = waitforelement;

		try {
			if (object.equalsIgnoreCase("individual.plandisplay.planselection.addtocart.btn")) {
				maxWait = 60;
			}

			for (int i = 1; i <= maxWait; i++) {
				try {
					if (driver.findElement(By.cssSelector(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == maxWait) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String clickByCss(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {
			WebElement objElement = driver.findElement(By.cssSelector(data));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("var evt = document.createEvent('MouseEvents');"
					+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
					+ "arguments[0].dispatchEvent(evt);", objElement);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	/*
	 * @author=Timir
	 * 
	 * @Purpose: Function to wait for the object by xpath to load and visible
	 * 
	 * @Return:Result=Pass/Fail
	 */
	public String waitforDocumentToUploadByXpath(String object, String data) {
		int maxWait = waitfordocUpload;

		try {

			for (int i = 1; i <= maxWait; i++) {
				try {
					if (driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == maxWait) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String captureScreenshot(String filename, String keyword_execution_result) throws IOException {
		// take screen shots

		String strPath = null;

		try {
			if (screenshotfoldercreate) {
				String folder = "";
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd-MMM_HH.mm");
				folder = CONFIG.getProperty("environment") + "_" + df.format(cal.getTime());
				filePath = System.getProperty("user.dir") + "//screenshots//" + folder + "//";

				File f = new File(filePath);
				if (f.exists() == false) {
					f.mkdirs();
				}
				screenshotfoldercreate = false;
			}

		} catch (Exception e) {
			screenshotfoldercreate = false;
			filePath = System.getProperty("user.dir") + "//screenshots//";
		}

		try {

			if (CONFIG.getProperty("screenshot_everystep").equals("Y")) {
				// capturescreen
				// File DestFile = new File(System.getProperty("user.dir")
				// +"//screenshots//"+filename+".jpg");
				File DestFile = new File(filePath + filename + ".jpg");
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, DestFile);

				strPath = DestFile.getAbsolutePath();
				System.out.println(strPath);

			} else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL)
					&& CONFIG.getProperty("screenshot_error").equals("Y")) {
				// capture screenshot
				// File DestFile = new File(System.getProperty("user.dir")
				// +"//screenshots//"+filename+".jpg");
				File DestFile = new File(filePath + filename + ".jpg");
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, DestFile);

				strPath = DestFile.getAbsolutePath();
				System.out.println("IN ERROR CONDITION:" + strPath);
			}
		} catch (Exception e) {
		}

		return strPath;
	}

	public String waitforElementNotToDisplayByName(String object, String data) {
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					if (driver.findElement(By.name(OR.getProperty(object))).isDisplayed() == false) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String generic_clearText(String object, String data) {
		By by;
		APP_LOGS.debug("Clearing the text from input field");
		try {
			by = object_type_identifier(object);
			driver.findElement(by).clear();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Not able to clear";
		}
		return Constants.KEYWORD_PASS;
	}

	public String generic_click(String object, String data) {
		By by = null;
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				by = object_type_identifier(object);
				driver.findElement(by);
				try {

					driver.findElement(by).click();

				} catch (Exception e) {
					driver.findElement(by).sendKeys("\n");
				}
				sleep(4);

				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(by);
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String generic_exist(String object, String data) {
		By by = null;
		APP_LOGS.debug("Checking existance of element");
		try {
			by = object_type_identifier(object);
			System.out.println("Value of by as : " + by);
			driver.findElement(by);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}

	public String generic_selectList(String object, String data) {
		By by;
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			by = object_type_identifier(object);
			WebElement dropDownListBox = driver.findElement(by);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String generic_uploadDoc(String object, String data) {
		String strPath = "";
		By by;
		APP_LOGS.debug("uploading Document...");
		try {
			strPath = System.getProperty("user.dir") + data;
			System.out.println("path:" + strPath);

			by = object_type_identifier(object);
			driver.findElement(by).sendKeys(strPath);
			sleep(3);

		} catch (Exception e) {
			System.out.println(" - Getting error while document uploading" + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Getting error while document uploading";
		}

		return Constants.KEYWORD_PASS;
	}

	public String generic_verifyText(String object, String data) {
		By by = null;
		APP_LOGS.debug("Verifying the text");
		try {
			sleep(3);
			by = object_type_identifier(object);
			String actual = driver.findElement(by).getText().trim();
			String expected = data;

			if (actual.equalsIgnoreCase(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String generic_verifyTextinInput(String object, String data) {
		By by = null;
		APP_LOGS.debug("Verifying the text in input box");
		try {
			by = object_type_identifier(object);
			String actual = driver.findElement(by).getAttribute("value");
			String expected = data;

			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	public String generic_waitforElementToDisplay(String object, String data) {
		By by;
		try {
			for (int i = 1; i <= waitforelement; i++) {
				try {
					by = object_type_identifier(object);
					if (driver.findElement(by).isDisplayed() == true) {
						result = Constants.KEYWORD_PASS;
						sleep(3);
						break;
					} else {
						sleep(1);
					}

					if (i == waitforelement) {
						result = Constants.KEYWORD_FAIL;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + "in catch";

		}
		return result;
	}

	public String generic_waitforElementToLoad(String object, String data) {
		By by;
		data = CONFIG.getProperty("implicitwait");
		try {
			by = object_type_identifier(object);
			sleep(3);
			WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

			waiting.until(ExpectedConditions.presenceOfElementLocated(by));
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String generic_WriteInInputbox(String object, String data) {
		By by = null;
		try {
			by = object_type_identifier(object);
			System.out.println("Value of by as : " + by);

			driver.findElement(by).sendKeys(data);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
			System.out.println("Not able to click");
			e.printStackTrace();
		}

		return result;
	}

	public By object_type_identifier(String OR_props_key_value) {
		By by = null;
		try {

			String[] xpath_split;
			String object_xpath_val;

			if (OR_props_key_value.startsWith("//")) {
				System.out.println("props_key_value : " + OR_props_key_value);
				by = By.xpath(OR_props_key_value);
			} else if (OR_props_key_value.contains("linktext=")) {
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("linktext_val : " + object_xpath_val);
				by = By.linkText(object_xpath_val);

			} else if (OR_props_key_value.contains("id=")) {
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("id : " + object_xpath_val);
				by = By.id(object_xpath_val);

			} else if (OR_props_key_value.contains("css=")) {
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("css : " + object_xpath_val);
				by = By.cssSelector(object_xpath_val);
			} else if (OR_props_key_value.contains("name=")) {
				xpath_split = OR_props_key_value.split("=");
				object_xpath_val = xpath_split[1];
				System.out.println("name : " + object_xpath_val);
				by = By.name(object_xpath_val);
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
			e.printStackTrace();
		}

		return by;
	}

	public String getAndSetCropname(String object, String data) {

		try {
			String actualCropName = driver.findElement(By.xpath(OR.getProperty("DefaultCropName"))).getText();

			String expectedCropName = actualCropName;
			// System.out.println("value");

			System.out.println("Crop Name is " + expectedCropName);

			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expectedCropName);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e);
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getAndSetdefaultYield(String object, String data) {

		try {
			String actualdefaultYield = driver.findElement(By.xpath(OR.getProperty("DefaultYield"))).getText();

			String expecteddefaultYield = actualdefaultYield;

			// System.out.println("value");

			System.out.println("Default Yield is " + expecteddefaultYield);

			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expecteddefaultYield);

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e);
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getAndSetcostofproduction(String object, String data) {

		try {

			String actualcostofproduction = driver.findElement(By.xpath(OR.getProperty("DefaultCostofProduction")))
					.getText();

			String expectedcostofproduction = actualcostofproduction;
			// System.out.println("value");

			System.out.println("Cost of Production is " + expectedcostofproduction);

			currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, expectedcostofproduction);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e);
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String getAndElementenabled(String object, String data) {

		try {
			String actualattribute = driver.findElement(By.xpath(OR.getProperty("checkbox"))).getAttribute("disabled");

			System.out.println("Checkbox attribute is " + actualattribute);
			// driver.findElement(By.xpath(OR.getProperty("Inward.bar.alert.ok.btn"))).click();

			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			boolean enable = driver.findElement(By.xpath("//*[@id='td_SI_NO_0']/a/input")).isEnabled();
			System.out.print("\nAfter : chekbox status is : " + enable);

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			System.out.println(e);
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String BirthdatePicker(String object, String data) {
		try {
			APP_LOGS.debug("Waiting for date selection");

			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1]",
					driver.findElement(By.xpath(OR.getProperty("AccAdmin.DOB.txt"))), "07/21/2015");

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String CommencementDate(String object, String data) {
		try {
			APP_LOGS.debug("Waiting for date selection");

			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1]",
					driver.findElement(By.xpath(OR.getProperty("Commencement.date.select"))), "11-06-2014");

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	// Exe for apply login id and password in to authentication window
	/*
	 * public String RunAuthenticateWindowExe(String object,String data) { try{
	 * 
	 * Process proc=Runtime.getRuntime().exec(("user.dir")+
	 * "//AutoitScript//ING_AuthenticationWindow.exe"); Thread.sleep(5000);
	 * 
	 * } catch(Exception e){ e.printStackTrace(); return Constants.KEYWORD_FAIL; }
	 * return Constants.KEYWORD_PASS;
	 * 
	 * }
	 */

	public String RunAuthenticateWindowFFExe(String object, String data) {
		try {

			Process proc = Runtime.getRuntime()
					.exec((System.getProperty("user.dir")) + "//AutoitScript//ING_AuthenticationWindowFF.exe");
			Thread.sleep(5000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	public String RunAuthenticateWindowIEExe(String object, String data) {
		try {

			Process proc = Runtime.getRuntime()
					.exec((System.getProperty("user.dir")) + "//AutoitScript//ING_AuthenticationWindowIE.exe");
			Thread.sleep(5000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			e.printStackTrace();
			result = Constants.KEYWORD_FAIL;
		}
		return result;

	}

	// Exe for upload TIF file

	public String RunUploadTIFDocumentExe(String object, String data) {
		try {

			// driver = new FirefoxDriver();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse TIF File-.exe");

			// driver.get("http://svt-srv-39:1000/");

			Thread.sleep(5000);

			// driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	// Exe for upload execel document

	public String RunUploadExcelDocumentExe(String object, String data) {
		try {

			// driver = new FirefoxDriver();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse XLS File-.exe");

			// driver.get("http://svt-srv-39:1000/");

			Thread.sleep(5000);

			// driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String RunUploadCSVDocumentExe(String object, String data) {
		try {

			// driver = new FirefoxDriver();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING Browse CSV File-.exe");

			// driver.get("http://svt-srv-39:1000/");

			Thread.sleep(5000);

			// driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	// Exe for save download file
	public String RunviewDownloadedFileExe(String object, String data) {
		try {

			// driver = new FirefoxDriver();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\save file.exe");

			// driver.get("http://svt-srv-39:1000/");

			Thread.sleep(5000);

			// driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	// EXE for open windows explore
	public String RunOpenDownloadedFileExe(String object, String data) {
		try {

			// driver = new FirefoxDriver();
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Runtime.getRuntime().exec("D:\\Automation\\ING CLMS\\src\\com\\sample\\util\\ING OpenDownloadFolder.exe");

			// driver.get("http://svt-srv-39:1000/");

			Thread.sleep(5000);

			// driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
	}

	public String draganddrop(String object, String data) throws InterruptedException {

		// public static void main(String[] args)
		{

			Actions act = new Actions(driver);

			// WebElement
			// dragElement=driver.findElement(By.xpath("//div[@id='dvCustomPallet']/div"));
			// WebElement dragElement=driver.findElement(By.id("textbox"));
			WebElement dragElement1 = driver.findElement(By.cssSelector("#textbox"));
			dragElement1.click();

			WebElement dragElement2 = driver.findElement(By.cssSelector("#radiobuttonList"));
			dragElement2.click();

			WebElement dragElement3 = driver.findElement(By.cssSelector("#textarea"));
			dragElement3.click();

			WebElement dragElement4 = driver.findElement(By.cssSelector("#email"));
			dragElement4.click();
			// WebElement
			// dragElement2=driver.findElement(By.xpath("//div[@id='textbox']"));

			// WebElement
			// dropElement=driver.findElement(By.xpath(".//*[@id='drop-form-1']"));
			WebElement dropElement = driver.findElement(By.cssSelector("#drop-form-1"));

			Actions builder = new Actions(driver);
			// Action dragAndDrop =
			// builder.clickAndHold(dragElement).moveToElement(dropElement).release(dropElement).build();
			Action dragAndDrop1 = builder.clickAndHold(dragElement1).moveToElement(dropElement).release(dropElement)
					.build();
			dragAndDrop1.perform();
			Thread.sleep(1000);

			Action dragAndDrop2 = builder.clickAndHold(dragElement2).moveToElement(dropElement).release(dropElement)
					.build();
			dragAndDrop2.perform();
			Thread.sleep(1000);
			Action dragAndDrop3 = builder.clickAndHold(dragElement3).moveToElement(dropElement).release(dropElement)
					.build();
			dragAndDrop3.perform();
			Thread.sleep(1000);
			Action dragAndDrop4 = builder.clickAndHold(dragElement4).moveToElement(dropElement).release(dropElement)
					.build();
			dragAndDrop4.perform();
			Thread.sleep(1000);
			// Action dragAndDrop3 =
			// builder.clickAndHold(dragElement3).moveToElement(dropElement).release(dropElement).build();

			// dragAndDrop.perform();
			// dragAndDrop1.perform();
			// Thread.sleep(1000);
			// dragAndDrop2.perform();
			// Thread.sleep(1000);
			// dragAndDrop3.perform();
			// Thread.sleep(1000);
			// dragAndDrop4.perform();

		}
		return data;
	}

	public String autoSuggest(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data,
			// Keys.ENTER);

			driver.findElement(By.id("cropTypeAuto")).sendKeys("Corn");
			Thread.sleep(1000);
			driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.DOWN);
			Thread.sleep(1500);
			driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
			Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String autoSuggest1(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data,
			// Keys.ENTER);

			driver.findElement(By.id("cropTypeAuto")).sendKeys("Cron");
			Thread.sleep(1000);
			driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.DOWN);
			Thread.sleep(1500);
			driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
			Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public Connection DbConnect(String object, String data)
			throws SQLException, FileNotFoundException, ClassNotFoundException {

		APP_LOGS.debug("fetching data from database");
		driver = new FirefoxDriver();
		// driver.navigate().to("http://www.google.com");
		// baseUrl =
		// "https://www.google.co.in/?gfe_rd=cr&ei=6uvSVbOUPKrv8wfHtIyQBA&gws_rd=ssl";

		WebDriver driver = null;
		Connection con = null;
		Statement stmt = null;
		String baseUrl;

		PrintStream outStream = null;
		PrintStream errStream = null;
		PrintStream fileStream = null;
		outStream = System.out;
		errStream = System.err;
		System.out.println("hi");
		OutputStream os = new FileOutputStream("D:/Dhvani/automation/iFormFactor/src/com/sample/util/result.html",
				false); // only the file output stream
		os = new TeeOutputStream(outStream, os); // create a TeeOutputStream
													// that duplicates data to
													// outStream and os
		fileStream = new PrintStream(os);

		System.setErr(fileStream);
		System.setOut(fileStream);

		// Load Microsoft SQL Server JDBC driver.
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// Class.forName("com.mysql.jdbc.Driver");
		// Prepare connection url.
		String url = "jdbc:sqlserver://SVT-SRV-55:1433;DatabaseName=iFormsQA";
		// String url = "jdbc:mysql://SVT-SRV-55;DatabaseName=iFormsQA";

		// String url ="jdbc:mysql://SVT-SRV-55//SQLEXPRESS:11.0.2100/";

		String pwd = CONFIG.getProperty("QA_DB_Password");
		// Get connection to DB.
		con = DriverManager.getConnection(url, "sa", pwd);
		// Create statement object which would be used in writing DDL and DML
		// SQL statement.
		stmt = con.createStatement();
		// Send SQL SELECT statements to the database via the
		// Statement.executeQuery
		// method which returns the requested information as rows of data in a
		// ResultSet object.
		// define query to read data

		try {
			// String tableName="[Lookup].[Country]";

			String query = "select * from [Lookup].[Country]";
			ResultSet result = stmt.executeQuery(query);

			int count = 0;
			while (result.next()) {
				// if (result.next()) {
				String CountryName = result.getString("CountryName");
				System.out.println("Country Name : " + CountryName);
				// fw.write(System.getProperty("line.separator"));
				// System.getProperty("line.separator");
				// System.out.println( "<br>");
				System.out.println("&nbsp;");
				String CountryId = result.getString("CountryId");
				// System.out.println( "<br>");
				System.out.println("&nbsp;");
				System.out.println("CountryId : " + CountryId);
				System.out.println("<br>");
				count = count + 1;
			}

		}

		catch (SQLException ex) {
			System.out.println("Error:" + ex);
		}

		return con;

	}

	public String storeValue(String object, String data) {
		try {
			String Value1 = driver.findElement(By.id((OR.getProperty(object)))).getText();
			Pattern p = Pattern.compile("The work order: (.*)and assignment were created successfully");
			Matcher m = p.matcher(Value1);
			if (m.find()) {
				String Value = m.group(1);
				System.out.println("generated Work Order Number:" + Value);
				currentTestSuiteXLS.setCellData(DriverScript.currentTestCaseName, data, currentTestDataSetID, Value);
				// updateProperty(In_No);
				result = Constants.KEYWORD_PASS;
			}

			result = Constants.KEYWORD_PASS;
		}

		catch (Exception e) {
			result = Constants.KEYWORD_FAIL + e;
		}
		/*
		 * try { System.out.println("Updating Excel References");
		 * getRefFlag(DriverScript.currentTestCaseName, data); updatePropertyFile(data);
		 * 
		 * } catch(Exception e) { System.out.println(
		 * "Unable to update data in ref sheet"); System.out.println(e.getMessage());
		 * result= Constants.KEYWORD_FAIL; }
		 */

		return result;
	}

	public String getLatestValue(String object, String data) {
		String output = "";
		// String finalPrice="";
		try {
			URL url1 = new URL(
					"https://ondemand.websol.barchart.com/getQuote.json?apikey=d8984813a5fd51b6bf7a8b6756e12b26&symbols=ZCH17&fields=month,year&mode=I");
			// URL url1 = new
			// URL("https://ondemand.websol.barchart.com/getQuote.json?apikey=d8984813a5fd51b6bf7a8b6756e12b26&symbols=ZC^F&fields=month,year&mode=I",
			// "ZCH17", "lastPrice");
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				final JSONObject obj = new JSONObject(output);
				final JSONArray geodata = obj.getJSONArray("results");
				final int n = geodata.length();
				for (int i = 0; i < n; ++i) {
					final JSONObject person = geodata.getJSONObject(i);
					String expected = person.getString("id");
					String expected1 = person.getString("localPrice");
					String expected2 = person.getString("cropName");
					/*
					 * if(expected.equals("ZCH17")) { String
					 * latestPrice=String.valueOf(person.getDouble("lastPrice")/ 100);
					 * finalPrice=latestPrice.substring(0,latestPrice.length());
					 */

					System.out.println(expected);
					System.out.println(expected1);
					System.out.println(expected2);

				}
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		// currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID,
		// finalPrice);
		return output;

		// return finalPrice;

	}

	public String getLocalPriceValue(String object, String data) {
		String output = "";
		String finalPrice = "";
		String cropname = "";
		try {
			URL url1 = new URL("http://14.102.76.54:81/agyieldservices/services/fieldCrop/fetchLocalPrice?year=2017");
			// URL url1 = new
			// URL("https://ondemand.websol.barchart.com/getQuote.json?apikey=d8984813a5fd51b6bf7a8b6756e12b26&symbols=ZC^F&fields=month,year&mode=I",
			// "ZCH17", "lastPrice");
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				final JSONObject obj = new JSONObject(output);
				final JSONArray geodata = obj.getJSONArray("results");
				final int n = geodata.length();
				for (int i = 0; i < n; ++i) {
					final JSONObject person = geodata.getJSONObject(i);
					String expected = person.getString("cropName");
					String expected1 = person.getString("localPrice");
					// if(expected.equals("ZCH17"))
					{
						// String
						// latestPrice=String.valueOf(person.getDouble("lastPrice")/100);
						// finalPrice=latestPrice.substring(0,latestPrice.length());
						// cropname=expected;
						finalPrice = expected1;
						System.out.println(cropname);
						System.out.println(finalPrice);

					}
				}

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return finalPrice;

		/*
		 * currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID,
		 * finalPrice); return finalPrice; currentTestSuiteXLS.setCellData(object, data,
		 * currentTestDataSetID, cropname); return cropname;
		 */

	}

	// Keyword for Calculate And Verify Unsold Production Values

	// Keyword for Calculate And Verify Cash Sales Values

	public String caluclateAndVerfiyCashSalesValue(String object, String data)

	{
		int size = driver.findElements(By.xpath("//table[@id='cashSalesTable']//tbody//tr")).size();
		ArrayList<String> cashType = new ArrayList<String>();
		ArrayList<String> cashCrop = new ArrayList<String>();
		ArrayList<String> cashquantity = new ArrayList<String>();
		ArrayList<String> cashPrice = new ArrayList<String>();
		ArrayList<String> cashbasis = new ArrayList<String>();
		ArrayList<String> cashvalue = new ArrayList<String>();
		for (int cashsales = 1; cashsales <= size; cashsales++) {

			String type = driver
					.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr[" + cashsales + "]//td[1]"))
					.getText();
			String crop = driver
					.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr[" + cashsales + "]//td[2]"))
					.getText();
			String quantity = driver
					.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr[" + cashsales + "]//td[3]"))
					.getText();
			String price = driver
					.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr[" + cashsales + "]//td[4]"))
					.getText();
			String basis = driver
					.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr[" + cashsales + "]//td[5]"))
					.getText();
			// String
			// actualvalue=driver.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr["+cashsales+"]//td[7]")).getText();

			cashType.add(type);
			cashCrop.add(crop);
			cashquantity.add(quantity);
			cashPrice.add(price);
			cashbasis.add(basis);
			// cashvalue.add(actualvalue);

			price = price.replaceAll("[^0.0-9.0]", "").trim();
			// BigDecimal localprice = new BigDecimal(price);
			// localprice = localprice.setScale(2, RoundingMode.HALF_EVEN);
			quantity = quantity.replaceAll("[^0.0-9.0]", "").trim();

			System.out.println(quantity);
			double actualvalue = Double.parseDouble(cashquantity.get(cashsales))
					* Double.parseDouble(cashPrice.get(cashsales));

			if (driver.findElement(By.xpath(".//*[@id='cashSales']/td[4]")).getText().equals(actualvalue))
				;

			driver.findElement(By.xpath(".//*[@id='cashSales']/td[4]")).getText();
			// if(driver.findElement(By.xpath("//table[@id='cashSalesTable']//tbody//tr["+cashsales+"]//td[7]")).getText().equals(cashvalue));
			System.out.println(actualvalue);
			return Constants.KEYWORD_PASS;

		}
		return Constants.KEYWORD_FAIL;
	}

	// Keyword for Calculate And Verify Cash Sales Values

	/*
	 * public String caluclateAndVerfiyFutureOptionValue(String object,String data)
	 * 
	 * { //int size=driver.findElements(By.xpath(
	 * "//*[@id='futureAndOptionOpenBlock']/div")).size(); int
	 * size=driver.findElements(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr")).size();
	 * 
	 * ArrayList<String> OpenBuys=new ArrayList<String>(); ArrayList<String>
	 * OpenSells=new ArrayList<String>(); ArrayList<String> OpenContract=new
	 * ArrayList<String>(); ArrayList<String> OpenTradePrice=new
	 * ArrayList<String>(); ArrayList<String> OpenMarketPrice=new
	 * ArrayList<String>(); ArrayList<String> OpenProfitLoss=new
	 * ArrayList<String>();
	 * 
	 * 
	 * for(int openfuture=1;openfuture<=size;openfuture++) {
	 * //.//*[@id='futureOptionOpenTable']/tbody/tr[1]/td[1] String
	 * Buys=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[1]")).
	 * getText(); String Sells=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[2]")).
	 * getText(); String Contract=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[3]")).
	 * getText(); String TradePrice=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[4]")).
	 * getText(); String MarketPrice=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[5]")).
	 * getText(); String ProfitLoss=driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[6]")).
	 * getText();
	 * 
	 * 
	 * OpenBuys.add(Buys); OpenSells.add(Sells); OpenContract.add(Contract);
	 * OpenTradePrice.add(TradePrice); OpenMarketPrice.add(MarketPrice);
	 * //OpenProfitLoss.add(ProfitLoss);
	 * 
	 * 
	 * TradePrice=TradePrice.replaceAll("[^0.0-9.0]", "").trim(); BigDecimal
	 * localprice = new BigDecimal(TradePrice); localprice = localprice.setScale(2,
	 * RoundingMode.HALF_EVEN); quantity=quantity.replaceAll("[^0.0-9.0]",
	 * "").trim();
	 * 
	 * System.out.println(localprice); System.out.println(quantity); double
	 * finalval=Double.parseDouble(cashquantity.get(cashsales))*Double.
	 * parseDouble(cashPrice.get(cashsales));
	 * 
	 * if(driver.findElement(By.xpath(
	 * "//*[@id='futureOptionOpenTable']//tbody//tr["+openfuture+"]//td[6]")).
	 * getText().equals(cashvalue)); System.out.println(finalval); return
	 * Constants.KEYWORD_PASS;
	 * 
	 * } return Constants.KEYWORD_FAIL; }
	 */

	public ArrayList<String> getresponse(String object, String data)

	{

		ArrayList<String> listItems = new ArrayList<String>();
		ArrayList<String> CropID = new ArrayList<String>();
		ArrayList<String> CropLocalPrice = new ArrayList<String>();
		ArrayList<String> CropList = new ArrayList<String>();

		try {
			URL url1 = new URL("http://14.102.76.54:81/agyieldservices/services/fieldCrop/fetchLocalPrice?year=2017");

			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader in = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String line;
			while ((line = in.readLine()) != null) {
				JSONArray ja = new JSONArray(line);

				caluclateAndVerfiyUnsoldResult(null, ja);

				// System.out.println(line);
				/*
				 * for (int i = 0; i < ja.length(); i++) { JSONObject jo = (JSONObject)
				 * ja.get(i); String jsonStr = jo.toString(); System.out.println("jsonstr:: " +
				 * jsonStr); //String jsonStr2 = jsonStr.replace("\"", "\"\"");
				 * //System.out.println("jsonstr2:: " + jsonStr2); ObjectMapper mapper = new
				 * ObjectMapper(); KeywordPOJO staff1 = mapper.readValue(jo.toString(),
				 * KeywordPOJO.class); System.out.println("staff1:: " + staff1.toString());
				 * 
				 * 
				 * }
				 */
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listItems;
	}

	public String compareTabeleSize(String object, String data) {
		try {
			sleep(5);
			String S1 = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/div[3]"))
					.getText();

			System.out.println(S1);
			sleep(10);
			driver.findElement(By.id("customerGroup_MenuBtn")).click();
			sleep(10);

			String S2 = driver
					.findElement(
							By.xpath(".//*[@id='customerGroupList_CRM']/tbody/tr[td[text()=' Cash Sales Only']]/td[2]"))
					.getText();
			sleep(10);

			System.out.println(S2);

			if (S1.contains(S2)) {
				return Constants.KEYWORD_PASS;

			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String OnlyInsurance(String object, String data) {
		try {
			sleep(5);
			String S1 = "You can add only one insurance for one global strategy.";

			System.out.println(S1);
			sleep(10);

			String S2 = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
			sleep(10);

			System.out.println(S2);

			if (S1.contains(S2)) {
				return Constants.KEYWORD_PASS;

			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String AddAccount(String object, String data) {
		try {

			String AccNo = "Acc_" + System.currentTimeMillis();
			System.out.println(AccNo);

			AccNo = AccNo.substring(0, 4) + AccNo.substring(13, 17);

			String Expected_Result = AccNo;
			Thread.sleep(15000);

			// driver.findElement(By.id("tradingAccountLInk")).click();
			sleep(15);
			driver.findElement(By.id("FCM")).click();
			driver.findElement(By.xpath("//option[text()='ADM']")).click();
			sleep(10);

			driver.findElement(By.id("OfficeId")).click();
			driver.findElement(By.xpath("//option[text()='265 EHedger']")).click();
			sleep(10);
			driver.findElement(By.id("accountCode")).sendKeys(AccNo);
			driver.findElement(By.id("saveTradingAccount")).click();
			sleep(10);
			// driver.findElement(By.id("tradingAccountLInk")).click();
			sleep(10);

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String VerifyDeletebutton(String object, String data) {
		APP_LOGS.debug("Verifying link Text");
		try {

			Boolean actual = driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
			System.out.println(actual);

			if (actual.equals(true)) {

				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- Delete button is not exists";

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String VerifyDeletebutton1(String object, String data) {
		APP_LOGS.debug("Verifying link Text");
		try {

			Boolean actual = driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			System.out.println(actual);

			if (actual.equals(true)) {

				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- Delete button is not exists";

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String SelectOrganization(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			// driver.findElement(By.xpath(OR.getProperty(object))).click();
			// Thread.sleep(1000);

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			Thread.sleep(1000);
			// Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String click_tab(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			// driver.findElement(By.xpath(OR.getProperty(object))).click();
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh1_pc(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(3000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			// Thread.sleep(3000);
//			   driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization2(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh_pc(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
//			WebDriverWait wait3 = new WebDriverWait(driver, 1000);
//			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
//			System.out.println("Element is now clickable");

//			driver.findElement(By.id(OR.getProperty(object))).clear();
//			driver.findElement(By.id(OR.getProperty(object))).click();
			Thread.sleep(1000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			// Thread.sleep(3000);
//			   driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);

			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh2(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(3000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh3(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			Thread.sleep(2000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String selectorganization_without_down(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			Thread.sleep(500);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String selectorganization_without_down_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String selectorganization_without_down_xpath(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public void enter_pan_if_visible(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebElement d1 = driver.findElement(By.xpath("//input[@name='PanNo']"));
			d1.clear();
			if (driver.findElement(By.xpath("//input[@name='PanNo']")).isDisplayed()) {
				driver.findElement(By.xpath("//input[@name='PanNo']")).clear();
				driver.findElement(By.xpath("//input[@name='PanNo']")).click();

				Random rnd = new Random();
				String firstfivechar = RandomStringUtils.randomAlphabetic(5).toUpperCase();

				System.out.println("First str: " + firstfivechar);
				String digit = RandomStringUtils.randomNumeric(4);
				System.out.println("Diget str: " + digit);
				String lastchar = RandomStringUtils.randomAlphabetic(1).toUpperCase();
				System.out.println("last str: " + lastchar);

				String full = firstfivechar + digit + lastchar;
				System.out.println(full);

				driver.findElement(By.xpath("//input[@name='PanNo']")).sendKeys(full);

			}

			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
	}

	public String Selectmodel_pc(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			// driver.findElement(By.id(OR.getProperty(object))).click();
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			//Thread.sleep(1000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			// Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			// Thread.sleep(5000);
//			   driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Selectmodel(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Selectmodelwithxpath(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			// driver.findElement(By.xpath(OR.getProperty(object))).click();
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			//
			// Thread.sleep(5000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectImd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();
			// Thread.sleep(1000);
			// driver.findElement(By.id(OR.getProperty(object))).click();

			// Robot robot = new Robot(); // Robot class throws AWT Exception
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(1000);
			// Thread.sleep(3000);

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationonlydrop(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception
			Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(5000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(8000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationhtab(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(10000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(5000);
//			   driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
//			   Thread.sleep(10000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(8000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(8000);
			// driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.DOWN);
//			   driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectCorn(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			js5.executeScript("$('b:contains(\"Corn\")').click();");
			Thread.sleep(10000);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//		       Thread.sleep(1500);
			driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.TAB);
			Thread.sleep(5000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Discount1(String object, String data) {
		try {

			String Discount = "D" + System.currentTimeMillis();

			System.out.println(Discount);

			// Discount = Discount.substring(0, 4) + Discount.substring(17, 17);
			Discount = Discount.substring(0, 9);

			System.out.println(Discount);

			String Expected_Result = Discount;

			Thread.sleep(5000);

			driver.findElement(By.xpath("//input[@id='discountAddCode']")).sendKeys(Discount);
			sleep(10);

			{

				return Constants.KEYWORD_PASS;

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String datePicker1(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("validDateFrom")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[3]")).click();
			driver.findElement(By.id("validDateFrom")).sendKeys(Keys.ENTER);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	public String datePicker2(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("validDateTo")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[6]/td[3]")).click();
			driver.findElement(By.id("validDateTo")).sendKeys(Keys.ENTER);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker3(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("insStartDt")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[6]/div[1]/table/tbody/tr[3]/td[3]")).click();
			driver.findElement(By.id("insStartDt")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker31(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("insStartDt")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[3]")).click();
			driver.findElement(By.id("insStartDt")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker4(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("insEndDt")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[6]/div[1]/table/tbody/tr[6]/td[3]")).click();
			driver.findElement(By.id("insEndDt")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker41(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("insEndDt")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[3]")).click();
			driver.findElement(By.id("insEndDt")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker5(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("startDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[5]")).click();
			driver.findElement(By.id("startDate")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker8(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[3]/td[4]")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker6(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("endDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[6]")).click();
			driver.findElement(By.id("endDate")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker10(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[3]/td[5]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker11(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("cashSalesSellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("cashSalesSellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker12(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("cashSalesDeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[4]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("cashSalesDeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker13(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("HTAsellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("HTAsellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker14(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("HTAdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("HTAdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker15(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasissellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[2]/td[5]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasissellDate")).sendKeys(Keys.ENTER);

			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker16(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker17(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("percentagesellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("percentagesellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker18(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("percentagedeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("percentagedeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker19(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("Futuresdate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("Futuresdate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker20(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("Optionsdate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("Optionsdate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker21(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasissellDate")).click();
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasissellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker22(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker23(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(4000);
			driver.findElement(By.id("startDate")).click();
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[3]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("startDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker24(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("endDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("endDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker25(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("HTAsellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[5]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("HTAsellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker26(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("HTAdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[4]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("HTAdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker27(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasissellDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasissellDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker28(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[3]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker29(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("startDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[3]/td[7]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("startDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker30(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("endDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/thead/tr[1]/th[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[5]/td[1]")).click();
			Thread.sleep(5000);

			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("endDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String dateotherreve(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("OtherDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[1]/td[4]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("OtherDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String dateotherreve1(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("indemnityDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("html/body/div[7]/div[1]/table/tbody/tr[3]/td[6]")).click();
			// driver.findElement(By.id("BasisdeliveryDate")).click();
			driver.findElement(By.id("indemnityDate")).sendKeys(Keys.ENTER);
			// driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.TAB);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public String datePicker101(String object, String data) {
		APP_LOGS.debug("selecting date");
		try {
			driver.findElement(By.id("BasisdeliveryDate")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[7]/div/table/tbody/tr[2]/td[4]")).click();
			driver.findElement(By.id("BasisdeliveryDate")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to select the date" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	public static String DownloadFile(String object, String data) throws AWTException {
		StringSelection ss = new StringSelection(data);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		return Constants.KEYWORD_PASS;

	}

	public String AddEntity1(String object, String data) {
		try {

			String ENTNO = "ENT-" + System.currentTimeMillis();
			System.out.println(ENTNO);

			ENTNO = ENTNO.substring(0, 4) + ENTNO.substring(13, 17);

			String Expected_Result = ENTNO;

			driver.findElement(By.id("addEntityBtn")).click();

			Thread.sleep(5000);

			driver.findElement(By.id("EntityName")).sendKeys(ENTNO);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, ENTNO)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}
			driver.findElement(By.id("updateEntityBtn")).click();
			sleep(10);

			{

				return Constants.KEYWORD_PASS;

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String EnterField(String object, String data) {
		try {

			String FD = "FLA-" + System.currentTimeMillis();
			System.out.println(FD);

			FD = FD.substring(0, 4) + FD.substring(13, 17);

			String Expected_Result = FD;

			System.out.println(Expected_Result);

			Thread.sleep(10000);

			driver.findElement(By.xpath("//input[@id='fieldName_1']")).sendKeys(FD);
			System.out.println(FD);
			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, FD)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

			sleep(10);

			{

				return Constants.KEYWORD_PASS;

			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Otherrevenuecompare(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath("//table[@id='otherRevenueTable']//tr[1]/td[4]")).getText()
					.trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("$", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Insurancecompare(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver
					.findElement(By.xpath(
							"html/body/div[2]/div[2]/div[3]/div/div[3]/table/tbody/tr[4]/td/div/table/tbody/tr/td[8]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("$", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecashsales(String object, String data) {
		try {

			Thread.sleep(5000);
			driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();
			Thread.sleep(5000);
			String accumalatorqty = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[3]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;
			String accumalatoravgb = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			if (value1.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecashsalesHTA(String object, String data) {
		try {

			Thread.sleep(5000);
			// driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();
			Thread.sleep(5000);
			String accumalatorqty = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'HTA')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'HTA')]//following::td[3]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;

			String accumalatoravgb = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'HTA')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			// System.out.println(AccumalatorAVGb);

			// String accumalatoravgb1=accumalatoravgb.replace("/bu", "").replace("$",
			// "").replace(" ", "").replace("(", "").replace(")", "");
			// Double AccumalatorAVGb= Double.parseDouble(accumalatoravgb1);
			System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'HTA')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			if (value1.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareACCUMULATOR(String object, String data) {
		try {

			Thread.sleep(10000);

			String accumalatorqty = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR ')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR ')]//following::td[3]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;
			String accumalatoravgb = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR ')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			// String accumalatoravgb1=accumalatoravgb.replace("/bu", "").replace("$",
			// "").replace(" ", "").replace("(", "").replace(")", "");
			// Double AccumalatorAVGb= Double.parseDouble(accumalatoravgb1);
			// System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'ACCUMULATOR ')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			if (value1.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparevalue$acre(String object, String data) {
		try {

			Thread.sleep(10000);

			String value$ = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id ='trGainLoss']/td[4]")).getText()
					.trim();
			String value$s = value$.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "").replace(",", "");
			;
			Double values$n = Double.parseDouble(value$s);
			System.out.println(values$n);
			Thread.sleep(5000);

			String acre = driver.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='acres']/span"))
					.getText().trim();
			String acres = acre.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "").replace(")", "");
			Double ACRE = Double.parseDouble(acres);
			System.out.println(ACRE);
			Thread.sleep(5000);

			df2.setRoundingMode(RoundingMode.HALF_UP);
			Double value$acres = (values$n / ACRE);

			Double ans = Double.valueOf(df2.format(value$acres.doubleValue()));
			System.out.println(ans);

			driver.findElement(By.id("units_dollars_summary")).sendKeys("VALUE in $/ac");
			Thread.sleep(10000);

			String value$acc = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id ='trGainLoss']/td[4]")).getText()
					.trim();

			String value$accs = value$acc.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "");
			Double value$accn = Double.parseDouble(value$accs);
			System.out.println(value$accn);
			Thread.sleep(5000);

			if (value$accn.equals(ans))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value$acres + "--" + value$accn;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparevalue$unit(String object, String data) {
		try {

			driver.findElement(By.id("units_dollars_summary")).sendKeys("VALUE in $");
			driver.findElement(By.id("units_dollars_summary")).sendKeys("dollar");
			driver.findElement(By.id("units_dollars_summary")).sendKeys("VALUE in $/unit");
			Thread.sleep(5000);
			driver.findElement(By.id("units_dollars_summary")).sendKeys("VALUE in $");

			String value$ = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id ='trGainLoss']/td[4]")).getText()
					.trim();
			String value$s = value$.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "").replace(",", "");
			;
			Double values$n = Double.parseDouble(value$s);
			System.out.println(values$n);
			Thread.sleep(5000);

			String acre = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText().trim();
			String acres = acre.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "").replace(")", "")
					.replace(",", "");
			Double ACRE = Double.parseDouble(acres);
			System.out.println(ACRE);
			Thread.sleep(5000);

			Double value$acre = (values$n / ACRE);
			System.out.println(value$acre);
			df2.setRoundingMode(RoundingMode.HALF_UP);
			Double value$acres = (values$n / ACRE);

			Double ans = Double.valueOf(df2.format(value$acres.doubleValue()));
			System.out.println(ans);

			driver.findElement(By.id("units_dollars_summary")).sendKeys("VALUE in $/unit");
			Thread.sleep(10000);

			String value$acc = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id ='trGainLoss']/td[4]")).getText()
					.trim();

			String value$accs = value$acc.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "");
			Double value$accn = Double.parseDouble(value$accs);
			System.out.println(value$accn);
			Thread.sleep(5000);

			if (value$accn.equals(ans))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value$accn + "--" + ans;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareunsold(String object, String data) {
		try {

			Thread.sleep(10000);

			String accumalatorqty = driver.findElement(By.xpath("//table[@id='unsoldBushelsTable']/tbody/tr/td[3]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath("//table[@id='unsoldBushelsTable']/tbody/tr/td[4]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;
			String accumalatoravgb = driver.findElement(By.xpath("//table[@id='unsoldBushelsTable']/tbody/tr/td[5]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			String premiums = driver.findElement(By.xpath("//table[@id='unsoldBushelsTable']/tbody/tr/td[6]")).getText()
					.trim();
			String premiumf = premiums.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "");
			Double premiumss = Double.parseDouble(premiumf);
			System.out.println(premiumss);

			String accumalatoravgvalue = driver
					.findElement(By.xpath("//table[@id='unsoldBushelsTable']/tbody/tr/td[7]")).getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double H = (AccumalatorAVG + AccumalatorAVGb);
			System.out.println(H);

			Double value1 = (H + premiumss) * AccumalatorQTY;

			/*
			 * DecimalFormat ans = new DecimalFormat(df2.format(value1.doubleValue()));
			 * ans.setRoundingMode();
			 */
			df2.setRoundingMode(RoundingMode.CEILING);

			Double ans = Double.valueOf(df2.format(value1.doubleValue()));

			System.out.println(ans);

			if (ans.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparefuttureoption(String object, String data) {
		try {

			Thread.sleep(5000);

			// driver.findElement(By.xpath("futureOption_Link")).click();
			Thread.sleep(5000);

			boolean isNegative = false;
			String TradePrices = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[1]/td[4]"))
					.getText().trim();
			String Tradedpricess = "";
			if (TradePrices.contains("(")) {
				isNegative = true;

				Tradedpricess = TradePrices.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				Tradedpricess = TradePrices.replace("$", "").replace(" ", "").replace("/bu", "");

			}
			Double Tradeprice = Double.parseDouble(Tradedpricess);
			if (isNegative) {
				Tradeprice = -Tradeprice;
			}
			System.out.println(Tradeprice);
			Thread.sleep(5000);

			String Makretprices = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[1]/td[5]"))
					.getText().trim();
			String Marketpricess = Makretprices.replace("/bu", "").replace("$", "").replace(" ", "");
			Double MP = Double.parseDouble(Marketpricess);
			System.out.println(MP);

			boolean isNegativePL = false;
			String profitlosss = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[1]/td[6]"))
					.getText().trim();
			String profifitlosss;
			if (profitlosss.contains("(")) {
				isNegativePL = true;

				profifitlosss = profitlosss.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
						.replace(")", "").replace("$", "").replace(",", "");

			}

			else

			{
				profifitlosss = profitlosss.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
						.replace(")", "").replace("$", "").replace(",", "");

			}
			Double PL = Double.parseDouble(profifitlosss);
			System.out.println(PL);
			if (isNegativePL) {
				PL = -PL;
			}
			System.out.println(PL);

			Double value1 = (MP - Tradeprice) * 100;
			Double value2 = value1 * 5000;

			Double ans = Double.valueOf(df2.format(value2.doubleValue()));

			System.out.println(ans);

			if (ans.equals(PL))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + PL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String caculategainlossgoal(String object, String data) {
		try {

			Thread.sleep(5000);

			driver.findElement(By.id("RevenueGoal_Link")).click();

			Thread.sleep(5000);

			String Acres = driver.findElement(By.xpath("//table[@id='revenueGoalTable']/tbody/tr[1]/td[3]")).getText()
					.trim();
			String Acress = Acres.replace("$", "").replace(" ", "").replace("/bu", "").replace(" ", "");
			Double ACRE = Double.parseDouble(Acress);
			System.out.println(ACRE);
			Thread.sleep(5000);

			String totaloperatingcosts = driver
					.findElement(By.xpath("//table[@id='revenueGoalTable']/tbody/tr[1]/td[5]")).getText().trim();
			String totaloperatingcostss = totaloperatingcosts.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace("$", "").replace(",", "").replace("/ac", "");
			Double Totaloperatingcost = Double.parseDouble(totaloperatingcostss);
			System.out.println(Totaloperatingcost);

			String totals = driver.findElement(By.xpath("//table[@id='revenueGoalTable']/tbody/tr[1]/td[6]")).getText()
					.trim();
			String totalss = totals.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "").replace("$", "").replace(",", "").replace("/ac", "");
			Double Total = Double.parseDouble(totalss);
			System.out.println(Total);

			Double value1 = (ACRE * Totaloperatingcost);

			if (value1.equals(Total))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Totaloperatingcost + "--" + value1;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparefuttureoption1(String object, String data) {
		try {

			Thread.sleep(10000);

			String TradePrices = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[2]/td[4]"))
					.getText().trim();
			String Tradedpricess = TradePrices.replace("$", "").replace(" ", "").replace("/bu", "");
			Double Tradeprice = Double.parseDouble(Tradedpricess);
			System.out.println(Tradeprice);
			Thread.sleep(5000);

			String Makretprices = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[2]/td[5]"))
					.getText().trim();
			String Marketpricess = Makretprices.replace("/bu", "").replace("$", "").replace(" ", "");
			Double MP = Double.parseDouble(Marketpricess);
			System.out.println(MP);

			String profitlosss = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']/tbody/tr[2]/td[6]"))
					.getText().trim();
			String profifitlosss = profitlosss.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "").replace("$", "").replace(",", "");
			Double PL = Double.parseDouble(profifitlosss);
			System.out.println(PL);

			Double value1 = (MP - Tradeprice) * 50;
			Double value2 = value1 * 5000;

			Double ans = Double.valueOf(df2.format(value2.doubleValue()));
			ans = ans * (-1);
			System.out.println(ans);

			if (ans.equals(PL))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + PL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecop(String object, String data) {
		try {

			Thread.sleep(10000);

			String acres = driver.findElement(By.xpath("//table[@id='costOfProdTable']/tbody/tr/td[3]")).getText()
					.trim();
			String acres1 = acres.replace("bu", "").replace(",", "").replace(" ", "");
			Double ACRE = Double.parseDouble(acres1);
			System.out.println(ACRE);
			Thread.sleep(5000);

			String costperaverages = driver.findElement(By.xpath("//table[@id='costOfProdTable']/tbody/tr/td[4]"))
					.getText().trim();
			String costperaverages1 = costperaverages.replace("/bu", "").replace("$", "").replace(" ", "");
			Double COP = Double.parseDouble(costperaverages1);
			System.out.println(COP);

			String totaloperatingcosts = driver.findElement(By.xpath("//table[@id='costOfProdTable']/tbody/tr/td[5]"))
					.getText().trim();
			String totaloperatingcosts1 = totaloperatingcosts.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(" ", "").replace(",", "");
			Double Totaloperatingcosts = Double.parseDouble(totaloperatingcosts1);
			System.out.println(Totaloperatingcosts);

			Double value1 = (ACRE * COP);

			if (value1.equals(Totaloperatingcosts))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + ACRE;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareMINMAX(String object, String data) {
		try {

			Thread.sleep(10000);

			String accumalatorqty = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath(
					"//table[contains(@class, 'transactionDatatableGrid')]/tbody/tr/td[text()='Min/Max']//following::td[2]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			driver.findElement(By.id("reports")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();

			boolean isNegative = false;
			String accumalatoravgb = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			// String accumalatoravgb1=accumalatoravgb.replace("/bu", "").replace("$",
			// "").replace(" ", "").replace("(", "").replace(")", "");
			// Double AccumalatorAVGb= Double.parseDouble(accumalatoravgb1);
			// System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			Double ans = Double.valueOf(df2.format(value1.doubleValue()));
			System.out.println(ans);

			if (ans.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecashsalesPER(String object, String data) {
		try {

			Thread.sleep(5000);
			// driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();
			Thread.sleep(5000);
			String accumalatorqty = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[3]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;
			String accumalatoravgb = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			// String accumalatoravgb1=accumalatoravgb.replace("/bu", "").replace("$",
			// "").replace(" ", "").replace("(", "").replace(")", "");
			// Double AccumalatorAVGb= Double.parseDouble(accumalatoravgb1);
			// System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			if (value1.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecashsalesManageP(String object, String data) {
		try {

			Thread.sleep(5000);
			// driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();
			Thread.sleep(5000);
			String accumalatorqty = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MBP')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			String accumalatoravg = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MBP')]//following::td[3]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			boolean isNegative = false;
			String accumalatoravgb = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MBP')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'MBP')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			if (value1.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparecashsalesBasis(String object, String data) {
		try {

			Thread.sleep(5000);
			// driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();

			String accumalatorqty = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'BASIS')]//following::td[2]"))
					.getText().trim();
			String accumalatorqty1 = accumalatorqty.replace("bu", "").replace(",", "").replace(" ", "");
			Double AccumalatorQTY = Double.parseDouble(accumalatorqty1);
			System.out.println(AccumalatorQTY);
			Thread.sleep(5000);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(5000);

			String accumalatoravg = driver.findElement(By.xpath(
					"//table[contains(@class, 'transactionDatatableGrid')]/tbody/tr/td[text()='Basis']//following::td[2]"))
					.getText().trim();
			String accumalatoravg1 = accumalatoravg.replace("/bu", "").replace("$", "").replace(" ", "");
			Double AccumalatorAVG = Double.parseDouble(accumalatoravg1);
			System.out.println(AccumalatorAVG);

			driver.findElement(By.id("reports")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//td[@id='caseSales_Link']")).click();

			boolean isNegative = false;
			String accumalatoravgb = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'BASIS')]//following::td[4]"))
					.getText().trim();
			if (accumalatoravgb.contains("(")) {
				isNegative = true;

				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "");

			}

			else

			{
				accumalatoravgb = accumalatoravgb.replace("$", "").replace(" ", "").replace("/bu", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(accumalatoravgb);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			String accumalatoravgvalue = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'BASIS')]//following::td[5]"))
					.getText().trim();
			String accumalatoravgbvalue1 = accumalatoravgvalue.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double AccumalatorAVGvalue = Double.parseDouble(accumalatoravgbvalue1);
			System.out.println(AccumalatorAVGvalue);

			Double value1 = (AccumalatorAVG + AccumalatorAVGb) * AccumalatorQTY;

			Double ans = Double.valueOf(df2.format(value1.doubleValue()));
			System.out.println(ans);

			if (ans.equals(AccumalatorAVGvalue))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + value1 + "--" + AccumalatorAVGvalue;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareHTA(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver
					.findElement(By.xpath(
							"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'HTA')]//following::td[2]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareOtherrevenue(String object, String data) {
		try {

			Thread.sleep(5000);
			String actualOther = driver.findElement(By.xpath(
					"html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr/td[5]"))
					.getText().trim();
			String actualother1 = actualOther.replace("$", "").replace(",", "").replace(" ", "");
			Double actulother2 = Double.parseDouble(actualother1);
			System.out.println(actulother2);

			driver.findElement(By.id("reports")).click();
			Thread.sleep(5000);

			String expected = driver.findElement(By.xpath("//td[@id='otherRevenue_Link']//following::td[3]")).getText()
					.trim();
			System.out.println(expected);
			String actualother11 = expected.replace("$", "").replace(",", "").replace(" ", "");

			Double expectedothers = Double.parseDouble(actualother11);

			if (actulother2.equals(expectedothers))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actulother2 + "--" + expectedothers;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareinsurance(String object, String data) {
		try {

			Thread.sleep(5000);
			String actual = driver.findElement(By.xpath(
					"html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[1]/h4[2]/span"))
					.getText().trim();
			System.out.println(actual);

			String actual1 = actual.replace("$ ", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);
			Double a1 = Double.parseDouble(actual1);
			driver.findElement(By.id("reports")).click();
			Thread.sleep(10000);
			String expectedI = driver.findElement(By.xpath("//td[@id='cropInsurance_Link']//following::td[3]"))
					.getText().trim();
			String expectedI1 = expectedI.replace("$", "").replace(",", "").replace(" ", "");

			Double expectedInsurance = Double.parseDouble(expectedI1);

			if (a1.equals(expectedInsurance))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expectedInsurance;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareestimatedreturn(String object, String data) {
		try {

			Thread.sleep(5000);
			String COP = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[3]/div/div[3]/table/tbody/tr[14]/td[4]"))
					.getText().trim();
			System.out.println(COP);

			String actual1 = COP.replace("$", "").replace("(", "").replace(")", "").replace(",", "");
			System.out.println(actual1);
			Double a1COP = Double.parseDouble(actual1);

			Thread.sleep(5000);

			String Gain = driver.findElement(By.xpath(".//*[@id='cop']")).getText().trim();
			System.out.println(Gain);
			String actual2 = Gain.replace("$", "").replace("(", "").replace(",", "").replace(")", "");

			System.out.println(actual2);
			Double a2Gain = Double.parseDouble(actual2);

			Double a3 = a1COP / a2Gain * 100;

			// Double a3 = (double)Math.round(a1/a2*100.0);
			Double ans = Double.valueOf(df2.format(a3.doubleValue()));
			System.out.println(ans);

			// driver.findElement(By.id("reports")).click();
			Thread.sleep(10000);
			String expectedI = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='estReturn']/span")).getText()
					.trim();
			String expectedI1 = expectedI.replace("-", "").replace("%", "").replace(" ", "");

			Double expectedreturns = Double.parseDouble(expectedI1);

			if (ans.equals(expectedreturns))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a3 + "--" + expectedreturns;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String calculateexpectedproduction(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText().trim();
			System.out.println(actual);
			String expected = data.replace(",", "").replace(",", "").replace(" ", "");
			Double a1 = Double.parseDouble(expected);
			System.out.println(a1);

			String actual1 = actual.replace(",", "").replace(",", "").replace(" ", "");
			// String actual1=actual.replace("Expected Production:", "");

			System.out.println(actual1);
			Double a2 = Double.parseDouble(actual1);
			System.out.println(a2);

			if (a1.equals(a2))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String agyieldscore(String object, String data) {
		try {

			Thread.sleep(15000);
			String actual = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='riskScore']/span")).getText()
					.trim();
			System.out.println(actual);

			Double a1 = Double.parseDouble(actual);
			System.out.println(a1);

			// driver.findElement(By.xpath("//a[contains(text(),'Benchmarking')]")).click();
			// Thread.sleep(15000);

			String expected = driver.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[27]/td[3]")).getText()
					.trim();

			Double a2 = Double.parseDouble(expected);

			System.out.println(a2);

			if (a1.equals(a2))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String acre(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath(
					"html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[1]/span[1]"))
					.getText().trim();
			System.out.println(actual);

			Double a1 = Double.parseDouble(actual);
			System.out.println(a1);

			driver.findElement(By.id("reports")).click();
			Thread.sleep(10000);

			String expected = driver.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='acres']/span"))
					.getText().trim();

			Double a2 = Double.parseDouble(expected);

			System.out.println(a2);

			if (a1.equals(a2))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String expectedproduction(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver.findElement(By.xpath(
					"html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[1]/span[1]"))
					.getText().trim();
			System.out.println(actual);

			Double a1 = Double.parseDouble(actual);
			System.out.println(a1);

			Thread.sleep(5000);

			String expected = driver.findElement(By.xpath(
					"html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[2]/h4[2]/span"))
					.getText().trim();
			String expected1 = expected.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a2 = Double.parseDouble(expected1);
			Double a3 = a1 * a2;
			System.out.println(a3);

			driver.findElement(By.id("reports")).click();
			Thread.sleep(5000);
			String Pexepcted = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText().trim();
			String Pexepcted1 = Pexepcted.replace(",", "").replace(" ", "");
			Double Pexepcted2 = Double.parseDouble(Pexepcted1);
			System.out.println(Pexepcted2);

			System.out.println(a2);

			if (a3.equals(Pexepcted2))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String calculatecashsales(String object, String data) {
		try {

			Thread.sleep(10000);
			String Qty = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'CASH')]//following::td[2]"))
					.getText().trim();
			String Qty1 = Qty.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(Qty1);

			Double a1 = Double.parseDouble(Qty1);
			System.out.println(a1);
			Thread.sleep(10000);

			String AvgPrice = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'CASH')]//following::td[3]"))
					.getText().trim();

			// String AvgPrice1=Qty.replace("$", "").replace("/bu", "").replace(" ", "");
			String Qty2 = AvgPrice.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");

			Double a2 = Double.parseDouble(Qty2);
			Double Cashsales = a1 * a2;
			System.out.println(Cashsales);

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String calculateHTA(String object, String data) {
		try {

			Thread.sleep(10000);
			String Qty = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'HTA')]//following::td[2]"))
					.getText().trim();
			String Qty1 = Qty.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(Qty1);

			Double a1 = Double.parseDouble(Qty1);
			System.out.println(a1);
			Thread.sleep(10000);

			String AvgPrice = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'HTA')]//following::td[3]"))
					.getText().trim();

			// String AvgPrice1=Qty.replace("$", "").replace("/bu", "").replace(" ", "");
			String Qty2 = AvgPrice.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");

			Double a2 = Double.parseDouble(Qty2);
			Double HTA = a1 * a2;
			System.out.println(HTA);

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String GlobalCashSales(String object, String data) {
		try {

			Thread.sleep(10000);
			String GridQTY = driver.findElement(By.xpath("//tr/td[contains(text(),'CASH')]//following::td[2]"))
					.getText().trim();
			String GridQTYs = GridQTY.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(GridQTYs);

			Double GridQTYF = Double.parseDouble(GridQTYs);
			System.out.println(GridQTYF);
			Thread.sleep(10000);

			String Pexepcted = driver.findElement(By.xpath("//div[@id='exp_production']//span[contains(text(),'')]"))
					.getText().trim();
			String Pexepcted1 = Pexepcted.replace(",", "").replace(" ", "");
			Double Pexepcted2 = Double.parseDouble(Pexepcted1);
			System.out.println("Exp. Production Value:" + Pexepcted2);

			Double Expected_Result = (Pexepcted2 * 10) / 100;

			if (Expected_Result.equals(GridQTYF))

				return Constants.KEYWORD_PASS;

			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_Result + "--" + GridQTYF;
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String GlobalInsurance(String object, String data) {
		try {

			String GridQTY = driver.findElement(By.xpath("//tr/td[contains(text(),'Insurance')]//following::td[2]"))
					.getText().trim();
			String GridQTYs = GridQTY.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(GridQTYs);

			String Acre = driver.findElement(By.xpath("//div[@id='land_unit_name']//span[contains(text(),'')]"))
					.getText().trim();
			String Acres = Acre.replace("bu", "").replace(",", "").replace(" ", "");

			Double AcresF = Double.parseDouble(Acres);
			System.out.println("Acre Value:" + Acres);

			Double Expected_result = (AcresF * 250 * 60) / 100;

			Double GridQTYF = Double.parseDouble(GridQTYs);
			System.out.println(GridQTYF);

			if (Expected_result.equals(GridQTYF))

				return Constants.KEYWORD_PASS;

			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_result + "--" + GridQTYF;
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String GlobalOptions(String object, String data) {
		try {

			String Pexepcted = driver.findElement(By.xpath("//div[@id='exp_production']//span[contains(text(),'')]"))
					.getText().trim();
			String Pexepcted1 = Pexepcted.replace(",", "").replace(" ", "");
			Double Pexepcted2 = Double.parseDouble(Pexepcted1);
			System.out.println("Exp. Production Value:" + Pexepcted2);

			Double Expected_result = (Pexepcted2 / 5000) * 0.10;

			Thread.sleep(10000);
			String GridQTY = driver.findElement(By.xpath("//tr/td[contains(text(),'OPTIONS')]//following::td[2]"))
					.getText().trim();
			String GridQTYs = GridQTY.replace("contracts", "").replace(",", "").replace(" ", "");

			System.out.println(GridQTYs);

			Double GridQTYF = Double.parseDouble(GridQTYs);
			System.out.println(GridQTYF);

			if (Expected_result.equals(GridQTYF))

				return Constants.KEYWORD_PASS;

			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_result + "--" + GridQTYF;
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String calcualatePercentage(String object, String data) {
		try {

			Thread.sleep(10000);
			String Qty = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[2]"))
					.getText().trim();
			String Qty1 = Qty.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(Qty1);

			Double a1 = Double.parseDouble(Qty1);
			System.out.println(a1);
			Thread.sleep(10000);

			String AvgPrice = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[3]"))
					.getText().trim();

			String Qty2 = AvgPrice.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");

			Double a2 = Double.parseDouble(Qty2);
			Double Percentage = a1 * a2;
			System.out.println(Percentage);

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String calcualateMinMax(String object, String data) {
		try {

			Thread.sleep(10000);
			String QtyMinMax = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[2]"))
					.getText().trim();
			String QtyMinMax1 = QtyMinMax.replace("bu", "").replace(",", "").replace(" ", "");

			System.out.println(QtyMinMax1);

			Double QtyMinmax = Double.parseDouble(QtyMinMax1);
			System.out.println(QtyMinmax);
			Thread.sleep(10000);

			String MinMaxAvgPrice = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[3]"))
					.getText().trim();

			String MinMaxQty2 = MinMaxAvgPrice.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");

			Double a2 = Double.parseDouble(MinMaxQty2);
			Double Percentage = QtyMinmax * a2;
			System.out.println(Percentage);

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String FinalCashsales1(String object, String data) {
		try {

			Thread.sleep(10000);
			String FinalCashsales = driver
					.findElement(By
							.xpath("html/body/div[2]/div[2]/div[3]/div/div[3]/table/tbody/tr[@id='cashSales']//td[4]"))
					.getText().trim();
			String Finalcashsaless = FinalCashsales.replace("$", "").replace(",", "").replace(" ", "");

			System.out.println(Finalcashsaless);

			Double FiCashsales = Double.parseDouble(Finalcashsaless);
			System.out.println(FiCashsales);

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Allcash(String object, String data) {
		try {

			Thread.sleep(10000);
			String QAccu = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[2]"))
					.getText().trim();
			String QAccus = QAccu.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(QAccus);

			Double AccuQty = Double.parseDouble(QAccus);
			System.out.println(QAccus);
			Thread.sleep(5000);

			String AccuAve = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'ACCUMULATOR')]//following::td[3]"))
					.getText().trim();

			String AccuAves = AccuAve.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");

			Double AccuAver = Double.parseDouble(AccuAves);
			Double FinalAccus = AccuQty * AccuAver;
			System.out.println(FinalAccus);

			Thread.sleep(5000);
			/* Min Max */
			String QMinMax = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[2]"))
					.getText().trim();
			String QMinMaxs = QMinMax.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(QMinMaxs);

			Double MinMaxQty = Double.parseDouble(QMinMaxs);
			System.out.println(MinMaxQty);
			Thread.sleep(5000);

			String MinMaxAvg = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'MIN/MAX')]//following::td[3]"))
					.getText().trim();
			String MinMaxAvgs = MinMaxAvg.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");
			Double MinMaxAvgss = Double.parseDouble(MinMaxAvgs);
			Double finalMinmax = MinMaxQty * MinMaxAvgss;

			/* Percentage */

			String QPer = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[2]"))
					.getText().trim();
			String QPers = QPer.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(QPers);
			Double PerQty = Double.parseDouble(QPers);
			Thread.sleep(5000);

			String PerAvg = driver.findElement(By.xpath(
					"//table[@id='summary_table']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[3]"))
					.getText().trim();
			String PerAvgs = PerAvg.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");
			Double Peravg = Double.parseDouble(MinMaxAvgs);
			Double finalPercentage = PerQty * Peravg;

			/* HTA */

			String QHTA = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'HTA')]//following::td[2]"))
					.getText().trim();
			String QHTAs = QHTA.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(QHTA);
			Double HTAQty = Double.parseDouble(QHTAs);
			Thread.sleep(5000);

			String HTAAvg = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'HTA')]//following::td[3]"))
					.getText().trim();
			String HTAAvgs = HTAAvg.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");
			Double HAverages = Double.parseDouble(HTAAvgs);
			Double finalHTA = HTAQty * HAverages;

			/* Cash */

			String QCash = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'CASH')]//following::td[2]"))
					.getText().trim();
			String QCashs = QCash.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(QCashs);
			Double CashQty = Double.parseDouble(QCashs);
			Thread.sleep(5000);

			String CashAvg = driver
					.findElement(By.xpath(
							"//table[@id='summary_table']//tbody/tr/td[contains(text(),'CASH')]//following::td[3]"))
					.getText().trim();
			String CashAvgs = CashAvg.replace("$", "").replace("/bu", "").replace("bu", "").replace(" ", "");
			Double CashAvgss = Double.parseDouble(CashAvgs);
			Double finalCash = CashQty * CashAvgss;

			Double finalallcashsales = FinalAccus + finalMinmax + finalPercentage + finalHTA + finalCash;

			System.out.println(finalallcashsales);

			if (currentTestSuiteXLS.setCellData1(object, data, currentTestDataSetID, finalallcashsales)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

			return Constants.KEYWORD_PASS;

		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String calculateacre(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='acres']"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("ACRES: ", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String compareunpricecommited(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath(
					"//table[@id='commitedUnpricedTable']//tbody/tr/td[contains(text(),'OPEN BASIS CONTRACTS')]//following::td[2]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String comparemanagepool(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath(
					"//table[@id='commitedUnpricedTable']//tbody/tr/td[contains(text(),'MANAGED POOL')]//following::td[2]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String CompareFuture1(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath("//table[@id='futureOptionOpenTable']//tbody/tr[1]/td[1]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String CompareOption1(String object, String data) {
		try {

			Thread.sleep(10000);
			String actual = driver.findElement(By.xpath(
					"//table[@id='futureOptionOpenTable']//tbody/tr/td[contains(text(),'PUT')]//preceding::td[2]"))
					.getText().trim();
			System.out.println(actual);
			String expected = data;
			String actual1 = actual.replace("bu", "").replace(",", "").replace(" ", "");
			System.out.println(actual1);

			if (actual1.toUpperCase().equals(expected.toUpperCase()))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String o(String object, String data) {
		try {

			Thread.sleep(5000);
			String actual = driver.findElement(By.xpath(
					"//table[@id='cashSalesTable']//tbody/tr/td[contains(text(),'PERCENTAGE')]//following::td[3]"))
					.getText().trim();
			System.out.println(actual);

			String expected = data;
			System.out.println(expected);

			String actual1 = actual.replace("$", "").replace("/bu", "").replace(" ", "");

			String expected2 = expected.replace(".0", "");
			System.out.println(expected2);

			// String actual1=actual.replace("bu", "");

			System.out.println(actual1);

			if ((actual1.equals(expected2)))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual1 + "--" + expected2;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String SelectCounty(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(10000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			Thread.sleep(1000);
			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);
			driver.findElement(By.id((OR.getProperty(object)))).sendKeys(Keys.RETURN);
			Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String expectedproduction1(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver
					.findElement(
							By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_link']//following::td[2]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "");

			System.out.println(actual);

			Double ExpecteProdu = Double.parseDouble(actual1);
			System.out.println(ExpecteProdu);

			Thread.sleep(5000);

			String SeededAcres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Seeded Acres']//following::td[1]"))
					.getText().trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a1 = Double.parseDouble(SeedAcres);

			Thread.sleep(5000);

			String Yieldacres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Yield/Acre ']//following::td[1]"))
					.getText().trim();
			String Yieldacress = Yieldacres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a2 = Double.parseDouble(Yieldacress);

			Double a3 = (a1) * (a2);

			System.out.println(a3);

			if (a3.equals(ExpecteProdu))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a3 + "--" + ExpecteProdu;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String expectedproductionMO(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver
					.findElement(
							By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_link']//following::td[4]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "");

			System.out.println(actual);

			Double ExpecteProdu = Double.parseDouble(actual1);
			System.out.println(ExpecteProdu);

			Thread.sleep(5000);

			String SeededAcres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Seeded Acres']//following::td[3]"))
					.getText().trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a1 = Double.parseDouble(SeedAcres);

			Thread.sleep(5000);

			String Yieldacres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Yield/Acre ']//following::td[3]"))
					.getText().trim();
			String Yieldacress = Yieldacres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a2 = Double.parseDouble(Yieldacress);

			Double a3 = (a1) * (a2);

			System.out.println(a3);

			if (a3.equals(ExpecteProdu))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a3 + "--" + ExpecteProdu;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String OtherrevenueBM(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver
					.findElement(
							By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='otherRevenue']//following::td[1]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(actual);

			Double ExpectedOR = Double.parseDouble(actual1);
			System.out.println(ExpectedOR);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath("html/body/div[2]/div[2]/div/div/div[3]/div/div[2]/div[1]/ul/li[4]/a")).click();

			Thread.sleep(10000);

			String SeededAcres = driver.findElement(By.xpath("//table[@id='example']/tbody/tr/td[5]")).getText().trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			Double a1 = Double.parseDouble(SeedAcres);

			if (a1.equals(ExpectedOR))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a1 + "--" + ExpectedOR;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String CropInsuranceSO(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver
					.findElement(
							By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='cropInsurance']//following::td[1]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(actual);

			Double ExpectedOR = Double.parseDouble(actual1);
			System.out.println(ExpectedOR);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Insurance')]")).click();
			Thread.sleep(10000);

			String SeededAcres = driver.findElement(By.xpath(".//*[@id='policyListGrid']/div/div[2]/div[1]/h4[2]/span"))
					.getText().trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "");

			Double a1 = Double.parseDouble(SeedAcres);

			if (a1.equals(ExpectedOR))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a1 + "--" + ExpectedOR;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String UnpricedgainQty(String object, String data) {
		try {

			Thread.sleep(10000);

			String actual = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[1]/td[3]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(actual);

			Double ExpectedOR = Double.parseDouble(actual1);
			System.out.println(ExpectedOR);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String SeededAcres = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='cashSales']/td[2]")).getText()
					.trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "");

			Double a1 = Double.parseDouble(SeedAcres);

			if (a1.equals(ExpectedOR))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a1 + "--" + ExpectedOR;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String AverageoriceSO(String object, String data) {
		try {

			Thread.sleep(10000);

			String AveragePrice = driver
					.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[3]/td[3]")).getText().trim();
			String Averageprices = AveragePrice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Averageprices);

			Double AP = Double.parseDouble(Averageprices);
			System.out.println(AP);

			String Futureprice = driver
					.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[4]/td[3]")).getText().trim();
			String Futureprices = Futureprice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Futureprices);

			Double FP = Double.parseDouble(Futureprices);
			System.out.println(FP);

			String Basis = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[5]/td[3]"))
					.getText().trim();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Basiss);

			Double BP = Double.parseDouble(Basiss);
			System.out.println(BP);

			String Premium = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[6]/td[3]"))
					.getText().trim();
			String Premiums = Premium.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Premiums);

			Double PR = Double.parseDouble(Premiums);
			System.out.println(PR);

			String Fee = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[7]/td[3]"))
					.getText().trim();
			String Fees = Fee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Fees);

			Double FE = Double.parseDouble(Fees);
			System.out.println(FE);

			Double totalval = (FP + BP + PR - FE);

			if (totalval.equals(AP))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + totalval + "--" + AP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String Basis(String object, String data) {
		try {

			Thread.sleep(10000);

			String Basis = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[5]/td[3]"))
					.getText().trim();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Basiss);

			Double BP = Double.parseDouble(Basiss);
			System.out.println(BP);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String cashsalesQty = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='cashSales']/td[2]")).getText()
					.trim();
			String cashsalesQtys = cashsalesQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "");

			Double CQ = Double.parseDouble(cashsalesQtys);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(10000);

			String AccuQtry = driver.findElement(By.xpath(" //td[text()='Accumulator']//following::td[1]")).getText();
			String AccuQtys = AccuQtry.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(AccuQtys);
			Double ACQ = Double.parseDouble(AccuQtys);
			System.out.println(ACQ);

			String AccuQtryB = driver.findElement(By.xpath(" //td[text()='Accumulator']//following::td[3]")).getText();
			String AccuQtyBs = AccuQtryB.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(AccuQtyBs);
			Double ACQB = Double.parseDouble(AccuQtyBs);
			System.out.println(ACQB);

			String CashQtry = driver.findElement(By.xpath(" //td[text()='Cash']//following::td[1]")).getText();
			String CashQtys = CashQtry.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(CashQtys);
			Double CCQ = Double.parseDouble(CashQtys);
			System.out.println(CCQ);

			String CashQtryB = driver.findElement(By.xpath(" //td[text()='Cash']//following::td[3]")).getText();
			String CashQtyBs = CashQtryB.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(CashQtyBs);
			Double CCQB = Double.parseDouble(CashQtyBs);
			System.out.println(CCQB);

			String HTAQtry = driver.findElement(By.xpath(" //td[text()='HTA']//following::td[1]")).getText();
			String HTAQtys = HTAQtry.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(HTAQtys);
			Double HCQ = Double.parseDouble(HTAQtys);
			System.out.println(HCQ);

			String HTAQtryB = driver.findElement(By.xpath(" //td[text()='HTA']//following::td[3]")).getText();
			String HTAQtyBs = HTAQtryB.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(HTAQtyBs);
			Double HCQB = Double.parseDouble(HTAQtyBs);
			System.out.println(HCQB);

			String MXQtry = driver.findElement(By.xpath(" //td[text()='Min/Max']//following::td[1]")).getText();
			String MXQtys = MXQtry.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MXQtys);
			Double MXCQ = Double.parseDouble(MXQtys);
			System.out.println(MXCQ);

			String MXQtryB = driver.findElement(By.xpath(" //td[text()='Min/Max']//following::td[3]")).getText();
			String MXQtyBs = MXQtryB.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MXQtyBs);
			Double MXCQB = Double.parseDouble(MXQtyBs);
			System.out.println(MXCQB);

			String Pertry = driver.findElement(By.xpath(" //td[text()='Percentage']//following::td[1]")).getText();
			String Pertrys = Pertry.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(Pertrys);
			Double PrXCQ = Double.parseDouble(Pertrys);
			System.out.println(PrXCQ);

			String PerQtryB = driver.findElement(By.xpath(" //td[text()='Percentage']//following::td[3]")).getText();
			String PerQtyBs = PerQtryB.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(PerQtyBs);
			Double PerCQB = Double.parseDouble(PerQtyBs);
			System.out.println(PerCQB);

			Double actual_result = (ACQ * ACQB) / CQ + (CCQ * CCQB) / CQ + (HCQ * HCQB) / CQ + (MXCQ * MXCQB) / CQ
					+ (PrXCQ * PerCQB) / CQ;

			df2.setRoundingMode(RoundingMode.HALF_UP);

			Double ans = Double.valueOf(df2.format(actual_result.doubleValue()));
			System.out.println(ans);

			if (ans.equals(BP))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual_result + "--" + BP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String Perexpected(String object, String data) {
		try {

			Thread.sleep(10000);

			String ExpectedP = driver.findElement(By.xpath(".//*[@id='expected']//following::td[1]")).getText().trim();
			String ExpectedPs = ExpectedP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(ExpectedPs);

			Double EP = Double.parseDouble(ExpectedPs);
			System.out.println(EP);

			driver.findElement(By.id("priceGrain_Link")).click();
			Thread.sleep(10000);

			String Perexpected = driver
					.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[2]/td[3]")).getText().trim();
			String Perexpecteds = Perexpected.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "").replace("%", "");

			Double PE = Double.parseDouble(Perexpecteds);
			System.out.println(PE);

			Thread.sleep(5000);

			String Qunatity = driver.findElement(By.xpath(".//*[@id='priceGrain_block']/div/table/tbody/tr[1]/td[3]"))
					.getText().trim();
			String Qunatitys = Qunatity.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "").replace("%", "");

			Double QTY = Double.parseDouble(Qunatitys);
			System.out.println(QTY);

			Double Actual_Result = (QTY * 100) / EP;

			Double ans = Double.valueOf(df2.format(Actual_Result.doubleValue()));

			if (ans.equals(PE))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + PE;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String NGL(String object, String data) {
		try {

			Thread.sleep(10000);

			String GR = driver.findElement(By.xpath(".//td[@id='grossRevenue']//following::td[1]")).getText().trim();
			String GRs = GR.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(GRs);
			Double GrossRevenue = Double.parseDouble(GRs);
			System.out.println(GrossRevenue);

			Boolean isNegative = false;
			String EP = driver.findElement(By.xpath(".//*[@id='expense']//following::td[1]")).getText().trim();

			String EPs = EP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(EPs);
			Double Expense = Double.parseDouble(EPs);
			System.out.println(Expense);

			Double expected_result = (GrossRevenue - Expense);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			String NGL = driver.findElement(By.xpath("//td[@id='netGainLoss']//following::td[1]")).getText().trim();

			if (NGL.contains("(")) {
				isNegative = true;

				NGL = NGL.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace(",", "");

			}

			else

			{
				NGL = NGL.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace(",", "");

			}

			Double AccumalatorAVGb = Double.parseDouble(NGL);
			if (isNegative) {
				AccumalatorAVGb = -AccumalatorAVGb;
			}
			System.out.println(AccumalatorAVGb);

			if (AccumalatorAVGb.equals(ans))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + AccumalatorAVGb;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Expense(String object, String data) {
		try {

			String EP = driver.findElement(By.xpath(".//*[@id='expense']//following::td[1]")).getText().trim();

			Thread.sleep(10000);
			String EPs = EP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(EPs);
			Double Expense = Double.parseDouble(EPs);
			System.out.println(Expense);

			driver.findElement(By.id("landAndCrops")).click();

			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Crops')]")).click();
			Thread.sleep(10000);

			String Crops = driver.findElement(By.xpath(".//*[@id='cropListGrid']/div/div[2]/div[1]/span[1]")).getText();

			System.out.println(Crops);

			Double CP = Double.parseDouble(Crops);

			String Acres = driver.findElement(By.xpath(".//*[@id='cropListGrid']/div/div[2]/div[1]/h4/span")).getText();

			Double ACR = Double.parseDouble(Acres);

			System.out.println(ACR);

			Double Actual_Result = (CP) * (ACR);

			if (Actual_Result.equals(Expense))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expense + "--" + Actual_Result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String UnpricedQty(String object, String data) {
		try {

			Thread.sleep(5000);

			driver.findElement(By.xpath(".//*[@id='unpricedGrain_Link']")).click();

			String Quantity = driver.findElement(By.xpath("//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quanltys = Quantity.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quanltys);
			Double QTY = Double.parseDouble(Quanltys);
			System.out.println(Quanltys);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String Unsoldprodq = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[contains(text(),'Committed & Unpriced')]//following::td[1]"))
					.getText();

			String Unsoldprodqs = Unsoldprodq.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Unsoldprodqs);
			Double UnQTY = Double.parseDouble(Unsoldprodqs);
			System.out.println(UnQTY);

			String CashSale = driver
					.findElement(By.xpath(
							".//*[@id='summary_table']/tbody/tr/td[contains(text(),'Cash Sales')]//following::td[1]"))
					.getText();

			String CashSales = CashSale.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Unsoldprodqs);
			Double CS = Double.parseDouble(CashSales);
			System.out.println(CS);

			String EP = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText();

			String EPs = EP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(EPs);
			Double EPro = Double.parseDouble(EPs);
			System.out.println(EPro);

			Double Expected_result = (EPro - CS - UnQTY);

			if (Expected_result.equals(QTY))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_result + "--" + QTY;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Unpricedperexpected(String object, String data) {
		try {

			Thread.sleep(10000);
			driver.findElement(By.xpath(".//*[@id='unpricedGrain_Link']")).click();

			String Perofexpected = driver
					.findElement(By.xpath("//td[contains(text(),' % Of Expected')]//following::td[1]")).getText();
			String Perofexpecteds = Perofexpected.replace("%", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Perofexpecteds);
			Double PER = Double.parseDouble(Perofexpecteds);
			System.out.println(PER);

			String Quantity = driver.findElement(By.xpath("//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quanltys = Quantity.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quanltys);
			Double QTY = Double.parseDouble(Quanltys);
			System.out.println(Quanltys);

			Thread.sleep(10000);

			String EP = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText();
			String EPs = EP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(EPs);
			Double EPr = Double.parseDouble(EPs);
			System.out.println(EPs);

			Double Expected_result = (QTY * 100) / EPr;

			Double ans = Double.valueOf(df2.format(Expected_result.doubleValue()));

			if (ans.equals(PER))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_result + "--" + PER;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Unpricedavgprice(String object, String data) {
		try {

			String AveragePrice = driver
					.findElement(By.xpath("//td[contains(text(),'Average Price')]//following::td[1]")).getText();
			String AveragePrices = AveragePrice.replace("%", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(AveragePrice);
			Double AP = Double.parseDouble(AveragePrices);
			System.out.println(AP);

			String Futureprice = driver.findElement(By.xpath("//td[contains(text(),'Future Price')]//following::td[1]"))
					.getText();
			String Futureprices = Futureprice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Futureprices);
			Double FP = Double.parseDouble(Futureprices);
			System.out.println(Futureprices);

			String Basis = driver.findElement(By.xpath("//td[contains(text(),'Basis')]//following::td[1]")).getText();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Basiss);
			Double BS = Double.parseDouble(Basiss);
			System.out.println(BS);

			String Premium = driver.findElement(By.xpath("//td[contains(text(),'Premium')]//following::td[1]"))
					.getText();
			String Premiums = Premium.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Premiums);
			Double PR = Double.parseDouble(Premiums);
			System.out.println(PR);

			Double Expected_result = (FP + PR + BS);

			Double ans = Double.valueOf(df2.format(Expected_result.doubleValue()));

			if (ans.equals(AP))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + Expected_result + "--" + AP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String QtytotalGrainSO(String object, String data) {
		try {

			driver.findElement(By.id("unpricedGrain_Link")).click();
			Thread.sleep(10000);

			String Quantity = driver.findElement(By.xpath("//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quantitys = Quantity.replace("%", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quantitys);
			Double QT = Double.parseDouble(Quantitys);
			System.out.println(QT);
			driver.findElement(By.id("unpricedGrain_Link")).click();
			Thread.sleep(10000);

			driver.findElement(By.id("basisContract_Link")).click();
			Thread.sleep(10000);
			String Quan = driver
					.findElement(By.xpath(
							".//*[@id='basisContract_block']//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quans = Quan.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quans);
			Double QTYs = Double.parseDouble(Quans);
			System.out.println(QTYs);
			driver.findElement(By.id("basisContract_Link")).click();
			Thread.sleep(10000);

			driver.findElement(By.id("priceGrain_Link")).click();
			Thread.sleep(10000);
			String Quan1 = driver
					.findElement(
							By.xpath(".//*[@id='priceGrain_block']//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quan1s = Quan1.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quan1s);
			Double QTY1s = Double.parseDouble(Quan1s);
			System.out.println(QTY1s);
			driver.findElement(By.id("priceGrain_Link")).click();
			Thread.sleep(10000);

			Double expected_result = (QT + QTYs + QTY1s);

			driver.findElement(By.id("totalGrain_Link")).click();
			Thread.sleep(10000);
			String Quan11 = driver
					.findElement(
							By.xpath(".//*[@id='totalGrain_block']//td[contains(text(),'Quantity')]//following::td[1]"))
					.getText();
			String Quan11s = Quan11.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quan11s);
			Double QTY11s = Double.parseDouble(Quan11s);
			System.out.println(QTYs);
			driver.findElement(By.id("totalGrain_Link")).click();
			Thread.sleep(10000);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			if (ans.equals(QTY11s))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + expected_result + "--" + QTY11s;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Averagepricetotalgrain(String object, String data) {
		try {

			driver.findElement(By.id("totalGrain_Link")).click();
			Thread.sleep(10000);

			String AvgPrice = driver
					.findElement(By.xpath(
							".//*[@id='totalGrain_block']//td[contains(text(),'Average Price')]//following::td[1]"))
					.getText();
			String AvgPrices = AvgPrice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(AvgPrices);
			Double AG = Double.parseDouble(AvgPrices);
			System.out.println(AG);

			String FP = driver
					.findElement(By.xpath(
							".//*[@id='totalGrain_block']//td[contains(text(),'Future Price')]//following::td[1]"))
					.getText();
			String FPs = FP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(FPs);
			Double FPFinal = Double.parseDouble(FPs);
			System.out.println(FPFinal);

			String Basis = driver
					.findElement(
							By.xpath(".//*[@id='totalGrain_block']//td[contains(text(),'Basis')]//following::td[1]"))
					.getText();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Basis);
			Double BS = Double.parseDouble(Basiss);
			System.out.println(BS);

			String Premium = driver
					.findElement(
							By.xpath(".//*[@id='totalGrain_block']//td[contains(text(),'Premium')]//following::td[1]"))
					.getText();
			String Premiums = Premium.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Premiums);
			Double PR = Double.parseDouble(Premiums);
			System.out.println(PR);

			String Fee = driver
					.findElement(
							By.xpath(".//*[@id='totalGrain_block']//td[contains(text(),'Fees')]//following::td[1]"))
					.getText();
			String Fees = Fee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Fees);
			Double FE = Double.parseDouble(Fees);
			System.out.println(FE);

			Double expected_result = (FPFinal + BS + PR - FE);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			driver.findElement(By.id("totalGrain_Link")).click();

			if (ans.equals(AG))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + expected_result + "--" + AG;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Totalgrossexpectedproduction(String object, String data) {
		try {

			driver.findElement(By.id("unpricedGrain_Link")).click();
			Thread.sleep(10000);

			String Quantity = driver.findElement(By.xpath("//td[contains(text(),' % Of Expected')]//following::td[1]"))
					.getText();
			String Quantitys = Quantity.replace("%", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Quantitys);
			Double QT = Double.parseDouble(Quantitys);
			System.out.println(QT);
			driver.findElement(By.id("unpricedGrain_Link")).click();
			Thread.sleep(10000);

			driver.findElement(By.id("basisContract_Link")).click();
			Thread.sleep(10000);
			String Quan = driver
					.findElement(By.xpath(
							".//*[@id='basisContract_block']//td[contains(text(),'% Of Expected')]//following::td[1]"))
					.getText();
			String Quans = Quan.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("%",
					"");
			System.out.println(Quans);
			Double QTYs = Double.parseDouble(Quans);
			System.out.println(QTYs);
			driver.findElement(By.id("basisContract_Link")).click();
			Thread.sleep(10000);

			driver.findElement(By.id("priceGrain_Link")).click();
			Thread.sleep(10000);
			String Quan1 = driver
					.findElement(By.xpath(
							".//*[@id='priceGrain_block']//td[contains(text(),'% Of Expected')]//following::td[1]"))
					.getText();
			String Quan1s = Quan1.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("%",
					"");
			System.out.println(Quan1s);
			Double QTY1s = Double.parseDouble(Quan1s);
			System.out.println(QTY1s);
			driver.findElement(By.id("priceGrain_Link")).click();
			Thread.sleep(10000);

			Double expected_result = (QT + QTYs + QTY1s);
			System.out.println(expected_result);

			driver.findElement(By.id("totalGrain_Link")).click();
			Thread.sleep(10000);
			String Quan11 = driver
					.findElement(By.xpath(
							".//*[@id='totalGrain_block']//td[contains(text(),'% Of Expected')]//following::td[1]"))
					.getText();
			String Quan11s = Quan11.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("%",
					"");
			System.out.println(Quan11s);
			Double QTY11s = Double.parseDouble(Quan11s);
			System.out.println(QTYs);
			driver.findElement(By.id("totalGrain_Link")).click();
			Thread.sleep(10000);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			if (ans.equals(QTY11s))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + expected_result + "--" + QTY11s;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String TotalHedgePerFuture(String object, String data) {
		try {
			Thread.sleep(10000);
			driver.findElement(By.id("totalHedge_Link")).click();
			Thread.sleep(10000);

			Boolean isNegative = false;

			String Future = driver
					.findElement(
							By.xpath(".//*[@id='totalHedge_block']//td[contains(text(),'Future')]//following::td[1]"))
					.getText().trim();

			if (Future.contains("(")) {
				isNegative = true;

				Future = Future.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace(",", "").replace("%", "");

			}

			else

			{
				Future = Future.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace(",", "").replace("%", "");

			}

			Double FU = Double.parseDouble(Future);
			if (isNegative) {
				FU = -FU;
			}
			System.out.println(FU);

			String CGC = driver.findElement(By.xpath(
					".//*[@id='totalHedge_block']//td[contains(text(),'Cash Grain Contracts')]//following::td[1]"))
					.getText();
			String CGCS = CGC.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("%", "");
			System.out.println(CGCS);
			Double CG = Double.parseDouble(CGCS);
			System.out.println(CG);

			Double expected_result = (CG + FU);
			System.out.println(expected_result);

			String totalHedge = driver.findElement(By.xpath(".//*[@id='totalHedge']/td[3]")).getText();

			if (totalHedge.contains("(")) {
				isNegative = true;

				totalHedge = totalHedge.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "").replace(",", "").replace("%", "").replace("%", "");

			}

			else

			{
				totalHedge = totalHedge.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "").replace(",", "").replace("%", "");

			}

			Double TH = Double.parseDouble(totalHedge);
			if (isNegative) {
				TH = -TH;
			}
			System.out.println(TH);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			driver.findElement(By.id("totalHedge_Link")).click();

			if (ans.equals(TH))

				return Constants.KEYWORD_PASS;

			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + expected_result + "--" + TH;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String TotalGrainFeesSO(String object, String data) {
		try {

			Thread.sleep(10000);

			driver.findElement(By.id("totalGrain_Link")).click();

			Thread.sleep(10000);

			String Basis = driver
					.findElement(
							By.xpath(".//*[@id='totalGrain_block']//td[contains(text(),'Fees')]//following::td[1]"))
					.getText().trim();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");

			System.out.println(Basiss);

			Double BP = Double.parseDouble(Basiss);
			System.out.println(BP);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String cashsalesQty = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='cashSales']/td[2]")).getText()
					.trim();
			String cashsalesQtys = cashsalesQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace(".00", "");

			Double CQ = Double.parseDouble(cashsalesQtys);

			String cashsalesQty1 = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='commitedUnpriced']/td[2]"))
					.getText().trim();
			String cashsalesQtys1 = cashsalesQty1.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replace(".00", "");

			Double CQ1 = Double.parseDouble(cashsalesQtys1);

			String acre = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText().trim();
			String acres = acre.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "").replace(")", "")
					.replace(",", "");
			Double ACRE = Double.parseDouble(acres);
			System.out.println(ACRE);
			Thread.sleep(5000);

			String Unsold = driver.findElement(By.xpath("//td[text()='Unsold Production']//following-sibling::td[1]"))
					.getText().trim();
			String Unsolds = Unsold.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "")
					.replace(")", "").replace(",", "");
			Double CQ4 = Double.parseDouble(Unsolds);
			System.out.println(CQ4);
			Thread.sleep(5000);

			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//td[text()='Accumulator']//preceding-sibling::td[1]")).click();

			Thread.sleep(10000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String AccFee = js.executeScript("return $('#truckingFees').val()", "").toString();
			System.out.println(AccFee);

			System.out.println(AccFee);
			String AccFees = AccFee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(AccFees);
			Double ACF = Double.parseDouble(AccFees);
			System.out.println(ACF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);
			System.out.println(ACF);

			driver.findElement(By.xpath("//td[text()='Basis']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			String BasisF = js1.executeScript("return $('#truckingFees').val()", "").toString();
			System.out.println(BasisF);

			String BasisFs = BasisF.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(BasisFs);
			Double BasisFE = Double.parseDouble(BasisFs);
			System.out.println(BasisFE);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("//td[text()='Cash']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);

			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			String CashSalesF = js2.executeScript("return $('#truckingFees').val()", "").toString();
			System.out.println(CashSalesF);
			String CashSalesFs = CashSalesF.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(CashSalesFs);
			Double CSF = Double.parseDouble(CashSalesFs);
			System.out.println(CSF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("//td[text()='HTA']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);
			JavascriptExecutor js3 = (JavascriptExecutor) driver;
			String HTAFes = js.executeScript("return $('#truckingFees').val()", "").toString();
			System.out.println(CashSalesF);
			String HTAFess = HTAFes.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(HTAFess);
			Double HTAF = Double.parseDouble(HTAFess);
			System.out.println(HTAF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("//td[text()='MBP']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);

			JavascriptExecutor js4 = (JavascriptExecutor) driver;
			String MBPFee = js.executeScript("return $('#truckingFees').val()", "").toString();

			String MBPFees = MBPFee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MBPFees);
			Double MBPF = Double.parseDouble(MBPFees);
			System.out.println(MBPF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("//td[text()='Min/Max']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String MinMaxfee = js.executeScript("return $('#truckingFees').val()", "").toString();
			String MinMaxfees = MinMaxfee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MinMaxfees);
			Double MinMaxF = Double.parseDouble(MinMaxfees);
			System.out.println(MinMaxF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("//td[text()='Percentage']//preceding-sibling::td[1]")).click();
			Thread.sleep(10000);
			JavascriptExecutor js6 = (JavascriptExecutor) driver;
			String PercentageFee = js.executeScript("return $('#truckingFees').val()", "").toString();
			String PercentageFees = PercentageFee.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replaceAll("bu", "");
			System.out.println(PercentageFee);
			Double PERF = Double.parseDouble(PercentageFee);
			System.out.println(PERF);
			driver.findElement(By.xpath("//label[text()='CANCEL']")).click();
			Thread.sleep(5000);

			String AccuQty = driver.findElement(By.xpath("//td[text()='Accumulator']//following-sibling::td[1]"))
					.getText();
			Thread.sleep(10000);
			System.out.println(AccuQty);
			String QTyF = AccuQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(QTyF);
			Double AccQty = Double.parseDouble(QTyF);
			System.out.println(AccQty);

			String BasisQty = driver.findElement(By.xpath("//td[text()='Basis']//following-sibling::td[1]")).getText();
			Thread.sleep(10000);
			System.out.println(BasisQty);
			String BasisF1 = BasisQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(BasisF1);
			Double BasisQty1 = Double.parseDouble(BasisF1);
			System.out.println(BasisQty1);

			String CashQty = driver.findElement(By.xpath("//td[text()='Cash']//following-sibling::td[1]")).getText();
			Thread.sleep(10000);
			System.out.println(CashQty);
			String CashQtys = CashQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(CashQtys);
			Double CashQTY = Double.parseDouble(CashQtys);
			System.out.println(CashQTY);

			String HTAQty = driver.findElement(By.xpath("//td[text()='HTA']//following-sibling::td[1]")).getText();
			Thread.sleep(10000);
			System.out.println(HTAQty);
			String HTAQtys = CashQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(HTAQtys);
			Double HTAQTY = Double.parseDouble(HTAQtys);
			System.out.println(HTAQTY);

			String MBPQty = driver.findElement(By.xpath("//td[text()='MBP']//following-sibling::td[1]")).getText();
			Thread.sleep(10000);
			System.out.println(MBPQty);
			String MBPQtys = CashQty.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MBPQtys);
			Double MBPQTY = Double.parseDouble(MBPQtys);
			System.out.println(MBPQTY);

			String MINQts = driver.findElement(By.xpath("//td[text()='Min/Max']//following-sibling::td[1]")).getText();
			Thread.sleep(10000);
			System.out.println(MINQts);
			String MINQtss = MINQts.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(MINQts);
			Double MINQTY = Double.parseDouble(MINQtss);
			System.out.println(MINQTY);

			String PerQtys = driver.findElement(By.xpath("//td[text()='Percentage']//following-sibling::td[1]"))
					.getText();
			Thread.sleep(10000);
			System.out.println(PerQtys);
			String PerQtyss = PerQtys.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replaceAll("bu", "");
			System.out.println(PerQtyss);
			Double PERQTY = Double.parseDouble(PerQtyss);
			System.out.println(PERQTY);

			Double expected_resutlt = (BasisFE * BasisQty1) / CQ1 + (MBPF * MBPQTY) / CQ1;
			Double expected_result1 = (ACF * AccQty) / CQ + (CSF * CashQTY) / CQ + (HTAQTY * HTAF) / CQ
					+ (MINQTY * MinMaxF) / CQ + (PERQTY * PERF) / CQ;
			Double exppected_result2 = CQ4 * 0;

			Double expected_fees = (expected_resutlt * CQ1) / ACRE + (CQ * expected_result1) / ACRE
					+ (exppected_result2 * CQ4 / ACRE);

			Double ans = Double.valueOf(df2.format(expected_fees.doubleValue()));

			System.out.println(ans);

			if (ans.equals(BP))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + BP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String FutureoptionSO(String object, String data) {
		try {

			String FO = driver.findElement(By.xpath(".//*[@id='future_detail']//following::td[1]")).getText().trim();
			String FOS = FO.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "");
			System.out.println(FOS);
			Double FutureOption = Double.parseDouble(FOS);
			System.out.println(FutureOption);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);
			String FOA = driver.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='futures']/td[4]"))
					.getText().trim();
			String FOAs = FOA.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "");
			System.out.println(FOAs);
			Double FutureOptionA = Double.parseDouble(FOS);
			System.out.println(FutureOptionA);

			Thread.sleep(10000);

			if (FutureOption.equals(FutureOptionA))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + FutureOption + "--" + FutureOptionA;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String FutureoptionSOContract(String object, String data) {
		try {

			Thread.sleep(10000);

			driver.findElement(By.id("futures_Link")).click();

			Thread.sleep(10000);

			String Contract = driver.findElement(By.xpath("//td[text()='# of contracts']//following-sibling::td[1]"))
					.getText().trim();
			String Contracts = Contract.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "");
			System.out.println(Contracts);
			Double CT = Double.parseDouble(Contracts);
			System.out.println(CT);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String cashsalesQty1 = driver
					.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='commitedUnpriced']/td[2]"))
					.getText().trim();
			String cashsalesQtys1 = cashsalesQty1.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replace(".00", "");

			Double CQ1 = Double.parseDouble(cashsalesQtys1);

			if (CT.equals(CQ1))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + CT + "--" + CQ1;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String FutureoptionSOPL(String object, String data) {
		try {

			Thread.sleep(10000);
			driver.findElement(By.id("futures_Link")).click();

			Thread.sleep(10000);

			String ProfiltLoss = driver.findElement(By.xpath("//td[text()='P/L Per Unit ']//following-sibling::td[1]"))
					.getText().trim();
			String ProfiltLosss = ProfiltLoss.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "");
			System.out.println(ProfiltLosss);
			Double PL = Double.parseDouble(ProfiltLosss);
			System.out.println(PL);

			driver.findElement(By.xpath("//a[contains(text(),'Account Summary')]")).click();
			Thread.sleep(10000);

			String FOA = driver.findElement(By.xpath("//table[@id='summary_table']/tbody/tr[@id='futures']/td[4]"))
					.getText().trim();
			String FOAs = FOA.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "");
			System.out.println(FOAs);
			Double FutureOptionA = Double.parseDouble(FOAs);
			System.out.println(FutureOptionA);

			String ExpectedProduction = driver
					.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/label[@id='expectedProduction']/span"))
					.getText().trim();
			String ExpectedProductions = ExpectedProduction.replace("/bu", "").replace("$", "").replace(" ", "")
					.replace("(", "").replace(")", "").replace(",", "");
			Double EP = Double.parseDouble(ExpectedProductions);
			System.out.println(EP);
			Thread.sleep(5000);

			Double Expected_result = (FutureOptionA / EP);

			Double ans = Double.valueOf(df2.format(Expected_result.doubleValue()));

			if (ans.equals(PL))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + PL;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String FutureoptionsCommissionsandfeeso(String object, String data) {
		try {

			Thread.sleep(10000);
			driver.findElement(By.id("futures_Link")).click();

			Thread.sleep(10000);

			String CommissionFe = driver
					.findElement(By.xpath("//td[text()='Commissions & Fees/contract']//following-sibling::td[1]"))
					.getText().trim();
			String CommissionFes = CommissionFe.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "");
			System.out.println(CommissionFes);
			Double CF = Double.parseDouble(CommissionFes);
			System.out.println(CF);

			Thread.sleep(10000);
			driver.findElement(By.id("landAndCrops")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
			Thread.sleep(10000);

			driver.findElement(By.xpath(".//*[@id='FOLi']")).click();
			Thread.sleep(10000);

			String FutureOp = driver.findElement(By.xpath(".//*[@id='example']/tbody/tr[1]/td[6]")).getText();
			String FutureOps = FutureOp.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "");
			System.out.println(FutureOps);
			Double FOV = Double.parseDouble(FutureOps);
			System.out.println(FOV);

			String OptionOp = driver.findElement(By.xpath(".//*[@id='example']/tbody/tr[2]/td[6]")).getText();
			String OptionOps = OptionOp.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "");
			System.out.println(OptionOps);
			Double OOV = Double.parseDouble(OptionOps);
			System.out.println(OOV);

			Double final_Qty = (OOV + FOV);

			driver.findElement(By.id("MyAccountDropdownTab")).click();
			Thread.sleep(5000);
			driver.findElement(By.id("admin")).click();
			Thread.sleep(10000);
			driver.findElement(By.id("searchUser")).sendKeys("benchmarking1");
			driver.findElement(By.id("searchUserBtn")).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(".//*[@id='userList']/div/div[1]/div[2]/a")).click();
			Thread.sleep(10000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String FO = js.executeScript("return $('#futures').val()", "").toString();
			System.out.println(FO);

			String FOS = FO.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "");
			System.out.println(FOS);
			Double Futureop = Double.parseDouble(FOS);
			System.out.println(Futureop);

			Thread.sleep(5000);

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			String OP = js.executeScript("return $('#options').val()", "").toString();
			System.out.println(OP);

			String OPs = OP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "");
			System.out.println(OPs);
			Double Options = Double.parseDouble(OPs);
			System.out.println(Options);

			Double expected_result = (FOV * Futureop) / final_Qty + (OOV * Options) / final_Qty;

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			if (ans.equals(CF))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + CF;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String TotalHedged(String object, String data) {
		try {

			Thread.sleep(10000);
			driver.findElement(By.id("totalHedge_Link")).click();

			Thread.sleep(10000);

			String Cashgraincontract = driver
					.findElement(By.xpath("//td[text()='Cash Grain Contracts ']//following-sibling::td[1]")).getText()
					.trim();
			String Cashgraincontracts = Cashgraincontract.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replace("(", "").replace(")", "").replace("%", "");
			System.out.println(Cashgraincontracts);
			Double CG = Double.parseDouble(Cashgraincontracts);
			System.out.println(CG);

			boolean isNegative = false;
			String Future = driver.findElement(By.xpath("//td[text()='Futures ']//following-sibling::td[1]")).getText()
					.trim();
			String futuress = "";
			if (Future.contains("(")) {
				isNegative = true;

				futuress = Future.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "").replace(",", "");

			}

			else

			{
				futuress = Future.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "");

			}
			Double FO = Double.parseDouble(futuress);
			if (isNegative) {
				FO = -FO;
			}
			System.out.println(FO);

			String Options = driver.findElement(By.xpath("//td[text()='Options ']//following-sibling::td[1]")).getText()
					.trim();
			String Optionss = Options.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace(",", "").replace("%", "");
			System.out.println(Optionss);
			Double OP = Double.parseDouble(Optionss);
			System.out.println(OP);

			boolean isNegative1 = false;
			String totalHedge = driver.findElement(By.xpath(".//*[@id='totalHedge']//td[3]")).getText().trim();

			String totalH = "";
			if (totalHedge.contains("(")) {
				isNegative1 = true;

				totalH = totalHedge.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "").replace("%", "").replace(",", "");

			}

			else

			{
				totalH = totalHedge.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
						.replace(")", "").replace("%", "");

			}
			Double TH = Double.parseDouble(totalH);
			if (isNegative) {
				TH = -TH;
			}
			System.out.println(TH);

			Double Expected_result = (CG + FO + OP);

			if (Expected_result.equals(TH))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + TH + "--" + Expected_result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String TotalHedgeFuture(String object, String data) {
		try {

			Thread.sleep(10000);

			String Future = driver.findElement(By.xpath("//td[text()='Futures ']//following-sibling::td[1]")).getText()
					.trim();
			String Futures = Future.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");
			System.out.println(Futures);
			Double FU = Double.parseDouble(Futures);
			System.out.println(FU);

			driver.findElement(By.id("totalHedge_Link")).click();

			Thread.sleep(10000);

			driver.findElement(By.xpath(".//*[@id='future_detail']")).click();

			Thread.sleep(10000);

			String popupfo = driver.findElement(By.xpath("//td[text()='% Delta ']//following-sibling::td[1]"))
					.getAttribute("innerText");
			String popupfos = popupfo.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");
			System.out.println(popupfos);
			Double FOP = Double.parseDouble(popupfos);
			System.out.println(FOP);

			if (FOP.equals(FU))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + FOP + "--" + FU;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String TotalHedgeOption(String object, String data) {
		try {

			Thread.sleep(10000);
			driver.findElement(By.id("totalHedge_Link")).click();

			Thread.sleep(10000);

			String Options = driver.findElement(By.xpath("//td[text()='Options ']//following-sibling::td[1]")).getText()
					.trim();
			String Optionss = Options.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");
			System.out.println(Optionss);
			Double OP = Double.parseDouble(Optionss);
			System.out.println(OP);

			driver.findElement(By.id("totalHedge_Link")).click();

			Thread.sleep(5000);

			driver.findElement(By.xpath(".//*[@id='future_detail']")).click();

			Thread.sleep(10000);

			driver.findElement(By.xpath(".//*[@id='b']/a")).click();
			Thread.sleep(5000);

			String popupfo = driver.findElement(By.xpath("//div[@id='three']//div/div/table/tbody/tr[1]/td[2]"))
					.getAttribute("innerText");
			String popupfos = popupfo.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");
			System.out.println(popupfo);
			Double FOP = Double.parseDouble(popupfos);
			System.out.println(FOP);

			if (FOP.equals(OP))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + FOP + "--" + OP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String NetGainlossSO(String object, String data) {
		try {

			Thread.sleep(10000);

			String Grossrevenue = driver.findElement(By.xpath(".//*[@id='grossRevenue']//td[3]")).getText().trim();
			String Grossrevenues = Grossrevenue.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");
			System.out.println(Grossrevenues);
			Double GR = Double.parseDouble(Grossrevenues);
			System.out.println(GR);

			Thread.sleep(5000);

			boolean isNegative = false;
			String Expenses = "";

			String Expense = driver.findElement(By.xpath(".//*[@id='expense']//following-sibling::td[1]")).getText()
					.trim();
			{
				if (Expense.contains("(")) {
					isNegative = true;

					Expense = Expense.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
							.replace(")", "").replace("%", "").replace(",", "");

				}

				else

				{
					Expense = Expense.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
							.replace(")", "").replace("%", "").replace(",", "");

				}

				Double TE = Double.parseDouble(Expense);
				if (isNegative) {
					TE = -TE;
				}
				System.out.println(TE);

				boolean isNegative1 = false;
				String Netgainloss = "";

				String Netgainloss1 = driver.findElement(By.xpath(".//*[@id='netGainLoss']//td[3]")).getText().trim();

				{
					if (Netgainloss1.contains("(")) {
						isNegative1 = true;

						Netgainloss = Netgainloss1.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
								.replace(")", "").replace("%", "").replace(",", "");

					}

					else

					{
						Netgainloss = Netgainloss.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
								.replace(")", "").replace("%", "").replace(",", "");

					}

					Double NGL = Double.parseDouble(Netgainloss);
					if (isNegative1) {
						NGL = -NGL;
					}
					System.out.println(NGL);

					Double expected_result = (GR - TE);

					Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

					if (NGL.equals(ans))

						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + NGL;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String AddValuesExcel(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(OR.getProperty(object))).getText();

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String AddValuesExcelMO(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(".//*[@id='expectedProduction']/span")).getText().trim();

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String BenchMarkingMOExpectedproduction(String object, String data) {
		try {

			Thread.sleep(10000);

			String EP1 = driver.findElement(By.xpath(".//*[@id='expectedProduction']/span")).getText().trim();
			String EPS = EP1.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "").replace("%", "");
			System.out.println(EPS);
			Double EP = Double.parseDouble(EPS);
			System.out.println(EP);

			Thread.sleep(5000);

			driver.findElement(By.id("allOrgList")).clear();
			driver.findElement(By.id("allOrgList")).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.id("allOrgList")).sendKeys("benchmarkingMO");
			Thread.sleep(5000);
			driver.findElement(By.id("allOrgList")).sendKeys(Keys.DOWN);
			Thread.sleep(1000);
			driver.findElement(By.id("allOrgList")).sendKeys(Keys.TAB);
			Thread.sleep(5000);

			driver.findElement(By.id("allOrgList")).sendKeys(Keys.ENTER);

			Thread.sleep(1000);

			driver.findElement(By.id("allOrgList")).sendKeys(Keys.RETURN);
			Thread.sleep(1500);

			String EP2 = driver.findElement(By.xpath(".//*[@id='expectedProduction']/span")).getText().trim();
			String EPS1 = EP2.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "").replace("(", "")
					.replace(")", "").replace("%", "");
			System.out.println(EPS1);
			Double EPM = Double.parseDouble(EPS1);
			System.out.println(EPM);

			Double expected_reult = (EP + EPM) / 2;

			driver.findElement(By.xpath("//a[contains(text(),'Benchmarking')]")).click();
			Thread.sleep(15000);

			String Actual_Result = driver.findElement(By.xpath("//*[@id='expected']//following::td[3]")).getText();
			String Actual_Results = Actual_Result.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replace("(", "").replace(")", "").replace("%", "");
			System.out.println(Actual_Results);
			Double AR = Double.parseDouble(Actual_Results);
			System.out.println(AR);

			if (AR.equals(EP))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + AR + "--" + EP;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;

		}
		return result;
	}

	public String expectedproductionMOUser(String object, String data) {
		try {

			driver.findElement(By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_link']")).click();
			Thread.sleep(10000);

			String actual = driver
					.findElement(
							By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_link']//following::td[4]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "");

			System.out.println(actual);

			Double ExpecteProdu = Double.parseDouble(actual1);
			System.out.println(ExpecteProdu);

			Thread.sleep(5000);

			String SeededAcres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Seeded Acres']//following::td[3]"))
					.getText().trim();
			String SeedAcres = SeededAcres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a1 = Double.parseDouble(SeedAcres);

			Thread.sleep(5000);

			String Yieldacres = driver.findElement(By.xpath(
					".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_block']//table/tbody/tr/td[text()='Yield/Acre ']//following::td[3]"))
					.getText().trim();
			String Yieldacress = Yieldacres.replace("bu/ac", "").replace(",", "").replace(" ", "");

			Double a2 = Double.parseDouble(Yieldacress);

			Double a3 = (a1) * (a2);

			driver.findElement(By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='pricedGrain_link']")).click();

			System.out.println(a3);

			if (a3.equals(ExpecteProdu))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + a3 + "--" + ExpecteProdu;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String UnpricedgrainMO(String object, String data) {
		try {

			driver.findElement(By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='unpricedGrain_Link']")).click();
			Thread.sleep(10000);

			String actual = driver
					.findElement(By
							.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='unpricedGrain_Link']//following::td[4]"))
					.getText().trim();
			String actual1 = actual.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");

			System.out.println(actual);

			Double UnpriceGrain = Double.parseDouble(actual1);
			System.out.println(UnpriceGrain);

			Thread.sleep(5000);

			String Quantity = driver.findElement(By.xpath("//td[text()=' Quantity ']//following::td[3]")).getText()
					.trim();
			String Quantitys = Quantity.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");

			Double a1 = Double.parseDouble(Quantitys);

			Thread.sleep(5000);

			String AveragePrice = driver.findElement(By.xpath("//td[text()='Average Price ']//following::td[3]"))
					.getText().trim();
			String AveragePrices = AveragePrice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");

			Double a2 = Double.parseDouble(AveragePrices);

			Double a3 = (a1) * (a2);

			Double ans = Double.valueOf(df2.format(a3.doubleValue()));

			System.out.println(ans);

			System.out.println(ans);

			if (ans.equals(UnpriceGrain))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + UnpriceGrain;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String AveragepriceBM(String object, String data) {
		try {

			Thread.sleep(10000);

			String AveragePrice = driver.findElement(By.xpath("//td[text()='Average Price ']//following::td[3]"))
					.getText().trim();
			String Averageprices = AveragePrice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(".", "");

			System.out.println(Averageprices);

			Double AP = Double.parseDouble(Averageprices);
			System.out.println(AP);

			String Futureprice = driver.findElement(By.xpath("//td[text()='Future Price ']//following::td[3]"))
					.getText().trim();
			String Futureprices = Futureprice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(".", "");

			System.out.println(Futureprices);

			Double FP = Double.parseDouble(Futureprices);
			System.out.println(FP);

			String Basis = driver.findElement(By.xpath("//td[text()='Basis ']//following::td[3]")).getText().trim();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(".", "");

			System.out.println(Basiss);

			Double BP = Double.parseDouble(Basiss);
			System.out.println(BP);

			String Premium = driver.findElement(By.xpath("//td[text()='Premium ']//following::td[3]")).getText().trim();
			String Premiums = Premium.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(".", "");

			System.out.println(Premiums);

			Double PR = Double.parseDouble(Premiums);
			System.out.println(PR);

			/*
			 * String Fee = driver.findElement(By.xpath(
			 * ".//*[@id='priceGrain_block']/div/table/tbody/tr[7]/td[3]")).getText().trim()
			 * ; String Fees=Fee.replace("bu/ac", "").replace(",", "").replace(" ",
			 * "").replace("$", "").replace("(", "").replace(")", "").replace("%", "");
			 * 
			 * 
			 * System.out.println(Fees);
			 * 
			 * Double FE=Double.parseDouble(Fees); System.out.println(FE);
			 */

			Double totalval = (FP + BP + PR);

			driver.findElement(By.xpath(".//*[@id='summary_table']/tbody/tr/td[@id='unpricedGrain_Link']")).click();
			Thread.sleep(10000);

			if (totalval.equals(AP))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + totalval + "--" + AP;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String comittedunpriced(String object, String data) {
		try {

			Thread.sleep(10000);

			driver.findElement(By.xpath(".//*[@id='basisContract_Link']")).click();
			Thread.sleep(5000);

			String CommitedUnprice = driver.findElement(By.xpath(".//*[@id='basisContract']//following::td[3]"))
					.getText().trim();
			String CommitedUnprices = CommitedUnprice.replace("bu/ac", "").replace(",", "").replace(" ", "")
					.replace("$", "").replace("(", "").replace(")", "").replace("%", "");

			System.out.println(CommitedUnprices);

			Double CUnprice = Double.parseDouble(CommitedUnprices);
			System.out.println(CUnprice);

			/*
			 * String Quantity =
			 * driver.findElement(By.id(".//td[@id='opMOQuantity']")).getText().trim();
			 * String Qauntitys=Quantity.replace("bu/ac", "").replace(",", "").replace(" ",
			 * "").replace("$", "").replace("(", "").replace(")", "").replace("%", "");
			 * 
			 * 
			 * System.out.println(Qauntitys);
			 * 
			 * Double QTY=Double.parseDouble(Qauntitys); System.out.println(QTY);
			 */

			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			String BasisF = js1.executeScript("return $('#opMOQuantity').html()", "").toString();
			System.out.println(BasisF);

			String Averageprice = driver.findElement(By.id(".//td[@id='opMOAvg']")).getText().trim();
			String Averageprices = Averageprice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "")
					.replace("(", "").replace(")", "").replace("%", "");

			System.out.println(Averageprices);

			Double AP = Double.parseDouble(Averageprices);
			System.out.println(AP);

			// Double expected_result=(QTY*AP);

			// if (expected_result.equals(CUnprice))
			return Constants.KEYWORD_PASS;
			// else
			// return Constants.KEYWORD_FAIL + " -- text content not verified " + CUnprice +
			// "--" + expected_result;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String AveragepriceBMMO(String object, String data) {
		try {

			String AvgPrice = driver.findElement(By.xpath("//td[contains(text(),'Average Price')]//following::td[3]"))
					.getText();
			String AvgPrices = AvgPrice.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(AvgPrices);
			Double AG = Double.parseDouble(AvgPrices);
			System.out.println(AG);

			String FP = driver.findElement(By.xpath("//td[contains(text(),'Future Price')]//following::td[3]"))
					.getText();
			String FPs = FP.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(FPs);
			Double FPFinal = Double.parseDouble(FPs);
			System.out.println(FPFinal);

			String Basis = driver.findElement(By.xpath("//td[contains(text(),'Basis')]//following::td[3]")).getText();
			String Basiss = Basis.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Basis);
			Double BS = Double.parseDouble(Basiss);
			System.out.println(BS);

			String Premium = driver.findElement(By.xpath("//td[contains(text(),'Premium')]//following::td[3]"))
					.getText();
			String Premiums = Premium.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Premiums);
			Double PR = Double.parseDouble(Premiums);
			System.out.println(PR);

			String Fee = driver.findElement(By.xpath("//td[contains(text(),'Fees')]//following::td[3]")).getText();
			String Fees = Fee.replace("bu/ac", "").replace(",", "").replace(" ", "").replace("$", "");
			System.out.println(Fees);
			Double FE = Double.parseDouble(Fees);
			System.out.println(FE);

			Double expected_result = (FPFinal + BS + PR - FE);

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			driver.findElement(By.id("totalGrain_Link")).click();

			if (ans.equals(AG))

				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + expected_result + "--" + AG;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String FO1(String object, String data) {

		String Result = "";

		try {

			boolean isNegative = false;

			String Price = driver
					.findElement(By.xpath(".//*[@class='table forecast-table profit-table']/tbody/tr[2]/td[5]"))
					.getText().trim();

			if (Price.contains("(")) {
				isNegative = true;
				Price = Price.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "").replace(",", "");
			}

			else

			{
				Price = Price.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "").replace(",", "");

			}

			Double Pri = Double.parseDouble(Price);
			if (isNegative) {
				Pri = -Pri;
			}
			System.out.println(Pri);

			boolean isNegative1 = false;

			String Price1 = driver
					.findElement(By.xpath(" .//*[@class='table forecast-table profit-table']/tbody//tr[4]/td[6]"))
					.getText().trim();

			if (Price1.contains("(")) {
				isNegative1 = true;
				Price1 = Price1.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "").replace(",", "");
			}

			else

			{
				Price1 = Price1.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
						.replace("%", "").replace(",", "");
			}

			Double Pri1 = Double.parseDouble(Price1);
			if (isNegative1) {
				Pri1 = -Pri1;
			}
			System.out.println(Pri1);

			Double actual_result1 = Math.floor(((Pri - 10) * (5000 * 15) - (0.5 * 15)));

			System.out.println(actual_result1);
			// df2.setRoundingMode(RoundingMode.HALF_UP);

			if (actual_result1.equals(Pri1)) {

				Result = Constants.KEYWORD_PASS;
			} else {
				Result = Constants.KEYWORD_FAIL + " -- text content not verified " + actual_result1 + "--" + Pri1;
			}

			this.ActualResult1 = actual_result1;

		} catch (Exception e) {
			Result = Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

		this.Result = Result;

		return Result;
	}

	public String AddvalueCol(String object, String data) {
		try {

			JavascriptExecutor js1 = (JavascriptExecutor) driver;

			String year = js1.executeScript("return $('#fieldID_1').val('2019')", "").toString();

			JavascriptExecutor js2 = (JavascriptExecutor) driver;

			String CTID = js2.executeScript("return $('#fieldID_2').val('1')", "").toString();

			JavascriptExecutor js3 = (JavascriptExecutor) driver;

			String Month = js3.executeScript("return $('#fieldID_3').val('7')", "").toString();

			JavascriptExecutor js4 = (JavascriptExecutor) driver;

			String Put = js4.executeScript("return $('#fieldID_15').val('f')", "").toString();
			return Constants.KEYWORD_PASS;

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String FetchPriceclose(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(".//*[@id='table_results']/tbody/tr/td[10]")).getText()
					.trim();

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String FetchPriceclose1(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(".//*[@id='table_results']/tbody/tr/td[10]")).getText()
					.trim();

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {
				result = Constants.KEYWORD_PASS;
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	

	
	public Double getDiference() {
		Double x = 0.0;
		Double x1 = 0.0;
		Double val;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://172.30.0.128/agyield_qa","automation2","Vande@Mataram");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat1.agyield.com/agyield_qa","automation2","Synoverge@123");
			Connection con = DriverManager.getConnection("jdbc:mysql://172.30.0.128/agyield_qa", "automation2",
					"Synoverge@123");

			System.out.println(con);
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2019 AND `crop_type_id` = 1 AND `month` = 7 AND `put_or_call` LIKE 'f' limit 1");
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				x = rs.getDouble(1);
			}
			ResultSet rs1 = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2019 AND `crop_type_id` = 1 AND `month` = 12 AND `put_or_call` LIKE 'f' limit 1");
			while (rs1.next()) {
				// System.out.println(rs.getString(1));
				x1 = rs1.getDouble(1);
			}
			con.close();
			val = x1 - x;
			System.out.println(val);
			return Double.valueOf(df2.format(val.doubleValue()));
		} catch (Exception e) {
			System.out.println("2323");
			System.out.println(e.toString());
		}
		return x;
	}

	public Double getDiference1() {
		Double x = 0.0;
		Double x1 = 0.0;
		Double val;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat2.agyield.com/agyield_qa","automation2","Vande@Mataram");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat1.agyield.com/agyield_qa","automation2","Synoverge@123");
			Connection con = DriverManager.getConnection("jdbc:mysql://172.30.0.128/agyield_qa", "automation2",
					"Synoverge@123");

			System.out.println(con);
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2019 AND `crop_type_id` = 1 AND `month` = 7 AND `put_or_call` LIKE 'f' limit 1");
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				x = rs.getDouble(1);
			}
			ResultSet rs1 = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2019 AND `crop_type_id` = 1 AND `month` = 12 AND `put_or_call` LIKE 'f' limit 1");
			while (rs1.next()) {
				// System.out.println(rs.getString(1));
				x1 = rs1.getDouble(1);
			}
			con.close();
			val = x1 - x;
			System.out.println(val);
			return Double.valueOf(df2.format(val.doubleValue()));
		} catch (Exception e) {
			System.out.println("2323");
			System.out.println(e.toString());
		}
		return x;
	}

	public Double getDiference2() {
		Double x = 0.0000;
		Double x1 = 0.0;
		Double val = 0.0000;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://dev.agyield.com/agyield","automation2","Vande@Mataram");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat1.agyield.com/agyield_qa","automation2","Synoverge@123");
			Connection con = DriverManager.getConnection("jdbc:mysql://172.30.0.128/agyield_qa", "automation2",
					"Synoverge@123");

			System.out.println(con);
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2021 AND `crop_type_id` = 5 AND `month` = 8 AND `put_or_call` LIKE 'f' ORDER BY `datetime` DESC limit 1");

			while (rs.next()) {
				// System.out.println(rs.getString(1));
				x = rs.getDouble(1);
			}
			ResultSet rs1 = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2021 AND `crop_type_id` = 5 AND `month` = 11 AND `put_or_call` LIKE 'f' ORDER BY `datetime` DESC limit 1");
			while (rs1.next()) {
				// System.out.println(rs.getString(1));
				x1 = rs1.getDouble(1);
			}
			con.close();
			val = x1 - x;
			// val = x - x1;
			System.out.println(val);

			df2.setRoundingMode(RoundingMode.CEILING);

			// Double diffbasis2 = Double.valueOf(df2.format(val.doubleValue()));
			// System.out.println(diffbasis2);

			// return Double.valueOf(df2.format(val.doubleValue()));

		} catch (Exception e) {
			System.out.println("2323");
			System.out.println(e.toString());
		}
		return val;
	}

	public Double getDiference3() {
		Double x = 0.0;
		Double x1 = 0.0;
		Double val;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat2.agyield.com/agyield_qa","automation2","Vande@Mataram");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://34.192.35.185/agyield_qa","automation","Vande@Mataram123");
			// Connection
			// con=DriverManager.getConnection("jdbc:mysql://uat1.agyield.com/agyield_qa","automation2","Synoverge@123");
			Connection con = DriverManager.getConnection("jdbc:mysql://172.30.0.128/agyield_qa", "automation2",
					"Synoverge@123");

			System.out.println(con);
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2020 AND `crop_type_id` = 5 AND `month` = 1 AND `put_or_call` LIKE 'f' limit 1");
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				x = rs.getDouble(1);
			}
			ResultSet rs1 = stmt.executeQuery(
					"SELECT price_close FROM settlements_history  WHERE `year` = 2020 AND `crop_type_id` = 5 AND `month` = 11 AND `put_or_call` LIKE 'f' limit 1");
			while (rs1.next()) {
				// System.out.println(rs.getString(1));
				x1 = rs1.getDouble(1);
			}
			con.close();
			val = x1 - x;
			System.out.println(val);
			return Double.valueOf(df2.format(val.doubleValue()));
		} catch (Exception e) {
			System.out.println("2323");
			System.out.println(e.toString());
		}
		return x;
	}

	public String OutlooksOtherrevenue(String object, String data) {
		try {

			Thread.sleep(10000);
			String Outlookotherprice = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Outlookotherprice);
			String Outlookotherprices = Outlookotherprice.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(",", "");
			Double OutlookOther = Double.parseDouble(Outlookotherprices);
			System.out.println(OutlookOther);
			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

//			driver.findElement(By.id("landAndCrops")).click();
//			Thread.sleep(15000);
//			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
//			Thread.sleep(10000);
			driver.findElement(By.xpath("//div[contains(text(),'Other Revenue')]")).click();
			Thread.sleep(10000);
			String OR = driver.findElement(By.xpath("//table[@id='tblIndemnityList']//tbody//tr[1]/td[5]")).getText();

			String OtherPrice = OR.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double OtherPrices = Double.parseDouble(OtherPrice);
			System.out.println("Other Price Value:-  " + OtherPrices);
			Thread.sleep(10000);

			if (OtherPrices.equals(OutlookOther))
				return Constants.KEYWORD_PASS;

			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + OutlookOther + "|" + OtherPrices;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String GlobalStrategy(String object, String data) {
		try {

			JavascriptExecutor js6 = (JavascriptExecutor) driver;
			js6.executeScript("return $('a[href=\"#/global-strategy\"]').children().click()", "");
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}
		return Constants.KEYWORD_PASS;
	}

	public String Enteremailcontent(String object, String data) {
		try {

			JavascriptExecutor js6 = (JavascriptExecutor) driver;
			js6.executeScript(
					"return CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].setData('Testing Email Contetnt');",
					"");
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}
		return Constants.KEYWORD_PASS;
	}

	public String Openprooutmenu(String object, String data) {
		try {

			JavascriptExecutor js6 = (JavascriptExecutor) driver;
			js6.executeScript("return $('a[href=\"#/profitability-outlook\"]').children().click()", "");
			Thread.sleep(10000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "- Not able to find radio button";

		}
		return Constants.KEYWORD_PASS;
	}

//	catch (Exception e) {
////		return;
//	}
//
// }

	public String Outlookinsurance(String object, String data) {
		try {
			Thread.sleep(15000);
			String OutlookInsurance = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(OutlookInsurance);
			String OutlookInsurances = OutlookInsurance.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(",", "");
			Double OutlookInsuranceva = Double.parseDouble(OutlookInsurances);
			System.out.println(OutlookInsurances);
			Thread.sleep(10000);
			System.out.println("OutlookInsurancevalue- " + OutlookInsuranceva);
			Thread.sleep(5000);

//			driver.findElement(By.id("landAndCrops")).click();
//			Thread.sleep(15000);
//			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
//			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//div[contains(text(),'Indemnity Payment')]")).click();
			Thread.sleep(10000);
			String AmoutRe = driver.findElement(By.xpath("//table[@id='tblIndemnityList']//tbody//tr[1]/td[4]"))
					.getText();

			String AmountRece = AmoutRe.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double AR = Double.parseDouble(AmountRece);
			System.out.println(AR);
			Thread.sleep(10000);

			if (OutlookInsuranceva.equals(AR)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text content not verified " + OutlookInsuranceva + "--" + AR;
			}

		} catch (Exception e) {
			// return "0.00";
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutlookCOP(String object, String data) {
		try {
			Thread.sleep(10000);
			String COP = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(COP);
			String COPs = COP.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double CP = Double.parseDouble(COPs);
			System.out.println(CP);
			Thread.sleep(10000);
			System.out.println("CP Value - " + CP);

			Thread.sleep(5000);

//			driver.findElement(By.id("landAndCrops")).click();
//			Thread.sleep(15000);
//			driver.findElement(By.xpath("//a[contains(text(),'Expenses')]")).click();
//			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/expense-crop\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

			String $ac = driver.findElement(By.xpath("(.//*[@class='cropAcres'])[1]")).getText();
			System.out.println($ac);
			String $acs = $ac.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double $acf = Double.parseDouble($acs);
			System.out.println("Acre Value - " + $acf);

			String Costofproduction = driver.findElement(By.xpath("(.//*[@class='userRoleLabel'])[1]")).getText();
			System.out.println($ac);
			String Costofproductions = Costofproduction.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(",", "");
			Double COPf = Double.parseDouble(Costofproductions);
			System.out.println("COP Value - " + COPf);

			Double expected_result = ($acf * COPf);

			Thread.sleep(10000);

			if (expected_result.equals(CP))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + CP + "--" + expected_result;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutlookValuein$(String object, String data) {
		try {

//			Double Cashsales= a1*a2;
//			System.out.println("Double a1*a2 = " + Cashsales);
//			Integer Cashsaless = (int) Math.round(Cashsales);

			Thread.sleep(10000);
			String Valuin$ = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Valuin$);
			String Valuin$s = Valuin$.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double V$ = Double.parseDouble(Valuin$s);
			Integer V$1 = (int) Math.round(V$);
			// System.out.println("V$1 Int Value: " + V$1);
			Thread.sleep(10000);
			System.out.println("Valuin$s Int Value is - " + V$1);

			driver.findElement(By.id("valueIn")).sendKeys("$/ac");
			Thread.sleep(10000);

			String Valuin$ac = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Valuin$);
			String Valuin$acs = Valuin$ac.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double V$a = Double.parseDouble(Valuin$acs);
			Integer V$a1 = (int) Math.round(V$a);
			System.out.println("Valuin$a Integer Value is -" + V$a1);
			Thread.sleep(10000);

			String acre = driver.findElement(By.xpath("//div[@id='land_unit_name']//span[contains(text(),'')]"))
					.getText().trim();
			String acres = acre.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "").replace(")", "");
			Double ACRE = Double.parseDouble(acres);
			Integer ACRE1 = (int) Math.round(ACRE);
			System.out.println("Acre Integer Value - " + ACRE1);
			Thread.sleep(5000);

			int expected_result1 = V$1 / ACRE1;
			Integer expected_result = (int) Math.round(expected_result1);

			Thread.sleep(10000);

			if (V$a1.equals(expected_result))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + V$a1 + "|" + expected_result;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutlookValuein$bu(String object, String data) {
		try {
			Thread.sleep(10000);
			String Valuin$ = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Valuin$);
			String Valuin$s = Valuin$.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double V$ = Double.parseDouble(Valuin$s);
			System.out.println(V$);
			Thread.sleep(10000);
			System.out.println(V$);

			driver.findElement(By.id("valueIn")).sendKeys("$/bu");
			Thread.sleep(10000);

			String Valuin$ac = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Valuin$);
			String Valuin$acs = Valuin$ac.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double V$a = Double.parseDouble(Valuin$acs);
			System.out.println(V$a);
			Thread.sleep(10000);

			String EP = driver.findElement(By.xpath("//div[@id='exp_production']//span[contains(text(),'')]")).getText()
					.trim();
			String EPS = EP.replace("/bu", "").replace("$", "").replace(" ", "").replace("(", "").replace(")", "")
					.replace(",", "");
			;
			Double ExpeProd = Double.parseDouble(EPS);
			System.out.println(ExpeProd);
			Thread.sleep(5000);

			Double expected_result = V$ / ExpeProd;

			Double ans = Double.valueOf(df2.format(expected_result.doubleValue()));

			Thread.sleep(10000);

			if (V$a.equals(ans))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + expected_result;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutllookCashSales(String object, String data) {
		try {
			Thread.sleep(10000);
			String CashSale = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(CashSale);
			String CashSales = CashSale.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double CS = Double.parseDouble(CashSales);
			System.out.println("CashSales Value: " + CS);
			Thread.sleep(15000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(15000);

			/* Accumulator Value */

			driver.findElement(
					By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Accumulator')]//preceding::td[1]"))
					.click();
			Thread.sleep(15000);

//			Karan will update following netprice.val

			JavascriptExecutor jscashsaleqty = (JavascriptExecutor) driver;
			String Accumulatornetprice = jscashsaleqty.executeScript("return $('input[id=\"netPrice\"]').val()", "")
					.toString();
			System.out.println(Accumulatornetprice);
			String Accumulatornetprices = Accumulatornetprice.replace("$", "").replace(" ", "").replace("bu", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(",", "");
			Double AN = Double.parseDouble(Accumulatornetprices);

			System.out.println("Transaction Accumulator Price:- " + AN);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String AccumaltorQuantity = driver
					.findElement(By.xpath(
							"//table[@id='tblCashList']/tbody/tr//td[contains(.,'Accumulator')]//following::td[1]"))
					.getText();
			Thread.sleep(15000);

			String AccumaltorQuantitys = AccumaltorQuantity.replace("$", "").replace(" ", "").replace("bu", "")
					.replace("(", "").replace(")", "").replace("%", "").replace(",", "");
			Double AQ = Double.parseDouble(AccumaltorQuantitys);

			System.out.println("AccumaltorQuantitys:- " + AQ);

			Double finalAcc = AQ * AN;

			/* Calculate Basis */

			driver.findElement(
					By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Basis')]//preceding::td[1]")).click();
			Thread.sleep(15000);

			JavascriptExecutor jsbasisprice = (JavascriptExecutor) driver;
			String Basicnetprice = jsbasisprice.executeScript("return $('#netPrice').val()", "").toString();
			System.out.println(Basicnetprice);
			String Basicnetprices = Basicnetprice.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BNP = Double.parseDouble(Basicnetprices);
			System.out.println("Basicnetprices Value: " + BNP);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String BasicQty = driver
					.findElement(
							By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Basis')]//following::td[1]"))
					.getText();

			String BasicQtys = BasicQty.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BQ = Double.parseDouble(BasicQtys);
			System.out.println("BasicQtys:- " + BQ);

			Double BFQ = (BQ * BNP);
			Thread.sleep(5000);

			/* Calculate Cash */

			driver.findElement(
					By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Cash')]//preceding::td[1]")).click();
			Thread.sleep(15000);

			JavascriptExecutor jscashsaleprice = (JavascriptExecutor) driver;
			String CashsalesQtyB = jscashsaleprice.executeScript("return $('input[id=\"netPrice\"]').val()", "")
					.toString();
			System.out.println(CashsalesQtyB);
			String CashsalesQtysB = CashsalesQtyB.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double CashsaleQtyB = Double.parseDouble(CashsalesQtysB);
			System.out.println("CashsalesQtysB Value: " + CashsalesQtysB);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String CashsalesQty = driver
					.findElement(
							By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Cash')]//following::td[1]"))
					.getText();
			Thread.sleep(15000);

			String CashsalesQtys = CashsalesQty.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double CashsaleQty = Double.parseDouble(CashsalesQtys);
			System.out.println("CashsalesQtysB:-  " + CashsalesQtysB);

			Double CashsalesF = CashsaleQtyB * CashsaleQty;

			System.out.println("CashsalesF Value: " + CashsalesF);

			/* Calculate HTA */

			driver.findElement(By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'HTA')]//preceding::td[1]"))
					.click();
			Thread.sleep(15000);

			JavascriptExecutor jsHTA = (JavascriptExecutor) driver;
			String HTAQuantityB = jsHTA.executeScript("return $('#netPrice').val()", "").toString();
			System.out.println(HTAQuantityB);
			String HTAQuantityBS = HTAQuantityB.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double HTAQB = Double.parseDouble(HTAQuantityBS);
			System.out.println("HTAQuantityBS Value: " + HTAQB);

			Thread.sleep(15000);
			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String HTAQTY = driver
					.findElement(
							By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'HTA')]//following::td[1]"))
					.getText();
			Thread.sleep(15000);

			String HTAQTYs = HTAQTY.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double HQY = Double.parseDouble(HTAQTYs);
			System.out.println("HTAQTY Value: " + HQY);

			Double HTANetprice = (HTAQB * HQY);

			System.out.println("HTANetprice:-  " + HTANetprice);

			/* MBP Calculation */

			driver.findElement(By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'MBP')]//preceding::td[1]"))
					.click();
			Thread.sleep(15000);

			JavascriptExecutor jsMBP = (JavascriptExecutor) driver;
			String MBPNetprice = jsMBP.executeScript("return $('input[id=\"netPrice\"]').val()", "").toString();
			System.out.println(MBPNetprice);
			String MBPNetprices = MBPNetprice.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double MBPN = Double.parseDouble(MBPNetprices);
			System.out.println("MBPNetprices Value: " + MBPN);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String MBPQty = driver
					.findElement(
							By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'MBP')]//following::td[1]"))
					.getText();
			Thread.sleep(15000);

			String MBPQtys = MBPQty.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double MBPQTY = Double.parseDouble(MBPQtys);

			Double MBPF = (MBPN * MBPQTY);

			System.out.println("MBPF Value :- " + MBPF);

			/* Min/Max */

			driver.findElement(
					By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Min/Max')]//preceding::td[1]"))
					.click();
			Thread.sleep(15000);

			JavascriptExecutor jsMinMAx = (JavascriptExecutor) driver;
			String MinMaxPricebu = jsMinMAx.executeScript("return $('input[id=\"netPrice\"]').val()", "").toString();
			System.out.println(MinMaxPricebu);
			String MinMaxPricebus = MinMaxPricebu.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double MinMaxQB = Double.parseDouble(MinMaxPricebu);
			System.out.println(MinMaxQB);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(15000);

			String MinMaxQty = driver
					.findElement(By
							.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Min/Max')]//following::td[1]"))
					.getText();

			String MinMaxQtys = MinMaxQty.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double MinMaxN = Double.parseDouble(MinMaxQtys);

			Double MXF = (MinMaxN * MinMaxQB);

			System.out.println(MXF);

			/* Percentage */

			String PercentageQty = driver
					.findElement(By.xpath(
							"//table[@id='tblCashList']/tbody/tr//td[contains(.,'Percentage')]//following::td[1]"))
					.getText();
			Thread.sleep(15000);

			String PercentageQtys = PercentageQty.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double PerQty = Double.parseDouble(PercentageQtys);
			Double PerQtys = PerQty * 9; /* 9 is Net price */

			Double TransactionCashSales = (finalAcc + BFQ + CashsalesF + HTANetprice + MBPF + MXF + PerQtys);
			Thread.sleep(15000);

			if (TransactionCashSales.equals(CS))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + CS + "--" + TransactionCashSales;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutllookCashSales1(String object, String data) {
		try {
			Thread.sleep(10000);
			String CashSale = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(CashSale);
			String CashSales = CashSale.replace("$", "").replace(" ", "").replace("bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double CS = Double.parseDouble(CashSales);
			System.out.println("CashSales Value: " + CS);
			Thread.sleep(15000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(15000);

			String TotalValue = driver.findElement(By.xpath("//label[contains(text(),'Total value:')]")).getText();

			String TotalValues = TotalValue.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("Total value:", "").replace("Totalvalue:", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");

			Double QTY = Double.parseDouble(TotalValues);
			System.out.println("Total Quantity Value:" + TotalValues);
			Thread.sleep(15000);

			if (QTY.equals(CS))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + CS + "--" + QTY;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String OutlookUnsold(String object, String data) {
		try {
			Thread.sleep(10000);
			String Yield = driver.findElement(By.xpath("//tbody//tr[2]//td[10]")).getText();
			System.out.println(Yield);
			String Yields = Yield.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double YD = Double.parseDouble(Yields);
			System.out.println("Yields Value:- " + YD);
			Thread.sleep(10000);

			String Value = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Value);
			String Values = Value.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double VA = Double.parseDouble(Values);
			System.out.println("td11 value  " + VA);
			Thread.sleep(10000);

//			driver.findElement(By.id("landAndCrops")).click();
//			Thread.sleep(10000);
//			
//			driver.findElement(By.xpath("//a[contains(text(),'Crops')]")).click();
//			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String CropMenu = js5.executeScript("return $('a[href=\"#/crops\"]').children().click()", "").toString();
			Thread.sleep(10000);

			driver.findElement(By.xpath("//a[contains(@class,'fieldName editField')]")).click();
			Thread.sleep(10000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String BasisF = js.executeScript("return $('#defaultBasis').val()", "").toString();
			System.out.println(BasisF);
			String BAS = BasisF.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			// Double BA=Double.parseDouble("BasisFValue: " + BasisF);
			Double BA = Double.parseDouble(BAS);
			Thread.sleep(10000);
			Double Finalbasis = BA + YD;

			// Double expected_result=Finalbasis*VA;

//			driver.findElement(By.xpath("//a[contains(text(),'Expense')]")).click();
//			Thread.sleep(10000);

			JavascriptExecutor js9 = (JavascriptExecutor) driver;
			String ExpenseMenu = js9.executeScript("return $('a[href=\"#/expense-crop\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

			String $ac = driver.findElement(By.xpath("(.//*[@class='cropAcres'])[1]")).getText();
			System.out.println($ac);
			String $acs = $ac.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double $acf = Double.parseDouble($acs);
			System.out.println("100 Acres:- " + $acf);

			String EP = driver.findElement(By.xpath("(.//*[@class='userRoleLabel'])[3]")).getText();
			System.out.println(EP);
			String EPs = EP.replace("$", "").replace(" ", "").replace("bu/ac", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double EPYield = Double.parseDouble(EPs);
			System.out.println("EPYield Value: " + EPYield);

			Double Expense = (EPYield * $acf);

			System.out.println("Expense Value:  " + Expense);

//			driver.findElement(By.xpath("//a[contains(text(),'Transactions')]")).click();
//			Thread.sleep(10000);

			JavascriptExecutor js10 = (JavascriptExecutor) driver;
			String TransactionMenu = js10.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

//			String TQ=driver.findElement(By.id("cashSalesTotalQuantitylbl")).getText();

//			Doubtful Xpath - Hiren
			String TQ = driver.findElement(By.xpath("//label[contains(text(),'Total Quantity:')]")).getText();

			System.out.println(TQ);
			String TQs = TQ.replace("$", "").replace(" ", "").replace("/bu", "").replace("Total Quantity:", "")
					.replace("TotalQuantity:", "").replace("(", "").replace(")", "").replace("%", "").replace(",", "")
					.replace(".00", "");

			Double TQu = Double.parseDouble(TQs);
			System.out.println("Cash sales Total Quantity: " + TQu);

			Double Quantity = (Expense - TQu);

			df2.setRoundingMode(RoundingMode.HALF_UP);
			Double expected_result1 = (Finalbasis * Quantity);
			Double ans = Double.valueOf(df2.format(expected_result1.doubleValue()));

			System.out.println(ans);

			Thread.sleep(10000);

			if (ans.equals(VA))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + ans + "--" + VA;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Outlooksettings1(String object, String data) {
		try {

			JavascriptExecutor Minvalue = (JavascriptExecutor) driver;
			String Minvalues = Minvalue.executeScript("return $('#minPriceData').val()", "").toString();
			System.out.println(Minvalues);
			Double MV = Double.parseDouble(Minvalues);
			Thread.sleep(10000);

			JavascriptExecutor Maxvalue = (JavascriptExecutor) driver;
			String Maxvalues = Maxvalue.executeScript("return $('#maxPriceData').val()", "").toString();
			System.out.println(Minvalues);
			Double MXV = Double.parseDouble(Maxvalues);
			Thread.sleep(10000);

			driver.findElement(By.xpath("//span[contains(text(),'�')]")).click();
			Thread.sleep(5000);

			String Yieldtop = driver.findElement(By.xpath("//tbody//tr[2]//td[1]")).getText();

			String Yieldtops = Yieldtop.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("Total Quantity:", "").replace("TotalQuantity:", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "").replace(".00", "");

			Double Yieldtopss = Double.parseDouble(Yieldtops);
			System.out.println("Yield First Value: " + Yieldtops);

			String Yieldlast = driver.findElement(By.xpath("//tbody//tr[2]//td[10]")).getText();

			String Yieldtopsf = Yieldlast.replace("$", "").replace(" ", "").replace("/bu", "")
					.replace("Total Quantity:", "").replace("TotalQuantity:", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "").replace(".00", "");

			Double Yieldlasts = Double.parseDouble(Yieldtopsf);
			System.out.println("Yield Last Value: " + Yieldlasts);
			Thread.sleep(5000);

			if (MV.equals(Yieldtopss) && (MXV.equals(Yieldlasts)))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + MV + "--" + Yieldtops;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Outlooksettings(String object, String data) {
		try {

			JavascriptExecutor Minvalue = (JavascriptExecutor) driver;
			String Minvalues = Minvalue.executeScript("return $('#minPriceData').val()", "").toString();
			System.out.println(Minvalues);
			Double MV = Double.parseDouble(Minvalues);
			Thread.sleep(10000);

			JavascriptExecutor Maxvalue = (JavascriptExecutor) driver;
			String Maxvalues = Maxvalue.executeScript("return $('#maxPriceData').val()", "").toString();
			System.out.println(Minvalues);
			Double MXV = Double.parseDouble(Maxvalues);
			Thread.sleep(10000);

			driver.findElement(By.xpath("//span[contains(text(),'�')]")).click();
			Thread.sleep(5000);

			String Yieldtop = driver.findElement(By.xpath("//tbody//tr[2]//td[1]")).getText();

			Double Yieldtops = Double.parseDouble(Yieldtop);
			System.out.println("Yield First Value: " + Yieldtops);

			String Yieldlast = driver.findElement(By.xpath("//tbody//tr[13]//td[1]")).getText();

			Double Yieldlasts = Double.parseDouble(Yieldlast);
			System.out.println("Yield Last Value: " + Yieldlasts);
			Thread.sleep(5000);

			if (MV.equals(Yieldtops) && (MXV.equals(Yieldlasts)))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + MV + "--" + Yieldtops;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String Outlooksettingpricerange(String object, String data) {
		try {

			Thread.sleep(5000);
			JavascriptExecutor Minvalue = (JavascriptExecutor) driver;
			String Minvalues = Minvalue.executeScript("return $('#minYieldData').val()", "").toString();
			System.out.println(Minvalues);
			Double MV = Double.parseDouble(Minvalues);

			Thread.sleep(10000);
			JavascriptExecutor Maxvalue = (JavascriptExecutor) driver;
			String Maxvalues = Maxvalue.executeScript("return $('#maxYieldData').val()", "").toString();
			System.out.println(Minvalues);
			Double MXV = Double.parseDouble(Maxvalues);
			Thread.sleep(10000);

			driver.findElement(By.xpath("//span[contains(text(),'�')]")).click();
			Thread.sleep(5000);

			String Yieldtop = driver.findElement(By.xpath("//tbody//tr[4]//td[1]")).getText();

			String Yieldstops = Yieldtop.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double Yieldtopss = Double.parseDouble(Yieldstops);
			System.out.println(Yieldtopss);

			String Yieldlast = driver.findElement(By.xpath("//tbody//tr[13]//td[1]")).getText();
			String Yieldlasts = Yieldlast.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");

			Double Yieldlastss = Double.parseDouble(Yieldlasts);
			System.out.println(Yieldlasts);

			Thread.sleep(5000);

			if (MV.equals(Yieldtopss) && (Maxvalues.equals(Yieldlasts)))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + MV + "--" + Yieldtopss;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	public String login_without_otp_verification_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//p[contains(text(),'Passenger Carrying Policy')]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("//p[contains(text(),'Passenger Carrying Policy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - PASSENGER CARRYING POLICY')]")));
					Thread.sleep(3000);

				} catch (Exception e) {
					System.out.println("GC Service Error");

					// driver.quit();
					// driver.close();
				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to Login due to service error";
		}
		return result;
	}

	public String writeInInputByID_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			// driver.findElement(By.id(OR.getProperty(object))).click();
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String SelectOrganizationh_without_down_press_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Selectmodelwithxpath_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(2000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Addcurrentdateinsheet_pcv(String object, String data) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = "";
		try

		{

			Date date = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate2 = sdf2.format(date);
			System.out.println(formattedDate2);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, formattedDate2)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String selectList_and__press_Tab_pcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			dropDownListBox.sendKeys(Keys.TAB);

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//h4[contains(text(),'Calculate Premium')]")));
			System.out.println("elememt now clickable");

			Thread.sleep(3000);
			System.out.println("wait complete");
			/*
			 * WebDriverWait wait = new WebDriverWait(driver,15);
			 * wait.until(ExpectedConditions.stalenessOf(
			 * driver.findElement(By.xpath(OR.getProperty(object)))));
			 * 
			 * WebDriverWait wait = new WebDriverWait(driver,55);
			 * wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//h4[contains(text(),'Calculate Premium')]")));
			 */
		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_pcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String Selectmodel_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.id(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(2000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInput_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String Addcartvalueinsheet_pcv(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Cart value is ", "").replace("/bu", "");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addcartvalueinsheet_add_1000_in_value_pcv(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);

			System.out.println(EXLVALUEBID);
			int i = Integer.parseInt(EXLVALUEBID);
			int updated_val = i + 2000;
			// Thread.sleep(1000);
			String modify_val = String.valueOf(updated_val);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = modify_val.replace("Your Cart value is ", "").replace("/bu", "");
			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addcartvalueinsheet_TW(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);

			System.out.println(EXLVALUEBID);
			int cart_val = Integer.parseInt(EXLVALUEBID);
			// int updated_val = cart_val + 2000;
			// Thread.sleep(1000);
			String modify_val = String.valueOf(cart_val);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = modify_val.replace("Your Cart value is ", "").replace("/bu", "");
			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String explicitwait_calculatepremiumandgeneratequote_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));

				try {

WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
					System.out.println("Element is now clickable");

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate
					// Proposal')]")));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(5000);
				} catch (Exception e) {

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					Thread.sleep(2000);

					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(1000);
					/*
					 * driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[11]")).
					 * click(); Thread.sleep(2000);
					 */

					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String Addvalueofquoteinsheet_pcv(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[6]")).getText();

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu",
					"");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String scroll_up_and_writeInInputByID_pcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			Thread.sleep(2000);

			driver.findElement(By.id(OR.getProperty(object))).click();
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String ex_calculatepremium_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(3000);
					//

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(2000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String selectListByID_pcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String verifycustomeroncheckout_pcv(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//input[@name='CustomerName'])[2]"))
					.getAttribute("value").toUpperCase();
			System.out.println(EXLVALUEBID);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Customer name is fetched ",
			// "").replace("/bu", "");

			// Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(3000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String verifypremiumforquoteandcart_PCV(String object, String data) {
		try {

			String actuals = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText().trim();
			Double actual = Double.parseDouble(actuals);
			System.out.println(actual);
			// Thread.sleep(3000);

			String expect = driver.findElement(By.xpath("(//span[@class='Amount ng-binding'])[2]")).getText().trim();
			// String expectsrplace = expect.replace("Rs", "").replace("/bu", "");
			Double expects = Double.parseDouble(expect);

			System.out.println(expects);
			// Thread.sleep(3000);

			if (actual.equals(expects))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + actual + "--" + expects;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	public String explicitwait_generateproposal_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();

					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(2000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			System.out.println("GC service error");
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String mannual_payment_and_download_policy_pcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
						Thread.sleep(2000);
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_gcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//p[contains(text(),'Goods Carrying Policy')]")));

					Thread.sleep(1000);
					driver.findElement(By.xpath("//p[contains(text(),'Goods Carrying Policy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - GOODS CARRYING POLICY')]")));
					Thread.sleep(3000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error, closing browser for stop execution");

					driver.quit();
					driver.close();
					result = Constants.KEYWORD_PASS;

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";

		}
		return result;
	}

	public String SelectOrganizationh_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
			// Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String selectListByID_gcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String Selectmodelwithxpath_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInput_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			// Thread.sleep(1000);
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInputByID_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).clear();

			Thread.sleep(1000);
			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String scroll_up_and_writeInInputByID_gcv(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String scroll_up_and_writeInInput_xpath(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
			driver.getWindowHandle();
			Actions builder = new Actions(driver);
			builder.moveToElement(objElement).build().perform();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-350)", "");

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			System.out.println("wait");
			Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String Addvalueofquoteinsheet_gcv(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[6]")).getText();

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu",
					"");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String selectList_and__press_Tab_gcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			dropDownListBox.sendKeys(Keys.TAB);
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//h4[contains(text(),'Calculate Premium')]")));
			System.out.println("elememt now clickable");

			Thread.sleep(5000);
			System.out.println("wait complete");
		}

		catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String selectList_gcv(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String calculatepremiumandquotegenerate_gcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

					driver.findElement(By.xpath("(//button[@ng-disabled='flag.isPaymentButtonDisable'])[2]")).click();
					wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='rdoProposalList']")));

				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				Thread.sleep(2000);

				System.out.println("GC service error, closing browser for stop execution");

				driver.quit();
				driver.close();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_calculatepremiumandgeneratequote_gcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(5000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				} catch (Exception e) {

					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				Thread.sleep(2000);

				System.out.println("GC service error, closing browser for stop execution");

				driver.quit();
				driver.close();
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_generateproposal_gcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");

		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					Thread.sleep(3000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}

				catch (Exception e) {

					// final int MAX_RETRIES = 3;
					// for (int i = 0; i <= MAX_RETRIES; i++) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();

					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(3000);

					// if (i == MAX_RETRIES) {
					// throw e;
					// }
					// }

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					result = Constants.KEYWORD_PASS;
				}
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_gcv(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
					Thread.sleep(2000);
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// Thread.sleep(4000);
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(2000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();
						Thread.sleep(2000);
						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
						Thread.sleep(2000);
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_miscd(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					wait.until(
							ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'Misc.d Policy')]")));
					Thread.sleep(1000);

					driver.findElement(By.xpath("//p[contains(text(),'Misc.d Policy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Quote - Misc.d Policy')]")));
					Thread.sleep(3000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error, closing browser for stop execution");

					// driver.quit();
					// driver.close();
					result = Constants.KEYWORD_PASS;
				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String writeInInputByID_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();

			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String SelectOrganizationh_without_down_press_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).clear();
			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(3000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(2000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Selectmodelwithxpath_without_down_press_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			// driver.findElement(By.xpath(OR.getProperty(object))).click();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(2000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Addcurrentdateinsheet_miscd(String object, String data) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = "";
		try

		{

			Date date = new Date();
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate2 = sdf2.format(date);
			System.out.println(formattedDate2);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, formattedDate2)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String SelectOrganizationh_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");
			driver.findElement(By.id(OR.getProperty(object))).click();

			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).clear();

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String selectList_and__press_Tab_miscd(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {
			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);

			dropDownListBox.sendKeys(Keys.TAB);

			Thread.sleep(5000);
			System.out.println("wait complete");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[contains(text(),'Calculate Premium')]")));

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String writeInInput_miscd(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			// Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String selectListByID_miscd(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(1000);

			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			droplist.selectByVisibleText(data);
			result = Constants.KEYWORD_PASS;

			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			result = Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return result;
	}

	public String selectList_miscd(String object, String data) {
		APP_LOGS.debug("Selecting from list");
		try {

			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));

			data = data.trim();
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			// dropDownListBox.sendKeys(data);

			Select droplist = new Select(dropDownListBox);
			Thread.sleep(1000);
			droplist.selectByVisibleText(data);
			// Thread.sleep(3000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			System.out.println(" - Could not select from list. " + e.getMessage());
			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
		}

		return Constants.KEYWORD_PASS;
	}

	public String ex_calculatepremium_miscd(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(3000);
					//

				}

				catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String explicitwait_calculatepremiumandgeneratequote_miscd(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));

				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					// wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Generate
					// Proposal')]")));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(5000);
				} catch (Exception e) {

					// driver.findElement(By.xpath("(//button[@class='btn btn-primary
					// margin-bottom-10'])[1]")).click();
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
					Thread.sleep(3000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					// Thread.sleep(2000);

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));

				}
				sleep(1);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Quote')]")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
				Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String calculatepremiumandquotegenerate(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("IE")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					Thread.sleep(25000);
				} catch (Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(25000);
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String Addvalueofquoteinsheet(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("(//p[@class='ng-binding'])[6]")).getText();

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Your Quote is created successfully with ", "").replace("/bu",
					"");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Addcartvalueinsheet_add_1000_in_value(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath(
					"(//div[@class='table-bordered in']//h5[@class='pull-right padding-right-5']//b[@class='ng-binding'])"))
					.getText();
			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);

			System.out.println(EXLVALUEBID);
			int i = Integer.parseInt(EXLVALUEBID);
			int updated_val = i + 2000;
			String modify_val = String.valueOf(updated_val);
			String trim_EXLVALUEBID = modify_val.replace("Your Cart value is ", "").replace("/bu", "");
			System.out.println(trim_EXLVALUEBID);

			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String explicitwait_generateproposal_miscd(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();
					// Thread.sleep(25000);
					// Thread.sleep(1000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					Thread.sleep(2000);

					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String makepayment_create_and_download_policy_miscd(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;

	}

	public String writeInInputByID_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();

			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String login_without_otp_verification_os(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					Thread.sleep(2000);
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Optima Secure')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, closing browser for stop execution");
					driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String SelectOrganization_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			// driver.findElement(By.xpath(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			Thread.sleep(1000);
			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh2_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(3000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization2_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Robot robot = new Robot(); // Robot class throws AWT Exception

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);

			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			driver.findElement(By.id(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInput_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String selectorganization_without_down_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(3000);

			driver.findElement(By.id(OR.getProperty(object))).clear();
			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			// Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String makepayment_create_and_download_policy_os(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String selectorganization_without_down_xpath_os(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			// Thread.sleep(5000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			// Thread.sleep(3000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(7000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInputByID_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			Thread.sleep(10);
			// driver.findElement(By.id(OR.getProperty(object))).sendKeys(data, Keys.ENTER);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(10);
			result = Constants.KEYWORD_PASS;
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;

	}

	public String login_without_otp_verification_or(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Optima Restore')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, closing browser for stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String SelectOrganization_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			//WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			
	        Duration positiveSeconds = Duration.ofSeconds(1);

			//Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.DOWN);
			 Duration positiveSeconds1 = Duration.ofSeconds(1);
			//Thread.sleep(500);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			// Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			 Duration positiveSeconds2 = Duration.ofSeconds(1);
			//Thread.sleep(500);
			// driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.DOWN);

//			   driver.findElement(By.xpath((OR.getProperty(object)))).sendKeys(Keys.RETURN);
//			   Thread.sleep(1500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh2_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			driver.findElement(By.id(OR.getProperty(object))).clear();
			// driver.findElement(By.id(OR.getProperty(object))).click();
			// Thread.sleep(3000);
			Robot robot = new Robot(); // Robot class throws AWT Exception
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganization2_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			// Robot robot = new Robot(); // Robot class throws AWT Exception
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(500);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String SelectOrganizationh_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.id(OR.getProperty(object))).clear();
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.DOWN);
			// Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);

			Thread.sleep(1000);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String writeInInput_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			System.out.println("wait");
			// Thread.sleep(1000);
			System.out.println("wait over");
			System.out.println(data);
			// Thread.sleep(1000);
			System.out.println("above data is pass");

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(500);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String writeInInput_DOCUMENT_NAME_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			Thread.sleep(3000);
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			System.out.println("wait over");
			System.out.println(data);

			System.out.println("above data is pass");
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}

	public String selectorganization_without_down_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(8));
			wait3.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String makepayment_create_and_download_policy_or(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				driver.findElement(By.id(OR.getProperty(object)));
				// Thread.sleep(5000);
				try {

					driver.findElement(By.id(OR.getProperty(object))).click();
WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='btn btn-pdf'])[1]")));
					System.out.println("wait completed, element is now clickable");
					Thread.sleep(4000);
					try {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					} catch (Exception e) {
						driver.findElement(By.xpath("(//button[@class='btn btn-pdf'])[1]")).click();

						wait3.until(ExpectedConditions
								.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary'])[1]")));
						Thread.sleep(2000);
						driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[1]")).click();
					}

				} catch (Exception e) {
					driver.findElement(By.id(OR.getProperty(object))).sendKeys("\n");
				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.id(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String selectorganization_without_down_xpath_or(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			Thread.sleep(1000);
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(6));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String Add_premiuminsheet_or(String object, String data) {
		try {
			String EXLVALUEBID = driver.findElement(By.xpath("//td[@class='width-180px']")).getText().trim();
			System.out.println(EXLVALUEBID);
			Thread.sleep(1000);
			// String trim_EXLVALUEBID = EXLVALUEBID.replace("Quote No ", "").replace(" is
			// created successfully.", "");

			System.out.println("Premium AMount is  :" + EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String ex_calculatepremium_tw(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(3000);
					//

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(2000);
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_medisure(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//p[contains(text(),'Medisure Super Topup')]")));

					Thread.sleep(1000);
					driver.findElement(By.xpath("//p[contains(text(),'Medisure Super Topup')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - MEDISURE SUPER TOPUP')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");

					// driver.quit();
					// driver.close();
					result = Constants.KEYWORD_PASS;
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String ex_calculatepremium_medisure(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);
					//

				}

				catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String ex_calculatepremium(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// driver.SwitchTo().Window(driver.CurrentWindowHandle);
				// driver.findElement(By.id(OR.getProperty(object))).click();
				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(2000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);
					//

				}

				catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();

					// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Calculate Premium')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Quote Summary')]")));
					Thread.sleep(2000);
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Quote')]")).click();
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Generate Proposal')]")));
					Thread.sleep(3000);

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String SelectOrganization_medisure(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(500);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String explicitwait_generateproposal_medisure(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// String windowHandle = driver.getWindowHandle();
				// driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					JavascriptExecutor j = (JavascriptExecutor) driver;
					if (j.executeScript("return document.readyState").toString().equals("complete")) {
						System.out.println("Page has loaded");
					}

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String explicitwait_generateproposal(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				// String windowHandle = driver.getWindowHandle();
				// driver.switchTo().window(windowHandle);
				// Thread.sleep(1000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {
					// Thread.sleep(1000);
					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					JavascriptExecutor j = (JavascriptExecutor) driver;
					if (j.executeScript("return document.readyState").toString().equals("complete")) {
						System.out.println("Page has loaded");
					}

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Send Consent By OTP')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Generate Proposal')]")));
					driver.findElement(By.xpath("//h4[contains(text(),'Generate Proposal')]")).click();
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
					// Thread.sleep(2000);
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Check Out/Consent')]")));
					Thread.sleep(2000);

				}
				sleep(4);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				// Thread.sleep(25000);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Proceed to Checkout')]")));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[contains(text(),'Proceed to Checkout')]")).click();
				// Thread.sleep(2000);
				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
			System.out.println("GC service error");
			// driver.close();
			// driver.quit();
			// System.exit(1);
		}
		return result;
	}

	public String Addvalueofquoteinsheet_medisure(String object, String data) {
		try {
			String EXLVALUEBID = driver
					.findElement(By.xpath(
							"//div[@class='notice-page '] //p[@class='ng-binding' and contains(text(),'Quote No') ]"))
					.getText();

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Quote No ", "").replace("/bu", "")
					.replace(" is created successfully.", "");

			System.out.println(trim_EXLVALUEBID);
			// Thread.sleep(1000);

			/*
			 * String EXLVALUEBID1 = EXLVALUEBID.replace("bu/ac", "").replace(",",
			 * "").replace(" ", "").replace("$", "") .replace("/bu", "");
			 */

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Add_premium_in_sheet_checkout_medisure(String object, String data) {
		try {
			String EXLVALUEBID = driver
					.findElement(By.xpath("(//td[@data-title='Total Premium']//span[@class='price ng-binding'])[2]"))
					.getText();
			System.out.println(EXLVALUEBID);
			Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(10000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String Add_premium_in_sheet_checkout(String object, String data) {
		try {
			String EXLVALUEBID = driver
					.findElement(By.xpath("(//td[@data-title='Total Premium']//span[@class='price ng-binding'])[2]"))
					.getText();
			System.out.println(EXLVALUEBID);
			Thread.sleep(1000);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				Thread.sleep(10000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String SelectOrganization_critical_illness(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(object))));
			System.out.println("Premium Calculated");

			Thread.sleep(500);

			driver.findElement(By.xpath(OR.getProperty(object))).clear();

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			Thread.sleep(1000);

			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String login_without_otp_verification_critical_illness(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//p[contains(text(),'Critical Illness')]")));

					Thread.sleep(1000);
					driver.findElement(By.xpath("//p[contains(text(),'Critical Illness')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//h2[contains(text(),'Quote - CRITICAL ILLNESS INSURANCE POLICY')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);

					System.out.println("GC service error");

					// driver.quit();
					// driver.close();
					result = Constants.KEYWORD_PASS;
				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String Addvalueofquoteinsheet_critical_illness(String object, String data) {
		try {
			String EXLVALUEBID = driver
					.findElement(By.xpath(
							"//div[@class='notice-page '] //p[@class='ng-binding' and contains(text(),'Quote No') ]"))
					.getText();

			System.out.println(EXLVALUEBID);
			// Thread.sleep(1000);
			String trim_EXLVALUEBID = EXLVALUEBID.replace("Quote No ", "").replace("/bu", "")
					.replace(" is created successfully.", "");

			System.out.println(trim_EXLVALUEBID);

			if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID, trim_EXLVALUEBID)) {

				result = Constants.KEYWORD_PASS;
				// Thread.sleep(1000);
			} else {
				result = Constants.KEYWORD_FAIL;
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL;
		}
		return result;
	}

	public String login_without_otp_verification_energy(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Energy')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, Please stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_ican(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Ican')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, Please stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_amipa(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

					//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					driver.findElement(By.xpath("//p[contains(text(),'Individual Personal Accident (AMIPA)')]"))
							.click();

					//WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));

					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, Please stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_quick_renewal(String object, String data) {
		APP_LOGS.debug("Clicking on product element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					Thread.sleep(2000);
					driver.findElement(By.xpath("//p[contains(text(),'Quick Health Renewal')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//button[@type='button' and contains(text(),'Search')]")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, Please stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String login_without_otp_verification_health_wallet(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		try {

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("Chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty(object)));
				// Thread.sleep(1000);
				try {

					driver.findElement(By.xpath(OR.getProperty(object))).click();

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));

					Thread.sleep(2000);

					driver.findElement(By.xpath("//p[contains(text(),'Health Wallet')]")).click();

					WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					wait2.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@id='ProductType_Individual']")));
					Thread.sleep(2000);

				} catch (Exception e) {
					Thread.sleep(2000);
					System.out.println("GC service error, Please stop execution");
					// driver.close();
					Thread.sleep(2000);
					// driver.quit();
					result = Constants.KEYWORD_PASS;

				}
				sleep(2);
				// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("\n");
				result = Constants.KEYWORD_PASS;
			} else {
				WebElement objElement = driver.findElement(By.xpath(OR.getProperty(object)));
				driver.getWindowHandle();
				Actions builder = new Actions(driver);
				builder.moveToElement(objElement).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", objElement);

				result = Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return result;
	}

	public String value_calculation_verify(String object, String data) {

		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;

			String val_upper_limit_s = js.executeScript("return $('input[id=\"upperLimit_1\"]').val()").toString();
			System.out.println(val_upper_limit_s);

			Double val_upper_limit_d = Double.parseDouble(val_upper_limit_s);
			System.out.println("Upper Yield Limit / ac : " + val_upper_limit_d);

			String Percentage_s = js.executeScript("return $('input[id=\"per_1\"]').val()").toString();
			System.out.println(Percentage_s);

			Double Percentage_d = Double.parseDouble(Percentage_s);
			System.out.println("Percentage : " + Percentage_d);

			// WebElement Price = driver.findElement(By.xpath("//input[@id='price_1']"));

			String Price_s = js.executeScript("return $('input[id=\"per_1\"]').val()").toString();
			System.out.println(Price_s);

			Double Price_d = Double.parseDouble(Price_s);
			System.out.println("Price  : " + Price_d);

			Double total_calculate_d = val_upper_limit_d * Percentage_d * Price_d * 100;

			System.out.println("total cost :" + total_calculate_d);

			String total_cost_get_text_s = js.executeScript("return $('input[id=\"totalCost_1\"]').val()").toString();

			String trim_EXLVALUEBID = total_cost_get_text_s.replace(",", "");
			Double total_cost_get_text_d = Double.parseDouble(trim_EXLVALUEBID);
			System.out.println(total_cost_get_text_d);

			if (total_cost_get_text_d.equals(total_calculate_d))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + total_calculate_d + " -- "
						+ total_cost_get_text_d;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * public String value_calculation_verify_(String object, String data) {
	 * 
	 * try {
	 * 
	 * JavascriptExecutor js = (JavascriptExecutor) driver;
	 * 
	 * 
	 * String val_upper_limit_s =
	 * js.executeScript("return $('input[id=\"upperLimit_1\"]').val()").toString();
	 * System.out.println(val_upper_limit_s);
	 * 
	 * Double val_upper_limit_d = Double.parseDouble(val_upper_limit_s);
	 * System.out.println("Upper Yield Limit / ac : " + val_upper_limit_d);
	 * 
	 * 
	 * 
	 * String Percentage_s =
	 * js.executeScript("return $('input[id=\"per_1\"]').val()").toString();
	 * System.out.println(Percentage_s);
	 * 
	 * Double Percentage_d = Double.parseDouble(Percentage_s);
	 * System.out.println("Percentage : " + Percentage_d);
	 * 
	 * // WebElement Price = driver.findElement(By.xpath("//input[@id='price_1']"));
	 * 
	 * String Price_s =
	 * js.executeScript("return $('input[id=\"per_1\"]').val()").toString();
	 * System.out.println(Price_s);
	 * 
	 * Double Price_d = Double.parseDouble(Price_s); System.out.println("Price  : "
	 * + Price_d);
	 * 
	 * //calculation formula Double total_calculate_d = val_upper_limit_d *
	 * Percentage_d * Price_d * 100;
	 * 
	 * System.out.println("total cost :" + total_calculate_d);
	 * 
	 * String total_cost_get_text_s =
	 * js.executeScript("return $('input[id=\"totalCost_1\"]').val()").toString();
	 * System.out.println(total_cost_get_text_s);
	 * 
	 * String trim_EXLVALUEBID = total_cost_get_text_s.replace(",", ""); Double
	 * total_cost_get_text_d = Double.parseDouble(trim_EXLVALUEBID);
	 * System.out.println(total_cost_get_text_d);
	 * 
	 * if (total_cost_get_text_d.equals(total_calculate_d)) return
	 * Constants.KEYWORD_PASS; else return Constants.KEYWORD_FAIL +
	 * " -- text not verified " + total_calculate_d + " -- " +
	 * total_cost_get_text_d; } catch (Exception e) { return Constants.KEYWORD_FAIL
	 * + " Object not found " + e.getMessage(); } }
	 */

	public String fetch_customer(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			// clicked on search icon on Basic detail page

			Thread.sleep(1000);
			// Enter Customer Name
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.xpath("//input[@name='CustomerName']")).sendKeys(data);

			// Click on Search button

			driver.findElement(By.xpath("//a[@ng-click='GetSearchCustomer();']")).click();
			Thread.sleep(1000);

			wait3.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//td[@data-title='Customer Name'])[8]//preceding::a[1]")));
			Thread.sleep(2000);

			driver.findElement(By.xpath("(//td[@data-title='Customer Name'])[8]//preceding::a[1]")).click();
			Thread.sleep(2000);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String fetch_customer_using_code(String object, String data) {
		APP_LOGS.debug("Writing in text box");

		// String newdata = String.valueOf(data);
		try {
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
			wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(object))));
			System.out.println("Element is now clickable");
			// clicked on search icon on Basic detail page

			Thread.sleep(1000);
			// Enter Customer Name
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.xpath("//input[@name='CustomerName']")).sendKeys(data);

			// Click on Search button

			driver.findElement(By.xpath("//a[@ng-click='GetSearchCustomer();']")).click();
			Thread.sleep(1000);

			wait3.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//a[@class='ng-binding'and contains(text(),'" + data + "')]")));
			Thread.sleep(2000);

			driver.findElement(By.xpath("//a[@class='ng-binding'and contains(text(),'" + data + "')]")).click();
			Thread.sleep(2000);

			result = Constants.KEYWORD_PASS;

		} catch (Exception e) {
			result = Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();

		}
		return result;
	}

	public String try_renewal_policy_no(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

		try {

			// List<String> valid_renewal_policy_no = null;
			List<String> valid_renewal_policy_no = new ArrayList<String>();

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				wait3.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[contains(text(),'Easy')])[1]")));
				Thread.sleep(1000);
				System.out.println("Easy health policy is visible in grid");

				// driver.findElement(By.xpath("//a[@class='ng-binding'and
				// contains(text(),'"+data+"')]")).click();
				try {

					// click on renew link of first record of easy health

					driver.findElement(By.xpath("(//td[contains(text(),'Easy')])[1]//preceding::a[2]")).click();
					Thread.sleep(1000);
					wait3.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[@type='button' and contains(text(),'Yes')]")));

					WebElement yes_button = driver
							.findElement(By.xpath("//button[@type='button' and contains(text(),'Yes')]"));

					// click on Yes for proceed further

					driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Yes')]")).click();
					wait3.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//button[contains(text(),'Proceed to generate policy')]")));
					
					Thread.sleep(2000);
					// WebElement product_detail=
					// driver.findElement(By.xpath("(//h4[@class='ng-binding'and
					// contains(text(),'PRODUCT DETAILS')])[1]"));
					if (driver
							.findElement(
									By.xpath("//button[contains(text(),'Proceed to generate policy')]"))
							.isDisplayed()) {
						driver.findElement(
								By.xpath("//button[contains(text(),'Proceed to generate policy')]")).click();
						Thread.sleep(2000);
						WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
						wait1.until(ExpectedConditions.elementToBeClickable(
								By.xpath("(//h4[@class='ng-binding'and contains(text(),'PRODUCT DETAILS')])[1]")));

						result = Constants.KEYWORD_PASS;
					}

					else {

						System.out.println("Not handled");
						

					}
				}

				catch (Exception e) {
					
					Thread.sleep(1000);
					
					wait3.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")));
					Thread.sleep(1000);
					driver.findElement(By
							.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]"))
							.click();
					
					wait3.until(ExpectedConditions.elementToBeClickable(
							By.xpath("(//td[contains(text(),'Easy')])[1]//preceding::a[2]")));
					Thread.sleep(2000);

			/*		driver.findElement(By.xpath("(//td[contains(text(),'Easy')])[1]//preceding::a[2]")).click();
					Thread.sleep(1000);
					wait3.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//button[@type='button' and contains(text(),'Yes')]")));

					// click on Yes for proceed further

					driver.findElement(By.xpath("//button[@type='button' and contains(text(),'Yes')]")).click();
				
					wait3.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")));
*/
				//	int page_counter = 1;
					int page_counter = 1;
					// WebDriverWait wait1 = new WebDriverWait(driver, 120);
					


					
	/*				driver.findElement(By
							.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]"))
							.click();

					wait3.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("(//td[contains(text(),'Easy')])[1]")));

		*/			/*
					 * //click on expiredate for date sorting
					 * 
					 * driver.findElement(By.xpath("//a[contains(text(),'Expiry Date')]")).click();
					 * Thread.sleep(4000);
					 * 
					 * driver.findElement(By.xpath("//a[contains(text(),'Expiry Date')]")).click();
					 * Thread.sleep(4000);
					 */
					Thread.sleep(2000);
					String fetched_date;

					String m = "";
					long r = 0;
					int rowNum = 0;
					String s;

					// No. of Columns
					List col = driver.findElements(By.xpath("//a[contains(text(),'Expiry Date')]"));
					System.out.println("Total No of columns are : " + col.size());
					// No.of rows
					List rows = driver.findElements(By.xpath("//td[@class='ng-scope']//following::td[4]"));
					System.out.println("Total No of rows are : " + rows.size());

					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					System.out.println(c.getTime());
					Date finaldate = c.getTime();
					SimpleDateFormat formated_date = new SimpleDateFormat("dd/MM/yyyy");

					String formated_current_date_s = formated_date.format(finaldate);
					System.out.println("current date is :" + formated_current_date_s);
					Date current_date = (Date) formated_date.parse(formated_current_date_s);

					for (int i = 0; i < rows.size(); i++) {

						// getting all date from grid
						fetched_date = driver
								.findElement(By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[4]"))
								.getText();
						Date formated_fetched_date = (Date) formated_date.parse(fetched_date); // formate fetched date
																								// in dd/mm/yy

						System.out.println("Formated Fetched date is: " + formated_fetched_date);

						System.out.println('\n');

						long current = current_date.getTime();
						long fetched = formated_fetched_date.getTime();

						long timeDiff = current - fetched;

						long day_difference = (timeDiff / (1000 * 60 * 60 * 24));
						System.out.println("Total day difference is: " + day_difference);

						if (day_difference <= 30 && day_difference >= -30) {

							System.out.println("Policy no  : " + driver
									.findElement(
											By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[2]"))
									.getText() + " Counter list : " + valid_renewal_policy_no.size());

							valid_renewal_policy_no.add(driver
									.findElement(
											By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[2]"))

									.getText());
							
							Thread.sleep(1000);
							
						}
						
						// else {
						// i=0;

						if (i >= rows.size() - 1 && page_counter<5) {
							i = 0;

							System.out.println("page counter :" + (page_counter + 1));
							System.out.println("xpath test for pagination :" + driver.findElement(
									By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 1) + "')])")));
							// (//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'9')])
							// (//a[@ng-click='setCurrent(pageNumber)'])["+(page_counter+1)+"]
							WebElement xpath_for__page = driver.findElement(
									By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 1) + "')])"));
							System.out.println("xpath for pagination :" + xpath_for__page);
							xpath_for__page.click();

						
							wait3.until(ExpectedConditions.elementToBeClickable(
									By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 1) + "')])")));
							Thread.sleep(5000);
							page_counter++;

						}
						
						
					}
					for(int policy_count = 0; policy_count < valid_renewal_policy_no.size(); policy_count++) {
			            System.out.println("policy nos : " + valid_renewal_policy_no.get(policy_count));
			            
			            
			        }
					
					Thread.sleep(500);
					
					// print first policy no from list array
					
					System.out.println("first policy no from list isssss : " + valid_renewal_policy_no.get(0));
			
					// click on home icon for navi gate to home page
					
					driver.findElement(By.xpath("//i[contains(@class,'fa fa-home')]")).click(); 

					wait3.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					//CLick on Easy Health Product
					
					driver.findElement(By.xpath("//p[contains(text(),'Easy Health')]")).click(); 
					
					wait3.until(
							ExpectedConditions.elementToBeClickable(By.xpath("(//h4[text()='PRODUCT DETAILS'])[1]")));
					
					// Enter policy no in Old latest policy no field
					for (int i=0; i<valid_renewal_policy_no.size();i++) {
						
						driver.findElement(By.xpath("//input[@id='OldLatestPolicyNo']")).sendKeys(valid_renewal_policy_no.get(i),Keys.TAB); 
						
						wait3.until(
								ExpectedConditions.elementToBeClickable(By.xpath("(//h4[text()='PRODUCT DETAILS'])[1]")));
						
						Thread.sleep(5000);
						
						
						if(!driver.findElement(By.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")).isDisplayed() )
						{
							System.out.println("Policy fetched data and not get any error , so please proceed further");
		break;
						}
							
						else {
							wait3.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")));
							Thread.sleep(1000);
							
							driver.findElement(By.xpath("//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")).click();
							
					}
				}
				// }
			}
				return Constants.KEYWORD_PASS;
				}
				else {

				result = Constants.KEYWORD_FAIL + " Not able to perform action ";

			}

		} catch (

		Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}

	
	
	
	
	public String try_renewal_policy_no1(String object, String data) {
		APP_LOGS.debug("Clicking on any element");
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));

		try {

			// List<String> valid_renewal_policy_no = null;
			List<String> valid_renewal_policy_no = new ArrayList<String>();

			if (CONFIG.getProperty("browserType").equalsIgnoreCase("chrome")) {

				String windowHandle = driver.getWindowHandle();
				driver.switchTo().window(windowHandle);

				// click on Search button and wait till data of selected product visible in grid

				wait3.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//button[@type='button' and contains(text(),'Search')])")));

				Thread.sleep(1000);

				driver.findElement(By.xpath("(//button[@type='button' and contains(text(),'Search')])")).click();

				wait3.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[contains(text(),'Easy')])[1]")));
				Thread.sleep(2000);
				System.out.println("Easy health policy is visible in grid");

				// driver.findElement(By.xpath("//a[@class='ng-binding'and
				// contains(text(),'"+data+"')]")).click();
				try {
					int page_counter = 1;

					Thread.sleep(2000);
					String fetched_date;

					String m = "";
					long r = 0;
					int rowNum = 0;
					String s;

					// No. of Columns
					List col = driver.findElements(By.xpath("//a[contains(text(),'Expiry Date')]"));
					System.out.println("Total No of columns are : " + col.size());
					// No.of rows
					List rows = driver.findElements(By.xpath("//td[@class='ng-scope']//following::td[4]"));
					System.out.println("Total No of rows are : " + rows.size());

					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					System.out.println(c.getTime());
					Date finaldate = c.getTime();
					SimpleDateFormat formated_date = new SimpleDateFormat("dd/MM/yyyy");

					String formated_current_date_s = formated_date.format(finaldate);
					System.out.println("current date is :" + formated_current_date_s);
					Date current_date = (Date) formated_date.parse(formated_current_date_s);

					for (int i = 0; i < rows.size(); i++) {

						// getting all date from grid
						fetched_date = driver
								.findElement(By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[4]"))
								.getText();
						Date formated_fetched_date = (Date) formated_date.parse(fetched_date); // formate fetched date
																								// in dd/mm/yy

						System.out.println("Formated Fetched date is: " + formated_fetched_date);

						System.out.println('\n');

						long current = current_date.getTime();
						long fetched = formated_fetched_date.getTime();

						long timeDiff = current - fetched;

						long day_difference = (timeDiff / (1000 * 60 * 60 * 24));
						System.out.println("Total day difference is: " + day_difference);

						if (day_difference <= 30 && day_difference >= -30) {

							System.out.println("Policy no  : " + driver
									.findElement(
											By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[2]"))
									.getText() + " Counter list : " + valid_renewal_policy_no.size());

							valid_renewal_policy_no.add(driver
									.findElement(
											By.xpath("//tr[" + (i + 1) + "]//td[@class='ng-scope']//following::td[2]"))

									.getText());

							Thread.sleep(500);
							break;
						}

						// else {
						// i=0;
						Thread.sleep(500);

						if (i >= rows.size() - 1 && page_counter < 10) {
							i = 0;
							WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
							Thread.sleep(500);
							System.out.println("page counter :" + (page_counter + 1));
//							System.out.println("xpath test for pagination :" + driver.findElement(
//									By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
//											+ (page_counter + 1) + "')])")));
							Thread.sleep(500);
							WebElement xpath_for__page = driver.findElement(
									By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 1) + "')])[1]"));
							System.out.println("xpath for pagination :" + xpath_for__page);
							Thread.sleep(2500);
							wait5.until(ExpectedConditions
									.elementToBeClickable(By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 2) + "')])[1]")));
							
							xpath_for__page.click();
							Thread.sleep(1000);


							wait5.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//div[@id='LoadingModel']")));
							Thread.sleep(1000);
							
							
							wait5.until(ExpectedConditions
									.elementToBeClickable(By.xpath("(//a[@ng-click='setCurrent(pageNumber)' and contains(text(),'"
											+ (page_counter + 2) + "')])[1]")));
							Thread.sleep(3000);
							page_counter++;

						}

					}
					for (int policy_count = 0; policy_count < valid_renewal_policy_no.size(); policy_count++) {
						System.out.println("policy nos : " + valid_renewal_policy_no.get(policy_count));

					}

					Thread.sleep(200);

					// print first policy no from list array

					System.out.println("first policy no from list isssss : " + valid_renewal_policy_no.get(0));

					// click on home icon for navi gate to home page

					driver.findElement(By.xpath("//i[contains(@class,'fa fa-home')]")).click();

					wait3.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Home')]")));
					// CLick on Easy Health Product

					driver.findElement(By.xpath("//p[contains(text(),'Easy Health')]")).click();

					WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(waitforelement));
					

					// Enter policy no in Old latest policy no field
					for (int i = 0; i < valid_renewal_policy_no.size(); i++) {
						
						//enter policy no and press tab
						driver.findElement(By.xpath("//input[@id='OldLatestPolicyNo']"))
								.sendKeys(valid_renewal_policy_no.get(i), Keys.TAB);
						Thread.sleep(2000);

						wait5.until(
								ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='LoadingModel']")));
						
						Thread.sleep(35000);
						String stay_in = driver.findElement(By.xpath("//input[@id='IStayIn']")).getText();

					//if stay in field is not empty then click on product detail button , else wait 
						if (!stay_in.isEmpty()) {

							driver.findElement(By.xpath("(//h4[text()='PRODUCT DETAILS'])[1]")).click();

						}

						else {
							wait5.until(ExpectedConditions
									.textToBePresentInElementValue(By.xpath("//input[@id='IStayIn']"), stay_in));
						}

						wait5.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='LoadingModel']")));
						ExpectedConditions.elementToBeClickable(By.xpath("(//h4[text()='PRODUCT DETAILS'])[1]"));
						
						WebElement cancle = driver.findElement(By.xpath(
								"//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]"));
						
						//if cancle button not display then fetch policy no and store in excel sheet

						if (!cancle.isDisplayed()) {
							System.out.println("Policy fetched data and not get any error , so please proceed further");

							if (currentTestSuiteXLS.setCellData(object, data, currentTestDataSetID,
									valid_renewal_policy_no.get(i))) {

								System.out.println("Used Policy no Store in sheet ");
								// Thread.sleep(1000);
							} else {

								System.out.println("facing error to store data in sheet");

							}

							break;

						}

						else {
							wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(
									"//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]")));
							Thread.sleep(1000);

							driver.findElement(By.xpath(
									"//button[@class='btn btn-primary margin-bottom-10' and contains(text(),'Cancel')]"))
									.click();

							// refresh page
							// driver.navigate().refresh();

							wait3.until(ExpectedConditions
									.elementToBeClickable(By.xpath("(//h4[text()='PRODUCT DETAILS'])[1]")));
							Thread.sleep(2000);

						}
					}
					// return Constants.KEYWORD_PASS;
				}

				catch (Exception e) {

					Thread.sleep(1000);

					return Constants.KEYWORD_FAIL;

					// }
				}
				// return Constants.KEYWORD_PASS;
			} else {

				// result = Constants.KEYWORD_FAIL + " Not able to perform action ";

			}

		} catch (

		Exception e) {
			result = Constants.KEYWORD_FAIL + " Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	public String check_and_fill_type_ahead(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Dropdown is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			Thread.sleep(1000);
			
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			dropDownListBox.sendKeys(Keys.DOWN);
			
			dropDownListBox.sendKeys(Keys.ENTER);
			Thread.sleep(500);
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Dropdown is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
		dropDownListBox.sendKeys(Keys.DOWN);
		
		dropDownListBox.sendKeys(Keys.ENTER);
		Thread.sleep(500);
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	
	public String check_and_fill_type_ahead_wtithot_down(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
				dropDownListBox.clear();
			System.out.println("Dropdown is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			Thread.sleep(1000);
			
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			//dropDownListBox.sendKeys(Keys.DOWN);
			
			dropDownListBox.sendKeys(Keys.ENTER);
			Thread.sleep(500);
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Dropdown is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
	//	dropDownListBox.sendKeys(Keys.DOWN);
		
		dropDownListBox.sendKeys(Keys.ENTER);
		Thread.sleep(500);
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	
	
	
	public String check_and_fill_type_ahead_by_id(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Dropdown is enabled for edit");
			
			
			Thread.sleep(1000);
			dropDownListBox.clear();
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			dropDownListBox.sendKeys(Keys.DOWN);
			
			dropDownListBox.sendKeys(Keys.ENTER);
			//Thread.sleep(500);
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Dropdown is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
		//dropDownListBox.sendKeys(Keys.DOWN);
		
		dropDownListBox.sendKeys(Keys.ENTER);
		Thread.sleep(500);
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	public String check_and_fill_type_ahead_by_idhiren(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Dropdown is enabled for edit");
			
			
			Thread.sleep(1000);
			dropDownListBox.clear();
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			dropDownListBox.sendKeys(Keys.DOWN);
			
			dropDownListBox.sendKeys(Keys.ENTER);
			//Thread.sleep(500);
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Dropdown is disabled for edit");
			}

		} catch (Exception e) {
			result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	
	
	public String check_and_fill_text_area(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Text area is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			dropDownListBox.clear();
			Thread.sleep(1000);
			
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Text area is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
		
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	public String check_and_fill_text_area_check_display(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean check_display= dropDownListBox.isDisplayed();
			if(check_display) {
			
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Text area is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			dropDownListBox.clear();
			Thread.sleep(1000);
			
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Text area is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			}
			
			else {
				System.out.println("Pan no field is not on UI field");
			}

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
		
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	
	
	public String check_and_fill_text_area_by_id(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Text area is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			dropDownListBox.clear();
			Thread.sleep(1000);
			
			dropDownListBox.sendKeys(data);
			Thread.sleep(1000);
			
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Text area is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.id(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.sendKeys(data);
		Thread.sleep(1000);
		
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	public String check_and_click(String object, String data) throws InterruptedException {
		APP_LOGS.debug("Selecting from list");
		try {
			
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
			Thread.sleep(1000);
			Boolean nn =dropDownListBox.isEnabled();	
			if(nn) {
			
			System.out.println("Text area is enabled for edit");
			
			//result = Constants.KEYWORD_PASS;
			dropDownListBox.click();
			Thread.sleep(1000);
			
			
			result = Constants.KEYWORD_PASS;
			}
			
			else {
				System.out.println("Text area is disabled for edit");
			}
			// driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);

		} catch (Exception e) {
			WebElement dropDownListBox = driver.findElement(By.xpath(OR.getProperty(object)));
		Thread.sleep(1000);
		dropDownListBox.click();
		Thread.sleep(1000);
		
		
		result = Constants.KEYWORD_PASS;
		Thread.sleep(1000);
		}

		return result;
	}
	
	
	
	public String Outlookcommitedunpricced(String object, String data) {
		try {
			Thread.sleep(10000);
			String Yield = driver.findElement(By.xpath("//tbody//tr[2]//td[10]")).getText();
			System.out.println(Yield);
			String Yields = Yield.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double YD = Double.parseDouble(Yields);
			System.out.println("X-Axis Price Value" + YD);
			Thread.sleep(10000);

			// Basis Transaction
			String Value = driver.findElement(By.xpath("//tbody//tr[4]//td[11]")).getText();
			System.out.println(Value);
			String Values = Value.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "").replace(")", "")
					.replace("%", "").replace(",", "");
			Double VA = Double.parseDouble(Values);
			System.out.println("Total Value" + VA);
			Thread.sleep(10000);

			JavascriptExecutor js5 = (JavascriptExecutor) driver;
			String TransactionMenu = js5.executeScript("return $('a[href=\"#/transactions\"]').children().click()", "")
					.toString();
			Thread.sleep(10000);

			driver.findElement(
					By.xpath("//table[@id='tblCashList']/tbody/tr//td[contains(.,'Basis')]//preceding::td[1]")).click();
			Thread.sleep(10000);

			JavascriptExecutor jsbasisprice = (JavascriptExecutor) driver;
			String BasicPrice = jsbasisprice.executeScript("return $('#basis').val()", "").toString();
			System.out.println(BasicPrice);
			String BasicPrices = BasicPrice.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BP = Double.parseDouble(BasicPrices);
			System.out.println("Transaction BasicPrices" + BP);

			JavascriptExecutor jstruckingfee = (JavascriptExecutor) driver;
			String BTruckingFee = jstruckingfee.executeScript("return $('#truckingFees').val()", "").toString();
			System.out.println(BTruckingFee);
			String BTruckingFees = BTruckingFee.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BTF = Double.parseDouble(BTruckingFees);
			System.out.println("BTruckingFees" + BTF);

			JavascriptExecutor jspremium = (JavascriptExecutor) driver;
			String BPremium = jspremium.executeScript("return $('#premium').val()", "").toString();
			System.out.println(BPremium);
			String BPremiums = BPremium.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BPr = Double.parseDouble(BPremiums);
			System.out.println(BPr);

			JavascriptExecutor jsbasisfee = (JavascriptExecutor) driver;
			String Basisfee = jsbasisfee.executeScript("return $('#fee').val()", "").toString();
			System.out.println(Basisfee);
			String Basisfees = Basisfee.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double Bfee = Double.parseDouble(Basisfees);
			System.out.println(Bfee);

			Double val = this.getDiference2();// this.Calculatespread(object, data);
			System.out.println("Here");
			Double diffbasis1 = val;

			Double pricess = YD + BP - BTF + BPr - Bfee + diffbasis1;
			System.out.println("Pricess Value: " + pricess);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String BasisQ = js.executeScript("return $('#qunatity').val()", "").toString();
			System.out.println(BasisQ);
			String BasisQs = BasisQ.replace("$", "").replace(" ", "").replace("/bu", "").replace("(", "")
					.replace(")", "").replace("%", "").replace(",", "");
			Double BQ = Double.parseDouble(BasisQs);
			Thread.sleep(10000);

			Double result1 = (pricess * BQ);

			driver.findElement(By.xpath("//button[contains(.,'Back')]")).click();
			Thread.sleep(5000);

			// Add here

			df2.setRoundingMode(RoundingMode.CEILING);
			Double result5 = result1; // +result2+result3+result4;
			Double result5s = Double.valueOf(df2.format(result5.doubleValue()));

			df2.setRoundingMode(RoundingMode.CEILING);

			System.out.println(result5s);

			Thread.sleep(10000);

			if (result5s.equals(VA))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text content not verified " + result5s + "--" + VA;

		} catch (Exception e) {
			return "0.00";
			// return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

}

//