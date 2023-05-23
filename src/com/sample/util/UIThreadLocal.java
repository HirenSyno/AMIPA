package com.sample.util;


import org.openqa.selenium.WebDriver;
import java.lang.ThreadLocal;
public class UIThreadLocal {

	 private static ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();

	    /**
	     * webdriver object used by thread for running all test cases.
	     *
	     * @return WebDriver
	     */
	    public static WebDriver getWebDriver() {
	        return webdriver.get();
	    }

	    public static void setWebDriver(WebDriver dobj) {
	        webdriver.set(dobj);
	    }

}
