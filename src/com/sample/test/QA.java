package com.sample.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QA {

	
	 public  void main(String[] args) {
		String dates=getCurrentDateandTime();
		System.out.println(dates);
	 }
		 
		 public String getCurrentDateandTime() {
			 
		 
		// Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
		 
		 //get current date time with Date()
		 Date date = new Date();
		 
		 // Now format the date
		 String date1= dateFormat.format(date);
		 
		 // Print the Date
		 //
		return date1;
		 
		 }

}