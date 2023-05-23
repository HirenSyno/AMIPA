package com.sample.util;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail
import static com.sample.test.DriverScript.CONFIG;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.sample.test.Constants;
import com.sample.test.DriverScript;
import com.sample.test.Executor;
import com.sample.xls.read.Xls_Reader;



public class ReportUtil {
	public static String result_FolderName=null;
	public static String report_FolderName=null;
	public static String currentTestSuite;
	public static String totalpass;
	public static String totalfail;
	public static String totalblock;
	public static String testcaseFilePath;
	public static String author;
	//public static void main(String[] arg) throws Exception {
	public boolean reportGeneration(String branch,String module, String browser) throws IOException{
		// read suite.xls
		
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		//System.out.println(date);
		report_FolderName="HTML Reports";//+"_"+Executor.branch+"_"+Executor.module/*+"_"+jenkinsJobID*/;
		new File(report_FolderName).mkdirs();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//config//"+branch+".properties");
		Properties CONFIG= new Properties();
		CONFIG.load(fs);
		
		result_FolderName="Reports";
		String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName+"\\"+result_FolderName;
		//String reportsDirPath=System.getProperty("user.dir")+"\\"+report_FolderName;
		
		
		
		new File(reportsDirPath).mkdirs();
		int rowmoduleindex=2;
		
		String environment=branch;
	
		String URL=CONFIG.getProperty("URL");
		
		String finalresult=null;
		String finalresultsuite=null;
		Xls_Reader current_suite_xls=null;
		int pcount=0;
		int bcount=0;
		int fcount=0;
		int row=0;
		int totalf=0;
		int totalp=0;
		int prowindex=0;
		
		
		String currentTestSuite=null;

		currentTestSuite=module+"_"+browser;
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy_HH_mm_ss");
		if(branch.contains("-"))
		{
			branch.replace("-","_");
		}
		String indexPage= "GI";
		String indexHtmlPath=report_FolderName+"\\"+result_FolderName+"\\"+currentTestSuite+"_index.html";
		//String indexHtmlPath=report_FolderName+"\\"+currentTestSuite+"_index.html";
		
		String testSteps_file=null;
		new File(indexHtmlPath).createNewFile();
		

		FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//config//config//"+branch+".properties");
		CONFIG= new Properties();

		
		try{

			FileWriter fstream = new FileWriter(indexHtmlPath);
			BufferedWriter out = new BufferedWriter(fstream);
			
			out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(d.toString());
			
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>URL</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			out.write(URL);
			
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Browser</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			
			CONFIG.load(fs1);	 		
			out.write(browser);
			out.write("</b></td></tr></table>");
			fs1.close();
			
			
			
			out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Summary Report :</u></h4>");
			
			out.write("<table  border=1 cellspacing=1 cellpadding=1 width=30%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Pass</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Fail</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Total Block</b></td></tr>");
			//int totalTestSuites1=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			
				//String currentTestSuite1=null;

				String suite_result="";
				//for(int currentSuiteID1 =2;currentSuiteID1<= totalTestSuites1;currentSuiteID1++)
				//{
				//currentSuiteID1=DriverScript
					suite_result="";
					currentTestSuite=module+"_"+browser;
					current_suite_xls=null;
					//currentTestSuite1 = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,currentSuiteID1);
					current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//config//"+currentTestSuite+".xlsx");
					String currentTestName=null;
					String currentTestRunmode=null;
					String currentTestDescription=null;
					String testcaseFilePath=currentTestSuite;
					out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><a href=file:///"+DriverScript.dest_path+">");
					out.write(module+"</a>");
					
					for(prowindex=2;prowindex<=current_suite_xls.getRowCount("Test Cases");prowindex++)
					{
						if(current_suite_xls.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("PASS")&& current_suite_xls.getCellData("Test Cases", "Status", prowindex).isEmpty()==false)
						{
							pcount++;
							
						}
						else if(current_suite_xls.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("FAIL") && current_suite_xls.getCellData("Test Cases", "Status", prowindex).isEmpty()==false)
						{
							fcount++;
							//Executor.result=false;
							
						}
						else if(current_suite_xls.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("Blocked") ||current_suite_xls.getCellData("Test Cases", "Status", prowindex).equalsIgnoreCase("Block")&& current_suite_xls.getCellData("Test Cases", "Status", prowindex).isEmpty()==false)
						{
							bcount++;
							
						}
						
					}
					
					
					//out.write("</b></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					out.write("</b></td><td width=20% align= center><FONT COLOR=#00ff00 FACE= Arial  SIZE=2><b>");
					totalpass=String.valueOf(pcount);
					out.write(totalpass);
					//out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					out.write("</b/></td><td width=20% align= center><FONT COLOR=#ff0000 FACE= Arial  SIZE=2><b>");
					totalfail=String.valueOf(fcount);
					out.write(totalfail);
					//out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
					out.write("</b/></td><td width=20% align= center><FONT COLOR=#ffff00 FACE= Arial  SIZE=2><b>");
					totalblock=String.valueOf(bcount);
					//out.write(totalblock);
					pcount=0;
					fcount=0;
					bcount=0;
				
				out.write("</td></tr></table>");
				/*out.write("</b/><tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("TOTAL");
				out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				String totalpc=String.valueOf(totalp);
				out.write(totalpc);
				out.write("</b/></td><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				String totalfc=String.valueOf(totalf);
				out.write(totalfc);
				out.write("</td></tr></table>");*/
			
			
				out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Detailed Report :</u></h4>");
			
			//out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");
				out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td></tr>");

			//int totalTestSuites=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			//currentTestSuite=null;

			String suite_result1="";
			//for(int currentSuiteID =2;currentSuiteID<= totalTestSuites;currentSuiteID++)
			//{
				suite_result1="";
				currentTestSuite=module;
				current_suite_xls=null;
				//currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,currentSuiteID);
				current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//config//"+module+"_"+browser+".xlsx");

				String currentTestName1=null;
				String currentTestRunmode1=null;
				String currentTestDescription1=null;
				String currentcomponent=null;
				Calendar cal1 = Calendar.getInstance();
				DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy_HH_mm_ss");
				//String testcaseFilePath= currentTestSuite+df.format(cal.getTime());
				//String testcaseFilePath= df.format(cal.getTime())+"_detailResult";
				//String testcaseFilePath= indexPage+"_"+"testResult";
				testcaseFilePath= currentTestSuite+"_"+"testResult";
				//String mainpage=indexPage+"_index.html";
				//String testcaseFilePath=currentTestSuite;
				new File(testcaseFilePath).createNewFile();

				//Code to create test case files
				for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);currentTestCaseID++)
				{
					currentTestName1=null;
					currentTestDescription1=null;
					currentTestRunmode1=null;
					currentcomponent=null;
					currentTestName1 = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
					currentcomponent=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.COMPONENT, currentTestCaseID);
					currentTestDescription1 = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.DESCRIPTION, currentTestCaseID);
					currentTestRunmode = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID);
					// make the file corresponding to test Steps

					testSteps_file=report_FolderName+"\\"+result_FolderName+"\\"+currentTestSuite+"_steps.html";
					String backIndexpage1=currentTestSuite+"_steps.html";
					testSteps_file=report_FolderName+"\\"+result_FolderName+"\\"+backIndexpage1;
					
					new File(testSteps_file).createNewFile();
					int rows= current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);
					int cols = current_suite_xls.getColumnCount(Constants.TEST_CASES_SHEET);
					FileWriter fstream_test_steps= new FileWriter(report_FolderName+"\\"+result_FolderName+"\\"+testcaseFilePath+".html");
					BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
					//String mainpage=indexPage+"_index.html";
					String mainpage=currentTestSuite+"_index.html";
					//String stepfile=indexPage+"_steps.html";
					String stepfile=currentTestSuite+"_steps.html";
					//module+"_"+browser+"_"+branch
					out_test_steps.write("<html><HEAD> <TITLE>"+module+" Detail Test Results</TITLE></HEAD><body><table><tr><td><a href='"+module+"_"+browser+"_index.html"+"'><h4 align=left><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Back To index Page</u></b></h4></a></td><td align=right><a href='"+stepfile+"'><h4 align=right><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Click for Detail Result</u></b></h4></a></td></tr></table><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+module+" Test Case Result</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
					out_test_steps.write("<tr>");
					for(int colNum=0;colNum<=4;colNum++)
					{
						//if(colNum!=2)
							out_test_steps.write("<td align= left bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
						if (current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).isEmpty()){
							out_test_steps.write("--");  
						}
						//else if(colNum!=2){
							out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1));  
						//}							  
					}

					out_test_steps.write("</b></tr>");

					// fill the whole sheet
					boolean result_col=false;
					for(int rowNum=2;rowNum<=rows;rowNum++)
					{
						out_test_steps.write("<tr>");
						String tcIDdata=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, 0, rowNum);
						for(int colNum=0;colNum<=4;colNum++)
						{
							String data=null;
							/*if(colNum==2)
							{
								data="";
							}
							else
							{
							data=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, rowNum);
							
							result_col=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).startsWith(Constants.TC_STATUS);
							
							}*/
							
							/*if(colNum==2)
							{
								data="";
							}
							else
							{*/
							data=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, rowNum);
							
							result_col=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, colNum, 1).startsWith(Constants.TC_STATUS);
							
							//}
							
							
							if(data.isEmpty()){
								if(result_col)
									data="NONE";  
								/*else
									data="--";*/
								
							}
							
							if((data.startsWith("Pass") || data.startsWith("PASS")) || data.startsWith("P") || data.startsWith("p") && result_col && colNum!=2)
								out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							else if((data.startsWith("Fail") || data.startsWith("FAIL")) || data.startsWith("F") || data.startsWith("f") && result_col && colNum!=2){
								out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
								if(suite_result1.equals(""))
									suite_result1="FAIL";
							}
							else if((data.startsWith("Skip") || data.startsWith("SKIP")||data.startsWith("Blocked")||data.startsWith("BLOCKED")||data.startsWith("Block")||data.startsWith("BLOCK")) ||data.contains("-") && result_col && colNum!=2)
								out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							/*else if(colNum!=2)
								out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							out_test_steps.write(data);*/
							else if(data.startsWith("NONE")){
								data = "";
								out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							}
							
							else
								out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							
							out_test_steps.write(data);
							
							
							// out_test_steps.write(data);

						}
						out_test_steps.write("</tr>");
					}
					out_test_steps.write("</tr>");
					out_test_steps.write("</table>");  
					out_test_steps.close();

				} 

				//Code complete for creation of test case files


				out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("<a href="+testcaseFilePath+".html>"+currentTestSuite+"</a>");
				out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				//out.write(suiteXLS.getCellData(module, Constants.DESCRIPTION,1));
				out.write("Execution for"+" "+module+"</td></tr>");
				//out.write("</b></td><td width=10% align=center  bgcolor=");


				int rowm;

				/*System.out.println("Total rows are"+suiteXLS.getRowCount("Test Suite"));
				System.out.println("currentSuiteID="+currentSuiteID);*/
				//for(int rowmodule=currentSuiteID;rowmodule<=suiteXLS.getRowCount("Test Suite");rowmodule++)
				//{
					//System.out.println("rowmoduleindex="+rowmodule);
					//if(suiteXLS.getCellData("Test Suite","Runmode",rowmodule).contains("N"))
					//{
						/*finalresultsuite="SKIP";
						finalresult="SKIP";
						out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+finalresult+"</b></td></tr>");
						break;*/
					//}
					//else if(suiteXLS.getCellData("Test Suite","Runmode",rowmodule).equalsIgnoreCase("Y"))

					//{
						//System.out.println("N rowmoduleindex="+rowmodule);
						current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"///config//"+module+"_"+browser+".xlsx");
						for(row=2;row<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);row++)
						{
							
							if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(Constants.KEYWORD_FAIL))
							{
								finalresult="FAIL";
								//out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
								break;
							}
							else if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(Constants.KEYWORD_PASS))
							{
								
								finalresult="PASS";
								
								//out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							}
							else if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(Constants.KEYWORD_SKIP))
							{
								
								finalresult="SKIP";
								
								//out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							}
							else if(current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TC_STATUS,row).equalsIgnoreCase(""))
							{
								
								finalresult="";
								
								//out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							}
							
						
						//System.out.println("after loop row="+row);
						
						if(finalresult=="PASS")
						{
							out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
						break;	
						}
						else if(finalresult=="FAIL")
						{
							out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
							break;
						}
						else if(finalresult=="SKIP")
						{
							out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+finalresult+"</b></td></tr>");
							break;
						}
						else if(finalresult.equalsIgnoreCase(""))
						{
							out.write("white><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+finalresult+"</b></td></tr>");
							break;
						}
				
						}
				
				//for  test steps 

				int  rows= current_suite_xls.getRowCount(Constants.TEST_STEPS_SHEET);
				int  cols = current_suite_xls.getColumnCount(Constants.TEST_STEPS_SHEET);
				FileWriter  fstream_test_steps= new FileWriter(testSteps_file);
				BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
				//String backIndexpage=currentTestSuite1+"_index.html";
				String backIndexpage=module+"_"+browser+"_index.html";
				out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+"Detail Test Results</TITLE></HEAD><body><table><tr><td><a href="+backIndexpage+"><h4 align=left><FONT COLOR=660066 FACE=AriaL SIZE=1><b><u>Back To Index Page</u></b></h4></a></td></tr></table><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Case Result</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
				out_test_steps.write("<tr>");
				for(int colNum=0;colNum<cols;colNum++){
					out_test_steps.write("<td align= left bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
					if (current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).isEmpty()){
						out_test_steps.write("--");  
					}
					else{
						out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));  
					}							  
				}

				out_test_steps.write("</b></tr>");

				// fill the whole sheet
				boolean result_col=false;
				for(int rowNum=2;rowNum<=rows;rowNum++)
				{
					out_test_steps.write("<tr>");
					String tcIDdata=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, 0, rowNum);

					for(int colNum=0;colNum<cols;colNum++){
						String data=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, rowNum);

						result_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT);
						if(data.isEmpty()){
							if(result_col)
								data="NONE";  
							else
								data="--";
						}
						if((data.startsWith("Pass") || data.startsWith("PASS")) || data.startsWith("P") || data.startsWith("p")  && result_col)
							out_test_steps.write("<td align=left bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						else if((data.startsWith("Fail") || data.startsWith("FAIL")) || data.startsWith("F") || data.startsWith("F") && result_col){
							out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							if(suite_result1.equals(""))
								suite_result1="FAIL";
						}
						else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
							out_test_steps.write("<td align=left bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						else if((data.startsWith("NONE") && result_col)) {
							out_test_steps.write("<td align=left bgcolor=white><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							data="";
						}
						else 
							out_test_steps.write("<td align= left bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
						out_test_steps.write(data);
						// out_test_steps.write(data);

					}
					out_test_steps.write("</tr>");
				}
				out_test_steps.write("</tr>");


				out_test_steps.write("</table>");  
				out_test_steps.close();

				// }
			
			//Close the output stream
			out.write("</table>");
			out.close();
		return Executor.result;
		}
		catch (Exception e){
		//	return Executor.result;
		}
		return false;

		
	}


}

